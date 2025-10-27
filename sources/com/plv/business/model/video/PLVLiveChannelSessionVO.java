package com.plv.business.model.video;

/* loaded from: classes4.dex */
public class PLVLiveChannelSessionVO {
    private int code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public int getCode() {
        return this.code;
    }

    public String getData() {
        return (String) this.data;
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
}
