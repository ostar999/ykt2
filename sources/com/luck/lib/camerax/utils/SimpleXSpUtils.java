package com.luck.lib.camerax.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.luck.lib.camerax.CustomCameraConfig;

/* loaded from: classes4.dex */
public class SimpleXSpUtils {
    private static SharedPreferences pictureSpUtils;

    public static boolean getBoolean(Context context, String str, boolean z2) {
        return getSp(context).getBoolean(str, z2);
    }

    private static SharedPreferences getSp(Context context) {
        if (pictureSpUtils == null) {
            pictureSpUtils = context.getSharedPreferences(CustomCameraConfig.SP_NAME, 0);
        }
        return pictureSpUtils;
    }

    public static void putBoolean(Context context, String str, boolean z2) {
        getSp(context).edit().putBoolean(str, z2).apply();
    }
}
