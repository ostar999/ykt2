package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class CourseList2Bean implements Serializable {
    private int code;
    private List<DataBean> data;
    private String message;
    private int server_time;

    public static class DataBean implements Serializable {
        private int c_id;
        private List<DataChildBean> items;
        private String label;
        private int more;

        public static class DataChildBean implements Serializable {
            public String activity_id;
            public String buy;
            public String cover_img;
            public String create_time;
            public String description;
            public String id = "";
            public String is_collected = "0";
            public String label;
            public String require_interface;
            public String series;
            public String title;
            public String update_time;

            public String getActivity_id() {
                return this.activity_id;
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

            public String getId() {
                return this.id;
            }

            public String getIs_collected() {
                return this.is_collected;
            }

            public String getLabel() {
                return this.label;
            }

            public String getRequire_interface() {
                return this.require_interface;
            }

            public String getSeries() {
                return this.series;
            }

            public String getTitle() {
                return this.title;
            }

            public String getUpdate_time() {
                return this.update_time;
            }

            public void setActivity_id(String activity_id) {
                this.activity_id = activity_id;
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

            public void setId(String id) {
                this.id = id;
            }

            public void setIs_collected(String is_collected) {
                this.is_collected = is_collected;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public void setRequire_interface(String require_interface) {
                this.require_interface = require_interface;
            }

            public void setSeries(String series) {
                this.series = series;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setUpdate_time(String update_time) {
                this.update_time = update_time;
            }
        }

        public int getC_id() {
            return this.c_id;
        }

        public List<DataChildBean> getItems() {
            return this.items;
        }

        public String getLabel() {
            return this.label;
        }

        public int getMore() {
            return this.more;
        }

        public void setC_id(int c_id) {
            this.c_id = c_id;
        }

        public void setItems(List<DataChildBean> items) {
            this.items = items;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setMore(int more) {
            this.more = more;
        }
    }

    public int getCode() {
        return this.code;
    }

    public List<DataBean> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public int getServer_time() {
        return this.server_time;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(int server_time) {
        this.server_time = server_time;
    }
}
