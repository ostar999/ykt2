package com.tencent.rtmp;

import java.util.Map;

/* loaded from: classes6.dex */
public class TXLivePlayConfig {
    String mCacheFolderPath;
    protected Map<String, String> mHeaders;
    int mMaxCacheItems;
    float mCacheTime = 5.0f;
    float mMaxAutoAdjustCacheTime = 5.0f;
    float mMinAutoAdjustCacheTime = 1.0f;
    int mVideoBlockThreshold = 800;
    int mConnectRetryCount = 3;
    int mConnectRetryInterval = 3;
    boolean mAutoAdjustCacheTime = true;
    boolean mEnableAec = false;
    boolean mEnableNearestIP = true;
    boolean mEnableMessage = false;
    boolean mEnableMetaData = false;
    String mFlvSessionKey = "";
    int mRtmpChannelType = 0;
    boolean mAutoRotate = true;

    public void enableAEC(boolean z2) {
        this.mEnableAec = z2;
    }

    public void setAutoAdjustCacheTime(boolean z2) {
        this.mAutoAdjustCacheTime = z2;
    }

    @Deprecated
    public void setCacheFolderPath(String str) {
        this.mCacheFolderPath = str;
    }

    public void setCacheTime(float f2) {
        this.mCacheTime = f2;
    }

    public void setConnectRetryCount(int i2) {
        this.mConnectRetryCount = i2;
    }

    public void setConnectRetryInterval(int i2) {
        this.mConnectRetryInterval = i2;
    }

    public void setEnableMessage(boolean z2) {
        this.mEnableMessage = z2;
    }

    public void setEnableMetaData(boolean z2) {
        this.mEnableMetaData = z2;
    }

    @Deprecated
    public void setEnableNearestIP(boolean z2) {
        this.mEnableNearestIP = z2;
    }

    public void setFlvSessionKey(String str) {
        this.mFlvSessionKey = str;
    }

    @Deprecated
    public void setHeaders(Map<String, String> map) {
        this.mHeaders = map;
    }

    public void setMaxAutoAdjustCacheTime(float f2) {
        this.mMaxAutoAdjustCacheTime = f2;
    }

    @Deprecated
    public void setMaxCacheItems(int i2) {
        this.mMaxCacheItems = i2;
    }

    public void setMinAutoAdjustCacheTime(float f2) {
        this.mMinAutoAdjustCacheTime = f2;
    }

    @Deprecated
    public void setRtmpChannelType(int i2) {
        this.mRtmpChannelType = i2;
    }

    public void setVideoBlockThreshold(int i2) {
        this.mVideoBlockThreshold = i2;
    }

    public String toString() {
        return "{mCacheTime=" + this.mCacheTime + ", mMaxAutoAdjustCacheTime=" + this.mMaxAutoAdjustCacheTime + ", mMinAutoAdjustCacheTime=" + this.mMinAutoAdjustCacheTime + ", mAutoAdjustCacheTime=" + this.mAutoAdjustCacheTime + ", mVideoBlockThreshold=" + this.mVideoBlockThreshold + ", mConnectRetryCount=" + this.mConnectRetryCount + ", mConnectRetryInterval=" + this.mConnectRetryInterval + ", mEnableAec=" + this.mEnableAec + ", mEnableMessage=" + this.mEnableMessage + ", mEnableMetaData=" + this.mEnableMetaData + '}';
    }
}
