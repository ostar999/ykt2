package com.tencent.live2.impl;

import android.content.Context;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.TextureView;
import cn.hutool.core.text.StrPool;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.f;
import com.tencent.liteav.basic.util.h;
import com.tencent.liteav.p;
import com.tencent.live2.V2TXLiveDef;
import com.tencent.live2.V2TXLivePlayer;
import com.tencent.live2.V2TXLivePlayerObserver;
import com.tencent.live2.impl.a;
import com.tencent.rtmp.ui.TXCloudVideoView;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes6.dex */
public class V2TXLivePlayerImpl extends V2TXLivePlayer {
    private static final String TAG = "V2-TXLivePlayerImpl";
    private V2TXLiveDef.V2TXLiveBufferType mBufferType;
    private float mCacheMaxTime;
    private float mCacheMinTime;
    private Context mContext;
    private boolean mEnableCustomRendering;
    private boolean mEnableDebugView;
    private boolean mEnableReceiveSEI;
    private V2TXLivePlayer mInnerPlayer;
    private boolean mIsPauseAudio;
    private boolean mIsPauseVideo;
    private V2TXLiveDef.V2TXLivePixelFormat mPixelFormat;
    private V2TXLivePlayerObserver mPlayerObserver;
    private HashMap<String, Object> mPropertyMap;
    private V2TXLiveDef.V2TXLiveFillMode mRenderFillMode;
    private V2TXLiveDef.V2TXLiveRotation mRenderRotation;
    private int mSEIPayLoadType;
    private Surface mSurface;
    private a.C0343a mSurfaceSize;
    private Object mView;
    private a.b mAsyncState = a.b.TXLiveAsyncState_None;
    private f mMainHandler = new f(Looper.getMainLooper());
    private int mVolumeIntervals = -1;
    private int mPlayoutVolume = -1;

    static {
        h.d();
    }

    public V2TXLivePlayerImpl(Context context) {
        apiLog("create: context-" + context);
        this.mContext = context.getApplicationContext();
        this.mPropertyMap = new HashMap<>();
    }

    private void apiError(String str) {
        TXCLog.e(TAG, "v2_api_player(" + hashCode() + ") " + str);
    }

    private void apiLog(String str) {
        TXCLog.i(TAG, "v2_api_player(" + hashCode() + ") " + str);
    }

