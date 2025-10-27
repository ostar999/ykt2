package com.tencent.bugly.proguard;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import com.tencent.bugly.BuglyStrategy;
import com.tencent.bugly.crashreport.common.strategy.StrategyBean;
import com.tencent.bugly.proguard.r.a;

/* loaded from: classes6.dex */
public class s {

    /* renamed from: a, reason: collision with root package name */
    public static boolean f17869a = false;

    /* renamed from: b, reason: collision with root package name */
    public static r f17870b = null;

    /* renamed from: c, reason: collision with root package name */
    private static int f17871c = 10;

    /* renamed from: d, reason: collision with root package name */
    private static long f17872d = 300000;

    /* renamed from: e, reason: collision with root package name */
    private static long f17873e = 30000;

    /* renamed from: f, reason: collision with root package name */
    private static long f17874f = 0;

    /* renamed from: g, reason: collision with root package name */
    private static int f17875g = 0;

    /* renamed from: h, reason: collision with root package name */
    private static long f17876h = 0;

    /* renamed from: i, reason: collision with root package name */
    private static long f17877i = 0;

    /* renamed from: j, reason: collision with root package name */
    private static long f17878j = 0;

    /* renamed from: k, reason: collision with root package name */
    private static Application.ActivityLifecycleCallbacks f17879k = null;

    /* renamed from: l, reason: collision with root package name */
    private static Class<?> f17880l = null;

    /* renamed from: m, reason: collision with root package name */
    private static boolean f17881m = true;

