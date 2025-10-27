package org.wrtca.api;

import android.graphics.Matrix;
import android.graphics.Point;
import android.view.View;

/* loaded from: classes9.dex */
public class RendererCommon {
    private static float BALANCED_VISIBLE_FRACTION = 0.5625f;
    private static final String TAG = "RendererCommon";

    /* renamed from: org.wrtca.api.RendererCommon$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$org$wrtca$api$RendererCommon$ScalingType;

        static {
            int[] iArr = new int[ScalingType.values().length];
            $SwitchMap$org$wrtca$api$RendererCommon$ScalingType = iArr;
            try {
                iArr[ScalingType.SCALE_ASPECT_FIT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$wrtca$api$RendererCommon$ScalingType[ScalingType.SCALE_ASPECT_FILL.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$wrtca$api$RendererCommon$ScalingType[ScalingType.SCALE_ASPECT_BALANCED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public interface GlDrawer {
        void drawOes(int i2, float[] fArr, int i3, int i4, int i5, int i6, int i7, int i8);

        void drawRgb(int i2, float[] fArr, int i3, int i4, int i5, int i6, int i7, int i8);

        void drawYuv(int[] iArr, float[] fArr, int i2, int i3, int i4, int i5, int i6, int i7);

        void release();
    }

    public interface RendererEvents {
        void onFirstFrameRendered();

        void onFrameResolutionChanged(int i2, int i3, int i4);
    }

    public enum ScalingType {
        SCALE_ASPECT_FIT,
        SCALE_ASPECT_FILL,
        SCALE_ASPECT_BALANCED
    }

    private static void adjustOrigin(float[] fArr) {
        float f2 = fArr[12] - ((fArr[0] + fArr[4]) * 0.5f);
        fArr[12] = f2;
        float f3 = fArr[13] - ((fArr[1] + fArr[5]) * 0.5f);
        fArr[13] = f3;
        fArr[12] = f2 + 0.5f;
        fArr[13] = f3 + 0.5f;
    }

    public static float[] convertMatrixFromAndroidGraphicsMatrix(Matrix matrix) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        return new float[]{fArr[0], fArr[3], 0.0f, fArr[6], fArr[1], fArr[4], 0.0f, fArr[7], 0.0f, 0.0f, 1.0f, 0.0f, fArr[2], fArr[5], 0.0f, fArr[8]};
    }

    public static Matrix convertMatrixToAndroidGraphicsMatrix(float[] fArr) {
        float[] fArr2 = {fArr[0], fArr[4], fArr[12], fArr[1], fArr[5], fArr[13], fArr[3], fArr[7], fArr[15]};
        Matrix matrix = new Matrix();
        matrix.setValues(fArr2);
        return matrix;
    }

    private static float convertScalingTypeToVisibleFraction(ScalingType scalingType) {
        int i2 = AnonymousClass1.$SwitchMap$org$wrtca$api$RendererCommon$ScalingType[scalingType.ordinal()];
        if (i2 == 1) {
            return 1.0f;
        }
        if (i2 == 2) {
            return 0.0f;
        }
        if (i2 == 3) {
            return BALANCED_VISIBLE_FRACTION;
        }
        throw new IllegalArgumentException();
    }

    public static Point getDisplaySize(ScalingType scalingType, float f2, int i2, int i3) {
        return getDisplaySize(convertScalingTypeToVisibleFraction(scalingType), f2, i2, i3);
    }

    public static float[] getLayoutMatrix(boolean z2, float f2, float f3) {
        float f4;
        float f5;
        if (f3 > f2) {
            f5 = f2 / f3;
            f4 = 1.0f;
        } else {
            f4 = f3 / f2;
            f5 = 1.0f;
        }
        if (z2) {
            f4 *= -1.0f;
        }
        float[] fArr = new float[16];
        android.opengl.Matrix.setIdentityM(fArr, 0);
        android.opengl.Matrix.scaleM(fArr, 0, f4, f5, 1.0f);
        adjustOrigin(fArr);
        return fArr;
    }

    public static final float[] horizontalFlipMatrix() {
        return new float[]{-1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f};
    }

    public static final float[] identityMatrix() {
        return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f};
    }

    public static float[] multiplyMatrices(float[] fArr, float[] fArr2) {
        float[] fArr3 = new float[16];
        android.opengl.Matrix.multiplyMM(fArr3, 0, fArr, 0, fArr2, 0);
        return fArr3;
    }

    public static float[] rotateTextureMatrix(float[] fArr, float f2) {
        float[] fArr2 = new float[16];
        android.opengl.Matrix.setRotateM(fArr2, 0, f2, 0.0f, 0.0f, 1.0f);
        adjustOrigin(fArr2);
        return multiplyMatrices(fArr, fArr2);
    }

    public static final float[] verticalFlipMatrix() {
        return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f};
    }

    public static class VideoLayoutMeasure {
        private ScalingType scalingTypeMatchOrientation;
        private ScalingType scalingTypeMismatchOrientation;

        public VideoLayoutMeasure() {
            ScalingType scalingType = ScalingType.SCALE_ASPECT_BALANCED;
            this.scalingTypeMatchOrientation = scalingType;
            this.scalingTypeMismatchOrientation = scalingType;
        }

        public Point measure(int i2, int i3, int i4, int i5) {
            int defaultSize = View.getDefaultSize(Integer.MAX_VALUE, i2);
            int defaultSize2 = View.getDefaultSize(Integer.MAX_VALUE, i3);
            c.h.a(RendererCommon.TAG, "RendererCommonmeasure maxWidth: " + defaultSize + "maxHeight: " + defaultSize2 + "frameWidth: " + i4 + "frameHeight: " + i5);
            if (i4 == 0 || i5 == 0 || defaultSize == 0 || defaultSize2 == 0) {
                return new Point(defaultSize, defaultSize2);
            }
            float f2 = i4 / i5;
            ScalingType scalingType = ((f2 > 1.0f ? 1 : (f2 == 1.0f ? 0 : -1)) > 0) == (((float) defaultSize) / ((float) defaultSize2) > 1.0f) ? this.scalingTypeMatchOrientation : this.scalingTypeMismatchOrientation;
            c.h.a(RendererCommon.TAG, "RendererCommonmeasure scalingType: " + scalingType);
            Point displaySize = RendererCommon.getDisplaySize(scalingType, f2, defaultSize, defaultSize2);
            if (View.MeasureSpec.getMode(i2) == 1073741824) {
                displaySize.x = defaultSize;
            }
            if (View.MeasureSpec.getMode(i3) == 1073741824) {
                displaySize.y = defaultSize2;
            }
            return displaySize;
        }

        public void setScalingType(ScalingType scalingType) {
            this.scalingTypeMatchOrientation = scalingType;
            this.scalingTypeMismatchOrientation = scalingType;
        }

        public void setScalingType(ScalingType scalingType, ScalingType scalingType2) {
            this.scalingTypeMatchOrientation = scalingType;
            this.scalingTypeMismatchOrientation = scalingType2;
        }
    }

    private static Point getDisplaySize(float f2, float f3, int i2, int i3) {
        return (f2 == 0.0f || f3 == 0.0f) ? new Point(i2, i3) : new Point(Math.min(i2, Math.round((i3 / f2) * f3)), Math.min(i3, Math.round((i2 / f2) / f3)));
    }
}
