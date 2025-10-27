package core.data;

/* loaded from: classes8.dex */
public class MediaOp {
    private String mUid = "";
    private int mMediaType = 1;
    private boolean mEnableAudio = false;
    private boolean mEnableVideo = false;

    public int getMediaType() {
        return this.mMediaType;
    }

    public String getUId() {
        return this.mUid;
    }

    public boolean isEnableAudio() {
        return this.mEnableAudio;
    }

    public boolean isEnableVideo() {
        return this.mEnableVideo;
    }

    public void setEnableAudio(boolean z2) {
        this.mEnableAudio = z2;
    }

    public void setEnableVideo(boolean z2) {
        this.mEnableVideo = z2;
    }

    public void setMediaType(int i2) {
        this.mMediaType = i2;
    }

    public void setUId(String str) {
        this.mUid = str;
    }
}
