package com.xiaomi.push;

import android.os.Build;
import android.util.Log;
import java.lang.reflect.Field;

/* loaded from: classes6.dex */
public class bm {

    /* renamed from: a, reason: collision with root package name */
    private static Class f24643a = null;

    /* renamed from: a, reason: collision with other field name */
    private static String f225a = "NLPBuild";

    /* renamed from: a, reason: collision with other field name */
    private static Field f226a = null;

    /* renamed from: a, reason: collision with other field name */
    private static boolean f227a = false;

    /* renamed from: b, reason: collision with other field name */
    private static Field f228b;

    /* renamed from: c, reason: collision with other field name */
    private static Field f229c;

    /* renamed from: d, reason: collision with root package name */
    private static Field f24646d;

    /* renamed from: b, reason: collision with root package name */
    private static String f24644b = Build.BRAND;

    /* renamed from: c, reason: collision with root package name */
    private static String f24645c = Build.TYPE;

    static {
        boolean z2 = true;
        try {
            Class<?> cls = Class.forName("miui.os.Build");
            f24643a = cls;
            f226a = cls.getField("IS_CTA_BUILD");
            f228b = f24643a.getField("IS_ALPHA_BUILD");
            f229c = f24643a.getField("IS_DEVELOPMENT_VERSION");
            f24646d = f24643a.getField("IS_STABLE_VERSION");
            z2 = false;
        } catch (ClassNotFoundException | NoSuchFieldException | Exception unused) {
        }
        if (z2) {
            f24643a = null;
            f226a = null;
            f228b = null;
            f229c = null;
            f24646d = null;
        }
    }

    public static String a() {
        return "3rdROM-" + f24645c;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m247a() {
        if (f227a) {
            Log.d(f225a, "brand=" + f24644b);
        }
        String str = f24644b;
        return str != null && str.equalsIgnoreCase("xiaomi");
    }

    public static boolean b() throws IllegalAccessException, IllegalArgumentException {
        Class cls;
        Field field;
        if (!m247a() || (cls = f24643a) == null || (field = f228b) == null) {
            return false;
        }
        try {
            boolean z2 = field.getBoolean(cls);
            if (f227a) {
                Log.d(f225a, "is alpha version=" + z2);
            }
            return z2;
        } catch (IllegalAccessException unused) {
            return false;
        }
    }

    public static boolean c() throws IllegalAccessException, IllegalArgumentException {
        Class cls;
        Field field;
        if (!m247a() || (cls = f24643a) == null || (field = f229c) == null) {
            return false;
        }
        try {
            boolean z2 = field.getBoolean(cls);
            if (f227a) {
                Log.d(f225a, "is dev version=" + z2);
            }
            return z2;
        } catch (IllegalAccessException unused) {
            return false;
        }
    }

    public static boolean d() throws IllegalAccessException, IllegalArgumentException {
        Class cls;
        Field field;
        if (!m247a() || (cls = f24643a) == null || (field = f24646d) == null) {
            return false;
        }
        try {
            boolean z2 = field.getBoolean(cls);
            if (f227a) {
                Log.d(f225a, "is stable version=" + z2);
            }
            return z2;
        } catch (IllegalAccessException unused) {
            return false;
        }
    }
}
