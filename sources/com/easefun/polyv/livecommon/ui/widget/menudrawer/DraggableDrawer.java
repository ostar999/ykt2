package com.easefun.polyv.livecommon.ui.widget.menudrawer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/* loaded from: classes3.dex */
public abstract class DraggableDrawer extends PLVMenuDrawer {
    private static final int CLOSE_ENOUGH = 3;
    private static final long DEFAULT_PEEK_DELAY = 10000;
    private static final long DEFAULT_PEEK_START_DELAY = 5000;
    protected static final int INVALID_POINTER = -1;
    protected static final int MAX_MENU_OVERLAY_ALPHA = 185;
    protected static final int PEEK_DURATION = 5000;
    private static final Interpolator PEEK_INTERPOLATOR = new PeekInterpolator();
    private static final String STATE_MENU_VISIBLE = "net.simonvt.menudrawer.MenuDrawer.menuVisible";
    protected int mActivePointerId;
    protected int mCloseEnough;
    protected int mDragAreaMenuBottom;
    private final Runnable mDragRunnable;
    protected float mInitialMotionX;
    protected float mInitialMotionY;
    protected boolean mIsDragging;
    protected boolean mIsPeeking;
    protected float mLastMotionX;
    protected float mLastMotionY;
    protected boolean mLayerTypeHardware;
    protected int mMaxVelocity;
    protected boolean mOffsetMenu;
    protected long mPeekDelay;
    protected final Runnable mPeekRunnable;
    protected Scroller mPeekScroller;
    private Runnable mPeekStartRunnable;
    private Scroller mScroller;
    protected int mTouchSlop;
    protected VelocityTracker mVelocityTracker;

    /* renamed from: com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer$5, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass5 {
        static final /* synthetic */ int[] $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position;

