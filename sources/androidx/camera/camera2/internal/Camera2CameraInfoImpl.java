package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCharacteristics;
import android.util.Pair;
import androidx.annotation.GuardedBy;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.OptIn;
import androidx.annotation.RequiresApi;
import androidx.camera.camera2.internal.compat.CameraAccessExceptionCompat;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.camera2.internal.compat.CameraManagerCompat;
import androidx.camera.camera2.internal.compat.quirk.CameraQuirks;
import androidx.camera.camera2.internal.compat.workaround.FlashAvailabilityChecker;
import androidx.camera.camera2.interop.Camera2CameraInfo;
import androidx.camera.camera2.interop.ExperimentalCamera2Interop;
import androidx.camera.core.CameraInfo;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraState;
import androidx.camera.core.ExposureState;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.Logger;
import androidx.camera.core.ZoomState;
import androidx.camera.core.impl.CamcorderProfileProvider;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.CameraInfoInternal;
import androidx.camera.core.impl.Quirks;
import androidx.camera.core.impl.Timebase;
import androidx.core.util.Preconditions;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

@OptIn(markerClass = {ExperimentalCamera2Interop.class})
@RequiresApi(21)
/* loaded from: classes.dex */
public final class Camera2CameraInfoImpl implements CameraInfoInternal {
    private static final String TAG = "Camera2CameraInfo";

    @NonNull
    private final CamcorderProfileProvider mCamera2CamcorderProfileProvider;

    @Nullable
    @GuardedBy("mLock")
    private Camera2CameraControlImpl mCamera2CameraControlImpl;
    private final Camera2CameraInfo mCamera2CameraInfo;
    private final CameraCharacteristicsCompat mCameraCharacteristicsCompat;
    private final String mCameraId;

    @NonNull
    private final CameraManagerCompat mCameraManager;

    @NonNull
    private final Quirks mCameraQuirks;

    @NonNull
    private final RedirectableLiveData<CameraState> mCameraStateLiveData;
    private final Object mLock = new Object();

    @Nullable
    @GuardedBy("mLock")
    private RedirectableLiveData<Integer> mRedirectTorchStateLiveData = null;

    @Nullable
    @GuardedBy("mLock")
    private RedirectableLiveData<ZoomState> mRedirectZoomStateLiveData = null;

    @Nullable
    @GuardedBy("mLock")
    private List<Pair<CameraCaptureCallback, Executor>> mCameraCaptureCallbacks = null;

    public static class RedirectableLiveData<T> extends MediatorLiveData<T> {
        private final T mInitialValue;
        private LiveData<T> mLiveDataSource;

        public RedirectableLiveData(T t2) {
            this.mInitialValue = t2;
        }

        @Override // androidx.lifecycle.MediatorLiveData
        public <S> void addSource(@NonNull LiveData<S> liveData, @NonNull Observer<? super S> observer) {
            throw new UnsupportedOperationException();
        }

        @Override // androidx.lifecycle.LiveData
        public T getValue() {
            LiveData<T> liveData = this.mLiveDataSource;
            return liveData == null ? this.mInitialValue : liveData.getValue();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void redirectTo(@NonNull LiveData<T> liveData) {
            LiveData<T> liveData2 = this.mLiveDataSource;
            if (liveData2 != null) {
                super.removeSource(liveData2);
            }
            this.mLiveDataSource = liveData;
            super.addSource(liveData, new Observer() { // from class: androidx.camera.camera2.internal.k0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    this.f1361a.setValue(obj);
                }
            });
        }
    }

