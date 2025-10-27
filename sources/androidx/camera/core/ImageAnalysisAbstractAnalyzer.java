package androidx.camera.core;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ImageWriter;
import androidx.annotation.GuardedBy;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.internal.compat.ImageWriterCompat;
import androidx.concurrent.futures.CallbackToFutureAdapter;
import androidx.core.os.OperationCanceledException;
import java.nio.ByteBuffer;
import java.util.concurrent.Executor;

@RequiresApi(21)
/* loaded from: classes.dex */
abstract class ImageAnalysisAbstractAnalyzer implements ImageReaderProxy.OnImageAvailableListener {
    private static final String TAG = "ImageAnalysisAnalyzer";
    private volatile boolean mOnePixelShiftEnabled;
    private volatile boolean mOutputImageRotationEnabled;

    @IntRange(from = 0, to = 359)
    private volatile int mPrevBufferRotationDegrees;

    @Nullable
    @GuardedBy("mAnalyzerLock")
    private SafeCloseImageReaderProxy mProcessedImageReaderProxy;

    @Nullable
    @GuardedBy("mAnalyzerLock")
    private ImageWriter mProcessedImageWriter;

    @Nullable
    @GuardedBy("mAnalyzerLock")
    @VisibleForTesting
    ByteBuffer mRGBConvertedBuffer;

    @IntRange(from = 0, to = 359)
    private volatile int mRelativeRotation;

    @GuardedBy("mAnalyzerLock")
    private ImageAnalysis.Analyzer mSubscribedAnalyzer;

    @Nullable
    @GuardedBy("mAnalyzerLock")
    @VisibleForTesting
    ByteBuffer mURotatedBuffer;

    @GuardedBy("mAnalyzerLock")
    private Executor mUserExecutor;

    @Nullable
    @GuardedBy("mAnalyzerLock")
    @VisibleForTesting
    ByteBuffer mVRotatedBuffer;

    @Nullable
    @GuardedBy("mAnalyzerLock")
    @VisibleForTesting
    ByteBuffer mYRotatedBuffer;
    private volatile int mOutputImageFormat = 1;

    @GuardedBy("mAnalyzerLock")
    private Rect mOriginalViewPortCropRect = new Rect();

    @GuardedBy("mAnalyzerLock")
    private Rect mUpdatedViewPortCropRect = new Rect();

    @GuardedBy("mAnalyzerLock")
    private Matrix mOriginalSensorToBufferTransformMatrix = new Matrix();

    @GuardedBy("mAnalyzerLock")
    private Matrix mUpdatedSensorToBufferTransformMatrix = new Matrix();
    private final Object mAnalyzerLock = new Object();
    protected boolean mIsAttached = true;

    @GuardedBy("mAnalyzerLock")
    private void createHelperBuffer(@NonNull ImageProxy imageProxy) {
        if (this.mOutputImageFormat != 1) {
            if (this.mOutputImageFormat == 2 && this.mRGBConvertedBuffer == null) {
                this.mRGBConvertedBuffer = ByteBuffer.allocateDirect(imageProxy.getWidth() * imageProxy.getHeight() * 4);
                return;
            }
            return;
        }
        if (this.mYRotatedBuffer == null) {
            this.mYRotatedBuffer = ByteBuffer.allocateDirect(imageProxy.getWidth() * imageProxy.getHeight());
        }
        this.mYRotatedBuffer.position(0);
        if (this.mURotatedBuffer == null) {
            this.mURotatedBuffer = ByteBuffer.allocateDirect((imageProxy.getWidth() * imageProxy.getHeight()) / 4);
        }
        this.mURotatedBuffer.position(0);
        if (this.mVRotatedBuffer == null) {
            this.mVRotatedBuffer = ByteBuffer.allocateDirect((imageProxy.getWidth() * imageProxy.getHeight()) / 4);
        }
        this.mVRotatedBuffer.position(0);
    }

    @NonNull
    private static SafeCloseImageReaderProxy createImageReaderProxy(int i2, int i3, int i4, int i5, int i6) {
        boolean z2 = i4 == 90 || i4 == 270;
        int i7 = z2 ? i3 : i2;
        if (!z2) {
            i2 = i3;
        }
        return new SafeCloseImageReaderProxy(ImageReaderProxys.createIsolatedReader(i7, i2, i5, i6));
    }

    @NonNull
    @VisibleForTesting
    public static Matrix getAdditionalTransformMatrixAppliedByProcessor(int i2, int i3, int i4, int i5, @IntRange(from = 0, to = 359) int i6) {
        Matrix matrix = new Matrix();
        if (i6 > 0) {
            matrix.setRectToRect(new RectF(0.0f, 0.0f, i2, i3), TransformUtils.NORMALIZED_RECT, Matrix.ScaleToFit.FILL);
            matrix.postRotate(i6);
            matrix.postConcat(TransformUtils.getNormalizedToBuffer(new RectF(0.0f, 0.0f, i4, i5)));
        }
        return matrix;
    }

