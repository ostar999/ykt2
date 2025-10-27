package org.eclipse.jetty.util;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/* loaded from: classes9.dex */
public class IntrospectionUtil {
    public static boolean checkParams(Class<?>[] clsArr, Class<?>[] clsArr2, boolean z2) {
        int i2;
        if (clsArr == null) {
            return clsArr2 == null;
        }
        if (clsArr2 == null || clsArr.length != clsArr2.length) {
            return false;
        }
        if (clsArr.length == 0) {
            return true;
        }
        if (z2) {
            i2 = 0;
            while (i2 < clsArr.length && clsArr[i2].equals(clsArr2[i2])) {
                i2++;
            }
        } else {
            i2 = 0;
            while (i2 < clsArr.length && clsArr[i2].isAssignableFrom(clsArr2[i2])) {
                i2++;
            }
        }
        return i2 == clsArr.length;
    }

    public static boolean containsSameFieldName(Field field, Class<?> cls, boolean z2) {
        if (z2 && !cls.getPackage().equals(field.getDeclaringClass().getPackage())) {
            return false;
        }
        Field[] declaredFields = cls.getDeclaredFields();
        boolean z3 = false;
        for (int i2 = 0; i2 < declaredFields.length && !z3; i2++) {
            if (declaredFields[i2].getName().equals(field.getName())) {
                z3 = true;
            }
        }
        return z3;
    }

    public static boolean containsSameMethodSignature(Method method, Class<?> cls, boolean z2) throws SecurityException {
        if (z2 && !cls.getPackage().equals(method.getDeclaringClass().getPackage())) {
            return false;
        }
        Method[] declaredMethods = cls.getDeclaredMethods();
        boolean z3 = false;
        for (int i2 = 0; i2 < declaredMethods.length && !z3; i2++) {
            if (isSameSignature(method, declaredMethods[i2])) {
                z3 = true;
            }
        }
        return z3;
    }

    public static Field findField(Class<?> cls, String str, Class<?> cls2, boolean z2, boolean z3) throws NoSuchFieldException {
        if (cls == null) {
            throw new NoSuchFieldException("No class");
        }
        if (str == null) {
            throw new NoSuchFieldException("No field name");
        }
        try {
            Field declaredField = cls.getDeclaredField(str);
            if (z3) {
                if (declaredField.getType().equals(cls2)) {
                    return declaredField;
                }
            } else if (declaredField.getType().isAssignableFrom(cls2)) {
                return declaredField;
            }
            if (z2) {
                return findInheritedField(cls.getPackage(), cls.getSuperclass(), str, cls2, z3);
            }
            throw new NoSuchFieldException("No field with name " + str + " in class " + cls.getName() + " of type " + cls2);
        } catch (NoSuchFieldException unused) {
            return findInheritedField(cls.getPackage(), cls.getSuperclass(), str, cls2, z3);
        }
    }

    public static Field findInheritedField(Package r12, Class<?> cls, String str, Class<?> cls2, boolean z2) throws NoSuchFieldException {
        if (cls == null) {
            throw new NoSuchFieldException("No class");
        }
        if (str == null) {
            throw new NoSuchFieldException("No field name");
        }
        try {
            Field declaredField = cls.getDeclaredField(str);
            return (isInheritable(r12, declaredField) && isTypeCompatible(cls2, declaredField.getType(), z2)) ? declaredField : findInheritedField(cls.getPackage(), cls.getSuperclass(), str, cls2, z2);
        } catch (NoSuchFieldException unused) {
            return findInheritedField(cls.getPackage(), cls.getSuperclass(), str, cls2, z2);
        }
    }

    public static Method findInheritedMethod(Package r4, Class<?> cls, String str, Class<?>[] clsArr, boolean z2) throws NoSuchMethodException, SecurityException {
        if (cls == null) {
            throw new NoSuchMethodException("No class");
        }
        if (str == null) {
            throw new NoSuchMethodException("No method name");
        }
        Method[] declaredMethods = cls.getDeclaredMethods();
        Method method = null;
        for (int i2 = 0; i2 < declaredMethods.length && method == null; i2++) {
            if (declaredMethods[i2].getName().equals(str) && isInheritable(r4, declaredMethods[i2]) && checkParams(declaredMethods[i2].getParameterTypes(), clsArr, z2)) {
                method = declaredMethods[i2];
            }
        }
        return method != null ? method : findInheritedMethod(cls.getPackage(), cls.getSuperclass(), str, clsArr, z2);
    }

    public static Method findMethod(Class<?> cls, String str, Class<?>[] clsArr, boolean z2, boolean z3) throws NoSuchMethodException, SecurityException {
        if (cls == null) {
            throw new NoSuchMethodException("No class");
        }
        if (str == null || str.trim().equals("")) {
            throw new NoSuchMethodException("No method name");
        }
        Method[] declaredMethods = cls.getDeclaredMethods();
        Method method = null;
        for (int i2 = 0; i2 < declaredMethods.length && method == null; i2++) {
            if (declaredMethods[i2].getName().equals(str)) {
                if (checkParams(declaredMethods[i2].getParameterTypes(), clsArr == null ? new Class[0] : clsArr, z3)) {
                    method = declaredMethods[i2];
                }
            }
        }
        if (method != null) {
            return method;
        }
        if (z2) {
            return findInheritedMethod(cls.getPackage(), cls.getSuperclass(), str, clsArr, z3);
        }
        throw new NoSuchMethodException("No such method " + str + " on class " + cls.getName());
    }

    public static boolean isInheritable(Package r4, Member member) {
        if (r4 == null || member == null) {
            return false;
        }
        int modifiers = member.getModifiers();
        if (Modifier.isPublic(modifiers) || Modifier.isProtected(modifiers)) {
            return true;
        }
        return !Modifier.isPrivate(modifiers) && r4.equals(member.getDeclaringClass().getPackage());
    }

    public static boolean isJavaBeanCompliantSetter(Method method) {
        return method != null && method.getReturnType() == Void.TYPE && method.getName().startsWith("set") && method.getParameterTypes().length == 1;
    }

    public static boolean isSameSignature(Method method, Method method2) {
        if (method == null || method2 == null) {
            return false;
        }
        return method.getName().equals(method2.getName()) && Arrays.asList(method.getParameterTypes()).containsAll(Arrays.asList(method2.getParameterTypes()));
    }

    public static boolean isTypeCompatible(Class<?> cls, Class<?> cls2, boolean z2) {
        if (cls == null) {
            return cls2 == null;
        }
        if (cls2 == null) {
            return false;
        }
        return z2 ? cls.equals(cls2) : cls.isAssignableFrom(cls2);
    }
}
