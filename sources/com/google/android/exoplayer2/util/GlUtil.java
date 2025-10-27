package com.google.android.exoplayer2.util;

import android.content.Context;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.text.TextUtils;
import androidx.annotation.DoNotInline;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.yikaobang.yixue.R2;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/* loaded from: classes3.dex */
public final class GlUtil {
    private static final String EXTENSION_PROTECTED_CONTENT = "EGL_EXT_protected_content";
    private static final String EXTENSION_SURFACELESS_CONTEXT = "EGL_KHR_surfaceless_context";
    private static final String TAG = "GlUtil";
    public static final int TEXTURE_ID_UNSET = -1;
    public static boolean glAssertionsEnabled = false;

    @RequiresApi(17)
    public static final class Api17 {
        private Api17() {
        }

        @DoNotInline
        public static EGLContext createEglContext(EGLDisplay eGLDisplay) throws UnsupportedEglVersionException {
            EGLContext eGLContextEglCreateContext = EGL14.eglCreateContext(eGLDisplay, getEglConfig(eGLDisplay), EGL14.EGL_NO_CONTEXT, new int[]{R2.drawable.shape_light_yellow_bg, 2, R2.drawable.shape_coupon_record_bg}, 0);
            if (eGLContextEglCreateContext != null) {
                GlUtil.checkGlError();
                return eGLContextEglCreateContext;
            }
            EGL14.eglTerminate(eGLDisplay);
            throw new UnsupportedEglVersionException();
        }

        @DoNotInline
        public static EGLDisplay createEglDisplay() {
            EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
            GlUtil.checkEglException(!eGLDisplayEglGetDisplay.equals(EGL14.EGL_NO_DISPLAY), "No EGL display.");
            if (!EGL14.eglInitialize(eGLDisplayEglGetDisplay, new int[1], 0, new int[1], 0)) {
                GlUtil.throwGlException("Error in eglInitialize.");
            }
            GlUtil.checkGlError();
            return eGLDisplayEglGetDisplay;
        }

        @DoNotInline
        public static void destroyEglContext(@Nullable EGLDisplay eGLDisplay, @Nullable EGLContext eGLContext) {
            if (eGLDisplay == null) {
                return;
            }
            EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
            EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
            int iEglGetError = EGL14.eglGetError();
            boolean z2 = iEglGetError == 12288;
            StringBuilder sb = new StringBuilder(36);
            sb.append("Error releasing context: ");
            sb.append(iEglGetError);
            GlUtil.checkEglException(z2, sb.toString());
            if (eGLContext != null) {
                EGL14.eglDestroyContext(eGLDisplay, eGLContext);
                int iEglGetError2 = EGL14.eglGetError();
                boolean z3 = iEglGetError2 == 12288;
                StringBuilder sb2 = new StringBuilder(37);
                sb2.append("Error destroying context: ");
                sb2.append(iEglGetError2);
                GlUtil.checkEglException(z3, sb2.toString());
            }
            EGL14.eglReleaseThread();
            int iEglGetError3 = EGL14.eglGetError();
            boolean z4 = iEglGetError3 == 12288;
            StringBuilder sb3 = new StringBuilder(35);
            sb3.append("Error releasing thread: ");
            sb3.append(iEglGetError3);
            GlUtil.checkEglException(z4, sb3.toString());
            EGL14.eglTerminate(eGLDisplay);
            int iEglGetError4 = EGL14.eglGetError();
            boolean z5 = iEglGetError4 == 12288;
            StringBuilder sb4 = new StringBuilder(38);
            sb4.append("Error terminating display: ");
            sb4.append(iEglGetError4);
            GlUtil.checkEglException(z5, sb4.toString());
        }

        @DoNotInline
        public static void focusSurface(EGLDisplay eGLDisplay, EGLContext eGLContext, EGLSurface eGLSurface, int i2, int i3) {
            int[] iArr = new int[1];
            GLES20.glGetIntegerv(36006, iArr, 0);
            if (iArr[0] != 0) {
                GLES20.glBindFramebuffer(36160, 0);
            }
            EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, eGLContext);
            GLES20.glViewport(0, 0, i2, i3);
        }

