package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraDevice;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class CameraDeviceStateCallbacks {

    public static final class ComboDeviceStateCallback extends CameraDevice.StateCallback {
        private final List<CameraDevice.StateCallback> mCallbacks = new ArrayList();

        public ComboDeviceStateCallback(@NonNull List<CameraDevice.StateCallback> list) {
            for (CameraDevice.StateCallback stateCallback : list) {
                if (!(stateCallback instanceof NoOpDeviceStateCallback)) {
                    this.mCallbacks.add(stateCallback);
                }
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onClosed(@NonNull CameraDevice cameraDevice) {
            Iterator<CameraDevice.StateCallback> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onClosed(cameraDevice);
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
            Iterator<CameraDevice.StateCallback> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onDisconnected(cameraDevice);
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(@NonNull CameraDevice cameraDevice, int i2) {
            Iterator<CameraDevice.StateCallback> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onError(cameraDevice, i2);
            }
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(@NonNull CameraDevice cameraDevice) {
            Iterator<CameraDevice.StateCallback> it = this.mCallbacks.iterator();
            while (it.hasNext()) {
                it.next().onOpened(cameraDevice);
            }
        }
    }

    public static final class NoOpDeviceStateCallback extends CameraDevice.StateCallback {
        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onClosed(@NonNull CameraDevice cameraDevice) {
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onDisconnected(@NonNull CameraDevice cameraDevice) {
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onError(@NonNull CameraDevice cameraDevice, int i2) {
        }

        @Override // android.hardware.camera2.CameraDevice.StateCallback
        public void onOpened(@NonNull CameraDevice cameraDevice) {
        }
    }

    private CameraDeviceStateCallbacks() {
    }

    @NonNull
    public static CameraDevice.StateCallback createComboCallback(@NonNull List<CameraDevice.StateCallback> list) {
        return list.isEmpty() ? createNoOpCallback() : list.size() == 1 ? list.get(0) : new ComboDeviceStateCallback(list);
    }

    @NonNull
    public static CameraDevice.StateCallback createNoOpCallback() {
        return new NoOpDeviceStateCallback();
    }

    @NonNull
    public static CameraDevice.StateCallback createComboCallback(@NonNull CameraDevice.StateCallback... stateCallbackArr) {
        return createComboCallback((List<CameraDevice.StateCallback>) Arrays.asList(stateCallbackArr));
    }
}
