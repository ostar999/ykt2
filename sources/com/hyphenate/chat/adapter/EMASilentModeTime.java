package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public class EMASilentModeTime extends EMABase {
    public EMASilentModeTime(int i2, int i3) {
        nativeInit(i2, i3);
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public int getHour() {
        return nativeGetHour();
    }

    public int getMinute() {
        return nativeGetMinute();
    }

    public native void nativeFinalize();

    public native int nativeGetHour();

    public native int nativeGetMinute();

    public native void nativeInit(int i2, int i3);

    public native void nativeSetHour(int i2);

    public native void nativeSetMinute(int i2);

    public void setHour(int i2) {
        nativeSetHour(i2);
    }

    public void setMinute(int i2) {
        nativeSetMinute(i2);
    }
}
