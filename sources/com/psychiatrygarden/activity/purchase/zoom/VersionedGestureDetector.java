package com.psychiatrygarden.activity.purchase.zoom;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import androidx.core.view.MotionEventCompat;

/* loaded from: classes5.dex */
public abstract class VersionedGestureDetector {
    static final String LOG_TAG = "VersionedGestureDetector";
    OnGestureListener mListener;

    public static class CupcakeDetector extends VersionedGestureDetector {
        private boolean mIsDragging;
        float mLastTouchX;
        float mLastTouchY;
        final float mMinimumVelocity;
        final float mTouchSlop;
        private VelocityTracker mVelocityTracker;

        public CupcakeDetector(Context context) {
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

        @Override // com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector
        public boolean isScaling() {
            return false;
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector
        public boolean onTouchEvent(MotionEvent ev) {
            VelocityTracker velocityTracker;
            int action = ev.getAction();
            if (action == 0) {
                VelocityTracker velocityTrackerObtain = VelocityTracker.obtain();
                this.mVelocityTracker = velocityTrackerObtain;
                velocityTrackerObtain.addMovement(ev);
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
    }

    @TargetApi(5)
    public static class EclairDetector extends CupcakeDetector {
        private static final int INVALID_POINTER_ID = -1;
        private int mActivePointerId;
        private int mActivePointerIndex;

        public EclairDetector(Context context) {
            super(context);
            this.mActivePointerId = -1;
            this.mActivePointerIndex = 0;
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector.CupcakeDetector
        public float getActiveX(MotionEvent ev) {
            try {
                return ev.getX(this.mActivePointerIndex);
            } catch (Exception unused) {
                return ev.getX();
            }
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector.CupcakeDetector
        public float getActiveY(MotionEvent ev) {
            try {
                return ev.getY(this.mActivePointerIndex);
            } catch (Exception unused) {
                return ev.getY();
            }
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector.CupcakeDetector, com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector
        public boolean onTouchEvent(MotionEvent ev) {
            int action = ev.getAction() & 255;
            if (action != 0) {
                if (action == 1 || action == 3) {
                    this.mActivePointerId = -1;
                } else if (action == 6) {
                    int action2 = (ev.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                    if (ev.getPointerId(action2) == this.mActivePointerId) {
                        int i2 = action2 != 0 ? 0 : 1;
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

    @TargetApi(8)
    public static class FroyoDetector extends EclairDetector {
        private final ScaleGestureDetector mDetector;
        private final ScaleGestureDetector.OnScaleGestureListener mScaleListener;

        public FroyoDetector(Context context) {
            super(context);
            ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() { // from class: com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector.FroyoDetector.1
                @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
                public boolean onScale(ScaleGestureDetector detector) {
                    FroyoDetector.this.mListener.onScale(detector.getScaleFactor(), detector.getFocusX(), detector.getFocusY());
                    return true;
                }

                @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
                public boolean onScaleBegin(ScaleGestureDetector detector) {
                    return true;
                }

                @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
                public void onScaleEnd(ScaleGestureDetector detector) {
                }
            };
            this.mScaleListener = onScaleGestureListener;
            this.mDetector = new ScaleGestureDetector(context, onScaleGestureListener);
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector.CupcakeDetector, com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector
        public boolean isScaling() {
            return this.mDetector.isInProgress();
        }

        @Override // com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector.EclairDetector, com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector.CupcakeDetector, com.psychiatrygarden.activity.purchase.zoom.VersionedGestureDetector
        public boolean onTouchEvent(MotionEvent ev) {
            this.mDetector.onTouchEvent(ev);
            return super.onTouchEvent(ev);
        }
    }

    public interface OnGestureListener {
        void onDrag(float dx, float dy);

        void onFling(float startX, float startY, float velocityX, float velocityY);

        void onScale(float scaleFactor, float focusX, float focusY);
    }

    public static VersionedGestureDetector newInstance(Context context, OnGestureListener listener) {
        FroyoDetector froyoDetector = new FroyoDetector(context);
        froyoDetector.mListener = listener;
        return froyoDetector;
    }

    public abstract boolean isScaling();

    public abstract boolean onTouchEvent(MotionEvent ev);
}
