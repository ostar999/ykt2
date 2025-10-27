package com.hyphenate;

/* loaded from: classes4.dex */
public interface EMConnectionListener {
    void onConnected();

    void onDisconnected(int i2);

    void onLogout(int i2);

    void onTokenExpired();

    void onTokenWillExpire();
}
