package com.plv.foundationsdk.net;

import androidx.annotation.NonNull;

/* loaded from: classes4.dex */
public class PLVNetworkDisconnectRegistry {
    static NetworkDisconnectCallback disconnectCallback;

    public interface NetworkDisconnectCallback {
        void networkDisconnect(String str);
    }

    public static void addNetworkDisconnect(@NonNull NetworkDisconnectCallback networkDisconnectCallback) {
        disconnectCallback = networkDisconnectCallback;
    }

    public static void dispatchNetworkDisconnectMessage(String str) {
        NetworkDisconnectCallback networkDisconnectCallback = disconnectCallback;
        if (networkDisconnectCallback != null) {
            networkDisconnectCallback.networkDisconnect(str);
        }
    }

    public static void removeNetworkDisconnect(NetworkDisconnectCallback networkDisconnectCallback) {
        disconnectCallback = null;
    }
}
