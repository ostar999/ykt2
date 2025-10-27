package org.wrtca.api;

import java.util.List;
import org.wrtca.api.CameraEnumerationAndroid;
import org.wrtca.api.CameraVideoCapturer;

/* loaded from: classes9.dex */
public interface CameraEnumerator {
    CameraVideoCapturer createCapturer(String str, CameraVideoCapturer.CameraEventsHandler cameraEventsHandler);

    String[] getDeviceNames();

    int getFacingIndex(String str);

    List<CameraEnumerationAndroid.CaptureFormat> getSupportedFormats(String str);

    boolean isBackFacing(String str);

    boolean isFrontFacing(String str);
}
