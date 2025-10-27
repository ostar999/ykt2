package com.hyphenate.easeui.viewholder;

import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVoice;
import com.hyphenate.easeui.widget.chatrow.EaseChatRowVoicePlayer;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EMLog;
import java.io.File;
import java.io.IOException;

/* loaded from: classes4.dex */
public class EaseVoiceViewHolder extends EaseChatRowViewHolder {
    private EaseChatRowVoicePlayer voicePlayer;

    /* renamed from: com.hyphenate.easeui.viewholder.EaseVoiceViewHolder$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        static final /* synthetic */ int[] $SwitchMap$com$hyphenate$chat$EMFileMessageBody$EMDownloadStatus;

        static {
            int[] iArr = new int[EMFileMessageBody.EMDownloadStatus.values().length];
            $SwitchMap$com$hyphenate$chat$EMFileMessageBody$EMDownloadStatus = iArr;
            try {
                iArr[EMFileMessageBody.EMDownloadStatus.PENDING.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMFileMessageBody$EMDownloadStatus[EMFileMessageBody.EMDownloadStatus.FAILED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMFileMessageBody$EMDownloadStatus[EMFileMessageBody.EMDownloadStatus.DOWNLOADING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$hyphenate$chat$EMFileMessageBody$EMDownloadStatus[EMFileMessageBody.EMDownloadStatus.SUCCESSED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public EaseVoiceViewHolder(@NonNull View view, MessageListItemClickListener messageListItemClickListener) {
        super(view, messageListItemClickListener);
        this.voicePlayer = EaseChatRowVoicePlayer.getInstance(getContext());
    }

    private void ackMessage(EMMessage eMMessage) {
        EMMessage.ChatType chatType = eMMessage.getChatType();
        if (!eMMessage.isAcked() && chatType == EMMessage.ChatType.Chat) {
            try {
                EMClient.getInstance().chatManager().ackMessageRead(eMMessage.getFrom(), eMMessage.getMsgId());
            } catch (HyphenateException e2) {
                e2.printStackTrace();
            }
        }
        if (eMMessage.isListened()) {
            return;
        }
        EMClient.getInstance().chatManager().setVoiceMessageListened(eMMessage);
    }

    private void asyncDownloadVoice(final EMMessage eMMessage) {
        new AsyncTask<Void, Void, Void>() { // from class: com.hyphenate.easeui.viewholder.EaseVoiceViewHolder.2
            @Override // android.os.AsyncTask
            public Void doInBackground(Void... voidArr) {
                EMClient.getInstance().chatManager().downloadAttachment(eMMessage);
                return null;
            }

            @Override // android.os.AsyncTask
            public void onPostExecute(Void r2) {
                super.onPostExecute((AnonymousClass2) r2);
                EaseVoiceViewHolder.this.getChatRow().updateView(eMMessage);
            }
        }.execute(new Void[0]);
    }

    public static EaseChatRowViewHolder create(ViewGroup viewGroup, boolean z2, MessageListItemClickListener messageListItemClickListener) {
        return new EaseVoiceViewHolder(new EaseChatRowVoice(viewGroup.getContext(), z2), messageListItemClickListener);
    }

    private void play(EMMessage eMMessage) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        File file = new File(((EMVoiceMessageBody) eMMessage.getBody()).getLocalUrl());
        if (!file.exists() || !file.isFile()) {
            EMLog.e(EMClient.TAG, "file not exist");
            return;
        }
        ackMessage(eMMessage);
        playVoice(eMMessage);
        ((EaseChatRowVoice) getChatRow()).startVoicePlayAnimation();
    }

    private void playVoice(EMMessage eMMessage) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        this.voicePlayer.play(eMMessage, new MediaPlayer.OnCompletionListener() { // from class: com.hyphenate.easeui.viewholder.EaseVoiceViewHolder.1
            @Override // android.media.MediaPlayer.OnCompletionListener
            public void onCompletion(MediaPlayer mediaPlayer) {
                ((EaseChatRowVoice) EaseVoiceViewHolder.this.getChatRow()).stopVoicePlayAnimation();
            }
        });
    }

    @Override // com.hyphenate.easeui.viewholder.EaseChatRowViewHolder, com.hyphenate.easeui.widget.chatrow.EaseChatRow.EaseChatRowActionCallback
    public void onBubbleClick(EMMessage eMMessage) throws IllegalStateException, Resources.NotFoundException, IOException, SecurityException, IllegalArgumentException {
        super.onBubbleClick(eMMessage);
        String msgId = eMMessage.getMsgId();
        if (this.voicePlayer.isPlaying()) {
            this.voicePlayer.stop();
            ((EaseChatRowVoice) getChatRow()).stopVoicePlayAnimation();
            if (msgId.equals(this.voicePlayer.getCurrentPlayingId())) {
                return;
            }
        }
        if (eMMessage.direct() == EMMessage.Direct.SEND) {
            File file = new File(((EMVoiceMessageBody) eMMessage.getBody()).getLocalUrl());
            if (!file.exists() || !file.isFile()) {
                asyncDownloadVoice(eMMessage);
                return;
            } else {
                playVoice(eMMessage);
                ((EaseChatRowVoice) getChatRow()).startVoicePlayAnimation();
                return;
            }
        }
        String string = getContext().getResources().getString(R.string.Is_download_voice_click_later);
        if (eMMessage.status() != EMMessage.Status.SUCCESS) {
            if (eMMessage.status() == EMMessage.Status.INPROGRESS) {
                Toast.makeText(getContext(), string, 0).show();
                return;
            } else {
                if (eMMessage.status() == EMMessage.Status.FAIL) {
                    Toast.makeText(getContext(), string, 0).show();
                    asyncDownloadVoice(eMMessage);
                    return;
                }
                return;
            }
        }
        if (EMClient.getInstance().getOptions().getAutodownloadThumbnail()) {
            play(eMMessage);
            return;
        }
        EMVoiceMessageBody eMVoiceMessageBody = (EMVoiceMessageBody) eMMessage.getBody();
        EMLog.i(EMClient.TAG, "Voice body download status: " + eMVoiceMessageBody.downloadStatus());
        int i2 = AnonymousClass3.$SwitchMap$com$hyphenate$chat$EMFileMessageBody$EMDownloadStatus[eMVoiceMessageBody.downloadStatus().ordinal()];
        if (i2 == 1 || i2 == 2) {
            getChatRow().updateView(eMMessage);
            asyncDownloadVoice(eMMessage);
        } else if (i2 == 3) {
            Toast.makeText(getContext(), string, 0).show();
        } else {
            if (i2 != 4) {
                return;
            }
            play(eMMessage);
        }
    }

    @Override // com.hyphenate.easeui.viewholder.EaseChatRowViewHolder, com.hyphenate.easeui.widget.chatrow.EaseChatRow.EaseChatRowActionCallback
    public void onDetachedFromWindow() throws IllegalStateException {
        super.onDetachedFromWindow();
        if (this.voicePlayer.isPlaying()) {
            this.voicePlayer.stop();
            this.voicePlayer.release();
        }
    }
}
