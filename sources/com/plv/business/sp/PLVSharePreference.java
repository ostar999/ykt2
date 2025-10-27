package com.plv.business.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.plv.thirdpart.blankj.utilcode.util.Utils;

/* loaded from: classes4.dex */
public class PLVSharePreference {
    protected static String sPrefName;

    public static SharedPreferences getSharedPreferences() {
        return TextUtils.isEmpty(sPrefName) ? PreferenceManager.getDefaultSharedPreferences(Utils.getApp()) : Utils.getApp().getSharedPreferences(sPrefName, 0);
    }

    public static boolean putBoolean(Context context, String str, boolean z2) {
        return getSharedPreferences().edit().putBoolean(str, z2).commit();
    }

    public static boolean putFloat(String str, float f2) {
        return getSharedPreferences().edit().putFloat(str, f2).commit();
    }

    public static boolean putInt(String str, int i2) {
        return getSharedPreferences().edit().putInt(str, i2).commit();
    }

    public static boolean putLong(String str, long j2) {
        return getSharedPreferences().edit().putLong(str, j2).commit();
    }

    public static boolean putString(String str, String str2) {
        return getSharedPreferences().edit().putString(str, str2).commit();
    }

    public static void setPrefFileName(String str) {
        sPrefName = str;
    }
}
