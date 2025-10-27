package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionYearBean implements Serializable {
    private String error_num;
    private String right_num;
    private String total;
    private String year = "0";
    private List<QuestionInfoBean> topics = new ArrayList();

    public String getError_num() {
        return this.error_num;
    }

    public String getRight_num() {
        return this.right_num;
    }

    public List<QuestionInfoBean> getTopics() {
        return this.topics;
    }

    public String getTotal() {
        return this.total;
    }

    public String getYear() {
        return this.year;
    }

    public void setError_num(String error_num) {
        this.error_num = error_num;
    }

    public void setRight_num(String right_num) {
        this.right_num = right_num;
    }

    public void setTopics(List<QuestionInfoBean> topics) {
        this.topics = topics;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
