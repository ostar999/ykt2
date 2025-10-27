package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class twoTitleDb implements Serializable {
    private String cate_id;
    private String cate_name;
    private String cate_p_id;
    private int cate_user_count;
    private long[] list_questionid;
    private String total;
    private String unit;
    private String year;
    private long count = 0;
    private boolean is_choice = true;
    private List<QuestionInfoBean> topics = new ArrayList();

    public String getCate_id() {
        return this.cate_id;
    }

    public String getCate_name() {
        return this.cate_name;
    }

    public String getCate_p_id() {
        return this.cate_p_id;
    }

    public int getCate_user_count() {
        return this.cate_user_count;
    }

    public long getCount() {
        return this.count;
    }

    public long[] getList_questionid() {
        return this.list_questionid;
    }

    public List<QuestionInfoBean> getTopics() {
        return this.topics;
    }

    public String getTotal() {
        return this.total;
    }

    public String getUnit() {
        return this.unit;
    }

    public String getYear() {
        return this.year;
    }

    public boolean isIs_choice() {
        return this.is_choice;
    }

    public void setCate_id(String cate_id) {
        this.cate_id = cate_id;
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

    public void setCount(long count) {
        this.count = count;
    }

    public void setIs_choice(boolean is_choice) {
        this.is_choice = is_choice;
    }

    public void setList_questionid(long[] list_questionid) {
        this.list_questionid = list_questionid;
    }

    public void setTopics(List<QuestionInfoBean> topics) {
        this.topics = topics;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
