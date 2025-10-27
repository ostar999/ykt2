package core.data;

/* loaded from: classes8.dex */
public class StreamStatus {
    public String mUid = "";
    public int mMediaType = 0;
    public int mTrackType = 0;
    public int mBitrate = 0;
    public int mLostPercent = 0;
    public int mRttMs = 0;
    public int mDelayMs = 0;

    public int getBitrate() {
        return this.mBitrate;
    }

    public int getDelayMs() {
        return this.mDelayMs;
    }

    public int getLostPercent() {
        return this.mLostPercent;
    }

    public int getMediaType() {
        return this.mMediaType;
    }

    public int getRttMs() {
        return this.mRttMs;
    }

    public int getTrackType() {
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

    public void setMediaType(int i2) {
        this.mMediaType = i2;
    }

    public void setRttMs(int i2) {
        this.mRttMs = i2;
    }

    public void setTrackType(int i2) {
        this.mTrackType = i2;
    }

    public void setUId(String str) {
        this.mUid = str;
    }
}
