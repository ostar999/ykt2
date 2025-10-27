package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkAudioVideoMode {
    UCLOUD_RTC_SDK_BOTH_AUDIO_VIDEO(0),
    UCLOUD_RTC_SDK_ONLY_AUDIO_MODULE(1),
    UCLOUD_RTC_SDK_ONLY_VIDEO_MODULE(2);

    private int result;

    UCloudRtcSdkAudioVideoMode(int i2) {
        this.result = i2;
    }

    public static UCloudRtcSdkAudioVideoMode matchValue(int i2) {
        for (UCloudRtcSdkAudioVideoMode uCloudRtcSdkAudioVideoMode : values()) {
            if (uCloudRtcSdkAudioVideoMode.ordinal() == i2) {
                return uCloudRtcSdkAudioVideoMode;
            }
        }
        return UCLOUD_RTC_SDK_BOTH_AUDIO_VIDEO;
    }

    public int getResult() {
        return this.result;
    }
}
