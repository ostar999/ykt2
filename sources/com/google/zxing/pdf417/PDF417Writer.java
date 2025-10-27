package com.google.zxing.pdf417;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;
import com.google.zxing.pdf417.encoder.PDF417;
import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Map;

/* loaded from: classes4.dex */
public final class PDF417Writer implements Writer {
    static final int WHITE_SPACE = 30;

    private static BitMatrix bitMatrixFromEncoder(PDF417 pdf417, String str, int i2, int i3, int i4) throws WriterException {
        boolean z2;
        pdf417.generateBarcodeLogic(str, 2);
        byte[][] scaledMatrix = pdf417.getBarcodeMatrix().getScaledMatrix(2, 8);
        if ((i3 > i2) ^ (scaledMatrix[0].length < scaledMatrix.length)) {
            scaledMatrix = rotateArray(scaledMatrix);
            z2 = true;
        } else {
            z2 = false;
        }
        int length = i2 / scaledMatrix[0].length;
        int length2 = i3 / scaledMatrix.length;
        if (length >= length2) {
            length = length2;
        }
        if (length <= 1) {
            return bitMatrixFrombitArray(scaledMatrix, i4);
        }
        byte[][] scaledMatrix2 = pdf417.getBarcodeMatrix().getScaledMatrix(length * 2, length * 4 * 2);
        if (z2) {
            scaledMatrix2 = rotateArray(scaledMatrix2);
        }
        return bitMatrixFrombitArray(scaledMatrix2, i4);
    }

    private static BitMatrix bitMatrixFrombitArray(byte[][] bArr, int i2) {
        int i3 = i2 * 2;
        BitMatrix bitMatrix = new BitMatrix(bArr[0].length + i3, bArr.length + i3);
        bitMatrix.clear();
        int height = (bitMatrix.getHeight() - i2) - 1;
        int i4 = 0;
        while (i4 < bArr.length) {
            for (int i5 = 0; i5 < bArr[0].length; i5++) {
                if (bArr[i4][i5] == 1) {
                    bitMatrix.set(i5 + i2, height);
                }
            }
            i4++;
            height--;
        }
        return bitMatrix;
    }

    private static byte[][] rotateArray(byte[][] bArr) {
        byte[][] bArr2 = (byte[][]) Array.newInstance((Class<?>) Byte.TYPE, bArr[0].length, bArr.length);
        for (int i2 = 0; i2 < bArr.length; i2++) {
            int length = (bArr.length - i2) - 1;
            for (int i3 = 0; i3 < bArr[0].length; i3++) {
                bArr2[i3][length] = bArr[i2][i3];
            }
        }
        return bArr2;
    }

    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3, Map<EncodeHintType, ?> map) throws WriterException {
        if (barcodeFormat != BarcodeFormat.PDF_417) {
            throw new IllegalArgumentException("Can only encode PDF_417, but got " + barcodeFormat);
        }
        PDF417 pdf417 = new PDF417();
        if (map != null) {
            EncodeHintType encodeHintType = EncodeHintType.PDF417_COMPACT;
            if (map.containsKey(encodeHintType)) {
                pdf417.setCompact(((Boolean) map.get(encodeHintType)).booleanValue());
            }
            EncodeHintType encodeHintType2 = EncodeHintType.PDF417_COMPACTION;
            if (map.containsKey(encodeHintType2)) {
                pdf417.setCompaction((Compaction) map.get(encodeHintType2));
            }
            EncodeHintType encodeHintType3 = EncodeHintType.PDF417_DIMENSIONS;
            if (map.containsKey(encodeHintType3)) {
                Dimensions dimensions = (Dimensions) map.get(encodeHintType3);
                pdf417.setDimensions(dimensions.getMaxCols(), dimensions.getMinCols(), dimensions.getMaxRows(), dimensions.getMinRows());
            }
            EncodeHintType encodeHintType4 = EncodeHintType.MARGIN;
            iIntValue = map.containsKey(encodeHintType4) ? ((Number) map.get(encodeHintType4)).intValue() : 30;
            EncodeHintType encodeHintType5 = EncodeHintType.CHARACTER_SET;
            if (map.containsKey(encodeHintType5)) {
                pdf417.setEncoding(Charset.forName((String) map.get(encodeHintType5)));
            }
        }
        return bitMatrixFromEncoder(pdf417, str, i2, i3, iIntValue);
    }

    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i2, int i3) throws WriterException {
        return encode(str, barcodeFormat, i2, i3, null);
    }
}
