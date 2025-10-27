package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class PlayQuestionMediaEvent {
    private String id;
    private boolean play;

    public PlayQuestionMediaEvent(boolean play, String id) {
        this.play = play;
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public boolean isPlay() {
        return this.play;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlay(boolean play) {
        this.play = play;
    }
}
