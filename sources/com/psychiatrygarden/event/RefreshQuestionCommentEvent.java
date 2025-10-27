package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class RefreshQuestionCommentEvent {
    private String questionId;

    public RefreshQuestionCommentEvent(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
