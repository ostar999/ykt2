package androidx.camera.core.impl.utils;

import android.util.Rational;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.internal.utils.SizeUtil;
import androidx.core.util.Preconditions;
import java.util.Comparator;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class AspectRatioUtil {
    private static final int ALIGN16 = 16;
    public static final Rational ASPECT_RATIO_4_3 = new Rational(4, 3);
    public static final Rational ASPECT_RATIO_3_4 = new Rational(3, 4);
    public static final Rational ASPECT_RATIO_16_9 = new Rational(16, 9);
    public static final Rational ASPECT_RATIO_9_16 = new Rational(9, 16);

    @RequiresApi(21)
    public static final class CompareAspectRatiosByDistanceToTargetRatio implements Comparator<Rational> {
        private Rational mTargetRatio;

        public CompareAspectRatiosByDistanceToTargetRatio(@NonNull Rational rational) {
            this.mTargetRatio = rational;
        }

        @Override // java.util.Comparator
        public int compare(Rational rational, Rational rational2) {
            if (rational.equals(rational2)) {
                return 0;
            }
            return (int) Math.signum(Float.valueOf(Math.abs(rational.floatValue() - this.mTargetRatio.floatValue())).floatValue() - Float.valueOf(Math.abs(rational2.floatValue() - this.mTargetRatio.floatValue())).floatValue());
        }
    }

    private AspectRatioUtil() {
    }

    public static boolean hasMatchingAspectRatio(@NonNull Size size, @Nullable Rational rational) {
        if (rational == null) {
            return false;
        }
        if (rational.equals(new Rational(size.getWidth(), size.getHeight()))) {
            return true;
        }
        if (SizeUtil.getArea(size) >= SizeUtil.getArea(SizeUtil.RESOLUTION_VGA)) {
            return isPossibleMod16FromAspectRatio(size, rational);
        }
        return false;
    }

    private static boolean isPossibleMod16FromAspectRatio(@NonNull Size size, @NonNull Rational rational) {
        int width = size.getWidth();
        int height = size.getHeight();
        Rational rational2 = new Rational(rational.getDenominator(), rational.getNumerator());
        int i2 = width % 16;
        if (i2 == 0 && height % 16 == 0) {
            return ratioIntersectsMod16Segment(Math.max(0, height + (-16)), width, rational) || ratioIntersectsMod16Segment(Math.max(0, width + (-16)), height, rational2);
        }
        if (i2 == 0) {
            return ratioIntersectsMod16Segment(height, width, rational);
        }
        if (height % 16 == 0) {
            return ratioIntersectsMod16Segment(width, height, rational2);
        }
        return false;
    }

    private static boolean ratioIntersectsMod16Segment(int i2, int i3, Rational rational) {
        Preconditions.checkArgument(i3 % 16 == 0);
        double numerator = (i2 * rational.getNumerator()) / rational.getDenominator();
        return numerator > ((double) Math.max(0, i3 + (-16))) && numerator < ((double) (i3 + 16));
    }
}
