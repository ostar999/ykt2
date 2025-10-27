package com.tencent.liteav.trtc.wrapper;

import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;

/* loaded from: classes6.dex */
public class TRTCAudioFrameListenerJNI implements TRTCCloudListener.TRTCAudioFrameListener {
    private long mAudioFrameCallback;

    private native void nativeOnCapturedRawAudioFrame(long j2, TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame);

    private native void nativeOnLocalProcessedAudioFrame(long j2, TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame);

    private native void nativeOnMixedPlayAudioFrame(long j2, TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame);

    private native void nativeOnRemoteUserAudioFrame(long j2, TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame, String str);

    @Override // com.tencent.trtc.TRTCCloudListener.TRTCAudioFrameListener
    public void onCapturedRawAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame) {
        synchronized (this) {
            nativeOnCapturedRawAudioFrame(this.mAudioFrameCallback, tRTCAudioFrame);
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener.TRTCAudioFrameListener
    public void onLocalProcessedAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame) {
        synchronized (this) {
            nativeOnLocalProcessedAudioFrame(this.mAudioFrameCallback, tRTCAudioFrame);
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener.TRTCAudioFrameListener
    public void onMixedAllAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame) {
    }

    @Override // com.tencent.trtc.TRTCCloudListener.TRTCAudioFrameListener
    public void onMixedPlayAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame) {
        synchronized (this) {
            nativeOnMixedPlayAudioFrame(this.mAudioFrameCallback, tRTCAudioFrame);
        }
    }

    @Override // com.tencent.trtc.TRTCCloudListener.TRTCAudioFrameListener
    public void onRemoteUserAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame, String str) {
        synchronized (this) {
            nativeOnRemoteUserAudioFrame(this.mAudioFrameCallback, tRTCAudioFrame, str);
        }
    }

    public void setCallback(long j2) {
        synchronized (this) {
            this.mAudioFrameCallback = j2;
        }
    }
}
