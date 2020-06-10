package com.yunjuanyunshu.modules.web.system;


import com.yunjuanyunshu.modules.entity.Area;
import com.yunjuanyunshu.modules.service.AreaService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author  apple
 * @since 2017-06-28 14:52:32
 */
@Component
@ClassAnnot("system.area")
public class AreaController {
    private static AreaService areaService;
    /**
     *静态注入service
    *@param areaService
    */
    @Autowired
    public AreaController(AreaService areaService){
 AreaController.areaService=areaService;
 }
    /**
     * 删除记录
     *@param area
     */	
     @MethodAnnot
     public static boolean delete(Area area){
        return areaService.deleteById(area);
    }

    /**
     * Insert or update boolean.
     *
     * @param area 
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(Area area){
        return areaService.insertOrUpdate(area);
    }

    /**
     * Get area.
     *
     * @param area 
     * @return the area
     */
    @MethodAnnot
    public static Area get(Area area){
        return areaService.selectById(area);
    }
	
}