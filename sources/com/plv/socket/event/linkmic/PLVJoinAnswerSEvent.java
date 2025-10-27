package com.plv.socket.event.linkmic;

import cn.hutool.core.text.CharPool;

/* loaded from: classes5.dex */
public class PLVJoinAnswerSEvent {
    private boolean result;
    private int status;
    private String userId;

    public int getStatus() {
        return this.status;
    }

    public String getUserId() {
        return this.userId;
    }

    public boolean isRefuse() {
        return this.status == 0;
    }

    public boolean isResult() {
        return this.result;
    }

    public void setResult(boolean z2) {
        this.result = z2;
    }

    public void setStatus(int i2) {
        this.status = i2;
    }

    public void setUserId(String str) {
        this.userId = str;
    }

    public String toString() {
        return "PLVJoinAnswerSEvent{status=" + this.status + ", userId='" + this.userId + CharPool.SINGLE_QUOTE + ", result=" + this.result + '}';
    }
}
