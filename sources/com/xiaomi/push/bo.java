package com.xiaomi.push;

/* loaded from: classes6.dex */
public class bo {
    private static Class a() {
        return Class.forName("android.os.SystemProperties");
    }

    public static String a(String str, String str2) {
        try {
            return (String) a().getMethod("get", String.class, String.class).invoke(null, str, str2);
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }
}
