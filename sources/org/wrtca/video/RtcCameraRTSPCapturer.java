package org.wrtca.video;

import android.content.Context;
import org.wrtca.api.CameraVideoCapturer;
import org.wrtca.api.SurfaceTextureHelper;
import org.wrtca.video.CameraSession;
import org.wrtca.video.RtcCameraRTSPSession;

/* loaded from: classes9.dex */
public class RtcCameraRTSPCapturer extends RTSPCameraCapturer {
    public RtcCameraRTSPCapturer(String str, CameraVideoCapturer.CameraEventsHandler cameraEventsHandler) {
        super(str, cameraEventsHandler, new RtcCameraRTSPEnumerator());
    }

    @Override // org.wrtca.video.RTSPCameraCapturer
    public void createRTSPCameraSession(CameraSession.CreateSessionCallback createSessionCallback, CameraSession.Events events, String str, Context context, SurfaceTextureHelper surfaceTextureHelper, String str2, RtcCameraRTSPSession.RTSPCameraDataFormat rTSPCameraDataFormat, int i2, int i3, int i4) {
        RtcCameraRTSPSession.create(createSessionCallback, events, context, surfaceTextureHelper, str, 0, rTSPCameraDataFormat, i2, i3, i4);
    }
}
