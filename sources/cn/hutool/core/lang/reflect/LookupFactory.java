package cn.hutool.core.lang.reflect;

import cn.hutool.core.exceptions.UtilException;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class LookupFactory {
    private static final int ALLOWED_MODES = 15;
    private static Constructor<MethodHandles.Lookup> java8LookupConstructor;
    private static Method privateLookupInMethod;

    static {
        try {
            privateLookupInMethod = MethodHandles.class.getMethod("privateLookupIn", Class.class, MethodHandles.Lookup.class);
        } catch (NoSuchMethodException unused) {
        }
        if (privateLookupInMethod == null) {
            try {
                Constructor<MethodHandles.Lookup> declaredConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
                java8LookupConstructor = declaredConstructor;
                declaredConstructor.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                throw new IllegalStateException("There is neither 'privateLookupIn(Class, Lookup)' nor 'Lookup(Class, int)' method in java.lang.invoke.MethodHandles.", e2);
            }
        }
    }

    public static MethodHandles.Lookup lookup(Class<?> cls) {
        Method method = privateLookupInMethod;
        if (method != null) {
            try {
                return (MethodHandles.Lookup) method.invoke(MethodHandles.class, cls, MethodHandles.lookup());
            } catch (IllegalAccessException | InvocationTargetException e2) {
                throw new UtilException(e2);
            }
        }
        try {
            return java8LookupConstructor.newInstance(cls, 15);
        } catch (Exception e3) {
            throw new IllegalStateException("no 'Lookup(Class, int)' method in java.lang.invoke.MethodHandles.", e3);
        }
    }
}
