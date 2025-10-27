package com.google.zxing.pdf417.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.pdf417.PDF417Common;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Formatter;

/* loaded from: classes4.dex */
public final class PDF417ScanningDecoder {
    private static final int CODEWORD_SKEW_SIZE = 2;
    private static final int MAX_EC_CODEWORDS = 512;
    private static final int MAX_ERRORS = 3;
    private static final ErrorCorrection errorCorrection = new ErrorCorrection();

    private PDF417ScanningDecoder() {
    }

    private static BoundingBox adjustBoundingBox(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn) throws NotFoundException, FormatException {
        int[] rowHeights;
        if (detectionResultRowIndicatorColumn == null || (rowHeights = detectionResultRowIndicatorColumn.getRowHeights()) == null) {
            return null;
        }
        int max = getMax(rowHeights);
        int i2 = 0;
        int i3 = 0;
        for (int i4 : rowHeights) {
            i3 += max - i4;
            if (i4 > 0) {
                break;
            }
        }
        Codeword[] codewords = detectionResultRowIndicatorColumn.getCodewords();
        for (int i5 = 0; i3 > 0 && codewords[i5] == null; i5++) {
            i3--;
        }
        for (int length = rowHeights.length - 1; length >= 0; length--) {
            int i6 = rowHeights[length];
            i2 += max - i6;
            if (i6 > 0) {
                break;
            }
        }
        for (int length2 = codewords.length - 1; i2 > 0 && codewords[length2] == null; length2--) {
            i2--;
        }
        return detectionResultRowIndicatorColumn.getBoundingBox().addMissingRows(i3, i2, detectionResultRowIndicatorColumn.isLeft());
    }

