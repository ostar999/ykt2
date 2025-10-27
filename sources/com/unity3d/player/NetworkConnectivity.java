package com.unity3d.player;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;

/* loaded from: classes6.dex */
public class NetworkConnectivity extends Activity {

    /* renamed from: a, reason: collision with root package name */
    private final int f23896a = 0;

    /* renamed from: b, reason: collision with root package name */
    private final int f23897b = 1;

    /* renamed from: c, reason: collision with root package name */
    private final int f23898c = 2;

    /* renamed from: d, reason: collision with root package name */
    private int f23899d;

    /* renamed from: e, reason: collision with root package name */
    private ConnectivityManager f23900e;

    /* renamed from: f, reason: collision with root package name */
    private final ConnectivityManager.NetworkCallback f23901f;

    public NetworkConnectivity(Context context) {
        this.f23899d = 0;
        ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.unity3d.player.NetworkConnectivity.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onAvailable(Network network) {
                super.onAvailable(network);
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                NetworkConnectivity networkConnectivity;
                int i2;
                super.onCapabilitiesChanged(network, networkCapabilities);
                if (networkCapabilities.hasTransport(0)) {
                    networkConnectivity = NetworkConnectivity.this;
                    i2 = 1;
                } else {
                    networkConnectivity = NetworkConnectivity.this;
                    i2 = 2;
                }
                networkConnectivity.f23899d = i2;
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onLost(Network network) {
                super.onLost(network);
                NetworkConnectivity.this.f23899d = 0;
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public final void onUnavailable() {
                super.onUnavailable();
                NetworkConnectivity.this.f23899d = 0;
            }
        };
        this.f23901f = networkCallback;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        this.f23900e = connectivityManager;
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
        NetworkInfo activeNetworkInfo = this.f23900e.getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return;
        }
        this.f23899d = activeNetworkInfo.getType() != 0 ? 2 : 1;
    }

    public final int a() {
        return this.f23899d;
    }

    public final void b() {
        this.f23900e.unregisterNetworkCallback(this.f23901f);
    }
}
