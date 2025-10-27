package androidx.camera.core.imagecapture;

import android.graphics.Matrix;
import android.graphics.Rect;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.CaptureBundle;
import androidx.camera.core.impl.CaptureStage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
class ProcessingRequest {

    @NonNull
    private final TakePictureCallback mCallback;

    @NonNull
    private final Rect mCropRect;
    private final int mJpegQuality;

    @Nullable
    private final ImageCapture.OutputFileOptions mOutputFileOptions;
    private final int mRotationDegrees;

    @NonNull
    private final Matrix mSensorToBufferTransform;

    @NonNull
    private final List<Integer> mStageIds = new ArrayList();

    @NonNull
    private final String mTagBundleKey;

    public ProcessingRequest(@NonNull CaptureBundle captureBundle, @Nullable ImageCapture.OutputFileOptions outputFileOptions, @NonNull Rect rect, int i2, int i3, @NonNull Matrix matrix, @NonNull TakePictureCallback takePictureCallback) {
        this.mOutputFileOptions = outputFileOptions;
        this.mJpegQuality = i3;
        this.mRotationDegrees = i2;
        this.mCropRect = rect;
        this.mSensorToBufferTransform = matrix;
        this.mCallback = takePictureCallback;
        this.mTagBundleKey = String.valueOf(captureBundle.hashCode());
        List<CaptureStage> captureStages = captureBundle.getCaptureStages();
        Objects.requireNonNull(captureStages);
        Iterator<CaptureStage> it = captureStages.iterator();
        while (it.hasNext()) {
            this.mStageIds.add(Integer.valueOf(it.next().getId()));
        }
    }

    @NonNull
    public Rect getCropRect() {
        return this.mCropRect;
    }

    public int getJpegQuality() {
        return this.mJpegQuality;
    }

    @Nullable
    public ImageCapture.OutputFileOptions getOutputFileOptions() {
        return this.mOutputFileOptions;
    }

    public int getRotationDegrees() {
        return this.mRotationDegrees;
    }

    @NonNull
    public Matrix getSensorToBufferTransform() {
        return this.mSensorToBufferTransform;
    }

    @NonNull
    public List<Integer> getStageIds() {
        return this.mStageIds;
    }

    @NonNull
    public String getTagBundleKey() {
        return this.mTagBundleKey;
    }

    public boolean isAborted() {
        return this.mCallback.isAborted();
    }

    public boolean isInMemoryCapture() {
        return getOutputFileOptions() == null;
    }

    @MainThread
    public void onFinalResult(@NonNull ImageCapture.OutputFileResults outputFileResults) {
        this.mCallback.onFinalResult(outputFileResults);
    }

    @MainThread
    public void onImageCaptured() {
        this.mCallback.onImageCaptured();
    }

    @MainThread
    public void onProcessFailure(@NonNull ImageCaptureException imageCaptureException) {
        this.mCallback.onProcessFailure(imageCaptureException);
    }

    @MainThread
    public void onFinalResult(@NonNull ImageProxy imageProxy) {
        this.mCallback.onFinalResult(imageProxy);
    }
}
