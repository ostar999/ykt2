package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class UpdateScrollEvent {
    private String questionId;
    private int verticalDistance;

    public UpdateScrollEvent(int verticalDistance, String questionId) {
        this.verticalDistance = verticalDistance;
        this.questionId = questionId;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public int getVerticalDistance() {
        return this.verticalDistance;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setVerticalDistance(int verticalDistance) {
        this.verticalDistance = verticalDistance;
    }

    public UpdateScrollEvent(int verticalDistance) {
        this.verticalDistance = verticalDistance;
    }
}
