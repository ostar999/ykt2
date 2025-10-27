package com.xiaomi.push.service;

import android.content.SharedPreferences;
import com.xiaomi.push.al;
import com.xiaomi.push.es;
import com.xiaomi.push.et;
import com.xiaomi.push.hf;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class bi {

    /* renamed from: a, reason: collision with root package name */
    private static bi f25654a = new bi();

    /* renamed from: a, reason: collision with other field name */
    private static String f1036a;

    /* renamed from: a, reason: collision with other field name */
    private al.b f1037a;

    /* renamed from: a, reason: collision with other field name */
    private es.a f1038a;

    /* renamed from: a, reason: collision with other field name */
    private List<a> f1039a = new ArrayList();

    public static abstract class a {
        public void a(es.a aVar) {
        }

        public void a(et.b bVar) {
        }
    }

    private bi() {
    }

    public static bi a() {
        return f25654a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static synchronized String m731a() {
        if (f1036a == null) {
            SharedPreferences sharedPreferences = com.xiaomi.push.v.m770a().getSharedPreferences("XMPushServiceConfig", 0);
            String string = sharedPreferences.getString("DeviceUUID", null);
            f1036a = string;
            if (string == null) {
                String strA = com.xiaomi.push.j.a(com.xiaomi.push.v.m770a(), false);
                f1036a = strA;
                if (strA != null) {
                    sharedPreferences.edit().putString("DeviceUUID", f1036a).commit();
                }
            }
        }
        return f1036a;
    }

    private void b() throws Throwable {
        if (this.f1038a == null) {
            d();
        }
    }

    private void c() {
        if (this.f1037a != null) {
            return;
        }
        bj bjVar = new bj(this);
        this.f1037a = bjVar;
        hf.a(bjVar);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:26:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void d() throws java.lang.Throwable {
        /*
            r4 = this;
            r0 = 0
            android.content.Context r1 = com.xiaomi.push.v.m770a()     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L27
            java.lang.String r2 = "XMCloudCfg"
            java.io.FileInputStream r1 = r1.openFileInput(r2)     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L27
            java.io.BufferedInputStream r2 = new java.io.BufferedInputStream     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L27
            r2.<init>(r1)     // Catch: java.lang.Throwable -> L23 java.lang.Exception -> L27
            com.xiaomi.push.b r0 = com.xiaomi.push.b.a(r2)     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L4f
            com.xiaomi.push.es$a r0 = com.xiaomi.push.es.a.b(r0)     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L4f
            r4.f1038a = r0     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L4f
            r2.close()     // Catch: java.lang.Exception -> L21 java.lang.Throwable -> L4f
        L1d:
            com.xiaomi.push.y.a(r2)
            goto L43
        L21:
            r0 = move-exception
            goto L2a
        L23:
            r1 = move-exception
            r2 = r0
            r0 = r1
            goto L50
        L27:
            r1 = move-exception
            r2 = r0
            r0 = r1
        L2a:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L4f
            r1.<init>()     // Catch: java.lang.Throwable -> L4f
            java.lang.String r3 = "load config failure: "
            r1.append(r3)     // Catch: java.lang.Throwable -> L4f
            java.lang.String r0 = r0.getMessage()     // Catch: java.lang.Throwable -> L4f
            r1.append(r0)     // Catch: java.lang.Throwable -> L4f
            java.lang.String r0 = r1.toString()     // Catch: java.lang.Throwable -> L4f
            com.xiaomi.channel.commonutils.logger.b.m117a(r0)     // Catch: java.lang.Throwable -> L4f
            goto L1d
        L43:
            com.xiaomi.push.es$a r0 = r4.f1038a
            if (r0 != 0) goto L4e
            com.xiaomi.push.es$a r0 = new com.xiaomi.push.es$a
            r0.<init>()
            r4.f1038a = r0
        L4e:
            return
        L4f:
            r0 = move-exception
        L50:
            com.xiaomi.push.y.a(r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.bi.d():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void e() throws IOException {
        try {
            if (this.f1038a != null) {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(com.xiaomi.push.v.m770a().openFileOutput("XMCloudCfg", 0));
                com.xiaomi.push.c cVarA = com.xiaomi.push.c.a(bufferedOutputStream);
                this.f1038a.a(cVarA);
                cVarA.m253a();
                bufferedOutputStream.close();
            }
        } catch (Exception e2) {
            com.xiaomi.channel.commonutils.logger.b.m117a("save config failure: " + e2.getMessage());
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    public int m734a() throws Throwable {
        b();
        es.a aVar = this.f1038a;
        if (aVar != null) {
            return aVar.c();
        }
        return 0;
    }

    /* renamed from: a, reason: collision with other method in class */
    public es.a m735a() throws Throwable {
        b();
        return this.f1038a;
    }

    /* renamed from: a, reason: collision with other method in class */
    public synchronized void m736a() {
        this.f1039a.clear();
    }

    public void a(et.b bVar) {
        a[] aVarArr;
        if (bVar.m362d() && bVar.d() > m734a()) {
            c();
        }
        synchronized (this) {
            List<a> list = this.f1039a;
            aVarArr = (a[]) list.toArray(new a[list.size()]);
        }
        for (a aVar : aVarArr) {
            aVar.a(bVar);
        }
    }

    public synchronized void a(a aVar) {
        this.f1039a.add(aVar);
    }
}
