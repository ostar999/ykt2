package com.aliyun.player.aliyunplayerbase.view.tipsview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aliyun.player.aliyunplayerbase.R;

/* loaded from: classes2.dex */
public class CustomTipsView extends RelativeLayout {
    private ImageView mBackImageView;
    private TextView mContinuePlayTextView;
    private TextView mMsgTextView;
    private OnTipsViewBackClickListener mOnTipsViewBackClickListener;
    private OnTipsViewClickListener mOnTipsViewClickListener;
    private TextView mStopPlayBtn;

    public interface OnTipsViewClickListener {
        void onExit();

        void onWait();
    }

    public CustomTipsView(Context context) {
        super(context);
        this.mOnTipsViewClickListener = null;
        this.mOnTipsViewBackClickListener = null;
        init();
    }

    private void init() {
        View viewInflate = ((LayoutInflater) getContext().getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.alivc_dialog_netchange, this);
        this.mMsgTextView = (TextView) viewInflate.findViewById(R.id.msg);
        TextView textView = (TextView) viewInflate.findViewById(R.id.continue_play);
        this.mContinuePlayTextView = textView;
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.CustomTipsView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CustomTipsView.this.mOnTipsViewClickListener != null) {
                    CustomTipsView.this.mOnTipsViewClickListener.onWait();
                }
            }
        });
        this.mStopPlayBtn = (TextView) viewInflate.findViewById(R.id.stop_play);
        this.mBackImageView = (ImageView) viewInflate.findViewById(R.id.iv_back);
        this.mStopPlayBtn.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.CustomTipsView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CustomTipsView.this.mOnTipsViewClickListener != null) {
                    CustomTipsView.this.mOnTipsViewClickListener.onExit();
                }
            }
        });
        this.mBackImageView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.CustomTipsView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (CustomTipsView.this.mOnTipsViewBackClickListener != null) {
                    CustomTipsView.this.mOnTipsViewBackClickListener.onBackClick();
                }
            }
        });
    }

    public void setCancelText(String str) {
        TextView textView = this.mStopPlayBtn;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setConfirmText(String str) {
        TextView textView = this.mContinuePlayTextView;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setOnBackClickListener(OnTipsViewBackClickListener onTipsViewBackClickListener) {
        this.mOnTipsViewBackClickListener = onTipsViewBackClickListener;
    }

    public void setOnTipsViewClickListener(OnTipsViewClickListener onTipsViewClickListener) {
        this.mOnTipsViewClickListener = onTipsViewClickListener;
    }

    public void setTipsText(String str) {
        TextView textView = this.mMsgTextView;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public CustomTipsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnTipsViewClickListener = null;
        this.mOnTipsViewBackClickListener = null;
        init();
    }

    public CustomTipsView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mOnTipsViewClickListener = null;
        this.mOnTipsViewBackClickListener = null;
        init();
    }
}
