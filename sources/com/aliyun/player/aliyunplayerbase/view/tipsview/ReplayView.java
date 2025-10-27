package com.aliyun.player.aliyunplayerbase.view.tipsview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.aliyun.player.aliyunplayerbase.R;

/* loaded from: classes2.dex */
public class ReplayView extends RelativeLayout {
    private ImageView mBackImageView;
    private OnReplayClickListener mOnReplayClickListener;
    private OnTipsViewBackClickListener mOnTipsViewBackClickListener;
    private View mReplayBtn;

    public interface OnReplayClickListener {
        void onReplay();
    }

    public ReplayView(Context context) {
        super(context);
        this.mOnReplayClickListener = null;
        this.mOnTipsViewBackClickListener = null;
        init();
    }

    private void init() {
        View viewInflate = ((LayoutInflater) getContext().getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.alivc_dialog_replay, this);
        this.mReplayBtn = viewInflate.findViewById(R.id.replay);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_back);
        this.mBackImageView = imageView;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.topMargin = getContext().getResources().getDimensionPixelSize(getContext().getResources().getIdentifier("status_bar_height", "dimen", "android"));
        this.mBackImageView.setLayoutParams(layoutParams);
        this.mReplayBtn.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.ReplayView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ReplayView.this.mOnReplayClickListener != null) {
                    ReplayView.this.mOnReplayClickListener.onReplay();
                }
            }
        });
        this.mBackImageView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.ReplayView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ReplayView.this.mOnTipsViewBackClickListener != null) {
                    ReplayView.this.mOnTipsViewBackClickListener.onBackClick();
                }
            }
        });
    }

    public void isShowBackBtn(boolean z2) {
        this.mBackImageView.setVisibility(z2 ? 0 : 8);
    }

    public void setOnBackClickListener(OnTipsViewBackClickListener onTipsViewBackClickListener) {
        this.mOnTipsViewBackClickListener = onTipsViewBackClickListener;
    }

    public void setOnReplayClickListener(OnReplayClickListener onReplayClickListener) {
        this.mOnReplayClickListener = onReplayClickListener;
    }

    public ReplayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnReplayClickListener = null;
        this.mOnTipsViewBackClickListener = null;
        init();
    }

    public ReplayView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mOnReplayClickListener = null;
        this.mOnTipsViewBackClickListener = null;
        init();
    }
}
