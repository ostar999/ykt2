package com.huawei.hms.hatool;

import android.util.Pair;
import com.huawei.secure.android.common.encrypt.aes.AesCbc;
import com.huawei.secure.android.common.encrypt.utils.HexUtil;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/* loaded from: classes4.dex */
public class d {

    /* renamed from: a, reason: collision with root package name */
    public static final Charset f7714a = Charset.forName("UTF-8");

    public static Pair<byte[], String> a(String str) {
        if (str == null || str.length() < 32) {
            return new Pair<>(new byte[0], str);
        }
        String strSubstring = str.substring(0, 32);
        return new Pair<>(HexUtil.hexStr2ByteArray(strSubstring), str.substring(32));
    }

    public static String a(String str, String str2) {
        Pair<byte[], String> pairA = a(str);
        return new String(AesCbc.decrypt(HexUtil.hexStr2ByteArray((String) pairA.second), HexUtil.hexStr2ByteArray(str2), (byte[]) pairA.first), f7714a);
    }

    public static String a(byte[] bArr, String str) throws UnsupportedEncodingException {
        String str2;
        if (bArr == null || bArr.length == 0 || str == null) {
            str2 = "cbc encrypt(byte) param is not right";
        } else {
            byte[] bArrHexStr2ByteArray = HexUtil.hexStr2ByteArray(str);
            if (bArrHexStr2ByteArray.length >= 16) {
                return HexUtil.byteArray2HexStr(AesCbc.encrypt(bArr, bArrHexStr2ByteArray));
            }
            str2 = "key length is not right";
        }
        y.b("AesCipher", str2);
        return "";
    }

    public static String b(String str, String str2) {
        return HexUtil.byteArray2HexStr(AesCbc.encrypt(str.getBytes(f7714a), HexUtil.hexStr2ByteArray(str2)));
    }
}
