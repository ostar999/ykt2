package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

/* loaded from: classes4.dex */
public final class EAN13Writer extends UPCEANWriter {
    private static final int CODE_WIDTH = 95;

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter, com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat == BarcodeFormat.EAN_13) {
            return super.encode(str, barcodeFormat, i2, i3, map);
        }
        throw new IllegalArgumentException("Can only encode EAN_13, but got " + barcodeFormat);
    }

    @Override // com.google.zxing.oned.OneDimensionalCodeWriter
    public boolean[] encode(String str) throws NumberFormatException {
        if (str.length() == 13) {
            try {
                if (UPCEANReader.checkStandardUPCEANChecksum(str)) {
                    int i2 = EAN13Reader.FIRST_DIGIT_ENCODINGS[Integer.parseInt(str.substring(0, 1))];
                    boolean[] zArr = new boolean[95];
                    int iAppendPattern = OneDimensionalCodeWriter.appendPattern(zArr, 0, UPCEANReader.START_END_PATTERN, true) + 0;
                    int i3 = 1;
                    while (i3 <= 6) {
                        int i4 = i3 + 1;
                        int i5 = Integer.parseInt(str.substring(i3, i4));
                        if (((i2 >> (6 - i3)) & 1) == 1) {
                            i5 += 10;
                        }
                        iAppendPattern += OneDimensionalCodeWriter.appendPattern(zArr, iAppendPattern, UPCEANReader.L_AND_G_PATTERNS[i5], false);
                        i3 = i4;
                    }
                    int iAppendPattern2 = iAppendPattern + OneDimensionalCodeWriter.appendPattern(zArr, iAppendPattern, UPCEANReader.MIDDLE_PATTERN, false);
                    int i6 = 7;
                    while (i6 <= 12) {
                        int i7 = i6 + 1;
                        iAppendPattern2 += OneDimensionalCodeWriter.appendPattern(zArr, iAppendPattern2, UPCEANReader.L_PATTERNS[Integer.parseInt(str.substring(i6, i7))], true);
                        i6 = i7;
                    }
                    OneDimensionalCodeWriter.appendPattern(zArr, iAppendPattern2, UPCEANReader.START_END_PATTERN, true);
                    return zArr;
                }
                throw new IllegalArgumentException("Contents do not pass checksum");
            } catch (FormatException unused) {
                throw new IllegalArgumentException("Illegal contents");
            }
        }
        throw new IllegalArgumentException("Requested contents should be 13 digits long, but got " + str.length());
    }
}
