package com.psychiatrygarden.activity.purchase.beans;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class ShowAddressBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String addr_id;
        private String city;
        private String city_title;
        private String express_fee;
        private String express_id;
        private String express_no;
        private String full_address;
        private String id;
        private String is_default;
        private String mobile;
        private String name;
        private String postcode;
        private String province;
        private String province_title;
        private String street;
        private int count = 1;
        private boolean select = false;

        public String getAddr_id() {
            return this.addr_id;
        }

        public String getCity() {
            return this.city;
        }

        public String getCity_title() {
            return this.city_title;
        }

        public int getCount() {
            return this.count;
        }

        public String getExpress_fee() {
            return this.express_fee;
        }

        public String getExpress_id() {
            return this.express_id;
        }

        public String getExpress_no() {
            return this.express_no;
        }

        public String getFull_address() {
            return this.full_address;
        }

        public String getId() {
            return this.id;
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

        public boolean isSelect() {
            return this.select;
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

        public void setCount(int count) {
            this.count = count;
        }

        public void setExpress_fee(String express_fee) {
            this.express_fee = express_fee;
        }

        public void setExpress_id(String express_id) {
            this.express_id = express_id;
        }

        public void setExpress_no(String express_no) {
            this.express_no = express_no;
        }

        public void setFull_address(String full_address) {
            this.full_address = full_address;
        }

        public void setId(String id) {
            this.id = id;
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

        public void setSelect(boolean select) {
            this.select = select;
        }

        public void setStreet(String street) {
            this.street = street;
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
