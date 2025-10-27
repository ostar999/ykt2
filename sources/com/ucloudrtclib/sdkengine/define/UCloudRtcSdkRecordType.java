package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkRecordType {
    U_CLOUD_RTC_SDK_RECORD_TYPE_MP4(0);

    private int result;

    UCloudRtcSdkRecordType(int i2) {
        this.result = i2;
    }

    public static UCloudRtcSdkRecordType matchValue(int i2) {
        for (UCloudRtcSdkRecordType uCloudRtcSdkRecordType : values()) {
            if (uCloudRtcSdkRecordType.ordinal() == i2) {
                return uCloudRtcSdkRecordType;
            }
        }
        return U_CLOUD_RTC_SDK_RECORD_TYPE_MP4;
    }

    public int getResult() {
        return this.result;
    }
}
