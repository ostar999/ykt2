package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkVideoOutputOrientationMode {
    UCLOUD_RTC_VIDEO_OUTPUT_ADAPTIVE_MODE,
    UCLOUD_RTC_VIDEO_OUTPUT_FIXED_LANDSCAPE_MODE,
    UCLOUD_RTC_VIDEO_OUTPUT_FIXED_PORTRAIT_MODE;

    public static UCloudRtcSdkVideoOutputOrientationMode matchValue(int i2) {
        for (UCloudRtcSdkVideoOutputOrientationMode uCloudRtcSdkVideoOutputOrientationMode : values()) {
            if (uCloudRtcSdkVideoOutputOrientationMode.ordinal() == i2) {
                return uCloudRtcSdkVideoOutputOrientationMode;
            }
        }
        return null;
    }
}
