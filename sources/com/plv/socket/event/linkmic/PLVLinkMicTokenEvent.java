package com.plv.socket.event.linkmic;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public class PLVLinkMicTokenEvent implements PLVFoundationVO {
    private String EVENT;
    private String token;

    public String getEVENT() {
        return this.EVENT;
    }

    public String getToken() {
        return this.token;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setToken(String str) {
        this.token = str;
    }
}
