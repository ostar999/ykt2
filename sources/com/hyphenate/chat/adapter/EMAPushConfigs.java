package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public class EMAPushConfigs extends EMABase {
    public EMAPushConfigs() {
        nativeInit();
    }

    public EMAPushConfigs(EMAPushConfigs eMAPushConfigs) {
        nativeInit(eMAPushConfigs);
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public String getDisplayNickname() {
        return nativeGetDisplayNickname();
    }

    public int getDisplayStyle() {
        return nativeGetDisplayStyle();
    }

    public int getNoDisturbEndHour() {
        return nativeGetNoDisturbEndHour();
    }

    public int getNoDisturbStartHour() {
        return nativeGetNoDisturbStartHour();
    }

    public boolean isNoDisturbOn() {
        return nativeIsNoDisturbOn();
    }

    public native void nativeFinalize();

    public native String nativeGetDisplayNickname();

    public native int nativeGetDisplayStyle();

    public native int nativeGetNoDisturbEndHour();

    public native int nativeGetNoDisturbStartHour();

    public native void nativeInit();

    public native void nativeInit(EMAPushConfigs eMAPushConfigs);

    public native boolean nativeIsNoDisturbOn();
}
