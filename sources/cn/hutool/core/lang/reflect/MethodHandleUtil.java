package cn.hutool.core.lang.reflect;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ReflectUtil;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class MethodHandleUtil {
    public static MethodHandle findConstructor(Class<?> cls, Class<?>... clsArr) {
        return findConstructor(cls, MethodType.methodType((Class<?>) Void.TYPE, (Class<?>[]) clsArr));
    }

    public static MethodHandle findMethod(Class<?> cls, String str, MethodType methodType) throws IllegalAccessException, NoSuchMethodException {
        MethodHandle methodHandleFindStatic;
        if (CharSequenceUtil.isBlank(str)) {
            return findConstructor(cls, methodType);
        }
        MethodHandles.Lookup lookup = lookup(cls);
        try {
            methodHandleFindStatic = lookup.findVirtual(cls, str, methodType);
        } catch (IllegalAccessException | NoSuchMethodException unused) {
            methodHandleFindStatic = null;
        }
        if (methodHandleFindStatic == null) {
            try {
                methodHandleFindStatic = lookup.findStatic(cls, str, methodType);
            } catch (IllegalAccessException | NoSuchMethodException unused2) {
            }
        }
        if (methodHandleFindStatic != null) {
            return methodHandleFindStatic;
        }
        try {
            return lookup.findSpecial(cls, str, methodType, cls);
        } catch (IllegalAccessException e2) {
            throw new UtilException(e2);
        } catch (NoSuchMethodException unused3) {
            return methodHandleFindStatic;
        }
    }

    public static <T> T invoke(Object obj, Method method, Object... objArr) {
        return (T) invoke(false, obj, method, objArr);
    }

    public static <T> T invokeSpecial(Object obj, String str, Object... objArr) throws SecurityException, IllegalArgumentException {
        Assert.notNull(obj, "Object to get method must be not null!", new Object[0]);
        Assert.notBlank(str, "Method name must be not blank!", new Object[0]);
        Method methodOfObj = ReflectUtil.getMethodOfObj(obj, str, objArr);
        if (methodOfObj != null) {
            return (T) invokeSpecial(obj, methodOfObj, objArr);
        }
        throw new UtilException("No such method: [{}] from [{}]", str, obj.getClass());
    }

    public static MethodHandles.Lookup lookup(Class<?> cls) {
        return LookupFactory.lookup(cls);
    }

    public static MethodHandle findConstructor(Class<?> cls, MethodType methodType) {
        try {
            return lookup(cls).findConstructor(cls, methodType);
        } catch (IllegalAccessException e2) {
            throw new UtilException(e2);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static <T> T invoke(boolean z2, Object obj, Method method, Object... objArr) throws IllegalArgumentException {
        Assert.notNull(method, "Method must be not null!", new Object[0]);
        Class<?> declaringClass = method.getDeclaringClass();
        MethodHandles.Lookup lookup = lookup(declaringClass);
        try {
            MethodHandle methodHandleUnreflectSpecial = z2 ? lookup.unreflectSpecial(method, declaringClass) : lookup.unreflect(method);
            if (obj != null) {
                methodHandleUnreflectSpecial = methodHandleUnreflectSpecial.bindTo(obj);
            }
            return (T) methodHandleUnreflectSpecial.invokeWithArguments(objArr);
        } catch (Throwable th) {
            throw new UtilException(th);
        }
    }

    public static <T> T invokeSpecial(Object obj, Method method, Object... objArr) {
        return (T) invoke(true, obj, method, objArr);
    }
}
