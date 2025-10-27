package androidx.camera.core.processing;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLExt;
import android.opengl.EGLSurface;
import android.opengl.GLES20;
import android.util.Log;
import android.util.Size;
import android.view.Surface;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.WorkerThread;
import androidx.camera.core.Logger;
import androidx.core.util.Preconditions;
import com.google.auto.value.AutoValue;
import com.yikaobang.yixue.R2;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@RequiresApi(21)
@WorkerThread
/* loaded from: classes.dex */
public final class OpenGlRenderer {
    private static final String DEFAULT_FRAGMENT_SHADER;
    private static final String DEFAULT_VERTEX_SHADER;
    private static final int SIZEOF_FLOAT = 4;
    private static final String TAG = "OpenGlRenderer";
    private static final FloatBuffer TEX_BUF;
    private static final float[] TEX_COORDS;
    private static final int TEX_TARGET = 36197;
    private static final String VAR_TEXTURE = "sTexture";
    private static final String VAR_TEXTURE_COORD = "vTextureCoord";
    private static final FloatBuffer VERTEX_BUF;
    private static final float[] VERTEX_COORDS;

    @Nullable
    private OutputSurface mCurrentOutputSurface;

    @Nullable
    private EGLConfig mEglConfig;

    @Nullable
    private Thread mGlThread;
    private final AtomicBoolean mInitialized = new AtomicBoolean(false);
    private final Map<Surface, OutputSurface> mOutputSurfaceMap = new HashMap();

    @NonNull
    private EGLDisplay mEglDisplay = EGL14.EGL_NO_DISPLAY;

    @NonNull
    private EGLContext mEglContext = EGL14.EGL_NO_CONTEXT;

    @NonNull
    private EGLSurface mTempSurface = EGL14.EGL_NO_SURFACE;
    private int mTexId = -1;
    private int mProgramHandle = -1;
    private int mTexMatrixLoc = -1;
    private int mPositionLoc = -1;
    private int mTexCoordLoc = -1;

    @AutoValue
    public static abstract class OutputSurface {
        @NonNull
        public static OutputSurface of(@NonNull EGLSurface eGLSurface, int i2, int i3) {
            return new AutoValue_OpenGlRenderer_OutputSurface(eGLSurface, i2, i3);
        }

        @NonNull
        public abstract EGLSurface getEglSurface();

        public abstract int getHeight();

        public abstract int getWidth();
    }

    static {
        Locale locale = Locale.US;
        DEFAULT_VERTEX_SHADER = String.format(locale, "uniform mat4 uTexMatrix;\nattribute vec4 aPosition;\nattribute vec4 aTextureCoord;\nvarying vec2 %s;\nvoid main() {\n    gl_Position = aPosition;\n    %s = (uTexMatrix * aTextureCoord).xy;\n}\n", VAR_TEXTURE_COORD, VAR_TEXTURE_COORD);
        DEFAULT_FRAGMENT_SHADER = String.format(locale, "#extension GL_OES_EGL_image_external : require\nprecision mediump float;\nvarying vec2 %s;\nuniform samplerExternalOES %s;\nvoid main() {\n    gl_FragColor = texture2D(%s, %s);\n}\n", VAR_TEXTURE_COORD, VAR_TEXTURE, VAR_TEXTURE, VAR_TEXTURE_COORD);
        float[] fArr = {-1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 1.0f};
        VERTEX_COORDS = fArr;
        VERTEX_BUF = createFloatBuffer(fArr);
        float[] fArr2 = {0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f};
        TEX_COORDS = fArr2;
        TEX_BUF = createFloatBuffer(fArr2);
    }

    private static void checkEglErrorOrThrow(@NonNull String str) {
        int iEglGetError = EGL14.eglGetError();
        if (iEglGetError == 12288) {
            return;
        }
        throw new IllegalStateException(str + ": EGL error: 0x" + Integer.toHexString(iEglGetError));
    }

    private static void checkGlErrorOrThrow(@NonNull String str) {
        int iGlGetError = GLES20.glGetError();
        if (iGlGetError == 0) {
            return;
        }
        throw new IllegalStateException(str + ": GL error 0x" + Integer.toHexString(iGlGetError));
    }

    private void checkGlThreadOrThrow() {
        Preconditions.checkState(this.mGlThread == Thread.currentThread(), "Method call must be called on the GL thread.");
    }

    private void checkInitializedOrThrow(boolean z2) {
        Preconditions.checkState(z2 == this.mInitialized.get(), z2 ? "OpenGlRenderer is not initialized" : "OpenGlRenderer is already initialized");
    }

