package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public class EMARHttpCallback extends EMABase {
    public EMARHttpCallback() {
        nativeInit();
    }

    public EMARHttpCallback(EMARHttpCallback eMARHttpCallback) {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public native void nativeInit(EMARHttpCallback eMARHttpCallback);

    public native void native_onProgress(double d3, double d4);

    public void onProgress(double d3, double d4) {
        native_onProgress(d3, d4);
    }
}
