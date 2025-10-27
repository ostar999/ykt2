package androidx.camera.camera2.internal;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.internal.Camera2CameraControlImpl;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.camera2.internal.compat.workaround.FlashAvailabilityChecker;
import androidx.camera.core.CameraControl;
import androidx.camera.core.Logger;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.core.impl.utils.futures.Futures;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.concurrent.Executor;

@RequiresApi(21)
/* loaded from: classes.dex */
final class TorchControl {
    static final int DEFAULT_TORCH_STATE = 0;
    private static final String TAG = "TorchControl";
    private final Camera2CameraControlImpl mCamera2CameraControlImpl;
    CallbackToFutureAdapter.Completer<Void> mEnableTorchCompleter;
    private final Executor mExecutor;
    private final boolean mHasFlashUnit;
    private boolean mIsActive;
    boolean mTargetTorchEnabled;
    private final MutableLiveData<Integer> mTorchState = new MutableLiveData<>(0);

    public TorchControl(@NonNull Camera2CameraControlImpl camera2CameraControlImpl, @NonNull CameraCharacteristicsCompat cameraCharacteristicsCompat, @NonNull Executor executor) {
        this.mCamera2CameraControlImpl = camera2CameraControlImpl;
        this.mExecutor = executor;
        this.mHasFlashUnit = FlashAvailabilityChecker.isFlashAvailable(cameraCharacteristicsCompat);
        camera2CameraControlImpl.addCaptureResultListener(new Camera2CameraControlImpl.CaptureResultListener() { // from class: androidx.camera.camera2.internal.i2
            @Override // androidx.camera.camera2.internal.Camera2CameraControlImpl.CaptureResultListener
            public final boolean onCaptureResult(TotalCaptureResult totalCaptureResult) {
                return this.f1352a.lambda$new$0(totalCaptureResult);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$enableTorch$2(final boolean z2, final CallbackToFutureAdapter.Completer completer) throws Exception {
        this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.g2
            @Override // java.lang.Runnable
            public final void run() {
                this.f1331c.lambda$enableTorch$1(completer, z2);
            }
        });
        return "enableTorch: " + z2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$0(TotalCaptureResult totalCaptureResult) {
        if (this.mEnableTorchCompleter != null) {
            Integer num = (Integer) totalCaptureResult.getRequest().get(CaptureRequest.FLASH_MODE);
            if ((num != null && num.intValue() == 2) == this.mTargetTorchEnabled) {
                this.mEnableTorchCompleter.set(null);
                this.mEnableTorchCompleter = null;
            }
        }
        return false;
    }

    private <T> void setLiveDataValue(@NonNull MutableLiveData<T> mutableLiveData, T t2) {
        if (Threads.isMainThread()) {
            mutableLiveData.setValue(t2);
        } else {
            mutableLiveData.postValue(t2);
        }
    }

    public ListenableFuture<Void> enableTorch(final boolean z2) {
        if (this.mHasFlashUnit) {
            setLiveDataValue(this.mTorchState, Integer.valueOf(z2 ? 1 : 0));
            return CallbackToFutureAdapter.getFuture(new CallbackToFutureAdapter.Resolver() { // from class: androidx.camera.camera2.internal.h2
                @Override // androidx.concurrent.futures.CallbackToFutureAdapter.Resolver
                public final Object attachCompleter(CallbackToFutureAdapter.Completer completer) {
                    return this.f1341a.lambda$enableTorch$2(z2, completer);
                }
            });
        }
        Logger.d(TAG, "Unable to enableTorch due to there is no flash unit.");
        return Futures.immediateFailedFuture(new IllegalStateException("No flash unit"));
    }

    /* renamed from: enableTorchInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$enableTorch$1(@Nullable CallbackToFutureAdapter.Completer<Void> completer, boolean z2) {
        if (!this.mHasFlashUnit) {
            if (completer != null) {
                completer.setException(new IllegalStateException("No flash unit"));
            }
        } else {
            if (!this.mIsActive) {
                setLiveDataValue(this.mTorchState, 0);
                if (completer != null) {
                    completer.setException(new CameraControl.OperationCanceledException("Camera is not active."));
                    return;
                }
                return;
            }
            this.mTargetTorchEnabled = z2;
            this.mCamera2CameraControlImpl.enableTorchInternal(z2);
            setLiveDataValue(this.mTorchState, Integer.valueOf(z2 ? 1 : 0));
            CallbackToFutureAdapter.Completer<Void> completer2 = this.mEnableTorchCompleter;
            if (completer2 != null) {
                completer2.setException(new CameraControl.OperationCanceledException("There is a new enableTorch being set"));
            }
            this.mEnableTorchCompleter = completer;
        }
    }

    @NonNull
    public LiveData<Integer> getTorchState() {
        return this.mTorchState;
    }

    public void setActive(boolean z2) {
        if (this.mIsActive == z2) {
            return;
        }
        this.mIsActive = z2;
        if (z2) {
            return;
        }
        if (this.mTargetTorchEnabled) {
            this.mTargetTorchEnabled = false;
            this.mCamera2CameraControlImpl.enableTorchInternal(false);
            setLiveDataValue(this.mTorchState, 0);
        }
        CallbackToFutureAdapter.Completer<Void> completer = this.mEnableTorchCompleter;
        if (completer != null) {
            completer.setException(new CameraControl.OperationCanceledException("Camera is not active."));
            this.mEnableTorchCompleter = null;
        }
    }
}
