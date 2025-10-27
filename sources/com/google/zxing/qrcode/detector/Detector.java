package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Map;

/* loaded from: classes4.dex */
public class Detector {
    private final BitMatrix image;
    private ResultPointCallback resultPointCallback;

    public Detector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    private float calculateModuleSizeOneWay(ResultPoint resultPoint, ResultPoint resultPoint2) {
        float fSizeOfBlackWhiteBlackRunBothWays = sizeOfBlackWhiteBlackRunBothWays((int) resultPoint.getX(), (int) resultPoint.getY(), (int) resultPoint2.getX(), (int) resultPoint2.getY());
        float fSizeOfBlackWhiteBlackRunBothWays2 = sizeOfBlackWhiteBlackRunBothWays((int) resultPoint2.getX(), (int) resultPoint2.getY(), (int) resultPoint.getX(), (int) resultPoint.getY());
        return Float.isNaN(fSizeOfBlackWhiteBlackRunBothWays) ? fSizeOfBlackWhiteBlackRunBothWays2 / 7.0f : Float.isNaN(fSizeOfBlackWhiteBlackRunBothWays2) ? fSizeOfBlackWhiteBlackRunBothWays / 7.0f : (fSizeOfBlackWhiteBlackRunBothWays + fSizeOfBlackWhiteBlackRunBothWays2) / 14.0f;
    }

