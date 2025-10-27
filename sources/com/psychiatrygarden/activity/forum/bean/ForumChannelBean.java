package com.psychiatrygarden.activity.forum.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class ForumChannelBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String id;
        private int selected = 0;
        private String title;

        public String getId() {
            return this.id;
        }

        public int getSelected() {
            return this.selected;
        }

        public String getTitle() {
            return this.title;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setSelected(int selected) {
            this.selected = selected;
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
