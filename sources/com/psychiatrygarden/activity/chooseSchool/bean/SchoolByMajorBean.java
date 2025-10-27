package com.psychiatrygarden.activity.chooseSchool.bean;

import com.psychiatrygarden.activity.chooseSchool.bean.FollowSchoolBean;
import com.psychiatrygarden.bean.OnlineServiceBean;
import java.util.List;

/* loaded from: classes5.dex */
public class SchoolByMajorBean {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;
    private boolean success;

    public static class DataBean {
        private OnlineServiceBean feedback_cs;
        private String is_follow;
        private List<FollowSchoolBean.DataBean> list;
        private String major_code;
        private String major_id;
        private String major_title;
        private String major_type;

        public OnlineServiceBean getFeedback_cs() {
            return this.feedback_cs;
        }

        public String getIs_follow() {
            return this.is_follow;
        }

        public List<FollowSchoolBean.DataBean> getList() {
            return this.list;
        }

        public String getMajor_code() {
            return this.major_code;
        }

        public String getMajor_id() {
            return this.major_id;
        }

        public String getMajor_title() {
            return this.major_title;
        }

        public String getMajor_type() {
            return this.major_type;
        }

        public void setFeedback_cs(OnlineServiceBean feedback_cs) {
            this.feedback_cs = feedback_cs;
        }

        public void setIs_follow(String is_follow) {
            this.is_follow = is_follow;
        }

        public void setList(List<FollowSchoolBean.DataBean> list) {
            this.list = list;
        }

        public void setMajor_code(String major_code) {
            this.major_code = major_code;
        }

        public void setMajor_id(String major_id) {
            this.major_id = major_id;
        }

        public void setMajor_title(String major_title) {
            this.major_title = major_title;
        }

        public void setMajor_type(String major_type) {
            this.major_type = major_type;
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