    public Camera2CameraInfoImpl(@NonNull String str, @NonNull CameraManagerCompat cameraManagerCompat) throws CameraAccessExceptionCompat {
        String str2 = (String) Preconditions.checkNotNull(str);
        this.mCameraId = str2;
        this.mCameraManager = cameraManagerCompat;
        CameraCharacteristicsCompat cameraCharacteristicsCompat = cameraManagerCompat.getCameraCharacteristicsCompat(str2);
        this.mCameraCharacteristicsCompat = cameraCharacteristicsCompat;
        this.mCamera2CameraInfo = new Camera2CameraInfo(this);
        this.mCameraQuirks = CameraQuirks.get(str, cameraCharacteristicsCompat);
        this.mCamera2CamcorderProfileProvider = new Camera2CamcorderProfileProvider(str, cameraCharacteristicsCompat);
        this.mCameraStateLiveData = new RedirectableLiveData<>(CameraState.create(CameraState.Type.CLOSED));
    }

    private void logDeviceInfo() {
        logDeviceLevel();
    }

    private void logDeviceLevel() {
        String str;
        int supportedHardwareLevel = getSupportedHardwareLevel();
        if (supportedHardwareLevel == 0) {
            str = "INFO_SUPPORTED_HARDWARE_LEVEL_LIMITED";
        } else if (supportedHardwareLevel == 1) {
            str = "INFO_SUPPORTED_HARDWARE_LEVEL_FULL";
        } else if (supportedHardwareLevel == 2) {
            str = "INFO_SUPPORTED_HARDWARE_LEVEL_LEGACY";
        } else if (supportedHardwareLevel == 3) {
            str = "INFO_SUPPORTED_HARDWARE_LEVEL_3";
        } else if (supportedHardwareLevel != 4) {
            str = "Unknown value: " + supportedHardwareLevel;
        } else {
            str = "INFO_SUPPORTED_HARDWARE_LEVEL_EXTERNAL";
        }
        Logger.i(TAG, "Device Level: " + str);
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public void addSessionCaptureCallback(@NonNull Executor executor, @NonNull CameraCaptureCallback cameraCaptureCallback) {
        synchronized (this.mLock) {
            Camera2CameraControlImpl camera2CameraControlImpl = this.mCamera2CameraControlImpl;
            if (camera2CameraControlImpl != null) {
                camera2CameraControlImpl.addSessionCameraCaptureCallback(executor, cameraCaptureCallback);
                return;
            }
            if (this.mCameraCaptureCallbacks == null) {
                this.mCameraCaptureCallbacks = new ArrayList();
            }
            this.mCameraCaptureCallbacks.add(new Pair<>(cameraCaptureCallback, executor));
        }
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    @NonNull
    public CamcorderProfileProvider getCamcorderProfileProvider() {
        return this.mCamera2CamcorderProfileProvider;
    }

    @NonNull
    public Camera2CameraInfo getCamera2CameraInfo() {
        return this.mCamera2CameraInfo;
    }

    @NonNull
    public CameraCharacteristicsCompat getCameraCharacteristicsCompat() {
        return this.mCameraCharacteristicsCompat;
    }

    @NonNull
    public Map<String, CameraCharacteristics> getCameraCharacteristicsMap() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put(this.mCameraId, this.mCameraCharacteristicsCompat.toCameraCharacteristics());
        for (String str : this.mCameraCharacteristicsCompat.getPhysicalCameraIds()) {
            if (!Objects.equals(str, this.mCameraId)) {
                try {
                    linkedHashMap.put(str, this.mCameraManager.getCameraCharacteristicsCompat(str).toCameraCharacteristics());
                } catch (CameraAccessExceptionCompat e2) {
                    Logger.e(TAG, "Failed to get CameraCharacteristics for cameraId " + str, e2);
                }
            }
        }
        return linkedHashMap;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    @NonNull
    public String getCameraId() {
        return this.mCameraId;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    @NonNull
    public Quirks getCameraQuirks() {
        return this.mCameraQuirks;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal, androidx.camera.core.CameraInfo
    public /* synthetic */ CameraSelector getCameraSelector() {
        return androidx.camera.core.impl.h.a(this);
    }

    @Override // androidx.camera.core.CameraInfo
    @NonNull
    public LiveData<CameraState> getCameraState() {
        return this.mCameraStateLiveData;
    }

    @Override // androidx.camera.core.CameraInfo
    @NonNull
    public ExposureState getExposureState() {
        synchronized (this.mLock) {
            Camera2CameraControlImpl camera2CameraControlImpl = this.mCamera2CameraControlImpl;
            if (camera2CameraControlImpl == null) {
                return ExposureControl.getDefaultExposureState(this.mCameraCharacteristicsCompat);
            }
            return camera2CameraControlImpl.getExposureControl().getExposureState();
        }
    }

    @Override // androidx.camera.core.CameraInfo
    @NonNull
    public String getImplementationType() {
        return getSupportedHardwareLevel() == 2 ? CameraInfo.IMPLEMENTATION_TYPE_CAMERA2_LEGACY : CameraInfo.IMPLEMENTATION_TYPE_CAMERA2;
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    @Nullable
    public Integer getLensFacing() {
        Integer num = (Integer) this.mCameraCharacteristicsCompat.get(CameraCharacteristics.LENS_FACING);
        Preconditions.checkNotNull(num);
        int iIntValue = num.intValue();
        if (iIntValue != 0) {
            return iIntValue != 1 ? null : 1;
        }
        return 0;
    }

    public int getSensorOrientation() {
        Integer num = (Integer) this.mCameraCharacteristicsCompat.get(CameraCharacteristics.SENSOR_ORIENTATION);
        Preconditions.checkNotNull(num);
        return num.intValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    @Override // androidx.camera.core.CameraInfo
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public int getSensorRotationDegrees(int r4) {
        /*
            r3 = this;
            int r0 = r3.getSensorOrientation()
            int r4 = androidx.camera.core.impl.utils.CameraOrientationUtil.surfaceRotationToDegrees(r4)
            java.lang.Integer r1 = r3.getLensFacing()
            if (r1 == 0) goto L16
            int r1 = r1.intValue()
            r2 = 1
            if (r2 != r1) goto L16
            goto L17
        L16:
            r2 = 0
        L17:
            int r4 = androidx.camera.core.impl.utils.CameraOrientationUtil.getRelativeImageRotation(r4, r0, r2)
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.camera2.internal.Camera2CameraInfoImpl.getSensorRotationDegrees(int):int");
    }

    public int getSupportedHardwareLevel() {
        Integer num = (Integer) this.mCameraCharacteristicsCompat.get(CameraCharacteristics.INFO_SUPPORTED_HARDWARE_LEVEL);
        Preconditions.checkNotNull(num);
        return num.intValue();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    @NonNull
    public Timebase getTimebase() {
        Integer num = (Integer) this.mCameraCharacteristicsCompat.get(CameraCharacteristics.SENSOR_INFO_TIMESTAMP_SOURCE);
        Preconditions.checkNotNull(num);
        return num.intValue() != 1 ? Timebase.UPTIME : Timebase.REALTIME;
    }

    @Override // androidx.camera.core.CameraInfo
    @NonNull
    public LiveData<Integer> getTorchState() {
        synchronized (this.mLock) {
            Camera2CameraControlImpl camera2CameraControlImpl = this.mCamera2CameraControlImpl;
            if (camera2CameraControlImpl == null) {
                if (this.mRedirectTorchStateLiveData == null) {
                    this.mRedirectTorchStateLiveData = new RedirectableLiveData<>(0);
                }
                return this.mRedirectTorchStateLiveData;
            }
            RedirectableLiveData<Integer> redirectableLiveData = this.mRedirectTorchStateLiveData;
            if (redirectableLiveData != null) {
                return redirectableLiveData;
            }
            return camera2CameraControlImpl.getTorchControl().getTorchState();
        }
    }

    @Override // androidx.camera.core.CameraInfo
    @NonNull
    public LiveData<ZoomState> getZoomState() {
        synchronized (this.mLock) {
            Camera2CameraControlImpl camera2CameraControlImpl = this.mCamera2CameraControlImpl;
            if (camera2CameraControlImpl == null) {
                if (this.mRedirectZoomStateLiveData == null) {
                    this.mRedirectZoomStateLiveData = new RedirectableLiveData<>(ZoomControl.getDefaultZoomState(this.mCameraCharacteristicsCompat));
                }
                return this.mRedirectZoomStateLiveData;
            }
            RedirectableLiveData<ZoomState> redirectableLiveData = this.mRedirectZoomStateLiveData;
            if (redirectableLiveData != null) {
                return redirectableLiveData;
            }
            return camera2CameraControlImpl.getZoomControl().getZoomState();
        }
    }

    @Override // androidx.camera.core.CameraInfo
    public boolean hasFlashUnit() {
        return FlashAvailabilityChecker.isFlashAvailable(this.mCameraCharacteristicsCompat);
    }

    @Override // androidx.camera.core.CameraInfo
    public boolean isFocusMeteringSupported(@NonNull FocusMeteringAction focusMeteringAction) {
        synchronized (this.mLock) {
            Camera2CameraControlImpl camera2CameraControlImpl = this.mCamera2CameraControlImpl;
            if (camera2CameraControlImpl == null) {
                return false;
            }
            return camera2CameraControlImpl.getFocusMeteringControl().isFocusMeteringSupported(focusMeteringAction);
        }
    }

    @Override // androidx.camera.core.CameraInfo
    public boolean isPrivateReprocessingSupported() {
        return ZslUtil.isCapabilitySupported(this.mCameraCharacteristicsCompat, 4);
    }

    @Override // androidx.camera.core.CameraInfo
    public boolean isZslSupported() {
        return isPrivateReprocessingSupported();
    }

    public void linkWithCameraControl(@NonNull Camera2CameraControlImpl camera2CameraControlImpl) {
        synchronized (this.mLock) {
            this.mCamera2CameraControlImpl = camera2CameraControlImpl;
            RedirectableLiveData<ZoomState> redirectableLiveData = this.mRedirectZoomStateLiveData;
            if (redirectableLiveData != null) {
                redirectableLiveData.redirectTo(camera2CameraControlImpl.getZoomControl().getZoomState());
            }
            RedirectableLiveData<Integer> redirectableLiveData2 = this.mRedirectTorchStateLiveData;
            if (redirectableLiveData2 != null) {
                redirectableLiveData2.redirectTo(this.mCamera2CameraControlImpl.getTorchControl().getTorchState());
            }
            List<Pair<CameraCaptureCallback, Executor>> list = this.mCameraCaptureCallbacks;
            if (list != null) {
                for (Pair<CameraCaptureCallback, Executor> pair : list) {
                    this.mCamera2CameraControlImpl.addSessionCameraCaptureCallback((Executor) pair.second, (CameraCaptureCallback) pair.first);
                }
                this.mCameraCaptureCallbacks = null;
            }
        }
        logDeviceInfo();
    }

    @Override // androidx.camera.core.impl.CameraInfoInternal
    public void removeSessionCaptureCallback(@NonNull CameraCaptureCallback cameraCaptureCallback) {
        synchronized (this.mLock) {
            Camera2CameraControlImpl camera2CameraControlImpl = this.mCamera2CameraControlImpl;
            if (camera2CameraControlImpl != null) {
                camera2CameraControlImpl.removeSessionCameraCaptureCallback(cameraCaptureCallback);
                return;
            }
            List<Pair<CameraCaptureCallback, Executor>> list = this.mCameraCaptureCallbacks;
            if (list == null) {
                return;
            }
            Iterator<Pair<CameraCaptureCallback, Executor>> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().first == cameraCaptureCallback) {
                    it.remove();
                }
            }
        }
    }

    public void setCameraStateSource(@NonNull LiveData<CameraState> liveData) {
        this.mCameraStateLiveData.redirectTo(liveData);
    }

    @Override // androidx.camera.core.CameraInfo
    public int getSensorRotationDegrees() {
        return getSensorRotationDegrees(0);
    }
}
