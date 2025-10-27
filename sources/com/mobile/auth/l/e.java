package com.mobile.auth.l;

import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static ConcurrentHashMap<String, com.mobile.auth.e.b> f10417a = new ConcurrentHashMap<>(16);

    /* renamed from: b, reason: collision with root package name */
    private static ConcurrentHashMap<String, com.cmic.sso.sdk.a> f10418b = new ConcurrentHashMap<>();

    public static void a(String str, com.cmic.sso.sdk.a aVar) {
        if (str == null || aVar == null) {
            return;
        }
        f10418b.put(str, aVar);
    }

    public static void a(String str, com.mobile.auth.e.b bVar) {
        f10417a.put(str, bVar);
    }

    public static boolean a() {
        return f10417a.isEmpty();
    }

    public static boolean a(String str) {
        return !f10417a.containsKey(str);
    }

    public static void b(String str) {
        f10417a.remove(str);
    }

    public static com.mobile.auth.e.b c(String str) {
        return f10417a.get(str);
    }

    public static com.cmic.sso.sdk.a d(String str) {
        return str != null ? f10418b.get(str) : new com.cmic.sso.sdk.a(0);
    }
}
