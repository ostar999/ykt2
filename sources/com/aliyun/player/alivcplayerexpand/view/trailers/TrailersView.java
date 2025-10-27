package com.aliyun.player.alivcplayerexpand.view.trailers;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aliyun.player.alivcplayerexpand.R;

/* loaded from: classes2.dex */
public class TrailersView extends RelativeLayout {
    public OnTrailerViewClickListener mListener;
    private TextView mTrailerOpenTextView;
    private LinearLayout mTrailerPlayAgainRootView;
    private LinearLayout mTrailerPlayTipsRoot;
    private FrameLayout mTrailerTipsRootView;
    private View view;

    public interface OnTrailerViewClickListener {
        void onOpenVipClick();

        void onTrailerPlayAgainClick();
    }

    public TrailersView(Context context) {
        super(context);
        init();
    }

    private void init() {
        this.view = ((LayoutInflater) getContext().getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.alivc_trailers_view_layout, (ViewGroup) this, true);
        initView();
        initListener();
    }

    private void initListener() {
        this.mTrailerOpenTextView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.trailers.TrailersView.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                OnTrailerViewClickListener onTrailerViewClickListener = TrailersView.this.mListener;
                if (onTrailerViewClickListener != null) {
                    onTrailerViewClickListener.onOpenVipClick();
                }
            }
        });
        this.mTrailerPlayAgainRootView.setOnClickListener(new View.OnClickListener() { // from class: com.aliyun.player.alivcplayerexpand.view.trailers.TrailersView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                OnTrailerViewClickListener onTrailerViewClickListener = TrailersView.this.mListener;
                if (onTrailerViewClickListener != null) {
                    onTrailerViewClickListener.onTrailerPlayAgainClick();
                }
            }
        });
    }

    private void initView() {
        this.mTrailerOpenTextView = (TextView) this.view.findViewById(R.id.tv_trailer_open);
        this.mTrailerTipsRootView = (FrameLayout) this.view.findViewById(R.id.ll_trailer_tips_root);
        this.mTrailerPlayTipsRoot = (LinearLayout) this.view.findViewById(R.id.ll_trailer_play_tips_root);
        this.mTrailerPlayAgainRootView = (LinearLayout) this.view.findViewById(R.id.ll_trailer_play_again);
    }

    public void hideAll() {
        LinearLayout linearLayout = this.mTrailerPlayTipsRoot;
        if (linearLayout != null) {
            linearLayout.setVisibility(8);
        }
        FrameLayout frameLayout = this.mTrailerTipsRootView;
        if (frameLayout != null) {
            frameLayout.setVisibility(8);
        }
    }

    public void setOnTrailerViewClickListener(OnTrailerViewClickListener onTrailerViewClickListener) {
        this.mListener = onTrailerViewClickListener;
    }

    public void trailerPlayTipsIsShow(boolean z2) {
        LinearLayout linearLayout = this.mTrailerPlayTipsRoot;
        if (linearLayout != null) {
            linearLayout.setVisibility(z2 ? 0 : 8);
        }
        FrameLayout frameLayout = this.mTrailerTipsRootView;
        if (frameLayout != null) {
            frameLayout.setVisibility(z2 ? 8 : 0);
        }
    }

    public TrailersView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public TrailersView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }
}
