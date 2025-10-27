package com.umeng.commonsdk.statistics.internal;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.umeng.analytics.pro.am;
import com.umeng.commonsdk.internal.crash.UMCrashManager;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.utils.UMUtils;

/* loaded from: classes6.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static Context f23388a;

    /* renamed from: b, reason: collision with root package name */
    private String f23389b;

    /* renamed from: c, reason: collision with root package name */
    private String f23390c;

    /* renamed from: com.umeng.commonsdk.statistics.internal.a$a, reason: collision with other inner class name */
    public static class C0385a {

        /* renamed from: a, reason: collision with root package name */
        private static final a f23391a = new a();

        private C0385a() {
        }
    }

    public static a a(Context context) {
        if (f23388a == null && context != null) {
            f23388a = context.getApplicationContext();
        }
        return C0385a.f23391a;
    }

    private void e(String str) {
        try {
            this.f23389b = str.replaceAll("&=", " ").replaceAll("&&", " ").replaceAll("==", "/") + "/Android/" + Build.DISPLAY + "/" + Build.MODEL + "/" + Build.VERSION.RELEASE + " " + HelperUtils.getUmengMD5(UMUtils.getAppkey(f23388a));
        } catch (Throwable th) {
            UMCrashManager.reportCrash(f23388a, th);
        }
    }

    private void f(String str) {
        try {
            String str2 = str.split("&&")[0];
            if (TextUtils.isEmpty(str2)) {
                return;
            }
            String[] strArrSplit = str2.split("&=");
            StringBuilder sb = new StringBuilder();
            sb.append(am.aP);
            for (String str3 : strArrSplit) {
                if (!TextUtils.isEmpty(str3)) {
                    String strSubstring = str3.substring(0, 2);
                    if (strSubstring.endsWith("=")) {
                        strSubstring = strSubstring.replace("=", "");
                    }
                    sb.append(strSubstring);
                }
            }
            this.f23390c = sb.toString();
        } catch (Throwable th) {
            UMCrashManager.reportCrash(f23388a, th);
        }
    }

    public boolean b(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("t");
    }

    public boolean c(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith("z");
    }

    public void d(String str) {
        String strSubstring = str.substring(0, str.indexOf(95));
        f(strSubstring);
        e(strSubstring);
    }

    private a() {
        this.f23389b = null;
        this.f23390c = null;
    }

    public String b() {
        return this.f23389b;
    }

    public boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return str.startsWith(am.av);
    }

    public String a() {
        return this.f23390c;
    }
}
