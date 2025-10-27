package com.mobile.auth.c;

import android.util.Log;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public class a {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9600a = "a";

    /* renamed from: b, reason: collision with root package name */
    private static byte[] f9601b = "0000000000000000".getBytes();

    /* renamed from: c, reason: collision with root package name */
    private static byte[] f9602c = "vrf5g7h0tededwx3".getBytes();

    public static String a(String str, String str2) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(f9601b);
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] bytes = str.getBytes("utf-8");
            cipher.init(1, secretKeySpec, ivParameterSpec);
            return q.a(cipher.doFinal(bytes));
        } catch (Throwable th) {
            try {
                Log.w(f9600a, "encryptAesNew error", th);
                return null;
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
    }

    public static String b(String str, String str2) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(f9601b);
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            byte[] bArrDoFinal = cipher.doFinal(q.a(str));
            if (bArrDoFinal != null) {
                return new String(bArrDoFinal);
            }
            Log.i(f9600a, "Aes decrypt result is empty");
            return "";
        } catch (Throwable th) {
            try {
                Log.w(f9600a, "decryptAesNew error", th);
                return "";
            } catch (Throwable th2) {
                try {
                    ExceptionProcessor.processException(th2);
                    return null;
                } catch (Throwable th3) {
                    ExceptionProcessor.processException(th3);
                    return null;
                }
            }
        }
    }
}
