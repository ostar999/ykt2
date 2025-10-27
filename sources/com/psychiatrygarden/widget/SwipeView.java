package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/* loaded from: classes6.dex */
public class SwipeView extends HorizontalScrollView {
    private static int DEFAULT_SWIPE_THRESHOLD = 60;
    private int SCREEN_WIDTH;
    protected boolean mCallScrollToPageInOnLayout;
    private Context mContext;
    private int mCurrentPage;
    private boolean mJustInterceptedAndIgnored;
    private LinearLayout mLinearLayout;
    private boolean mMostlyScrollingInX;
    private boolean mMostlyScrollingInY;
    private int mMotionStartX;
    private int mMotionStartY;
    private OnPageChangedListener mOnPageChangedListener;
    private View.OnTouchListener mOnTouchListener;
    private int mPageWidth;
    private SwipeOnTouchListener mSwipeOnTouchListener;

    public interface OnPageChangedListener {
        void onPageChanged(int oldPage, int newPage);
    }

    public class SwipeOnTouchListener implements View.OnTouchListener {
        private int mDistanceX;
        private boolean mFirstMotionEvent;
        private int mPreviousDirection;
        private boolean mSendingDummyMotionEvent;

        private SwipeOnTouchListener() {
            this.mSendingDummyMotionEvent = false;
            this.mFirstMotionEvent = true;
        }

        private boolean actionDown(MotionEvent event) {
            SwipeView.this.mMotionStartX = (int) event.getX();
            SwipeView.this.mMotionStartY = (int) event.getY();
            this.mFirstMotionEvent = false;
            return false;
        }

        private boolean actionMove(MotionEvent event) {
            int x2 = SwipeView.this.mMotionStartX - ((int) event.getX());
            int i2 = -1;
            if (x2 >= 0 ? this.mDistanceX - 4 <= x2 : this.mDistanceX + 4 <= x2) {
                i2 = 1;
            }
            if (i2 == this.mPreviousDirection || this.mFirstMotionEvent) {
                this.mDistanceX = x2;
            } else {
                SwipeView.this.mMotionStartX = (int) event.getX();
                this.mDistanceX = SwipeView.this.mMotionStartX - ((int) event.getX());
            }
            this.mPreviousDirection = i2;
            if (!SwipeView.this.mJustInterceptedAndIgnored) {
                return false;
            }
            this.mSendingDummyMotionEvent = true;
            SwipeView.this.dispatchTouchEvent(MotionEvent.obtain(event.getDownTime(), event.getEventTime(), 0, SwipeView.this.mMotionStartX, SwipeView.this.mMotionStartY, event.getPressure(), event.getSize(), event.getMetaState(), event.getXPrecision(), event.getYPrecision(), event.getDeviceId(), event.getEdgeFlags()));
            SwipeView.this.mJustInterceptedAndIgnored = false;
            return true;
        }

