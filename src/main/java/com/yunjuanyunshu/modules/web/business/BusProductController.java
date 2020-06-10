package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusProduct;
import com.yunjuanyunshu.modules.service.BusProductService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjz
 * @since 2017-07-27
 */
@Component
@ClassAnnot("business.busProduct")
public class BusProductController  {
        private static BusProductService busproductService;

        /**
         * 静态注入service
         *
         * @param busproductService
         */
        @Autowired
        public BusProductController (BusProductService busproductService){
            BusProductController .busproductService=busproductService;
        }

        /**
         * Delete boolean.
         *
         * @param busproduct the busproduct
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(BusProduct busproduct){
                return busproductService.deleteById(busproduct);
        }

        /**
         * Insert or update boolean.
         *
         * @param busproduct the busproduct
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(BusProduct busproduct){
                return busproductService.insertOrUpdate(busproduct);
        }

        /**
         * Get busproduct.
         *
         * @param busproduct the busproduct
         * @return the busproduct
         */
        @MethodAnnot
        public static BusProduct get(BusProduct busproduct){
                return busproductService.selectById(busproduct);
        }
        @MethodAnnot
        public static void getPro(BusProduct busproduct, RespInfo respInfo){
                 busproductService.getPro(busproduct,respInfo);
        }

        /**
         * Gets busproduct by page.
         *
         * @return the busproduct by page
         */
        @MethodAnnot
        public static void getBusProductByPage(Page<BusProduct> page,RespInfo respInfo){
            busproductService.getBusProductByPage(page,respInfo);
        }
        }