package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class PurchaseCourseBean implements Serializable {
    private String goods_desc;
    private String goods_discount_price_android;
    private String goods_id;
    private String goods_name;
    private String goods_price_android;
    private String goods_unit;
    private String seconedtitle;
    private String service_id;
    private String status;
    private String status_str;
    private String subjecet_name;
    private int subjecet_type = 0;
    private boolean isShow = false;

    public String getGoods_desc() {
        return this.goods_desc;
    }

    public String getGoods_discount_price_android() {
        return this.goods_discount_price_android;
    }

    public String getGoods_id() {
        return this.goods_id;
    }

    public String getGoods_name() {
        return this.goods_name;
    }

    public String getGoods_price_android() {
        return this.goods_price_android;
    }

    public String getGoods_unit() {
        return this.goods_unit;
    }

    public String getSeconedtitle() {
        return this.seconedtitle;
    }

    public String getService_id() {
        return this.service_id;
    }

    public String getStatus() {
        return this.status;
    }

    public String getStatus_str() {
        return this.status_str;
    }

    public String getSubjecet_name() {
        return this.subjecet_name;
    }

    public int getSubjecet_type() {
        return this.subjecet_type;
    }

    public boolean isShow() {
        return this.isShow;
    }

    public void setGoods_desc(String goods_desc) {
        this.goods_desc = goods_desc;
    }

    public void setGoods_discount_price_android(String goods_discount_price_android) {
        this.goods_discount_price_android = goods_discount_price_android;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public void setGoods_price_android(String goods_price_android) {
        this.goods_price_android = goods_price_android;
    }

    public void setGoods_unit(String goods_unit) {
        this.goods_unit = goods_unit;
    }

    public void setSeconedtitle(String seconedtitle) {
        this.seconedtitle = seconedtitle;
    }

    public void setService_id(String service_id) {
        this.service_id = service_id;
    }

    public void setShow(boolean isShow) {
        this.isShow = isShow;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setStatus_str(String status_str) {
        this.status_str = status_str;
    }

    public void setSubjecet_name(String subjecet_name) {
        this.subjecet_name = subjecet_name;
    }

    public void setSubjecet_type(int subjecet_type) {
        this.subjecet_type = subjecet_type;
    }
}
