package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.style.URLSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.chat.EMTranslationResult;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.interfaces.MessageListItemClickListener;
import com.hyphenate.easeui.manager.EaseDingMessageHelper;
import com.hyphenate.easeui.utils.DarkModeHelper;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.just.agentweb.DefaultWebClient;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseChatRowText extends EaseChatRow {
    private TextView contentView;
    private View translationContainer;
    private TextView translationContentView;
    private ImageView translationStatusView;
    private EaseDingMessageHelper.IAckUserUpdateListener userUpdateListener;

    public EaseChatRowText(Context context, boolean z2) {
        super(context, z2);
        this.userUpdateListener = new EaseDingMessageHelper.IAckUserUpdateListener() { // from class: com.hyphenate.easeui.widget.chatrow.d
            @Override // com.hyphenate.easeui.manager.EaseDingMessageHelper.IAckUserUpdateListener
            public final void onUpdate(List list) {
                this.f8757a.lambda$new$1(list);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(List list) {
        onAckUserUpdate(list.size());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAckUserUpdate$2(int i2) {
        if (isSender()) {
            this.ackedView.setVisibility(0);
            this.ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), Integer.valueOf(i2)));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$onSetUpView$0(View view) {
        this.contentView.setTag(R.id.action_chat_long_click, Boolean.TRUE);
        MessageListItemClickListener messageListItemClickListener = this.itemClickListener;
        if (messageListItemClickListener != null) {
            return messageListItemClickListener.onBubbleLongClick(view, this.message);
        }
        return false;
    }

    private void replaceSpan() {
        Spannable spannable = (Spannable) this.contentView.getText();
        URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int i2 = 0; i2 < uRLSpanArr.length; i2++) {
            String url = uRLSpanArr[i2].getURL();
            int iIndexOf = spannable.toString().indexOf(url);
            int length = url.length() + iIndexOf;
            if (iIndexOf == -1) {
                if (url.contains(DefaultWebClient.HTTP_SCHEME)) {
                    url = url.replace(DefaultWebClient.HTTP_SCHEME, "");
                } else if (url.contains(DefaultWebClient.HTTPS_SCHEME)) {
                    url = url.replace(DefaultWebClient.HTTPS_SCHEME, "");
                } else if (url.contains("rtsp://")) {
                    url = url.replace("rtsp://", "");
                }
                iIndexOf = spannable.toString().indexOf(url);
                length = iIndexOf + url.length();
            }
            if (iIndexOf != -1) {
                spannable.removeSpan(uRLSpanArr[i2]);
                spannable.setSpan(new AutolinkSpan(uRLSpanArr[i2].getURL()), iIndexOf, length, 18);
            }
        }
    }

    private void setStatus(int i2, int i3) {
        ProgressBar progressBar = this.progressBar;
        if (progressBar != null) {
            progressBar.setVisibility(i2);
        }
        ImageView imageView = this.statusView;
        if (imageView != null) {
            imageView.setVisibility(i3);
        }
    }

    public void onAckUserUpdate(final int i2) {
        TextView textView = this.ackedView;
        if (textView == null) {
            return;
        }
        textView.post(new Runnable() { // from class: com.hyphenate.easeui.widget.chatrow.c
            @Override // java.lang.Runnable
            public final void run() {
                this.f8755c.lambda$onAckUserUpdate$2(i2);
            }
        });
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onFindViewById() {
        this.contentView = (TextView) findViewById(R.id.tv_chatcontent);
        this.translationContentView = (TextView) findViewById(R.id.tv_subContent);
        this.translationStatusView = (ImageView) findViewById(R.id.translation_status);
        this.translationContainer = findViewById(R.id.subBubble);
        if (DarkModeHelper.isDarkMode(getContext())) {
            this.contentView.setTextColor(Color.parseColor("#7380A9"));
            findViewById(R.id.bubble).setBackground(ContextCompat.getDrawable(getContext(), this.showSenderType ? R.drawable.ease_chat_bubble_send_bg_night : R.drawable.ease_chat_bubble_receive_bg_night));
        }
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onInflateView() {
        this.inflater.inflate(!this.showSenderType ? R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageCreate() {
        setStatus(0, 8);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageError() {
        super.onMessageError();
        setStatus(8, 0);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageInProgress() {
        setStatus(0, 8);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageSuccess() {
        TextView textView;
        setStatus(8, 8);
        if (isSender() && EaseDingMessageHelper.get().isDingMessage(this.message) && (textView = this.ackedView) != null) {
            textView.setVisibility(0);
            this.ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), Integer.valueOf(this.message.groupAckCount())));
        }
        EaseDingMessageHelper.get().setUserUpdateListener(this.message, this.userUpdateListener);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onSetUpView() {
        EMTextMessageBody eMTextMessageBody = (EMTextMessageBody) this.message.getBody();
        if (eMTextMessageBody != null) {
            this.contentView.setText(EaseSmileUtils.getSmiledText(this.context, eMTextMessageBody.getMessage()), TextView.BufferType.SPANNABLE);
            this.contentView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.hyphenate.easeui.widget.chatrow.e
                @Override // android.view.View.OnLongClickListener
                public final boolean onLongClick(View view) {
                    return this.f8758c.lambda$onSetUpView$0(view);
                }
            });
            replaceSpan();
            EMTranslationResult translationResult = EMClient.getInstance().translationManager().getTranslationResult(this.message.getMsgId());
            if (translationResult == null) {
                this.translationContainer.setVisibility(8);
                return;
            }
            if (!translationResult.showTranslation()) {
                this.translationContainer.setVisibility(8);
                return;
            }
            this.translationContainer.setVisibility(0);
            this.translationContentView.setText(translationResult.translatedText());
            this.translationContainer.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.hyphenate.easeui.widget.chatrow.EaseChatRowText.1
                @Override // android.view.View.OnLongClickListener
                public boolean onLongClick(View view) {
                    EaseChatRowText.this.contentView.setTag(R.id.action_chat_long_click, Boolean.TRUE);
                    EaseChatRowText easeChatRowText = EaseChatRowText.this;
                    MessageListItemClickListener messageListItemClickListener = easeChatRowText.itemClickListener;
                    if (messageListItemClickListener != null) {
                        return messageListItemClickListener.onBubbleLongClick(view, easeChatRowText.message);
                    }
                    return false;
                }
            });
            ImageView imageView = this.translationStatusView;
            int i2 = R.drawable.translation_success;
            imageView.setImageResource(i2);
            if (DarkModeHelper.isDarkMode(getContext())) {
                DarkModeHelper.setDarkModeDrawable(this.translationStatusView, i2);
            }
        }
    }

    public EaseChatRowText(Context context, EMMessage eMMessage, int i2, Object obj) {
        super(context, eMMessage, i2, obj);
        this.userUpdateListener = new EaseDingMessageHelper.IAckUserUpdateListener() { // from class: com.hyphenate.easeui.widget.chatrow.d
            @Override // com.hyphenate.easeui.manager.EaseDingMessageHelper.IAckUserUpdateListener
            public final void onUpdate(List list) {
                this.f8757a.lambda$new$1(list);
            }
        };
    }
}
