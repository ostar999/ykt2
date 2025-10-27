package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class SchoolDepartmentBean {
    private String code;
    private SchoolDepartmentData data;
    private String message;

    public class DepartmentData {
        private String id;
        private String major_count;
        private String title;

        public DepartmentData() {
        }

        public String getId() {
            return this.id;
        }

        public String getMajor_count() {
            return this.major_count;
        }

        public String getTitle() {
            return this.title;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setMajor_count(String major_count) {
            this.major_count = major_count;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public class SchoolDepartmentData {
        private List<String> attr;
        private String code;
        private String cover;
        private List<DepartmentData> department;
        private String location;
        private String title;

        public SchoolDepartmentData() {
        }

        public List<String> getAttr() {
            return this.attr;
        }

        public String getCode() {
            return this.code;
        }

        public String getCover() {
            return this.cover;
        }

        public List<DepartmentData> getDepartment() {
            return this.department;
        }

        public String getLocation() {
            return this.location;
        }

        public String getTitle() {
            return this.title;
        }

        public void setAttr(List<String> attr) {
            this.attr = attr;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public void setDepartment(List<DepartmentData> department) {
            this.department = department;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public String getCode() {
        return this.code;
    }

    public SchoolDepartmentData getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(SchoolDepartmentData data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
