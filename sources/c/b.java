package c;

import android.util.Base64;
import java.io.UnsupportedEncodingException;

/* loaded from: classes.dex */
public class b {

    /* renamed from: a, reason: collision with root package name */
    public static final String f2216a = "Hy7Gi*cQPMd19XbgRsMno0dz4^sb#sQ0Unx$s!a158ScTuxPk8n6BksTcB$sc^aP";

    public static String a(String str) {
        try {
            return a(str, f2216a);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String b(String str) {
        try {
            return b(str, f2216a);
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
            return "";
        }
    }

    public static String a(String str, String str2) throws UnsupportedEncodingException {
        byte[] bArrDecode = Base64.decode(str.getBytes("utf-8"), 3);
        int length = bArrDecode.length;
        byte[] bArr = new byte[length];
        byte[] bytes = str2.getBytes();
        for (int i2 = 0; i2 < bArrDecode.length; i2++) {
            bArr[i2] = (byte) (bArrDecode[i2] - bytes[i2 % bytes.length]);
        }
        return new String(bArr, 0, length, "utf-8");
    }

    public static String b(String str, String str2) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes("utf-8");
        byte[] bArr = new byte[bytes.length];
        byte[] bytes2 = str2.getBytes();
        for (int i2 = 0; i2 < bytes.length; i2++) {
            bArr[i2] = (byte) (bytes[i2] + bytes2[i2 % bytes2.length]);
        }
        return new String(Base64.encode(bArr, 3));
    }
}
