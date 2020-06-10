package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.entity.*;
import com.yunjuanyunshu.modules.mapper.BusCodeRecordDao;
import com.yunjuanyunshu.modules.service.*;
import com.yunjuanyunshu.modules.util.Base64Util;
import com.yunjuanyunshu.modules.util.FileUtil;
import com.yunjuanyunshu.modules.util.GetServerRealPathUtil;
import com.yunjuanyunshu.modules.util.ShellUtil;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author xjz
 * @since 2017-07-18
 */
@Service
public class BusCodeRecordServiceImpl extends ServiceImpl<BusCodeRecordDao, BusCodeRecord> implements BusCodeRecordService {
    @Autowired
    private UserService userService;
    @Autowired
    private BusChapterRecordService busChapterRecordService;
    @Autowired
    private BusCodeService busCodeService;
    @Autowired
    private BusChapterService busChapterService;
    @Autowired
    private BusScratchFileService busScratchFileService;

    @Override
    public void execCplus(HttpServletRequest request, BusCodeRecord busCodeRecord, String inputValue,RespInfo respInfo) {
        execCode(request, busCodeRecord, inputValue, respInfo, "c");
    }

    @Override
    public void execJava(HttpServletRequest request, BusCodeRecord busCodeRecord, String inputValue, RespInfo respInfo) {
        execCode(request, busCodeRecord, inputValue, respInfo, "java");
    }

    @Override
    public void execPy(HttpServletRequest request, BusCodeRecord busCodeRecord, String inputValue, RespInfo respInfo) {
        execCode(request, busCodeRecord, inputValue, respInfo, "py");
    }

    /**
     * 执行html代码
     * @param request
     * @param busCodeRecord
     * @param respInfo
     */
    @Override
    public void execHtml(HttpServletRequest request, BusCodeRecord busCodeRecord, RespInfo respInfo) {
        try{

            String code = Base64Util.urlDecode(busCodeRecord.getContent());
            String language = "html";
            String filename = "index.html";
            String filePath = "";
            filePath = request.getSession().getServletContext().getRealPath("/") + "code/"
                    + busCodeRecord.getUserId() + "/" + language + "/" + filename;

            String urlPath = request.getContextPath() + "/" + "code/"
                    + busCodeRecord.getUserId() + "/" + language + "/" + filename;
            FileUtil.mkFile(filePath, code);

            //检查是否已有该记录
            BusCodeRecord busCodeRecord1 = super.selectOne(new EntityWrapper<BusCodeRecord>().eq("chapter_id", busCodeRecord.getChapterId())
                    .eq("user_id", busCodeRecord.getUserId())
                    .eq("code_id", busCodeRecord.getCodeId()));

            if(busCodeRecord1!=null){
                busCodeRecord1.setContent(busCodeRecord.getContent());
                busCodeRecord=busCodeRecord1;
                busCodeRecord.setUpdateDate(new Date());
                busCodeRecord.setResult("");
            }else {
                busCodeRecord.setUpdateDate(new Date());
                busCodeRecord.setResult("");
                //将用户的输出结果存库
                busCodeRecord.setCreateDate(new Date());
                busCodeRecord.setCreateBy(busCodeRecord.getUserId());
                //对应的章节并且status+1
                BusChapter busChapter = busChapterService.selectOne(new EntityWrapper<BusChapter>()
                        .setSqlSelect("id,course_id AS courseId")
                        .eq("id",busCodeRecord.getChapterId()));
                //查找用户与子章节的记录
                BusChapterRecord bcRcord = busChapterRecordService.selectOne(new EntityWrapper<BusChapterRecord>()
                        .eq("chapter_id", busCodeRecord.getChapterId())
                        .eq("user_id", busCodeRecord.getUserId()));
                BusChapterRecord busChapterRecord = new BusChapterRecord();
                //通过用户id和章节id查找章节记录
                if(bcRcord == null){
                    busChapterRecord.setChapterId(busChapter.getId());
                    busChapterRecord.setStatus(1);
                    busChapterRecord.setCourseId(busChapter.getCourseId());
                    busChapterRecord.setUserId(busCodeRecord.getUserId());
                    busChapterRecord.setCreateTime(new Date());
                    busChapterRecord.setCreateBy(busCodeRecord.getUserId());
                }else{
                    busChapterRecord = bcRcord;
                    busChapterRecord.setStatus(busChapterRecord.getStatus()+1);
                }
                busChapterRecordService.insertOrUpdate(busChapterRecord);
            }
            super.insertOrUpdate(busCodeRecord);
            respInfo.setCode(1);
            respInfo.setMsg("完成任务");
            respInfo.setValue(urlPath);

        } catch (Exception ex) {
            respInfo.setCode(-1);
            respInfo.setMsg("失败");
            respInfo.setValue(ex.getMessage());
        }
    }

