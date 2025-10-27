package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.utils.TbsLog;
import java.io.File;

/* loaded from: classes6.dex */
class g {

    /* renamed from: a, reason: collision with root package name */
    static int f21179a = 0;

    /* renamed from: b, reason: collision with root package name */
    static boolean f21180b = false;

    /* renamed from: e, reason: collision with root package name */
    private static g f21181e = null;

    /* renamed from: h, reason: collision with root package name */
    private static int f21182h = 0;

    /* renamed from: j, reason: collision with root package name */
    private static int f21183j = 3;

    /* renamed from: l, reason: collision with root package name */
    private static String f21184l;

    /* renamed from: c, reason: collision with root package name */
    private u f21185c = null;

    /* renamed from: d, reason: collision with root package name */
    private u f21186d = null;

    /* renamed from: f, reason: collision with root package name */
    private boolean f21187f = false;

    /* renamed from: g, reason: collision with root package name */
    private boolean f21188g = false;

    /* renamed from: i, reason: collision with root package name */
    private String f21189i = "";

    /* renamed from: k, reason: collision with root package name */
    private File f21190k = null;

    private g() {
    }

    public static g a(boolean z2) {
        if (f21181e == null && z2) {
            synchronized (g.class) {
                if (f21181e == null) {
                    f21181e = new g();
                }
            }
        }
        return f21181e;
    }

