package com.nirvana.tools.jsoner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/* loaded from: classes4.dex */
final class a {

    /* renamed from: b, reason: collision with root package name */
    private ConcurrentHashMap<String, b> f10664b = new ConcurrentHashMap<>();

    /* renamed from: a, reason: collision with root package name */
    List<Field> f10663a = new ArrayList();

    public a(Class cls) {
        while (!Object.class.equals(cls)) {
            Collections.addAll(this.f10663a, cls.getDeclaredFields());
            cls = cls.getSuperclass();
        }
        Iterator<Field> it = this.f10663a.iterator();
        while (it.hasNext()) {
            if (Modifier.isFinal(it.next().getModifiers())) {
                it.remove();
            }
        }
    }

    public final b a(String str) {
        return this.f10664b.get(str);
    }

    public final void a(String str, b bVar) {
        this.f10664b.put(str, bVar);
    }
}
