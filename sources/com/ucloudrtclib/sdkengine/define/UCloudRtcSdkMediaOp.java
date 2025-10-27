package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public class UCloudRtcSdkMediaOp {
    private String mUid = "";
    private UCloudRtcSdkMediaType mMediaType = UCloudRtcSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO;
    private boolean mEnableAudio = false;
    private boolean mEnableVideo = false;

    public UCloudRtcSdkMediaType getMediaType() {
        return this.mMediaType;
    }

    public String getUId() {
        return this.mUid;
    }

    public boolean isEnableAudio() {
        return this.mEnableAudio;
    }

    public boolean isEnableVideo() {
        return this.mEnableVideo;
    }

    public void setEnableAudio(boolean z2) {
        this.mEnableAudio = z2;
    }

    public void setEnableVideo(boolean z2) {
        this.mEnableVideo = z2;
    }

    public void setMediaType(UCloudRtcSdkMediaType uCloudRtcSdkMediaType) {
        this.mMediaType = uCloudRtcSdkMediaType;
    }

    public void setUId(String str) {
        this.mUid = str;
    }
}
