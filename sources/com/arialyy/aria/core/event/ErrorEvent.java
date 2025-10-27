package com.arialyy.aria.core.event;

/* loaded from: classes2.dex */
public class ErrorEvent {
    public String errorMsg;
    public long taskId;

    public ErrorEvent(long j2, String str) {
        this.taskId = j2;
        this.errorMsg = str;
    }
}
