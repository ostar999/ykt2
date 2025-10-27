package com.psychiatrygarden.activity.RegisterBean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class RegisterDepartmentBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String department_id;
        private String have;
        private String title;

        public String getDepartment_id() {
            return this.department_id;
        }

        public String getHave() {
            return this.have;
        }

        public String getTitle() {
            return this.title;
        }

        public void setDepartment_id(String department_id) {
            this.department_id = department_id;
        }

        public void setHave(String have) {
            this.have = have;
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
