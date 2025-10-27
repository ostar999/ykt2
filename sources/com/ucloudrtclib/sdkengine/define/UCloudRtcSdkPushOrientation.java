package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkPushOrientation {
    UCLOUD_RTC_PUSH_AUTO_MODE,
    UCLOUD_RTC_PUSH_LANDSCAPE_MODE,
    UCLOUD_RTC_PUSH_PORTRAIT_MODE,
    UCLOUD_RTC_PUSH_LANDSCAPE_MODE2,
    UCLOUD_RTC_PUSH_PORTRAIT_MODE2;

    public static UCloudRtcSdkPushOrientation matchValue(int i2) {
        for (UCloudRtcSdkPushOrientation uCloudRtcSdkPushOrientation : values()) {
            if (uCloudRtcSdkPushOrientation.ordinal() == i2) {
                return uCloudRtcSdkPushOrientation;
            }
        }
        return null;
    }
}
