package com.plv.rtc.urtc.model;

import cn.hutool.core.text.CharPool;
import com.plv.rtc.urtc.enummeration.URTCSdkMediaType;

/* loaded from: classes5.dex */
public class URTCSdkStreamInfo {
    boolean mMuteaudio;
    boolean mMutevideo;
    String mUid = "";
    boolean mHasvideo = false;
    boolean mHasaudio = false;
    boolean mHasdata = false;
    URTCSdkMediaType mMediatype = URTCSdkMediaType.UCLOUD_RTC_SDK_MEDIA_TYPE_VIDEO;

    public URTCSdkMediaType getMediaType() {
        return this.mMediatype;
    }

    public String getUId() {
        return this.mUid;
    }

    public boolean isHasAudio() {
        return this.mHasaudio;
    }

    public boolean isHasData() {
        return this.mHasdata;
    }

    public boolean isHasVideo() {
        return this.mHasvideo;
    }

    public boolean isMuteAudio() {
        return this.mMuteaudio;
    }

    public boolean isMuteVideo() {
        return this.mMutevideo;
    }

    public void setHasAudio(boolean z2) {
        this.mHasaudio = z2;
    }

    public void setHasData(boolean z2) {
        this.mHasdata = z2;
    }

    public void setHasVideo(boolean z2) {
        this.mHasvideo = z2;
    }

    public void setMediaType(URTCSdkMediaType uRTCSdkMediaType) {
        this.mMediatype = uRTCSdkMediaType;
    }

    public void setMuteAudio(boolean z2) {
        this.mMuteaudio = z2;
    }

    public void setMuteVideo(boolean z2) {
        this.mMutevideo = z2;
    }

    public void setUid(String str) {
        this.mUid = str;
    }

    public String toString() {
        return "URTCSdkStreamInfo{mUid='" + this.mUid + CharPool.SINGLE_QUOTE + ", mHasvideo=" + this.mHasvideo + ", mHasaudio=" + this.mHasaudio + ", mHasdata=" + this.mHasdata + ", mMutevideo=" + this.mMutevideo + ", mMuteaudio=" + this.mMuteaudio + ", mMediatype=" + this.mMediatype + '}';
    }
}