    /**
     * Exec code.
     *
     * @param request       the request
     * @param busCodeRecord the bus code record
     * @param respInfo      the resp info
     * @param language      the language
     */
    private void execCode(HttpServletRequest request, BusCodeRecord busCodeRecord, String inputValue, RespInfo respInfo, String language) {
        try {
//            User user = userService.selectById(busCodeRecord.getUserId());
            //运行代码
            String code = Base64Util.urlDecode(busCodeRecord.getContent());
            //获取当前系统时间戳，用来命名
            String time = "" + System.currentTimeMillis();
            //设置当前路径，执行编译命令的时候用到
            String fp = request.getSession().getServletContext().getRealPath("/") + "code/" + busCodeRecord.getUserId() + "/" + language + "/";
            String makefile = "";
            String filename = "";
            String filePath = "";
            String fileInputPath = request.getSession().getServletContext().getRealPath("/") + "code/"
                    + busCodeRecord.getUserId() + "/" + language + "/" + "input.txt";
            if ("py".equals(language)) {
                makefile = "makepy.sh";
                filename = "HelloWorld.py";
                filePath = request.getSession().getServletContext().getRealPath("/") + "code/"
                        + busCodeRecord.getUserId() + "/" + language + "/" + filename;
            } else if ("java".equals(language)) {
                makefile = "makejava.sh";
                filename = "HelloWorld";
                //code = "class " + "zhangjie01 " + "{" + code + "}";
                filePath = request.getSession().getServletContext().getRealPath("/") + "code/"
                        + busCodeRecord.getUserId() + "/" + language + "/" + filename + ".java";
            } else if ("c".equals(language)) {
                makefile = "makecplus.sh";
                filename = busCodeRecord.getUserId() + "-" + time;
                filePath = request.getSession().getServletContext().getRealPath("/") + "code/"
                        + busCodeRecord.getUserId() + "/c/" + time + ".cpp";
            }
            String shell = request.getSession().getServletContext().getRealPath("/") + "code/shell/" + makefile;
            String killshell = request.getSession().getServletContext().getRealPath("/") + "code/shell/killprocess.sh";
            inputValue=inputValue.replaceAll("\\\\n","\n");
//            System.out.println(inputValue);
            FileUtil.mkFile(fileInputPath, inputValue);
            FileUtil.mkFile(filePath, code);
            //执行编译命令／此处文件名应设置为章节名
            String[] order = {shell, fp, filePath, filename, fileInputPath};
            List<String> sts = ShellUtil.execTimeout(order,killshell);
            //用来记录到code_record里面去
            String temp = "";
            for (int i = 0; i < sts.size(); i++) {
                sts.set(i, sts.get(i).replace(GetServerRealPathUtil.getRootPath(),""));
                temp += sts.get(i);
            }

            //todo 结果判断

            //现在假设只要运行一遍结果都是正确的,都需要有coderecord记录
            //查询这个章节代码有没有正确执行过
            BusCodeRecord busCodeRecord1 = super.selectOne(new EntityWrapper<BusCodeRecord>().eq("chapter_id", busCodeRecord.getChapterId())
                    .eq("user_id", busCodeRecord.getUserId())
                    .eq("code_id", busCodeRecord.getCodeId()));

            if(busCodeRecord1!=null){
                busCodeRecord1.setContent(busCodeRecord.getContent());
                busCodeRecord=busCodeRecord1;
                busCodeRecord.setUpdateDate(new Date());
                busCodeRecord.setResult(temp);
            }else {
                busCodeRecord.setUpdateDate(new Date());
                busCodeRecord.setResult(temp);
                //将用户的输出结果存库
                busCodeRecord.setCreateDate(new Date());
                busCodeRecord.setCreateBy(busCodeRecord.getUserId());
                //对应的章节并且status+1
                BusChapter busChapter = busChapterService.selectOne(new EntityWrapper<BusChapter>()
                        .setSqlSelect("id,course_id AS courseId,classtime")
                        .eq("id",busCodeRecord.getChapterId()));
                //查找用户与子章节的记录
                BusChapterRecord bcRcord = busChapterRecordService.selectOne(new EntityWrapper<BusChapterRecord>()
                        .eq("chapter_id", busCodeRecord.getChapterId())
                        .eq("user_id", busCodeRecord.getUserId()));
                BusChapterRecord busChapterRecord = new BusChapterRecord();
                //通过用户id和章节id查找章节记录
                if(bcRcord==null){
                    busChapterRecord.setStatus(1);
                    busChapterRecord.setChapterId(busChapter.getId());
                    busChapterRecord.setCourseId(busChapter.getCourseId());
                    busChapterRecord.setUserId(busCodeRecord.getUserId());
                    busChapterRecord.setCreateTime(new Date());
                    busChapterRecord.setCreateBy(busCodeRecord.getUserId());
                }else{
                    busChapterRecord = bcRcord;
                    busChapterRecord.setStatus(busChapterRecord.getStatus()+1);
                }
                //判断是否该用户是否的子章节代码任务是否都完成了,如果完成了就设置finishTime
                if (busChapterRecord.getStatus()/busChapter.getClasstime() == 1) {
                    busChapterRecord.setFinishTime(new Date());
                }
                busChapterRecordService.insertOrUpdate(busChapterRecord);
            }
            super.insertOrUpdate(busCodeRecord);
            respInfo.setCode(1);
            respInfo.setMsg("完成任务");
            respInfo.setValue(sts);


        } catch (Exception ex) {
            respInfo.setCode(-1);
            respInfo.setMsg("失败");
            respInfo.setValue(ex.toString());
        }
    }

