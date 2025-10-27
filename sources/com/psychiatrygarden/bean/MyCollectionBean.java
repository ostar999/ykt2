package com.psychiatrygarden.bean;

import com.psychiatrygarden.bean.MaterialListBean;
import java.util.List;

/* loaded from: classes5.dex */
public class MyCollectionBean {
    private String code;
    private List<MaterialListBean.MaterialListData> data;
    private String message;

    public String getCode() {
        return this.code;
    }

    public List<MaterialListBean.MaterialListData> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<MaterialListBean.MaterialListData> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
