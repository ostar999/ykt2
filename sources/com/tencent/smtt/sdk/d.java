package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.export.external.DexLoader;
import com.tencent.smtt.utils.TbsLog;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    private static String f21161a = "EmergencyManager";

    /* renamed from: b, reason: collision with root package name */
    private static int f21162b = 0;

    /* renamed from: c, reason: collision with root package name */
    private static int f21163c = 1;

    /* renamed from: d, reason: collision with root package name */
    private static int f21164d = 2;

    /* renamed from: e, reason: collision with root package name */
    private static int f21165e = 3;

    /* renamed from: f, reason: collision with root package name */
    private static int f21166f = 4;

    /* renamed from: g, reason: collision with root package name */
    private static int f21167g = 5;

    /* renamed from: h, reason: collision with root package name */
    private static d f21168h;

    /* renamed from: i, reason: collision with root package name */
    private long f21169i = 60000;

    /* renamed from: j, reason: collision with root package name */
    private long f21170j = 86400000;

    /* renamed from: k, reason: collision with root package name */
    private boolean f21171k = false;

    private d() {
    }

    public static synchronized d a() {
        if (f21168h == null) {
            f21168h = new d();
        }
        return f21168h;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void a(Context context, int i2, List<com.tencent.smtt.sdk.a.b> list) {
        String str;
        String str2;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        com.tencent.smtt.sdk.a.g gVarA = com.tencent.smtt.sdk.a.g.a();
        List<String> listA = gVarA.a(context, "emergence_ids");
        HashSet hashSet = new HashSet();
        if (listA != null && !listA.isEmpty()) {
            Iterator<String> it = listA.iterator();
            while (it.hasNext()) {
                String[] strArrA = com.tencent.smtt.sdk.a.g.a(it.next());
                if (strArrA != null && strArrA.length == 4) {
                    hashSet.add(Integer.valueOf(Integer.parseInt(strArrA[0])));
                }
            }
        }
        for (com.tencent.smtt.sdk.a.b bVar : list) {
            int iB = bVar.b();
            int iA = bVar.a();
            if (hashSet.contains(Integer.valueOf(iA))) {
                str = f21161a;
                str2 = "Command has been executed: " + bVar.toString() + ", ignored";
            } else if (bVar.e()) {
                str = f21161a;
                str2 = "Command is out of date: " + bVar.toString() + ", now: " + com.tencent.smtt.sdk.a.a.a(System.currentTimeMillis());
            } else {
                linkedHashMap.put(Integer.valueOf(iB), bVar.c());
                gVarA.a(context, "emergence_ids", com.tencent.smtt.sdk.a.g.a(new String[]{String.valueOf(iA), String.valueOf(bVar.b()), String.valueOf(bVar.c()), String.valueOf(bVar.d())}));
            }
            TbsLog.d(str, str2);
        }
        a(context, Integer.valueOf(i2), linkedHashMap);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0046 A[Catch: all -> 0x00e8, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x001d, B:6:0x0025, B:13:0x003e, B:15:0x0046, B:16:0x004a, B:20:0x005d, B:21:0x008c, B:23:0x0092, B:25:0x009e, B:27:0x00a4, B:29:0x00a8, B:31:0x00bd, B:35:0x00ca, B:34:0x00c6, B:17:0x004e, B:7:0x0029, B:12:0x0039), top: B:43:0x0001, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004e A[Catch: all -> 0x00e8, TryCatch #1 {, blocks: (B:3:0x0001, B:5:0x001d, B:6:0x0025, B:13:0x003e, B:15:0x0046, B:16:0x004a, B:20:0x005d, B:21:0x008c, B:23:0x0092, B:25:0x009e, B:27:0x00a4, B:29:0x00a8, B:31:0x00bd, B:35:0x00ca, B:34:0x00c6, B:17:0x004e, B:7:0x0029, B:12:0x0039), top: B:43:0x0001, inners: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0092 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private synchronized void b(final android.content.Context r11) {
        /*
            r10 = this;
            monitor-enter(r10)
            com.tencent.smtt.sdk.a.c r0 = new com.tencent.smtt.sdk.a.c     // Catch: java.lang.Throwable -> Le8
            r0.<init>()     // Catch: java.lang.Throwable -> Le8
            java.lang.String r1 = com.tencent.smtt.utils.b.a(r11)     // Catch: java.lang.Throwable -> Le8
            r0.a(r1)     // Catch: java.lang.Throwable -> Le8
            java.lang.String r1 = com.tencent.smtt.utils.b.d(r11)     // Catch: java.lang.Throwable -> Le8
            r0.b(r1)     // Catch: java.lang.Throwable -> Le8
            com.tencent.smtt.sdk.TbsPrivacyAccess r1 = com.tencent.smtt.sdk.TbsPrivacyAccess.AndroidVersion     // Catch: java.lang.Throwable -> Le8
            boolean r1 = r1.isEnabled()     // Catch: java.lang.Throwable -> Le8
            r2 = 0
            if (r1 == 0) goto L29
            int r1 = com.tencent.smtt.utils.b.b(r11)     // Catch: java.lang.Throwable -> Le8
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: java.lang.Throwable -> Le8
        L25:
            r0.a(r1)     // Catch: java.lang.Throwable -> Le8
            goto L3e
        L29:
            com.tencent.smtt.sdk.TbsPrivacyAccess$ConfigurablePrivacy r1 = com.tencent.smtt.sdk.TbsPrivacyAccess.ConfigurablePrivacy.ANDROID_VERSION     // Catch: java.lang.Throwable -> Le8
            java.lang.String r3 = "0"
            java.lang.String r1 = com.tencent.smtt.sdk.TbsPrivacyAccess.getConfigurePrivacy(r11, r1, r3)     // Catch: java.lang.Throwable -> Le8
            int r1 = java.lang.Integer.parseInt(r1)     // Catch: java.lang.Throwable -> L36
            goto L37
        L36:
            r1 = r2
        L37:
            if (r1 == 0) goto L3e
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: java.lang.Throwable -> Le8
            goto L25
        L3e:
            com.tencent.smtt.sdk.TbsPrivacyAccess r1 = com.tencent.smtt.sdk.TbsPrivacyAccess.DeviceModel     // Catch: java.lang.Throwable -> Le8
            boolean r1 = r1.isEnabled()     // Catch: java.lang.Throwable -> Le8
            if (r1 == 0) goto L4e
            java.lang.String r1 = com.tencent.smtt.utils.b.c(r11)     // Catch: java.lang.Throwable -> Le8
        L4a:
            r0.c(r1)     // Catch: java.lang.Throwable -> Le8
            goto L5d
        L4e:
            com.tencent.smtt.sdk.TbsPrivacyAccess$ConfigurablePrivacy r1 = com.tencent.smtt.sdk.TbsPrivacyAccess.ConfigurablePrivacy.DEVICE_MODEL     // Catch: java.lang.Throwable -> Le8
            java.lang.String r3 = ""
            java.lang.String r1 = com.tencent.smtt.sdk.TbsPrivacyAccess.getConfigurePrivacy(r11, r1, r3)     // Catch: java.lang.Throwable -> Le8
            boolean r3 = android.text.TextUtils.isEmpty(r1)     // Catch: java.lang.Throwable -> Le8
            if (r3 != 0) goto L5d
            goto L4a
        L5d:
            java.lang.String r1 = "x5webview"
            r0.d(r1)     // Catch: java.lang.Throwable -> Le8
            int r1 = com.tencent.smtt.sdk.QbSdk.getTbsSdkVersion()     // Catch: java.lang.Throwable -> Le8
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: java.lang.Throwable -> Le8
            r0.b(r1)     // Catch: java.lang.Throwable -> Le8
            int r1 = com.tencent.smtt.sdk.QbSdk.getTbsVersion(r11)     // Catch: java.lang.Throwable -> Le8
            java.lang.Integer r1 = java.lang.Integer.valueOf(r1)     // Catch: java.lang.Throwable -> Le8
            r0.c(r1)     // Catch: java.lang.Throwable -> Le8
            com.tencent.smtt.sdk.a.g r1 = com.tencent.smtt.sdk.a.g.a()     // Catch: java.lang.Throwable -> Le8
            java.lang.String r3 = "emergence_ids"
            java.util.List r1 = r1.a(r11, r3)     // Catch: java.lang.Throwable -> Le8
            java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Throwable -> Le8
            java.util.ArrayList r3 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Le8
            r3.<init>()     // Catch: java.lang.Throwable -> Le8
        L8c:
            boolean r4 = r1.hasNext()     // Catch: java.lang.Throwable -> Le8
            if (r4 == 0) goto Lca
            java.lang.Object r4 = r1.next()     // Catch: java.lang.Exception -> Lc5 java.lang.Throwable -> Le8
            java.lang.String r4 = (java.lang.String) r4     // Catch: java.lang.Exception -> Lc5 java.lang.Throwable -> Le8
            boolean r5 = android.text.TextUtils.isEmpty(r4)     // Catch: java.lang.Exception -> Lc5 java.lang.Throwable -> Le8
            if (r5 != 0) goto L8c
            java.lang.String[] r4 = com.tencent.smtt.sdk.a.g.a(r4)     // Catch: java.lang.Exception -> Lc5 java.lang.Throwable -> Le8
            if (r4 == 0) goto L8c
            int r5 = r4.length     // Catch: java.lang.Exception -> Lc5 java.lang.Throwable -> Le8
            r6 = 4
            if (r5 != r6) goto L8c
            r5 = r4[r2]     // Catch: java.lang.Exception -> Lc5 java.lang.Throwable -> Le8
            int r5 = java.lang.Integer.parseInt(r5)     // Catch: java.lang.Exception -> Lc5 java.lang.Throwable -> Le8
            r6 = 3
            r4 = r4[r6]     // Catch: java.lang.Exception -> Lc5 java.lang.Throwable -> Le8
            long r6 = java.lang.Long.parseLong(r4)     // Catch: java.lang.Exception -> Lc5 java.lang.Throwable -> Le8
            long r8 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Exception -> Lc5 java.lang.Throwable -> Le8
            int r4 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r4 >= 0) goto L8c
            java.lang.Integer r4 = java.lang.Integer.valueOf(r5)     // Catch: java.lang.Exception -> Lc5 java.lang.Throwable -> Le8
            r3.add(r4)     // Catch: java.lang.Exception -> Lc5 java.lang.Throwable -> Le8
            goto L8c
        Lc5:
            r4 = move-exception
            r4.printStackTrace()     // Catch: java.lang.Throwable -> Le8
            goto L8c
        Lca:
            r0.a(r3)     // Catch: java.lang.Throwable -> Le8
            com.tencent.smtt.sdk.a.e r1 = new com.tencent.smtt.sdk.a.e     // Catch: java.lang.Throwable -> Le8
            com.tencent.smtt.utils.o r2 = com.tencent.smtt.utils.o.a(r11)     // Catch: java.lang.Throwable -> Le8
            java.lang.String r2 = r2.j()     // Catch: java.lang.Throwable -> Le8
            java.lang.String r0 = r0.a()     // Catch: java.lang.Throwable -> Le8
            r1.<init>(r11, r2, r0)     // Catch: java.lang.Throwable -> Le8
            com.tencent.smtt.sdk.d$1 r0 = new com.tencent.smtt.sdk.d$1     // Catch: java.lang.Throwable -> Le8
            r0.<init>()     // Catch: java.lang.Throwable -> Le8
            r1.a(r0)     // Catch: java.lang.Throwable -> Le8
            monitor-exit(r10)
            return
        Le8:
            r11 = move-exception
            monitor-exit(r10)
            throw r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.sdk.d.b(android.content.Context):void");
    }

    public synchronized void a(Context context) {
        com.tencent.smtt.sdk.a.g gVarA;
        if (!this.f21171k) {
            this.f21171k = true;
            com.tencent.smtt.sdk.a.g gVarA2 = com.tencent.smtt.sdk.a.g.a();
            if (gVarA2.b()) {
                a(context, f21166f, new ArrayList());
            } else {
                try {
                    gVarA2.a(context);
                    try {
                        long jB = com.tencent.smtt.sdk.a.g.a().b(context, "emergence_timestamp");
                        long jB2 = com.tencent.smtt.sdk.a.g.a().b(context, "emergence_req_interval");
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        long j2 = jCurrentTimeMillis - jB;
                        long jMin = Math.min(Math.max(this.f21169i, jB2), this.f21170j);
                        if (j2 > jMin) {
                            TbsLog.d(f21161a, "Emergency configuration is out of date, attempt to query again, " + (j2 / 1000) + " seconds has past");
                            com.tencent.smtt.sdk.a.g.a().a(context, "emergence_timestamp", jCurrentTimeMillis);
                            b(context);
                        } else {
                            a(context, f21163c, new ArrayList());
                            TbsLog.d(f21161a, "Emergency configuration is up to date, " + (j2 / 1000) + " seconds has past, need " + (Math.abs(j2 - jMin) / 1000) + " more seconds for an another request");
                        }
                        gVarA = com.tencent.smtt.sdk.a.g.a();
                    } catch (Exception e2) {
                        a(context, f21167g, new ArrayList());
                        TbsLog.d(f21161a, "Unexpected exception happened when query emergency configuration: " + e2.getMessage());
                        gVarA = com.tencent.smtt.sdk.a.g.a();
                    }
                    gVarA.c();
                } catch (Throwable th) {
                    com.tencent.smtt.sdk.a.g.a().c();
                    throw th;
                }
            }
        }
    }

    public synchronized void a(Context context, Integer num, Map<Integer, String> map) {
        c.a().b(context);
        TbsLog.e(f21161a, "Dispatch emergency commands on tbs extension");
        QbSdk.a(context, num, map);
        g gVarA = g.a(true);
        if (gVarA == null) {
            return;
        }
        u uVarA = gVarA.a();
        if (uVarA == null) {
            return;
        }
        DexLoader dexLoaderC = uVarA.c();
        if (dexLoaderC != null) {
            TbsLog.e(f21161a, "Dispatch emergency commands on tbs shell");
            dexLoaderC.invokeStaticMethod("com.tencent.tbs.tbsshell.TBSShell", "dispatchEmergencyCommand", new Class[]{Integer.class, Map.class}, num, map);
        } else {
            TbsLog.e(f21161a, "Dex loader is null, cancel commands dispatching of tbs shell");
        }
    }
}
