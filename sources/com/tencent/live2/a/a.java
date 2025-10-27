package com.tencent.live2.a;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import com.just.agentweb.DefaultWebClient;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCBuild;
import com.tencent.liteav.basic.util.h;
import com.tencent.live2.V2TXLiveDef;
import com.tencent.live2.V2TXLivePlayer;
import com.tencent.live2.V2TXLivePlayerObserver;
import com.tencent.live2.impl.V2TXLiveUtils;
import com.tencent.live2.impl.a;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import java.util.Locale;
import javax.microedition.khronos.egl.EGLContext;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class a extends V2TXLivePlayer implements SurfaceHolder.Callback, TextureView.SurfaceTextureListener, ITXLivePlayListener, TXLivePlayer.ITXAudioRawDataListener, TXLivePlayer.ITXAudioVolumeEvaluationListener, TXLivePlayer.ITXLivePlayVideoRenderListener, TXLivePlayer.ITXVideoRawDataListener {

    /* renamed from: a, reason: collision with root package name */
    private Context f20378a;

    /* renamed from: b, reason: collision with root package name */
    private V2TXLivePlayer f20379b;

    /* renamed from: c, reason: collision with root package name */
    private TXLivePlayer f20380c;

    /* renamed from: d, reason: collision with root package name */
    private TXLivePlayConfig f20381d;

    /* renamed from: e, reason: collision with root package name */
    private boolean f20382e;

    /* renamed from: f, reason: collision with root package name */
    private boolean f20383f;

    /* renamed from: g, reason: collision with root package name */
    private boolean f20384g;

    /* renamed from: h, reason: collision with root package name */
    private boolean f20385h;

    /* renamed from: i, reason: collision with root package name */
    private V2TXLiveDef.V2TXLivePlayerStatistics f20386i;

    /* renamed from: j, reason: collision with root package name */
    private V2TXLiveDef.V2TXLivePlayStatus f20387j;

    /* renamed from: k, reason: collision with root package name */
    private V2TXLiveDef.V2TXLivePlayStatus f20388k;

    /* renamed from: l, reason: collision with root package name */
    private boolean f20389l;

    /* renamed from: m, reason: collision with root package name */
    private V2TXLivePlayerObserver f20390m;

    /* renamed from: n, reason: collision with root package name */
    private boolean f20391n;

    /* renamed from: o, reason: collision with root package name */
    private TXCloudVideoView f20392o;

    /* renamed from: p, reason: collision with root package name */
    private TextureView f20393p;

    /* renamed from: q, reason: collision with root package name */
    private SurfaceView f20394q;

    /* renamed from: r, reason: collision with root package name */
    private int f20395r;

    /* renamed from: s, reason: collision with root package name */
    private int f20396s;

    public a(V2TXLivePlayer v2TXLivePlayer, Context context) {
        V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus = V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusStopped;
        this.f20387j = v2TXLivePlayStatus;
        this.f20388k = v2TXLivePlayStatus;
        this.f20395r = -1;
        this.f20378a = context.getApplicationContext();
        this.f20379b = v2TXLivePlayer;
        this.f20386i = new V2TXLiveDef.V2TXLivePlayerStatistics();
        TXLivePlayer tXLivePlayer = new TXLivePlayer(this.f20378a);
        this.f20380c = tXLivePlayer;
        tXLivePlayer.setPlayListener(this);
        TXLivePlayConfig tXLivePlayConfig = new TXLivePlayConfig();
        this.f20381d = tXLivePlayConfig;
        tXLivePlayConfig.setConnectRetryInterval(3);
        this.f20381d.setConnectRetryCount(3);
        this.f20380c.setConfig(this.f20381d);
        this.f20380c.enableHardwareDecode(true);
        this.f20380c.setAudioVolumeEvaluationListener(this);
    }

    private void c() {
        TextureView textureView = this.f20393p;
        if (textureView != null) {
            c("unbindRenderView: unbind texture view.");
            textureView.setSurfaceTextureListener(null);
            this.f20380c.setSurface(null);
            this.f20380c.setSurfaceSize(0, 0);
        }
        SurfaceView surfaceView = this.f20394q;
        if (surfaceView != null) {
            c("unbindRenderView: unbind surface view.");
            surfaceView.getHolder().removeCallback(this);
            this.f20380c.setSurface(null);
            this.f20380c.setSurfaceSize(0, 0);
        }
    }

    private void d(String str) {
        TXCLog.e("V2-TXRTMPPlayerImpl", "v2_api_rtmp_player(" + this + ") " + str);
    }

    private void e(String str) {
        TXCLog.w("V2-TXRTMPPlayerImpl", "v2_api_rtmp_player(" + this + ") " + str);
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int enableObserveVideoFrame(boolean z2, V2TXLiveDef.V2TXLivePixelFormat v2TXLivePixelFormat, V2TXLiveDef.V2TXLiveBufferType v2TXLiveBufferType) {
        c("setVideoFrameObserver: enable-" + z2 + " format-" + v2TXLivePixelFormat + " type-" + v2TXLiveBufferType);
        if (v2TXLivePixelFormat == V2TXLiveDef.V2TXLivePixelFormat.V2TXLivePixelFormatI420 && v2TXLiveBufferType == V2TXLiveDef.V2TXLiveBufferType.V2TXLiveBufferTypeByteArray) {
            c("setVideoFrameObserver: use I420 array render.");
            this.f20380c.setVideoRenderListener(null, null);
            this.f20380c.setVideoRawDataListener(z2 ? this : null);
            return 0;
        }
        if (v2TXLivePixelFormat == V2TXLiveDef.V2TXLivePixelFormat.V2TXLivePixelFormatTexture2D && v2TXLiveBufferType == V2TXLiveDef.V2TXLiveBufferType.V2TXLiveBufferTypeTexture) {
            c("setVideoFrameObserver: use texture render.");
            this.f20380c.setVideoRawDataListener(null);
            this.f20380c.setVideoRenderListener(z2 ? this : null, null);
            return 0;
        }
        this.f20380c.setVideoRawDataListener(null);
        this.f20380c.setVideoRenderListener(null, null);
        d("setVideoFrameObserver: format or type isn't support. force clean observer. format-" + v2TXLivePixelFormat + " type-" + v2TXLiveBufferType);
        return -4;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int enableReceiveSeiMessage(boolean z2, int i2) throws JSONException {
        this.f20396s = i2;
        this.f20381d.setEnableMessage(z2);
        this.f20380c.setConfig(this.f20381d);
        this.f20380c.callExperimentalAPI(String.format(Locale.ENGLISH, "{\"api\":\"setSEIPayloadType\", \"params\": {\"payloadType\":%d}}", Integer.valueOf(i2)));
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int enableVolumeEvaluation(int i2) {
        if (i2 < 0) {
            e("enableVolumeEvaluation: invalid params.");
            i2 = 0;
        }
        this.f20380c.enableAudioVolumeEvaluation(i2);
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int isPlaying() {
        return this.f20384g ? 1 : 0;
    }

    @Override // com.tencent.rtmp.TXLivePlayer.ITXAudioRawDataListener
    public void onAudioInfoChanged(int i2, int i3, int i4) {
        c("onAudioInfoChanged: sampleRate-" + i2 + " channels-" + i3 + " bits-" + i4);
    }

    @Override // com.tencent.rtmp.TXLivePlayer.ITXAudioVolumeEvaluationListener
    public void onAudioVolumeEvaluationNotify(int i2) {
        V2TXLivePlayerObserver v2TXLivePlayerObserver = this.f20390m;
        if (v2TXLivePlayerObserver != null) {
            v2TXLivePlayerObserver.onPlayoutVolumeUpdate(this.f20379b, i2);
        }
    }

    @Override // com.tencent.rtmp.ITXLivePlayListener
    public void onNetStatus(Bundle bundle) {
        try {
            int[] iArrA = h.a();
            if (iArrA != null && iArrA.length == 2) {
                V2TXLiveDef.V2TXLivePlayerStatistics v2TXLivePlayerStatistics = this.f20386i;
                v2TXLivePlayerStatistics.appCpu = iArrA[0] / 10;
                v2TXLivePlayerStatistics.systemCpu = iArrA[1] / 10;
            }
            this.f20386i.width = bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_WIDTH);
            this.f20386i.height = bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_HEIGHT);
            this.f20386i.fps = bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_FPS);
            this.f20386i.videoBitrate = bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_BITRATE);
            this.f20386i.audioBitrate = bundle.getInt(TXLiveConstants.NET_STATUS_AUDIO_BITRATE);
        } catch (Exception unused) {
        }
        V2TXLivePlayerObserver v2TXLivePlayerObserver = this.f20390m;
        if (v2TXLivePlayerObserver != null) {
            v2TXLivePlayerObserver.onStatisticsUpdate(this.f20379b, this.f20386i);
        }
        if (v2TXLivePlayerObserver == null || !(v2TXLivePlayerObserver instanceof com.tencent.live2.impl.a.a)) {
            return;
        }
        ((com.tencent.live2.impl.a.a) v2TXLivePlayerObserver).a(bundle);
    }

    @Override // com.tencent.rtmp.TXLivePlayer.ITXAudioRawDataListener
    public void onPcmDataAvailable(byte[] bArr, long j2) {
    }

    @Override // com.tencent.rtmp.ITXLivePlayListener
    public void onPlayEvent(int i2, Bundle bundle) {
        c("onPlayEvent event:" + i2 + " param:" + bundle);
        V2TXLivePlayerObserver v2TXLivePlayerObserver = this.f20390m;
        if (i2 == -2301) {
            d("onPlayEvent: stop play because of disconnect.");
            a();
            V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus = V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusStopped;
            V2TXLiveDef.V2TXLiveStatusChangeReason v2TXLiveStatusChangeReason = V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonRemoteOffline;
            a(v2TXLivePlayStatus, v2TXLiveStatusChangeReason);
            b(v2TXLivePlayStatus, v2TXLiveStatusChangeReason);
        } else if (i2 != 2007) {
            if (i2 != 2012) {
                if (i2 != 2026) {
                    if (i2 != 2105) {
                        if (i2 != 2003) {
                            if (i2 == 2004 && this.f20385h) {
                                c("onPlayEvent: loading finish.");
                                this.f20385h = false;
                                if (this.f20383f) {
                                    a(V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusPlaying, V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonBufferingEnd);
                                }
                                if (this.f20382e) {
                                    b(V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusPlaying, V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonBufferingEnd);
                                }
                            }
                        } else if (!this.f20382e) {
                            c("onPlayEvent: onRecvFirstVideoFrame.");
                            this.f20382e = true;
                            b(V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusPlaying, V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonLocalStarted);
                        }
                    } else if (v2TXLivePlayerObserver != null) {
                        v2TXLivePlayerObserver.onWarning(this.f20379b, 2105, "player video block happen.", bundle == null ? new Bundle() : bundle);
                    }
                } else if (!this.f20383f) {
                    c("onPlayEvent: onRecvFirstAudioFrame.");
                    this.f20383f = true;
                    a(V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusPlaying, V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonLocalStarted);
                }
            } else if (v2TXLivePlayerObserver != null) {
                v2TXLivePlayerObserver.onReceiveSeiMessage(this.f20379b, this.f20396s, bundle.getByteArray(TXLiveConstants.EVT_GET_MSG));
            }
        } else if (!this.f20385h) {
            c("onPlayEvent: loading start.");
            this.f20385h = true;
            if (this.f20383f) {
                a(V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusLoading, V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonBufferingBegin);
            }
            if (this.f20382e) {
                b(V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusLoading, V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonBufferingBegin);
            }
        }
        if (v2TXLivePlayerObserver == null || !(v2TXLivePlayerObserver instanceof com.tencent.live2.impl.a.a)) {
            return;
        }
        ((com.tencent.live2.impl.a.a) v2TXLivePlayerObserver).a(i2, bundle);
    }

    @Override // com.tencent.rtmp.TXLivePlayer.ITXLivePlayVideoRenderListener
    public void onRenderVideoFrame(TXLivePlayer.TXLiteAVTexture tXLiteAVTexture) {
        V2TXLivePlayerObserver v2TXLivePlayerObserver = this.f20390m;
        if (v2TXLivePlayerObserver == null || tXLiteAVTexture == null) {
            return;
        }
        V2TXLiveDef.V2TXLiveVideoFrame v2TXLiveVideoFrame = new V2TXLiveDef.V2TXLiveVideoFrame();
        v2TXLiveVideoFrame.pixelFormat = V2TXLiveDef.V2TXLivePixelFormat.V2TXLivePixelFormatTexture2D;
        v2TXLiveVideoFrame.bufferType = V2TXLiveDef.V2TXLiveBufferType.V2TXLiveBufferTypeTexture;
        v2TXLiveVideoFrame.width = tXLiteAVTexture.width;
        v2TXLiveVideoFrame.height = tXLiteAVTexture.height;
        v2TXLiveVideoFrame.rotation = 0;
        V2TXLiveDef.V2TXLiveTexture v2TXLiveTexture = new V2TXLiveDef.V2TXLiveTexture();
        v2TXLiveVideoFrame.texture = v2TXLiveTexture;
        v2TXLiveTexture.textureId = tXLiteAVTexture.textureId;
        Object obj = tXLiteAVTexture.eglContext;
        if (obj instanceof EGLContext) {
            v2TXLiveTexture.eglContext10 = (EGLContext) obj;
        } else if (TXCBuild.VersionInt() >= 17) {
            Object obj2 = tXLiteAVTexture.eglContext;
            if (obj2 instanceof android.opengl.EGLContext) {
                v2TXLiveVideoFrame.texture.eglContext14 = (android.opengl.EGLContext) obj2;
            }
        }
        v2TXLivePlayerObserver.onRenderVideoFrame(this.f20379b, v2TXLiveVideoFrame);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i2, int i3) {
        c("TextureView: available width-" + i2 + " height-" + i3);
        if (surfaceTexture != null) {
            this.f20380c.setSurface(new Surface(surfaceTexture));
        }
        this.f20380c.setSurfaceSize(i2, i3);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        c("TextureView: destroyed.");
        this.f20380c.setSurface(null);
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i2, int i3) {
        c("TextureView: size changed width-" + i2 + " height-" + i3);
        this.f20380c.setSurfaceSize(i2, i3);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    @Override // com.tencent.rtmp.TXLivePlayer.ITXVideoRawDataListener
    public void onVideoRawDataAvailable(byte[] bArr, int i2, int i3, int i4) {
        V2TXLivePlayerObserver v2TXLivePlayerObserver = this.f20390m;
        if (v2TXLivePlayerObserver != null) {
            V2TXLiveDef.V2TXLiveVideoFrame v2TXLiveVideoFrame = new V2TXLiveDef.V2TXLiveVideoFrame();
            v2TXLiveVideoFrame.pixelFormat = V2TXLiveDef.V2TXLivePixelFormat.V2TXLivePixelFormatI420;
            v2TXLiveVideoFrame.bufferType = V2TXLiveDef.V2TXLiveBufferType.V2TXLiveBufferTypeByteArray;
            v2TXLiveVideoFrame.data = bArr;
            v2TXLiveVideoFrame.width = i2;
            v2TXLiveVideoFrame.height = i3;
            v2TXLiveVideoFrame.rotation = 0;
            v2TXLivePlayerObserver.onRenderVideoFrame(this.f20379b, v2TXLiveVideoFrame);
        }
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int pauseAudio() {
        this.f20389l = true;
        this.f20380c.setMute(true);
        a(V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusStopped, V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonLocalStopped);
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int pauseVideo() {
        if (this.f20392o != null) {
            this.f20380c.setPlayerView(null);
        } else if (this.f20394q != null || this.f20393p != null) {
            this.f20380c.setSurface(null);
        }
        b(V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusStopped, V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonLocalStopped);
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int resumeAudio() {
        this.f20389l = false;
        this.f20380c.setMute(false);
        if (this.f20383f) {
            a(V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusPlaying, V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonLocalStarted);
            if (this.f20385h) {
                a(V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusLoading, V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonBufferingBegin);
            }
        }
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int resumeVideo() {
        TXCloudVideoView tXCloudVideoView = this.f20392o;
        if (tXCloudVideoView != null) {
            this.f20380c.setPlayerView(tXCloudVideoView);
        } else {
            TextureView textureView = this.f20393p;
            if (textureView != null) {
                SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
                if (surfaceTexture != null) {
                    this.f20380c.setSurface(new Surface(surfaceTexture));
                }
            } else {
                SurfaceView surfaceView = this.f20394q;
                if (surfaceView != null) {
                    Surface surface = surfaceView.getHolder().getSurface();
                    if (surface.isValid()) {
                        this.f20380c.setSurface(surface);
                    }
                }
            }
        }
        if (!this.f20382e) {
            return 0;
        }
        b(V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusPlaying, V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonLocalStarted);
        if (!this.f20385h) {
            return 0;
        }
        b(V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusLoading, V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonBufferingBegin);
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setCacheParams(float f2, float f3) {
        if (f2 > f3) {
            e("force fix error params. min:" + f3 + " max:" + f2);
        } else {
            f3 = f2;
            f2 = f3;
        }
        this.f20381d.setCacheTime(f2);
        this.f20381d.setMaxAutoAdjustCacheTime(f2);
        this.f20381d.setMinAutoAdjustCacheTime(f3);
        this.f20381d.setAutoAdjustCacheTime(f3 != f2);
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public void setObserver(V2TXLivePlayerObserver v2TXLivePlayerObserver) {
        this.f20390m = v2TXLivePlayerObserver;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setPlayoutVolume(int i2) {
        this.f20380c.setVolume(i2);
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    @Override // com.tencent.live2.V2TXLivePlayer
    public int setProperty(String str, Object obj) {
        str.hashCode();
        switch (str) {
            case "enableHardwareAcceleration":
                if (obj != null && (obj instanceof Boolean)) {
                    this.f20380c.enableHardwareDecode(((Boolean) obj).booleanValue());
                }
                return 0;
            case "setPlayURLType":
                if (obj != null && (obj instanceof Integer)) {
                    this.f20395r = ((Integer) obj).intValue();
                }
                return 0;
            case "setSurfaceSize":
                if (obj != null && (obj instanceof a.C0343a)) {
                    a.C0343a c0343a = (a.C0343a) obj;
                    this.f20380c.setSurfaceSize(c0343a.f20406a, c0343a.f20407b);
                }
                return 0;
            case "enableRecvSEIMessage":
                if (obj != null && (obj instanceof Boolean)) {
                    this.f20381d.setEnableMessage(((Boolean) obj).booleanValue());
                    this.f20380c.setConfig(this.f20381d);
                }
                return 0;
            case "setSurface":
                if (obj == null) {
                    this.f20380c.setSurface(null);
                } else if (obj instanceof Surface) {
                    this.f20380c.setSurface((Surface) obj);
                }
                return 0;
            case "maxNumberOfReconnection":
                if (obj != null && (obj instanceof Integer)) {
                    this.f20381d.setConnectRetryCount(((Integer) obj).intValue());
                    this.f20380c.setConfig(this.f20381d);
                }
                return 0;
            case "secondsBetweenReconnection":
                if (obj != null && (obj instanceof Integer)) {
                    this.f20381d.setConnectRetryInterval(((Integer) obj).intValue());
                    this.f20380c.setConfig(this.f20381d);
                }
                return 0;
            default:
                return 0;
        }
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setRenderFillMode(V2TXLiveDef.V2TXLiveFillMode v2TXLiveFillMode) {
        if (v2TXLiveFillMode == null) {
            e("setRenderFillMode: param is null, fix it.");
            v2TXLiveFillMode = V2TXLiveDef.V2TXLiveFillMode.V2TXLiveFillModeFill;
        }
        this.f20380c.setRenderMode(V2TXLiveUtils.getRTMPFillMode(v2TXLiveFillMode));
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setRenderRotation(V2TXLiveDef.V2TXLiveRotation v2TXLiveRotation) {
        if (v2TXLiveRotation == null) {
            e("setRenderRotation: param is null, fix it.");
            v2TXLiveRotation = V2TXLiveDef.V2TXLiveRotation.V2TXLiveRotation0;
        }
        this.f20380c.setRenderRotation(V2TXLiveUtils.getRTMPRotation(v2TXLiveRotation));
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setRenderView(TXCloudVideoView tXCloudVideoView) {
        c();
        if (tXCloudVideoView != null) {
            tXCloudVideoView.showLog(this.f20391n);
        }
        this.f20392o = tXCloudVideoView;
        b();
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public void showDebugView(boolean z2) {
        this.f20391n = z2;
        TXCloudVideoView tXCloudVideoView = this.f20392o;
        if (tXCloudVideoView != null) {
            tXCloudVideoView.showLog(z2);
        }
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int snapshot() {
        this.f20380c.snapshot(new TXLivePlayer.ITXSnapshotListener() { // from class: com.tencent.live2.a.a.1
            @Override // com.tencent.rtmp.TXLivePlayer.ITXSnapshotListener
            public void onSnapshot(Bitmap bitmap) {
                V2TXLivePlayerObserver v2TXLivePlayerObserver = a.this.f20390m;
                if (v2TXLivePlayerObserver != null) {
                    v2TXLivePlayerObserver.onSnapshotComplete(a.this.f20379b, bitmap);
                }
            }
        });
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int startPlay(String str) throws JSONException {
        int iB = b(str);
        c("startPlay: url-" + str + " type-" + iB);
        c();
        b();
        this.f20382e = false;
        this.f20383f = false;
        V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus = V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusLoading;
        this.f20387j = v2TXLivePlayStatus;
        this.f20388k = v2TXLivePlayStatus;
        this.f20385h = false;
        this.f20384g = true;
        this.f20380c.setConfig(this.f20381d);
        this.f20380c.callExperimentalAPI(String.format(Locale.ENGLISH, "{\"api\":\"setInterfaceType\", \"params\": {\"type\":%d}}", 1));
        int iStartPlay = this.f20380c.startPlay(str, iB);
        this.f20380c.setMute(this.f20389l);
        if (iStartPlay != 0) {
            d("startPlay: play fail, force stop.");
            V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus2 = V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusStopped;
            this.f20387j = v2TXLivePlayStatus2;
            this.f20388k = v2TXLivePlayStatus2;
            a();
        }
        return 0;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int stopPlay() {
        c("stopPlay:");
        if (!this.f20384g) {
            e("stopPlay: player have been stop.");
            return 0;
        }
        a();
        V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus = V2TXLiveDef.V2TXLivePlayStatus.V2TXLivePlayStatusStopped;
        V2TXLiveDef.V2TXLiveStatusChangeReason v2TXLiveStatusChangeReason = V2TXLiveDef.V2TXLiveStatusChangeReason.V2TXLiveStatusChangeReasonLocalStopped;
        a(v2TXLivePlayStatus, v2TXLiveStatusChangeReason);
        b(v2TXLivePlayStatus, v2TXLiveStatusChangeReason);
        return 0;
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i2, int i3, int i4) {
        c("SurfaceView: onSizeChanged.");
        this.f20380c.setSurfaceSize(i3, i4);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        c("SurfaceView: onCreate.");
        this.f20380c.setSurface(surfaceHolder.getSurface());
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        c("SurfaceView: onDestroyed.");
        this.f20380c.setSurface(null);
    }

    public String toString() {
        return "@" + Integer.toHexString(hashCode());
    }

    private void a() {
        c("stopPlayInner: ");
        this.f20382e = false;
        this.f20383f = false;
        this.f20385h = false;
        this.f20384g = false;
        this.f20386i = new V2TXLiveDef.V2TXLivePlayerStatistics();
        c();
        this.f20380c.stopPlay(true);
    }

    private void b(V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus, V2TXLiveDef.V2TXLiveStatusChangeReason v2TXLiveStatusChangeReason) {
        if (V2TXLiveUtils.isNextPlayerStatusValid(this.f20388k, v2TXLivePlayStatus, v2TXLiveStatusChangeReason)) {
            this.f20388k = v2TXLivePlayStatus;
            V2TXLivePlayerObserver v2TXLivePlayerObserver = this.f20390m;
            if (v2TXLivePlayerObserver != null) {
                v2TXLivePlayerObserver.onVideoPlayStatusUpdate(this.f20379b, v2TXLivePlayStatus, v2TXLiveStatusChangeReason, new Bundle());
            }
        }
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setRenderView(TextureView textureView) {
        c();
        this.f20393p = textureView;
        b();
        return 0;
    }

    private int b(String str) {
        if (this.f20395r == -1) {
            return (str != null && str.startsWith("rtmp")) ? 0 : 1;
        }
        c("force set url type:" + this.f20395r);
        return this.f20395r;
    }

    @Override // com.tencent.live2.V2TXLivePlayer
    public int setRenderView(SurfaceView surfaceView) {
        c();
        this.f20394q = surfaceView;
        b();
        return 0;
    }

    private void a(V2TXLiveDef.V2TXLivePlayStatus v2TXLivePlayStatus, V2TXLiveDef.V2TXLiveStatusChangeReason v2TXLiveStatusChangeReason) {
        if (V2TXLiveUtils.isNextPlayerStatusValid(this.f20387j, v2TXLivePlayStatus, v2TXLiveStatusChangeReason)) {
            this.f20387j = v2TXLivePlayStatus;
            V2TXLivePlayerObserver v2TXLivePlayerObserver = this.f20390m;
            if (v2TXLivePlayerObserver != null) {
                v2TXLivePlayerObserver.onAudioPlayStatusUpdate(this.f20379b, v2TXLivePlayStatus, v2TXLiveStatusChangeReason, new Bundle());
            }
        }
    }

    private void b() {
        TXCloudVideoView tXCloudVideoView = this.f20392o;
        TextureView textureView = this.f20393p;
        SurfaceView surfaceView = this.f20394q;
        if (tXCloudVideoView != null) {
            c("bindRenderView: cloud view.");
            this.f20380c.setSurface(null);
            this.f20380c.setSurfaceSize(0, 0);
            this.f20380c.setPlayerView(tXCloudVideoView);
            return;
        }
        if (textureView != null) {
            c("bindRenderView: texture view.");
            this.f20380c.setPlayerView(null);
            SurfaceTexture surfaceTexture = textureView.getSurfaceTexture();
            textureView.setSurfaceTextureListener(this);
            if (surfaceTexture != null) {
                c("bindRenderView: surface texture is valid, set into player.");
                this.f20380c.setSurface(new Surface(surfaceTexture));
                this.f20380c.setSurfaceSize(textureView.getWidth(), textureView.getHeight());
                return;
            }
            return;
        }
        if (surfaceView != null) {
            c("bindRenderView: surface view.");
            this.f20380c.setPlayerView(null);
            surfaceView.getHolder().addCallback(this);
            Surface surface = surfaceView.getHolder().getSurface();
            if (surface.isValid()) {
                c("bindRenderView: surface is valid, set into player.");
                this.f20380c.setSurface(surface);
                this.f20380c.setSurfaceSize(surfaceView.getWidth(), surfaceView.getHeight());
                return;
            }
            return;
        }
        d("bindRender: all view is null, bind fail.");
    }

    private void c(String str) {
        TXCLog.i("V2-TXRTMPPlayerImpl", "v2_api_rtmp_player(" + this + ") " + str);
    }

    public static int a(String str) {
        return (str.startsWith("rtmp://") || str.startsWith("room://") || str.startsWith(DefaultWebClient.HTTP_SCHEME) || str.startsWith(DefaultWebClient.HTTPS_SCHEME)) ? 0 : -2;
    }
}
