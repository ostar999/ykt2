package androidx.camera.core.internal.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.YuvImage;
import android.util.Rational;
import android.util.Size;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Logger;
import androidx.core.util.Preconditions;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

@RequiresApi(21)
/* loaded from: classes.dex */
public final class ImageUtil {
    public static final int DEFAULT_RGBA_PIXEL_STRIDE = 4;
    private static final String TAG = "ImageUtil";

    private ImageUtil() {
    }

    @Nullable
    public static Rect computeCropRectFromAspectRatio(@NonNull Size size, @NonNull Rational rational) {
        int i2;
        if (!isAspectRatioValid(rational)) {
            Logger.w(TAG, "Invalid view ratio.");
            return null;
        }
        int width = size.getWidth();
        int height = size.getHeight();
        float f2 = width;
        float f3 = height;
        float f4 = f2 / f3;
        int numerator = rational.getNumerator();
        int denominator = rational.getDenominator();
        int i3 = 0;
        if (rational.floatValue() > f4) {
            int iRound = Math.round((f2 / numerator) * denominator);
            i2 = (height - iRound) / 2;
            height = iRound;
        } else {
            int iRound2 = Math.round((f3 / denominator) * numerator);
            int i4 = (width - iRound2) / 2;
            width = iRound2;
            i2 = 0;
            i3 = i4;
        }
        return new Rect(i3, i2, width + i3, height + i2);
    }

    @NonNull
    public static Rect computeCropRectFromDispatchInfo(@NonNull Rect rect, int i2, @NonNull Size size, int i3) {
        Matrix matrix = new Matrix();
        matrix.setRotate(i3 - i2);
        float[] fArrSizeToVertexes = sizeToVertexes(size);
        matrix.mapPoints(fArrSizeToVertexes);
        matrix.postTranslate(-min(fArrSizeToVertexes[0], fArrSizeToVertexes[2], fArrSizeToVertexes[4], fArrSizeToVertexes[6]), -min(fArrSizeToVertexes[1], fArrSizeToVertexes[3], fArrSizeToVertexes[5], fArrSizeToVertexes[7]));
        matrix.invert(matrix);
        RectF rectF = new RectF();
        matrix.mapRect(rectF, new RectF(rect));
        rectF.sort();
        Rect rect2 = new Rect();
        rectF.round(rect2);
        return rect2;
    }

    @NonNull
    public static Bitmap createBitmapFromPlane(@NonNull ImageProxy.PlaneProxy[] planeProxyArr, int i2, int i3) {
        Preconditions.checkArgument(planeProxyArr.length == 1, "Expect a single plane");
        Preconditions.checkArgument(planeProxyArr[0].getPixelStride() == 4, "Expect pixelStride=4");
        Preconditions.checkArgument(planeProxyArr[0].getRowStride() == i2 * 4, "Expect rowStride=width*4");
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        bitmapCreateBitmap.copyPixelsFromBuffer(planeProxyArr[0].getBuffer());
        return bitmapCreateBitmap;
    }

    @NonNull
    public static ByteBuffer createDirectByteBuffer(@NonNull Bitmap bitmap) {
        Preconditions.checkArgument(bitmap.getConfig() == Bitmap.Config.ARGB_8888, "Only accept Bitmap with ARGB_8888 format for now.");
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(bitmap.getAllocationByteCount());
        bitmap.copyPixelsToBuffer(byteBufferAllocateDirect);
        byteBufferAllocateDirect.rewind();
        return byteBufferAllocateDirect;
    }

