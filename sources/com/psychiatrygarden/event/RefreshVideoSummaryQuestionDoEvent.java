package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class RefreshVideoSummaryQuestionDoEvent {
    private boolean _do;
    private String questionId;

    public RefreshVideoSummaryQuestionDoEvent(String questionId, boolean _do) {
        this.questionId = questionId;
        this._do = _do;
    }

    public String getQuestionId() {
        return this.questionId;
    }

    public boolean is_do() {
        return this._do;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public void set_do(boolean _do) {
        this._do = _do;
    }

    public RefreshVideoSummaryQuestionDoEvent(boolean _do) {
        this._do = _do;
    }
}
