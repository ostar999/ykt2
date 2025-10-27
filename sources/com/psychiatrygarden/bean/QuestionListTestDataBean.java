package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionListTestDataBean implements Serializable {
    private String get_score;
    private List<String> list;
    private String scrore_rate;
    private String time;
    private String title;
    private String total_score;
    private String user_name;

    public String getGet_score() {
        return this.get_score;
    }

    public List<String> getList() {
        return this.list;
    }

    public String getScrore_rate() {
        return this.scrore_rate;
    }

    public String getTime() {
        return this.time;
    }

    public String getTitle() {
        return this.title;
    }

    public String getTotal_score() {
        return this.total_score;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public void setGet_score(String get_score) {
        this.get_score = get_score;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setScrore_rate(String scrore_rate) {
        this.scrore_rate = scrore_rate;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTotal_score(String total_score) {
        this.total_score = total_score;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