    @NonNull
    private static byte[] cropJpegByteArray(@NonNull byte[] bArr, @NonNull Rect rect, @IntRange(from = 1, to = 100) int i2) throws IOException, CodecFailedException {
        try {
            BitmapRegionDecoder bitmapRegionDecoderNewInstance = BitmapRegionDecoder.newInstance(bArr, 0, bArr.length, false);
            Bitmap bitmapDecodeRegion = bitmapRegionDecoderNewInstance.decodeRegion(rect, new BitmapFactory.Options());
            bitmapRegionDecoderNewInstance.recycle();
            if (bitmapDecodeRegion == null) {
                throw new CodecFailedException("Decode byte array failed.", CodecFailedException.FailureType.DECODE_FAILED);
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (!bitmapDecodeRegion.compress(Bitmap.CompressFormat.JPEG, i2, byteArrayOutputStream)) {
                throw new CodecFailedException("Encode bitmap failed.", CodecFailedException.FailureType.ENCODE_FAILED);
            }
            bitmapDecodeRegion.recycle();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            throw new CodecFailedException("Decode byte array failed.", CodecFailedException.FailureType.DECODE_FAILED);
        } catch (IllegalArgumentException e2) {
            throw new CodecFailedException("Decode byte array failed with illegal argument." + e2, CodecFailedException.FailureType.DECODE_FAILED);
        }
    }

    @NonNull
    public static Rational getRotatedAspectRatio(@IntRange(from = 0, to = 359) int i2, @NonNull Rational rational) {
        return (i2 == 90 || i2 == 270) ? inverseRational(rational) : new Rational(rational.getNumerator(), rational.getDenominator());
    }

    private static Rational inverseRational(@Nullable Rational rational) {
        return rational == null ? rational : new Rational(rational.getDenominator(), rational.getNumerator());
    }

    public static boolean isAspectRatioValid(@Nullable Rational rational) {
        return (rational == null || rational.floatValue() <= 0.0f || rational.isNaN()) ? false : true;
    }

    private static boolean isCropAspectRatioHasEffect(@NonNull Size size, @NonNull Rational rational) {
        int width = size.getWidth();
        int height = size.getHeight();
        float numerator = rational.getNumerator();
        float denominator = rational.getDenominator();
        return (height == Math.round((((float) width) / numerator) * denominator) && width == Math.round((((float) height) / denominator) * numerator)) ? false : true;
    }

    @NonNull
    public static byte[] jpegImageToJpegByteArray(@NonNull ImageProxy imageProxy) {
        if (imageProxy.getFormat() != 256) {
            throw new IllegalArgumentException("Incorrect image format of the input image proxy: " + imageProxy.getFormat());
        }
        ByteBuffer buffer = imageProxy.getPlanes()[0].getBuffer();
        byte[] bArr = new byte[buffer.capacity()];
        buffer.rewind();
        buffer.get(bArr);
        return bArr;
    }

    public static float min(float f2, float f3, float f4, float f5) {
        return Math.min(Math.min(f2, f3), Math.min(f4, f5));
    }

    private static byte[] nv21ToJpeg(@NonNull byte[] bArr, int i2, int i3, @Nullable Rect rect, @IntRange(from = 1, to = 100) int i4) throws CodecFailedException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        YuvImage yuvImage = new YuvImage(bArr, 17, i2, i3, null);
        if (rect == null) {
            rect = new Rect(0, 0, i2, i3);
        }
        if (yuvImage.compressToJpeg(rect, i4, byteArrayOutputStream)) {
            return byteArrayOutputStream.toByteArray();
        }
        throw new CodecFailedException("YuvImage failed to encode jpeg.", CodecFailedException.FailureType.ENCODE_FAILED);
    }

    public static boolean shouldCropImage(int i2, int i3, int i4, int i5) {
        return (i2 == i4 && i3 == i5) ? false : true;
    }

    public static boolean shouldCropImage(@NonNull ImageProxy imageProxy) {
        return shouldCropImage(imageProxy.getWidth(), imageProxy.getHeight(), imageProxy.getCropRect().width(), imageProxy.getCropRect().height());
    }

    @NonNull
    public static float[] sizeToVertexes(@NonNull Size size) {
        return new float[]{0.0f, 0.0f, size.getWidth(), 0.0f, size.getWidth(), size.getHeight(), 0.0f, size.getHeight()};
    }

