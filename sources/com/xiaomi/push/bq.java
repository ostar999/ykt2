package com.xiaomi.push;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes6.dex */
public final class bq {

    /* renamed from: a, reason: collision with root package name */
    private static String f24648a;

    /* renamed from: b, reason: collision with root package name */
    private static String f24649b;

    /* renamed from: c, reason: collision with root package name */
    private static String f24650c;

    public static int a() {
        String strA = bp.a();
        if (strA == null) {
            return -1;
        }
        int length = strA.length();
        if (!strA.isEmpty() && length > 1) {
            try {
                return Integer.parseInt(strA.substring(0, 3));
            } catch (Exception unused) {
            }
        }
        return -1;
    }

    /* renamed from: a, reason: collision with other method in class */
    public static String m248a() {
        String str = f24648a;
        if (str != null) {
            return str;
        }
        String strB = b(bp.b());
        if (strB == null) {
            return b(bo.a("ro.ril.miui.imei", ""));
        }
        f24648a = strB;
        return strB;
    }

    public static String a(String str) throws NoSuchAlgorithmException {
        char[] cArr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] bytes = str.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            byte[] bArrDigest = messageDigest.digest();
            char[] cArr2 = new char[bArrDigest.length * 2];
            int i2 = 0;
            for (byte b3 : bArrDigest) {
                int i3 = i2 + 1;
                cArr2[i2] = cArr[(b3 >>> 4) & 15];
                i2 = i3 + 1;
                cArr2[i3] = cArr[b3 & 15];
            }
            return new String(cArr2);
        } catch (Exception unused) {
            return null;
        }
    }

    public static boolean a(int i2) {
        return i2 == 1;
    }

    public static int b() {
        String strA = bp.a();
        if (strA == null) {
            return -1;
        }
        int length = strA.length();
        if (!strA.isEmpty() && length > 1) {
            try {
                return Integer.parseInt(strA.substring(3));
            } catch (Exception unused) {
            }
        }
        return -1;
    }

    /* renamed from: b, reason: collision with other method in class */
    public static String m249b() {
        String str = f24649b;
        if (str != null && !str.isEmpty()) {
            return f24649b;
        }
        String strA = bo.a("ro.product.model", "");
        f24649b = strA;
        String strReplaceAll = strA.replaceAll(" ", "");
        f24649b = strReplaceAll;
        return strReplaceAll;
    }

    private static String b(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        if (str.startsWith(",") || str.endsWith(",")) {
            str = str.replace(",", "");
        }
        if (str.startsWith("0")) {
            if (Long.parseLong(str) == 0) {
                return null;
            }
        }
        return str;
    }

    public static String c() {
        String str = f24650c;
        if (str != null && !str.isEmpty()) {
            return f24650c;
        }
        String strA = bo.a("ro.build.version.incremental", "");
        f24650c = strA;
        return strA;
    }

    public static String d() {
        return !bm.m247a() ? bm.a() : !bo.a("ro.product.locale.region", "CN").equals("CN") ? "global" : bm.b() ? "alpha" : bm.c() ? "dev" : bm.d() ? "stable" : "alpha";
    }

    public static String e() {
        try {
            return bk.a().getPackageManager().getPackageInfo(bk.a().getPackageName(), 0).versionName;
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String f() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
