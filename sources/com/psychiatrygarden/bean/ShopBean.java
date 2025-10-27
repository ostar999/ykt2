package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class ShopBean {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean {
        private String cat_id;
        private String cat_name;

        public String getCat_id() {
            return this.cat_id;
        }

        public String getCat_name() {
            return this.cat_name;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public void setCat_name(String cat_name) {
            this.cat_name = cat_name;
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
}
