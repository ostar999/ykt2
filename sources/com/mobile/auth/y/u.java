package com.mobile.auth.y;

import com.just.agentweb.DefaultWebClient;
import com.mobile.auth.gatewayauth.ExceptionProcessor;

/* loaded from: classes4.dex */
public final class u {

    /* renamed from: a, reason: collision with root package name */
    public static String f10640a = "123.125.99.31";

    /* renamed from: b, reason: collision with root package name */
    public static int f10641b = 0;

    /* renamed from: c, reason: collision with root package name */
    public static String f10642c = "0";

    /* renamed from: d, reason: collision with root package name */
    public static c f10643d = null;

    /* renamed from: e, reason: collision with root package name */
    private static String f10644e = "";

    /* renamed from: f, reason: collision with root package name */
    private static String f10645f = "";

    /* renamed from: g, reason: collision with root package name */
    private static String f10646g = "";

    /* renamed from: h, reason: collision with root package name */
    private static int f10647h = 5;

    /* renamed from: i, reason: collision with root package name */
    private static int f10648i = -1;

    /* renamed from: j, reason: collision with root package name */
    private static String f10649j = "";

    /* renamed from: k, reason: collision with root package name */
    private static String f10650k = "";

    /* renamed from: l, reason: collision with root package name */
    private static String f10651l = "";

    public static String a() {
        try {
            return DefaultWebClient.HTTPS_SCHEME + e.d() + "/unicomAuth/android/v3.0/qc?";
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void a(int i2) {
        try {
            f10641b = i2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void a(String str) {
        try {
            f10642c = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static boolean a(String str, String str2) {
        try {
            t.c("\nhostname : " + str + "\nsubjectName : " + str2);
            if (str.endsWith(".10010.com") && b(str2, "CN=10010.com")) {
                return true;
            }
            if (str.equals("auth.wosms.cn") && b(str2, "CN=auth.wosms.cn")) {
                return true;
            }
            if (str.equals("msv6.wosms.cn") && b(str2, "CN=msv6.wosms.cn")) {
                return true;
            }
            if (str.equals("ali.wosms.cn") && b(str2, "CN=ali.wosms.cn")) {
                return true;
            }
            if (str.equals("test.wosms.cn") && b(str2, "CN=test.wosms.cn")) {
                return true;
            }
            if (str.equals("m.zzx.cnklog.com") && b(str2, "CN=m.zzx.cnklog.com")) {
                return true;
            }
            if (str.equals("id6.me") && b(str2, "CN=*.id6.me")) {
                return true;
            }
            if (str.equals("cert.cmpassport.com")) {
                if (b(str2, "CN=*.cmpassport.com")) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static String b() {
        try {
            return f10642c;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void b(int i2) {
        try {
            f10647h = i2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void b(String str) {
        try {
            f10644e = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    private static boolean b(String str, String str2) {
        try {
            if (str.startsWith(str2)) {
                return true;
            }
            return str.endsWith(str2);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static String c() {
        try {
            return f10644e;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void c(int i2) {
        try {
            f10648i = i2;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void c(String str) {
        try {
            f10645f = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static String d() {
        try {
            return f10645f;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void d(String str) {
        try {
            t.c("APN:".concat(String.valueOf(str)));
            f10646g = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static String e() {
        try {
            String strA = i.a();
            f10645f = strA;
            return strA;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String e(String str) {
        try {
            if (!"cmnet".equals(str) && !"cmwap".equals(str)) {
                if (!"3gwap".equals(str) && !"uniwap".equals(str) && !"3gnet".equals(str) && !"uninet".equals(str)) {
                    if (!"ctnet".equals(str)) {
                        if (!"ctwap".equals(str)) {
                            return "0";
                        }
                    }
                    return "2";
                }
                return "3";
            }
            return "1";
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String f() {
        try {
            return f10646g;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void f(String str) {
        try {
            f10649j = str;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static int g() {
        try {
            return f10647h;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }

    public static int h() {
        try {
            return f10648i;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return -1;
        }
    }
}
