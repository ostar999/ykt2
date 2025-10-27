package com.plv.rtc.urtc.a;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.plv.beauty.api.IPLVBeautyManager;
import com.plv.beauty.api.PLVBeautyManager;
import com.plv.rtc.urtc.PLVURTCEngine;
import com.plv.rtc.urtc.enummeration.URTCSdkAudioDevice;
import com.plv.rtc.urtc.enummeration.URTCSdkMediaType;
import com.plv.rtc.urtc.enummeration.URTCSdkRoomType;
import com.plv.rtc.urtc.enummeration.URTCSdkScaleType;
import com.plv.rtc.urtc.enummeration.URTCSdkStreamRole;
import com.plv.rtc.urtc.enummeration.URTCSdkVideoProfile;
import com.plv.rtc.urtc.listener.URTCFirstFrameRendered;
import com.plv.rtc.urtc.listener.URtcSdkEventListener;
import com.plv.rtc.urtc.model.URTCSdkAuthInfo;
import com.plv.rtc.urtc.model.URTCSdkMixProfile;
import com.plv.rtc.urtc.model.URTCSdkStreamInfo;
import com.ucloudrtclib.sdkengine.UCloudRtcSdkEngine;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkAuthInfo;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMixProfile;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamInfo;
import com.ucloudrtclib.sdkengine.listener.UcloudRtcVideoFramePreProcessListener;
import com.ucloudrtclib.sdkengine.openinterface.UCloudRTCFirstFrameRendered;
import core.data.WrappedVideoFrame;
import java.lang.ref.WeakReference;
import java.util.List;

/* loaded from: classes5.dex */
public class a implements PLVURTCEngine {

    /* renamed from: a, reason: collision with root package name */
    private final UCloudRtcSdkEngine f10912a;

    /* renamed from: b, reason: collision with root package name */
    private IPLVBeautyManager.InitCallback f10913b;

    /* renamed from: c, reason: collision with root package name */
    private boolean f10914c = false;

    /* renamed from: com.plv.rtc.urtc.a.a$a, reason: collision with other inner class name */
    public class C0230a implements UCloudRTCFirstFrameRendered {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ URTCFirstFrameRendered f10915a;

        public C0230a(URTCFirstFrameRendered uRTCFirstFrameRendered) {
            this.f10915a = uRTCFirstFrameRendered;
        }

        @Override // com.ucloudrtclib.sdkengine.openinterface.UCloudRTCFirstFrameRendered
        public void onFirstFrameRender(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, View view) {
            URTCFirstFrameRendered uRTCFirstFrameRendered = this.f10915a;
            if (uRTCFirstFrameRendered != null) {
                uRTCFirstFrameRendered.onFirstFrameRender(com.plv.rtc.urtc.a.c.a(uCloudRtcSdkStreamInfo), view);
            }
        }
    }

    public class b implements UCloudRTCFirstFrameRendered {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ URTCFirstFrameRendered f10917a;

        public b(URTCFirstFrameRendered uRTCFirstFrameRendered) {
            this.f10917a = uRTCFirstFrameRendered;
        }

        @Override // com.ucloudrtclib.sdkengine.openinterface.UCloudRTCFirstFrameRendered
        public void onFirstFrameRender(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, View view) {
            URTCFirstFrameRendered uRTCFirstFrameRendered = this.f10917a;
            if (uRTCFirstFrameRendered != null) {
                uRTCFirstFrameRendered.onFirstFrameRender(com.plv.rtc.urtc.a.c.a(uCloudRtcSdkStreamInfo), view);
            }
        }
    }

    public class c implements UCloudRTCFirstFrameRendered {

        /* renamed from: a, reason: collision with root package name */
        final /* synthetic */ URTCFirstFrameRendered f10919a;

        public c(URTCFirstFrameRendered uRTCFirstFrameRendered) {
            this.f10919a = uRTCFirstFrameRendered;
        }

