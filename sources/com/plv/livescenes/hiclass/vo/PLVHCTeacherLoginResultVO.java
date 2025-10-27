package com.plv.livescenes.hiclass.vo;

import com.google.gson.JsonElement;
import com.plv.thirdpart.blankj.utilcode.util.ImageUtils;
import java.util.List;

/* loaded from: classes4.dex */
public class PLVHCTeacherLoginResultVO {
    private Integer code;
    private JsonElement data;
    private ErrorBody error;
    private String requestId;
    private String status;
    private Boolean success;

    public static class CompanyVO {
        private String company;
        private Integer count;
        private String userId;

        public String getCompany() {
            return this.company;
        }

        public Integer getCount() {
            return this.count;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setCompany(String str) {
            this.company = str;
        }

        public void setCount(Integer num) {
            this.count = num;
        }

        public void setUserId(String str) {
            this.userId = str;
        }
    }

    public static class DataVO {
        private List<LessonVO> lessonList;
        private String nickname;
        private String token;
        private String viewerId;

        public static class LessonVO {
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

        public List<LessonVO> getLessonList() {
            return this.lessonList;
        }

        public String getNickname() {
            return this.nickname;
        }

        public String getToken() {
            return this.token;
        }

        public String getViewerId() {
            return this.viewerId;
        }

        public void setLessonList(List<LessonVO> list) {
            this.lessonList = list;
        }

        public void setNickname(String str) {
            this.nickname = str;
        }

        public void setToken(String str) {
            this.token = str;
        }

        public void setViewerId(String str) {
            this.viewerId = str;
        }
    }

    public static class ErrorBody {
        public static final int ERROR_NO_LESSON_IN_CLASS = 20027;
        public static final int ERROR_TEACHER_COMPANY_MORE_THAN_ONE = 20028;
        public static final int ERROR_TEACHER_NOT_EXIST = 2000;
        public static final int ERROR_WRONG_PASSWORD = 20033;
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

    public JsonElement getData() {
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

    public void setData(JsonElement jsonElement) {
        this.data = jsonElement;
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
