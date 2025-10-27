package com.github.mervick.aes_everywhere;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes3.dex */
public abstract class AbstractAes256 {
    protected static final byte[] SALTED = "Salted__".getBytes(StandardCharsets.US_ASCII);

    public static byte[] _decrypt(byte[] bArr, byte[] bArr2) throws Exception {
        byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 8, 16);
        if (!Arrays.equals(Arrays.copyOfRange(bArr, 0, 8), SALTED)) {
            throw new IllegalArgumentException("Invalid crypted data");
        }
        Object[] objArrDeriveKeyAndIv = deriveKeyAndIv(bArr2, bArrCopyOfRange);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, new SecretKeySpec((byte[]) objArrDeriveKeyAndIv[0], "AES"), new IvParameterSpec((byte[]) objArrDeriveKeyAndIv[1]));
        return cipher.doFinal(bArr, 16, bArr.length - 16);
    }

    public static byte[] _encrypt(byte[] bArr, byte[] bArr2) throws Exception {
        byte[] bArrGenerateSeed = new SecureRandom().generateSeed(8);
        Object[] objArrDeriveKeyAndIv = deriveKeyAndIv(bArr2, bArrGenerateSeed);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(1, new SecretKeySpec((byte[]) objArrDeriveKeyAndIv[0], "AES"), new IvParameterSpec((byte[]) objArrDeriveKeyAndIv[1]));
        return concat(concat(SALTED, bArrGenerateSeed), cipher.doFinal(bArr));
    }

    public static byte[] concat(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public static Object[] deriveKeyAndIv(byte[] bArr, byte[] bArr2) throws Exception {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        byte[] bArrConcat = concat(bArr, bArr2);
        byte[] bArrConcat2 = new byte[0];
        byte[] bArrDigest = new byte[0];
        for (int i2 = 0; i2 < 3; i2++) {
            bArrDigest = messageDigest.digest(concat(bArrDigest, bArrConcat));
            bArrConcat2 = concat(bArrConcat2, bArrDigest);
        }
        return new Object[]{Arrays.copyOfRange(bArrConcat2, 0, 32), Arrays.copyOfRange(bArrConcat2, 32, 48)};
    }
}
