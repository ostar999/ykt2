package com.google.zxing.pdf417.detector;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class Detector {
    private static final int BARCODE_MIN_HEIGHT = 10;
    private static final int INTEGER_MATH_SHIFT = 8;
    private static final int MAX_AVG_VARIANCE = 107;
    private static final int MAX_INDIVIDUAL_VARIANCE = 204;
    private static final int MAX_PATTERN_DRIFT = 5;
    private static final int MAX_PIXEL_DRIFT = 3;
    private static final int PATTERN_MATCH_RESULT_SCALE_FACTOR = 256;
    private static final int ROW_STEP = 5;
    private static final int SKIPPED_ROW_COUNT_MAX = 25;
    private static final int[] INDEXES_START_PATTERN = {0, 4, 1, 5};
    private static final int[] INDEXES_STOP_PATTERN = {6, 2, 7, 3};
    private static final int[] START_PATTERN = {8, 1, 1, 1, 1, 1, 1, 3};
    private static final int[] STOP_PATTERN = {7, 1, 1, 3, 1, 1, 1, 2, 1};

    private Detector() {
    }

    private static void copyToResult(ResultPoint[] resultPointArr, ResultPoint[] resultPointArr2, int[] iArr) {
        for (int i2 = 0; i2 < iArr.length; i2++) {
            resultPointArr[iArr[i2]] = resultPointArr2[i2];
        }
    }

    public static PDF417DetectorResult detect(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, boolean z2) throws NotFoundException {
        BitMatrix blackMatrix = binaryBitmap.getBlackMatrix();
        List<ResultPoint[]> listDetect = detect(z2, blackMatrix);
        if (listDetect.isEmpty()) {
            blackMatrix = blackMatrix.m93clone();
            blackMatrix.rotate180();
            listDetect = detect(z2, blackMatrix);
        }
        return new PDF417DetectorResult(blackMatrix, listDetect);
    }

    private static int[] findGuardPattern(BitMatrix bitMatrix, int i2, int i3, int i4, boolean z2, int[] iArr, int[] iArr2) {
        Arrays.fill(iArr2, 0, iArr2.length, 0);
        int length = iArr.length;
        int i5 = 0;
        while (bitMatrix.get(i2, i3) && i2 > 0) {
            int i6 = i5 + 1;
            if (i5 >= 3) {
                break;
            }
            i2--;
            i5 = i6;
        }
        boolean z3 = z2;
        int i7 = 0;
        int i8 = i2;
        while (i2 < i4) {
            if (bitMatrix.get(i2, i3) ^ z3) {
                iArr2[i7] = iArr2[i7] + 1;
            } else {
                int i9 = length - 1;
                if (i7 != i9) {
                    i7++;
                } else {
                    if (patternMatchVariance(iArr2, iArr, 204) < 107) {
                        return new int[]{i8, i2};
                    }
                    i8 += iArr2[0] + iArr2[1];
                    int i10 = length - 2;
                    System.arraycopy(iArr2, 2, iArr2, 0, i10);
                    iArr2[i10] = 0;
                    iArr2[i9] = 0;
                    i7--;
                }
                iArr2[i7] = 1;
                z3 = !z3;
            }
            i2++;
        }
        if (i7 != length - 1 || patternMatchVariance(iArr2, iArr, 204) >= 107) {
            return null;
        }
        return new int[]{i8, i2 - 1};
    }

    private static ResultPoint[] findRowsWithPattern(BitMatrix bitMatrix, int i2, int i3, int i4, int i5, int[] iArr) {
        int i6;
        boolean z2;
        int i7;
        int i8;
        int i9;
        ResultPoint[] resultPointArr = new ResultPoint[4];
        int[] iArr2 = new int[iArr.length];
        int i10 = i4;
        while (true) {
            if (i10 >= i2) {
                z2 = false;
                break;
            }
            int[] iArrFindGuardPattern = findGuardPattern(bitMatrix, i5, i10, i3, false, iArr, iArr2);
            if (iArrFindGuardPattern != null) {
                int i11 = i10;
                int[] iArr3 = iArrFindGuardPattern;
                int i12 = i11;
                while (true) {
                    if (i12 <= 0) {
                        i9 = i12;
                        break;
                    }
                    int i13 = i12 - 1;
                    int[] iArrFindGuardPattern2 = findGuardPattern(bitMatrix, i5, i13, i3, false, iArr, iArr2);
                    if (iArrFindGuardPattern2 == null) {
                        i9 = i13 + 1;
                        break;
                    }
                    iArr3 = iArrFindGuardPattern2;
                    i12 = i13;
                }
                float f2 = i9;
                resultPointArr[0] = new ResultPoint(iArr3[0], f2);
                resultPointArr[1] = new ResultPoint(iArr3[1], f2);
                z2 = true;
                i10 = i9;
            } else {
                i10 += 5;
            }
        }
        int i14 = i10 + 1;
        if (z2) {
            int[] iArr4 = {(int) resultPointArr[0].getX(), (int) resultPointArr[1].getX()};
            int i15 = i14;
            int i16 = 0;
            while (true) {
                if (i15 >= i2) {
                    i7 = i16;
                    i8 = i15;
                    break;
                }
                i7 = i16;
                i8 = i15;
                int[] iArrFindGuardPattern3 = findGuardPattern(bitMatrix, iArr4[0], i15, i3, false, iArr, iArr2);
                if (iArrFindGuardPattern3 != null && Math.abs(iArr4[0] - iArrFindGuardPattern3[0]) < 5 && Math.abs(iArr4[1] - iArrFindGuardPattern3[1]) < 5) {
                    iArr4 = iArrFindGuardPattern3;
                    i16 = 0;
                } else {
                    if (i7 > 25) {
                        break;
                    }
                    i16 = i7 + 1;
                }
                i15 = i8 + 1;
            }
            i14 = i8 - (i7 + 1);
            float f3 = i14;
            resultPointArr[2] = new ResultPoint(iArr4[0], f3);
            resultPointArr[3] = new ResultPoint(iArr4[1], f3);
        }
        if (i14 - i10 < 10) {
            for (i6 = 0; i6 < 4; i6++) {
                resultPointArr[i6] = null;
            }
        }
        return resultPointArr;
    }

    private static ResultPoint[] findVertices(BitMatrix bitMatrix, int i2, int i3) {
        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        ResultPoint[] resultPointArr = new ResultPoint[8];
        copyToResult(resultPointArr, findRowsWithPattern(bitMatrix, height, width, i2, i3, START_PATTERN), INDEXES_START_PATTERN);
        ResultPoint resultPoint = resultPointArr[4];
        if (resultPoint != null) {
            i3 = (int) resultPoint.getX();
            i2 = (int) resultPointArr[4].getY();
        }
        copyToResult(resultPointArr, findRowsWithPattern(bitMatrix, height, width, i2, i3, STOP_PATTERN), INDEXES_STOP_PATTERN);
        return resultPointArr;
    }

    private static int patternMatchVariance(int[] iArr, int[] iArr2, int i2) {
        int length = iArr.length;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            i3 += iArr[i5];
            i4 += iArr2[i5];
        }
        if (i3 < i4) {
            return Integer.MAX_VALUE;
        }
        int i6 = (i3 << 8) / i4;
        int i7 = (i2 * i6) >> 8;
        int i8 = 0;
        for (int i9 = 0; i9 < length; i9++) {
            int i10 = iArr[i9] << 8;
            int i11 = iArr2[i9] * i6;
            int i12 = i10 > i11 ? i10 - i11 : i11 - i10;
            if (i12 > i7) {
                return Integer.MAX_VALUE;
            }
            i8 += i12;
        }
        return i8 / i3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001d, code lost:
    
        if (r4 != 0) goto L12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x0020, code lost:
    
        r3 = r0.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0028, code lost:
    
        if (r3.hasNext() == false) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x002a, code lost:
    
        r4 = (com.google.zxing.ResultPoint[]) r3.next();
        r7 = r4[1];
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0032, code lost:
    
        if (r7 == null) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0034, code lost:
    
        r2 = (int) java.lang.Math.max(r2, r7.getY());
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003e, code lost:
    
        r4 = r4[3];
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0040, code lost:
    
        if (r4 == null) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0042, code lost:
    
        r2 = java.lang.Math.max(r2, (int) r4.getY());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static java.util.List<com.google.zxing.ResultPoint[]> detect(boolean r8, com.google.zxing.common.BitMatrix r9) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            r2 = r1
            r3 = r2
        L8:
            r4 = r3
        L9:
            int r5 = r9.getHeight()
            if (r2 >= r5) goto L79
            com.google.zxing.ResultPoint[] r3 = findVertices(r9, r2, r3)
            r5 = r3[r1]
            r6 = 1
            if (r5 != 0) goto L50
            r5 = 3
            r7 = r3[r5]
            if (r7 != 0) goto L50
            if (r4 != 0) goto L20
            goto L79
        L20:
            java.util.Iterator r3 = r0.iterator()
        L24:
            boolean r4 = r3.hasNext()
            if (r4 == 0) goto L4c
            java.lang.Object r4 = r3.next()
            com.google.zxing.ResultPoint[] r4 = (com.google.zxing.ResultPoint[]) r4
            r7 = r4[r6]
            if (r7 == 0) goto L3e
            float r2 = (float) r2
            float r7 = r7.getY()
            float r2 = java.lang.Math.max(r2, r7)
            int r2 = (int) r2
        L3e:
            r4 = r4[r5]
            if (r4 == 0) goto L24
            float r4 = r4.getY()
            int r4 = (int) r4
            int r2 = java.lang.Math.max(r2, r4)
            goto L24
        L4c:
            int r2 = r2 + 5
            r3 = r1
            goto L8
        L50:
            r0.add(r3)
            if (r8 != 0) goto L56
            goto L79
        L56:
            r2 = 2
            r4 = r3[r2]
            if (r4 == 0) goto L67
            float r4 = r4.getX()
            int r4 = (int) r4
            r2 = r3[r2]
            float r2 = r2.getY()
            goto L75
        L67:
            r2 = 4
            r4 = r3[r2]
            float r4 = r4.getX()
            int r4 = (int) r4
            r2 = r3[r2]
            float r2 = r2.getY()
        L75:
            int r2 = (int) r2
            r3 = r4
            r4 = r6
            goto L9
        L79:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.detector.Detector.detect(boolean, com.google.zxing.common.BitMatrix):java.util.List");
    }
}
