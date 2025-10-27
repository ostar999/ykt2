package a.a.a.a.f;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;
import com.yikaobang.yixue.R2;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/* loaded from: classes.dex */
public abstract class e {

    /* renamed from: a, reason: collision with root package name */
    public static final String f1162a = "mqi";

    /* renamed from: b, reason: collision with root package name */
    public static final float[] f1163b;

    /* renamed from: c, reason: collision with root package name */
    private static final int f1164c = 4;

    static {
        float[] fArr = new float[16];
        f1163b = fArr;
        Matrix.setIdentityM(fArr, 0);
    }

    private e() {
    }

    public static int a(String str, String str2) {
        int iB;
        int iB2 = b(35633, str);
        if (iB2 == 0 || (iB = b(35632, str2)) == 0) {
            return 0;
        }
        int iGlCreateProgram = GLES20.glCreateProgram();
        a("glCreateProgram");
        if (iGlCreateProgram == 0) {
            Log.e(f1162a, "Could not create program");
        }
        GLES20.glAttachShader(iGlCreateProgram, iB2);
        a("glAttachShader");
        GLES20.glAttachShader(iGlCreateProgram, iB);
        a("glAttachShader");
        GLES20.glLinkProgram(iGlCreateProgram);
        int[] iArr = new int[1];
        GLES20.glGetProgramiv(iGlCreateProgram, 35714, iArr, 0);
        if (iArr[0] == 1) {
            return iGlCreateProgram;
        }
        Log.e(f1162a, "Could not link program: ");
        Log.e(f1162a, GLES20.glGetProgramInfoLog(iGlCreateProgram));
        GLES20.glDeleteProgram(iGlCreateProgram);
        return 0;
    }

    public static int b(int i2, String str) {
        int iGlCreateShader = GLES20.glCreateShader(i2);
        a("glCreateShader type=" + i2);
        GLES20.glShaderSource(iGlCreateShader, str);
        GLES20.glCompileShader(iGlCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return iGlCreateShader;
        }
        Log.e(f1162a, "Could not compile shader " + i2 + ":");
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        sb.append(GLES20.glGetShaderInfoLog(iGlCreateShader));
        Log.e(f1162a, sb.toString());
        GLES20.glDeleteShader(iGlCreateShader);
        return 0;
    }

    public static void b(int i2) {
        GLES20.glDeleteTextures(1, new int[]{i2}, 0);
        a("glDeleteTextures");
    }

    public static void a(String str) {
        int iGlGetError = GLES20.glGetError();
        if (iGlGetError != 0) {
            Log.e(f1162a, str + ": glError 0x" + Integer.toHexString(iGlGetError));
        }
    }

    public static void a(int i2, String str) {
        if (i2 < 0) {
            Log.e(f1162a, "Unable to locate '" + str + "' in program");
        }
    }

    public static int a(ByteBuffer byteBuffer, int i2, int i3, int i4) {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        int i5 = iArr[0];
        a("glGenTextures");
        GLES20.glBindTexture(R2.attr.tab_indicator_height, i5);
        GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, R2.drawable.fff4f2ee_12);
        GLES20.glTexParameteri(R2.attr.tab_indicator_height, 10240, R2.drawable.fff4f2ee_12);
        a("loadImageTexture");
        GLES20.glTexImage2D(R2.attr.tab_indicator_height, 0, i4, i2, i3, 0, i4, R2.color.m3_ref_palette_dynamic_tertiary100, byteBuffer);
        a("loadImageTexture");
        return i5;
    }

    public static int a(Bitmap bitmap) {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        int i2 = iArr[0];
        a("glGenTextures");
        GLES20.glBindTexture(R2.attr.tab_indicator_height, i2);
        GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, R2.drawable.fff4f2ee_12);
        GLES20.glTexParameteri(R2.attr.tab_indicator_height, 10240, R2.drawable.fff4f2ee_12);
        a("loadImageTexture");
        GLUtils.texImage2D(R2.attr.tab_indicator_height, 0, bitmap, 0);
        a("loadImageTexture");
        return i2;
    }

    public static FloatBuffer a(float[] fArr) {
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer = byteBufferAllocateDirect.asFloatBuffer();
        floatBufferAsFloatBuffer.put(fArr);
        floatBufferAsFloatBuffer.position(0);
        return floatBufferAsFloatBuffer;
    }

    public static void a() {
        Log.i(f1162a, "vendor  : " + GLES20.glGetString(R2.dimen.material_cursor_width));
        Log.i(f1162a, "renderer: " + GLES20.glGetString(R2.dimen.material_divider_thickness));
        Log.i(f1162a, "version : " + GLES20.glGetString(R2.dimen.material_emphasis_disabled));
    }

    public static int a(int i2) {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        a("glGenTextures");
        int i3 = iArr[0];
        GLES20.glBindTexture(i2, i3);
        a("glBindTexture " + i3);
        GLES20.glTexParameterf(36197, R2.drawable.ic_home_index_normal_day, 9728.0f);
        GLES20.glTexParameterf(36197, 10240, 9729.0f);
        GLES20.glTexParameteri(36197, R2.drawable.ic_home_index_normal_night, 33071);
        GLES20.glTexParameteri(36197, R2.drawable.ic_home_index_select_day, 33071);
        a("glTexParameter");
        return i3;
    }

    public static float[] a(float[] fArr, float f2, float f3, float f4, float f5) {
        float f6 = ((f2 * f5) / f3) / f4;
        if (f6 == 1.0f) {
            return fArr;
        }
        float[] fArr2 = new float[16];
        float[] fArr3 = new float[16];
        Matrix.setIdentityM(fArr3, 0);
        float f7 = f6 > 1.0f ? 1.0f : 1.0f / f6;
        if (f6 <= 1.0f) {
            f6 = 1.0f;
        }
        Matrix.scaleM(fArr3, 0, f7, f6, 1.0f);
        Matrix.multiplyMM(fArr2, 0, fArr3, 0, fArr, 0);
        return fArr2;
    }
}
