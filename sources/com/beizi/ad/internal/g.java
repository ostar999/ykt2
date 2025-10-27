package com.beizi.ad.internal;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.webkit.WebView;
import com.beizi.ad.RewardedVideoAd;
import com.beizi.ad.internal.c.f;
import com.beizi.ad.internal.utilities.HaoboLog;
import com.beizi.ad.internal.utilities.SPUtils;
import com.beizi.ad.internal.utilities.StringUtil;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import org.eclipse.jetty.util.URIUtil;

/* loaded from: classes2.dex */
public class g {
    private static boolean F = false;
    private static boolean G = false;

    /* renamed from: f, reason: collision with root package name */
    public static String f4172f = null;

    /* renamed from: g, reason: collision with root package name */
    public static String f4173g = "";

    /* renamed from: h, reason: collision with root package name */
    public static String f4174h = "";

    /* renamed from: l, reason: collision with root package name */
    private static String f4175l = "USED_AD_UNIT_IDS_KEY";

    /* renamed from: m, reason: collision with root package name */
    private static g f4176m = null;

    /* renamed from: n, reason: collision with root package name */
    private static String f4177n = "BeiZiImpl";

    /* renamed from: o, reason: collision with root package name */
    private static String f4178o;
    private RewardedVideoAd A;
    private DisplayMetrics B;
    private com.beizi.ad.internal.c.f E;

    /* renamed from: i, reason: collision with root package name */
    public Context f4184i;

    /* renamed from: j, reason: collision with root package name */
    public float f4185j;

    /* renamed from: k, reason: collision with root package name */
    public float f4186k;

    /* renamed from: y, reason: collision with root package name */
    private float f4196y;

    /* renamed from: z, reason: collision with root package name */
    private boolean f4197z;

    /* renamed from: a, reason: collision with root package name */
    public boolean f4179a = false;

    /* renamed from: b, reason: collision with root package name */
    public String f4180b = null;

    /* renamed from: c, reason: collision with root package name */
    public boolean f4181c = false;

    /* renamed from: d, reason: collision with root package name */
    public String f4182d = "";

    /* renamed from: e, reason: collision with root package name */
    public HashMap<String, String> f4183e = new HashMap<>();

    /* renamed from: p, reason: collision with root package name */
    private HashSet<String> f4187p = new HashSet<>();

    /* renamed from: q, reason: collision with root package name */
    private HashSet<String> f4188q = new HashSet<>();

    /* renamed from: r, reason: collision with root package name */
    private HashSet<String> f4189r = new HashSet<>();

    /* renamed from: s, reason: collision with root package name */
    private HashSet<String> f4190s = new HashSet<>();

    /* renamed from: t, reason: collision with root package name */
    private HashSet<String> f4191t = new HashSet<>();

    /* renamed from: u, reason: collision with root package name */
    private HashSet<String> f4192u = new HashSet<>();

