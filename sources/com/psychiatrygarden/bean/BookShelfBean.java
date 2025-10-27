package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class BookShelfBean {
    private String code;
    private List<BookShelfDataBean> data;
    private String message;

    public String getCode() {
        return this.code;
    }

    public List<BookShelfDataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<BookShelfDataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
