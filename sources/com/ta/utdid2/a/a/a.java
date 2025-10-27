package com.ta.utdid2.a.a;

import com.google.common.base.Ascii;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import okio.Utf8;
import org.apache.commons.compress.archivers.tar.TarConstants;

/* loaded from: classes6.dex */
public class a {
    private static byte[] a() throws Exception {
        return com.ta.a.e.g.b(new byte[]{33, TarConstants.LF_GNUTYPE_SPARSE, -50, -89, -84, -114, 80, 99, 10, Utf8.REPLACEMENT_BYTE, 22, -65, -11, Ascii.RS, 101, -118});
    }

    private static byte[] b(byte[] bArr, byte[] bArr2) throws Exception {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(2, secretKeySpec, new IvParameterSpec(b()));
        return cipher.doFinal(bArr2);
    }

    public static String e(String str) {
        try {
            return new String(b(a(), a(str)));
        } catch (Exception unused) {
            return null;
        }
    }

    private static byte[] a(String str) {
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = Integer.valueOf(str.substring(i3, i3 + 2), 16).byteValue();
        }
        return bArr;
    }

    private static byte[] b() {
        try {
            byte[] bArrDecode = b.decode("IUQSvE6r1TfFPdPEjfklLw==".getBytes("UTF-8"), 2);
            if (bArrDecode != null) {
                return com.ta.a.e.g.b(bArrDecode);
            }
        } catch (Exception unused) {
        }
        return new byte[16];
    }
}
