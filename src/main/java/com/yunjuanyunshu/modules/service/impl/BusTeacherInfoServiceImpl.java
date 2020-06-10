package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunjuanyunshu.modules.entity.BusTeacherInfo;
import com.yunjuanyunshu.modules.mapper.BusTeacherInfoDao;
import com.yunjuanyunshu.modules.service.BusTeacherInfoService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author carl
 * @since 2017-11-17
 */
@Service
public class BusTeacherInfoServiceImpl extends ServiceImpl<BusTeacherInfoDao, BusTeacherInfo> implements BusTeacherInfoService {

    @Override
    public void insertOrUpdateTeacher(BusTeacherInfo busTeacherInfo, RespInfo respInfo) {
        int count = super.selectCount(new EntityWrapper<BusTeacherInfo>().eq("work_id", busTeacherInfo.getWorkId())
                .eq("college_id",busTeacherInfo.getCollegeId()).ne("user_id",busTeacherInfo.getUserId()));
        if (count > 0) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("该教工号在该院校已存在!");
            return ;
        }
        if (busTeacherInfo.getId() == null) {
            busTeacherInfo.setCreateDate(new Date());
        }
        busTeacherInfo.setUpdateDate(new Date());
        super.insertOrUpdate(busTeacherInfo);
        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        respInfo.setMsg("保存教师信息成功");
    }
}