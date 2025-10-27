package com.yddmi.doctor.entity.request;

import java.util.List;

/* loaded from: classes6.dex */
public class ShengYunSetsBean {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean {
        private String activity_id;
        private String author;
        private String author_id;
        private String buy;
        private String cid;
        private String cover_img;
        private String create_time;
        private String description;
        private String exam_id;
        private String id;
        private String is_collected;
        private String is_english;
        private String is_show_number;
        private String label;
        private String question_file;
        private String require_interface;
        private String series;
        private String show_restore_img;
        private String title;
        private String type;
        private String update_time;

        public String getActivity_id() {
            return this.activity_id;
        }

        public String getAuthor() {
            return this.author;
        }

        public String getAuthor_id() {
            return this.author_id;
        }

        public String getBuy() {
            return this.buy;
        }

        public String getCid() {
            return this.cid;
        }

        public String getCover_img() {
            return this.cover_img;
        }

        public String getCreate_time() {
            return this.create_time;
        }

        public String getDescription() {
            return this.description;
        }

        public String getExam_id() {
            return this.exam_id;
        }

        public String getId() {
            return this.id;
        }

        public String getIs_collected() {
            return this.is_collected;
        }

        public String getIs_english() {
            return this.is_english;
        }

        public String getIs_show_number() {
            return this.is_show_number;
        }

        public String getLabel() {
            return this.label;
        }

        public String getQuestion_file() {
            return this.question_file;
        }

        public String getRequire_interface() {
            return this.require_interface;
        }

        public String getSeries() {
            return this.series;
        }

        public String getShow_restore_img() {
            return this.show_restore_img;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public String getUpdate_time() {
            return this.update_time;
        }

        public void setActivity_id(String str) {
            this.activity_id = str;
        }

        public void setAuthor(String str) {
            this.author = str;
        }

        public void setAuthor_id(String str) {
            this.author_id = str;
        }

        public void setBuy(String str) {
            this.buy = str;
        }

        public void setCid(String str) {
            this.cid = str;
        }

        public void setCover_img(String str) {
            this.cover_img = str;
        }

        public void setCreate_time(String str) {
            this.create_time = str;
        }

        public void setDescription(String str) {
            this.description = str;
        }

        public void setExam_id(String str) {
            this.exam_id = str;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setIs_collected(String str) {
            this.is_collected = str;
        }

        public void setIs_english(String str) {
            this.is_english = str;
        }

        public void setIs_show_number(String str) {
            this.is_show_number = str;
        }

        public void setLabel(String str) {
            this.label = str;
        }

        public void setQuestion_file(String str) {
            this.question_file = str;
        }

        public void setRequire_interface(String str) {
            this.require_interface = str;
        }

        public void setSeries(String str) {
            this.series = str;
        }

        public void setShow_restore_img(String str) {
            this.show_restore_img = str;
        }

        public void setTitle(String str) {
            this.title = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public void setUpdate_time(String str) {
            this.update_time = str;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public void setData(List<DataBean> list) {
        this.data = list;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setServer_time(String str) {
        this.server_time = str;
    }
}
