package com.ucloudrtclib.sdkengine.adapter;

import b.b;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkAudioDevice;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMediaServiceStatus;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkMediaType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkNetWorkQuality;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStats;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamInfo;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkStreamType;
import com.ucloudrtclib.sdkengine.define.UCloudRtcSdkTrackType;
import com.ucloudrtclib.sdkengine.listener.UCloudRtcSdkEventListener;
import core.data.StreamInfo;
import core.data.StreamStatus;

/* loaded from: classes6.dex */
public class UCloudRtcCallBackAdapter implements b {
    public UCloudRtcSdkEventListener mURTCEventHandler;

    public UCloudRtcCallBackAdapter(UCloudRtcSdkEventListener uCloudRtcSdkEventListener) {
        this.mURTCEventHandler = uCloudRtcSdkEventListener;
    }

    @Override // b.b
    public void onAddStreams(int i2, String str) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onAddStreams(i2, str);
        }
    }

    @Override // b.b
    public void onAudioDeviceChanged(int i2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onAudioDeviceChanged(UCloudRtcSdkAudioDevice.matchValue(i2));
        }
    }

    @Override // b.b
    public void onAudioFileFinish() {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onAudioFileFinish();
        }
    }

    @Override // b.b
    public void onDelStreams(int i2, String str) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onDelStreams(i2, str);
        }
    }

    @Override // b.b
    public void onError(int i2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onError(i2);
        }
    }

    @Override // b.b
    public void onFirstLocalVideoFrame() {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onFirstLocalVideoFrame();
        }
    }

    @Override // b.b
    public void onJoinRoomResult(int i2, String str, String str2, String str3) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onJoinRoomResult(i2, str, str2);
        }
    }

    @Override // b.b
    public void onKickoff(int i2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onKickoff(i2);
        }
    }

    @Override // b.b
    public void onLeaveRoomResult(int i2, String str, String str2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onLeaveRoomResult(i2, str, str2);
        }
    }

    @Override // b.b
    public void onLocalAudioLevel(int i2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onLocalAudioLevel(i2);
        }
    }

    @Override // b.b
    public void onLocalPublish(int i2, String str, StreamInfo streamInfo) {
        if (this.mURTCEventHandler != null) {
            UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = new UCloudRtcSdkStreamInfo();
            uCloudRtcSdkStreamInfo.toProxy(streamInfo, uCloudRtcSdkStreamInfo);
            this.mURTCEventHandler.onLocalPublish(i2, str, uCloudRtcSdkStreamInfo);
        }
    }

    @Override // b.b
    public void onLocalStreamMuteRsp(int i2, String str, int i3, int i4, boolean z2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onLocalStreamMuteRsp(i2, str, UCloudRtcSdkMediaType.matchValue(i3), UCloudRtcSdkTrackType.matchValue(i4), z2);
        }
    }

    @Override // b.b
    public void onLocalUnPublish(int i2, String str, StreamInfo streamInfo) {
        if (this.mURTCEventHandler != null) {
            UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = new UCloudRtcSdkStreamInfo();
            uCloudRtcSdkStreamInfo.toProxy(streamInfo, uCloudRtcSdkStreamInfo);
            this.mURTCEventHandler.onLocalUnPublish(i2, str, uCloudRtcSdkStreamInfo);
        }
    }

    @Override // b.b
    public void onLocalUnPublishOnly(int i2, String str, StreamInfo streamInfo) {
        if (this.mURTCEventHandler != null) {
            UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = new UCloudRtcSdkStreamInfo();
            uCloudRtcSdkStreamInfo.toProxy(streamInfo, uCloudRtcSdkStreamInfo);
            this.mURTCEventHandler.onLocalUnPublishOnly(i2, str, uCloudRtcSdkStreamInfo);
        }
    }

    @Override // b.b
    public void onLogOffNotify(int i2, String str) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onLogOffNotify(i2, str);
        }
    }

    @Override // b.b
    public void onLogOffUsers(int i2, String str) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onLogOffUsers(i2, str);
        }
    }

    @Override // b.b
    public void onMessageNotify(int i2, String str) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onMsgNotify(i2, str);
        }
    }

    @Override // b.b
    public void onNetWorkQuality(String str, int i2, int i3, int i4) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onNetWorkQuality(str, UCloudRtcSdkStreamType.matchValue(i2), UCloudRtcSdkMediaType.matchValue(i3), UCloudRtcSdkNetWorkQuality.matchValue(i4));
        }
    }

    @Override // b.b
    public void onPeerLostConnection(int i2, StreamInfo streamInfo) {
        if (this.mURTCEventHandler != null) {
            UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = new UCloudRtcSdkStreamInfo();
            uCloudRtcSdkStreamInfo.toProxy(streamInfo, uCloudRtcSdkStreamInfo);
            this.mURTCEventHandler.onPeerLostConnection(i2, uCloudRtcSdkStreamInfo);
        }
    }

    @Override // b.b
    public void onPeerReconnect(int i2, StreamInfo streamInfo) {
        if (this.mURTCEventHandler != null) {
            UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = new UCloudRtcSdkStreamInfo();
            uCloudRtcSdkStreamInfo.toProxy(streamInfo, uCloudRtcSdkStreamInfo);
            this.mURTCEventHandler.onPeerReconnected(i2, uCloudRtcSdkStreamInfo);
        }
    }

    @Override // b.b
    public void onQueryMix(int i2, String str, int i3, String str2, String str3) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onQueryMix(i2, str, i3, str2, str3);
        }
    }

    @Override // b.b
    public void onRecordStart(int i2, String str) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onRecordStart(i2, str);
        }
    }

    @Override // b.b
    public void onRecordStatusNotify(int i2, int i3, String str, String str2, String str3, String str4, String str5) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onRecordStatusNotify(UCloudRtcSdkMediaServiceStatus.matchValue(i2), i3, str, str2, str3, str4, str5);
        }
    }

    @Override // b.b
    public void onRecordStop(int i2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onRecordStop(i2);
        }
    }

    @Override // b.b
    public void onRejoinRoomResult(String str) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onRejoinRoomResult(str);
        }
    }

    @Override // b.b
    public void onRejoiningRoom(String str) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onRejoiningRoom(str);
        }
    }

    @Override // b.b
    public void onRelayStatusNotify(int i2, int i3, String str, String str2, String str3, String str4, String[] strArr) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onRelayStatusNotify(UCloudRtcSdkMediaServiceStatus.matchValue(i2), i3, str, str2, str3, str4, strArr);
        }
    }

    @Override // b.b
    public void onRemoteAudioLevel(String str, int i2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onRemoteAudioLevel(str, i2);
        }
    }

    @Override // b.b
    public void onRemotePublish(StreamInfo streamInfo) {
        if (this.mURTCEventHandler != null) {
            UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = new UCloudRtcSdkStreamInfo();
            uCloudRtcSdkStreamInfo.toProxy(streamInfo, uCloudRtcSdkStreamInfo);
            this.mURTCEventHandler.onRemotePublish(uCloudRtcSdkStreamInfo);
        }
    }

    @Override // b.b
    public void onRemoteRTCStatus(StreamStatus streamStatus) {
        if (this.mURTCEventHandler != null) {
            UCloudRtcSdkStats uCloudRtcSdkStats = new UCloudRtcSdkStats();
            uCloudRtcSdkStats.toProxy(streamStatus, uCloudRtcSdkStats);
            this.mURTCEventHandler.onRemoteRTCStatus(uCloudRtcSdkStats);
        }
    }

    @Override // b.b
    public void onRemoteStreamMuteRsp(int i2, String str, String str2, int i3, int i4, boolean z2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onRemoteStreamMuteRsp(i2, str, str2, UCloudRtcSdkMediaType.matchValue(i3), UCloudRtcSdkTrackType.matchValue(i4), z2);
        }
    }

    @Override // b.b
    public void onRemoteTrackNotify(String str, int i2, int i3, boolean z2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onRemoteTrackNotify(str, UCloudRtcSdkMediaType.matchValue(i2), UCloudRtcSdkTrackType.matchValue(i3), z2);
        }
    }

    @Override // b.b
    public void onRemoteUnPublish(StreamInfo streamInfo) {
        if (this.mURTCEventHandler != null) {
            UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = new UCloudRtcSdkStreamInfo();
            uCloudRtcSdkStreamInfo.toProxy(streamInfo, uCloudRtcSdkStreamInfo);
            this.mURTCEventHandler.onRemoteUnPublish(uCloudRtcSdkStreamInfo);
        }
    }

    @Override // b.b
    public void onRemoteUserJoin(String str) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onRemoteUserJoin(str);
        }
    }

    @Override // b.b
    public void onRemoteUserLeave(String str, int i2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onRemoteUserLeave(str, i2);
        }
    }

    @Override // b.b
    public void onSendRTCStatus(StreamStatus streamStatus) {
        if (this.mURTCEventHandler != null) {
            UCloudRtcSdkStats uCloudRtcSdkStats = new UCloudRtcSdkStats();
            uCloudRtcSdkStats.toProxy(streamStatus, uCloudRtcSdkStats);
            this.mURTCEventHandler.onSendRTCStatus(uCloudRtcSdkStats);
        }
    }

    @Override // b.b
    public void onServerBroadcastMessage(String str, String str2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onServerBroadCastMsg(str, str2);
        }
    }

    @Override // b.b
    public void onServerDisconnect() {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onServerDisconnect();
        }
    }

    @Override // b.b
    public void onStartLocalRenderFailed(String str) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onStartLocalRenderFailed(str);
        }
    }

    @Override // b.b
    public void onSubscribeResult(int i2, String str, StreamInfo streamInfo) {
        if (this.mURTCEventHandler != null) {
            UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = new UCloudRtcSdkStreamInfo();
            uCloudRtcSdkStreamInfo.toProxy(streamInfo, uCloudRtcSdkStreamInfo);
            this.mURTCEventHandler.onSubscribeResult(i2, str, uCloudRtcSdkStreamInfo);
        }
    }

    @Override // b.b
    public void onUnSubscribeResult(int i2, String str, StreamInfo streamInfo) {
        if (this.mURTCEventHandler != null) {
            UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo = new UCloudRtcSdkStreamInfo();
            uCloudRtcSdkStreamInfo.toProxy(streamInfo, uCloudRtcSdkStreamInfo);
            this.mURTCEventHandler.onUnSubscribeResult(i2, str, uCloudRtcSdkStreamInfo);
        }
    }

    @Override // b.b
    public void onWarning(int i2) {
        UCloudRtcSdkEventListener uCloudRtcSdkEventListener = this.mURTCEventHandler;
        if (uCloudRtcSdkEventListener != null) {
            uCloudRtcSdkEventListener.onWarning(i2);
        }
    }
}
