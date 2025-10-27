package androidx.camera.core.imagecapture;

import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Size;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.impl.utils.Exif;
import androidx.camera.core.impl.utils.ExifData;
import androidx.camera.core.impl.utils.ExifOutputStream;
import androidx.camera.core.impl.utils.TransformUtils;
import androidx.camera.core.internal.ByteBufferOutputStream;
import androidx.camera.core.internal.utils.ImageUtil;
import androidx.camera.core.processing.Operation;
import androidx.camera.core.processing.Packet;
import com.google.auto.value.AutoValue;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;

@RequiresApi(api = 21)
/* loaded from: classes.dex */
final class Image2JpegBytes implements Operation<In, Packet<byte[]>> {

    @AutoValue
    public static abstract class In {
        @NonNull
        public static In of(@NonNull Packet<ImageProxy> packet, int i2) {
            return new AutoValue_Image2JpegBytes_In(packet, i2);
        }

        public abstract int getJpegQuality();

        public abstract Packet<ImageProxy> getPacket();
    }

    private static byte[] byteBufferToByteArray(@NonNull ByteBuffer byteBuffer) {
        int iPosition = byteBuffer.position();
        byte[] bArr = new byte[iPosition];
        byteBuffer.rewind();
        byteBuffer.get(bArr, 0, iPosition);
        return bArr;
    }

    private static Exif extractExif(@NonNull byte[] bArr) throws ImageCaptureException {
        try {
            return Exif.createFromInputStream(new ByteArrayInputStream(bArr));
        } catch (IOException e2) {
            throw new ImageCaptureException(0, "Failed to extract Exif from YUV-generated JPEG", e2);
        }
    }

    private Packet<byte[]> processJpegImage(@NonNull In in) {
        Packet<ImageProxy> packet = in.getPacket();
        byte[] bArrJpegImageToJpegByteArray = ImageUtil.jpegImageToJpegByteArray(packet.getData());
        Exif exif = packet.getExif();
        Objects.requireNonNull(exif);
        return Packet.of(bArrJpegImageToJpegByteArray, exif, 256, packet.getSize(), packet.getCropRect(), packet.getRotationDegrees(), packet.getSensorToBufferTransform(), packet.getCameraCaptureResult());
    }

    private Packet<byte[]> processYuvImage(@NonNull In in) throws ImageCaptureException {
        Packet<ImageProxy> packet = in.getPacket();
        ImageProxy data = packet.getData();
        Rect cropRect = packet.getCropRect();
        YuvImage yuvImage = new YuvImage(ImageUtil.yuv_420_888toNv21(data), 17, data.getWidth(), data.getHeight(), null);
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(cropRect.width() * cropRect.height() * 2);
        yuvImage.compressToJpeg(cropRect, in.getJpegQuality(), new ExifOutputStream(new ByteBufferOutputStream(byteBufferAllocateDirect), ExifData.create(data, packet.getRotationDegrees())));
        byte[] bArrByteBufferToByteArray = byteBufferToByteArray(byteBufferAllocateDirect);
        return Packet.of(bArrByteBufferToByteArray, extractExif(bArrByteBufferToByteArray), 256, new Size(cropRect.width(), cropRect.height()), new Rect(0, 0, cropRect.width(), cropRect.height()), packet.getRotationDegrees(), TransformUtils.updateSensorToBufferTransform(packet.getSensorToBufferTransform(), cropRect), packet.getCameraCaptureResult());
    }

    @Override // androidx.camera.core.processing.Operation
    @NonNull
    public Packet<byte[]> apply(@NonNull In in) throws ImageCaptureException {
        Packet<byte[]> packetProcessYuvImage;
        try {
            int format = in.getPacket().getFormat();
            if (format == 35) {
                packetProcessYuvImage = processYuvImage(in);
            } else {
                if (format != 256) {
                    throw new IllegalArgumentException("Unexpected format: " + format);
                }
                packetProcessYuvImage = processJpegImage(in);
            }
            return packetProcessYuvImage;
        } finally {
            in.getPacket().getData().close();
        }
    }
}
