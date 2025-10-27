package com.easefun.polyv.livecommon.ui.widget.scrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/* loaded from: classes3.dex */
public class PLVNoTouchScrollView extends ScrollView {
    public PLVNoTouchScrollView(Context context) {
        super(context);
    }

    @Override // android.widget.ScrollView, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }

    public PLVNoTouchScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PLVNoTouchScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
