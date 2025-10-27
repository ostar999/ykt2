package androidx.camera.camera2.internal.compat;

import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.params.SessionConfiguration;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.internal.compat.params.SessionConfigurationCompat;
import androidx.core.util.Preconditions;

@RequiresApi(28)
/* loaded from: classes.dex */
class CameraDeviceCompatApi28Impl extends CameraDeviceCompatApi24Impl {
    public CameraDeviceCompatApi28Impl(@NonNull CameraDevice cameraDevice) {
        super((CameraDevice) Preconditions.checkNotNull(cameraDevice), null);
    }

    @Override // androidx.camera.camera2.internal.compat.CameraDeviceCompatApi24Impl, androidx.camera.camera2.internal.compat.CameraDeviceCompatApi23Impl, androidx.camera.camera2.internal.compat.CameraDeviceCompatBaseImpl, androidx.camera.camera2.internal.compat.CameraDeviceCompat.CameraDeviceCompatImpl
    public void createCaptureSession(@NonNull SessionConfigurationCompat sessionConfigurationCompat) throws CameraAccessException, CameraAccessExceptionCompat {
        SessionConfiguration sessionConfiguration = (SessionConfiguration) sessionConfigurationCompat.unwrap();
        Preconditions.checkNotNull(sessionConfiguration);
        try {
            this.mCameraDevice.createCaptureSession(sessionConfiguration);
        } catch (CameraAccessException e2) {
            throw CameraAccessExceptionCompat.toCameraAccessExceptionCompat(e2);
        }
    }
}