        @Override // com.ucloudrtclib.sdkengine.openinterface.UCloudRTCFirstFrameRendered
        public void onFirstFrameRender(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, View view) {
            URTCFirstFrameRendered uRTCFirstFrameRendered = this.f10919a;
            if (uRTCFirstFrameRendered != null) {
                uRTCFirstFrameRendered.onFirstFrameRender(com.plv.rtc.urtc.a.c.a(uCloudRtcSdkStreamInfo), view);
            }
        }
    }

    public class d implements IPLVBeautyManager.InitCallback {
        public d() {
        }

        @Override // com.plv.beauty.api.IPLVBeautyManager.InitCallback
        public void onFinishInit(Integer num) {
            a.this.f10914c = num != null && num.intValue() == 0;
        }

        @Override // com.plv.beauty.api.IPLVBeautyManager.InitCallback
        public void onStartInit() {
        }
    }

    public class e implements UcloudRtcVideoFramePreProcessListener {

        /* renamed from: c, reason: collision with root package name */
        private static final int f10922c = 1;

        /* renamed from: d, reason: collision with root package name */
        private static final int f10923d = 2;

        /* renamed from: e, reason: collision with root package name */
        private static final int f10924e = 1;

        /* renamed from: a, reason: collision with root package name */
        private boolean f10925a = false;

        /* renamed from: com.plv.rtc.urtc.a.a$e$a, reason: collision with other inner class name */
        public class C0231a implements IPLVBeautyManager.SetupCallback {
            public C0231a() {
            }

            @Override // com.plv.beauty.api.IPLVBeautyManager.SetupCallback
            public void onSetup(boolean z2) {
            }
        }

        public e() {
        }

        private void a() {
            if (!this.f10925a && PLVBeautyManager.getInstance().isBeautySupport() && a.this.f10914c) {
                PLVBeautyManager.getInstance().release();
                PLVBeautyManager.getInstance().setup(new C0231a());
                this.f10925a = true;
            }
        }

        @Override // core.interfaces.VideoFramePreProcessListener
        public void onGLContextCreated() {
            a();
        }

        @Override // core.interfaces.VideoFramePreProcessListener
        public void onGLContextDestroy() {
            PLVBeautyManager.getInstance().release();
            this.f10925a = false;
        }

        @Override // core.interfaces.VideoFramePreProcessListener
        public int onPreProcessVideoFrame(WrappedVideoFrame wrappedVideoFrame, WrappedVideoFrame wrappedVideoFrame2) {
            a();
            if (this.f10925a && a.this.f10914c && wrappedVideoFrame.getFormat() == 2) {
                wrappedVideoFrame2.setTextureId(PLVBeautyManager.getInstance().processTextureOesTo2d(wrappedVideoFrame.getTextureId(), wrappedVideoFrame.getWidth(), wrappedVideoFrame.getHeight(), wrappedVideoFrame.getRotation(), wrappedVideoFrame.getTimeStamp()));
                wrappedVideoFrame2.setFormat(1);
                wrappedVideoFrame2.setBufferType(1);
            }
            return 0;
        }
    }

    public a(URtcSdkEventListener uRtcSdkEventListener) {
        this.f10912a = o1.a.a(new com.plv.rtc.urtc.a.b(uRtcSdkEventListener));
        b();
        a();
    }

