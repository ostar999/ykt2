package com.plv.livescenes.socket;

/* loaded from: classes5.dex */
public class PLVSocketWrapper extends com.plv.socket.impl.PLVSocketWrapper {
    private static volatile PLVSocketWrapper socketWrapper;

    private PLVSocketWrapper() {
    }

    public static PLVSocketWrapper getInstance() {
        if (socketWrapper == null) {
            synchronized (PLVSocketWrapper.class) {
                if (socketWrapper == null) {
                    socketWrapper = new PLVSocketWrapper();
                }
            }
        }
        return socketWrapper;
    }
}
