package com.mobile.auth.l;

import android.content.Context;
import android.text.TextUtils;
import com.mobile.auth.l.k;
import com.mobile.auth.l.n;

/* loaded from: classes4.dex */
public class h {

    /* renamed from: a, reason: collision with root package name */
    private static String f10419a;

    /* renamed from: b, reason: collision with root package name */
    private static String f10420b;

    /* renamed from: c, reason: collision with root package name */
    private static long f10421c;

    private static int a(String str) {
        String strB;
        if (TextUtils.isEmpty(f10420b)) {
            strB = k.b("pre_sim_key", "");
            f10420b = strB;
        } else {
            strB = f10420b;
        }
        if (TextUtils.isEmpty(strB)) {
            return 0;
        }
        return strB.equals(str) ? 1 : 2;
    }

    public static long a() {
        long jA;
        long j2;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (TextUtils.isEmpty(f10419a)) {
            String strB = k.b("phonescripcache", "");
            jA = k.a("phonescripstarttime", 0L);
            if (TextUtils.isEmpty(strB)) {
                j2 = 0;
            }
            return Math.max(j2 / 1000, 0L);
        }
        c.b("PhoneScripUtils", f10420b + " " + f10421c);
        jA = f10421c;
        j2 = (jA - jCurrentTimeMillis) - com.heytap.mcssdk.constant.a.f7153q;
        return Math.max(j2 / 1000, 0L);
    }

    public static String a(Context context) {
        if (!TextUtils.isEmpty(f10419a)) {
            return f10419a;
        }
        String strB = k.b("phonescripcache", "");
        if (TextUtils.isEmpty(strB)) {
            c.a("PhoneScripUtils", "null");
            return null;
        }
        f10421c = k.a("phonescripstarttime", 0L);
        f10420b = k.b("pre_sim_key", "");
        String strB2 = b.b(context, strB);
        f10419a = strB2;
        return strB2;
    }

    public static void a(final Context context, final String str, long j2, final String str2, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || j2 <= 0) {
            return;
        }
        c.b("PhoneScripUtils", "save phone scrip simKey = " + str2);
        f10419a = str;
        long j3 = j2 * 1000;
        f10421c = System.currentTimeMillis() + j3;
        c.b("sLifeTime", f10421c + "");
        f10420b = str2;
        if (!"operator".equals(str3)) {
            n.a(new n.a() { // from class: com.mobile.auth.l.h.1
                @Override // com.mobile.auth.l.n.a
                public void a() {
                    c.b("PhoneScripUtils", "start save scrip to sp in sub thread");
                    h.b(context, str, h.f10421c, str2);
                }
            });
        } else if (j3 > com.heytap.mcssdk.constant.a.f7141e) {
            f10421c = System.currentTimeMillis() + com.heytap.mcssdk.constant.a.f7141e;
        } else {
            f10421c = System.currentTimeMillis() + j3;
        }
    }

    public static void a(boolean z2, boolean z3) {
        k.a aVarA = k.a();
        aVarA.a("phonescripstarttime");
        aVarA.a("phonescripcache");
        aVarA.a("pre_sim_key");
        if (z3) {
            aVarA.a();
        } else {
            aVarA.b();
        }
        if (z2) {
            f10419a = null;
            f10420b = null;
            f10421c = 0L;
        }
    }

    private static boolean a(long j2) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        c.b("PhoneScripUtils", j2 + "");
        c.b("PhoneScripUtils", jCurrentTimeMillis + "");
        return j2 - jCurrentTimeMillis > com.heytap.mcssdk.constant.a.f7153q;
    }

    public static boolean a(com.cmic.sso.sdk.a aVar) {
        int iA = a(aVar.b("scripKey"));
        aVar.a("imsiState", iA + "");
        c.b("PhoneScripUtils", "simState = " + iA);
        if (k.a("phonescripversion", 0) != 1 && iA != 0) {
            a(true, false);
            b.a();
            c.b("PhoneScripUtils", "phoneScriptVersion change");
            return false;
        }
        if (iA == 1) {
            return c();
        }
        if (iA == 2) {
            a(true, false);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void b(Context context, String str, long j2, String str2) {
        String strA = b.a(context, str);
        if (TextUtils.isEmpty(strA)) {
            return;
        }
        k.a aVarA = k.a();
        aVarA.a("phonescripcache", strA);
        aVarA.a("phonescripstarttime", j2);
        aVarA.a("phonescripversion", 1);
        aVarA.a("pre_sim_key", str2);
        aVarA.b();
    }

    private static boolean c() {
        if (TextUtils.isEmpty(f10419a)) {
            return !TextUtils.isEmpty(k.b("phonescripcache", "")) && a(k.a("phonescripstarttime", 0L));
        }
        c.b("PhoneScripUtils", f10420b + " " + f10421c);
        return a(f10421c);
    }
}
