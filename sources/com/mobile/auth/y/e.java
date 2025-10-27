package com.mobile.auth.y;

import android.content.Context;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class e {

    /* renamed from: b, reason: collision with root package name */
    private static volatile e f10595b;

    /* renamed from: a, reason: collision with root package name */
    public Context f10596a;

    /* renamed from: c, reason: collision with root package name */
    private ExecutorService f10597c = Executors.newSingleThreadExecutor();

    private e() {
    }

    public static e a() {
        try {
            if (f10595b == null) {
                synchronized (e.class) {
                    if (f10595b == null) {
                        f10595b = new e();
                    }
                }
            }
            return f10595b;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static String a(Context context, String str, String str2) {
        try {
            return f.a(context, str, str2);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void a(int i2, d dVar, String str) {
        try {
            t.e("type:" + i2 + "\nmsg:" + str);
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("resultCode", 1);
                jSONObject.put("resultMsg", str);
                jSONObject.put("resultData", "");
                jSONObject.put("seq", "");
                dVar.onResult(jSONObject.toString());
            } catch (Exception unused) {
                t.b();
            }
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void a(boolean z2) {
        try {
            t.a(z2);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static boolean a(Context context) {
        try {
            if (g.a(context)) {
                if (g.c(context)) {
                    return true;
                }
            }
            return false;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static boolean a(Context context, String str, String str2, String str3) {
        try {
            return f.a(context, str, str2, str3);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static String b() {
        try {
            return p.b();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static void b(Context context) {
        try {
            g.b(context);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static boolean b(String str) {
        try {
            if (str.equalsIgnoreCase("ali.wosms.cn") || str.equalsIgnoreCase("m.zzx.cnklog.com") || str.equalsIgnoreCase("msv6.wosms.cn") || str.equalsIgnoreCase("test.wosms.cn")) {
                p.f10627c = str;
                return true;
            }
            p.f10627c = "msv6.wosms.cn";
            return false;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static String c() {
        return "phoneinfo";
    }

    public static void c(Context context) {
        try {
            g.d(context);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static String d() {
        try {
            String str = p.f10627c;
            if (((str == null || str.length() == 0 || str.trim().length() == 0 || "null".equals(str)) ? Boolean.TRUE : Boolean.FALSE).booleanValue()) {
                p.f10627c = "msv6.wosms.cn";
            }
            return p.f10627c;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public static boolean d(Context context) {
        try {
            int iB = v.b(context);
            return iB == 0 || iB == 1;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return false;
        }
    }

    public static String e() {
        return "auth.wosms.cn";
    }

    public static void e(Context context) {
        try {
            f.a(context);
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void f() {
        try {
            n.f10620a = false;
            n.f10621b = false;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    public static void g() {
        try {
            q.a().b();
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.String a(java.lang.String r8) {
        /*
            r7 = this;
            android.content.Context r0 = r7.f10596a     // Catch: java.lang.Throwable -> La1
            if (r0 != 0) goto L7
            java.lang.String r8 = "sdk 未初始化, context 为空"
            return r8
        L7:
            java.lang.String r0 = r8.toLowerCase()     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            int r1 = r0.hashCode()     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            r2 = 5
            r3 = 4
            r4 = 3
            r5 = 2
            r6 = 1
            switch(r1) {
                case -903629273: goto L4a;
                case 107902: goto L40;
                case 3528965: goto L36;
                case 93029116: goto L2c;
                case 667683678: goto L22;
                case 909712337: goto L18;
                default: goto L17;
            }     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
        L17:
            goto L54
        L18:
            java.lang.String r1 = "packagename"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            if (r0 == 0) goto L54
            r0 = r6
            goto L55
        L22:
            java.lang.String r1 = "sdkversion"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            if (r0 == 0) goto L54
            r0 = r2
            goto L55
        L2c:
            java.lang.String r1 = "appid"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            if (r0 == 0) goto L54
            r0 = 0
            goto L55
        L36:
            java.lang.String r1 = "sha1"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            if (r0 == 0) goto L54
            r0 = r4
            goto L55
        L40:
            java.lang.String r1 = "md5"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            if (r0 == 0) goto L54
            r0 = r5
            goto L55
        L4a:
            java.lang.String r1 = "sha256"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            if (r0 == 0) goto L54
            r0 = r3
            goto L55
        L54:
            r0 = -1
        L55:
            if (r0 == 0) goto L88
            if (r0 == r6) goto L7d
            if (r0 == r5) goto L6e
            if (r0 == r4) goto L6e
            if (r0 == r3) goto L6e
            if (r0 != r2) goto L66
            java.lang.String r8 = com.mobile.auth.y.p.b()     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            return r8
        L66:
            java.lang.Exception r8 = new java.lang.Exception     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            java.lang.String r0 = "no info"
            r8.<init>(r0)     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            throw r8     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
        L6e:
            android.content.Context r0 = r7.f10596a     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            java.lang.String r1 = r0.getPackageName()     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            java.lang.String r8 = r8.toLowerCase()     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            java.lang.String r8 = com.mobile.auth.y.v.a(r0, r1, r8)     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            return r8
        L7d:
            android.content.Context r8 = r7.f10596a     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            android.content.Context r8 = r8.getApplicationContext()     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            java.lang.String r8 = r8.getPackageName()     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            return r8
        L88:
            java.lang.String r8 = com.mobile.auth.y.u.c()     // Catch: java.lang.Exception -> L8d java.lang.Throwable -> La1
            return r8
        L8d:
            r8 = move-exception
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> La1
            java.lang.String r1 = "no info:"
            r0.<init>(r1)     // Catch: java.lang.Throwable -> La1
            java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> La1
            r0.append(r8)     // Catch: java.lang.Throwable -> La1
            java.lang.String r8 = r0.toString()     // Catch: java.lang.Throwable -> La1
            return r8
        La1:
            r8 = move-exception
            com.mobile.auth.gatewayauth.ExceptionProcessor.processException(r8)
            r8 = 0
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.mobile.auth.y.e.a(java.lang.String):java.lang.String");
    }
}
