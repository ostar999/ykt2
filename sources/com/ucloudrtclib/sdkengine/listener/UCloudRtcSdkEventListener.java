package com.ucloudrtclib.sdkengine.listener;

import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkAudioDevice;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMediaServiceStatus;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMediaType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkNetWorkQuality;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStats;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamInfo;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkTrackType;

/* loaded from: classes6.dex */
public interface UCloudRtcSdkEventListener {
    void onAddStreams(int i2, String str);

    void onAudioDeviceChanged(UCloudRtcSdkAudioDevice uCloudRtcSdkAudioDevice);

    void onAudioFileFinish();

    void onDelStreams(int i2, String str);

    void onError(int i2);

    void onFirstLocalVideoFrame();

    void onJoinRoomResult(int i2, String str, String str2);

    void onKickoff(int i2);

    void onLeaveRoomResult(int i2, String str, String str2);

    void onLocalAudioLevel(int i2);

    void onLocalPublish(int i2, String str, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo);

    void onLocalStreamMuteRsp(int i2, String str, UCloudRtcSdkMediaType uCloudRtcSdkMediaType, UCloudRtcSdkTrackType uCloudRtcSdkTrackType, boolean z2);

    void onLocalUnPublish(int i2, String str, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo);

    void onLocalUnPublishOnly(int i2, String str, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo);

    void onLogOffNotify(int i2, String str);

    void onLogOffUsers(int i2, String str);

    void onMsgNotify(int i2, String str);

    void onNetWorkQuality(String str, UCloudRtcSdkStreamType uCloudRtcSdkStreamType, UCloudRtcSdkMediaType uCloudRtcSdkMediaType, UCloudRtcSdkNetWorkQuality uCloudRtcSdkNetWorkQuality);

    void onPeerLostConnection(int i2, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo);

    void onPeerReconnected(int i2, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo);

    void onQueryMix(int i2, String str, int i3, String str2, String str3);

    void onRecordStart(int i2, String str);

    void onRecordStatusNotify(UCloudRtcSdkMediaServiceStatus uCloudRtcSdkMediaServiceStatus, int i2, String str, String str2, String str3, String str4, String str5);

    void onRecordStop(int i2);

    void onRejoinRoomResult(String str);

    void onRejoiningRoom(String str);

    void onRelayStatusNotify(UCloudRtcSdkMediaServiceStatus uCloudRtcSdkMediaServiceStatus, int i2, String str, String str2, String str3, String str4, String[] strArr);

    void onRemoteAudioLevel(String str, int i2);

    void onRemotePublish(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo);

    void onRemoteRTCStatus(UCloudRtcSdkStats uCloudRtcSdkStats);

    void onRemoteStreamMuteRsp(int i2, String str, String str2, UCloudRtcSdkMediaType uCloudRtcSdkMediaType, UCloudRtcSdkTrackType uCloudRtcSdkTrackType, boolean z2);

    void onRemoteTrackNotify(String str, UCloudRtcSdkMediaType uCloudRtcSdkMediaType, UCloudRtcSdkTrackType uCloudRtcSdkTrackType, boolean z2);

    void onRemoteUnPublish(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo);

    void onRemoteUserJoin(String str);

    void onRemoteUserLeave(String str, int i2);

    void onSendRTCStatus(UCloudRtcSdkStats uCloudRtcSdkStats);

    void onServerBroadCastMsg(String str, String str2);

    void onServerDisconnect();

    void onStartLocalRenderFailed(String str);

    void onSubscribeResult(int i2, String str, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo);

    void onUnSubscribeResult(int i2, String str, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo);

    void onWarning(int i2);
}
