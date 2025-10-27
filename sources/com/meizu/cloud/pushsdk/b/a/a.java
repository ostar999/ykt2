package com.meizu.cloud.pushsdk.b.a;

import com.meizu.cloud.pushinternal.DebugLogger;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static boolean f8968a = false;

    /* renamed from: b, reason: collision with root package name */
    private static String f8969b = "AndroidNetworking";

    public static void a() {
        f8968a = true;
    }

    public static void a(String str) {
        if (f8968a) {
            DebugLogger.d(f8969b, str);
        }
    }

    public static void b(String str) {
        if (f8968a) {
            DebugLogger.i(f8969b, str);
        }
    }
}
