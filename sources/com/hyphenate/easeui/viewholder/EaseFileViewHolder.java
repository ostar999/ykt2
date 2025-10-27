package com.hyphenate.easeui.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMNormalFileMessageBody;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.ui.EaseShowNormalFileActivity;
import com.hyphenate.easeui.utils.EaseCompat;
import com.hyphenate.easeui.utils.EaseFileUtils;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowFile;
import com.hyphenate.exceptions.HyphenateException;

/* loaded from: classes4.dex */
public class EaseFileViewHolder extends EaseChatRowViewHolder {
    public EaseFileViewHolder(@NonNull View view, MessageListItemClickListener messageListItemClickListener) {
        super(view, messageListItemClickListener);
    }

    public static EaseChatRowViewHolder create(ViewGroup viewGroup, boolean z2, MessageListItemClickListener messageListItemClickListener) {
        return new EaseFileViewHolder(new EaseChatRowFile(viewGroup.getContext(), z2), messageListItemClickListener);
    }

    @Override // com.hyphenate.easeui.viewholder.EaseChatRowViewHolder, com.hyphenate.easeui.widget.chatrow.EaseChatRow.EaseChatRowActionCallback
    public void onBubbleClick(EMMessage eMMessage) {
        super.onBubbleClick(eMMessage);
        Uri localUri = ((EMNormalFileMessageBody) eMMessage.getBody()).getLocalUri();
        EaseFileUtils.takePersistableUriPermission(getContext(), localUri);
        if (EaseFileUtils.isFileExistByUri(getContext(), localUri)) {
            EaseCompat.openFile(getContext(), localUri);
        } else {
            getContext().startActivity(new Intent(getContext(), (Class<?>) EaseShowNormalFileActivity.class).putExtra("msg", eMMessage));
        }
        if (eMMessage.direct() == EMMessage.Direct.RECEIVE && !eMMessage.isAcked() && eMMessage.getChatType() == EMMessage.ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(eMMessage.getFrom(), eMMessage.getMsgId());
            } catch (HyphenateException e2) {
                e2.printStackTrace();
            }
        }
    }
}
