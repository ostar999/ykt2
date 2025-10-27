package com.tencent.smtt.utils;

import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes6.dex */
public class Md5Utils {
    /* JADX WARN: Removed duplicated region for block: B:45:0x0053 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x003d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0048 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getMD5(java.io.File r5) throws java.lang.Throwable {
        /*
            r0 = 0
            java.lang.String r1 = "MD5"
            java.security.MessageDigest r1 = java.security.MessageDigest.getInstance(r1)     // Catch: java.lang.Throwable -> L8 java.io.IOException -> La java.io.FileNotFoundException -> Lc java.security.NoSuchAlgorithmException -> Le
            goto L13
        L8:
            r5 = move-exception
            goto L3b
        La:
            r2 = r0
            goto L46
        Lc:
            r2 = r0
            goto L51
        Le:
            r1 = move-exception
            r1.printStackTrace()     // Catch: java.lang.Throwable -> L8 java.io.IOException -> La java.io.FileNotFoundException -> Lc
            r1 = r0
        L13:
            java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L8 java.io.IOException -> La java.io.FileNotFoundException -> Lc
            r2.<init>(r5)     // Catch: java.lang.Throwable -> L8 java.io.IOException -> La java.io.FileNotFoundException -> Lc
            r5 = 8192(0x2000, float:1.148E-41)
            byte[] r5 = new byte[r5]     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L46 java.io.FileNotFoundException -> L51
        L1c:
            int r3 = r2.read(r5)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L46 java.io.FileNotFoundException -> L51
            r4 = -1
            if (r3 == r4) goto L28
            r4 = 0
            r1.update(r5, r4, r3)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L46 java.io.FileNotFoundException -> L51
            goto L1c
        L28:
            byte[] r5 = r1.digest()     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L46 java.io.FileNotFoundException -> L51
            java.lang.String r5 = com.tencent.smtt.utils.ByteUtils.a(r5)     // Catch: java.lang.Throwable -> L39 java.io.IOException -> L46 java.io.FileNotFoundException -> L51
            r2.close()     // Catch: java.io.IOException -> L34
            goto L38
        L34:
            r0 = move-exception
            r0.printStackTrace()
        L38:
            return r5
        L39:
            r5 = move-exception
            r0 = r2
        L3b:
            if (r0 == 0) goto L45
            r0.close()     // Catch: java.io.IOException -> L41
            goto L45
        L41:
            r0 = move-exception
            r0.printStackTrace()
        L45:
            throw r5
        L46:
            if (r2 == 0) goto L50
            r2.close()     // Catch: java.io.IOException -> L4c
            goto L50
        L4c:
            r5 = move-exception
            r5.printStackTrace()
        L50:
            return r0
        L51:
            if (r2 == 0) goto L5b
            r2.close()     // Catch: java.io.IOException -> L57
            goto L5b
        L57:
            r5 = move-exception
            r5.printStackTrace()
        L5b:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.smtt.utils.Md5Utils.getMD5(java.io.File):java.lang.String");
    }

    public static String getMD5(String str) throws NoSuchAlgorithmException {
        if (str == null) {
            return null;
        }
        try {
            byte[] bytes = str.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            return ByteUtils.a(messageDigest.digest());
        } catch (Exception unused) {
            return null;
        }
    }

    public static byte[] getMD5(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            if (messageDigest == null) {
                return null;
            }
            byte[] bArr = new byte[8192];
            while (true) {
                int i2 = inputStream.read(bArr);
                if (i2 == -1) {
                    return messageDigest.digest();
                }
                messageDigest.update(bArr, 0, i2);
            }
        } catch (Throwable unused) {
            return null;
        }
    }

    public static byte[] getMD5(byte[] bArr) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            return messageDigest.digest();
        } catch (Exception unused) {
            return null;
        }
    }
}
