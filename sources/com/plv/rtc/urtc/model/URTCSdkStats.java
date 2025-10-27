package com.plv.rtc.urtc.model;

import com.plv.rtc.urtc.enummeration.URTCSdkMediaType;
import com.plv.rtc.urtc.enummeration.URTCSdkTrackType;

/* loaded from: classes5.dex */
public class URTCSdkStats {
    int mBitrate;
    int mDelayMs;
    int mLostPercent;
    int mRttMs;
    String mUid = "";
    URTCSdkMediaType mMtype = URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_NULL;
    URTCSdkTrackType mTracktype = URTCSdkTrackType.UCLOUD_RTC_SDK_TRACK_TYPE_NULL;

    public URTCSdkStats() {
        this.mBitrate = 0;
        this.mLostPercent = 0;
        this.mRttMs = 0;
        this.mDelayMs = 0;
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

    public URTCSdkMediaType getMediaType() {
        return this.mMtype;
    }

    public int getRttMs() {
        return this.mRttMs;
    }

    @Deprecated
    public int getRttMsg() {
        return getRttMs();
    }

    public URTCSdkTrackType getTrackType() {
        return this.mTracktype;
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

    public void setMediaType(URTCSdkMediaType uRTCSdkMediaType) {
        this.mMtype = uRTCSdkMediaType;
    }

    public void setRttMs(int i2) {
        this.mRttMs = i2;
    }

    @Deprecated
    public void setRttMsg(int i2) {
        setRttMs(i2);
    }

    public void setTrackType(URTCSdkTrackType uRTCSdkTrackType) {
        this.mTracktype = uRTCSdkTrackType;
    }

    public void setUId(String str) {
        this.mUid = str;
    }
}
