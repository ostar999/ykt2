package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

/* loaded from: classes4.dex */
public final class MonochromeRectangleDetector {
    private static final int MAX_MODULES = 32;
    private final BitMatrix image;

    public MonochromeRectangleDetector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0020  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0031 A[EDGE_INSN: B:69:0x0031->B:22:0x0031 BREAK  A[LOOP:1: B:13:0x001c->B:72:0x001c], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0066 A[EDGE_INSN: B:85:0x0066->B:47:0x0066 BREAK  A[LOOP:3: B:38:0x0052->B:90:0x0052], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private int[] blackWhiteRange(int r6, int r7, int r8, int r9, boolean r10) {
        /*
            r5 = this;
            int r0 = r8 + r9
            r1 = 1
            int r0 = r0 >> r1
            r2 = r0
        L5:
            if (r2 < r8) goto L3a
            com.google.zxing.common.BitMatrix r3 = r5.image
            if (r10 == 0) goto L12
            boolean r3 = r3.get(r2, r6)
            if (r3 == 0) goto L1b
            goto L18
        L12:
            boolean r3 = r3.get(r6, r2)
            if (r3 == 0) goto L1b
        L18:
            int r2 = r2 + (-1)
            goto L5
        L1b:
            r3 = r2
        L1c:
            int r3 = r3 + (-1)
            if (r3 < r8) goto L31
            com.google.zxing.common.BitMatrix r4 = r5.image
            if (r10 == 0) goto L2b
            boolean r4 = r4.get(r3, r6)
            if (r4 == 0) goto L1c
            goto L31
        L2b:
            boolean r4 = r4.get(r6, r3)
            if (r4 == 0) goto L1c
        L31:
            int r4 = r2 - r3
            if (r3 < r8) goto L3a
            if (r4 <= r7) goto L38
            goto L3a
        L38:
            r2 = r3
            goto L5
        L3a:
            int r2 = r2 + r1
        L3b:
            if (r0 >= r9) goto L6f
            com.google.zxing.common.BitMatrix r8 = r5.image
            if (r10 == 0) goto L48
            boolean r8 = r8.get(r0, r6)
            if (r8 == 0) goto L51
            goto L4e
        L48:
            boolean r8 = r8.get(r6, r0)
            if (r8 == 0) goto L51
        L4e:
            int r0 = r0 + 1
            goto L3b
        L51:
            r8 = r0
        L52:
            int r8 = r8 + r1
            if (r8 >= r9) goto L66
            com.google.zxing.common.BitMatrix r3 = r5.image
            if (r10 == 0) goto L60
            boolean r3 = r3.get(r8, r6)
            if (r3 == 0) goto L52
            goto L66
        L60:
            boolean r3 = r3.get(r6, r8)
            if (r3 == 0) goto L52
        L66:
            int r3 = r8 - r0
            if (r8 >= r9) goto L6f
            if (r3 <= r7) goto L6d
            goto L6f
        L6d:
            r0 = r8
            goto L3b
        L6f:
            int r0 = r0 + (-1)
            if (r0 <= r2) goto L7c
            r6 = 2
            int[] r6 = new int[r6]
            r7 = 0
            r6[r7] = r2
            r6[r1] = r0
            goto L7d
        L7c:
            r6 = 0
        L7d:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.common.detector.MonochromeRectangleDetector.blackWhiteRange(int, int, int, int, boolean):int[]");
    }

    private ResultPoint findCornerFromCenter(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) throws NotFoundException {
        int[] iArr = null;
        int i11 = i2;
        int i12 = i6;
        while (i12 < i9 && i12 >= i8 && i11 < i5 && i11 >= i4) {
            int[] iArrBlackWhiteRange = i3 == 0 ? blackWhiteRange(i12, i10, i4, i5, true) : blackWhiteRange(i11, i10, i8, i9, false);
            if (iArrBlackWhiteRange == null) {
                if (iArr == null) {
                    throw NotFoundException.getNotFoundInstance();
                }
                if (i3 == 0) {
                    int i13 = i12 - i7;
                    int i14 = iArr[0];
                    if (i14 >= i2) {
                        return new ResultPoint(iArr[1], i13);
                    }
                    int i15 = iArr[1];
                    if (i15 > i2) {
                        return new ResultPoint(i7 > 0 ? i14 : i15, i13);
                    }
                    return new ResultPoint(i14, i13);
                }
                int i16 = i11 - i3;
                int i17 = iArr[0];
                if (i17 >= i6) {
                    return new ResultPoint(i16, iArr[1]);
                }
                int i18 = iArr[1];
                if (i18 > i6) {
                    return new ResultPoint(i16, i3 < 0 ? i17 : i18);
                }
                return new ResultPoint(i16, i17);
            }
            i12 += i7;
            i11 += i3;
            iArr = iArrBlackWhiteRange;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public ResultPoint[] detect() throws NotFoundException {
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int i2 = height >> 1;
        int i3 = width >> 1;
        int iMax = Math.max(1, height / 256);
        int iMax2 = Math.max(1, width / 256);
        int i4 = -iMax;
        int i5 = i3 >> 1;
        int y2 = ((int) findCornerFromCenter(i3, 0, 0, width, i2, i4, 0, height, i5).getY()) - 1;
        int i6 = i2 >> 1;
        ResultPoint resultPointFindCornerFromCenter = findCornerFromCenter(i3, -iMax2, 0, width, i2, 0, y2, height, i6);
        int x2 = ((int) resultPointFindCornerFromCenter.getX()) - 1;
        ResultPoint resultPointFindCornerFromCenter2 = findCornerFromCenter(i3, iMax2, x2, width, i2, 0, y2, height, i6);
        int x3 = ((int) resultPointFindCornerFromCenter2.getX()) + 1;
        ResultPoint resultPointFindCornerFromCenter3 = findCornerFromCenter(i3, 0, x2, x3, i2, iMax, y2, height, i5);
        return new ResultPoint[]{findCornerFromCenter(i3, 0, x2, x3, i2, i4, y2, ((int) resultPointFindCornerFromCenter3.getY()) + 1, i3 >> 2), resultPointFindCornerFromCenter, resultPointFindCornerFromCenter2, resultPointFindCornerFromCenter3};
    }
}
