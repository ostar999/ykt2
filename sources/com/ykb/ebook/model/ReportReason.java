package com.ykb.ebook.model;

import java.util.ArrayList;

/* loaded from: classes7.dex */
public class ReportReason {
    private String code;
    private ArrayList<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean {
        private String id;
        private String title;

        public String getId() {
            return this.id;
        }

        public String getTitle() {
            return this.title;
        }

        public void setId(String str) {
            this.id = str;
        }

        public void setTitle(String str) {
            this.title = str;
        }
    }

    public String getCode() {
        return this.code;
    }

    public ArrayList<DataBean> getData() {
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

    public void setData(ArrayList<DataBean> arrayList) {
        this.data = arrayList;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setServer_time(String str) {
        this.server_time = str;
    }
}
