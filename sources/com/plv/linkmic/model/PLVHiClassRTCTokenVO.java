package com.plv.linkmic.model;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVHiClassRTCTokenVO {
    private Integer code;
    private String data;
    private ErrorBean error;
    private String requestId;
    private String status;
    private Boolean success;

    public static class ErrorBean {
        private Integer code;
        private String desc;

        public Integer getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public void setCode(Integer num) {
            this.code = num;
        }

        public void setDesc(String str) {
            this.desc = str;
        }

        public String toString() {
            return "ErrorBean{code=" + this.code + ", desc='" + this.desc + CharPool.SINGLE_QUOTE + '}';
        }
    }

    public Integer getCode() {
        return this.code;
    }

    public String getData() {
        return this.data;
    }

    public ErrorBean getError() {
        return this.error;
    }

    public String getRequestId() {
        return this.requestId;
    }

    public String getStatus() {
        return this.status;
    }

    public Boolean isSuccess() {
        return this.success;
    }

    public void setCode(Integer num) {
        this.code = num;
    }

    public void setData(String str) {
        this.data = str;
    }

    public void setError(ErrorBean errorBean) {
        this.error = errorBean;
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

    public String toString() {
        return "PLVHiClassRTCTokenVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", requestId='" + this.requestId + CharPool.SINGLE_QUOTE + ", data=" + this.data + ", error=" + this.error + ", success=" + this.success + '}';
    }
}
