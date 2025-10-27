package com.plv.foundationsdk.model.web;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVJSResponseVO<T> implements PLVFoundationVO {
    private T data;
    private String message;
    private int status;

    public T getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return this.status;
    }

    public void setData(T t2) {
        this.data = t2;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(int i2) {
        this.status = i2;
    }
}
