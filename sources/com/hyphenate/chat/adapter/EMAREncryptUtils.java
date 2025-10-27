package com.hyphenate.chat.adapter;

import android.util.Base64;
import com.hyphenate.util.EMLog;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes4.dex */
public class EMAREncryptUtils {
    public static final String TAG = "EMAREncryptUtils";
    private Cipher gcmEncryptCipher;

    public enum B64_ENCODE_FLAG {
        ONESDK_B64_DEFAULT,
        ONESDK_B64_NO_WRAP
    }

    public static byte[] b64Decode(String str) {
        return Base64.decode(str, 0);
    }

    public static byte[] b64Decode(String str, int i2) {
        return Base64.decode(str, i2 == B64_ENCODE_FLAG.ONESDK_B64_NO_WRAP.ordinal() ? 2 : 0);
    }

    public static String b64Encode(byte[] bArr, int i2) {
        return Base64.encodeToString(bArr, 0, bArr.length, i2 == B64_ENCODE_FLAG.ONESDK_B64_NO_WRAP.ordinal() ? 2 : 0);
    }

    public static byte[] encryptByRSAPublicKey(String str, byte[] bArr, AtomicBoolean atomicBoolean) throws BadPaddingException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            PublicKey publicKeyGeneratePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replace("\n", "").getBytes(), 2)));
            EMLog.d(TAG, "publicKey.getFormat:" + publicKeyGeneratePublic.getFormat());
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(1, publicKeyGeneratePublic);
            byte[] bArrDoFinal = cipher.doFinal(bArr);
            atomicBoolean.set(true);
            return bArrDoFinal;
        } catch (Exception e2) {
            e2.printStackTrace();
            EMLog.d(TAG, Arrays.toString(e2.getStackTrace()));
            return null;
        }
    }

    public static byte[] generateAESKey(int i2) throws NoSuchAlgorithmException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(i2 * 8);
            return keyGenerator.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static byte[] generateAESKey256() throws NoSuchAlgorithmException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            return keyGenerator.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String messageDigest(int i2, byte[] bArr) throws NoSuchAlgorithmException {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance(i2 == 1 ? "SHA-256" : "MD5");
        } catch (Exception e2) {
            e2.printStackTrace();
            messageDigest = null;
        }
        byte[] bArrDigest = messageDigest != null ? messageDigest.digest(bArr) : null;
        StringBuffer stringBuffer = new StringBuffer();
        if (bArrDigest != null) {
            for (byte b3 : bArrDigest) {
                int i3 = b3 & 255;
                if (i3 < 16) {
                    stringBuffer.append("0");
                }
                stringBuffer.append(Integer.toHexString(i3));
            }
        }
        return stringBuffer.toString();
    }

    public String aesGcmDecrypt(String str, byte[] bArr, int i2) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            ByteBuffer byteBufferWrap = ByteBuffer.wrap(b64Decode(str, i2));
            byte[] bArr2 = new byte[byteBufferWrap.get()];
            byteBufferWrap.get(bArr2);
            byte[] bArr3 = new byte[byteBufferWrap.remaining()];
            byteBufferWrap.get(bArr3);
            GCMParameterSpec gCMParameterSpec = new GCMParameterSpec(128, bArr2);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(2, new SecretKeySpec(bArr, "AES"), gCMParameterSpec);
            return new String(cipher.doFinal(bArr3));
        } catch (Exception e2) {
            e2.printStackTrace();
            EMLog.d(TAG, Arrays.toString(e2.getStackTrace()));
            return null;
        }
    }

    public String aesGcmEncrypt(String str, int i2) throws BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        try {
            byte[] bArrDoFinal = this.gcmEncryptCipher.doFinal(str.getBytes("UTF-8"));
            byte[] iv = this.gcmEncryptCipher.getIV();
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(iv.length + 1 + bArrDoFinal.length);
            byteBufferAllocate.put((byte) iv.length);
            byteBufferAllocate.put(iv);
            byteBufferAllocate.put(bArrDoFinal);
            return b64Encode(byteBufferAllocate.array(), i2);
        } catch (Exception e2) {
            e2.printStackTrace();
            EMLog.d(TAG, Arrays.toString(e2.getStackTrace()));
            return null;
        }
    }

    public void initAESgcm(byte[] bArr) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
            byte[] bArr2 = new byte[12];
            new SecureRandom().nextBytes(bArr2);
            GCMParameterSpec gCMParameterSpec = new GCMParameterSpec(128, bArr2);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            this.gcmEncryptCipher = cipher;
            cipher.init(1, secretKeySpec, gCMParameterSpec);
            EMLog.d("encrypt", "init for AES gcm");
        } catch (Exception e2) {
            e2.printStackTrace();
            EMLog.d(TAG, Arrays.toString(e2.getStackTrace()));
        }
    }
}
