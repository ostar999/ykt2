package com.hyphenate.easeui.interfaces;

import android.view.ViewGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.model.styles.EaseMessageListItemStyle;
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder;

/* loaded from: classes4.dex */
public interface IViewHolderProvider {
    EaseChatRowViewHolder provideViewHolder(ViewGroup viewGroup, int i2, MessageListItemClickListener messageListItemClickListener, EaseMessageListItemStyle easeMessageListItemStyle);

    int provideViewType(EMMessage eMMessage);
}
