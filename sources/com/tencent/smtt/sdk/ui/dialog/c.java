package com.tencent.smtt.sdk.ui.dialog;

import android.content.Context;
import android.util.DisplayMetrics;
import java.lang.reflect.Field;

/* loaded from: classes6.dex */
public class c {

    /* renamed from: a, reason: collision with root package name */
    private static float f21326a = -1.0f;

    /* renamed from: b, reason: collision with root package name */
    private static int f21327b = -1;

    /* renamed from: c, reason: collision with root package name */
    private static int f21328c = -1;

    public static int a(Context context) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (f21327b == -1) {
            b(context);
        }
        return f21327b;
    }

    public static int a(Context context, float f2) {
        return b(context, (f2 * 160.0f) / 320.0f);
    }

    private static int b(Context context, float f2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (f21326a == -1.0f) {
            b(context);
        }
        return (int) ((f2 * f21326a) + 0.5f);
    }

    private static void b(Context context) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (f21326a < 0.0f) {
            try {
                Field declaredField = Class.forName("com.tencent.mobileqq.app.FontSettingManager").getDeclaredField("customMetrics");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(null);
                if (obj instanceof DisplayMetrics) {
                    DisplayMetrics displayMetrics = (DisplayMetrics) obj;
                    f21326a = displayMetrics.density;
                    f21327b = displayMetrics.heightPixels;
                }
            } catch (Exception unused) {
                DisplayMetrics displayMetrics2 = context.getResources().getDisplayMetrics();
                f21326a = displayMetrics2.density;
                f21327b = displayMetrics2.heightPixels;
            }
        }
    }
}
