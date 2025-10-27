package com.psychiatrygarden.activity.forum.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class ForumSectionIndexBean implements Serializable {
    public String code;
    public List<DataBean> data;
    public String message;

    public static class DataBean implements Serializable {
        private String id;
        private String name;
        private int selected = 0;

        public String getId() {
            return this.id;
        }

        public String getName() {
            return this.name;
        }

        public int getSelected() {
            return this.selected;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setSelected(int selected) {
            this.selected = selected;
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

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
