package com.psychiatrygarden.bean;

/* loaded from: classes5.dex */
public class ExcerptBean {
    private String id;
    private boolean playing;
    private int time;
    private String title;

    public String getId() {
        return this.id;
    }

    public int getTime() {
        return this.time;
    }

    public String getTitle() {
        return this.title;
    }

    public boolean isPlaying() {
        return this.playing;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
