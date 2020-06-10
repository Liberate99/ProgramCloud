package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusClass;
import com.yunjuanyunshu.modules.entity.VwCourseRecordName;
import com.yunjuanyunshu.modules.mapper.VwCourseRecordNameDao;
import com.yunjuanyunshu.modules.service.VwCourseRecordNameService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * VIEW 服务实现类
 * </p>
 *
 * @author carl
 * @since 2017-11-02
 */
@Service
public class VwCourseRecordNameServiceImpl extends ServiceImpl<VwCourseRecordNameDao, VwCourseRecordName> implements VwCourseRecordNameService {



        @Override
        public void getCourseRecordName(Page<VwCourseRecordName> page, BusClass busClass, RespInfo respInfo) {

                try {
                        Page<VwCourseRecordName> vwCourseRecordNamePage;
                        vwCourseRecordNamePage = super.selectPage(page,new EntityWrapper<VwCourseRecordName>()
                                .eq("class_id",busClass.getId())
                                .orderBy(page.getOrderByField(), page.isAsc()));

                        respInfo.setValue(vwCourseRecordNamePage);
                        respInfo.setMsg("获取改班级的学生信息成功");
                        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                } catch (Exception ex) {
                        respInfo.setMsg("操作失败");
                        respInfo.setError(ex.getMessage());
                        respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                }
        }
}