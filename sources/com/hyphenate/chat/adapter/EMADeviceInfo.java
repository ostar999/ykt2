package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public class EMADeviceInfo extends EMABase {
    public EMADeviceInfo() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public String getDeviceName() {
        return nativeGetDeviceName();
    }

    public String getDeviceUUID() {
        return nativeGetDeviceUUID();
    }

    public String getResource() {
        return nativeGetResource();
    }

    public native void nativeFinalize();

    public native String nativeGetDeviceName();

    public native String nativeGetDeviceUUID();

    public native String nativeGetResource();

    public native void nativeInit();

    public native void nativeSetDeviceName(String str);

    public native void nativeSetDeviceUUID(String str);

    public native void nativeSetResource(String str);

    public void setDeviceName(String str) {
        nativeSetDeviceName(str);
    }

    public void setDeviceUUID(String str) {
        nativeSetDeviceUUID(str);
    }

    public void setResource(String str) {
        nativeSetResource(str);
    }
}
