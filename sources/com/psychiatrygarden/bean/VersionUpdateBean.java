package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes5.dex */
public class VersionUpdateBean {
    private String code;
    private VersionBean data;
    private String message;

    public static class VersionBean {

        @SerializedName("app_url")
        private String appUrl;
        private String code;

        @SerializedName("is_force")
        private String isForce;
        private String message;
        private String is_open = "0";
        private String version = "";

        public String getAppUrl() {
            return this.appUrl;
        }

        public String getCode() {
            return this.code;
        }

        public String getIsForce() {
            return this.isForce;
        }

        public String getIs_open() {
            return this.is_open;
        }

        public String getMessage() {
            return this.message;
        }

        public String getVersion() {
            return this.version;
        }

        public void setAppUrl(String appUrl) {
            this.appUrl = appUrl;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public void setIsForce(String isForce) {
            this.isForce = isForce;
        }

        public void setIs_open(String is_open) {
            this.is_open = is_open;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public String getCode() {
        return this.code;
    }

    public VersionBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(VersionBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
