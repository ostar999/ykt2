package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class AdmissionBrochureBean {
    private String code;
    private List<AdmissionBrochureData> data;
    private String message;

    public class AdmissionBrochureData {
        private String content;
        private String id;
        private String school_id;
        private String title;
        private String type;
        private String view_count;

        public AdmissionBrochureData() {
        }

        public String getContent() {
            return this.content;
        }

        public String getId() {
            return this.id;
        }

        public String getSchool_id() {
            return this.school_id;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public String getView_count() {
            return this.view_count;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }
    }

    public String getCode() {
        return this.code;
    }

    public List<AdmissionBrochureData> getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(List<AdmissionBrochureData> data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
