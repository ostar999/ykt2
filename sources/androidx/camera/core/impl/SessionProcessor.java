package androidx.camera.core.impl;

import android.hardware.camera2.CaptureResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.CameraInfo;
import java.util.Map;

@RequiresApi(21)
/* loaded from: classes.dex */
public interface SessionProcessor {

    public interface CaptureCallback {
        void onCaptureCompleted(long j2, int i2, @NonNull Map<CaptureResult.Key, Object> map);

        void onCaptureFailed(int i2);

        void onCaptureProcessStarted(int i2);

        void onCaptureSequenceAborted(int i2);

        void onCaptureSequenceCompleted(int i2);

        void onCaptureStarted(int i2, long j2);
    }

    void abortCapture(int i2);

    void deInitSession();

    @NonNull
    SessionConfig initSession(@NonNull CameraInfo cameraInfo, @NonNull OutputSurface outputSurface, @NonNull OutputSurface outputSurface2, @Nullable OutputSurface outputSurface3);

    void onCaptureSessionEnd();

    void onCaptureSessionStart(@NonNull RequestProcessor requestProcessor);

    void setParameters(@NonNull Config config);

    int startCapture(@NonNull CaptureCallback captureCallback);

    int startRepeating(@NonNull CaptureCallback captureCallback);

    void stopRepeating();
}
