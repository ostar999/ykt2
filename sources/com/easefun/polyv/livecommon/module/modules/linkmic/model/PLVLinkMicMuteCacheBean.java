package com.easefun.polyv.livecommon.module.modules.linkmic.model;

/* loaded from: classes3.dex */
public class PLVLinkMicMuteCacheBean {
    private String linkMicId;
    private boolean muteAudio = false;
    private boolean muteVideo = false;

    public PLVLinkMicMuteCacheBean(String linkMicId) {
        this.linkMicId = linkMicId;
    }

    public String getLinkMicId() {
        return this.linkMicId;
    }

    public boolean isMuteAudio() {
        return this.muteAudio;
    }

    public boolean isMuteVideo() {
        return this.muteVideo;
    }

    public void setLinkMicId(String linkMicId) {
        this.linkMicId = linkMicId;
    }

    public void setMuteAudio(boolean muteAudio) {
        this.muteAudio = muteAudio;
    }

    public void setMuteVideo(boolean muteVideo) {
        this.muteVideo = muteVideo;
    }
}
