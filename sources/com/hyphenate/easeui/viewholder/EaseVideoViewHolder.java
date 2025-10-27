package com.hyphenate.easeui.viewholder;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.ui.EaseShowVideoActivity;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVideo;
import com.hyphenate.util.EMLog;

/* loaded from: classes4.dex */
public class EaseVideoViewHolder extends EaseChatRowViewHolder {
    private static final String TAG = "EaseVideoViewHolder";

    public EaseVideoViewHolder(@NonNull View view, MessageListItemClickListener messageListItemClickListener) {
        super(view, messageListItemClickListener);
    }

    public static EaseChatRowViewHolder create(ViewGroup viewGroup, boolean z2, MessageListItemClickListener messageListItemClickListener) {
        return new EaseVideoViewHolder(new EaseChatRowVideo(viewGroup.getContext(), z2), messageListItemClickListener);
    }

    @Override // com.hyphenate.easeui.viewholder.EaseChatRowViewHolder, com.hyphenate.easeui.widget.chatrow.EaseChatRow.EaseChatRowActionCallback
    public void onBubbleClick(EMMessage eMMessage) {
        super.onBubbleClick(eMMessage);
        EMVideoMessageBody eMVideoMessageBody = (EMVideoMessageBody) eMMessage.getBody();
        EMLog.d(TAG, "video view is on click");
        if (!EMClient.getInstance().getOptions().getAutodownloadThumbnail() && (eMVideoMessageBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING || eMVideoMessageBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING || eMVideoMessageBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.FAILED)) {
            EMClient.getInstance().chatManager().downloadThumbnail(eMMessage);
            return;
        }
        Intent intent = new Intent(getContext(), (Class<?>) EaseShowVideoActivity.class);
        intent.putExtra("msg", eMMessage);
        if (eMMessage.direct() == EMMessage.Direct.RECEIVE && !eMMessage.isAcked() && eMMessage.getChatType() == EMMessage.ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(eMMessage.getFrom(), eMMessage.getMsgId());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        getContext().startActivity(intent);
    }
}
