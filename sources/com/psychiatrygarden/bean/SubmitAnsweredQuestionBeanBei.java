package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class SubmitAnsweredQuestionBeanBei {
    private String answer;
    private String app_id;
    private String is_right;
    private String question_app_id;
    private Long question_id;

    public SubmitAnsweredQuestionBeanBei() {
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getApp_id() {
        return this.app_id;
    }

    public String getIs_right() {
        return this.is_right;
    }

    public String getQuestion_app_id() {
        return this.question_app_id;
    }

    public Long getQuestion_id() {
        return this.question_id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void setIs_right(String is_right) {
        this.is_right = is_right;
    }

    public void setQuestion_app_id(String question_app_id) {
        this.question_app_id = question_app_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public SubmitAnsweredQuestionBeanBei(Long question_id, String answer, String is_right, String app_id, String question_app_id) {
        this.question_id = question_id;
        this.answer = answer;
        this.is_right = is_right;
        this.app_id = app_id;
        this.question_app_id = question_app_id;
    }
}
