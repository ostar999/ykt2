package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public abstract class EMALogCallbackListener extends EMABase implements EMALogCallbackListenerInterface {
    public EMALogCallbackListener() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public void onLogCallback(String str) {
    }
}
