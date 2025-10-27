package com.aliyun.vod.common.utils;

import android.content.Context;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* loaded from: classes2.dex */
public class SignatureUtils {
    private static final char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getSingInfo(Context context) {
        try {
            return hexdigest(context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures[0].toByteArray());
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }

    public static String hexdigest(byte[] bArr) throws NoSuchAlgorithmException {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bArr);
            byte[] bArrDigest = messageDigest.digest();
            char[] cArr = new char[32];
            int i2 = 0;
            for (int i3 = 0; i3 < 16; i3++) {
                byte b3 = bArrDigest[i3];
                int i4 = i2 + 1;
                char[] cArr2 = hexDigits;
                cArr[i2] = cArr2[(b3 >>> 4) & 15];
                i2 = i4 + 1;
                cArr[i4] = cArr2[b3 & 15];
            }
            return new String(cArr);
        } catch (Exception e2) {
            e2.printStackTrace();
            return null;
        }
    }
}
