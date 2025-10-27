package com.aliyun.svideo.common.utils;

import android.content.Context;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import com.easefun.polyv.livecommon.module.utils.PLVNotchUtils;
import com.psychiatrygarden.utils.Constants;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes2.dex */
public class NotchScreenUtil {
    private static final int FLAG_NOTCH_SUPPORT_HW = 65536;
    private static final int FLAG_NOTCH_SUPPORT_VIVO = 32;
    private static final String TAG = "com.aliyun.svideo.common.utils.NotchScreenUtil";

    private static boolean checkHuaWei(Context context) throws ClassNotFoundException {
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
            return ((Boolean) clsLoadClass.getMethod("hasNotchInScreen", new Class[0]).invoke(clsLoadClass, new Object[0])).booleanValue();
        } catch (ClassNotFoundException unused) {
            Log.e(TAG, "hasNotchInScreen ClassNotFoundException");
            return false;
        } catch (NoSuchMethodException unused2) {
            Log.e(TAG, "hasNotchInScreen NoSuchMethodException");
            return false;
        } catch (Exception unused3) {
            Log.e(TAG, "hasNotchInScreen Exception");
            return false;
        }
    }

    private static boolean checkMiUI(Context context) throws ClassNotFoundException {
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("android.os.SystemProperties");
            return ((Integer) clsLoadClass.getMethod("getInt", String.class, Integer.TYPE).invoke(clsLoadClass, "ro.miui.notch", 0)).intValue() == 1;
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
            return false;
        } catch (IllegalAccessException e3) {
            e3.printStackTrace();
            return false;
        } catch (IllegalArgumentException e4) {
            e4.printStackTrace();
            return false;
        } catch (NoSuchMethodException e5) {
            e5.printStackTrace();
            return false;
        } catch (InvocationTargetException e6) {
            e6.printStackTrace();
            return false;
        }
    }

    public static boolean checkNotchScreen(Context context) {
        return checkHuaWei(context) || checkVivo(context) || checkMiUI(context) || checkOppo(context);
    }

    private static boolean checkOppo(Context context) {
        try {
            return context.getPackageManager().hasSystemFeature(PLVNotchUtils.COM_OPPO_FEATURE_SCREEN_HETEROMORPHISM);
        } catch (Exception unused) {
            Log.e(TAG, "checkOppo notchScreen exception");
            return false;
        }
    }

    private static boolean checkVivo(Context context) throws ClassNotFoundException {
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("android.util.FtFeature");
            return ((Boolean) clsLoadClass.getMethod("isFeatureSupport", new Class[0]).invoke(clsLoadClass, 32)).booleanValue();
        } catch (Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public static int[] getNotchSize(Context context) throws ClassNotFoundException {
        int[] iArr = {0, 0};
        try {
            Class<?> clsLoadClass = context.getClassLoader().loadClass("com.huawei.android.util.HwNotchSizeUtil");
            return (int[]) clsLoadClass.getMethod("getNotchSize", new Class[0]).invoke(clsLoadClass, new Object[0]);
        } catch (ClassNotFoundException unused) {
            Log.e(Constants.ANSWER_MODE.TEST_MODE, "getNotchSize ClassNotFoundException");
            return iArr;
        } catch (NoSuchMethodException unused2) {
            Log.e(Constants.ANSWER_MODE.TEST_MODE, "getNotchSize NoSuchMethodException");
            return iArr;
        } catch (Exception unused3) {
            Log.e(Constants.ANSWER_MODE.TEST_MODE, "getNotchSize Exception");
            return iArr;
        }
    }

    public static void setFullScreenWindowLayoutInDisplayCutout(Window window) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        try {
            Class<?> cls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            cls.getMethod("addHwFlags", Integer.TYPE).invoke(cls.getConstructor(WindowManager.LayoutParams.class).newInstance(attributes), 65536);
        } catch (ClassNotFoundException unused) {
            Log.e(TAG, "hw add notch screen flag api error");
        } catch (IllegalAccessException unused2) {
            Log.e(TAG, "hw add notch screen flag api error");
        } catch (InstantiationException unused3) {
            Log.e(TAG, "hw add notch screen flag api error");
        } catch (NoSuchMethodException unused4) {
            Log.e(TAG, "hw add notch screen flag api error");
        } catch (InvocationTargetException unused5) {
            Log.e(TAG, "hw add notch screen flag api error");
        } catch (Exception unused6) {
            Log.e(TAG, "other Exception");
        }
    }

    public static void setNotFullScreenWindowLayoutInDisplayCutout(Window window) throws IllegalAccessException, InstantiationException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (window == null) {
            return;
        }
        WindowManager.LayoutParams attributes = window.getAttributes();
        try {
            Class<?> cls = Class.forName("com.huawei.android.view.LayoutParamsEx");
            cls.getMethod("clearHwFlags", Integer.TYPE).invoke(cls.getConstructor(WindowManager.LayoutParams.class).newInstance(attributes), 65536);
            Log.e(TAG, "............clear");
        } catch (ClassNotFoundException unused) {
            Log.e(TAG, "hw clear notch screen flag api error");
        } catch (IllegalAccessException unused2) {
            Log.e(TAG, "hw clear notch screen flag api error");
        } catch (InstantiationException unused3) {
            Log.e(TAG, "hw clear notch screen flag api error");
        } catch (NoSuchMethodException unused4) {
            Log.e(TAG, "hw clear notch screen flag api error");
        } catch (InvocationTargetException unused5) {
            Log.e(TAG, "hw clear notch screen flag api error");
        } catch (Exception unused6) {
            Log.e(TAG, "other Exception");
        }
    }
}
