package com.heytap.mcssdk.utils;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f7211a = "file";

    /* renamed from: b, reason: collision with root package name */
    private static final String f7212b = "ro.crypto.type";

    private static String a(String str) {
        try {
            return (String) Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke(null, str);
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean a() {
        return "file".equals(a(f7212b));
    }
}
