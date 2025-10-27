package com.meizu.cloud.pushsdk.util;

import android.content.Context;
import android.content.SharedPreferences;
import cn.hutool.core.text.StrPool;
import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;

/* loaded from: classes4.dex */
public class b {
    public static String a(Context context, String str) {
        return a(context, PushConstants.PUSH_ID_PREFERENCE_NAME, str + StrPool.UNDERLINE + PushConstants.KEY_PUSH_ID);
    }

    public static String a(Context context, String str, String str2) {
        return k(context, str).getString(str2, "");
    }

    public static void a(Context context, int i2, String str) {
        a(context, PushConstants.PUSH_ID_PREFERENCE_NAME, str + StrPool.UNDERLINE + PushConstants.KEY_PUSH_ID_EXPIRE_TIME, i2);
    }

    public static void a(Context context, String str, int i2) {
        a(context, "mz_push_preference", str + ".notification_id", i2);
    }

    public static void a(Context context, String str, String str2, int i2) {
        k(context, str).edit().putInt(str2, i2).apply();
    }

    public static void a(Context context, String str, String str2, String str3) {
        k(context, str).edit().putString(str2, str3).apply();
    }

    public static void a(Context context, String str, String str2, boolean z2) {
        k(context, str).edit().putBoolean(str2, z2).apply();
    }

    public static void a(Context context, String str, boolean z2) {
        a(context, "mz_push_preference", "switch_notification_message_" + str, z2);
    }

    public static int b(Context context, String str) {
        return b(context, PushConstants.PUSH_ID_PREFERENCE_NAME, str + StrPool.UNDERLINE + PushConstants.KEY_PUSH_ID_EXPIRE_TIME);
    }

    public static int b(Context context, String str, String str2) {
        return k(context, str).getInt(str2, 0);
    }

    public static void b(Context context, String str, int i2) {
        a(context, "mz_push_preference", str + ".notification_push_task_id", i2);
    }

    public static void b(Context context, String str, String str2, int i2) {
        a(context, "mz_push_preference", str + StrPool.DOT + str2, i2);
    }

    public static void b(Context context, String str, boolean z2) {
        a(context, "mz_push_preference", "switch_through_message_" + str, z2);
    }

    public static int c(Context context, String str) {
        return k(context, "mz_push_preference").getInt(str + ".notification_id", 0);
    }

    public static void c(Context context, String str, int i2) {
        a(context, "mz_push_preference", str + ".message_seq", i2);
    }

    public static boolean c(Context context, String str, String str2) {
        return k(context, str).getBoolean(str2, true);
    }

    public static int d(Context context, String str) {
        return k(context, "mz_push_preference").getInt(str + ".notification_push_task_id", 0);
    }

    public static boolean d(Context context, String str, String str2) {
        return k(context, str).edit().remove(str2).commit();
    }

    public static boolean e(Context context, String str) {
        return c(context, "mz_push_preference", "switch_notification_message_" + str);
    }

    public static boolean e(Context context, String str, String str2) {
        return k(context, str).contains(str2);
    }

    public static void f(Context context, String str, String str2) {
        a(context, PushConstants.PUSH_ID_PREFERENCE_NAME, str2 + StrPool.UNDERLINE + PushConstants.KEY_PUSH_ID, str);
    }

    public static boolean f(Context context, String str) {
        return e(context, "mz_push_preference", "switch_notification_message_" + str);
    }

    public static String g(Context context, String str) {
        return a(context, "mz_push_preference", "push_alias_" + str);
    }

    public static void g(Context context, String str, String str2) {
        a(context, "mz_push_preference", "push_alias_" + str, str2);
    }

    public static int h(Context context, String str, String str2) {
        return b(context, "mz_push_preference", str + StrPool.DOT + str2);
    }

    public static boolean h(Context context, String str) {
        return c(context, "mz_push_preference", "switch_through_message_" + str);
    }

    public static boolean i(Context context, String str) {
        return e(context, "mz_push_preference", "switch_through_message_" + str);
    }

    public static boolean i(Context context, String str, String str2) {
        return d(context, "mz_push_preference", str + StrPool.DOT + str2);
    }

    public static int j(Context context, String str) {
        int iB = b(context, "mz_push_preference", str + ".message_seq") + 1;
        c(context, str, iB);
        DebugLogger.e("mz_push_preference", "current messageSeq " + iB);
        return iB;
    }

    private static SharedPreferences k(Context context, String str) {
        return context.getSharedPreferences(str, 0);
    }
}
