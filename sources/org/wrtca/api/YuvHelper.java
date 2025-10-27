package org.wrtca.api;

import java.nio.ByteBuffer;
import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class YuvHelper {
    public static int ArgbToI420(int i2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i3, int i4) {
        return nativeArgbToI420(i2, byteBuffer, byteBuffer2, i3, i4);
    }

    public static void I420Copy(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3, ByteBuffer byteBuffer3, int i4, ByteBuffer byteBuffer4, int i5, int i6) {
        int i7 = (i5 + 1) / 2;
        int i8 = i5 * i6;
        int i9 = ((i6 + 1) / 2) * i7;
        int i10 = (i9 * 2) + i8;
        if (byteBuffer4.capacity() < i10) {
            throw new IllegalArgumentException("Expected destination buffer capacity to be at least " + i10 + " was " + byteBuffer4.capacity());
        }
        byteBuffer4.position(0);
        ByteBuffer byteBufferSlice = byteBuffer4.slice();
        byteBuffer4.position(i8);
        ByteBuffer byteBufferSlice2 = byteBuffer4.slice();
        byteBuffer4.position(i9 + i8);
        nativeI420Copy(byteBuffer, i2, byteBuffer2, i3, byteBuffer3, i4, byteBufferSlice, i5, byteBufferSlice2, i7, byteBuffer4.slice(), i7, i5, i6);
    }

    public static void I420Rotate(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3, ByteBuffer byteBuffer3, int i4, ByteBuffer byteBuffer4, int i5, int i6, int i7) {
        int i8 = i7 % 180;
        int i9 = i8 == 0 ? i5 : i6;
        int i10 = i8 == 0 ? i6 : i5;
        int i11 = (i10 + 1) / 2;
        int i12 = (i9 + 1) / 2;
        int i13 = i10 * i9;
        int i14 = i11 * i12;
        int i15 = (i14 * 2) + i13;
        if (byteBuffer4.capacity() < i15) {
            throw new IllegalArgumentException("Expected destination buffer capacity to be at least " + i15 + " was " + byteBuffer4.capacity());
        }
        byteBuffer4.position(0);
        ByteBuffer byteBufferSlice = byteBuffer4.slice();
        byteBuffer4.position(i13);
        ByteBuffer byteBufferSlice2 = byteBuffer4.slice();
        byteBuffer4.position(i14 + i13);
        nativeI420Rotate(byteBuffer, i2, byteBuffer2, i3, byteBuffer3, i4, byteBufferSlice, i9, byteBufferSlice2, i12, byteBuffer4.slice(), i12, i5, i6, i7);
    }

    public static void I420ToNV12(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3, ByteBuffer byteBuffer3, int i4, ByteBuffer byteBuffer4, int i5, int i6) {
        int i7 = (i5 + 1) / 2;
        int i8 = i5 * i6;
        int i9 = (((i6 + 1) / 2) * i7 * 2) + i8;
        if (byteBuffer4.capacity() >= i9) {
            byteBuffer4.position(0);
            ByteBuffer byteBufferSlice = byteBuffer4.slice();
            byteBuffer4.position(i8);
            nativeI420ToNV12(byteBuffer, i2, byteBuffer2, i3, byteBuffer3, i4, byteBufferSlice, i5, byteBuffer4.slice(), i7 * 2, i5, i6);
            return;
        }
        throw new IllegalArgumentException("Expected destination buffer capacity to be at least " + i9 + " was " + byteBuffer4.capacity());
    }

    public static int I420ToRgba(int i2, int i3, int i4, ByteBuffer byteBuffer, int i5, ByteBuffer byteBuffer2, int i6, ByteBuffer byteBuffer3, int i7, ByteBuffer byteBuffer4) {
        return nativeI420ToRGBA(i2, i3, i4, byteBuffer, i5, byteBuffer2, i6, byteBuffer3, i7, byteBuffer4);
    }

    public static int NV12ToI420(int i2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i3, int i4) {
        return nativeNV12ToI420(i2, byteBuffer, byteBuffer2, i3, i4);
    }

    public static int NV21ToI420(int i2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i3, int i4) {
        return nativeNV21ToI420(i2, byteBuffer, byteBuffer2, i3, i4);
    }

    public static int Rgb24ToI420(int i2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i3, int i4) {
        return nativeRgb24ToI420(i2, byteBuffer, byteBuffer2, i3, i4);
    }

    public static int Rgb565ToI420(int i2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i3, int i4) {
        return nativeRgb565ToI420(i2, byteBuffer, byteBuffer2, i3, i4);
    }

    public static int RgbaToI420(int i2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i3, int i4) {
        return nativeRgbaToI420(i2, byteBuffer, byteBuffer2, i3, i4);
    }

    public static native int nativeArgbToI420(int i2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i3, int i4);

    private static native void nativeI420Copy(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3, ByteBuffer byteBuffer3, int i4, ByteBuffer byteBuffer4, int i5, ByteBuffer byteBuffer5, int i6, ByteBuffer byteBuffer6, int i7, int i8, int i9);

    private static native void nativeI420Rotate(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3, ByteBuffer byteBuffer3, int i4, ByteBuffer byteBuffer4, int i5, ByteBuffer byteBuffer5, int i6, ByteBuffer byteBuffer6, int i7, int i8, int i9, int i10);

    private static native void nativeI420ToNV12(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3, ByteBuffer byteBuffer3, int i4, ByteBuffer byteBuffer4, int i5, ByteBuffer byteBuffer5, int i6, int i7, int i8);

    public static native int nativeI420ToRGBA(int i2, int i3, int i4, ByteBuffer byteBuffer, int i5, ByteBuffer byteBuffer2, int i6, ByteBuffer byteBuffer3, int i7, ByteBuffer byteBuffer4);

    public static native int nativeNV12ToI420(int i2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i3, int i4);

    public static native int nativeNV21ToI420(int i2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i3, int i4);

    public static native int nativeRgb24ToI420(int i2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i3, int i4);

    public static native int nativeRgb565ToI420(int i2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i3, int i4);

    public static native int nativeRgbaToI420(int i2, ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i3, int i4);

    public static void I420ToNV12(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3, ByteBuffer byteBuffer3, int i4, ByteBuffer byteBuffer4, int i5, ByteBuffer byteBuffer5, int i6, int i7, int i8) {
        nativeI420ToNV12(byteBuffer, i2, byteBuffer2, i3, byteBuffer3, i4, byteBuffer4, i5, byteBuffer5, i6, i7, i8);
    }

    public static void I420Copy(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3, ByteBuffer byteBuffer3, int i4, ByteBuffer byteBuffer4, int i5, ByteBuffer byteBuffer5, int i6, ByteBuffer byteBuffer6, int i7, int i8, int i9) {
        nativeI420Copy(byteBuffer, i2, byteBuffer2, i3, byteBuffer3, i4, byteBuffer4, i5, byteBuffer5, i6, byteBuffer6, i7, i8, i9);
    }

    public static void I420Rotate(ByteBuffer byteBuffer, int i2, ByteBuffer byteBuffer2, int i3, ByteBuffer byteBuffer3, int i4, ByteBuffer byteBuffer4, int i5, ByteBuffer byteBuffer5, int i6, ByteBuffer byteBuffer6, int i7, int i8, int i9, int i10) {
        nativeI420Rotate(byteBuffer, i2, byteBuffer2, i3, byteBuffer3, i4, byteBuffer4, i5, byteBuffer5, i6, byteBuffer6, i7, i8, i9, i10);
    }
}
