package com.plv.livescenes.model;

import cn.hutool.core.text.CharPool;
import com.plv.business.model.PLVBaseVO;

/* loaded from: classes5.dex */
public class PLVSocketMessageVO implements PLVBaseVO {
    private String event;
    private String message;

    public PLVSocketMessageVO(String str, String str2) {
        this.message = str;
        this.event = str2;
    }

    public String getEvent() {
        return this.event;
    }

    public String getMessage() {
        return this.message;
    }

    public void setEvent(String str) {
        this.event = str;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public String toString() {
        return "PLVSocketMessageVO{message='" + this.message + CharPool.SINGLE_QUOTE + ", event='" + this.event + CharPool.SINGLE_QUOTE + '}';
    }
}