    public static class a implements Application.ActivityLifecycleCallbacks {
        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityCreated(Activity activity, Bundle bundle) {
            String name = activity.getClass().getName();
            if (s.f17880l == null || s.f17880l.getName().equals(name)) {
                al.c(">>> %s onCreated <<<", name);
                aa aaVarB = aa.b();
                if (aaVarB != null) {
                    aaVarB.L.add(s.a(name, "onCreated"));
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityDestroyed(Activity activity) {
            String name = activity.getClass().getName();
            if (s.f17880l == null || s.f17880l.getName().equals(name)) {
                al.c(">>> %s onDestroyed <<<", name);
                aa aaVarB = aa.b();
                if (aaVarB != null) {
                    aaVarB.L.add(s.a(name, "onDestroyed"));
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityPaused(Activity activity) {
            String name = activity.getClass().getName();
            if (s.f17880l == null || s.f17880l.getName().equals(name)) {
                al.c(">>> %s onPaused <<<", name);
                aa aaVarB = aa.b();
                if (aaVarB == null) {
                    return;
                }
                aaVarB.L.add(s.a(name, "onPaused"));
                long jCurrentTimeMillis = System.currentTimeMillis();
                aaVarB.A = jCurrentTimeMillis;
                aaVarB.B = jCurrentTimeMillis - aaVarB.f17440z;
                long unused = s.f17876h = jCurrentTimeMillis;
                if (aaVarB.B < 0) {
                    aaVarB.B = 0L;
                }
                aaVarB.f17439y = "background";
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityResumed(Activity activity) {
            String name = activity.getClass().getName();
            if (s.f17880l == null || s.f17880l.getName().equals(name)) {
                al.c(">>> %s onResumed <<<", name);
                aa aaVarB = aa.b();
                if (aaVarB == null) {
                    return;
                }
                aaVarB.L.add(s.a(name, "onResumed"));
                aaVarB.f17439y = name;
                long jCurrentTimeMillis = System.currentTimeMillis();
                aaVarB.f17440z = jCurrentTimeMillis;
                aaVarB.C = jCurrentTimeMillis - s.f17877i;
                long j2 = aaVarB.f17440z - s.f17876h;
                if (j2 > (s.f17874f > 0 ? s.f17874f : s.f17873e)) {
                    aaVarB.c();
                    s.g();
                    al.a("[session] launch app one times (app in background %d seconds and over %d seconds)", Long.valueOf(j2 / 1000), Long.valueOf(s.f17873e / 1000));
                    if (s.f17875g % s.f17871c == 0) {
                        s.f17870b.a(4, s.f17881m);
                        return;
                    }
                    s.f17870b.a(4, false);
                    long jCurrentTimeMillis2 = System.currentTimeMillis();
                    if (jCurrentTimeMillis2 - s.f17878j > s.f17872d) {
                        long unused = s.f17878j = jCurrentTimeMillis2;
                        al.a("add a timer to upload hot start user info", new Object[0]);
                        if (s.f17881m) {
                            ak.a().a(s.f17870b.new a(null, true), s.f17872d);
                        }
                    }
                }
            }
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStarted(Activity activity) {
            al.c(">>> %s onStart <<<", activity.getClass().getName());
            aa.b().a(activity.hashCode(), true);
        }

        @Override // android.app.Application.ActivityLifecycleCallbacks
        public final void onActivityStopped(Activity activity) {
            al.c(">>> %s onStop <<<", activity.getClass().getName());
            aa.b().a(activity.hashCode(), false);
        }
    }

    public static void a(final Context context, final BuglyStrategy buglyStrategy) {
        long appReportDelay;
        if (f17869a) {
            return;
        }
        f17881m = aa.a(context).f17420f;
        f17870b = new r(context, f17881m);
        f17869a = true;
        if (buglyStrategy != null) {
            f17880l = buglyStrategy.getUserInfoActivity();
            appReportDelay = buglyStrategy.getAppReportDelay();
        } else {
            appReportDelay = 0;
        }
        if (appReportDelay <= 0) {
            c(context, buglyStrategy);
        } else {
            ak.a().a(new Runnable() { // from class: com.tencent.bugly.proguard.s.1
                @Override // java.lang.Runnable
                public final void run() {
                    s.c(context, buglyStrategy);
                }
            }, appReportDelay);
        }
    }

    public static /* synthetic */ int g() {
        int i2 = f17875g;
        f17875g = i2 + 1;
        return i2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:27:0x005b A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x005c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void c(android.content.Context r11, com.tencent.bugly.BuglyStrategy r12) {
        /*
            r0 = 1
            r1 = 0
            if (r12 == 0) goto Ld
            boolean r2 = r12.recordUserInfoOnceADay()
            boolean r12 = r12.isEnableUserInfo()
            goto Lf
        Ld:
            r12 = r0
            r2 = r1
        Lf:
            if (r2 == 0) goto L5d
            com.tencent.bugly.proguard.aa r12 = com.tencent.bugly.proguard.aa.a(r11)
            java.lang.String r2 = r12.f17418d
            java.util.List r2 = com.tencent.bugly.proguard.r.a(r2)
            if (r2 == 0) goto L58
            r3 = r1
        L1e:
            int r4 = r2.size()
            if (r3 >= r4) goto L58
            java.lang.Object r4 = r2.get(r3)
            com.tencent.bugly.crashreport.biz.UserInfoBean r4 = (com.tencent.bugly.crashreport.biz.UserInfoBean) r4
            java.lang.String r5 = r4.f17327n
            java.lang.String r6 = r12.f17429o
            boolean r5 = r5.equals(r6)
            if (r5 == 0) goto L55
            int r5 = r4.f17315b
            if (r5 != r0) goto L55
            long r5 = com.tencent.bugly.proguard.ap.b()
            r7 = 0
            int r9 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r9 <= 0) goto L58
            long r9 = r4.f17318e
            int r5 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1))
            if (r5 < 0) goto L55
            long r2 = r4.f17319f
            int r12 = (r2 > r7 ? 1 : (r2 == r7 ? 0 : -1))
            if (r12 > 0) goto L53
            com.tencent.bugly.proguard.r r12 = com.tencent.bugly.proguard.s.f17870b
            r12.b()
        L53:
            r12 = r1
            goto L59
        L55:
            int r3 = r3 + 1
            goto L1e
        L58:
            r12 = r0
        L59:
            if (r12 != 0) goto L5c
            return
        L5c:
            r12 = r1
        L5d:
            com.tencent.bugly.proguard.aa r2 = com.tencent.bugly.proguard.aa.b()
            if (r2 == 0) goto L6c
            boolean r3 = com.tencent.bugly.proguard.z.a()
            if (r3 == 0) goto L6c
            r2.a(r1, r0)
        L6c:
            if (r12 == 0) goto L9b
            android.content.Context r12 = r11.getApplicationContext()
            boolean r12 = r12 instanceof android.app.Application
            if (r12 == 0) goto L7d
            android.content.Context r11 = r11.getApplicationContext()
            android.app.Application r11 = (android.app.Application) r11
            goto L7e
        L7d:
            r11 = 0
        L7e:
            if (r11 == 0) goto L9b
            android.app.Application$ActivityLifecycleCallbacks r12 = com.tencent.bugly.proguard.s.f17879k     // Catch: java.lang.Exception -> L91
            if (r12 != 0) goto L8b
            com.tencent.bugly.proguard.s$a r12 = new com.tencent.bugly.proguard.s$a     // Catch: java.lang.Exception -> L91
            r12.<init>()     // Catch: java.lang.Exception -> L91
            com.tencent.bugly.proguard.s.f17879k = r12     // Catch: java.lang.Exception -> L91
        L8b:
            android.app.Application$ActivityLifecycleCallbacks r12 = com.tencent.bugly.proguard.s.f17879k     // Catch: java.lang.Exception -> L91
            r11.registerActivityLifecycleCallbacks(r12)     // Catch: java.lang.Exception -> L91
            goto L9b
        L91:
            r11 = move-exception
            boolean r12 = com.tencent.bugly.proguard.al.a(r11)
            if (r12 != 0) goto L9b
            r11.printStackTrace()
        L9b:
            boolean r11 = com.tencent.bugly.proguard.s.f17881m
            if (r11 == 0) goto Lbe
            long r11 = java.lang.System.currentTimeMillis()
            com.tencent.bugly.proguard.s.f17877i = r11
            com.tencent.bugly.proguard.r r11 = com.tencent.bugly.proguard.s.f17870b
            r11.a(r0, r1)
            java.lang.String r11 = "[session] launch app, new start"
            java.lang.Object[] r12 = new java.lang.Object[r1]
            com.tencent.bugly.proguard.al.a(r11, r12)
            com.tencent.bugly.proguard.r r11 = com.tencent.bugly.proguard.s.f17870b
            r11.a()
            com.tencent.bugly.proguard.r r11 = com.tencent.bugly.proguard.s.f17870b
            r0 = 21600000(0x1499700, double:1.0671818E-316)
            r11.a(r0)
        Lbe:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.s.c(android.content.Context, com.tencent.bugly.BuglyStrategy):void");
    }

    public static void a(long j2) {
        if (j2 < 0) {
            j2 = ac.a().c().f17351p;
        }
        f17874f = j2;
    }

    public static void a(StrategyBean strategyBean, boolean z2) {
        r rVar = f17870b;
        if (rVar != null && !z2) {
            rVar.b();
        }
        if (strategyBean == null) {
            return;
        }
        long j2 = strategyBean.f17351p;
        if (j2 > 0) {
            f17873e = j2;
        }
        int i2 = strategyBean.f17356u;
        if (i2 > 0) {
            f17871c = i2;
        }
        long j3 = strategyBean.f17357v;
        if (j3 > 0) {
            f17872d = j3;
        }
    }

    public static void a() {
        r rVar = f17870b;
        if (rVar != null) {
            rVar.a(2, false);
        }
    }

    public static void a(Context context) {
        if (!f17869a || context == null) {
            return;
        }
        Application application = context.getApplicationContext() instanceof Application ? (Application) context.getApplicationContext() : null;
        if (application != null) {
            try {
                Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = f17879k;
                if (activityLifecycleCallbacks != null) {
                    application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks);
                }
            } catch (Exception e2) {
                if (!al.a(e2)) {
                    e2.printStackTrace();
                }
            }
        }
        f17869a = false;
    }

    public static /* synthetic */ String a(String str, String str2) {
        return ap.a() + "  " + str + "  " + str2 + "\n";
    }
}
