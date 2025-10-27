package cn.hutool.core.lang.id;

import cn.hutool.core.util.RandomUtil;
import java.security.SecureRandom;
import java.util.Random;

/* loaded from: classes.dex */
public class NanoId {
    public static final int DEFAULT_SIZE = 21;
    private static final SecureRandom DEFAULT_NUMBER_GENERATOR = RandomUtil.getSecureRandom();
    private static final char[] DEFAULT_ALPHABET = "_-0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static String randomNanoId() {
        return randomNanoId(21);
    }

    public static String randomNanoId(int i2) {
        return randomNanoId(null, null, i2);
    }

    public static String randomNanoId(Random random, char[] cArr, int i2) {
        if (random == null) {
            random = DEFAULT_NUMBER_GENERATOR;
        }
        if (cArr == null) {
            cArr = DEFAULT_ALPHABET;
        }
        if (cArr.length == 0 || cArr.length >= 256) {
            throw new IllegalArgumentException("Alphabet must contain between 1 and 255 symbols.");
        }
        if (i2 > 0) {
            int iFloor = (2 << ((int) Math.floor(Math.log(cArr.length - 1) / Math.log(2.0d)))) - 1;
            int iCeil = (int) Math.ceil(((iFloor * 1.6d) * i2) / cArr.length);
            StringBuilder sb = new StringBuilder();
            while (true) {
                byte[] bArr = new byte[iCeil];
                random.nextBytes(bArr);
                for (int i3 = 0; i3 < iCeil; i3++) {
                    int i4 = bArr[i3] & iFloor;
                    if (i4 < cArr.length) {
                        sb.append(cArr[i4]);
                        if (sb.length() == i2) {
                            return sb.toString();
                        }
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Size must be greater than zero.");
        }
    }
}
