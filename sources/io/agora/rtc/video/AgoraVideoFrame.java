package io.agora.rtc.video;

import javax.microedition.khronos.egl.EGLContext;

/* loaded from: classes8.dex */
public class AgoraVideoFrame {
    public static final int BUFFER_TYPE_ARRAY = 2;
    public static final int BUFFER_TYPE_BUFFER = 1;
    public static final int BUFFER_TYPE_NONE = -1;
    public static final int BUFFER_TYPE_TEXTURE = 3;
    public static final int FORMAT_I420 = 1;
    public static final int FORMAT_NONE = -1;
    public static final int FORMAT_NV21 = 3;
    public static final int FORMAT_RGBA = 4;
    public static final int FORMAT_TEXTURE_2D = 10;
    public static final int FORMAT_TEXTURE_OES = 11;
    public int format = 10;
    public long timeStamp = 0;
    public int stride = 0;
    public int height = 0;
    public int textureID = 0;
    public boolean syncMode = true;
    public float[] transform = null;
    public EGLContext eglContext11 = null;
    public android.opengl.EGLContext eglContext14 = null;
    public byte[] buf = null;
    public int cropLeft = 0;
    public int cropTop = 0;
    public int cropRight = 0;
    public int cropBottom = 0;
    public int rotation = 0;
}
