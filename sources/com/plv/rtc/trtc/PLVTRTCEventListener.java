package com.plv.rtc.trtc;

import android.os.Bundle;
import com.plv.rtc.trtc.PLVTRTCDef;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class PLVTRTCEventListener {
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

    public void onMicDidReady() {
    }

    public void onMissCustomCmdMsg(String str, int i2, int i3, int i4) {
    }

    public void onNetworkQuality(PLVTRTCDef.TRTCQuality tRTCQuality, ArrayList<PLVTRTCDef.TRTCQuality> arrayList) {
    }

    public void onRecvCustomCmdMsg(String str, int i2, int i3, byte[] bArr) {
    }

    public void onRecvSEIMsg(String str, byte[] bArr) {
    }

    public void onRemoteUserEnterRoom(String str) {
    }

    public void onRemoteUserLeaveRoom(String str, int i2) {
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

    public void onStartPublishCDNStream(int i2, String str) {
    }

    public void onStartPublishing(int i2, String str) {
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

    public void onUserSubStreamAvailable(String str, boolean z2) {
    }

    public void onUserVideoAvailable(String str, boolean z2) {
    }

    public void onUserVoiceVolume(ArrayList<PLVTRTCDef.TRTCVolumeInfo> arrayList, int i2) {
    }

    public void onWarning(int i2, String str, Bundle bundle) {
    }
}
