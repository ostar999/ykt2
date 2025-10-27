package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class RankBean {
    private String code;
    private List<RankBeanData> data;
    private String message;

    public String getCode() {
        return this.code;
    }

    public List<RankBeanData> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<RankBeanData> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
