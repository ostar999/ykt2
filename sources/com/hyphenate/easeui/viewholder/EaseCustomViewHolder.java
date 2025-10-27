package com.hyphenate.easeui.viewholder;

import android.view.View;
import androidx.annotation.NonNull;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.manager.EaseDingMessageHelper;
import com.hyphenate.exceptions.HyphenateException;

/* loaded from: classes4.dex */
public class EaseCustomViewHolder extends EaseChatRowViewHolder {
    public EaseCustomViewHolder(@NonNull View view, MessageListItemClickListener messageListItemClickListener) {
        super(view, messageListItemClickListener);
    }

    @Override // com.hyphenate.easeui.viewholder.EaseChatRowViewHolder
    public void handleReceiveMessage(EMMessage eMMessage) {
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
}
