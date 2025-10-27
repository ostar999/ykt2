package androidx.camera.camera2.internal;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.params.InputConfiguration;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageWriter;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.VisibleForTesting;
import androidx.camera.camera2.internal.compat.CameraCharacteristicsCompat;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Logger;
import androidx.camera.core.MetadataImageReader;
import androidx.camera.core.SafeCloseImageReaderProxy;
import androidx.camera.core.impl.CameraCaptureCallback;
import androidx.camera.core.impl.DeferrableSurface;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.impl.ImmediateSurface;
import androidx.camera.core.impl.SessionConfig;
import androidx.camera.core.impl.utils.CompareSizesByArea;
import androidx.camera.core.impl.utils.executor.CameraXExecutors;
import androidx.camera.core.internal.compat.ImageWriterCompat;
import androidx.camera.core.internal.utils.RingBuffer;
import androidx.camera.core.internal.utils.ZslRingBuffer;
import com.google.common.util.concurrent.ListenableFuture;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

@RequiresApi(23)
/* loaded from: classes.dex */
final class ZslControlImpl implements ZslControl {

    @VisibleForTesting
    static final int MAX_IMAGES = 9;

    @VisibleForTesting
    static final int RING_BUFFER_CAPACITY = 3;
    private static final String TAG = "ZslControlImpl";

    @NonNull
    private final CameraCharacteristicsCompat mCameraCharacteristicsCompat;
    private boolean mIsPrivateReprocessingSupported;
    private CameraCaptureCallback mMetadataMatchingCaptureCallback;
    private DeferrableSurface mReprocessingImageDeferrableSurface;
    SafeCloseImageReaderProxy mReprocessingImageReader;

    @Nullable
    ImageWriter mReprocessingImageWriter;

    @NonNull
    private final Map<Integer, Size> mReprocessingInputSizeMap;
    private boolean mIsZslDisabledByUseCaseConfig = false;
    private boolean mIsZslDisabledByFlashMode = false;

    @NonNull
    @VisibleForTesting
    final ZslRingBuffer mImageRingBuffer = new ZslRingBuffer(3, new RingBuffer.OnRemoveCallback() { // from class: androidx.camera.camera2.internal.p2
        @Override // androidx.camera.core.internal.utils.RingBuffer.OnRemoveCallback
        public final void onRemove(Object obj) {
            ((ImageProxy) obj).close();
        }
    });

    public ZslControlImpl(@NonNull CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        this.mIsPrivateReprocessingSupported = false;
        this.mCameraCharacteristicsCompat = cameraCharacteristicsCompat;
        this.mIsPrivateReprocessingSupported = ZslUtil.isCapabilitySupported(cameraCharacteristicsCompat, 4);
        this.mReprocessingInputSizeMap = createReprocessingInputSizeMap(cameraCharacteristicsCompat);
    }

    private void cleanup() {
        ZslRingBuffer zslRingBuffer = this.mImageRingBuffer;
        while (!zslRingBuffer.isEmpty()) {
            zslRingBuffer.dequeue().close();
        }
        DeferrableSurface deferrableSurface = this.mReprocessingImageDeferrableSurface;
        if (deferrableSurface != null) {
            SafeCloseImageReaderProxy safeCloseImageReaderProxy = this.mReprocessingImageReader;
            if (safeCloseImageReaderProxy != null) {
                deferrableSurface.getTerminationFuture().addListener(new o2(safeCloseImageReaderProxy), CameraXExecutors.mainThreadExecutor());
                this.mReprocessingImageReader = null;
            }
            deferrableSurface.close();
            this.mReprocessingImageDeferrableSurface = null;
        }
        ImageWriter imageWriter = this.mReprocessingImageWriter;
        if (imageWriter != null) {
            imageWriter.close();
            this.mReprocessingImageWriter = null;
        }
    }

