package org.wrtca.api;

import android.content.Context;

/* loaded from: classes9.dex */
public interface VideoCapturer {

    public interface CapturerObserver {
        @Deprecated
        void onByteBufferFrameCaptured(byte[] bArr, int i2, int i3, int i4, long j2);

        void onCapturerStarted(boolean z2);

        void onCapturerStopped();

        void onFrameCaptured(VideoFrame videoFrame);

        @Deprecated
        void onTextureFrameCaptured(int i2, int i3, int i4, float[] fArr, int i5, long j2);
    }

    void changeCaptureFormat(int i2, int i3, int i4);

    void dispose();

    void initialize(SurfaceTextureHelper surfaceTextureHelper, Context context, CapturerObserver capturerObserver);

    boolean isScreencast();

    void startCapture(int i2, int i3, int i4);

    void stopCapture() throws InterruptedException;
}
