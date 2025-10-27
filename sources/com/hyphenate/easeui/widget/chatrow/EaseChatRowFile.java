package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMNormalFileMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.DarkModeHelper;
import com.hyphenate.easeui.utils.EaseEditTextUtils;
import com.hyphenate.easeui.utils.EaseFileUtils;
import com.hyphenate.util.TextFormater;

/* loaded from: classes4.dex */
public class EaseChatRowFile extends EaseChatRow {
    private static final String TAG = "EaseChatRowFile";
    private EMNormalFileMessageBody fileMessageBody;
    protected TextView fileNameView;
    protected TextView fileSizeView;
    protected TextView fileStateView;

    public EaseChatRowFile(Context context, boolean z2) {
        super(context, z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onSetUpView$0() {
        this.fileNameView.setText(EaseEditTextUtils.ellipsizeMiddleString(this.fileNameView, this.fileMessageBody.getFileName(), this.fileNameView.getMaxLines(), (this.fileNameView.getWidth() - this.fileNameView.getPaddingLeft()) - this.fileNameView.getPaddingRight()));
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onFindViewById() {
        this.fileNameView = (TextView) findViewById(R.id.tv_file_name);
        this.fileSizeView = (TextView) findViewById(R.id.tv_file_size);
        this.fileStateView = (TextView) findViewById(R.id.tv_file_state);
        this.percentageView = (TextView) findViewById(R.id.percentage);
        ImageView imageView = (ImageView) findViewById(R.id.iv_file_icon);
        if (DarkModeHelper.isDarkMode(getContext())) {
            findViewById(R.id.bubble).setBackground(ContextCompat.getDrawable(getContext(), !this.showSenderType ? R.drawable.ease_chat_bubble_receive_bg_night : R.drawable.ease_chat_bubble_send_bg_night));
            this.fileNameView.setTextColor(Color.parseColor("#7380a9"));
            this.fileSizeView.setTextColor(Color.parseColor("#606A8A"));
            this.fileStateView.setTextColor(Color.parseColor("#606A8A"));
            imageView.setImageResource(R.drawable.ease_chat_item_file_night);
        }
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onInflateView() {
        this.inflater.inflate(!this.showSenderType ? R.layout.ease_row_received_file : R.layout.ease_row_sent_file, this);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageCreate() {
        super.onMessageCreate();
        this.progressBar.setVisibility(0);
        TextView textView = this.percentageView;
        if (textView != null) {
            textView.setVisibility(4);
        }
        ImageView imageView = this.statusView;
        if (imageView != null) {
            imageView.setVisibility(4);
        }
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageError() {
        super.onMessageError();
        this.progressBar.setVisibility(4);
        TextView textView = this.percentageView;
        if (textView != null) {
            textView.setVisibility(4);
        }
        ImageView imageView = this.statusView;
        if (imageView != null) {
            imageView.setVisibility(0);
        }
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageInProgress() {
        super.onMessageInProgress();
        if (this.progressBar.getVisibility() != 0) {
            this.progressBar.setVisibility(0);
        }
        TextView textView = this.percentageView;
        if (textView != null) {
            textView.setVisibility(0);
            this.percentageView.setText(this.message.progress() + "%");
        }
        ImageView imageView = this.statusView;
        if (imageView != null) {
            imageView.setVisibility(4);
        }
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onMessageSuccess() {
        TextView textView;
        super.onMessageSuccess();
        this.progressBar.setVisibility(4);
        TextView textView2 = this.percentageView;
        if (textView2 != null) {
            textView2.setVisibility(4);
        }
        ImageView imageView = this.statusView;
        if (imageView != null) {
            imageView.setVisibility(4);
        }
        if (this.message.direct() != EMMessage.Direct.SEND || (textView = this.fileStateView) == null) {
            return;
        }
        textView.setText(R.string.have_uploaded);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onSetUpView() {
        EMNormalFileMessageBody eMNormalFileMessageBody = (EMNormalFileMessageBody) this.message.getBody();
        this.fileMessageBody = eMNormalFileMessageBody;
        Uri localUri = eMNormalFileMessageBody.getLocalUri();
        this.fileNameView.setText(this.fileMessageBody.getFileName());
        this.fileNameView.post(new Runnable() { // from class: com.hyphenate.easeui.widget.chatrow.b
            @Override // java.lang.Runnable
            public final void run() {
                this.f8754c.lambda$onSetUpView$0();
            }
        });
        this.fileSizeView.setText(TextFormater.getDataSize(this.fileMessageBody.getFileSize()));
        if (this.message.direct() == EMMessage.Direct.SEND) {
            if (EaseFileUtils.isFileExistByUri(this.context, localUri) && this.message.status() == EMMessage.Status.SUCCESS) {
                this.fileStateView.setText(R.string.have_uploaded);
            } else {
                this.fileStateView.setText("");
            }
        }
        if (this.message.direct() == EMMessage.Direct.RECEIVE) {
            if (EaseFileUtils.isFileExistByUri(this.context, localUri)) {
                this.fileStateView.setText(R.string.have_downloaded);
            } else {
                this.fileStateView.setText(R.string.did_not_download);
            }
        }
    }

    public EaseChatRowFile(Context context, EMMessage eMMessage, int i2, Object obj) {
        super(context, eMMessage, i2, obj);
    }
}
