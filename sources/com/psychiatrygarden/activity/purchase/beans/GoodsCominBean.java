package com.psychiatrygarden.activity.purchase.beans;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.psychiatrygarden.activity.purchase.beans.CommodityEvaluationBean;
import com.psychiatrygarden.activity.purchase.beans.GoodsBean;
import java.io.Serializable;

/* loaded from: classes5.dex */
public class GoodsCominBean implements MultiItemEntity, Serializable {
    private CommodityEvaluationBean.DataBean commentData;
    public GoodsBean.DataBean data;
    public int viewType = 1;
    public String detailUrl = "";

    public CommodityEvaluationBean.DataBean getCommentData() {
        return this.commentData;
    }

    public GoodsBean.DataBean getData() {
        return this.data;
    }

    public String getDetailUrl() {
        return this.detailUrl;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.viewType;
    }

    public int getViewType() {
        return this.viewType;
    }

    public void setCommentData(CommodityEvaluationBean.DataBean commentData) {
        this.commentData = commentData;
    }

    public void setData(GoodsBean.DataBean data) {
        this.data = data;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
