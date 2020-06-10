package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.mapper.BusCourseDao;
import com.yunjuanyunshu.modules.mapper.UserDao;
import com.yunjuanyunshu.modules.service.*;
import com.yunjuanyunshu.modules.util.Base64Util;
import com.yunjuanyunshu.modules.util.IdGen;
import com.yunjuanyunshu.modules.util.UserUtil;
import com.yunjuanyunshu.yunfd.common.util.StringUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xjz
 * @since 2017-07-04
 */
@Service
public class BusCourseServiceImpl extends ServiceImpl<BusCourseDao, BusCourse> implements BusCourseService {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private BusCourseRecordService busCourseRecordService;
    @Autowired
    private BusCourseService busCourseService;
    @Autowired
    private DictService dictService;
    @Autowired
    private BusChapterService busChapterService;
    @Autowired
    private BusTeacherInfoService busTeacherInfoService;
    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private DataSourceTransactionManager sourceTransactionManager;
    @Override
    public void focusCourse(User user, BusCourse busCourse, RespInfo respInfo) {
        try {
//                        EntityWrapper<User> ew = new EntityWrapper<User>();
//                        ew.eq("token",user.getToken());
//                        User currentUser = userService.selectOne(ew);
//                        if(currentUser==null){
//                                respInfo.setCode(ServiceErrorCodeEnum.TokenIsOverdue.getErrorCode());
//                                throw new Exception(ServiceErrorCodeEnum.TokenIsOverdue.getErrorStr());
//                        }
            User currentUser = (User) UserUtil.getUser(user.getToken(), respInfo);
            //判断用户是否关注课程
            List<BusCourseRecord> busCourseRecordList = busCourseRecordService.selectList(
                    new EntityWrapper<BusCourseRecord>()
                            .eq("user_id", currentUser.getId()).eq("course_id", busCourse.getId()));
            if (busCourseRecordList.size() != 0) {
                throw new Exception("用户已经关注该课程！");
            }
            BusCourseRecord busCourseRecord = new BusCourseRecord();
            busCourseRecord.setCourseId(busCourse.getId());
            busCourseRecord.setUserId(currentUser.getId());
            busCourseRecord.setCompletion("0");
            busCourseRecord.setCreateTime(new Date());
            //busCourse的focus字段加1
            BusCourse bsCourse = busCourseService.selectById(busCourse.getId());
            int focus = Integer.parseInt(bsCourse.getFocus()) + 1;
            busCourse.setFocus(String.valueOf(focus));
            busCourseService.insertOrUpdate(busCourse);
            respInfo.setValue(busCourseRecordService.insertOrUpdate(busCourseRecord));
            respInfo.setMsg("关注成功");
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setError(ex.getMessage());
        }


    }

    @Override
    public void myCourse(User user, Boolean hasFinished, RespInfo respInfo) {
        try {
//                        EntityWrapper<User> ew = new EntityWrapper<User>();
//                        ew.eq("token",user.getToken());
//                        User currentUser = userService.selectOne(ew);
//                        User currentUser = (User) CacheUtils.get(user.getToken());
//                        if(currentUser==null){
//                                respInfo.setCode(ServiceErrorCodeEnum.TokenIsOverdue.getErrorCode());
//                                throw new Exception(ServiceErrorCodeEnum.TokenIsOverdue.getErrorStr());
//                        }
            User currentUser = (User) UserUtil.getUser(user.getToken(), respInfo);
            //判断用户是否关注课程
            List<BusCourseRecord> busCourseRecordList = busCourseRecordService.selectList(
                    new EntityWrapper<BusCourseRecord>()
                            .eq("user_id", currentUser.getId()));
            if (busCourseRecordList.size() == 0) {
                throw new Exception("未关注任何课程！");
            }
            List<BusCourse> busCourselist = new ArrayList<BusCourse>();
            for (BusCourseRecord busCourseRecord : busCourseRecordList) {
                if (hasFinished) {
                    if (!"100".equals(busCourseRecord.getCompletion())) {
                        continue;
                    }
                } else {
                    if ("100".equals(busCourseRecord.getCompletion())) {
                        continue;
                    }
                }

                BusCourse busCourse = busCourseService.selectOne(new EntityWrapper<BusCourse>().eq("id", busCourseRecord.getCourseId()));
                if (busCourse != null) {
                    busCourselist.add(busCourse);
//                                        busCourse.set
                    //完成度
                }
            }
            respInfo.setValue(busCourselist);
            respInfo.setMsg("获取成功");
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setError(ex.getMessage());
        }

    }

