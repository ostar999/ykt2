package com.xiaomi.push.service;

import android.text.TextUtils;

/* loaded from: classes6.dex */
public class ar {

    /* renamed from: a, reason: collision with root package name */
    private static long f25588a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static String f1001a = "";

    public static String a() {
        if (TextUtils.isEmpty(f1001a)) {
            f1001a = com.xiaomi.push.ay.a(4);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(f1001a);
        long j2 = f25588a;
        f25588a = 1 + j2;
        sb.append(j2);
        return sb.toString();
    }
}
