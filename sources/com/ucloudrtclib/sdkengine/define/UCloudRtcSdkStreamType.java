package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkStreamType {
    UCLOUD_RTC_SDK_STREAM_TYPE_NULL(0),
    UCLOUD_RTC_SDK_STREAM_TYPE_PUB(1),
    UCLOUD_RTC_SDK_STREAM_TYPE_SUB(2);

    private int result;

    UCloudRtcSdkStreamType(int i2) {
        this.result = i2;
    }

    public static UCloudRtcSdkStreamType matchValue(int i2) {
        for (UCloudRtcSdkStreamType uCloudRtcSdkStreamType : values()) {
            if (uCloudRtcSdkStreamType.ordinal() == i2) {
                return uCloudRtcSdkStreamType;
            }
        }
        return UCLOUD_RTC_SDK_STREAM_TYPE_NULL;
    }

    public int getResult() {
        return this.result;
    }
}
