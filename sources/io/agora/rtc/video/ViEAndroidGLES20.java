package io.agora.rtc.video;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.yikaobang.yixue.R2;
import io.agora.rtc.internal.Logging;
import java.util.concurrent.locks.ReentrantLock;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

/* loaded from: classes8.dex */
public class ViEAndroidGLES20 extends GLSurfaceView implements GLSurfaceView.Renderer {
    private static final boolean DEBUG = false;
    private static String TAG = "ViEAndroidGLES20";
    private int mLastRotation;
    private ReentrantLock nativeFunctionLock;
    private boolean nativeFunctionsRegisted;
    private int nativeGLPragram;
    private boolean nativeGLResourceUpdated;
    private int[] nativeGLTextureId;
    private long nativeObject;
    private boolean openGLCreated;
    private boolean surfaceCreated;
    private int viewHeight;
    private int viewWidth;

    public ViEAndroidGLES20(Context context) {
        super(context);
        this.surfaceCreated = false;
        this.openGLCreated = false;
        this.nativeFunctionsRegisted = false;
        this.nativeFunctionLock = new ReentrantLock();
        this.nativeObject = 0L;
        this.viewWidth = 0;
        this.viewHeight = 0;
        this.nativeGLPragram = 0;
        this.nativeGLTextureId = new int[]{0, 0, 0};
        this.nativeGLResourceUpdated = false;
        this.mLastRotation = -1;
        init(false, 0, 0);
    }

    private native int CreateOpenGLNative(long nativeObject, int width, int height);

    private native void DrawNative(long nativeObject);

    public static boolean IsSupported(Context context) {
        return ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getDeviceConfigurationInfo().reqGlEsVersion >= 131072;
    }

    private native void OnCfgChangedNative(long nativeObject, int ori);

    public static boolean UseOpenGL2(Object renderWindow) {
        return ViEAndroidGLES20.class.isInstance(renderWindow);
    }

    private static void checkEglError(String prompt, EGL10 egl) {
        while (true) {
            int iEglGetError = egl.eglGetError();
            if (iEglGetError == 12288) {
                return;
            }
            try {
                Logging.e(TAG, String.format("%s: EGL error: 0x%x", prompt, Integer.valueOf(iEglGetError)));
            } catch (Exception unused) {
                Log.e("AGORA_SDK", "egl error!!, video may not displayed!!");
            }
        }
    }

    private int checkOrientation() {
        Display defaultDisplay;
        if (getContext() == null || getContext().getSystemService("window") == null || (defaultDisplay = ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay()) == null) {
            return this.mLastRotation;
        }
        try {
            return defaultDisplay.getRotation();
        } catch (RuntimeException unused) {
            Logging.e(TAG, "checkOrientation display getRotation throwout exception");
            return this.mLastRotation;
        }
    }

    private void init(boolean translucent, int depth, int stencil) {
        if (translucent) {
            getHolder().setFormat(-3);
        }
        setEGLContextClientVersion(2);
        setEGLConfigChooser(translucent ? new ConfigChooser(8, 8, 8, 8, depth, stencil) : new ConfigChooser(5, 6, 5, 0, depth, stencil));
        setRenderer(this);
        setRenderMode(0);
    }

    private void updateOrientation() {
        int iCheckOrientation = checkOrientation();
        if (iCheckOrientation != this.mLastRotation) {
            this.nativeFunctionLock.lock();
            if (this.nativeFunctionsRegisted) {
                OnCfgChangedNative(this.nativeObject, iCheckOrientation);
            }
            this.mLastRotation = iCheckOrientation;
            this.nativeFunctionLock.unlock();
        }
    }

    public void DeRegisterNativeObject() {
        this.nativeFunctionLock.lock();
        this.nativeFunctionsRegisted = false;
        this.openGLCreated = false;
        this.nativeObject = 0L;
        this.nativeFunctionLock.unlock();
        releaseOpenGLResource();
    }

    public void ReDraw() {
        if (this.surfaceCreated) {
            requestRender();
        }
    }

    public void RegisterNativeObject(long nativeObject) {
        this.nativeFunctionLock.lock();
        this.nativeObject = nativeObject;
        this.nativeFunctionsRegisted = true;
        this.nativeFunctionLock.unlock();
    }

