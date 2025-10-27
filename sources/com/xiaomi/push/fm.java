package com.xiaomi.push;

import android.content.Context;
import com.xiaomi.push.service.XMJobService;

/* loaded from: classes6.dex */
public final class fm {

    /* renamed from: a, reason: collision with other field name */
    private static a f422a;

    /* renamed from: a, reason: collision with other field name */
    private static final String f423a = XMJobService.class.getCanonicalName();

    /* renamed from: a, reason: collision with root package name */
    private static int f24853a = 0;

    public interface a {
        /* renamed from: a */
        void mo423a();

        void a(boolean z2);

        /* renamed from: a, reason: collision with other method in class */
        boolean mo422a();
    }

    public static synchronized void a() {
        a aVar = f422a;
        if (aVar == null) {
            return;
        }
        aVar.mo423a();
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x007a A[EDGE_INSN: B:49:0x007a->B:30:0x007a BREAK  A[LOOP:0: B:11:0x002f->B:27:0x0074], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(android.content.Context r9) {
        /*
            java.lang.String r0 = "android.permission.BIND_JOB_SERVICE"
            android.content.Context r9 = r9.getApplicationContext()
            java.lang.String r1 = r9.getPackageName()
            java.lang.String r2 = "com.xiaomi.xmsf"
            boolean r1 = r2.equals(r1)
            if (r1 == 0) goto L1b
            com.xiaomi.push.fn r0 = new com.xiaomi.push.fn
            r0.<init>(r9)
        L17:
            com.xiaomi.push.fm.f422a = r0
            goto Lcb
        L1b:
            android.content.pm.PackageManager r1 = r9.getPackageManager()
            r2 = 0
            java.lang.String r3 = r9.getPackageName()     // Catch: java.lang.Exception -> L7c
            r4 = 4
            android.content.pm.PackageInfo r1 = r1.getPackageInfo(r3, r4)     // Catch: java.lang.Exception -> L7c
            android.content.pm.ServiceInfo[] r1 = r1.services     // Catch: java.lang.Exception -> L7c
            if (r1 == 0) goto L95
            int r3 = r1.length     // Catch: java.lang.Exception -> L7c
            r4 = r2
        L2f:
            if (r2 >= r3) goto L7a
            r5 = r1[r2]     // Catch: java.lang.Exception -> L77
            java.lang.String r6 = r5.permission     // Catch: java.lang.Exception -> L77
            boolean r6 = r0.equals(r6)     // Catch: java.lang.Exception -> L77
            r7 = 1
            if (r6 == 0) goto L60
            java.lang.String r6 = com.xiaomi.push.fm.f423a     // Catch: java.lang.Exception -> L77
            java.lang.String r8 = r5.name     // Catch: java.lang.Exception -> L77
            boolean r8 = r6.equals(r8)     // Catch: java.lang.Exception -> L77
            if (r8 == 0) goto L48
        L46:
            r4 = r7
            goto L5d
        L48:
            java.lang.String r8 = r5.name     // Catch: java.lang.Exception -> L5d
            java.lang.Class r8 = java.lang.Class.forName(r8)     // Catch: java.lang.Exception -> L5d
            java.lang.Class r8 = r8.getSuperclass()     // Catch: java.lang.Exception -> L5d
            java.lang.String r8 = r8.getCanonicalName()     // Catch: java.lang.Exception -> L5d
            boolean r6 = r6.equals(r8)     // Catch: java.lang.Exception -> L5d
            if (r6 == 0) goto L5d
            goto L46
        L5d:
            if (r4 != r7) goto L60
            goto L7a
        L60:
            java.lang.String r6 = com.xiaomi.push.fm.f423a     // Catch: java.lang.Exception -> L77
            java.lang.String r8 = r5.name     // Catch: java.lang.Exception -> L77
            boolean r6 = r6.equals(r8)     // Catch: java.lang.Exception -> L77
            if (r6 == 0) goto L74
            java.lang.String r5 = r5.permission     // Catch: java.lang.Exception -> L77
            boolean r5 = r0.equals(r5)     // Catch: java.lang.Exception -> L77
            if (r5 == 0) goto L74
            r2 = r7
            goto L95
        L74:
            int r2 = r2 + 1
            goto L2f
        L77:
            r1 = move-exception
            r2 = r4
            goto L7d
        L7a:
            r2 = r4
            goto L95
        L7c:
            r1 = move-exception
        L7d:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.String r4 = "check service err : "
            r3.append(r4)
            java.lang.String r1 = r1.getMessage()
            r3.append(r1)
            java.lang.String r1 = r3.toString()
            com.xiaomi.channel.commonutils.logger.b.m117a(r1)
        L95:
            if (r2 != 0) goto Lc4
            boolean r1 = com.xiaomi.push.v.m772a(r9)
            if (r1 != 0) goto L9e
            goto Lc4
        L9e:
            java.lang.RuntimeException r9 = new java.lang.RuntimeException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = "Should export service: "
            r1.append(r2)
            java.lang.String r2 = com.xiaomi.push.fm.f423a
            r1.append(r2)
            java.lang.String r2 = " with permission "
            r1.append(r2)
            r1.append(r0)
            java.lang.String r0 = " in AndroidManifest.xml file"
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r9.<init>(r0)
            throw r9
        Lc4:
            com.xiaomi.push.fn r0 = new com.xiaomi.push.fn
            r0.<init>(r9)
            goto L17
        Lcb:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.fm.a(android.content.Context):void");
    }

    public static synchronized void a(Context context, int i2) {
        int i3 = f24853a;
        if (!"com.xiaomi.xmsf".equals(context.getPackageName())) {
            if (i2 == 2) {
                f24853a = 2;
            } else {
                f24853a = 0;
            }
        }
        int i4 = f24853a;
        if (i3 != i4 && i4 == 2) {
            a();
            f422a = new fp(context);
        }
    }

    public static synchronized void a(boolean z2) {
        a aVar = f422a;
        if (aVar == null) {
            com.xiaomi.channel.commonutils.logger.b.m117a("timer is not initialized");
        } else {
            aVar.a(z2);
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public static synchronized boolean m421a() {
        a aVar = f422a;
        if (aVar == null) {
            return false;
        }
        return aVar.mo422a();
    }
}
