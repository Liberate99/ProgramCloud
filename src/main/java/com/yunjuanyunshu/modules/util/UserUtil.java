package com.yunjuanyunshu.modules.util;

import com.yunjuanyunshu.modules.entity.User;
import com.yunjuanyunshu.yunfd.sysInfo.RespInfo;
import com.yunjuanyunshu.yunfd.sysInfo.ServiceErrorCodeEnum;

/**
 * @Author  26928 on 2017-10-21.
 */
public class UserUtil {

    public static User getUser(String token,RespInfo respInfo) throws Exception {
        User user=(User) CacheUtils.get(token);
        if(user==null){
            respInfo.setCode(ServiceErrorCodeEnum.TokenIsOverdue.getErrorCode());
            throw new Exception(ServiceErrorCodeEnum.TokenIsOverdue.getErrorStr());
        }
        return user;
    }
}
