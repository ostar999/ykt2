package androidx.camera.core.processing;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.CameraCaptureResult;
import androidx.camera.core.impl.utils.Exif;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.core.util.Preconditions;
import com.google.auto.value.AutoValue;

@AutoValue
@RequiresApi(api = 21)
/* loaded from: classes.dex */
public abstract class Packet<T> {
    @NonNull
    public static Packet<Bitmap> of(@NonNull Bitmap bitmap, @NonNull Exif exif, @NonNull Rect rect, int i2, @NonNull Matrix matrix, @NonNull CameraCaptureResult cameraCaptureResult) {
        return new AutoValue_Packet(bitmap, exif, 42, new Size(bitmap.getWidth(), bitmap.getHeight()), rect, i2, matrix, cameraCaptureResult);
    }

    @NonNull
    public abstract CameraCaptureResult getCameraCaptureResult();

    @NonNull
    public abstract Rect getCropRect();

    @NonNull
    public abstract T getData();

    @Nullable
    public abstract Exif getExif();

    public abstract int getFormat();

    public abstract int getRotationDegrees();

    @NonNull
    public abstract Matrix getSensorToBufferTransform();

    @NonNull
    public abstract Size getSize();

    public boolean hasCropping() {
        return TransformUtils.hasCropping(getCropRect(), getSize());
    }

    @NonNull
    public static Packet<ImageProxy> of(@NonNull ImageProxy imageProxy, @Nullable Exif exif, @NonNull Rect rect, int i2, @NonNull Matrix matrix, @NonNull CameraCaptureResult cameraCaptureResult) {
        if (imageProxy.getFormat() == 256) {
            Preconditions.checkNotNull(exif, "JPEG image must have Exif.");
        }
        return new AutoValue_Packet(imageProxy, exif, imageProxy.getFormat(), new Size(imageProxy.getWidth(), imageProxy.getHeight()), rect, i2, matrix, cameraCaptureResult);
    }

    @NonNull
    public static Packet<byte[]> of(@NonNull byte[] bArr, @NonNull Exif exif, int i2, @NonNull Size size, @NonNull Rect rect, int i3, @NonNull Matrix matrix, @NonNull CameraCaptureResult cameraCaptureResult) {
        return new AutoValue_Packet(bArr, exif, i2, size, rect, i3, matrix, cameraCaptureResult);
    }
}
