package com.plv.linkmic.processor.a;

import android.content.Context;
import android.content.Intent;
import android.view.SurfaceView;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.plv.foundationsdk.log.PLVCommonLog;
import com.plv.foundationsdk.rx.PLVRxTimer;
import com.plv.foundationsdk.utils.PLVFormatUtils;
import com.plv.linkmic.PLVLinkMicConstant;
import com.plv.linkmic.model.PLVRTCConfig;
import com.plv.linkmic.processor.PLVVideoDimensionBitrate;
import com.plv.linkmic.processor.e;
import com.plv.linkmic.repository.PLVLinkMicEngineToken;
import com.plv.livescenes.log.linkmic.PLVLinkMicELog;
import com.plv.rtc.IPLVARtcEngineEventHandler;
import com.plv.rtc.PLVARTCEngine;
import com.plv.rtc.model.PLVARTCEncoderConfiguration;
import io.reactivex.functions.Consumer;
import java.io.File;

/* loaded from: classes4.dex */
public class b extends e {
    private static final String TAG = "PLVLinkMicAgoraProcessor";

    /* renamed from: n, reason: collision with root package name */
    private static final PLVARTCEncoderConfiguration.ORIENTATION_MODE f10797n = PLVARTCEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_LANDSCAPE;

    /* renamed from: o, reason: collision with root package name */
    private static final int f10798o = 2;

    /* renamed from: p, reason: collision with root package name */
    private String f10799p;

    /* renamed from: q, reason: collision with root package name */
    private String f10800q;

    /* renamed from: r, reason: collision with root package name */
    private PLVRTCConfig f10801r;

    /* renamed from: s, reason: collision with root package name */
    private Intent f10802s;

    /* renamed from: t, reason: collision with root package name */
    private int f10803t = 1;

    /* renamed from: u, reason: collision with root package name */
    private final PLVARTCEncoderConfiguration f10804u = new PLVARTCEncoderConfiguration();
    private int uid;

    /* renamed from: v, reason: collision with root package name */
    private int f10805v;

    /* renamed from: w, reason: collision with root package name */
    @Nullable
    private SurfaceView f10806w;

    /* renamed from: x, reason: collision with root package name */
    private PLVARTCEngine f10807x;

