package androidx.camera.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.annotation.RestrictTo;
import androidx.camera.core.Camera;
import androidx.camera.core.UseCaseGroup;
import androidx.camera.core.impl.utils.Threads;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.lifecycle.LifecycleOwner;
import com.hjq.permissions.Permission;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class LifecycleCameraController extends CameraController {
    private static final String TAG = "CamLifecycleController";

    @Nullable
    private LifecycleOwner mLifecycleOwner;

    public LifecycleCameraController(@NonNull Context context) {
        super(context);
    }

    @SuppressLint({"MissingPermission"})
    @MainThread
    public void bindToLifecycle(@NonNull LifecycleOwner lifecycleOwner) {
        Threads.checkMainThread();
        this.mLifecycleOwner = lifecycleOwner;
        startCameraAndTrackStates();
    }

    @RestrictTo({RestrictTo.Scope.TESTS})
    public void shutDownForTests() {
        ProcessCameraProvider processCameraProvider = this.mCameraProvider;
        if (processCameraProvider != null) {
            processCameraProvider.unbindAll();
            this.mCameraProvider.shutdown();
        }
    }

    @Override // androidx.camera.view.CameraController
    @Nullable
    @RequiresPermission(Permission.CAMERA)
    public Camera startCamera() {
        if (this.mLifecycleOwner == null) {
            Log.d(TAG, "Lifecycle is not set.");
            return null;
        }
        if (this.mCameraProvider == null) {
            Log.d(TAG, "CameraProvider is not ready.");
            return null;
        }
        UseCaseGroup useCaseGroupCreateUseCaseGroup = createUseCaseGroup();
        if (useCaseGroupCreateUseCaseGroup == null) {
            return null;
        }
        return this.mCameraProvider.bindToLifecycle(this.mLifecycleOwner, this.mCameraSelector, useCaseGroupCreateUseCaseGroup);
    }

    @MainThread
    public void unbind() {
        Threads.checkMainThread();
        this.mLifecycleOwner = null;
        this.mCamera = null;
        ProcessCameraProvider processCameraProvider = this.mCameraProvider;
        if (processCameraProvider != null) {
            processCameraProvider.unbindAll();
        }
    }
}
