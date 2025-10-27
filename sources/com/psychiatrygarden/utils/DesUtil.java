package com.psychiatrygarden.utils;

import com.google.common.base.Ascii;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes6.dex */
public class DesUtil {
    private static final String HEX = "0123456789ABCDEF";
    private static byte[] iv = {Ascii.DC2, TarConstants.LF_BLK, 86, TarConstants.LF_PAX_EXTENDED_HEADER_LC, -112, -85, -51, -17};

    private static void appendHex(StringBuffer sb, byte b3) {
        sb.append("0123456789ABCDEF".charAt((b3 >> 4) & 15));
        sb.append("0123456789ABCDEF".charAt(b3 & 15));
    }

    public static String decode(String key, String aaa) throws Exception {
        try {
            byte[] bArrDecode = Base64Utils.decode(aaa);
            SecretKey secretKeyGenerateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key.getBytes("utf-8")));
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(2, secretKeyGenerateSecret, new IvParameterSpec(iv));
            return new String(cipher.doFinal(bArrDecode), "utf-8");
        } catch (Exception e2) {
            throw new Exception(e2);
        }
    }

    public static String encode(String key, String data) throws Exception {
        try {
            SecretKey secretKeyGenerateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(key.getBytes("utf-8")));
            Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
            cipher.init(1, secretKeyGenerateSecret, new IvParameterSpec(iv));
            return Base64Utils.encode(cipher.doFinal(data.getBytes("utf-8")));
        } catch (Exception e2) {
            throw new Exception(e2);
        }
    }

    public static String toHex(byte[] buf) {
        if (buf == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(buf.length * 2);
        for (byte b3 : buf) {
            appendHex(stringBuffer, b3);
        }
        return stringBuffer.toString();
    }
}
