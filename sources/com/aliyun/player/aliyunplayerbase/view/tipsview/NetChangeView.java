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
public class NetChangeView extends RelativeLayout {
    private OnNetChangeClickListener mOnNetChangeClickListener;
    private OnTipsViewBackClickListener mOnTipsViewBackClickListener;

    public interface OnNetChangeClickListener {
        void onContinuePlay();

        void onStopPlay();
    }

    public NetChangeView(Context context) {
        super(context);
        this.mOnNetChangeClickListener = null;
        this.mOnTipsViewBackClickListener = null;
        init();
    }

    private void init() {
        View viewInflate = ((LayoutInflater) getContext().getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.alivc_dialog_netchange_new, this);
        viewInflate.findViewById(R.id.continue_play).setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.NetChangeView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (NetChangeView.this.mOnNetChangeClickListener != null) {
                    NetChangeView.this.mOnNetChangeClickListener.onContinuePlay();
                }
            }
        });
        TextView textView = (TextView) viewInflate.findViewById(R.id.stop_play);
        ImageView imageView = (ImageView) viewInflate.findViewById(R.id.iv_back);
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.NetChangeView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (NetChangeView.this.mOnNetChangeClickListener != null) {
                    NetChangeView.this.mOnNetChangeClickListener.onStopPlay();
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.aliyunplayerbase.view.tipsview.NetChangeView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (NetChangeView.this.mOnTipsViewBackClickListener != null) {
                    NetChangeView.this.mOnTipsViewBackClickListener.onBackClick();
                }
            }
        });
    }

    public void hideBackView() {
        findViewById(R.id.iv_back).setVisibility(8);
    }

    public void setOnBackClickListener(OnTipsViewBackClickListener onTipsViewBackClickListener) {
        this.mOnTipsViewBackClickListener = onTipsViewBackClickListener;
    }

    public void setOnNetChangeClickListener(OnNetChangeClickListener onNetChangeClickListener) {
        this.mOnNetChangeClickListener = onNetChangeClickListener;
    }

    public void showBackView() {
        findViewById(R.id.iv_back).setVisibility(0);
    }

    public NetChangeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mOnNetChangeClickListener = null;
        this.mOnTipsViewBackClickListener = null;
        init();
    }

    public NetChangeView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mOnNetChangeClickListener = null;
        this.mOnTipsViewBackClickListener = null;
        init();
    }
}
