package com.alipay.sdk.encrypt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/* loaded from: classes2.dex */
public final class d {

    /* renamed from: a, reason: collision with root package name */
    private static final String f3273a = "RSA";

    public static byte[] a(String str, String str2) throws Throwable {
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayOutputStream byteArrayOutputStream2 = null;
        byteArray = null;
        byte[] byteArray = null;
        try {
            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance(f3273a).generatePublic(new X509EncodedKeySpec(a.a(str2)));
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, publicKeyGeneratePublic);
            byte[] bytes = str.getBytes("UTF-8");
            int blockSize = cipher.getBlockSize();
            byteArrayOutputStream = new ByteArrayOutputStream();
            for (int i2 = 0; i2 < bytes.length; i2 += blockSize) {
                try {
                    byteArrayOutputStream.write(cipher.doFinal(bytes, i2, bytes.length - i2 < blockSize ? bytes.length - i2 : blockSize));
                } catch (Exception unused) {
                    if (byteArrayOutputStream != null) {
                        byteArrayOutputStream.close();
                    }
                    return byteArray;
                } catch (Throwable th) {
                    th = th;
                    byteArrayOutputStream2 = byteArrayOutputStream;
                    if (byteArrayOutputStream2 != null) {
                        try {
                            byteArrayOutputStream2.close();
                        } catch (IOException unused2) {
                        }
                    }
                    throw th;
                }
            }
            byteArray = byteArrayOutputStream.toByteArray();
        } catch (Exception unused3) {
            byteArrayOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException unused4) {
        }
        return byteArray;
    }

    private static PublicKey b(String str, String str2) throws Exception {
        return KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(a.a(str2)));
    }
}