    public static void a(int i2) {
        f21182h = i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:25:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void b(int r6) throws java.io.IOException {
        /*
            r5 = this;
            java.lang.String r6 = java.lang.String.valueOf(r6)
            java.util.Properties r0 = new java.util.Properties
            r0.<init>()
            java.lang.String r1 = com.tencent.smtt.sdk.g.f21184l
            r0.setProperty(r1, r6)
            r6 = 0
            java.io.FileOutputStream r1 = new java.io.FileOutputStream     // Catch: java.io.IOException -> L25 java.io.FileNotFoundException -> L27
            java.io.File r2 = new java.io.File     // Catch: java.io.IOException -> L25 java.io.FileNotFoundException -> L27
            java.io.File r3 = r5.f21190k     // Catch: java.io.IOException -> L25 java.io.FileNotFoundException -> L27
            java.lang.String r4 = "count.prop"
            r2.<init>(r3, r4)     // Catch: java.io.IOException -> L25 java.io.FileNotFoundException -> L27
            r1.<init>(r2)     // Catch: java.io.IOException -> L25 java.io.FileNotFoundException -> L27
            r0.store(r1, r6)     // Catch: java.io.IOException -> L21 java.io.FileNotFoundException -> L23
            goto L2d
        L21:
            r6 = move-exception
            goto L2a
        L23:
            r6 = move-exception
            goto L2a
        L25:
            r0 = move-exception
            goto L28
        L27:
            r0 = move-exception
        L28:
            r1 = r6
            r6 = r0
        L2a:
            r6.printStackTrace()
        L2d:
            if (r1 == 0) goto L32
            r1.close()     // Catch: java.lang.Throwable -> L32
        L32:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.g.b(int):void");
    }

    public static int d() {
        return f21182h;
    }

    /* JADX WARN: Not initialized variable reg: 2, insn: 0x0054: MOVE (r1 I:??[OBJECT, ARRAY]) = (r2 I:??[OBJECT, ARRAY]), block:B:27:0x0054 */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0057 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int j() throws java.lang.Throwable {
        /*
            r6 = this;
            r0 = 0
            r1 = 0
            java.io.File r2 = new java.io.File     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            java.io.File r3 = r6.f21190k     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            java.lang.String r4 = "count.prop"
            r2.<init>(r3, r4)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            boolean r3 = r2.exists()     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            if (r3 != 0) goto L12
            return r0
        L12:
            java.io.FileInputStream r3 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            r3.<init>(r2)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L41
            java.util.Properties r1 = new java.util.Properties     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L53
            r1.<init>()     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L53
            r1.load(r2)     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L53
            java.lang.String r3 = com.tencent.smtt.sdk.g.f21184l     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L53
            java.lang.String r4 = "1"
            java.lang.String r1 = r1.getProperty(r3, r4)     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L53
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L53
            int r0 = r1.intValue()     // Catch: java.lang.Exception -> L3d java.lang.Throwable -> L53
            r2.close()     // Catch: java.io.IOException -> L38
            goto L3c
        L38:
            r1 = move-exception
            r1.printStackTrace()
        L3c:
            return r0
        L3d:
            r1 = move-exception
            goto L45
        L3f:
            r0 = move-exception
            goto L55
        L41:
            r2 = move-exception
            r5 = r2
            r2 = r1
            r1 = r5
        L45:
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L53
            if (r2 == 0) goto L52
            r2.close()     // Catch: java.io.IOException -> L4e
            goto L52
        L4e:
            r1 = move-exception
            r1.printStackTrace()
        L52:
            return r0
        L53:
            r0 = move-exception
            r1 = r2
        L55:
            if (r1 == 0) goto L5f
            r1.close()     // Catch: java.io.IOException -> L5b
            goto L5f
        L5b:
            r1 = move-exception
            r1.printStackTrace()
        L5f:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.g.j():int");
    }

    public u a() {
        if (this.f21187f) {
            return this.f21185c;
        }
        return null;
    }

    public synchronized void a(Context context, boolean z2, boolean z3) {
        TbsLog.initIfNeed(context);
        f21179a++;
        TbsLog.i("SDKEngine", "init", "#1# context: " + context + ", mInitCount: " + f21179a);
        o.a().b(context, f21179a == 1);
        o.a().j(context);
        boolean zA = QbSdk.a(context, z2, z3);
        TbsLog.i("SDKEngine", "init", "#2# canLoadX5 is " + zA);
        if (zA) {
            if (this.f21187f) {
                return;
            }
            TbsLog.i("SDKEngine", "init", "#3# start to load tbs");
            try {
                File fileP = o.a().p(context);
                Context applicationContext = context.getApplicationContext() != null ? context.getApplicationContext() : context;
                if (fileP == null) {
                    this.f21187f = false;
                    this.f21189i = "false03";
                    TbsCoreLoadStat.getInstance().a(context, 312, new Throwable());
                    QbSdk.a(context, "SDKEngine::useSystemWebView by tbs_core_share_dir null!");
                    return;
                }
                String[] dexLoaderFileList = QbSdk.getDexLoaderFileList(context, applicationContext, fileP.getAbsolutePath());
                for (int i2 = 0; i2 < dexLoaderFileList.length; i2++) {
                    TbsLog.i("SDKEngine", "dexLoaderFileList[" + i2 + "]: " + dexLoaderFileList[i2]);
                }
                String absolutePath = fileP.getAbsolutePath();
                TbsLog.i("SDKEngine", "init", "#4# optDir is " + absolutePath);
                u uVar = this.f21186d;
                if (uVar != null) {
                    this.f21185c = uVar;
                    uVar.a(context, applicationContext, fileP.getAbsolutePath(), absolutePath, dexLoaderFileList, QbSdk.f20820d);
                } else {
                    this.f21185c = new u(context, applicationContext, fileP.getAbsolutePath(), absolutePath, dexLoaderFileList, QbSdk.f20820d);
                }
                this.f21187f = true;
                this.f21189i = "true01";
            } catch (Throwable th) {
                TbsLog.e("SDKEngine", "useSystemWebView by exception: " + th);
                TbsCoreLoadStat.getInstance().a(context, 320, th);
                this.f21187f = false;
                this.f21189i = "false04";
                QbSdk.a(context, "SDKEngine::useSystemWebView by exception: " + th);
            }
        } else if (!QbSdk.f20817a || !this.f21187f) {
            this.f21187f = false;
            this.f21189i = "false05";
            TbsLog.e("SDKEngine", "init", "[LoadError] check log upon for details");
        }
        d.a().a(context);
        this.f21190k = o.r(context);
        this.f21188g = true;
    }

    public void a(String str) {
        f21184l = str;
    }

    public boolean b() {
        return this.f21187f;
    }

    public boolean b(boolean z2) {
        f21180b = z2;
        return z2;
    }

    public u c() {
        return this.f21185c;
    }

    public String e() {
        u uVar = this.f21185c;
        return (uVar == null || QbSdk.f20817a) ? "system webview get nothing..." : uVar.a();
    }

    public String f() {
        u uVar = this.f21185c;
        return (uVar == null || QbSdk.f20817a) ? "system webview get nothing..." : uVar.b();
    }

    public boolean g() throws Throwable {
        if (f21180b) {
            if (f21184l == null) {
                return false;
            }
            int iJ = j();
            if (iJ == 0) {
                b(1);
            } else {
                int i2 = iJ + 1;
                if (i2 > f21183j) {
                    return false;
                }
                b(i2);
            }
        }
        return f21180b;
    }

    public boolean h() {
        return this.f21188g;
    }

    public boolean i() {
        return QbSdk.useSoftWare();
    }
}