        static {
            int[] iArr = new int[Position.values().length];
            $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position = iArr;
            try {
                iArr[Position.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.TOP.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[Position.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public DraggableDrawer(Activity activity, int dragMode) {
        super(activity, dragMode);
        this.mPeekRunnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer.1
            @Override // java.lang.Runnable
            public void run() {
                DraggableDrawer.this.peekDrawerInvalidate();
            }
        };
        this.mDragRunnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer.2
            @Override // java.lang.Runnable
            public void run() {
                DraggableDrawer.this.postAnimationInvalidate();
            }
        };
        this.mActivePointerId = -1;
        this.mLastMotionX = -1.0f;
        this.mLastMotionY = -1.0f;
        this.mOffsetMenu = true;
    }

    private void completeAnimation() {
        this.mScroller.abortAnimation();
        int finalX = this.mScroller.getFinalX();
        setOffsetPixels(finalX);
        setDrawerState(finalX == 0 ? 0 : 8);
        stopLayerTranslation();
    }

    private void completePeek() {
        this.mPeekScroller.abortAnimation();
        setOffsetPixels(0.0f);
        setDrawerState(0);
        stopLayerTranslation();
        this.mIsPeeking = false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void peekDrawerInvalidate() {
        if (this.mPeekScroller.computeScrollOffset()) {
            int i2 = (int) this.mOffsetPixels;
            int currX = this.mPeekScroller.getCurrX();
            if (currX != i2) {
                setOffsetPixels(currX);
            }
            if (!this.mPeekScroller.isFinished()) {
                postOnAnimation(this.mPeekRunnable);
                return;
            } else if (this.mPeekDelay > 0) {
                Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer.4
                    @Override // java.lang.Runnable
                    public void run() {
                        DraggableDrawer.this.startPeek();
                    }
                };
                this.mPeekStartRunnable = runnable;
                postDelayed(runnable, this.mPeekDelay);
            }
        }
        completePeek();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void postAnimationInvalidate() {
        if (this.mScroller.computeScrollOffset()) {
            int i2 = (int) this.mOffsetPixels;
            int currX = this.mScroller.getCurrX();
            if (currX != i2) {
                setOffsetPixels(currX);
            }
            if (currX != this.mScroller.getFinalX()) {
                postOnAnimation(this.mDragRunnable);
                return;
            }
        }
        completeAnimation();
    }

    private int supportGetTranslationX(View v2) {
        return (int) v2.getTranslationX();
    }

    private int supportGetTranslationY(View v2) {
        return (int) v2.getTranslationY();
    }

    public void animateOffsetTo(int position, int velocity, boolean animate) {
        endDrag();
        endPeek();
        int i2 = position - ((int) this.mOffsetPixels);
        if (i2 != 0 && animate) {
            int iAbs = Math.abs(velocity);
            animateOffsetTo(position, Math.min(iAbs > 0 ? Math.round(Math.abs(i2 / iAbs) * 1000.0f) * 4 : (int) (Math.abs(i2 / this.mMenuSize) * 600.0f), this.mMaxAnimationDuration));
        } else {
            setOffsetPixels(position);
            setDrawerState(position == 0 ? 0 : 8);
            stopLayerTranslation();
        }
    }

    public boolean canChildScrollHorizontally(View v2, boolean checkV, int dx, int x2, int y2) {
        if (v2 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v2;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int left = childAt.getLeft() + supportGetTranslationX(childAt);
                int right = childAt.getRight() + supportGetTranslationX(childAt);
                int top2 = childAt.getTop() + supportGetTranslationY(childAt);
                int bottom = childAt.getBottom() + supportGetTranslationY(childAt);
                if (x2 >= left && x2 < right && y2 >= top2 && y2 < bottom && canChildScrollHorizontally(childAt, true, dx, x2 - left, y2 - top2)) {
                    return true;
                }
            }
        }
        return checkV && this.mOnInterceptMoveEventListener.isViewDraggable(v2, dx, x2, y2);
    }

    public boolean canChildScrollVertically(View v2, boolean checkV, int dx, int x2, int y2) {
        if (v2 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v2;
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int left = childAt.getLeft() + supportGetTranslationX(childAt);
                int right = childAt.getRight() + supportGetTranslationX(childAt);
                int top2 = childAt.getTop() + supportGetTranslationY(childAt);
                int bottom = childAt.getBottom() + supportGetTranslationY(childAt);
                if (bottom < y2 && getPosition() == Position.BOTTOM) {
                    bottom = y2 + 1;
                }
                if (x2 >= left && x2 < right && y2 >= top2 && y2 < bottom && canChildScrollVertically(childAt, true, dx, x2 - left, y2 - top2)) {
                    return true;
                }
            }
        }
        return checkV && this.mOnInterceptMoveEventListener.isViewDraggable(v2, dx, x2, y2);
    }

    public boolean canChildrenScroll(int dx, int dy, int x2, int y2) {
        int i2 = AnonymousClass5.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$menudrawer$Position[getPosition().ordinal()];
        if (i2 == 1 || i2 == 2) {
            if (this.mMenuVisible) {
                BuildLayerFrameLayout buildLayerFrameLayout = this.mMenuContainer;
                return canChildScrollHorizontally(buildLayerFrameLayout, false, dx, x2 - ViewHelper.getLeft(buildLayerFrameLayout), y2 - ViewHelper.getTop(this.mContentContainer));
            }
            BuildLayerFrameLayout buildLayerFrameLayout2 = this.mContentContainer;
            return canChildScrollHorizontally(buildLayerFrameLayout2, false, dx, x2 - ViewHelper.getLeft(buildLayerFrameLayout2), y2 - ViewHelper.getTop(this.mContentContainer));
        }
        if (i2 != 3 && i2 != 4) {
            return false;
        }
        if (this.mMenuVisible) {
            BuildLayerFrameLayout buildLayerFrameLayout3 = this.mMenuContainer;
            return canChildScrollVertically(buildLayerFrameLayout3, false, dy, x2 - ViewHelper.getLeft(buildLayerFrameLayout3), y2 - ViewHelper.getTop(this.mContentContainer));
        }
        BuildLayerFrameLayout buildLayerFrameLayout4 = this.mContentContainer;
        return canChildScrollVertically(buildLayerFrameLayout4, false, dy, x2 - ViewHelper.getLeft(buildLayerFrameLayout4), y2 - ViewHelper.getTop(this.mContentContainer));
    }

    public void cancelContentTouch() {
        long jUptimeMillis = SystemClock.uptimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0);
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            getChildAt(i2).dispatchTouchEvent(motionEventObtain);
        }
        this.mContentContainer.dispatchTouchEvent(motionEventObtain);
        motionEventObtain.recycle();
    }

    public void endDrag() {
        this.mIsDragging = false;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public void endPeek() {
        removeCallbacks(this.mPeekStartRunnable);
        removeCallbacks(this.mPeekRunnable);
        stopLayerTranslation();
        this.mIsPeeking = false;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public boolean getOffsetMenuEnabled() {
        return this.mOffsetMenu;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public int getTouchBezelSize() {
        return this.mTouchBezelSize;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public int getTouchMode() {
        return this.mTouchMode;
    }

    public float getXVelocity(VelocityTracker velocityTracker) {
        return velocityTracker.getXVelocity(this.mActivePointerId);
    }

    public float getYVelocity(VelocityTracker velocityTracker) {
        return velocityTracker.getYVelocity(this.mActivePointerId);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void initDrawer(Context context, AttributeSet attrs, int defStyle) {
        super.initDrawer(context, attrs, defStyle);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mScroller = new Scroller(context, PLVMenuDrawer.SMOOTH_INTERPOLATOR);
        this.mPeekScroller = new Scroller(context, PEEK_INTERPOLATOR);
        this.mCloseEnough = dpToPx(3);
    }

    public abstract void initPeekScroller();

    public boolean isCloseEnough() {
        return Math.abs(this.mOffsetPixels) <= ((float) this.mCloseEnough);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public boolean isMenuVisible() {
        return this.mMenuVisible;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void peekDrawer() {
        peekDrawer(5000L, 10000L);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void restoreState(Parcelable in) {
        super.restoreState(in);
        boolean z2 = ((Bundle) in).getBoolean(STATE_MENU_VISIBLE);
        if (z2) {
            openMenu(false);
        } else {
            setOffsetPixels(0.0f);
        }
        this.mDrawerState = z2 ? 8 : 0;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void saveState(Bundle state) {
        int i2 = this.mDrawerState;
        state.putBoolean(STATE_MENU_VISIBLE, i2 == 8 || i2 == 4);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setDragAreaMenuBottom(int bottom) {
        this.mDragAreaMenuBottom = bottom;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setHardwareLayerEnabled(boolean enabled) {
        if (enabled != this.mHardwareLayersEnabled) {
            this.mHardwareLayersEnabled = enabled;
            this.mMenuContainer.setHardwareLayersEnabled(enabled);
            this.mContentContainer.setHardwareLayersEnabled(enabled);
            stopLayerTranslation();
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setMenuSize(final int size) {
        this.mMenuSize = size;
        int i2 = this.mDrawerState;
        if (i2 == 8 || i2 == 4) {
            setOffsetPixels(size);
        }
        requestLayout();
        invalidate();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setOffsetMenuEnabled(boolean offsetMenu) {
        if (offsetMenu != this.mOffsetMenu) {
            this.mOffsetMenu = offsetMenu;
            requestLayout();
            invalidate();
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setTouchBezelSize(int size) {
        this.mTouchBezelSize = size;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void setTouchMode(int mode) {
        if (this.mTouchMode != mode) {
            this.mTouchMode = mode;
            updateTouchAreaSize();
        }
    }

    public void startLayerTranslation() {
        if (PLVMenuDrawer.USE_TRANSLATIONS && this.mHardwareLayersEnabled && !this.mLayerTypeHardware) {
            this.mLayerTypeHardware = true;
            this.mContentContainer.setLayerType(2, null);
            this.mMenuContainer.setLayerType(2, null);
        }
    }

    public void startPeek() {
        this.mIsPeeking = true;
        initPeekScroller();
        startLayerTranslation();
        peekDrawerInvalidate();
    }

    public void stopAnimation() {
        removeCallbacks(this.mDragRunnable);
        this.mScroller.abortAnimation();
        stopLayerTranslation();
    }

    public void stopLayerTranslation() {
        if (this.mLayerTypeHardware) {
            this.mLayerTypeHardware = false;
            this.mContentContainer.setLayerType(0, null);
            this.mMenuContainer.setLayerType(0, null);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void toggleMenu(boolean animate) {
        int i2 = this.mDrawerState;
        if (i2 == 8 || i2 == 4) {
            closeMenu(animate);
        } else if (i2 == 0 || i2 == 1) {
            openMenu(animate);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void peekDrawer(long delay) {
        peekDrawer(5000L, delay);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.menudrawer.PLVMenuDrawer
    public void peekDrawer(final long startDelay, final long delay) {
        if (startDelay < 0) {
            throw new IllegalArgumentException("startDelay must be zero or larger.");
        }
        if (delay >= 0) {
            removeCallbacks(this.mPeekRunnable);
            removeCallbacks(this.mPeekStartRunnable);
            this.mPeekDelay = delay;
            Runnable runnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer.3
                @Override // java.lang.Runnable
                public void run() {
                    DraggableDrawer.this.startPeek();
                }
            };
            this.mPeekStartRunnable = runnable;
            postDelayed(runnable, startDelay);
            return;
        }
        throw new IllegalArgumentException("delay must be zero or larger");
    }

    public DraggableDrawer(Context context) {
        super(context);
        this.mPeekRunnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer.1
            @Override // java.lang.Runnable
            public void run() {
                DraggableDrawer.this.peekDrawerInvalidate();
            }
        };
        this.mDragRunnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer.2
            @Override // java.lang.Runnable
            public void run() {
                DraggableDrawer.this.postAnimationInvalidate();
            }
        };
        this.mActivePointerId = -1;
        this.mLastMotionX = -1.0f;
        this.mLastMotionY = -1.0f;
        this.mOffsetMenu = true;
    }

    public void animateOffsetTo(int position, int duration) {
        int i2 = (int) this.mOffsetPixels;
        int i3 = position - i2;
        if (i3 > 0) {
            setDrawerState(getPosition() == Position.BOTTOM ? 1 : 4);
            this.mScroller.startScroll(i2, 0, i3, 0, duration);
        } else {
            setDrawerState(getPosition() != Position.BOTTOM ? 1 : 4);
            this.mScroller.startScroll(i2, 0, i3, 0, duration);
        }
        startLayerTranslation();
        postAnimationInvalidate();
    }

    public DraggableDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPeekRunnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer.1
            @Override // java.lang.Runnable
            public void run() {
                DraggableDrawer.this.peekDrawerInvalidate();
            }
        };
        this.mDragRunnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer.2
            @Override // java.lang.Runnable
            public void run() {
                DraggableDrawer.this.postAnimationInvalidate();
            }
        };
        this.mActivePointerId = -1;
        this.mLastMotionX = -1.0f;
        this.mLastMotionY = -1.0f;
        this.mOffsetMenu = true;
    }

    public DraggableDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mPeekRunnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer.1
            @Override // java.lang.Runnable
            public void run() {
                DraggableDrawer.this.peekDrawerInvalidate();
            }
        };
        this.mDragRunnable = new Runnable() { // from class: com.easefun.polyv.livecommon.ui.widget.menudrawer.DraggableDrawer.2
            @Override // java.lang.Runnable
            public void run() {
                DraggableDrawer.this.postAnimationInvalidate();
            }
        };
        this.mActivePointerId = -1;
        this.mLastMotionX = -1.0f;
        this.mLastMotionY = -1.0f;
        this.mOffsetMenu = true;
    }
}
