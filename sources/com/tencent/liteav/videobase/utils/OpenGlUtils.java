package com.tencent.liteav.videobase.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.e;
import com.tencent.liteav.videobase.a.a;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes6.dex */
public class OpenGlUtils {
    private static final String TAG = "OpenGlUtils";

    /* renamed from: com.tencent.liteav.videobase.utils.OpenGlUtils$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$tencent$liteav$videobase$utils$Rotation;

        static {
            int[] iArr = new int[d.values().length];
            $SwitchMap$com$tencent$liteav$videobase$utils$Rotation = iArr;
            try {
                iArr[d.ROTATION_90.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$tencent$liteav$videobase$utils$Rotation[d.ROTATION_180.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$tencent$liteav$videobase$utils$Rotation[d.ROTATION_270.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$tencent$liteav$videobase$utils$Rotation[d.NORMAL.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static void attachTextureToFrameBuffer(int i2, int i3) {
        GLES20.glBindFramebuffer(36160, i3);
        GLES20.glFramebufferTexture2D(36160, 36064, R2.attr.tab_indicator_height, i2, 0);
        GLES20.glBindFramebuffer(36160, 0);
    }

    public static void bindFramebuffer(int i2, int i3) {
        GLES20.glBindFramebuffer(i2, i3);
        checkGlError("bindFramebuffer(" + i3 + ")");
    }

    public static void bindTexture(int i2, int i3) {
        GLES20.glBindTexture(i2, i3);
        checkGlError("bindTexture(" + i3 + ")");
    }

    public static void checkGlError(String str) {
    }

    public static void convertYuvFormat(a.c cVar, Object obj, a.c cVar2, Object obj2, int i2, int i3) {
        int iA = cVar.a();
        int iA2 = cVar2.a();
        boolean z2 = obj instanceof ByteBuffer;
        if ((z2 && (obj2 instanceof ByteBuffer)) ? convertYuvFormatBufferToBuffer(iA, (ByteBuffer) obj, iA2, (ByteBuffer) obj2, i2, i3) : (z2 && (obj2 instanceof byte[])) ? convertYuvFormatBufferToArray(iA, (ByteBuffer) obj, iA2, (byte[]) obj2, i2, i3) : ((obj instanceof byte[]) && (obj2 instanceof ByteBuffer)) ? convertYuvFormatArrayToBuffer(iA, (byte[]) obj, iA2, (ByteBuffer) obj2, i2, i3) : convertYuvFormatArrayToArray(iA, (byte[]) obj, iA2, (byte[]) obj2, i2, i3)) {
            return;
        }
        throw new IllegalArgumentException("Do not support " + cVar + " to " + cVar2);
    }

    private static native boolean convertYuvFormatArrayToArray(int i2, byte[] bArr, int i3, byte[] bArr2, int i4, int i5);

    private static native boolean convertYuvFormatArrayToBuffer(int i2, byte[] bArr, int i3, ByteBuffer byteBuffer, int i4, int i5);

    private static native boolean convertYuvFormatBufferToArray(int i2, ByteBuffer byteBuffer, int i3, byte[] bArr, int i4, int i5);

    private static native boolean convertYuvFormatBufferToBuffer(int i2, ByteBuffer byteBuffer, int i3, ByteBuffer byteBuffer2, int i4, int i5);

    public static native void copyDataFromByteArrayToByteBuffer(byte[] bArr, ByteBuffer byteBuffer, int i2);

    public static native void copyDataFromByteBufferToByteArray(ByteBuffer byteBuffer, byte[] bArr, int i2);

    public static native void copyDataFromByteBufferToByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i2);

    public static FloatBuffer createNormalCubeVerticesBuffer() {
        float[] fArr = com.tencent.liteav.videobase.a.a.f19933c;
        return (FloatBuffer) ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer().put(fArr).position(0);
    }

    public static int createTexture(int i2, int i3, int i4, int i5) {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        TXCLog.d(TAG, "glGenTextures textureId: " + iArr[0]);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, iArr[0]);
        GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, 9729.0f);
        GLES20.glTexParameterf(R2.attr.tab_indicator_height, 10240, 9729.0f);
        GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071);
        GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071);
        GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, i4, i2, i3, 0, i5, R2.color.m3_ref_palette_dynamic_tertiary100, null);
        return iArr[0];
    }

    public static FloatBuffer createTextureCoordsBuffer(d dVar, boolean z2, boolean z3) {
        float[] fArr = com.tencent.liteav.videobase.a.a.f19934d;
        float[] fArr2 = new float[fArr.length];
        initTextureCoordsBuffer(fArr2, dVar, z2, z3);
        FloatBuffer floatBufferAsFloatBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        floatBufferAsFloatBuffer.put(fArr2).position(0);
        return floatBufferAsFloatBuffer;
    }

    public static void deleteFrameBuffer(int i2) {
        if (i2 != -1) {
            GLES20.glDeleteFramebuffers(1, new int[]{i2}, 0);
            TXCLog.d(TAG, "delete frame buffer id: " + i2);
        }
    }

    public static void deleteShaderId(int i2) {
        if (i2 != -1) {
            GLES20.glDeleteShader(i2);
        }
    }

    public static void deleteTexture(int i2) {
        if (i2 != -1) {
            GLES20.glDeleteTextures(1, new int[]{i2}, 0);
            TXCLog.d(TAG, "delete textureId " + i2);
        }
    }

    public static void detachTextureFromFrameBuffer(int i2) {
        GLES20.glBindFramebuffer(36160, i2);
        GLES20.glFramebufferTexture2D(36160, 36064, R2.attr.tab_indicator_height, 0, 0);
        GLES20.glBindFramebuffer(36160, 0);
    }

    private static float flip(float f2) {
        return f2 == 0.0f ? 1.0f : 0.0f;
    }

    public static int generateFrameBufferId() {
        int[] iArr = new int[1];
        GLES20.glGenFramebuffers(1, iArr, 0);
        return iArr[0];
    }

    public static int generateTextureOES() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameterf(36197, R2.drawable.ic_home_index_normal_day, 9729.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, R2.drawable.ic_home_index_normal_night, 33071);
        GLES20.glTexParameteri(36197, R2.drawable.ic_home_index_select_day, 33071);
        return iArr[0];
    }

    public static Object getCurrentContext() {
        return EGL14.eglGetCurrentContext();
    }

    public static void glViewport(int i2, int i3, int i4, int i5) {
        GLES20.glViewport(i2, i3, i4, i5);
    }

    public static void initTextureCoordsBuffer(float[] fArr, d dVar, boolean z2, boolean z3) {
        int i2 = AnonymousClass1.$SwitchMap$com$tencent$liteav$videobase$utils$Rotation[dVar.ordinal()];
        float[] fArr2 = i2 != 1 ? i2 != 2 ? i2 != 3 ? com.tencent.liteav.videobase.a.a.f19934d : com.tencent.liteav.videobase.a.a.f19935e : com.tencent.liteav.videobase.a.a.f19937g : com.tencent.liteav.videobase.a.a.f19936f;
        System.arraycopy(fArr2, 0, fArr, 0, fArr2.length);
        if (z2) {
            fArr[0] = flip(fArr[0]);
            fArr[2] = flip(fArr[2]);
            fArr[4] = flip(fArr[4]);
            fArr[6] = flip(fArr[6]);
        }
        if (z3) {
            fArr[1] = flip(fArr[1]);
            fArr[3] = flip(fArr[3]);
            fArr[5] = flip(fArr[5]);
            fArr[7] = flip(fArr[7]);
        }
    }

    public static int loadTexture(Bitmap bitmap, int i2, boolean z2) {
        int[] iArr = new int[1];
        if (i2 == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            TXCLog.d(TAG, "glGenTextures textureId: " + iArr[0]);
            bindTexture(R2.attr.tab_indicator_height, iArr[0]);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, 10240, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071.0f);
            GLUtils.texImage2D(R2.attr.tab_indicator_height, 0, bitmap, 0);
        } else {
            bindTexture(R2.attr.tab_indicator_height, i2);
            GLUtils.texSubImage2D(R2.attr.tab_indicator_height, 0, 0, 0, bitmap);
            iArr[0] = i2;
        }
        if (z2) {
            bitmap.recycle();
        }
        return iArr[0];
    }

    private static native void loadYuv420ByteArrayToTextures(byte[] bArr, int i2, int i3, int i4, int[] iArr);

    private static native void loadYuv420ByteBufferToTextures(ByteBuffer byteBuffer, int i2, int i3, int i4, int[] iArr);

    public static void loadYuv420DataToTextures(ByteBuffer byteBuffer, int i2, int i3, int i4, int[] iArr) {
        if (byteBuffer.isDirect()) {
            loadYuv420ByteBufferToTextures(byteBuffer, i2, i3, i4, iArr);
        } else {
            loadYuv420ByteArrayToTextures(byteBuffer.array(), i2, i3, i4, iArr);
        }
    }

    public static void readPixels(int i2, int i3, int i4, int i5, Object obj) {
        GLES20.glFinish();
        if (obj instanceof Buffer) {
            Buffer buffer = (Buffer) obj;
            buffer.position(0);
            GLES20.glReadPixels(i2, i3, i4, i5, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, buffer);
        } else {
            if (!(obj instanceof byte[])) {
                TXCLog.e(TAG, "read pixels failed due to unsupport object. " + obj);
                return;
            }
            GLES20.glReadPixels(i2, i3, i4, i5, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, ByteBuffer.wrap((byte[]) obj));
        }
        checkGlError("glReadPixels");
    }

    public static Point reverseMappingPoint(a.EnumC0340a enumC0340a, d dVar, Point point, e eVar, e eVar2) {
        float f2 = (eVar2.f18712a * 1.0f) / eVar.f18712a;
        float f3 = (eVar2.f18713b * 1.0f) / eVar.f18713b;
        Matrix matrix = new Matrix();
        matrix.setTranslate((-eVar.f18712a) / 2.0f, (-eVar.f18713b) / 2.0f);
        if (enumC0340a == a.EnumC0340a.CENTER_CROP) {
            float fMin = Math.min(f2, f3);
            matrix.postScale(fMin, fMin);
        } else if (enumC0340a == a.EnumC0340a.FILL) {
            matrix.postScale(f2, f3);
        } else if (enumC0340a == a.EnumC0340a.FIT_CENTER) {
            float fMax = Math.max(f2, f3);
            matrix.postScale(fMax, fMax);
        }
        matrix.postRotate(360 - dVar.a());
        if (dVar == d.ROTATION_90 || dVar == d.ROTATION_270) {
            matrix.postTranslate(eVar2.f18713b / 2.0f, eVar2.f18712a / 2.0f);
        } else {
            matrix.postTranslate(eVar2.f18712a / 2.0f, eVar2.f18713b / 2.0f);
        }
        float[] fArr = new float[2];
        matrix.mapPoints(fArr, new float[]{point.x, point.y});
        return new Point((int) fArr[0], (int) fArr[1]);
    }

    public static int loadTexture(int i2, Buffer buffer, int i3, int i4, int i5) {
        int[] iArr = new int[1];
        if (i5 == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            TXCLog.d(TAG, "glGenTextures textureId: " + iArr[0]);
            bindTexture(R2.attr.tab_indicator_height, iArr[0]);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, 10240, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071.0f);
            GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, i2, i3, i4, 0, i2, R2.color.m3_ref_palette_dynamic_tertiary100, buffer);
        } else {
            bindTexture(R2.attr.tab_indicator_height, i5);
            GLES20.glTexSubImage2D(R2.attr.tab_indicator_height, 0, 0, 0, i3, i4, i2, R2.color.m3_ref_palette_dynamic_tertiary100, buffer);
            iArr[0] = i5;
        }
        return iArr[0];
    }
}
