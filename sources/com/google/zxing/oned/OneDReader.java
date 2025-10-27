package com.google.zxing.oned;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

/* loaded from: classes4.dex */
public abstract class OneDReader implements Reader {
    protected static final int INTEGER_MATH_SHIFT = 8;
    protected static final int PATTERN_MATCH_RESULT_SCALE_FACTOR = 256;

    /* JADX WARN: Removed duplicated region for block: B:38:0x0074  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private com.google.zxing.Result doDecode(com.google.zxing.BinaryBitmap r22, java.util.Map<com.google.zxing.DecodeHintType, ?> r23) throws com.google.zxing.NotFoundException {
        /*
            Method dump skipped, instructions count: 238
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.OneDReader.doDecode(com.google.zxing.BinaryBitmap, java.util.Map):com.google.zxing.Result");
    }

    public static int patternMatchVariance(int[] iArr, int[] iArr2, int i2) {
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

    public static void recordPattern(BitArray bitArray, int i2, int[] iArr) throws NotFoundException {
        int length = iArr.length;
        int i3 = 0;
        Arrays.fill(iArr, 0, length, 0);
        int size = bitArray.getSize();
        if (i2 >= size) {
            throw NotFoundException.getNotFoundInstance();
        }
        boolean z2 = !bitArray.get(i2);
        while (i2 < size) {
            if (bitArray.get(i2) ^ z2) {
                iArr[i3] = iArr[i3] + 1;
            } else {
                i3++;
                if (i3 == length) {
                    break;
                }
                iArr[i3] = 1;
                z2 = !z2;
            }
            i2++;
        }
        if (i3 != length) {
            if (i3 != length - 1 || i2 != size) {
                throw NotFoundException.getNotFoundInstance();
            }
        }
    }

    public static void recordPatternInReverse(BitArray bitArray, int i2, int[] iArr) throws NotFoundException {
        int length = iArr.length;
        boolean z2 = bitArray.get(i2);
        while (i2 > 0 && length >= 0) {
            i2--;
            if (bitArray.get(i2) != z2) {
                length--;
                z2 = !z2;
            }
        }
        if (length >= 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        recordPattern(bitArray, i2 + 1, iArr);
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, FormatException {
        return decode(binaryBitmap, null);
    }

    public abstract Result decodeRow(int i2, BitArray bitArray, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException;

    @Override // com.google.zxing.Reader
    public void reset() {
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        try {
            return doDecode(binaryBitmap, map);
        } catch (NotFoundException e2) {
            if (!(map != null && map.containsKey(DecodeHintType.TRY_HARDER)) || !binaryBitmap.isRotateSupported()) {
                throw e2;
            }
            BinaryBitmap binaryBitmapRotateCounterClockwise = binaryBitmap.rotateCounterClockwise();
            Result resultDoDecode = doDecode(binaryBitmapRotateCounterClockwise, map);
            Map<ResultMetadataType, Object> resultMetadata = resultDoDecode.getResultMetadata();
            int iIntValue = 270;
            if (resultMetadata != null) {
                ResultMetadataType resultMetadataType = ResultMetadataType.ORIENTATION;
                if (resultMetadata.containsKey(resultMetadataType)) {
                    iIntValue = (((Integer) resultMetadata.get(resultMetadataType)).intValue() + 270) % 360;
                }
            }
            resultDoDecode.putMetadata(ResultMetadataType.ORIENTATION, Integer.valueOf(iIntValue));
            ResultPoint[] resultPoints = resultDoDecode.getResultPoints();
            if (resultPoints != null) {
                int height = binaryBitmapRotateCounterClockwise.getHeight();
                for (int i2 = 0; i2 < resultPoints.length; i2++) {
                    resultPoints[i2] = new ResultPoint((height - resultPoints[i2].getY()) - 1.0f, resultPoints[i2].getX());
                }
            }
            return resultDoDecode;
        }
    }
}
