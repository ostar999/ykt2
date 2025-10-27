package com.meizu.cloud.pushsdk.base.a;

import com.meizu.cloud.pushsdk.base.h;
import java.lang.reflect.Constructor;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    private String f9222a = "ReflectConstructor";

    /* renamed from: b, reason: collision with root package name */
    private a f9223b;

    /* renamed from: c, reason: collision with root package name */
    private Class<?>[] f9224c;

    public b(a aVar, Class<?>... clsArr) {
        this.f9223b = aVar;
        this.f9224c = clsArr;
    }

    public <T> d<T> a(Object... objArr) throws NoSuchMethodException, SecurityException {
        d<T> dVar = new d<>();
        try {
            Constructor<?> declaredConstructor = this.f9223b.a().getDeclaredConstructor(this.f9224c);
            declaredConstructor.setAccessible(true);
            dVar.f9231b = (T) declaredConstructor.newInstance(objArr);
            dVar.f9230a = true;
        } catch (Exception e2) {
            h.b().a(this.f9222a, "newInstance", e2);
        }
        return dVar;
    }
}
