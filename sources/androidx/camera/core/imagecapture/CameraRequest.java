package androidx.camera.core.imagecapture;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.impl.CaptureConfig;
import androidx.camera.core.impl.utils.Threads;
import java.util.List;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
public final class CameraRequest {
    private final TakePictureCallback mCallback;
    private final List<CaptureConfig> mCaptureConfigs;

    public CameraRequest(@NonNull List<CaptureConfig> list, @NonNull TakePictureCallback takePictureCallback) {
        this.mCaptureConfigs = list;
        this.mCallback = takePictureCallback;
    }

    @NonNull
    public List<CaptureConfig> getCaptureConfigs() {
        return this.mCaptureConfigs;
    }

    @MainThread
    public void onCaptureFailure(@NonNull ImageCaptureException imageCaptureException) {
        Threads.checkMainThread();
        this.mCallback.onCaptureFailure(imageCaptureException);
    }
}
