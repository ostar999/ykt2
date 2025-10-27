package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.aztec.encoder.AztecCode;
import com.google.zxing.aztec.encoder.Encoder;
import com.google.zxing.common.BitMatrix;
import java.nio.charset.Charset;
import java.util.Map;

/* loaded from: classes4.dex */
public final class AztecWriter implements Writer {
    private static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");

    private static BitMatrix renderResult(AztecCode aztecCode, int i2, int i3) {
        BitMatrix matrix = aztecCode.getMatrix();
        if (matrix == null) {
            throw new IllegalStateException();
        }
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int iMax = Math.max(i2, width);
        int iMax2 = Math.max(i3, height);
        int iMin = Math.min(iMax / width, iMax2 / height);
        int i4 = (iMax - (width * iMin)) / 2;
        int i5 = (iMax2 - (height * iMin)) / 2;
        BitMatrix bitMatrix = new BitMatrix(iMax, iMax2);
        int i6 = 0;
        while (i6 < height) {
            int i7 = 0;
            int i8 = i4;
            while (i7 < width) {
                if (matrix.get(i7, i6)) {
                    bitMatrix.setRegion(i8, i5, iMin, iMin);
                }
                i7++;
                i8 += iMin;
            }
            i6++;
            i5 += iMin;
        }
        return bitMatrix;
    }

    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3) {
        return encode(str, barcodeFormat, i2, i3, null);
    }

    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<EncodeHintType, ?> map) {
        String str2 = map == null ? null : (String) map.get(EncodeHintType.CHARACTER_SET);
        Number number = map == null ? null : (Number) map.get(EncodeHintType.ERROR_CORRECTION);
        Number number2 = map != null ? (Number) map.get(EncodeHintType.AZTEC_LAYERS) : null;
        return encode(str, barcodeFormat, i2, i3, str2 == null ? DEFAULT_CHARSET : Charset.forName(str2), number == null ? 33 : number.intValue(), number2 == null ? 0 : number2.intValue());
    }

    private static BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3, Charset charset, int i4, int i5) {
        if (barcodeFormat == BarcodeFormat.AZTEC) {
            return renderResult(Encoder.encode(str.getBytes(charset), i4, i5), i2, i3);
        }
        throw new IllegalArgumentException("Can only encode AZTEC, but got " + barcodeFormat);
    }
}
