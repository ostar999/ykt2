package com.psychiatrygarden.bean;

import java.io.Serializable;

/* loaded from: classes5.dex */
public class OrderConfirmParams implements Serializable {
    private String goodType;
    private String goods_id = "";
    private String ebook_id = "";
    private String enclosure_id = "";
    private String course_id = "";
    private String price = "";
    private String quantity = "1";
    private String upgrade_type = "";
    private String deduction_id = "";
    private boolean isGoods = false;
    private boolean courseHaveAddress = false;
    private boolean goodsHaveAddress = false;
    private boolean goodsHaveGiftAddress = false;
    private boolean is_promotion = false;
    private int goodsCanBuyNum = 0;
    private boolean isInventoryLimit = false;

    public String getCourse_id() {
        return this.course_id;
    }

    public String getDeduction_id() {
        return this.deduction_id;
    }

    public String getEbook_id() {
        return this.ebook_id;
    }

    public String getEnclosure_id() {
        return this.enclosure_id;
    }

    public String getGoodType() {
        return this.goodType;
    }

    public int getGoodsCanBuyNum() {
        return this.goodsCanBuyNum;
    }

    public String getGoods_id() {
        return this.goods_id;
    }

    public String getPrice() {
        return this.price;
    }

    public String getQuantity() {
        return this.quantity;
    }

    public String getUpgrade_type() {
        return this.upgrade_type;
    }

    public boolean isCourseHaveAddress() {
        return this.courseHaveAddress;
    }

    public boolean isGoods() {
        return this.isGoods;
    }

    public boolean isGoodsHaveAddress() {
        return this.goodsHaveAddress;
    }

    public boolean isGoodsHaveGiftAddress() {
        return this.goodsHaveGiftAddress;
    }

    public boolean isInventoryLimit() {
        return this.isInventoryLimit;
    }

    public boolean isIs_promotion() {
        return this.is_promotion;
    }

    public OrderConfirmParams setCourseHaveAddress(boolean courseHaveAddress) {
        this.courseHaveAddress = courseHaveAddress;
        return this;
    }

    public OrderConfirmParams setCourse_id(String course_id) {
        this.course_id = course_id;
        return this;
    }

    public OrderConfirmParams setDeduction_id(String deduction_id) {
        this.deduction_id = deduction_id;
        return this;
    }

    public OrderConfirmParams setEbook_id(String ebook_id) {
        this.ebook_id = ebook_id;
        return this;
    }

    public OrderConfirmParams setEnclosure_id(String enclosure_id) {
        this.enclosure_id = enclosure_id;
        return this;
    }

    public OrderConfirmParams setGoodType(String goodType) {
        this.goodType = goodType;
        return this;
    }

    public OrderConfirmParams setGoods(boolean goods) {
        this.isGoods = goods;
        return this;
    }

    public OrderConfirmParams setGoodsCanBuyNum(int goodsCanBuyNum) {
        this.goodsCanBuyNum = goodsCanBuyNum;
        return this;
    }

    public OrderConfirmParams setGoodsHaveAddress(boolean goodsHaveAddress) {
        this.goodsHaveAddress = goodsHaveAddress;
        return this;
    }

    public OrderConfirmParams setGoodsHaveGiftAddress(boolean goodsHaveGiftAddress) {
        this.goodsHaveGiftAddress = goodsHaveGiftAddress;
        return this;
    }

    public OrderConfirmParams setGoods_id(String goods_id) {
        this.goods_id = goods_id;
        return this;
    }

    public OrderConfirmParams setInventoryLimit(boolean inventoryLimit) {
        this.isInventoryLimit = inventoryLimit;
        return this;
    }

    public OrderConfirmParams setIs_promotion(boolean is_promotion) {
        this.is_promotion = is_promotion;
        return this;
    }

    public OrderConfirmParams setPrice(String price) {
        this.price = price;
        return this;
    }

    public OrderConfirmParams setQuantity(String quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderConfirmParams setUpgrade_type(String upgrade_type) {
        this.upgrade_type = upgrade_type;
        return this;
    }
}
