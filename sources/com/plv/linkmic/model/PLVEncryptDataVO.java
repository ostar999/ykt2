package com.plv.linkmic.model;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVEncryptDataVO<T> {
    private int code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public int getCode() {
        return this.code;
    }

    public T getData() {
        return (T) this.data;
    }

    public Object getDataObj() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isEncryption() {
        return this.encryption;
    }

    public void setCode(int i2) {
        this.code = i2;
    }

    public void setData(Object obj) {
        this.data = obj;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
        return "PLVEncryptDataVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", data=" + this.data + ", encryption=" + this.encryption + '}';
    }
}
