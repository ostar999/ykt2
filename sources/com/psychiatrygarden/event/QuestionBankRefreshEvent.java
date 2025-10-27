package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class QuestionBankRefreshEvent {
    private boolean success;

    public QuestionBankRefreshEvent(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
