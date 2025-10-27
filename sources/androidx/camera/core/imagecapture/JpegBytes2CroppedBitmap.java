package androidx.camera.core.imagecapture;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.impl.utils.Exif;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.processing.Operation;
import androidx.camera.core.processing.Packet;
import java.io.IOException;
import java.util.Objects;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
final class JpegBytes2CroppedBitmap implements Operation<Packet<byte[]>, Packet<Bitmap>> {
    @NonNull
    private Bitmap createCroppedBitmap(@NonNull byte[] bArr, @NonNull Rect rect) throws ImageCaptureException, IOException {
        try {
            return BitmapRegionDecoder.newInstance(bArr, 0, bArr.length, false).decodeRegion(rect, new BitmapFactory.Options());
        } catch (IOException e2) {
            throw new ImageCaptureException(1, "Failed to decode JPEG.", e2);
        }
    }

    @Override // androidx.camera.core.processing.Operation
    @NonNull
    public Packet<Bitmap> apply(@NonNull Packet<byte[]> packet) throws ImageCaptureException, IOException {
        Rect cropRect = packet.getCropRect();
        Bitmap bitmapCreateCroppedBitmap = createCroppedBitmap(packet.getData(), cropRect);
        Exif exif = packet.getExif();
        Objects.requireNonNull(exif);
        return Packet.of(bitmapCreateCroppedBitmap, exif, new Rect(0, 0, bitmapCreateCroppedBitmap.getWidth(), bitmapCreateCroppedBitmap.getHeight()), packet.getRotationDegrees(), TransformUtils.updateSensorToBufferTransform(packet.getSensorToBufferTransform(), cropRect), packet.getCameraCaptureResult());
    }
}
