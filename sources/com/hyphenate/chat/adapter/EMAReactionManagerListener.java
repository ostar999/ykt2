package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public abstract class EMAReactionManagerListener extends EMABase implements EMAReactionManagerListenerInterface {
    public EMAReactionManagerListener() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public void onMessageReactionDidChange(List<EMAMessageReactionChange> list) {
    }
}
