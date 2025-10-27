package com.plv.rtc.zrtc.enummeration;

/* loaded from: classes5.dex */
public enum ZRTCRemoteDeviceState {
    OPEN(0),
    GENERIC_ERROR(1),
    INVALID_ID(2),
    NO_AUTHORIZATION(3),
    ZERO_FPS(4),
    IN_USE_BY_OTHER(5),
    UNPLUGGED(6),
    REBOOT_REQUIRED(7),
    SYSTEM_MEDIA_SERVICES_LOST(8),
    DISABLE(9),
    MUTE(10),
    INTERRUPTION(11),
    IN_BACKGROUND(12),
    MULTI_FOREGROUND_APP(13),
    BY_SYSTEM_PRESSURE(14);

    private int value;

    ZRTCRemoteDeviceState(int i2) {
        this.value = i2;
    }
}
