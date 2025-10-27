package com.psychiatrygarden.bean;

import com.psychiatrygarden.bean.ForumTopBean;
import java.util.List;

/* loaded from: classes5.dex */
public class ForumTopCategoryBean {
    private String code;
    private List<ForumTopBean.TopModule> data;
    private String message;

    public String getCode() {
        return this.code;
    }

    public List<ForumTopBean.TopModule> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<ForumTopBean.TopModule> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
