package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class UpdateTopMargin {
    private String questionId;
    private int topMargin;

    public UpdateTopMargin(int topMargin, String questionId) {
        this.topMargin = topMargin;
        this.questionId = questionId;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public int getTopMargin() {
        return this.topMargin;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void setTopMargin(int topMargin) {
        this.topMargin = topMargin;
    }
}