    /**
     * 查询用户在该章节的代码任务是否有代码记录
     * @param busCodeRecord
     * @param respInfo
     */
    @Override
    public void getRecordByCIdAndUId(BusCodeRecord busCodeRecord,RespInfo respInfo){
        try{
            BusCodeRecord isExistRecord = super.selectOne(new EntityWrapper<BusCodeRecord>().eq("chapter_id", busCodeRecord.getChapterId())
                    .eq("user_id", busCodeRecord.getUserId())
                    .eq("code_id", busCodeRecord.getCodeId()));
            if (isExistRecord!=null){
                respInfo.setCode(0);
                respInfo.setMsg("查询记录成功");
                isExistRecord.setContent(Base64Util.urlDecode(isExistRecord.getContent()));
                respInfo.setValue(isExistRecord);
            } else {
                respInfo.setCode(1);
                respInfo.setMsg("查询记录失败");
            }
        } catch (Exception ex){
            respInfo.setCode(-1);
            respInfo.setMsg("失败");
            respInfo.setValue(ex.getMessage());
        }
    }
 /**
     * 查询用户在该章节的代码任务是否有代码记录
     * @param busCodeRecord
     * @param respInfo
     */
    @Override
    public void getRecordByCIdAndUIdForScratch(BusCodeRecord busCodeRecord,RespInfo respInfo){
        try{
            BusCodeRecord isExistRecord = super.selectOne(new EntityWrapper<BusCodeRecord>().eq("chapter_id", busCodeRecord.getChapterId())
                    .eq("user_id", busCodeRecord.getUserId())
                    .eq("code_id", busCodeRecord.getCodeId()));
            if (isExistRecord!=null){
                respInfo.setCode(0);
                respInfo.setMsg("查询记录成功");
                respInfo.setValue(isExistRecord);
            } else {
                respInfo.setCode(1);
                respInfo.setMsg("查询记录失败");
            }
        } catch (Exception ex){
            respInfo.setCode(-1);
            respInfo.setMsg("失败");
            respInfo.setValue(ex.getMessage());
        }
    }

    @Override
    public void insertScratch(BusScratchFile busScratchFile, BusCodeRecord busCodeRecord, RespInfo respInfo) {

        try{
            //此接口中busCodeRecord的id应该为null,此接口只用作用户第一次为当前任务创建记录，
            // 如果已经生成了记录，那么busScratchFile文件记录也应该生成，应该只更新busScratchFile就可以
            if(StringUtils.isEmpty(busCodeRecord.getId())){


                //避免出现多条记录，逻辑正确的话无意义，测试后可删除
            BusCodeRecord isExistRecord = super.selectOne(new EntityWrapper<BusCodeRecord>().eq("chapter_id", busCodeRecord.getChapterId())
                    .eq("user_id", busCodeRecord.getUserId())
                    .eq("code_id", busCodeRecord.getCodeId()));
            if (isExistRecord!=null){
                respInfo.setCode(-1);
                respInfo.setMsg("逻辑出错");
                return;
            }

            busScratchFile.setCreateDate(new Date());
            busScratchFile.setContent(Base64Utils.encodeToString(busScratchFile.getContent().getBytes()));
            busScratchFileService.insert(busScratchFile);
            BusCode busCode = busCodeService.selectOne(new EntityWrapper<BusCode>().setSqlSelect("chapter_id AS chapterId,id").
                    eq("id",busCodeRecord.getCodeId()));
            busCodeRecord.setChapterId(busCode.getChapterId());
            busCodeRecord.setContent(busScratchFile.getId());
            busCodeRecord.setCreateDate(new Date());
            super.insert(busCodeRecord);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            respInfo.setMsg(ServiceErrorCodeEnum.SUCCESS.getErrorStr());
            respInfo.setValue(busScratchFile.getId());
            }

        }catch (Exception ex){
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setMsg(ex.getMessage());
        }

    }
}