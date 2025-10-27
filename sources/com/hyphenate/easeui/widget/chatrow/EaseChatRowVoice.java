package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVoiceMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.DarkModeHelper;
import com.hyphenate.easeui.utils.EaseVoiceLengthUtils;
import com.hyphenate.util.EMLog;

/* loaded from: classes4.dex */
public class EaseChatRowVoice extends EaseChatRowFile {
    private static final String TAG = "EaseChatRowVoice";
    private ImageView readStatusView;
    private AnimationDrawable voiceAnimation;
    private ImageView voiceImageView;
    private TextView voiceLengthView;

    public EaseChatRowVoice(Context context, boolean z2) {
        super(context, z2);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowFile, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onFindViewById() {
        this.voiceImageView = (ImageView) findViewById(R.id.iv_voice);
        this.voiceLengthView = (TextView) findViewById(R.id.tv_length);
        this.readStatusView = (ImageView) findViewById(R.id.iv_unread_voice);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowFile, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onInflateView() {
        this.inflater.inflate(!this.showSenderType ? R.layout.ease_row_received_voice : R.layout.ease_row_sent_voice, this);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowFile, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onSetUpView() {
        int voiceLength;
        EMVoiceMessageBody eMVoiceMessageBody = (EMVoiceMessageBody) this.message.getBody();
        int length = eMVoiceMessageBody.getLength();
        if (length > 0) {
            voiceLength = EaseVoiceLengthUtils.getVoiceLength(getContext(), length);
            this.voiceLengthView.setText(eMVoiceMessageBody.getLength() + "\"");
            this.voiceLengthView.setVisibility(0);
        } else {
            this.voiceLengthView.setVisibility(4);
            voiceLength = 0;
        }
        if (this.showSenderType) {
            this.voiceImageView.setImageResource(R.drawable.ease_chatto_voice_playing);
            this.voiceLengthView.setPadding(0, 0, voiceLength, 0);
        } else {
            this.voiceImageView.setImageResource(R.drawable.ease_chatfrom_voice_playing);
            this.voiceLengthView.setPadding(voiceLength, 0, 0, 0);
        }
        if (DarkModeHelper.isDarkMode(getContext())) {
            findViewById(R.id.bubble).setBackground(ContextCompat.getDrawable(getContext(), !this.showSenderType ? R.drawable.ease_chat_bubble_receive_bg_night : R.drawable.ease_chat_bubble_send_bg_night));
            this.voiceLengthView.setTextColor(Color.parseColor("#606A8A"));
            Drawable drawable = this.voiceImageView.getDrawable();
            if (drawable != null) {
                drawable.setColorFilter(Color.parseColor("#7380a9"), PorterDuff.Mode.SRC_IN);
                this.voiceImageView.setImageDrawable(drawable);
            }
        }
        if (this.message.direct() == EMMessage.Direct.RECEIVE) {
            if (this.readStatusView != null) {
                if (this.message.isListened()) {
                    this.readStatusView.setVisibility(4);
                } else {
                    this.readStatusView.setVisibility(0);
                    if (DarkModeHelper.isDarkMode(getContext())) {
                        this.readStatusView.setImageResource(R.drawable.ease_chat_voice_unread_icon_night);
                    }
                }
            }
            EMLog.d(TAG, "it is receive msg");
            if ((eMVoiceMessageBody.downloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING || eMVoiceMessageBody.downloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) && EMClient.getInstance().getOptions().getAutodownloadThumbnail()) {
                this.progressBar.setVisibility(0);
            } else {
                this.progressBar.setVisibility(4);
            }
        } else {
            this.readStatusView.setVisibility(4);
        }
        EaseChatRowVoicePlayer easeChatRowVoicePlayer = EaseChatRowVoicePlayer.getInstance(getContext());
        if (easeChatRowVoicePlayer.isPlaying() && this.message.getMsgId().equals(easeChatRowVoicePlayer.getCurrentPlayingId())) {
            startVoicePlayAnimation();
        }
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onViewUpdate(EMMessage eMMessage) {
        super.onViewUpdate(eMMessage);
        if (this.message.direct() == EMMessage.Direct.SEND) {
            return;
        }
        EMVoiceMessageBody eMVoiceMessageBody = (EMVoiceMessageBody) eMMessage.getBody();
        if (eMVoiceMessageBody.downloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING || eMVoiceMessageBody.downloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
            this.progressBar.setVisibility(0);
        } else {
            this.progressBar.setVisibility(4);
        }
    }

    public void startVoicePlayAnimation() {
        EMMessage.Direct direct = this.message.direct();
        EMMessage.Direct direct2 = EMMessage.Direct.RECEIVE;
        if (direct == direct2) {
            this.voiceImageView.setImageResource(R.anim.voice_from_icon);
        } else {
            this.voiceImageView.setImageResource(R.anim.voice_to_icon);
        }
        this.voiceAnimation = (AnimationDrawable) this.voiceImageView.getDrawable();
        if (DarkModeHelper.isDarkMode(getContext())) {
            this.voiceAnimation.setColorFilter(Color.parseColor("#7380a9"), PorterDuff.Mode.SRC_IN);
        }
        this.voiceAnimation.start();
        if (this.message.direct() == direct2) {
            this.readStatusView.setVisibility(4);
        }
    }

    public void stopVoicePlayAnimation() {
        AnimationDrawable animationDrawable = this.voiceAnimation;
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
        if (this.message.direct() == EMMessage.Direct.RECEIVE) {
            if (DarkModeHelper.isDarkMode(getContext())) {
                DarkModeHelper.setDarkModeDrawable(this.voiceImageView, R.drawable.ease_chatfrom_voice_playing);
                return;
            } else {
                this.voiceImageView.setImageResource(R.drawable.ease_chatfrom_voice_playing);
                return;
            }
        }
        if (DarkModeHelper.isDarkMode(getContext())) {
            DarkModeHelper.setDarkModeDrawable(this.voiceImageView, R.drawable.ease_chatto_voice_playing);
        } else {
            this.voiceImageView.setImageResource(R.drawable.ease_chatto_voice_playing);
        }
    }

    public EaseChatRowVoice(Context context, EMMessage eMMessage, int i2, Object obj) {
        super(context, eMMessage, i2, obj);
    }
}
