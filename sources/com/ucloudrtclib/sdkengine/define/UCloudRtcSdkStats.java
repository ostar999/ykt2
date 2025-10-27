package com.ucloudrtclib.sdkengine.define;

import core.data.Convert;
import core.data.StreamStatus;

/* loaded from: classes6.dex */
public class UCloudRtcSdkStats implements Convert<UCloudRtcSdkStats, StreamStatus> {
    public int mBitrate;
    public int mDelayMs;
    public int mLostPercent;
    public UCloudRtcSdkMediaType mMediaType;
    public int mRttMs;
    public UCloudRtcSdkTrackType mTrackType;
    public String mUid;

    public UCloudRtcSdkStats() {
        this.mBitrate = 0;
        this.mLostPercent = 0;
        this.mRttMs = 0;
        this.mDelayMs = 0;
        this.mUid = "";
        this.mMediaType = UCloudRtcSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_NULL;
        this.mTrackType = UCloudRtcSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_NULL;
        this.mBitrate = 0;
        this.mLostPercent = 0;
        this.mRttMs = 0;
        this.mDelayMs = 0;
    }

    public int getBitrate() {
        return this.mBitrate;
    }

    public int getDelayMs() {
        return this.mDelayMs;
    }

    public int getLostPercent() {
        return this.mLostPercent;
    }

    public UCloudRtcSdkMediaType getMediaType() {
        return this.mMediaType;
    }

    public int getRttMs() {
        return this.mRttMs;
    }

    public UCloudRtcSdkTrackType getTrackType() {
        return this.mTrackType;
    }

    public String getUId() {
        return this.mUid;
    }

    public void setBitrate(int i2) {
        this.mBitrate = i2;
    }

    public void setDelayMs(int i2) {
        this.mDelayMs = i2;
    }

    public void setLostPercent(int i2) {
        this.mLostPercent = i2;
    }

    public void setMediaType(UCloudRtcSdkMediaType uCloudRtcSdkMediaType) {
        this.mMediaType = uCloudRtcSdkMediaType;
    }

    public void setRttMs(int i2) {
        this.mRttMs = i2;
    }

    public void setTrackType(UCloudRtcSdkTrackType uCloudRtcSdkTrackType) {
        this.mTrackType = uCloudRtcSdkTrackType;
    }

    public void setUId(String str) {
        this.mUid = str;
    }

    @Override // core.data.Convert
    public StreamStatus toCore(UCloudRtcSdkStats uCloudRtcSdkStats, StreamStatus streamStatus) {
        return null;
    }

    @Override // core.data.Convert
    public UCloudRtcSdkStats toProxy(StreamStatus streamStatus, UCloudRtcSdkStats uCloudRtcSdkStats) {
        if (uCloudRtcSdkStats == null) {
            return new UCloudRtcSdkStats(streamStatus.getUId(), UCloudRtcSdkMediaType.matchValue(streamStatus.getMediaType()), UCloudRtcSdkTrackType.matchValue(streamStatus.getTrackType()), streamStatus.getBitrate(), streamStatus.getLostPercent(), streamStatus.getRttMs(), streamStatus.getDelayMs());
        }
        uCloudRtcSdkStats.mUid = streamStatus.getUId();
        uCloudRtcSdkStats.mMediaType = UCloudRtcSdkMediaType.matchValue(streamStatus.getMediaType());
        uCloudRtcSdkStats.mTrackType = UCloudRtcSdkTrackType.matchValue(streamStatus.getTrackType());
        uCloudRtcSdkStats.mBitrate = streamStatus.getBitrate();
        uCloudRtcSdkStats.mDelayMs = streamStatus.getDelayMs();
        uCloudRtcSdkStats.mLostPercent = streamStatus.getLostPercent();
        uCloudRtcSdkStats.mRttMs = streamStatus.getRttMs();
        return uCloudRtcSdkStats;
    }

    public UCloudRtcSdkStats(String str, UCloudRtcSdkMediaType uCloudRtcSdkMediaType, UCloudRtcSdkTrackType uCloudRtcSdkTrackType, int i2, int i3, int i4, int i5) {
        this.mUid = str;
        this.mMediaType = uCloudRtcSdkMediaType;
        this.mTrackType = uCloudRtcSdkTrackType;
        this.mBitrate = i2;
        this.mLostPercent = i3;
        this.mRttMs = i4;
        this.mDelayMs = i5;
    }
}
