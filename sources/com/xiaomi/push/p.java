package com.xiaomi.push;

import android.content.Context;
import android.preference.PreferenceManager;
import java.util.Map;

/* loaded from: classes6.dex */
public abstract class p {
    public static void a(Context context) {
    }

    public static void a(Context context, String str, boolean z2) {
        a(context);
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(str, z2).commit();
    }

    public static void a(Map<String, String> map, String str, String str2) {
        if (map == null || str == null || str2 == null) {
            return;
        }
        map.put(str, str2);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static boolean m681a(Context context, String str, boolean z2) {
        a(context);
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(str, z2);
    }
}
