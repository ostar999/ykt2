package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkEngineType {
    UCLOUD_RTC_SDK_ENGINE_TYPE_0,
    UCLOUD_RTC_SDK_ENGINE_TYPE_1;

    public static UCloudRtcSdkEngineType valueOf(int i2) {
        if (i2 == 0) {
            return UCLOUD_RTC_SDK_ENGINE_TYPE_0;
        }
        if (i2 != 1) {
            return null;
        }
        return UCLOUD_RTC_SDK_ENGINE_TYPE_1;
    }
}
