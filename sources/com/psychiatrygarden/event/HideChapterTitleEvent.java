package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class HideChapterTitleEvent {
    private boolean isHide;
    private String questionId;

    public HideChapterTitleEvent(boolean isHide, String questionId) {
        this.isHide = isHide;
        this.questionId = questionId;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public boolean isHide() {
        return this.isHide;
    }

    public void setHide(boolean hide) {
        this.isHide = hide;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public HideChapterTitleEvent(boolean isHide) {
        this.isHide = isHide;
    }
}
