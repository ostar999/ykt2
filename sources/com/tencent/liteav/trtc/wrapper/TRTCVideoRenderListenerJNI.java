package com.tencent.liteav.trtc.wrapper;

import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;

/* loaded from: classes6.dex */
public class TRTCVideoRenderListenerJNI implements TRTCCloudListener.TRTCVideoRenderListener {
    private long mVideoRenderCallback;

    private native void nativeOnRenderVideoFrame(long j2, String str, int i2, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame);

    @Override // com.tencent.trtc.TRTCCloudListener.TRTCVideoRenderListener
    public void onRenderVideoFrame(String str, int i2, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame) {
        synchronized (this) {
            nativeOnRenderVideoFrame(this.mVideoRenderCallback, str, i2, tRTCVideoFrame);
        }
    }

    public void setCallback(long j2) {
        synchronized (this) {
            this.mVideoRenderCallback = j2;
        }
    }
}
