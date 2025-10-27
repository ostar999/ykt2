package androidx.camera.core.imagecapture;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;

/* loaded from: classes.dex */
interface TakePictureCallback {
    boolean isAborted();

    @MainThread
    void onCaptureFailure(@NonNull ImageCaptureException imageCaptureException);

    @MainThread
    void onFinalResult(@NonNull ImageCapture.OutputFileResults outputFileResults);

    @MainThread
    void onFinalResult(@NonNull ImageProxy imageProxy);

    @MainThread
    void onImageCaptured();

    @MainThread
    void onProcessFailure(@NonNull ImageCaptureException imageCaptureException);
}
