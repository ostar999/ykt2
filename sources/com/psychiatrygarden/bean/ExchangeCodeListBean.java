package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class ExchangeCodeListBean implements Serializable {
    private int code;
    private List<DataBean> data;
    private String message;
    private int server_time;

    public static class DataBean implements Serializable {
        private String cat;
        private String label;

        public String getCat() {
            return this.cat;
        }

        public String getLabel() {
            return this.label;
        }

        public void setCat(String cat) {
            this.cat = cat;
        }

        public void setLabel(String label) {
            this.label = label;
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
