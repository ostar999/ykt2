package com.psychiatrygarden.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.psychiatrygarden.bean.CirclrListBean;

/* loaded from: classes5.dex */
public class HotCircleBean implements MultiItemEntity {
    public static final int TYPE_CIRCLE = 1;
    public static final int TYPE_MORE = 2;
    private CirclrListBean.DataBean circleInfo;
    private int itemType = 1;

    public CirclrListBean.DataBean getCircleInfo() {
        return this.circleInfo;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.itemType;
    }

    public void setCircleInfo(CirclrListBean.DataBean circleInfo) {
        this.circleInfo = circleInfo;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
