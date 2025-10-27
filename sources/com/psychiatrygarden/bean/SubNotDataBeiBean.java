package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class SubNotDataBeiBean implements Serializable {
    private String app_id;
    private String content;
    private String question_id;

    public String getApp_id() {
        return this.app_id;
    }

    public String getContent() {
        return this.content;
    }

    public String getQuestion_id() {
        return this.question_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }
}
