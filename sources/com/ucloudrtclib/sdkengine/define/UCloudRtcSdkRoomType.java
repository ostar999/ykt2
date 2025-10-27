package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkRoomType {
    UCLOUD_RTC_SDK_ROOM_SMALL,
    UCLOUD_RTC_SDK_ROOM_LARGE;

    public static UCloudRtcSdkRoomType valueOf(int i2) {
        if (i2 == 0) {
            return UCLOUD_RTC_SDK_ROOM_SMALL;
        }
        if (i2 != 1) {
            return null;
        }
        return UCLOUD_RTC_SDK_ROOM_LARGE;
    }
}
