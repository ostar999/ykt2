package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class SchoolEnrollmentBean {
    private String code;
    private SchoolEnrollmentData data;
    private String message;

    public class SchoolEnrollmentData {
        private List<String> attr;
        private String city_title;
        private String code;
        private String cover;
        private String describe;
        private List<EnrollmentData> result;
        private String title;

        public SchoolEnrollmentData() {
        }

        public List<String> getAttr() {
            return this.attr;
        }

        public String getCity_title() {
            return this.city_title;
        }

        public String getCode() {
            return this.code;
        }

        public String getCover() {
            return this.cover;
        }

        public String getDescribe() {
            return this.describe;
        }

        public List<EnrollmentData> getResult() {
            return this.result;
        }

        public String getTitle() {
            return this.title;
        }

        public void setAttr(List<String> attr) {
            this.attr = attr;
        }

        public void setCity_title(String city_title) {
            this.city_title = city_title;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public void setResult(List<EnrollmentData> result) {
            this.result = result;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public String getCode() {
        return this.code;
    }

    public SchoolEnrollmentData getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(SchoolEnrollmentData data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
