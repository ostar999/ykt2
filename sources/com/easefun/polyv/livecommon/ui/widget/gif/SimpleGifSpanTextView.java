package com.easefun.polyv.livecommon.ui.widget.gif;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes3.dex */
public class SimpleGifSpanTextView extends GifSpanTextView {
    public SimpleGifSpanTextView(Context context) {
        super(context);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.gif.GifSpanTextView, android.widget.TextView, android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int size = View.MeasureSpec.getSize(widthMeasureSpec);
        if (mode == 1073741824 || !(getParent() instanceof ViewGroup)) {
            return;
        }
        int measuredWidth = ((ViewGroup) getParent()).getMeasuredWidth();
        int width = ((ViewGroup) getParent()).getWidth();
        if (measuredWidth == 0 || size > measuredWidth || getMeasuredWidth() >= size) {
            return;
        }
        if (width != measuredWidth || getMeasuredWidth() == getWidth()) {
            setMeasuredDimension(size, getMeasuredHeight());
        }
    }

    public SimpleGifSpanTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleGifSpanTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
