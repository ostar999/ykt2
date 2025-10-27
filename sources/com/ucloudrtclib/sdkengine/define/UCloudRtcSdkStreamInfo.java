package com.ucloudrtclib.sdkengine.define;

import cn.hutool.core.text.CharPool;
import core.data.Convert;
import core.data.StreamInfo;

/* loaded from: classes6.dex */
public class UCloudRtcSdkStreamInfo implements Convert<UCloudRtcSdkStreamInfo, StreamInfo> {
    public boolean mHasAudio;
    public boolean mHasData;
    public boolean mHasVideo;
    public UCloudRtcSdkMediaType mMediaType;
    public boolean mMuteAudio;
    public boolean mMuteVideo;
    public String mUid;

    public UCloudRtcSdkStreamInfo() {
        this.mUid = "";
        this.mHasVideo = false;
        this.mHasAudio = false;
        this.mHasData = false;
        this.mMediaType = UCloudRtcSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO;
    }

    public UCloudRtcSdkMediaType getMediaType() {
        return this.mMediaType;
    }

    public String getUId() {
        return this.mUid;
    }

    public boolean isHasAudio() {
        return this.mHasAudio;
    }

    public boolean isHasData() {
        return this.mHasData;
    }

    public boolean isHasVideo() {
        return this.mHasVideo;
    }

    public boolean isMuteAudio() {
        return this.mMuteAudio;
    }

    public boolean isMuteVideo() {
        return this.mMuteVideo;
    }

    public void setHasAudio(boolean z2) {
        this.mHasAudio = z2;
    }

    public void setHasData(boolean z2) {
        this.mHasData = z2;
    }

    public void setHasVideo(boolean z2) {
        this.mHasVideo = z2;
    }

    public void setMediaType(UCloudRtcSdkMediaType uCloudRtcSdkMediaType) {
        this.mMediaType = uCloudRtcSdkMediaType;
    }

    public void setMuteAudio(boolean z2) {
        this.mMuteAudio = z2;
    }

    public void setMuteVideo(boolean z2) {
        this.mMuteVideo = z2;
    }

    public void setUid(String str) {
        this.mUid = str;
    }

    public String toString() {
        return "UCloudRtcSdkStreamInfo{mUid='" + this.mUid + CharPool.SINGLE_QUOTE + ", mHasvideo=" + this.mHasVideo + ", mHasaudio=" + this.mHasAudio + ", mHasdata=" + this.mHasData + ", mMutevideo=" + this.mMuteVideo + ", mMuteaudio=" + this.mMuteAudio + ", mMediatype=" + this.mMediaType + '}';
    }

    @Override // core.data.Convert
    public StreamInfo toCore(UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo, StreamInfo streamInfo) {
        if (streamInfo == null) {
            return new StreamInfo(uCloudRtcSdkStreamInfo.mUid, uCloudRtcSdkStreamInfo.mHasVideo, uCloudRtcSdkStreamInfo.mHasAudio, uCloudRtcSdkStreamInfo.mHasData, uCloudRtcSdkStreamInfo.mMuteVideo, uCloudRtcSdkStreamInfo.mMuteAudio, uCloudRtcSdkStreamInfo.mMediaType.ordinal());
        }
        streamInfo.setUid(uCloudRtcSdkStreamInfo.mUid);
        streamInfo.setHasVideo(uCloudRtcSdkStreamInfo.mHasVideo);
        streamInfo.setHasAudio(uCloudRtcSdkStreamInfo.mHasAudio);
        streamInfo.setHasData(uCloudRtcSdkStreamInfo.mHasData);
        streamInfo.setMuteVideo(uCloudRtcSdkStreamInfo.mMuteVideo);
        streamInfo.setMuteAudio(uCloudRtcSdkStreamInfo.mMuteAudio);
        streamInfo.setMediaType(uCloudRtcSdkStreamInfo.mMediaType.ordinal());
        return streamInfo;
    }

    @Override // core.data.Convert
    public UCloudRtcSdkStreamInfo toProxy(StreamInfo streamInfo, UCloudRtcSdkStreamInfo uCloudRtcSdkStreamInfo) {
        if (streamInfo == null) {
            return new UCloudRtcSdkStreamInfo();
        }
        if (uCloudRtcSdkStreamInfo == null) {
            return new UCloudRtcSdkStreamInfo(streamInfo.getUId(), streamInfo.isHasVideo(), streamInfo.isHasAudio(), streamInfo.isHasData(), streamInfo.isMuteVideo(), streamInfo.isMuteAudio(), streamInfo.getMediaType());
        }
        uCloudRtcSdkStreamInfo.setUid(streamInfo.getUId());
        uCloudRtcSdkStreamInfo.setHasVideo(streamInfo.isHasVideo());
        uCloudRtcSdkStreamInfo.setHasAudio(streamInfo.isHasAudio());
        uCloudRtcSdkStreamInfo.setHasData(streamInfo.isHasData());
        uCloudRtcSdkStreamInfo.setMuteVideo(streamInfo.isMuteVideo());
        uCloudRtcSdkStreamInfo.setMuteAudio(streamInfo.isMuteAudio());
        uCloudRtcSdkStreamInfo.setMediaType(UCloudRtcSdkMediaType.matchValue(streamInfo.getMediaType()));
        return uCloudRtcSdkStreamInfo;
    }

    public UCloudRtcSdkStreamInfo(String str, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i2) {
        this.mUid = str;
        this.mHasVideo = z2;
        this.mHasAudio = z3;
        this.mHasData = z4;
        this.mMuteVideo = z5;
        this.mMuteAudio = z6;
        this.mMediaType = UCloudRtcSdkMediaType.matchValue(i2);
    }
}
