package com.mobile.auth.l;

import android.text.TextUtils;
import java.security.SecureRandom;
import java.util.UUID;

/* loaded from: classes4.dex */
public class q {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f10439a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        char[] cArr = new char[bArr.length * 2];
        int i2 = 0;
        for (byte b3 : bArr) {
            int i3 = i2 + 1;
            char[] cArr2 = f10439a;
            cArr[i2] = cArr2[(b3 >>> 4) & 15];
            i2 = i3 + 1;
            cArr[i3] = cArr2[b3 & 15];
        }
        return new String(cArr);
    }

    public static void a(com.cmic.sso.sdk.a aVar, String str) {
        if (!TextUtils.isEmpty(aVar.b("interfaceType", ""))) {
            str = aVar.b("interfaceType") + com.alipay.sdk.util.h.f3376b + str;
        }
        aVar.a("interfaceType", str);
    }

    public static boolean a(com.mobile.auth.d.a aVar) {
        return k.a("logCloseTime", 0L) + ((long) (((aVar.l() * 60) * 60) * 1000)) >= System.currentTimeMillis();
    }

    public static byte[] a() {
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        return bArr;
    }

    public static String b() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static void b(com.cmic.sso.sdk.a aVar, String str) {
        if (!TextUtils.isEmpty(aVar.b("interfaceCode", ""))) {
            str = aVar.b("interfaceCode") + com.alipay.sdk.util.h.f3376b + str;
        }
        aVar.a("interfaceCode", str);
    }

    public static String c() {
        return d().replace("-", "");
    }

    public static void c(com.cmic.sso.sdk.a aVar, String str) {
        if (!TextUtils.isEmpty(aVar.b("interfaceElasped", ""))) {
            str = aVar.b("interfaceElasped") + com.alipay.sdk.util.h.f3376b + str;
        }
        aVar.a("interfaceElasped", str);
    }

    private static String d() {
        return UUID.randomUUID().toString();
    }
}
