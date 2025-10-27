package com.plv.foundationsdk.ijk.player.media;

/* loaded from: classes4.dex */
public class PLVPlayerNative {
    private static PLVPlayerNative instance;

    public static PLVPlayerNative getInstance() {
        if (instance == null) {
            instance = new PLVPlayerNative();
        }
        return instance;
    }

    public native long getCurFrameAgoraUserTC(Object obj);
}
