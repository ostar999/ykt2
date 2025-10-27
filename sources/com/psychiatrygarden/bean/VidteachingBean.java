package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class VidteachingBean implements Serializable {
    private String code;
    private List<DataDTO> data;
    private String message;
    private String server_time;

    public static class DataDTO implements Serializable {
        private String activity_id;
        private String buy;
        private String cover_img;
        private String create_time;
        private String description;
        private String id;
        private String label;
        private String require_interface;
        private String series;
        private String title;
        private String update_time;

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
