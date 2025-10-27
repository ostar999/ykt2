package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class QuestionBack2TopEvent {
    private String questionId;

    public QuestionBack2TopEvent(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
