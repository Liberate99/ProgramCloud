package com.yunjuanyunshu.modules.common;

/**
 * @author carl
 * @date 2018/02/13
 */

public enum ResponseCode {

    SUCCESS(0, "SUCCESS"),
    ERROR(-1, "ERROR"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    IN_SUFFICIENT_AUTHORITY(11008, "权限不足"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }
}
