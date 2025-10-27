package cn.hutool.crypto.symmetric;

/* loaded from: classes.dex */
public class Vigenere {
    public static String decrypt(CharSequence charSequence, CharSequence charSequence2) {
        int length = charSequence.length();
        int length2 = charSequence2.length();
        char[] cArr = new char[length];
        for (int i2 = 0; i2 < length; i2++) {
            for (int i3 = 0; i3 < length2; i3++) {
                int i4 = (i2 * length2) + i3;
                if (i4 < length) {
                    int iCharAt = charSequence.charAt(i4) - charSequence2.charAt(i3);
                    if (iCharAt >= 0) {
                        cArr[i4] = (char) ((iCharAt % 95) + 32);
                    } else {
                        cArr[i4] = (char) (((iCharAt + 95) % 95) + 32);
                    }
                }
            }
        }
        return String.valueOf(cArr);
    }

    public static String encrypt(CharSequence charSequence, CharSequence charSequence2) {
        int length = charSequence.length();
        int length2 = charSequence2.length();
        char[] cArr = new char[length];
        for (int i2 = 0; i2 < (length / length2) + 1; i2++) {
            for (int i3 = 0; i3 < length2; i3++) {
                int i4 = (i2 * length2) + i3;
                if (i4 < length) {
                    cArr[i4] = (char) ((((charSequence.charAt(i4) + charSequence2.charAt(i3)) - 64) % 95) + 32);
                }
            }
        }
        return String.valueOf(cArr);
    }
}
