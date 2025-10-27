package com.psychiatrygarden.activity.purchase.beans;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class CreateOrderBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean {
        private Object order_no;
        private Sign sign;
        private String status;

        public Object getOrder_no() {
            return this.order_no;
        }

        public Sign getSign() {
            return this.sign;
        }

        public String getStatus() {
            return this.status;
        }

        public void setOrder_no(Object order_no) {
            this.order_no = order_no;
        }

        public void setSign(Sign sign) {
            this.sign = sign;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class Sign {
        private String appId;
        private String nonceStr;
        private String packageValue;
        private String partnerId;
        private String prepayId;
        private String sign;
        private String timeStamp;

        public String getAppId() {
            return this.appId;
        }

        public String getNonceStr() {
            return this.nonceStr;
        }

        public String getPackageValue() {
            return this.packageValue;
        }

        public String getPartnerId() {
            return this.partnerId;
        }

        public String getPrepayId() {
            return this.prepayId;
        }

        public String getSign() {
            return this.sign;
        }

        public String getTimeStamp() {
            return this.timeStamp;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public void setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
        }

        public void setPackageValue(String packageValue) {
            this.packageValue = packageValue;
        }

        public void setPartnerId(String partnerId) {
            this.partnerId = partnerId;
        }

        public void setPrepayId(String prepayId) {
            this.prepayId = prepayId;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
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
}
