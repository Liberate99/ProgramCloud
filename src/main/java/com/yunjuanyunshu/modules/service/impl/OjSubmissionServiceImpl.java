package com.yunjuanyunshu.modules.service.impl;

import com.yunjuanyunshu.modules.common.ResponseCode;
import com.yunjuanyunshu.modules.entity.OjSubmission;
import com.yunjuanyunshu.modules.mapper.OjSubmissionDao;
import com.yunjuanyunshu.modules.messenger.MessageSender;
import com.yunjuanyunshu.modules.service.OjSubmissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author carl
 * @since 2018-02-12
 */
@Service
public class OjSubmissionServiceImpl extends ServiceImpl<OjSubmissionDao, OjSubmission> implements OjSubmissionService {

    @Autowired
    private OjSubmissionDao ojSubmissionDao;

    /**
     * 自动注入的MessageSender对象.
     */
    @Autowired
    private MessageSender messageSender;

    @Override
    public void createSubmission(OjSubmission ojSubmission, RespInfo respInfo) {
        ojSubmission.setSubmitTime(new Date());
        ojSubmissionDao.createSubmission(ojSubmission);
        long submissionId = ojSubmission.getId();
        createSubmissionTask(submissionId);
        respInfo.setCode(ResponseCode.SUCCESS.getCode());
        respInfo.setMsg("提交代码成功");
        respInfo.setValue(submissionId);
    }

    /**
     * 创建评测任务, 将提交的信息提交至消息队列.
     * @param submissionId - 提交记录的唯一标识符
     */
    public void createSubmissionTask(long submissionId) {
        Map<String, Object> mapMessage = new HashMap<>();
        mapMessage.put("event", "SubmissionCreated");
        mapMessage.put("submissionId", submissionId);

        messageSender.sendMessage(mapMessage);
    }
}