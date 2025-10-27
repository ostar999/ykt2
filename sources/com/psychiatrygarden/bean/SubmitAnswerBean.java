package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class SubmitAnswerBean {
    private String answer;
    private String app_id;
    private String chapter_id;
    private String chapter_parent_id;
    private String duration;
    private String identity_id;
    private String is_right;
    private int paper_id;
    private String question_id;
    private String subject_id;

    public SubmitAnswerBean(String answer, String question_id, String is_right, String app_id, String identity_id, String chapter_id) {
        this.answer = answer;
        this.question_id = question_id;
        this.is_right = is_right;
        this.app_id = app_id;
        this.identity_id = identity_id;
        this.chapter_id = chapter_id;
    }

    public String getAnswer() {
        return this.answer;
    }

    public String getApp_id() {
        return this.app_id;
    }

    public String getChapter_id() {
        return this.chapter_id;
    }

    public String getChapter_parent_id() {
        return this.chapter_parent_id;
    }

    public String getDuration() {
        return this.duration;
    }

    public String getIdentity_id() {
        return this.identity_id;
    }

    public String getIs_right() {
        return this.is_right;
    }

    public int getPaper_id() {
        return this.paper_id;
    }

    public String getQuestion_id() {
        return this.question_id;
    }

    public String getSubject_id() {
        return this.subject_id;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public void setChapter_parent_id(String chapter_parent_id) {
        this.chapter_parent_id = chapter_parent_id;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }

    public void setIs_right(String is_right) {
        this.is_right = is_right;
    }

    public void setPaper_id(int paper_id) {
        this.paper_id = paper_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }
}
