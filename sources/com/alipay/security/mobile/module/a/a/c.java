package com.alipay.security.mobile.module.a.a;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import net.lingala.zip4j.crypto.PBKDF2.BinTools;

/* loaded from: classes2.dex */
public final class c {

    /* renamed from: a, reason: collision with root package name */
    private static String f3416a = new String("idnjfhncnsfuobcnt847y929o449u474w7j3h22aoddc98euk#%&&)*&^%#");

    public static String a() {
        String str = new String();
        for (int i2 = 0; i2 < f3416a.length() - 1; i2 += 4) {
            str = str + f3416a.charAt(i2);
        }
        return str;
    }

    public static String a(String str, String str2) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException, ShortBufferException {
        try {
            try {
                PBEKeySpec pBEKeySpecA = a(str);
                byte[] bytes = str2.getBytes();
                SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(pBEKeySpecA).getEncoded(), "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(1, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
                byte[] salt = pBEKeySpecA.getSalt();
                ByteBuffer byteBufferAllocate = ByteBuffer.allocate(salt.length + cipher.getOutputSize(bytes.length));
                byteBufferAllocate.put(salt);
                cipher.doFinal(ByteBuffer.wrap(bytes), byteBufferAllocate);
                return b(byteBufferAllocate.array());
            } catch (Exception unused) {
                return b(a(a(str.getBytes()), str2.getBytes()));
            }
        } catch (Exception unused2) {
            return null;
        }
    }

    private static PBEKeySpec a(String str) throws IllegalAccessException, NoSuchMethodException, InstantiationException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        Class<?> cls = Class.forName(new String(a.a("amF2YS5zZWN1cml0eS5TZWN1cmVSYW5kb20=")));
        Object objNewInstance = cls.newInstance();
        byte[] bArr = new byte[16];
        Method method = cls.getMethod("nextBytes", bArr.getClass());
        method.setAccessible(true);
        method.invoke(objNewInstance, bArr);
        return new PBEKeySpec(str.toCharArray(), bArr, 10, 128);
    }

    private static byte[] a(byte[] bArr) throws IllegalAccessException, NoSuchMethodException, NoSuchAlgorithmException, ClassNotFoundException, SecurityException, IllegalArgumentException, InvocationTargetException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        Class<?> cls = Class.forName(new String(a.a("amF2YS5zZWN1cml0eS5TZWN1cmVSYW5kb20=")));
        Object objInvoke = cls.getMethod("getInstance", String.class, String.class).invoke(null, "SHA1PRNG", "Crypto");
        Method method = cls.getMethod("setSeed", bArr.getClass());
        method.setAccessible(true);
        method.invoke(objInvoke, bArr);
        KeyGenerator.class.getMethod("init", Integer.TYPE, cls).invoke(keyGenerator, 128, objInvoke);
        return keyGenerator.generateKey().getEncoded();
    }

    private static byte[] a(byte[] bArr, byte[] bArr2) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(1, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
            return cipher.doFinal(bArr2);
        } catch (Throwable unused) {
            return null;
        }
    }

    public static String b(String str, String str2) throws Exception {
        String str3;
        byte[] bArrDoFinal;
        try {
            PBEKeySpec pBEKeySpecA = a(str);
            byte[] bArrB = b(str2);
            if (bArrB.length <= 16) {
                bArrDoFinal = null;
            } else {
                SecretKeySpec secretKeySpec = new SecretKeySpec(SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(new PBEKeySpec(pBEKeySpecA.getPassword(), Arrays.copyOf(bArrB, 16), 10, 128)).getEncoded(), "AES");
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(2, secretKeySpec, new IvParameterSpec(new byte[cipher.getBlockSize()]));
                bArrDoFinal = cipher.doFinal(bArrB, 16, bArrB.length - 16);
            }
        } catch (Exception unused) {
        }
        if (bArrDoFinal == null) {
            throw new Exception();
        }
        String str4 = new String(bArrDoFinal);
        if (com.alipay.security.mobile.module.a.a.c(str4)) {
            return str4;
        }
        try {
            byte[] bArrA = a(str.getBytes());
            byte[] bArrB2 = b(str2);
            SecretKeySpec secretKeySpec2 = new SecretKeySpec(bArrA, "AES");
            Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher2.init(2, secretKeySpec2, new IvParameterSpec(new byte[cipher2.getBlockSize()]));
            str3 = new String(cipher2.doFinal(bArrB2));
        } catch (Exception unused2) {
        }
        if (com.alipay.security.mobile.module.a.a.c(str3)) {
            return str3;
        }
        return null;
    }

    private static String b(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b3 : bArr) {
            stringBuffer.append(BinTools.hex.charAt((b3 >> 4) & 15));
            stringBuffer.append(BinTools.hex.charAt(b3 & 15));
        }
        return stringBuffer.toString();
    }

    private static byte[] b(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = Integer.valueOf(str.substring(i3, i3 + 2), 16).byteValue();
        }
        return bArr;
    }
}
