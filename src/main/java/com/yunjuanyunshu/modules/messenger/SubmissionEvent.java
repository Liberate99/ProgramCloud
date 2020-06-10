package com.yunjuanyunshu.modules.messenger;

import org.springframework.context.ApplicationEvent;

/**
 * @author carl
 * @date 2018/02/12
 */

public class SubmissionEvent extends ApplicationEvent {

    public SubmissionEvent(Object source, long submissionId, String judgeResult, String message, boolean isCompleted) {
        super(source);
        this.submissionId = submissionId;
        this.judgeResult = judgeResult;
        this.message = message;
        this.isCompleted = isCompleted;
    }

    public long getSubmissionId() {
        return submissionId;
    }

    public String getJudgeResult() {
        return judgeResult;
    }

    public String getMessage() {
        return message;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * 评测记录的唯一标识符.
     */
    private final long submissionId;

    /**
     * 当前评测结果.
     */
    private final String judgeResult;

    /**
     * 评测消息.
     */
    private final String message;

    /**
     * 评测是否完成.
     */
    private final boolean isCompleted;
}
