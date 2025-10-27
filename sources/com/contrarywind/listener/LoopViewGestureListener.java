package com.contrarywind.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import com.contrarywind.view.WheelView;

/* loaded from: classes3.dex */
public final class LoopViewGestureListener extends GestureDetector.SimpleOnGestureListener {
    private final WheelView wheelView;

    public LoopViewGestureListener(WheelView wheelView) {
        this.wheelView = wheelView;
    }

    @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
    public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f2, float f3) {
        this.wheelView.scrollBy(f3);
        return true;
    }
}
