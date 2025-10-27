package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMAMessageReaction;
import com.hyphenate.chat.adapter.EMAMessageReactionChange;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes4.dex */
public class EMMessageReactionChange extends EMBase<EMAMessageReactionChange> {
    /* JADX WARN: Multi-variable type inference failed */
    public EMMessageReactionChange(EMAMessageReactionChange eMAMessageReactionChange) {
        this.emaObject = eMAMessageReactionChange;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getConversionID() {
        return EMClient.getInstance().getCurrentUser().equals(((EMAMessageReactionChange) this.emaObject).getTo()) ? ((EMAMessageReactionChange) this.emaObject).getFrom() : ((EMAMessageReactionChange) this.emaObject).getTo();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public String getMessageId() {
        return ((EMAMessageReactionChange) this.emaObject).getMessageId();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public List<EMMessageReaction> getMessageReactionList() {
        List<EMAMessageReaction> messageReactionList = ((EMAMessageReactionChange) this.emaObject).getMessageReactionList();
        ArrayList arrayList = new ArrayList(messageReactionList.size());
        Iterator<EMAMessageReaction> it = messageReactionList.iterator();
        while (it.hasNext()) {
            arrayList.add(new EMMessageReaction(it.next()));
        }
        return arrayList;
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }
}
