package com.aliyun.auth.model;

/* loaded from: classes2.dex */
public class VodErrorResponse {
    private String Code;
    private String HostId;
    private String Message;
    private String RequestId;

    public String getCode() {
        return this.Code;
    }

    public String getHostId() {
        return this.HostId;
    }

    public String getMessage() {
        return this.Message;
    }

    public String getRequestId() {
        return this.RequestId;
    }

    public void setCode(String str) {
        this.Code = str;
    }

    public void setHostId(String str) {
        this.HostId = str;
    }

    public void setMessage(String str) {
        this.Message = str;
    }

    public void setRequestId(String str) {
        this.RequestId = str;
    }
}
