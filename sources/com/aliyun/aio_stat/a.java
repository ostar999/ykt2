package com.aliyun.aio_stat;

import android.content.Context;

/* loaded from: classes2.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static Context f3500a;

    public static Context a() {
        return f3500a;
    }

    public static void a(Context context) {
        f3500a = context;
        AioStat.nSetApplicationContext(context);
    }
}
