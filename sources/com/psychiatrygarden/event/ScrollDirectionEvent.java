package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class ScrollDirectionEvent {
    private String questionId;
    private boolean showAnim;
    private boolean upScroll;

    public ScrollDirectionEvent(boolean upScroll, boolean showAnim, String questionId) {
        this.upScroll = upScroll;
        this.showAnim = showAnim;
        this.questionId = questionId;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public boolean isShowAnim() {
        return this.showAnim;
    }

    public boolean isUpScroll() {
        return this.upScroll;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setShowAnim(boolean showAnim) {
        this.showAnim = showAnim;
    }

    public void setUpScroll(boolean upScroll) {
        this.upScroll = upScroll;
    }

    public ScrollDirectionEvent(boolean upScroll, boolean showAnim) {
        this.upScroll = upScroll;
        this.showAnim = showAnim;
    }
}
