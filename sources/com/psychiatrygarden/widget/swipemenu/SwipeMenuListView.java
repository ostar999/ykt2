package com.psychiatrygarden.widget.swipemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.ListAdapter;
import android.widget.ListView;

/* loaded from: classes6.dex */
public class SwipeMenuListView extends ListView {
    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_RIGHT = -1;
    private static final int TOUCH_STATE_NONE = 0;
    private static final int TOUCH_STATE_X = 1;
    private static final int TOUCH_STATE_Y = 2;
    private int MAX_X;
    private int MAX_Y;
    private Interpolator mCloseInterpolator;
    private int mDirection;
    private float mDownX;
    private float mDownY;
    private SwipeMenuCreator mMenuCreator;
    private OnMenuItemClickListener mOnMenuItemClickListener;
    private OnMenuStateChangeListener mOnMenuStateChangeListener;
    private OnSwipeListener mOnSwipeListener;
    private Interpolator mOpenInterpolator;
    private int mTouchPosition;
    private int mTouchState;
    private SwipeMenuLayout mTouchView;

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(int position, SwipeMenu menu, int index);
    }

    public interface OnMenuStateChangeListener {
        void onMenuClose(int position);

        void onMenuOpen(int position);
    }

    public interface OnSwipeListener {
        void onSwipeEnd(int position);

        void onSwipeStart(int position);
    }

    public SwipeMenuListView(Context context) {
        super(context);
        this.mDirection = 1;
        this.MAX_Y = 5;
        this.MAX_X = 3;
        init();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(1, dp, getContext().getResources().getDisplayMetrics());
    }

    public static boolean inRangeOfView(View view, MotionEvent ev) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i2 = iArr[0];
        int i3 = iArr[1];
        return ev.getRawX() >= ((float) i2) && ev.getRawX() <= ((float) (i2 + view.getWidth())) && ev.getRawY() >= ((float) i3) && ev.getRawY() <= ((float) (i3 + view.getHeight()));
    }

    private void init() {
        this.MAX_X = dp2px(this.MAX_X);
        this.MAX_Y = dp2px(this.MAX_Y);
        this.mTouchState = 0;
    }

    public Interpolator getCloseInterpolator() {
        return this.mCloseInterpolator;
    }

    public Interpolator getOpenInterpolator() {
        return this.mOpenInterpolator;
    }

    public SwipeMenuCreator getmMenuCreator() {
        return this.mMenuCreator;
    }

    @Override // android.widget.AbsListView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action != 0) {
            if (action == 2) {
                float fAbs = Math.abs(ev.getY() - this.mDownY);
                float fAbs2 = Math.abs(ev.getX() - this.mDownX);
                if (Math.abs(fAbs) > this.MAX_Y || Math.abs(fAbs2) > this.MAX_X) {
                    if (this.mTouchState == 0) {
                        if (Math.abs(fAbs) > this.MAX_Y) {
                            this.mTouchState = 2;
                        } else if (fAbs2 > this.MAX_X) {
                            this.mTouchState = 1;
                            OnSwipeListener onSwipeListener = this.mOnSwipeListener;
                            if (onSwipeListener != null) {
                                onSwipeListener.onSwipeStart(this.mTouchPosition);
                            }
                        }
                    }
                    return true;
                }
            }
            return super.onInterceptTouchEvent(ev);
        }
        this.mDownX = ev.getX();
        this.mDownY = ev.getY();
        boolean zOnInterceptTouchEvent = super.onInterceptTouchEvent(ev);
        this.mTouchState = 0;
        int iPointToPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
        this.mTouchPosition = iPointToPosition;
        View childAt = getChildAt(iPointToPosition - getFirstVisiblePosition());
        if (childAt instanceof SwipeMenuLayout) {
            SwipeMenuLayout swipeMenuLayout = this.mTouchView;
            if (swipeMenuLayout != null && swipeMenuLayout.isOpen() && !inRangeOfView(this.mTouchView.getMenuView(), ev)) {
                return true;
            }
            SwipeMenuLayout swipeMenuLayout2 = (SwipeMenuLayout) childAt;
            this.mTouchView = swipeMenuLayout2;
            swipeMenuLayout2.setSwipeDirection(this.mDirection);
        }
        SwipeMenuLayout swipeMenuLayout3 = this.mTouchView;
        boolean z2 = (swipeMenuLayout3 == null || !swipeMenuLayout3.isOpen() || childAt == this.mTouchView) ? zOnInterceptTouchEvent : true;
        SwipeMenuLayout swipeMenuLayout4 = this.mTouchView;
        if (swipeMenuLayout4 != null) {
            swipeMenuLayout4.onSwipe(ev);
        }
        return z2;
    }

    @Override // android.widget.AbsListView, android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        SwipeMenuLayout swipeMenuLayout;
        OnMenuStateChangeListener onMenuStateChangeListener;
        if (ev.getAction() != 0 && this.mTouchView == null) {
            return super.onTouchEvent(ev);
        }
        int action = ev.getAction();
        if (action == 0) {
            int i2 = this.mTouchPosition;
            this.mDownX = ev.getX();
            this.mDownY = ev.getY();
            this.mTouchState = 0;
            int iPointToPosition = pointToPosition((int) ev.getX(), (int) ev.getY());
            this.mTouchPosition = iPointToPosition;
            if (iPointToPosition == i2 && (swipeMenuLayout = this.mTouchView) != null && swipeMenuLayout.isOpen()) {
                this.mTouchState = 1;
                this.mTouchView.onSwipe(ev);
                return true;
            }
            View childAt = getChildAt(this.mTouchPosition - getFirstVisiblePosition());
            SwipeMenuLayout swipeMenuLayout2 = this.mTouchView;
            if (swipeMenuLayout2 != null && swipeMenuLayout2.isOpen()) {
                this.mTouchView.smoothCloseMenu();
                this.mTouchView = null;
                MotionEvent motionEventObtain = MotionEvent.obtain(ev);
                motionEventObtain.setAction(3);
                onTouchEvent(motionEventObtain);
                OnMenuStateChangeListener onMenuStateChangeListener2 = this.mOnMenuStateChangeListener;
                if (onMenuStateChangeListener2 != null) {
                    onMenuStateChangeListener2.onMenuClose(i2);
                }
                return true;
            }
            if (childAt instanceof SwipeMenuLayout) {
                SwipeMenuLayout swipeMenuLayout3 = (SwipeMenuLayout) childAt;
                this.mTouchView = swipeMenuLayout3;
                swipeMenuLayout3.setSwipeDirection(this.mDirection);
            }
            SwipeMenuLayout swipeMenuLayout4 = this.mTouchView;
            if (swipeMenuLayout4 != null) {
                swipeMenuLayout4.onSwipe(ev);
            }
        } else if (action != 1) {
            if (action == 2) {
                this.mTouchPosition = pointToPosition((int) ev.getX(), (int) ev.getY()) - getHeaderViewsCount();
                if (this.mTouchView.getSwipEnable() && this.mTouchPosition == this.mTouchView.getPosition()) {
                    float fAbs = Math.abs(ev.getY() - this.mDownY);
                    float fAbs2 = Math.abs(ev.getX() - this.mDownX);
                    int i3 = this.mTouchState;
                    if (i3 == 1) {
                        SwipeMenuLayout swipeMenuLayout5 = this.mTouchView;
                        if (swipeMenuLayout5 != null) {
                            swipeMenuLayout5.onSwipe(ev);
                        }
                        getSelector().setState(new int[]{0});
                        ev.setAction(3);
                        super.onTouchEvent(ev);
                        return true;
                    }
                    if (i3 == 0) {
                        if (Math.abs(fAbs) > this.MAX_Y) {
                            this.mTouchState = 2;
                        } else if (fAbs2 > this.MAX_X) {
                            this.mTouchState = 1;
                            OnSwipeListener onSwipeListener = this.mOnSwipeListener;
                            if (onSwipeListener != null) {
                                onSwipeListener.onSwipeStart(this.mTouchPosition);
                            }
                        }
                    }
                }
            }
        } else if (this.mTouchState == 1) {
            SwipeMenuLayout swipeMenuLayout6 = this.mTouchView;
            if (swipeMenuLayout6 != null) {
                boolean zIsOpen = swipeMenuLayout6.isOpen();
                this.mTouchView.onSwipe(ev);
                boolean zIsOpen2 = this.mTouchView.isOpen();
                if (zIsOpen != zIsOpen2 && (onMenuStateChangeListener = this.mOnMenuStateChangeListener) != null) {
                    if (zIsOpen2) {
                        onMenuStateChangeListener.onMenuOpen(this.mTouchPosition);
                    } else {
                        onMenuStateChangeListener.onMenuClose(this.mTouchPosition);
                    }
                }
                if (!zIsOpen2) {
                    this.mTouchPosition = -1;
                    this.mTouchView = null;
                }
            }
            OnSwipeListener onSwipeListener2 = this.mOnSwipeListener;
            if (onSwipeListener2 != null) {
                onSwipeListener2.onSwipeEnd(this.mTouchPosition);
            }
            ev.setAction(3);
            super.onTouchEvent(ev);
            return true;
        }
        return super.onTouchEvent(ev);
    }

    public void setCloseInterpolator(Interpolator interpolator) {
        this.mCloseInterpolator = interpolator;
    }

    public void setMenuCreator(SwipeMenuCreator menuCreator) {
        this.mMenuCreator = menuCreator;
    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.mOnMenuItemClickListener = onMenuItemClickListener;
    }

    public void setOnMenuStateChangeListener(OnMenuStateChangeListener onMenuStateChangeListener) {
        this.mOnMenuStateChangeListener = onMenuStateChangeListener;
    }

    public void setOnSwipeListener(OnSwipeListener onSwipeListener) {
        this.mOnSwipeListener = onSwipeListener;
    }

    public void setOpenInterpolator(Interpolator interpolator) {
        this.mOpenInterpolator = interpolator;
    }

    public void setSwipeDirection(int direction) {
        this.mDirection = direction;
    }

    public void smoothCloseMenu() {
        SwipeMenuLayout swipeMenuLayout = this.mTouchView;
        if (swipeMenuLayout == null || !swipeMenuLayout.isOpen()) {
            return;
        }
        this.mTouchView.smoothCloseMenu();
    }

    public void smoothOpenMenu(int position) {
        if (position < getFirstVisiblePosition() || position > getLastVisiblePosition()) {
            return;
        }
        View childAt = getChildAt(position - getFirstVisiblePosition());
        if (childAt instanceof SwipeMenuLayout) {
            this.mTouchPosition = position;
            SwipeMenuLayout swipeMenuLayout = this.mTouchView;
            if (swipeMenuLayout != null && swipeMenuLayout.isOpen()) {
                this.mTouchView.smoothCloseMenu();
            }
            SwipeMenuLayout swipeMenuLayout2 = (SwipeMenuLayout) childAt;
            this.mTouchView = swipeMenuLayout2;
            swipeMenuLayout2.setSwipeDirection(this.mDirection);
            this.mTouchView.smoothOpenMenu();
        }
    }

    @Override // android.widget.AdapterView
    public void setAdapter(ListAdapter adapter) {
        super.setAdapter((ListAdapter) new SwipeMenuAdapter(getContext(), adapter) { // from class: com.psychiatrygarden.widget.swipemenu.SwipeMenuListView.1
            @Override // com.psychiatrygarden.widget.swipemenu.SwipeMenuAdapter
            public void createMenu(SwipeMenu menu, int pos) {
                if (SwipeMenuListView.this.mMenuCreator != null) {
                    SwipeMenuListView.this.mMenuCreator.create(menu, pos);
                }
            }

            @Override // com.psychiatrygarden.widget.swipemenu.SwipeMenuAdapter, com.psychiatrygarden.widget.swipemenu.SwipeMenuView.OnSwipeItemClickListener
            public void onItemClick(SwipeMenuView view, SwipeMenu menu, int index) {
                boolean zOnMenuItemClick = SwipeMenuListView.this.mOnMenuItemClickListener != null ? SwipeMenuListView.this.mOnMenuItemClickListener.onMenuItemClick(view.getPosition(), menu, index) : false;
                if (SwipeMenuListView.this.mTouchView == null || zOnMenuItemClick) {
                    return;
                }
                SwipeMenuListView.this.mTouchView.smoothCloseMenu();
            }
        });
    }

    public SwipeMenuListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mDirection = 1;
        this.MAX_Y = 5;
        this.MAX_X = 3;
        init();
    }

    public SwipeMenuListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mDirection = 1;
        this.MAX_Y = 5;
        this.MAX_X = 3;
        init();
    }
}
