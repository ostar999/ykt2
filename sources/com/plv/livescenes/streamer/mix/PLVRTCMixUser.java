package com.plv.livescenes.streamer.mix;

/* loaded from: classes5.dex */
public class PLVRTCMixUser {
    private boolean hidden = false;
    private boolean isScreenShare = false;
    private boolean muteVideo;
    private String userId;

    public String getUserId() {
        return this.userId;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public boolean isMuteVideo() {
        return this.muteVideo;
    }

    public boolean isScreenShare() {
        return this.isScreenShare;
    }

    public void setHidden(boolean z2) {
        this.hidden = z2;
    }

    public void setMuteVideo(boolean z2) {
        this.muteVideo = z2;
    }

    public void setScreenShare(boolean z2) {
        this.isScreenShare = z2;
    }

    public void setUserId(String str) {
        this.userId = str;
    }
}
