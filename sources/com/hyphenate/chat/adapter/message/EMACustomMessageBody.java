package com.hyphenate.chat.adapter.message;

import java.util.Map;

/* loaded from: classes4.dex */
public class EMACustomMessageBody extends EMAMessageBody {
    private EMACustomMessageBody() {
        nativeInit("");
    }

    public EMACustomMessageBody(EMACustomMessageBody eMACustomMessageBody) {
        nativeInit(eMACustomMessageBody);
    }

    public EMACustomMessageBody(String str) {
        nativeInit(str);
    }

    public String event() {
        return nativeEvent();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native String nativeEvent();

    public native void nativeFinalize();

    public native void nativeInit(EMACustomMessageBody eMACustomMessageBody);

    public native void nativeInit(String str);

    public native Map<String, String> nativeParams();

    public native void nativeSetEvent(String str);

    public native void nativeSetParams(Map<String, String> map);

    public Map<String, String> params() {
        return nativeParams();
    }

    public void setEvent(String str) {
        nativeSetEvent(str);
    }

    public void setParams(Map<String, String> map) {
        nativeSetParams(map);
    }
}
