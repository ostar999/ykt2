package com.tencent.liteav.device;

/* loaded from: classes6.dex */
public interface TXDeviceManager {

    public enum TXAudioRoute {
        TXAudioRouteSpeakerphone,
        TXAudioRouteEarpiece
    }

    public enum TXCameraCaptureMode {
        TXCameraResolutionStrategyAuto,
        TXCameraResolutionStrategyPerformance,
        TXCameraResolutionStrategyHighQuality,
        TXCameraCaptureManual
    }

    public static class TXCameraCaptureParam {
        public int height;
        public TXCameraCaptureMode mode;
        public int width;
    }

    public enum TXSystemVolumeType {
        TXSystemVolumeTypeAuto,
        TXSystemVolumeTypeMedia,
        TXSystemVolumeTypeVOIP
    }

    int enableCameraAutoFocus(boolean z2);

    boolean enableCameraTorch(boolean z2);

    float getCameraZoomMaxRatio();

    boolean isAutoFocusEnabled();

    boolean isFrontCamera();

    int setAudioRoute(TXAudioRoute tXAudioRoute);

    void setCameraCapturerParam(TXCameraCaptureParam tXCameraCaptureParam);

    int setCameraFocusPosition(int i2, int i3);

    int setCameraZoomRatio(float f2);

    int setSystemVolumeType(TXSystemVolumeType tXSystemVolumeType);

    int switchCamera(boolean z2);
}
