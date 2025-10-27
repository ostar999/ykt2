package io.agora.live;

import io.agora.rtc.IRtcEngineEventHandler;

/* loaded from: classes8.dex */
public class LiveStats {
    public double cpuAppUsage;
    public double cpuTotalUsage;
    public int duration;
    public int gatewayRtt;
    public int rxAudioKBitrate;
    public int rxBytes;
    public int rxVideoKBitrate;
    public int txAudioKBitrate;
    public int txBytes;
    public int txVideoKBitrate;
    public int userCount;

    public LiveStats(IRtcEngineEventHandler.RtcStats rtcStats) {
        this.duration = rtcStats.totalDuration;
        this.txBytes = rtcStats.txBytes;
        this.rxBytes = rtcStats.rxBytes;
        this.txAudioKBitrate = rtcStats.txAudioKBitRate;
        this.rxAudioKBitrate = rtcStats.rxAudioKBitRate;
        this.txVideoKBitrate = rtcStats.txVideoKBitRate;
        this.rxVideoKBitrate = rtcStats.rxVideoKBitRate;
        this.userCount = rtcStats.users;
        this.cpuAppUsage = rtcStats.cpuAppUsage;
        this.cpuTotalUsage = rtcStats.cpuTotalUsage;
        this.gatewayRtt = rtcStats.gatewayRtt;
    }
}
