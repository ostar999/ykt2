package org.wrtca.api;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.camera2.CameraManager;
import android.media.MediaRecorder;
import com.plv.business.model.ppt.PLVPPTAuthentic;
import org.wrtca.api.CameraVideoCapturer;
import org.wrtca.record.model.CameraParamObserver;
import org.wrtca.video.Camera2Session;
import org.wrtca.video.CameraCapturer;
import org.wrtca.video.CameraSession;

@TargetApi(21)
/* loaded from: classes9.dex */
public class Camera2Capturer extends CameraCapturer {
    private final CameraManager cameraManager;
    private final Context context;

    public Camera2Capturer(Context context, String str, CameraVideoCapturer.CameraEventsHandler cameraEventsHandler) {
        super(str, cameraEventsHandler, new Camera2Enumerator(context));
        this.context = context;
        this.cameraManager = (CameraManager) context.getSystemService(PLVPPTAuthentic.PermissionType.CAMERA);
    }

    @Override // org.wrtca.video.CameraCapturer
    public void createCameraSession(CameraSession.CreateSessionCallback createSessionCallback, CameraSession.Events events, Context context, SurfaceTextureHelper surfaceTextureHelper, MediaRecorder mediaRecorder, String str, int i2, int i3, int i4, CameraParamObserver cameraParamObserver) {
        Camera2Session.create(createSessionCallback, events, context, this.cameraManager, surfaceTextureHelper, mediaRecorder, str, i2, i3, i4, cameraParamObserver);
    }
}
