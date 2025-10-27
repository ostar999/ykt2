package com.google.common.reflect;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

@Beta
/* loaded from: classes4.dex */
public final class Reflection {
    private Reflection() {
    }

    public static String getPackageName(Class<?> cls) {
        return getPackageName(cls.getName());
    }

    public static void initialize(Class<?>... clsArr) throws ClassNotFoundException {
        for (Class<?> cls : clsArr) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
            } catch (ClassNotFoundException e2) {
                throw new AssertionError(e2);
            }
        }
    }

    public static <T> T newProxy(Class<T> cls, InvocationHandler invocationHandler) {
        Preconditions.checkNotNull(invocationHandler);
        Preconditions.checkArgument(cls.isInterface(), "%s is not an interface", cls);
        return cls.cast(Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, invocationHandler));
    }

    public static String getPackageName(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf < 0 ? "" : str.substring(0, iLastIndexOf);
    }
}
