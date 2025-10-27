package com.tencent.tbs.one.impl.c.a;

import dalvik.system.DexClassLoader;

/* loaded from: classes6.dex */
public final class e extends DexClassLoader {

    /* renamed from: a, reason: collision with root package name */
    public final String[] f21883a;

    /* renamed from: b, reason: collision with root package name */
    public final boolean f21884b;

    public e(String str, String str2, String str3, ClassLoader classLoader, String[] strArr) {
        super(str, str2, str3, classLoader);
        this.f21883a = strArr;
        this.f21884b = strArr != null && strArr.length > 0;
    }

    private Class<?> a(String str) throws ClassNotFoundException {
        ClassLoader parent;
        Class<?> clsFindLoadedClass = findLoadedClass(str);
        if (clsFindLoadedClass != null) {
            return clsFindLoadedClass;
        }
        try {
            clsFindLoadedClass = findClass(str);
        } catch (ClassNotFoundException unused) {
        }
        return (clsFindLoadedClass != null || (parent = getParent()) == null) ? clsFindLoadedClass : parent.loadClass(str);
    }

    @Override // java.lang.ClassLoader
    public final Class<?> loadClass(String str, boolean z2) {
        if (this.f21884b && str != null) {
            for (String str2 : this.f21883a) {
                if (str.startsWith(str2)) {
                    return super.loadClass(str, z2);
                }
            }
        }
        return a(str);
    }
}
