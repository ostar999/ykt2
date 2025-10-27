package com.blankj.utilcode.util;

import android.os.Build;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public final class EncryptUtils {
    private EncryptUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static byte[] decrypt3DES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return symmetricTemplate(bArr, bArr2, "DESede", str, bArr3, false);
    }

    public static byte[] decryptAES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return symmetricTemplate(bArr, bArr2, "AES", str, bArr3, false);
    }

    public static byte[] decryptBase64AES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return decryptAES(UtilsBridge.base64Decode(bArr), bArr2, str, bArr3);
    }

    public static byte[] decryptBase64DES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return decryptDES(UtilsBridge.base64Decode(bArr), bArr2, str, bArr3);
    }

    public static byte[] decryptBase64RSA(byte[] bArr, byte[] bArr2, int i2, String str) {
        return decryptRSA(UtilsBridge.base64Decode(bArr), bArr2, i2, str);
    }

    public static byte[] decryptBase64_3DES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return decrypt3DES(UtilsBridge.base64Decode(bArr), bArr2, str, bArr3);
    }

    public static byte[] decryptDES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return symmetricTemplate(bArr, bArr2, "DES", str, bArr3, false);
    }

    public static byte[] decryptHexString3DES(String str, byte[] bArr, String str2, byte[] bArr2) {
        return decrypt3DES(UtilsBridge.hexString2Bytes(str), bArr, str2, bArr2);
    }

    public static byte[] decryptHexStringAES(String str, byte[] bArr, String str2, byte[] bArr2) {
        return decryptAES(UtilsBridge.hexString2Bytes(str), bArr, str2, bArr2);
    }

    public static byte[] decryptHexStringDES(String str, byte[] bArr, String str2, byte[] bArr2) {
        return decryptDES(UtilsBridge.hexString2Bytes(str), bArr, str2, bArr2);
    }

    public static byte[] decryptHexStringRSA(String str, byte[] bArr, int i2, String str2) {
        return decryptRSA(UtilsBridge.hexString2Bytes(str), bArr, i2, str2);
    }

    public static byte[] decryptRSA(byte[] bArr, byte[] bArr2, int i2, String str) {
        return rsaTemplate(bArr, bArr2, i2, str, false);
    }

    public static byte[] encrypt3DES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return symmetricTemplate(bArr, bArr2, "DESede", str, bArr3, true);
    }

    public static byte[] encrypt3DES2Base64(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return UtilsBridge.base64Encode(encrypt3DES(bArr, bArr2, str, bArr3));
    }

    public static String encrypt3DES2HexString(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return UtilsBridge.bytes2HexString(encrypt3DES(bArr, bArr2, str, bArr3));
    }

    public static byte[] encryptAES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return symmetricTemplate(bArr, bArr2, "AES", str, bArr3, true);
    }

    public static byte[] encryptAES2Base64(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return UtilsBridge.base64Encode(encryptAES(bArr, bArr2, str, bArr3));
    }

    public static String encryptAES2HexString(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return UtilsBridge.bytes2HexString(encryptAES(bArr, bArr2, str, bArr3));
    }

    public static byte[] encryptDES(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return symmetricTemplate(bArr, bArr2, "DES", str, bArr3, true);
    }

    public static byte[] encryptDES2Base64(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return UtilsBridge.base64Encode(encryptDES(bArr, bArr2, str, bArr3));
    }

    public static String encryptDES2HexString(byte[] bArr, byte[] bArr2, String str, byte[] bArr3) {
        return UtilsBridge.bytes2HexString(encryptDES(bArr, bArr2, str, bArr3));
    }

    public static byte[] encryptHmacMD5(byte[] bArr, byte[] bArr2) {
        return hmacTemplate(bArr, bArr2, "HmacMD5");
    }

    public static String encryptHmacMD5ToString(String str, String str2) {
        return (str == null || str.length() == 0 || str2 == null || str2.length() == 0) ? "" : encryptHmacMD5ToString(str.getBytes(), str2.getBytes());
    }

    public static byte[] encryptHmacSHA1(byte[] bArr, byte[] bArr2) {
        return hmacTemplate(bArr, bArr2, "HmacSHA1");
    }

    public static String encryptHmacSHA1ToString(String str, String str2) {
        return (str == null || str.length() == 0 || str2 == null || str2.length() == 0) ? "" : encryptHmacSHA1ToString(str.getBytes(), str2.getBytes());
    }

    public static byte[] encryptHmacSHA224(byte[] bArr, byte[] bArr2) {
        return hmacTemplate(bArr, bArr2, "HmacSHA224");
    }

    public static String encryptHmacSHA224ToString(String str, String str2) {
        return (str == null || str.length() == 0 || str2 == null || str2.length() == 0) ? "" : encryptHmacSHA224ToString(str.getBytes(), str2.getBytes());
    }

    public static byte[] encryptHmacSHA256(byte[] bArr, byte[] bArr2) {
        return hmacTemplate(bArr, bArr2, "HmacSHA256");
    }

    public static String encryptHmacSHA256ToString(String str, String str2) {
        return (str == null || str.length() == 0 || str2 == null || str2.length() == 0) ? "" : encryptHmacSHA256ToString(str.getBytes(), str2.getBytes());
    }

    public static byte[] encryptHmacSHA384(byte[] bArr, byte[] bArr2) {
        return hmacTemplate(bArr, bArr2, "HmacSHA384");
    }

    public static String encryptHmacSHA384ToString(String str, String str2) {
        return (str == null || str.length() == 0 || str2 == null || str2.length() == 0) ? "" : encryptHmacSHA384ToString(str.getBytes(), str2.getBytes());
    }

    public static byte[] encryptHmacSHA512(byte[] bArr, byte[] bArr2) {
        return hmacTemplate(bArr, bArr2, "HmacSHA512");
    }

    public static String encryptHmacSHA512ToString(String str, String str2) {
        return (str == null || str.length() == 0 || str2 == null || str2.length() == 0) ? "" : encryptHmacSHA512ToString(str.getBytes(), str2.getBytes());
    }

    public static byte[] encryptMD2(byte[] bArr) {
        return hashTemplate(bArr, "MD2");
    }

    public static String encryptMD2ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptMD2ToString(str.getBytes());
    }

    public static byte[] encryptMD5(byte[] bArr) {
        return hashTemplate(bArr, "MD5");
    }

    public static byte[] encryptMD5File(String str) {
        return encryptMD5File(UtilsBridge.isSpace(str) ? null : new File(str));
    }

    public static String encryptMD5File2String(String str) {
        return encryptMD5File2String(UtilsBridge.isSpace(str) ? null : new File(str));
    }

    public static String encryptMD5ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptMD5ToString(str.getBytes());
    }

    public static byte[] encryptRSA(byte[] bArr, byte[] bArr2, int i2, String str) {
        return rsaTemplate(bArr, bArr2, i2, str, true);
    }

    public static byte[] encryptRSA2Base64(byte[] bArr, byte[] bArr2, int i2, String str) {
        return UtilsBridge.base64Encode(encryptRSA(bArr, bArr2, i2, str));
    }

    public static String encryptRSA2HexString(byte[] bArr, byte[] bArr2, int i2, String str) {
        return UtilsBridge.bytes2HexString(encryptRSA(bArr, bArr2, i2, str));
    }

    public static byte[] encryptSHA1(byte[] bArr) {
        return hashTemplate(bArr, "SHA-1");
    }

    public static String encryptSHA1ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptSHA1ToString(str.getBytes());
    }

    public static byte[] encryptSHA224(byte[] bArr) {
        return hashTemplate(bArr, "SHA224");
    }

    public static String encryptSHA224ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptSHA224ToString(str.getBytes());
    }

    public static byte[] encryptSHA256(byte[] bArr) {
        return hashTemplate(bArr, "SHA-256");
    }

    public static String encryptSHA256ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptSHA256ToString(str.getBytes());
    }

    public static byte[] encryptSHA384(byte[] bArr) {
        return hashTemplate(bArr, "SHA-384");
    }

    public static String encryptSHA384ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptSHA384ToString(str.getBytes());
    }

    public static byte[] encryptSHA512(byte[] bArr) {
        return hashTemplate(bArr, "SHA-512");
    }

    public static String encryptSHA512ToString(String str) {
        return (str == null || str.length() == 0) ? "" : encryptSHA512ToString(str.getBytes());
    }

    public static byte[] hashTemplate(byte[] bArr, String str) throws NoSuchAlgorithmException {
        if (bArr != null && bArr.length > 0) {
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(str);
                messageDigest.update(bArr);
                return messageDigest.digest();
            } catch (NoSuchAlgorithmException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    private static byte[] hmacTemplate(byte[] bArr, byte[] bArr2, String str) throws NoSuchAlgorithmException, InvalidKeyException {
        if (bArr != null && bArr.length != 0 && bArr2 != null && bArr2.length != 0) {
            try {
                SecretKeySpec secretKeySpec = new SecretKeySpec(bArr2, str);
                Mac mac = Mac.getInstance(str);
                mac.init(secretKeySpec);
                return mac.doFinal(bArr);
            } catch (InvalidKeyException | NoSuchAlgorithmException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    private static byte[] joins(byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return bArr3;
    }

    public static byte[] rc4(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr.length == 0 || bArr2 == null) {
            return null;
        }
        if (bArr2.length < 1 || bArr2.length > 256) {
            throw new IllegalArgumentException("key must be between 1 and 256 bytes");
        }
        byte[] bArr3 = new byte[256];
        byte[] bArr4 = new byte[256];
        int length = bArr2.length;
        for (int i2 = 0; i2 < 256; i2++) {
            bArr3[i2] = (byte) i2;
            bArr4[i2] = bArr2[i2 % length];
        }
        int i3 = 0;
        for (int i4 = 0; i4 < 256; i4++) {
            byte b3 = bArr3[i4];
            i3 = (i3 + b3 + bArr4[i4]) & 255;
            byte b4 = bArr3[i3];
            bArr3[i3] = b3;
            bArr3[i4] = b4;
        }
        byte[] bArr5 = new byte[bArr.length];
        int i5 = 0;
        for (int i6 = 0; i6 < bArr.length; i6++) {
            i5 = (i5 + 1) & 255;
            byte b5 = bArr3[i5];
            i3 = (i3 + b5) & 255;
            byte b6 = bArr3[i3];
            bArr3[i3] = b5;
            bArr3[i5] = b6;
            bArr5[i6] = (byte) (bArr3[(b6 + bArr3[i3]) & 255] ^ bArr[i6]);
        }
        return bArr5;
    }

    private static byte[] rsaTemplate(byte[] bArr, byte[] bArr2, int i2, String str, boolean z2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        if (bArr != null && bArr.length != 0 && bArr2 != null && bArr2.length != 0) {
            try {
                KeyFactory keyFactory = Build.VERSION.SDK_INT < 28 ? KeyFactory.getInstance("RSA", "BC") : KeyFactory.getInstance("RSA");
                Key keyGeneratePublic = z2 ? keyFactory.generatePublic(new X509EncodedKeySpec(bArr2)) : keyFactory.generatePrivate(new PKCS8EncodedKeySpec(bArr2));
                if (keyGeneratePublic == null) {
                    return null;
                }
                Cipher cipher = Cipher.getInstance(str);
                cipher.init(z2 ? 1 : 2, keyGeneratePublic);
                int length = bArr.length;
                int i3 = i2 / 8;
                if (z2 && str.toLowerCase().endsWith("pkcs1padding")) {
                    i3 -= 11;
                }
                int i4 = length / i3;
                if (i4 <= 0) {
                    return cipher.doFinal(bArr);
                }
                byte[] bArrJoins = new byte[0];
                byte[] bArr3 = new byte[i3];
                int i5 = 0;
                for (int i6 = 0; i6 < i4; i6++) {
                    System.arraycopy(bArr, i5, bArr3, 0, i3);
                    bArrJoins = joins(bArrJoins, cipher.doFinal(bArr3));
                    i5 += i3;
                }
                if (i5 == length) {
                    return bArrJoins;
                }
                int i7 = length - i5;
                byte[] bArr4 = new byte[i7];
                System.arraycopy(bArr, i5, bArr4, 0, i7);
                return joins(bArrJoins, cipher.doFinal(bArr4));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    private static byte[] symmetricTemplate(byte[] bArr, byte[] bArr2, String str, String str2, byte[] bArr3, boolean z2) {
        SecretKey secretKeySpec;
        if (bArr != null && bArr.length != 0 && bArr2 != null && bArr2.length != 0) {
            try {
                if ("DES".equals(str)) {
                    secretKeySpec = SecretKeyFactory.getInstance(str).generateSecret(new DESKeySpec(bArr2));
                } else {
                    secretKeySpec = new SecretKeySpec(bArr2, str);
                }
                Cipher cipher = Cipher.getInstance(str2);
                int i2 = 1;
                if (bArr3 == null || bArr3.length == 0) {
                    if (!z2) {
                        i2 = 2;
                    }
                    cipher.init(i2, secretKeySpec);
                } else {
                    IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr3);
                    if (!z2) {
                        i2 = 2;
                    }
                    cipher.init(i2, secretKeySpec, ivParameterSpec);
                }
                return cipher.doFinal(bArr);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        return null;
    }

    public static String encryptHmacMD5ToString(byte[] bArr, byte[] bArr2) {
        return UtilsBridge.bytes2HexString(encryptHmacMD5(bArr, bArr2));
    }

    public static String encryptHmacSHA1ToString(byte[] bArr, byte[] bArr2) {
        return UtilsBridge.bytes2HexString(encryptHmacSHA1(bArr, bArr2));
    }

    public static String encryptHmacSHA224ToString(byte[] bArr, byte[] bArr2) {
        return UtilsBridge.bytes2HexString(encryptHmacSHA224(bArr, bArr2));
    }

    public static String encryptHmacSHA256ToString(byte[] bArr, byte[] bArr2) {
        return UtilsBridge.bytes2HexString(encryptHmacSHA256(bArr, bArr2));
    }

    public static String encryptHmacSHA384ToString(byte[] bArr, byte[] bArr2) {
        return UtilsBridge.bytes2HexString(encryptHmacSHA384(bArr, bArr2));
    }

    public static String encryptHmacSHA512ToString(byte[] bArr, byte[] bArr2) {
        return UtilsBridge.bytes2HexString(encryptHmacSHA512(bArr, bArr2));
    }

    public static String encryptMD2ToString(byte[] bArr) {
        return UtilsBridge.bytes2HexString(encryptMD2(bArr));
    }

    /* JADX WARN: Not initialized variable reg: 1, insn: 0x0048: MOVE (r0 I:??[OBJECT, ARRAY]) = (r1 I:??[OBJECT, ARRAY]), block:B:33:0x0048 */
    /* JADX WARN: Removed duplicated region for block: B:43:0x003e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] encryptMD5File(java.io.File r4) throws java.lang.Throwable {
        /*
            r0 = 0
            if (r4 != 0) goto L4
            return r0
        L4:
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            r1.<init>(r4)     // Catch: java.lang.Throwable -> L33 java.io.IOException -> L35 java.security.NoSuchAlgorithmException -> L37
            java.lang.String r4 = "MD5"
            java.security.MessageDigest r4 = java.security.MessageDigest.getInstance(r4)     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            java.security.DigestInputStream r2 = new java.security.DigestInputStream     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            r2.<init>(r1, r4)     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            r4 = 262144(0x40000, float:3.67342E-40)
            byte[] r4 = new byte[r4]     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
        L18:
            int r3 = r2.read(r4)     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            if (r3 > 0) goto L18
            java.security.MessageDigest r4 = r2.getMessageDigest()     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            byte[] r4 = r4.digest()     // Catch: java.io.IOException -> L2f java.security.NoSuchAlgorithmException -> L31 java.lang.Throwable -> L47
            r1.close()     // Catch: java.io.IOException -> L2a
            goto L2e
        L2a:
            r0 = move-exception
            r0.printStackTrace()
        L2e:
            return r4
        L2f:
            r4 = move-exception
            goto L39
        L31:
            r4 = move-exception
            goto L39
        L33:
            r4 = move-exception
            goto L49
        L35:
            r4 = move-exception
            goto L38
        L37:
            r4 = move-exception
        L38:
            r1 = r0
        L39:
            r4.printStackTrace()     // Catch: java.lang.Throwable -> L47
            if (r1 == 0) goto L46
            r1.close()     // Catch: java.io.IOException -> L42
            goto L46
        L42:
            r4 = move-exception
            r4.printStackTrace()
        L46:
            return r0
        L47:
            r4 = move-exception
            r0 = r1
        L49:
            if (r0 == 0) goto L53
            r0.close()     // Catch: java.io.IOException -> L4f
            goto L53
        L4f:
            r0 = move-exception
            r0.printStackTrace()
        L53:
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.EncryptUtils.encryptMD5File(java.io.File):byte[]");
    }

    public static String encryptMD5File2String(File file) {
        return UtilsBridge.bytes2HexString(encryptMD5File(file));
    }

    public static String encryptMD5ToString(String str, String str2) {
        if (str == null && str2 == null) {
            return "";
        }
        if (str2 == null) {
            return UtilsBridge.bytes2HexString(encryptMD5(str.getBytes()));
        }
        if (str == null) {
            return UtilsBridge.bytes2HexString(encryptMD5(str2.getBytes()));
        }
        return UtilsBridge.bytes2HexString(encryptMD5((str + str2).getBytes()));
    }

    public static String encryptSHA1ToString(byte[] bArr) {
        return UtilsBridge.bytes2HexString(encryptSHA1(bArr));
    }

    public static String encryptSHA224ToString(byte[] bArr) {
        return UtilsBridge.bytes2HexString(encryptSHA224(bArr));
    }

    public static String encryptSHA256ToString(byte[] bArr) {
        return UtilsBridge.bytes2HexString(encryptSHA256(bArr));
    }

    public static String encryptSHA384ToString(byte[] bArr) {
        return UtilsBridge.bytes2HexString(encryptSHA384(bArr));
    }

    public static String encryptSHA512ToString(byte[] bArr) {
        return UtilsBridge.bytes2HexString(encryptSHA512(bArr));
    }

    public static String encryptMD5ToString(byte[] bArr) {
        return UtilsBridge.bytes2HexString(encryptMD5(bArr));
    }

    public static String encryptMD5ToString(byte[] bArr, byte[] bArr2) {
        if (bArr == null && bArr2 == null) {
            return "";
        }
        if (bArr2 == null) {
            return UtilsBridge.bytes2HexString(encryptMD5(bArr));
        }
        if (bArr == null) {
            return UtilsBridge.bytes2HexString(encryptMD5(bArr2));
        }
        byte[] bArr3 = new byte[bArr.length + bArr2.length];
        System.arraycopy(bArr, 0, bArr3, 0, bArr.length);
        System.arraycopy(bArr2, 0, bArr3, bArr.length, bArr2.length);
        return UtilsBridge.bytes2HexString(encryptMD5(bArr3));
    }
}
