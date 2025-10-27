package com.tencent.liteav.trtc.wrapper;

import com.tencent.liteav.audio.TXAudioEffectManager;

/* loaded from: classes6.dex */
public class TXMusicPlayObserverJNI implements TXAudioEffectManager.TXMusicPlayObserver {
    private long mMusicPlayObserver;

    private native void nativeOnComplete(long j2, int i2, int i3);

    private native void nativeOnPlayProgress(long j2, int i2, long j3, long j4);

    private native void nativeOnStart(long j2, int i2, int i3);

    @Override // com.tencent.liteav.audio.TXAudioEffectManager.TXMusicPlayObserver
    public void onComplete(int i2, int i3) {
        synchronized (this) {
            nativeOnComplete(this.mMusicPlayObserver, i2, i3);
        }
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager.TXMusicPlayObserver
    public void onPlayProgress(int i2, long j2, long j3) {
        synchronized (this) {
            nativeOnPlayProgress(this.mMusicPlayObserver, i2, j2, j3);
        }
    }

    @Override // com.tencent.liteav.audio.TXAudioEffectManager.TXMusicPlayObserver
    public void onStart(int i2, int i3) {
        synchronized (this) {
            nativeOnStart(this.mMusicPlayObserver, i2, i3);
        }
    }

    public void setCallback(long j2) {
        synchronized (this) {
            this.mMusicPlayObserver = j2;
        }
    }
}
