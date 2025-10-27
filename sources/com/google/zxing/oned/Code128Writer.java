package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public final class Code128Writer extends OneDimensionalCodeWriter {
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_B = 100;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final char ESCAPE_FNC_1 = 241;
    private static final char ESCAPE_FNC_2 = 242;
    private static final char ESCAPE_FNC_3 = 243;
    private static final char ESCAPE_FNC_4 = 244;

    private static boolean isDigits(CharSequence charSequence, int i2, int i3) {
        int i4 = i3 + i2;
        int length = charSequence.length();
        while (i2 < i4 && i2 < length) {
            char cCharAt = charSequence.charAt(i2);
            if (cCharAt < '0' || cCharAt > '9') {
                if (cCharAt != 241) {
                    return false;
                }
                i4++;
            }
            i2++;
        }
        return i4 <= length;
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.CODE_128) {
            return super.encode(str, barcodeFormat, i2, i3, map);
        }
        throw new IllegalArgumentException("Can only encode CODE_128, but got " + barcodeFormat);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) throws NumberFormatException {
        int length = str.length();
        if (length >= 1 && length <= 80) {
            int iAppendPattern = 0;
            for (int i2 = 0; i2 < length; i2++) {
                char cCharAt = str.charAt(i2);
                if (cCharAt < ' ' || cCharAt > '~') {
                    switch (cCharAt) {
                        case 241:
                        case 242:
                        case 243:
                        case 244:
                            break;
                        default:
                            throw new IllegalArgumentException("Bad character in input: " + cCharAt);
                    }
                }
            }
            ArrayList<int[]> arrayList = new ArrayList();
            int i3 = 1;
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            while (i4 < length) {
                int iCharAt = 100;
                int i7 = isDigits(str, i4, i6 == 99 ? 2 : 4) ? 99 : 100;
                if (i7 == i6) {
                    switch (str.charAt(i4)) {
                        case 241:
                            iCharAt = 102;
                            break;
                        case 242:
                            iCharAt = 97;
                            break;
                        case 243:
                            iCharAt = 96;
                            break;
                        case 244:
                            break;
                        default:
                            if (i6 != 100) {
                                iCharAt = Integer.parseInt(str.substring(i4, i4 + 2));
                                i4++;
                                break;
                            } else {
                                iCharAt = str.charAt(i4) - ' ';
                                break;
                            }
                    }
                    i4++;
                } else {
                    iCharAt = i6 == 0 ? i7 == 100 ? 104 : 105 : i7;
                    i6 = i7;
                }
                arrayList.add(Code128Reader.CODE_PATTERNS[iCharAt]);
                i5 += iCharAt * i3;
                if (i4 != 0) {
                    i3++;
                }
            }
            int[][] iArr = Code128Reader.CODE_PATTERNS;
            arrayList.add(iArr[i5 % 103]);
            arrayList.add(iArr[106]);
            int i8 = 0;
            for (int[] iArr2 : arrayList) {
                for (int i9 : iArr2) {
                    i8 += i9;
                }
            }
            boolean[] zArr = new boolean[i8];
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                iAppendPattern += OneDimensionalCodeWriter.appendPattern(zArr, iAppendPattern, (int[]) it.next(), true);
            }
            return zArr;
        }
        throw new IllegalArgumentException("Contents length should be between 1 and 80 characters, but got " + length);
    }
}
