package com.yunjuanyunshu.modules.web.system;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.Dict;
import com.yunjuanyunshu.modules.service.DictService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.common.util.RespInfoUtil;
import com.yunjuanyunshu.yunfd.common.util.StringUtils;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author  apple
 * @since 2017-06-28 14:52:36
 */
@Component
@ClassAnnot("system.dict")
public class DictController {
    private static DictService dictService;
    /**
     *静态注入service
    *@param dictService
    */
    @Autowired
    public DictController(DictService dictService){
 DictController.dictService=dictService;
 }
    /**
     * 删除记录
     *@param dict
     */	
     @MethodAnnot
     public static boolean delete(Dict dict){
        return dictService.deleteById(dict);
    }

    /**
     * Insert or update boolean.
     *
     * @param dict 
     * @return the boolean
     */
    @MethodAnnot
    public static void insertOrUpdate(Dict dict,RespInfo paraRespInfo)
    {
        if (StringUtils.isBlank(dict.getId())) {
            dict.setCreateDate(new Date());
        } else {
            dict.setUpdateDate(new Date());
        }

        RespInfoUtil.businessError(dictService.insertOrUpdate(dict),"插入成功",
                ServiceErrorCodeEnum.SUCCESS,
                paraRespInfo);
    }

    /**
     * Get dict.
     *
     * @param dict 
     * @return the dict
     */
    @MethodAnnot
    public static void get(Dict dict,RespInfo paraRespInfo){
        EntityWrapper<Dict> ew = new EntityWrapper<Dict>();
        if(dict.getType()!=null) {
            ew.eq("type", dict.getType());
        }
        if(dict.getId()!=null) {
            ew.eq("id", dict.getId());
        }
        ew.orderBy("sort",false);
        RespInfoUtil.businessError(dictService.selectList(ew),"查询成功",
                ServiceErrorCodeEnum.SUCCESS,
                paraRespInfo);
    }
    @MethodAnnot
    public static Page<Dict> getDictByPageList(Page<Dict> page,Dict dict) {
         EntityWrapper<Dict> ew = new EntityWrapper<Dict>();
        ew.setEntity(dict);
        if(dict.getType()!=null) {
            ew.eq("type", dict.getType());
        }
        return dictService.selectPage(page,ew);
    }
    @MethodAnnot
    public static Page<Dict> getDictByPage(Page<Dict> page) {
        Wrapper<Dict> wrapper=new EntityWrapper<>();
        wrapper.orderBy("type,sort,update_date");
        return dictService.selectPage(page, wrapper);
    }
	
}