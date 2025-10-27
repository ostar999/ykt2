package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class RefreshQuestionBankEvent {
    private String identity_id;

    public RefreshQuestionBankEvent(String identity_id) {
        this.identity_id = identity_id;
    }

    public String getIdentity_id() {
        return this.identity_id;
    }

    public void setIdentity_id(String identity_id) {
        this.identity_id = identity_id;
    }
}
