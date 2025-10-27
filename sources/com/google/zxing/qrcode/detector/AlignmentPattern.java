package com.google.zxing.qrcode.detector;

import com.google.zxing.ResultPoint;

/* loaded from: classes4.dex */
public final class AlignmentPattern extends ResultPoint {
    private final float estimatedModuleSize;

    public AlignmentPattern(float f2, float f3, float f4) {
        super(f2, f3);
        this.estimatedModuleSize = f4;
    }

    public boolean aboutEquals(float f2, float f3, float f4) {
        if (Math.abs(f3 - getY()) > f2 || Math.abs(f4 - getX()) > f2) {
            return false;
        }
        float fAbs = Math.abs(f2 - this.estimatedModuleSize);
        return fAbs <= 1.0f || fAbs <= this.estimatedModuleSize;
    }

    public AlignmentPattern combineEstimate(float f2, float f3, float f4) {
        return new AlignmentPattern((getX() + f3) / 2.0f, (getY() + f2) / 2.0f, (this.estimatedModuleSize + f4) / 2.0f);
    }
}
