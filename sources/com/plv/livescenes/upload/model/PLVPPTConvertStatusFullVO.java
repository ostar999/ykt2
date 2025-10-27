package com.plv.livescenes.upload.model;

import java.util.List;

/* loaded from: classes5.dex */
public class PLVPPTConvertStatusFullVO {
    private int code;
    private Object data;
    private boolean encryption;
    private String message;
    private String status;

    public int getCode() {
        return this.code;
    }

    public List<PLVPPTConvertStatusVO> getData() {
        return (List) this.data;
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
