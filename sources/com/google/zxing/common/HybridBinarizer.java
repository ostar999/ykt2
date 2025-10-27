package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;

/* loaded from: classes4.dex */
public final class HybridBinarizer extends GlobalHistogramBinarizer {
    private static final int BLOCK_SIZE = 8;
    private static final int BLOCK_SIZE_MASK = 7;
    private static final int BLOCK_SIZE_POWER = 3;
    private static final int MINIMUM_DIMENSION = 40;
    private static final int MIN_DYNAMIC_RANGE = 24;
    private BitMatrix matrix;

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x008c A[PHI: r4
      0x008c: PHI (r4v5 int) = (r4v4 int), (r4v8 int), (r4v8 int) binds: [B:32:0x006c, B:34:0x0070, B:35:0x0072] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private static int[][] calculateBlackPoints(byte[] r17, int r18, int r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 160
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.common.HybridBinarizer.calculateBlackPoints(byte[], int, int, int, int):int[][]");
    }

    private static void calculateThresholdForBlock(byte[] bArr, int i2, int i3, int i4, int i5, int[][] iArr, BitMatrix bitMatrix) {
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = i6 << 3;
            int i8 = i5 - 8;
            if (i7 > i8) {
                i7 = i8;
            }
            for (int i9 = 0; i9 < i2; i9++) {
                int i10 = i9 << 3;
                int i11 = i4 - 8;
                if (i10 <= i11) {
                    i11 = i10;
                }
                int iCap = cap(i9, 2, i2 - 3);
                int iCap2 = cap(i6, 2, i3 - 3);
                int i12 = 0;
                for (int i13 = -2; i13 <= 2; i13++) {
                    int[] iArr2 = iArr[iCap2 + i13];
                    i12 += iArr2[iCap - 2] + iArr2[iCap - 1] + iArr2[iCap] + iArr2[iCap + 1] + iArr2[iCap + 2];
                }
                thresholdBlock(bArr, i11, i7, i12 / 25, i4, bitMatrix);
            }
        }
    }

    private static int cap(int i2, int i3, int i4) {
        return i2 < i3 ? i3 : i2 > i4 ? i4 : i2;
    }

    private static void thresholdBlock(byte[] bArr, int i2, int i3, int i4, int i5, BitMatrix bitMatrix) {
        int i6 = (i3 * i5) + i2;
        int i7 = 0;
        while (i7 < 8) {
            for (int i8 = 0; i8 < 8; i8++) {
                if ((bArr[i6 + i8] & 255) <= i4) {
                    bitMatrix.set(i2 + i8, i3 + i7);
                }
            }
            i7++;
            i6 += i5;
        }
    }

    @Override // com.google.zxing.common.GlobalHistogramBinarizer, com.google.zxing.Binarizer
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    @Override // com.google.zxing.common.GlobalHistogramBinarizer, com.google.zxing.Binarizer
    public BitMatrix getBlackMatrix() throws NotFoundException {
        BitMatrix bitMatrix = this.matrix;
        if (bitMatrix != null) {
            return bitMatrix;
        }
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        if (width < 40 || height < 40) {
            this.matrix = super.getBlackMatrix();
        } else {
            byte[] matrix = luminanceSource.getMatrix();
            int i2 = width >> 3;
            if ((width & 7) != 0) {
                i2++;
            }
            int i3 = i2;
            int i4 = height >> 3;
            if ((height & 7) != 0) {
                i4++;
            }
            int i5 = i4;
            int[][] iArrCalculateBlackPoints = calculateBlackPoints(matrix, i3, i5, width, height);
            BitMatrix bitMatrix2 = new BitMatrix(width, height);
            calculateThresholdForBlock(matrix, i3, i5, width, height, iArrCalculateBlackPoints, bitMatrix2);
            this.matrix = bitMatrix2;
        }
        return this.matrix;
    }
}
