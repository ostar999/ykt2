package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class SchoolDetailsBean {
    private String code;
    private SchoolDetailData data;
    private String message;

    public class SchoolDetailData {
        private String address;
        private String admission_website;
        private List<String> attr;
        private String city_id;
        private String city_title;
        private String code;
        private String cover;
        private String desc;
        private String email;
        private String follow_count;
        private String follow_status;
        private String last_count;
        private String major_count;
        private List<EnrollmentData> major_list;
        private String official_website;
        private String postcode;
        private String recent_7days_follow;
        private String school_id;
        private String student_count;
        private String title;
        private String view_count;

        public SchoolDetailData() {
        }

        public String getAddress() {
            return this.address;
        }

        public String getAdmission_website() {
            return this.admission_website;
        }

        public List<String> getAttr() {
            return this.attr;
        }

        public String getCity_id() {
            return this.city_id;
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

        public String getDesc() {
            return this.desc;
        }

        public String getEmail() {
            return this.email;
        }

        public String getFollow_count() {
            return this.follow_count;
        }

        public String getFollow_status() {
            return this.follow_status;
        }

        public String getLast_count() {
            return this.last_count;
        }

        public String getMajor_count() {
            return this.major_count;
        }

        public List<EnrollmentData> getMajor_list() {
            return this.major_list;
        }

        public String getOfficial_website() {
            return this.official_website;
        }

        public String getPostcode() {
            return this.postcode;
        }

        public String getRecent_7days_follow() {
            return this.recent_7days_follow;
        }

        public String getSchool_id() {
            return this.school_id;
        }

        public String getStudent_count() {
            return this.student_count;
        }

        public String getTitle() {
            return this.title;
        }

        public String getView_count() {
            return this.view_count;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setAdmission_website(String admission_website) {
            this.admission_website = admission_website;
        }

        public void setAttr(List<String> attr) {
            this.attr = attr;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
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

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setFollow_count(String follow_count) {
            this.follow_count = follow_count;
        }

        public void setFollow_status(String follow_status) {
            this.follow_status = follow_status;
        }

        public void setLast_count(String last_count) {
            this.last_count = last_count;
        }

        public void setMajor_count(String major_count) {
            this.major_count = major_count;
        }

        public void setMajor_list(List<EnrollmentData> major_list) {
            this.major_list = major_list;
        }

        public void setOfficial_website(String official_website) {
            this.official_website = official_website;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public void setRecent_7days_follow(String recent_7days_follow) {
            this.recent_7days_follow = recent_7days_follow;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
        }

        public void setStudent_count(String student_count) {
            this.student_count = student_count;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setView_count(String view_count) {
            this.view_count = view_count;
        }
    }

    public String getCode() {
        return this.code;
    }

    public SchoolDetailData getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(SchoolDetailData data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
