package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public class EMASilentModeParam extends EMABase {
    public EMASilentModeParam() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public native void nativeSetEndTime(EMASilentModeTime eMASilentModeTime);

    public native void nativeSetParamType(int i2);

    public native void nativeSetRemindType(int i2);

    public native void nativeSetSilentDuration(int i2);

    public native void nativeSetStartTime(EMASilentModeTime eMASilentModeTime);

    public void setEndTime(EMASilentModeTime eMASilentModeTime) {
        nativeSetEndTime(eMASilentModeTime);
    }

    public void setParamType(int i2) {
        nativeSetParamType(i2);
    }

    public void setRemindType(int i2) {
        nativeSetRemindType(i2);
    }

    public void setSilentDuration(int i2) {
        nativeSetSilentDuration(i2);
    }

    public void setStartTime(EMASilentModeTime eMASilentModeTime) {
        nativeSetStartTime(eMASilentModeTime);
    }
}
