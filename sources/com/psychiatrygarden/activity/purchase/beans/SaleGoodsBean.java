package com.psychiatrygarden.activity.purchase.beans;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class SaleGoodsBean implements Serializable {
    private String coupon_id;
    private String course_id;
    public String ebook_id;
    private String enclosure_id;
    public String goods_id;
    private String identity_id;
    private String is_promotion = "0";
    private String order_no;
    public String price;
    private String privilege_id;
    public String quantity;
    private String red_packet_id;
    private String sku_id;

    public String getCoupon_id() {
        return this.coupon_id;
    }

    public String getCourse_id() {
        return this.course_id;
    }

    public String getEbook_id() {
        return this.ebook_id;
    }

    public String getEnclosure_id() {
        return this.enclosure_id;
    }

    public String getGoods_id() {
        return this.goods_id;
    }

    public String getIdentity_id() {
        return this.identity_id;
    }

    public String getIs_promotion() {
        return this.is_promotion;
    }

    public String getOrder_no() {
        return this.order_no;
    }

    public String getPrice() {
        return this.price;
    }

    public String getPrivilege_id() {
        return this.privilege_id;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public String getRed_packet_id() {
        return this.red_packet_id;
    }

    public String getSku_id() {
        return this.sku_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    public void setCourse_id(String course_id) {
        this.course_id = course_id;
    }

    public void setEbook_id(String ebook_id) {
        this.ebook_id = ebook_id;
    }

    public void setEnclosure_id(String enclosure_id) {
        this.enclosure_id = enclosure_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }

    public void setIs_promotion(String is_promotion) {
        this.is_promotion = is_promotion;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setPrivilege_id(String privilege_id) {
        this.privilege_id = privilege_id;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setRed_packet_id(String red_packet_id) {
        this.red_packet_id = red_packet_id;
    }

    public void setSku_id(String sku_id) {
        this.sku_id = sku_id;
    }
}
