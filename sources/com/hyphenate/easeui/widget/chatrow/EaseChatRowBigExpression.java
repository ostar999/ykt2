package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseIM;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.domain.EaseEmojicon;

/* loaded from: classes4.dex */
public class EaseChatRowBigExpression extends EaseChatRowText {
    private ImageView imageView;

    public EaseChatRowBigExpression(Context context, boolean z2) {
        super(context, z2);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowText, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onFindViewById() {
        this.percentageView = (TextView) findViewById(R.id.percentage);
        this.imageView = (ImageView) findViewById(R.id.image);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowText, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onInflateView() {
        this.inflater.inflate(!this.showSenderType ? R.layout.ease_row_received_bigexpression : R.layout.ease_row_sent_bigexpression, this);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowText, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onSetUpView() {
        EaseEmojicon emojiconInfo = EaseIM.getInstance().getEmojiconInfoProvider() != null ? EaseIM.getInstance().getEmojiconInfoProvider().getEmojiconInfo(this.message.getStringAttribute(EaseConstant.MESSAGE_ATTR_EXPRESSION_ID, null)) : null;
        if (emojiconInfo != null) {
            if (emojiconInfo.getBigIcon() != 0) {
                Glide.with(this.context).load(Integer.valueOf(emojiconInfo.getBigIcon())).apply((BaseRequestOptions<?>) RequestOptions.placeholderOf(R.drawable.ease_default_expression)).into(this.imageView);
            } else if (emojiconInfo.getBigIconPath() != null) {
                Glide.with(this.context).load(emojiconInfo.getBigIconPath()).apply((BaseRequestOptions<?>) RequestOptions.placeholderOf(R.drawable.ease_default_expression)).into(this.imageView);
            } else {
                this.imageView.setImageResource(R.drawable.ease_default_expression);
            }
        }
    }

    public EaseChatRowBigExpression(Context context, EMMessage eMMessage, int i2, BaseAdapter baseAdapter) {
        super(context, eMMessage, i2, baseAdapter);
    }
}
