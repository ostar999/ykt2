package com.plv.livescenes.video.api;

/* loaded from: classes5.dex */
public interface IPLVLiveVideoView {
    void changeMediaPlayMode(int i2);

    void enableFrameSkip(boolean z2);

    String getLinkMicType();

    int getMediaPlayMode();

    boolean isOnline();

    void rtcPrepared();

    void setAudioModeView(IPLVLiveAudioModeView iPLVLiveAudioModeView);

    void setMediaPlayMode(int i2);

    void setPPTLivePlay(String str, String str2, boolean z2);

    void updateMainScreenStatus(boolean z2);
}
