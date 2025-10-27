package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMAThreadInfo;

/* loaded from: classes4.dex */
public class EMChatThread extends EMBase<EMAThreadInfo> {
    /* JADX WARN: Multi-variable type inference failed */
    public EMChatThread(EMAThreadInfo eMAThreadInfo) {
        this.emaObject = eMAThreadInfo;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getChatThreadId() {
        return ((EMAThreadInfo) this.emaObject).getThreadId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getChatThreadName() {
        return ((EMAThreadInfo) this.emaObject).getThreadName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public long getCreateAt() {
        return ((EMAThreadInfo) this.emaObject).getCreateAt();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public EMMessage getLastMessage() {
        if (((EMAThreadInfo) this.emaObject).getLastMessage() == null) {
            return null;
        }
        return new EMMessage(((EMAThreadInfo) this.emaObject).getLastMessage());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getMemberCount() {
        return ((EMAThreadInfo) this.emaObject).getMemberCount();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getMessageCount() {
        return ((EMAThreadInfo) this.emaObject).getMessageCount();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getMessageId() {
        return ((EMAThreadInfo) this.emaObject).getMessageId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getOwner() {
        return ((EMAThreadInfo) this.emaObject).getOwner();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getParentId() {
        return ((EMAThreadInfo) this.emaObject).getParentId();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    public String toString() {
        String chatThreadName = getChatThreadName();
        return chatThreadName != null ? chatThreadName : getChatThreadId();
    }
}
