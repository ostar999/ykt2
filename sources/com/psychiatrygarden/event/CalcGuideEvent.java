package com.psychiatrygarden.event;

import com.psychiatrygarden.bean.NewHomeKingKongItem;
import java.util.List;

/* loaded from: classes5.dex */
public class CalcGuideEvent {
    private boolean hasBannerAd;
    private List<List<NewHomeKingKongItem>> itemList;
    private int totalCount;

    public CalcGuideEvent(int totalCount, List<List<NewHomeKingKongItem>> itemList) {
        this.totalCount = totalCount;
        this.itemList = itemList;
    }

    public List<List<NewHomeKingKongItem>> getItemList() {
        return this.itemList;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public boolean isHasBannerAd() {
        return this.hasBannerAd;
    }

    public void setHasBannerAd(boolean hasBannerAd) {
        this.hasBannerAd = hasBannerAd;
    }

    public void setItemList(List<List<NewHomeKingKongItem>> itemList) {
        this.itemList = itemList;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public CalcGuideEvent(int totalCount) {
        this.totalCount = totalCount;
    }

    public CalcGuideEvent(List<List<NewHomeKingKongItem>> itemList) {
        this.totalCount = 0;
        this.itemList = itemList;
    }
}
