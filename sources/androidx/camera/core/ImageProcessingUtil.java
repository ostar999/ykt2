package androidx.camera.core;

import android.media.Image;
import android.media.ImageWriter;
import android.util.Log;
import android.view.Surface;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.camera.core.ForwardingImageProxy;
import androidx.camera.core.impl.ImageReaderProxy;
import androidx.camera.core.internal.compat.ImageWriterCompat;
import androidx.core.util.Preconditions;
import java.nio.ByteBuffer;
import java.util.Locale;

@RequiresApi(21)
@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public final class ImageProcessingUtil {
    private static final String TAG = "ImageProcessingUtil";
    private static int sImageCount;

    public enum Result {
        UNKNOWN,
        SUCCESS,
        ERROR_CONVERSION
    }

    static {
        System.loadLibrary("image_processing_util_jni");
    }

    private ImageProcessingUtil() {
    }

    public static boolean applyPixelShiftForYUV(@NonNull ImageProxy imageProxy) {
        if (!isSupportedYUVFormat(imageProxy)) {
            Logger.e(TAG, "Unsupported format for YUV to RGB");
            return false;
        }
        if (applyPixelShiftInternal(imageProxy) != Result.ERROR_CONVERSION) {
            return true;
        }
        Logger.e(TAG, "One pixel shift for YUV failure");
        return false;
    }

    @NonNull
    private static Result applyPixelShiftInternal(@NonNull ImageProxy imageProxy) {
        int width = imageProxy.getWidth();
        int height = imageProxy.getHeight();
        int rowStride = imageProxy.getPlanes()[0].getRowStride();
        int rowStride2 = imageProxy.getPlanes()[1].getRowStride();
        int rowStride3 = imageProxy.getPlanes()[2].getRowStride();
        int pixelStride = imageProxy.getPlanes()[0].getPixelStride();
        int pixelStride2 = imageProxy.getPlanes()[1].getPixelStride();
        return nativeShiftPixel(imageProxy.getPlanes()[0].getBuffer(), rowStride, imageProxy.getPlanes()[1].getBuffer(), rowStride2, imageProxy.getPlanes()[2].getBuffer(), rowStride3, pixelStride, pixelStride2, width, height, pixelStride, pixelStride2, pixelStride2) != 0 ? Result.ERROR_CONVERSION : Result.SUCCESS;
    }

    @Nullable
    public static ImageProxy convertJpegBytesToImage(@NonNull ImageReaderProxy imageReaderProxy, @NonNull byte[] bArr) {
        Preconditions.checkArgument(imageReaderProxy.getImageFormat() == 256);
        Preconditions.checkNotNull(bArr);
        Surface surface = imageReaderProxy.getSurface();
        Preconditions.checkNotNull(surface);
        if (nativeWriteJpegToSurface(bArr, surface) != 0) {
            Logger.e(TAG, "Failed to enqueue JPEG image.");
            return null;
        }
        ImageProxy imageProxyAcquireLatestImage = imageReaderProxy.acquireLatestImage();
        if (imageProxyAcquireLatestImage == null) {
            Logger.e(TAG, "Failed to get acquire JPEG image.");
        }
        return imageProxyAcquireLatestImage;
    }

    @Nullable
    public static ImageProxy convertYUVToRGB(@NonNull final ImageProxy imageProxy, @NonNull ImageReaderProxy imageReaderProxy, @Nullable ByteBuffer byteBuffer, @IntRange(from = 0, to = 359) int i2, boolean z2) {
        if (!isSupportedYUVFormat(imageProxy)) {
            Logger.e(TAG, "Unsupported format for YUV to RGB");
            return null;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (!isSupportedRotationDegrees(i2)) {
            Logger.e(TAG, "Unsupported rotation degrees for rotate RGB");
            return null;
        }
        if (convertYUVToRGBInternal(imageProxy, imageReaderProxy.getSurface(), byteBuffer, i2, z2) == Result.ERROR_CONVERSION) {
            Logger.e(TAG, "YUV to RGB conversion failure");
            return null;
        }
        if (Log.isLoggable("MH", 3)) {
            Logger.d(TAG, String.format(Locale.US, "Image processing performance profiling, duration: [%d], image count: %d", Long.valueOf(System.currentTimeMillis() - jCurrentTimeMillis), Integer.valueOf(sImageCount)));
            sImageCount++;
        }
        final ImageProxy imageProxyAcquireLatestImage = imageReaderProxy.acquireLatestImage();
        if (imageProxyAcquireLatestImage == null) {
            Logger.e(TAG, "YUV to RGB acquireLatestImage failure");
            return null;
        }
        SingleCloseImageProxy singleCloseImageProxy = new SingleCloseImageProxy(imageProxyAcquireLatestImage);
        singleCloseImageProxy.addOnImageCloseListener(new ForwardingImageProxy.OnImageCloseListener() { // from class: androidx.camera.core.s0
            @Override // androidx.camera.core.ForwardingImageProxy.OnImageCloseListener
            public final void onImageClose(ImageProxy imageProxy2) {
                ImageProcessingUtil.lambda$convertYUVToRGB$0(imageProxyAcquireLatestImage, imageProxy, imageProxy2);
            }
        });
        return singleCloseImageProxy;
    }

    @NonNull
    private static Result convertYUVToRGBInternal(@NonNull ImageProxy imageProxy, @NonNull Surface surface, @Nullable ByteBuffer byteBuffer, int i2, boolean z2) {
        int width = imageProxy.getWidth();
        int height = imageProxy.getHeight();
        int rowStride = imageProxy.getPlanes()[0].getRowStride();
        int rowStride2 = imageProxy.getPlanes()[1].getRowStride();
        int rowStride3 = imageProxy.getPlanes()[2].getRowStride();
        int pixelStride = imageProxy.getPlanes()[0].getPixelStride();
        int pixelStride2 = imageProxy.getPlanes()[1].getPixelStride();
        return nativeConvertAndroid420ToABGR(imageProxy.getPlanes()[0].getBuffer(), rowStride, imageProxy.getPlanes()[1].getBuffer(), rowStride2, imageProxy.getPlanes()[2].getBuffer(), rowStride3, pixelStride, pixelStride2, surface, byteBuffer, width, height, z2 ? pixelStride : 0, z2 ? pixelStride2 : 0, z2 ? pixelStride2 : 0, i2) != 0 ? Result.ERROR_CONVERSION : Result.SUCCESS;
    }

    private static boolean isSupportedRotationDegrees(@IntRange(from = 0, to = 359) int i2) {
        return i2 == 0 || i2 == 90 || i2 == 180 || i2 == 270;
    }

    private static boolean isSupportedYUVFormat(@NonNull ImageProxy imageProxy) {
        return imageProxy.getFormat() == 35 && imageProxy.getPlanes().length == 3;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$convertYUVToRGB$0(ImageProxy imageProxy, ImageProxy imageProxy2, ImageProxy imageProxy3) {
        if (imageProxy == null || imageProxy2 == null) {
            return;
        }
        imageProxy2.close();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$rotateYUV$1(ImageProxy imageProxy, ImageProxy imageProxy2, ImageProxy imageProxy3) {
        if (imageProxy == null || imageProxy2 == null) {
            return;
        }
        imageProxy2.close();
    }

    private static native int nativeConvertAndroid420ToABGR(@NonNull ByteBuffer byteBuffer, int i2, @NonNull ByteBuffer byteBuffer2, int i3, @NonNull ByteBuffer byteBuffer3, int i4, int i5, int i6, @NonNull Surface surface, @Nullable ByteBuffer byteBuffer4, int i7, int i8, int i9, int i10, int i11, int i12);

    private static native int nativeRotateYUV(@NonNull ByteBuffer byteBuffer, int i2, @NonNull ByteBuffer byteBuffer2, int i3, @NonNull ByteBuffer byteBuffer3, int i4, int i5, @NonNull ByteBuffer byteBuffer4, int i6, int i7, @NonNull ByteBuffer byteBuffer5, int i8, int i9, @NonNull ByteBuffer byteBuffer6, int i10, int i11, @NonNull ByteBuffer byteBuffer7, @NonNull ByteBuffer byteBuffer8, @NonNull ByteBuffer byteBuffer9, int i12, int i13, int i14);

    private static native int nativeShiftPixel(@NonNull ByteBuffer byteBuffer, int i2, @NonNull ByteBuffer byteBuffer2, int i3, @NonNull ByteBuffer byteBuffer3, int i4, int i5, int i6, int i7, int i8, int i9, int i10, int i11);

    private static native int nativeWriteJpegToSurface(@NonNull byte[] bArr, @NonNull Surface surface);

    @Nullable
    public static ImageProxy rotateYUV(@NonNull final ImageProxy imageProxy, @NonNull ImageReaderProxy imageReaderProxy, @NonNull ImageWriter imageWriter, @NonNull ByteBuffer byteBuffer, @NonNull ByteBuffer byteBuffer2, @NonNull ByteBuffer byteBuffer3, @IntRange(from = 0, to = 359) int i2) {
        if (!isSupportedYUVFormat(imageProxy)) {
            Logger.e(TAG, "Unsupported format for rotate YUV");
            return null;
        }
        if (!isSupportedRotationDegrees(i2)) {
            Logger.e(TAG, "Unsupported rotation degrees for rotate YUV");
            return null;
        }
        Result result = Result.ERROR_CONVERSION;
        if ((i2 > 0 ? rotateYUVInternal(imageProxy, imageWriter, byteBuffer, byteBuffer2, byteBuffer3, i2) : result) == result) {
            Logger.e(TAG, "rotate YUV failure");
            return null;
        }
        final ImageProxy imageProxyAcquireLatestImage = imageReaderProxy.acquireLatestImage();
        if (imageProxyAcquireLatestImage == null) {
            Logger.e(TAG, "YUV rotation acquireLatestImage failure");
            return null;
        }
        SingleCloseImageProxy singleCloseImageProxy = new SingleCloseImageProxy(imageProxyAcquireLatestImage);
        singleCloseImageProxy.addOnImageCloseListener(new ForwardingImageProxy.OnImageCloseListener() { // from class: androidx.camera.core.t0
            @Override // androidx.camera.core.ForwardingImageProxy.OnImageCloseListener
            public final void onImageClose(ImageProxy imageProxy2) {
                ImageProcessingUtil.lambda$rotateYUV$1(imageProxyAcquireLatestImage, imageProxy, imageProxy2);
            }
        });
        return singleCloseImageProxy;
    }

    @Nullable
    @RequiresApi(23)
    private static Result rotateYUVInternal(@NonNull ImageProxy imageProxy, @NonNull ImageWriter imageWriter, @NonNull ByteBuffer byteBuffer, @NonNull ByteBuffer byteBuffer2, @NonNull ByteBuffer byteBuffer3, int i2) {
        int width = imageProxy.getWidth();
        int height = imageProxy.getHeight();
        int rowStride = imageProxy.getPlanes()[0].getRowStride();
        int rowStride2 = imageProxy.getPlanes()[1].getRowStride();
        int rowStride3 = imageProxy.getPlanes()[2].getRowStride();
        int pixelStride = imageProxy.getPlanes()[1].getPixelStride();
        Image imageDequeueInputImage = ImageWriterCompat.dequeueInputImage(imageWriter);
        if (imageDequeueInputImage == null) {
            return Result.ERROR_CONVERSION;
        }
        if (nativeRotateYUV(imageProxy.getPlanes()[0].getBuffer(), rowStride, imageProxy.getPlanes()[1].getBuffer(), rowStride2, imageProxy.getPlanes()[2].getBuffer(), rowStride3, pixelStride, imageDequeueInputImage.getPlanes()[0].getBuffer(), imageDequeueInputImage.getPlanes()[0].getRowStride(), imageDequeueInputImage.getPlanes()[0].getPixelStride(), imageDequeueInputImage.getPlanes()[1].getBuffer(), imageDequeueInputImage.getPlanes()[1].getRowStride(), imageDequeueInputImage.getPlanes()[1].getPixelStride(), imageDequeueInputImage.getPlanes()[2].getBuffer(), imageDequeueInputImage.getPlanes()[2].getRowStride(), imageDequeueInputImage.getPlanes()[2].getPixelStride(), byteBuffer, byteBuffer2, byteBuffer3, width, height, i2) != 0) {
            return Result.ERROR_CONVERSION;
        }
        ImageWriterCompat.queueInputImage(imageWriter, imageDequeueInputImage);
        return Result.SUCCESS;
    }
}
