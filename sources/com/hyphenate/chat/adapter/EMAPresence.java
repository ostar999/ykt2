package com.hyphenate.chat.adapter;

import java.util.Map;

/* loaded from: classes4.dex */
public class EMAPresence extends EMABase {
    public EMAPresence() {
        nativeInit("");
    }

    public EMAPresence(String str) {
        nativeInit(str);
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public long getExpiryTime() {
        return nativeGetExpiryTime();
    }

    public String getExt() {
        return nativeGetExt();
    }

    public long getLatestTime() {
        return nativeGetLatestTime();
    }

    public String getPublisher() {
        return nativeGetPublisher();
    }

    public Map<String, Integer> getStatusList() {
        return nativeGetStatusList();
    }

    public native void nativeFinalize();

    public native long nativeGetExpiryTime();

    public native String nativeGetExt();

    public native long nativeGetLatestTime();

    public native String nativeGetPublisher();

    public native Map<String, Integer> nativeGetStatusList();

    public native void nativeInit(String str);
}
