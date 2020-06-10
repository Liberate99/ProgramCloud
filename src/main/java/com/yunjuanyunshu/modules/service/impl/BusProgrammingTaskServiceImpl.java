package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.common.collect.Maps;
import com.yunjuanyunshu.modules.common.ResponseCode;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.mapper.BusProgrammingTaskDao;
import com.yunjuanyunshu.modules.service.*;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author carl
 * @since 2018-02-25
 */
@Service
public class BusProgrammingTaskServiceImpl extends ServiceImpl<BusProgrammingTaskDao, BusProgrammingTask> implements BusProgrammingTaskService {

    @Autowired
    private BusProgrammingTaskService busProgrammingTaskService;

    @Autowired
    private BusChapterService busChapterService;

    @Autowired
    private BusChapterRecordService busChapterRecordService;

    @Autowired
    private OjProblemService ojProblemService;

    @Autowired
    private OjSubmissionService ojSubmissionService;

    @Autowired
    private BusTaskRecordService busTaskRecordService;

    @Override
    public boolean insertOrUpdateProgrammingTask(HttpSession session, BusProgrammingTask busProgrammingTask) {
        User user = (User)session.getAttribute("token");
        if (busProgrammingTask.getId() != null) {
            busProgrammingTask.setUpdateBy(user.getId());
            busProgrammingTask.setUpdateTime(new Date());
            return busProgrammingTaskService.insertOrUpdate(busProgrammingTask);
        } else {
            busProgrammingTask.setCreateBy(user.getId());
            busProgrammingTask.setCreateTime(new Date());
            busProgrammingTask.setUpdateBy(user.getId());
            busProgrammingTask.setUpdateTime(new Date());
            BusChapter busChapter =
                    busChapterService.selectOne(new EntityWrapper<BusChapter>().setSqlSelect("id, classtime")
                            .eq("id", busProgrammingTask.getChapterId()));
            busChapter.setClasstime(busChapter.getClasstime() + 1);
            busChapterService.updateById(busChapter);
            return busProgrammingTaskService.insertOrUpdate(busProgrammingTask);
        }
    }

    @Override
    public void getBusProgrammingTaskListByChapterId(String chapterId, RespInfo respInfo) {
        List<BusProgrammingTask> busProgrammingTasks = super.selectList(new EntityWrapper<BusProgrammingTask>()
                .setSqlSelect("id,problem_id,name,sort").eq("chapter_id", chapterId).eq("del_flag", 0)
                .orderBy("sort", true));
        respInfo.setValue(busProgrammingTasks);
        respInfo.setMsg("获取代码任务列表成功");
        respInfo.setCode(ResponseCode.SUCCESS.getCode());
    }

    @Override
    public void getBusProgrammingTaskWithProblem(BusProgrammingTask busProgrammingTask, RespInfo respInfo) {
        busProgrammingTask = super.selectById(busProgrammingTask);
        if (busProgrammingTask == null) {
            respInfo.setCode(ResponseCode.ERROR.getCode());
            respInfo.setMsg("代码任务不存在");
            return;
        }

        OjProblem ojProblem = new OjProblem();
        ojProblem.setId(busProgrammingTask.getProblemId());
        ojProblem = ojProblemService.selectById(ojProblem);

        Map<String, Object> resultMap = Maps.newHashMap();

        resultMap.put("busProgrammingTask",busProgrammingTask);
        resultMap.put("ojProblem",ojProblem);

        respInfo.setMsg("获取代码任务试题内容成功");
        respInfo.setCode(ResponseCode.SUCCESS.getCode());
        respInfo.setValue(resultMap);
    }

    @Override
    public void deleteProgrammingTask(HttpSession session, BusProgrammingTask busProgrammingTask) {
        User user = (User)session.getAttribute("token");
        busProgrammingTask = busProgrammingTaskService.selectById(busProgrammingTask);
        busProgrammingTask.setUpdateTime(new Date());
        busProgrammingTask.setUpdateBy(user.getId());
        busProgrammingTask.setDelFlag(1);
        BusChapter busChapter =
                busChapterService.selectOne(new EntityWrapper<BusChapter>().setSqlSelect("id, classtime")
                        .eq("id", busProgrammingTask.getChapterId()));
        busChapter.setClasstime(busChapter.getClasstime() - 1);
        busChapterService.updateById(busChapter);
        super.updateById(busProgrammingTask);
    }

    /**
     * 当编程任务AC时执行此函数,更新编程任务完成信息
     * @param user
     * @param busProgrammingTask
     * @param busChapter
     * @param respInfo
     */
    @Override
    public void hasFinishProgrammingTask(User user, BusProgrammingTask busProgrammingTask, BusChapter busChapter, RespInfo respInfo) {
        try{
            BusChapterRecord busChapterRecord = busChapterRecordService.selectOne(new EntityWrapper<BusChapterRecord>()
                    .eq("chapter_id",busChapter.getId()).eq("user_id", user.getId()));

            //如果用户的章节记录没有,初始化它
            if (busChapterRecord == null) {
                busChapterRecord = new BusChapterRecord();
                busChapterRecord.setChapterId(busChapter.getId());
                busChapterRecord.setCourseId(busChapter.getCourseId());
                busChapterRecord.setUserId(user.getId());
                busChapterRecord.setCreateBy(user.getId());
                busChapterRecord.setCreateTime(new Date());
                busChapterRecord.setStatus(0);
            }

            BusTaskRecord busTaskRecord = busTaskRecordService.selectOne(new EntityWrapper<BusTaskRecord>()
                    .eq("task_id", busProgrammingTask.getId()).eq("user_id", user.getId()));

            if (busTaskRecord == null) {
                busTaskRecord = new BusTaskRecord();
                busTaskRecord.setTaskId(busProgrammingTask.getId());
                busTaskRecord.setUserId(user.getId());
                busTaskRecord.setFinishTime(new Date());
                busTaskRecord.setHasFinish(1);
                busTaskRecordService.insert(busTaskRecord);
            }

            //查找这个章节下的所有编程任务id
            List<BusProgrammingTask> busProgrammingTasks = busProgrammingTaskService.selectList(
                    new EntityWrapper<BusProgrammingTask>()
                            .setSqlSelect("id")
                            .eq("chapter_id", busChapter.getId()).eq("del_flag", 0));
            //累计完成章节的数目
            int total = 0;
            for (BusProgrammingTask brt : busProgrammingTasks) {
                int count = busTaskRecordService.selectCount(new EntityWrapper<BusTaskRecord>().eq("task_id", brt.getId()));
                if (count > 0) {
                    ++total;
                }
            }

            busChapterRecord.setStatus(total);
            busChapterRecordService.insertOrUpdate(busChapterRecord);
        } catch (Exception ex) {
            respInfo.setError(ex.getMessage());
        }



    }
}