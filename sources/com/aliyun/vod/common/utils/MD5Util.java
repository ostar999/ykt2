package com.aliyun.vod.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public class MD5Util {
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final char[] DIGITS_UPPER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final int STREAM_BUFFER_LENGTH = 1024;

    private static String bytesToHexStr(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int i3 = i2 * 2;
            char[] cArr2 = DIGITS_UPPER;
            byte b3 = bArr[i2];
            cArr[i3] = cArr2[(b3 >>> 4) & 15];
            cArr[i3 + 1] = cArr2[b3 & 15];
        }
        return new String(cArr);
    }

    public static byte[] encrypt(String str) {
        return encrypt(str.getBytes());
    }

    public static String encryptToHexStr(String str) throws NoSuchAlgorithmException {
        if (str == null || str.isEmpty()) {
            return "";
        }
        try {
            byte[] bytes = str.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            return bytesToHexStr(messageDigest.digest());
        } catch (Exception unused) {
            return null;
        }
    }

    private static MessageDigest getDigest(String str) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(str);
    }

    private static MessageDigest updateDigest(MessageDigest messageDigest, InputStream inputStream) throws IOException {
        byte[] bArr = new byte[1024];
        while (true) {
            int i2 = inputStream.read(bArr, 0, 1024);
            if (i2 <= -1) {
                return messageDigest;
            }
            messageDigest.update(bArr, 0, i2);
        }
    }

    public static byte[] encrypt(byte[] bArr) {
        try {
            MessageDigest digest = getDigest("MD5");
            digest.update(bArr);
            return digest.digest();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static byte[] encrypt(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
        return updateDigest(getDigest("MD5"), inputStream).digest();
    }
}
