package com.psychiatrygarden.activity.RegisterBean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class RegisterDataBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String area_id;
        private String is_direction;
        private String major_id;
        private String school_department_id;
        private String school_id;
        private String title;

        public String getArea_id() {
            return this.area_id;
        }

        public String getIs_direction() {
            return this.is_direction;
        }

        public String getMajor_id() {
            return this.major_id;
        }

        public String getSchool_department_id() {
            return this.school_department_id;
        }

        public String getSchool_id() {
            return this.school_id;
        }

        public String getTitle() {
            return this.title;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public void setIs_direction(String is_direction) {
            this.is_direction = is_direction;
        }

        public void setMajor_id(String major_id) {
            this.major_id = major_id;
        }

        public void setSchool_department_id(String school_department_id) {
            this.school_department_id = school_department_id;
        }

        public void setSchool_id(String school_id) {
            this.school_id = school_id;
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
