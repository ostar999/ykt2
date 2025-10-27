package cn.hutool.core.text;

/* loaded from: classes.dex */
public class ASCIIStrCache {
    private static final int ASCII_LENGTH = 128;
    private static final String[] CACHE = new String[128];

    static {
        for (char c3 = 0; c3 < 128; c3 = (char) (c3 + 1)) {
            CACHE[c3] = String.valueOf(c3);
        }
    }

    public static String toString(char c3) {
        return c3 < 128 ? CACHE[c3] : String.valueOf(c3);
    }
}
