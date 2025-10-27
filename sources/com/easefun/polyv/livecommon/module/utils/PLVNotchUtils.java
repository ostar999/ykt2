package com.easefun.polyv.livecommon.module.utils;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes3.dex */
public class PLVNotchUtils {
    private static final String ANDROID_OS_SYSTEM_PROPERTIES = "android.os.SystemProperties";
    private static final String ANDROID_UTIL_FT_FEATURE = "android.util.FtFeature";
    private static final String COM_HUAWEI_ANDROID_UTIL_HW_NOTCH_SIZE_UTIL = "com.huawei.android.util.HwNotchSizeUtil";
    public static final String COM_OPPO_FEATURE_SCREEN_HETEROMORPHISM = "com.oppo.feature.screen.heteromorphism";
    private static final String FLYME_CONFIG_FLYME_FEATURE = "flyme.config.FlymeFeature";
    private static final String GET_INT = "getInt";
    private static final String HAS_NOTCH_HW = "hasNotchHw:";
    private static final String HAS_NOTCH_IN_SCREEN = "hasNotchInScreen";
    private static final String HAS_NOTCH_MEIZU = "hasNotchMeizu:";
    private static final String HAS_NOTCH_VIVO = "hasNotchVIVO:";
    private static final String HAS_NOTCH_XIAO_MI = "hasNotchXiaoMi:";
    private static final String HUAWEI = "HUAWEI";
    private static final String IS_FEATURE_SUPPORT = "isFeatureSupport";
    private static final String IS_FRINGE_DEVICE = "IS_FRINGE_DEVICE";
    private static final String MEIZU = "meizu";
    private static final String OPPO = "oppo";
    private static final String RO_MIUI_NOTCH = "ro.miui.notch";
    private static final String TAG = "PLVNotchUtils";
    private static final String VIVO = "vivo";
    private static final String XIAOMI = "xiaomi";

    private static boolean hasNotchHw(Activity activity) throws ClassNotFoundException {
        try {
            Class<?> clsLoadClass = activity.getClassLoader().loadClass(COM_HUAWEI_ANDROID_UTIL_HW_NOTCH_SIZE_UTIL);
            return ((Boolean) clsLoadClass.getMethod(HAS_NOTCH_IN_SCREEN, new Class[0]).invoke(clsLoadClass, new Object[0])).booleanValue();
        } catch (Exception e2) {
            PLVCommonLog.d(TAG, HAS_NOTCH_HW + e2.getMessage());
            return false;
        }
    }

    public static boolean hasNotchInScreen(Activity activity) {
        String str = Build.MANUFACTURER;
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.equalsIgnoreCase(HUAWEI)) {
            return hasNotchHw(activity);
        }
        if (str.equalsIgnoreCase(XIAOMI)) {
            return hasNotchXiaoMi(activity);
        }
        if (str.equalsIgnoreCase(OPPO)) {
            return hasNotchOPPO(activity);
        }
        if (str.equalsIgnoreCase(VIVO)) {
            return hasNotchVIVO(activity);
        }
        if (str.equalsIgnoreCase(MEIZU)) {
            return hasNotchMeizu(activity);
        }
        return false;
    }

    private static boolean hasNotchMeizu(Activity activity) {
        try {
            return ((Boolean) Class.forName(FLYME_CONFIG_FLYME_FEATURE).getDeclaredField(IS_FRINGE_DEVICE).get(null)).booleanValue();
        } catch (ClassNotFoundException e2) {
            PLVCommonLog.d(TAG, HAS_NOTCH_MEIZU + e2.getMessage());
            return false;
        } catch (IllegalAccessException e3) {
            PLVCommonLog.d(TAG, HAS_NOTCH_MEIZU + e3.getMessage());
            return false;
        } catch (NoSuchFieldException e4) {
            PLVCommonLog.d(TAG, HAS_NOTCH_MEIZU + e4.getMessage());
            return false;
        }
    }

    private static boolean hasNotchOPPO(Activity activity) {
        return activity.getPackageManager().hasSystemFeature(COM_OPPO_FEATURE_SCREEN_HETEROMORPHISM);
    }

    private static boolean hasNotchVIVO(Activity activity) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName(ANDROID_UTIL_FT_FEATURE);
            return ((Boolean) cls.getMethod(IS_FEATURE_SUPPORT, Integer.TYPE).invoke(cls, 32)).booleanValue();
        } catch (ClassNotFoundException e2) {
            PLVCommonLog.d(TAG, HAS_NOTCH_VIVO + e2.getMessage());
            return false;
        } catch (IllegalAccessException e3) {
            PLVCommonLog.d(TAG, HAS_NOTCH_VIVO + e3.getMessage());
            return false;
        } catch (NoSuchMethodException e4) {
            PLVCommonLog.d(TAG, HAS_NOTCH_VIVO + e4.getMessage());
            return false;
        } catch (InvocationTargetException e5) {
            PLVCommonLog.d(TAG, HAS_NOTCH_VIVO + e5.getMessage());
            return false;
        }
    }

    private static boolean hasNotchXiaoMi(Activity activity) throws ClassNotFoundException {
        try {
            Class<?> cls = Class.forName(ANDROID_OS_SYSTEM_PROPERTIES);
            return ((Integer) cls.getMethod(GET_INT, String.class, Integer.TYPE).invoke(cls, RO_MIUI_NOTCH, 0)).intValue() == 1;
        } catch (ClassNotFoundException e2) {
            PLVCommonLog.d(TAG, HAS_NOTCH_XIAO_MI + e2.getMessage());
            return false;
        } catch (IllegalAccessException e3) {
            PLVCommonLog.d(TAG, HAS_NOTCH_XIAO_MI + e3.getMessage());
            return false;
        } catch (NoSuchMethodException e4) {
            PLVCommonLog.d(TAG, HAS_NOTCH_XIAO_MI + e4.getMessage());
            return false;
        } catch (InvocationTargetException e5) {
            PLVCommonLog.d(TAG, HAS_NOTCH_XIAO_MI + e5.getMessage());
            return false;
        }
    }
}
