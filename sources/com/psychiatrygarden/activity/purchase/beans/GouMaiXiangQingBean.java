package com.psychiatrygarden.activity.purchase.beans;

import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import com.psychiatrygarden.activity.purchase.beans.ShowAddressBean;
import com.psychiatrygarden.bean.OnlineServiceBean;
import java.io.Serializable;
import java.util.List;

/* loaded from: classes5.dex */
public class GouMaiXiangQingBean implements Serializable {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean implements Serializable {
        private String article_id;
        private List<ButtonBean> button;
        private String ctime;
        private GoodsBean.DataBean.CoumService customer_service;
        private String express;
        private String express_code;
        private ShowAddressBean.DataBean express_detail;
        private String express_id;
        private String express_no;
        private String express_query_url;
        private String goods_id;
        private String goods_name;
        private String goods_thumbnail;
        private String goods_type;
        private String order_no;
        private String original_ctime;
        private String pending_payment_ctime;
        private String price;
        private String quantity;
        private String status;
        private String total_amount;
        private OnlineServiceBean cs = new OnlineServiceBean();
        private String wechat_corpid = "";
        private String wechat_enterprise_url = "";

        public static class ButtonBean implements Serializable {
            private String border_color;
            private String title;
            private String type;
            private String word_color;

            public String getBorder_color() {
                return this.border_color;
            }

            public String getTitle() {
                return this.title;
            }

            public String getType() {
                return this.type;
            }

            public String getWord_color() {
                return this.word_color;
            }

            public void setBorder_color(String border_color) {
                this.border_color = border_color;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setWord_color(String word_color) {
                this.word_color = word_color;
            }
        }

        public String getArticle_id() {
            return this.article_id;
        }

        public List<ButtonBean> getButton() {
            return this.button;
        }

        public OnlineServiceBean getCs() {
            return this.cs;
        }

        public String getCtime() {
            return this.ctime;
        }

        public GoodsBean.DataBean.CoumService getCustomer_service() {
            return this.customer_service;
        }

        public String getExpress() {
            return this.express;
        }

        public String getExpress_code() {
            return this.express_code;
        }

        public ShowAddressBean.DataBean getExpress_detail() {
            return this.express_detail;
        }

        public String getExpress_id() {
            return this.express_id;
        }

        public String getExpress_no() {
            return this.express_no;
        }

        public String getExpress_query_url() {
            return this.express_query_url;
        }

        public String getGoods_id() {
            return this.goods_id;
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

        public String getOrder_no() {
            return this.order_no;
        }

        public String getOriginal_ctime() {
            return this.original_ctime;
        }

        public String getPending_payment_ctime() {
            return this.pending_payment_ctime;
        }

        public String getPrice() {
            return this.price;
        }

        public String getQuantity() {
            return this.quantity;
        }

        public String getStatus() {
            return this.status;
        }

        public String getTotal_amount() {
            return this.total_amount;
        }

        public String getWechat_corpid() {
            return this.wechat_corpid;
        }

        public String getWechat_enterprise_url() {
            return this.wechat_enterprise_url;
        }

        public void setArticle_id(String article_id) {
            this.article_id = article_id;
        }

        public void setButton(List<ButtonBean> button) {
            this.button = button;
        }

        public void setCs(OnlineServiceBean cs) {
            this.cs = cs;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setCustomer_service(GoodsBean.DataBean.CoumService customer_service) {
            this.customer_service = customer_service;
        }

        public void setExpress(String express) {
            this.express = express;
        }

        public void setExpress_code(String express_code) {
            this.express_code = express_code;
        }

        public void setExpress_detail(ShowAddressBean.DataBean express_detail) {
            this.express_detail = express_detail;
        }

        public void setExpress_id(String express_id) {
            this.express_id = express_id;
        }

        public void setExpress_no(String express_no) {
            this.express_no = express_no;
        }

        public void setExpress_query_url(String express_query_url) {
            this.express_query_url = express_query_url;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
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

        public void setOrder_no(String order_no) {
            this.order_no = order_no;
        }

        public void setOriginal_ctime(String original_ctime) {
            this.original_ctime = original_ctime;
        }

        public void setPending_payment_ctime(String pending_payment_ctime) {
            this.pending_payment_ctime = pending_payment_ctime;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public void setWechat_corpid(String wechat_corpid) {
            this.wechat_corpid = wechat_corpid;
        }

        public void setWechat_enterprise_url(String wechat_enterprise_url) {
            this.wechat_enterprise_url = wechat_enterprise_url;
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
