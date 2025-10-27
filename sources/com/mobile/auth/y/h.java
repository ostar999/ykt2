package com.mobile.auth.y;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

@SuppressLint({"ApplySharedPref"})
/* loaded from: classes4.dex */
public final class h {
    public static String a(Context context, String str) {
        try {
            try {
                return context.getSharedPreferences("cuAuthCacheName", 0).getString(str, "");
            } catch (Exception e2) {
                t.d(e2.getMessage());
                return "";
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void a(Context context, String str, Long l2) {
        try {
            try {
                SharedPreferences.Editor editorEdit = context.getSharedPreferences("cuAuthCacheName", 0).edit();
                editorEdit.putLong(str, l2.longValue());
                editorEdit.commit();
            } catch (Exception e2) {
                t.d(e2.getMessage());
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static boolean a(Context context, String str, String str2) {
        try {
            try {
                SharedPreferences.Editor editorEdit = context.getSharedPreferences("cuAuthCacheName", 0).edit();
                editorEdit.putString(str, str2);
                return editorEdit.commit();
            } catch (Exception e2) {
                t.d(e2.getMessage());
                return false;
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static Long b(Context context, String str) {
        long j2 = 0;
        try {
            try {
                j2 = context.getSharedPreferences("cuAuthCacheName", 0).getLong(str, 0L);
            } catch (Exception e2) {
                t.d(e2.getMessage());
            }
            return Long.valueOf(j2);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }
}
