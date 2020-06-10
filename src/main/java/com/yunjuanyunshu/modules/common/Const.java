package com.yunjuanyunshu.modules.common;

/**
 * @author carl
 * @date 2018/02/13
 */

public class Const {

    public static final String CURRENT_USER = "currentUser";

    public interface DeleteFlagStatus {
        /**
         * 删除状态
         */
        int IS_DELETE = 1;
        /**
         * 未删除状态
         */
        int UN_DELETE = 0;
    }

    public interface Role{
        //普通用户
        String ROLE_USER = "0";
        //教师用户
        String ROLE_TEACHER = "1";
        //管理员用户
        String ROLE_ADMIN = "2";
    }
}
