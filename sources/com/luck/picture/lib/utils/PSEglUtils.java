package com.luck.picture.lib.utils;

import android.annotation.TargetApi;
import android.opengl.EGL14;
import android.opengl.GLES10;
import android.opengl.GLES20;
import android.util.Log;
import com.yikaobang.yixue.R2;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;

/* loaded from: classes4.dex */
public class PSEglUtils {
    private static final String TAG = "EglUtils";

    private PSEglUtils() {
    }

    @TargetApi(14)
    private static int getMaxTextureEgl10() {
        EGL10 egl10 = (EGL10) EGLContext.getEGL();
        EGLDisplay eGLDisplayEglGetDisplay = egl10.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl10.eglInitialize(eGLDisplayEglGetDisplay, new int[2]);
        EGLConfig[] eGLConfigArr = new EGLConfig[1];
        int[] iArr = new int[1];
        egl10.eglChooseConfig(eGLDisplayEglGetDisplay, new int[]{R2.drawable.shape_course_directory_num_bg, R2.drawable.shape_invite_share_bg, R2.drawable.shape_comment_answer_bg, 0, R2.drawable.shape_computer_statistics_top_bg, 1, R2.drawable.shape_coupon_record_bg}, eGLConfigArr, 1, iArr);
        if (iArr[0] == 0) {
            return 0;
        }
        EGLConfig eGLConfig = eGLConfigArr[0];
        EGLSurface eGLSurfaceEglCreatePbufferSurface = egl10.eglCreatePbufferSurface(eGLDisplayEglGetDisplay, eGLConfig, new int[]{R2.drawable.shape_discuss_right_press, 64, R2.drawable.shape_discuss_right_default, 64, R2.drawable.shape_coupon_record_bg});
        EGLContext eGLContextEglCreateContext = egl10.eglCreateContext(eGLDisplayEglGetDisplay, eGLConfig, EGL10.EGL_NO_CONTEXT, new int[]{R2.drawable.shape_light_yellow_bg, 1, R2.drawable.shape_coupon_record_bg});
        egl10.eglMakeCurrent(eGLDisplayEglGetDisplay, eGLSurfaceEglCreatePbufferSurface, eGLSurfaceEglCreatePbufferSurface, eGLContextEglCreateContext);
        int[] iArr2 = new int[1];
        GLES10.glGetIntegerv(R2.attr.stroke_Width, iArr2, 0);
        EGLSurface eGLSurface = EGL10.EGL_NO_SURFACE;
        egl10.eglMakeCurrent(eGLDisplayEglGetDisplay, eGLSurface, eGLSurface, EGL10.EGL_NO_CONTEXT);
        egl10.eglDestroySurface(eGLDisplayEglGetDisplay, eGLSurfaceEglCreatePbufferSurface);
        egl10.eglDestroyContext(eGLDisplayEglGetDisplay, eGLContextEglCreateContext);
        egl10.eglTerminate(eGLDisplayEglGetDisplay);
        return iArr2[0];
    }

    @TargetApi(17)
    private static int getMaxTextureEgl14() {
        android.opengl.EGLDisplay eGLDisplayEglGetDisplay = EGL14.eglGetDisplay(0);
        int[] iArr = new int[2];
        EGL14.eglInitialize(eGLDisplayEglGetDisplay, iArr, 0, iArr, 1);
        android.opengl.EGLConfig[] eGLConfigArr = new android.opengl.EGLConfig[1];
        int[] iArr2 = new int[1];
        EGL14.eglChooseConfig(eGLDisplayEglGetDisplay, new int[]{R2.drawable.shape_course_directory_num_bg, R2.drawable.shape_invite_share_bg, R2.drawable.shape_comment_answer_bg, 0, R2.drawable.shape_course_tags_bg, 4, R2.drawable.shape_computer_statistics_top_bg, 1, R2.drawable.shape_coupon_record_bg}, 0, eGLConfigArr, 0, 1, iArr2, 0);
        if (iArr2[0] == 0) {
            return 0;
        }
        android.opengl.EGLConfig eGLConfig = eGLConfigArr[0];
        android.opengl.EGLSurface eGLSurfaceEglCreatePbufferSurface = EGL14.eglCreatePbufferSurface(eGLDisplayEglGetDisplay, eGLConfig, new int[]{R2.drawable.shape_discuss_right_press, 64, R2.drawable.shape_discuss_right_default, 64, R2.drawable.shape_coupon_record_bg}, 0);
        android.opengl.EGLContext eGLContextEglCreateContext = EGL14.eglCreateContext(eGLDisplayEglGetDisplay, eGLConfig, EGL14.EGL_NO_CONTEXT, new int[]{R2.drawable.shape_light_yellow_bg, 2, R2.drawable.shape_coupon_record_bg}, 0);
        EGL14.eglMakeCurrent(eGLDisplayEglGetDisplay, eGLSurfaceEglCreatePbufferSurface, eGLSurfaceEglCreatePbufferSurface, eGLContextEglCreateContext);
        int[] iArr3 = new int[1];
        GLES20.glGetIntegerv(R2.attr.stroke_Width, iArr3, 0);
        android.opengl.EGLSurface eGLSurface = EGL14.EGL_NO_SURFACE;
        EGL14.eglMakeCurrent(eGLDisplayEglGetDisplay, eGLSurface, eGLSurface, EGL14.EGL_NO_CONTEXT);
        EGL14.eglDestroySurface(eGLDisplayEglGetDisplay, eGLSurfaceEglCreatePbufferSurface);
        EGL14.eglDestroyContext(eGLDisplayEglGetDisplay, eGLContextEglCreateContext);
        EGL14.eglTerminate(eGLDisplayEglGetDisplay);
        return iArr3[0];
    }

    public static int getMaxTextureSize() {
        try {
            return getMaxTextureEgl14();
        } catch (Exception e2) {
            Log.d(TAG, "getMaxTextureSize: ", e2);
            return 0;
        }
    }
}
