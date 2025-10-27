package com.mobile.auth.c;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.net.InetAddress;

/* loaded from: classes4.dex */
public class j {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9650a = "j";

    /* renamed from: e, reason: collision with root package name */
    private a f9654e;

    /* renamed from: b, reason: collision with root package name */
    private boolean f9651b = false;

    /* renamed from: c, reason: collision with root package name */
    private ConnectivityManager f9652c = null;

    /* renamed from: d, reason: collision with root package name */
    private ConnectivityManager.NetworkCallback f9653d = null;

    /* renamed from: f, reason: collision with root package name */
    private long f9655f = 0;

    /* renamed from: g, reason: collision with root package name */
    private long f9656g = 0;

    public interface a {
        void a();

        void a(int i2, String str, long j2);

        void a(Network network, long j2);
    }

    public static int a(String str) {
        try {
            try {
                byte[] address = InetAddress.getByName(str).getAddress();
                return (address[0] & 255) | ((address[3] & 255) << 24) | ((address[2] & 255) << 16) | ((address[1] & 255) << 8);
            } catch (Throwable th) {
                com.mobile.auth.a.a.a(f9650a, "When InetAddress.getByName(),throws exception", th);
                return -1;
            }
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
                return -1;
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
                return -1;
            }
        }
    }

    public static /* synthetic */ long a(j jVar, long j2) {
        try {
            jVar.f9655f = j2;
            return j2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }

    public static /* synthetic */ ConnectivityManager a(j jVar, ConnectivityManager connectivityManager) {
        try {
            jVar.f9652c = connectivityManager;
            return connectivityManager;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static /* synthetic */ String a() {
        try {
            return f9650a;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    @TargetApi(21)
    private void a(Context context) {
        try {
            this.f9655f = 0L;
            this.f9652c = (ConnectivityManager) context.getApplicationContext().getSystemService("connectivity");
            this.f9656g = System.currentTimeMillis();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            builder.addCapability(12);
            builder.addTransportType(0);
            NetworkRequest networkRequestBuild = builder.build();
            ConnectivityManager.NetworkCallback networkCallback = new ConnectivityManager.NetworkCallback() { // from class: com.mobile.auth.c.j.2
                @Override // android.net.ConnectivityManager.NetworkCallback
                public void onAvailable(Network network) {
                    try {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        j jVar = j.this;
                        j.a(jVar, jCurrentTimeMillis - j.d(jVar));
                        j.a(j.this, true);
                        if (j.b(j.this) != null) {
                            j.b(j.this).a(network, j.e(j.this));
                        }
                        if (j.f(j.this) != null) {
                            try {
                                j.f(j.this).unregisterNetworkCallback(this);
                                j.a(j.this, (ConnectivityManager) null);
                            } catch (Throwable th) {
                                com.mobile.auth.a.a.a(j.a(), "switchToMobileForAboveL", th);
                            }
                        }
                    } catch (Throwable th2) {
                        try {
                            ExceptionProcessor.processException(th2);
                        } catch (Throwable th3) {
                            ExceptionProcessor.processException(th3);
                        }
                    }
                }
            };
            this.f9653d = networkCallback;
            this.f9652c.requestNetwork(networkRequestBuild, networkCallback);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ boolean a(j jVar) {
        try {
            return jVar.f9651b;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static /* synthetic */ boolean a(j jVar, boolean z2) {
        try {
            jVar.f9651b = z2;
            return z2;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }

    public static /* synthetic */ a b(j jVar) {
        try {
            return jVar.f9654e;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static String b(String str) {
        try {
            int iIndexOf = str.indexOf("://");
            if (iIndexOf > 0) {
                str = str.substring(iIndexOf + 3);
            }
            int iIndexOf2 = str.indexOf(58);
            if (iIndexOf2 >= 0) {
                str = str.substring(0, iIndexOf2);
            }
            int iIndexOf3 = str.indexOf(47);
            if (iIndexOf3 >= 0) {
                str = str.substring(0, iIndexOf3);
            }
            int iIndexOf4 = str.indexOf(63);
            return iIndexOf4 >= 0 ? str.substring(0, iIndexOf4) : str;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    private void b() {
        ConnectivityManager.NetworkCallback networkCallback;
        try {
            ConnectivityManager connectivityManager = this.f9652c;
            if (connectivityManager == null || (networkCallback = this.f9653d) == null) {
                return;
            }
            try {
                connectivityManager.unregisterNetworkCallback(networkCallback);
            } catch (Throwable th) {
                com.mobile.auth.a.a.a(f9650a, "unregisterNetworkCallback", th);
            }
            this.f9652c = null;
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
            }
        }
    }

    private boolean b(Context context, String str) {
        boolean z2;
        try {
            Class<?> cls = Class.forName("android.net.ConnectivityManager");
            this.f9655f = 0L;
            this.f9656g = System.currentTimeMillis();
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            this.f9652c = connectivityManager;
            if (connectivityManager.getNetworkInfo(5).getState().compareTo(NetworkInfo.State.CONNECTED) != 0) {
                cls.getMethod("startUsingNetworkFeature", Integer.TYPE, String.class).invoke(this.f9652c, 0, "enableHIPRI");
                for (int i2 = 0; i2 < 5; i2++) {
                    try {
                        if (this.f9652c.getNetworkInfo(5).getState().compareTo(NetworkInfo.State.CONNECTED) == 0) {
                            break;
                        }
                        Thread.sleep(500L);
                    } catch (Throwable th) {
                        com.mobile.auth.a.a.a(f9650a, "switchToMobileForUnderL", th);
                    }
                }
            }
            int iA = a(b(str));
            Class<?> cls2 = Integer.TYPE;
            boolean zBooleanValue = ((Boolean) cls.getMethod("requestRouteToHost", cls2, cls2).invoke(this.f9652c, 5, Integer.valueOf(iA))).booleanValue();
            try {
                this.f9655f = System.currentTimeMillis() - this.f9656g;
                com.mobile.auth.a.a.a(f9650a, "Switch network result ： " + zBooleanValue + " (4.x) , expendTime ：" + this.f9655f);
                return zBooleanValue;
            } catch (Throwable th2) {
                z2 = zBooleanValue;
                th = th2;
                try {
                    com.mobile.auth.a.a.a(f9650a, "4.x网络切换异常", th);
                    return z2;
                } catch (Throwable th3) {
                    try {
                        ExceptionProcessor.processException(th3);
                        return false;
                    } catch (Throwable th4) {
                        ExceptionProcessor.processException(th4);
                        return false;
                    }
                }
            }
        } catch (Throwable th5) {
            th = th5;
            z2 = false;
        }
    }

    public static /* synthetic */ void c(j jVar) {
        try {
            jVar.b();
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public static /* synthetic */ long d(j jVar) {
        try {
            return jVar.f9656g;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }

    public static /* synthetic */ long e(j jVar) {
        try {
            return jVar.f9655f;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return -1L;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return -1L;
            }
        }
    }

    public static /* synthetic */ ConnectivityManager f(j jVar) {
        try {
            return jVar.f9652c;
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public void a(final int i2) {
        try {
            r.a().a(new Runnable() { // from class: com.mobile.auth.c.j.1
                @Override // java.lang.Runnable
                public void run() {
                    try {
                        if (i2 > 2500) {
                            try {
                                Thread.sleep(2500L);
                            } catch (Throwable th) {
                                com.mobile.auth.a.a.a(j.a(), "timeoutCheckRunnable exception!", th);
                            }
                            if (!j.a(j.this)) {
                                if (j.b(j.this) != null) {
                                    j.b(j.this).a(80800, "WIFI切换超时", 2500L);
                                }
                                com.mobile.auth.a.a.a(j.a(), "切换网络超时(L)");
                                j.c(j.this);
                                return;
                            }
                        }
                        try {
                            int i3 = i2;
                            if (i3 > 2500) {
                                i3 -= 2500;
                            }
                            Thread.sleep(i3);
                        } catch (Throwable th2) {
                            com.mobile.auth.a.a.a(j.a(), "timeoutCheckRunnable exception!", th2);
                        }
                        if (j.b(j.this) != null) {
                            if (j.a(j.this)) {
                                j.b(j.this).a();
                            } else {
                                j.b(j.this).a(80800, "WIFI切换超时", 2500L);
                                j.c(j.this);
                            }
                        }
                    } catch (Throwable th3) {
                        try {
                            ExceptionProcessor.processException(th3);
                        } catch (Throwable th4) {
                            ExceptionProcessor.processException(th4);
                        }
                    }
                }
            });
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
            }
        }
    }

    public void a(Context context, a aVar) {
        try {
            this.f9654e = aVar;
            try {
                a(context);
            } catch (Throwable th) {
                com.mobile.auth.a.a.a(f9650a, "switchToMobileForAboveL", th);
                if (this.f9654e != null) {
                    this.f9654e.a(80801, "WIFI切换异常", -1L);
                }
            }
        } catch (Throwable th2) {
            try {
                ExceptionProcessor.processException(th2);
            } catch (Throwable th3) {
                ExceptionProcessor.processException(th3);
            }
        }
    }

    public boolean a(Context context, String str) {
        try {
            return b(context, str);
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return false;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return false;
            }
        }
    }
}
