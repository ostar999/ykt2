package com.psychiatrygarden.activity.purchase.beans;

import java.util.List;

/* loaded from: classes5.dex */
public class GoodsOrderDetail {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean {
        private String ctime;
        private String express;
        private String express_address;
        private String express_fee;
        private String express_mobile;
        private String express_name;
        private String express_no;
        private String express_postcode;
        private String express_receiver;
        private List<DataBanGoods> goods;
        private String leave_message;
        private String order_no;
        private String status_str;
        private String total_amount;

        public class DataBanGoods {
            private String goods_description;
            private String goods_name;
            private String goods_thumbnail;
            private String goods_type;
            private String price;
            private String quantity;

            public DataBanGoods() {
            }

            public String getGoods_description() {
                return this.goods_description;
            }

            public String getGoods_name() {
                return this.goods_name;
            }

            public String getGoods_thumbnail() {
                return this.goods_thumbnail;
            }

            public String getGoods_type() {
                return this.goods_type;
            }

            public String getPrice() {
                return this.price;
            }

            public String getQuantity() {
                return this.quantity;
            }

            public void setGoods_description(String goods_description) {
                this.goods_description = goods_description;
            }

            public void setGoods_name(String goods_name) {
                this.goods_name = goods_name;
            }

            public void setGoods_thumbnail(String goods_thumbnail) {
                this.goods_thumbnail = goods_thumbnail;
            }

            public void setGoods_type(String goods_type) {
                this.goods_type = goods_type;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public void setQuantity(String quantity) {
                this.quantity = quantity;
            }
        }

        public String getCtime() {
            return this.ctime;
        }

        public String getExpress() {
            return this.express;
        }

        public String getExpress_address() {
            return this.express_address;
        }

        public String getExpress_fee() {
            return this.express_fee;
        }

        public String getExpress_mobile() {
            return this.express_mobile;
        }

        public String getExpress_name() {
            return this.express_name;
        }

        public String getExpress_no() {
            return this.express_no;
        }

        public String getExpress_postcode() {
            return this.express_postcode;
        }

        public String getExpress_receiver() {
            return this.express_receiver;
        }

        public List<DataBanGoods> getGoods() {
            return this.goods;
        }

        public String getLeave_message() {
            return this.leave_message;
        }

        public String getOrder_no() {
            return this.order_no;
        }

        public String getStatus_str() {
            return this.status_str;
        }

        public String getTotal_amount() {
            return this.total_amount;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setExpress(String express) {
            this.express = express;
        }

        public void setExpress_address(String express_address) {
            this.express_address = express_address;
        }

        public void setExpress_fee(String express_fee) {
            this.express_fee = express_fee;
        }

        public void setExpress_mobile(String express_mobile) {
            this.express_mobile = express_mobile;
        }

        public void setExpress_name(String express_name) {
            this.express_name = express_name;
        }

        public void setExpress_no(String express_no) {
            this.express_no = express_no;
        }

        public void setExpress_postcode(String express_postcode) {
            this.express_postcode = express_postcode;
        }

        public void setExpress_receiver(String express_receiver) {
            this.express_receiver = express_receiver;
        }

        public void setGoods(List<DataBanGoods> goods) {
            this.goods = goods;
        }

        public void setLeave_message(String leave_message) {
            this.leave_message = leave_message;
        }

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public void setStatus_str(String status_str) {
            this.status_str = status_str;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
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
