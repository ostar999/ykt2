package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkStreamSt {
    UCLOUD_RTC_SDK_STREAM_ST_NULL(0),
    UCLOUD_RTC_SDK_STREAM_ST_ADD(1),
    UCLOUD_RTC_SDK_STREAM_ST_REMOVE(2),
    UCLOUD_RTC_SDK_STREAM_ST_UPDATE(3);

    private int mValue;

    UCloudRtcSdkStreamSt(int i2) {
        this.mValue = i2;
    }

    public static UCloudRtcSdkStreamSt matchValue(int i2) {
        for (UCloudRtcSdkStreamSt uCloudRtcSdkStreamSt : values()) {
            if (uCloudRtcSdkStreamSt.ordinal() == i2) {
                return uCloudRtcSdkStreamSt;
            }
        }
        return UCLOUD_RTC_SDK_STREAM_ST_NULL;
    }
}
