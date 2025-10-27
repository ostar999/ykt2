package com.huawei.hms.hatool;

import android.content.Context;

/* loaded from: classes4.dex */
public final class h1 {

    /* renamed from: b, reason: collision with root package name */
    public static h1 f7740b;

    /* renamed from: c, reason: collision with root package name */
    public static final Object f7741c = new Object();

    /* renamed from: a, reason: collision with root package name */
    public Context f7742a;

    public static h1 a() {
        if (f7740b == null) {
            b();
        }
        return f7740b;
    }

    public static synchronized void b() {
        if (f7740b == null) {
            f7740b = new h1();
        }
    }

    public void a(Context context) {
        synchronized (f7741c) {
            if (this.f7742a != null) {
                y.f("hmsSdk", "DataManager already initialized.");
                return;
            }
            this.f7742a = context;
            i.c().b().a(this.f7742a);
            i.c().b().j(context.getPackageName());
            z0.a().a(context);
        }
    }

    public void a(String str) {
        y.c("hmsSdk", "HiAnalyticsDataManager.setAppid(String appid) is execute.");
        Context context = this.f7742a;
        if (context == null) {
            y.e("hmsSdk", "sdk is not init");
        } else {
            i.c().b().i(s0.a(com.heytap.mcssdk.constant.b.f7196u, str, "[a-zA-Z0-9_][a-zA-Z0-9. _-]{0,255}", context.getPackageName()));
        }
    }
}
