package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkStreamRole {
    UCLOUD_RTC_SDK_STREAM_ROLE_PUB,
    UCLOUD_RTC_SDK_STREAM_ROLE_SUB,
    UCLOUD_RTC_SDK_STREAM_ROLE_BOTH;

    public static UCloudRtcSdkStreamRole valueOf(int i2) {
        if (i2 == 0) {
            return UCLOUD_RTC_SDK_STREAM_ROLE_PUB;
        }
        if (i2 == 1) {
            return UCLOUD_RTC_SDK_STREAM_ROLE_SUB;
        }
        if (i2 != 2) {
            return null;
        }
        return UCLOUD_RTC_SDK_STREAM_ROLE_BOTH;
    }
}
