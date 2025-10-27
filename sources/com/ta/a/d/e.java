package com.ta.a.d;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import android.text.TextUtils;
import java.io.File;

/* loaded from: classes6.dex */
public class e {

    /* renamed from: c, reason: collision with root package name */
    private static final String f17212c = ".UTSystemConfig" + File.separator + "Global";

    public static void a(String str) {
        try {
            com.ta.a.e.h.f();
            com.ta.a.e.c.b(b(), str);
        } catch (Throwable unused) {
        }
    }

    private static String b() {
        String str = a(com.ta.a.a.a().getContext()) + File.separator + "4635b664f789000d";
        com.ta.a.e.h.b("", str);
        return str;
    }

    public static String c() {
        return a(com.ta.a.a.a().getContext()) + File.separator + "9983c160aa044115";
    }

    public static String d() {
        return a(com.ta.a.a.a().getContext()) + File.separator + "a325712a39bd320a";
    }

    public static String e() {
        try {
            return com.ta.a.e.c.c(b());
        } catch (Exception unused) {
            return null;
        }
    }

    private static String f() {
        String str = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + f17212c;
        com.ta.a.e.h.b("", "SdcardRoot dir:" + str);
        com.ta.a.e.c.m107c(str);
        return str;
    }

    public static String g() {
        try {
            String strH = h();
            if (TextUtils.isEmpty(strH)) {
                return null;
            }
            return com.ta.a.e.c.c(strH);
        } catch (Exception unused) {
            return null;
        }
    }

    private static String h() {
        if (!com.ta.a.b.d.a(com.ta.a.a.a().getContext())) {
            return null;
        }
        return f() + File.separator + "7934039a7252be16";
    }

    private static String a(Context context) {
        String str = context.getFilesDir().getAbsolutePath() + File.separator + ".7934039a7252be16";
        com.ta.a.e.h.b("", "UtdidAppRoot dir:" + str);
        com.ta.a.e.c.m107c(str);
        return str;
    }

    public static boolean c(Context context) {
        try {
            return !context.getFileStreamPath("3c9b584e65e6c983").exists();
        } catch (Exception unused) {
            return true;
        }
    }

    public static String b(Context context) {
        try {
            return Settings.System.getString(context.getContentResolver(), "7934039a7252be16");
        } catch (Exception unused) {
            return null;
        }
    }

    public static void b(String str) {
        try {
            String strH = h();
            if (TextUtils.isEmpty(strH)) {
                return;
            }
            com.ta.a.e.c.b(strH, str);
        } catch (Exception unused) {
        }
    }

    public static void a(Context context, String str) {
        String string;
        try {
            string = Settings.System.getString(context.getContentResolver(), "7934039a7252be16");
        } catch (Exception unused) {
            string = null;
        }
        if (str.equals(string)) {
            return;
        }
        try {
            Settings.System.putString(context.getContentResolver(), "7934039a7252be16", str);
        } catch (Exception unused2) {
        }
    }
}
