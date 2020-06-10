package com.yunjuanyunshu.modules.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.yunjuanyunshu.modules.entity.BusChapter;
import com.yunjuanyunshu.modules.entity.BusCode;
import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.modules.mapper.BusCodeDao;
import com.yunjuanyunshu.modules.service.BusChapterService;
import com.yunjuanyunshu.modules.service.BusCodeService;
import com.yunjuanyunshu.modules.util.Base64Util;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;
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
public class BusCodeServiceImpl extends ServiceImpl<BusCodeDao, BusCode> implements BusCodeService {

    @Autowired
    private BusCodeService busCodeService;

    @Autowired
    private BusChapterService busChapterService;

    /**
     * 这个接口给后台调代码任务列表用
     * @param page
     * @param chapter
     * @param respInfo
     */
    @Override
    public void getCodeListByChapter(Page<BusCode> page, BusChapter chapter, RespInfo respInfo) {

        try{
            Page<BusCode> resultPage;
            resultPage = super.selectPage(page,
                    new EntityWrapper<BusCode>()
                            .setSqlSelect("id,title,create_time")
                            .eq("chapter_id",chapter.getId())
                            .orderBy("sort",true));
            respInfo.setMsg("获取代码任务信息成功");
            respInfo.setValue(resultPage);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取代码任务信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    /**
     * 这个接口给前端调用任务代码列表用
     * @param chapter
     * @param respInfo
     */
    @Override
    public void getCodeListToFront(BusChapter chapter, RespInfo respInfo){
        try{
            List<BusCode> busCodeList = super.selectList(
                    new EntityWrapper<BusCode>()
                        .setSqlSelect("id,title")
                        .eq("chapter_id",chapter.getId())
                        .orderBy("sort",true));
            respInfo.setMsg("获取代码任务信息成功");
            respInfo.setValue(busCodeList);
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取代码任务信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }


    @Override
    public void insertOrUpdateCode(User user, BusChapter busChapter, BusCode busCode, RespInfo respInfo) {
        try{
            if (StringUtils.isNotBlank(busChapter.getId())) {
                busCode.setChapterId(busChapter.getId());
                if (StringUtils.isBlank(busCode.getId())) { //是插入一条代码任务
                    busCode.setCreateTime(new Date());
                    busCode.setCreateBy(user.getId());
                    //子章节的课时加1
                    busChapter = busChapterService.selectOne(new EntityWrapper<BusChapter>()
                                            .setSqlSelect("id,classtime")
                                            .eq("id",busChapter.getId()));
                    busChapter.setClasstime(busChapter.getClasstime()+1);
                    busChapterService.insertOrUpdate(busChapter);
                }
                busCode.setTitle(Base64Util.urlDecode(busCode.getTitle()));
                busCode.setContent(Base64Util.urlDecode(busCode.getContent()));
//                busCode.setResult(Base64Util.urlDecode(busCode.getResult()));
                busCode.setMessage(Base64Util.urlDecode(busCode.getMessage()));
                if ("".equals(busCode.getContent().trim())){
                    busCode.setContent("");
                }
//                if ("".equals(busCode.getResult().trim())){
//                    busCode.setResult("");
//                }
                super.insertOrUpdate(busCode);
                respInfo.setMsg("操作章节信息成功");
                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            } else {
                respInfo.setError("章节ID为空");
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            respInfo.setValue(ex);
            respInfo.setMsg("插入或更新代码任务信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    @Override
    public void deleteCode(BusCode busCode, RespInfo respInfo) {
        try{
            busCode = super.selectOne(new EntityWrapper<BusCode>()
                    .setSqlSelect("id,chapter_id AS chapterId")
                    .eq("id", busCode.getId()));
            BusChapter busChapter = busChapterService.selectOne(new EntityWrapper<BusChapter>()
                    .setSqlSelect("id,classtime")
                    .eq("id",busCode.getChapterId()));
            busChapter.setClasstime(busChapter.getClasstime()-1);
            busChapterService.insertOrUpdate(busChapter);
            super.deleteById(busCode);
            respInfo.setMsg("删除代码任务成功");
            respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("删除代码任务出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }

    @Override
    public void getByChapterId(BusCode buscode, RespInfo respInfo) {
        try{
            BusCode busCode =  busCodeService.selectOne(new EntityWrapper<BusCode>().eq("chapter_id",buscode.getChapterId()));
            if (busCode != null) {
                respInfo.setMsg("获取code信息成功");
                respInfo.setValue(busCode);
                respInfo.setCode(ServiceErrorCodeEnum.SUCCESS.getErrorCode());
            } else {
                respInfo.setMsg("获取code信息失败");
                respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            }

        } catch (Exception ex) {
            respInfo.setValue(ex);
            respInfo.setMsg("获取code信息出错");
            respInfo.setCode(ServiceErrorCodeEnum.ERROR.getErrorCode());
            respInfo.setError(ex.toString());
        }
    }
}