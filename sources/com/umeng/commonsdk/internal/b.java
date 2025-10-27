package com.umeng.commonsdk.internal;

import android.content.Context;

/* loaded from: classes6.dex */
public class b {

    /* renamed from: b, reason: collision with root package name */
    private static b f23193b;

    /* renamed from: a, reason: collision with root package name */
    private Context f23194a;

    /* renamed from: c, reason: collision with root package name */
    private c f23195c;

    private b(Context context) {
        this.f23194a = context;
        this.f23195c = new c(context);
    }

    public static synchronized b a(Context context) {
        if (f23193b == null) {
            f23193b = new b(context.getApplicationContext());
        }
        return f23193b;
    }

    public c a() {
        return this.f23195c;
    }
}
