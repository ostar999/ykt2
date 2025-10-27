package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.Color;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.hyphenate.chat.EMCustomMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.manager.EaseDingMessageHelper;
import com.hyphenate.easeui.utils.DarkModeHelper;
import java.util.List;

/* loaded from: classes4.dex */
public class EaseChatRowCustom extends EaseChatRow {
    private RelativeLayout rl_content;
    private TextView timestamp;
    private TextView tv_welcome;
    private EaseDingMessageHelper.IAckUserUpdateListener userUpdateListener;

    public EaseChatRowCustom(Context context, boolean z2) {
        super(context, z2);
        this.userUpdateListener = new EaseDingMessageHelper.IAckUserUpdateListener() { // from class: com.hyphenate.easeui.widget.chatrow.EaseChatRowCustom.1
            @Override // com.hyphenate.easeui.manager.EaseDingMessageHelper.IAckUserUpdateListener
            public void onUpdate(List<String> list) {
                EaseChatRowCustom.this.onAckUserUpdate(list.size());
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onAckUserUpdate$0(int i2) {
        this.ackedView.setVisibility(0);
        this.ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), Integer.valueOf(i2)));
    }

    public void onAckUserUpdate(final int i2) {
        if (this.ackedView == null || !isSender()) {
            return;
        }
        this.ackedView.post(new Runnable() { // from class: com.hyphenate.easeui.widget.chatrow.a
            @Override // java.lang.Runnable
            public final void run() {
                this.f8752c.lambda$onAckUserUpdate$0(i2);
            }
        });
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onFindViewById() {
        this.rl_content = (RelativeLayout) findViewById(R.id.rl_content);
        this.timestamp = (TextView) findViewById(R.id.timestamp);
        this.tv_welcome = (TextView) findViewById(R.id.tv_welcome);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onInflateView() {
        this.inflater.inflate(!this.showSenderType ? R.layout.ease_row_received_message : R.layout.ease_row_sent_message, this);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageCreate() {
        ProgressBar progressBar = this.progressBar;
        if (progressBar != null) {
            progressBar.setVisibility(0);
        }
        ImageView imageView = this.statusView;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageError() {
        super.onMessageError();
        ProgressBar progressBar = this.progressBar;
        if (progressBar != null) {
            progressBar.setVisibility(8);
        }
        ImageView imageView = this.statusView;
        if (imageView != null) {
            imageView.setVisibility(0);
        }
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageInProgress() {
        ProgressBar progressBar = this.progressBar;
        if (progressBar != null) {
            progressBar.setVisibility(0);
        }
        ImageView imageView = this.statusView;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageSuccess() {
        TextView textView;
        ProgressBar progressBar = this.progressBar;
        if (progressBar != null) {
            progressBar.setVisibility(8);
        }
        ImageView imageView = this.statusView;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
        if (isSender() && EaseDingMessageHelper.get().isDingMessage(this.message) && (textView = this.ackedView) != null) {
            textView.setVisibility(0);
            this.ackedView.setText(String.format(getContext().getString(R.string.group_ack_read_count), Integer.valueOf(this.message.groupAckCount())));
        }
        EaseDingMessageHelper.get().setUserUpdateListener(this.message, this.userUpdateListener);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onSetUpView() {
        this.tv_welcome.setText(((EMCustomMessageBody) this.message.getBody()).event());
        this.tv_welcome.setVisibility(0);
        this.rl_content.setVisibility(8);
        findViewById(R.id.subBubble).setVisibility(8);
        if (DarkModeHelper.isDarkMode(getContext())) {
            ((TextView) findViewById(R.id.tv_chatcontent)).setTextColor(Color.parseColor("#7380A9"));
            findViewById(R.id.bubble).setBackground(ContextCompat.getDrawable(getContext(), !this.showSenderType ? R.drawable.ease_chat_bubble_receive_bg_night : R.drawable.ease_chat_bubble_send_bg_night));
        }
        this.timestamp.setVisibility(8);
    }

    public EaseChatRowCustom(Context context, EMMessage eMMessage, int i2, BaseAdapter baseAdapter) {
        super(context, eMMessage, i2, baseAdapter);
        this.userUpdateListener = new EaseDingMessageHelper.IAckUserUpdateListener() { // from class: com.hyphenate.easeui.widget.chatrow.EaseChatRowCustom.1
            @Override // com.hyphenate.easeui.manager.EaseDingMessageHelper.IAckUserUpdateListener
            public void onUpdate(List<String> list) {
                EaseChatRowCustom.this.onAckUserUpdate(list.size());
            }
        };
    }
}