    @Override
    public void recommendedCourses(User user, int num, RespInfo respInfo) {
        try {
            User currentUser = (User) UserUtil.getUser(user.getToken(), respInfo);
            //推荐课程结果集
            List<BusCourse> resBusCourseList = new ArrayList<BusCourse>();
            //判断用户是否关注课程
            List<BusCourseRecord> busCourseRecordList = busCourseRecordService.selectList(
                    new EntityWrapper<BusCourseRecord>()
                            .eq("user_id", currentUser.getId()));
            //如果有关注课程
            if (busCourseRecordList.size() != 0) {
                //先查找用户所有已关注的课程
                List<BusCourseRecord> hasbusCourseRecordList = busCourseRecordService.selectList(
                        new EntityWrapper<BusCourseRecord>()
                                .eq("user_id", currentUser.getId()));
                List<String> hasbusCourseIdList = new ArrayList<String>();

                for (BusCourseRecord item : hasbusCourseRecordList) {
                    hasbusCourseIdList.add(item.getCourseId());
                }
                //查询当前用户最近关注的一门课程
                BusCourseRecord busCourseRecord = busCourseRecordService.selectOne(
                        new EntityWrapper<BusCourseRecord>()
                                .eq("user_id", currentUser.getId())
                                .orderBy("create_time", false));
                BusCourse busCourse = busCourseService.selectOne(
                        new EntityWrapper<BusCourse>().eq("id", busCourseRecord.getCourseId()));
                //根据用户最近所选的课程查找 同课程分类的 未关注的 关注度高的 前num个课程
                List<BusCourse> busCourseList = busCourseService.selectList(
                        new EntityWrapper<BusCourse>()
                                .eq("classify", busCourse.getClassify())
                                .eq("del_flag", "1")
                                .notIn("id", hasbusCourseIdList)
                                .orderBy("focus", false));
                if (busCourseList.size() > num) {
                    busCourseList = busCourseList.subList(0, num);
                }
                //加入到结果集
                for (int i = 0; i < busCourseList.size(); i++) {
                    resBusCourseList.add(busCourseList.get(i));
                }

                for (BusCourse item : resBusCourseList) {
                    hasbusCourseIdList.add(item.getId());
                }
                //如果得到的推荐结果集不满足Num个
                if (resBusCourseList.size() < num) {
                    //把未关注的课程查找出来，同样按关注度排序
                    List<BusCourse> busCourseList1 = busCourseService.selectList(
                            new EntityWrapper<BusCourse>()
                                    .notIn("id", hasbusCourseIdList)
                                    .eq("del_flag", "1")
                                    .orderBy("focus", false));
                    //加入到结果集中
                    for (int i = 0; resBusCourseList.size() < num; i++) {
                        //如果用户未关注的课程少于需要推荐的课程数的时候,就直接返回(因为用户把课程都关注的差不多了,不需要再推荐给他了)
                        if (i >= busCourseList1.size()) {
                            break;
                        }
                        resBusCourseList.add(busCourseList1.get(i));
                    }
                }
            } else {
                //按课程关注数来选取num门推荐课程
                List<BusCourse> busCourseList2 = busCourseService.selectList(
                        new EntityWrapper<BusCourse>().orderBy("focus", false)).subList(0, num);

                for (int i = 0; resBusCourseList.size() < num; i++) {
                    resBusCourseList.add(busCourseList2.get(i));
                }
            }
            respInfo.setValue(resBusCourseList);
            respInfo.setMsg("获取成功");
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setError(ex.getMessage());
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }
    }

    @Override
    public void getCourse(User user, Page<BusCourse> page, RespInfo paraRespInfo, Integer hasPublic,String courseStatus) {
        try {
            //判断角色
            User u = userDao.getUserWithRoleTypeById(user.getId());
            if (u == null || u.getRoleType() == null) {
                throw new Exception(ServiceErrorCodeEnum.ERROR.getErrorStr());
            }

            EntityWrapper<BusCourse> ew = new EntityWrapper<BusCourse>();
            ew.setSqlSelect("id,title,classify,classtime,score,create_time,create_by,del_flag,has_public,pic,view,focus");
            if(StringUtils.isNotBlank(courseStatus)){
                ew.eq("del_flag",courseStatus);
            }
            //超级管理员
            if ("2".equals(u.getRoleType())) {
                ew.orderBy(page.getOrderByField(),
                        page.isAsc());
                if (hasPublic != null) {
                    ew.eq("has_public", hasPublic);
                }
            }
            //老师
            if ("1".equals(u.getRoleType())) {
                //查询创建者或者公有的课程
                if (hasPublic == null) {
                    ew.eq("create_by", user.getId());
                } else {
                    ew.eq("has_public", hasPublic);
                    if (hasPublic == 0) {
                        ew.eq("create_by", user.getId());
                    }
                }
                ew.orderBy(page.getOrderByField(),
                        page.isAsc());
            }

            Page<BusCourse> busCoursePage = super.selectPage(page, ew);
            List<BusCourse> busCourseList = busCoursePage.getRecords();
            //需调整为一次性获取字典值
            List<Dict> dictTag = dictService.selectList(new EntityWrapper<Dict>()
                    .eq("type", "course_tag"));
            List<Dict> dictStatus = dictService.selectList(new EntityWrapper<Dict>()
                    .eq("type", "course_status"));
            for (BusCourse busCourse : busCourseList) {
                if (busCourse.getClassify() == null) {
                    continue;
                }
                busCourse.setClassifyName(getDictLabelByValue(busCourse.getClassify(), dictTag));
                busCourse.setDelFlagName(getDictLabelByValue(busCourse.getDelFlag(), dictStatus));
            }
            busCoursePage.setRecords(busCourseList);
            paraRespInfo.setValue(busCoursePage);
            paraRespInfo.setMsg("查询成功");
            paraRespInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            paraRespInfo.setError(ex.getMessage());
            paraRespInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }
    }

