package com.yunjuanyunshu.modules.common;

/**
 * @author penelopeWu
 * Date:2018-02-05 17:58
 */
public enum AnswerLikeEnum {
    /**
     * 点赞状态：已赞
     */
    STATUS_LIKE("已赞","1"),
    /**
     * 点赞状态：未赞
     */
    STATUS_NOT_LIKE("未赞","0");

    private String status;
    private String code;

    AnswerLikeEnum(String status,String code){
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
