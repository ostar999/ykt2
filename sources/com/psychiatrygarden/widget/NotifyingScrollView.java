package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/* loaded from: classes6.dex */
public class NotifyingScrollView extends ScrollView {
    private OnScrollChangedListener mOnScrollChangedListener;

    public interface OnScrollChangedListener {
        void onScrollChanged(ScrollView who, int l2, int t2, int oldl, int oldt);
    }

    public NotifyingScrollView(Context context) {
        super(context);
    }

    @Override // android.view.View
    public void onScrollChanged(int l2, int t2, int oldl, int oldt) {
        super.onScrollChanged(l2, t2, oldl, oldt);
        OnScrollChangedListener onScrollChangedListener = this.mOnScrollChangedListener;
        if (onScrollChangedListener != null) {
            onScrollChangedListener.onScrollChanged(this, l2, t2, oldl, oldt);
        }
    }

    public void setOnScrollChangedListener(OnScrollChangedListener listener) {
        this.mOnScrollChangedListener = listener;
    }

    public NotifyingScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NotifyingScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
