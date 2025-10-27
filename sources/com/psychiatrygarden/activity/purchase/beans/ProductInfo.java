package com.psychiatrygarden.activity.purchase.beans;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class ProductInfo extends BaseInfo implements Serializable {
    private int count;
    private String desc;
    private String goods_id;
    private String imageUrl;
    private int position;
    private double price;

    public ProductInfo() {
    }

    public int getCount() {
        return this.count;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getGoods_id() {
        return this.goods_id;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public int getPosition() {
        return this.position;
    }

    public double getPrice() {
        return this.price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public ProductInfo(String id, String name, String imageUrl, String desc, double price, int count) {
        this.Id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.desc = desc;
        this.price = price;
        this.count = count;
    }
}