    private static void checkLocationOrThrow(int i2, @NonNull String str) {
        if (i2 >= 0) {
            return;
        }
        throw new IllegalStateException("Unable to locate '" + str + "' in program");
    }

    private void createEglContext() {
        EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
        this.mEglDisplay = eGLDisplayEglGetDisplay;
        if (Objects.equals(eGLDisplayEglGetDisplay, EGL14.EGL_NO_DISPLAY)) {
            throw new IllegalStateException("Unable to get EGL14 display");
        }
        int[] iArr = new int[2];
        if (!EGL14.eglInitialize(this.mEglDisplay, iArr, 0, iArr, 1)) {
            this.mEglDisplay = EGL14.EGL_NO_DISPLAY;
            throw new IllegalStateException("Unable to initialize EGL14");
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        if (!EGL14.eglChooseConfig(this.mEglDisplay, new int[]{R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_color22_corner12, 8, R2.drawable.shape_course_tags_bg, 4, 12610, 1, R2.drawable.shape_computer_statistics_top_bg, 5, R2.drawable.shape_coupon_record_bg}, 0, eGLConfigArr, 0, 1, new int[1], 0)) {
            throw new IllegalStateException("Unable to find a suitable EGLConfig");
        }
        EGLConfig eGLConfig = eGLConfigArr[0];
        EGLContext eGLContextEglCreateContext = EGL14.eglCreateContext(this.mEglDisplay, eGLConfig, EGL14.EGL_NO_CONTEXT, new int[]{R2.drawable.shape_light_yellow_bg, 2, R2.drawable.shape_coupon_record_bg}, 0);
        checkEglErrorOrThrow("eglCreateContext");
        this.mEglConfig = eGLConfig;
        this.mEglContext = eGLContextEglCreateContext;
        int[] iArr2 = new int[1];
        EGL14.eglQueryContext(this.mEglDisplay, eGLContextEglCreateContext, R2.drawable.shape_light_yellow_bg, iArr2, 0);
        Log.d(TAG, "EGLContext created, client version " + iArr2[0]);
    }

    @NonNull
    public static FloatBuffer createFloatBuffer(@NonNull float[] fArr) {
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(fArr.length * 4);
        byteBufferAllocateDirect.order(ByteOrder.nativeOrder());
        FloatBuffer floatBufferAsFloatBuffer = byteBufferAllocateDirect.asFloatBuffer();
        floatBufferAsFloatBuffer.put(fArr);
        floatBufferAsFloatBuffer.position(0);
        return floatBufferAsFloatBuffer;
    }

    @NonNull
    private static EGLSurface createPBufferSurface(@NonNull EGLDisplay eGLDisplay, @NonNull EGLConfig eGLConfig, int i2, int i3) {
        EGLSurface eGLSurfaceEglCreatePbufferSurface = EGL14.eglCreatePbufferSurface(eGLDisplay, eGLConfig, new int[]{R2.drawable.shape_discuss_right_press, i2, R2.drawable.shape_discuss_right_default, i3, R2.drawable.shape_coupon_record_bg}, 0);
        checkEglErrorOrThrow("eglCreatePbufferSurface");
        if (eGLSurfaceEglCreatePbufferSurface != null) {
            return eGLSurfaceEglCreatePbufferSurface;
        }
        throw new IllegalStateException("surface was null");
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x006b  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void createProgram(@androidx.annotation.NonNull androidx.camera.core.processing.ShaderProvider r8) throws java.lang.Throwable {
        /*
            r7 = this;
            java.lang.String r0 = "glAttachShader"
            r1 = -1
            java.lang.String r2 = androidx.camera.core.processing.OpenGlRenderer.DEFAULT_VERTEX_SHADER     // Catch: java.lang.IllegalArgumentException -> L63 java.lang.IllegalStateException -> L65
            r3 = 35633(0x8b31, float:4.9932E-41)
            int r2 = loadShader(r3, r2)     // Catch: java.lang.IllegalArgumentException -> L63 java.lang.IllegalStateException -> L65
            int r8 = r7.loadFragmentShader(r8)     // Catch: java.lang.IllegalArgumentException -> L5d java.lang.IllegalStateException -> L5f
            int r3 = android.opengl.GLES20.glCreateProgram()     // Catch: java.lang.IllegalArgumentException -> L58 java.lang.IllegalStateException -> L5a
            java.lang.String r4 = "glCreateProgram"
            checkGlErrorOrThrow(r4)     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            android.opengl.GLES20.glAttachShader(r3, r2)     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            checkGlErrorOrThrow(r0)     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            android.opengl.GLES20.glAttachShader(r3, r8)     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            checkGlErrorOrThrow(r0)     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            android.opengl.GLES20.glLinkProgram(r3)     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            r0 = 1
            int[] r4 = new int[r0]     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            r5 = 35714(0x8b82, float:5.0046E-41)
            r6 = 0
            android.opengl.GLES20.glGetProgramiv(r3, r5, r4, r6)     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            r4 = r4[r6]     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            if (r4 != r0) goto L39
            r7.mProgramHandle = r3     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            return
        L39:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            r4.<init>()     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            java.lang.String r5 = "Could not link program: "
            r4.append(r5)     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            java.lang.String r5 = android.opengl.GLES20.glGetProgramInfoLog(r3)     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            r4.append(r5)     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            java.lang.String r4 = r4.toString()     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            r0.<init>(r4)     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
            throw r0     // Catch: java.lang.IllegalArgumentException -> L54 java.lang.IllegalStateException -> L56
        L54:
            r0 = move-exception
            goto L69
        L56:
            r0 = move-exception
            goto L69
        L58:
            r0 = move-exception
            goto L5b
        L5a:
            r0 = move-exception
        L5b:
            r3 = r1
            goto L69
        L5d:
            r0 = move-exception
            goto L60
        L5f:
            r0 = move-exception
        L60:
            r8 = r1
            r3 = r8
            goto L69
        L63:
            r0 = move-exception
            goto L66
        L65:
            r0 = move-exception
        L66:
            r8 = r1
            r2 = r8
            r3 = r2
        L69:
            if (r2 == r1) goto L6e
            android.opengl.GLES20.glDeleteShader(r2)
        L6e:
            if (r8 == r1) goto L73
            android.opengl.GLES20.glDeleteShader(r8)
        L73:
            if (r3 == r1) goto L78
            android.opengl.GLES20.glDeleteProgram(r3)
        L78:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.camera.core.processing.OpenGlRenderer.createProgram(androidx.camera.core.processing.ShaderProvider):void");
    }

    private void createTempSurface() {
        EGLDisplay eGLDisplay = this.mEglDisplay;
        EGLConfig eGLConfig = this.mEglConfig;
        Objects.requireNonNull(eGLConfig);
        this.mTempSurface = createPBufferSurface(eGLDisplay, eGLConfig, 1, 1);
    }

    private void createTexture() {
        int[] iArr = new int[1];
        GLES20.glGenTextures(1, iArr, 0);
        checkGlErrorOrThrow("glGenTextures");
        int i2 = iArr[0];
        GLES20.glBindTexture(TEX_TARGET, i2);
        checkGlErrorOrThrow("glBindTexture " + i2);
        GLES20.glTexParameterf(TEX_TARGET, R2.drawable.ic_home_index_normal_day, 9728.0f);
        GLES20.glTexParameterf(TEX_TARGET, 10240, 9729.0f);
        GLES20.glTexParameteri(TEX_TARGET, R2.drawable.ic_home_index_normal_night, 33071);
        GLES20.glTexParameteri(TEX_TARGET, R2.drawable.ic_home_index_select_day, 33071);
        checkGlErrorOrThrow("glTexParameter");
        this.mTexId = i2;
    }

    @NonNull
    private static EGLSurface createWindowSurface(@NonNull EGLDisplay eGLDisplay, @NonNull EGLConfig eGLConfig, @NonNull Surface surface) {
        EGLSurface eGLSurfaceEglCreateWindowSurface = EGL14.eglCreateWindowSurface(eGLDisplay, eGLConfig, surface, new int[]{R2.drawable.shape_coupon_record_bg}, 0);
        checkEglErrorOrThrow("eglCreateWindowSurface");
        if (eGLSurfaceEglCreateWindowSurface != null) {
            return eGLSurfaceEglCreateWindowSurface;
        }
        throw new IllegalStateException("surface was null");
    }

    @NonNull
    private Size getSurfaceSize(@NonNull EGLSurface eGLSurface) {
        return new Size(querySurface(this.mEglDisplay, eGLSurface, R2.drawable.shape_discuss_right_press), querySurface(this.mEglDisplay, eGLSurface, R2.drawable.shape_discuss_right_default));
    }

    private int loadFragmentShader(@NonNull ShaderProvider shaderProvider) {
        if (shaderProvider == ShaderProvider.DEFAULT) {
            return loadShader(35632, DEFAULT_FRAGMENT_SHADER);
        }
        try {
            String strCreateFragmentShader = shaderProvider.createFragmentShader(VAR_TEXTURE, VAR_TEXTURE_COORD);
            if (strCreateFragmentShader != null && strCreateFragmentShader.contains(VAR_TEXTURE_COORD) && strCreateFragmentShader.contains(VAR_TEXTURE)) {
                return loadShader(35632, strCreateFragmentShader);
            }
            throw new IllegalArgumentException("Invalid fragment shader");
        } catch (Throwable th) {
            if (th instanceof IllegalArgumentException) {
                throw th;
            }
            throw new IllegalArgumentException("Unable to compile fragment shader", th);
        }
    }

    private void loadLocations() {
        int iGlGetAttribLocation = GLES20.glGetAttribLocation(this.mProgramHandle, "aPosition");
        this.mPositionLoc = iGlGetAttribLocation;
        checkLocationOrThrow(iGlGetAttribLocation, "aPosition");
        int iGlGetAttribLocation2 = GLES20.glGetAttribLocation(this.mProgramHandle, "aTextureCoord");
        this.mTexCoordLoc = iGlGetAttribLocation2;
        checkLocationOrThrow(iGlGetAttribLocation2, "aTextureCoord");
        int iGlGetUniformLocation = GLES20.glGetUniformLocation(this.mProgramHandle, "uTexMatrix");
        this.mTexMatrixLoc = iGlGetUniformLocation;
        checkLocationOrThrow(iGlGetUniformLocation, "uTexMatrix");
    }

    private static int loadShader(int i2, @NonNull String str) {
        int iGlCreateShader = GLES20.glCreateShader(i2);
        checkGlErrorOrThrow("glCreateShader type=" + i2);
        GLES20.glShaderSource(iGlCreateShader, str);
        GLES20.glCompileShader(iGlCreateShader);
        int[] iArr = new int[1];
        GLES20.glGetShaderiv(iGlCreateShader, 35713, iArr, 0);
        if (iArr[0] != 0) {
            return iGlCreateShader;
        }
        Logger.w(TAG, "Could not compile shader: " + str);
        GLES20.glDeleteShader(iGlCreateShader);
        throw new IllegalStateException("Could not compile shader type " + i2 + ":" + GLES20.glGetShaderInfoLog(iGlCreateShader));
    }

    private void makeCurrent(@NonNull EGLSurface eGLSurface) {
        Preconditions.checkNotNull(this.mEglDisplay);
        Preconditions.checkNotNull(this.mEglContext);
        if (!EGL14.eglMakeCurrent(this.mEglDisplay, eGLSurface, eGLSurface, this.mEglContext)) {
            throw new IllegalStateException("eglMakeCurrent failed");
        }
    }

    private static int querySurface(@NonNull EGLDisplay eGLDisplay, @NonNull EGLSurface eGLSurface, int i2) {
        int[] iArr = new int[1];
        EGL14.eglQuerySurface(eGLDisplay, eGLSurface, i2, iArr, 0);
        return iArr[0];
    }

    private void releaseInternal() {
        int i2 = this.mProgramHandle;
        if (i2 != -1) {
            GLES20.glDeleteProgram(i2);
            this.mProgramHandle = -1;
        }
        Iterator<OutputSurface> it = this.mOutputSurfaceMap.values().iterator();
        while (it.hasNext()) {
            EGL14.eglDestroySurface(this.mEglDisplay, it.next().getEglSurface());
        }
        this.mOutputSurfaceMap.clear();
        if (!Objects.equals(this.mTempSurface, EGL14.EGL_NO_SURFACE)) {
            EGL14.eglDestroySurface(this.mEglDisplay, this.mTempSurface);
            this.mTempSurface = EGL14.EGL_NO_SURFACE;
        }
        if (!Objects.equals(this.mEglDisplay, EGL14.EGL_NO_DISPLAY)) {
            if (!Objects.equals(this.mEglContext, EGL14.EGL_NO_CONTEXT)) {
                EGLDisplay eGLDisplay = this.mEglDisplay;
                EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
                EGL14.eglMakeCurrent(eGLDisplay, eGLSurface, eGLSurface, this.mEglContext);
                EGL14.eglDestroyContext(this.mEglDisplay, this.mEglContext);
                this.mEglContext = EGL14.EGL_NO_CONTEXT;
            }
            EGL14.eglTerminate(this.mEglDisplay);
            this.mEglDisplay = EGL14.EGL_NO_DISPLAY;
        }
        this.mEglConfig = null;
        this.mProgramHandle = -1;
        this.mTexMatrixLoc = -1;
        this.mPositionLoc = -1;
        this.mTexCoordLoc = -1;
        this.mTexId = -1;
        this.mCurrentOutputSurface = null;
        this.mGlThread = null;
    }

    public int getTextureName() {
        checkInitializedOrThrow(true);
        checkGlThreadOrThrow();
        return this.mTexId;
    }

    public void init(@NonNull ShaderProvider shaderProvider) {
        checkInitializedOrThrow(false);
        try {
            createEglContext();
            createTempSurface();
            makeCurrent(this.mTempSurface);
            createProgram(shaderProvider);
            loadLocations();
            createTexture();
            this.mGlThread = Thread.currentThread();
            this.mInitialized.set(true);
        } catch (IllegalArgumentException | IllegalStateException e2) {
            releaseInternal();
            throw e2;
        }
    }

    public void release() {
        if (this.mInitialized.getAndSet(false)) {
            checkGlThreadOrThrow();
            releaseInternal();
        }
    }

    public void render(long j2, @NonNull float[] fArr) {
        checkInitializedOrThrow(true);
        checkGlThreadOrThrow();
        if (this.mCurrentOutputSurface == null) {
            return;
        }
        GLES20.glUseProgram(this.mProgramHandle);
        checkGlErrorOrThrow("glUseProgram");
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(TEX_TARGET, this.mTexId);
        GLES20.glUniformMatrix4fv(this.mTexMatrixLoc, 1, false, fArr, 0);
        checkGlErrorOrThrow("glUniformMatrix4fv");
        GLES20.glEnableVertexAttribArray(this.mPositionLoc);
        checkGlErrorOrThrow("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.mPositionLoc, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) VERTEX_BUF);
        checkGlErrorOrThrow("glVertexAttribPointer");
        GLES20.glEnableVertexAttribArray(this.mTexCoordLoc);
        checkGlErrorOrThrow("glEnableVertexAttribArray");
        GLES20.glVertexAttribPointer(this.mTexCoordLoc, 2, R2.color.m3_ref_palette_dynamic_tertiary60, false, 0, (Buffer) TEX_BUF);
        checkGlErrorOrThrow("glVertexAttribPointer");
        GLES20.glDrawArrays(5, 0, 4);
        checkGlErrorOrThrow("glDrawArrays");
        GLES20.glDisableVertexAttribArray(this.mPositionLoc);
        GLES20.glDisableVertexAttribArray(this.mTexCoordLoc);
        GLES20.glUseProgram(0);
        GLES20.glBindTexture(TEX_TARGET, 0);
        EGLExt.eglPresentationTimeANDROID(this.mEglDisplay, this.mCurrentOutputSurface.getEglSurface(), j2);
        if (EGL14.eglSwapBuffers(this.mEglDisplay, this.mCurrentOutputSurface.getEglSurface())) {
            return;
        }
        Logger.w(TAG, "Failed to swap buffers with EGL error: 0x" + Integer.toHexString(EGL14.eglGetError()));
    }

    public void setOutputSurface(@NonNull Surface surface) {
        checkInitializedOrThrow(true);
        checkGlThreadOrThrow();
        if (!this.mOutputSurfaceMap.containsKey(surface)) {
            EGLDisplay eGLDisplay = this.mEglDisplay;
            EGLConfig eGLConfig = this.mEglConfig;
            Objects.requireNonNull(eGLConfig);
            EGLSurface eGLSurfaceCreateWindowSurface = createWindowSurface(eGLDisplay, eGLConfig, surface);
            Size surfaceSize = getSurfaceSize(eGLSurfaceCreateWindowSurface);
            this.mOutputSurfaceMap.put(surface, OutputSurface.of(eGLSurfaceCreateWindowSurface, surfaceSize.getWidth(), surfaceSize.getHeight()));
        }
        OutputSurface outputSurface = this.mOutputSurfaceMap.get(surface);
        Objects.requireNonNull(outputSurface);
        this.mCurrentOutputSurface = outputSurface;
        makeCurrent(outputSurface.getEglSurface());
        GLES20.glViewport(0, 0, this.mCurrentOutputSurface.getWidth(), this.mCurrentOutputSurface.getHeight());
        GLES20.glScissor(0, 0, this.mCurrentOutputSurface.getWidth(), this.mCurrentOutputSurface.getHeight());
    }
}
