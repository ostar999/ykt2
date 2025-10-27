package com.github.chrisbanes.photoview;

import android.widget.ImageView;
import androidx.core.view.MotionEventCompat;

/* loaded from: classes3.dex */
class Util {

    /* renamed from: com.github.chrisbanes.photoview.Util$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$android$widget$ImageView$ScaleType;

        static {
            int[] iArr = new int[ImageView.ScaleType.values().length];
            $SwitchMap$android$widget$ImageView$ScaleType = iArr;
            try {
                iArr[ImageView.ScaleType.MATRIX.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public static void checkZoomLevels(float f2, float f3, float f4) {
        if (f2 >= f3) {
            throw new IllegalArgumentException("Minimum zoom has to be less than Medium zoom. Call setMinimumZoom() with a more appropriate value");
        }
        if (f3 >= f4) {
            throw new IllegalArgumentException("Medium zoom has to be less than Maximum zoom. Call setMaximumZoom() with a more appropriate value");
        }
    }

    public static int getPointerIndex(int i2) {
        return (i2 & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
    }

    public static boolean hasDrawable(ImageView imageView) {
        return imageView.getDrawable() != null;
    }

    public static boolean isSupportedScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == null) {
            return false;
        }
        if (AnonymousClass1.$SwitchMap$android$widget$ImageView$ScaleType[scaleType.ordinal()] != 1) {
            return true;
        }
        throw new IllegalStateException("Matrix scale type is not supported");
    }
}
