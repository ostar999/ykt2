package com.plv.livescenes.hiclass.vo;

import cn.hutool.core.text.CharPool;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVHCStudentLessonListVO {
    private Integer code;
    private List<DataVO> data;
    private ErrorBody error;
    private String requestId;
    private String status;
    private Boolean success;

    public static class DataVO {
        private String channelId;
        private String cover;
        private Integer duration;
        private Long lessonId;
        private String name;
        private String startTime;
        private Integer status;
        private String teacherName;

        public String getChannelId() {
            return this.channelId;
        }

        public String getCover() {
            return this.cover;
        }

        public Integer getDuration() {
            return this.duration;
        }

        public Long getLessonId() {
            return this.lessonId;
        }

        public String getName() {
            return this.name;
        }

        public String getStartTime() {
            return this.startTime;
        }

        public Integer getStatus() {
            return this.status;
        }

        public String getTeacherName() {
            return this.teacherName;
        }

        public void setChannelId(String str) {
            this.channelId = str;
        }

        public void setCover(String str) {
            this.cover = str;
        }

        public void setDuration(Integer num) {
            this.duration = num;
        }

        public void setLessonId(Long l2) {
            this.lessonId = l2;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setStartTime(String str) {
            this.startTime = str;
        }

        public void setStatus(Integer num) {
            this.status = num;
        }

        public void setTeacherName(String str) {
            this.teacherName = str;
        }

        public String toString() {
            return "DataVO{cover='" + this.cover + CharPool.SINGLE_QUOTE + ", duration=" + this.duration + ", lessonId=" + this.lessonId + ", name='" + this.name + CharPool.SINGLE_QUOTE + ", startTime='" + this.startTime + CharPool.SINGLE_QUOTE + ", status=" + this.status + ", teacherName='" + this.teacherName + CharPool.SINGLE_QUOTE + ", channelId='" + this.channelId + CharPool.SINGLE_QUOTE + '}';
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

    public List<DataVO> getData() {
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

    public void setData(List<DataVO> list) {
        this.data = list;
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
        return "PLVHCStudentLessonListVO{code=" + this.code + ", data=" + this.data + ", error=" + this.error + ", requestId='" + this.requestId + CharPool.SINGLE_QUOTE + ", status='" + this.status + CharPool.SINGLE_QUOTE + ", success=" + this.success + '}';
    }
}
