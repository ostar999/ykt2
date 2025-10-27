package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/* loaded from: classes3.dex */
class NoClickThroughFrameLayout extends BuildLayerFrameLayout {
    public NoClickThroughFrameLayout(Context context) {
        super(context);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    public NoClickThroughFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoClickThroughFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
