package com.plv.rtc.urtc.enummeration;

/* loaded from: classes5.dex */
public enum URTCSdkMediaType {
    UCLOUD_RTC_SDK_MEDIA_TYPE_NULL(0),
    UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO(1),
    UCLOUD_RTC_SDK_MEDIA_TYPE_SCREEN(2),
    UCLOUD_RTC_SDK_MEDIA_TYPE_FILE(3);

    private int result;

    URTCSdkMediaType(int i2) {
        this.result = i2;
    }

    public int getResult() {
        return this.result;
    }
}
