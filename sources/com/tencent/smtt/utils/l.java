package com.tencent.smtt.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.tencent.connect.common.Constants;
import com.tencent.smtt.sdk.TbsConfig;
import com.tencent.smtt.sdk.WebView;

/* loaded from: classes6.dex */
public class l {

    /* renamed from: a, reason: collision with root package name */
    private static String f21560a = null;

    /* renamed from: b, reason: collision with root package name */
    private static String f21561b = "GA";

    /* renamed from: c, reason: collision with root package name */
    private static String f21562c = "GE";

    /* renamed from: d, reason: collision with root package name */
    private static String f21563d = "9422";

    /* renamed from: e, reason: collision with root package name */
    private static String f21564e = "0";

    /* renamed from: f, reason: collision with root package name */
    private static String f21565f = "";

    /* renamed from: g, reason: collision with root package name */
    private static boolean f21566g = false;

    /* renamed from: h, reason: collision with root package name */
    private static boolean f21567h = false;

    /* renamed from: i, reason: collision with root package name */
    private static boolean f21568i = false;

    public static String a(Context context) {
        return a(context, "0");
    }

    public static String a(Context context, String str) throws PackageManager.NameNotFoundException {
        if (!TextUtils.isEmpty(f21560a)) {
            return f21560a;
        }
        String strA = a(context, String.valueOf(WebView.getTbsSDKVersion(context)), str, f21561b, f21562c, f21563d, f21564e, f21565f, f21566g);
        f21560a = strA;
        return strA;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00fc  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.lang.String a(android.content.Context r7, java.lang.String r8, java.lang.String r9, java.lang.String r10, java.lang.String r11, java.lang.String r12, java.lang.String r13, java.lang.String r14, boolean r15) throws android.content.pm.PackageManager.NameNotFoundException {
        /*
            Method dump skipped, instructions count: 284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.l.a(android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean):java.lang.String");
    }

    private static String a(String str) {
        return "com.tencent.mm".equals(str) ? "WX" : "com.tencent.mobileqq".equals(str) ? Constants.SOURCE_QQ : "com.qzone".equals(str) ? "QZ" : TbsConfig.APP_QB.equals(str) ? "QB" : "TRD";
    }

    private static void a(StringBuilder sb, String str, String str2) {
        sb.append("&");
        sb.append(str);
        sb.append("=");
        sb.append(str2);
    }

    private static int b(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (defaultDisplay != null) {
            return defaultDisplay.getWidth();
        }
        return -1;
    }

    private static int c(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        if (defaultDisplay != null) {
            return defaultDisplay.getHeight();
        }
        return -1;
    }

    private static boolean d(Context context) {
        if (f21567h) {
            return f21568i;
        }
        try {
            boolean z2 = (Math.min(b(context), c(context)) * 160) / e(context) >= 700;
            f21568i = z2;
            f21567h = true;
            return z2;
        } catch (Throwable unused) {
            return false;
        }
    }

    private static int e(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService("window");
        DisplayMetrics displayMetrics = new DisplayMetrics();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        if (defaultDisplay == null) {
            return 160;
        }
        defaultDisplay.getMetrics(displayMetrics);
        return displayMetrics.densityDpi;
    }
}
