package org.wrtca.api;

import android.content.Context;
import android.media.MediaRecorder;
import java.io.IOException;
import org.wrtca.api.CameraVideoCapturer;
import org.wrtca.record.model.CameraParamObserver;
import org.wrtca.video.Camera1Session;
import org.wrtca.video.CameraCapturer;
import org.wrtca.video.CameraSession;

/* loaded from: classes9.dex */
public class Camera1Capturer extends CameraCapturer {
    private final boolean captureToTexture;

    public Camera1Capturer(String str, CameraVideoCapturer.CameraEventsHandler cameraEventsHandler, boolean z2) {
        super(str, cameraEventsHandler, new Camera1Enumerator(z2));
        this.captureToTexture = z2;
    }

    @Override // org.wrtca.video.CameraCapturer
    public void createCameraSession(CameraSession.CreateSessionCallback createSessionCallback, CameraSession.Events events, Context context, SurfaceTextureHelper surfaceTextureHelper, MediaRecorder mediaRecorder, String str, int i2, int i3, int i4, CameraParamObserver cameraParamObserver) throws IOException {
        Camera1Session.create(createSessionCallback, events, this.captureToTexture || mediaRecorder != null, context, surfaceTextureHelper, mediaRecorder, Camera1Enumerator.getCameraIndex(str), i2, i3, i4, cameraParamObserver);
    }
}
