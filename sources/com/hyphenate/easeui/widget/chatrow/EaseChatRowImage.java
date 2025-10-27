package com.hyphenate.easeui.widget.chatrow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseImageUtils;

/* loaded from: classes4.dex */
public class EaseChatRowImage extends EaseChatRowFile {
    protected ImageView imageView;
    private EMImageMessageBody imgBody;

    public EaseChatRowImage(Context context, boolean z2) {
        super(context, z2);
    }

    @SuppressLint({"StaticFieldLeak"})
    private void showImageView(EMMessage eMMessage) {
        EaseImageUtils.showImage(this.context, this.imageView, eMMessage);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowFile, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onFindViewById() {
        this.percentageView = (TextView) findViewById(R.id.percentage);
        this.imageView = (ImageView) findViewById(R.id.image);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowFile, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onInflateView() {
        this.inflater.inflate(!this.showSenderType ? R.layout.ease_row_received_picture : R.layout.ease_row_sent_picture, this);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowFile, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageInProgress() {
        if (this.message.direct() == EMMessage.Direct.SEND) {
            super.onMessageInProgress();
            return;
        }
        if (EMClient.getInstance().getOptions().getAutodownloadThumbnail()) {
            return;
        }
        this.progressBar.setVisibility(4);
        TextView textView = this.percentageView;
        if (textView != null) {
            textView.setVisibility(4);
        }
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowFile, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageSuccess() {
        super.onMessageSuccess();
        showImageView(this.message);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowFile, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onSetUpView() {
        View view = this.bubbleLayout;
        if (view != null) {
            view.setBackground(null);
        }
        this.imgBody = (EMImageMessageBody) this.message.getBody();
        if (this.message.direct() != EMMessage.Direct.RECEIVE) {
            showImageView(this.message);
            return;
        }
        ViewGroup.LayoutParams imageShowSize = EaseImageUtils.getImageShowSize(this.context, this.message);
        ViewGroup.LayoutParams layoutParams = this.imageView.getLayoutParams();
        layoutParams.width = imageShowSize.width;
        layoutParams.height = imageShowSize.height;
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onViewUpdate(EMMessage eMMessage) {
        super.onViewUpdate(eMMessage);
    }

    public EaseChatRowImage(Context context, EMMessage eMMessage, int i2, Object obj) {
        super(context, eMMessage, i2, obj);
    }
}
