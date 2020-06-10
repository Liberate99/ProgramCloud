package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusCollegeUser;
import com.yunjuanyunshu.modules.service.BusCollegeUserService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author carl
 * @since 2017-11-09
 */
@Component
@ClassAnnot("business.busCollegeUser")
public class BusCollegeUserController  {
        private static BusCollegeUserService busCollegeUserService;

        /**
         * 静态注入service
         *
         * @param busCollegeUserService
         */
        @Autowired
        public BusCollegeUserController (BusCollegeUserService busCollegeUserService){
            BusCollegeUserController .busCollegeUserService=busCollegeUserService;
        }

        /**
         * Delete boolean.
         *
         * @param busCollegeUser the busCollegeUser
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(BusCollegeUser busCollegeUser){
                return busCollegeUserService.deleteById(busCollegeUser);
        }

        /**
         * Insert or update boolean.
         *
         * @param busCollegeUser the busCollegeUser
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(BusCollegeUser busCollegeUser){
                return busCollegeUserService.insertOrUpdate(busCollegeUser);
        }

        /**
         * Get busCollegeUser.
         *
         * @param busCollegeUser the busCollegeUser
         * @return the busCollegeUser
         */
        @MethodAnnot
        public static BusCollegeUser get(BusCollegeUser busCollegeUser){
                return busCollegeUserService.selectById(busCollegeUser);
        }

        /**
         * Gets busCollegeUser by page.
         *
         * @return the busCollegeUser by page
         */
        @MethodAnnot
        public static Page<BusCollegeUser> getBusCollegeUserByPage(Page<BusCollegeUser> page){
            return busCollegeUserService.selectPage(page,
                new EntityWrapper<BusCollegeUser>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }
        }