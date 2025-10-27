package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes4.dex */
public final class CodaBarReader extends OneDReader {
    private static final int MAX_ACCEPTABLE = 512;
    private static final int MIN_CHARACTER_LENGTH = 3;
    private static final int PADDING = 384;
    private static final String ALPHABET_STRING = "0123456789-$:/.+ABCD";
    static final char[] ALPHABET = ALPHABET_STRING.toCharArray();
    static final int[] CHARACTER_ENCODINGS = {3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14};
    private static final char[] STARTEND_ENCODING = {'A', 'B', 'C', 'D'};
    private final StringBuilder decodeRowResult = new StringBuilder(20);
    private int[] counters = new int[80];
    private int counterLength = 0;

    public static boolean arrayContains(char[] cArr, char c3) {
        if (cArr != null) {
            for (char c4 : cArr) {
                if (c4 == c3) {
                    return true;
                }
            }
        }
        return false;
    }

    private void counterAppend(int i2) {
        int[] iArr = this.counters;
        int i3 = this.counterLength;
        iArr[i3] = i2;
        int i4 = i3 + 1;
        this.counterLength = i4;
        if (i4 >= iArr.length) {
            int[] iArr2 = new int[i4 * 2];
            System.arraycopy(iArr, 0, iArr2, 0, i4);
            this.counters = iArr2;
        }
    }

