package com.easefun.polyv.livecommon.module.modules.chatroom;

import cn.hutool.core.text.CharPool;

/* loaded from: classes3.dex */
public class PLVCustomGiftBean {
    public static final String EVENT = "GiftMessage";
    public static final String GIFTTYPE_666 = "666";
    public static final String GIFTTYPE_CLAP = "clap";
    public static final String GIFTTYPE_COFFEE = "coffee";
    public static final String GIFTTYPE_DIAMOND = "diamond";
    public static final String GIFTTYPE_FLOWER = "flower";
    public static final String GIFTTYPE_LIKES = "likes";
    public static final String GIFTTYPE_ROCKET = "rocket";
    public static final String GIFTTYPE_SPORTSCAR = "sportscar";
    public static final String GIFTTYPE_STARLET = "starlet";
    private int giftCount;
    private String giftName;
    private String giftType;

    public PLVCustomGiftBean(String giftType, String giftName, int giftCount) {
        this.giftType = giftType;
        this.giftName = giftName;
        this.giftCount = giftCount;
    }

    public static String getGiftName(String giftType) {
        giftType.hashCode();
        switch (giftType) {
            case "starlet":
                return "小星星";
            case "coffee":
                return "咖啡";
            case "flower":
                return "鲜花";
            case "sportscar":
                return "跑车";
            case "rocket":
                return "火箭";
            case "666":
                return GIFTTYPE_666;
            case "clap":
                return "掌声";
            case "likes":
                return "点赞";
            case "diamond":
                return "钻石";
            default:
                return "";
        }
    }

    public int getGiftCount() {
        return this.giftCount;
    }

    public String getGiftType() {
        return this.giftType;
    }

    public void setGiftCount(int giftCount) {
        this.giftCount = giftCount;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public void setGiftType(String giftType) {
        this.giftType = giftType;
    }

    public String toString() {
        return "PLVCustomGiftBean{giftType='" + this.giftType + CharPool.SINGLE_QUOTE + ", giftName='" + this.giftName + CharPool.SINGLE_QUOTE + ", giftCount=" + this.giftCount + '}';
    }

    public String getGiftName() {
        return this.giftName;
    }
}
