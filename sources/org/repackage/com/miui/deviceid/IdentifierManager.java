package org.repackage.com.miui.deviceid;

import android.content.Context;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes9.dex */
public class IdentifierManager {

    /* renamed from: a, reason: collision with root package name */
    private static final String f28019a = "IdentifierManager";

    /* renamed from: b, reason: collision with root package name */
    private static Object f28020b;

    /* renamed from: c, reason: collision with root package name */
    private static Class<?> f28021c;

    /* renamed from: d, reason: collision with root package name */
    private static Method f28022d;

    /* renamed from: e, reason: collision with root package name */
    private static Method f28023e;

    /* renamed from: f, reason: collision with root package name */
    private static Method f28024f;

    /* renamed from: g, reason: collision with root package name */
    private static Method f28025g;

    static {
        try {
            Class<?> cls = Class.forName("com.android.id.impl.IdProviderImpl");
            f28021c = cls;
            f28020b = cls.newInstance();
            f28022d = f28021c.getMethod("getUDID", Context.class);
            f28023e = f28021c.getMethod("getOAID", Context.class);
            f28024f = f28021c.getMethod("getVAID", Context.class);
            f28025g = f28021c.getMethod("getAAID", Context.class);
        } catch (Exception e2) {
            Log.e(f28019a, "reflect exception!", e2);
        }
    }

    public static boolean a() {
        return (f28021c == null || f28020b == null) ? false : true;
    }

    public static String b(Context context) {
        return a(context, f28023e);
    }

    public static String c(Context context) {
        return a(context, f28024f);
    }

    public static String d(Context context) {
        return a(context, f28025g);
    }

    public static String a(Context context) {
        return a(context, f28022d);
    }

    private static String a(Context context, Method method) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Object obj = f28020b;
        if (obj == null || method == null) {
            return null;
        }
        try {
            Object objInvoke = method.invoke(obj, context);
            if (objInvoke != null) {
                return (String) objInvoke;
            }
            return null;
        } catch (Exception e2) {
            Log.e(f28019a, "invoke exception!", e2);
            return null;
        }
    }
}
