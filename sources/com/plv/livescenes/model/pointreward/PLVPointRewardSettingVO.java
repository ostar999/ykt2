package com.plv.livescenes.model.pointreward;

import java.util.List;

@Deprecated
/* loaded from: classes5.dex */
public class PLVPointRewardSettingVO {
    private Object channelDonatePointEnabled;
    private String donatePointEnabled;
    private List<GoodsBean> goods;
    private String pointNotEnoughTips;
    private String pointUnit;
    private Object queryPointUrl;
    private String requestFailTips;
    private Object updatePointUrl;
    private String userId;

    public static class GoodsBean {
        private String goodEnabled;
        private int goodId;
        private String goodImg;
        private String goodName;
        private int goodPrice;

        public String getGoodEnabled() {
            return this.goodEnabled;
        }

        public int getGoodId() {
            return this.goodId;
        }

        public String getGoodImg() {
            return this.goodImg;
        }

        public String getGoodName() {
            return this.goodName;
        }

        public int getGoodPrice() {
            return this.goodPrice;
        }

        public void setGoodEnabled(String str) {
            this.goodEnabled = str;
        }

        public void setGoodId(int i2) {
            this.goodId = i2;
        }

        public void setGoodImg(String str) {
            this.goodImg = str;
        }

        public void setGoodName(String str) {
            this.goodName = str;
        }

        public void setGoodPrice(int i2) {
            this.goodPrice = i2;
        }
    }

    public Object getChannelDonatePointEnabled() {
        return this.channelDonatePointEnabled;
    }

    public String getDonatePointEnabled() {
        return this.donatePointEnabled;
    }

    public List<GoodsBean> getGoods() {
        return this.goods;
    }

    public String getPointNotEnoughTips() {
        return this.pointNotEnoughTips;
    }

    public String getPointUnit() {
        return this.pointUnit;
    }

    public Object getQueryPointUrl() {
        return this.queryPointUrl;
    }

    public String getRequestFailTips() {
        return this.requestFailTips;
    }

    public Object getUpdatePointUrl() {
        return this.updatePointUrl;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setChannelDonatePointEnabled(Object obj) {
        this.channelDonatePointEnabled = obj;
    }

    public void setDonatePointEnabled(String str) {
        this.donatePointEnabled = str;
    }

    public void setGoods(List<GoodsBean> list) {
        this.goods = list;
    }

    public void setPointNotEnoughTips(String str) {
        this.pointNotEnoughTips = str;
    }

    public void setPointUnit(String str) {
        this.pointUnit = str;
    }

    public void setQueryPointUrl(Object obj) {
        this.queryPointUrl = obj;
    }

    public void setRequestFailTips(String str) {
        this.requestFailTips = str;
    }

    public void setUpdatePointUrl(Object obj) {
        this.updatePointUrl = obj;
    }

    public void setUserId(String str) {
        this.userId = str;
    }
}
