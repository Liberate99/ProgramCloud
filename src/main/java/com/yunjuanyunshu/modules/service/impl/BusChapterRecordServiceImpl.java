package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.mapper.BusChapterRecordDao;
import com.yunjuanyunshu.modules.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.util.UserUtil;
import com.yunjuanyunshu.yunfd.common.util.StringUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
@Service
public class BusChapterRecordServiceImpl extends ServiceImpl<BusChapterRecordDao, BusChapterRecord> implements BusChapterRecordService {

    @Autowired
    private UserService userService;
    @Autowired
    private BusChapterService busChapterService;
    @Autowired
    private BusCourseRecordService busCourseRecordService;

    @Override
    public void inOrUpBusCpRecord(User user, BusChapterRecord busChapterRecord, RespInfo respInfo) {
        try {
            if (StringUtils.isNotBlank(busChapterRecord.getChapterId()) && StringUtils.isNotBlank(user.getToken())) {
                //如果章节id和token不为空，则返回参数为空
                user = (User) UserUtil.getUser(user.getToken(), respInfo);
                //后续将统一处理User
                if (user == null) {
                    respInfo.setError(ServiceErrorCodeEnum.TokenIsOverdue.getErrorStr());
                    respInfo.setCode(ServiceErrorCodeEnum.TokenIsOverdue.getErrorCode());
                    return;
                }
                //通过token获取用户
                BusChapterRecord busChapterRecord1 = super.selectOne(new EntityWrapper<BusChapterRecord>().
                        eq("chapter_id", busChapterRecord.getChapterId()).eq("user_id", user.getId()));
                //通过用户id和章节id查找章节记录
                if (busChapterRecord1 != null) {
                    busChapterRecord.setId(busChapterRecord1.getId());
                }
                BusChapter busChapter = busChapterService.selectById(busChapterRecord.getChapterId());
                //如果是更新操作无需执行这两步，执行一下也死不了人，就不写if了
                busChapterRecord.setChapterId(busChapter.getId());
                busChapterRecord.setCourseId(busChapter.getCourseId());
                busChapterRecord.setUserId(user.getId());
                super.insertOrUpdate(busChapterRecord);
                respInfo.setMsg("修改章节记录信息成功");
                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            } else {
                respInfo.setError("章节ID或用户token为空");
                respInfo.setCode(ServiceErrorCodeEnum.ParamterIsMissing.getErrorCode());
            }

        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("插入或更新章节记录信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    /**
     * 课程完成方法
     *
     * @param user
     * @param busCourse
     * @param respInfo
     */
    @Override
    public void finishProgress(User user, BusCourse busCourse, RespInfo respInfo) {

        try {
            user = (User) UserUtil.getUser(user.getToken(), respInfo);
            //这门课程所完成的章节数
            Integer finishNum = super.selectCount(new EntityWrapper<BusChapterRecord>()
                    .eq("user_id", user.getId())
                    .ne("finishTime", "")
                    .eq("course_id", busCourse.getId()));
            //这个课程的所有子章节
            Integer allChapternum = busChapterService.selectCount(new EntityWrapper<BusChapter>()
                    .ne("parent_id", "")
                    .eq("course_id", busCourse.getId()));
            //用户与课程相关联的表要记录它的完成进度
            BusCourseRecord busCourseRecord = busCourseRecordService.selectOne(new EntityWrapper<BusCourseRecord>()
                    .eq("course_id", busCourse.getId())
                    .eq("user_id", user.getId()));
            //用户还没有关注学习
            if (busCourseRecord == null) {
                busCourseRecord = new BusCourseRecord();
                busCourseRecord.setCompletion("0");
            } else {
                if (finishNum > 0) {
                    //创建一个数值格式化对象
                    NumberFormat numberFormat = NumberFormat.getInstance();
                    //设置精确到小数点后2位
                    numberFormat.setMaximumFractionDigits(1);
                    String result = numberFormat.format((float) finishNum / (float) allChapternum * 100);

                    busCourseRecord.setCompletion(result);
                    busCourseRecordService.insertOrUpdate(busCourseRecord);
                }
            }

            respInfo.setValue(busCourseRecord);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg("查询课程学习进度成功");
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("查询课程学习进度失败");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }


}