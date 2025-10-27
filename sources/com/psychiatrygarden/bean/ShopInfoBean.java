package com.psychiatrygarden.bean;

import android.text.TextUtils;
import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.utils.Constants;
import java.util.List;

/* loaded from: classes5.dex */
public class ShopInfoBean {
    private String code;
    private List<DataBean> data;
    private String message;
    private String server_time;

    public static class DataBean {
        private String app_id;
        private String button_text;
        private String cat_id;
        private String goods_id;
        private String goods_name;
        private String goods_sale_type;
        private String goods_thumbnail;
        private String goods_type;
        private String inventory;
        private String is_best_sellers;
        private String is_coupon;
        private String is_gift;
        private String is_stop;
        private List<LabelBean> label;
        private String max_price;
        private String min_price;
        private String now_price;
        private String original_price;
        private String price;
        private String price_gray;
        private String price_label;
        private String sales_begin_time;
        private String sales_end_time;
        private String sales_volume;

        public static class LabelBean {

            @SerializedName(alternate = {"color"}, value = "background_color")
            private String color;
            private String font_color;
            private boolean isCoupon;
            private boolean isGift;
            private boolean isPromotion;

            @SerializedName(alternate = {"value"}, value = Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL)
            private String label;

            public String getColor() {
                return this.color;
            }

            public String getFont_color() {
                return this.font_color;
            }

            public String getLabel() {
                return this.label;
            }

            public boolean isCoupon() {
                return this.isCoupon;
            }

            public boolean isGift() {
                return this.isGift;
            }

            public boolean isPromotion() {
                return this.isPromotion;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public void setCoupon(boolean coupon) {
                this.isCoupon = coupon;
            }

            public void setFont_color(String font_color) {
                this.font_color = font_color;
            }

            public void setGift(boolean gift) {
                this.isGift = gift;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public void setPromotion(boolean promotion) {
                this.isPromotion = promotion;
            }
        }

        public String getApp_id() {
            return this.app_id;
        }

        public String getButton_text() {
            return this.button_text;
        }

        public String getCat_id() {
            return this.cat_id;
        }

        public String getGoods_id() {
            return this.goods_id;
        }

        public String getGoods_name() {
            return this.goods_name;
        }

        public String getGoods_sale_type() {
            return this.goods_sale_type;
        }

        public String getGoods_thumbnail() {
            return this.goods_thumbnail;
        }

        public String getGoods_type() {
            return this.goods_type;
        }

        public String getInventory() {
            return this.inventory;
        }

        public String getIs_best_sellers() {
            return this.is_best_sellers;
        }

        public String getIs_coupon() {
            return this.is_coupon;
        }

        public String getIs_gift() {
            return this.is_gift;
        }

        public String getIs_stop() {
            return this.is_stop;
        }

        public List<LabelBean> getLabel() {
            return this.label;
        }

        public String getMax_price() {
            return TextUtils.isEmpty(this.max_price) ? this.max_price : this.max_price.replace("¥", "");
        }

        public String getMin_price() {
            return TextUtils.isEmpty(this.min_price) ? this.min_price : this.min_price.replace("¥", "");
        }

        public String getNow_price() {
            return this.now_price;
        }

        public String getOriginal_price() {
            return this.original_price;
        }

        public String getPrice() {
            String str = this.price;
            return str == null ? "" : str.replace("¥", "");
        }

        public String getPrice_gray() {
            return this.price_gray;
        }

        public String getPrice_label() {
            return this.price_label;
        }

        public String getSales_begin_time() {
            return this.sales_begin_time;
        }

        public String getSales_end_time() {
            return this.sales_end_time;
        }

        public String getSales_volume() {
            return this.sales_volume;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public void setButton_text(String button_text) {
            this.button_text = button_text;
        }

        public void setCat_id(String cat_id) {
            this.cat_id = cat_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public void setGoods_sale_type(String goods_sale_type) {
            this.goods_sale_type = goods_sale_type;
        }

        public void setGoods_thumbnail(String goods_thumbnail) {
            this.goods_thumbnail = goods_thumbnail;
        }

        public void setGoods_type(String goods_type) {
            this.goods_type = goods_type;
        }

        public void setInventory(String inventory) {
            this.inventory = inventory;
        }

        public void setIs_best_sellers(String is_best_sellers) {
            this.is_best_sellers = is_best_sellers;
        }

        public void setIs_coupon(String is_coupon) {
            this.is_coupon = is_coupon;
        }

        public void setIs_gift(String is_gift) {
            this.is_gift = is_gift;
        }

        public void setIs_stop(String is_stop) {
            this.is_stop = is_stop;
        }

        public void setLabel(List<LabelBean> label) {
            this.label = label;
        }

        public void setMax_price(String max_price) {
            this.max_price = max_price;
        }

        public void setMin_price(String min_price) {
            this.min_price = min_price;
        }

        public void setNow_price(String now_price) {
            this.now_price = now_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setPrice_gray(String price_gray) {
            this.price_gray = price_gray;
        }

        public void setPrice_label(String price_label) {
            this.price_label = price_label;
        }

        public void setSales_begin_time(String sales_begin_time) {
            this.sales_begin_time = sales_begin_time;
        }

        public void setSales_end_time(String sales_end_time) {
            this.sales_end_time = sales_end_time;
        }

        public void setSales_volume(String sales_volume) {
            this.sales_volume = sales_volume;
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
