package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
public final class GenericMultipleBarcodeReader implements MultipleBarcodeReader {
    private static final int MAX_DEPTH = 4;
    private static final int MIN_DIMENSION_TO_RECUR = 100;
    private final Reader delegate;

    public GenericMultipleBarcodeReader(Reader reader) {
        this.delegate = reader;
    }

    private void doDecodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, List<Result> list, int i2, int i3, int i4) {
        boolean z2;
        float f2;
        float f3;
        int i5;
        int i6;
        int i7;
        int i8;
        if (i4 > 4) {
            return;
        }
        try {
            Result resultDecode = this.delegate.decode(binaryBitmap, map);
            Iterator<Result> it = list.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().getText().equals(resultDecode.getText())) {
                        z2 = true;
                        break;
                    }
                } else {
                    z2 = false;
                    break;
                }
            }
            if (!z2) {
                list.add(translateResultPoints(resultDecode, i2, i3));
            }
            ResultPoint[] resultPoints = resultDecode.getResultPoints();
            if (resultPoints == null || resultPoints.length == 0) {
                return;
            }
            int width = binaryBitmap.getWidth();
            int height = binaryBitmap.getHeight();
            float f4 = width;
            float f5 = 0.0f;
            float f6 = height;
            float f7 = 0.0f;
            for (ResultPoint resultPoint : resultPoints) {
                float x2 = resultPoint.getX();
                float y2 = resultPoint.getY();
                if (x2 < f4) {
                    f4 = x2;
                }
                if (y2 < f6) {
                    f6 = y2;
                }
                if (x2 > f7) {
                    f7 = x2;
                }
                if (y2 > f5) {
                    f5 = y2;
                }
            }
            if (f4 > 100.0f) {
                f2 = f7;
                f3 = f6;
                i5 = height;
                i6 = width;
                doDecodeMultiple(binaryBitmap.crop(0, 0, (int) f4, height), map, list, i2, i3, i4 + 1);
            } else {
                f2 = f7;
                f3 = f6;
                i5 = height;
                i6 = width;
            }
            if (f3 > 100.0f) {
                int i9 = (int) f3;
                i7 = i6;
                doDecodeMultiple(binaryBitmap.crop(0, 0, i7, i9), map, list, i2, i3, i4 + 1);
            } else {
                i7 = i6;
            }
            float f8 = f2;
            if (f8 < i7 - 100) {
                int i10 = (int) f8;
                i8 = i5;
                doDecodeMultiple(binaryBitmap.crop(i10, 0, i7 - i10, i8), map, list, i2 + i10, i3, i4 + 1);
            } else {
                i8 = i5;
            }
            if (f5 < i8 - 100) {
                int i11 = (int) f5;
                doDecodeMultiple(binaryBitmap.crop(0, i11, i7, i8 - i11), map, list, i2, i3 + i11, i4 + 1);
            }
        } catch (ReaderException unused) {
        }
    }

    private static Result translateResultPoints(Result result, int i2, int i3) {
        ResultPoint[] resultPoints = result.getResultPoints();
        if (resultPoints == null) {
            return result;
        }
        ResultPoint[] resultPointArr = new ResultPoint[resultPoints.length];
        for (int i4 = 0; i4 < resultPoints.length; i4++) {
            ResultPoint resultPoint = resultPoints[i4];
            resultPointArr[i4] = new ResultPoint(resultPoint.getX() + i2, resultPoint.getY() + i3);
        }
        Result result2 = new Result(result.getText(), result.getRawBytes(), resultPointArr, result.getBarcodeFormat());
        result2.putAllMetadata(result.getResultMetadata());
        return result2;
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return decodeMultiple(binaryBitmap, null);
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        ArrayList arrayList = new ArrayList();
        doDecodeMultiple(binaryBitmap, map, arrayList, 0, 0, 0);
        if (arrayList.isEmpty()) {
            throw NotFoundException.getNotFoundInstance();
        }
        return (Result[]) arrayList.toArray(new Result[arrayList.size()]);
    }
}
