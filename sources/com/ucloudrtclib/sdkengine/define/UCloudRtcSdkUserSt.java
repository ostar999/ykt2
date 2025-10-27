package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkUserSt {
    UCLOUD_RTC_SDK_USER_ST_NULL(0),
    UCLOUD_RTC_SDK_USER_ST_JOIN(1),
    UCLOUD_RTC_SDK_USER_ST_LEAVE(2),
    UCLOUD_RTC_SDK_USER_ST_REJOIN(3);

    private int value;

    UCloudRtcSdkUserSt(int i2) {
        this.value = i2;
    }

    public static UCloudRtcSdkUserSt matchValue(int i2) {
        for (UCloudRtcSdkUserSt uCloudRtcSdkUserSt : values()) {
            if (uCloudRtcSdkUserSt.ordinal() == i2) {
                return uCloudRtcSdkUserSt;
            }
        }
        return UCLOUD_RTC_SDK_USER_ST_NULL;
    }
}
