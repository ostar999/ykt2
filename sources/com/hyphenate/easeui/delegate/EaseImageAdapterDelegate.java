package com.hyphenate.easeui.delegate;

import android.view.View;
import android.view.ViewGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.model.styles.EaseMessageListItemStyle;
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder;
import com.hyphenate.easeui.viewholder.EaseImageViewHolder;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowImage;

/* loaded from: classes4.dex */
public class EaseImageAdapterDelegate extends EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder> {
    public EaseImageAdapterDelegate() {
    }

    @Override // com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
    public EaseChatRowViewHolder createViewHolder(View view, MessageListItemClickListener messageListItemClickListener) {
        return new EaseImageViewHolder(view, messageListItemClickListener);
    }

    @Override // com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
    public EaseChatRow getEaseChatRow(ViewGroup viewGroup, boolean z2) {
        return new EaseChatRowImage(viewGroup.getContext(), z2);
    }

    public EaseImageAdapterDelegate(MessageListItemClickListener messageListItemClickListener, EaseMessageListItemStyle easeMessageListItemStyle) {
        super(messageListItemClickListener, easeMessageListItemStyle);
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public boolean isForViewType(EMMessage eMMessage, int i2) {
        return eMMessage.getType() == EMMessage.Type.IMAGE;
    }
}
