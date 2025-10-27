package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkLogLevel {
    UCLOUD_RTC_SDK_LogLevelNone,
    UCLOUD_RTC_SDK_LogLevelDebug,
    UCLOUD_RTC_SDK_LogLevelInfo,
    UCLOUD_RTC_SDK_LogLevelWarn,
    UCLOUD_RTC_SDK_LogLevelError;

    public static UCloudRtcSdkLogLevel matchValue(int i2) {
        for (UCloudRtcSdkLogLevel uCloudRtcSdkLogLevel : values()) {
            if (uCloudRtcSdkLogLevel.ordinal() == i2) {
                return uCloudRtcSdkLogLevel;
            }
        }
        return UCLOUD_RTC_SDK_LogLevelNone;
    }
}
