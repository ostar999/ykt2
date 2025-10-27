package com.tencent.liteav.beauty;

import android.util.Log;
import com.tencent.liteav.basic.util.h;
import java.nio.ByteBuffer;

/* loaded from: classes6.dex */
public class NativeLoad {
    private static final String TAG = "NativeLoad";

    static {
        h.d();
        OnLoadBeauty();
        Log.i(TAG, "NativeLoad: load jni");
    }

    private NativeLoad() {
    }

    public static native void OnLoadBeauty();

    public static native void nativeClearQueue();

    public static native void nativeDeleteYuv2Yuv();

    public static native void nativeGlMapBufferToQueue(int i2, int i3, ByteBuffer byteBuffer);

    public static native void nativeGlReadPixs(int i2, int i3, byte[] bArr);

    public static native boolean nativeGlReadPixsFromQueue(int i2, int i3, byte[] bArr);

    public static native void nativeGlReadPixsToQueue(int i2, int i3);

    public static native int nativeLoadGLProgram(int i2);

    public static native void nativeglTexImage2D(int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9, byte[] bArr, int i10);
}
