package edu.qhu.qhuoj.message;

import org.springframework.context.ApplicationEvent;

public class SubmissionEvent extends ApplicationEvent {

    public SubmissionEvent(Object source, int submissionId, String judgeResult, String message, boolean isCompleted) {
        super(source);
        this.submissionId = submissionId;
        this.judgeResult = judgeResult;
        this.message = message;
        this.isCompleted = isCompleted;
    }

    public int getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(int submissionId) {
        this.submissionId = submissionId;
    }

    public String getJudgeResult() {
        return judgeResult;
    }

    public void setJudgeResult(String judgeResult) {
        this.judgeResult = judgeResult;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    private int submissionId;
    private String judgeResult;
    private String message;
    private boolean isCompleted;
}
