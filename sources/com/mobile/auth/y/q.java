package com.mobile.auth.y;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkRequest;
import android.os.Build;
import android.text.TextUtils;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes4.dex */
public class q {

    /* renamed from: f, reason: collision with root package name */
    private static q f10628f;

    /* renamed from: a, reason: collision with root package name */
    private Network f10629a = null;

    /* renamed from: b, reason: collision with root package name */
    private ConnectivityManager.NetworkCallback f10630b = null;

    /* renamed from: c, reason: collision with root package name */
    private ConnectivityManager f10631c = null;

    /* renamed from: d, reason: collision with root package name */
    private List<a> f10632d = new ArrayList();

    /* renamed from: e, reason: collision with root package name */
    private Timer f10633e = null;

    public interface a {
        void a(boolean z2, Object obj);
    }

    private q() {
    }

    public static /* synthetic */ Network a(q qVar) {
        try {
            return qVar.f10629a;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static /* synthetic */ Network a(q qVar, Network network) {
        try {
            qVar.f10629a = network;
            return network;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static q a() {
        try {
            if (f10628f == null) {
                synchronized (q.class) {
                    if (f10628f == null) {
                        f10628f = new q();
                    }
                }
            }
            return f10628f;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    private synchronized void a(a aVar) {
        try {
            try {
                this.f10632d.add(aVar);
            } catch (Exception unused) {
                t.b();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static /* synthetic */ void a(q qVar, boolean z2, Network network) {
        try {
            qVar.a(z2, network);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private synchronized void a(boolean z2, Network network) {
        try {
            try {
                Timer timer = this.f10633e;
                if (timer != null) {
                    timer.cancel();
                    this.f10633e = null;
                }
                Iterator<a> it = this.f10632d.iterator();
                while (it.hasNext()) {
                    it.next().a(z2, network);
                }
                this.f10632d.clear();
            } catch (Exception unused) {
                t.b();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static /* synthetic */ ConnectivityManager b(q qVar) {
        try {
            return qVar.f10631c;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    @TargetApi(21)
    public final synchronized void a(Context context, a aVar) {
        try {
            Network network = this.f10629a;
            if (network != null) {
                aVar.a(true, network);
                return;
            }
            a(aVar);
            if (this.f10630b == null || this.f10632d.size() < 2) {
                try {
                    this.f10631c = (ConnectivityManager) context.getSystemService("connectivity");
                    NetworkRequest.Builder builder = new NetworkRequest.Builder();
                    builder.addTransportType(0);
                    builder.addCapability(12);
                    NetworkRequest networkRequestBuild = builder.build();
                    this.f10630b = new ConnectivityManager.NetworkCallback() { // from class: com.mobile.auth.y.q.1
                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onAvailable(Network network2) {
                            try {
                                super.onAvailable(network2);
                                t.c("Network onAvailable");
                                q.a(q.this, network2);
                                q.a(q.this, true, network2);
                                try {
                                    String extraInfo = q.b(q.this).getNetworkInfo(q.a(q.this)).getExtraInfo();
                                    if (TextUtils.isEmpty(extraInfo)) {
                                        return;
                                    }
                                    u.d(extraInfo);
                                } catch (Exception unused) {
                                    t.b();
                                }
                            } catch (Throwable th) {
                                ExceptionProcessor.processException(th);
                            }
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onLost(Network network2) {
                            try {
                                super.onLost(network2);
                                t.c("Network onLost");
                                q.this.b();
                            } catch (Throwable th) {
                                ExceptionProcessor.processException(th);
                            }
                        }

                        @Override // android.net.ConnectivityManager.NetworkCallback
                        public final void onUnavailable() {
                            try {
                                super.onUnavailable();
                                t.c("Network onUnavailable");
                                q.a(q.this, false, null);
                                q.this.b();
                            } catch (Throwable th) {
                                ExceptionProcessor.processException(th);
                            }
                        }
                    };
                    int i2 = 3000;
                    if (u.g() < 3000) {
                        i2 = 2000;
                    }
                    if (Build.VERSION.SDK_INT >= 26) {
                        this.f10631c.requestNetwork(networkRequestBuild, this.f10630b, i2);
                        return;
                    }
                    Timer timer = new Timer();
                    this.f10633e = timer;
                    timer.schedule(new TimerTask() { // from class: com.mobile.auth.y.q.2
                        @Override // java.util.TimerTask, java.lang.Runnable
                        public final void run() {
                            try {
                                q.a(q.this, false, null);
                            } catch (Throwable th) {
                                ExceptionProcessor.processException(th);
                            }
                        }
                    }, i2);
                    this.f10631c.requestNetwork(networkRequestBuild, this.f10630b);
                } catch (Exception unused) {
                    t.b();
                    a(false, (Network) null);
                }
            }
        } finally {
        }
    }

    public final synchronized void b() {
        ConnectivityManager.NetworkCallback networkCallback;
        try {
            try {
                Timer timer = this.f10633e;
                if (timer != null) {
                    timer.cancel();
                    this.f10633e = null;
                }
                ConnectivityManager connectivityManager = this.f10631c;
                if (connectivityManager != null && (networkCallback = this.f10630b) != null) {
                    connectivityManager.unregisterNetworkCallback(networkCallback);
                }
                this.f10631c = null;
                this.f10630b = null;
                this.f10629a = null;
                this.f10632d.clear();
            } catch (Exception unused) {
                t.b();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
