package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.hyphenate.chat.EMLocationMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.DarkModeHelper;

/* loaded from: classes4.dex */
public class EaseChatRowLocation extends EaseChatRow {
    private EMLocationMessageBody locBody;
    private TextView locationView;
    private TextView tvLocationName;

    public EaseChatRowLocation(Context context, boolean z2) {
        super(context, z2);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onFindViewById() {
        this.locationView = (TextView) findViewById(R.id.tv_location);
        this.tvLocationName = (TextView) findViewById(R.id.tv_location_name);
        if (DarkModeHelper.isDarkMode(getContext())) {
            this.locationView.setTextColor(Color.parseColor("#7380a9"));
            this.locationView.setBackground(new ColorDrawable(0));
            findViewById(R.id.bubble).setBackground(ContextCompat.getDrawable(getContext(), !this.showSenderType ? R.drawable.ease_chat_location_content_receive_bg_night : R.drawable.ease_chat_location_content_send_bg_night));
            findViewById(R.id.rl_location).setBackground(new ColorDrawable(Color.parseColor("#1C2134")));
        }
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onInflateView() {
        this.inflater.inflate(!this.showSenderType ? R.layout.ease_row_received_location : R.layout.ease_row_sent_location, this);
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
        ProgressBar progressBar = this.progressBar;
        if (progressBar != null) {
            progressBar.setVisibility(8);
        }
        ImageView imageView = this.statusView;
        if (imageView != null) {
            imageView.setVisibility(8);
        }
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onSetUpView() {
        EMLocationMessageBody eMLocationMessageBody = (EMLocationMessageBody) this.message.getBody();
        this.locBody = eMLocationMessageBody;
        this.locationView.setText(eMLocationMessageBody.getAddress());
    }

    public EaseChatRowLocation(Context context, EMMessage eMMessage, int i2, Object obj) {
        super(context, eMMessage, i2, obj);
    }
}
