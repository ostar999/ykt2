package com.plv.linkmic.model;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes4.dex */
public class PLVLinkMicMedia implements PLVFoundationVO {
    private boolean mute;
    private String socketId;
    private String type;

    public String getSocketId() {
        return this.socketId;
    }

    public String getType() {
        return this.type;
    }

    public boolean isMute() {
        return this.mute;
    }

    public void setMute(boolean z2) {
        this.mute = z2;
    }

    public void setSocketId(String str) {
        this.socketId = str;
    }

    public void setType(String str) {
        this.type = str;
    }
}
