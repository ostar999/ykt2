package com.psychiatrygarden.bean;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionSearchBean {
    private String code;
    private List<DataDTO> data;
    private String message;
    private String server_time;

    public static class DataDTO {
        private String app_id;
        private String comment_count;
        private String id;
        private String number;
        private String question_type_name;
        private String title;
        private String year;
        private List<OptionDTO> option = new ArrayList();
        private String type = "";
        private String subject_title = "";

        public static class OptionDTO {
            private String img;
            private String key;
            private String title;

            public String getImg() {
                return this.img;
            }

            public String getKey() {
                return this.key;
            }

            public String getTitle() {
                return this.title;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public String getApp_id() {
            return this.app_id;
        }

        public String getComment_count() {
            return this.comment_count;
        }

        public String getId() {
            return this.id;
        }

        public String getNumber() {
            return this.number;
        }

        public List<OptionDTO> getOption() {
            return this.option;
        }

        public String getQuestion_type_name() {
            return this.question_type_name;
        }

        public String getSubject_title() {
            return this.subject_title;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public String getYear() {
            return this.year;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setComment_count(String comment_count) {
            this.comment_count = comment_count;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public void setOption(List<OptionDTO> option) {
            this.option = option;
        }

        public void setQuestion_type_name(String question_type_name) {
            this.question_type_name = question_type_name;
        }

        public void setSubject_title(String subject_title) {
            this.subject_title = subject_title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataDTO> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataDTO> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }
}
