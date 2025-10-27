package com.beizi.ad.internal.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;
import java.util.Set;

/* loaded from: classes2.dex */
public class SPUtils {
    private static final String FILE_NAME = "beizisdk_config";
    private static final String FILE_NAME_FUSION_SDK = "fusion_config";

    public static void clear(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.clear();
            editorEdit.apply();
        }
    }

    public static boolean contains(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, 0);
        return sharedPreferences != null && sharedPreferences.contains(str);
    }

    public static Object get(Context context, String str, Object obj) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, 0);
        if (sharedPreferences == null) {
            return null;
        }
        if (obj instanceof Boolean) {
            return Boolean.valueOf(sharedPreferences.getBoolean(str, ((Boolean) obj).booleanValue()));
        }
        if (obj instanceof Float) {
            return Float.valueOf(sharedPreferences.getFloat(str, ((Float) obj).floatValue()));
        }
        if (obj instanceof Integer) {
            return Integer.valueOf(sharedPreferences.getInt(str, ((Integer) obj).intValue()));
        }
        if (obj instanceof Long) {
            return Long.valueOf(sharedPreferences.getLong(str, ((Long) obj).longValue()));
        }
        if (obj instanceof String) {
            return sharedPreferences.getString(str, (String) obj);
        }
        if (obj instanceof Set) {
            return sharedPreferences.getStringSet(str, (Set) obj);
        }
        return null;
    }

    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, 0);
        if (sharedPreferences != null) {
            return sharedPreferences.getAll();
        }
        return null;
    }

    public static int getInt(Context context, String str, int i2) {
        return context.getSharedPreferences(FILE_NAME, 0).getInt(str, i2);
    }

    public static String getString(Context context, String str) {
        SharedPreferences sharedPreferences;
        if (context == null || (sharedPreferences = context.getSharedPreferences(FILE_NAME, 0)) == null) {
            return null;
        }
        return sharedPreferences.getString(str, null);
    }

    public static String getStringFromFusionSdk(Context context, String str) {
        SharedPreferences sharedPreferences;
        if (context == null || (sharedPreferences = context.getSharedPreferences(FILE_NAME_FUSION_SDK, 0)) == null) {
            return null;
        }
        return sharedPreferences.getString(str, null);
    }

    public static String getUpdated(Context context, String str) {
        return context.getSharedPreferences(FILE_NAME, 0).getString(str, String.valueOf(System.currentTimeMillis() / 1000));
    }

    public static void put(Context context, String str, Object obj) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            if (obj instanceof Boolean) {
                editorEdit.putBoolean(str, ((Boolean) obj).booleanValue());
            } else if (obj instanceof Float) {
                editorEdit.putFloat(str, ((Float) obj).floatValue());
            } else if (obj instanceof Integer) {
                editorEdit.putInt(str, ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                editorEdit.putLong(str, ((Long) obj).longValue());
            } else if (obj instanceof String) {
                editorEdit.putString(str, (String) obj);
            } else if (obj instanceof Set) {
                editorEdit.remove(str);
                editorEdit.putStringSet(str, (Set) obj);
            }
            editorEdit.apply();
        }
    }

    public static void putInt(Context context, String str, int i2) {
        context.getSharedPreferences(FILE_NAME, 0).edit().putInt(str, i2).commit();
    }

    public static void remove(Context context, String str) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, 0);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            editorEdit.remove(str);
            editorEdit.apply();
        }
    }

    public static void saveUpdated(Context context, String str, String str2) {
        SharedPreferences.Editor editorEdit = context.getSharedPreferences(FILE_NAME, 0).edit();
        editorEdit.putString(str, str2);
        editorEdit.commit();
    }
}
