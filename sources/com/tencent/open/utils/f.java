package com.tencent.open.utils;

import android.content.Context;
import java.io.File;

/* loaded from: classes6.dex */
public final class f {

    /* renamed from: a, reason: collision with root package name */
    private static Context f20667a;

    public static final Context a() {
        Context context = f20667a;
        if (context == null) {
            return null;
        }
        return context;
    }

    public static final String b() {
        return a() == null ? "" : a().getPackageName();
    }

    public static final File c() {
        if (a() == null) {
            return null;
        }
        return a().getFilesDir();
    }

    public static final File d() {
        Context contextA = a();
        if (contextA != null) {
            return contextA.getCacheDir();
        }
        return null;
    }

    public static final File e() {
        return a((String) null);
    }

    public static final void a(Context context) {
        f20667a = context;
    }

    public static final File a(String str) {
        return k.h(a(), str);
    }
}
