package com.hyphenate.easeui.modules.conversation.delegate;

import com.hyphenate.easeui.adapter.EaseAdapterDelegate;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter.ViewHolder;
import com.hyphenate.easeui.modules.conversation.model.EaseConversationSetStyle;

/* loaded from: classes4.dex */
public abstract class EaseBaseConversationDelegate<T, VH extends EaseBaseRecyclerViewAdapter.ViewHolder<T>> extends EaseAdapterDelegate<T, VH> {
    public EaseConversationSetStyle setModel;

    public EaseBaseConversationDelegate(EaseConversationSetStyle easeConversationSetStyle) {
        this.setModel = easeConversationSetStyle;
    }

    public void setSetModel(EaseConversationSetStyle easeConversationSetStyle) {
        this.setModel = easeConversationSetStyle;
    }
}
