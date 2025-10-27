package com.plv.livescenes.hiclass.vo;

import cn.hutool.core.text.CharPool;

/* loaded from: classes4.dex */
public class PLVHCLessonFinishVO {
    private Integer code;
    private DataBean data;
    private ErrorBean error;
    private String requestId;
    private String status;
    private Boolean success;

    public static class DataBean {
        private long endTime;
        private Long lessonId;
        private long startTime;
        private Integer status;

        public long getEndTime() {
            return this.endTime;
        }

        public Long getLessonId() {
            return this.lessonId;
        }

        public long getStartTime() {
            return this.startTime;
        }

        public Integer getStatus() {
            return this.status;
        }

        public void setEndTime(long j2) {
            this.endTime = j2;
        }

        public void setLessonId(Long l2) {
            this.lessonId = l2;
        }

        public void setStartTime(long j2) {
            this.startTime = j2;
        }

        public void setStatus(Integer num) {
            this.status = num;
        }

        public String toString() {
            return "DataBean{lessonId=" + this.lessonId + ", startTime=" + this.startTime + ", endTime=" + this.endTime + ", status=" + this.status + '}';
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
        return "PLVHCLessonFinishVO{code=" + this.code + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", requestId='" + this.requestId + CharPool.SINGLE_QUOTE + ", data=" + this.data + ", error=" + this.error + ", success=" + this.success + '}';
    }
}