        private boolean actionUp(MotionEvent event) {
            int i2;
            int i3;
            float scrollX = SwipeView.this.getScrollX();
            float measuredWidth = SwipeView.this.mLinearLayout.getMeasuredWidth() / SwipeView.this.mPageWidth;
            float f2 = scrollX / SwipeView.this.mPageWidth;
            if (this.mPreviousDirection == 1) {
                if (this.mDistanceX > SwipeView.DEFAULT_SWIPE_THRESHOLD) {
                    if (SwipeView.this.mCurrentPage < measuredWidth - 1.0f) {
                        i2 = (int) (f2 + 1.0f);
                        i3 = SwipeView.this.mPageWidth;
                    } else {
                        i2 = SwipeView.this.mCurrentPage;
                        i3 = SwipeView.this.mPageWidth;
                    }
                } else if (Math.round(f2) == measuredWidth - 1.0f) {
                    i2 = (int) (f2 + 1.0f);
                    i3 = SwipeView.this.mPageWidth;
                } else {
                    i2 = SwipeView.this.mCurrentPage;
                    i3 = SwipeView.this.mPageWidth;
                }
            } else if (this.mDistanceX >= (-SwipeView.DEFAULT_SWIPE_THRESHOLD) && Math.round(f2) != 0) {
                i2 = SwipeView.this.mCurrentPage;
                i3 = SwipeView.this.mPageWidth;
            } else {
                i2 = (int) f2;
                i3 = SwipeView.this.mPageWidth;
            }
            float f3 = i2 * i3;
            SwipeView swipeView = SwipeView.this;
            swipeView.smoothScrollToPage(((int) f3) / swipeView.mPageWidth);
            this.mFirstMotionEvent = true;
            this.mDistanceX = 0;
            SwipeView.this.mMostlyScrollingInX = false;
            SwipeView.this.mMostlyScrollingInY = false;
            return true;
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View v2, MotionEvent event) {
            if (((SwipeView.this.mOnTouchListener != null && !SwipeView.this.mJustInterceptedAndIgnored) || (SwipeView.this.mOnTouchListener != null && this.mSendingDummyMotionEvent)) && SwipeView.this.mOnTouchListener.onTouch(v2, event)) {
                if (event.getAction() == 1) {
                    actionUp(event);
                }
                return true;
            }
            if (this.mSendingDummyMotionEvent) {
                this.mSendingDummyMotionEvent = false;
                return false;
            }
            int action = event.getAction();
            if (action == 0) {
                return actionDown(event);
            }
            if (action == 1) {
                return actionUp(event);
            }
            if (action != 2) {
                return false;
            }
            return actionMove(event);
        }
    }

    public SwipeView(Context context) {
        super(context);
        this.mMostlyScrollingInX = false;
        this.mMostlyScrollingInY = false;
        this.mJustInterceptedAndIgnored = false;
        this.mCallScrollToPageInOnLayout = false;
        this.mCurrentPage = 0;
        this.mPageWidth = 0;
        this.mOnPageChangedListener = null;
        this.mContext = context;
        initSwipeView();
    }

    private void detectMostlyScrollingDirection(MotionEvent ev) {
        if (this.mMostlyScrollingInX || this.mMostlyScrollingInY) {
            return;
        }
        float fAbs = Math.abs(this.mMotionStartX - ev.getX());
        float fAbs2 = Math.abs(this.mMotionStartY - ev.getY());
        if (fAbs2 > fAbs + 5.0f) {
            this.mMostlyScrollingInY = true;
        } else if (fAbs > fAbs2 + 5.0f) {
            this.mMostlyScrollingInX = true;
        }
    }

    private void initSwipeView() {
        LinearLayout linearLayout = new LinearLayout(this.mContext);
        this.mLinearLayout = linearLayout;
        linearLayout.setOrientation(0);
        super.addView(this.mLinearLayout, -1, new FrameLayout.LayoutParams(-1, -1));
        setSmoothScrollingEnabled(true);
        setHorizontalFadingEdgeEnabled(false);
        setHorizontalScrollBarEnabled(false);
        int width = ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getWidth();
        this.SCREEN_WIDTH = width;
        this.mPageWidth = width;
        this.mCurrentPage = 0;
        SwipeOnTouchListener swipeOnTouchListener = new SwipeOnTouchListener();
        this.mSwipeOnTouchListener = swipeOnTouchListener;
        super.setOnTouchListener(swipeOnTouchListener);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View child) {
        addView(child, -1);
    }

    public int calculatePageSize(ViewGroup.MarginLayoutParams childLayoutParams) {
        return setPageWidth(childLayoutParams.leftMargin + childLayoutParams.width + childLayoutParams.rightMargin);
    }

    public LinearLayout getChildContainer() {
        return this.mLinearLayout;
    }

    public int getCurrentPage() {
        return this.mCurrentPage;
    }

    public OnPageChangedListener getOnPageChangedListener() {
        return this.mOnPageChangedListener;
    }

    public int getPageCount() {
        return this.mLinearLayout.getChildCount();
    }

    public int getPageWidth() {
        return this.mPageWidth;
    }

    public int getSwipeThreshold() {
        return DEFAULT_SWIPE_THRESHOLD;
    }

