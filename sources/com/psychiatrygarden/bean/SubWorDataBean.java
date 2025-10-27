package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class SubWorDataBean implements Serializable {
    private String answer;
    private String app_id;
    private String is_right;
    private String question_id;

    public String getAnswer() {
        return this.answer;
    }

    public String getApp_id() {
        return this.app_id;
    }

    public String getIs_right() {
        return this.is_right;
    }

    public String getQuestion_id() {
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

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }
}
