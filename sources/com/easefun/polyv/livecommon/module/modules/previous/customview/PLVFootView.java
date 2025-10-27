package com.easefun.polyv.livecommon.module.modules.previous.customview;

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
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.extra.IRefreshView;
import me.dkzwm.widget.srl.indicator.IIndicator;

/* loaded from: classes3.dex */
public class PLVFootView extends FrameLayout implements IRefreshView<IIndicator> {
    private final TextView loadMoreTv;

    public PLVFootView(@NonNull Context context) {
        this(context, null);
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getCustomHeight() {
        return 0;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getStyle() {
        return 0;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public int getType() {
        return 1;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    @NonNull
    public View getView() {
        return this;
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onFingerUp(SmoothRefreshLayout layout, IIndicator indicator) {
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onPureScrollPositionChanged(SmoothRefreshLayout layout, byte status, IIndicator indicator) {
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshBegin(SmoothRefreshLayout layout, IIndicator indicator) {
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshComplete(SmoothRefreshLayout layout, boolean isSuccessful) {
        if (!isSuccessful) {
            this.loadMoreTv.setText(R.string.plv_previous_load_error);
        } else if (layout.isEnabledNoMoreData()) {
            this.loadMoreTv.setText(R.string.plv_previous_no_more_data);
        }
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshPositionChanged(SmoothRefreshLayout layout, byte status, IIndicator indicator) {
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onRefreshPrepare(SmoothRefreshLayout layout) {
    }

    @Override // me.dkzwm.widget.srl.extra.IRefreshView
    public void onReset(SmoothRefreshLayout layout) {
    }

    public void setText(String string) {
        this.loadMoreTv.setText(string);
    }

    public PLVFootView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVFootView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(getContext()).inflate(R.layout.plv_previous_loadmore_foot, (ViewGroup) this, true);
        this.loadMoreTv = (TextView) findViewById(R.id.plv_foot_load_more_tv);
    }
}
