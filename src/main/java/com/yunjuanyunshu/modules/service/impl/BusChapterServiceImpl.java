package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.mapper.BusChapterDao;
import com.yunjuanyunshu.modules.mapper.CourseChapterDao;
import com.yunjuanyunshu.modules.service.*;
import com.yunjuanyunshu.modules.util.Base64Util;
import com.yunjuanyunshu.modules.util.CacheUtils;
import com.yunjuanyunshu.modules.util.UserUtil;
import com.yunjuanyunshu.yunfd.common.util.StringUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
public class BusChapterServiceImpl extends ServiceImpl<BusChapterDao, BusChapter> implements BusChapterService {

    @Autowired
    private BusCourseService busCourseService;
    @Autowired
    private BusChapterRecordService busChapterRecordService;
    @Autowired
    private UserService userService;
    @Autowired
    private BusResourceService busResourceService;
    @Autowired
    private BusCodeService busCodeService;
    @Autowired
    private CourseChapterDao courseChapterDao;


    //需要改成事务
    @Override
    public void insertOrUpdateChapter(BusCourse busCourse, BusChapter busChapter, RespInfo respInfo) {
        try {
            // 修改章节
            //模板修改


            //正常修改
            //新增章节
            if (StringUtils.isNotBlank(busCourse.getId())) {
                //通过templateCourseId和chapterId 判断是否是公共模板章节
//                if (!busCourse.getId().equals(busChapter.getCourseId())) {
//                    CourseChapter courseChapter = new CourseChapter();
//                    courseChapter.setCourseId(busChapter.getCourseId());
//                    courseChapter.setChapterId(busChapter.getId());
//                    CourseChapter chapter = courseChapterDao.selectOne(courseChapter);
//                    //如果是，则删除原来的cc记录，
//                    if (chapter != null) {
//                        courseChapterDao.delete(new EntityWrapper<CourseChapter>().eq("course_id", busCourse.getId()).eq("chapter_id", busChapter.getId()));
//                        busChapter.setId(null);
//                    }
//                }


                busChapter.setCourseId(busCourse.getId());
                boolean isInsert = StringUtils.isBlank(busChapter.getId());
                //是插入一条chapter
                if (isInsert) {
                    busChapter.setCreateTime(new Date());
                    //判断是父章节还是子章节,对子章节
                    if (StringUtils.isNotBlank(busChapter.getParentId())) {
                        //是子章节插入
                        BusChapter pBusChapter = super.selectOne(new EntityWrapper<BusChapter>()
                                .setSqlSelect("id,classtime")
                                .eq("id", busChapter.getParentId()));
                        //父章节课时加1
                        pBusChapter.setClasstime(pBusChapter.getClasstime() + 1);
                        super.insertOrUpdate(pBusChapter);
                    }
                }
                busChapter.setTitle(Base64Util.urlDecode(busChapter.getTitle()));
                busChapter.setDes(Base64Util.urlDecode(busChapter.getDes()));
                super.insertOrUpdate(busChapter);
//                if (isInsert) {
//                    CourseChapter cc = new CourseChapter();
//                    cc.setChapterId(busChapter.getId());
//                    cc.setCourseId(busCourse.getId());
//                    courseChapterDao.insert(cc);
//                }
                respInfo.setMsg("操作章节信息成功");
                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            } else {
                respInfo.setError("课程ID为空");
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            respInfo.setValue(ex);
            respInfo.setMsg("插入或更新章节信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    @Override
    public void deleteChapter(BusChapter busChapter, RespInfo respInfo) {
        try {
            busChapter = super.selectOne(new EntityWrapper<BusChapter>()
                    .setSqlSelect("id,parent_id AS parentId")
                    .eq("id", busChapter.getId()));
            //父章节
            if ("".equals(busChapter.getParentId())) {
                //查询是否有子章节
                int count = super.selectCount(new EntityWrapper<BusChapter>()
                        .eq("parent_id", busChapter.getId()));
                if (count > 0) {
                    respInfo.setMsg("无法删除含有子章节的章节");
                    respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                } else {
                    super.deleteById(busChapter);
                    respInfo.setMsg("删除章节信息成功");
                    respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                }

            } else { //子章节
                int count = busCodeService.selectCount(new EntityWrapper<BusCode>()
                        .eq("chapter_id", busChapter.getId()));
                if (count > 0) {
                    respInfo.setMsg("无法删除含有任务代码的子章节");
                    respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
                } else {
                    busChapter = super.selectOne(new EntityWrapper<BusChapter>()
                            .setSqlSelect("id,parent_id AS parentId")
                            .eq("id", busChapter.getId()));
                    BusChapter pBusChapter = super.selectOne(new EntityWrapper<BusChapter>()
                            .setSqlSelect("id,classtime")
                            .eq("id", busChapter.getParentId()));
                    pBusChapter.setClasstime(pBusChapter.getClasstime() - 1);
                    super.insertOrUpdate(pBusChapter);
                    super.deleteById(busChapter);
                    //父章节的课时-1
                    respInfo.setMsg("删除章节信息成功");
                    respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            respInfo.setValue(ex);
            respInfo.setMsg("删除章节信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    @Override
    public void getBusChapter(BusChapter busChapter, RespInfo respInfo) {
        try {
            busChapter = super.selectById(busChapter);
            if (StringUtils.isNotBlank(busChapter.getResourceId())) {
                BusResource busResource = busResourceService.selectById(busChapter.getResourceId());
                busChapter.setResourceUrl(busResource.getUrl());
            }
            respInfo.setMsg("获取章节详情成功");
            respInfo.setValue(busChapter);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取章节详情出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    @Override
    public void getBusChapterWithCourseClassify(BusChapter busChapter, RespInfo respInfo) {
        try {
            busChapter = super.selectById(busChapter);
            if (StringUtils.isNotBlank(busChapter.getResourceId())) {
                BusResource busResource = busResourceService.selectById(busChapter.getResourceId());
                busChapter.setResourceUrl(busResource.getUrl());
            }
            BusCourse busCourse = busCourseService.selectOne(new EntityWrapper<BusCourse>()
                    .setSqlSelect("classify")
                    .eq("id", busChapter.getCourseId()));
            Map<String, Object> resultMap = Maps.newHashMap();
            resultMap.put("busChapter", busChapter);
            resultMap.put("busCourse", busCourse);
            respInfo.setMsg("获取章节详情成功");
            respInfo.setValue(resultMap);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取章节详情出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    /**
     * 获取该章节对应的课程分类
     *
     * @param busChapter
     * @param respInfo
     */
    @Override
    public void getClassifyByChapter(BusChapter busChapter, RespInfo respInfo) {
        try {
            BusCourse busCourse = busCourseService.selectOne(new EntityWrapper<BusCourse>()
                    .setSqlSelect("classify")
                    .eq("id", busChapter.getCourseId()));
            respInfo.setMsg("获取课程分类成功");
            respInfo.setValue(busCourse);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取课程分类出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    @Override
    public void getChaptersWithStatus(BusCourse busCourse, User user, RespInfo respInfo) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            User user1 = (User) UserUtil.getUser(user.getToken(), respInfo);
            List<BusChapter> busChapters = super.selectList(new EntityWrapper<BusChapter>().eq("course_id", busCourse.getId()));
            for (BusChapter busChapter : busChapters) {
                BusChapterRecord bcRecord = busChapterRecordService.selectOne(new EntityWrapper<BusChapterRecord>().eq("user_id", user1.getId()).eq("chapter_id", busChapter.getId()));
                BusResource bsResource = busResourceService.selectById(busChapter.getResourceId() == null ? "" : busChapter.getResourceId());
                busChapter.setResourceUrl(bsResource == null ? "/" : bsResource.getUrl());
                if (bcRecord != null) {
                    busChapter.setHasFinished(1);
                    busChapter.setFinishDate(bcRecord.getFinishTime() != null ? sdf.format(bcRecord.getFinishTime()) : "");
                }
            }
            respInfo.setMsg("获取章节信息成功");
            respInfo.setValue(busChapters);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取章节信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }

    }

    @Override
    public void getChaptersWithStatusByPage(Page<BusChapter> page, BusCourse busCourse, User user, RespInfo respInfo) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            User user1 = (User) UserUtil.getUser(user.getToken(), respInfo);
            Page<BusChapter> busChapters = super.selectPage(page, new EntityWrapper<BusChapter>().eq("course_id", busCourse.getId()));
            for (BusChapter busChapter : busChapters.getRecords()) {
                BusChapterRecord bcRecord = busChapterRecordService.selectOne(new EntityWrapper<BusChapterRecord>().eq("user_id", user1.getId()).eq("chapter_id", busChapter.getId()));
                if (bcRecord != null) {
                    busChapter.setHasFinished(1);
                    busChapter.setFinishDate(bcRecord.getFinishTime() != null ? sdf.format(bcRecord.getFinishTime()) : "");
                }
            }
            respInfo.setMsg("获取章节信息成功");
            respInfo.setValue(busChapters);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取章节信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }

    }

    @Override
    public void getChaptersByCourseId(BusCourse busCourse, RespInfo respInfo) {
        try {
//            List<BusChapter> busChapterList = baseMapper.getChaptersByCourseIdAndParent(busCourse.getId(),"");
            List<BusChapter> busChapterList = super.selectList(
                new EntityWrapper<BusChapter>()
                    .setSqlSelect("id,title")
                    .eq("course_id", busCourse.getId())
                    .eq("parent_id", "")
                    .orderBy("sort", true));
            List resultList = Lists.newArrayList();
            for (BusChapter busChapter : busChapterList) {
                Map map = Maps.newHashMap();
                map.put("id", busChapter.getId());
                map.put("title", busChapter.getTitle());
                resultList.add(map);
            }
            respInfo.setMsg("获取章节信息成功");
            respInfo.setValue(resultList);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取章节信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    /**
     * 后台获取章节列表用
     *
     *
     * @param busCourse
     * @param respInfo
     */
    @Override
    public void getTreeChapterList(BusCourse busCourse, RespInfo respInfo) {
        try {
            // 获取所有章节信息
            List<BusChapter> busChapterAllList = super.selectList(
                    new EntityWrapper<BusChapter>()
                            .setSqlSelect("id,parent_id AS parentId,title,course_id AS courseId,sort,classtime,score,type")
                            .eq("course_id", busCourse.getId())
                            .orderBy("sort", true));
            //获取一级章节
            List<BusChapter> busChapterList = getChaptersLevelOne(busChapterAllList);
            respInfo.setMsg("获取章节信息成功");
            respInfo.setValue(treeChaptersChildData(busChapterAllList, busChapterList));
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取章节信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    /**
     * 获取子章节信息
     *
     * @param allBusChapterList
     * @param pid
     */
    private List getChaptersChild(List<BusChapter> allBusChapterList, String pid) {
        List resultList = Lists.newArrayList();
        for (BusChapter busChapter : allBusChapterList) {
            if (pid.equals(busChapter.getParentId())) {
                resultList.add(busChapter);
            }
        }
        return resultList;
    }

    /**
     * 获取一级章节信息
     *
     * @param allBusChapterList
     */
    private List getChaptersLevelOne(List<BusChapter> allBusChapterList) {
        List resultList = Lists.newArrayList();
        for (BusChapter busChapter : allBusChapterList) {
            if (StringUtils.isBlank(busChapter.getParentId())) {
                resultList.add(busChapter);
            }
        }
        return resultList;
    }

    private List treeChaptersChildData(List<BusChapter> busChapterAllList, List<BusChapter> busChapterList) {
        List resultList = Lists.newArrayList();
        for (BusChapter busChapter : busChapterList) {
            List<BusChapter> busChapters = getChaptersChild(busChapterAllList, busChapter.getId());
            Map map = Maps.newHashMap();
            if (busChapters.size() > 0) {
                map.put("children", treeChaptersChildData(busChapterAllList, busChapters));
            }
            map.put("id", busChapter.getId());
            map.put("title", busChapter.getTitle());
            map.put("classtime", busChapter.getClasstime());
            map.put("score", busChapter.getScore());
            map.put("type", busChapter.getType());
            map.put("sort", busChapter.getSort());
            map.put("courseId", busChapter.getCourseId());
            map.put("parentId", busChapter.getParentId());
            resultList.add(map);
        }
        return resultList;
    }

    /**
     * 前端获取章节列表
     * 已修改为从中间表获取
     *
     * @param busCourse
     * @param user
     * @param respInfo
     */
    @Override
    public void getChapterListToFront(BusCourse busCourse, User user, RespInfo respInfo) {
        try {
            // 获取所有章节信息
            user = UserUtil.getUser(user.getToken(), respInfo);
            //通过中间表获取所有章节信息
            List<BusChapter> busChapterAllList = super.selectList(
                    new EntityWrapper<BusChapter>()
                            .setSqlSelect("id,parent_id AS parentId,title,course_id AS courseId,sort,classtime,score,type")
                            .eq("course_id", busCourse.getId())
                            .orderBy("sort", true));

            List<BusChapterRecord> busChapterRecords = busChapterRecordService.selectList(
                    new EntityWrapper<BusChapterRecord>()
                            .setSqlSelect("id,chapter_id as chapterId,status")
                            .eq("create_by", user.getId())
                            .eq("course_id", busCourse.getId())
            );
            HashMap<String, BusChapterRecord> recordMap = Maps.newHashMap();
            for (BusChapterRecord busChapter : busChapterRecords) {
                recordMap.put(busChapter.getChapterId(), busChapter);
            }
            //获取一级章节
            Map<String, List<BusChapter>> busChapterList = getChaptersLevel2(busChapterAllList, recordMap);
            respInfo.setMsg("获取章节信息成功");
            respInfo.setValue(busChapterList);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            ex.printStackTrace();
            respInfo.setValue(ex);
            respInfo.setMsg("获取章节信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    /**
     * 章节信息数据封装
     *
     * @param allBusChapterList 所有父子级章节列表
     * @param recordMap         章节记录
     * @return
     */
    private Map<String, List<BusChapter>> getChaptersLevel2(List<BusChapter> allBusChapterList, HashMap<String, BusChapterRecord> recordMap) {

        Map<String, List<BusChapter>> result = Maps.newLinkedHashMap();

        //父级章节
        for (BusChapter parent : allBusChapterList) {
            if (StringUtils.isBlank(parent.getParentId())) {
                List parentAndChild = Lists.newArrayList();
                parent.setScore(0);
                parentAndChild.add(parent);
                result.put(parent.getId(), parentAndChild);
            }
        }
        //子级章节
        for (BusChapter child : allBusChapterList) {
            if (StringUtils.isNotBlank(child.getParentId())) {
                List<BusChapter> parentAndChild = result.get(child.getParentId());
                BusChapterRecord chapterRecord = recordMap.get(child.getId());
                if (chapterRecord != null) {
                    child.setScore(chapterRecord.getStatus());
                    if (chapterRecord.getStatus().equals(child.getClasstime())) {
                        parentAndChild.get(0).setScore(parentAndChild.get(0).getScore() + 1);
                    }
                } else {
                    child.setScore(0);
                }
                parentAndChild.add(child);
            }
        }
        return result;
    }

    /**
     * 章节信息数据封装
     *
     * @param allBusChapterList 所有父子级章节列表
     * @param recordMap         章节记录
     * @return
     */
    private Map<String, List<BusChapter>> getChaptersLevel(List<BusChapter> allBusChapterList, HashMap<String, BusChapterRecord> recordMap) {
        Map<String, List<BusChapter>> chapterMap = Maps.newLinkedHashMap();
        for (BusChapter busChapter : allBusChapterList) {
            if (StringUtils.isBlank(busChapter.getParentId())) {
                List resultList = Lists.newArrayList();
                busChapter.setScore(0);
                resultList.add(busChapter);
                chapterMap.put(busChapter.getId(), resultList);
            } else {
                //当子章节在父章节前出现时要报空指针错误，所以必须注意子章节sort要大于父章节
                List<BusChapter> list = chapterMap.get(busChapter.getParentId());
                BusChapterRecord busChapterRecord = recordMap.get(busChapter.getId());
                if (busChapterRecord != null) {
                    busChapter.setScore(busChapterRecord.getStatus());
                    if (busChapterRecord.getStatus().equals(busChapter.getClasstime())) {
                        //子章节学习完成，父章节学习完成数+1
                        list.get(0).setScore(list.get(0).getScore() + 1);
                    }
                } else {
                    busChapter.setScore(0);
                }
                list.add(busChapter);

            }
        }

        return chapterMap;
    }

    @Override
    public void getTreeChapterListWithStatus(BusCourse busCourse, User user, RespInfo respInfo) {
        try {
            //获取一级章节
            List<BusChapter> busChapterList = super.selectList(new EntityWrapper<BusChapter>().eq("course_id", busCourse.getId()).eq("parent_id", "").orderBy("sort", true));
            respInfo.setMsg("获取章节信息成功");
            respInfo.setValue(getTreeChaptersChildWithStatus(busChapterList, user));
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取章节信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    /**
     * now为现在的章节级别,deep为要查到的章节级别
     *
     * @param busChapterList
     * @return
     */
    private List getTreeChaptersChildToFront(List<BusChapter> busChapterList, int now, int deep) {
        List resultList = Lists.newArrayList();
        for (BusChapter busChapter : busChapterList) {
            List<BusChapter> busChapters = super.selectList(new EntityWrapper<BusChapter>().eq("parent_id", busChapter.getId()).orderBy("sort", true));
            Map map = Maps.newHashMap();
            if (busChapters.size() > 0 && now < deep) {
                now++;
                map.put("children", getTreeChaptersChildToFront(busChapters, now, deep));
                now--;
            }
            map.put("id", busChapter.getId());
            map.put("title", busChapter.getTitle());
            resultList.add(map);
        }
        return resultList;
    }

    private List getTreeChaptersChildWithStatus(List<BusChapter> busChapterList, User user) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        User user1 = (User) CacheUtils.get(user.getToken());
        List resultList = Lists.newArrayList();
        for (BusChapter busChapter : busChapterList) {
            List<BusChapter> busChapters = super.selectList(new EntityWrapper<BusChapter>().eq("parent_id", busChapter.getId()).orderBy("sort", true));
            Map map = Maps.newHashMap();
            if (busChapters.size() > 0) {
                map.put("children", getTreeChaptersChildWithStatus(busChapters, user));
            }
            BusChapterRecord bcRecord = busChapterRecordService.selectOne(new EntityWrapper<BusChapterRecord>().eq("user_id", user1.getId()).eq("chapter_id", busChapter.getId()));
            BusResource bsResource = busResourceService.selectById(busChapter.getResourceId() == null ? "" : busChapter.getResourceId());
            busChapter.setResourceUrl(bsResource == null ? "/" : bsResource.getUrl());
            if (bcRecord != null) {
                busChapter.setHasFinished(1);
                busChapter.setFinishDate(bcRecord.getFinishTime() != null ? sdf.format(bcRecord.getFinishTime()) : "");
            }
            map.put("id", busChapter.getId());
            map.put("title", busChapter.getTitle());
            map.put("des", busChapter.getDes());
            map.put("type", busChapter.getType());
            map.put("resourceUrl", busChapter.getResourceUrl());
            map.put("hasFinished", busChapter.getHasFinished());
            resultList.add(map);

        }
        return resultList;
    }
}