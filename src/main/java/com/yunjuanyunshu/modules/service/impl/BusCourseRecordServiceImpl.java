package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.mapper.BusCourseDao;
import com.yunjuanyunshu.modules.mapper.BusCourseRecordDao;
import com.yunjuanyunshu.modules.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.util.UserUtil;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
@Service
public class BusCourseRecordServiceImpl extends ServiceImpl<BusCourseRecordDao, BusCourseRecord> implements BusCourseRecordService {

        @Autowired
        private BusCodeRecordService busCodeRecordService;

        @Autowired
        private UserService userService;

        @Autowired
        private BusCourseService busCourseService;

        @Autowired
        private BusChapterService busChapterService;

        @Autowired
        private BusChapterRecordService busChapterRecordService;

        @Autowired
        private BusCourseDao busCourseDao;

        /**
         * 查询历史课程记录
         * @param user
         * @param num
         * @param respInfo
         */
        @Override
        public void getLatestCourseRecordByUser(User user, int num, RespInfo respInfo) {
                try {

                        User currentUser =  UserUtil.getUser(user.getToken(),respInfo);
                        //查询当前用户的课程记录的最近一条
                        List<BusCourse> busCourseList = busCourseDao.getLatestCourseRecordByUser(currentUser.getId(),num);
                        if (busCourseList == null) {
                                respInfo.setMsg("暂时没有历史课程");
                                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                                return ;
                        }
                        respInfo.setValue(busCourseList);
                        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                        respInfo.setMsg("获取最近课程成功");
                } catch (Exception ex) {
                        respInfo.setValue(ex);
                        respInfo.setMsg("获取信息出错");
                        respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                        respInfo.setError(ex.toString());
                }

        }


        @Override
        public void getLastestChapter(User user, BusCourse busCourse, RespInfo respInfo) {
                try {
                        user = UserUtil.getUser(user.getToken(),respInfo);
                        BusCourseRecord busCourseRecord = super.selectOne(new EntityWrapper<BusCourseRecord>()
                                .eq("course_id", busCourse.getId())
                                .eq("user_id", user.getId()));
                        if (busCourseRecord != null) {
                                respInfo.setValue(busCourseRecord);
                                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                                respInfo.setMsg("获取最近章节成功");
                        } else {
                                busCourseRecord = new BusCourseRecord();
                                //获取该课程的子章节列表的第一个章节
                                //先找到第一父章节
                                BusChapter pBusChapter = busChapterService.selectOne(new EntityWrapper<BusChapter>()
                                        .eq("course_id" ,busCourse.getId())
                                        .eq("parent_id","")
                                        .orderBy("sort", true));
                                BusChapter cBusChpater = busChapterService.selectOne(new EntityWrapper<BusChapter>()
                                        .eq("parent_id",pBusChapter.getId())
                                        .orderBy("sort", true));
                                busCourseRecord.setLastest(cBusChpater.getId());

                                respInfo.setValue(busCourseRecord);
                                respInfo.setCode(2);
                                respInfo.setMsg("可以开始学习了");
                        }

                } catch (Exception ex) {
                        respInfo.setValue(ex);
                        respInfo.setMsg("获取信息出错");
                        respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                        respInfo.setError(ex.toString());
                }
        }

        @Override
        public void beginStudy(User user, BusCourse busCourse, RespInfo respInfo) {
                try{
                        user = UserUtil.getUser(user.getToken(),respInfo);
                        BusCourseRecord busCourseRecord = super.selectOne(new EntityWrapper<BusCourseRecord>()
                                .eq("course_id", busCourse.getId())
                                .eq("user_id", user.getId()));
                        if (busCourseRecord != null) {
                                respInfo.setMsg("该用户已进入课程学习");
                                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                                return;
                        }
                        busCourseRecord = new BusCourseRecord();
                        busCourseRecord.setCourseId(busCourse.getId());
                        busCourseRecord.setUserId(user.getId());
//                      busCourseRecord.setCompletion("0");
                        busCourseRecord.setCreateTime(new Date());
                        //busCourse的focus字段加1
                        BusCourse bsCourse = busCourseService.selectById(busCourse.getId());
                        int focus = Integer.parseInt(bsCourse.getFocus()) + 1;
                        busCourse.setFocus(String.valueOf(focus));
                        busCourseService.insertOrUpdate(busCourse);
                        super.insertOrUpdate(busCourseRecord);
                        respInfo.setMsg("用户加入课程学习成功");
                        respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                } catch (Exception ex) {
                        respInfo.setValue(ex);
                        respInfo.setMsg("操作错误");
                        respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                        respInfo.setError(ex.toString());
                }
        }

        @Override
        public void setLastestChapter(User user, BusChapter busChapter, RespInfo respInfo) {
                try {
//                        user  = userService.selectOne(new EntityWrapper<User>().eq("token",user.getToken()));
                        user = (User) UserUtil.getUser(user.getToken(),respInfo);
                        busChapter = busChapterService.selectOne(new EntityWrapper<BusChapter>()
                                .eq("id", busChapter.getId()));
                        BusCourse busCourse = busCourseService.selectOne(new EntityWrapper<BusCourse>()
                                .eq("id", busChapter.getCourseId()));
                        BusCourseRecord busCourseRecord = super.selectOne(new EntityWrapper<BusCourseRecord>()
                                .eq("course_id", busCourse.getId())
                                .eq("user_id", user.getId()));
                        if (busCourseRecord != null) {
                                busCourseRecord.setLastest(busChapter.getId());
                                super.insertOrUpdate(busCourseRecord);
                                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                                respInfo.setMsg("更新最近章节成功");
                        } else {
                                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                                respInfo.setMsg("获取用户课程相关表失败");
                        }
                } catch (Exception ex) {
                        respInfo.setError(ex.getMessage());
                }
        }


}