package com.actionbarsherlock.widget.swipeback;

import android.content.Context;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.VelocityTrackerCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.ScrollerCompat;
import java.util.Arrays;

/* loaded from: classes.dex */
public class ViewDragHelper {
    private static final int BASE_SETTLE_DURATION = 256;
    public static final int DIRECTION_ALL = 3;
    public static final int DIRECTION_HORIZONTAL = 1;
    public static final int DIRECTION_VERTICAL = 2;
    public static final int EDGE_ALL = 15;
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    public static final int EDGE_SIZE = 20;
    public static final int EDGE_TOP = 4;
    public static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "ViewDragHelper";
    private static final Interpolator sInterpolator = new Interpolator() { // from class: com.actionbarsherlock.widget.swipeback.ViewDragHelper.1
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f2) {
            float f3 = f2 - 1.0f;
            return (f3 * f3 * f3 * f3 * f3) + 1.0f;
        }
    };
    private final Callback mCallback;
    private View mCapturedView;
    private int mDragState;
    private int[] mEdgeDragsInProgress;
    private int[] mEdgeDragsLocked;
    private int mEdgeSize;
    private int[] mInitialEdgeTouched;
    private float[] mInitialMotionX;
    private float[] mInitialMotionY;
    private float[] mLastMotionX;
    private float[] mLastMotionY;
    private float mMaxVelocity;
    private float mMinVelocity;
    private final ViewGroup mParentView;
    private int mPointersDown;
    private boolean mReleaseInProgress;
    private ScrollerCompat mScroller;
    private int mTouchSlop;
    private int mTrackingEdges;
    private VelocityTracker mVelocityTracker;
    private int mActivePointerId = -1;
    private final Runnable mSetIdleRunnable = new Runnable() { // from class: com.actionbarsherlock.widget.swipeback.ViewDragHelper.2
        @Override // java.lang.Runnable
        public void run() {
            ViewDragHelper.this.setDragState(0);
        }
    };

    public static abstract class Callback {
        public int clampViewPositionHorizontal(View view, int i2, int i3) {
            return 0;
        }

        public int clampViewPositionVertical(View view, int i2, int i3) {
            return 0;
        }

        public int getOrderedChildIndex(int i2) {
            return i2;
        }

        public int getViewHorizontalDragRange(View view) {
            return 0;
        }

        public int getViewVerticalDragRange(View view) {
            return 0;
        }

        public void onEdgeDragStarted(int i2, int i3) {
        }

        public boolean onEdgeLock(int i2) {
            return false;
        }

        public void onEdgeTouched(int i2, int i3) {
        }

        public void onViewCaptured(View view, int i2) {
        }

        public void onViewDragStateChanged(int i2) {
        }

        public void onViewPositionChanged(View view, int i2, int i3, int i4, int i5) {
        }

        public void onViewReleased(View view, float f2, float f3) {
        }

        public abstract boolean tryCaptureView(View view, int i2);
    }

    private ViewDragHelper(Context context, ViewGroup viewGroup, Callback callback) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("Callback may not be null");
        }
        this.mParentView = viewGroup;
        this.mCallback = callback;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mEdgeSize = (int) ((context.getResources().getDisplayMetrics().density * 20.0f) + 0.5f);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mMinVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mScroller = ScrollerCompat.create(context, sInterpolator);
    }

    private boolean checkNewEdgeDrag(float f2, float f3, int i2, int i3) {
        float fAbs = Math.abs(f2);
        float fAbs2 = Math.abs(f3);
        if ((this.mInitialEdgeTouched[i2] & i3) != i3 || (this.mTrackingEdges & i3) == 0 || (this.mEdgeDragsLocked[i2] & i3) == i3 || (this.mEdgeDragsInProgress[i2] & i3) == i3) {
            return false;
        }
        int i4 = this.mTouchSlop;
        if (fAbs <= i4 && fAbs2 <= i4) {
            return false;
        }
        if (fAbs >= fAbs2 * 0.5f || !this.mCallback.onEdgeLock(i3)) {
            return (this.mEdgeDragsInProgress[i2] & i3) == 0 && fAbs > ((float) this.mTouchSlop);
        }
        int[] iArr = this.mEdgeDragsLocked;
        iArr[i2] = iArr[i2] | i3;
        return false;
    }

    private boolean checkTouchSlop(View view, float f2, float f3) {
        if (view == null) {
            return false;
        }
        boolean z2 = this.mCallback.getViewHorizontalDragRange(view) > 0;
        boolean z3 = this.mCallback.getViewVerticalDragRange(view) > 0;
        if (!z2 || !z3) {
            return z2 ? Math.abs(f2) > ((float) this.mTouchSlop) : z3 && Math.abs(f3) > ((float) this.mTouchSlop);
        }
        float f4 = (f2 * f2) + (f3 * f3);
        int i2 = this.mTouchSlop;
        return f4 > ((float) (i2 * i2));
    }

    private int clampMag(int i2, int i3, int i4) {
        int iAbs = Math.abs(i2);
        if (iAbs < i3) {
            return 0;
        }
        return iAbs > i4 ? i2 > 0 ? i4 : -i4 : i2;
    }

    private void clearMotionHistory() {
        float[] fArr = this.mInitialMotionX;
        if (fArr == null) {
            return;
        }
        Arrays.fill(fArr, 0.0f);
        Arrays.fill(this.mInitialMotionY, 0.0f);
        Arrays.fill(this.mLastMotionX, 0.0f);
        Arrays.fill(this.mLastMotionY, 0.0f);
        Arrays.fill(this.mInitialEdgeTouched, 0);
        Arrays.fill(this.mEdgeDragsInProgress, 0);
        Arrays.fill(this.mEdgeDragsLocked, 0);
        this.mPointersDown = 0;
    }

    private int computeAxisDuration(int i2, int i3, int i4) {
        if (i2 == 0) {
            return 0;
        }
        int width = this.mParentView.getWidth();
        float f2 = width / 2;
        float fDistanceInfluenceForSnapDuration = f2 + (distanceInfluenceForSnapDuration(Math.min(1.0f, Math.abs(i2) / width)) * f2);
        int iAbs = Math.abs(i3);
        return Math.min(iAbs > 0 ? Math.round(Math.abs(fDistanceInfluenceForSnapDuration / iAbs) * 1000.0f) * 4 : (int) (((Math.abs(i2) / i4) + 1.0f) * 256.0f), 600);
    }

    private int computeSettleDuration(View view, int i2, int i3, int i4, int i5) {
        float f2;
        float f3;
        float f4;
        float f5;
        int iClampMag = clampMag(i4, (int) this.mMinVelocity, (int) this.mMaxVelocity);
        int iClampMag2 = clampMag(i5, (int) this.mMinVelocity, (int) this.mMaxVelocity);
        int iAbs = Math.abs(i2);
        int iAbs2 = Math.abs(i3);
        int iAbs3 = Math.abs(iClampMag);
        int iAbs4 = Math.abs(iClampMag2);
        int i6 = iAbs3 + iAbs4;
        int i7 = iAbs + iAbs2;
        if (iClampMag != 0) {
            f2 = iAbs3;
            f3 = i6;
        } else {
            f2 = iAbs;
            f3 = i7;
        }
        float f6 = f2 / f3;
        if (iClampMag2 != 0) {
            f4 = iAbs4;
            f5 = i6;
        } else {
            f4 = iAbs2;
            f5 = i7;
        }
        return (int) ((computeAxisDuration(i2, iClampMag, this.mCallback.getViewHorizontalDragRange(view)) * f6) + (computeAxisDuration(i3, iClampMag2, this.mCallback.getViewVerticalDragRange(view)) * (f4 / f5)));
    }

    public static ViewDragHelper create(ViewGroup viewGroup, Callback callback) {
        return new ViewDragHelper(viewGroup.getContext(), viewGroup, callback);
    }

    private void dispatchViewReleased(float f2, float f3) {
        this.mReleaseInProgress = true;
        this.mCallback.onViewReleased(this.mCapturedView, f2, f3);
        this.mReleaseInProgress = false;
        if (this.mDragState == 1) {
            setDragState(0);
        }
    }

    private float distanceInfluenceForSnapDuration(float f2) {
        return (float) Math.sin((float) ((f2 - 0.5f) * 0.4712389167638204d));
    }

    private void dragTo(int i2, int i3, int i4, int i5) {
        int left = this.mCapturedView.getLeft();
        int top2 = this.mCapturedView.getTop();
        if (i4 != 0) {
            i2 = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, i2, i4);
            this.mCapturedView.offsetLeftAndRight(i2 - left);
        }
        int i6 = i2;
        if (i5 != 0) {
            i3 = this.mCallback.clampViewPositionVertical(this.mCapturedView, i3, i5);
            this.mCapturedView.offsetTopAndBottom(i3 - top2);
        }
        int i7 = i3;
        if (i4 == 0 && i5 == 0) {
            return;
        }
        this.mCallback.onViewPositionChanged(this.mCapturedView, i6, i7, i6 - left, i7 - top2);
    }

    private void ensureMotionHistorySizeForId(int i2) {
        float[] fArr = this.mInitialMotionX;
        if (fArr == null || fArr.length <= i2) {
            int i3 = i2 + 1;
            float[] fArr2 = new float[i3];
            float[] fArr3 = new float[i3];
            float[] fArr4 = new float[i3];
            float[] fArr5 = new float[i3];
            int[] iArr = new int[i3];
            int[] iArr2 = new int[i3];
            int[] iArr3 = new int[i3];
            if (fArr != null) {
                System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                float[] fArr6 = this.mInitialMotionY;
                System.arraycopy(fArr6, 0, fArr3, 0, fArr6.length);
                float[] fArr7 = this.mLastMotionX;
                System.arraycopy(fArr7, 0, fArr4, 0, fArr7.length);
                float[] fArr8 = this.mLastMotionY;
                System.arraycopy(fArr8, 0, fArr5, 0, fArr8.length);
                int[] iArr4 = this.mInitialEdgeTouched;
                System.arraycopy(iArr4, 0, iArr, 0, iArr4.length);
                int[] iArr5 = this.mEdgeDragsInProgress;
                System.arraycopy(iArr5, 0, iArr2, 0, iArr5.length);
                int[] iArr6 = this.mEdgeDragsLocked;
                System.arraycopy(iArr6, 0, iArr3, 0, iArr6.length);
            }
            this.mInitialMotionX = fArr2;
            this.mInitialMotionY = fArr3;
            this.mLastMotionX = fArr4;
            this.mLastMotionY = fArr5;
            this.mInitialEdgeTouched = iArr;
            this.mEdgeDragsInProgress = iArr2;
            this.mEdgeDragsLocked = iArr3;
        }
    }

    private boolean forceSettleCapturedViewAt(int i2, int i3, int i4, int i5) {
        int left = this.mCapturedView.getLeft();
        int top2 = this.mCapturedView.getTop();
        int i6 = i2 - left;
        int i7 = i3 - top2;
        if (i6 == 0 && i7 == 0) {
            this.mScroller.abortAnimation();
            setDragState(0);
            return false;
        }
        this.mScroller.startScroll(left, top2, i6, i7, computeSettleDuration(this.mCapturedView, i6, i7, i4, i5));
        setDragState(2);
        return true;
    }

    private int getEdgeTouched(int i2, int i3) {
        int i4 = i2 < this.mParentView.getLeft() + this.mEdgeSize ? 1 : 0;
        if (i3 < this.mParentView.getTop() + this.mEdgeSize) {
            i4 = 4;
        }
        if (i2 > this.mParentView.getRight() - this.mEdgeSize) {
            i4 = 2;
        }
        if (i3 > this.mParentView.getBottom() - this.mEdgeSize) {
            return 8;
        }
        return i4;
    }

    private void releaseViewForPointerUp() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
        dispatchViewReleased(clampMag(VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), clampMag(VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity));
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v4, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r3v3, types: [com.actionbarsherlock.widget.swipeback.ViewDragHelper$Callback] */
    private void reportNewEdgeDrags(float f2, float f3, int i2) {
        boolean zCheckNewEdgeDrag = checkNewEdgeDrag(f2, f3, i2, 1);
        boolean z2 = zCheckNewEdgeDrag;
        if (checkNewEdgeDrag(f3, f2, i2, 4)) {
            z2 = (zCheckNewEdgeDrag ? 1 : 0) | 4;
        }
        boolean z3 = z2;
        if (checkNewEdgeDrag(f2, f3, i2, 2)) {
            z3 = (z2 ? 1 : 0) | 2;
        }
        ?? r02 = z3;
        if (checkNewEdgeDrag(f3, f2, i2, 8)) {
            r02 = (z3 ? 1 : 0) | 8;
        }
        if (r02 != 0) {
            int[] iArr = this.mEdgeDragsInProgress;
            iArr[i2] = iArr[i2] | r02;
            this.mCallback.onEdgeDragStarted(r02, i2);
        }
    }

    private void saveInitialMotion(float f2, float f3, int i2) {
        ensureMotionHistorySizeForId(i2);
        float[] fArr = this.mInitialMotionX;
        this.mLastMotionX[i2] = f2;
        fArr[i2] = f2;
        float[] fArr2 = this.mInitialMotionY;
        this.mLastMotionY[i2] = f3;
        fArr2[i2] = f3;
        this.mInitialEdgeTouched[i2] = getEdgeTouched((int) f2, (int) f3);
        this.mPointersDown |= 1 << i2;
    }

    private void saveLastMotion(MotionEvent motionEvent) {
        int pointerCount = MotionEventCompat.getPointerCount(motionEvent);
        for (int i2 = 0; i2 < pointerCount; i2++) {
            int pointerId = MotionEventCompat.getPointerId(motionEvent, i2);
            float x2 = MotionEventCompat.getX(motionEvent, i2);
            float y2 = MotionEventCompat.getY(motionEvent, i2);
            this.mLastMotionX[pointerId] = x2;
            this.mLastMotionY[pointerId] = y2;
        }
    }

    public void abort() {
        cancel();
        if (this.mDragState == 2) {
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            this.mScroller.abortAnimation();
            int currX2 = this.mScroller.getCurrX();
            int currY2 = this.mScroller.getCurrY();
            this.mCallback.onViewPositionChanged(this.mCapturedView, currX2, currY2, currX2 - currX, currY2 - currY);
        }
        setDragState(0);
    }

    public boolean canScroll(View view, boolean z2, int i2, int i3, int i4, int i5) {
        int i6;
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int scrollX = view.getScrollX();
            int scrollY = view.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i7 = i4 + scrollX;
                if (i7 >= childAt.getLeft() && i7 < childAt.getRight() && (i6 = i5 + scrollY) >= childAt.getTop() && i6 < childAt.getBottom() && canScroll(childAt, true, i2, i3, i7 - childAt.getLeft(), i6 - childAt.getTop())) {
                    return true;
                }
            }
        }
        return z2 && (ViewCompat.canScrollHorizontally(view, -i2) || ViewCompat.canScrollVertically(view, -i3));
    }

    public void cancel() {
        this.mActivePointerId = -1;
        clearMotionHistory();
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void captureChildView(View view, int i2) {
        if (view.getParent() == this.mParentView) {
            this.mCapturedView = view;
            this.mActivePointerId = i2;
            this.mCallback.onViewCaptured(view, i2);
            setDragState(1);
            return;
        }
        throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + this.mParentView + ")");
    }

    public boolean continueSettling(boolean z2) {
        if (this.mDragState == 2) {
            boolean zComputeScrollOffset = this.mScroller.computeScrollOffset();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            int left = currX - this.mCapturedView.getLeft();
            int top2 = currY - this.mCapturedView.getTop();
            if (left != 0) {
                this.mCapturedView.offsetLeftAndRight(left);
            }
            if (top2 != 0) {
                this.mCapturedView.offsetTopAndBottom(top2);
            }
            if (left != 0 || top2 != 0) {
                this.mCallback.onViewPositionChanged(this.mCapturedView, currX, currY, left, top2);
            }
            if (zComputeScrollOffset && currX == this.mScroller.getFinalX() && currY == this.mScroller.getFinalY()) {
                this.mScroller.abortAnimation();
                zComputeScrollOffset = this.mScroller.isFinished();
            }
            if (!zComputeScrollOffset) {
                if (z2) {
                    this.mParentView.post(this.mSetIdleRunnable);
                } else {
                    setDragState(0);
                }
            }
        }
        return this.mDragState == 2;
    }

    public View findTopChildUnder(int i2, int i3) {
        for (int childCount = this.mParentView.getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(childCount));
            if (i2 >= childAt.getLeft() && i2 < childAt.getRight() && i3 >= childAt.getTop() && i3 < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }

    public void flingCapturedView(int i2, int i3, int i4, int i5) {
        if (!this.mReleaseInProgress) {
            throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
        }
        this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int) VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), (int) VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId), i2, i4, i3, i5);
        setDragState(2);
    }

    public int getActivePointerId() {
        return this.mActivePointerId;
    }

    public View getCapturedView() {
        return this.mCapturedView;
    }

    public int getEdgeSize() {
        return this.mEdgeSize;
    }

    public float getMinVelocity() {
        return this.mMinVelocity;
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public int getViewDragState() {
        return this.mDragState;
    }

    public boolean isCapturedViewUnder(int i2, int i3) {
        return isViewUnder(this.mCapturedView, i2, i3);
    }

    public boolean isEdgeTouched(int i2) {
        int length = this.mInitialEdgeTouched.length;
        for (int i3 = 0; i3 < length; i3++) {
            if (isEdgeTouched(i2, i3)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPointerDown(int i2) {
        return ((1 << i2) & this.mPointersDown) != 0;
    }

    public boolean isViewUnder(View view, int i2, int i3) {
        return view != null && i2 >= view.getLeft() && i2 < view.getRight() && i3 >= view.getTop() && i3 < view.getBottom();
    }

    public void processTouchEvent(MotionEvent motionEvent) {
        int i2;
        int actionMasked = MotionEventCompat.getActionMasked(motionEvent);
        int actionIndex = MotionEventCompat.getActionIndex(motionEvent);
        if (actionMasked == 0) {
            cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int i3 = 0;
        if (actionMasked == 0) {
            float x2 = motionEvent.getX();
            float y2 = motionEvent.getY();
            int pointerId = MotionEventCompat.getPointerId(motionEvent, 0);
            View viewFindTopChildUnder = findTopChildUnder((int) x2, (int) y2);
            saveInitialMotion(x2, y2, pointerId);
            tryCaptureViewForDrag(viewFindTopChildUnder, pointerId);
            int i4 = this.mInitialEdgeTouched[pointerId];
            int i5 = this.mTrackingEdges;
            if ((i4 & i5) != 0) {
                this.mCallback.onEdgeTouched(i4 & i5, pointerId);
                return;
            }
            return;
        }
        if (actionMasked == 1) {
            if (this.mDragState == 1) {
                releaseViewForPointerUp();
            }
            cancel();
            return;
        }
        if (actionMasked == 2) {
            if (this.mDragState == 1) {
                int iFindPointerIndex = MotionEventCompat.findPointerIndex(motionEvent, this.mActivePointerId);
                float x3 = MotionEventCompat.getX(motionEvent, iFindPointerIndex);
                float y3 = MotionEventCompat.getY(motionEvent, iFindPointerIndex);
                float[] fArr = this.mLastMotionX;
                int i6 = this.mActivePointerId;
                int i7 = (int) (x3 - fArr[i6]);
                int i8 = (int) (y3 - this.mLastMotionY[i6]);
                dragTo(this.mCapturedView.getLeft() + i7, this.mCapturedView.getTop() + i8, i7, i8);
                saveLastMotion(motionEvent);
                return;
            }
            int pointerCount = MotionEventCompat.getPointerCount(motionEvent);
            while (i3 < pointerCount) {
                int pointerId2 = MotionEventCompat.getPointerId(motionEvent, i3);
                float x4 = MotionEventCompat.getX(motionEvent, i3);
                float y4 = MotionEventCompat.getY(motionEvent, i3);
                float f2 = x4 - this.mInitialMotionX[pointerId2];
                float f3 = y4 - this.mInitialMotionY[pointerId2];
                reportNewEdgeDrags(f2, f3, pointerId2);
                if (this.mDragState != 1) {
                    View viewFindTopChildUnder2 = findTopChildUnder((int) x4, (int) y4);
                    if (checkTouchSlop(viewFindTopChildUnder2, f2, f3) && tryCaptureViewForDrag(viewFindTopChildUnder2, pointerId2)) {
                        break;
                    } else {
                        i3++;
                    }
                } else {
                    break;
                }
            }
            saveLastMotion(motionEvent);
            return;
        }
        if (actionMasked == 3) {
            if (this.mDragState == 1) {
                dispatchViewReleased(0.0f, 0.0f);
            }
            cancel();
            return;
        }
        if (actionMasked == 5) {
            int pointerId3 = MotionEventCompat.getPointerId(motionEvent, actionIndex);
            float x5 = MotionEventCompat.getX(motionEvent, actionIndex);
            float y5 = MotionEventCompat.getY(motionEvent, actionIndex);
            saveInitialMotion(x5, y5, pointerId3);
            if (this.mDragState != 0) {
                if (isCapturedViewUnder((int) x5, (int) y5)) {
                    tryCaptureViewForDrag(this.mCapturedView, pointerId3);
                    return;
                }
                return;
            } else {
                tryCaptureViewForDrag(findTopChildUnder((int) x5, (int) y5), pointerId3);
                int i9 = this.mInitialEdgeTouched[pointerId3];
                int i10 = this.mTrackingEdges;
                if ((i9 & i10) != 0) {
                    this.mCallback.onEdgeTouched(i9 & i10, pointerId3);
                    return;
                }
                return;
            }
        }
        if (actionMasked != 6) {
            return;
        }
        int pointerId4 = MotionEventCompat.getPointerId(motionEvent, actionIndex);
        if (this.mDragState == 1 && pointerId4 == this.mActivePointerId) {
            int pointerCount2 = MotionEventCompat.getPointerCount(motionEvent);
            while (true) {
                if (i3 >= pointerCount2) {
                    i2 = -1;
                    break;
                }
                int pointerId5 = MotionEventCompat.getPointerId(motionEvent, i3);
                if (pointerId5 != this.mActivePointerId) {
                    View viewFindTopChildUnder3 = findTopChildUnder((int) MotionEventCompat.getX(motionEvent, i3), (int) MotionEventCompat.getY(motionEvent, i3));
                    View view = this.mCapturedView;
                    if (viewFindTopChildUnder3 == view && tryCaptureViewForDrag(view, pointerId5)) {
                        i2 = this.mActivePointerId;
                        break;
                    }
                }
                i3++;
            }
            if (i2 == -1) {
                releaseViewForPointerUp();
            }
        }
        clearMotionHistory(pointerId4);
    }

    public void setDragState(int i2) {
        if (this.mDragState != i2) {
            this.mDragState = i2;
            this.mCallback.onViewDragStateChanged(i2);
            if (i2 == 0) {
                this.mCapturedView = null;
            }
        }
    }

    public void setEdgeSize(int i2) {
        this.mEdgeSize = i2;
    }

    public void setEdgeTrackingEnabled(int i2) {
        this.mTrackingEdges = i2;
    }

    public void setMaxVelocity(float f2) {
        this.mMaxVelocity = f2;
    }

    public void setMinVelocity(float f2) {
        this.mMinVelocity = f2;
    }

    public void setSensitivity(Context context, float f2) {
        this.mTouchSlop = (int) (ViewConfiguration.get(context).getScaledTouchSlop() * (1.0f / Math.max(0.0f, Math.min(1.0f, f2))));
    }

    public boolean settleCapturedViewAt(int i2, int i3) {
        if (this.mReleaseInProgress) {
            return forceSettleCapturedViewAt(i2, i3, (int) VelocityTrackerCompat.getXVelocity(this.mVelocityTracker, this.mActivePointerId), (int) VelocityTrackerCompat.getYVelocity(this.mVelocityTracker, this.mActivePointerId));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x00b2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean shouldInterceptTouchEvent(android.view.MotionEvent r11) {
        /*
            Method dump skipped, instructions count: 236
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.actionbarsherlock.widget.swipeback.ViewDragHelper.shouldInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public boolean smoothSlideViewTo(View view, int i2, int i3) {
        this.mCapturedView = view;
        this.mActivePointerId = -1;
        return forceSettleCapturedViewAt(i2, i3, 0, 0);
    }

    public boolean tryCaptureViewForDrag(View view, int i2) {
        if (view == this.mCapturedView && this.mActivePointerId == i2) {
            return true;
        }
        if (view == null || !this.mCallback.tryCaptureView(view, i2)) {
            return false;
        }
        this.mActivePointerId = i2;
        captureChildView(view, i2);
        return true;
    }

    private float clampMag(float f2, float f3, float f4) {
        float fAbs = Math.abs(f2);
        if (fAbs < f3) {
            return 0.0f;
        }
        return fAbs > f4 ? f2 > 0.0f ? f4 : -f4 : f2;
    }

    public static ViewDragHelper create(ViewGroup viewGroup, float f2, Callback callback) {
        ViewDragHelper viewDragHelperCreate = create(viewGroup, callback);
        viewDragHelperCreate.mTouchSlop = (int) (viewDragHelperCreate.mTouchSlop * (1.0f / f2));
        return viewDragHelperCreate;
    }

    public boolean isEdgeTouched(int i2, int i3) {
        return isPointerDown(i3) && (i2 & this.mInitialEdgeTouched[i3]) != 0;
    }

    public boolean checkTouchSlop(int i2) {
        int length = this.mInitialMotionX.length;
        for (int i3 = 0; i3 < length; i3++) {
            if (checkTouchSlop(i2, i3)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkTouchSlop(int i2, int i3) {
        if (!isPointerDown(i3)) {
            return false;
        }
        boolean z2 = (i2 & 1) == 1;
        boolean z3 = (i2 & 2) == 2;
        float f2 = this.mLastMotionX[i3] - this.mInitialMotionX[i3];
        float f3 = this.mLastMotionY[i3] - this.mInitialMotionY[i3];
        if (!z2 || !z3) {
            return z2 ? Math.abs(f2) > ((float) this.mTouchSlop) : z3 && Math.abs(f3) > ((float) this.mTouchSlop);
        }
        float f4 = (f2 * f2) + (f3 * f3);
        int i4 = this.mTouchSlop;
        return f4 > ((float) (i4 * i4));
    }

    private void clearMotionHistory(int i2) {
        float[] fArr = this.mInitialMotionX;
        if (fArr == null) {
            return;
        }
        fArr[i2] = 0.0f;
        this.mInitialMotionY[i2] = 0.0f;
        this.mLastMotionX[i2] = 0.0f;
        this.mLastMotionY[i2] = 0.0f;
        this.mInitialEdgeTouched[i2] = 0;
        this.mEdgeDragsInProgress[i2] = 0;
        this.mEdgeDragsLocked[i2] = 0;
        this.mPointersDown = (~(1 << i2)) & this.mPointersDown;
    }
}
