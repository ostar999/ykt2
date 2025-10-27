package org.wrtca.video;

import android.content.Context;
import android.os.Handler;
import org.wrtca.api.SurfaceTextureHelper;
import org.wrtca.video.CameraSession;

/* loaded from: classes9.dex */
public class RtcCameraRTSPSession implements CameraSession {
    private final Context applicationContext;
    private final int cameraId;
    private final Handler cameraThreadHandler = new Handler();
    private final CameraSession.Events events;
    private RtcCameraRTSP mCamera;
    private int mFrameRate;
    private int mHeight;
    private int mWidth;
    private SessionState state;
    private final SurfaceTextureHelper surfaceTextureHelper;

    public enum RTSPCameraDataFormat {
        NV_21,
        I420
    }

    public static class RTSPCameraFormat {
        public int mFrameRate;
        public int mHeight;
        public RTSPCameraDataFormat mOutputFormat;
        public int mWidth;

        public RTSPCameraFormat(RTSPCameraDataFormat rTSPCameraDataFormat, int i2, int i3, int i4) {
            this.mOutputFormat = rTSPCameraDataFormat;
            this.mWidth = i2;
            this.mHeight = i3;
            this.mFrameRate = i4;
        }
    }

    public enum SessionState {
        RUNNING,
        STOPPED
    }

    public RtcCameraRTSPSession(CameraSession.Events events, Context context, RtcCameraRTSP rtcCameraRTSP, SurfaceTextureHelper surfaceTextureHelper, int i2, int i3, int i4, int i5) {
        this.events = events;
        this.applicationContext = context;
        this.surfaceTextureHelper = surfaceTextureHelper;
        this.cameraId = i2;
        this.mWidth = i3;
        this.mHeight = i4;
        this.mFrameRate = i5;
        this.mCamera = rtcCameraRTSP;
    }

    private void checkIsOnCameraThread() {
        if (Thread.currentThread() != this.cameraThreadHandler.getLooper().getThread()) {
            throw new IllegalStateException("Wrong RTSP Camera thread");
        }
    }

    public static void create(CameraSession.CreateSessionCallback createSessionCallback, CameraSession.Events events, Context context, SurfaceTextureHelper surfaceTextureHelper, String str, int i2, RTSPCameraDataFormat rTSPCameraDataFormat, int i3, int i4, int i5) {
        RTSPCameraFormat rTSPCameraFormat = new RTSPCameraFormat(rTSPCameraDataFormat, i3, i4, i5);
        RtcCameraRTSPSession rtcCameraRTSPSession = new RtcCameraRTSPSession(events, context, null, surfaceTextureHelper, i2, i3, i4, i5);
        rtcCameraRTSPSession.setCamera(new RtcCameraRTSP(str, rTSPCameraFormat, rtcCameraRTSPSession));
        createSessionCallback.onDone(rtcCameraRTSPSession);
    }

    @Override // org.wrtca.video.CameraSession
    public int getDeviceOrientation() {
        return 0;
    }

    public CameraSession.Events getEvents() {
        return this.events;
    }

    @Override // org.wrtca.video.CameraSession
    public int getFrameOrientation() {
        return 0;
    }

    @Override // org.wrtca.video.CameraSession
    public SurfaceTextureHelper getSurfaceTextureHelper() {
        return this.surfaceTextureHelper;
    }

    @Override // org.wrtca.video.CameraSession
    public CameraSession.CameraParam requestCamera() {
        return null;
    }

    public void setCamera(RtcCameraRTSP rtcCameraRTSP) {
        this.mCamera = rtcCameraRTSP;
    }

    @Override // org.wrtca.video.CameraSession
    public void setFlashLight(boolean z2) {
    }

    @Override // org.wrtca.video.CameraSession
    public void stop() {
    }
}
