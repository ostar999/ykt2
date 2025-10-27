package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class UpdateQuestionCutEvent {
    private String isCut;
    private String questionId;

    public UpdateQuestionCutEvent() {
    }

    public String getIsCut() {
        return this.isCut;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public void setIsCut(String isCut) {
        this.isCut = isCut;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public UpdateQuestionCutEvent(String questionId, String isCut) {
        this.questionId = questionId;
        this.isCut = isCut;
    }
}
