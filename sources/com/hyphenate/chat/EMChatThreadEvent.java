package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMAThreadInfo;

/* loaded from: classes4.dex */
public class EMChatThreadEvent extends EMBase<EMAThreadInfo> {
    private EMChatThread chatThread;

    public enum TYPE {
        UNKNOWN,
        CREATE,
        UPDATE,
        DELETE,
        UPDATE_MSG
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMChatThreadEvent(EMAThreadInfo eMAThreadInfo) {
        this.emaObject = eMAThreadInfo;
        this.chatThread = new EMChatThread(eMAThreadInfo);
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public EMChatThread getChatThread() {
        return this.chatThread;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getFrom() {
        return ((EMAThreadInfo) this.emaObject).getFrom();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public TYPE getType() {
        return ((EMAThreadInfo) this.emaObject).getType();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }
}
