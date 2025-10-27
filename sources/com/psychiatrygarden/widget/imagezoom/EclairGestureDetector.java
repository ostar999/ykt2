package com.psychiatrygarden.widget.imagezoom;

import android.content.Context;
import android.view.MotionEvent;

/* loaded from: classes6.dex */
public class EclairGestureDetector extends CupcakeGestureDetector {
    private static final int INVALID_POINTER_ID = -1;
    private int mActivePointerId;
    private int mActivePointerIndex;

    public EclairGestureDetector(Context context) {
        super(context);
        this.mActivePointerId = -1;
        this.mActivePointerIndex = 0;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.CupcakeGestureDetector
    public float getActiveX(MotionEvent ev) {
        try {
            return ev.getX(this.mActivePointerIndex);
        } catch (Exception unused) {
            return ev.getX();
        }
    }

    @Override // com.psychiatrygarden.widget.imagezoom.CupcakeGestureDetector
    public float getActiveY(MotionEvent ev) {
        try {
            return ev.getY(this.mActivePointerIndex);
        } catch (Exception unused) {
            return ev.getY();
        }
    }

    @Override // com.psychiatrygarden.widget.imagezoom.CupcakeGestureDetector, com.psychiatrygarden.widget.imagezoom.GestureDetector
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction() & 255;
        if (action != 0) {
            if (action == 1 || action == 3) {
                this.mActivePointerId = -1;
            } else if (action == 6) {
                int pointerIndex = Compat.getPointerIndex(ev.getAction());
                if (ev.getPointerId(pointerIndex) == this.mActivePointerId) {
                    int i2 = pointerIndex != 0 ? 0 : 1;
                    this.mActivePointerId = ev.getPointerId(i2);
                    this.mLastTouchX = ev.getX(i2);
                    this.mLastTouchY = ev.getY(i2);
                }
            }
        } else {
            this.mActivePointerId = ev.getPointerId(0);
        }
        int i3 = this.mActivePointerId;
        this.mActivePointerIndex = ev.findPointerIndex(i3 != -1 ? i3 : 0);
        return super.onTouchEvent(ev);
    }
}
