package com.mobile.auth.l;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import java.util.Map;

@SuppressLint({"ApplySharedPref"})
/* loaded from: classes4.dex */
public class k {

    /* renamed from: a, reason: collision with root package name */
    @SuppressLint({"StaticFieldLeak"})
    private static Context f10431a;

    public static class a {

        /* renamed from: a, reason: collision with root package name */
        private final SharedPreferences.Editor f10432a;

        public a(SharedPreferences.Editor editor) {
            this.f10432a = editor;
        }

        public void a() {
            this.f10432a.apply();
        }

        public void a(String str) {
            this.f10432a.remove(d.a(str));
        }

        public void a(String str, int i2) {
            this.f10432a.putInt(d.a(str), i2);
        }

        public void a(String str, long j2) {
            this.f10432a.putLong(d.a(str), j2);
        }

        public void a(String str, String str2) {
            this.f10432a.putString(d.a(str), str2);
        }

        public void b() {
            this.f10432a.commit();
        }

        public void c() {
            this.f10432a.clear();
        }
    }

    public static int a(String str, int i2) {
        return f10431a.getSharedPreferences("ssoconfigs", 0).getInt(d.a(str), i2);
    }

    public static int a(String str, String str2, int i2) {
        return f10431a.getSharedPreferences(str, 0).getInt(d.a(str2), i2);
    }

    public static long a(String str, long j2) {
        return f10431a.getSharedPreferences("ssoconfigs", 0).getLong(d.a(str), j2);
    }

    public static long a(String str, String str2, long j2) {
        return f10431a.getSharedPreferences(str, 0).getLong(d.a(str2), j2);
    }

    public static a a() {
        return new a(f10431a.getSharedPreferences("ssoconfigs", 0).edit());
    }

    public static String a(String str, String str2, String str3) {
        return f10431a.getSharedPreferences(str, 0).getString(d.a(str2), str3);
    }

    public static void a(Context context) {
        f10431a = context.getApplicationContext();
    }

    public static void a(String str) {
        SharedPreferences sharedPreferences = f10431a.getSharedPreferences("ssoconfigs", 0);
        sharedPreferences.edit().remove(d.a(str)).commit();
    }

    public static void a(String str, String str2) {
        SharedPreferences sharedPreferences = f10431a.getSharedPreferences("ssoconfigs", 0);
        sharedPreferences.edit().putString(d.a(str), str2).commit();
    }

    public static void a(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return;
        }
        SharedPreferences.Editor editorEdit = f10431a.getSharedPreferences("ssoconfigs", 0).edit();
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            String strA = d.a(str);
            if (obj instanceof String) {
                editorEdit.putString(strA, (String) obj);
            } else if (obj instanceof Integer) {
                editorEdit.putInt(strA, ((Integer) obj).intValue());
            } else if (obj instanceof Long) {
                editorEdit.putLong(strA, ((Long) obj).longValue());
            } else if (obj instanceof Boolean) {
                editorEdit.putBoolean(strA, ((Boolean) obj).booleanValue());
            }
        }
        editorEdit.commit();
    }

    public static a b(String str) {
        return new a(f10431a.getSharedPreferences(str, 0).edit());
    }

    public static String b(String str, String str2) {
        return f10431a.getSharedPreferences("ssoconfigs", 0).getString(d.a(str), str2);
    }
}
