package androidx.camera.camera2.internal.compat;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import com.hjq.permissions.Permission;
import java.util.concurrent.Executor;

@RequiresApi(29)
/* loaded from: classes.dex */
class CameraManagerCompatApi29Impl extends CameraManagerCompatApi28Impl {
    public CameraManagerCompatApi29Impl(@NonNull Context context) {
        super(context);
    }

    @Override // androidx.camera.camera2.internal.compat.CameraManagerCompatApi28Impl, androidx.camera.camera2.internal.compat.CameraManagerCompatBaseImpl, androidx.camera.camera2.internal.compat.CameraManagerCompat.CameraManagerCompatImpl
    @NonNull
    public CameraCharacteristics getCameraCharacteristics(@NonNull String str) throws CameraAccessExceptionCompat {
        try {
            return this.mCameraManager.getCameraCharacteristics(str);
        } catch (CameraAccessException e2) {
            throw CameraAccessExceptionCompat.toCameraAccessExceptionCompat(e2);
        }
    }

    @Override // androidx.camera.camera2.internal.compat.CameraManagerCompatApi28Impl, androidx.camera.camera2.internal.compat.CameraManagerCompatBaseImpl, androidx.camera.camera2.internal.compat.CameraManagerCompat.CameraManagerCompatImpl
    @RequiresPermission(Permission.CAMERA)
    public void openCamera(@NonNull String str, @NonNull Executor executor, @NonNull CameraDevice.StateCallback stateCallback) throws CameraAccessException, CameraAccessExceptionCompat {
        try {
            this.mCameraManager.openCamera(str, executor, stateCallback);
        } catch (CameraAccessException e2) {
            throw CameraAccessExceptionCompat.toCameraAccessExceptionCompat(e2);
        }
    }
}
