package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class GetNextChapterDataSuccessEvent {
    private String chapterId;
    private int currentPos;

    public GetNextChapterDataSuccessEvent(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getChapterId() {
        return this.chapterId;
    }

    public int getCurrentPos() {
        return this.currentPos;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    public GetNextChapterDataSuccessEvent(String chapterId, int currentPos) {
        this.chapterId = chapterId;
        this.currentPos = currentPos;
    }
}
