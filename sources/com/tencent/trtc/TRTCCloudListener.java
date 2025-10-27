package com.tencent.trtc;

import android.graphics.Bitmap;
import android.os.Bundle;
import com.tencent.trtc.TRTCCloudDef;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public abstract class TRTCCloudListener {

    public interface TRTCAudioFrameListener {
        void onCapturedRawAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame);

        void onLocalProcessedAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame);

        void onMixedAllAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame);

        void onMixedPlayAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame);

        void onRemoteUserAudioFrame(TRTCCloudDef.TRTCAudioFrame tRTCAudioFrame, String str);
    }

    public static abstract class TRTCLogListener {
        public abstract void onLog(String str, int i2, String str2);
    }

    public interface TRTCSnapshotListener {
        void onSnapshotComplete(Bitmap bitmap);
    }

    public interface TRTCVideoFrameListener {
        void onGLContextCreated();

        void onGLContextDestory();

        int onProcessVideoFrame(TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame2);
    }

    public interface TRTCVideoRenderListener {
        void onRenderVideoFrame(String str, int i2, TRTCCloudDef.TRTCVideoFrame tRTCVideoFrame);
    }

    @Deprecated
    public void onAudioEffectFinished(int i2, int i3) {
    }

    public void onAudioRouteChanged(int i2, int i3) {
    }

    public void onCameraDidReady() {
    }

    public void onConnectOtherRoom(String str, int i2, String str2) {
    }

    public void onConnectionLost() {
    }

    public void onConnectionRecovery() {
    }

    public void onDisConnectOtherRoom(int i2, String str) {
    }

    public void onEnterRoom(long j2) {
    }

    public void onError(int i2, String str, Bundle bundle) {
    }

    public void onExitRoom(int i2) {
    }

    public void onFirstAudioFrame(String str) {
    }

    public void onFirstVideoFrame(String str, int i2, int i3, int i4) {
    }

    public void onLocalRecordBegin(int i2, String str) {
    }

    public void onLocalRecordComplete(int i2, String str) {
    }

    public void onLocalRecording(long j2, String str) {
    }

    public void onMicDidReady() {
    }

    public void onMissCustomCmdMsg(String str, int i2, int i3, int i4) {
    }

    public void onNetworkQuality(TRTCCloudDef.TRTCQuality tRTCQuality, ArrayList<TRTCCloudDef.TRTCQuality> arrayList) {
    }

    public void onRecvCustomCmdMsg(String str, int i2, int i3, byte[] bArr) {
    }

    public void onRecvSEIMsg(String str, byte[] bArr) {
    }

    public void onRemoteUserEnterRoom(String str) {
    }

    public void onRemoteUserLeaveRoom(String str, int i2) {
    }

    public void onRemoteVideoStatusUpdated(String str, int i2, int i3, int i4, Bundle bundle) {
    }

    public void onScreenCapturePaused() {
    }

    public void onScreenCaptureResumed() {
    }

    public void onScreenCaptureStarted() {
    }

    public void onScreenCaptureStopped(int i2) {
    }

    public void onSendFirstLocalAudioFrame() {
    }

    public void onSendFirstLocalVideoFrame(int i2) {
    }

    public void onSetMixTranscodingConfig(int i2, String str) {
    }

    public void onSpeedTest(TRTCCloudDef.TRTCSpeedTestResult tRTCSpeedTestResult, int i2, int i3) {
    }

    public void onSpeedTestResult(TRTCCloudDef.TRTCSpeedTestResult tRTCSpeedTestResult) {
    }

    public void onStartPublishCDNStream(int i2, String str) {
    }

    public void onStartPublishing(int i2, String str) {
    }

    public void onStatistics(TRTCStatistics tRTCStatistics) {
    }

    public void onStopPublishCDNStream(int i2, String str) {
    }

    public void onStopPublishing(int i2, String str) {
    }

    public void onSwitchRole(int i2, String str) {
    }

    public void onSwitchRoom(int i2, String str) {
    }

    public void onTryToReconnect() {
    }

    public void onUserAudioAvailable(String str, boolean z2) {
    }

    @Deprecated
    public void onUserEnter(String str) {
    }

    @Deprecated
    public void onUserExit(String str, int i2) {
    }

    public void onUserSubStreamAvailable(String str, boolean z2) {
    }

    public void onUserVideoAvailable(String str, boolean z2) {
    }

    public void onUserVoiceVolume(ArrayList<TRTCCloudDef.TRTCVolumeInfo> arrayList, int i2) {
    }

    public void onWarning(int i2, String str, Bundle bundle) {
    }
}
