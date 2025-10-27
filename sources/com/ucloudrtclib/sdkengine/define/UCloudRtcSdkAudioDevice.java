package com.ucloudrtclib.sdkengine.define;

/* loaded from: classes6.dex */
public enum UCloudRtcSdkAudioDevice {
    UCLOUD_RTC_SDK_AUDIODEVICE_NONE,
    UCLOUD_RTC_SDK_AUDIODEVICE_SPEAKER,
    UCLOUD_RTC_SDK_AUDIODEVICE_WIRED_HEADSET,
    UCLOUD_RTC_SDK_AUDIODEVICE_EARPIECE,
    UCLOUD_RTC_SDK_AUDIODEVICE_BLUETOOTH;

    public static UCloudRtcSdkAudioDevice matchValue(int i2) {
        for (UCloudRtcSdkAudioDevice uCloudRtcSdkAudioDevice : values()) {
            if (uCloudRtcSdkAudioDevice.ordinal() == i2) {
                return uCloudRtcSdkAudioDevice;
            }
        }
        return UCLOUD_RTC_SDK_AUDIODEVICE_NONE;
    }
}
