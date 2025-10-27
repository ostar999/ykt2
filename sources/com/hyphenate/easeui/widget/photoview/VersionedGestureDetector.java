package com.hyphenate.easeui.widget.photoview;

import android.annotation.TargetApi;
import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import androidx.core.view.MotionEventCompat;

/* loaded from: classes4.dex */
abstract class VersionedGestureDetector {
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

        public float getActiveX(MotionEvent motionEvent) {
            return motionEvent.getX();
        }

        public float getActiveY(MotionEvent motionEvent) {
            return motionEvent.getY();
        }

        @Override // com.hyphenate.easeui.widget.photoview.VersionedGestureDetector
        public boolean isScaling() {
            return false;
        }

        @Override // com.hyphenate.easeui.widget.photoview.VersionedGestureDetector
        public boolean onTouchEvent(MotionEvent motionEvent) {
            VelocityTracker velocityTracker;
            int action = motionEvent.getAction();
            if (action == 0) {
                VelocityTracker velocityTrackerObtain = VelocityTracker.obtain();
                this.mVelocityTracker = velocityTrackerObtain;
                velocityTrackerObtain.addMovement(motionEvent);
                this.mLastTouchX = getActiveX(motionEvent);
                this.mLastTouchY = getActiveY(motionEvent);
                this.mIsDragging = false;
            } else if (action == 1) {
                if (this.mIsDragging && this.mVelocityTracker != null) {
                    this.mLastTouchX = getActiveX(motionEvent);
                    this.mLastTouchY = getActiveY(motionEvent);
                    this.mVelocityTracker.addMovement(motionEvent);
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
                float activeX = getActiveX(motionEvent);
                float activeY = getActiveY(motionEvent);
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
                        velocityTracker3.addMovement(motionEvent);
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

        @Override // com.hyphenate.easeui.widget.photoview.VersionedGestureDetector.CupcakeDetector
        public float getActiveX(MotionEvent motionEvent) {
            try {
                return motionEvent.getX(this.mActivePointerIndex);
            } catch (Exception unused) {
                return motionEvent.getX();
            }
        }

        @Override // com.hyphenate.easeui.widget.photoview.VersionedGestureDetector.CupcakeDetector
        public float getActiveY(MotionEvent motionEvent) {
            try {
                return motionEvent.getY(this.mActivePointerIndex);
            } catch (Exception unused) {
                return motionEvent.getY();
            }
        }

        @Override // com.hyphenate.easeui.widget.photoview.VersionedGestureDetector.CupcakeDetector, com.hyphenate.easeui.widget.photoview.VersionedGestureDetector
        public boolean onTouchEvent(MotionEvent motionEvent) {
            int action = motionEvent.getAction() & 255;
            if (action != 0) {
                if (action == 1 || action == 3) {
                    this.mActivePointerId = -1;
                } else if (action == 6) {
                    int action2 = (motionEvent.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
                    if (motionEvent.getPointerId(action2) == this.mActivePointerId) {
                        int i2 = action2 != 0 ? 0 : 1;
                        this.mActivePointerId = motionEvent.getPointerId(i2);
                        this.mLastTouchX = motionEvent.getX(i2);
                        this.mLastTouchY = motionEvent.getY(i2);
                    }
                }
            } else {
                this.mActivePointerId = motionEvent.getPointerId(0);
            }
            int i3 = this.mActivePointerId;
            this.mActivePointerIndex = motionEvent.findPointerIndex(i3 != -1 ? i3 : 0);
            return super.onTouchEvent(motionEvent);
        }
    }

    @TargetApi(8)
    public static class FroyoDetector extends EclairDetector {
        private final ScaleGestureDetector mDetector;
        private final ScaleGestureDetector.OnScaleGestureListener mScaleListener;

        public FroyoDetector(Context context) {
            super(context);
            ScaleGestureDetector.OnScaleGestureListener onScaleGestureListener = new ScaleGestureDetector.OnScaleGestureListener() { // from class: com.hyphenate.easeui.widget.photoview.VersionedGestureDetector.FroyoDetector.1
                @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
                public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
                    FroyoDetector.this.mListener.onScale(scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY());
                    return true;
                }

                @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
                public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
                    return true;
                }

                @Override // android.view.ScaleGestureDetector.OnScaleGestureListener
                public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
                }
            };
            this.mScaleListener = onScaleGestureListener;
            this.mDetector = new ScaleGestureDetector(context, onScaleGestureListener);
        }

        @Override // com.hyphenate.easeui.widget.photoview.VersionedGestureDetector.CupcakeDetector, com.hyphenate.easeui.widget.photoview.VersionedGestureDetector
        public boolean isScaling() {
            return this.mDetector.isInProgress();
        }

        @Override // com.hyphenate.easeui.widget.photoview.VersionedGestureDetector.EclairDetector, com.hyphenate.easeui.widget.photoview.VersionedGestureDetector.CupcakeDetector, com.hyphenate.easeui.widget.photoview.VersionedGestureDetector
        public boolean onTouchEvent(MotionEvent motionEvent) {
            this.mDetector.onTouchEvent(motionEvent);
            return super.onTouchEvent(motionEvent);
        }
    }

    public interface OnGestureListener {
        void onDrag(float f2, float f3);

        void onFling(float f2, float f3, float f4, float f5);

        void onScale(float f2, float f3, float f4);
    }

    public static VersionedGestureDetector newInstance(Context context, OnGestureListener onGestureListener) {
        FroyoDetector froyoDetector = new FroyoDetector(context);
        froyoDetector.mListener = onGestureListener;
        return froyoDetector;
    }

    public abstract boolean isScaling();

    public abstract boolean onTouchEvent(MotionEvent motionEvent);
}
