package org.wrtca.video;

import org.wrtca.api.VideoFrame;
import org.wrtca.jni.CalledByNative;
import org.wrtca.jni.JNINamespace;
import org.wrtca.video.CameraSession;
import org.wrtca.video.RtcCameraRTSPSession;

@JNINamespace("webrtc::jni")
/* loaded from: classes9.dex */
public class RtcCameraRTSP {
    private RtcCameraRTSPSession mCameraSession;
    private CameraSession.Events mEvents;
    private RtcCameraRTSPSession.RTSPCameraFormat mRTSPCameraFormat;
    private String mUrl;

    public RtcCameraRTSP(String str, RtcCameraRTSPSession.RTSPCameraFormat rTSPCameraFormat, RtcCameraRTSPSession rtcCameraRTSPSession) {
        this.mRTSPCameraFormat = rTSPCameraFormat;
        this.mUrl = str;
        this.mCameraSession = rtcCameraRTSPSession;
        this.mEvents = rtcCameraRTSPSession.getEvents();
        int iOrdinal = this.mRTSPCameraFormat.mOutputFormat.ordinal();
        RtcCameraRTSPSession.RTSPCameraFormat rTSPCameraFormat2 = this.mRTSPCameraFormat;
        nativeInitCamera(str, iOrdinal, rTSPCameraFormat2.mWidth, rTSPCameraFormat2.mHeight, rTSPCameraFormat2.mFrameRate);
        startCapturing();
    }

    private native int nativeInitCamera(String str, int i2, int i3, int i4, int i5);

    private native int nativeStartCapturing();

    private native int nativeStopCapturing();

    @CalledByNative
    public void onPreviewData(WrappedNativeI420Buffer wrappedNativeI420Buffer, int i2, long j2) {
        if (this.mCameraSession == null || this.mEvents == null) {
            return;
        }
        this.mEvents.onFrameCaptured(this.mCameraSession, new VideoFrame(wrappedNativeI420Buffer, i2, j2), d.b.s());
    }

    public int startCapturing() {
        return nativeStartCapturing();
    }

    public int stopCapturing() {
        return nativeStopCapturing();
    }
}
