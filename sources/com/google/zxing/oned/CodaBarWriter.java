package com.google.zxing.oned;

import cn.hutool.core.text.CharPool;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class CodaBarWriter extends OneDimensionalCodeWriter {
    private static final char[] START_END_CHARS = {'A', 'B', 'C', 'D'};
    private static final char[] ALT_START_END_CHARS = {'T', 'N', '*', 'E'};
    private static final char[] CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED = {'/', ':', '+', '.'};

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) {
        int i2;
        if (str.length() < 2) {
            throw new IllegalArgumentException("Codabar should start/end with start/stop symbols");
        }
        char upperCase = Character.toUpperCase(str.charAt(0));
        char upperCase2 = Character.toUpperCase(str.charAt(str.length() - 1));
        char[] cArr = START_END_CHARS;
        boolean z2 = CodaBarReader.arrayContains(cArr, upperCase) && CodaBarReader.arrayContains(cArr, upperCase2);
        char[] cArr2 = ALT_START_END_CHARS;
        boolean z3 = CodaBarReader.arrayContains(cArr2, upperCase) && CodaBarReader.arrayContains(cArr2, upperCase2);
        if (!z2 && !z3) {
            throw new IllegalArgumentException("Codabar should start/end with " + Arrays.toString(cArr) + ", or start/end with " + Arrays.toString(cArr2));
        }
        int i3 = 20;
        for (int i4 = 1; i4 < str.length() - 1; i4++) {
            if (Character.isDigit(str.charAt(i4)) || str.charAt(i4) == '-' || str.charAt(i4) == '$') {
                i3 += 9;
            } else {
                if (!CodaBarReader.arrayContains(CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED, str.charAt(i4))) {
                    throw new IllegalArgumentException("Cannot encode : '" + str.charAt(i4) + CharPool.SINGLE_QUOTE);
                }
                i3 += 10;
            }
        }
        boolean[] zArr = new boolean[i3 + (str.length() - 1)];
        int i5 = 0;
        for (int i6 = 0; i6 < str.length(); i6++) {
            char upperCase3 = Character.toUpperCase(str.charAt(i6));
            if (i6 == 0 || i6 == str.length() - 1) {
                if (upperCase3 == '*') {
                    upperCase3 = 'C';
                } else if (upperCase3 == 'E') {
                    upperCase3 = 'D';
                } else if (upperCase3 == 'N') {
                    upperCase3 = 'B';
                } else if (upperCase3 == 'T') {
                    upperCase3 = 'A';
                }
            }
            int i7 = 0;
            while (true) {
                char[] cArr3 = CodaBarReader.ALPHABET;
                if (i7 >= cArr3.length) {
                    i2 = 0;
                    break;
                }
                if (upperCase3 == cArr3[i7]) {
                    i2 = CodaBarReader.CHARACTER_ENCODINGS[i7];
                    break;
                }
                i7++;
            }
            int i8 = 0;
            int i9 = 0;
            boolean z4 = true;
            while (i8 < 7) {
                zArr[i5] = z4;
                i5++;
                if (((i2 >> (6 - i8)) & 1) == 0 || i9 == 1) {
                    z4 = !z4;
                    i8++;
                    i9 = 0;
                } else {
                    i9++;
                }
            }
            if (i6 < str.length() - 1) {
                zArr[i5] = false;
                i5++;
            }
        }
        return zArr;
    }
}
