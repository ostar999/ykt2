package com.plv.foundationsdk.net;

/* loaded from: classes4.dex */
public class PLVBaseResponseBean<T> {
    protected int code;
    protected T convertBody;
    protected String message;
    protected String status;

    public int getCode() {
        return this.code;
    }

    public T getConvertBody() {
        return this.convertBody;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setConvertBody(T t2) {
        this.convertBody = t2;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
