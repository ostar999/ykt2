package com.airbnb.lottie.model.content;

import com.airbnb.lottie.utils.GammaEvaluator;
import com.airbnb.lottie.utils.MiscUtils;

/* loaded from: classes2.dex */
public class GradientColor {
    private final int[] colors;
    private final float[] positions;

    public GradientColor(float[] fArr, int[] iArr) {
        this.positions = fArr;
        this.colors = iArr;
    }

    public int[] getColors() {
        return this.colors;
    }

    public float[] getPositions() {
        return this.positions;
    }

    public int getSize() {
        return this.colors.length;
    }

    public void lerp(GradientColor gradientColor, GradientColor gradientColor2, float f2) {
        if (gradientColor.colors.length == gradientColor2.colors.length) {
            for (int i2 = 0; i2 < gradientColor.colors.length; i2++) {
                this.positions[i2] = MiscUtils.lerp(gradientColor.positions[i2], gradientColor2.positions[i2], f2);
                this.colors[i2] = GammaEvaluator.evaluate(f2, gradientColor.colors[i2], gradientColor2.colors[i2]);
            }
            return;
        }
        throw new IllegalArgumentException("Cannot interpolate between gradients. Lengths vary (" + gradientColor.colors.length + " vs " + gradientColor2.colors.length + ")");
    }
}
