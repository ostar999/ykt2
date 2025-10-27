package com.beizi.fusion.g;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.Set;

/* loaded from: classes2.dex */
public class aq {
    public static void a(Context context, String str, Object obj) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("fusion_config", 0);
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

    public static Object b(Context context, String str, Object obj) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("fusion_config", 0);
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

    public static String a(Context context, String str) {
        SharedPreferences sharedPreferences;
        if (context == null || (sharedPreferences = context.getSharedPreferences("beizisdk_config", 0)) == null) {
            return null;
        }
        return sharedPreferences.getString(str, "");
    }

    public static void a(Context context, String str, String str2) {
        if (context != null) {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("beizisdk_config", 0).edit();
            editorEdit.putString(str, str2);
            editorEdit.commit();
        }
    }
}
