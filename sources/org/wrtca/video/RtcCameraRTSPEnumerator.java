package org.wrtca.video;

import java.util.List;
import org.wrtca.api.CameraEnumerationAndroid;
import org.wrtca.api.CameraEnumerator;
import org.wrtca.api.CameraVideoCapturer;

/* loaded from: classes9.dex */
public class RtcCameraRTSPEnumerator implements CameraEnumerator {
    @Override // org.wrtca.api.CameraEnumerator
    public CameraVideoCapturer createCapturer(String str, CameraVideoCapturer.CameraEventsHandler cameraEventsHandler) {
        return new RtcCameraRTSPCapturer(str, cameraEventsHandler);
    }

    @Override // org.wrtca.api.CameraEnumerator
    public String[] getDeviceNames() {
        return new String[]{"rtspCamera"};
    }

    @Override // org.wrtca.api.CameraEnumerator
    public int getFacingIndex(String str) {
        return 0;
    }

    @Override // org.wrtca.api.CameraEnumerator
    public List<CameraEnumerationAndroid.CaptureFormat> getSupportedFormats(String str) {
        return null;
    }

    @Override // org.wrtca.api.CameraEnumerator
    public boolean isBackFacing(String str) {
        return true;
    }

    @Override // org.wrtca.api.CameraEnumerator
    public boolean isFrontFacing(String str) {
        return false;
    }
}
