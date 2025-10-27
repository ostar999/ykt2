package androidx.camera.core;

import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import com.google.common.util.concurrent.ListenableFuture;

@RequiresApi(21)
/* loaded from: classes.dex */
public interface CameraControl {

    public static final class OperationCanceledException extends Exception {
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public OperationCanceledException(@NonNull String str) {
            super(str);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public OperationCanceledException(@NonNull String str, @NonNull Throwable th) {
            super(str, th);
        }
    }

    @NonNull
    ListenableFuture<Void> cancelFocusAndMetering();

    @NonNull
    ListenableFuture<Void> enableTorch(boolean z2);

    @NonNull
    ListenableFuture<Integer> setExposureCompensationIndex(int i2);

    @NonNull
    ListenableFuture<Void> setLinearZoom(@FloatRange(from = 0.0d, to = 1.0d) float f2);

    @NonNull
    ListenableFuture<Void> setZoomRatio(float f2);

    @NonNull
    ListenableFuture<FocusMeteringResult> startFocusAndMetering(@NonNull FocusMeteringAction focusMeteringAction);
}
