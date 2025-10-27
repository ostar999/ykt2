package com.yddmi.doctor.entity.request;

import java.util.List;

/* loaded from: classes6.dex */
public class ShengYunGoodsBean {
    private String code;
    private DataBean data;
    private String message;
    private String server_time;

    public static class DataBean {
        private String after_purchase_goto;
        private String apple_pay_goods_name;
        private String buy_permission;
        private List<?> buy_ways;
        private String cat_id;
        private String cs;
        private String description;
        private String goods_id;
        private List<String> goods_img;
        private String goods_name;
        private String goods_thumbnail;
        private String goods_type;
        private GoodsVideoBean goods_video;
        private String group_purchase;
        private String inventory;
        private String is_open_tb;
        private String is_sale;
        private List<?> label;
        private List<?> meal;
        private String now_price;
        private String original_price;
        private String price;
        private String sales_volume;
        private String sku;
        private List<?> skus;
        private String statement;
        private String tb_link;
        private String use_apple_pay;

        public static class GoodsVideoBean {
        }

        public String getAfter_purchase_goto() {
            return this.after_purchase_goto;
        }

        public String getApple_pay_goods_name() {
            return this.apple_pay_goods_name;
        }

        public String getBuy_permission() {
            return this.buy_permission;
        }

        public List<?> getBuy_ways() {
            return this.buy_ways;
        }

        public String getCat_id() {
            return this.cat_id;
        }

        public String getCs() {
            return this.cs;
        }

        public String getDescription() {
            return this.description;
        }

        public String getGoods_id() {
            return this.goods_id;
        }

        public List<String> getGoods_img() {
            return this.goods_img;
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

        public GoodsVideoBean getGoods_video() {
            return this.goods_video;
        }

        public String getGroup_purchase() {
            return this.group_purchase;
        }

        public String getInventory() {
            return this.inventory;
        }

        public String getIs_open_tb() {
            return this.is_open_tb;
        }

        public String getIs_sale() {
            return this.is_sale;
        }

        public List<?> getLabel() {
            return this.label;
        }

        public List<?> getMeal() {
            return this.meal;
        }

        public String getNow_price() {
            return this.now_price;
        }

        public String getOriginal_price() {
            return this.original_price;
        }

        public String getPrice() {
            return this.price;
        }

        public String getSales_volume() {
            return this.sales_volume;
        }

        public String getSku() {
            return this.sku;
        }

        public List<?> getSkus() {
            return this.skus;
        }

        public String getStatement() {
            return this.statement;
        }

        public String getTb_link() {
            return this.tb_link;
        }

        public String getUse_apple_pay() {
            return this.use_apple_pay;
        }

        public void setAfter_purchase_goto(String str) {
            this.after_purchase_goto = str;
        }

        public void setApple_pay_goods_name(String str) {
            this.apple_pay_goods_name = str;
        }

        public void setBuy_permission(String str) {
            this.buy_permission = str;
        }

        public void setBuy_ways(List<?> list) {
            this.buy_ways = list;
        }

        public void setCat_id(String str) {
            this.cat_id = str;
        }

        public void setCs(String str) {
            this.cs = str;
        }

        public void setDescription(String str) {
            this.description = str;
        }

        public void setGoods_id(String str) {
            this.goods_id = str;
        }

        public void setGoods_img(List<String> list) {
            this.goods_img = list;
        }

        public void setGoods_name(String str) {
            this.goods_name = str;
        }

        public void setGoods_thumbnail(String str) {
            this.goods_thumbnail = str;
        }

        public void setGoods_type(String str) {
            this.goods_type = str;
        }

        public void setGoods_video(GoodsVideoBean goodsVideoBean) {
            this.goods_video = goodsVideoBean;
        }

        public void setGroup_purchase(String str) {
            this.group_purchase = str;
        }

        public void setInventory(String str) {
            this.inventory = str;
        }

        public void setIs_open_tb(String str) {
            this.is_open_tb = str;
        }

        public void setIs_sale(String str) {
            this.is_sale = str;
        }

        public void setLabel(List<?> list) {
            this.label = list;
        }

        public void setMeal(List<?> list) {
            this.meal = list;
        }

        public void setNow_price(String str) {
            this.now_price = str;
        }

        public void setOriginal_price(String str) {
            this.original_price = str;
        }

        public void setPrice(String str) {
            this.price = str;
        }

        public void setSales_volume(String str) {
            this.sales_volume = str;
        }

        public void setSku(String str) {
            this.sku = str;
        }

        public void setSkus(List<?> list) {
            this.skus = list;
        }

        public void setStatement(String str) {
            this.statement = str;
        }

        public void setTb_link(String str) {
            this.tb_link = str;
        }

        public void setUse_apple_pay(String str) {
            this.use_apple_pay = str;
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

    public void setCode(String str) {
        this.code = str;
    }

    public void setData(DataBean dataBean) {
        this.data = dataBean;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setServer_time(String str) {
        this.server_time = str;
    }
}
