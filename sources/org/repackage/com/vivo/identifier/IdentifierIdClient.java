package org.repackage.com.vivo.identifier;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.exoplayer2.ExoPlayer;

/* loaded from: classes9.dex */
public class IdentifierIdClient {
    private static String A = null;
    private static volatile IdentifierIdClient B = null;
    private static volatile DataBaseOperation C = null;

    /* renamed from: a, reason: collision with root package name */
    private static final String f28044a = "VMS_IDLG_SDK_Client";

    /* renamed from: b, reason: collision with root package name */
    private static final String f28045b = "content://com.vivo.vms.IdProvider/IdentifierId";

    /* renamed from: c, reason: collision with root package name */
    private static final String f28046c = "persist.sys.identifierid.supported";

    /* renamed from: d, reason: collision with root package name */
    private static final String f28047d = "appid";

    /* renamed from: e, reason: collision with root package name */
    private static final String f28048e = "type";

    /* renamed from: f, reason: collision with root package name */
    private static final String f28049f = "OAID";

    /* renamed from: g, reason: collision with root package name */
    private static final String f28050g = "VAID";

    /* renamed from: h, reason: collision with root package name */
    private static final String f28051h = "AAID";

    /* renamed from: i, reason: collision with root package name */
    private static final int f28052i = 0;

    /* renamed from: j, reason: collision with root package name */
    private static final int f28053j = 1;

    /* renamed from: k, reason: collision with root package name */
    private static final int f28054k = 2;

    /* renamed from: l, reason: collision with root package name */
    private static final int f28055l = 4;

    /* renamed from: m, reason: collision with root package name */
    private static final int f28056m = 11;

    /* renamed from: n, reason: collision with root package name */
    private static final int f28057n = 2000;

    /* renamed from: o, reason: collision with root package name */
    private static Context f28058o = null;

    /* renamed from: p, reason: collision with root package name */
    private static boolean f28059p = false;

    /* renamed from: q, reason: collision with root package name */
    private static IdentifierIdObserver f28060q;

    /* renamed from: r, reason: collision with root package name */
    private static IdentifierIdObserver f28061r;

    /* renamed from: s, reason: collision with root package name */
    private static IdentifierIdObserver f28062s;

    /* renamed from: t, reason: collision with root package name */
    private static Object f28063t = new Object();

    /* renamed from: u, reason: collision with root package name */
    private static HandlerThread f28064u;

    /* renamed from: v, reason: collision with root package name */
    private static Handler f28065v;

    /* renamed from: w, reason: collision with root package name */
    private static String f28066w;

    /* renamed from: x, reason: collision with root package name */
    private static String f28067x;

    /* renamed from: y, reason: collision with root package name */
    private static String f28068y;

    /* renamed from: z, reason: collision with root package name */
    private static String f28069z;

    private IdentifierIdClient() {
    }

    public static IdentifierIdClient a(Context context) {
        if (B == null) {
            synchronized (IdentifierIdClient.class) {
                f28058o = context.getApplicationContext();
                B = new IdentifierIdClient();
            }
        }
        if (C == null) {
            synchronized (IdentifierIdClient.class) {
                f28058o = context.getApplicationContext();
                g();
                C = new DataBaseOperation(f28058o);
                d();
            }
        }
        return B;
    }

    public static void d() {
        f28059p = "1".equals(a(f28046c, "0"));
    }

    private static void g() {
        HandlerThread handlerThread = new HandlerThread("SqlWorkThread");
        f28064u = handlerThread;
        handlerThread.start();
        f28065v = new Handler(f28064u.getLooper()) { // from class: org.repackage.com.vivo.identifier.IdentifierIdClient.1
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what != 11) {
                    Log.e(IdentifierIdClient.f28044a, "message type valid");
                    return;
                }
                String unused = IdentifierIdClient.f28066w = IdentifierIdClient.C.a(message.getData().getInt("type"), message.getData().getString("appid"));
                synchronized (IdentifierIdClient.f28063t) {
                    IdentifierIdClient.f28063t.notify();
                }
            }
        };
    }

    public String b() {
        if (!a()) {
            return null;
        }
        String str = f28067x;
        if (str != null) {
            return str;
        }
        a(0, (String) null);
        if (f28060q == null) {
            a(f28058o, 0, null);
        }
        return f28067x;
    }

    public String c() {
        if (!a()) {
            return null;
        }
        a(4, (String) null);
        return A;
    }

    public String b(String str) {
        if (!a()) {
            return null;
        }
        String str2 = f28069z;
        if (str2 != null) {
            return str2;
        }
        a(2, str);
        if (f28062s == null && f28069z != null) {
            a(f28058o, 2, str);
        }
        return f28069z;
    }

    private void b(int i2, String str) {
        Message messageObtainMessage = f28065v.obtainMessage();
        messageObtainMessage.what = 11;
        Bundle bundle = new Bundle();
        bundle.putInt("type", i2);
        if (i2 == 1 || i2 == 2) {
            bundle.putString("appid", str);
        }
        messageObtainMessage.setData(bundle);
        f28065v.sendMessage(messageObtainMessage);
    }

    public boolean a() {
        return f28059p;
    }

    public String a(String str) {
        if (!a()) {
            return null;
        }
        String str2 = f28068y;
        if (str2 != null) {
            return str2;
        }
        a(1, str);
        if (f28061r == null && f28068y != null) {
            a(f28058o, 1, str);
        }
        return f28068y;
    }

    public void a(int i2, String str) {
        synchronized (f28063t) {
            b(i2, str);
            long jUptimeMillis = SystemClock.uptimeMillis();
            try {
                f28063t.wait(ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            if (SystemClock.uptimeMillis() - jUptimeMillis >= ExoPlayer.DEFAULT_DETACH_SURFACE_TIMEOUT_MS) {
                Log.d(f28044a, "query timeout");
            } else if (i2 == 0) {
                f28067x = f28066w;
                f28066w = null;
            } else if (i2 != 1) {
                if (i2 == 2) {
                    String str2 = f28066w;
                    if (str2 != null) {
                        f28069z = str2;
                        f28066w = null;
                    } else {
                        Log.e(f28044a, "get aaid failed");
                    }
                } else if (i2 != 4) {
                }
                A = f28066w;
                f28066w = null;
            } else {
                String str3 = f28066w;
                if (str3 != null) {
                    f28068y = str3;
                    f28066w = null;
                } else {
                    Log.e(f28044a, "get vaid failed");
                }
            }
        }
    }

    public static String a(String str, String str2) {
        try {
            try {
                Class<?> cls = Class.forName("android.os.SystemProperties");
                return (String) cls.getMethod("get", String.class, String.class).invoke(cls, str, "unknown");
            } catch (Exception e2) {
                e2.printStackTrace();
                return str2;
            }
        } catch (Throwable unused) {
            return str2;
        }
    }

    private static void a(Context context, int i2, String str) {
        if (i2 == 0) {
            f28060q = new IdentifierIdObserver(B, 0, null);
            context.getContentResolver().registerContentObserver(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/OAID"), true, f28060q);
            return;
        }
        if (i2 == 1) {
            f28061r = new IdentifierIdObserver(B, 1, str);
            context.getContentResolver().registerContentObserver(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/VAID_" + str), false, f28061r);
            return;
        }
        if (i2 != 2) {
            return;
        }
        f28062s = new IdentifierIdObserver(B, 2, str);
        context.getContentResolver().registerContentObserver(Uri.parse("content://com.vivo.vms.IdProvider/IdentifierId/AAID_" + str), false, f28062s);
    }
}
