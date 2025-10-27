package com.plv.livescenes.hiclass.vo;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVHCLessonSimpleInfoResultVO {
    private Integer code;
    private DataVO data;
    private ErrorBody error;
    private String requestId;
    private String status;
    private Boolean success;

    public static class DataVO {
        public static final String WATCH_CONDITION_CODE = "CODE";
        public static final String WATCH_CONDITION_DIRECT = "DIRECT";
        public static final String WATCH_CONDITION_NULL = "NULL";
        public static final String WATCH_CONDITION_WHITE_LIST = "WHITE_LIST";
        private String cover;
        private String name;
        private String watchCondition;

        public String getCover() {
            return this.cover;
        }

        public String getName() {
            return this.name;
        }

        public String getWatchCondition() {
            return this.watchCondition;
        }

        public void setCover(String str) {
            this.cover = str;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setWatchCondition(String str) {
            this.watchCondition = str;
        }

        public String toString() {
            return "DataVO{cover='" + this.cover + CharPool.SINGLE_QUOTE + ", name='" + this.name + CharPool.SINGLE_QUOTE + ", watchCondition='" + this.watchCondition + CharPool.SINGLE_QUOTE + '}';
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

        public String toString() {
            return "ErrorBody{code=" + this.code + ", desc='" + this.desc + CharPool.SINGLE_QUOTE + '}';
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

    public String toString() {
        return "PLVHCLessonSimpleInfoResultVO{code=" + this.code + ", data=" + this.data + ", error=" + this.error + ", requestId='" + this.requestId + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", success=" + this.success + '}';
    }
}