    @NonNull
    public static Rect getUpdatedCropRect(@NonNull Rect rect, @NonNull Matrix matrix) {
        RectF rectF = new RectF(rect);
        matrix.mapRect(rectF);
        Rect rect2 = new Rect();
        rectF.round(rect2);
        return rect2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$analyzeImage$0(ImageProxy imageProxy, Matrix matrix, ImageProxy imageProxy2, Rect rect, ImageAnalysis.Analyzer analyzer, CallbackToFutureAdapter.Completer completer) {
        if (!this.mIsAttached) {
            completer.setException(new OperationCanceledException("ImageAnalysis is detached"));
            return;
        }
        SettableImageProxy settableImageProxy = new SettableImageProxy(imageProxy2, ImmutableImageInfo.create(imageProxy.getImageInfo().getTagBundle(), imageProxy.getImageInfo().getTimestamp(), this.mOutputImageRotationEnabled ? 0 : this.mRelativeRotation, matrix));
        if (!rect.isEmpty()) {
            settableImageProxy.setCropRect(rect);
        }
        analyzer.analyze(settableImageProxy);
        completer.set(null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Object lambda$analyzeImage$1(Executor executor, final ImageProxy imageProxy, final Matrix matrix, final ImageProxy imageProxy2, final Rect rect, final ImageAnalysis.Analyzer analyzer, final CallbackToFutureAdapter.Completer completer) throws Exception {
        executor.execute(new Runnable() { // from class: androidx.camera.core.w
            @Override // java.lang.Runnable
            public final void run() {
                this.f1697c.lambda$analyzeImage$0(imageProxy, matrix, imageProxy2, rect, analyzer, completer);
            }
        });
        return "analyzeImage";
    }

    @GuardedBy("mAnalyzerLock")
    private void recalculateTransformMatrixAndCropRect(int i2, int i3, int i4, int i5) {
        Matrix additionalTransformMatrixAppliedByProcessor = getAdditionalTransformMatrixAppliedByProcessor(i2, i3, i4, i5, this.mRelativeRotation);
        this.mUpdatedViewPortCropRect = getUpdatedCropRect(this.mOriginalViewPortCropRect, additionalTransformMatrixAppliedByProcessor);
        this.mUpdatedSensorToBufferTransformMatrix.setConcat(this.mOriginalSensorToBufferTransformMatrix, additionalTransformMatrixAppliedByProcessor);
    }

    @GuardedBy("mAnalyzerLock")
    private void recreateImageReaderProxy(@NonNull ImageProxy imageProxy, @IntRange(from = 0, to = 359) int i2) {
        SafeCloseImageReaderProxy safeCloseImageReaderProxy = this.mProcessedImageReaderProxy;
        if (safeCloseImageReaderProxy == null) {
            return;
        }
        safeCloseImageReaderProxy.safeClose();
        this.mProcessedImageReaderProxy = createImageReaderProxy(imageProxy.getWidth(), imageProxy.getHeight(), i2, this.mProcessedImageReaderProxy.getImageFormat(), this.mProcessedImageReaderProxy.getMaxImages());
        if (this.mOutputImageFormat == 1) {
            ImageWriter imageWriter = this.mProcessedImageWriter;
            if (imageWriter != null) {
                ImageWriterCompat.close(imageWriter);
            }
            this.mProcessedImageWriter = ImageWriterCompat.newInstance(this.mProcessedImageReaderProxy.getSurface(), this.mProcessedImageReaderProxy.getMaxImages());
        }
    }

    @Nullable
    public abstract ImageProxy acquireImage(@NonNull ImageReaderProxy imageReaderProxy);

    /* JADX WARN: Removed duplicated region for block: B:39:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.common.util.concurrent.ListenableFuture<java.lang.Void> analyzeImage(@androidx.annotation.NonNull final androidx.camera.core.ImageProxy r15) {
        /*
            Method dump skipped, instructions count: 195
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.ImageAnalysisAbstractAnalyzer.analyzeImage(androidx.camera.core.ImageProxy):com.google.common.util.concurrent.ListenableFuture");
    }

    public void attach() {
        this.mIsAttached = true;
    }

    public abstract void clearCache();

    public void detach() {
        this.mIsAttached = false;
        clearCache();
    }

    @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
    public void onImageAvailable(@NonNull ImageReaderProxy imageReaderProxy) {
        try {
            ImageProxy imageProxyAcquireImage = acquireImage(imageReaderProxy);
            if (imageProxyAcquireImage != null) {
                onValidImageAvailable(imageProxyAcquireImage);
            }
        } catch (IllegalStateException e2) {
            Logger.e(TAG, "Failed to acquire image.", e2);
        }
    }

    public abstract void onValidImageAvailable(@NonNull ImageProxy imageProxy);

    public void setAnalyzer(@Nullable Executor executor, @Nullable ImageAnalysis.Analyzer analyzer) {
        if (analyzer == null) {
            clearCache();
        }
        synchronized (this.mAnalyzerLock) {
            this.mSubscribedAnalyzer = analyzer;
            this.mUserExecutor = executor;
        }
    }

    public void setOnePixelShiftEnabled(boolean z2) {
        this.mOnePixelShiftEnabled = z2;
    }

    public void setOutputImageFormat(int i2) {
        this.mOutputImageFormat = i2;
    }

    public void setOutputImageRotationEnabled(boolean z2) {
        this.mOutputImageRotationEnabled = z2;
    }

    public void setProcessedImageReaderProxy(@NonNull SafeCloseImageReaderProxy safeCloseImageReaderProxy) {
        synchronized (this.mAnalyzerLock) {
            this.mProcessedImageReaderProxy = safeCloseImageReaderProxy;
        }
    }

    public void setRelativeRotation(int i2) {
        this.mRelativeRotation = i2;
    }

    public void setSensorToBufferTransformMatrix(@NonNull Matrix matrix) {
        synchronized (this.mAnalyzerLock) {
            this.mOriginalSensorToBufferTransformMatrix = matrix;
            this.mUpdatedSensorToBufferTransformMatrix = new Matrix(this.mOriginalSensorToBufferTransformMatrix);
        }
    }

    public void setViewPortCropRect(@NonNull Rect rect) {
        synchronized (this.mAnalyzerLock) {
            this.mOriginalViewPortCropRect = rect;
            this.mUpdatedViewPortCropRect = new Rect(this.mOriginalViewPortCropRect);
        }
    }
}
