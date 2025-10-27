package com.hyphenate.easeui.delegate;

import android.view.View;
import android.view.ViewGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.model.styles.EaseMessageListItemStyle;
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder;
import com.hyphenate.easeui.viewholder.EaseLocationViewHolder;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowLocation;

/* loaded from: classes4.dex */
public class EaseLocationAdapterDelegate extends EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder> {
    public EaseLocationAdapterDelegate() {
    }

    @Override // com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
    public EaseChatRowViewHolder createViewHolder(View view, MessageListItemClickListener messageListItemClickListener) {
        return new EaseLocationViewHolder(view, messageListItemClickListener);
    }

    @Override // com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
    public EaseChatRow getEaseChatRow(ViewGroup viewGroup, boolean z2) {
        return new EaseChatRowLocation(viewGroup.getContext(), z2);
    }

    public EaseLocationAdapterDelegate(MessageListItemClickListener messageListItemClickListener, EaseMessageListItemStyle easeMessageListItemStyle) {
        super(messageListItemClickListener, easeMessageListItemStyle);
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public boolean isForViewType(EMMessage eMMessage, int i2) {
        return eMMessage.getType() == EMMessage.Type.LOCATION;
    }
}
