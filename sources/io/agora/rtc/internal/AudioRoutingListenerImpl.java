package io.agora.rtc.internal;

/* loaded from: classes8.dex */
class AudioRoutingListenerImpl implements AudioRoutingListener {
    private long mAudioRoutingNativeHandle;

    public AudioRoutingListenerImpl(long nativeHandle) {
        this.mAudioRoutingNativeHandle = nativeHandle;
    }

    public native void nativeAudioRoutingChanged(long nativeHandle, int routing);

    public native void nativeAudioRoutingError(long nativeHandle, int errCode);

    @Override // io.agora.rtc.internal.AudioRoutingListener
    public void onAudioRoutingChanged(int routing) {
        synchronized (this) {
            nativeAudioRoutingChanged(this.mAudioRoutingNativeHandle, routing);
        }
    }

    @Override // io.agora.rtc.internal.AudioRoutingListener
    public void onAudioRoutingDestroyed() {
        synchronized (this) {
            this.mAudioRoutingNativeHandle = 0L;
        }
    }

    @Override // io.agora.rtc.internal.AudioRoutingListener
    public void onAudioRoutingError(int errCode) {
        synchronized (this) {
            nativeAudioRoutingError(this.mAudioRoutingNativeHandle, errCode);
        }
    }
}
