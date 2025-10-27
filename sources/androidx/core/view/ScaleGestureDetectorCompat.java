package androidx.core.view;

import android.view.ScaleGestureDetector;
import androidx.annotation.DoNotInline;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/* loaded from: classes.dex */
public final class ScaleGestureDetectorCompat {

    @RequiresApi(19)
    public static class Api19Impl {
        private Api19Impl() {
        }

        @DoNotInline
        public static boolean isQuickScaleEnabled(ScaleGestureDetector scaleGestureDetector) {
            return scaleGestureDetector.isQuickScaleEnabled();
        }

        @DoNotInline
        public static void setQuickScaleEnabled(ScaleGestureDetector scaleGestureDetector, boolean z2) {
            scaleGestureDetector.setQuickScaleEnabled(z2);
        }
    }

    private ScaleGestureDetectorCompat() {
    }

    @Deprecated
    public static boolean isQuickScaleEnabled(Object obj) {
        return isQuickScaleEnabled((ScaleGestureDetector) obj);
    }

    @Deprecated
    public static void setQuickScaleEnabled(Object obj, boolean z2) {
        setQuickScaleEnabled((ScaleGestureDetector) obj, z2);
    }

    public static boolean isQuickScaleEnabled(@NonNull ScaleGestureDetector scaleGestureDetector) {
        return Api19Impl.isQuickScaleEnabled(scaleGestureDetector);
    }

    public static void setQuickScaleEnabled(@NonNull ScaleGestureDetector scaleGestureDetector, boolean z2) {
        Api19Impl.setQuickScaleEnabled(scaleGestureDetector, z2);
    }
}
