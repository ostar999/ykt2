package androidx.core.graphics;

import android.graphics.Path;
import android.graphics.PointF;
import androidx.annotation.DoNotInline;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes.dex */
public final class PathUtils {

    @RequiresApi(26)
    public static class Api26Impl {
        private Api26Impl() {
        }

        @DoNotInline
        public static float[] approximate(Path path, float f2) {
            return path.approximate(f2);
        }
    }

    private PathUtils() {
    }

    @NonNull
    @RequiresApi(26)
    public static Collection<PathSegment> flatten(@NonNull Path path) {
        return flatten(path, 0.5f);
    }

    @NonNull
    @RequiresApi(26)
    public static Collection<PathSegment> flatten(@NonNull Path path, @FloatRange(from = 0.0d) float f2) {
        float[] fArrApproximate = Api26Impl.approximate(path, f2);
        int length = fArrApproximate.length / 3;
        ArrayList arrayList = new ArrayList(length);
        for (int i2 = 1; i2 < length; i2++) {
            int i3 = i2 * 3;
            int i4 = (i2 - 1) * 3;
            float f3 = fArrApproximate[i3];
            float f4 = fArrApproximate[i3 + 1];
            float f5 = fArrApproximate[i3 + 2];
            float f6 = fArrApproximate[i4];
            float f7 = fArrApproximate[i4 + 1];
            float f8 = fArrApproximate[i4 + 2];
            if (f3 != f6 && (f4 != f7 || f5 != f8)) {
                arrayList.add(new PathSegment(new PointF(f7, f8), f6, new PointF(f4, f5), f3));
            }
        }
        return arrayList;
    }
}
