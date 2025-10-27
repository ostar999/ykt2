package com.psychiatrygarden.activity.purchase.beans;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class AddressBean implements Serializable {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean {
        private String addr_id;
        private String city;
        private String city_title;
        private String is_default;
        private String mobile;
        private String postcode;
        private String province;
        private String province_title;
        private String street;
        private String name = "";
        private String full_address = "";

        public String getAddr_id() {
            return this.addr_id;
        }

        public String getCity() {
            return this.city;
        }

        public String getCity_title() {
            return this.city_title;
        }

        public String getFull_address() {
            return this.full_address;
        }

        public String getIs_default() {
            return this.is_default;
        }

        public String getMobile() {
            return this.mobile;
        }

        public String getName() {
            return this.name;
        }

        public String getPostcode() {
            return this.postcode;
        }

        public String getProvince() {
            return this.province;
        }

        public String getProvince_title() {
            return this.province_title;
        }

        public String getStreet() {
            return this.street;
        }

        public void setAddr_id(String addr_id) {
            this.addr_id = addr_id;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public void setCity_title(String city_title) {
            this.city_title = city_title;
        }

        public void setFull_address(String full_address) {
            this.full_address = full_address;
        }

        public void setIs_default(String is_default) {
            this.is_default = is_default;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public void setProvince_title(String province_title) {
            this.province_title = province_title;
        }

        public void setStreet(String street) {
            this.street = street;
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