        @DoNotInline
        private static EGLConfig getEglConfig(EGLDisplay eGLDisplay) {
            EGLConfig[] eGLConfigArr = new EGLConfig[1];
            if (!EGL14.eglChooseConfig(eGLDisplay, new int[]{R2.drawable.shape_course_tags_bg, 4, R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_color22_corner12, 8, R2.drawable.shape_color2a_corner12, 0, R2.drawable.shape_color2a_normal, 0, R2.drawable.shape_coupon_record_bg}, 0, eGLConfigArr, 0, 1, new int[1], 0)) {
                GlUtil.throwGlException("eglChooseConfig failed.");
            }
            return eGLConfigArr[0];
        }

        @DoNotInline
        public static EGLSurface getEglSurface(EGLDisplay eGLDisplay, Object obj) {
            return EGL14.eglCreateWindowSurface(eGLDisplay, getEglConfig(eGLDisplay), obj, new int[]{R2.drawable.shape_coupon_record_bg}, 0);
        }
    }

    public static final class Attribute {

        @Nullable
        private Buffer buffer;
        private final int index;
        private final int location;
        public final String name;
        private int size;

        public Attribute(String str, int i2, int i3) {
            this.name = str;
            this.index = i2;
            this.location = i3;
        }

        public void bind() {
            Buffer buffer = (Buffer) Assertions.checkNotNull(this.buffer, "call setBuffer before bind");
            GLES20.glBindBuffer(34962, 0);
            GLES20.glVertexAttribPointer(this.location, this.size, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, buffer);
            GLES20.glEnableVertexAttribArray(this.index);
            GlUtil.checkGlError();
        }

        public void setBuffer(float[] fArr, int i2) {
            this.buffer = GlUtil.createBuffer(fArr);
            this.size = i2;
        }
    }

    public static final class GlException extends RuntimeException {
        public GlException(String str) {
            super(str);
        }
    }

    public static final class Uniform {
        private final int location;
        public final String name;
        private int texId;
        private final int type;
        private int unit;
        private final float[] value = new float[16];

        public Uniform(String str, int i2, int i3) {
            this.name = str;
            this.location = i2;
            this.type = i3;
        }

        public void bind() {
            int i2 = this.type;
            if (i2 == 5126) {
                GLES20.glUniform1fv(this.location, 1, this.value, 0);
                GlUtil.checkGlError();
                return;
            }
            if (i2 == 35676) {
                GLES20.glUniformMatrix4fv(this.location, 1, false, this.value, 0);
                GlUtil.checkGlError();
                return;
            }
            if (this.texId == 0) {
                throw new IllegalStateException("Call setSamplerTexId before bind.");
            }
            GLES20.glActiveTexture(this.unit + 33984);
            int i3 = this.type;
            if (i3 == 36198) {
                GLES20.glBindTexture(36197, this.texId);
            } else {
                if (i3 != 35678) {
                    int i4 = this.type;
                    StringBuilder sb = new StringBuilder(36);
                    sb.append("Unexpected uniform type: ");
                    sb.append(i4);
                    throw new IllegalStateException(sb.toString());
                }
                GLES20.glBindTexture(R2.attr.tab_indicator_height, this.texId);
            }
            GLES20.glUniform1i(this.location, this.unit);
            GLES20.glTexParameteri(R2.attr.tab_indicator_height, 10240, R2.drawable.fff4f2ee_12);
            GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_day, R2.drawable.fff4f2ee_12);
            GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_normal_night, 33071);
            GLES20.glTexParameteri(R2.attr.tab_indicator_height, R2.drawable.ic_home_index_select_day, 33071);
            GlUtil.checkGlError();
        }

        public void setFloat(float f2) {
            this.value[0] = f2;
        }

        public void setFloats(float[] fArr) {
            System.arraycopy(fArr, 0, this.value, 0, fArr.length);
        }

        public void setSamplerTexId(int i2, int i3) {
            this.texId = i2;
            this.unit = i3;
        }
    }

    public static final class UnsupportedEglVersionException extends Exception {
    }

    private GlUtil() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void checkEglException(boolean z2, String str) {
        if (z2) {
            return;
        }
        throwGlException(str);
    }

    public static void checkGlError() {
        int i2 = 0;
        while (true) {
            int iGlGetError = GLES20.glGetError();
            if (iGlGetError == 0) {
                break;
            }
            String strValueOf = String.valueOf(GLU.gluErrorString(iGlGetError));
            Log.e(TAG, strValueOf.length() != 0 ? "glError ".concat(strValueOf) : new String("glError "));
            i2 = iGlGetError;
        }
        if (i2 != 0) {
            String strValueOf2 = String.valueOf(GLU.gluErrorString(i2));
            throwGlException(strValueOf2.length() != 0 ? "glError ".concat(strValueOf2) : new String("glError "));
        }
    }

    public static FloatBuffer createBuffer(float[] fArr) {
        return (FloatBuffer) createBuffer(fArr.length).put(fArr).flip();
    }

    @RequiresApi(17)
    public static EGLContext createEglContext(EGLDisplay eGLDisplay) throws UnsupportedEglVersionException {
        return Api17.createEglContext(eGLDisplay);
    }

    @RequiresApi(17)
    public static EGLDisplay createEglDisplay() {
        return Api17.createEglDisplay();
    }

    public static int createExternalTexture() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, IntBuffer.wrap(iArr));
        GLES20.glBindTexture(36197, iArr[0]);
        GLES20.glTexParameteri(36197, R2.drawable.ic_home_index_normal_day, R2.drawable.fff4f2ee_12);
        GLES20.glTexParameteri(36197, 10240, R2.drawable.fff4f2ee_12);
        GLES20.glTexParameteri(36197, R2.drawable.ic_home_index_normal_night, 33071);
        GLES20.glTexParameteri(36197, R2.drawable.ic_home_index_select_day, 33071);
        checkGlError();
        return iArr[0];
    }

    public static void deleteTexture(int i2) {
        GLES20.glDeleteTextures(1, new int[]{i2}, 0);
        checkGlError();
    }

    @RequiresApi(17)
    public static void destroyEglContext(@Nullable EGLDisplay eGLDisplay, @Nullable EGLContext eGLContext) {
        Api17.destroyEglContext(eGLDisplay, eGLContext);
    }

    @RequiresApi(17)
    public static void focusSurface(EGLDisplay eGLDisplay, EGLContext eGLContext, EGLSurface eGLSurface, int i2, int i3) {
        Api17.focusSurface(eGLDisplay, eGLContext, eGLSurface, i2, i3);
    }

    @RequiresApi(17)
    public static EGLSurface getEglSurface(EGLDisplay eGLDisplay, Object obj) {
        return Api17.getEglSurface(eGLDisplay, obj);
    }

    public static boolean isProtectedContentExtensionSupported(Context context) {
        String strEglQueryString;
        int i2 = Util.SDK_INT;
        if (i2 < 24) {
            return false;
        }
        if (i2 >= 26 || !("samsung".equals(Util.MANUFACTURER) || "XT1650".equals(Util.MODEL))) {
            return (i2 >= 26 || context.getPackageManager().hasSystemFeature("android.hardware.vr.high_performance")) && (strEglQueryString = EGL14.eglQueryString(EGL14.eglGetDisplay(0), R2.drawable.shape_discuss_press)) != null && strEglQueryString.contains(EXTENSION_PROTECTED_CONTENT);
        }
        return false;
    }

    public static boolean isSurfacelessContextExtensionSupported() {
        String strEglQueryString;
        return Util.SDK_INT >= 17 && (strEglQueryString = EGL14.eglQueryString(EGL14.eglGetDisplay(0), R2.drawable.shape_discuss_press)) != null && strEglQueryString.contains(EXTENSION_SURFACELESS_CONTEXT);
    }

    public static String loadAsset(Context context, String str) throws IOException {
        InputStream inputStreamOpen = null;
        try {
            inputStreamOpen = context.getAssets().open(str);
            return Util.fromUtf8Bytes(Util.toByteArray(inputStreamOpen));
        } finally {
            Util.closeQuietly(inputStreamOpen);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int strlen(byte[] bArr) {
        for (int i2 = 0; i2 < bArr.length; i2++) {
            if (bArr[i2] == 0) {
                return i2;
            }
        }
        return bArr.length;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void throwGlException(String str) {
        Log.e(TAG, str);
        if (glAssertionsEnabled) {
            throw new GlException(str);
        }
    }

    public static FloatBuffer createBuffer(int i2) {
        return ByteBuffer.allocateDirect(i2 * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
    }

    public static final class Program {
        private final int programId;

        public Program(String str, String str2) {
            this.programId = GLES20.glCreateProgram();
            GlUtil.checkGlError();
            addShader(35633, str);
            addShader(35632, str2);
        }

        private void addShader(int i2, String str) {
            int iGlCreateShader = GLES20.glCreateShader(i2);
            GLES20.glShaderSource(iGlCreateShader, str);
            GLES20.glCompileShader(iGlCreateShader);
            int[] iArr = {0};
            GLES20.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
            if (iArr[0] != 1) {
                String strGlGetShaderInfoLog = GLES20.glGetShaderInfoLog(iGlCreateShader);
                StringBuilder sb = new StringBuilder(String.valueOf(strGlGetShaderInfoLog).length() + 10 + String.valueOf(str).length());
                sb.append(strGlGetShaderInfoLog);
                sb.append(", source: ");
                sb.append(str);
                GlUtil.throwGlException(sb.toString());
            }
            GLES20.glAttachShader(this.programId, iGlCreateShader);
            GLES20.glDeleteShader(iGlCreateShader);
            GlUtil.checkGlError();
        }

        private Attribute createAttribute(int i2) {
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(this.programId, 35722, iArr, 0);
            int i3 = iArr[0];
            byte[] bArr = new byte[i3];
            int[] iArr2 = new int[1];
            int i4 = this.programId;
            GLES20.glGetActiveAttrib(i4, i2, i3, iArr2, 0, new int[1], 0, new int[1], 0, bArr, 0);
            String str = new String(bArr, 0, GlUtil.strlen(bArr));
            return new Attribute(str, i2, getAttribLocation(str));
        }

        private Uniform createUniform(int i2) {
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(this.programId, 35719, iArr, 0);
            int[] iArr2 = new int[1];
            int i3 = iArr[0];
            byte[] bArr = new byte[i3];
            int i4 = this.programId;
            GLES20.glGetActiveUniform(i4, i2, i3, new int[1], 0, new int[1], 0, iArr2, 0, bArr, 0);
            String str = new String(bArr, 0, GlUtil.strlen(bArr));
            return new Uniform(str, getUniformLocation(str), iArr2[0]);
        }

        public void delete() {
            GLES20.glDeleteProgram(this.programId);
        }

        public int getAttribLocation(String str) {
            return GLES20.glGetAttribLocation(this.programId, str);
        }

        public Attribute[] getAttributes() {
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(this.programId, 35721, iArr, 0);
            int i2 = iArr[0];
            if (i2 != 2) {
                throw new IllegalStateException("Expected two attributes.");
            }
            Attribute[] attributeArr = new Attribute[i2];
            for (int i3 = 0; i3 < iArr[0]; i3++) {
                attributeArr[i3] = createAttribute(i3);
            }
            return attributeArr;
        }

        public int getUniformLocation(String str) {
            return GLES20.glGetUniformLocation(this.programId, str);
        }

        public Uniform[] getUniforms() {
            int[] iArr = new int[1];
            GLES20.glGetProgramiv(this.programId, 35718, iArr, 0);
            Uniform[] uniformArr = new Uniform[iArr[0]];
            for (int i2 = 0; i2 < iArr[0]; i2++) {
                uniformArr[i2] = createUniform(i2);
            }
            return uniformArr;
        }

        public void use() {
            GLES20.glLinkProgram(this.programId);
            int[] iArr = {0};
            GLES20.glGetProgramiv(this.programId, 35714, iArr, 0);
            if (iArr[0] != 1) {
                String strValueOf = String.valueOf(GLES20.glGetProgramInfoLog(this.programId));
                GlUtil.throwGlException(strValueOf.length() != 0 ? "Unable to link shader program: \n".concat(strValueOf) : new String("Unable to link shader program: \n"));
            }
            GlUtil.checkGlError();
            GLES20.glUseProgram(this.programId);
        }

        public Program(Context context, String str, String str2) throws IOException {
            this(GlUtil.loadAsset(context, str), GlUtil.loadAsset(context, str2));
        }

        public Program(String[] strArr, String[] strArr2) {
            this(TextUtils.join("\n", strArr), TextUtils.join("\n", strArr2));
        }
    }
}