    private static void adjustCodewordCount(DetectionResult detectionResult, BarcodeValue[][] barcodeValueArr) throws NotFoundException {
        int[] value = barcodeValueArr[0][1].getValue();
        int barcodeColumnCount = (detectionResult.getBarcodeColumnCount() * detectionResult.getBarcodeRowCount()) - getNumberOfECCodeWords(detectionResult.getBarcodeECLevel());
        if (value.length != 0) {
            if (value[0] != barcodeColumnCount) {
                barcodeValueArr[0][1].setValue(barcodeColumnCount);
            }
        } else {
            if (barcodeColumnCount < 1 || barcodeColumnCount > 928) {
                throw NotFoundException.getNotFoundInstance();
            }
            barcodeValueArr[0][1].setValue(barcodeColumnCount);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0023, code lost:
    
        r0 = -r0;
        r8 = !r8;
        r1 = r1 + 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int adjustCodewordStartColumn(com.google.zxing.common.BitMatrix r5, int r6, int r7, boolean r8, int r9, int r10) {
        /*
            if (r8 == 0) goto L4
            r0 = -1
            goto L5
        L4:
            r0 = 1
        L5:
            r1 = 0
            r2 = r9
        L7:
            r3 = 2
            if (r1 >= r3) goto L29
        La:
            if (r8 == 0) goto Le
            if (r2 >= r6) goto L12
        Le:
            if (r8 != 0) goto L23
            if (r2 >= r7) goto L23
        L12:
            boolean r4 = r5.get(r2, r10)
            if (r8 != r4) goto L23
            int r4 = r9 - r2
            int r4 = java.lang.Math.abs(r4)
            if (r4 <= r3) goto L21
            return r9
        L21:
            int r2 = r2 + r0
            goto La
        L23:
            int r0 = -r0
            r8 = r8 ^ 1
            int r1 = r1 + 1
            goto L7
        L29:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.PDF417ScanningDecoder.adjustCodewordStartColumn(com.google.zxing.common.BitMatrix, int, int, boolean, int, int):int");
    }

    private static boolean checkCodewordSkew(int i2, int i3, int i4) {
        return i3 + (-2) <= i2 && i2 <= i4 + 2;
    }

    private static int correctErrors(int[] iArr, int[] iArr2, int i2) throws ChecksumException {
        if ((iArr2 == null || iArr2.length <= (i2 / 2) + 3) && i2 >= 0 && i2 <= 512) {
            return errorCorrection.decode(iArr, i2, iArr2);
        }
        throw ChecksumException.getChecksumInstance();
    }

    private static BarcodeValue[][] createBarcodeMatrix(DetectionResult detectionResult) {
        BarcodeValue[][] barcodeValueArr = (BarcodeValue[][]) Array.newInstance((Class<?>) BarcodeValue.class, detectionResult.getBarcodeRowCount(), detectionResult.getBarcodeColumnCount() + 2);
        for (BarcodeValue[] barcodeValueArr2 : barcodeValueArr) {
            int i2 = 0;
            while (true) {
                if (i2 < barcodeValueArr2.length) {
                    barcodeValueArr2[i2] = new BarcodeValue();
                    i2++;
                }
            }
        }
        int i3 = -1;
        for (DetectionResultColumn detectionResultColumn : detectionResult.getDetectionResultColumns()) {
            i3++;
            if (detectionResultColumn != null) {
                for (Codeword codeword : detectionResultColumn.getCodewords()) {
                    if (codeword != null && codeword.getRowNumber() != -1) {
                        barcodeValueArr[codeword.getRowNumber()][i3].setValue(codeword.getValue());
                    }
                }
            }
        }
        return barcodeValueArr;
    }

    private static DecoderResult createDecoderResult(DetectionResult detectionResult) throws NotFoundException, ChecksumException, FormatException {
        BarcodeValue[][] barcodeValueArrCreateBarcodeMatrix = createBarcodeMatrix(detectionResult);
        adjustCodewordCount(detectionResult, barcodeValueArrCreateBarcodeMatrix);
        ArrayList arrayList = new ArrayList();
        int[] iArr = new int[detectionResult.getBarcodeRowCount() * detectionResult.getBarcodeColumnCount()];
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        for (int i2 = 0; i2 < detectionResult.getBarcodeRowCount(); i2++) {
            int i3 = 0;
            while (i3 < detectionResult.getBarcodeColumnCount()) {
                int i4 = i3 + 1;
                int[] value = barcodeValueArrCreateBarcodeMatrix[i2][i4].getValue();
                int barcodeColumnCount = (detectionResult.getBarcodeColumnCount() * i2) + i3;
                if (value.length == 0) {
                    arrayList.add(Integer.valueOf(barcodeColumnCount));
                } else if (value.length == 1) {
                    iArr[barcodeColumnCount] = value[0];
                } else {
                    arrayList3.add(Integer.valueOf(barcodeColumnCount));
                    arrayList2.add(value);
                }
                i3 = i4;
            }
        }
        int size = arrayList2.size();
        int[][] iArr2 = new int[size][];
        for (int i5 = 0; i5 < size; i5++) {
            iArr2[i5] = (int[]) arrayList2.get(i5);
        }
        return createDecoderResultFromAmbiguousValues(detectionResult.getBarcodeECLevel(), iArr, PDF417Common.toIntArray(arrayList), PDF417Common.toIntArray(arrayList3), iArr2);
    }

    private static DecoderResult createDecoderResultFromAmbiguousValues(int i2, int[] iArr, int[] iArr2, int[] iArr3, int[][] iArr4) throws ChecksumException, FormatException {
        int length = iArr3.length;
        int[] iArr5 = new int[length];
        int i3 = 100;
        while (true) {
            int i4 = i3 - 1;
            if (i3 <= 0) {
                throw ChecksumException.getChecksumInstance();
            }
            for (int i5 = 0; i5 < length; i5++) {
                iArr[iArr3[i5]] = iArr4[i5][iArr5[i5]];
            }
            try {
                return decodeCodewords(iArr, i2, iArr2);
            } catch (ChecksumException unused) {
                if (length == 0) {
                    throw ChecksumException.getChecksumInstance();
                }
                int i6 = 0;
                while (true) {
                    if (i6 >= length) {
                        break;
                    }
                    int i7 = iArr5[i6];
                    if (i7 < iArr4[i6].length - 1) {
                        iArr5[i6] = i7 + 1;
                        break;
                    }
                    iArr5[i6] = 0;
                    if (i6 == length - 1) {
                        throw ChecksumException.getChecksumInstance();
                    }
                    i6++;
                }
                i3 = i4;
            }
        }
    }

    public static DecoderResult decode(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i2, int i3) throws NotFoundException, ChecksumException, FormatException {
        int i4;
        int i5;
        int i6;
        int i7;
        DetectionResultRowIndicatorColumn rowIndicatorColumn = null;
        DetectionResultRowIndicatorColumn rowIndicatorColumn2 = null;
        DetectionResult detectionResultMerge = null;
        BoundingBox boundingBox = new BoundingBox(bitMatrix, resultPoint, resultPoint2, resultPoint3, resultPoint4);
        for (int i8 = 0; i8 < 2; i8++) {
            if (resultPoint != null) {
                rowIndicatorColumn = getRowIndicatorColumn(bitMatrix, boundingBox, resultPoint, true, i2, i3);
            }
            if (resultPoint3 != null) {
                rowIndicatorColumn2 = getRowIndicatorColumn(bitMatrix, boundingBox, resultPoint3, false, i2, i3);
            }
            detectionResultMerge = merge(rowIndicatorColumn, rowIndicatorColumn2);
            if (detectionResultMerge == null) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (i8 != 0 || detectionResultMerge.getBoundingBox() == null || (detectionResultMerge.getBoundingBox().getMinY() >= boundingBox.getMinY() && detectionResultMerge.getBoundingBox().getMaxY() <= boundingBox.getMaxY())) {
                detectionResultMerge.setBoundingBox(boundingBox);
                break;
            }
            boundingBox = detectionResultMerge.getBoundingBox();
        }
        int barcodeColumnCount = detectionResultMerge.getBarcodeColumnCount() + 1;
        detectionResultMerge.setDetectionResultColumn(0, rowIndicatorColumn);
        detectionResultMerge.setDetectionResultColumn(barcodeColumnCount, rowIndicatorColumn2);
        boolean z2 = rowIndicatorColumn != null;
        int iMin = i2;
        int iMax = i3;
        for (int i9 = 1; i9 <= barcodeColumnCount; i9++) {
            int i10 = z2 ? i9 : barcodeColumnCount - i9;
            if (detectionResultMerge.getDetectionResultColumn(i10) == null) {
                DetectionResultColumn detectionResultRowIndicatorColumn = (i10 == 0 || i10 == barcodeColumnCount) ? new DetectionResultRowIndicatorColumn(boundingBox, i10 == 0) : new DetectionResultColumn(boundingBox);
                detectionResultMerge.setDetectionResultColumn(i10, detectionResultRowIndicatorColumn);
                int i11 = -1;
                int minY = boundingBox.getMinY();
                int i12 = -1;
                while (minY <= boundingBox.getMaxY()) {
                    int startColumn = getStartColumn(detectionResultMerge, i10, minY, z2);
                    if (startColumn >= 0 && startColumn <= boundingBox.getMaxX()) {
                        i4 = startColumn;
                    } else if (i12 == i11) {
                        i5 = i12;
                        i6 = minY;
                        i7 = i11;
                        i12 = i5;
                        minY = i6 + 1;
                        i11 = i7;
                    } else {
                        i4 = i12;
                    }
                    i5 = i12;
                    int i13 = minY;
                    i7 = i11;
                    Codeword codewordDetectCodeword = detectCodeword(bitMatrix, boundingBox.getMinX(), boundingBox.getMaxX(), z2, i4, i13, iMin, iMax);
                    i6 = i13;
                    if (codewordDetectCodeword != null) {
                        detectionResultRowIndicatorColumn.setCodeword(i6, codewordDetectCodeword);
                        iMin = Math.min(iMin, codewordDetectCodeword.getWidth());
                        iMax = Math.max(iMax, codewordDetectCodeword.getWidth());
                        i12 = i4;
                    } else {
                        i12 = i5;
                    }
                    minY = i6 + 1;
                    i11 = i7;
                }
            }
        }
        return createDecoderResult(detectionResultMerge);
    }

    private static DecoderResult decodeCodewords(int[] iArr, int i2, int[] iArr2) throws ChecksumException, FormatException {
        if (iArr.length == 0) {
            throw FormatException.getFormatInstance();
        }
        int i3 = 1 << (i2 + 1);
        int iCorrectErrors = correctErrors(iArr, iArr2, i3);
        verifyCodewordCount(iArr, i3);
        DecoderResult decoderResultDecode = DecodedBitStreamParser.decode(iArr, String.valueOf(i2));
        decoderResultDecode.setErrorsCorrected(Integer.valueOf(iCorrectErrors));
        decoderResultDecode.setErasures(Integer.valueOf(iArr2.length));
        return decoderResultDecode;
    }

    private static Codeword detectCodeword(BitMatrix bitMatrix, int i2, int i3, boolean z2, int i4, int i5, int i6, int i7) {
        int i8;
        int iAdjustCodewordStartColumn = adjustCodewordStartColumn(bitMatrix, i2, i3, z2, i4, i5);
        int[] moduleBitCount = getModuleBitCount(bitMatrix, i2, i3, z2, iAdjustCodewordStartColumn, i5);
        if (moduleBitCount == null) {
            return null;
        }
        int bitCountSum = PDF417Common.getBitCountSum(moduleBitCount);
        if (z2) {
            i8 = iAdjustCodewordStartColumn + bitCountSum;
        } else {
            for (int i9 = 0; i9 < (moduleBitCount.length >> 1); i9++) {
                int i10 = moduleBitCount[i9];
                moduleBitCount[i9] = moduleBitCount[(moduleBitCount.length - 1) - i9];
                moduleBitCount[(moduleBitCount.length - 1) - i9] = i10;
            }
            iAdjustCodewordStartColumn -= bitCountSum;
            i8 = iAdjustCodewordStartColumn;
        }
        if (!checkCodewordSkew(bitCountSum, i6, i7)) {
            return null;
        }
        int decodedValue = PDF417CodewordDecoder.getDecodedValue(moduleBitCount);
        int codeword = PDF417Common.getCodeword(decodedValue);
        if (codeword == -1) {
            return null;
        }
        return new Codeword(iAdjustCodewordStartColumn, i8, getCodewordBucketNumber(decodedValue), codeword);
    }

    private static BarcodeMetadata getBarcodeMetadata(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn, DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn2) {
        BarcodeMetadata barcodeMetadata;
        BarcodeMetadata barcodeMetadata2;
        if (detectionResultRowIndicatorColumn == null || (barcodeMetadata = detectionResultRowIndicatorColumn.getBarcodeMetadata()) == null) {
            if (detectionResultRowIndicatorColumn2 == null) {
                return null;
            }
            return detectionResultRowIndicatorColumn2.getBarcodeMetadata();
        }
        if (detectionResultRowIndicatorColumn2 == null || (barcodeMetadata2 = detectionResultRowIndicatorColumn2.getBarcodeMetadata()) == null || barcodeMetadata.getColumnCount() == barcodeMetadata2.getColumnCount() || barcodeMetadata.getErrorCorrectionLevel() == barcodeMetadata2.getErrorCorrectionLevel() || barcodeMetadata.getRowCount() == barcodeMetadata2.getRowCount()) {
            return barcodeMetadata;
        }
        return null;
    }

    private static int[] getBitCountForCodeword(int i2) {
        int[] iArr = new int[8];
        int i3 = 0;
        int i4 = 7;
        while (true) {
            int i5 = i2 & 1;
            if (i5 != i3) {
                i4--;
                if (i4 < 0) {
                    return iArr;
                }
                i3 = i5;
            }
            iArr[i4] = iArr[i4] + 1;
            i2 >>= 1;
        }
    }

    private static int getCodewordBucketNumber(int i2) {
        return getCodewordBucketNumber(getBitCountForCodeword(i2));
    }

    private static int getMax(int[] iArr) {
        int iMax = -1;
        for (int i2 : iArr) {
            iMax = Math.max(iMax, i2);
        }
        return iMax;
    }

    private static int[] getModuleBitCount(BitMatrix bitMatrix, int i2, int i3, boolean z2, int i4, int i5) {
        int[] iArr = new int[8];
        int i6 = z2 ? 1 : -1;
        int i7 = 0;
        boolean z3 = z2;
        while (true) {
            if (((!z2 || i4 >= i3) && (z2 || i4 < i2)) || i7 >= 8) {
                break;
            }
            if (bitMatrix.get(i4, i5) == z3) {
                iArr[i7] = iArr[i7] + 1;
                i4 += i6;
            } else {
                i7++;
                z3 = !z3;
            }
        }
        if (i7 == 8 || (((z2 && i4 == i3) || (!z2 && i4 == i2)) && i7 == 7)) {
            return iArr;
        }
        return null;
    }

    private static int getNumberOfECCodeWords(int i2) {
        return 2 << i2;
    }

    private static DetectionResultRowIndicatorColumn getRowIndicatorColumn(BitMatrix bitMatrix, BoundingBox boundingBox, ResultPoint resultPoint, boolean z2, int i2, int i3) {
        DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn = new DetectionResultRowIndicatorColumn(boundingBox, z2);
        int i4 = 0;
        while (i4 < 2) {
            int i5 = i4 == 0 ? 1 : -1;
            int x2 = (int) resultPoint.getX();
            for (int y2 = (int) resultPoint.getY(); y2 <= boundingBox.getMaxY() && y2 >= boundingBox.getMinY(); y2 += i5) {
                Codeword codewordDetectCodeword = detectCodeword(bitMatrix, 0, bitMatrix.getWidth(), z2, x2, y2, i2, i3);
                if (codewordDetectCodeword != null) {
                    detectionResultRowIndicatorColumn.setCodeword(y2, codewordDetectCodeword);
                    x2 = z2 ? codewordDetectCodeword.getStartX() : codewordDetectCodeword.getEndX();
                }
            }
            i4++;
        }
        return detectionResultRowIndicatorColumn;
    }

    private static int getStartColumn(DetectionResult detectionResult, int i2, int i3, boolean z2) {
        int i4 = z2 ? 1 : -1;
        int i5 = i2 - i4;
        Codeword codeword = isValidBarcodeColumn(detectionResult, i5) ? detectionResult.getDetectionResultColumn(i5).getCodeword(i3) : null;
        if (codeword != null) {
            return z2 ? codeword.getEndX() : codeword.getStartX();
        }
        Codeword codewordNearby = detectionResult.getDetectionResultColumn(i2).getCodewordNearby(i3);
        if (codewordNearby != null) {
            return z2 ? codewordNearby.getStartX() : codewordNearby.getEndX();
        }
        if (isValidBarcodeColumn(detectionResult, i5)) {
            codewordNearby = detectionResult.getDetectionResultColumn(i5).getCodewordNearby(i3);
        }
        if (codewordNearby != null) {
            return z2 ? codewordNearby.getEndX() : codewordNearby.getStartX();
        }
        int i6 = 0;
        while (true) {
            i2 -= i4;
            if (!isValidBarcodeColumn(detectionResult, i2)) {
                BoundingBox boundingBox = detectionResult.getBoundingBox();
                return z2 ? boundingBox.getMinX() : boundingBox.getMaxX();
            }
            for (Codeword codeword2 : detectionResult.getDetectionResultColumn(i2).getCodewords()) {
                if (codeword2 != null) {
                    return (z2 ? codeword2.getEndX() : codeword2.getStartX()) + (i4 * i6 * (codeword2.getEndX() - codeword2.getStartX()));
                }
            }
            i6++;
        }
    }

    private static boolean isValidBarcodeColumn(DetectionResult detectionResult, int i2) {
        return i2 >= 0 && i2 <= detectionResult.getBarcodeColumnCount() + 1;
    }

    private static DetectionResult merge(DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn, DetectionResultRowIndicatorColumn detectionResultRowIndicatorColumn2) throws NotFoundException, FormatException {
        BarcodeMetadata barcodeMetadata;
        if ((detectionResultRowIndicatorColumn == null && detectionResultRowIndicatorColumn2 == null) || (barcodeMetadata = getBarcodeMetadata(detectionResultRowIndicatorColumn, detectionResultRowIndicatorColumn2)) == null) {
            return null;
        }
        return new DetectionResult(barcodeMetadata, BoundingBox.merge(adjustBoundingBox(detectionResultRowIndicatorColumn), adjustBoundingBox(detectionResultRowIndicatorColumn2)));
    }

    public static String toString(BarcodeValue[][] barcodeValueArr) {
        Formatter formatter = new Formatter();
        for (int i2 = 0; i2 < barcodeValueArr.length; i2++) {
            formatter.format("Row %2d: ", Integer.valueOf(i2));
            int i3 = 0;
            while (true) {
                BarcodeValue[] barcodeValueArr2 = barcodeValueArr[i2];
                if (i3 < barcodeValueArr2.length) {
                    BarcodeValue barcodeValue = barcodeValueArr2[i3];
                    if (barcodeValue.getValue().length == 0) {
                        formatter.format("        ", null);
                    } else {
                        formatter.format("%4d(%2d)", Integer.valueOf(barcodeValue.getValue()[0]), barcodeValue.getConfidence(barcodeValue.getValue()[0]));
                    }
                    i3++;
                }
            }
            formatter.format("%n", new Object[0]);
        }
        String string = formatter.toString();
        formatter.close();
        return string;
    }

    private static void verifyCodewordCount(int[] iArr, int i2) throws FormatException {
        if (iArr.length < 4) {
            throw FormatException.getFormatInstance();
        }
        int i3 = iArr[0];
        if (i3 > iArr.length) {
            throw FormatException.getFormatInstance();
        }
        if (i3 == 0) {
            if (i2 >= iArr.length) {
                throw FormatException.getFormatInstance();
            }
            iArr[0] = iArr.length - i2;
        }
    }

    private static int getCodewordBucketNumber(int[] iArr) {
        return ((((iArr[0] - iArr[2]) + iArr[4]) - iArr[6]) + 9) % 9;
    }
}