    @NonNull
    private Map<Integer, Size> createReprocessingInputSizeMap(@NonNull CameraCharacteristicsCompat cameraCharacteristicsCompat) {
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristicsCompat.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap == null || streamConfigurationMap.getInputFormats() == null) {
            return new HashMap();
        }
        HashMap map = new HashMap();
        for (int i2 : streamConfigurationMap.getInputFormats()) {
            Size[] inputSizes = streamConfigurationMap.getInputSizes(i2);
            if (inputSizes != null) {
                Arrays.sort(inputSizes, new CompareSizesByArea(true));
                map.put(Integer.valueOf(i2), inputSizes[0]);
            }
        }
        return map;
    }

    private boolean isJpegValidOutputForInputFormat(@NonNull CameraCharacteristicsCompat cameraCharacteristicsCompat, int i2) {
        int[] validOutputFormatsForInput;
        StreamConfigurationMap streamConfigurationMap = (StreamConfigurationMap) cameraCharacteristicsCompat.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);
        if (streamConfigurationMap == null || (validOutputFormatsForInput = streamConfigurationMap.getValidOutputFormatsForInput(i2)) == null) {
            return false;
        }
        for (int i3 : validOutputFormatsForInput) {
            if (i3 == 256) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$addZslConfig$1(ImageReaderProxy imageReaderProxy) {
        try {
            ImageProxy imageProxyAcquireLatestImage = imageReaderProxy.acquireLatestImage();
            if (imageProxyAcquireLatestImage != null) {
                this.mImageRingBuffer.enqueue(imageProxyAcquireLatestImage);
            }
        } catch (IllegalStateException e2) {
            Logger.e(TAG, "Failed to acquire latest image IllegalStateException = " + e2.getMessage());
        }
    }

    @Override // androidx.camera.camera2.internal.ZslControl
    public void addZslConfig(@NonNull SessionConfig.Builder builder) {
        cleanup();
        if (!this.mIsZslDisabledByUseCaseConfig && this.mIsPrivateReprocessingSupported && !this.mReprocessingInputSizeMap.isEmpty() && this.mReprocessingInputSizeMap.containsKey(34) && isJpegValidOutputForInputFormat(this.mCameraCharacteristicsCompat, 34)) {
            Size size = this.mReprocessingInputSizeMap.get(34);
            MetadataImageReader metadataImageReader = new MetadataImageReader(size.getWidth(), size.getHeight(), 34, 9);
            this.mMetadataMatchingCaptureCallback = metadataImageReader.getCameraCaptureCallback();
            this.mReprocessingImageReader = new SafeCloseImageReaderProxy(metadataImageReader);
            metadataImageReader.setOnImageAvailableListener(new ImageReaderProxy.OnImageAvailableListener() { // from class: androidx.camera.camera2.internal.n2
                @Override // androidx.camera.core.impl.ImageReaderProxy.OnImageAvailableListener
                public final void onImageAvailable(ImageReaderProxy imageReaderProxy) {
                    this.f1390a.lambda$addZslConfig$1(imageReaderProxy);
                }
            }, CameraXExecutors.ioExecutor());
            ImmediateSurface immediateSurface = new ImmediateSurface(this.mReprocessingImageReader.getSurface(), new Size(this.mReprocessingImageReader.getWidth(), this.mReprocessingImageReader.getHeight()), 34);
            this.mReprocessingImageDeferrableSurface = immediateSurface;
            SafeCloseImageReaderProxy safeCloseImageReaderProxy = this.mReprocessingImageReader;
            ListenableFuture<Void> terminationFuture = immediateSurface.getTerminationFuture();
            Objects.requireNonNull(safeCloseImageReaderProxy);
            terminationFuture.addListener(new o2(safeCloseImageReaderProxy), CameraXExecutors.mainThreadExecutor());
            builder.addSurface(this.mReprocessingImageDeferrableSurface);
            builder.addCameraCaptureCallback(this.mMetadataMatchingCaptureCallback);
            builder.addSessionStateCallback(new CameraCaptureSession.StateCallback() { // from class: androidx.camera.camera2.internal.ZslControlImpl.1
                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onConfigureFailed(@NonNull CameraCaptureSession cameraCaptureSession) {
                }

                @Override // android.hardware.camera2.CameraCaptureSession.StateCallback
                public void onConfigured(@NonNull CameraCaptureSession cameraCaptureSession) {
                    Surface inputSurface = cameraCaptureSession.getInputSurface();
                    if (inputSurface != null) {
                        ZslControlImpl.this.mReprocessingImageWriter = ImageWriterCompat.newInstance(inputSurface, 1);
                    }
                }
            });
            builder.setInputConfiguration(new InputConfiguration(this.mReprocessingImageReader.getWidth(), this.mReprocessingImageReader.getHeight(), this.mReprocessingImageReader.getImageFormat()));
        }
    }

    @Override // androidx.camera.camera2.internal.ZslControl
    @Nullable
    public ImageProxy dequeueImageFromBuffer() {
        try {
            return this.mImageRingBuffer.dequeue();
        } catch (NoSuchElementException unused) {
            Logger.e(TAG, "dequeueImageFromBuffer no such element");
            return null;
        }
    }

    @Override // androidx.camera.camera2.internal.ZslControl
    public boolean enqueueImageToImageWriter(@NonNull ImageProxy imageProxy) {
        Image image = imageProxy.getImage();
        ImageWriter imageWriter = this.mReprocessingImageWriter;
        if (imageWriter != null && image != null) {
            try {
                ImageWriterCompat.queueInputImage(imageWriter, image);
                return true;
            } catch (IllegalStateException e2) {
                Logger.e(TAG, "enqueueImageToImageWriter throws IllegalStateException = " + e2.getMessage());
            }
        }
        return false;
    }

    @Override // androidx.camera.camera2.internal.ZslControl
    public boolean isZslDisabledByFlashMode() {
        return this.mIsZslDisabledByFlashMode;
    }

    @Override // androidx.camera.camera2.internal.ZslControl
    public boolean isZslDisabledByUserCaseConfig() {
        return this.mIsZslDisabledByUseCaseConfig;
    }

    @Override // androidx.camera.camera2.internal.ZslControl
    public void setZslDisabledByFlashMode(boolean z2) {
        this.mIsZslDisabledByFlashMode = z2;
    }

    @Override // androidx.camera.camera2.internal.ZslControl
    public void setZslDisabledByUserCaseConfig(boolean z2) {
        this.mIsZslDisabledByUseCaseConfig = z2;
    }
}
