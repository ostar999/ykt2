package core.data;

import cn.hutool.core.text.CharPool;

/* loaded from: classes8.dex */
public class StreamInfo {
    public boolean mHasAudio;
    public boolean mHasData;
    public boolean mHasVideo;
    public int mMediaType;
    public boolean mMuteAudio;
    public boolean mMuteVideo;
    public String mUid;

    public StreamInfo() {
        this.mUid = "";
        this.mHasVideo = false;
        this.mHasAudio = false;
        this.mHasData = false;
        this.mMediaType = 1;
    }

    public int getMediaType() {
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

    public void setMediaType(int i2) {
        this.mMediaType = i2;
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
        return "StreamInfo{mUid='" + this.mUid + CharPool.SINGLE_QUOTE + ", mHasvideo=" + this.mHasVideo + ", mHasaudio=" + this.mHasAudio + ", mHasdata=" + this.mHasData + ", mMutevideo=" + this.mMuteVideo + ", mMuteaudio=" + this.mMuteAudio + ", mMediatype=" + this.mMediaType + '}';
    }

    public StreamInfo(String str, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, int i2) {
        this.mUid = str;
        this.mHasVideo = z2;
        this.mHasAudio = z3;
        this.mHasData = z4;
        this.mMuteVideo = z5;
        this.mMuteAudio = z6;
        this.mMediaType = i2;
    }
}