    private int findStartPattern() throws NotFoundException {
        for (int i2 = 1; i2 < this.counterLength; i2 += 2) {
            int narrowWidePattern = toNarrowWidePattern(i2);
            if (narrowWidePattern != -1 && arrayContains(STARTEND_ENCODING, ALPHABET[narrowWidePattern])) {
                int i3 = 0;
                for (int i4 = i2; i4 < i2 + 7; i4++) {
                    i3 += this.counters[i4];
                }
                if (i2 == 1 || this.counters[i2 - 1] >= i3 / 2) {
                    return i2;
                }
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private void setCounters(BitArray bitArray) throws NotFoundException {
        int i2 = 0;
        this.counterLength = 0;
        int nextUnset = bitArray.getNextUnset(0);
        int size = bitArray.getSize();
        if (nextUnset >= size) {
            throw NotFoundException.getNotFoundInstance();
        }
        boolean z2 = true;
        while (nextUnset < size) {
            if (bitArray.get(nextUnset) ^ z2) {
                i2++;
            } else {
                counterAppend(i2);
                z2 = !z2;
                i2 = 1;
            }
            nextUnset++;
        }
        counterAppend(i2);
    }

    private int toNarrowWidePattern(int i2) {
        int i3 = i2 + 7;
        if (i3 >= this.counterLength) {
            return -1;
        }
        int[] iArr = this.counters;
        int i4 = Integer.MAX_VALUE;
        int i5 = 0;
        int i6 = Integer.MAX_VALUE;
        int i7 = 0;
        for (int i8 = i2; i8 < i3; i8 += 2) {
            int i9 = iArr[i8];
            if (i9 < i6) {
                i6 = i9;
            }
            if (i9 > i7) {
                i7 = i9;
            }
        }
        int i10 = (i6 + i7) / 2;
        int i11 = 0;
        for (int i12 = i2 + 1; i12 < i3; i12 += 2) {
            int i13 = iArr[i12];
            if (i13 < i4) {
                i4 = i13;
            }
            if (i13 > i11) {
                i11 = i13;
            }
        }
        int i14 = (i4 + i11) / 2;
        int i15 = 128;
        int i16 = 0;
        for (int i17 = 0; i17 < 7; i17++) {
            i15 >>= 1;
            if (iArr[i2 + i17] > ((i17 & 1) == 0 ? i10 : i14)) {
                i16 |= i15;
            }
        }
        while (true) {
            int[] iArr2 = CHARACTER_ENCODINGS;
            if (i5 >= iArr2.length) {
                return -1;
            }
            if (iArr2[i5] == i16) {
                return i5;
            }
            i5++;
        }
    }

    @Override // com.google.zxing.oned.OneDReader
    public Result decodeRow(int i2, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException {
        Arrays.fill(this.counters, 0);
        setCounters(bitArray);
        int iFindStartPattern = findStartPattern();
        this.decodeRowResult.setLength(0);
        int i3 = iFindStartPattern;
        do {
            int narrowWidePattern = toNarrowWidePattern(i3);
            if (narrowWidePattern == -1) {
                throw NotFoundException.getNotFoundInstance();
            }
            this.decodeRowResult.append((char) narrowWidePattern);
            i3 += 8;
            if (this.decodeRowResult.length() > 1 && arrayContains(STARTEND_ENCODING, ALPHABET[narrowWidePattern])) {
                break;
            }
        } while (i3 < this.counterLength);
        int i4 = i3 - 1;
        int i5 = this.counters[i4];
        int i6 = 0;
        for (int i7 = -8; i7 < -1; i7++) {
            i6 += this.counters[i3 + i7];
        }
        if (i3 < this.counterLength && i5 < i6 / 2) {
            throw NotFoundException.getNotFoundInstance();
        }
        validatePattern(iFindStartPattern);
        for (int i8 = 0; i8 < this.decodeRowResult.length(); i8++) {
            StringBuilder sb = this.decodeRowResult;
            sb.setCharAt(i8, ALPHABET[sb.charAt(i8)]);
        }
        char cCharAt = this.decodeRowResult.charAt(0);
        char[] cArr = STARTEND_ENCODING;
        if (!arrayContains(cArr, cCharAt)) {
            throw NotFoundException.getNotFoundInstance();
        }
        StringBuilder sb2 = this.decodeRowResult;
        if (!arrayContains(cArr, sb2.charAt(sb2.length() - 1))) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (this.decodeRowResult.length() <= 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (map == null || !map.containsKey(DecodeHintType.RETURN_CODABAR_START_END)) {
            StringBuilder sb3 = this.decodeRowResult;
            sb3.deleteCharAt(sb3.length() - 1);
            this.decodeRowResult.deleteCharAt(0);
        }
        int i9 = 0;
        for (int i10 = 0; i10 < iFindStartPattern; i10++) {
            i9 += this.counters[i10];
        }
        float f2 = i9;
        while (iFindStartPattern < i4) {
            i9 += this.counters[iFindStartPattern];
            iFindStartPattern++;
        }
        float f3 = i2;
        return new Result(this.decodeRowResult.toString(), null, new ResultPoint[]{new ResultPoint(f2, f3), new ResultPoint(i9, f3)}, BarcodeFormat.CODABAR);
    }

    public void validatePattern(int i2) throws NotFoundException {
        int[] iArr = {0, 0, 0, 0};
        int[] iArr2 = {0, 0, 0, 0};
        int length = this.decodeRowResult.length() - 1;
        int i3 = 0;
        int i4 = i2;
        int i5 = 0;
        while (true) {
            int i6 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(i5)];
            for (int i7 = 6; i7 >= 0; i7--) {
                int i8 = (i7 & 1) + ((i6 & 1) * 2);
                iArr[i8] = iArr[i8] + this.counters[i4 + i7];
                iArr2[i8] = iArr2[i8] + 1;
                i6 >>= 1;
            }
            if (i5 >= length) {
                break;
            }
            i4 += 8;
            i5++;
        }
        int[] iArr3 = new int[4];
        int[] iArr4 = new int[4];
        for (int i9 = 0; i9 < 2; i9++) {
            iArr4[i9] = 0;
            int i10 = i9 + 2;
            int i11 = (((iArr[i9] << 8) / iArr2[i9]) + ((iArr[i10] << 8) / iArr2[i10])) >> 1;
            iArr4[i10] = i11;
            iArr3[i9] = i11;
            iArr3[i10] = ((iArr[i10] * 512) + 384) / iArr2[i10];
        }
        loop3: while (true) {
            int i12 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(i3)];
            for (int i13 = 6; i13 >= 0; i13--) {
                int i14 = (i13 & 1) + ((i12 & 1) * 2);
                int i15 = this.counters[i2 + i13] << 8;
                if (i15 < iArr4[i14] || i15 > iArr3[i14]) {
                    break loop3;
                }
                i12 >>= 1;
            }
            if (i3 >= length) {
                return;
            }
            i2 += 8;
            i3++;
        }
        throw NotFoundException.getNotFoundInstance();
    }
}