    /* renamed from: v, reason: collision with root package name */
    private Handler f4193v = new Handler(Looper.getMainLooper()) { // from class: com.beizi.ad.internal.g.1
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            int i2 = message.what;
        }
    };

    /* renamed from: w, reason: collision with root package name */
    private Handler f4194w = null;

    /* renamed from: x, reason: collision with root package name */
    private HandlerThread f4195x = null;
    private boolean C = false;
    private boolean D = false;

    /* renamed from: com.beizi.ad.internal.g$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {

        /* renamed from: a, reason: collision with root package name */
        static final /* synthetic */ int[] f4200a;

        static {
            int[] iArr = new int[l.values().length];
            f4200a = iArr;
            try {
                iArr[l.SPLASH.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f4200a[l.BANNER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f4200a[l.INTERSTITIAL.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f4200a[l.NATIVE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f4200a[l.REWARDEDVIDEO.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private com.beizi.ad.internal.c.f o() {
        Context context = this.f4184i;
        if (context == null) {
            return null;
        }
        return new f.a(context).a(52428800L).a();
    }

    private void p() {
        try {
            Class.forName("android.content.pm.PackageParser$Package").getDeclaredConstructor(String.class).setAccessible(true);
        } catch (Exception e2) {
            e2.printStackTrace();
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread", new Class[0]);
            declaredMethod.setAccessible(true);
            Object objInvoke = declaredMethod.invoke(null, new Object[0]);
            Field declaredField = cls.getDeclaredField("mHiddenApiWarningShown");
            declaredField.setAccessible(true);
            declaredField.setBoolean(objInvoke, true);
        } catch (Exception e3) {
            e3.printStackTrace();
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public g a(String str) {
        f4178o = str;
        return f4176m;
    }

    public void a(String str, boolean z2) {
    }

    public com.beizi.ad.internal.c.f b() {
        if (this.f4184i == null) {
            return null;
        }
        com.beizi.ad.internal.c.f fVar = this.E;
        if (fVar != null) {
            return fVar;
        }
        com.beizi.ad.internal.c.f fVarO = o();
        this.E = fVarO;
        return fVarO;
    }

    public Handler c() {
        if (this.f4194w == null) {
            if (this.f4195x == null) {
                HandlerThread handlerThread = new HandlerThread("BackgroundHandler");
                this.f4195x = handlerThread;
                handlerThread.start();
            }
            this.f4194w = new Handler(this.f4195x.getLooper());
        }
        return this.f4194w;
    }

    public String d() {
        return f4172f;
    }

    public Context e() {
        return this.f4184i;
    }

    public String f() {
        String strA = com.beizi.ad.a.a.b.a("aHR0cDovL2FwaS5odHAuYWQtc2NvcGUuY29tLmNuOjQ1NjAw");
        return TextUtils.isEmpty(strA) ? "" : this.f4179a ? strA.replace(URIUtil.HTTP_COLON, URIUtil.HTTPS_COLON) : strA;
    }

    public String g() {
        if (!TextUtils.isEmpty(f4178o)) {
            return f4178o;
        }
        String strA = com.beizi.ad.a.a.b.a("aHR0cDovL2FwaS5odHAuYWQtc2NvcGUuY29tLmNuOjQ1NjAw");
        return TextUtils.isEmpty(strA) ? "" : this.f4179a ? strA.replace(URIUtil.HTTP_COLON, URIUtil.HTTPS_COLON) : strA;
    }

    public float h() {
        return this.f4185j;
    }

    public float i() {
        return this.f4186k;
    }

    public float j() {
        return Math.max(this.f4185j, this.f4186k);
    }

    public DisplayMetrics k() {
        return this.B;
    }

    public HashSet<String> l() {
        return this.f4192u;
    }

    public boolean m() {
        return F;
    }

    public String n() {
        if (!TextUtils.isEmpty(this.f4180b)) {
            return this.f4180b;
        }
        return f() + "/mb/sdk0/json";
    }

    public static g a() {
        g gVar;
        synchronized (g.class) {
            if (f4176m == null) {
                f4176m = new g();
            }
            gVar = f4176m;
        }
        return gVar;
    }

    public void b(String str) {
        this.f4180b = str;
    }

    public void a(Context context, String str) {
        synchronized (g.class) {
            try {
                if (context != null) {
                    Log.i("lance", "SDK_VERSION:3.4.20.26");
                    this.f4184i = context.getApplicationContext();
                    try {
                        try {
                            HaoboLog.setErrorContext(context.getApplicationContext());
                            f4172f = str;
                            String string = SPUtils.getString(this.f4184i, "userAgent");
                            if (!TextUtils.isEmpty(string)) {
                                a().f4182d = string;
                            } else {
                                WebView webView = new WebView(context);
                                WebView.setWebContentsDebuggingEnabled(false);
                                webView.getSettings().setSavePassword(false);
                                a().f4182d = webView.getSettings().getUserAgentString();
                                if (!TextUtils.isEmpty(a().f4182d)) {
                                    SPUtils.put(this.f4184i, "userAgent", a().f4182d);
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            a().f4182d = "";
                        }
                    } catch (Throwable th) {
                        th.printStackTrace();
                        a().f4182d = "";
                    }
                    DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
                    this.B = displayMetrics;
                    int i2 = displayMetrics.widthPixels;
                    int i3 = displayMetrics.heightPixels;
                    if (i2 < i3) {
                        this.f4185j = i2 / 720.0f;
                        this.f4186k = i3 / 1280.0f;
                    } else {
                        this.f4185j = i3 / 720.0f;
                        this.f4186k = i2 / 1280.0f;
                    }
                    if (!this.D) {
                        com.beizi.ad.a.d.a(this.f4184i).a(new com.beizi.ad.a.c() { // from class: com.beizi.ad.internal.g.2
                            @Override // com.beizi.ad.a.c
                            public void a(long j2, long j3) {
                                com.beizi.ad.a.a.i.a("lance", "在线时长:" + (j3 - j2));
                                SPUtils.put(g.this.f4184i, "startTime", Long.valueOf(j2));
                                SPUtils.put(g.this.f4184i, "endTime", Long.valueOf(j3));
                            }

                            @Override // com.beizi.ad.a.c
                            public void a() {
                                com.beizi.ad.a.a.i.a("lance", "上报活跃量");
                            }
                        });
                    }
                    this.D = true;
                    if (Build.VERSION.SDK_INT >= 28) {
                        p();
                    }
                } else {
                    throw new IllegalArgumentException("Context cannot be null.");
                }
            } catch (Throwable th2) {
                throw th2;
            }
        }
    }

    public RewardedVideoAd a(Context context) {
        p pVar;
        synchronized (g.class) {
            pVar = new p(context);
            this.A = pVar;
        }
        return pVar;
    }

    public void a(float f2) {
        q.a(0.0f <= f2 && f2 <= 1.0f, "The app volume must be a value between 0 and 1 inclusive.");
        q.a(f4172f, (Object) "BeiZi.initialize() must be called prior to setting the app volume.");
        this.f4196y = f2;
    }

    public void a(boolean z2) {
        q.a(f4172f, (Object) "BeiZi.initialize() must be called prior to setting the app volume.");
        this.f4197z = z2;
    }

    public void a(l lVar, String str) {
        if (StringUtil.isEmpty(str)) {
            return;
        }
        int i2 = AnonymousClass3.f4200a[lVar.ordinal()];
        if (i2 == 1) {
            this.f4187p.add(str);
            return;
        }
        if (i2 == 2) {
            this.f4188q.add(str);
            return;
        }
        if (i2 == 3) {
            this.f4189r.add(str);
        } else if (i2 == 4) {
            this.f4190s.add(str);
        } else {
            if (i2 != 5) {
                return;
            }
            this.f4191t.add(str);
        }
    }
}
