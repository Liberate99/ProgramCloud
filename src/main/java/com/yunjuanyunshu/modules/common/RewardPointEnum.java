package com.yunjuanyunshu.modules.common;

/**
 * @author penelopeWu
 * Date:2018-02-05 16:18
 */
public enum RewardPointEnum {

    /**
     * 积分变化类型
     */
    ADD("积分增加","1"),
    SUBTRACT("积分减少","-1"),

    /**
     * 积分变化事件
     */
    EVENT_LEARN_CHAPTER("章节学完","0"),
    EVENT_ASK_QUESTION("提问问题","1"),
    EVENT_ANSWER_RECOMMENDED("回答被推荐","2"),
    EVENT_QUESTION_RECOMMENDED("问题被推荐","3"),
    EVENT_ANSWER_ACCEPTED("回答被采纳","4"),

    /**
     * 积分规则
     */
    REWARD_CHAPTER_LEARN("学完一章节",10),
    REWARD_ANSWER_RECOMMENDED("回答被推荐",2),
    REWARD_QUESTION_RECOMMENDED("问题被推荐",1)

    ;

    RewardPointEnum(String message,String code){
        this.message = message;
        this.code = code;
    }
    RewardPointEnum(String message,Integer amount){
        this.message = message;
        this.amount = amount;
    }
    private String code;
    private String message;
    private Integer amount;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