    @NonNull
    public static byte[] yuvImageToJpegByteArray(@NonNull ImageProxy imageProxy, @Nullable Rect rect, @IntRange(from = 1, to = 100) int i2) throws CodecFailedException {
        if (imageProxy.getFormat() == 35) {
            return nv21ToJpeg(yuv_420_888toNv21(imageProxy), imageProxy.getWidth(), imageProxy.getHeight(), rect, i2);
        }
        throw new IllegalArgumentException("Incorrect image format of the input image proxy: " + imageProxy.getFormat());
    }

    @NonNull
    public static byte[] yuv_420_888toNv21(@NonNull ImageProxy imageProxy) {
        ImageProxy.PlaneProxy planeProxy = imageProxy.getPlanes()[0];
        ImageProxy.PlaneProxy planeProxy2 = imageProxy.getPlanes()[1];
        ImageProxy.PlaneProxy planeProxy3 = imageProxy.getPlanes()[2];
        ByteBuffer buffer = planeProxy.getBuffer();
        ByteBuffer buffer2 = planeProxy2.getBuffer();
        ByteBuffer buffer3 = planeProxy3.getBuffer();
        buffer.rewind();
        buffer2.rewind();
        buffer3.rewind();
        int iRemaining = buffer.remaining();
        byte[] bArr = new byte[((imageProxy.getWidth() * imageProxy.getHeight()) / 2) + iRemaining];
        int width = 0;
        for (int i2 = 0; i2 < imageProxy.getHeight(); i2++) {
            buffer.get(bArr, width, imageProxy.getWidth());
            width += imageProxy.getWidth();
            buffer.position(Math.min(iRemaining, (buffer.position() - imageProxy.getWidth()) + planeProxy.getRowStride()));
        }
        int height = imageProxy.getHeight() / 2;
        int width2 = imageProxy.getWidth() / 2;
        int rowStride = planeProxy3.getRowStride();
        int rowStride2 = planeProxy2.getRowStride();
        int pixelStride = planeProxy3.getPixelStride();
        int pixelStride2 = planeProxy2.getPixelStride();
        byte[] bArr2 = new byte[rowStride];
        byte[] bArr3 = new byte[rowStride2];
        for (int i3 = 0; i3 < height; i3++) {
            buffer3.get(bArr2, 0, Math.min(rowStride, buffer3.remaining()));
            buffer2.get(bArr3, 0, Math.min(rowStride2, buffer2.remaining()));
            int i4 = 0;
            int i5 = 0;
            for (int i6 = 0; i6 < width2; i6++) {
                int i7 = width + 1;
                bArr[width] = bArr2[i4];
                width = i7 + 1;
                bArr[i7] = bArr3[i5];
                i4 += pixelStride;
                i5 += pixelStride2;
            }
        }
        return bArr;
    }

    public static final class CodecFailedException extends Exception {
        private FailureType mFailureType;

        public enum FailureType {
            ENCODE_FAILED,
            DECODE_FAILED,
            UNKNOWN
        }

        public CodecFailedException(@NonNull String str) {
            super(str);
            this.mFailureType = FailureType.UNKNOWN;
        }

        @NonNull
        public FailureType getFailureType() {
            return this.mFailureType;
        }

        public CodecFailedException(@NonNull String str, @NonNull FailureType failureType) {
            super(str);
            this.mFailureType = failureType;
        }
    }

    public static boolean isAspectRatioValid(@NonNull Size size, @Nullable Rational rational) {
        return rational != null && rational.floatValue() > 0.0f && isCropAspectRatioHasEffect(size, rational) && !rational.isNaN();
    }

    @NonNull
    public static byte[] jpegImageToJpegByteArray(@NonNull ImageProxy imageProxy, @NonNull Rect rect, @IntRange(from = 1, to = 100) int i2) throws CodecFailedException {
        if (imageProxy.getFormat() == 256) {
            return cropJpegByteArray(jpegImageToJpegByteArray(imageProxy), rect, i2);
        }
        throw new IllegalArgumentException("Incorrect image format of the input image proxy: " + imageProxy.getFormat());
    }
}
