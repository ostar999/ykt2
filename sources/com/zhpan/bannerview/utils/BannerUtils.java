package com.zhpan.bannerview.utils;

import android.content.res.Resources;
import android.util.Log;

/* loaded from: classes8.dex */
public class BannerUtils {
    private static final String TAG = "BVP";
    private static boolean debugMode = false;

    public static int dp2px(float f2) {
        return (int) ((f2 * Resources.getSystem().getDisplayMetrics().density) + 0.5f);
    }

    public static int getOriginalPosition(int i2) {
        return 500 - (500 % i2);
    }

    public static int getRealPosition(int i2, int i3) {
        if (i3 == 0) {
            return 0;
        }
        return (i2 + i3) % i3;
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static void log(String str, String str2) {
        if (isDebugMode()) {
            Log.e(str, str2);
        }
    }

    public static void setDebugMode(boolean z2) {
        debugMode = z2;
    }

    public static void log(String str) {
        if (isDebugMode()) {
            log(TAG, str);
        }
    }
}
