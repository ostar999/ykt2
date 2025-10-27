package com.hyphenate.easeui.utils;

import android.R;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import com.yikaobang.yixue.R2;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes4.dex */
public class StatusBarCompat {
    private static final int COLOR_DEFAULT = Color.parseColor("#20000000");
    private static final int INVALID_VAL = -1;

    @TargetApi(21)
    public static void compat(Activity activity, int i2) {
        if (i2 != -1) {
            activity.getWindow().setStatusBarColor(i2);
        }
    }

    public static int getStatusBarHeight(Context context) {
        int identifier = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    private static void setAndroidNativeLightStatusBar(Activity activity, boolean z2) {
        View decorView = activity.getWindow().getDecorView();
        if (z2) {
            decorView.setSystemUiVisibility(R2.drawable.ddbq);
        } else {
            decorView.setSystemUiVisibility(1280);
        }
    }

    public static void setFitSystemForTheme(Activity activity, boolean z2, String str) {
        setFitSystemForTheme(activity, z2, Color.parseColor(str));
    }

    private static boolean setFlymeLightStatusBar(Activity activity, boolean z2) throws IllegalAccessException, NoSuchFieldException, SecurityException, IllegalArgumentException {
        if (activity != null) {
            try {
                WindowManager.LayoutParams attributes = activity.getWindow().getAttributes();
                Field declaredField = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field declaredField2 = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                declaredField.setAccessible(true);
                declaredField2.setAccessible(true);
                int i2 = declaredField.getInt(null);
                int i3 = declaredField2.getInt(attributes);
                declaredField2.setInt(attributes, z2 ? i3 | i2 : (~i2) & i3);
                activity.getWindow().setAttributes(attributes);
                return true;
            } catch (Exception unused) {
            }
        }
        return false;
    }

    public static void setLightStatusBar(Activity activity, boolean z2) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        int lightStatusBarAvailableRomType = RomUtils.getLightStatusBarAvailableRomType();
        if (lightStatusBarAvailableRomType == 1) {
            setMIUISetStatusBarLightMode(activity, z2);
        } else if (lightStatusBarAvailableRomType == 2) {
            setFlymeLightStatusBar(activity, z2);
        } else {
            if (lightStatusBarAvailableRomType != 3) {
                return;
            }
            setAndroidNativeLightStatusBar(activity, z2);
        }
    }

    public static boolean setMIUISetStatusBarLightMode(Activity activity, boolean z2) throws IllegalAccessException, NoSuchMethodException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Window window = activity.getWindow();
        if (window == null) {
            return false;
        }
        Class<?> cls = window.getClass();
        try {
            Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            int i2 = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
            Class<?> cls3 = Integer.TYPE;
            Method method = cls.getMethod("setExtraFlags", cls3, cls3);
            if (z2) {
                method.invoke(window, Integer.valueOf(i2), Integer.valueOf(i2));
            } else {
                method.invoke(window, 0, Integer.valueOf(i2));
            }
            try {
                if (RomUtils.isMiUIV7OrAbove()) {
                    if (z2) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(R2.drawable.ddbq);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(1280);
                    }
                }
            } catch (Exception unused) {
            }
            return true;
        } catch (Exception unused2) {
            return false;
        }
    }

    public static void compat(Activity activity) {
        compat(activity, -1);
    }

    public static void setFitSystemForTheme(Activity activity, boolean z2, int i2) {
        View childAt;
        if (z2 && (childAt = ((ViewGroup) activity.findViewById(R.id.content)).getChildAt(0)) != null) {
            childAt.setFitsSystemWindows(true);
        }
        Window window = activity.getWindow();
        window.clearFlags(67108864);
        window.getDecorView().setSystemUiVisibility(1280);
        window.addFlags(Integer.MIN_VALUE);
        compat(activity, i2);
    }
}
