package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraDevice;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.camera.camera2.internal.compat.params.SessionConfigurationCompat;
import androidx.camera.core.impl.utils.MainThreadAsyncHandler;
import java.util.concurrent.Executor;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class CameraDeviceCompat {

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int SESSION_OPERATION_MODE_CONSTRAINED_HIGH_SPEED = 1;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int SESSION_OPERATION_MODE_NORMAL = 0;
    private final CameraDeviceCompatImpl mImpl;

    public interface CameraDeviceCompatImpl {
        void createCaptureSession(@NonNull SessionConfigurationCompat sessionConfigurationCompat) throws CameraAccessExceptionCompat;

        @NonNull
        CameraDevice unwrap();
    }

    @RequiresApi(21)
    public static final class StateCallbackExecutorWrapper extends CameraDevice.StateCallback {
        private final Executor mExecutor;
        final CameraDevice.StateCallback mWrappedCallback;

        public StateCallbackExecutorWrapper(@NonNull Executor executor, @NonNull CameraDevice.StateCallback stateCallback) {
            this.mExecutor = executor;
            this.mWrappedCallback = stateCallback;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onClosed$3(CameraDevice cameraDevice) {
            this.mWrappedCallback.onClosed(cameraDevice);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onDisconnected$1(CameraDevice cameraDevice) {
            this.mWrappedCallback.onDisconnected(cameraDevice);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$2(CameraDevice cameraDevice, int i2) {
            this.mWrappedCallback.onError(cameraDevice, i2);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onOpened$0(CameraDevice cameraDevice) {
            this.mWrappedCallback.onOpened(cameraDevice);
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onClosed(@NonNull final CameraDevice cameraDevice) {
            this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.compat.w
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1292c.lambda$onClosed$3(cameraDevice);
                }
            });
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(@NonNull final CameraDevice cameraDevice) {
            this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.compat.x
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1299c.lambda$onDisconnected$1(cameraDevice);
                }
            });
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(@NonNull final CameraDevice cameraDevice, final int i2) {
            this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.compat.z
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1303c.lambda$onError$2(cameraDevice, i2);
                }
            });
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(@NonNull final CameraDevice cameraDevice) {
            this.mExecutor.execute(new Runnable() { // from class: androidx.camera.camera2.internal.compat.y
                @Override // java.lang.Runnable
                public final void run() {
                    this.f1301c.lambda$onOpened$0(cameraDevice);
                }
            });
        }
    }

    private CameraDeviceCompat(@NonNull CameraDevice cameraDevice, @NonNull Handler handler) {
        int i2 = Build.VERSION.SDK_INT;
        if (i2 >= 28) {
            this.mImpl = new CameraDeviceCompatApi28Impl(cameraDevice);
        } else if (i2 >= 24) {
            this.mImpl = CameraDeviceCompatApi24Impl.create(cameraDevice, handler);
        } else {
            this.mImpl = CameraDeviceCompatApi23Impl.create(cameraDevice, handler);
        }
    }

    @NonNull
    public static CameraDeviceCompat toCameraDeviceCompat(@NonNull CameraDevice cameraDevice) {
        return toCameraDeviceCompat(cameraDevice, MainThreadAsyncHandler.getInstance());
    }

    public void createCaptureSession(@NonNull SessionConfigurationCompat sessionConfigurationCompat) throws CameraAccessExceptionCompat {
        this.mImpl.createCaptureSession(sessionConfigurationCompat);
    }

    @NonNull
    public CameraDevice toCameraDevice() {
        return this.mImpl.unwrap();
    }

    @NonNull
    public static CameraDeviceCompat toCameraDeviceCompat(@NonNull CameraDevice cameraDevice, @NonNull Handler handler) {
        return new CameraDeviceCompat(cameraDevice, handler);
    }
}