    /**
     * 获取我创建的FPGA课程
     * @param user
     * @param page
     * @param paraRespInfo
     */
    @Override
    public void getFPGACourseCreateByMe(User user, Page<BusCourse> page, RespInfo paraRespInfo){
        try {
            // 根据token获取用户
            User u = (User) UserUtil.getUser(user.getToken(), paraRespInfo);
            //User u = userDao.getUserWithRoleTypeById(user.getId());

            if (u == null || u.getRoleType() == null) {
                throw new Exception(ServiceErrorCodeEnum.ERROR.getErrorStr());
            }

            EntityWrapper<BusCourse> ew = new EntityWrapper<BusCourse>();
            //ew.setSqlSelect("id,title,classify,classtime,score,create_time,create_by,del_flag,has_public,pic,view,focus");
            ew.eq("create_by",u.getId());
            ew.eq("classify",'7');

            Page<BusCourse> busCoursePage = super.selectPage(page, ew);
            List<BusCourse> busCourseList = busCoursePage.getRecords();
//            //需调整为一次性获取字典值
//            List<Dict> dictTag = dictService.selectList(new EntityWrapper<Dict>()
//                    .eq("type", "course_tag"));
//            List<Dict> dictStatus = dictService.selectList(new EntityWrapper<Dict>()
//                    .eq("type", "course_status"));
            for (BusCourse busCourse : busCourseList) {
                if (busCourse.getClassify() == null) {
                    continue;
                }
//                busCourse.setClassifyName(getDictLabelByValue(busCourse.getClassify(), dictTag));
//                busCourse.setDelFlagName(getDictLabelByValue(busCourse.getDelFlag(), dictStatus));
            }
            busCoursePage.setRecords(busCourseList);
            paraRespInfo.setValue(busCoursePage);
            paraRespInfo.setMsg("查询成功");
            paraRespInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            paraRespInfo.setError(ex.getMessage());
            paraRespInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
        }
    }

