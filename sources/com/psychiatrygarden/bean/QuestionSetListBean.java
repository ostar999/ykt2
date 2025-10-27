package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class QuestionSetListBean implements Serializable {
    private int code;
    private List<DataBeanX> data;
    private String message;

    public static class DataBeanX {
        private String c_id;
        private List<DataBean> data;
        private String label;
        private String more;
        private String type;

        public static class DataBean {
            private String activity_id;
            private String buy;
            private String cover_img;
            private String create_time;
            private String description;
            private String exam_id;
            private String id;
            private String is_live_broadcast;
            private String is_show_number;
            private String label;
            private String question_file;
            private String require_interface;
            private String series;
            private String title;
            private String type;
            private String update_time;
            private String show_restore_img = "1";
            private String is_collected = "0";
            private String author_id = "0";
            private String author = "";

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

            public String getIs_live_broadcast() {
                return this.is_live_broadcast;
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

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public void setAuthor_id(String author_id) {
                this.author_id = author_id;
            }

            public void setBuy(String buy) {
                this.buy = buy;
            }

            public void setCover_img(String cover_img) {
                this.cover_img = cover_img;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public void setExam_id(String exam_id) {
                this.exam_id = exam_id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setIs_collected(String is_collected) {
                this.is_collected = is_collected;
            }

            public void setIs_live_broadcast(String is_live_broadcast) {
                this.is_live_broadcast = is_live_broadcast;
            }

            public void setIs_show_number(String is_show_number) {
                this.is_show_number = is_show_number;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public void setQuestion_file(String question_file) {
                this.question_file = question_file;
            }

            public void setRequire_interface(String require_interface) {
                this.require_interface = require_interface;
            }

            public void setSeries(String series) {
                this.series = series;
            }

            public DataBean setShow_restore_img(String show_restore_img) {
                this.show_restore_img = show_restore_img;
                return this;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }
        }

        public String getC_id() {
            return this.c_id;
        }

        public List<DataBean> getData() {
            return this.data;
        }

        public String getLabel() {
            return this.label;
        }

        public String getMore() {
            return this.more;
        }

        public String getType() {
            return this.type;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setMore(String more) {
            this.more = more;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public int getCode() {
        return this.code;
    }

    public List<DataBeanX> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataBeanX> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
