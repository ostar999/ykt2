package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public abstract class EMANetCallback extends EMABase {
    public EMANetCallback() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public abstract int getNetState();

    public native void nativeFinalize();

    public native void nativeInit();
}
