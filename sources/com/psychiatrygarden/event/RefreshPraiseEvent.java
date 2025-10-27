package com.psychiatrygarden.event;

/* loaded from: classes5.dex */
public class RefreshPraiseEvent {
    private boolean praise;

    public RefreshPraiseEvent(boolean praise) {
        this.praise = praise;
    }

    public boolean isPraise() {
        return this.praise;
    }

    public void setPraise(boolean praise) {
        this.praise = praise;
    }
}
