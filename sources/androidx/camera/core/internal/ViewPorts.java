package androidx.camera.core.internal;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Rational;
import android.util.Size;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.UseCase;
import androidx.camera.core.internal.utils.ImageUtil;
import androidx.core.util.Preconditions;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(21)
/* loaded from: classes.dex */
public class ViewPorts {
    private ViewPorts() {
    }

    @NonNull
    public static Map<UseCase, Rect> calculateViewPortRects(@NonNull Rect rect, boolean z2, @NonNull Rational rational, @IntRange(from = 0, to = 359) int i2, int i3, int i4, @NonNull Map<UseCase, Size> map) {
        Preconditions.checkArgument(rect.width() > 0 && rect.height() > 0, "Cannot compute viewport crop rects zero sized sensor rect.");
        RectF rectF = new RectF(rect);
        HashMap map2 = new HashMap();
        RectF rectF2 = new RectF(rect);
        for (Map.Entry<UseCase, Size> entry : map.entrySet()) {
            Matrix matrix = new Matrix();
            RectF rectF3 = new RectF(0.0f, 0.0f, entry.getValue().getWidth(), entry.getValue().getHeight());
            matrix.setRectToRect(rectF3, rectF, Matrix.ScaleToFit.CENTER);
            map2.put(entry.getKey(), matrix);
            RectF rectF4 = new RectF();
            matrix.mapRect(rectF4, rectF3);
            rectF2.intersect(rectF4);
        }
        RectF scaledRect = getScaledRect(rectF2, ImageUtil.getRotatedAspectRatio(i2, rational), i3, z2, i4, i2);
        HashMap map3 = new HashMap();
        RectF rectF5 = new RectF();
        Matrix matrix2 = new Matrix();
        for (Map.Entry entry2 : map2.entrySet()) {
            ((Matrix) entry2.getValue()).invert(matrix2);
            matrix2.mapRect(rectF5, scaledRect);
            Rect rect2 = new Rect();
            rectF5.round(rect2);
            map3.put((UseCase) entry2.getKey(), rect2);
        }
        return map3;
    }

    private static RectF correctStartOrEnd(boolean z2, @IntRange(from = 0, to = 359) int i2, RectF rectF, RectF rectF2) {
        boolean z3 = i2 == 0 && !z2;
        boolean z4 = i2 == 90 && z2;
        if (z3 || z4) {
            return rectF2;
        }
        boolean z5 = i2 == 0 && z2;
        boolean z6 = i2 == 270 && !z2;
        if (z5 || z6) {
            return flipHorizontally(rectF2, rectF.centerX());
        }
        boolean z7 = i2 == 90 && !z2;
        boolean z8 = i2 == 180 && z2;
        if (z7 || z8) {
            return flipVertically(rectF2, rectF.centerY());
        }
        boolean z9 = i2 == 180 && !z2;
        boolean z10 = i2 == 270 && z2;
        if (z9 || z10) {
            return flipHorizontally(flipVertically(rectF2, rectF.centerY()), rectF.centerX());
        }
        throw new IllegalArgumentException("Invalid argument: mirrored " + z2 + " rotation " + i2);
    }

    private static RectF flipHorizontally(RectF rectF, float f2) {
        return new RectF(flipX(rectF.right, f2), rectF.top, flipX(rectF.left, f2), rectF.bottom);
    }

    private static RectF flipVertically(RectF rectF, float f2) {
        return new RectF(rectF.left, flipY(rectF.bottom, f2), rectF.right, flipY(rectF.top, f2));
    }

    private static float flipX(float f2, float f3) {
        return (f3 + f3) - f2;
    }

    private static float flipY(float f2, float f3) {
        return (f3 + f3) - f2;
    }

    @NonNull
    @SuppressLint({"SwitchIntDef"})
    public static RectF getScaledRect(@NonNull RectF rectF, @NonNull Rational rational, int i2, boolean z2, int i3, @IntRange(from = 0, to = 359) int i4) {
        if (i2 == 3) {
            return rectF;
        }
        Matrix matrix = new Matrix();
        RectF rectF2 = new RectF(0.0f, 0.0f, rational.getNumerator(), rational.getDenominator());
        if (i2 == 0) {
            matrix.setRectToRect(rectF2, rectF, Matrix.ScaleToFit.START);
        } else if (i2 == 1) {
            matrix.setRectToRect(rectF2, rectF, Matrix.ScaleToFit.CENTER);
        } else {
            if (i2 != 2) {
                throw new IllegalStateException("Unexpected scale type: " + i2);
            }
            matrix.setRectToRect(rectF2, rectF, Matrix.ScaleToFit.END);
        }
        RectF rectF3 = new RectF();
        matrix.mapRect(rectF3, rectF2);
        return correctStartOrEnd(shouldMirrorStartAndEnd(z2, i3), i4, rectF, rectF3);
    }

    private static boolean shouldMirrorStartAndEnd(boolean z2, int i2) {
        return z2 ^ (i2 == 1);
    }
}
