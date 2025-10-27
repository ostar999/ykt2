package com.plv.livescenes.model;

import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVLiveStatusSessionIdVO implements PLVFoundationVO {
    private String sessionId;
    private String status;

    public String getSessionId() {
        return this.sessionId;
    }

    public String getStatus() {
        return this.status;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }

    public void setStatus(String str) {
        this.status = str;
    }

    public String toString() {
        return "PLVLiveStatusSessionIdVO{status='" + this.status + CharPool.SINGLE_QUOTE + ", sessionId='" + this.sessionId + CharPool.SINGLE_QUOTE + '}';
    }
}
