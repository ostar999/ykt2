package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMAMessageReaction;
import java.util.List;

/* loaded from: classes4.dex */
public class EMMessageReaction extends EMBase<EMAMessageReaction> {
    /* JADX WARN: Multi-variable type inference failed */
    public EMMessageReaction(EMAMessageReaction eMAMessageReaction) {
        this.emaObject = eMAMessageReaction;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getReaction() {
        return ((EMAMessageReaction) this.emaObject).reaction();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int getUserCount() {
        return ((EMAMessageReaction) this.emaObject).count();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<String> getUserList() {
        return ((EMAMessageReaction) this.emaObject).userList();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public boolean isAddedBySelf() {
        return ((EMAMessageReaction) this.emaObject).state();
    }
}
