package com.meizu.cloud.pushsdk.base.a;

import com.meizu.cloud.pushsdk.base.h;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;

/* loaded from: classes4.dex */
public class c {

    /* renamed from: b, reason: collision with root package name */
    private static HashMap<String, Method> f9225b = new HashMap<>();

    /* renamed from: a, reason: collision with root package name */
    private String f9226a = "ReflectMethod";

    /* renamed from: c, reason: collision with root package name */
    private com.meizu.cloud.pushsdk.base.a.a f9227c;

    /* renamed from: d, reason: collision with root package name */
    private String f9228d;

    /* renamed from: e, reason: collision with root package name */
    private Class<?>[] f9229e;

    public class a {
    }

    public c(com.meizu.cloud.pushsdk.base.a.a aVar, String str, Class<?>... clsArr) {
        this.f9227c = aVar;
        this.f9228d = str;
        this.f9229e = clsArr;
    }

    private Class<?> a(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        return cls.isPrimitive() ? Boolean.TYPE == cls ? Boolean.class : Integer.TYPE == cls ? Integer.class : Long.TYPE == cls ? Long.class : Short.TYPE == cls ? Short.class : Byte.TYPE == cls ? Byte.class : Double.TYPE == cls ? Double.class : Float.TYPE == cls ? Float.class : Character.TYPE == cls ? Character.class : Void.TYPE == cls ? Void.class : cls : cls;
    }

    private Method a() throws NoSuchMethodException, ClassNotFoundException, SecurityException {
        Class<?> clsA = this.f9227c.a();
        for (Method method : clsA.getMethods()) {
            if (a(method, this.f9228d, this.f9229e)) {
                return method;
            }
        }
        for (Method method2 : clsA.getDeclaredMethods()) {
            if (a(method2, this.f9228d, this.f9229e)) {
                return method2;
            }
        }
        throw new NoSuchMethodException("No similar method " + this.f9228d + " with params " + Arrays.toString(this.f9229e) + " could be found on type " + clsA);
    }

    private boolean a(Method method, String str, Class<?>[] clsArr) {
        return method.getName().equals(str) && a(method.getParameterTypes(), clsArr);
    }

    private boolean a(Class<?>[] clsArr, Class<?>[] clsArr2) {
        if (clsArr.length != clsArr2.length) {
            return false;
        }
        for (int i2 = 0; i2 < clsArr2.length; i2++) {
            if (clsArr2[i2] != a.class && !a(clsArr[i2]).isAssignableFrom(a(clsArr2[i2]))) {
                return false;
            }
        }
        return true;
    }

    private String b() throws ClassNotFoundException {
        StringBuffer stringBuffer = new StringBuffer(this.f9227c.a().getName());
        stringBuffer.append(this.f9228d);
        for (Class<?> cls : this.f9229e) {
            stringBuffer.append(cls.getName());
        }
        return stringBuffer.toString();
    }

    public <T> d<T> a(Object obj, Object... objArr) throws NoSuchMethodException, SecurityException {
        d<T> dVar = new d<>();
        try {
            String strB = b();
            Method methodA = f9225b.get(strB);
            if (methodA == null) {
                if (this.f9229e.length == objArr.length) {
                    methodA = this.f9227c.a().getMethod(this.f9228d, this.f9229e);
                } else {
                    if (objArr.length > 0) {
                        this.f9229e = new Class[objArr.length];
                        for (int i2 = 0; i2 < objArr.length; i2++) {
                            this.f9229e[i2] = objArr[i2].getClass();
                        }
                    }
                    methodA = a();
                }
                f9225b.put(strB, methodA);
            }
            methodA.setAccessible(true);
            dVar.f9231b = (T) methodA.invoke(obj, objArr);
            dVar.f9230a = true;
        } catch (Exception e2) {
            h.b().a(this.f9226a, "invoke", e2);
        }
        return dVar;
    }

    public <T> d<T> a(Object... objArr) {
        try {
            return a(this.f9227c.a(), objArr);
        } catch (ClassNotFoundException unused) {
            return new d<>();
        }
    }
}
