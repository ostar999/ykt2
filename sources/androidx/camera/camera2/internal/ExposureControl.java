package androidx.camera.camera2.internal;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.TotalCaptureResult;
import android.util.Range;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.impl.Camera2ImplConfig;
import androidx.camera.camera2.internal.Camera2CameraControlImpl;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;
import androidx.camera.core.CameraControl;
import androidx.camera.core.ExposureState;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.util.Preconditions;
import cn.hutool.core.text.StrPool;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;

@RequiresApi(21)
/* loaded from: classes.dex */
public class ExposureControl {
    private static final int DEFAULT_EXPOSURE_COMPENSATION = 0;

    @NonNull
    private final Camera2CameraControlImpl mCameraControl;

    @NonNull
    private final Executor mExecutor;

    @NonNull
    private final ExposureStateImpl mExposureStateImpl;
    private boolean mIsActive = false;

    @Nullable
    private Camera2CameraControlImpl.CaptureResultListener mRunningCaptureResultListener;

    @Nullable
    private CallbackToFutureAdapter.Completer<Integer> mRunningCompleter;

    public ExposureControl(@NonNull Camera2CameraControlImpl camera2CameraControlImpl, @NonNull CameraCharacteristicsCompat cameraCharacteristicsCompat, @NonNull Executor executor) {
        this.mCameraControl = camera2CameraControlImpl;
        this.mExposureStateImpl = new ExposureStateImpl(cameraCharacteristicsCompat, 0);
        this.mExecutor = executor;
    }

    private void clearRunningTask() {
        CallbackToFutureAdapter.Completer<Integer> completer = this.mRunningCompleter;
        if (completer != null) {
            completer.setException(new CameraControl.OperationCanceledException("Cancelled by another setExposureCompensationIndex()"));
            this.mRunningCompleter = null;
        }
        Camera2CameraControlImpl.CaptureResultListener captureResultListener = this.mRunningCaptureResultListener;
        if (captureResultListener != null) {
            this.mCameraControl.removeCaptureResultListener(captureResultListener);
            this.mRunningCaptureResultListener = null;
        }
    }

    public static ExposureState getDefaultExposureState(CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        return new ExposureStateImpl(cameraCharacteristicsCompat, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$setExposureCompensationIndex$0(int i2, CallbackToFutureAdapter.Completer completer, TotalCaptureResult totalCaptureResult) {
        Integer num = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_STATE);
        Integer num2 = (Integer) totalCaptureResult.get(CaptureResult.CONTROL_AE_EXPOSURE_COMPENSATION);
        if (num == null || num2 == null) {
            if (num2 == null || num2.intValue() != i2) {
                return false;
            }
            completer.set(Integer.valueOf(i2));
            return true;
        }
        int iIntValue = num.intValue();
        if ((iIntValue != 2 && iIntValue != 3 && iIntValue != 4) || num2.intValue() != i2) {
            return false;
        }
        completer.set(Integer.valueOf(i2));
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setExposureCompensationIndex$1(final CallbackToFutureAdapter.Completer completer, final int i2) {
        if (!this.mIsActive) {
            this.mExposureStateImpl.setExposureCompensationIndex(0);
            completer.setException(new CameraControl.OperationCanceledException("Camera is not active."));
            return;
        }
        clearRunningTask();
        Preconditions.checkState(this.mRunningCompleter == null, "mRunningCompleter should be null when starting set a new exposure compensation value");
        Preconditions.checkState(this.mRunningCaptureResultListener == null, "mRunningCaptureResultListener should be null when starting set a new exposure compensation value");
        Camera2CameraControlImpl.CaptureResultListener captureResultListener = new Camera2CameraControlImpl.CaptureResultListener() { // from class: androidx.camera.camera2.internal.f1
            @Override // androidx.camera.camera2.internal.Camera2CameraControlImpl.CaptureResultListener
            public final boolean onCaptureResult(TotalCaptureResult totalCaptureResult) {
                return ExposureControl.lambda$setExposureCompensationIndex$0(i2, completer, totalCaptureResult);
            }
        };
        this.mRunningCaptureResultListener = captureResultListener;
        this.mRunningCompleter = completer;
        this.mCameraControl.addCaptureResultListener(captureResultListener);
        this.mCameraControl.updateSessionConfigSynchronous();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$setExposureCompensationIndex$2(final int i2, final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.d1
            @Override // java.lang.Runnable
            public final void run() {
                this.f1309c.lambda$setExposureCompensationIndex$1(completer, i2);
            }
        });
        return "setExposureCompensationIndex[" + i2 + StrPool.BRACKET_END;
    }

    @NonNull
    public ExposureState getExposureState() {
        return this.mExposureStateImpl;
    }

    public void setActive(boolean z2) {
        if (z2 == this.mIsActive) {
            return;
        }
        this.mIsActive = z2;
        if (z2) {
            return;
        }
        this.mExposureStateImpl.setExposureCompensationIndex(0);
        clearRunningTask();
    }

    @OptIn(markerClass = {ExperimentalCamera2Interop.class})
    public void setCaptureRequestOption(@NonNull Camera2ImplConfig.Builder builder) {
        builder.setCaptureRequestOption(CaptureRequest.CONTROL_AE_EXPOSURE_COMPENSATION, Integer.valueOf(this.mExposureStateImpl.getExposureCompensationIndex()));
    }

    @NonNull
    public ListenableFuture<Integer> setExposureCompensationIndex(final int i2) {
        if (!this.mExposureStateImpl.isExposureCompensationSupported()) {
            return Futures.immediateFailedFuture(new IllegalArgumentException("ExposureCompensation is not supported"));
        }
        Range<Integer> exposureCompensationRange = this.mExposureStateImpl.getExposureCompensationRange();
        if (exposureCompensationRange.contains((Range<Integer>) Integer.valueOf(i2))) {
            this.mExposureStateImpl.setExposureCompensationIndex(i2);
            return Futures.nonCancellationPropagating(CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.e1
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f1317a.lambda$setExposureCompensationIndex$2(i2, completer);
                }
            }));
        }
        return Futures.immediateFailedFuture(new IllegalArgumentException("Requested ExposureCompensation " + i2 + " is not within valid range [" + exposureCompensationRange.getUpper() + StrPool.DOUBLE_DOT + exposureCompensationRange.getLower() + StrPool.BRACKET_END));
    }
}
