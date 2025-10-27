package com.psychiatrygarden.activity.RegisterBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes5.dex */
public class EducationPublicBean implements Serializable {
    public String code;
    public List<DataBean> data = new ArrayList();
    public String message;

    public static class DataBean implements Serializable {
        private String id;
        public String label;
        private String show;
        private String title;
        public String value;

        public String getId() {
            return this.id;
        }

        public String getLabel() {
            return this.label;
        }

        public String getShow() {
            return this.show;
        }

        public String getTitle() {
            return this.title;
        }

        public String getValue() {
            return this.value;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public void setShow(String show) {
            this.show = show;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setValue(String value) {
            this.value = value;
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
