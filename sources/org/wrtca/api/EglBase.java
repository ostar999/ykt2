package org.wrtca.api;

import android.graphics.SurfaceTexture;
import android.view.Surface;
import com.yikaobang.yixue.R2;

/* loaded from: classes9.dex */
public interface EglBase {
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

    void createDummyPbufferSurface();

    void createPbufferSurface(int i2, int i3);

    void createSurface(SurfaceTexture surfaceTexture);

    void createSurface(Surface surface);

    void detachCurrent();

    Context getEglBaseContext();

    boolean hasSurface();

    void makeCurrent();

    void release();

    void releaseSurface();

    int surfaceHeight();

    int surfaceWidth();

    void swapBuffers();

    void swapBuffers(long j2);
}
