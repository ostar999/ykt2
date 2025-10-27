package com.alipay.sdk.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.sdk.util.e;
import com.alipay.sdk.util.l;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class AuthTask {

    /* renamed from: a, reason: collision with root package name */
    static final Object f3061a = com.alipay.sdk.util.e.class;

    /* renamed from: b, reason: collision with root package name */
    private static final int f3062b = 73;

    /* renamed from: c, reason: collision with root package name */
    private Activity f3063c;

    /* renamed from: d, reason: collision with root package name */
    private com.alipay.sdk.widget.a f3064d;

    public AuthTask(Activity activity) {
        this.f3063c = activity;
        com.alipay.sdk.sys.b bVarA = com.alipay.sdk.sys.b.a();
        Activity activity2 = this.f3063c;
        com.alipay.sdk.data.c.a();
        bVarA.a(activity2);
        com.alipay.sdk.app.statistic.a.a(activity);
        this.f3064d = new com.alipay.sdk.widget.a(activity, com.alipay.sdk.widget.a.f3399c);
    }

    private void b() {
        com.alipay.sdk.widget.a aVar = this.f3064d;
        if (aVar != null) {
            aVar.a();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void c() {
        com.alipay.sdk.widget.a aVar = this.f3064d;
        if (aVar != null) {
            aVar.b();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x004a A[Catch: all -> 0x0060, Exception -> 0x0073, TRY_LEAVE, TryCatch #3 {Exception -> 0x0073, all -> 0x0060, blocks: (B:6:0x0016, B:8:0x0027, B:10:0x003d, B:12:0x0043, B:14:0x004a), top: B:28:0x0016, outer: #1 }] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0027 A[Catch: all -> 0x0060, Exception -> 0x0073, TryCatch #3 {Exception -> 0x0073, all -> 0x0060, blocks: (B:6:0x0016, B:8:0x0027, B:10:0x003d, B:12:0x0043, B:14:0x004a), top: B:28:0x0016, outer: #1 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public synchronized java.lang.String auth(java.lang.String r5, boolean r6) {
        /*
            r4 = this;
            monitor-enter(r4)
            if (r6 == 0) goto L6
            r4.b()     // Catch: java.lang.Throwable -> L84
        L6:
            com.alipay.sdk.sys.b r6 = com.alipay.sdk.sys.b.a()     // Catch: java.lang.Throwable -> L84
            android.app.Activity r0 = r4.f3063c     // Catch: java.lang.Throwable -> L84
            com.alipay.sdk.data.c.a()     // Catch: java.lang.Throwable -> L84
            r6.a(r0)     // Catch: java.lang.Throwable -> L84
            java.lang.String r6 = com.alipay.sdk.app.i.a()     // Catch: java.lang.Throwable -> L84
            android.app.Activity r0 = r4.f3063c     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            com.alipay.sdk.sys.a r1 = new com.alipay.sdk.sys.a     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            r1.<init>(r0)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            java.lang.String r1 = r1.a(r5)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            boolean r2 = a(r0)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            if (r2 == 0) goto L4a
            com.alipay.sdk.util.e r2 = new com.alipay.sdk.util.e     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            com.alipay.sdk.app.a r3 = new com.alipay.sdk.app.a     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            r2.<init>(r0, r3)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            java.lang.String r2 = r2.a(r1)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            java.lang.String r3 = "failed"
            boolean r3 = android.text.TextUtils.equals(r2, r3)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            if (r3 != 0) goto L4a
            boolean r0 = android.text.TextUtils.isEmpty(r2)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            if (r0 == 0) goto L48
            java.lang.String r6 = com.alipay.sdk.app.i.a()     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
            goto L4e
        L48:
            r6 = r2
            goto L4e
        L4a:
            java.lang.String r6 = r4.b(r0, r1)     // Catch: java.lang.Throwable -> L60 java.lang.Exception -> L73
        L4e:
            com.alipay.sdk.data.a r0 = com.alipay.sdk.data.a.b()     // Catch: java.lang.Throwable -> L84
            android.app.Activity r1 = r4.f3063c     // Catch: java.lang.Throwable -> L84
            r0.a(r1)     // Catch: java.lang.Throwable -> L84
            r4.c()     // Catch: java.lang.Throwable -> L84
            android.app.Activity r0 = r4.f3063c     // Catch: java.lang.Throwable -> L84
        L5c:
            com.alipay.sdk.app.statistic.a.a(r0, r5)     // Catch: java.lang.Throwable -> L84
            goto L82
        L60:
            r6 = move-exception
            com.alipay.sdk.data.a r0 = com.alipay.sdk.data.a.b()     // Catch: java.lang.Throwable -> L84
            android.app.Activity r1 = r4.f3063c     // Catch: java.lang.Throwable -> L84
            r0.a(r1)     // Catch: java.lang.Throwable -> L84
            r4.c()     // Catch: java.lang.Throwable -> L84
            android.app.Activity r0 = r4.f3063c     // Catch: java.lang.Throwable -> L84
            com.alipay.sdk.app.statistic.a.a(r0, r5)     // Catch: java.lang.Throwable -> L84
            throw r6     // Catch: java.lang.Throwable -> L84
        L73:
            com.alipay.sdk.data.a r0 = com.alipay.sdk.data.a.b()     // Catch: java.lang.Throwable -> L84
            android.app.Activity r1 = r4.f3063c     // Catch: java.lang.Throwable -> L84
            r0.a(r1)     // Catch: java.lang.Throwable -> L84
            r4.c()     // Catch: java.lang.Throwable -> L84
            android.app.Activity r0 = r4.f3063c     // Catch: java.lang.Throwable -> L84
            goto L5c
        L82:
            monitor-exit(r4)
            return r6
        L84:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alipay.sdk.app.AuthTask.auth(java.lang.String, boolean):java.lang.String");
    }

    public synchronized Map<String, String> authV2(String str, boolean z2) {
        return com.alipay.sdk.util.j.a(auth(str, z2));
    }

    private e.a a() {
        return new a(this);
    }

    private String a(Activity activity, String str) {
        String strA = new com.alipay.sdk.sys.a(this.f3063c).a(str);
        if (a(activity)) {
            String strA2 = new com.alipay.sdk.util.e(activity, new a(this)).a(strA);
            if (TextUtils.equals(strA2, com.alipay.sdk.util.e.f3365b)) {
                return b(activity, strA);
            }
            return TextUtils.isEmpty(strA2) ? i.a() : strA2;
        }
        return b(activity, strA);
    }

    private String b(Activity activity, String str) {
        j jVarA;
        b();
        try {
            try {
                List<com.alipay.sdk.protocol.b> listA = com.alipay.sdk.protocol.b.a(new com.alipay.sdk.packet.impl.a().a(activity, str).a().optJSONObject(com.alipay.sdk.cons.c.f3228c).optJSONObject(com.alipay.sdk.cons.c.f3229d));
                c();
                for (int i2 = 0; i2 < listA.size(); i2++) {
                    if (listA.get(i2).f3313a == com.alipay.sdk.protocol.a.WapPay) {
                        return a(listA.get(i2));
                    }
                }
            } catch (IOException e2) {
                j jVarA2 = j.a(j.NETWORK_ERROR.f3105h);
                com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3111a, e2);
                c();
                jVarA = jVarA2;
            } catch (Throwable th) {
                com.alipay.sdk.app.statistic.a.a(com.alipay.sdk.app.statistic.c.f3112b, com.alipay.sdk.app.statistic.c.f3129s, th);
            }
            c();
            jVarA = null;
            if (jVarA == null) {
                jVarA = j.a(j.FAILED.f3105h);
            }
            return i.a(jVarA.f3105h, jVarA.f3106i, "");
        } finally {
            c();
        }
    }

    private static boolean a(Context context) throws PackageManager.NameNotFoundException {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(l.a(), 128);
            if (packageInfo == null) {
                return false;
            }
            return packageInfo.versionCode >= 73;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    private String a(com.alipay.sdk.protocol.b bVar) {
        String[] strArr = bVar.f3314b;
        Bundle bundle = new Bundle();
        bundle.putString("url", strArr[0]);
        Intent intent = new Intent(this.f3063c, (Class<?>) H5AuthActivity.class);
        intent.putExtras(bundle);
        this.f3063c.startActivity(intent);
        Object obj = f3061a;
        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException unused) {
                return i.a();
            }
        }
        String str = i.f3096a;
        return TextUtils.isEmpty(str) ? i.a() : str;
    }
}
