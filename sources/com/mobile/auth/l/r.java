package com.mobile.auth.l;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.util.Log;

/* loaded from: classes4.dex */
public class r {

    /* renamed from: a, reason: collision with root package name */
    private static r f10440a;

    /* renamed from: b, reason: collision with root package name */
    private ConnectivityManager f10441b;

    /* renamed from: c, reason: collision with root package name */
    private Network f10442c;

    /* renamed from: d, reason: collision with root package name */
    private ConnectivityManager.NetworkCallback f10443d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f10444e;

    public interface a {
        void a(Network network);
    }

    private r(Context context) {
        try {
            this.f10441b = (ConnectivityManager) context.getSystemService("connectivity");
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    public static r a(Context context) {
        if (f10440a == null) {
            synchronized (r.class) {
                if (f10440a == null) {
                    f10440a = new r(context);
                }
            }
        }
        return f10440a;
    }

    @TargetApi(21)
    public synchronized void a(final a aVar) {
        NetworkInfo networkInfo;
        ConnectivityManager connectivityManager = this.f10441b;
        if (connectivityManager == null) {
            c.a("WifiNetworkUtils", "mConnectivityManager 为空");
            aVar.a(null);
            return;
        }
        Network network = this.f10442c;
        if (network != null && !this.f10444e && (networkInfo = connectivityManager.getNetworkInfo(network)) != null && networkInfo.isAvailable()) {
            Log.e("HttpUtils", "reuse network: ");
            aVar.a(this.f10442c);
            return;
        }
        ConnectivityManager.NetworkCallback networkCallback = this.f10443d;
        if (networkCallback == null) {
            NetworkRequest networkRequestBuild = new NetworkRequest.Builder().addCapability(12).addTransportType(0).build();
            ConnectivityManager.NetworkCallback networkCallback2 = new ConnectivityManager.NetworkCallback() { // from class: com.mobile.auth.l.r.1
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network2) {
                    try {
                        if (r.this.f10441b.getNetworkCapabilities(network2).hasTransport(0)) {
                            r.this.f10442c = network2;
                            aVar.a(network2);
                            r.this.f10444e = false;
                        } else {
                            c.a("WifiNetworkUtils", "切换失败，未开启数据网络");
                            r.this.f10442c = null;
                            aVar.a(null);
                            r.this.f10441b.unregisterNetworkCallback(r.this.f10443d);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                        r.this.f10442c = null;
                        aVar.a(null);
                    }
                }

                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onLost(Network network2) {
                    r.this.f10444e = true;
                }
            };
            this.f10443d = networkCallback2;
            this.f10441b.requestNetwork(networkRequestBuild, networkCallback2);
            return;
        }
        try {
            this.f10441b.unregisterNetworkCallback(networkCallback);
        } catch (Exception e2) {
            e2.printStackTrace();
            this.f10443d = null;
        }
        Log.e("HttpUtils", "clear: ");
        NetworkRequest networkRequestBuild2 = new NetworkRequest.Builder().addCapability(12).addTransportType(0).build();
        ConnectivityManager.NetworkCallback networkCallback22 = new ConnectivityManager.NetworkCallback() { // from class: com.mobile.auth.l.r.1
            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onAvailable(Network network2) {
                try {
                    if (r.this.f10441b.getNetworkCapabilities(network2).hasTransport(0)) {
                        r.this.f10442c = network2;
                        aVar.a(network2);
                        r.this.f10444e = false;
                    } else {
                        c.a("WifiNetworkUtils", "切换失败，未开启数据网络");
                        r.this.f10442c = null;
                        aVar.a(null);
                        r.this.f10441b.unregisterNetworkCallback(r.this.f10443d);
                    }
                } catch (Exception e22) {
                    e22.printStackTrace();
                    r.this.f10442c = null;
                    aVar.a(null);
                }
            }

            @Override // android.net.ConnectivityManager.NetworkCallback
            public void onLost(Network network2) {
                r.this.f10444e = true;
            }
        };
        this.f10443d = networkCallback22;
        try {
            this.f10441b.requestNetwork(networkRequestBuild2, networkCallback22);
        } catch (Exception e3) {
            e3.printStackTrace();
            aVar.a(null);
        }
        return;
    }

    public boolean a() {
        return this.f10442c != null;
    }

    public void b() {
        ConnectivityManager connectivityManager = this.f10441b;
        if (connectivityManager == null) {
            return;
        }
        try {
            ConnectivityManager.NetworkCallback networkCallback = this.f10443d;
            if (networkCallback == null) {
                return;
            }
            connectivityManager.unregisterNetworkCallback(networkCallback);
            this.f10443d = null;
            this.f10442c = null;
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
