package com.plv.socket.event.sclass;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVInLiveEvent implements PLVFoundationVO {
    private String sessionId;

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String str) {
        this.sessionId = str;
    }
}
