package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.entity.BusStudentInfo;
import com.yunjuanyunshu.modules.mapper.BusStudentInfoDao;
import com.yunjuanyunshu.modules.service.BusStudentInfoService;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author carl
 * @since 2017-11-17
 */
@Service
public class BusStudentInfoServiceImpl extends ServiceImpl<BusStudentInfoDao, BusStudentInfo> implements BusStudentInfoService {

    @Override
    public void insertOrUpdateStu (BusStudentInfo busStudentInfo, RespInfo respInfo) {
        // 该学校（组织）已存在相同学号
        int count_sameWorkIDMajor = super.selectCount(new EntityWrapper<BusStudentInfo>()
                .eq("work_id", busStudentInfo.getWorkId())
                .eq("college_id",busStudentInfo.getCollegeId())
                .ne("user_id",busStudentInfo.getUserId()));
        // 该学校（组织）的专业已存在相同用户
        int count_sameUserIDMajor = super.selectCount(new EntityWrapper<BusStudentInfo>()
                .eq("college_id",busStudentInfo.getCollegeId())
                .eq("major",busStudentInfo.getMajor())
                .eq("user_id",busStudentInfo.getUserId()));
        if (count_sameWorkIDMajor > 0) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("该学号在该院校已存在!");
            return;
        } else if(count_sameUserIDMajor > 0) {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("该院校该专业下已存在该学生!");
            return;
        } else {
            super.insertOrUpdate(busStudentInfo);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg("保存学生信息成功");
        }
    }

    @Override
    public void getStudentCollegeIDInfoFromUserID(BusStudentInfo busStudentInfo, RespInfo respInfo) {
        System.out.println(busStudentInfo.getUserId());
        int count = super.selectCount(new EntityWrapper<BusStudentInfo>().eq("user_id", busStudentInfo.getUserId()));
        if(count == 0){
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("该用户没有完善相关信息!");
            return;
        } else if(count == 1) {
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg("获取院校ID成功");
            List<BusStudentInfo> busStudentInfos = super.selectList(new EntityWrapper<BusStudentInfo>().eq("user_id", busStudentInfo.getUserId()));
            System.out.println(busStudentInfos.get(0).getCollegeId());
            respInfo.setValue(busStudentInfos);
        } else {
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg("数据库错误：可能存在多条重复记录!");//可能存在多条重复记录
            return;
        }
    }

}