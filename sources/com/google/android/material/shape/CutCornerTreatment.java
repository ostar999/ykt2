package com.google.android.material.shape;

import androidx.annotation.NonNull;

/* loaded from: classes3.dex */
public class CutCornerTreatment extends CornerTreatment {
    float size;

    public CutCornerTreatment() {
        this.size = -1.0f;
    }

    @Override // com.google.android.material.shape.CornerTreatment
    public void getCornerPath(@NonNull ShapePath shapePath, float f2, float f3, float f4) {
        shapePath.reset(0.0f, f4 * f3, 180.0f, 180.0f - f2);
        double d3 = f4;
        double d4 = f3;
        shapePath.lineTo((float) (Math.sin(Math.toRadians(f2)) * d3 * d4), (float) (Math.sin(Math.toRadians(90.0f - f2)) * d3 * d4));
    }

    @Deprecated
    public CutCornerTreatment(float f2) {
        this.size = f2;
    }
}
