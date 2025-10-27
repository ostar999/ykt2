package com.plv.linkmic.model;

import androidx.annotation.NonNull;
import cn.hutool.core.text.CharPool;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.rtc.urtc.enummeration.URTCSdkTrackType;
import com.plv.rtc.urtc.model.URTCSdkStats;
import java.util.Map;

/* loaded from: classes4.dex */
public class PLVNetworkStatusVO {
    private int downstreamDelayMs;
    private float packageLostPercent;
    private TrackType trackType;
    private String uid;
    private int upstreamDelayMs;

    public enum TrackType {
        NULL,
        AUDIO,
        VIDEO,
        DATA
    }

    public static class URTCStatusMapper {
        private static final Map<URTCSdkTrackType, TrackType> MAP_TRACK_TYPE = PLVSugarUtil.mapOf(PLVSugarUtil.pair(URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_NULL, TrackType.NULL), PLVSugarUtil.pair(URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_AUDIO, TrackType.AUDIO), PLVSugarUtil.pair(URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_VIDEO, TrackType.VIDEO), PLVSugarUtil.pair(URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_DATA, TrackType.DATA));

        private URTCStatusMapper() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static void map(URTCSdkStats uRTCSdkStats, PLVNetworkStatusVO pLVNetworkStatusVO) {
            pLVNetworkStatusVO.uid = uRTCSdkStats.getUId();
            pLVNetworkStatusVO.trackType = (TrackType) PLVSugarUtil.getOrDefault(MAP_TRACK_TYPE.get(uRTCSdkStats.getTrackType()), TrackType.NULL);
            pLVNetworkStatusVO.packageLostPercent = uRTCSdkStats.getLostPercent();
            pLVNetworkStatusVO.upstreamDelayMs = uRTCSdkStats.getRttMs();
            pLVNetworkStatusVO.downstreamDelayMs = uRTCSdkStats.getDelayMs();
        }
    }

    public PLVNetworkStatusVO() {
    }

    public int getDownstreamDelayMs() {
        return this.downstreamDelayMs;
    }

    public float getPackageLostPercent() {
        return this.packageLostPercent;
    }

    public TrackType getTrackType() {
        return this.trackType;
    }

    public String getUid() {
        return this.uid;
    }

    public int getUpstreamDelayMs() {
        return this.upstreamDelayMs;
    }

    public void setDownstreamDelayMs(int i2) {
        this.downstreamDelayMs = i2;
    }

    public void setPackageLostPercent(float f2) {
        this.packageLostPercent = f2;
    }

    public void setTrackType(TrackType trackType) {
        this.trackType = trackType;
    }

    public void setUid(String str) {
        this.uid = str;
    }

    public void setUpstreamDelayMs(int i2) {
        this.upstreamDelayMs = i2;
    }

    public String toString() {
        return "PLVNetworkStatusVO{uid='" + this.uid + CharPool.SINGLE_QUOTE + ", trackType=" + this.trackType + ", packageLostPercent=" + this.packageLostPercent + ", upstreamDelayMs=" + this.upstreamDelayMs + ", downstreamDelayMs=" + this.downstreamDelayMs + '}';
    }

    public PLVNetworkStatusVO(@NonNull URTCSdkStats uRTCSdkStats) {
        URTCStatusMapper.map(uRTCSdkStats, this);
    }
}
