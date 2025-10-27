package com.umeng.commonsdk.config;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class b implements f {

    /* renamed from: a, reason: collision with root package name */
    private static Map<String, Boolean> f23041a = new HashMap();

    /* renamed from: b, reason: collision with root package name */
    private static Object f23042b = new Object();

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private static final b f23043a = new b();

        private a() {
        }
    }

    public static b b() {
        return a.f23043a;
    }

    public void a() {
        synchronized (f23042b) {
            f23041a.clear();
        }
    }

    private b() {
    }

    public static boolean a(String str) {
        if (!d.a(str)) {
            return false;
        }
        synchronized (f23042b) {
            if (!f23041a.containsKey(str)) {
                return true;
            }
            return f23041a.get(str).booleanValue();
        }
    }

    @Override // com.umeng.commonsdk.config.f
    public void a(String str, Boolean bool) {
        if (d.a(str)) {
            synchronized (f23042b) {
                Map<String, Boolean> map = f23041a;
                if (map != null) {
                    map.put(str, bool);
                }
            }
        }
    }
}
