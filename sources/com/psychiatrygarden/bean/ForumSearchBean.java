package com.psychiatrygarden.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.psychiatrygarden.bean.CirclrListBean;

/* loaded from: classes5.dex */
public class ForumSearchBean implements MultiItemEntity {
    public static final int TYPE_BOOK = 4;
    public static final int TYPE_CIRCLE = 1;
    public static final int TYPE_COMMENT = 3;
    public static final int TYPE_EXPERIENCE = 2;
    public static final int TYPE_INFO = 5;
    private CirclrListBean.DataBean circleInfo;
    private int type = 1;

    public CirclrListBean.DataBean getCircleInfo() {
        return this.circleInfo;
    }

    @Override // com.chad.library.adapter.base.entity.MultiItemEntity
    public int getItemType() {
        return this.type;
    }

    public int getType() {
        return this.type;
    }

    public void setCircleInfo(CirclrListBean.DataBean circleInfo) {
        this.circleInfo = circleInfo;
    }

    public void setType(int type) {
        this.type = type;
    }
}
