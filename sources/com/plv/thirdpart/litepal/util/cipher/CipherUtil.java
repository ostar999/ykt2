package com.plv.thirdpart.litepal.util.cipher;

import android.text.TextUtils;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes5.dex */
public class CipherUtil {
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static String aesKey = "LitePalKey";

    public static String aesDecrypt(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            return AESCrypt.decrypt(aesKey, str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String aesEncrypt(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            return AESCrypt.encrypt(aesKey, str);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String md5Encrypt(String str) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes(Charset.defaultCharset()));
            return new String(toHex(messageDigest.digest()));
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    private static char[] toHex(byte[] bArr) {
        char[] cArr = DIGITS_UPPER;
        char[] cArr2 = new char[bArr.length << 1];
        int i2 = 0;
        for (byte b3 : bArr) {
            int i3 = i2 + 1;
            cArr2[i2] = cArr[(b3 & 240) >>> 4];
            i2 = i3 + 1;
            cArr2[i3] = cArr[b3 & 15];
        }
        return cArr2;
    }
}
