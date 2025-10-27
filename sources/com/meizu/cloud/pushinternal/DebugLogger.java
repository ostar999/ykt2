package com.meizu.cloud.pushinternal;

import android.content.Context;
import android.os.Environment;
import com.meizu.cloud.pushsdk.base.h;

/* loaded from: classes4.dex */
public class DebugLogger {
    public static boolean debug = false;

    public static void d(String str, String str2) {
        h.b().a(str, str2);
    }

    public static void e(String str, String str2) {
        h.b().d(str, str2);
    }

    public static void flush() {
        h.b().a(false);
    }

    public static void i(String str, String str2) {
        h.b().b(str, str2);
    }

    public static void initDebugLogger(Context context) {
        h.b().a(context);
        h.b().a(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/pushSdk/" + context.getPackageName());
    }

    public static boolean isDebuggable() {
        return h.b().a();
    }

    public static void switchDebug(boolean z2) {
        h.b().b(z2);
    }

    public static void w(String str, String str2) {
        h.b().c(str, str2);
    }
}
