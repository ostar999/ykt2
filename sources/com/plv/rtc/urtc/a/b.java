package com.plv.rtc.urtc.a;

import com.plv.rtc.urtc.listener.URtcSdkEventListener;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkAudioDevice;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMediaServiceStatus;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMediaType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkNetWorkQuality;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStats;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamInfo;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkTrackType;
import com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener;

/* loaded from: classes5.dex */
public class b implements UCloudRtcSdkEventListener {

    /* renamed from: a, reason: collision with root package name */
    private URtcSdkEventListener f10928a;

    public b(URtcSdkEventListener uRtcSdkEventListener) {
        this.f10928a = uRtcSdkEventListener;
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onAddStreams(int i2, String str) {
        this.f10928a.onAddStreams(i2, str);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onAudioDeviceChanged(UCloudRtcSdkAudioDevice uCloudRtcSdkAudioDevice) {
        this.f10928a.onAudioDeviceChanged(c.a(uCloudRtcSdkAudioDevice));
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onAudioFileFinish() {
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onDelStreams(int i2, String str) {
        this.f10928a.onDelStreams(i2, str);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onError(int i2) {
        this.f10928a.onError(i2);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onFirstLocalVideoFrame() {
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onJoinRoomResult(int i2, String str, String str2) {
        this.f10928a.onJoinRoomResult(i2, str, str2);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onKickoff(int i2) {
        this.f10928a.onKickoff(i2);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onLeaveRoomResult(int i2, String str, String str2) {
        this.f10928a.onLeaveRoomResult(i2, str, str2);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onLocalAudioLevel(int i2) {
        this.f10928a.onLocalAudioLevel(i2);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onLocalPublish(int i2, String str, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        this.f10928a.onLocalPublish(i2, str, c.a(uCloudRtcSdkStreamInfo));
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onLocalStreamMuteRsp(int i2, String str, UCloudRtcSdkMediaType uCloudRtcSdkMediaType, UCloudRtcSdkTrackType uCloudRtcSdkTrackType, boolean z2) {
        this.f10928a.onLocalStreamMuteRsp(i2, str, c.a(uCloudRtcSdkMediaType), c.a(uCloudRtcSdkTrackType), z2);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onLocalUnPublish(int i2, String str, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        this.f10928a.onLocalUnPublish(i2, str, c.a(uCloudRtcSdkStreamInfo));
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onLocalUnPublishOnly(int i2, String str, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        this.f10928a.onLocalUnPublishOnly(i2, str, c.a(uCloudRtcSdkStreamInfo));
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onLogOffNotify(int i2, String str) {
        this.f10928a.onLogOffNotify(i2, str);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onLogOffUsers(int i2, String str) {
        this.f10928a.onLogOffUsers(i2, str);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onMsgNotify(int i2, String str) {
        this.f10928a.onMsgNotify(i2, str);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onNetWorkQuality(String str, UCloudRtcSdkStreamType uCloudRtcSdkStreamType, UCloudRtcSdkMediaType uCloudRtcSdkMediaType, UCloudRtcSdkNetWorkQuality uCloudRtcSdkNetWorkQuality) {
        this.f10928a.onNetWorkQuality(str, c.a(uCloudRtcSdkStreamType), c.a(uCloudRtcSdkMediaType), c.a(uCloudRtcSdkNetWorkQuality));
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onPeerLostConnection(int i2, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        this.f10928a.onPeerLostConnection(i2, c.a(uCloudRtcSdkStreamInfo));
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onPeerReconnected(int i2, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onQueryMix(int i2, String str, int i3, String str2, String str3) {
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRecordStart(int i2, String str) {
        this.f10928a.onRecordStart(i2, str);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRecordStatusNotify(UCloudRtcSdkMediaServiceStatus uCloudRtcSdkMediaServiceStatus, int i2, String str, String str2, String str3, String str4, String str5) {
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRecordStop(int i2) {
        this.f10928a.onRecordStop(i2);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRejoinRoomResult(String str) {
        this.f10928a.onRejoinRoomResult(str);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRejoiningRoom(String str) {
        this.f10928a.onRejoiningRoom(str);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRelayStatusNotify(UCloudRtcSdkMediaServiceStatus uCloudRtcSdkMediaServiceStatus, int i2, String str, String str2, String str3, String str4, String[] strArr) {
        this.f10928a.onRelayStatusNotify(c.a(uCloudRtcSdkMediaServiceStatus), i2, str, str2, str3, str4, strArr);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRemoteAudioLevel(String str, int i2) {
        this.f10928a.onRemoteAudioLevel(str, i2);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRemotePublish(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        this.f10928a.onRemotePublish(c.a(uCloudRtcSdkStreamInfo));
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRemoteRTCStatus(UCloudRtcSdkStats uCloudRtcSdkStats) {
        this.f10928a.onRemoteRTCStats(c.a(uCloudRtcSdkStats));
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRemoteStreamMuteRsp(int i2, String str, String str2, UCloudRtcSdkMediaType uCloudRtcSdkMediaType, UCloudRtcSdkTrackType uCloudRtcSdkTrackType, boolean z2) {
        this.f10928a.onRemoteStreamMuteRsp(i2, str, str2, c.a(uCloudRtcSdkMediaType), c.a(uCloudRtcSdkTrackType), z2);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRemoteTrackNotify(String str, UCloudRtcSdkMediaType uCloudRtcSdkMediaType, UCloudRtcSdkTrackType uCloudRtcSdkTrackType, boolean z2) {
        this.f10928a.onRemoteTrackNotify(str, c.a(uCloudRtcSdkMediaType), c.a(uCloudRtcSdkTrackType), z2);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRemoteUnPublish(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        this.f10928a.onRemoteUnPublish(c.a(uCloudRtcSdkStreamInfo));
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRemoteUserJoin(String str) {
        this.f10928a.onRemoteUserJoin(str);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onRemoteUserLeave(String str, int i2) {
        this.f10928a.onRemoteUserLeave(str, i2);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onSendRTCStatus(UCloudRtcSdkStats uCloudRtcSdkStats) {
        this.f10928a.onSendRTCStats(c.a(uCloudRtcSdkStats));
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onServerBroadCastMsg(String str, String str2) {
        this.f10928a.onServerBroadCastMsg(str, str2);
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onServerDisconnect() {
        this.f10928a.onServerDisconnect();
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onStartLocalRenderFailed(String str) {
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onSubscribeResult(int i2, String str, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        this.f10928a.onSubscribeResult(i2, str, c.a(uCloudRtcSdkStreamInfo));
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onUnSubscribeResult(int i2, String str, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        this.f10928a.onUnSubscribeResult(i2, str, c.a(uCloudRtcSdkStreamInfo));
    }

    @Override // com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener
    public void onWarning(int i2) {
        this.f10928a.onWarning(i2);
    }
}
