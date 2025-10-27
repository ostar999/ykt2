package com.mobile.auth.c;

import android.util.Log;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* loaded from: classes4.dex */
public class m {

    /* renamed from: a, reason: collision with root package name */
    private static final String f9662a = "m";

    /* renamed from: b, reason: collision with root package name */
    private static String f9663b = "RSA/ECB/PKCS1Padding";

    public static String a(String str, RSAPublicKey rSAPublicKey) {
        try {
            byte[] bArrA = a(rSAPublicKey, str.getBytes());
            return bArrA != null ? q.a(bArrA) : "";
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static PublicKey a(String str) throws Throwable {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(c.a(str)));
        } catch (Throwable th) {
            try {
                ExceptionProcessor.processException(th);
                return null;
            } catch (Throwable th2) {
                ExceptionProcessor.processException(th2);
                return null;
            }
        }
    }

    public static RSAPublicKey a() {
        try {
            return (RSAPublicKey) a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC5se07mkN71qsSJHjZ2Z0+Z+4LlLvf2sz7Md38VAa3EmAOvI7vZp3hbAxicL724ylcmisTPtZQhT/9C+25AELqy9PN9JmzKpwoVTUoJvxG4BoyT49+gGVl6s6zo1byNoHUzTfkmRfmC9MC53HvG8GwKP5xtcdptFjAIcgIR7oAWQIDAQAB");
        } catch (Throwable th) {
            try {
                Log.w(f9662a, "getPublicKey error", th);
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

    public static byte[] a(RSAPublicKey rSAPublicKey, byte[] bArr) {
        try {
            Cipher cipher = Cipher.getInstance(f9663b);
            cipher.init(1, rSAPublicKey);
            return cipher.doFinal(bArr);
        } catch (Throwable th) {
            try {
                Log.w(f9662a, "Rsa encrypt4Ux error", th);
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
}
