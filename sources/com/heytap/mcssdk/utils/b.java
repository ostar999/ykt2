package com.heytap.mcssdk.utils;

import android.text.TextUtils;
import com.heytap.msp.push.encrypt.AESEncrypt;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static String f7213a = null;

    /* renamed from: b, reason: collision with root package name */
    public static final String f7214b = "Y29tLm5lYXJtZS5tY3M=";

    /* renamed from: c, reason: collision with root package name */
    public static String f7215c = "";

    private static String a() {
        if (TextUtils.isEmpty(f7215c)) {
            f7215c = new String(com.heytap.mcssdk.a.a.b(f7214b));
        }
        byte[] bArrA = a(a(f7215c));
        return bArrA != null ? new String(bArrA, Charset.forName("UTF-8")) : "";
    }

    public static byte[] a(String str) {
        if (str == null) {
            return new byte[0];
        }
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return new byte[0];
        }
    }

    public static byte[] a(byte[] bArr) {
        int length = bArr.length % 2 == 0 ? bArr.length : bArr.length - 1;
        for (int i2 = 0; i2 < length; i2 += 2) {
            byte b3 = bArr[i2];
            int i3 = i2 + 1;
            bArr[i2] = bArr[i3];
            bArr[i3] = b3;
        }
        return bArr;
    }

    public static String b(String str) {
        boolean z2;
        String strDecrypt = "";
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            strDecrypt = c.a(str, a());
            d.b("sdkDecrypt desDecrypt des data " + strDecrypt);
            z2 = true;
        } catch (Exception e2) {
            d.b("sdkDecrypt DES excepiton " + e2.toString());
            z2 = false;
        }
        if (TextUtils.isEmpty(strDecrypt) ? false : z2) {
            return strDecrypt;
        }
        try {
            strDecrypt = AESEncrypt.decrypt(AESEncrypt.SDK_APP_SECRET, str);
            f7213a = "AES";
            e.f().b(f7213a);
            d.b("sdkDecrypt desDecrypt aes data " + strDecrypt);
            return strDecrypt;
        } catch (Exception e3) {
            d.b("sdkDecrypt AES excepiton " + e3.toString());
            return strDecrypt;
        }
    }

    public static String c(String str) {
        boolean z2;
        String strA = "";
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        try {
            strA = AESEncrypt.decrypt(AESEncrypt.SDK_APP_SECRET, str);
            d.b("sdkDecrypt aesDecrypt aes data " + strA);
            z2 = true;
        } catch (Exception e2) {
            d.b("sdkDecrypt AES excepiton " + e2.toString());
            z2 = false;
        }
        if (TextUtils.isEmpty(strA) ? false : z2) {
            return strA;
        }
        try {
            strA = c.a(str, a());
            f7213a = "DES";
            e.f().b(f7213a);
            d.b("sdkDecrypt aesDecrypt des data " + strA);
            return strA;
        } catch (Exception e3) {
            d.b("sdkDecrypt DES excepiton " + e3.toString());
            return strA;
        }
    }

    public static String d(String str) {
        d.b("sdkDecrypt start data " + str);
        if (TextUtils.isEmpty(f7213a)) {
            f7213a = e.f().e();
        }
        if ("DES".equals(f7213a)) {
            d.b("sdkDecrypt start DES");
            return b(str);
        }
        d.b("sdkDecrypt start AES");
        return c(str);
    }
}
