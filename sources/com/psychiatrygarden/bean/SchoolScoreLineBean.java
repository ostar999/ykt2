package com.psychiatrygarden.bean;

import java.util.List;

/* loaded from: classes5.dex */
public class SchoolScoreLineBean {
    private String code;
    private SchoolScoreLineData data;
    private String message;

    public class SchoolScoreLineData {
        private String activity_id;
        private List<String> attr;
        private String city_title;
        private String code;
        private String cover;
        private String follow_status;
        private String is_pass;
        private List<EnrollmentData> list;
        private String major_code;
        private String major_title;
        private String major_type;
        private List<EnrollmentData> school_cutoff_score;
        private List<EnrollmentData> school_department_cutoff_score;
        private String school_department_is_more;
        private String school_is_more;
        private String school_title;
        private String title;

        public SchoolScoreLineData() {
        }

        public String getActivity_id() {
            return this.activity_id;
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

        public String getFollow_status() {
            return this.follow_status;
        }

        public String getIs_pass() {
            return this.is_pass;
        }

        public List<EnrollmentData> getList() {
            return this.list;
        }

        public String getMajor_code() {
            return this.major_code;
        }

        public String getMajor_title() {
            return this.major_title;
        }

        public String getMajor_type() {
            return this.major_type;
        }

        public List<EnrollmentData> getSchool_cutoff_score() {
            return this.school_cutoff_score;
        }

        public List<EnrollmentData> getSchool_department_cutoff_score() {
            return this.school_department_cutoff_score;
        }

        public String getSchool_department_is_more() {
            return this.school_department_is_more;
        }

        public String getSchool_is_more() {
            return this.school_is_more;
        }

        public String getSchool_title() {
            return this.school_title;
        }

        public String getTitle() {
            return this.title;
        }

        public void setActivity_id(String activity_id) {
            this.activity_id = activity_id;
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

        public void setFollow_status(String follow_status) {
            this.follow_status = follow_status;
        }

        public void setIs_pass(String is_pass) {
            this.is_pass = is_pass;
        }

        public void setList(List<EnrollmentData> list) {
            this.list = list;
        }

        public void setMajor_code(String major_code) {
            this.major_code = major_code;
        }

        public void setMajor_title(String major_title) {
            this.major_title = major_title;
        }

        public void setMajor_type(String major_type) {
            this.major_type = major_type;
        }

        public void setSchool_cutoff_score(List<EnrollmentData> school_cutoff_score) {
            this.school_cutoff_score = school_cutoff_score;
        }

        public void setSchool_department_cutoff_score(List<EnrollmentData> school_department_cutoff_score) {
            this.school_department_cutoff_score = school_department_cutoff_score;
        }

        public void setSchool_department_is_more(String school_department_is_more) {
            this.school_department_is_more = school_department_is_more;
        }

        public void setSchool_is_more(String school_is_more) {
            this.school_is_more = school_is_more;
        }

        public void setSchool_title(String school_title) {
            this.school_title = school_title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public String getCode() {
        return this.code;
    }

    public SchoolScoreLineData getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(SchoolScoreLineData data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
