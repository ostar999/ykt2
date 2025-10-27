package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkNetWorkQuality {
    U_CLOUD_RTC_SDK_NET_WORK_QUALITY_UNKNOWN,
    U_CLOUD_RTC_SDK_NET_WORK_QUALITY_EXCELLENT,
    U_CLOUD_RTC_SDK_NET_WORK_QUALITY_GOOD,
    U_CLOUD_RTC_SDK_NET_WORK_QUALITY_NORMAL,
    U_CLOUD_RTC_SDK_NET_WORK_QUALITY_BAD,
    U_CLOUD_RTC_SDK_NET_WORK_QUALITY_TERRIBLE,
    U_CLOUD_RTC_SDK_NET_WORK_QUALITY_DOWN;

    public static UCloudRtcSdkNetWorkQuality matchValue(int i2) {
        for (UCloudRtcSdkNetWorkQuality uCloudRtcSdkNetWorkQuality : values()) {
            if (uCloudRtcSdkNetWorkQuality.ordinal() == i2) {
                return uCloudRtcSdkNetWorkQuality;
            }
        }
        return U_CLOUD_RTC_SDK_NET_WORK_QUALITY_UNKNOWN;
    }
}
