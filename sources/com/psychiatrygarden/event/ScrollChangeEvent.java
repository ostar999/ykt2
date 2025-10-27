package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class ScrollChangeEvent {
    private String questionId;
    private boolean upScroll;

    public ScrollChangeEvent(String questionId, boolean upScroll) {
        this.questionId = questionId;
        this.upScroll = upScroll;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public boolean isUpScroll() {
        return this.upScroll;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setUpScroll(boolean upScroll) {
        this.upScroll = upScroll;
    }

    public ScrollChangeEvent(boolean upScroll) {
        this.upScroll = upScroll;
    }
}
