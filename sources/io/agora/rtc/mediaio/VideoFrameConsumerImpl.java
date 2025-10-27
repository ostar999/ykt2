package io.agora.rtc.mediaio;

import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.opengl.GLException;
import java.nio.ByteBuffer;

/* loaded from: classes8.dex */
public class VideoFrameConsumerImpl implements IVideoFrameConsumer {
    private long mCaptureHandle;

    public VideoFrameConsumerImpl(long nativeHandle) {
        this.mCaptureHandle = nativeHandle;
    }

    @Override // io.agora.rtc.mediaio.IVideoFrameConsumer
    public void consumeByteArrayFrame(byte[] data, int format, int width, int height, int rotation, long ts) {
        provideByteArrayFrame(this.mCaptureHandle, data, format, width, height, rotation, ts);
    }

    @Override // io.agora.rtc.mediaio.IVideoFrameConsumer
    public void consumeByteBufferFrame(ByteBuffer buffer, int format, int width, int height, int rotation, long ts) {
        provideByteBufferFrame(this.mCaptureHandle, buffer, format, width, height, rotation, ts);
    }

    @Override // io.agora.rtc.mediaio.IVideoFrameConsumer
    public void consumeTextureFrame(int texId, int format, int width, int height, int rotation, long ts, float[] matrix) {
        EGLContext eGLContextEglGetCurrentContext = EGL14.eglGetCurrentContext();
        int iEglGetError = EGL14.eglGetError();
        if (iEglGetError == 12288) {
            provideTextureFrame(this.mCaptureHandle, eGLContextEglGetCurrentContext, texId, format, width, height, rotation, ts, matrix);
            return;
        }
        throw new GLException(iEglGetError, "eglError: " + iEglGetError);
    }

    public native void provideByteArrayFrame(long nativeHandle, byte[] data, int format, int width, int height, int rotation, long ts);

    public native void provideByteBufferFrame(long nativeHandle, ByteBuffer buffer, int format, int width, int height, int rotation, long ts);

    public native void provideTextureFrame(long nativeHandle, Object sharedContext, int texId, int format, int width, int height, int rotation, long ts, float[] matrix);
}
