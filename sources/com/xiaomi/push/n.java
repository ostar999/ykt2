package com.xiaomi.push;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes6.dex */
public class n {

    /* renamed from: a, reason: collision with root package name */
    private static int f25525a = 0;

    /* renamed from: a, reason: collision with other field name */
    private static Map<String, q> f937a = null;

    /* renamed from: b, reason: collision with root package name */
    private static int f25526b = -1;

    /* renamed from: c, reason: collision with root package name */
    private static int f25527c = -1;

    public static synchronized int a() {
        if (f25525a == 0) {
            try {
                int i2 = 1;
                if (!((TextUtils.isEmpty(m677a("ro.miui.ui.version.code")) && TextUtils.isEmpty(m677a("ro.miui.ui.version.name"))) ? false : true)) {
                    i2 = 2;
                }
                f25525a = i2;
            } catch (Throwable th) {
                com.xiaomi.channel.commonutils.logger.b.a("get isMIUI failed", th);
                f25525a = 0;
            }
            com.xiaomi.channel.commonutils.logger.b.b("isMIUI's value is: " + f25525a);
        }
        return f25525a;
    }

    public static q a(String str) {
        q qVarB = b(str);
        return qVarB == null ? q.Global : qVarB;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static synchronized String m676a() {
        int iA = v.a();
        return (!m679a() || iA <= 0) ? "" : iA < 2 ? "alpha" : iA < 3 ? "development" : "stable";
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m677a(String str) {
        try {
            try {
                return (String) at.a("android.os.SystemProperties", "get", str, "");
            } catch (Exception e2) {
                com.xiaomi.channel.commonutils.logger.b.a(e2);
                return null;
            }
        } catch (Throwable unused) {
            return null;
        }
    }

    /* renamed from: a, reason: collision with other method in class */
    private static void m678a() {
        if (f937a != null) {
            return;
        }
        HashMap map = new HashMap();
        f937a = map;
        map.put("CN", q.China);
        Map<String, q> map2 = f937a;
        q qVar = q.Europe;
        map2.put("FI", qVar);
        f937a.put("SE", qVar);
        f937a.put("NO", qVar);
        f937a.put("FO", qVar);
        f937a.put("EE", qVar);
        f937a.put("LV", qVar);
        f937a.put("LT", qVar);
        f937a.put("BY", qVar);
        f937a.put("MD", qVar);
        f937a.put("UA", qVar);
        f937a.put("PL", qVar);
        f937a.put("CZ", qVar);
        f937a.put("SK", qVar);
        f937a.put("HU", qVar);
        f937a.put("DE", qVar);
        f937a.put("AT", qVar);
        f937a.put("CH", qVar);
        f937a.put("LI", qVar);
        f937a.put("GB", qVar);
        f937a.put("IE", qVar);
        f937a.put("NL", qVar);
        f937a.put("BE", qVar);
        f937a.put("LU", qVar);
        f937a.put("FR", qVar);
        f937a.put("RO", qVar);
        f937a.put("BG", qVar);
        f937a.put("RS", qVar);
        f937a.put("MK", qVar);
        f937a.put("AL", qVar);
        f937a.put("GR", qVar);
        f937a.put("SI", qVar);
        f937a.put("HR", qVar);
        f937a.put("IT", qVar);
        f937a.put("SM", qVar);
        f937a.put("MT", qVar);
        f937a.put("ES", qVar);
        f937a.put("PT", qVar);
        f937a.put("AD", qVar);
        f937a.put("CY", qVar);
        f937a.put("DK", qVar);
        f937a.put("RU", q.Russia);
        f937a.put("IN", q.India);
    }

    /* renamed from: a, reason: collision with other method in class */
    public static synchronized boolean m679a() {
        return a() == 1;
    }

    private static q b(String str) {
        m678a();
        return f937a.get(str.toUpperCase());
    }

    public static String b() {
        String strA = u.a("ro.miui.region", "");
        if (TextUtils.isEmpty(strA)) {
            strA = u.a("ro.product.locale.region", "");
        }
        if (TextUtils.isEmpty(strA)) {
            strA = u.a("persist.sys.country", "");
        }
        return TextUtils.isEmpty(strA) ? Locale.getDefault().getCountry() : strA;
    }

    /* renamed from: b, reason: collision with other method in class */
    public static synchronized boolean m680b() {
        return a() == 2;
    }

    public static boolean c() {
        if (f25526b < 0) {
            Object objA = at.a("miui.external.SdkHelper", "isMiuiSystem", new Object[0]);
            f25526b = 0;
            if (objA != null && (objA instanceof Boolean) && !((Boolean) Boolean.class.cast(objA)).booleanValue()) {
                f25526b = 1;
            }
        }
        return f25526b > 0;
    }

    public static boolean d() {
        if (f25527c < 0) {
            if (q.Europe.name().equalsIgnoreCase(a(b()).name()) && m679a()) {
                f25527c = 1;
            } else {
                f25527c = 0;
            }
        }
        return f25527c > 0;
    }
}
