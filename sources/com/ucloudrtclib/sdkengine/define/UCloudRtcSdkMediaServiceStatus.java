package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkMediaServiceStatus {
    RELAY_STATUS_START_REQUEST_SEND,
    RELAY_STATUS_STOP_REQUEST_SEND,
    RELAY_STATUS_START,
    RELAY_STATUS_ERROR,
    RECORD_STATUS_START_REQUEST_SEND,
    RECORD_STATUS_STOP_REQUEST_SEND,
    RECORD_STATUS_START,
    RECORD_STATUS_ERROR,
    RELAY_RECORD_STATUS_STOP,
    STATUS_UPDATE_REQUEST_SEND,
    STATUS_UPDATE_ADD_STREAM_SUCCESS,
    STATUS_UPDATE_ERROR;

    public static UCloudRtcSdkMediaServiceStatus matchValue(int i2) {
        for (UCloudRtcSdkMediaServiceStatus uCloudRtcSdkMediaServiceStatus : values()) {
            if (uCloudRtcSdkMediaServiceStatus.ordinal() == i2) {
                return uCloudRtcSdkMediaServiceStatus;
            }
        }
        return null;
    }
}
