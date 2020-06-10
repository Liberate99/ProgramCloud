package com.yunjuanyunshu.modules.web.system;


import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.Log;
import com.yunjuanyunshu.modules.service.LogService;
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
 * @since 2017-06-28 14:52:40
 */
@Component
@ClassAnnot("system.log")
public class LogController {
    private static LogService logService;
    /**
     *静态注入service
    *@param logService
    */
    @Autowired
    public LogController(LogService logService){
 LogController.logService=logService;
 }
    /**
     * 删除记录
     *@param log
     */	
     @MethodAnnot
     public static boolean delete(Log log){
        return logService.deleteById(log);
    }

    /**
     * Insert or update boolean.
     *
     * @param log 
     * @return the boolean
     */
    @MethodAnnot
    public static boolean insertOrUpdate(Log log){
        return logService.insertOrUpdate(log);
    }

    /**
     * Get log.
     *
     * @param log 
     * @return the log
     */
    @MethodAnnot
    public static Log get(Log log){
        return logService.selectById(log);
    }
    @MethodAnnot
    public static Page<Log> getLogByPage(Page<Log> page) {
        return logService.selectPage(page);
    }
    @MethodAnnot
    public static  int insertLog(Log log){
        return logService.insertLog(log);
    }
	
}