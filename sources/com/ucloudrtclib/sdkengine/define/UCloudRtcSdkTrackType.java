package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkTrackType {
    UCLOUD_RTC_SDK_TRACK_TYPE_NULL(0),
    UCLOUD_RTC_SDK_TRACK_TYPE_AUDIO(1),
    UCLOUD_RTC_SDK_TRACK_TYPE_VIDEO(2),
    UCLOUD_RTC_SDK_TRACK_TYPE_DATA(3);

    private int result;

    UCloudRtcSdkTrackType(int i2) {
        this.result = i2;
    }

    public static UCloudRtcSdkTrackType matchValue(int i2) {
        for (UCloudRtcSdkTrackType uCloudRtcSdkTrackType : values()) {
            if (uCloudRtcSdkTrackType.ordinal() == i2) {
                return uCloudRtcSdkTrackType;
            }
        }
        return UCLOUD_RTC_SDK_TRACK_TYPE_NULL;
    }

    public int getResult() {
        return this.result;
    }
}
