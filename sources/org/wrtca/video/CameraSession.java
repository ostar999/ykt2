package org.wrtca.video;

import org.wrtca.api.SurfaceTextureHelper;
import org.wrtca.api.VideoFrame;

/* loaded from: classes9.dex */
public interface CameraSession {

    public static class CameraParam {
        public int cameraOrientation;
        public boolean isFrontCamera;

        public int getCameraOrientation() {
            return this.cameraOrientation;
        }

        public boolean isFrontCamera() {
            return this.isFrontCamera;
        }

        public void setCameraOrientation(int i2) {
            this.cameraOrientation = i2;
        }

        public void setFrontCamera(boolean z2) {
            this.isFrontCamera = z2;
        }
    }

    public interface CreateSessionCallback {
        void onDone(CameraSession cameraSession);

        void onFailure(FailureType failureType, int i2, String str);
    }

    public interface Events {
        void onCameraClosed(CameraSession cameraSession);

        void onCameraDisconnected(CameraSession cameraSession);

        void onCameraError(CameraSession cameraSession, String str);

        void onCameraOpening();

        void onFrameCaptured(CameraSession cameraSession, VideoFrame videoFrame);

        void onFrameCaptured(CameraSession cameraSession, VideoFrame videoFrame, boolean z2);
    }

    public enum FailureType {
        ERROR,
        DISCONNECTED
    }

    int getDeviceOrientation();

    int getFrameOrientation();

    SurfaceTextureHelper getSurfaceTextureHelper();

    CameraParam requestCamera();

    void setFlashLight(boolean z2);

    void stop();
}
