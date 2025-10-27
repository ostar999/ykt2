package com.plv.linkmic.model;

/* loaded from: classes4.dex */
public class PLVLinkMicJoinStatusVO {
    private int code;
    private Object data;
    private boolean encryption;
    private String requestId;
    private String status;
    private Boolean success;

    public int getCode() {
        return this.code;
    }

    public PLVLinkMicJoinStatus getData() {
        return (PLVLinkMicJoinStatus) this.data;
    }

    public Object getDataObj() {
        return this.data;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getStatus() {
        return this.status;
    }

    public Boolean getSuccess() {
        return this.success;
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

    public void setRequestId(String str) {
        this.requestId = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public void setSuccess(Boolean bool) {
        this.success = bool;
    }
}
