package com.google.android.material.bottomappbar;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import com.google.android.material.shape.EdgeTreatment;
import com.google.android.material.shape.ShapePath;

/* loaded from: classes3.dex */
public class BottomAppBarTopEdgeTreatment extends EdgeTreatment implements Cloneable {
    private static final int ANGLE_LEFT = 180;
    private static final int ANGLE_UP = 270;
    private static final int ARC_HALF = 180;
    private static final int ARC_QUARTER = 90;
    private static final float ROUNDED_CORNER_FAB_OFFSET = 1.75f;
    private float cradleVerticalOffset;
    private float fabCornerSize = -1.0f;
    private float fabDiameter;
    private float fabMargin;
    private float horizontalOffset;
    private float roundedCornerRadius;

    public BottomAppBarTopEdgeTreatment(float f2, float f3, float f4) {
        this.fabMargin = f2;
        this.roundedCornerRadius = f3;
        setCradleVerticalOffset(f4);
        this.horizontalOffset = 0.0f;
    }

    public float getCradleVerticalOffset() {
        return this.cradleVerticalOffset;
    }

    @Override // com.google.android.material.shape.EdgeTreatment
    public void getEdgePath(float f2, float f3, float f4, @NonNull ShapePath shapePath) {
        float f5;
        float f6;
        float f7 = this.fabDiameter;
        if (f7 == 0.0f) {
            shapePath.lineTo(f2, 0.0f);
            return;
        }
        float f8 = ((this.fabMargin * 2.0f) + f7) / 2.0f;
        float f9 = f4 * this.roundedCornerRadius;
        float f10 = f3 + this.horizontalOffset;
        float f11 = (this.cradleVerticalOffset * f4) + ((1.0f - f4) * f8);
        if (f11 / f8 >= 1.0f) {
            shapePath.lineTo(f2, 0.0f);
            return;
        }
        float f12 = this.fabCornerSize;
        float f13 = f12 * f4;
        boolean z2 = f12 == -1.0f || Math.abs((f12 * 2.0f) - f7) < 0.1f;
        if (z2) {
            f5 = f11;
            f6 = 0.0f;
        } else {
            f6 = 1.75f;
            f5 = 0.0f;
        }
        float f14 = f8 + f9;
        float f15 = f5 + f9;
        float fSqrt = (float) Math.sqrt((f14 * f14) - (f15 * f15));
        float f16 = f10 - fSqrt;
        float f17 = f10 + fSqrt;
        float degrees = (float) Math.toDegrees(Math.atan(fSqrt / f15));
        float f18 = (90.0f - degrees) + f6;
        shapePath.lineTo(f16, 0.0f);
        float f19 = f9 * 2.0f;
        shapePath.addArc(f16 - f9, 0.0f, f16 + f9, f19, 270.0f, degrees);
        if (z2) {
            shapePath.addArc(f10 - f8, (-f8) - f5, f10 + f8, f8 - f5, 180.0f - f18, (f18 * 2.0f) - 180.0f);
        } else {
            float f20 = this.fabMargin;
            float f21 = f13 * 2.0f;
            float f22 = f10 - f8;
            shapePath.addArc(f22, -(f13 + f20), f22 + f20 + f21, f20 + f13, 180.0f - f18, ((f18 * 2.0f) - 180.0f) / 2.0f);
            float f23 = f10 + f8;
            float f24 = this.fabMargin;
            shapePath.lineTo(f23 - ((f24 / 2.0f) + f13), f24 + f13);
            float f25 = this.fabMargin;
            shapePath.addArc(f23 - (f21 + f25), -(f13 + f25), f23, f25 + f13, 90.0f, f18 - 90.0f);
        }
        shapePath.addArc(f17 - f9, 0.0f, f17 + f9, f19, 270.0f - degrees, degrees);
        shapePath.lineTo(f2, 0.0f);
    }

    public float getFabCornerRadius() {
        return this.fabCornerSize;
    }

    public float getFabCradleMargin() {
        return this.fabMargin;
    }

    public float getFabCradleRoundedCornerRadius() {
        return this.roundedCornerRadius;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public float getFabDiameter() {
        return this.fabDiameter;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public float getHorizontalOffset() {
        return this.horizontalOffset;
    }

    public void setCradleVerticalOffset(@FloatRange(from = 0.0d) float f2) {
        if (f2 < 0.0f) {
            throw new IllegalArgumentException("cradleVerticalOffset must be positive.");
        }
        this.cradleVerticalOffset = f2;
    }

    public void setFabCornerSize(float f2) {
        this.fabCornerSize = f2;
    }

    public void setFabCradleMargin(float f2) {
        this.fabMargin = f2;
    }

    public void setFabCradleRoundedCornerRadius(float f2) {
        this.roundedCornerRadius = f2;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setFabDiameter(float f2) {
        this.fabDiameter = f2;
    }

    public void setHorizontalOffset(float f2) {
        this.horizontalOffset = f2;
    }
}
