package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class RefreshVideoProgressEvent {
    private String progress;
    private String vid;

    public RefreshVideoProgressEvent(String vid, String progress) {
        this.vid = vid;
        this.progress = progress;
    }

    public String getProgress() {
        return this.progress;
    }

    public String getVid() {
        return this.vid;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }
}
