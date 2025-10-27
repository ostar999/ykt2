package com.hyphenate.chat.adapter;

/* loaded from: classes4.dex */
public abstract class EMAContactListener extends EMABase implements EMAContactListenerInterface {
    public EMAContactListener() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public void onContactAdded(String str) {
    }

    public void onContactAgreed(String str) {
    }

    public void onContactDeleted(String str) {
    }

    public void onContactInvited(String str, String str2) {
    }

    public void onContactRefused(String str) {
    }
}