    private static int computeDimension(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, float f2) throws NotFoundException {
        int iRound = ((MathUtils.round(ResultPoint.distance(resultPoint, resultPoint2) / f2) + MathUtils.round(ResultPoint.distance(resultPoint, resultPoint3) / f2)) >> 1) + 7;
        int i2 = iRound & 3;
        if (i2 == 0) {
            return iRound + 1;
        }
        if (i2 == 2) {
            return iRound - 1;
        }
        if (i2 != 3) {
            return iRound;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static PerspectiveTransform createTransform(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4, int i2) {
        float x2;
        float y2;
        float f2;
        float f3 = i2 - 3.5f;
        if (resultPoint4 != null) {
            x2 = resultPoint4.getX();
            y2 = resultPoint4.getY();
            f2 = f3 - 3.0f;
        } else {
            x2 = (resultPoint2.getX() - resultPoint.getX()) + resultPoint3.getX();
            y2 = (resultPoint2.getY() - resultPoint.getY()) + resultPoint3.getY();
            f2 = f3;
        }
        return PerspectiveTransform.quadrilateralToQuadrilateral(3.5f, 3.5f, f3, 3.5f, f2, f2, 3.5f, f3, resultPoint.getX(), resultPoint.getY(), resultPoint2.getX(), resultPoint2.getY(), x2, y2, resultPoint3.getX(), resultPoint3.getY());
    }

    private static BitMatrix sampleGrid(BitMatrix bitMatrix, PerspectiveTransform perspectiveTransform, int i2) throws NotFoundException {
        return GridSampler.getInstance().sampleGrid(bitMatrix, i2, i2, perspectiveTransform);
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x0083, code lost:
    
        if (r14 != 2) goto L44;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x008b, code lost:
    
        return com.google.zxing.common.detector.MathUtils.distance(r19, r6, r1, r4);
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x008c, code lost:
    
        return Float.NaN;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private float sizeOfBlackWhiteBlackRun(int r18, int r19, int r20, int r21) {
        /*
            r17 = this;
            int r0 = r21 - r19
            int r0 = java.lang.Math.abs(r0)
            int r1 = r20 - r18
            int r1 = java.lang.Math.abs(r1)
            r3 = 1
            if (r0 <= r1) goto L11
            r0 = r3
            goto L12
        L11:
            r0 = 0
        L12:
            if (r0 == 0) goto L1d
            r4 = r18
            r1 = r19
            r6 = r20
            r5 = r21
            goto L25
        L1d:
            r1 = r18
            r4 = r19
            r5 = r20
            r6 = r21
        L25:
            int r7 = r5 - r1
            int r7 = java.lang.Math.abs(r7)
            int r8 = r6 - r4
            int r8 = java.lang.Math.abs(r8)
            int r9 = -r7
            int r9 = r9 >> r3
            r10 = -1
            if (r1 >= r5) goto L38
            r11 = r3
            goto L39
        L38:
            r11 = r10
        L39:
            if (r4 >= r6) goto L3c
            r10 = r3
        L3c:
            int r5 = r5 + r11
            r12 = r1
            r13 = r4
            r14 = 0
        L40:
            if (r12 == r5) goto L7e
            if (r0 == 0) goto L46
            r2 = r13
            goto L47
        L46:
            r2 = r12
        L47:
            if (r0 == 0) goto L4b
            r15 = r12
            goto L4c
        L4b:
            r15 = r13
        L4c:
            if (r14 != r3) goto L56
            r16 = r0
            r0 = r3
            r19 = r5
            r3 = r17
            goto L5d
        L56:
            r3 = r17
            r16 = r0
            r19 = r5
            r0 = 0
        L5d:
            com.google.zxing.common.BitMatrix r5 = r3.image
            boolean r2 = r5.get(r2, r15)
            if (r0 != r2) goto L6f
            r0 = 2
            if (r14 != r0) goto L6d
            float r0 = com.google.zxing.common.detector.MathUtils.distance(r12, r13, r1, r4)
            return r0
        L6d:
            int r14 = r14 + 1
        L6f:
            int r9 = r9 + r8
            if (r9 <= 0) goto L77
            if (r13 != r6) goto L75
            goto L82
        L75:
            int r13 = r13 + r10
            int r9 = r9 - r7
        L77:
            int r12 = r12 + r11
            r5 = r19
            r0 = r16
            r3 = 1
            goto L40
        L7e:
            r3 = r17
            r19 = r5
        L82:
            r0 = 2
            if (r14 != r0) goto L8c
            r5 = r19
            float r0 = com.google.zxing.common.detector.MathUtils.distance(r5, r6, r1, r4)
            return r0
        L8c:
            r0 = 2143289344(0x7fc00000, float:NaN)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.detector.Detector.sizeOfBlackWhiteBlackRun(int, int, int, int):float");
    }

    private float sizeOfBlackWhiteBlackRunBothWays(int i2, int i3, int i4, int i5) {
        float width;
        float height;
        float fSizeOfBlackWhiteBlackRun = sizeOfBlackWhiteBlackRun(i2, i3, i4, i5);
        int width2 = i2 - (i4 - i2);
        int height2 = 0;
        if (width2 < 0) {
            width = i2 / (i2 - width2);
            width2 = 0;
        } else if (width2 >= this.image.getWidth()) {
            width = ((this.image.getWidth() - 1) - i2) / (width2 - i2);
            width2 = this.image.getWidth() - 1;
        } else {
            width = 1.0f;
        }
        float f2 = i3;
        int i6 = (int) (f2 - ((i5 - i3) * width));
        if (i6 < 0) {
            height = f2 / (i3 - i6);
        } else if (i6 >= this.image.getHeight()) {
            height = ((this.image.getHeight() - 1) - i3) / (i6 - i3);
            height2 = this.image.getHeight() - 1;
        } else {
            height2 = i6;
            height = 1.0f;
        }
        return (fSizeOfBlackWhiteBlackRun + sizeOfBlackWhiteBlackRun(i2, i3, (int) (i2 + ((width2 - i2) * height)), height2)) - 1.0f;
    }

    public final float calculateModuleSize(ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3) {
        return (calculateModuleSizeOneWay(resultPoint, resultPoint2) + calculateModuleSizeOneWay(resultPoint, resultPoint3)) / 2.0f;
    }

    public DetectorResult detect() throws NotFoundException, FormatException {
        return detect(null);
    }

    public final AlignmentPattern findAlignmentInRegion(float f2, int i2, int i3, float f3) throws NotFoundException {
        int i4 = (int) (f3 * f2);
        int iMax = Math.max(0, i2 - i4);
        int iMin = Math.min(this.image.getWidth() - 1, i2 + i4) - iMax;
        float f4 = 3.0f * f2;
        if (iMin < f4) {
            throw NotFoundException.getNotFoundInstance();
        }
        int iMax2 = Math.max(0, i3 - i4);
        int iMin2 = Math.min(this.image.getHeight() - 1, i3 + i4) - iMax2;
        if (iMin2 >= f4) {
            return new AlignmentPatternFinder(this.image, iMax, iMax2, iMin, iMin2, f2, this.resultPointCallback).find();
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public final BitMatrix getImage() {
        return this.image;
    }

    public final ResultPointCallback getResultPointCallback() {
        return this.resultPointCallback;
    }

    public final DetectorResult processFinderPatternInfo(FinderPatternInfo finderPatternInfo) throws NotFoundException, FormatException {
        AlignmentPattern alignmentPatternFindAlignmentInRegion;
        FinderPattern topLeft = finderPatternInfo.getTopLeft();
        FinderPattern topRight = finderPatternInfo.getTopRight();
        FinderPattern bottomLeft = finderPatternInfo.getBottomLeft();
        float fCalculateModuleSize = calculateModuleSize(topLeft, topRight, bottomLeft);
        if (fCalculateModuleSize < 1.0f) {
            throw NotFoundException.getNotFoundInstance();
        }
        int iComputeDimension = computeDimension(topLeft, topRight, bottomLeft, fCalculateModuleSize);
        Version provisionalVersionForDimension = Version.getProvisionalVersionForDimension(iComputeDimension);
        int dimensionForVersion = provisionalVersionForDimension.getDimensionForVersion() - 7;
        if (provisionalVersionForDimension.getAlignmentPatternCenters().length > 0) {
            float x2 = (topRight.getX() - topLeft.getX()) + bottomLeft.getX();
            float y2 = (topRight.getY() - topLeft.getY()) + bottomLeft.getY();
            float f2 = 1.0f - (3.0f / dimensionForVersion);
            int x3 = (int) (topLeft.getX() + ((x2 - topLeft.getX()) * f2));
            int y3 = (int) (topLeft.getY() + (f2 * (y2 - topLeft.getY())));
            for (int i2 = 4; i2 <= 16; i2 <<= 1) {
                try {
                    alignmentPatternFindAlignmentInRegion = findAlignmentInRegion(fCalculateModuleSize, x3, y3, i2);
                    break;
                } catch (NotFoundException unused) {
                }
            }
            alignmentPatternFindAlignmentInRegion = null;
        } else {
            alignmentPatternFindAlignmentInRegion = null;
        }
        return new DetectorResult(sampleGrid(this.image, createTransform(topLeft, topRight, bottomLeft, alignmentPatternFindAlignmentInRegion, iComputeDimension), iComputeDimension), alignmentPatternFindAlignmentInRegion == null ? new ResultPoint[]{bottomLeft, topLeft, topRight} : new ResultPoint[]{bottomLeft, topLeft, topRight, alignmentPatternFindAlignmentInRegion});
    }

    public final DetectorResult detect(Map<DecodeHintType, ?> map) throws NotFoundException, FormatException {
        ResultPointCallback resultPointCallback = map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
        this.resultPointCallback = resultPointCallback;
        return processFinderPatternInfo(new FinderPatternFinder(this.image, resultPointCallback).find(map));
    }
}
