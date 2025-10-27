package com.alibaba.sdk.android.beacon;

import android.content.Context;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
final class c {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f2652a = "0123456789abcdef".toCharArray();

    public static String a(Context context) {
        if (context == null) {
            return "";
        }
        try {
            String str = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            return str == null ? "" : str;
        } catch (Exception e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String a(String str, String str2) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(str.getBytes(), "HmacSHA1");
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKeySpec);
            return a(mac.doFinal(str2.getBytes()));
        } catch (Throwable th) {
            th.printStackTrace();
            return "";
        }
    }

    private static String a(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = bArr[i2] & 255;
            int i4 = i2 * 2;
            char[] cArr2 = f2652a;
            cArr[i4] = cArr2[i3 >>> 4];
            cArr[i4 + 1] = cArr2[i3 & 15];
        }
        return new String(cArr);
    }
}
