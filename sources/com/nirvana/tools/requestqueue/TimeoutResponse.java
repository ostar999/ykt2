package com.nirvana.tools.requestqueue;

/* loaded from: classes4.dex */
public abstract class TimeoutResponse implements Response {
    private boolean isTimeout;

    public TimeoutResponse(boolean z2) {
        this.isTimeout = z2;
    }

    public abstract boolean isResultTimeout();

    @Override // com.nirvana.tools.requestqueue.Response
    public final boolean isTimeout() {
        if (this.isTimeout) {
            return true;
        }
        return isResultTimeout();
    }

    public void setTimeout(boolean z2) {
        this.isTimeout = z2;
    }
}
