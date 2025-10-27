package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class RedEnvelopeCouponsBean implements Serializable {
    private String code;
    private RedEnvelopeCouponsDataBean data;
    private String message;

    public class CouponsJumpBean implements Serializable {
        private String apply_range;
        private String apply_type;
        private String file_url;
        private String obj_id;
        private String status;
        private String toast;

        public CouponsJumpBean() {
        }

        public String getApply_range() {
            return this.apply_range;
        }

        public String getApply_type() {
            return this.apply_type;
        }

        public String getFile_url() {
            return this.file_url;
        }

        public String getObj_id() {
            return this.obj_id;
        }

        public String getStatus() {
            return this.status;
        }

        public String getToast() {
            return this.toast;
        }

        public void setApply_range(String apply_range) {
            this.apply_range = apply_range;
        }

        public void setApply_type(String apply_type) {
            this.apply_type = apply_type;
        }

        public void setFile_url(String file_url) {
            this.file_url = file_url;
        }

        public void setObj_id(String obj_id) {
            this.obj_id = obj_id;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setToast(String toast) {
            this.toast = toast;
        }
    }

    public class RedEnvelopeCouponsDataBean implements Serializable {
        private List<RedEnvelopeCouponsDataItem> items;
        private String more;

        public RedEnvelopeCouponsDataBean() {
        }

        public List<RedEnvelopeCouponsDataItem> getItems() {
            return this.items;
        }

        public String getMore() {
            return this.more;
        }

        public void setItems(List<RedEnvelopeCouponsDataItem> items) {
            this.items = items;
        }

        public void setMore(String more) {
            this.more = more;
        }
    }

    public static class RedEnvelopeCouponsDataItem implements Serializable {
        private String apply_range;
        private String apply_type;
        private String coupon_code;
        private String coupon_end;
        private String coupon_id;
        private String coupon_no;
        private String coupon_start;
        private String coupon_type;
        private String ctime;
        private String desc;
        private String describe;
        private String discount;
        private String end_time;
        private String expire_day;
        private String expire_type;
        private String id;
        private boolean isCheck = false;
        private boolean isExpand = false;
        private String is_receive;
        private CouponsJumpBean jump;
        private String obj_id;
        private String price;
        private String reason;
        private String record_id;
        private String red_packet_code;
        private String red_packet_id;
        private String start_time;
        private String status;
        private String threshold_price;
        private String title;
        private String type;
        private String use_type;

        public String getApply_range() {
            return this.apply_range;
        }

        public String getApply_type() {
            return this.apply_type;
        }

        public String getCoupon_code() {
            return this.coupon_code;
        }

        public String getCoupon_end() {
            return this.coupon_end;
        }

        public String getCoupon_id() {
            return this.coupon_id;
        }

        public String getCoupon_no() {
            return this.coupon_no;
        }

        public String getCoupon_start() {
            return this.coupon_start;
        }

        public String getCoupon_type() {
            return this.coupon_type;
        }

        public String getCtime() {
            return this.ctime;
        }

        public String getDesc() {
            return this.desc;
        }

        public String getDescribe() {
            return this.describe;
        }

        public String getDiscount() {
            return this.discount;
        }

        public String getEnd_time() {
            return this.end_time;
        }

        public String getExpire_day() {
            return this.expire_day;
        }

        public String getExpire_type() {
            return this.expire_type;
        }

        public String getId() {
            return this.id;
        }

        public String getIs_receive() {
            return this.is_receive;
        }

        public CouponsJumpBean getJump() {
            return this.jump;
        }

        public String getObj_id() {
            return this.obj_id;
        }

        public String getPrice() {
            return this.price;
        }

        public String getReason() {
            return this.reason;
        }

        public String getRecord_id() {
            return this.record_id;
        }

        public String getRed_packet_code() {
            return this.red_packet_code;
        }

        public String getRed_packet_id() {
            return this.red_packet_id;
        }

        public String getStart_time() {
            return this.start_time;
        }

        public String getStatus() {
            return this.status;
        }

        public String getThreshold_price() {
            return this.threshold_price;
        }

        public String getTitle() {
            return this.title;
        }

        public String getType() {
            return this.type;
        }

        public String getUse_type() {
            return this.use_type;
        }

        public boolean isCheck() {
            return this.isCheck;
        }

        public boolean isExpand() {
            return this.isExpand;
        }

        public void setApply_range(String apply_range) {
            this.apply_range = apply_range;
        }

        public void setApply_type(String apply_type) {
            this.apply_type = apply_type;
        }

        public void setCheck(boolean check) {
            this.isCheck = check;
        }

        public void setCoupon_code(String coupon_code) {
            this.coupon_code = coupon_code;
        }

        public void setCoupon_end(String coupon_end) {
            this.coupon_end = coupon_end;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
        }

        public void setCoupon_no(String coupon_no) {
            this.coupon_no = coupon_no;
        }

        public void setCoupon_start(String coupon_start) {
            this.coupon_start = coupon_start;
        }

        public void setCoupon_type(String coupon_type) {
            this.coupon_type = coupon_type;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setDescribe(String describe) {
            this.describe = describe;
        }

        public void setDiscount(String discount) {
            this.discount = discount;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public void setExpand(boolean expand) {
            this.isExpand = expand;
        }

        public void setExpire_day(String expire_day) {
            this.expire_day = expire_day;
        }

        public void setExpire_type(String expire_type) {
            this.expire_type = expire_type;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setIs_receive(String is_receive) {
            this.is_receive = is_receive;
        }

        public void setJump(CouponsJumpBean jump) {
            this.jump = jump;
        }

        public void setObj_id(String obj_id) {
            this.obj_id = obj_id;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public void setRecord_id(String record_id) {
            this.record_id = record_id;
        }

        public void setRed_packet_code(String red_packet_code) {
            this.red_packet_code = red_packet_code;
        }

        public void setRed_packet_id(String red_packet_id) {
            this.red_packet_id = red_packet_id;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setThreshold_price(String threshold_price) {
            this.threshold_price = threshold_price;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setUse_type(String use_type) {
            this.use_type = use_type;
        }
    }

    public String getCode() {
        return this.code;
    }

    public RedEnvelopeCouponsDataBean getData() {
        return this.data;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setData(RedEnvelopeCouponsDataBean data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
