package com.tencent.open.utils;

import android.util.Base64;
import com.tencent.open.log.SLog;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes6.dex */
public class e {

    /* renamed from: a, reason: collision with root package name */
    private static final char[] f20666a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static String a(String str, String str2, byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(1, secretKeySpec, ivParameterSpec);
            return Base64.encodeToString(cipher.doFinal(str.getBytes()), 0);
        } catch (Exception e2) {
            SLog.e("DESUtils", "encryptAES", e2);
            return null;
        }
    }

    public static byte[] a(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(str.getBytes());
            return messageDigest.digest();
        } catch (Exception e2) {
            SLog.e("DESUtils", "encryptSha", e2);
            return null;
        }
    }

    public static String a(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        char[] cArr = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b3 = bArr[i2];
            int i3 = i2 * 2;
            char[] cArr2 = f20666a;
            cArr[i3 + 1] = cArr2[b3 & 15];
            cArr[i3] = cArr2[((byte) (b3 >>> 4)) & 15];
        }
        return new String(cArr);
    }
}
