package com.plv.socket.event.commodity;

import com.plv.socket.event.PLVMessageBaseEvent;
import com.tencent.connect.common.Constants;

/* loaded from: classes5.dex */
public class PLVProductControlEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "PRODUCT_MESSAGE";
    private PLVProductContentBean content;
    private String status;

    public PLVProductContentBean getContent() {
        return this.content;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "PRODUCT_MESSAGE";
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isNewly() {
        return "4".equals(this.status);
    }

    public boolean isPush() {
        return Constants.VIA_SHARE_TYPE_MINI_PROGRAM.equals(this.status);
    }

    public boolean isPutOnShelves() {
        return "1".equals(this.status);
    }

    public boolean isRedact() {
        return "5".equals(this.status);
    }

    public void setContent(PLVProductContentBean pLVProductContentBean) {
        this.content = pLVProductContentBean;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
