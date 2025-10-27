package com.hyphenate.easeui.delegate;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.adapter.EaseAdapterDelegate;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.model.styles.EaseMessageListItemStyle;
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;

/* loaded from: classes4.dex */
public abstract class EaseMessageAdapterDelegate<T, VH extends EaseChatRowViewHolder> extends EaseAdapterDelegate<T, VH> {
    private MessageListItemClickListener mItemClickListener;

    public EaseMessageAdapterDelegate() {
    }

    private boolean isSender(String str) {
        return !TextUtils.isEmpty(str) && TextUtils.equals(str, EMMessage.Direct.SEND.toString());
    }

    public EaseMessageListItemStyle createDefaultItemStyle() {
        EaseMessageListItemStyle.Builder builder = new EaseMessageListItemStyle.Builder();
        builder.showAvatar(true).showUserNick(false);
        return builder.build();
    }

    public abstract VH createViewHolder(View view, MessageListItemClickListener messageListItemClickListener);

    public abstract EaseChatRow getEaseChatRow(ViewGroup viewGroup, boolean z2);

    public void setListItemClickListener(MessageListItemClickListener messageListItemClickListener) {
        this.mItemClickListener = messageListItemClickListener;
    }

    public EaseMessageAdapterDelegate(MessageListItemClickListener messageListItemClickListener) {
        this();
        this.mItemClickListener = messageListItemClickListener;
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public VH onCreateViewHolder(ViewGroup viewGroup, String str) {
        return (VH) createViewHolder(getEaseChatRow(viewGroup, isSender(str)), this.mItemClickListener);
    }

    public EaseMessageAdapterDelegate(MessageListItemClickListener messageListItemClickListener, EaseMessageListItemStyle easeMessageListItemStyle) {
        this(messageListItemClickListener);
    }
}
