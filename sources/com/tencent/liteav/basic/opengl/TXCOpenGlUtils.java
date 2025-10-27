package com.tencent.liteav.basic.opengl;

import android.graphics.Bitmap;
import android.opengl.EGL14;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.opengl.e;
import com.tencent.liteav.basic.util.TXCBuild;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes6.dex */
public class TXCOpenGlUtils {

    /* renamed from: a, reason: collision with root package name */
    public static FloatBuffer f18481a;

    /* renamed from: f, reason: collision with root package name */
    private static float[] f18486f;

    /* renamed from: g, reason: collision with root package name */
    private static float[] f18487g = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};

    /* renamed from: h, reason: collision with root package name */
    private static float[] f18488h = {0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 0.0f};

    /* renamed from: i, reason: collision with root package name */
    private static float[] f18489i = {1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};

    /* renamed from: j, reason: collision with root package name */
    private static float[] f18490j = {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, 0.0f, 1.0f};

    /* renamed from: b, reason: collision with root package name */
    public static FloatBuffer f18482b = a(f18487g);

    /* renamed from: c, reason: collision with root package name */
    public static FloatBuffer f18483c = a(f18488h);

    /* renamed from: d, reason: collision with root package name */
    public static FloatBuffer f18484d = a(f18489i);

    /* renamed from: e, reason: collision with root package name */
    public static FloatBuffer f18485e = a(f18490j);

    /* renamed from: k, reason: collision with root package name */
    private static int f18491k = 2;

    public interface a {
    }

    static {
        float[] fArr = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
        f18486f = fArr;
        f18481a = a(fArr);
    }

    public static void a(int i2) {
        f18491k = i2;
    }

    public static int b() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameterf(36197, R2.drawable.ic_home_index_normal_day, 9729.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, R2.drawable.ic_home_index_normal_night, 33071);
        GLES20.glTexParameteri(36197, R2.drawable.ic_home_index_select_day, 33071);
        return iArr[0];
    }

    public static int c() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, iArr[0]);
        GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, 9729.0f);
        GLES20.glTexParameterf(R2.attr.tab_indicator_height, 10240, 9729.0f);
        GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071);
        GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
        return iArr[0];
    }

    public static int d() {
        int[] iArr = new int[1];
        GLES20.glGenFramebuffers(1, iArr, 0);
        return iArr[0];
    }

    public static Object e() {
        return TXCBuild.VersionInt() >= 17 ? EGL14.eglGetCurrentContext() : ((EGL10) EGLContext.getEGL()).eglGetCurrentContext();
    }

    public static native void nativeCopyDataFromByteBufferToByteArray(ByteBuffer byteBuffer, byte[] bArr, int i2);

    public static native void nativeCopyDataFromByteBufferToByteBuffer(ByteBuffer byteBuffer, ByteBuffer byteBuffer2, int i2);

    private static native void nativeLoadYuv420ByteArrayToTextures(byte[] bArr, int i2, int i3, int i4, int[] iArr);

    private static native void nativeLoadYuv420ByteBufferToTextures(ByteBuffer byteBuffer, int i2, int i3, int i4, int[] iArr);

    public static final int a() {
        return f18491k;
    }

    public static void d(int i2) {
        GLES20.glBindFramebuffer(36160, i2);
        GLES20.glFramebufferTexture2D(36160, 36064, R2.attr.tab_indicator_height, 0, 0);
        GLES20.glBindFramebuffer(36160, 0);
    }

    public static int a(int i2, int i3, int i4, int i5, int[] iArr) {
        GLES20.glGenTextures(1, iArr, 0);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, iArr[0]);
        GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071);
        GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071);
        GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, R2.drawable.fff2f2f2_25);
        GLES20.glTexParameteri(R2.attr.tab_indicator_height, 10240, R2.drawable.fff4f2ee_12);
        GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, i4, i2, i3, 0, i5, R2.color.m3_ref_palette_dynamic_tertiary100, null);
        return iArr[0];
    }

    public static void b(int i2) {
        if (i2 != -1) {
            GLES20.glDeleteFramebuffers(1, new int[]{i2}, 0);
        }
    }

    public static void c(int i2) {
        if (i2 != -1) {
            GLES20.glDeleteTextures(1, new int[]{i2}, 0);
        }
    }

    public static FloatBuffer a(float[] fArr) {
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer = byteBufferAllocateDirect.asFloatBuffer();
        floatBufferAsFloatBuffer.put(fArr);
        floatBufferAsFloatBuffer.position(0);
        return floatBufferAsFloatBuffer;
    }

    public static int a(int i2, int i3, int i4, int i5, IntBuffer intBuffer) {
        int iC = c();
        GLES20.glBindTexture(R2.attr.tab_indicator_height, iC);
        GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, i4, i2, i3, 0, i5, R2.color.m3_ref_palette_dynamic_tertiary100, intBuffer);
        GLES20.glBindTexture(R2.attr.tab_indicator_height, 0);
        return iC;
    }

    public static int a(int i2, int i3, int i4, int i5) {
        return a(i2, i3, i4, i5, (IntBuffer) null);
    }

    public static int a(Bitmap bitmap, int i2, boolean z2) {
        try {
            int[] iArr = new int[1];
            if (i2 == -1) {
                GLES20.glGenTextures(1, iArr, 0);
                GLES20.glBindTexture(R2.attr.tab_indicator_height, iArr[0]);
                GLES20.glTexParameterf(R2.attr.tab_indicator_height, 10240, 9729.0f);
                GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, 9729.0f);
                GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071.0f);
                GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071.0f);
                if (bitmap != null && !bitmap.isRecycled()) {
                    GLUtils.texImage2D(R2.attr.tab_indicator_height, 0, bitmap, 0);
                }
            } else {
                GLES20.glBindTexture(R2.attr.tab_indicator_height, i2);
                if (bitmap != null && !bitmap.isRecycled()) {
                    GLUtils.texSubImage2D(R2.attr.tab_indicator_height, 0, 0, 0, bitmap);
                }
                iArr[0] = i2;
            }
            if (z2) {
                bitmap.recycle();
            }
            return iArr[0];
        } catch (IllegalArgumentException e2) {
            TXCLog.e("TXCOpenGlUtils", "loadTexture error " + e2);
            return -1;
        }
    }

    public static int a(ByteBuffer byteBuffer, int i2, int i3, int i4) {
        int[] iArr = new int[1];
        if (i4 == -1) {
            GLES20.glGenTextures(1, iArr, 0);
            GLES20.glBindTexture(R2.attr.tab_indicator_height, iArr[0]);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, 10240, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, 9729.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071.0f);
            GLES20.glTexParameterf(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071.0f);
            GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, R2.dimen.dm_200, i2, i3, 0, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, byteBuffer);
        } else {
            GLES20.glBindTexture(R2.attr.tab_indicator_height, i4);
            GLES20.glTexSubImage2D(R2.attr.tab_indicator_height, 0, 0, 0, i2, i3, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, byteBuffer);
            iArr[0] = i4;
        }
        return iArr[0];
    }

    public static int a(int i2, int i3, int[] iArr) {
        GLES20.glGenBuffers(1, iArr, 0);
        GLES20.glBindBuffer(35051, iArr[0]);
        GLES20.glBufferData(35051, i2 * i3 * 4, null, 35049);
        GLES20.glBindBuffer(35051, 0);
        return iArr[0];
    }

    public static int a(String str, int i2) {
        int[] iArr = new int[1];
        int iGlCreateShader = GLES20.glCreateShader(i2);
        GLES20.glShaderSource(iGlCreateShader, str);
        GLES20.glCompileShader(iGlCreateShader);
        GLES20.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return iGlCreateShader;
        }
        TXCLog.w("Load Shader Failed", "Compilation\n" + GLES20.glGetShaderInfoLog(iGlCreateShader));
        return 0;
    }

    public static int a(String str, String str2) {
        int[] iArr = new int[1];
        int iA = a(str, 35633);
        if (iA == 0) {
            TXCLog.w("Load Program", "Vertex Shader Failed");
            return 0;
        }
        int iA2 = a(str2, 35632);
        if (iA2 == 0) {
            TXCLog.w("Load Program", "Fragment Shader Failed");
            return 0;
        }
        int iGlCreateProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(iGlCreateProgram, iA);
        GLES20.glAttachShader(iGlCreateProgram, iA2);
        GLES20.glLinkProgram(iGlCreateProgram);
        GLES20.glGetProgramiv(iGlCreateProgram, 35714, iArr, 0);
        if (iArr[0] <= 0) {
            TXCLog.w("Load Program", "Linking Failed");
            return 0;
        }
        GLES20.glDeleteShader(iA);
        GLES20.glDeleteShader(iA2);
        return iGlCreateProgram;
    }

    public static void a(String str) {
        int iGlGetError = GLES20.glGetError();
        if (iGlGetError != 0) {
            TXCLog.e("OpenGlUtils", str + ": glError 0x" + Integer.toHexString(iGlGetError));
        }
    }

    public static void a(int i2, int i3) {
        GLES20.glBindFramebuffer(36160, i3);
        GLES20.glFramebufferTexture2D(36160, 36064, R2.attr.tab_indicator_height, i2, 0);
        GLES20.glBindFramebuffer(36160, 0);
    }

    public static void a(ByteBuffer byteBuffer, int i2, int i3, int i4, int[] iArr) {
        if (byteBuffer.isDirect()) {
            nativeLoadYuv420ByteBufferToTextures(byteBuffer, i2, i3, i4, iArr);
        } else {
            nativeLoadYuv420ByteArrayToTextures(byteBuffer.array(), i2, i3, i4, iArr);
        }
    }

    public static void a(e.a aVar, int i2, int i3, Object obj) {
        if (aVar == e.a.RGBA) {
            a(0, 0, i2, i3, obj);
            return;
        }
        if (i3 % 8 == 0) {
            a(0, 0, i2, (i3 * 3) / 8, obj);
            return;
        }
        int i4 = ((i3 * 3) + 7) / 8;
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(i2 * i4 * 4);
        a(0, 0, i2, i4, byteBufferAllocateDirect);
        if (obj instanceof ByteBuffer) {
            nativeCopyDataFromByteBufferToByteBuffer(byteBufferAllocateDirect, (ByteBuffer) obj, ((i2 * i3) * 3) / 2);
        } else {
            nativeCopyDataFromByteBufferToByteArray(byteBufferAllocateDirect, (byte[]) obj, ((i2 * i3) * 3) / 2);
        }
    }

    private static void a(int i2, int i3, int i4, int i5, Object obj) {
        GLES20.glFinish();
        if (obj instanceof ByteBuffer) {
            ByteBuffer byteBuffer = (ByteBuffer) obj;
            byteBuffer.position(0);
            GLES20.glReadPixels(i2, i3, i4, i5, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, byteBuffer);
            return;
        }
        GLES20.glReadPixels(i2, i3, i4, i5, R2.dimen.dm_200, R2.color.m3_ref_palette_dynamic_tertiary100, ByteBuffer.wrap((byte[]) obj));
    }
}
