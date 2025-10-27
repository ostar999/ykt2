package com.plv.livescenes.hiclass.vo;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVHCTeacherLoginVerifyVO {
    private Integer code;
    private DataBean data;
    private ErrorBean error;
    private String requestId;
    private String status;
    private Boolean success;

    public static class DataBean {
        private Boolean status;

        public Boolean isStatus() {
            return this.status;
        }

        public void setStatus(Boolean bool) {
            this.status = bool;
        }

        public String toString() {
            return "DataBean{status=" + this.status + '}';
        }
    }

    public static class ErrorBean {
        public static final int CODE_REPEAT_LOGIN = 20016;
        private Integer code;
        private String desc;

        public Integer getCode() {
            return this.code;
        }

        public String getDesc() {
            return this.desc;
        }

        public boolean isRepeatLogin() {
            Integer num = this.code;
            return num != null && 20016 == num.intValue();
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

    public DataBean getData() {
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

    public void setData(DataBean dataBean) {
        this.data = dataBean;
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
        return "PLVHCTeacherLoginVerifyVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", requestId='" + this.requestId + CharPool.SINGLE_QUOTE + ", data=" + this.data + ", success=" + this.success + '}';
    }
}
