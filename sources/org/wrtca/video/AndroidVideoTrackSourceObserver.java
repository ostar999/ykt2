package org.wrtca.video;

import org.wrtca.api.VideoCapturer;
import org.wrtca.api.VideoFrame;
import org.wrtca.jni.JNINamespace;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class AndroidVideoTrackSourceObserver implements VideoCapturer.CapturerObserver {
    private final long nativeSource;

    public AndroidVideoTrackSourceObserver(long j2) {
        this.nativeSource = j2;
    }

    private static native void nativeCapturerStarted(long j2, boolean z2);

    private static native void nativeCapturerStopped(long j2);

    private static native void nativeOnByteBufferFrameCaptured(long j2, byte[] bArr, int i2, int i3, int i4, int i5, long j3);

    private static native void nativeOnFrameCaptured(long j2, int i2, int i3, int i4, long j3, VideoFrame.Buffer buffer);

    private static native void nativeOnTextureFrameCaptured(long j2, int i2, int i3, int i4, float[] fArr, int i5, long j3);

    @Override // org.wrtca.api.VideoCapturer.CapturerObserver
    public void onByteBufferFrameCaptured(byte[] bArr, int i2, int i3, int i4, long j2) {
        nativeOnByteBufferFrameCaptured(this.nativeSource, bArr, bArr.length, i2, i3, i4, j2);
    }

    @Override // org.wrtca.api.VideoCapturer.CapturerObserver
    public void onCapturerStarted(boolean z2) {
        nativeCapturerStarted(this.nativeSource, z2);
    }

    @Override // org.wrtca.api.VideoCapturer.CapturerObserver
    public void onCapturerStopped() {
        nativeCapturerStopped(this.nativeSource);
    }

    @Override // org.wrtca.api.VideoCapturer.CapturerObserver
    public void onFrameCaptured(VideoFrame videoFrame) {
        nativeOnFrameCaptured(this.nativeSource, videoFrame.getBuffer().getWidth(), videoFrame.getBuffer().getHeight(), videoFrame.getRotation(), videoFrame.getTimestampNs(), videoFrame.getBuffer());
    }

    @Override // org.wrtca.api.VideoCapturer.CapturerObserver
    public void onTextureFrameCaptured(int i2, int i3, int i4, float[] fArr, int i5, long j2) {
        nativeOnTextureFrameCaptured(this.nativeSource, i2, i3, i4, fArr, i5, j2);
    }
}
