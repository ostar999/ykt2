package com.meizu.cloud.pushsdk.base.a;

import java.util.HashMap;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static HashMap<String, Class<?>> f9218a = new HashMap<>();

    /* renamed from: b, reason: collision with root package name */
    private Class<?> f9219b;

    /* renamed from: c, reason: collision with root package name */
    private String f9220c;

    /* renamed from: d, reason: collision with root package name */
    private Object f9221d;

    private a(Object obj) {
        this.f9221d = obj;
    }

    private a(String str) {
        this.f9220c = str;
    }

    public static a a(Object obj) {
        return new a(obj);
    }

    public static a a(String str) {
        return new a(str);
    }

    public b a(Class<?>... clsArr) {
        return new b(this, clsArr);
    }

    public c a(String str, Class<?>... clsArr) {
        return new c(this, str, clsArr);
    }

    public Class<?> a() throws ClassNotFoundException {
        Class<?> cls = this.f9219b;
        if (cls != null) {
            return cls;
        }
        Object obj = this.f9221d;
        if (obj != null) {
            return obj.getClass();
        }
        Class<?> cls2 = f9218a.get(this.f9220c);
        if (cls2 != null) {
            return cls2;
        }
        Class<?> cls3 = Class.forName(this.f9220c);
        f9218a.put(this.f9220c, cls3);
        return cls3;
    }
}