    public void UpdateOpenGLResource(int[] value) {
        this.nativeGLPragram = value[0];
        int i2 = 0;
        while (i2 < 3) {
            int i3 = i2 + 1;
            this.nativeGLTextureId[i2] = value[i3];
            i2 = i3;
        }
        this.nativeGLResourceUpdated = true;
        Logging.i(TAG, "UpdateOpenGLResource, program = " + value[0] + " texture[0~2] = " + value[1] + " ," + value[2] + " ," + value[3]);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        updateOrientation();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onDrawFrame(GL10 gl) {
        updateOrientation();
        this.nativeFunctionLock.lock();
        if (!this.nativeFunctionsRegisted || !this.surfaceCreated) {
            this.nativeFunctionLock.unlock();
            return;
        }
        if (!this.openGLCreated) {
            if (CreateOpenGLNative(this.nativeObject, this.viewWidth, this.viewHeight) != 0) {
                return;
            } else {
                this.openGLCreated = true;
            }
        }
        DrawNative(this.nativeObject);
        this.nativeFunctionLock.unlock();
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        this.surfaceCreated = true;
        this.viewWidth = width;
        this.viewHeight = height;
        Log.i("AGORA_SDK", "Surface changed to width " + width + " height " + height);
        this.nativeFunctionLock.lock();
        try {
            try {
                if (this.nativeFunctionsRegisted && CreateOpenGLNative(this.nativeObject, width, height) == 0) {
                    this.openGLCreated = true;
                }
            } catch (Exception unused) {
                Log.w("AGORA_SDK", "Exception occurs when create RtcEngine");
            }
        } finally {
            this.nativeFunctionLock.unlock();
        }
    }

    @Override // android.opengl.GLSurfaceView.Renderer
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    public void releaseOpenGLResource() {
        if (this.nativeGLResourceUpdated) {
            queueEvent(new Runnable() { // from class: io.agora.rtc.video.ViEAndroidGLES20.1
                @Override // java.lang.Runnable
                public void run() {
                    Logging.i(ViEAndroidGLES20.TAG, "releaseOpenGLResource, value = " + ViEAndroidGLES20.this.nativeGLPragram + " ," + ViEAndroidGLES20.this.nativeGLTextureId[0] + " ," + ViEAndroidGLES20.this.nativeGLTextureId[1] + " ," + ViEAndroidGLES20.this.nativeGLTextureId[2]);
                    GLES20.glDeleteProgram(ViEAndroidGLES20.this.nativeGLPragram);
                    GLES20.glDeleteTextures(3, ViEAndroidGLES20.this.nativeGLTextureId, 0);
                    int iGlGetError = GLES20.glGetError();
                    if (iGlGetError != 0) {
                        Logging.e(ViEAndroidGLES20.TAG, "glDelete error: " + iGlGetError);
                    }
                }
            });
            this.nativeGLResourceUpdated = false;
        }
    }

    public static class ConfigChooser implements GLSurfaceView.EGLConfigChooser {
        private static int EGL_OPENGL_ES2_BIT = 4;
        private static int[] s_configAttribs2 = {R2.drawable.shape_color2a_bottom_corner12, 4, R2.drawable.shape_color22_top_corner12, 4, R2.drawable.shape_color22_normal, 4, R2.drawable.shape_course_tags_bg, 4, R2.drawable.shape_coupon_record_bg};
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue = new int[1];

        public ConfigChooser(int r2, int g2, int b3, int a3, int depth, int stencil) {
            this.mRedSize = r2;
            this.mGreenSize = g2;
            this.mBlueSize = b3;
            this.mAlphaSize = a3;
            this.mDepthSize = depth;
            this.mStencilSize = stencil;
        }

        private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
            return egl.eglGetConfigAttrib(display, config, attribute, this.mValue) ? this.mValue[0] : defaultValue;
        }

        private void printConfig(EGL10 egl, EGLDisplay display, EGLConfig config) {
            int[] iArr = {R2.drawable.shape_color22_bottom_corner12, R2.drawable.shape_color22_corner12, R2.drawable.shape_color22_normal, R2.drawable.shape_color22_top_corner12, R2.drawable.shape_color2a_bottom_corner12, R2.drawable.shape_color2a_corner12, R2.drawable.shape_color2a_normal, R2.drawable.shape_color2a_top_corner12, R2.drawable.shape_color_f9fafb_bg, R2.drawable.shape_comment_answer_bg, R2.drawable.shape_comment_dialog_top16, R2.drawable.shape_computer_answerd_question_bg, R2.drawable.shape_computer_arrow_bg, R2.drawable.shape_computer_bottom_btn_bg, R2.drawable.shape_computer_current_question_bg, R2.drawable.shape_computer_no_answer_question_bg, R2.drawable.shape_computer_reset_test_btn_bg, R2.drawable.shape_computer_review_test_btn_bg, R2.drawable.shape_computer_review_test_btn_bg_round8, R2.drawable.shape_computer_statistics_top_bg, R2.drawable.shape_computer_statustics_line_bg, R2.drawable.shape_copy_invite, R2.drawable.shape_computer_yellow_question_bg, R2.drawable.shape_computer_time_bg, R2.drawable.shape_coupon_record_item_bg, R2.drawable.shape_coupon_record_item_bg_expand, R2.drawable.shape_course_combine_flag_bg, R2.drawable.shape_course_detail_activity_btn_bg, R2.drawable.shape_course_detail_sales_bg, R2.drawable.shape_course_detail_vip_btn_bg, R2.drawable.shape_course_directory_num_bg, R2.drawable.shape_course_tags_bg, R2.drawable.shape_course_tags_promotion_bg};
            String[] strArr = {"EGL_BUFFER_SIZE", "EGL_ALPHA_SIZE", "EGL_BLUE_SIZE", "EGL_GREEN_SIZE", "EGL_RED_SIZE", "EGL_DEPTH_SIZE", "EGL_STENCIL_SIZE", "EGL_CONFIG_CAVEAT", "EGL_CONFIG_ID", "EGL_LEVEL", "EGL_MAX_PBUFFER_HEIGHT", "EGL_MAX_PBUFFER_PIXELS", "EGL_MAX_PBUFFER_WIDTH", "EGL_NATIVE_RENDERABLE", "EGL_NATIVE_VISUAL_ID", "EGL_NATIVE_VISUAL_TYPE", "EGL_PRESERVED_RESOURCES", "EGL_SAMPLES", "EGL_SAMPLE_BUFFERS", "EGL_SURFACE_TYPE", "EGL_TRANSPARENT_TYPE", "EGL_TRANSPARENT_RED_VALUE", "EGL_TRANSPARENT_GREEN_VALUE", "EGL_TRANSPARENT_BLUE_VALUE", "EGL_BIND_TO_TEXTURE_RGB", "EGL_BIND_TO_TEXTURE_RGBA", "EGL_MIN_SWAP_INTERVAL", "EGL_MAX_SWAP_INTERVAL", "EGL_LUMINANCE_SIZE", "EGL_ALPHA_MASK_SIZE", "EGL_COLOR_BUFFER_TYPE", "EGL_RENDERABLE_TYPE", "EGL_CONFORMANT"};
            int[] iArr2 = new int[1];
            for (int i2 = 0; i2 < 33; i2++) {
                int i3 = iArr[i2];
                String str = strArr[i2];
                if (egl.eglGetConfigAttrib(display, config, i3, iArr2)) {
                    Logging.w(ViEAndroidGLES20.TAG, String.format("  %s: %d\n", str, Integer.valueOf(iArr2[0])));
                } else {
                    while (egl.eglGetError() != 12288) {
                    }
                }
            }
        }

        private void printConfigs(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
            int length = configs.length;
            Logging.w(ViEAndroidGLES20.TAG, String.format("%d configurations", Integer.valueOf(length)));
            for (int i2 = 0; i2 < length; i2++) {
                Logging.w(ViEAndroidGLES20.TAG, String.format("Configuration %d:\n", Integer.valueOf(i2)));
                printConfig(egl, display, configs[i2]);
            }
        }

        @Override // android.opengl.GLSurfaceView.EGLConfigChooser
        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] iArr = new int[1];
            egl.eglChooseConfig(display, s_configAttribs2, null, 0, iArr);
            int i2 = iArr[0];
            if (i2 <= 0) {
                Logging.w(ViEAndroidGLES20.TAG, "no configurations found");
                return null;
            }
            EGLConfig[] eGLConfigArr = new EGLConfig[i2];
            egl.eglChooseConfig(display, s_configAttribs2, eGLConfigArr, i2, iArr);
            return chooseConfig(egl, display, eGLConfigArr);
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
            for (EGLConfig eGLConfig : configs) {
                int iFindConfigAttrib = findConfigAttrib(egl, display, eGLConfig, R2.drawable.shape_color2a_corner12, 0);
                int iFindConfigAttrib2 = findConfigAttrib(egl, display, eGLConfig, R2.drawable.shape_color2a_normal, 0);
                if (iFindConfigAttrib >= this.mDepthSize && iFindConfigAttrib2 >= this.mStencilSize) {
                    int iFindConfigAttrib3 = findConfigAttrib(egl, display, eGLConfig, R2.drawable.shape_color2a_bottom_corner12, 0);
                    int iFindConfigAttrib4 = findConfigAttrib(egl, display, eGLConfig, R2.drawable.shape_color22_top_corner12, 0);
                    int iFindConfigAttrib5 = findConfigAttrib(egl, display, eGLConfig, R2.drawable.shape_color22_normal, 0);
                    int iFindConfigAttrib6 = findConfigAttrib(egl, display, eGLConfig, R2.drawable.shape_color22_corner12, 0);
                    if (iFindConfigAttrib3 == this.mRedSize && iFindConfigAttrib4 == this.mGreenSize && iFindConfigAttrib5 == this.mBlueSize && iFindConfigAttrib6 == this.mAlphaSize) {
                        return eGLConfig;
                    }
                }
            }
            return null;
        }
    }

    public ViEAndroidGLES20(Context context, boolean translucent, int depth, int stencil) {
        super(context);
        this.surfaceCreated = false;
        this.openGLCreated = false;
        this.nativeFunctionsRegisted = false;
        this.nativeFunctionLock = new ReentrantLock();
        this.nativeObject = 0L;
        this.viewWidth = 0;
        this.viewHeight = 0;
        this.nativeGLPragram = 0;
        this.nativeGLTextureId = new int[]{0, 0, 0};
        this.nativeGLResourceUpdated = false;
        this.mLastRotation = -1;
        init(translucent, depth, stencil);
    }
}
