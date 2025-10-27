package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public class EMAMultiDeviceListener extends EMABase implements EMAMultiDeviceListenerInterface {
    public EMAMultiDeviceListener() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public void onContactEvent(int i2, String str, String str2) {
    }

    public void onGroupEvent(int i2, String str, List<String> list) {
    }

    public void onThreadEvent(int i2, String str, List<String> list) {
    }
}
