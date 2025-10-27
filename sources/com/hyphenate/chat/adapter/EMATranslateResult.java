package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public class EMATranslateResult extends EMABase {
    public EMATranslateResult(String str) {
        nativeInit(str);
    }

    public String conversationId() {
        return nativeConversationId();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public String msgId() {
        return nativeMsgId();
    }

    public native String nativeConversationId();

    public native void nativeFinalize();

    public native void nativeInit(String str);

    public native String nativeMsgId();

    public native void nativeSetConversationId(String str);

    public native void nativeSetShowTranslation(boolean z2);

    public native void nativeSetTranslateTime(int i2);

    public native void nativeSetTranslations(String str);

    public native boolean nativeShowTranslation();

    public native int nativeTranslateTime();

    public native String nativeTranslations();

    public void setConversationId(String str) {
        nativeSetConversationId(str);
    }

    public void setShowTranslation(boolean z2) {
        nativeSetShowTranslation(z2);
    }

    public void setTranslateTime(int i2) {
        nativeSetTranslateTime(i2);
    }

    public void setTranslations(String str) {
        nativeSetTranslations(str);
    }

    public boolean showTranslation() {
        return nativeShowTranslation();
    }

    public int translateTime() {
        return nativeTranslateTime();
    }

    public String translations() {
        return nativeTranslations();
    }
}
