package org.wrtca.video;

import android.content.Context;
import android.media.MediaRecorder;
import org.wrtca.api.CameraEnumerator;
import org.wrtca.api.CameraVideoCapturer;
import org.wrtca.api.SurfaceTextureHelper;
import org.wrtca.record.model.CameraParamObserver;
import org.wrtca.video.CameraSession;
import org.wrtca.video.RtcCameraRTSPSession;

/* loaded from: classes9.dex */
abstract class RTSPCameraCapturer extends CameraCapturer {
    public RTSPCameraCapturer(String str, CameraVideoCapturer.CameraEventsHandler cameraEventsHandler, CameraEnumerator cameraEnumerator) {
        super(str, cameraEventsHandler, cameraEnumerator);
    }

    @Override // org.wrtca.video.CameraCapturer
    public void createCameraSession(CameraSession.CreateSessionCallback createSessionCallback, CameraSession.Events events, Context context, SurfaceTextureHelper surfaceTextureHelper, MediaRecorder mediaRecorder, String str, int i2, int i3, int i4, CameraParamObserver cameraParamObserver) {
    }

    public abstract void createRTSPCameraSession(CameraSession.CreateSessionCallback createSessionCallback, CameraSession.Events events, String str, Context context, SurfaceTextureHelper surfaceTextureHelper, String str2, RtcCameraRTSPSession.RTSPCameraDataFormat rTSPCameraDataFormat, int i2, int i3, int i4);

    @Override // org.wrtca.video.CameraCapturer
    public void createSessionInternal(int i2, MediaRecorder mediaRecorder) {
        this.uiThreadHandler.postDelayed(this.openCameraTimeoutRunnable, i2 + 10000);
        this.cameraThreadHandler.postDelayed(new Runnable() { // from class: org.wrtca.video.RTSPCameraCapturer.1
            @Override // java.lang.Runnable
            public void run() {
                RTSPCameraCapturer rTSPCameraCapturer = RTSPCameraCapturer.this;
                rTSPCameraCapturer.createRTSPCameraSession(rTSPCameraCapturer.createSessionCallback, rTSPCameraCapturer.cameraSessionEventsHandler, "rtsp://192.168.161.147/ch1", rTSPCameraCapturer.applicationContext, rTSPCameraCapturer.surfaceHelper, rTSPCameraCapturer.cameraName, RtcCameraRTSPSession.RTSPCameraDataFormat.I420, rTSPCameraCapturer.width, rTSPCameraCapturer.height, rTSPCameraCapturer.framerate);
            }
        }, i2);
    }
}
