package com.tencent.liteav.basic.c;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;

/* loaded from: classes6.dex */
public final class a {

    /* renamed from: a, reason: collision with root package name */
    private static String f18341a = "RSA";

    public static byte[] a(byte[] bArr, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(2, privateKey);
        int length = bArr.length;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int blockSize = 0;
        while (true) {
            int i2 = length - blockSize;
            if (i2 <= 0) {
                byte[] byteArray = byteArrayOutputStream.toByteArray();
                byteArrayOutputStream.close();
                return byteArray;
            }
            byteArrayOutputStream.write(i2 >= cipher.getBlockSize() ? cipher.doFinal(bArr, blockSize, cipher.getBlockSize()) : cipher.doFinal(bArr, blockSize, i2));
            blockSize += cipher.getBlockSize();
        }
    }

    public static PrivateKey a(byte[] bArr) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return KeyFactory.getInstance(f18341a).generatePrivate(new PKCS8EncodedKeySpec(bArr));
    }
}