    private void runOnMainThreadAsync(Runnable runnable) {
        this.mMainHandler.post(runnable);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setupParams() {
        V2TXLivePlayer v2TXLivePlayer = this.mInnerPlayer;
        if (v2TXLivePlayer == null) {
            return;
        }
        v2TXLivePlayer.setObserver(this.mPlayerObserver);
        v2TXLivePlayer.enableObserveVideoFrame(this.mEnableCustomRendering, this.mPixelFormat, this.mBufferType);
        v2TXLivePlayer.enableReceiveSeiMessage(this.mEnableReceiveSEI, this.mSEIPayLoadType);
        Object obj = this.mView;
        if (obj != null) {
            if (obj instanceof TXCloudVideoView) {
                v2TXLivePlayer.setRenderView((TXCloudVideoView) obj);
            } else if (obj instanceof TextureView) {
                v2TXLivePlayer.setRenderView((TextureView) obj);
            } else if (obj instanceof SurfaceView) {
                v2TXLivePlayer.setRenderView((SurfaceView) obj);
            }
        }
        V2TXLiveDef.V2TXLiveFillMode v2TXLiveFillMode = this.mRenderFillMode;
        if (v2TXLiveFillMode != null) {
            v2TXLivePlayer.setRenderFillMode(v2TXLiveFillMode);
        }
        V2TXLiveDef.V2TXLiveRotation v2TXLiveRotation = this.mRenderRotation;
        if (v2TXLiveRotation != null) {
            v2TXLivePlayer.setRenderRotation(v2TXLiveRotation);
        }
        if (this.mIsPauseAudio) {
            v2TXLivePlayer.pauseAudio();
        } else {
            v2TXLivePlayer.resumeAudio();
        }
        if (this.mIsPauseVideo) {
            v2TXLivePlayer.pauseVideo();
        } else {
            v2TXLivePlayer.resumeVideo();
        }
        float f2 = this.mCacheMinTime;
        if (f2 > 0.0f) {
            float f3 = this.mCacheMaxTime;
            if (f3 > 0.0f) {
                v2TXLivePlayer.setCacheParams(f2, f3);
            }
        }
        int i2 = this.mPlayoutVolume;
        if (i2 > 0) {
            v2TXLivePlayer.setPlayoutVolume(i2);
        }
        int i3 = this.mVolumeIntervals;
        if (i3 > 0) {
            v2TXLivePlayer.enableVolumeEvaluation(i3);
        }
        v2TXLivePlayer.showDebugView(this.mEnableDebugView);
        Surface surface = this.mSurface;
        if (surface != null) {
            v2TXLivePlayer.setProperty("setSurface", surface);
        }
        a.C0343a c0343a = this.mSurfaceSize;
        if (c0343a != null) {
            v2TXLivePlayer.setProperty("setSurfaceSize", c0343a);
        }
        for (Map.Entry<String, Object> entry : this.mPropertyMap.entrySet()) {
            this.mInnerPlayer.setProperty(entry.getKey(), entry.getValue());
        }
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int enableObserveVideoFrame(final boolean z2, final V2TXLiveDef.V2TXLivePixelFormat v2TXLivePixelFormat, final V2TXLiveDef.V2TXLiveBufferType v2TXLiveBufferType) {
        apiLog("enableCustomRendering: enable-" + z2 + " pixelFormat-" + v2TXLivePixelFormat + " bufferType-" + v2TXLiveBufferType);
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.19
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mEnableCustomRendering = z2;
                V2TXLivePlayerImpl.this.mPixelFormat = v2TXLivePixelFormat;
                V2TXLivePlayerImpl.this.mBufferType = v2TXLiveBufferType;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.enableObserveVideoFrame(z2, v2TXLivePixelFormat, v2TXLiveBufferType);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int enableReceiveSeiMessage(final boolean z2, final int i2) {
        apiLog("enableReceiveSeiMessage: enable-" + z2 + ", payloadType-" + i2);
        if (i2 == 5 || i2 == 242) {
            runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.3
                @Override // java.lang.Runnable
                public void run() {
                    V2TXLivePlayerImpl.this.mEnableReceiveSEI = z2;
                    V2TXLivePlayerImpl.this.mSEIPayLoadType = i2;
                    if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                        V2TXLivePlayerImpl.this.mInnerPlayer.enableReceiveSeiMessage(z2, i2);
                    }
                }
            });
            return 0;
        }
        apiError("enableReceiveSeiMessage payloadType invalid " + i2);
        return -2;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int enableVolumeEvaluation(final int i2) {
        apiLog("enableVolumeEvaluation: intervalMs-" + i2);
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.17
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mVolumeIntervals = i2;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.enableVolumeEvaluation(i2);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int isPlaying() {
        a.b bVar = this.mAsyncState;
        if (bVar == a.b.TXLiveAsyncState_None) {
            V2TXLivePlayer v2TXLivePlayer = this.mInnerPlayer;
            if (v2TXLivePlayer != null) {
                return v2TXLivePlayer.isPlaying();
            }
            return 0;
        }
        if (bVar == a.b.TXLiveAsyncState_Starting) {
            return 1;
        }
        a.b bVar2 = a.b.TXLiveAsyncState_Stopping;
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int pauseAudio() {
        apiLog("pauseAudio: ");
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.11
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mIsPauseAudio = true;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.pauseAudio();
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int pauseVideo() {
        apiLog("pauseVideo: ");
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.13
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mIsPauseVideo = true;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.pauseVideo();
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int resumeAudio() {
        apiLog("resumeAudio: ");
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.12
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mIsPauseAudio = false;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.resumeAudio();
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int resumeVideo() {
        apiLog("resumeVideo: ");
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.14
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mIsPauseVideo = false;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.resumeVideo();
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setCacheParams(final float f2, final float f3) {
        apiLog("setCacheParams: minTime-" + f2 + " maxTime-" + f3);
        if (f2 <= 0.0f || f3 <= 0.0f) {
            apiError("set cache param failed, invalid cache params.");
            return -2;
        }
        if (isPlaying() == 1) {
            apiError("set cache param failed, cant's set param when playing.");
            return -3;
        }
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.16
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mCacheMaxTime = f3;
                V2TXLivePlayerImpl.this.mCacheMinTime = f2;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.setCacheParams(f2, f3);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public void setObserver(final V2TXLivePlayerObserver v2TXLivePlayerObserver) {
        apiLog("setObserver: observer-" + v2TXLivePlayerObserver);
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.1
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mPlayerObserver = v2TXLivePlayerObserver;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.setObserver(v2TXLivePlayerObserver);
                }
            }
        });
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setPlayoutVolume(final int i2) {
        apiLog("setPlayoutVolume: volume-" + i2);
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.15
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mPlayoutVolume = i2;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.setPlayoutVolume(i2);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setProperty(final String str, final Object obj) {
        apiLog("setProperty: key-" + str + " value-" + obj);
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.2
            @Override // java.lang.Runnable
            public void run() {
                String str2 = str;
                str2.hashCode();
                switch (str2) {
                    case "setPlayURLType":
                    case "enableRecvSEIMessage":
                        V2TXLivePlayerImpl.this.mPropertyMap.put(str, obj);
                        break;
                    case "setSurfaceSize":
                        Object obj2 = obj;
                        if (obj2 != null) {
                            if (obj2 instanceof a.C0343a) {
                                V2TXLivePlayerImpl.this.mSurfaceSize = (a.C0343a) obj2;
                                break;
                            }
                        } else {
                            V2TXLivePlayerImpl.this.mSurfaceSize = null;
                            break;
                        }
                        break;
                    case "setSurface":
                        Object obj3 = obj;
                        if (obj3 != null) {
                            if (obj3 instanceof Surface) {
                                V2TXLivePlayerImpl.this.mSurface = (Surface) obj3;
                                break;
                            }
                        } else {
                            V2TXLivePlayerImpl.this.mSurface = null;
                            break;
                        }
                        break;
                }
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.setProperty(str, obj);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setRenderFillMode(final V2TXLiveDef.V2TXLiveFillMode v2TXLiveFillMode) {
        apiLog("setRenderFillMode: mode-" + v2TXLiveFillMode);
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.8
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mRenderFillMode = v2TXLiveFillMode;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.setRenderFillMode(v2TXLiveFillMode);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setRenderRotation(final V2TXLiveDef.V2TXLiveRotation v2TXLiveRotation) {
        apiLog("setRenderRotation: rotation-" + v2TXLiveRotation);
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.7
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mRenderRotation = v2TXLiveRotation;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.setRenderRotation(v2TXLiveRotation);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setRenderView(final TXCloudVideoView tXCloudVideoView) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("setTXCloudVideoView: view-");
        if (tXCloudVideoView != null) {
            str = tXCloudVideoView.hashCode() + "";
        } else {
            str = "null";
        }
        sb.append(str);
        apiLog(sb.toString());
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.4
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mView = tXCloudVideoView;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.setRenderView(tXCloudVideoView);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public void showDebugView(final boolean z2) {
        apiLog("showDebugView: enable-" + z2);
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.20
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mEnableDebugView = z2;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.showDebugView(z2);
                }
            }
        });
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int snapshot() {
        if (isPlaying() == 0) {
            apiError("snapshot: snapshot is not allowed before the player starts playing.");
            return -3;
        }
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.18
            @Override // java.lang.Runnable
            public void run() {
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.snapshot();
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int startPlay(final String str) {
        int iA;
        apiLog("startPlay url:" + str);
        if (TextUtils.isEmpty(str)) {
            TXCLog.e(TAG, "start play fail, url invalid:" + str);
            return -2;
        }
        final a.c playerType = V2TXLiveUtils.parsePlayerType(str);
        if (playerType == a.c.V2TXLiveProtocolTypeTRTC || playerType == a.c.V2TXLiveProtocolTypeROOM) {
            if (!p.a(str)) {
                apiError("start play fail. invalid param. [url:" + str + StrPool.BRACKET_END);
                return -2;
            }
        } else if (playerType != a.c.V2TXLiveProtocolTypeWEBRTC && (iA = com.tencent.live2.a.a.a(str)) != 0) {
            return iA;
        }
        this.mAsyncState = a.b.TXLiveAsyncState_Starting;
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.9
            @Override // java.lang.Runnable
            public void run() {
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null && V2TXLivePlayerImpl.this.mInnerPlayer.isPlaying() == 1) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.stopPlay();
                }
                V2TXLivePlayerImpl v2TXLivePlayerImpl = V2TXLivePlayerImpl.this;
                v2TXLivePlayerImpl.mInnerPlayer = p.a(v2TXLivePlayerImpl.mContext, V2TXLivePlayerImpl.this, playerType);
                V2TXLivePlayerImpl.this.setupParams();
                V2TXLivePlayerImpl.this.mInnerPlayer.startPlay(str);
                V2TXLivePlayerImpl.this.mAsyncState = a.b.TXLiveAsyncState_None;
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int stopPlay() {
        apiLog("stopPlay");
        this.mAsyncState = a.b.TXLiveAsyncState_Stopping;
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.10
            @Override // java.lang.Runnable
            public void run() {
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mAsyncState = a.b.TXLiveAsyncState_None;
                    V2TXLivePlayerImpl.this.mInnerPlayer.stopPlay();
                    V2TXLivePlayerImpl.this.mInnerPlayer = null;
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setRenderView(final TextureView textureView) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("setTextureView: view-");
        if (textureView != null) {
            str = textureView.hashCode() + "";
        } else {
            str = "null";
        }
        sb.append(str);
        apiLog(sb.toString());
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.5
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mView = textureView;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.setRenderView(textureView);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setRenderView(final SurfaceView surfaceView) {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append("setSurfaceView: view-");
        if (surfaceView != null) {
            str = surfaceView.hashCode() + "";
        } else {
            str = "null";
        }
        sb.append(str);
        apiLog(sb.toString());
        runOnMainThreadAsync(new Runnable() { // from class: com.tencent.live2.impl.V2TXLivePlayerImpl.6
            @Override // java.lang.Runnable
            public void run() {
                V2TXLivePlayerImpl.this.mView = surfaceView;
                if (V2TXLivePlayerImpl.this.mInnerPlayer != null) {
                    V2TXLivePlayerImpl.this.mInnerPlayer.setRenderView(surfaceView);
                }
            }
        });
        return 0;
    }
}
