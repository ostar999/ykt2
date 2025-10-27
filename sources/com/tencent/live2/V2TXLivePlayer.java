package com.tencent.live2;

import android.view.SurfaceView;
import android.view.TextureView;
import com.tencent.live2.V2TXLiveDef;
import com.tencent.rtmp.ui.TXCloudVideoView;

/* loaded from: classes6.dex */
public abstract class V2TXLivePlayer {
    public abstract int enableObserveVideoFrame(boolean z2, V2TXLiveDef.V2TXLivePixelFormat v2TXLivePixelFormat, V2TXLiveDef.V2TXLiveBufferType v2TXLiveBufferType);

    public abstract int enableReceiveSeiMessage(boolean z2, int i2);

    public abstract int enableVolumeEvaluation(int i2);

    public abstract int isPlaying();

    public abstract int pauseAudio();

    public abstract int pauseVideo();

    public abstract int resumeAudio();

    public abstract int resumeVideo();

    public abstract int setCacheParams(float f2, float f3);

    public abstract void setObserver(V2TXLivePlayerObserver v2TXLivePlayerObserver);

    public abstract int setPlayoutVolume(int i2);

    public abstract int setProperty(String str, Object obj);

    public abstract int setRenderFillMode(V2TXLiveDef.V2TXLiveFillMode v2TXLiveFillMode);

    public abstract int setRenderRotation(V2TXLiveDef.V2TXLiveRotation v2TXLiveRotation);

    public abstract int setRenderView(SurfaceView surfaceView);

    public abstract int setRenderView(TextureView textureView);

    public abstract int setRenderView(TXCloudVideoView tXCloudVideoView);

    public abstract void showDebugView(boolean z2);

    public abstract int snapshot();

    public abstract int startPlay(String str);

    public abstract int stopPlay();
}
