package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class oneTitleDb implements Serializable {
    private String cate_name;
    private String cate_p_id;
    private int cate_user_count;
    private long[] list_questionid;
    private String total;
    private String year;
    private boolean is_choice = true;
    private List<twoTitleDb> chapters = new ArrayList();

    public String getCate_name() {
        return this.cate_name;
    }

    public String getCate_p_id() {
        return this.cate_p_id;
    }

    public int getCate_user_count() {
        return this.cate_user_count;
    }

    public List<twoTitleDb> getChapters() {
        return this.chapters;
    }

    public long[] getList_questionid() {
        return this.list_questionid;
    }

    public String getTotal() {
        return this.total;
    }

    public String getYear() {
        return this.year;
    }

    public boolean isIs_choice() {
        return this.is_choice;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }

    public void setCate_p_id(String cate_p_id) {
        this.cate_p_id = cate_p_id;
    }

    public void setCate_user_count(int cate_user_count) {
        this.cate_user_count = cate_user_count;
    }

    public void setChapters(List<twoTitleDb> chapters) {
        this.chapters = chapters;
    }

    public void setIs_choice(boolean is_choice) {
        this.is_choice = is_choice;
    }

    public void setList_questionid(long[] list_questionid) {
        this.list_questionid = list_questionid;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
