package com.yunjuanyunshu.modules.web.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.yunjuanyunshu.modules.entity.BusProductComment;
import com.yunjuanyunshu.modules.service.BusProductCommentService;
import com.yunjuanyunshu.yunfd.aop.annotation.ClassAnnot;
import com.yunjuanyunshu.yunfd.aop.annotation.MethodAnnot;
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
@ClassAnnot("business.busProductComment")
public class BusProductCommentController  {
        private static BusProductCommentService busproductcommentService;

        /**
         * 静态注入service
         *
         * @param busproductcommentService
         */
        @Autowired
        public BusProductCommentController (BusProductCommentService busproductcommentService){
            BusProductCommentController .busproductcommentService=busproductcommentService;
        }

        /**
         * Delete boolean.
         *
         * @param busproductcomment the busproductcomment
         * @return the boolean
         */
        @MethodAnnot
        public static boolean delete(BusProductComment busproductcomment){
                return busproductcommentService.deleteById(busproductcomment);
        }

        /**
         * Insert or update boolean.
         *
         * @param busproductcomment the busproductcomment
         * @return the boolean
         */
        @MethodAnnot
        public static boolean insertOrUpdate(BusProductComment busproductcomment){
                return busproductcommentService.insertOrUpdate(busproductcomment);
        }

        /**
         * Get busproductcomment.
         *
         * @param busproductcomment the busproductcomment
         * @return the busproductcomment
         */
        @MethodAnnot
        public static BusProductComment get(BusProductComment busproductcomment){
                return busproductcommentService.selectById(busproductcomment);
        }

        /**
         * Gets busproductcomment by page.
         *
         * @return the busproductcomment by page
         */
        @MethodAnnot
        public static Page<BusProductComment> getBusProductCommentByPage(Page<BusProductComment> page){
            return busproductcommentService.selectPage(page,
                new EntityWrapper<BusProductComment>().orderBy(page.getOrderByField(),
                    page.isAsc()));
        }
        }