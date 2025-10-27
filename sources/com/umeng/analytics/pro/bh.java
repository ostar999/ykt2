package com.umeng.analytics.pro;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class bh implements Serializable {

    /* renamed from: d, reason: collision with root package name */
    private static Map<Class<? extends av>, Map<? extends bc, bh>> f22563d = new HashMap();

    /* renamed from: a, reason: collision with root package name */
    public final String f22564a;

    /* renamed from: b, reason: collision with root package name */
    public final byte f22565b;

    /* renamed from: c, reason: collision with root package name */
    public final bi f22566c;

    public bh(String str, byte b3, bi biVar) {
        this.f22564a = str;
        this.f22565b = b3;
        this.f22566c = biVar;
    }

    public static void a(Class<? extends av> cls, Map<? extends bc, bh> map) {
        f22563d.put(cls, map);
    }

    public static Map<? extends bc, bh> a(Class<? extends av> cls) throws IllegalAccessException, InstantiationException {
        if (!f22563d.containsKey(cls)) {
            try {
                cls.newInstance();
            } catch (IllegalAccessException e2) {
                throw new RuntimeException("IllegalAccessException for TBase class: " + cls.getName() + ", message: " + e2.getMessage());
            } catch (InstantiationException e3) {
                throw new RuntimeException("InstantiationException for TBase class: " + cls.getName() + ", message: " + e3.getMessage());
            }
        }
        return f22563d.get(cls);
    }
}
