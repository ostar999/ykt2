package com.nirvana.tools.core;

import android.content.Context;

/* loaded from: classes4.dex */
public class UTSharedPreferencesHelper {
    public static synchronized void clearInfo(Context context, String str, String str2) {
        try {
            context.getSharedPreferences(str, 0).edit().remove(str2).commit();
        } catch (Exception unused) {
        }
    }

    public static boolean contains(Context context, String str, String str2) {
        return context.getSharedPreferences(str, 0).contains(str2);
    }

    public static <T> T get(Context context, String str, String str2, T t2) throws Exception {
        try {
            if (contains(context, str, str2)) {
                String strDecode = EncodeUtil.decode(context.getSharedPreferences(str, 0).getString(str2, ""));
                if (t2 instanceof Integer) {
                    return (T) Integer.valueOf(strDecode);
                }
                if (t2 instanceof Boolean) {
                    return (T) Boolean.valueOf(strDecode);
                }
                if (t2 instanceof Long) {
                    return (T) Long.valueOf(strDecode);
                }
                if (t2 instanceof String) {
                    return (T) String.valueOf(strDecode);
                }
                throw new Exception("unsupported type");
            }
        } catch (Exception unused) {
        }
        return t2;
    }

    public static <T> void put(Context context, String str, String str2, T t2) {
        try {
            context.getSharedPreferences(str, 0).edit().putString(str2, EncodeUtil.encode(t2.toString())).commit();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
