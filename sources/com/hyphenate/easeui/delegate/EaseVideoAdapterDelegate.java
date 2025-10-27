package com.hyphenate.easeui.delegate;

import android.view.View;
import android.view.ViewGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.model.styles.EaseMessageListItemStyle;
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder;
import com.hyphenate.easeui.viewholder.EaseVideoViewHolder;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVideo;

/* loaded from: classes4.dex */
public class EaseVideoAdapterDelegate extends EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder> {
    public EaseVideoAdapterDelegate() {
    }

    @Override // com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
    public EaseChatRowViewHolder createViewHolder(View view, MessageListItemClickListener messageListItemClickListener) {
        return new EaseVideoViewHolder(view, messageListItemClickListener);
    }

    @Override // com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
    public EaseChatRow getEaseChatRow(ViewGroup viewGroup, boolean z2) {
        return new EaseChatRowVideo(viewGroup.getContext(), z2);
    }

    public EaseVideoAdapterDelegate(MessageListItemClickListener messageListItemClickListener, EaseMessageListItemStyle easeMessageListItemStyle) {
        super(messageListItemClickListener, easeMessageListItemStyle);
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public boolean isForViewType(EMMessage eMMessage, int i2) {
        return eMMessage.getType() == EMMessage.Type.VIDEO;
    }
}
