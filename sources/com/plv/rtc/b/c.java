package com.plv.rtc.b;

import com.plv.rtc.IPLVARtcEngineEventHandler;
import com.plv.rtc.PLVARTCAudioVolumeInfo;
import io.agora.rtc.IRtcEngineEventHandler;

/* loaded from: classes5.dex */
public class c extends IRtcEngineEventHandler {

    /* renamed from: a, reason: collision with root package name */
    private IPLVARtcEngineEventHandler f10894a;

    public c(IPLVARtcEngineEventHandler iPLVARtcEngineEventHandler) {
        this.f10894a = iPLVARtcEngineEventHandler;
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onAudioQuality(int i2, int i3, short s2, short s3) {
        this.f10894a.onAudioQuality(i2, i3, s2, s3);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onAudioVolumeIndication(IRtcEngineEventHandler.AudioVolumeInfo[] audioVolumeInfoArr, int i2) {
        PLVARTCAudioVolumeInfo[] pLVARTCAudioVolumeInfoArr = new PLVARTCAudioVolumeInfo[audioVolumeInfoArr.length];
        for (int i3 = 0; i3 < audioVolumeInfoArr.length; i3++) {
            PLVARTCAudioVolumeInfo pLVARTCAudioVolumeInfo = new PLVARTCAudioVolumeInfo();
            IRtcEngineEventHandler.AudioVolumeInfo audioVolumeInfo = audioVolumeInfoArr[i3];
            pLVARTCAudioVolumeInfo.uid = audioVolumeInfo.uid;
            pLVARTCAudioVolumeInfo.volume = audioVolumeInfo.volume;
            pLVARTCAudioVolumeInfoArr[i3] = pLVARTCAudioVolumeInfo;
        }
        this.f10894a.onAudioVolumeIndication(pLVARTCAudioVolumeInfoArr, i2);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onClientRoleChanged(int i2, int i3) {
        this.f10894a.onClientRoleChanged(i2, i3);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onError(int i2) {
        this.f10894a.onError(i2);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onFirstLocalVideoFrame(int i2, int i3, int i4) {
        this.f10894a.onFirstLocalVideoFrame(i2, i3, i4);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onFirstRemoteVideoDecoded(int i2, int i3, int i4, int i5) {
        this.f10894a.onFirstRemoteVideoDecoded(i2, i3, i4, i5);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onJoinChannelSuccess(String str, int i2, int i3) {
        this.f10894a.onJoinChannelSuccess(str, i2, i3);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onLastmileQuality(int i2) {
        this.f10894a.onLastmileQuality(i2);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onLeaveChannel(IRtcEngineEventHandler.RtcStats rtcStats) {
        this.f10894a.onLeaveChannel();
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onNetworkQuality(int i2, int i3, int i4) {
        this.f10894a.onNetworkQuality(i2, i3, i4);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onRejoinChannelSuccess(String str, int i2, int i3) {
        this.f10894a.onRejoinChannelSuccess(str, i2, i3);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onRequestToken() {
        this.f10894a.onRequestToken();
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onRtcStats(IRtcEngineEventHandler.RtcStats rtcStats) {
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onStreamPublished(String str, int i2) {
        this.f10894a.onStreamPublished(str, i2);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onStreamUnpublished(String str) {
        this.f10894a.onStreamUnpublished(str);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onTokenPrivilegeWillExpire(String str) {
        this.f10894a.onTokenPrivilegeWillExpire(str);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onUserJoined(int i2, int i3) {
        this.f10894a.onUserJoined(i2, i3);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onUserMuteAudio(int i2, boolean z2) {
        this.f10894a.onUserMuteAudio(i2, z2);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onUserMuteVideo(int i2, boolean z2) {
        this.f10894a.onUserMuteVideo(i2, z2);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onUserOffline(int i2, int i3) {
        this.f10894a.onUserOffline(i2, i3);
    }

    @Override // io.agora.rtc.IRtcEngineEventHandler
    public void onWarning(int i2) {
        this.f10894a.onWarning(i2);
    }
}
