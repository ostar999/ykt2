package com.easefun.polyv.livecommon.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.R;
import com.plv.thirdpart.blankj.utilcode.util.ToastUtils;

/* loaded from: classes3.dex */
public class PLVPlayerRetryLayout extends FrameLayout {
    private TextView playerRetryTv;

    public PLVPlayerRetryLayout(@NonNull Context context) {
        this(context, null);
    }

    private void initView(Context context) {
        this.playerRetryTv = (TextView) LayoutInflater.from(context).inflate(R.layout.plv_player_retry_layout, (ViewGroup) this, true).findViewById(R.id.plv_player_retry_tv);
    }

    public void onRetryFailed(String message) {
        ToastUtils.showShort(message);
    }

    public void setOnClickPlayerRetryListener(View.OnClickListener onClickListener) {
        this.playerRetryTv.setOnClickListener(onClickListener);
    }

    public PLVPlayerRetryLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVPlayerRetryLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }
}
