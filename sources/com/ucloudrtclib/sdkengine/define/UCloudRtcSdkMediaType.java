package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkMediaType {
    UCLOUD_RTC_SDK_MEDIA_TYPE_NULL(0),
    UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO(1),
    UCLOUD_RTC_SDK_MEDIA_TYPE_SCREEN(2),
    UCLOUD_RTC_SDK_MEDIA_TYPE_FILE(3);

    private int result;

    UCloudRtcSdkMediaType(int i2) {
        this.result = i2;
    }

    public static UCloudRtcSdkMediaType matchValue(int i2) {
        for (UCloudRtcSdkMediaType uCloudRtcSdkMediaType : values()) {
            if (uCloudRtcSdkMediaType.ordinal() == i2) {
                return uCloudRtcSdkMediaType;
            }
        }
        return UCLOUD_RTC_SDK_MEDIA_TYPE_NULL;
    }

    public int getResult() {
        return this.result;
    }
}
