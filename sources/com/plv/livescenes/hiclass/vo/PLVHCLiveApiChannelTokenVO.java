package com.plv.livescenes.hiclass.vo;

/* loaded from: classes4.dex */
public class PLVHCLiveApiChannelTokenVO {
    private Integer code;
    private DataVO data;
    private ErrorBody error;
    private String requestId;
    private String status;
    private Boolean success;

    public static class DataVO {
        private String appId;
        private Integer channelId;
        private String token;

        public String getAppId() {
            return this.appId;
        }

        public Integer getChannelId() {
            return this.channelId;
        }

        public String getToken() {
            return this.token;
        }

        public void setAppId(String str) {
            this.appId = str;
        }

        public void setChannelId(Integer num) {
            this.channelId = num;
        }

        public void setToken(String str) {
            this.token = str;
        }
    }

    public static class ErrorBody {
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
    }

    public Integer getCode() {
        return this.code;
    }

    public DataVO getData() {
        return this.data;
    }

    public ErrorBody getError() {
        return this.error;
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

    public void setCode(Integer num) {
        this.code = num;
    }

    public void setData(DataVO dataVO) {
        this.data = dataVO;
    }

    public void setError(ErrorBody errorBody) {
        this.error = errorBody;
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
