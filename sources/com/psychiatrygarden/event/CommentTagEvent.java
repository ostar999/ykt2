package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class CommentTagEvent {
    private String questionId;
    private boolean show;

    public CommentTagEvent(String questionId, boolean show) {
        this.questionId = questionId;
        this.show = show;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public boolean isShow() {
        return this.show;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public CommentTagEvent(boolean show) {
        this.show = show;
    }
}
