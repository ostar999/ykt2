package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public abstract class EMAConnectionListener extends EMABase implements EMAConnectionListenerInterface {
    public EMAConnectionListener() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public void onConnected() {
    }

    public void onDisconnected(int i2) {
    }

    public void onReceiveToken(String str, long j2) {
    }

    public void onTokenNotification(int i2) {
    }

    public boolean verifyServerCert(List<String> list, String str) {
        return true;
    }
}
