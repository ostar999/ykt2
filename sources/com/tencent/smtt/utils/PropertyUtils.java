package com.tencent.smtt.utils;

import android.text.TextUtils;
import java.lang.reflect.Method;

/* loaded from: classes6.dex */
public class PropertyUtils {

    /* renamed from: a, reason: collision with root package name */
    private static Class f21428a;

    /* renamed from: b, reason: collision with root package name */
    private static Method f21429b;

    static {
        try {
            Class<?> cls = Class.forName("android.os.SystemProperties");
            f21428a = cls;
            f21429b = cls.getDeclaredMethod("get", String.class, String.class);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    private static String a(String str, String str2) {
        Method method;
        Class cls = f21428a;
        if (cls == null || (method = f21429b) == null) {
            return str2;
        }
        try {
            return (String) method.invoke(cls, str, str2);
        } catch (Throwable th) {
            th.printStackTrace();
            return str2;
        }
    }

    public static String getQuickly(String str, String str2) {
        return TextUtils.isEmpty(str) ? str2 : a(str, str2);
    }
}
