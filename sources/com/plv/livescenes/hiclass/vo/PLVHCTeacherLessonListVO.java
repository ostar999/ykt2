package com.plv.livescenes.hiclass.vo;

import com.plv.thirdpart.blankj.utilcode.util.ImageUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVHCTeacherLessonListVO {
    private Integer code;
    private List<DataVO> data;
    private ErrorBody error;
    private String requestId;
    private String status;
    private Boolean success;

    public static class DataVO {
        public static final int STATUS_LESSON_ON_GOING = 1;
        public static final int STATUS_LESSON_OVER = 2;
        public static final int STATUS_LESSON_WAITING = 0;
        private String channelId;
        private String courseNames;
        private String cover;
        private String date;
        private String lessonCode;
        private Long lessonId;
        private String name;
        private String startTime;
        private Integer status;
        private String time;

        public String getChannelId() {
            return this.channelId;
        }

        public String getCourseNames() {
            return this.courseNames;
        }

        public String getCover() {
            return ImageUtils.fixImageUrl(this.cover);
        }

        public String getDate() {
            return this.date;
        }

        public String getLessonCode() {
            return this.lessonCode;
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

        public String getTime() {
            return this.time;
        }

        public void setChannelId(String str) {
            this.channelId = str;
        }

        public void setCourseNames(String str) {
            this.courseNames = str;
        }

        public void setCover(String str) {
            this.cover = str;
        }

        public void setDate(String str) {
            this.date = str;
        }

        public void setLessonCode(String str) {
            this.lessonCode = str;
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

        public void setTime(String str) {
            this.time = str;
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
}