    private int c() {
        PLVVideoDimensionBitrate pLVVideoDimensionBitrateMatch = PLVVideoDimensionBitrate.match(this.f10803t, this.f10801r.getResolutionRatio(), this.f10801r.getFrameRate());
        this.f10804u.dimensions = new PLVARTCEncoderConfiguration.VideoDimensions(pLVVideoDimensionBitrateMatch.width, pLVVideoDimensionBitrateMatch.height);
        PLVARTCEncoderConfiguration pLVARTCEncoderConfiguration = this.f10804u;
        pLVARTCEncoderConfiguration.bitrate = pLVVideoDimensionBitrateMatch.realBitrate;
        return this.f10807x.setVideoEncoderConfiguration(pLVARTCEncoderConfiguration);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.c
    public boolean a(PLVLinkMicEngineToken pLVLinkMicEngineToken, @NonNull PLVRTCConfig pLVRTCConfig, Context context, com.plv.linkmic.processor.a aVar) {
        this.uid = PLVFormatUtils.parseInt(pLVRTCConfig.getUid());
        this.f10799p = pLVLinkMicEngineToken.getAppId();
        this.f10800q = pLVLinkMicEngineToken.getToken();
        this.f10801r = pLVRTCConfig;
        return a(context, (IPLVARtcEngineEventHandler) aVar.b());
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int addPublishStreamUrl(String str, boolean z2) {
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine == null) {
            return -1;
        }
        return pLVARTCEngine.addPublishStreamUrl(str, false);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int adjustRecordingSignalVolume(int i2) {
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine == null) {
            return -1;
        }
        return pLVARTCEngine.adjustRecordingSignalVolume(i2);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public SurfaceView createRendererView(Context context) {
        return this.f10807x.createRenderView(context);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void destroy() {
        this.f10799p = "";
        this.f10800q = "";
        this.uid = 0;
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine != null) {
            pLVARTCEngine.destroy();
            this.f10807x = null;
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int enableLocalVideo(boolean z2) {
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine == null) {
            return -1;
        }
        return pLVARTCEngine.enableLocalVideo(z2);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int enableTorch(boolean z2) {
        return this.f10807x.setCameraTorchOn(z2);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public String getLinkMicUid() {
        return String.valueOf(this.uid);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int joinChannel(String str) {
        PLVCommonLog.d(TAG, PLVLinkMicELog.LinkMicTraceLogEvent.JOIN_CHANNEL);
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine == null) {
            return -1;
        }
        pLVARTCEngine.enableWebSdkInteroperability(true);
        PLVCommonLog.d(TAG, "speak :" + this.f10807x.isSpeakerphoneEnabled());
        return this.f10807x.joinChannel(this.f10800q, str, "OpenVCall", this.uid);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void leaveChannel(boolean z2) {
        PLVCommonLog.d(TAG, "leaveChannel->keepPreview=" + z2);
        try {
            PLVARTCEngine pLVARTCEngine = this.f10807x;
            if (pLVARTCEngine != null) {
                pLVARTCEngine.leaveChannel();
                if (z2) {
                    return;
                }
                this.f10807x.stopPreview();
            }
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int muteLocalAudio(boolean z2) {
        if (this.f10807x == null) {
            return -1;
        }
        PLVCommonLog.d(TAG, "muteLocalAudio:" + z2);
        return this.f10807x.muteLocalAudioStream(z2);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int muteLocalVideo(boolean z2) {
        if (this.f10807x == null) {
            return -1;
        }
        PLVCommonLog.d(TAG, "muteLocalVideo:" + z2);
        this.f10807x.enableLocalVideo(z2 ^ true);
        return this.f10807x.muteLocalVideoStream(z2);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void releaseRenderView(View view) {
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int removePublishStreamUrl(String str) {
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine == null) {
            return -1;
        }
        return pLVARTCEngine.removePublishStreamUrl(str);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int renewToken(String str) {
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine != null) {
            return pLVARTCEngine.renewToken(str);
        }
        return -1;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void setBitrate(int i2) {
        if (this.f10807x == null) {
            return;
        }
        this.f10803t = i2;
        c();
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setCameraZoomRatio(float f2) {
        return this.f10807x.setCameraZoomFactor(f2);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setLocalPreviewMirror(boolean z2) {
        int i2 = z2 ? 1 : 2;
        this.f10807x.setupLocalVideo(null, 0, 0);
        this.f10807x.setLocalVideoMirrorMode(i2);
        this.f10807x.setupLocalVideo(this.f10806w, this.f10805v, this.uid);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setLocalPushMirror(boolean z2) {
        if (z2) {
            this.f10804u.mirrorMode = 1;
        } else {
            this.f10804u.mirrorMode = 2;
        }
        return this.f10807x.setVideoEncoderConfiguration(this.f10804u);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setPushPictureResolutionType(int i2) {
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine == null) {
            return -1;
        }
        if (i2 == 0) {
            this.f10804u.orientationMode = PLVARTCEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_ADAPTIVE;
        } else if (i2 == 1) {
            this.f10804u.orientationMode = PLVARTCEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT;
        } else if (i2 == 2) {
            this.f10804u.orientationMode = PLVARTCEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_LANDSCAPE;
        }
        pLVARTCEngine.setVideoEncoderConfiguration(this.f10804u);
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setPushResolutionRatio(PLVLinkMicConstant.PushResolutionRatio pushResolutionRatio) {
        this.f10801r.resolutionRatio(pushResolutionRatio);
        return c();
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void setScreenCaptureSource(Intent intent) {
        super.setScreenCaptureSource(intent);
        this.f10802s = intent;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int setupLocalVideo(View view, int i2, String str) throws NumberFormatException {
        if (!a(view)) {
            return -1;
        }
        SurfaceView surfaceView = (SurfaceView) view;
        try {
            int i3 = Integer.parseInt(str);
            PLVARTCEngine pLVARTCEngine = this.f10807x;
            if (pLVARTCEngine == null) {
                return -1;
            }
            this.f10805v = i2;
            this.f10806w = surfaceView;
            pLVARTCEngine.setupLocalVideo(surfaceView, i2, i3);
            return this.f10807x.startPreview();
        } catch (NumberFormatException e2) {
            PLVCommonLog.e(TAG, "setupLocalVideo:" + e2.getMessage());
            return -1;
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void setupRemoteVideo(View view, int i2, String str) {
        if (a(view)) {
            SurfaceView surfaceView = (SurfaceView) view;
            try {
                int i3 = (int) Long.parseLong(str);
                PLVARTCEngine pLVARTCEngine = this.f10807x;
                if (pLVARTCEngine != null) {
                    pLVARTCEngine.setupRemoteVideo(surfaceView, i2, i3);
                }
            } catch (NumberFormatException e2) {
                PLVCommonLog.e(TAG, "setupRemoteVideo：" + e2.getMessage());
            }
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void startPreview() {
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine == null) {
            return;
        }
        pLVARTCEngine.startPreview();
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int startPushImageStream(String str) {
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int startShareScreen() {
        super.startShareScreen();
        return this.f10807x.startShareScreen(this.f10804u, this.f10802s);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int stopPushImageStream() {
        return 0;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int stopShareScreen() {
        this.f10807x.stopShareScreen();
        this.f10807x.recoverDefaultSource();
        this.f10807x.startPreview();
        return super.stopShareScreen();
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void switchBeauty(boolean z2) {
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine != null) {
            pLVARTCEngine.switchBeauty(z2);
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public void switchCamera() {
        PLVCommonLog.d(TAG, PLVLinkMicELog.LinkMicTraceLogEvent.SWITCH_CAMERA);
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine != null) {
            pLVARTCEngine.switchCamera();
        }
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int switchRoleToAudience() {
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine == null) {
            return -1;
        }
        pLVARTCEngine.setClientRole(2);
        PLVRxTimer.delay(1000L, new Consumer<Object>() { // from class: com.plv.linkmic.processor.a.b.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                b.this.enableLocalVideo(true);
            }
        });
        return 1;
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int switchRoleToBroadcaster() {
        PLVARTCEngine pLVARTCEngine = this.f10807x;
        if (pLVARTCEngine == null) {
            return -1;
        }
        return pLVARTCEngine.setClientRole(1);
    }

    @Override // com.plv.linkmic.processor.e, com.plv.linkmic.processor.b
    public int updateSEIFrameTimeStamp(String str) {
        return 0;
    }

    private boolean a(Context context, IPLVARtcEngineEventHandler iPLVARtcEngineEventHandler) {
        if (this.f10807x != null) {
            return true;
        }
        try {
            this.f10807x = PLVARTCEngine.create(context, this.f10799p, iPLVARtcEngineEventHandler);
            PLVVideoDimensionBitrate pLVVideoDimensionBitrateMatch = PLVVideoDimensionBitrate.match(this.f10803t, this.f10801r.getResolutionRatio(), this.f10801r.getFrameRate());
            this.f10807x.setChannelProfile(1);
            this.f10804u.dimensions = new PLVARTCEncoderConfiguration.VideoDimensions(pLVVideoDimensionBitrateMatch.width, pLVVideoDimensionBitrateMatch.height);
            PLVARTCEncoderConfiguration pLVARTCEncoderConfiguration = this.f10804u;
            pLVARTCEncoderConfiguration.bitrate = pLVVideoDimensionBitrateMatch.realBitrate;
            pLVARTCEncoderConfiguration.frameRate = this.f10801r.getFrameRate();
            PLVARTCEncoderConfiguration pLVARTCEncoderConfiguration2 = this.f10804u;
            pLVARTCEncoderConfiguration2.orientationMode = f10797n;
            pLVARTCEncoderConfiguration2.mirrorMode = 2;
            this.f10807x.setVideoEncoderConfiguration(pLVARTCEncoderConfiguration2);
            this.f10807x.setClientRole(1);
            this.f10807x.setAudioProfile(0, 2);
            this.f10807x.enableVideo();
            this.f10807x.enableAudioVolumeIndication(1000, 3);
            this.f10807x.setLogFile(context.getExternalCacheDir() + File.separator + "/log/agora-rtc.log");
            this.f10807x.enableDualStreamMode(true);
            return true;
        } catch (Exception e2) {
            PLVCommonLog.exception(e2);
            return false;
        }
    }

    private static boolean a(View view) {
        return view instanceof SurfaceView;
    }
}
