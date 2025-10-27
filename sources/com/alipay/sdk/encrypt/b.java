package com.alipay.sdk.encrypt;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes2.dex */
public final class b {
    private static String a(String str, String str2) {
        return a(1, str, str2);
    }

    private static String b(String str, String str2) {
        return a(2, str, str2);
    }

    public static String a(int i2, String str, String str2) throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeyException {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str2.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(i2, secretKeySpec);
            byte[] bArrDoFinal = cipher.doFinal(i2 == 2 ? a.a(str) : str.getBytes("UTF-8"));
            return i2 == 2 ? new String(bArrDoFinal) : a.a(bArrDoFinal);
        } catch (Exception unused) {
            return null;
        }
    }
}
