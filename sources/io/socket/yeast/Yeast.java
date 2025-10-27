package io.socket.yeast;

import cn.hutool.core.text.StrPool;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes8.dex */
public final class Yeast {
    private static char[] alphabet;
    private static int length;
    private static Map<Character, Integer> map;
    private static String prev;
    private static int seed;

    static {
        char[] charArray = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz-_".toCharArray();
        alphabet = charArray;
        length = charArray.length;
        seed = 0;
        map = new HashMap(length);
        for (int i2 = 0; i2 < length; i2++) {
            map.put(Character.valueOf(alphabet[i2]), Integer.valueOf(i2));
        }
    }

    private Yeast() {
    }

    public static long decode(String str) {
        long jIntValue = 0;
        for (int i2 = 0; i2 < str.toCharArray().length; i2++) {
            jIntValue = (jIntValue * length) + map.get(Character.valueOf(r7[i2])).intValue();
        }
        return jIntValue;
    }

    public static String encode(long j2) {
        StringBuilder sb = new StringBuilder();
        do {
            sb.insert(0, alphabet[(int) (j2 % length)]);
            j2 /= length;
        } while (j2 > 0);
        return sb.toString();
    }

    public static String yeast() {
        String strEncode = encode(new Date().getTime());
        if (!strEncode.equals(prev)) {
            seed = 0;
            prev = strEncode;
            return strEncode;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(strEncode);
        sb.append(StrPool.DOT);
        int i2 = seed;
        seed = i2 + 1;
        sb.append(encode(i2));
        return sb.toString();
    }
}
