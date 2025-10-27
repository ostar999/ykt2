package com.hyphenate.easeui.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.ui.EaseShowBigImageActivity;
import com.hyphenate.easeui.utils.EaseFileUtils;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowImage;
import com.hyphenate.util.EMLog;
import com.tencent.smtt.sdk.TbsVideoCacheTask;

/* loaded from: classes4.dex */
public class EaseImageViewHolder extends EaseChatRowViewHolder {
    public EaseImageViewHolder(@NonNull View view, MessageListItemClickListener messageListItemClickListener) {
        super(view, messageListItemClickListener);
    }

    public static EaseChatRowViewHolder create(ViewGroup viewGroup, boolean z2, MessageListItemClickListener messageListItemClickListener) {
        return new EaseImageViewHolder(new EaseChatRowImage(viewGroup.getContext(), z2), messageListItemClickListener);
    }

    @Override // com.hyphenate.easeui.viewholder.EaseChatRowViewHolder
    public void handleReceiveMessage(EMMessage eMMessage) {
        super.handleReceiveMessage(eMMessage);
        getChatRow().updateView(eMMessage);
    }

    @Override // com.hyphenate.easeui.viewholder.EaseChatRowViewHolder, com.hyphenate.easeui.widget.chatrow.EaseChatRow.EaseChatRowActionCallback
    public void onBubbleClick(EMMessage eMMessage) {
        super.onBubbleClick(eMMessage);
        EMImageMessageBody eMImageMessageBody = (EMImageMessageBody) eMMessage.getBody();
        if (EMClient.getInstance().getOptions().getAutodownloadThumbnail()) {
            if (eMImageMessageBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.FAILED) {
                getChatRow().updateView(eMMessage);
                EMClient.getInstance().chatManager().downloadThumbnail(eMMessage);
            }
        } else if (eMImageMessageBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING || eMImageMessageBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING || eMImageMessageBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.FAILED) {
            EMClient.getInstance().chatManager().downloadThumbnail(eMMessage);
            getChatRow().updateView(eMMessage);
            return;
        }
        Intent intent = new Intent(getContext(), (Class<?>) EaseShowBigImageActivity.class);
        Uri localUri = eMImageMessageBody.getLocalUri();
        EaseFileUtils.takePersistableUriPermission(getContext(), localUri);
        EMLog.e("Tag", "big image uri: " + localUri + "  exist: " + EaseFileUtils.isFileExistByUri(getContext(), localUri));
        if (EaseFileUtils.isFileExistByUri(getContext(), localUri)) {
            intent.putExtra("uri", localUri);
        } else {
            intent.putExtra("messageId", eMMessage.getMsgId());
            intent.putExtra(TbsVideoCacheTask.KEY_VIDEO_CACHE_PARAM_FILENAME, eMImageMessageBody.getFileName());
        }
        if (!EaseIM.getInstance().getConfigsManager().enableSendChannelAck() && eMMessage.direct() == EMMessage.Direct.RECEIVE && !eMMessage.isAcked() && eMMessage.getChatType() == EMMessage.ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(eMMessage.getFrom(), eMMessage.getMsgId());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        getContext().startActivity(intent);
    }
}
