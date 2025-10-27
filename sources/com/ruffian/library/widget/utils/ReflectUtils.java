package com.ruffian.library.widget.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public class ReflectUtils {
    public static Method getDeclaredMethod(Object obj, String str, Class<?>... clsArr) {
        for (Class<?> superclass = obj.getClass(); superclass != Object.class; superclass = superclass.getSuperclass()) {
            try {
                return superclass.getDeclaredMethod(str, clsArr);
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public static Object invokeMethod(Object obj, String str, Class<?>[] clsArr, Object[] objArr) throws SecurityException {
        Method declaredMethod = getDeclaredMethod(obj, str, clsArr);
        if (declaredMethod == null) {
            return null;
        }
        try {
            declaredMethod.setAccessible(true);
            return declaredMethod.invoke(obj, objArr);
        } catch (IllegalAccessException e2) {
            e2.printStackTrace();
            return null;
        } catch (IllegalArgumentException e3) {
            e3.printStackTrace();
            return null;
        } catch (InvocationTargetException e4) {
            e4.printStackTrace();
            return null;
        }
    }
}
