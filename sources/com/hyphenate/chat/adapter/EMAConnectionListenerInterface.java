package com.hyphenate.chat.adapter;

import java.util.List;

/* loaded from: classes4.dex */
public interface EMAConnectionListenerInterface {
    void onConnected();

    void onDisconnected(int i2);

    void onReceiveToken(String str, long j2);

    void onTokenNotification(int i2);

    boolean verifyServerCert(List<String> list, String str);
}