    public View getView(int index) {
        return this.mLinearLayout.getChildAt(index);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean zOnInterceptTouchEvent = super.onInterceptTouchEvent(ev);
        if (ev.getAction() == 0) {
            this.mMotionStartX = (int) ev.getX();
            this.mMotionStartY = (int) ev.getY();
            if (!this.mJustInterceptedAndIgnored) {
                this.mMostlyScrollingInX = false;
                this.mMostlyScrollingInY = false;
            }
        } else if (ev.getAction() == 2) {
            detectMostlyScrollingDirection(ev);
        }
        if (this.mMostlyScrollingInY) {
            return false;
        }
        if (!this.mMostlyScrollingInX) {
            return zOnInterceptTouchEvent;
        }
        this.mJustInterceptedAndIgnored = true;
        return true;
    }

    @Override // android.widget.HorizontalScrollView, android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        super.onLayout(changed, l2, t2, r2, b3);
        if (this.mCallScrollToPageInOnLayout) {
            scrollToPage(this.mCurrentPage);
            this.mCallScrollToPageInOnLayout = false;
        }
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        return false;
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent event) {
        return true;
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View child, View focused) {
        requestFocus();
    }

    public void scrollToPage(int page) {
        scrollToPage(page, false);
    }

    public void setOnPageChangedListener(OnPageChangedListener onPageChangedListener) {
        this.mOnPageChangedListener = onPageChangedListener;
    }

    @Override // android.view.View
    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.mOnTouchListener = onTouchListener;
    }

    public int setPageWidth(int pageWidth) {
        this.mPageWidth = pageWidth;
        return (this.SCREEN_WIDTH - pageWidth) / 2;
    }

    public void setSwipeThreshold(int swipeThreshold) {
        DEFAULT_SWIPE_THRESHOLD = swipeThreshold;
    }

    public void smoothScrollToPage(int page) {
        scrollToPage(page, true);
    }

    private void scrollToPage(int page, boolean smooth) {
        int i2 = this.mCurrentPage;
        if (page >= getPageCount() && getPageCount() > 0) {
            page--;
        } else if (page < 0) {
            page = 0;
        }
        if (smooth) {
            smoothScrollTo(this.mPageWidth * page, 0);
        } else {
            scrollTo(this.mPageWidth * page, 0);
        }
        this.mCurrentPage = page;
        OnPageChangedListener onPageChangedListener = this.mOnPageChangedListener;
        if (onPageChangedListener != null && i2 != page) {
            onPageChangedListener.onPageChanged(i2, page);
        }
        this.mCallScrollToPageInOnLayout = !this.mCallScrollToPageInOnLayout;
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View child, int index) {
        ViewGroup.LayoutParams layoutParams;
        if (child.getLayoutParams() == null) {
            layoutParams = new FrameLayout.LayoutParams(this.mPageWidth, -1);
        } else {
            layoutParams = child.getLayoutParams();
            layoutParams.width = this.mPageWidth;
        }
        addView(child, index, layoutParams);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup, android.view.ViewManager
    public void addView(View child, ViewGroup.LayoutParams params) {
        params.width = this.mPageWidth;
        addView(child, -1, params);
    }

    @Override // android.widget.HorizontalScrollView, android.view.ViewGroup
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        requestLayout();
        invalidate();
        this.mLinearLayout.addView(child, index, params);
    }

    public SwipeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mMostlyScrollingInX = false;
        this.mMostlyScrollingInY = false;
        this.mJustInterceptedAndIgnored = false;
        this.mCallScrollToPageInOnLayout = false;
        this.mCurrentPage = 0;
        this.mPageWidth = 0;
        this.mOnPageChangedListener = null;
        this.mContext = context;
        initSwipeView();
    }

    public SwipeView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mMostlyScrollingInX = false;
        this.mMostlyScrollingInY = false;
        this.mJustInterceptedAndIgnored = false;
        this.mCallScrollToPageInOnLayout = false;
        this.mCurrentPage = 0;
        this.mPageWidth = 0;
        this.mOnPageChangedListener = null;
        this.mContext = context;
        initSwipeView();
    }
}
