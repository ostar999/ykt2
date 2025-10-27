package c;

import java.io.ByteArrayOutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public class k {
    public static String a(String str, String str2, String str3, String str4, int i2, int i3) throws Exception {
        String strSubstring = ("0000000000" + Integer.toString(i2)).substring(Integer.toString(i2).length());
        String strSubstring2 = ("00000000" + Integer.toHexString(i3)).substring(Integer.toHexString(i3).length());
        return String.format("%s%s%s", a(str, str2, str3, str4, strSubstring, strSubstring2), strSubstring, strSubstring2);
    }

    public static String a(String str, String str2, String str3, String str4, String str5, String str6) throws Exception {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(str.getBytes());
        byteArrayOutputStream.write(str2.getBytes());
        byteArrayOutputStream.write(str5.getBytes());
        byteArrayOutputStream.write(str6.getBytes());
        byteArrayOutputStream.write(str4.getBytes());
        return a(a(str3, byteArrayOutputStream.toByteArray()));
    }

    public static byte[] a(String str, byte[] bArr) throws NoSuchAlgorithmException, InvalidKeyException {
        return a(str.getBytes(), bArr);
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKeySpec);
        return mac.doFinal(bArr2);
    }

    public static String a(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b3 : bArr) {
            sb.append(String.format("%02x", Byte.valueOf(b3)));
        }
        return sb.toString();
    }
}
