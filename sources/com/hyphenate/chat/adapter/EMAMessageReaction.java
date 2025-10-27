package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public class EMAMessageReaction extends EMABase {
    public EMAMessageReaction() {
        nativeInit();
    }

    public EMAMessageReaction(EMAMessageReaction eMAMessageReaction) {
        nativeInit(eMAMessageReaction);
    }

    public int count() {
        return nativeCount();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native int nativeCount();

    public native void nativeFinalize();

    public native void nativeInit();

    public native void nativeInit(EMAMessageReaction eMAMessageReaction);

    public native String nativeReaction();

    public native boolean nativeState();

    public native List<String> nativeUserList();

    public String reaction() {
        return nativeReaction();
    }

    public boolean state() {
        return nativeState();
    }

    public List<String> userList() {
        return nativeUserList();
    }
}
