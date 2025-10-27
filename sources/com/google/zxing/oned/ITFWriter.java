package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* loaded from: classes4.dex */
public final class ITFWriter extends OneDimensionalCodeWriter {
    private static final int[] START_PATTERN = {1, 1, 1, 1};
    private static final int[] END_PATTERN = {3, 1, 1};

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.ITF) {
            return super.encode(str, barcodeFormat, i2, i3, map);
        }
        throw new IllegalArgumentException("Can only encode ITF, but got " + barcodeFormat);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) {
        int length = str.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("The lenght of the input should be even");
        }
        if (length <= 80) {
            boolean[] zArr = new boolean[(length * 9) + 9];
            int iAppendPattern = OneDimensionalCodeWriter.appendPattern(zArr, 0, START_PATTERN, true);
            for (int i2 = 0; i2 < length; i2 += 2) {
                int iDigit = Character.digit(str.charAt(i2), 10);
                int iDigit2 = Character.digit(str.charAt(i2 + 1), 10);
                int[] iArr = new int[18];
                for (int i3 = 0; i3 < 5; i3++) {
                    int i4 = i3 << 1;
                    int[][] iArr2 = ITFReader.PATTERNS;
                    iArr[i4] = iArr2[iDigit][i3];
                    iArr[i4 + 1] = iArr2[iDigit2][i3];
                }
                iAppendPattern += OneDimensionalCodeWriter.appendPattern(zArr, iAppendPattern, iArr, true);
            }
            OneDimensionalCodeWriter.appendPattern(zArr, iAppendPattern, END_PATTERN, true);
            return zArr;
        }
        throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + length);
    }
}
