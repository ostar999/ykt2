package com.hyphenate.easeui.delegate;

import android.view.View;
import android.view.ViewGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.model.styles.EaseMessageListItemStyle;
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder;
import com.hyphenate.easeui.viewholder.EaseExpressionViewHolder;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowBigExpression;

/* loaded from: classes4.dex */
public class EaseExpressionAdapterDelegate extends EaseMessageAdapterDelegate<EMMessage, EaseChatRowViewHolder> {
    public EaseExpressionAdapterDelegate() {
    }

    @Override // com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
    public EaseChatRowViewHolder createViewHolder(View view, MessageListItemClickListener messageListItemClickListener) {
        return new EaseExpressionViewHolder(view, messageListItemClickListener);
    }

    @Override // com.hyphenate.easeui.delegate.EaseMessageAdapterDelegate
    public EaseChatRow getEaseChatRow(ViewGroup viewGroup, boolean z2) {
        return new EaseChatRowBigExpression(viewGroup.getContext(), z2);
    }

    public EaseExpressionAdapterDelegate(MessageListItemClickListener messageListItemClickListener, EaseMessageListItemStyle easeMessageListItemStyle) {
        super(messageListItemClickListener, easeMessageListItemStyle);
    }

    @Override // com.hyphenate.easeui.adapter.EaseAdapterDelegate
    public boolean isForViewType(EMMessage eMMessage, int i2) {
        return eMMessage.getType() == EMMessage.Type.TXT && eMMessage.getBooleanAttribute(EaseConstant.MESSAGE_ATTR_IS_BIG_EXPRESSION, false);
    }
}
