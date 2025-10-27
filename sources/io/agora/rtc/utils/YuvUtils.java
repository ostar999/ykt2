package io.agora.rtc.utils;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.media.Image;
import android.util.Log;
import io.agora.rtc.gl.JavaI420Buffer;
import io.agora.rtc.gl.VideoFrame;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

@TargetApi(21)
/* loaded from: classes8.dex */
public class YuvUtils {
    public static final int I420 = 35;
    public static final int NV21 = 17;
    private static final String TAG = "YuvUtils";

    public static class Plane {
        private ByteBuffer buffer;
        private int pixelStride;
        private int rowStride;

        public Plane(ByteBuffer buffer, int rowStride, int pixelStride) {
            this.buffer = buffer;
            this.rowStride = rowStride;
            this.pixelStride = pixelStride;
        }

        public ByteBuffer getBuffer() {
            return this.buffer;
        }

        public int getPixelStride() {
            return this.pixelStride;
        }

        public int getRowStride() {
            return this.rowStride;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:31:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007f  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0097  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static byte[] getImageData(android.media.Image r20, int r21) {
        /*
            Method dump skipped, instructions count: 249
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: io.agora.rtc.utils.YuvUtils.getImageData(android.media.Image, int):byte[]");
    }

    public static boolean supportedImageFormat(Image image) {
        int format = image.getFormat();
        return format == 17 || format == 35 || format == 842094169;
    }

    public static void write420ImageToFile(Image image, String filePath) {
        if (image == null) {
            return;
        }
        try {
            YuvImage yuvImage = new YuvImage(yuv420toNV21(image), 17, image.getWidth(), image.getHeight(), null);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            yuvImage.compressToJpeg(new Rect(0, 0, image.getWidth(), image.getHeight()), 100, byteArrayOutputStream);
            File file = new File(filePath);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e2) {
            Log.e(TAG, e2.toString());
        }
    }

    public static boolean writeNV21ToFile(byte[] imageData, int width, int height, String filePath) throws IOException {
        YuvImage yuvImage = new YuvImage(imageData, 17, width, height, null);
        Rect rect = new Rect(0, 0, width, height);
        try {
            File file = new File(filePath);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            yuvImage.compressToJpeg(rect, 100, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            return true;
        } catch (IOException e2) {
            Log.e(TAG, e2.toString());
            return false;
        }
    }

    public static void writeRawData(byte[] data, String filePath) {
        if (data != null) {
            if (data.length == 0) {
                return;
            }
            try {
                File file = new File(filePath);
                file.createNewFile();
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
                bufferedOutputStream.write(data);
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
            } catch (IOException e2) {
                Log.e(TAG, e2.toString());
            }
        }
    }

    public static void writeRgbaToFile(Buffer buffer, int width, int height, String filePath) throws IOException {
        try {
            File file = new File(filePath);
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            bitmapCreateBitmap.copyPixelsFromBuffer(buffer);
            bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e2) {
            Log.e(TAG, e2.toString());
        }
    }

    public static byte[] yuv420toNV21(Image image) {
        int i2;
        Rect cropRect = image.getCropRect();
        int format = image.getFormat();
        int iWidth = cropRect.width();
        int iHeight = cropRect.height();
        Image.Plane[] planes = image.getPlanes();
        int i3 = iWidth * iHeight;
        byte[] bArr = new byte[(ImageFormat.getBitsPerPixel(format) * i3) / 8];
        int i4 = 0;
        byte[] bArr2 = new byte[planes[0].getRowStride()];
        int i5 = 1;
        int i6 = 0;
        int i7 = 0;
        int i8 = 1;
        while (i6 < planes.length) {
            if (i6 != 0) {
                if (i6 == i5) {
                    i7 = i3 + 1;
                } else if (i6 == 2) {
                    i7 = i3;
                }
                i8 = 2;
            } else {
                i7 = i4;
                i8 = i5;
            }
            ByteBuffer buffer = planes[i6].getBuffer();
            int rowStride = planes[i6].getRowStride();
            int pixelStride = planes[i6].getPixelStride();
            int i9 = i6 == 0 ? i4 : i5;
            int i10 = iWidth >> i9;
            int i11 = iHeight >> i9;
            int i12 = iWidth;
            int i13 = iHeight;
            buffer.position(((cropRect.top >> i9) * rowStride) + ((cropRect.left >> i9) * pixelStride));
            for (int i14 = 0; i14 < i11; i14++) {
                if (pixelStride == 1 && i8 == 1) {
                    buffer.get(bArr, i7, i10);
                    i7 += i10;
                    i2 = i10;
                } else {
                    i2 = ((i10 - 1) * pixelStride) + 1;
                    buffer.get(bArr2, 0, i2);
                    for (int i15 = 0; i15 < i10; i15++) {
                        bArr[i7] = bArr2[i15 * pixelStride];
                        i7 += i8;
                    }
                }
                if (i14 < i11 - 1) {
                    buffer.position((buffer.position() + rowStride) - i2);
                }
            }
            i6++;
            iWidth = i12;
            iHeight = i13;
            i4 = 0;
            i5 = 1;
        }
        return bArr;
    }

    public static byte[] yuv420toNV21(byte[] data, int width, int height) {
        return yuv420toNV21(JavaI420Buffer.createYUV(data, width, height), width, height);
    }

    public static byte[] yuv420toNV21(VideoFrame.I420Buffer i420, int width, int height) {
        int i2;
        int i3 = width;
        int i4 = height;
        int i5 = 0;
        Rect rect = new Rect(0, 0, i3, i4);
        int i6 = 3;
        int i7 = 1;
        int i8 = 2;
        Plane[] planeArr = {new Plane(i420.getDataY(), i420.getStrideY(), 1), new Plane(i420.getDataU(), i420.getStrideU(), 1), new Plane(i420.getDataV(), i420.getStrideV(), 1)};
        int i9 = i3 * i4;
        byte[] bArr = new byte[(ImageFormat.getBitsPerPixel(35) * i9) / 8];
        byte[] bArr2 = new byte[planeArr[0].getRowStride()];
        int i10 = 0;
        int i11 = 0;
        int i12 = 1;
        while (i10 < i6) {
            if (i10 == 0) {
                i11 = i5;
                i12 = i7;
            } else if (i10 == i7) {
                i11 = i9 + 1;
                i12 = i8;
            } else if (i10 == i8) {
                i12 = i8;
                i11 = i9;
            }
            ByteBuffer buffer = planeArr[i10].getBuffer();
            int rowStride = planeArr[i10].getRowStride();
            int pixelStride = planeArr[i10].getPixelStride();
            int i13 = i10 == 0 ? i5 : i7;
            int i14 = i3 >> i13;
            int i15 = i4 >> i13;
            buffer.position(((rect.top >> i13) * rowStride) + ((rect.left >> i13) * pixelStride));
            for (int i16 = 0; i16 < i15; i16++) {
                if (pixelStride == 1 && i12 == 1) {
                    buffer.get(bArr, i11, i14);
                    i11 += i14;
                    i2 = i14;
                } else {
                    i2 = ((i14 - 1) * pixelStride) + 1;
                    buffer.get(bArr2, 0, i2);
                    for (int i17 = 0; i17 < i14; i17++) {
                        bArr[i11] = bArr2[i17 * pixelStride];
                        i11 += i12;
                    }
                }
                if (i16 < i15 - 1) {
                    buffer.position((buffer.position() + rowStride) - i2);
                }
            }
            i10++;
            i3 = width;
            i4 = height;
            i5 = 0;
            i6 = 3;
            i8 = 2;
            i7 = 1;
        }
        return bArr;
    }
}
