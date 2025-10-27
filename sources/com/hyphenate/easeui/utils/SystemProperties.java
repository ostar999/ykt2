package com.hyphenate.easeui.utils;

import android.text.TextUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class SystemProperties {
    private static final Method getStringProperty = getMethod(getClass("android.os.SystemProperties"));

    private static String defaultString(String str, String str2) {
        return str == null ? str2 : str;
    }

    public static String get(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method method = getStringProperty;
        if (method != null) {
            try {
                Object objInvoke = method.invoke(null, str);
                return objInvoke == null ? "" : trimToEmpty(objInvoke.toString());
            } catch (Exception unused) {
            }
        }
        return "";
    }

    private static Class<?> getClass(String str) {
        try {
            try {
                return Class.forName(str);
            } catch (ClassNotFoundException unused) {
                return null;
            }
        } catch (ClassNotFoundException unused2) {
            return ClassLoader.getSystemClassLoader().loadClass(str);
        }
    }

    private static Method getMethod(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        try {
            return cls.getMethod("get", String.class);
        } catch (Exception unused) {
            return null;
        }
    }

    private static String trim(String str) {
        if (str == null) {
            return null;
        }
        return str.trim();
    }

    private static String trimToEmpty(String str) {
        return str == null ? "" : str.trim();
    }

    private static String trimToNull(String str) {
        String strTrim = trim(str);
        if (TextUtils.isEmpty(strTrim)) {
            return null;
        }
        return strTrim;
    }

    public static String get(String str, String str2) {
        Method method = getStringProperty;
        if (method != null) {
            try {
                return defaultString(trimToNull((String) method.invoke(null, str)), str2);
            } catch (Exception unused) {
            }
        }
        return str2;
    }
}
