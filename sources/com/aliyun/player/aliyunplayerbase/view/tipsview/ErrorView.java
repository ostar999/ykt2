package com.aliyun.player.aliyunplayerbase.view.tipsview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aliyun.player.aliyunplayerbase.R;

/* loaded from: classes2.dex */
public class ErrorView extends RelativeLayout {
    private static final String TAG = "ErrorView";
    private ImageView mBackImageView;
    private TextView mCodeView;
    private TextView mMsgView;
    private OnRetryClickListener mOnRetryClickListener;
    private OnTipsViewBackClickListener mOnTipsViewBackClickListener;
    private TextView mRetryBtn;
    private View mRetryView;

    public interface OnRetryClickListener {
        void onRetryClick();
    }

    public ErrorView(Context context) throws Resources.NotFoundException {
        super(context);
        this.mOnTipsViewBackClickListener = null;
        this.mOnRetryClickListener = null;
        init();
    }

    private void init() throws Resources.NotFoundException {
        View viewInflate = ((LayoutInflater) getContext().getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.alivc_dialog_error, this);
        this.mRetryBtn = (TextView) viewInflate.findViewById(R.id.retry_btn);
        this.mMsgView = (TextView) viewInflate.findViewById(R.id.msg);
        this.mCodeView = (TextView) viewInflate.findViewById(R.id.code);
        this.mRetryView = viewInflate.findViewById(R.id.retry);
        this.mBackImageView = (ImageView) viewInflate.findViewById(R.id.iv_back);
        int dimensionPixelSize = getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height", "dimen", "android"));
        if (dimensionPixelSize > 0) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mBackImageView.getLayoutParams();
            layoutParams.topMargin = dimensionPixelSize;
            this.mBackImageView.setLayoutParams(layoutParams);
        }
        this.mRetryView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.ErrorView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ErrorView.this.mOnRetryClickListener != null) {
                    ErrorView.this.mOnRetryClickListener.onRetryClick();
                }
            }
        });
        this.mBackImageView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.ErrorView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ErrorView.this.mOnTipsViewBackClickListener != null) {
                    ErrorView.this.mOnTipsViewBackClickListener.onBackClick();
                }
            }
        });
    }

    public void setOnBackClickListener(OnTipsViewBackClickListener onTipsViewBackClickListener) {
        this.mOnTipsViewBackClickListener = onTipsViewBackClickListener;
    }

    public void setOnRetryClickListener(OnRetryClickListener onRetryClickListener) {
        this.mOnRetryClickListener = onRetryClickListener;
    }

    public void updateTips(int i2, String str, String str2) {
        this.mMsgView.setText(str2);
        this.mCodeView.setText(getContext().getString(R.string.alivc_error_code) + i2 + " - " + str);
    }

    public void updateTipsWithoutCode(String str) {
        this.mMsgView.setText(str);
        this.mCodeView.setVisibility(8);
    }

    public ErrorView(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.mOnTipsViewBackClickListener = null;
        this.mOnRetryClickListener = null;
        init();
    }

    public ErrorView(Context context, AttributeSet attributeSet, int i2) throws Resources.NotFoundException {
        super(context, attributeSet, i2);
        this.mOnTipsViewBackClickListener = null;
        this.mOnRetryClickListener = null;
        init();
    }
}
