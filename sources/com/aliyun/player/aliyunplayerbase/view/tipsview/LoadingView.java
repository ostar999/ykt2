package com.aliyun.player.aliyunplayerbase.view.tipsview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.aliyun.player.aliyunplayerbase.R;

/* loaded from: classes2.dex */
public class LoadingView extends RelativeLayout {
    private static final String TAG = "LoadingView";
    private TextView mLoadPercentView;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    private void init() {
        TextView textView = (TextView) ((LayoutInflater) getContext().getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.alivc_dialog_loading, this).findViewById(R.id.net_speed);
        this.mLoadPercentView = textView;
        textView.setText(getContext().getString(R.string.alivc_loading) + " 0%");
    }

    public void setOnlyLoading() {
        findViewById(R.id.loading_layout).setVisibility(8);
    }

    public void updateLoadingPercent(int i2) {
        this.mLoadPercentView.setText(getContext().getString(R.string.alivc_loading) + i2 + "%");
    }

    public LoadingView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public LoadingView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }
}
