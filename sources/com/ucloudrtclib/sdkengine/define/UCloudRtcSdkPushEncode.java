package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkPushEncode {
    UCLOUD_RTC_PUSH_ENCODE_MODE_H264,
    UCLOUD_RTC_PUSH_ENCODE_MODE_VP8;

    public static UCloudRtcSdkPushEncode matchValue(int i2) {
        for (UCloudRtcSdkPushEncode uCloudRtcSdkPushEncode : values()) {
            if (uCloudRtcSdkPushEncode.ordinal() == i2) {
                return uCloudRtcSdkPushEncode;
            }
        }
        return UCLOUD_RTC_PUSH_ENCODE_MODE_H264;
    }
}
