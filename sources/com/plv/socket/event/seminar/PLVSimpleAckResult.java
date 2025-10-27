package com.plv.socket.event.seminar;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVSimpleAckResult {
    public static final String STATUS_SUCCESS = "success";
    private Integer code;
    private String message;
    private String status;

    public Integer getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isSuccess() {
        return "success".equals(this.status);
    }

    public void setCode(Integer num) {
        this.code = num;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
        return "PLVSimpleAckResult{status='" + this.status + CharPool.SINGLE_QUOTE + ", message='" + this.message + CharPool.SINGLE_QUOTE + ", code=" + this.code + '}';
    }
}
