package com.hyphenate.chat.adapter.message;

import java.util.Map;

/* loaded from: classes4.dex */
public class EMACmdMessageBody extends EMAMessageBody {
    private EMACmdMessageBody() {
        nativeInit("");
    }

    public EMACmdMessageBody(EMACmdMessageBody eMACmdMessageBody) {
        nativeInit(eMACmdMessageBody);
    }

    public EMACmdMessageBody(String str) {
        nativeInit(str);
    }

    public String action() {
        return nativeAction();
    }

    public void deliverOnlineOnly(boolean z2) {
        nativeDeliverOnlineOnly(z2);
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public boolean isDeliverOnlineOnly() {
        return nativeIsDeliverOnlineOnly();
    }

    public native String nativeAction();

    public native void nativeDeliverOnlineOnly(boolean z2);

    public native void nativeFinalize();

    public native void nativeInit(EMACmdMessageBody eMACmdMessageBody);

    public native void nativeInit(String str);

    public native boolean nativeIsDeliverOnlineOnly();

    public native Map<String, String> nativeParams();

    public native void nativeSetAction(String str);

    public native void nativeSetParams(Map<String, String> map);

    public Map<String, String> params() {
        return nativeParams();
    }

    public void setAction(String str) {
        nativeSetAction(str);
    }

    public void setParams(Map<String, String> map) {
        nativeSetParams(map);
    }
}
