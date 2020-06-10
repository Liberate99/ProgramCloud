package com.yunjuanyunshu.modules.common;

/**
 * @author  penelopeWu
 * Date:2018-02-07 14:35
 */
public enum QuestionAndAnswerEnum {
    /**
     * 问题状态
     */
    QUESTION_IS_NOT_RECOMMENDED("问题未被推荐","0"),
    QUESTION_IS_RECOMMENDED("问题被推荐","1"),
    QUESTION_STATUS_DEFAULT("问题默认状态","1"),
    QUESTION_STATUS_SOLVED("问题被解决","0"),

    /**
     * 回答状态
     */
    ANSWER_IS_NOT_ACCEPTED("回答未被采纳","0"),
    ANSWER_IS_ACCEPTED("回答被采纳","1"),
    ANSWER_IS_NOT_RECOMMENDED("回答未被推荐","0"),
    ANSWER_IS_RECOMMENDED("回答被推荐","1"),



    ;


    private String message;
    private String code;

    QuestionAndAnswerEnum(String message,String code) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
