package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.AbsListView;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.core.view.MotionEventCompat;

/* loaded from: classes6.dex */
public class PullToZoomListView extends ExpandableListView implements AbsListView.OnScrollListener {
    private static float DEFAULT_MIN_SCALE = 1.0f;
    private static final int INVALID_VALUE = -1;
    private static final String TAG = "PullToZoomListView";
    private static final Interpolator sInterpolator = new Interpolator() { // from class: com.psychiatrygarden.widget.PullToZoomListView.1
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float t2) {
            float f2 = t2 - PullToZoomListView.DEFAULT_MIN_SCALE;
            return (f2 * f2 * f2 * f2 * f2) + PullToZoomListView.DEFAULT_MIN_SCALE;
        }
    };
    int mActivePointerId;
    private FrameLayout mHeaderContainer;
    private int mHeaderHeight;
    private ImageView mHeaderImage;
    float mLastMotionY;
    float mLastScale;
    float mMaxScale;
    private AbsListView.OnScrollListener mOnScrollListener;
    private ScalingRunnable mScalingRunnable;
    private int mScreenHeight;
    private boolean mScrollable;
    private ImageView mShadow;
    private boolean mShowHeaderImage;
    private boolean mZoomable;

    public class ScalingRunnable implements Runnable {
        long mDuration;
        boolean mIsFinished = true;
        float mScale;
        long mStartTime;

        public ScalingRunnable() {
        }

        public void abortAnimation() {
            this.mIsFinished = true;
        }

        public boolean isFinished() {
            return this.mIsFinished;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (this.mIsFinished) {
                return;
            }
            float f2 = this.mScale;
            if (f2 > 1.0d) {
                float interpolation = f2 - ((f2 - PullToZoomListView.DEFAULT_MIN_SCALE) * PullToZoomListView.sInterpolator.getInterpolation((SystemClock.currentThreadTimeMillis() - this.mStartTime) / this.mDuration));
                ViewGroup.LayoutParams layoutParams = PullToZoomListView.this.mHeaderContainer.getLayoutParams();
                if (interpolation <= PullToZoomListView.DEFAULT_MIN_SCALE) {
                    this.mIsFinished = true;
                    layoutParams.height = PullToZoomListView.this.mHeaderHeight;
                } else {
                    layoutParams.height = (int) (PullToZoomListView.this.mHeaderHeight * interpolation);
                }
                PullToZoomListView.this.mHeaderContainer.setLayoutParams(layoutParams);
                PullToZoomListView.this.post(this);
            }
        }

        public void startAnimation(long duration) {
            this.mStartTime = SystemClock.currentThreadTimeMillis();
            this.mDuration = duration;
            this.mScale = PullToZoomListView.this.mHeaderContainer.getBottom() / PullToZoomListView.this.mHeaderHeight;
            this.mIsFinished = false;
            PullToZoomListView.this.post(this);
        }
    }

    public PullToZoomListView(Context context) {
        super(context);
        this.mActivePointerId = -1;
        this.mLastMotionY = -1.0f;
        this.mLastScale = -1.0f;
        this.mMaxScale = -1.0f;
        this.mScalingRunnable = new ScalingRunnable();
        this.mScrollable = true;
        this.mShowHeaderImage = true;
        this.mZoomable = true;
    }

    private void endScaling() {
        if (this.mHeaderContainer.getBottom() >= this.mHeaderHeight) {
            this.mScalingRunnable.startAnimation(180L);
        }
    }

    private void init(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        this.mScreenHeight = displayMetrics.heightPixels;
        this.mHeaderContainer = new FrameLayout(context);
        this.mHeaderImage = new ImageView(context);
        int i2 = displayMetrics.widthPixels;
        setHeaderViewSize(i2, (int) ((i2 / 16.0f) * 9.0f));
        this.mShadow = new ImageView(context);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -2);
        layoutParams.gravity = 80;
        this.mShadow.setLayoutParams(layoutParams);
        this.mHeaderContainer.addView(this.mHeaderImage);
        this.mHeaderContainer.addView(this.mShadow);
        addHeaderView(this.mHeaderContainer);
        super.setOnScrollListener(this);
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int action = (ev.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (ev.getPointerId(action) == this.mActivePointerId) {
            int i2 = action == 0 ? 1 : 0;
            this.mLastMotionY = ev.getY(i2);
            this.mActivePointerId = ev.getPointerId(i2);
        }
    }

    private void reset() {
        this.mActivePointerId = -1;
        this.mLastMotionY = -1.0f;
        this.mMaxScale = -1.0f;
        this.mLastScale = -1.0f;
    }

    public ImageView getHeaderView() {
        return this.mHeaderImage;
    }

    public void hideHeaderImage() {
        this.mShowHeaderImage = false;
        this.mZoomable = false;
        this.mScrollable = false;
        removeHeaderView(this.mHeaderContainer);
    }

    public boolean isScrollable() {
        return this.mScrollable;
    }

    public boolean isZoomable() {
        return this.mZoomable;
    }

    @Override // android.widget.ListView, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        init(getContext());
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!this.mZoomable) {
            return super.onInterceptTouchEvent(ev);
        }
        int action = ev.getAction();
        if (action == 0) {
            this.mActivePointerId = ev.getPointerId(0);
            this.mMaxScale = this.mScreenHeight / this.mHeaderHeight;
        } else if (action == 1 || action == 3) {
            reset();
        } else if (action == 5) {
            this.mActivePointerId = ev.getPointerId(ev.getActionIndex());
        } else if (action == 6) {
            onSecondaryPointerUp(ev);
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override // android.widget.AbsListView, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        super.onLayout(changed, l2, t2, r2, b3);
        if (this.mHeaderHeight == 0) {
            this.mHeaderHeight = this.mHeaderContainer.getHeight();
        }
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (this.mScrollable) {
            float bottom = this.mHeaderHeight - this.mHeaderContainer.getBottom();
            if (bottom > 0.0f && bottom < this.mHeaderHeight) {
                this.mHeaderImage.scrollTo(0, -((int) (bottom * 0.65d)));
            } else if (this.mHeaderImage.getScrollY() != 0) {
                this.mHeaderImage.scrollTo(0, 0);
            }
        }
        AbsListView.OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    @Override // android.widget.AbsListView.OnScrollListener
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        AbsListView.OnScrollListener onScrollListener = this.mOnScrollListener;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChanged(view, scrollState);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00cd  */
    @Override // android.widget.AbsListView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r10) {
        /*
            Method dump skipped, instructions count: 261
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.PullToZoomListView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public void setHeaderViewSize(int width, int height) {
        if (this.mShowHeaderImage) {
            ViewGroup.LayoutParams layoutParams = this.mHeaderContainer.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = new AbsListView.LayoutParams(width, height);
            }
            layoutParams.width = width;
            layoutParams.height = height;
            this.mHeaderContainer.setLayoutParams(layoutParams);
            this.mHeaderHeight = height;
        }
    }

    @Override // android.widget.AbsListView
    public void setOnScrollListener(AbsListView.OnScrollListener l2) {
        this.mOnScrollListener = l2;
    }

    public void setScrollable(boolean scrollable) {
        if (this.mShowHeaderImage) {
            this.mScrollable = scrollable;
        }
    }

    public void setShadow(int resId) {
        if (this.mShowHeaderImage) {
            this.mShadow.setBackgroundResource(resId);
        }
    }

    public void setZoomable(boolean zoomable) {
        if (this.mShowHeaderImage) {
            this.mZoomable = zoomable;
        }
    }

    public PullToZoomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mActivePointerId = -1;
        this.mLastMotionY = -1.0f;
        this.mLastScale = -1.0f;
        this.mMaxScale = -1.0f;
        this.mScalingRunnable = new ScalingRunnable();
        this.mScrollable = true;
        this.mShowHeaderImage = true;
        this.mZoomable = true;
    }

    public PullToZoomListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mActivePointerId = -1;
        this.mLastMotionY = -1.0f;
        this.mLastScale = -1.0f;
        this.mMaxScale = -1.0f;
        this.mScalingRunnable = new ScalingRunnable();
        this.mScrollable = true;
        this.mShowHeaderImage = true;
        this.mZoomable = true;
    }
}
