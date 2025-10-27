package cn.hutool.core.util;

import com.easefun.polyv.mediasdk.player.IjkMediaMeta;

/* loaded from: classes.dex */
public class RadixUtil {
    public static final String RADIXS_34 = "0123456789ABCDEFGHJKLMNPQRSTUVWXYZ";
    public static final String RADIXS_59 = "0123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ";
    public static final String RADIXS_SHUFFLE_34 = "H3UM16TDFPSBZJ90CW28QYRE45AXKNGV7L";
    public static final String RADIXS_SHUFFLE_59 = "vh9wGkfK8YmqbsoENP3764SeCX0dVzrgy1HRtpnTaLjJW2xQiZAcBMUFDu5";

    public static long decode(String str, String str2) {
        int length = str.length();
        long jIndexOf = 0;
        for (int i2 = 0; i2 < str2.toCharArray().length; i2++) {
            jIndexOf = (jIndexOf * length) + str.indexOf(r9[i2]);
        }
        return jIndexOf;
    }

    public static int decodeToInt(String str, String str2) {
        return (int) decode(str, str2);
    }

    public static String encode(String str, int i2) {
        return encode(str, i2 >= 0 ? i2 : IjkMediaMeta.AV_CH_WIDE_RIGHT - ((~i2) + 1), 32);
    }

    public static String encode(String str, long j2) {
        if (j2 >= 0) {
            return encode(str, j2, 64);
        }
        throw new RuntimeException("暂不支持负数！");
    }

    private static String encode(String str, long j2, int i2) {
        if (str.length() >= 2) {
            int length = str.length();
            char[] cArr = new char[i2];
            int i3 = i2;
            do {
                i3--;
                long j3 = length;
                cArr[i3] = str.charAt((int) (j2 % j3));
                j2 /= j3;
            } while (j2 > 0);
            return new String(cArr, i3, i2 - i3);
        }
        throw new RuntimeException("自定义进制最少两个字符哦！");
    }
}
