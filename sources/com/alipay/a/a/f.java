package com.alipay.a.a;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public final class f {

    /* renamed from: a, reason: collision with root package name */
    private static List<j> f2920a;

    static {
        ArrayList arrayList = new ArrayList();
        f2920a = arrayList;
        arrayList.add(new l());
        f2920a.add(new d());
        f2920a.add(new c());
        f2920a.add(new h());
        f2920a.add(new b());
        f2920a.add(new a());
        f2920a.add(new g());
    }

    public static String a(Object obj) {
        if (obj == null) {
            return null;
        }
        Object objB = b(obj);
        if (com.alipay.a.b.a.a(objB.getClass())) {
            return org.json.alipay.b.c(objB.toString());
        }
        if (Collection.class.isAssignableFrom(objB.getClass())) {
            return new org.json.alipay.a((Collection) objB).toString();
        }
        if (Map.class.isAssignableFrom(objB.getClass())) {
            return new org.json.alipay.b((Map) objB).toString();
        }
        throw new IllegalArgumentException("Unsupported Class : " + objB.getClass());
    }

    public static Object b(Object obj) {
        Object objA;
        if (obj == null) {
            return null;
        }
        for (j jVar : f2920a) {
            if (jVar.a(obj.getClass()) && (objA = jVar.a(obj)) != null) {
                return objA;
            }
        }
        throw new IllegalArgumentException("Unsupported Class : " + obj.getClass());
    }
}
