package com.plv.socket.net.model;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVHiClassChatTokenVO {
    private Integer code;
    private DataBean data;
    private ErrorBean error;
    private String requestId;
    private String status;
    private Boolean success;

    public static class DataBean {
        private String auth;
        private String chatToken;
        private String nickname;
        private String viewerId;

        public String getAuth() {
            return this.auth;
        }

        public String getChatToken() {
            return this.chatToken;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getViewerId() {
            return this.viewerId;
        }

        public void setAuth(String str) {
            this.auth = str;
        }

        public void setChatToken(String str) {
            this.chatToken = str;
        }

        public void setNickname(String str) {
            this.nickname = str;
        }

        public void setViewerId(String str) {
            this.viewerId = str;
        }

        public String toString() {
            return "DataBean{auth='" + this.auth + CharPool.SINGLE_QUOTE + ", viewerId='" + this.viewerId + CharPool.SINGLE_QUOTE + ", chatToken='" + this.chatToken + CharPool.SINGLE_QUOTE + ", nickname='" + this.nickname + CharPool.SINGLE_QUOTE + '}';
        }
    }

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
        return "PLVHiClassChatTokenVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", requestId='" + this.requestId + CharPool.SINGLE_QUOTE + ", data=" + this.data + ", error=" + this.error + ", success=" + this.success + '}';
    }
}
