package b;

import core.data.StreamInfo;
import core.data.StreamStatus;

/* loaded from: classes.dex */
public interface b {
    void onAddStreams(int i2, String str);

    void onAudioDeviceChanged(int i2);

    void onAudioFileFinish();

    void onDelStreams(int i2, String str);

    void onError(int i2);

    void onFirstLocalVideoFrame();

    void onJoinRoomResult(int i2, String str, String str2, String str3);

    void onKickoff(int i2);

    void onLeaveRoomResult(int i2, String str, String str2);

    void onLocalAudioLevel(int i2);

    void onLocalPublish(int i2, String str, StreamInfo streamInfo);

    void onLocalStreamMuteRsp(int i2, String str, int i3, int i4, boolean z2);

    void onLocalUnPublish(int i2, String str, StreamInfo streamInfo);

    void onLocalUnPublishOnly(int i2, String str, StreamInfo streamInfo);

    void onLogOffNotify(int i2, String str);

    void onLogOffUsers(int i2, String str);

    void onMessageNotify(int i2, String str);

    void onNetWorkQuality(String str, int i2, int i3, int i4);

    void onPeerLostConnection(int i2, StreamInfo streamInfo);

    void onPeerReconnect(int i2, StreamInfo streamInfo);

    void onQueryMix(int i2, String str, int i3, String str2, String str3);

    void onRecordStart(int i2, String str);

    void onRecordStatusNotify(int i2, int i3, String str, String str2, String str3, String str4, String str5);

    void onRecordStop(int i2);

    void onRejoinRoomResult(String str);

    void onRejoiningRoom(String str);

    void onRelayStatusNotify(int i2, int i3, String str, String str2, String str3, String str4, String[] strArr);

    void onRemoteAudioLevel(String str, int i2);

    void onRemotePublish(StreamInfo streamInfo);

    void onRemoteRTCStatus(StreamStatus streamStatus);

    void onRemoteStreamMuteRsp(int i2, String str, String str2, int i3, int i4, boolean z2);

    void onRemoteTrackNotify(String str, int i2, int i3, boolean z2);

    void onRemoteUnPublish(StreamInfo streamInfo);

    void onRemoteUserJoin(String str);

    void onRemoteUserLeave(String str, int i2);

    void onSendRTCStatus(StreamStatus streamStatus);

    void onServerBroadcastMessage(String str, String str2);

    void onServerDisconnect();

    void onStartLocalRenderFailed(String str);

    void onSubscribeResult(int i2, String str, StreamInfo streamInfo);

    void onUnSubscribeResult(int i2, String str, StreamInfo streamInfo);

    void onWarning(int i2);
}
