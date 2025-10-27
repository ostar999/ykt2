package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class SubmitNotesBean {
    private String app_id;
    private String content;
    private String question_app_id;
    private Long question_id;

    public SubmitNotesBean() {
    }

    public String getApp_id() {
        return this.app_id;
    }

    public String getContent() {
        return this.content;
    }

    public String getQuestion_app_id() {
        return this.question_app_id;
    }

    public Long getQuestion_id() {
        return this.question_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setQuestion_app_id(String question_app_id) {
        this.question_app_id = question_app_id;
    }

    public void setQuestion_id(Long question_id) {
        this.question_id = question_id;
    }

    public SubmitNotesBean(Long question_id, String content, String app_id, String question_app_id) {
        this.question_id = question_id;
        this.content = content;
        this.app_id = app_id;
        this.question_app_id = question_app_id;
    }
}
