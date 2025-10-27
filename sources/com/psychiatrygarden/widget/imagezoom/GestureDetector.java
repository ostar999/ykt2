package com.psychiatrygarden.widget.imagezoom;

import android.view.MotionEvent;

/* loaded from: classes6.dex */
public interface GestureDetector {
    boolean isDragging();

    boolean isScaling();

    boolean onTouchEvent(MotionEvent ev);

    void setOnGestureListener(OnGestureListener listener);
}
