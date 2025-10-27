package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public class EMAMessageReactionChange extends EMABase {
    public EMAMessageReactionChange() {
        nativeInit();
    }

    public EMAMessageReactionChange(EMAMessageReactionChange eMAMessageReactionChange) {
        nativeInit(eMAMessageReactionChange);
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public String getFrom() {
        return nativeGetFrom();
    }

    public String getMessageId() {
        return nativeGetMessageId();
    }

    public List<EMAMessageReaction> getMessageReactionList() {
        return nativeGetMessageReactionList();
    }

    public String getTo() {
        return nativeGetTo();
    }

    public native void nativeFinalize();

    public native String nativeGetFrom();

    public native String nativeGetMessageId();

    public native List<EMAMessageReaction> nativeGetMessageReactionList();

    public native String nativeGetTo();

    public native void nativeInit();

    public native void nativeInit(EMAMessageReactionChange eMAMessageReactionChange);
}
