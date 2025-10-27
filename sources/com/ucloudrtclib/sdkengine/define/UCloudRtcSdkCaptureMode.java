package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkCaptureMode {
    UCLOUD_RTC_CAPTURE_MODE_LOCAL(0),
    UCLOUD_RTC_CAPTURE_MODE_EXTEND(1),
    UCLOUD_RTC_CAPTURE_MODE_RTSP(3);

    private int result;

    UCloudRtcSdkCaptureMode(int i2) {
        this.result = i2;
    }

    public static UCloudRtcSdkCaptureMode matchValue(int i2) {
        for (UCloudRtcSdkCaptureMode uCloudRtcSdkCaptureMode : values()) {
            if (uCloudRtcSdkCaptureMode.ordinal() == i2) {
                return uCloudRtcSdkCaptureMode;
            }
        }
        return UCLOUD_RTC_CAPTURE_MODE_LOCAL;
    }

    public int getResult() {
        return this.result;
    }
}
