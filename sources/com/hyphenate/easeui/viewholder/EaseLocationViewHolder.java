package com.hyphenate.easeui.viewholder;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.ui.EaseBaiduMapActivity;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowLocation;
import com.hyphenate.exceptions.HyphenateException;

/* loaded from: classes4.dex */
public class EaseLocationViewHolder extends EaseChatRowViewHolder {
    public EaseLocationViewHolder(@NonNull View view, MessageListItemClickListener messageListItemClickListener) {
        super(view, messageListItemClickListener);
    }

    public static EaseChatRowViewHolder create(ViewGroup viewGroup, boolean z2, MessageListItemClickListener messageListItemClickListener) {
        return new EaseLocationViewHolder(new EaseChatRowLocation(viewGroup.getContext(), z2), messageListItemClickListener);
    }

    @Override // com.hyphenate.easeui.viewholder.EaseChatRowViewHolder
    public void handleReceiveMessage(EMMessage eMMessage) {
        super.handleReceiveMessage(eMMessage);
        if (EaseIM.getInstance().getConfigsManager().enableSendChannelAck() || eMMessage.isAcked() || eMMessage.getChatType() != EMMessage.ChatType.Chat) {
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
        EMLocationMessageBody eMLocationMessageBody = (EMLocationMessageBody) eMMessage.getBody();
        EaseBaiduMapActivity.actionStart(getContext(), eMLocationMessageBody.getLatitude(), eMLocationMessageBody.getLongitude(), eMLocationMessageBody.getAddress());
    }
}
