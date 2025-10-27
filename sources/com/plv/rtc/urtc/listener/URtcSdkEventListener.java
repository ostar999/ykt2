package com.plv.rtc.urtc.listener;

import com.plv.rtc.urtc.enummeration.URTCSdkAudioDevice;
import com.plv.rtc.urtc.enummeration.URTCSdkMediaServerStatus;
import com.plv.rtc.urtc.enummeration.URTCSdkMediaType;
import com.plv.rtc.urtc.enummeration.URTCSdkNetWorkQuality;
import com.plv.rtc.urtc.enummeration.URTCSdkStreamType;
import com.plv.rtc.urtc.enummeration.URTCSdkTrackType;
import com.plv.rtc.urtc.model.URTCSdkStats;
import com.plv.rtc.urtc.model.URTCSdkStreamInfo;

/* loaded from: classes5.dex */
public interface URtcSdkEventListener {
    void onAddStreams(int i2, String str);

    void onAudioDeviceChanged(URTCSdkAudioDevice uRTCSdkAudioDevice);

    void onDelStreams(int i2, String str);

    void onError(int i2);

    void onJoinRoomResult(int i2, String str, String str2);

    void onKickoff(int i2);

    void onLeaveRoomResult(int i2, String str, String str2);

    void onLocalAudioLevel(int i2);

    void onLocalPublish(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo);

    void onLocalStreamMuteRsp(int i2, String str, URTCSdkMediaType uRTCSdkMediaType, URTCSdkTrackType uRTCSdkTrackType, boolean z2);

    void onLocalUnPublish(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo);

    void onLocalUnPublishOnly(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo);

    void onLogOffNotify(int i2, String str);

    void onLogOffUsers(int i2, String str);

    void onMixStart(int i2, String str, String str2);

    void onMixStop(int i2, String str, String str2);

    void onMsgNotify(int i2, String str);

    void onNetWorkQuality(String str, URTCSdkStreamType uRTCSdkStreamType, URTCSdkMediaType uRTCSdkMediaType, URTCSdkNetWorkQuality uRTCSdkNetWorkQuality);

    void onPeerLostConnection(int i2, URTCSdkStreamInfo uRTCSdkStreamInfo);

    void onRecordStart(int i2, String str);

    void onRecordStop(int i2);

    void onRejoinRoomResult(String str);

    void onRejoiningRoom(String str);

    void onRelayStatusNotify(URTCSdkMediaServerStatus uRTCSdkMediaServerStatus, int i2, String str, String str2, String str3, String str4, String[] strArr);

    void onRemoteAudioLevel(String str, int i2);

    void onRemotePublish(URTCSdkStreamInfo uRTCSdkStreamInfo);

    void onRemoteRTCStats(URTCSdkStats uRTCSdkStats);

    void onRemoteStreamMuteRsp(int i2, String str, String str2, URTCSdkMediaType uRTCSdkMediaType, URTCSdkTrackType uRTCSdkTrackType, boolean z2);

    void onRemoteTrackNotify(String str, URTCSdkMediaType uRTCSdkMediaType, URTCSdkTrackType uRTCSdkTrackType, boolean z2);

    void onRemoteUnPublish(URTCSdkStreamInfo uRTCSdkStreamInfo);

    void onRemoteUserJoin(String str);

    void onRemoteUserLeave(String str, int i2);

    void onSendRTCStats(URTCSdkStats uRTCSdkStats);

    void onServerBroadCastMsg(String str, String str2);

    void onServerDisconnect();

    void onSubscribeResult(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo);

    void onUnSubscribeResult(int i2, String str, URTCSdkStreamInfo uRTCSdkStreamInfo);

    void onWarning(int i2);
}
