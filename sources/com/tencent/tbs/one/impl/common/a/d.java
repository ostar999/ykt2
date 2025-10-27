package com.tencent.tbs.one.impl.common.a;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes6.dex */
public final class d {
    public static ArrayList<String> a(Context context) {
        try {
            return new ArrayList<>(Arrays.asList(context.getSharedPreferences("com.tencent.tbs.one.report", 4).getString("stat_not_yet_sent", "").split(",")));
        } catch (Throwable unused) {
            b(context);
            return new ArrayList<>();
        }
    }

    public static void b(Context context) {
        try {
            SharedPreferences.Editor editorEdit = context.getSharedPreferences("com.tencent.tbs.one.report", 4).edit();
            editorEdit.clear();
            editorEdit.apply();
        } catch (Throwable unused) {
        }
    }

    public static boolean c(Context context) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("com.tencent.tbs.one.report", 4);
            long j2 = sharedPreferences.getLong("last_send_time", -1L);
            if (j2 >= 0) {
                return System.currentTimeMillis() - j2 > 86400000;
            }
            sharedPreferences.edit().putLong("last_send_time", System.currentTimeMillis()).apply();
            return false;
        } catch (Throwable unused) {
            b(context);
            return false;
        }
    }
}
