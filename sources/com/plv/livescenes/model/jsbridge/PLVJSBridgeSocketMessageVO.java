package com.plv.livescenes.model.jsbridge;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVJSBridgeSocketMessageVO implements PLVFoundationVO {
    public String event;
    public String value;

    public PLVJSBridgeSocketMessageVO() {
    }

    public String getEvent() {
        return this.event;
    }

    public String getValue() {
        return this.value;
    }

    public void setEvent(String str) {
        this.event = str;
    }

    public void setValue(String str) {
        this.value = str;
    }

    public String toString() {
        return "PLVJSBridgeSocketMessageVO{event='" + this.event + CharPool.SINGLE_QUOTE + ", value='" + this.value + CharPool.SINGLE_QUOTE + '}';
    }

    public PLVJSBridgeSocketMessageVO(String str, String str2) {
        this.event = str;
        this.value = str2;
    }
}
