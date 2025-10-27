package com.plv.livescenes.hiclass.vo;

/* loaded from: classes4.dex */
public class PLVHCLessonStatusVO {
    private Integer code;
    private DataVO data;
    private ErrorBody error;
    private String requestId;
    private String status;
    private Boolean success;

    public static class DataVO {
        public static final int STATUS_FINISH_CLASS = 2;
        public static final int STATUS_LESSON_NOT_STARTED = 0;
        public static final int STATUS_ON_CLASS = 1;
        private Long endTime;
        private Long serverTime;
        private Integer status;

        public Long getEndTime() {
            return this.endTime;
        }

        public Long getServerTime() {
            return this.serverTime;
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setEndTime(Long l2) {
            this.endTime = l2;
        }

        public void setServerTime(Long l2) {
            this.serverTime = l2;
        }

        public void setStatus(Integer num) {
            this.status = num;
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
