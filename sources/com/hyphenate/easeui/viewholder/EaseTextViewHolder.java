package com.hyphenate.easeui.viewholder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.manager.EaseDingMessageHelper;
import com.hyphenate.easeui.ui.EaseDingAckUserListActivity;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowText;
import com.hyphenate.exceptions.HyphenateException;

/* loaded from: classes4.dex */
public class EaseTextViewHolder extends EaseChatRowViewHolder {
    public EaseTextViewHolder(@NonNull View view, MessageListItemClickListener messageListItemClickListener) {
        super(view, messageListItemClickListener);
    }

    public static EaseChatRowViewHolder create(ViewGroup viewGroup, boolean z2, MessageListItemClickListener messageListItemClickListener) {
        return new EaseTextViewHolder(new EaseChatRowText(viewGroup.getContext(), z2), messageListItemClickListener);
    }

    @Override // com.hyphenate.easeui.viewholder.EaseChatRowViewHolder
    public void handleReceiveMessage(EMMessage eMMessage) {
        super.handleReceiveMessage(eMMessage);
        if (EaseIM.getInstance().getConfigsManager().enableSendChannelAck() || eMMessage.isAcked() || eMMessage.getChatType() != EMMessage.ChatType.Chat) {
            EaseDingMessageHelper.get().sendAckMessage(eMMessage);
            return;
        }
        try {
            EMClient.getInstance().chatManager().ackMessageRead(eMMessage.getFrom(), eMMessage.getMsgId());
        } catch (HyphenateException e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.hyphenate.easeui.viewholder.EaseChatRowViewHolder, com.hyphenate.easeui.widget.chatrow.EaseChatRow.EaseChatRowActionCallback
    public void onBubbleClick(EMMessage eMMessage) {
        super.onBubbleClick(eMMessage);
        if (EaseDingMessageHelper.get().isDingMessage(eMMessage) && eMMessage.getChatType() == EMMessage.ChatType.GroupChat && eMMessage.direct() == EMMessage.Direct.SEND) {
            Intent intent = new Intent(getContext(), (Class<?>) EaseDingAckUserListActivity.class);
            intent.putExtra("msg", eMMessage);
            getContext().startActivity(intent);
        }
    }
}
