package com.psychiatrygarden.widget.imagezoom;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

/* loaded from: classes6.dex */
public class CupcakeGestureDetector implements GestureDetector {
    private static final String LOG_TAG = "CupcakeGestureDetector";
    private boolean mIsDragging;
    float mLastTouchX;
    float mLastTouchY;
    protected OnGestureListener mListener;
    final float mMinimumVelocity;
    final float mTouchSlop;
    private VelocityTracker mVelocityTracker;

    public CupcakeGestureDetector(Context context) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    public float getActiveX(MotionEvent ev) {
        return ev.getX();
    }

    public float getActiveY(MotionEvent ev) {
        return ev.getY();
    }

    @Override // com.psychiatrygarden.widget.imagezoom.GestureDetector
    public boolean isDragging() {
        return this.mIsDragging;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.GestureDetector
    public boolean isScaling() {
        return false;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.GestureDetector
    public boolean onTouchEvent(MotionEvent ev) {
        VelocityTracker velocityTracker;
        int action = ev.getAction();
        if (action == 0) {
            VelocityTracker velocityTrackerObtain = VelocityTracker.obtain();
            this.mVelocityTracker = velocityTrackerObtain;
            if (velocityTrackerObtain != null) {
                velocityTrackerObtain.addMovement(ev);
            } else {
                Log.i(LOG_TAG, "Velocity tracker is null");
            }
            this.mLastTouchX = getActiveX(ev);
            this.mLastTouchY = getActiveY(ev);
            this.mIsDragging = false;
        } else if (action == 1) {
            if (this.mIsDragging && this.mVelocityTracker != null) {
                this.mLastTouchX = getActiveX(ev);
                this.mLastTouchY = getActiveY(ev);
                this.mVelocityTracker.addMovement(ev);
                this.mVelocityTracker.computeCurrentVelocity(1000);
                float xVelocity = this.mVelocityTracker.getXVelocity();
                float yVelocity = this.mVelocityTracker.getYVelocity();
                if (Math.max(Math.abs(xVelocity), Math.abs(yVelocity)) >= this.mMinimumVelocity) {
                    this.mListener.onFling(this.mLastTouchX, this.mLastTouchY, -xVelocity, -yVelocity);
                }
            }
            VelocityTracker velocityTracker2 = this.mVelocityTracker;
            if (velocityTracker2 != null) {
                velocityTracker2.recycle();
                this.mVelocityTracker = null;
            }
        } else if (action == 2) {
            float activeX = getActiveX(ev);
            float activeY = getActiveY(ev);
            float f2 = activeX - this.mLastTouchX;
            float f3 = activeY - this.mLastTouchY;
            if (!this.mIsDragging) {
                this.mIsDragging = Math.sqrt((double) ((f2 * f2) + (f3 * f3))) >= ((double) this.mTouchSlop);
            }
            if (this.mIsDragging) {
                this.mListener.onDrag(f2, f3);
                this.mLastTouchX = activeX;
                this.mLastTouchY = activeY;
                VelocityTracker velocityTracker3 = this.mVelocityTracker;
                if (velocityTracker3 != null) {
                    velocityTracker3.addMovement(ev);
                }
            }
        } else if (action == 3 && (velocityTracker = this.mVelocityTracker) != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
        return true;
    }

    @Override // com.psychiatrygarden.widget.imagezoom.GestureDetector
    public void setOnGestureListener(OnGestureListener listener) {
        this.mListener = listener;
    }
}
