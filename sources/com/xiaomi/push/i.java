package com.xiaomi.push;

import com.google.common.base.Ascii;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes6.dex */
public class i {

    /* renamed from: a, reason: collision with root package name */
    private static final byte[] f25134a = {100, 23, 84, 114, 72, 0, 4, 97, 73, 97, 2, TarConstants.LF_BLK, 84, 102, Ascii.DC2, 32};

    private static Cipher a(byte[] bArr, int i2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        IvParameterSpec ivParameterSpec = new IvParameterSpec(f25134a);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(i2, secretKeySpec, ivParameterSpec);
        return cipher;
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        return a(bArr, 2).doFinal(bArr2);
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) {
        return a(bArr, 1).doFinal(bArr2);
    }
}
