package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class SortValueBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;
    private String time;

    public static class DataBean implements Serializable {
        private String isSelect = "0";
        private String sort_type;
        private String title;

        public String getIsSelect() {
            return this.isSelect;
        }

        public String getSort_type() {
            return this.sort_type;
        }

        public String getTitle() {
            return this.title;
        }

        public void setIsSelect(String isSelect) {
            this.isSelect = isSelect;
        }

        public void setSort_type(String sort_type) {
            this.sort_type = sort_type;
        }

        public void setTitle(String title) {
            this.title = title;
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

    public String getTime() {
        return this.time;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
