package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkMode {
    UCLOUD_RTC_SDK_MODE_NORMAL,
    UCLOUD_RTC_SDK_MODE_TRIAL;

    public static UCloudRtcSdkMode matchValue(int i2) {
        for (UCloudRtcSdkMode uCloudRtcSdkMode : values()) {
            if (uCloudRtcSdkMode.ordinal() == i2) {
                return uCloudRtcSdkMode;
            }
        }
        return UCLOUD_RTC_SDK_MODE_NORMAL;
    }
}
