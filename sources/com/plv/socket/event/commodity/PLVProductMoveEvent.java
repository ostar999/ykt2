package com.plv.socket.event.commodity;

import com.plv.socket.event.PLVMessageBaseEvent;
import com.tencent.connect.common.Constants;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVProductMoveEvent extends PLVMessageBaseEvent {
    public static final String EVENT = "PRODUCT_MESSAGE";
    private List<PLVProductContentBean> content;
    private String status;

    public List<PLVProductContentBean> getContent() {
        return this.content;
    }

    @Override // com.plv.socket.event.PLVBaseEvent
    public String getEVENT() {
        return "PRODUCT_MESSAGE";
    }

    public String getStatus() {
        return this.status;
    }

    public boolean isMoveDown() {
        return "7".equals(this.status);
    }

    public boolean isMoveUp() {
        return Constants.VIA_SHARE_TYPE_INFO.equals(this.status);
    }

    public void setContent(List<PLVProductContentBean> list) {
        this.content = list;
    }

    public void setStatus(String str) {
        this.status = str;
    }
}
