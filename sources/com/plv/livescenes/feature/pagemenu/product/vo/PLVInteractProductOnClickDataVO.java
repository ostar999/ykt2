package com.plv.livescenes.feature.pagemenu.product.vo;

import com.plv.socket.event.commodity.PLVProductContentBean;

/* loaded from: classes4.dex */
public class PLVInteractProductOnClickDataVO {
    private PLVProductContentBean data;

    public PLVProductContentBean getData() {
        return this.data;
    }

    public PLVInteractProductOnClickDataVO setData(PLVProductContentBean pLVProductContentBean) {
        this.data = pLVProductContentBean;
        return this;
    }

    public String toString() {
        return "PLVInteractProductOnClickDataVO{data=" + this.data + '}';
    }
}
