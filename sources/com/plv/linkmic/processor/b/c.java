package com.plv.linkmic.processor.b;

import android.os.Bundle;
import com.plv.rtc.trtc.PLVTRTCDef;
import com.plv.rtc.trtc.PLVTRTCEventListener;
import java.util.ArrayList;

/* loaded from: classes4.dex */
public abstract class c extends PLVTRTCEventListener {
    private PLVTRTCEventListener S;

    public c(PLVTRTCEventListener pLVTRTCEventListener) {
        this.S = pLVTRTCEventListener;
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onAudioRouteChanged(int i2, int i3) {
        this.S.onAudioRouteChanged(i2, i3);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onCameraDidReady() {
        this.S.onCameraDidReady();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onConnectOtherRoom(String str, int i2, String str2) {
        this.S.onConnectOtherRoom(str, i2, str2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onConnectionLost() {
        this.S.onConnectionLost();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onConnectionRecovery() {
        this.S.onConnectionRecovery();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onDisConnectOtherRoom(int i2, String str) {
        this.S.onDisConnectOtherRoom(i2, str);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onEnterRoom(long j2) {
        this.S.onEnterRoom(j2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onError(int i2, String str, Bundle bundle) {
        this.S.onError(i2, str, bundle);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onExitRoom(int i2) {
        this.S.onExitRoom(i2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onFirstAudioFrame(String str) {
        this.S.onFirstAudioFrame(str);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onFirstVideoFrame(String str, int i2, int i3, int i4) {
        this.S.onFirstVideoFrame(str, i2, i3, i4);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onMicDidReady() {
        this.S.onMicDidReady();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onMissCustomCmdMsg(String str, int i2, int i3, int i4) {
        this.S.onMissCustomCmdMsg(str, i2, i3, i4);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onNetworkQuality(PLVTRTCDef.TRTCQuality tRTCQuality, ArrayList<PLVTRTCDef.TRTCQuality> arrayList) {
        this.S.onNetworkQuality(tRTCQuality, arrayList);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onRecvCustomCmdMsg(String str, int i2, int i3, byte[] bArr) {
        this.S.onRecvCustomCmdMsg(str, i2, i3, bArr);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onRecvSEIMsg(String str, byte[] bArr) {
        this.S.onRecvSEIMsg(str, bArr);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onRemoteUserEnterRoom(String str) {
        this.S.onRemoteUserEnterRoom(str);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onRemoteUserLeaveRoom(String str, int i2) {
        this.S.onRemoteUserLeaveRoom(str, i2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onScreenCapturePaused() {
        this.S.onScreenCapturePaused();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onScreenCaptureResumed() {
        this.S.onScreenCaptureResumed();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onScreenCaptureStarted() {
        this.S.onScreenCaptureStarted();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onScreenCaptureStopped(int i2) {
        this.S.onScreenCaptureStopped(i2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onSendFirstLocalAudioFrame() {
        this.S.onSendFirstLocalAudioFrame();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onSendFirstLocalVideoFrame(int i2) {
        this.S.onSendFirstLocalVideoFrame(i2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onSetMixTranscodingConfig(int i2, String str) {
        this.S.onSetMixTranscodingConfig(i2, str);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onStartPublishCDNStream(int i2, String str) {
        this.S.onStartPublishCDNStream(i2, str);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onStartPublishing(int i2, String str) {
        this.S.onStartPublishing(i2, str);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onStopPublishCDNStream(int i2, String str) {
        this.S.onStopPublishCDNStream(i2, str);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onStopPublishing(int i2, String str) {
        this.S.onStopPublishing(i2, str);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onSwitchRole(int i2, String str) {
        this.S.onSwitchRole(i2, str);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onSwitchRoom(int i2, String str) {
        this.S.onSwitchRoom(i2, str);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onTryToReconnect() {
        this.S.onTryToReconnect();
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onUserAudioAvailable(String str, boolean z2) {
        this.S.onUserAudioAvailable(str, z2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onUserSubStreamAvailable(String str, boolean z2) {
        this.S.onUserSubStreamAvailable(str, z2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onUserVideoAvailable(String str, boolean z2) {
        this.S.onUserVideoAvailable(str, z2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onUserVoiceVolume(ArrayList<PLVTRTCDef.TRTCVolumeInfo> arrayList, int i2) {
        this.S.onUserVoiceVolume(arrayList, i2);
    }

    @Override // com.plv.rtc.trtc.PLVTRTCEventListener
    public void onWarning(int i2, String str, Bundle bundle) {
        this.S.onWarning(i2, str, bundle);
    }
}
