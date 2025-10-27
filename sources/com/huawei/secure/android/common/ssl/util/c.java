package com.huawei.secure.android.common.ssl.util;

import android.content.Context;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static Context f8411a;

    public static Context a() {
        return f8411a;
    }

    public static void a(Context context) {
        if (context == null || f8411a != null) {
            return;
        }
        f8411a = context.getApplicationContext();
    }
}
