package io.agora.rtc.gl;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import com.yikaobang.yixue.R2;
import io.agora.rtc.gl.EglBase10;
import io.agora.rtc.gl.EglBase14;
import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes8.dex */
public abstract class EglBase {
    public static final int EGL_OPENGL_ES2_BIT = 4;
    public static final int EGL_RECORDABLE_ANDROID = 12610;
    public static final Object lock = new Object();
    public static final int[] CONFIG_PLAIN = {R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_course_tags_bg, 4, R2.drawable.shape_coupon_record_bg};
    public static final int[] CONFIG_RGBA = {R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_color22_corner12, 8, R2.drawable.shape_course_tags_bg, 4, R2.drawable.shape_coupon_record_bg};
    public static final int[] CONFIG_PIXEL_BUFFER = {R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_course_tags_bg, 4, R2.drawable.shape_computer_statistics_top_bg, 1, R2.drawable.shape_coupon_record_bg};
    public static final int[] CONFIG_PIXEL_RGBA_BUFFER = {R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_color22_corner12, 8, R2.drawable.shape_course_tags_bg, 4, R2.drawable.shape_computer_statistics_top_bg, 1, R2.drawable.shape_coupon_record_bg};
    public static final int[] CONFIG_RECORDABLE = {R2.drawable.shape_color2a_bottom_corner12, 8, R2.drawable.shape_color22_top_corner12, 8, R2.drawable.shape_color22_normal, 8, R2.drawable.shape_course_tags_bg, 4, 12610, 1, R2.drawable.shape_coupon_record_bg};

    public interface Context {
        long getNativeEglContext();
    }

    public static EglBase create(Context sharedContext, int[] configAttributes) {
        return (EglBase14.isEGL14Supported() && (sharedContext == null || (sharedContext instanceof EglBase14.Context))) ? new EglBase14((EglBase14.Context) sharedContext, configAttributes) : new EglBase10((EglBase10.Context) sharedContext, configAttributes);
    }

    public static EglBase createEgl10(int[] configAttributes) {
        return new EglBase10(null, configAttributes);
    }

    public static EglBase createEgl14(int[] configAttributes) {
        return new EglBase14(null, configAttributes);
    }

    public abstract void createDummyPbufferSurface();

    public abstract void createPbufferSurface(int width, int height);

    public abstract void createSurface(SurfaceTexture surfaceTexture);

    public abstract void createSurface(Surface surface);

    public abstract void detachCurrent();

    public abstract Context getEglBaseContext();

    public abstract boolean hasSurface();

    public abstract void makeCurrent();

    public abstract void release();

    public abstract void releaseSurface();

    public abstract int surfaceHeight();

    public abstract int surfaceWidth();

    public abstract void swapBuffers();

    public abstract void swapBuffers(long presentationTimeStampNs);

    public static EglBase create() {
        return create(null, CONFIG_PLAIN);
    }

    public static EglBase createEgl10(EGLContext sharedContext, int[] configAttributes) {
        return new EglBase10(new EglBase10.Context(sharedContext), configAttributes);
    }

    public static EglBase createEgl14(android.opengl.EGLContext sharedContext, int[] configAttributes) {
        return new EglBase14(new EglBase14.Context(sharedContext), configAttributes);
    }

    public static EglBase create(Context sharedContext) {
        return create(sharedContext, CONFIG_PLAIN);
    }
}
