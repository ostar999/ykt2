package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public abstract class EMAPresenceManagerListener extends EMABase implements EMAPresenceManagerListenerInterface {
    public EMAPresenceManagerListener() {
        nativeInit();
    }

    public void finalize() throws Throwable {
        nativeFinalize();
        super.finalize();
    }

    public native void nativeFinalize();

    public native void nativeInit();

    public void onPresenceUpdated(List<EMAPresence> list) {
    }
}
