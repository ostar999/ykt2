package com.tencent.trtc;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import com.tencent.liteav.audio.TXAudioEffectManager;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.beauty.TXBeautyManager;
import com.tencent.liteav.device.TXDeviceManager;
import com.tencent.liteav.trtc.impl.TRTCCloudImpl;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public abstract class TRTCCloud {
    private static a mTXLogListener;
    static WeakReference<TRTCCloud> sInstance;

    @Deprecated
    public interface BGMNotify {
        void onBGMComplete(int i2);

        void onBGMProgress(long j2, long j3);

        void onBGMStart(int i2);
    }

    public static class TRTCViewMargin {
        public float bottomMargin;
        public float leftMargin;
        public float rightMargin;
        public float topMargin;

        public TRTCViewMargin(float f2, float f3, float f4, float f5) {
            this.leftMargin = f2;
            this.topMargin = f4;
            this.rightMargin = f3;
            this.bottomMargin = f5;
        }
    }

    public static class a implements TXCLog.a {

        /* renamed from: a, reason: collision with root package name */
        private TRTCCloudListener.TRTCLogListener f22289a = null;

        public void a(TRTCCloudListener.TRTCLogListener tRTCLogListener) {
            this.f22289a = tRTCLogListener;
        }

        @Override // com.tencent.liteav.basic.log.TXCLog.a
        public void a(int i2, String str, String str2) {
            TRTCCloudListener.TRTCLogListener tRTCLogListener = this.f22289a;
            if (tRTCLogListener != null) {
                tRTCLogListener.onLog(str2, i2, str);
            }
        }
    }

    public static void destroySharedInstance() {
        TRTCCloudImpl.destroySharedInstance();
    }

    public static String getSDKVersion() {
        return TXCCommonUtil.getSDKVersionStr();
    }

    public static void setConsoleEnabled(boolean z2) {
        TXCLog.setConsoleEnabled(z2);
    }

    public static void setLogCompressEnabled(boolean z2) {
        TXCLog.setLogCompressEnabled(z2);
    }

    public static void setLogDirPath(String str) {
        TXCLog.setLogDirPath(str);
    }

    public static void setLogLevel(int i2) {
        TXCLog.setLevel(i2);
    }

    public static void setLogListener(TRTCCloudListener.TRTCLogListener tRTCLogListener) {
        a aVar = mTXLogListener;
        if (aVar != null) {
            aVar.a(null);
        }
        if (tRTCLogListener != null) {
            a aVar2 = new a();
            mTXLogListener = aVar2;
            aVar2.a(tRTCLogListener);
        } else {
            mTXLogListener = null;
        }
        TXCLog.setListener(mTXLogListener);
    }

    public static native void setNetEnv(int i2);

    public static TRTCCloud sharedInstance(Context context) {
        return TRTCCloudImpl.sharedInstance(context);
    }

    public abstract void ConnectOtherRoom(String str);

    public abstract void DisconnectOtherRoom();

    public abstract void callExperimentalAPI(String str);

    public abstract int checkAudioCapabilitySupport(int i2);

    public abstract TRTCCloud createSubCloud();

    public abstract void destroySubCloud(TRTCCloud tRTCCloud);

    public abstract void enableAudioEarMonitoring(boolean z2);

    public abstract void enableAudioVolumeEvaluation(int i2);

    public abstract void enableCustomAudioCapture(boolean z2);

    public abstract void enableCustomAudioRendering(boolean z2);

    public abstract void enableCustomVideoCapture(int i2, boolean z2);

    @Deprecated
    public abstract void enableCustomVideoCapture(boolean z2);

    public abstract int enableEncSmallVideoStream(boolean z2, TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam);

    public abstract void enableMixExternalAudioFrame(boolean z2, boolean z3);

    @Deprecated
    public abstract boolean enableTorch(boolean z2);

    public abstract void enterRoom(TRTCCloudDef.TRTCParams tRTCParams, int i2);

    public abstract void exitRoom();

    public abstract long generateCustomPTS();

    public abstract int getAudioCaptureVolume();

    public abstract TXAudioEffectManager getAudioEffectManager();

    public abstract int getAudioPlayoutVolume();

    @Deprecated
    public abstract int getBGMDuration(String str);

    public abstract TXBeautyManager getBeautyManager();

    public abstract void getCustomAudioRenderingFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame);

    public abstract TXDeviceManager getDeviceManager();

    @Deprecated
    public abstract boolean isCameraAutoFocusFaceModeSupported();

    @Deprecated
    public abstract boolean isCameraFocusPositionInPreviewSupported();

    @Deprecated
    public abstract boolean isCameraTorchSupported();

    @Deprecated
    public abstract boolean isCameraZoomSupported();

    public abstract int mixExternalAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame);

    public abstract void muteAllRemoteAudio(boolean z2);

    public abstract void muteAllRemoteVideoStreams(boolean z2);

    public abstract void muteLocalAudio(boolean z2);

    public abstract void muteLocalVideo(int i2, boolean z2);

    @Deprecated
    public abstract void muteLocalVideo(boolean z2);

    public abstract void muteRemoteAudio(String str, boolean z2);

    public abstract void muteRemoteVideoStream(String str, int i2, boolean z2);

    @Deprecated
    public abstract void muteRemoteVideoStream(String str, boolean z2);

    @Deprecated
    public abstract void pauseAudioEffect(int i2);

    @Deprecated
    public abstract void pauseBGM();

    public abstract void pauseScreenCapture();

    @Deprecated
    public abstract void playAudioEffect(TRTCCloudDef.TRTCAudioEffectParam tRTCAudioEffectParam);

    @Deprecated
    public abstract void playBGM(String str, BGMNotify bGMNotify);

    @Deprecated
    public abstract void resumeAudioEffect(int i2);

    @Deprecated
    public abstract void resumeBGM();

    public abstract void resumeScreenCapture();

    @Deprecated
    public abstract void selectMotionTmpl(String str);

    public abstract void sendCustomAudioData(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame);

    public abstract boolean sendCustomCmdMsg(int i2, byte[] bArr, boolean z2, boolean z3);

    public abstract void sendCustomVideoData(int i2, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame);

    @Deprecated
    public abstract void sendCustomVideoData(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame);

    public abstract boolean sendSEIMsg(byte[] bArr, int i2);

    @Deprecated
    public abstract void setAllAudioEffectsVolume(int i2);

    public abstract void setAudioCaptureVolume(int i2);

    @Deprecated
    public abstract void setAudioEffectVolume(int i2, int i3);

    public abstract void setAudioFrameListener(TRTCCloudListener.TRTCAudioFrameListener tRTCAudioFrameListener);

    public abstract void setAudioPlayoutVolume(int i2);

    @Deprecated
    public abstract void setAudioQuality(int i2);

    public abstract void setAudioRoute(int i2);

    @Deprecated
    public abstract void setBGMPlayoutVolume(int i2);

    @Deprecated
    public abstract int setBGMPosition(int i2);

    @Deprecated
    public abstract void setBGMPublishVolume(int i2);

    @Deprecated
    public abstract void setBGMVolume(int i2);

    @Deprecated
    public abstract void setBeautyStyle(int i2, int i3, int i4, int i5);

    public abstract int setCapturedRawAudioFrameCallbackFormat(TRTCCloudDef.TRTCAudioFrameCallbackFormat tRTCAudioFrameCallbackFormat);

    @Deprecated
    public abstract void setChinLevel(int i2);

    public abstract void setDebugViewMargin(String str, TRTCViewMargin tRTCViewMargin);

    public abstract void setDefaultStreamRecvMode(boolean z2, boolean z3);

    @Deprecated
    public abstract void setEyeScaleLevel(int i2);

    @Deprecated
    public abstract void setFaceShortLevel(int i2);

    @Deprecated
    public abstract void setFaceSlimLevel(int i2);

    @Deprecated
    public abstract void setFaceVLevel(int i2);

    @Deprecated
    public abstract void setFilter(Bitmap bitmap);

    @Deprecated
    public abstract void setFilterConcentration(float f2);

    @Deprecated
    public abstract void setFocusPosition(int i2, int i3);

    public abstract void setGSensorMode(int i2);

    @Deprecated
    public abstract boolean setGreenScreenFile(String str);

    public abstract void setListener(TRTCCloudListener tRTCCloudListener);

    public abstract void setListenerHandler(Handler handler);

    public abstract int setLocalProcessedAudioFrameCallbackFormat(TRTCCloudDef.TRTCAudioFrameCallbackFormat tRTCAudioFrameCallbackFormat);

    public abstract void setLocalRenderParams(TRTCCloudDef.TRTCRenderParams tRTCRenderParams);

    public abstract int setLocalVideoProcessListener(int i2, int i3, TRTCCloudListener.TRTCVideoFrameListener tRTCVideoFrameListener);

    public abstract int setLocalVideoRenderListener(int i2, int i3, TRTCCloudListener.TRTCVideoRenderListener tRTCVideoRenderListener);

    @Deprecated
    public abstract void setLocalViewFillMode(int i2);

    @Deprecated
    public abstract void setLocalViewMirror(int i2);

    @Deprecated
    public abstract void setLocalViewRotation(int i2);

    @Deprecated
    public abstract void setMicVolumeOnMixing(int i2);

    public abstract void setMixExternalAudioVolume(int i2, int i3);

    public abstract void setMixTranscodingConfig(TRTCCloudDef.TRTCTranscodingConfig tRTCTranscodingConfig);

    public abstract int setMixedPlayAudioFrameCallbackFormat(TRTCCloudDef.TRTCAudioFrameCallbackFormat tRTCAudioFrameCallbackFormat);

    @Deprecated
    public abstract void setMotionMute(boolean z2);

    public abstract void setNetworkQosParam(TRTCCloudDef.TRTCNetworkQosParam tRTCNetworkQosParam);

    @Deprecated
    public abstract void setNoseSlimLevel(int i2);

    @Deprecated
    public abstract int setPriorRemoteVideoStreamType(int i2);

    public abstract void setRemoteAudioVolume(String str, int i2);

    public abstract void setRemoteRenderParams(String str, int i2, TRTCCloudDef.TRTCRenderParams tRTCRenderParams);

    @Deprecated
    public abstract void setRemoteSubStreamViewFillMode(String str, int i2);

    @Deprecated
    public abstract void setRemoteSubStreamViewRotation(String str, int i2);

    public abstract int setRemoteVideoRenderListener(String str, int i2, int i3, TRTCCloudListener.TRTCVideoRenderListener tRTCVideoRenderListener);

    public abstract int setRemoteVideoStreamType(String str, int i2);

    @Deprecated
    public abstract void setRemoteViewFillMode(String str, int i2);

    @Deprecated
    public abstract void setRemoteViewRotation(String str, int i2);

    @Deprecated
    public abstract void setReverbType(int i2);

    public abstract void setSubStreamEncoderParam(TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam);

    public abstract void setSystemVolumeType(int i2);

    public abstract void setVideoEncoderMirror(boolean z2);

    public abstract void setVideoEncoderParam(TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam);

    public abstract void setVideoEncoderRotation(int i2);

    public abstract void setVideoMuteImage(Bitmap bitmap, int i2);

    @Deprecated
    public abstract boolean setVoiceChangerType(int i2);

    public abstract void setWatermark(Bitmap bitmap, int i2, float f2, float f3, float f4);

    @Deprecated
    public abstract void setZoom(int i2);

    public abstract void showDebugView(int i2);

    public abstract void snapshotVideo(String str, int i2, TRTCCloudListener.TRTCSnapshotListener tRTCSnapshotListener);

    public abstract int startAudioRecording(TRTCCloudDef.TRTCAudioRecordingParams tRTCAudioRecordingParams);

    @Deprecated
    public abstract void startLocalAudio();

    public abstract void startLocalAudio(int i2);

    public abstract void startLocalPreview(boolean z2, TXCloudVideoView tXCloudVideoView);

    public abstract void startLocalRecording(TRTCCloudDef.TRTCLocalRecordingParams tRTCLocalRecordingParams);

    public abstract void startPublishCDNStream(TRTCCloudDef.TRTCPublishCDNParam tRTCPublishCDNParam);

    public abstract void startPublishing(String str, int i2);

    @Deprecated
    public abstract void startRemoteSubStreamView(String str, TXCloudVideoView tXCloudVideoView);

    public abstract void startRemoteView(String str, int i2, TXCloudVideoView tXCloudVideoView);

    @Deprecated
    public abstract void startRemoteView(String str, TXCloudVideoView tXCloudVideoView);

    public abstract void startScreenCapture(int i2, TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam, TRTCCloudDef.TRTCScreenShareParams tRTCScreenShareParams);

    @Deprecated
    public abstract void startScreenCapture(TRTCCloudDef.TRTCVideoEncParam tRTCVideoEncParam, TRTCCloudDef.TRTCScreenShareParams tRTCScreenShareParams);

    public abstract int startSpeedTest(TRTCCloudDef.TRTCSpeedTestParams tRTCSpeedTestParams);

    @Deprecated
    public abstract void startSpeedTest(int i2, String str, String str2);

    @Deprecated
    public abstract void stopAllAudioEffects();

    public abstract void stopAllRemoteView();

    @Deprecated
    public abstract void stopAudioEffect(int i2);

    public abstract void stopAudioRecording();

    @Deprecated
    public abstract void stopBGM();

    public abstract void stopLocalAudio();

    public abstract void stopLocalPreview();

    public abstract void stopLocalRecording();

    public abstract void stopPublishCDNStream();

    public abstract void stopPublishing();

    @Deprecated
    public abstract void stopRemoteSubStreamView(String str);

    @Deprecated
    public abstract void stopRemoteView(String str);

    public abstract void stopRemoteView(String str, int i2);

    public abstract void stopScreenCapture();

    public abstract void stopSpeedTest();

    @Deprecated
    public abstract void switchCamera();

    public abstract void switchRole(int i2);

    public abstract void switchRoom(TRTCCloudDef.TRTCSwitchRoomConfig tRTCSwitchRoomConfig);

    public abstract void updateLocalView(TXCloudVideoView tXCloudVideoView);

    public abstract void updateRemoteView(String str, int i2, TXCloudVideoView tXCloudVideoView);
}
