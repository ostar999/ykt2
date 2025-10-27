package com.plv.linkmic.processor.c;

import com.plv.rtc.urtc.enummeration.URTCSdkAudioDevice;
import com.plv.rtc.urtc.enummeration.URTCSdkMediaServerStatus;
import com.plv.rtc.urtc.enummeration.URTCSdkMediaType;
import com.plv.rtc.urtc.enummeration.URTCSdkNetWorkQuality;
import com.plv.rtc.urtc.enummeration.URTCSdkStreamType;
import com.plv.rtc.urtc.enummeration.URTCSdkTrackType;
import com.plv.rtc.urtc.listener.URtcSdkEventListener;
import com.plv.rtc.urtc.model.URTCSdkStats;
import com.plv.rtc.urtc.model.URTCSdkStreamInfo;

/* loaded from: classes4.dex */
public class c implements URtcSdkEventListener {
    private URtcSdkEventListener aK;

    public c(URtcSdkEventListener uRtcSdkEventListener) {
        this.aK = uRtcSdkEventListener;
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onAddStreams(int i2, String str) {
        this.aK.onAddStreams(i2, str);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onAudioDeviceChanged(URTCSdkAudioDevice uRTCSdkAudioDevice) {
        this.aK.onAudioDeviceChanged(uRTCSdkAudioDevice);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onDelStreams(int i2, String str) {
        this.aK.onDelStreams(i2, str);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onError(int i2) {
        this.aK.onError(i2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onJoinRoomResult(int i2, String str, String str2) {
        this.aK.onJoinRoomResult(i2, str, str2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onKickoff(int i2) {
        this.aK.onKickoff(i2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onLeaveRoomResult(int i2, String str, String str2) {
        this.aK.onLeaveRoomResult(i2, str, str2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onLocalAudioLevel(int i2) {
        this.aK.onLocalAudioLevel(i2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onLocalPublish(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo) {
        this.aK.onLocalPublish(i2, str, uRTCSdkStreamInfo);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onLocalStreamMuteRsp(int i2, String str, URTCSdkMediaType uRTCSdkMediaType, URTCSdkTrackType uRTCSdkTrackType, boolean z2) {
        this.aK.onLocalStreamMuteRsp(i2, str, uRTCSdkMediaType, uRTCSdkTrackType, z2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onLocalUnPublish(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo) {
        this.aK.onLocalUnPublish(i2, str, uRTCSdkStreamInfo);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onLocalUnPublishOnly(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo) {
        this.aK.onLocalUnPublishOnly(i2, str, uRTCSdkStreamInfo);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onLogOffNotify(int i2, String str) {
        this.aK.onLogOffNotify(i2, str);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onLogOffUsers(int i2, String str) {
        this.aK.onLogOffUsers(i2, str);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onMixStart(int i2, String str, String str2) {
        this.aK.onMixStart(i2, str, str2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onMixStop(int i2, String str, String str2) {
        this.aK.onMixStop(i2, str, str2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onMsgNotify(int i2, String str) {
        this.aK.onMsgNotify(i2, str);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onNetWorkQuality(String str, URTCSdkStreamType uRTCSdkStreamType, URTCSdkMediaType uRTCSdkMediaType, URTCSdkNetWorkQuality uRTCSdkNetWorkQuality) {
        this.aK.onNetWorkQuality(str, uRTCSdkStreamType, uRTCSdkMediaType, uRTCSdkNetWorkQuality);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onPeerLostConnection(int i2, URTCSdkStreamInfo uRTCSdkStreamInfo) {
        this.aK.onPeerLostConnection(i2, uRTCSdkStreamInfo);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRecordStart(int i2, String str) {
        this.aK.onRecordStart(i2, str);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRecordStop(int i2) {
        this.aK.onRecordStop(i2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRejoinRoomResult(String str) {
        this.aK.onRejoinRoomResult(str);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRejoiningRoom(String str) {
        this.aK.onRejoiningRoom(str);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRelayStatusNotify(URTCSdkMediaServerStatus uRTCSdkMediaServerStatus, int i2, String str, String str2, String str3, String str4, String[] strArr) {
        this.aK.onRelayStatusNotify(uRTCSdkMediaServerStatus, i2, str, str2, str3, str4, strArr);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRemoteAudioLevel(String str, int i2) {
        this.aK.onRemoteAudioLevel(str, i2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRemotePublish(URTCSdkStreamInfo uRTCSdkStreamInfo) {
        this.aK.onRemotePublish(uRTCSdkStreamInfo);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRemoteRTCStats(URTCSdkStats uRTCSdkStats) {
        this.aK.onRemoteRTCStats(uRTCSdkStats);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRemoteStreamMuteRsp(int i2, String str, String str2, URTCSdkMediaType uRTCSdkMediaType, URTCSdkTrackType uRTCSdkTrackType, boolean z2) {
        this.aK.onRemoteStreamMuteRsp(i2, str, str2, uRTCSdkMediaType, uRTCSdkTrackType, z2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRemoteTrackNotify(String str, URTCSdkMediaType uRTCSdkMediaType, URTCSdkTrackType uRTCSdkTrackType, boolean z2) {
        this.aK.onRemoteTrackNotify(str, uRTCSdkMediaType, uRTCSdkTrackType, z2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRemoteUnPublish(URTCSdkStreamInfo uRTCSdkStreamInfo) {
        this.aK.onRemoteUnPublish(uRTCSdkStreamInfo);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRemoteUserJoin(String str) {
        this.aK.onRemoteUserJoin(str);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onRemoteUserLeave(String str, int i2) {
        this.aK.onRemoteUserLeave(str, i2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onSendRTCStats(URTCSdkStats uRTCSdkStats) {
        this.aK.onSendRTCStats(uRTCSdkStats);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onServerBroadCastMsg(String str, String str2) {
        this.aK.onServerBroadCastMsg(str, str2);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onServerDisconnect() {
        this.aK.onServerDisconnect();
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onSubscribeResult(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo) {
        this.aK.onSubscribeResult(i2, str, uRTCSdkStreamInfo);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onUnSubscribeResult(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo) {
        this.aK.onUnSubscribeResult(i2, str, uRTCSdkStreamInfo);
    }

    @Override // com.plv.rtc.urtc.listener.URtcSdkEventListener
    public void onWarning(int i2) {
        this.aK.onWarning(i2);
    }
}
