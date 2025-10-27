package com.easefun.polyv.livecommon.module.modules.commodity.model.vo;

import com.easefun.polyv.livecommon.module.modules.socket.PLVSocketMessage;
import com.plv.socket.event.commodity.PLVProductEvent;

/* loaded from: classes3.dex */
public class PLVCommodityProductVO {
    private final PLVSocketMessage message;
    private final PLVProductEvent productEvent;

    public PLVCommodityProductVO(final PLVProductEvent productEvent, final PLVSocketMessage message) {
        this.productEvent = productEvent;
        this.message = message;
    }

    public PLVSocketMessage getMessage() {
        return this.message;
    }

    public PLVProductEvent getProductEvent() {
        return this.productEvent;
    }

    public String toString() {
        return "PLVCommodityProductVO{productEvent=" + this.productEvent + ", message=" + this.message + '}';
    }
}