    @Override
    public void getTeacherInfo(BusTeacherInfo busTeacherInfo, RespInfo respInfo) {
        try {
            busTeacherInfo = busTeacherInfoService.selectOne(new EntityWrapper<BusTeacherInfo>()
                    .eq("user_id", busTeacherInfo.getUserId()));
            Organization deOrganization = organizationService.selectOne(new EntityWrapper<Organization>()
                    .eq("id", busTeacherInfo.getDepartmentId()));
            Organization coOrganization = organizationService.selectOne(new EntityWrapper<Organization>()
                    .eq("id", deOrganization.getParentId()));
            Map resultMap = Maps.newHashMap();
            resultMap.put("major", busTeacherInfo.getMajor());
            resultMap.put("teaDes", busTeacherInfo.getDesc());
            resultMap.put("department", deOrganization.getName());
            resultMap.put("college", coOrganization.getName());

            respInfo.setValue(resultMap);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg("获取教师信息成功");
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    @Override
    public void getCourseDetail(BusCourse busCourse, RespInfo respInfo) {
        try {
            busCourse = super.selectOne(new EntityWrapper<BusCourse>()
                    .eq("id", busCourse.getId()));
            int parentCount = busChapterService.selectCount(new EntityWrapper<BusChapter>()
                    .eq("parent_id", "")
                    .eq("course_id", busCourse.getId()));
            int childCount = busChapterService.selectCount(new EntityWrapper<BusChapter>()
                    .ne("parent_id", "")
                    .eq("course_id", busCourse.getId()));
            User user = userService.selectOne(new EntityWrapper<User>()
                    .eq("id", busCourse.getCreateBy()));
            List<BusChapter> busChapterList = busChapterService.selectList(new EntityWrapper<BusChapter>()
                    .setSqlSelect("id,classtime")
                    .ne("parent_id", "")
                    .eq("course_id", busCourse.getId()));
            int codeCount = 0;
            for (BusChapter busChapter : busChapterList) {
                codeCount += busChapter.getClasstime();
            }
            BusTeacherInfo busTeacherInfo = busTeacherInfoService.selectOne(
                    new EntityWrapper<BusTeacherInfo>().eq("user_id", busCourse.getCreateBy()));
            Dict dict = dictService.selectOne(new EntityWrapper<Dict>().eq("id",
                    busTeacherInfo.getRankId()));
            Map resultMap = Maps.newHashMap();
            resultMap.put("pic", busCourse.getPic());
            resultMap.put("title", busCourse.getTitle());
            resultMap.put("classtime", busCourse.getClasstime());
            resultMap.put("score", busCourse.getScore());
            resultMap.put("desc", busCourse.getDesc());
            resultMap.put("overview", busCourse.getOverview());
            resultMap.put("parentCount", parentCount);
            resultMap.put("childCount", childCount);
            resultMap.put("codeCount", codeCount);
            resultMap.put("courseTeacher", user.getName());
            resultMap.put("teacherRank", dict.getLabel());
            resultMap.put("teacherImage", user.getPhoto());
            resultMap.put("teacherId", user.getId());

            respInfo.setValue(resultMap);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg("获取课程信息成功");
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    @Override
    public void insertWithTemplate(HttpSession session,BusCourse busCourse, RespInfo respInfo) {
        try {
            User user = (User)session.getAttribute("token");

            //先执行插入操作
            busCourse.setTitle(Base64Util.urlDecode(busCourse.getTitle()));
            busCourse.setDesc(Base64Util.urlDecode(busCourse.getDesc()));
            busCourse.setOverview(Base64Util.urlDecode(busCourse.getOverview()));
            String msg = "";
            //判断是否是从模板插入
            if (StringUtils.isBlank(busCourse.getId())&&StringUtils.isNotBlank(busCourse.getTemplateCourseId())) {
                //插入
                busCourse.setCreateTime(new Date());
                baseMapper.insert(busCourse);
                //批量复制课程模版，插入数据库课程章节中间表
//                Integer num = baseMapper.insertCourse2Chapter(busCourse.getId(), busCourse.getTemplateCourseId());
                //批量复制章节信息
                List<BusChapter> chapters = busChapterService.selectList(new EntityWrapper<BusChapter>().eq("course_id",busCourse.getTemplateCourseId()));
                Map<String,String> map = new HashMap<>();
                //设置准备数据
                for(BusChapter busChapter:chapters){
                    busChapter.setOldId(busChapter.getId());
                    busChapter.setId(IdGen.uuid());
                    //旧Id做key，新Id做value，放入map中
                    map.put(busChapter.getOldId(),busChapter.getId());
                    busChapter.setCourseId(busCourse.getId());
                    busChapter.setCreateTime(new Date());
                    busChapter.setCreateBy(user.getId());
                }
                //将副章节parentId设置为新的parentId
                for(BusChapter busChapter:chapters){
                   busChapter.setParentId(map.get(busChapter.getParentId()));
                }
                //插入数据库表生成新Id
                busChapterService.insertBatch(chapters);

                msg = "插入课程成功";
            } else {
                //无模板更新
                super.insertOrUpdate(busCourse);
                msg = "修改课程成功";
            }
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg(msg);
        } catch (Exception ex) {
            respInfo.setMsg("新增或修改课程出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    @Override
    public void getAllCourse(HttpSession session, RespInfo respInfo) {
        try {
            User user = (User)session.getAttribute("token");
            if (user != null) {

                EntityWrapper ew = new EntityWrapper<BusCourse>();
                ew.setSqlSelect("id,title");
                if("1".equals(user.getRoleType())){
                        ew.eq("create_by",user.getId());
                }else if(!"2".equals(user.getRoleType())){
                    respInfo.setMsg("用户角色不存在");
                    respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                }
                List<BusCourse> busCourseList = busCourseService.selectList(ew);
                List list = Lists.newArrayList();
                for (BusCourse busCourse : busCourseList) {
                    Map map = Maps.newHashMap();
                    map.put("id", busCourse.getId());
                    map.put("title", busCourse.getTitle());
                    list.add(map);
                }
                respInfo.setMsg("获取课程信息成功");
                respInfo.setValue(list);
                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            } else {
                respInfo.setMsg("当前用户不存在");
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            }
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    /**
     * 根据value获取字典label
     */
    public String getDictLabelByValue(String value, List<Dict> dicts) {
        for (Dict dict : dicts) {
            if (value.equals(dict.getValue())) {
                return dict.getLabel();
            }
        }
        return "";
    }
}