package com.tencent.liteav.trtc.wrapper;

import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;

/* loaded from: classes6.dex */
public class TRTCVideoProcessListenerJNI implements TRTCCloudListener.TRTCVideoFrameListener {
    private long mVideoProcessCallback;

    private native int nativeOnProcessVideoFrame(long j2, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame2);

    @Override // com.tencent.trtc.TRTCCloudListener.TRTCVideoFrameListener
    public void onGLContextCreated() {
    }

    @Override // com.tencent.trtc.TRTCCloudListener.TRTCVideoFrameListener
    public void onGLContextDestory() {
    }

    @Override // com.tencent.trtc.TRTCCloudListener.TRTCVideoFrameListener
    public int onProcessVideoFrame(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame2) {
        int iNativeOnProcessVideoFrame;
        synchronized (this) {
            iNativeOnProcessVideoFrame = nativeOnProcessVideoFrame(this.mVideoProcessCallback, tRTCVideoFrame, tRTCVideoFrame2);
        }
        return iNativeOnProcessVideoFrame;
    }

    public void setCallback(long j2) {
        synchronized (this) {
            this.mVideoProcessCallback = j2;
        }
    }
}
