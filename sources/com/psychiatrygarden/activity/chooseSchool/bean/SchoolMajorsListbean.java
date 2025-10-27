package com.psychiatrygarden.activity.chooseSchool.bean;

import com.psychiatrygarden.activity.chooseSchool.bean.FollowMajorsBean;
import java.util.List;

/* loaded from: classes5.dex */
public class SchoolMajorsListbean {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;
    private boolean success;

    public static class DataBean {
        private List<String> attr;
        private String code;
        private String cover;
        private String location;
        private List<FollowMajorsBean.DataBean> majorInfo;
        private String school_id;
        private String title;

        public List<String> getAttr() {
            return this.attr;
        }

        public String getCode() {
            return this.code;
        }

        public String getCover() {
            return this.cover;
        }

        public String getLocation() {
            return this.location;
        }

        public List<FollowMajorsBean.DataBean> getMajorInfo() {
            return this.majorInfo;
        }

        public String getSchool_id() {
            return this.school_id;
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

        public void setLocation(String location) {
            this.location = location;
        }

        public void setMajorInfo(List<FollowMajorsBean.DataBean> majorInfo) {
            this.majorInfo = majorInfo;
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

    public DataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public String getServer_time() {
        return this.server_time;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setServer_time(String server_time) {
        this.server_time = server_time;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
