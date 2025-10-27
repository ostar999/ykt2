package com.huawei.hms.hatool;

import android.content.Context;
import java.util.LinkedHashMap;

/* loaded from: classes4.dex */
public abstract class l1 {

    /* renamed from: a, reason: collision with root package name */
    public static j1 f7816a;

    public static synchronized j1 a() {
        if (f7816a == null) {
            f7816a = o1.c().b();
        }
        return f7816a;
    }

    public static void a(int i2, String str, LinkedHashMap<String, String> linkedHashMap) {
        if (a() == null || !w0.b().a()) {
            return;
        }
        if (i2 == 1 || i2 == 0) {
            f7816a.a(i2, str, linkedHashMap);
            return;
        }
        y.d("hmsSdk", "Data type no longer collects range.type: " + i2);
    }

    @Deprecated
    public static void a(Context context, String str, String str2) {
        if (a() != null) {
            f7816a.a(context, str, str2);
        }
    }

    public static boolean b() {
        return o1.c().a();
    }

    public static void c() {
        if (a() == null || !w0.b().a()) {
            return;
        }
        f7816a.a(-1);
    }
}