    private void b() {
        if (PLVBeautyManager.getInstance().isBeautySupport()) {
            this.f10913b = new d();
            PLVBeautyManager.getInstance().addInitCallback(new WeakReference<>(this.f10913b));
        }
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void adjustRecordVolume(int i2) {
        this.f10912a.adjustRecordVolume(i2);
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void changePushResolution(URTCSdkVideoProfile uRTCSdkVideoProfile) {
        this.f10912a.changePushResolution(com.plv.rtc.urtc.a.c.a(uRTCSdkVideoProfile));
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int configLocalAudioPublish(boolean z2) {
        return this.f10912a.configLocalAudioPublish(z2).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int configLocalCameraPublish(boolean z2) {
        return this.f10912a.configLocalCameraPublish(z2).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int configLocalScreenPublish(boolean z2) {
        return this.f10912a.configLocalScreenPublish(z2).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void controlAudio(boolean z2) {
        this.f10912a.controlAudio(z2);
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void controlAudioPlayOut(boolean z2) {
        this.f10912a.controlAudioPlayOut(z2);
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void controlAudioRecord(boolean z2) {
        this.f10912a.controlAudioRecord(z2);
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void controlLocalVideo(boolean z2) {
        this.f10912a.controlLocalVideo(z2);
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void destroy() {
        o1.a.b();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public URTCSdkAudioDevice getDefaultAudioDevice() {
        return com.plv.rtc.urtc.a.c.a(this.f10912a.getDefaultAudioDevice());
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public boolean getSpeakerOn() {
        return this.f10912a.getSpeakerOn();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public boolean isAudioOnlyMode() {
        return this.f10912a.isAudioOnlyMode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public boolean isAutoPublish() {
        return this.f10912a.isAutoPublish();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public boolean isAutoSubscribe() {
        return this.f10912a.isAutoSubscribe();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public boolean isLocalAudioPublishEnabled() {
        return this.f10912a.isLocalAudioPublishEnabled();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public boolean isLocalCameraPublishEnabled() {
        return this.f10912a.isLocalCameraPublishEnabled();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public boolean isLocalScreenPublishEnabled() {
        return this.f10912a.isLocalScreenPublishEnabled();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int joinChannel(URTCSdkAuthInfo uRTCSdkAuthInfo) {
        UCloudRtcSdkAuthInfo uCloudRtcSdkAuthInfo = new UCloudRtcSdkAuthInfo();
        uCloudRtcSdkAuthInfo.setAppId(uRTCSdkAuthInfo.getAppId());
        uCloudRtcSdkAuthInfo.setRoomId(uRTCSdkAuthInfo.getRoomId());
        uCloudRtcSdkAuthInfo.setToken(uRTCSdkAuthInfo.getToken());
        uCloudRtcSdkAuthInfo.setUId(uRTCSdkAuthInfo.getUId());
        return this.f10912a.joinChannel(uCloudRtcSdkAuthInfo).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void kickOffOthers(int i2, List<String> list) {
        this.f10912a.kickOffOthers(i2, list);
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int leaveChannel() {
        return this.f10912a.leaveChannel().getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int leaveChannelNonStopLocalPreview() {
        return this.f10912a.leaveChannelNonStopLocalPreview().getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void lockExtendDeviceInputBuffer() {
        this.f10912a.lockExtendDeviceInputBuffer();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void messageNotify(String str) {
        this.f10912a.messageNotify(str);
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int muteLocalMic(boolean z2) {
        return this.f10912a.muteLocalMic(z2).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int muteLocalVideo(boolean z2, URTCSdkMediaType uRTCSdkMediaType) {
        return this.f10912a.muteLocalVideo(z2, com.plv.rtc.urtc.a.c.a(uRTCSdkMediaType)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int muteRemoteAudio(String str, boolean z2) {
        return this.f10912a.muteRemoteAudio(str, z2).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int muteRemoteScreen(String str, boolean z2) {
        return this.f10912a.muteRemoteScreen(str, z2).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int muteRemoteVideo(String str, boolean z2) {
        return this.f10912a.muteRemoteVideo(str, z2).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void onScreenCaptureResult(Intent intent) {
        o1.a.e(intent);
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int publish(URTCSdkMediaType uRTCSdkMediaType, boolean z2, boolean z3) {
        return this.f10912a.publish(com.plv.rtc.urtc.a.c.a(uRTCSdkMediaType), z2, z3).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void releaseExtendDeviceInputBuffer() {
        this.f10912a.releaseExtendDeviceInputBuffer();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int renderLocalView(URTCSdkStreamInfo uRTCSdkStreamInfo, Object obj, URTCSdkScaleType uRTCSdkScaleType, URTCFirstFrameRendered uRTCFirstFrameRendered) {
        return this.f10912a.renderLocalView(com.plv.rtc.urtc.a.c.a(uRTCSdkStreamInfo), obj, com.plv.rtc.urtc.a.c.a(uRTCSdkScaleType), new b(uRTCFirstFrameRendered)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void requestScreenCapture(Activity activity) {
        o1.a.g(activity);
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void setAudioDevice(URTCSdkAudioDevice uRTCSdkAudioDevice) {
        this.f10912a.setAudioDevice(com.plv.rtc.urtc.a.c.a(uRTCSdkAudioDevice));
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int setAudioOnlyMode(boolean z2) {
        return this.f10912a.setAudioOnlyMode(z2).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int setAutoPublish(boolean z2) {
        return this.f10912a.setAutoPublish(z2).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int setAutoSubscribe(boolean z2) {
        return this.f10912a.setAutoSubscribe(z2).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int setClassType(URTCSdkRoomType uRTCSdkRoomType) {
        return this.f10912a.setClassType(com.plv.rtc.urtc.a.c.a(uRTCSdkRoomType)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void setEventListener(URtcSdkEventListener uRtcSdkEventListener) {
        this.f10912a.setEventListener(new com.plv.rtc.urtc.a.b(uRtcSdkEventListener));
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void setFlashOn(boolean z2) {
        this.f10912a.setFlashOn(z2);
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void setRenderViewMode(boolean z2, URTCSdkStreamInfo uRTCSdkStreamInfo, URTCSdkScaleType uRTCSdkScaleType) {
        this.f10912a.setRenderViewMode(z2, com.plv.rtc.urtc.a.c.a(uRTCSdkStreamInfo), com.plv.rtc.urtc.a.c.a(uRTCSdkScaleType));
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int setScreenProfile(URTCSdkVideoProfile uRTCSdkVideoProfile) {
        return this.f10912a.setScreenProfile(com.plv.rtc.urtc.a.c.a(uRTCSdkVideoProfile)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void setSpeakerOn(boolean z2) {
        this.f10912a.setSpeakerOn(z2);
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int setStreamRole(URTCSdkStreamRole uRTCSdkStreamRole) {
        return this.f10912a.setStreamRole(com.plv.rtc.urtc.a.c.a(uRTCSdkStreamRole)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int setVideoProfile(URTCSdkVideoProfile uRTCSdkVideoProfile) {
        return this.f10912a.setVideoProfile(com.plv.rtc.urtc.a.c.a(uRTCSdkVideoProfile)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int startCameraPreview(Object obj, URTCSdkScaleType uRTCSdkScaleType, URTCFirstFrameRendered uRTCFirstFrameRendered) {
        return this.f10912a.startCameraPreview(obj, com.plv.rtc.urtc.a.c.a(uRTCSdkScaleType), new C0230a(uRTCFirstFrameRendered)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void startRelay(URTCSdkMixProfile uRTCSdkMixProfile) {
        this.f10912a.startRelay(UCloudRtcSdkMixProfile.getInstance().assembleMixParamsBuilder().type(uRTCSdkMixProfile.getType()).layout(uRTCSdkMixProfile.getLayout()).resolution(uRTCSdkMixProfile.getWidth(), uRTCSdkMixProfile.getHeight()).bgColor(uRTCSdkMixProfile.getBgColor()).frameRate(uRTCSdkMixProfile.getFrameRate()).bitRate(uRTCSdkMixProfile.getBitrate()).videoCodec(uRTCSdkMixProfile.getVideoCodec()).qualityLevel(uRTCSdkMixProfile.getQualityLevel()).audioCodec(uRTCSdkMixProfile.getAudioCodec()).mainViewUserId(uRTCSdkMixProfile.getMainViewUserId()).mainViewMediaType(uRTCSdkMixProfile.getMainViewType()).addStreamMode(uRTCSdkMixProfile.getStreamMode()).streams(uRTCSdkMixProfile.getStreams()).pushUrl(uRTCSdkMixProfile.getPushUrl()).build());
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int startRemoteView(URTCSdkStreamInfo uRTCSdkStreamInfo, Object obj, URTCSdkScaleType uRTCSdkScaleType, URTCFirstFrameRendered uRTCFirstFrameRendered) {
        return this.f10912a.startRemoteView(com.plv.rtc.urtc.a.c.a(uRTCSdkStreamInfo), obj, com.plv.rtc.urtc.a.c.a(uRTCSdkScaleType), new c(uRTCFirstFrameRendered)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int stopPreview(URTCSdkMediaType uRTCSdkMediaType) {
        return this.f10912a.stopPreview(com.plv.rtc.urtc.a.c.a(uRTCSdkMediaType)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void stopRelay(String[] strArr) {
        this.f10912a.stopRelay(strArr);
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int stopRemoteView(URTCSdkStreamInfo uRTCSdkStreamInfo) {
        return this.f10912a.stopRemoteView(com.plv.rtc.urtc.a.c.a(uRTCSdkStreamInfo)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int subscribe(URTCSdkStreamInfo uRTCSdkStreamInfo) {
        return this.f10912a.subscribe(com.plv.rtc.urtc.a.c.a(uRTCSdkStreamInfo)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int switchCamera() {
        return this.f10912a.switchCameraSkipSameSide().getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int unPublish(URTCSdkMediaType uRTCSdkMediaType) {
        return this.f10912a.unPublish(com.plv.rtc.urtc.a.c.a(uRTCSdkMediaType)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int unPublishOnly(URTCSdkMediaType uRTCSdkMediaType) {
        return this.f10912a.unPublishOnly(com.plv.rtc.urtc.a.c.a(uRTCSdkMediaType)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int unSubscribe(URTCSdkStreamInfo uRTCSdkStreamInfo) {
        return this.f10912a.unSubscribe(com.plv.rtc.urtc.a.c.a(uRTCSdkStreamInfo)).getErrorCode();
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public void updateMixConfig(URTCSdkMixProfile uRTCSdkMixProfile) {
        this.f10912a.updateMixConfig(UCloudRtcSdkMixProfile.getInstance().assembleMixParamsBuilder().type(uRTCSdkMixProfile.getType()).layout(uRTCSdkMixProfile.getLayout()).resolution(uRTCSdkMixProfile.getWidth(), uRTCSdkMixProfile.getHeight()).bgColor(uRTCSdkMixProfile.getBgColor()).frameRate(uRTCSdkMixProfile.getFrameRate()).bitRate(uRTCSdkMixProfile.getBitrate()).videoCodec(uRTCSdkMixProfile.getVideoCodec()).qualityLevel(uRTCSdkMixProfile.getQualityLevel()).audioCodec(uRTCSdkMixProfile.getAudioCodec()).mainViewUserId(uRTCSdkMixProfile.getMainViewUserId()).mainViewMediaType(uRTCSdkMixProfile.getMainViewType()).addStreamMode(uRTCSdkMixProfile.getStreamMode()).streams(uRTCSdkMixProfile.getStreams()).pushUrl(uRTCSdkMixProfile.getPushUrl()).build());
    }

    @Override // com.plv.rtc.urtc.PLVURTCEngine
    public int stopPreview(URTCSdkMediaType uRTCSdkMediaType, Object obj) {
        return this.f10912a.stopPreview(com.plv.rtc.urtc.a.c.a(uRTCSdkMediaType), obj).getErrorCode();
    }

    private void a() {
        if (PLVBeautyManager.getInstance().isBeautySupport()) {
            this.f10912a.setVideoPreProcessListener(new e());
        }
    }
}
