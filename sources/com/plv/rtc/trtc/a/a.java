package com.plv.rtc.trtc.a;

import android.os.Bundle;
import com.plv.rtc.trtc.PLVTRTCDef;
import com.plv.rtc.trtc.PLVTRTCEventListener;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class a extends TRTCCloudListener {

    /* renamed from: a, reason: collision with root package name */
    private PLVTRTCEventListener f10901a;

    public a(PLVTRTCEventListener pLVTRTCEventListener) {
        this.f10901a = pLVTRTCEventListener;
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onAudioRouteChanged(int i2, int i3) {
        this.f10901a.onAudioRouteChanged(i2, i3);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onCameraDidReady() {
        this.f10901a.onCameraDidReady();
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onConnectOtherRoom(String str, int i2, String str2) {
        this.f10901a.onConnectOtherRoom(str, i2, str2);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onConnectionLost() {
        this.f10901a.onConnectionLost();
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onConnectionRecovery() {
        this.f10901a.onConnectionRecovery();
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onDisConnectOtherRoom(int i2, String str) {
        this.f10901a.onDisConnectOtherRoom(i2, str);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onEnterRoom(long j2) {
        this.f10901a.onEnterRoom(j2);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onError(int i2, String str, Bundle bundle) {
        this.f10901a.onError(i2, str, bundle);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onExitRoom(int i2) {
        this.f10901a.onExitRoom(i2);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onFirstAudioFrame(String str) {
        this.f10901a.onFirstAudioFrame(str);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onFirstVideoFrame(String str, int i2, int i3, int i4) {
        this.f10901a.onFirstVideoFrame(str, i2, i3, i4);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onMicDidReady() {
        this.f10901a.onMicDidReady();
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onMissCustomCmdMsg(String str, int i2, int i3, int i4) {
        this.f10901a.onMissCustomCmdMsg(str, i2, i3, i4);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onNetworkQuality(TRTCCloudDef.TRTCQuality tRTCQuality, ArrayList<TRTCCloudDef.TRTCQuality> arrayList) {
        PLVTRTCDef.TRTCQuality tRTCQuality2 = new PLVTRTCDef.TRTCQuality();
        tRTCQuality2.quality = tRTCQuality.quality;
        tRTCQuality2.userId = tRTCQuality.userId;
        ArrayList<PLVTRTCDef.TRTCQuality> arrayList2 = new ArrayList<>(arrayList.size());
        Iterator<TRTCCloudDef.TRTCQuality> it = arrayList.iterator();
        while (it.hasNext()) {
            TRTCCloudDef.TRTCQuality next = it.next();
            PLVTRTCDef.TRTCQuality tRTCQuality3 = new PLVTRTCDef.TRTCQuality();
            tRTCQuality3.quality = next.quality;
            tRTCQuality3.userId = next.userId;
            arrayList2.add(tRTCQuality3);
        }
        this.f10901a.onNetworkQuality(tRTCQuality2, arrayList2);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onRecvCustomCmdMsg(String str, int i2, int i3, byte[] bArr) {
        this.f10901a.onRecvCustomCmdMsg(str, i2, i3, bArr);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onRecvSEIMsg(String str, byte[] bArr) {
        this.f10901a.onRecvSEIMsg(str, bArr);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onRemoteUserEnterRoom(String str) {
        this.f10901a.onRemoteUserEnterRoom(str);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onRemoteUserLeaveRoom(String str, int i2) {
        this.f10901a.onRemoteUserLeaveRoom(str, i2);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onScreenCapturePaused() {
        this.f10901a.onScreenCapturePaused();
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onScreenCaptureResumed() {
        this.f10901a.onScreenCaptureResumed();
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onScreenCaptureStarted() {
        this.f10901a.onScreenCaptureStarted();
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onScreenCaptureStopped(int i2) {
        this.f10901a.onScreenCaptureStopped(i2);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onSendFirstLocalAudioFrame() {
        this.f10901a.onSendFirstLocalAudioFrame();
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onSendFirstLocalVideoFrame(int i2) {
        this.f10901a.onSendFirstLocalVideoFrame(i2);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onSetMixTranscodingConfig(int i2, String str) {
        this.f10901a.onSetMixTranscodingConfig(i2, str);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onStartPublishCDNStream(int i2, String str) {
        this.f10901a.onStartPublishCDNStream(i2, str);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onStartPublishing(int i2, String str) {
        this.f10901a.onStartPublishing(i2, str);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onStopPublishCDNStream(int i2, String str) {
        this.f10901a.onStopPublishCDNStream(i2, str);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onStopPublishing(int i2, String str) {
        this.f10901a.onStopPublishing(i2, str);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onSwitchRole(int i2, String str) {
        this.f10901a.onSwitchRole(i2, str);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onSwitchRoom(int i2, String str) {
        this.f10901a.onSwitchRoom(i2, str);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onTryToReconnect() {
        this.f10901a.onTryToReconnect();
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onUserAudioAvailable(String str, boolean z2) {
        this.f10901a.onUserAudioAvailable(str, z2);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onUserSubStreamAvailable(String str, boolean z2) {
        this.f10901a.onUserSubStreamAvailable(str, z2);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onUserVideoAvailable(String str, boolean z2) {
        this.f10901a.onUserVideoAvailable(str, z2);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onUserVoiceVolume(ArrayList<TRTCCloudDef.TRTCVolumeInfo> arrayList, int i2) {
        ArrayList<PLVTRTCDef.TRTCVolumeInfo> arrayList2 = new ArrayList<>(arrayList.size());
        Iterator<TRTCCloudDef.TRTCVolumeInfo> it = arrayList.iterator();
        while (it.hasNext()) {
            TRTCCloudDef.TRTCVolumeInfo next = it.next();
            PLVTRTCDef.TRTCVolumeInfo tRTCVolumeInfo = new PLVTRTCDef.TRTCVolumeInfo();
            tRTCVolumeInfo.volume = next.volume;
            tRTCVolumeInfo.userId = next.userId;
            arrayList2.add(tRTCVolumeInfo);
        }
        this.f10901a.onUserVoiceVolume(arrayList2, i2);
    }

    @Override // com.tencent.trtc.TRTCCloudListener
    public void onWarning(int i2, String str, Bundle bundle) {
        this.f10901a.onWarning(i2, str, bundle);
    }
}
