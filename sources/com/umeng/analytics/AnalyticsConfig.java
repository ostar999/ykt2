package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.pro.j;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.utils.UMUtils;
import java.util.Map;

/* loaded from: classes6.dex */
public class AnalyticsConfig {
    public static boolean CATCH_EXCEPTION = false;
    public static boolean CHANGE_CATCH_EXCEPTION_NOTALLOW = true;
    public static boolean CLEAR_EKV_BL = false;
    public static boolean CLEAR_EKV_WL = false;
    public static final String DEBUG_KEY = "debugkey";
    public static final String DEBUG_MODE_PERIOD = "sendaging";
    public static String GPU_RENDERER = "";
    public static String GPU_VENDER = "";
    public static final String RTD_PERIOD = "period";
    public static final String RTD_SP_FILE = "um_rtd_conf";
    public static final String RTD_START_TIME = "startTime";

    /* renamed from: a, reason: collision with root package name */
    static double[] f22340a = null;

    /* renamed from: b, reason: collision with root package name */
    private static String f22341b = null;

    /* renamed from: c, reason: collision with root package name */
    private static String f22342c = null;

    /* renamed from: d, reason: collision with root package name */
    private static String f22343d = null;

    /* renamed from: e, reason: collision with root package name */
    private static int f22344e = 0;
    public static boolean enable = true;

    /* renamed from: f, reason: collision with root package name */
    private static Object f22345f = new Object();

    /* renamed from: g, reason: collision with root package name */
    private static boolean f22346g = false;

    /* renamed from: h, reason: collision with root package name */
    private static String f22347h = "";
    public static long kContinueSessionMillis = 30000;
    public static String mWrapperType;
    public static String mWrapperVersion;

    public static void a(String str) {
        f22342c = str;
    }

    public static String getAppkey(Context context) {
        return UMUtils.getAppkey(context);
    }

    public static String getChannel(Context context) {
        return UMUtils.getChannel(context);
    }

    public static String getGameSdkVersion(Context context) {
        try {
            Class<?> cls = Class.forName("com.umeng.analytics.game.GameSdkVersion");
            return (String) cls.getDeclaredField("SDK_VERSION").get(cls);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static double[] getLocation() {
        return f22340a;
    }

    public static String getRealTimeDebugKey() {
        String str;
        synchronized (f22345f) {
            str = f22347h;
        }
        return str;
    }

    public static String getSecretKey(Context context) {
        if (TextUtils.isEmpty(f22343d)) {
            f22343d = com.umeng.common.b.a(context).c();
        }
        return f22343d;
    }

    public static int getVerticalType(Context context) {
        if (f22344e == 0) {
            f22344e = com.umeng.common.b.a(context).d();
        }
        return f22344e;
    }

    public static boolean isRealTimeDebugMode() {
        boolean z2;
        synchronized (f22345f) {
            z2 = f22346g;
        }
        return z2;
    }

    public static void turnOffRealTimeDebug() {
        synchronized (f22345f) {
            f22346g = false;
            f22347h = "";
        }
    }

    public static void turnOnRealTimeDebug(Map<String, String> map) {
        synchronized (f22345f) {
            f22346g = true;
            if (map != null && map.containsKey(DEBUG_KEY)) {
                f22347h = map.get(DEBUG_KEY);
            }
        }
    }

    public static void a(Context context, String str) {
        if (TextUtils.isEmpty(str)) {
            UMLog.aq(j.A, 0, "\\|");
        } else {
            f22343d = str;
            com.umeng.common.b.a(context).a(f22343d);
        }
    }

    public static void a(Context context, int i2) {
        f22344e = i2;
        com.umeng.common.b.a(context).a(f22344e);
    }
}
