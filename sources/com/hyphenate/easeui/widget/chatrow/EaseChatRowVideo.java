package com.hyphenate.easeui.widget.chatrow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.hyphenate.chat.EMFileMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMVideoMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseDateUtils;
import com.hyphenate.easeui.utils.EaseImageUtils;
import com.hyphenate.util.EMLog;
import com.hyphenate.util.TextFormater;

/* loaded from: classes4.dex */
public class EaseChatRowVideo extends EaseChatRowFile {
    private static final String TAG = "EaseChatRowVideo";
    private ImageView imageView;
    private ImageView playView;
    private TextView sizeView;
    private TextView timeLengthView;

    public EaseChatRowVideo(Context context, boolean z2) {
        super(context, z2);
    }

    private void setBubbleView(int i2, int i3) {
        ViewGroup.LayoutParams layoutParams = this.bubbleLayout.getLayoutParams();
        layoutParams.width = i2;
        layoutParams.height = i3;
    }

    @SuppressLint({"StaticFieldLeak"})
    private void showVideoThumbView(EMMessage eMMessage) {
        ViewGroup.LayoutParams layoutParamsShowVideoThumb = EaseImageUtils.showVideoThumb(this.context, this.imageView, eMMessage);
        setBubbleView(layoutParamsShowVideoThumb.width, layoutParamsShowVideoThumb.height);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowFile, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onFindViewById() {
        this.imageView = (ImageView) findViewById(R.id.chatting_content_iv);
        this.sizeView = (TextView) findViewById(R.id.chatting_size_iv);
        this.timeLengthView = (TextView) findViewById(R.id.chatting_length_iv);
        this.playView = (ImageView) findViewById(R.id.chatting_status_btn);
        this.percentageView = (TextView) findViewById(R.id.percentage);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowFile, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onInflateView() {
        this.inflater.inflate(!this.showSenderType ? R.layout.ease_row_received_video : R.layout.ease_row_sent_video, this);
    }

    @Override // com.hyphenate.easeui.widget.chatrow.EaseChatRowFile, com.hyphenate.easeui.widget.chatrow.EaseChatRow
    public void onSetUpView() {
        View view = this.bubbleLayout;
        if (view != null) {
            view.setBackground(null);
        }
        EMVideoMessageBody eMVideoMessageBody = (EMVideoMessageBody) this.message.getBody();
        if (eMVideoMessageBody.getDuration() > 0) {
            this.timeLengthView.setText(EaseDateUtils.toTime(eMVideoMessageBody.getDuration()));
        }
        EMMessage.Direct direct = this.message.direct();
        EMMessage.Direct direct2 = EMMessage.Direct.RECEIVE;
        if (direct != direct2) {
            this.sizeView.setText(TextFormater.getDataSize(eMVideoMessageBody.getVideoFileLength()));
        } else if (eMVideoMessageBody.getVideoFileLength() > 0) {
            this.sizeView.setText(TextFormater.getDataSize(eMVideoMessageBody.getVideoFileLength()));
        }
        EMLog.d(TAG, "video thumbnailStatus:" + eMVideoMessageBody.thumbnailDownloadStatus());
        if (this.message.direct() == direct2) {
            if (eMVideoMessageBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.DOWNLOADING) {
                this.imageView.setImageResource(R.drawable.ease_default_image);
                return;
            } else {
                this.imageView.setImageResource(R.drawable.ease_default_image);
                showVideoThumbView(this.message);
                return;
            }
        }
        if (eMVideoMessageBody.thumbnailDownloadStatus() != EMFileMessageBody.EMDownloadStatus.DOWNLOADING && eMVideoMessageBody.thumbnailDownloadStatus() != EMFileMessageBody.EMDownloadStatus.PENDING && eMVideoMessageBody.thumbnailDownloadStatus() != EMFileMessageBody.EMDownloadStatus.FAILED) {
            ProgressBar progressBar = this.progressBar;
            if (progressBar != null) {
                progressBar.setVisibility(8);
            }
            TextView textView = this.percentageView;
            if (textView != null) {
                textView.setVisibility(8);
            }
            this.imageView.setImageResource(R.drawable.ease_default_image);
            showVideoThumbView(this.message);
            return;
        }
        ProgressBar progressBar2 = this.progressBar;
        if (progressBar2 != null) {
            progressBar2.setVisibility(4);
        }
        TextView textView2 = this.percentageView;
        if (textView2 != null) {
            textView2.setVisibility(4);
        }
        if (eMVideoMessageBody.thumbnailDownloadStatus() == EMFileMessageBody.EMDownloadStatus.PENDING) {
            showVideoThumbView(this.message);
        } else {
            this.imageView.setImageResource(R.drawable.ease_default_image);
        }
    }

    public EaseChatRowVideo(Context context, EMMessage eMMessage, int i2, Object obj) {
        super(context, eMMessage, i2, obj);
    }
}
