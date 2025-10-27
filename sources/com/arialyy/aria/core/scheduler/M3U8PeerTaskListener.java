package com.arialyy.aria.core.scheduler;

/* loaded from: classes2.dex */
public interface M3U8PeerTaskListener extends TaskInternalListenerInterface {
    void onPeerComplete(String str, String str2, int i2);

    void onPeerFail(String str, String str2, int i2);

    void onPeerStart(String str, String str2, int i2);
}
