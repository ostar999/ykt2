package com.hyphenate.easeui.delegate;

import android.view.View;
import android.view.ViewGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder;
import com.hyphenate.easeui.viewholder.EaseCustomViewHolder;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowCustom;

/* loaded from: classes4.dex */
public class EaseCustomAdapterDelegate extends EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder> {
    @Override // com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
    public EaseChatRowViewHolder createViewHolder(View view, MessageListItemClickListener messageListItemClickListener) {
        return new EaseCustomViewHolder(view, messageListItemClickListener);
    }

    @Override // com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
    public EaseChatRow getEaseChatRow(ViewGroup viewGroup, boolean z2) {
        return new EaseChatRowCustom(viewGroup.getContext(), z2);
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public boolean isForViewType(EMMessage eMMessage, int i2) {
        return eMMessage.getType() == EMMessage.Type.CUSTOM;
    }
}
