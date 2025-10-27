package com.plv.rtc.trtc.a;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.SurfaceView;
import androidx.annotation.FloatRange;
import com.plv.beauty.api.IPLVBeautyManager;
import com.plv.beauty.api.PLVBeautyManager;
import com.plv.rtc.trtc.PLVTRTCDef;
import com.plv.rtc.trtc.PLVTRTCEngine;
import com.plv.rtc.trtc.PLVTRTCEventListener;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class b implements PLVTRTCEngine {

    /* renamed from: a, reason: collision with root package name */
    private final TRTCCloud f10902a;

    /* renamed from: b, reason: collision with root package name */
    private IPLVBeautyManager.InitCallback f10903b;

    /* renamed from: c, reason: collision with root package name */
    private volatile boolean f10904c = false;

    /* renamed from: d, reason: collision with root package name */
    private volatile boolean f10905d = true;

    /* renamed from: e, reason: collision with root package name */
    private volatile boolean f10906e = false;

    public class a implements IPLVBeautyManager.InitCallback {
        public a() {
        }

        @Override // com.plv.beauty.api.IPLVBeautyManager.InitCallback
        public void onFinishInit(Integer num) {
            b.this.f10904c = num != null && num.intValue() == 0;
        }

        @Override // com.plv.beauty.api.IPLVBeautyManager.InitCallback
        public void onStartInit() {
        }
    }

    /* renamed from: com.plv.rtc.trtc.a.b$b, reason: collision with other inner class name */
    public class C0229b implements TRTCCloudListener.TRTCVideoFrameListener {

        /* renamed from: a, reason: collision with root package name */
        private volatile boolean f10908a = false;

        /* renamed from: b, reason: collision with root package name */
        private volatile boolean f10909b = false;

        /* renamed from: com.plv.rtc.trtc.a.b$b$a */
        public class a implements IPLVBeautyManager.SetupCallback {
            public a() {
            }

            @Override // com.plv.beauty.api.IPLVBeautyManager.SetupCallback
            public void onSetup(boolean z2) {
                C0229b.this.f10909b = z2;
            }
        }

        public C0229b() {
        }

        @Override // com.tencent.trtc.TRTCCloudListener.TRTCVideoFrameListener
        public void onGLContextCreated() {
            if (this.f10908a) {
                return;
            }
            this.f10908a = a();
        }

        @Override // com.tencent.trtc.TRTCCloudListener.TRTCVideoFrameListener
        public void onGLContextDestory() {
            this.f10909b = false;
            PLVBeautyManager.getInstance().release();
            this.f10908a = false;
        }

        @Override // com.tencent.trtc.TRTCCloudListener.TRTCVideoFrameListener
        public int onProcessVideoFrame(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame2) {
            if (!this.f10908a) {
                this.f10908a = a();
            }
            if (PLVBeautyManager.getInstance().isBeautySupport() && b.this.f10905d && !b.this.f10906e && b.this.f10904c && this.f10909b) {
                tRTCVideoFrame2.texture.textureId = PLVBeautyManager.getInstance().processTexture2dTo2d(tRTCVideoFrame.texture.textureId, tRTCVideoFrame.width, tRTCVideoFrame.height, tRTCVideoFrame.rotation, tRTCVideoFrame.timestamp);
                return 0;
            }
            tRTCVideoFrame2.texture.textureId = tRTCVideoFrame.texture.textureId;
            return 0;
        }

        private boolean a() {
            if (!PLVBeautyManager.getInstance().isBeautySupport() || !b.this.f10904c) {
                return false;
            }
            PLVBeautyManager.getInstance().release();
            PLVBeautyManager.getInstance().setup(new a());
            return true;
        }
    }

    public b(Context context) {
        TRTCCloud tRTCCloudSharedInstance = TRTCCloud.sharedInstance(context);
        this.f10902a = tRTCCloudSharedInstance;
        tRTCCloudSharedInstance.setGSensorMode(0);
        a();
        b();
        a(tRTCCloudSharedInstance);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void destroy() {
        TRTCCloud.destroySharedInstance();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void enableAudioVolumeEvaluation(int i2) {
        this.f10902a.enableAudioVolumeEvaluation(i2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public boolean enableTorch(boolean z2) {
        return this.f10902a.getDeviceManager().enableCameraTorch(z2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void enterRoom(PLVTRTCDef.TRTCParams tRTCParams, int i2) {
        TRTCCloudDef.TRTCParams tRTCParams2 = new TRTCCloudDef.TRTCParams();
        tRTCParams2.sdkAppId = tRTCParams.sdkAppId;
        tRTCParams2.userId = tRTCParams.userId;
        tRTCParams2.userSig = tRTCParams.userSig;
        tRTCParams2.roomId = tRTCParams.roomId;
        tRTCParams2.strRoomId = tRTCParams.strRoomId;
        tRTCParams2.role = tRTCParams.role;
        tRTCParams2.streamId = tRTCParams.streamId;
        tRTCParams2.userDefineRecordId = tRTCParams.userDefineRecordId;
        tRTCParams2.privateMapKey = tRTCParams.privateMapKey;
        tRTCParams2.businessInfo = tRTCParams.businessInfo;
        this.f10902a.enterRoom(tRTCParams2, i2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void exitRoom() {
        this.f10902a.exitRoom();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void muteLocalAudio(boolean z2) {
        this.f10902a.muteLocalAudio(z2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void muteLocalVideo(boolean z2) {
        this.f10902a.muteLocalVideo(z2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public int setCameraZoomRatio(@FloatRange(from = 1.0d, to = 10.0d) float f2) {
        return this.f10902a.getDeviceManager().setCameraZoomRatio(((Math.max(1.0f, Math.min(f2, 10.0f)) - 1.0f) / 9.0f) * this.f10902a.getDeviceManager().getCameraZoomMaxRatio());
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void setListener(PLVTRTCEventListener pLVTRTCEventListener) {
        if (pLVTRTCEventListener == null) {
            this.f10902a.setListener(null);
        } else {
            this.f10902a.setListener(new com.plv.rtc.trtc.a.a(pLVTRTCEventListener));
        }
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void setLocalRenderParams(PLVTRTCDef.TRTCRenderParams tRTCRenderParams) {
        TRTCCloudDef.TRTCRenderParams tRTCRenderParams2 = new TRTCCloudDef.TRTCRenderParams();
        tRTCRenderParams2.rotation = tRTCRenderParams.rotation;
        tRTCRenderParams2.mirrorType = tRTCRenderParams.mirrorType;
        tRTCRenderParams2.fillMode = tRTCRenderParams.fillMode;
        this.f10902a.setLocalRenderParams(tRTCRenderParams2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void setLocalViewFillMode(int i2) {
        this.f10902a.setLocalViewFillMode(i2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void setMixTranscodingConfig(PLVTRTCDef.TRTCTranscodingConfig tRTCTranscodingConfig) {
        TRTCCloudDef.TRTCTranscodingConfig tRTCTranscodingConfig2 = new TRTCCloudDef.TRTCTranscodingConfig();
        tRTCTranscodingConfig2.appId = tRTCTranscodingConfig.appId;
        tRTCTranscodingConfig2.bizId = tRTCTranscodingConfig.bizId;
        tRTCTranscodingConfig2.mode = tRTCTranscodingConfig.mode;
        tRTCTranscodingConfig2.videoWidth = tRTCTranscodingConfig.videoWidth;
        tRTCTranscodingConfig2.videoHeight = tRTCTranscodingConfig.videoHeight;
        tRTCTranscodingConfig2.videoBitrate = tRTCTranscodingConfig.videoBitrate;
        tRTCTranscodingConfig2.videoFramerate = tRTCTranscodingConfig.videoFramerate;
        tRTCTranscodingConfig2.videoGOP = tRTCTranscodingConfig.videoGOP;
        tRTCTranscodingConfig2.backgroundColor = tRTCTranscodingConfig.backgroundColor;
        tRTCTranscodingConfig2.backgroundImage = tRTCTranscodingConfig.backgroundImage;
        tRTCTranscodingConfig2.audioSampleRate = tRTCTranscodingConfig.audioSampleRate;
        tRTCTranscodingConfig2.audioBitrate = tRTCTranscodingConfig.audioBitrate;
        tRTCTranscodingConfig2.audioChannels = tRTCTranscodingConfig.audioChannels;
        tRTCTranscodingConfig2.mixUsers = new ArrayList<>();
        Iterator<PLVTRTCDef.TRTCMixUser> it = tRTCTranscodingConfig.mixUsers.iterator();
        while (it.hasNext()) {
            PLVTRTCDef.TRTCMixUser next = it.next();
            TRTCCloudDef.TRTCMixUser tRTCMixUser = new TRTCCloudDef.TRTCMixUser();
            tRTCMixUser.userId = next.userId;
            tRTCMixUser.roomId = next.roomId;
            tRTCMixUser.f22290x = next.f10899x;
            tRTCMixUser.f22291y = next.f10900y;
            tRTCMixUser.width = next.width;
            tRTCMixUser.height = next.height;
            tRTCMixUser.zOrder = next.zOrder;
            tRTCMixUser.streamType = next.streamType;
            tRTCMixUser.pureAudio = next.pureAudio;
            tRTCTranscodingConfig2.mixUsers.add(tRTCMixUser);
        }
        tRTCTranscodingConfig2.streamId = tRTCTranscodingConfig.streamId;
        this.f10902a.setMixTranscodingConfig(tRTCTranscodingConfig2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void setRemoteRenderParams(String str, int i2, PLVTRTCDef.TRTCRenderParams tRTCRenderParams) {
        TRTCCloudDef.TRTCRenderParams tRTCRenderParams2 = new TRTCCloudDef.TRTCRenderParams();
        tRTCRenderParams2.fillMode = tRTCRenderParams.fillMode;
        tRTCRenderParams2.mirrorType = tRTCRenderParams.mirrorType;
        tRTCRenderParams2.rotation = tRTCRenderParams.rotation;
        this.f10902a.setRemoteRenderParams(str, i2, tRTCRenderParams2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void setVideoEncoderMirror(boolean z2) {
        this.f10902a.setVideoEncoderMirror(z2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void setVideoEncoderParam(PLVTRTCDef.TRTCVideoEncParam tRTCVideoEncParam) {
        TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam2 = new TRTCCloudDef.TRTCVideoEncParam();
        tRTCVideoEncParam2.videoResolution = tRTCVideoEncParam.videoResolution;
        tRTCVideoEncParam2.enableAdjustRes = tRTCVideoEncParam.enableAdjustRes;
        tRTCVideoEncParam2.minVideoBitrate = tRTCVideoEncParam.minVideoBitrate;
        tRTCVideoEncParam2.videoBitrate = tRTCVideoEncParam.videoBitrate;
        tRTCVideoEncParam2.videoFps = tRTCVideoEncParam.videoFps;
        tRTCVideoEncParam2.videoResolutionMode = tRTCVideoEncParam.videoResolutionMode;
        this.f10902a.setVideoEncoderParam(tRTCVideoEncParam2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void setVideoMuteImage(Bitmap bitmap, int i2) {
        this.f10902a.setVideoMuteImage(bitmap, i2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void startLocalAudio(int i2) {
        this.f10902a.startLocalAudio();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void startLocalPreview(boolean z2, SurfaceView surfaceView) {
        this.f10902a.startLocalPreview(z2, new TXCloudVideoView(surfaceView));
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void startPublishCDNStream(PLVTRTCDef.TRTCPublishCDNParam tRTCPublishCDNParam) {
        TRTCCloudDef.TRTCPublishCDNParam tRTCPublishCDNParam2 = new TRTCCloudDef.TRTCPublishCDNParam();
        tRTCPublishCDNParam2.appId = tRTCPublishCDNParam.appId;
        tRTCPublishCDNParam2.url = tRTCPublishCDNParam.url;
        tRTCPublishCDNParam2.bizId = tRTCPublishCDNParam.bizId;
        this.f10902a.startPublishCDNStream(tRTCPublishCDNParam2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void startPublishing(String str, int i2) {
        this.f10902a.startPublishing(str, i2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void startRemoteView(String str, int i2, SurfaceView surfaceView) {
        this.f10902a.startRemoteView(str, i2, new TXCloudVideoView(surfaceView));
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void startScreenCapture(int i2, PLVTRTCDef.TRTCVideoEncParam tRTCVideoEncParam) {
        TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam2 = new TRTCCloudDef.TRTCVideoEncParam();
        tRTCVideoEncParam2.videoResolution = tRTCVideoEncParam.videoResolution;
        tRTCVideoEncParam2.videoFps = tRTCVideoEncParam.videoFps;
        tRTCVideoEncParam2.enableAdjustRes = tRTCVideoEncParam.enableAdjustRes;
        tRTCVideoEncParam2.minVideoBitrate = tRTCVideoEncParam.minVideoBitrate;
        tRTCVideoEncParam2.videoBitrate = tRTCVideoEncParam.videoBitrate;
        tRTCVideoEncParam2.videoResolutionMode = tRTCVideoEncParam.videoResolutionMode;
        this.f10902a.startScreenCapture(i2, tRTCVideoEncParam2, new TRTCCloudDef.TRTCScreenShareParams());
        this.f10906e = true;
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void stopLocalPreview() {
        this.f10902a.stopLocalPreview();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void stopPublishCDNStream() {
        this.f10902a.stopPublishCDNStream();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void stopPublishing() {
        this.f10902a.stopPublishing();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void stopRemoteView(String str, int i2) {
        this.f10902a.stopRemoteView(str, i2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void stopScreenCapture() {
        this.f10902a.stopScreenCapture();
        this.f10906e = false;
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void switchBeauty(boolean z2) {
        this.f10905d = z2;
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void switchCamera() {
        this.f10902a.switchCamera();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEngine
    public void switchRole(int i2) {
        this.f10902a.switchRole(i2);
    }

    private void b() {
        this.f10902a.setLocalVideoProcessListener(2, 3, new C0229b());
    }

    private void a() {
        if (PLVBeautyManager.getInstance().isBeautySupport()) {
            this.f10903b = new a();
            PLVBeautyManager.getInstance().addInitCallback(new WeakReference<>(this.f10903b));
        }
    }

    @Deprecated
    private static void a(TRTCCloud tRTCCloud) {
        tRTCCloud.callExperimentalAPI("{\n  \"api\":\"setLocalAudioMuteAction\",\n  \"params\":{\n    \"volumeEvaluation\":1\n  }\n}");
    }
}
