package com.psychiatrygarden.widget;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.Interpolator;
import android.widget.Scroller;
import androidx.core.os.ParcelableCompat;
import androidx.core.os.ParcelableCompatCreatorCallbacks;
import androidx.core.view.MotionEventCompat;
import androidx.core.view.VelocityTrackerCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewConfigurationCompat;
import androidx.core.widget.EdgeEffectCompat;
import androidx.viewpager.widget.PagerAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/* loaded from: classes6.dex */
public class LazyViewPager extends ViewGroup {
    private static final boolean DEBUG = false;
    private static final int DEFAULT_OFFSCREEN_PAGES = 0;
    private static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    private static final String TAG = "LazyViewPager";
    private static final boolean USE_CACHE = false;
    private int mActivePointerId;
    private PagerAdapter mAdapter;
    private float mBaseLineFlingVelocity;
    private int mChildHeightMeasureSpec;
    private int mChildWidthMeasureSpec;
    private int mCurItem;
    private long mFakeDragBeginTime;
    private boolean mFakeDragging;
    private boolean mFirstLayout;
    private float mFlingVelocityInfluence;
    private boolean mInLayout;
    private float mInitialMotionX;
    private boolean mIsBeingDragged;
    private boolean mIsUnableToDrag;
    private final ArrayList<ItemInfo> mItems;
    private float mLastMotionX;
    private float mLastMotionY;
    private EdgeEffectCompat mLeftEdge;
    private Drawable mMarginDrawable;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private PagerObserver mObserver;
    private int mOffscreenPageLimit;
    private OnPageChangeListener mOnPageChangeListener;
    private int mPageMargin;
    private boolean mPopulatePending;
    private Parcelable mRestoredAdapterState;
    private ClassLoader mRestoredClassLoader;
    private int mRestoredCurItem;
    private EdgeEffectCompat mRightEdge;
    private int mScrollState;
    private Scroller mScroller;
    private boolean mScrolling;
    private boolean mScrollingCacheEnabled;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private static final Comparator<ItemInfo> COMPARATOR = new Comparator<ItemInfo>() { // from class: com.psychiatrygarden.widget.LazyViewPager.1
        @Override // java.util.Comparator
        public int compare(ItemInfo lhs, ItemInfo rhs) {
            return lhs.position - rhs.position;
        }
    };
    private static final Interpolator sInterpolator = new Interpolator() { // from class: com.psychiatrygarden.widget.LazyViewPager.2
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float t2) {
            float f2 = t2 - 1.0f;
            return (f2 * f2 * f2) + 1.0f;
        }
    };

    public static class ItemInfo {
        Object object;
        int position;
        boolean scrolling;
    }

    public interface OnPageChangeListener {
        void onPageScrollStateChanged(int state);

        void onPageScrolled(int position, float positionOffset, int positionOffsetPixels);

        void onPageSelected(int position);
    }

    public class PagerObserver extends DataSetObserver {
        private PagerObserver() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            LazyViewPager.this.dataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            LazyViewPager.this.dataSetChanged();
        }
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = ParcelableCompat.newCreator(new ParcelableCompatCreatorCallbacks<SavedState>() { // from class: com.psychiatrygarden.widget.LazyViewPager.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // androidx.core.os.ParcelableCompatCreatorCallbacks
            public SavedState createFromParcel(Parcel in, ClassLoader loader) {
                return new SavedState(in, loader);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // androidx.core.os.ParcelableCompatCreatorCallbacks
            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        });
        Parcelable adapterState;
        ClassLoader loader;
        int position;

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public String toString() {
            return "FragmentPager.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " position=" + this.position + "}";
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeInt(this.position);
            out.writeParcelable(this.adapterState, flags);
        }

        public SavedState(Parcel in, ClassLoader loader) {
            super(in);
            loader = loader == null ? getClass().getClassLoader() : loader;
            this.position = in.readInt();
            this.adapterState = in.readParcelable(loader);
            this.loader = loader;
        }
    }

    public static class SimpleOnPageChangeListener implements OnPageChangeListener {
        @Override // com.psychiatrygarden.widget.LazyViewPager.OnPageChangeListener
        public void onPageScrollStateChanged(int state) {
        }

        @Override // com.psychiatrygarden.widget.LazyViewPager.OnPageChangeListener
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override // com.psychiatrygarden.widget.LazyViewPager.OnPageChangeListener
        public void onPageSelected(int position) {
        }
    }

    public LazyViewPager(Context context) {
        super(context);
        this.mItems = new ArrayList<>();
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
        this.mOffscreenPageLimit = 0;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mScrollState = 0;
        initViewPager();
    }

    private void completeScroll() {
        boolean z2 = this.mScrolling;
        if (z2) {
            setScrollingCacheEnabled(false);
            this.mScroller.abortAnimation();
            int scrollX = getScrollX();
            int scrollY = getScrollY();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            if (scrollX != currX || scrollY != currY) {
                scrollTo(currX, currY);
            }
            setScrollState(0);
        }
        this.mPopulatePending = false;
        this.mScrolling = false;
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            ItemInfo itemInfo = this.mItems.get(i2);
            if (itemInfo.scrolling) {
                itemInfo.scrolling = false;
                z2 = true;
            }
        }
        if (z2) {
            populate();
        }
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.mIsUnableToDrag = false;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void onSecondaryPointerUp(MotionEvent ev) {
        int actionIndex = MotionEventCompat.getActionIndex(ev);
        if (MotionEventCompat.getPointerId(ev, actionIndex) == this.mActivePointerId) {
            int i2 = actionIndex == 0 ? 1 : 0;
            this.mLastMotionX = MotionEventCompat.getX(ev, i2);
            this.mActivePointerId = MotionEventCompat.getPointerId(ev, i2);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    private void recomputeScrollPosition(int width, int oldWidth, int margin, int oldMargin) {
        int i2 = width + margin;
        if (oldWidth <= 0) {
            int i3 = this.mCurItem * i2;
            if (i3 != getScrollX()) {
                completeScroll();
                scrollTo(i3, getScrollY());
                return;
            }
            return;
        }
        int i4 = oldWidth + oldMargin;
        int scrollX = (int) (((getScrollX() / i4) + ((r9 % i4) / i4)) * i2);
        scrollTo(scrollX, getScrollY());
        if (this.mScroller.isFinished()) {
            return;
        }
        this.mScroller.startScroll(scrollX, 0, this.mCurItem * i2, 0, this.mScroller.getDuration() - this.mScroller.timePassed());
    }

    private void setScrollState(int newState) {
        if (this.mScrollState == newState) {
            return;
        }
        this.mScrollState = newState;
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            onPageChangeListener.onPageScrollStateChanged(newState);
        }
    }

    private void setScrollingCacheEnabled(boolean enabled) {
        if (this.mScrollingCacheEnabled != enabled) {
            this.mScrollingCacheEnabled = enabled;
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList<View> views, int direction, int focusableMode) {
        ItemInfo itemInfoInfoForChild;
        int size = views.size();
        int descendantFocusability = getDescendantFocusability();
        if (descendantFocusability != 393216) {
            for (int i2 = 0; i2 < getChildCount(); i2++) {
                View childAt = getChildAt(i2);
                if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem) {
                    childAt.addFocusables(views, direction, focusableMode);
                }
            }
        }
        if ((descendantFocusability != 262144 || size == views.size()) && isFocusable()) {
            if ((focusableMode & 1) == 1 && isInTouchMode() && !isFocusableInTouchMode()) {
                return;
            }
            views.add(this);
        }
    }

    public void addNewItem(int position, int index) {
        ItemInfo itemInfo = new ItemInfo();
        itemInfo.position = position;
        itemInfo.object = this.mAdapter.instantiateItem((ViewGroup) this, position);
        if (index < 0) {
            this.mItems.add(itemInfo);
        } else {
            this.mItems.add(index, itemInfo);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addTouchables(ArrayList<View> views) {
        ItemInfo itemInfoInfoForChild;
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem) {
                childAt.addTouchables(views);
            }
        }
    }

    @Override // android.view.ViewGroup
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (!this.mInLayout) {
            super.addView(child, index, params);
        } else {
            addViewInLayout(child, index, params);
            child.measure(this.mChildWidthMeasureSpec, this.mChildHeightMeasureSpec);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0053  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean arrowScroll(int r5) {
        /*
            r4 = this;
            android.view.View r0 = r4.findFocus()
            if (r0 != r4) goto L7
            r0 = 0
        L7:
            android.view.FocusFinder r1 = android.view.FocusFinder.getInstance()
            android.view.View r1 = r1.findNextFocus(r4, r0, r5)
            r2 = 66
            r3 = 17
            if (r1 == 0) goto L47
            if (r1 == r0) goto L47
            if (r5 != r3) goto L2f
            if (r0 == 0) goto L2a
            int r2 = r1.getLeft()
            int r0 = r0.getLeft()
            if (r2 < r0) goto L2a
            boolean r0 = r4.pageLeft()
            goto L5e
        L2a:
            boolean r0 = r1.requestFocus()
            goto L5e
        L2f:
            if (r5 != r2) goto L53
            if (r0 == 0) goto L42
            int r2 = r1.getLeft()
            int r0 = r0.getLeft()
            if (r2 > r0) goto L42
            boolean r0 = r4.pageRight()
            goto L5e
        L42:
            boolean r0 = r1.requestFocus()
            goto L5e
        L47:
            if (r5 == r3) goto L5a
            r0 = 1
            if (r5 != r0) goto L4d
            goto L5a
        L4d:
            if (r5 == r2) goto L55
            r0 = 2
            if (r5 != r0) goto L53
            goto L55
        L53:
            r0 = 0
            goto L5e
        L55:
            boolean r0 = r4.pageRight()
            goto L5e
        L5a:
            boolean r0 = r4.pageLeft()
        L5e:
            if (r0 == 0) goto L67
            int r5 = android.view.SoundEffectConstants.getContantForFocusDirection(r5)
            r4.playSoundEffect(r5)
        L67:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.LazyViewPager.arrowScroll(int):boolean");
    }

    public boolean beginFakeDrag() {
        if (this.mIsBeingDragged) {
            return false;
        }
        this.mFakeDragging = true;
        setScrollState(1);
        this.mLastMotionX = 0.0f;
        this.mInitialMotionX = 0.0f;
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
        long jUptimeMillis = SystemClock.uptimeMillis();
        MotionEvent motionEventObtain = MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 0, 0.0f, 0.0f, 0);
        this.mVelocityTracker.addMovement(motionEventObtain);
        motionEventObtain.recycle();
        this.mFakeDragBeginTime = jUptimeMillis;
        return true;
    }

    public boolean canScroll(View v2, boolean checkV, int dx, int x2, int y2) {
        int i2;
        if (v2 instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) v2;
            int scrollX = v2.getScrollX();
            int scrollY = v2.getScrollY();
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                int i3 = x2 + scrollX;
                if (i3 >= childAt.getLeft() && i3 < childAt.getRight() && (i2 = y2 + scrollY) >= childAt.getTop() && i2 < childAt.getBottom() && canScroll(childAt, true, dx, i3 - childAt.getLeft(), i2 - childAt.getTop())) {
                    return true;
                }
            }
        }
        return checkV && ViewCompat.canScrollHorizontally(v2, -dx);
    }

    @Override // android.view.View
    public void computeScroll() {
        if (this.mScroller.isFinished() || !this.mScroller.computeScrollOffset()) {
            completeScroll();
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int currX = this.mScroller.getCurrX();
        int currY = this.mScroller.getCurrY();
        if (scrollX != currX || scrollY != currY) {
            scrollTo(currX, currY);
        }
        if (this.mOnPageChangeListener != null) {
            int width = getWidth() + this.mPageMargin;
            int i2 = currX / width;
            int i3 = currX % width;
            this.mOnPageChangeListener.onPageScrolled(i2, i3 / width, i3);
        }
        invalidate();
    }

    public void dataSetChanged() {
        boolean z2 = true;
        boolean z3 = this.mItems.size() < 3 && this.mItems.size() < this.mAdapter.getSize();
        int iMax = -1;
        int i2 = 0;
        while (i2 < this.mItems.size()) {
            ItemInfo itemInfo = this.mItems.get(i2);
            int itemPosition = this.mAdapter.getItemPosition(itemInfo.object);
            if (itemPosition != -1) {
                if (itemPosition == -2) {
                    this.mItems.remove(i2);
                    i2--;
                    this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
                    int i3 = this.mCurItem;
                    if (i3 == itemInfo.position) {
                        iMax = Math.max(0, Math.min(i3, this.mAdapter.getSize() - 1));
                    }
                } else {
                    int i4 = itemInfo.position;
                    if (i4 != itemPosition) {
                        if (i4 == this.mCurItem) {
                            iMax = itemPosition;
                        }
                        itemInfo.position = itemPosition;
                    }
                }
                z3 = true;
            }
            i2++;
        }
        Collections.sort(this.mItems, COMPARATOR);
        if (iMax >= 0) {
            setCurrentItemInternal(iMax, false, true);
        } else {
            z2 = z3;
        }
        if (z2) {
            populate();
            requestLayout();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event) || executeKeyEvent(event);
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
        ItemInfo itemInfoInfoForChild;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem && childAt.dispatchPopulateAccessibilityEvent(event)) {
                return true;
            }
        }
        return false;
    }

    public float distanceInfluenceForSnapDuration(float f2) {
        return (float) Math.sin((float) ((f2 - 0.5f) * 0.4712389167638204d));
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        PagerAdapter pagerAdapter;
        super.draw(canvas);
        int overScrollMode = ViewCompat.getOverScrollMode(this);
        boolean zDraw = false;
        if (overScrollMode == 0 || (overScrollMode == 1 && (pagerAdapter = this.mAdapter) != null && pagerAdapter.getSize() > 1)) {
            if (!this.mLeftEdge.isFinished()) {
                int iSave = canvas.save();
                int height = (getHeight() - getPaddingTop()) - getPaddingBottom();
                canvas.rotate(270.0f);
                canvas.translate((-height) + getPaddingTop(), 0.0f);
                this.mLeftEdge.setSize(height, getWidth());
                zDraw = false | this.mLeftEdge.draw(canvas);
                canvas.restoreToCount(iSave);
            }
            if (!this.mRightEdge.isFinished()) {
                int iSave2 = canvas.save();
                int width = getWidth();
                int height2 = (getHeight() - getPaddingTop()) - getPaddingBottom();
                PagerAdapter pagerAdapter2 = this.mAdapter;
                int size = pagerAdapter2 != null ? pagerAdapter2.getSize() : 1;
                canvas.rotate(90.0f);
                float f2 = -getPaddingTop();
                int i2 = this.mPageMargin;
                canvas.translate(f2, ((-size) * (width + i2)) + i2);
                this.mRightEdge.setSize(height2, width);
                zDraw |= this.mRightEdge.draw(canvas);
                canvas.restoreToCount(iSave2);
            }
        } else {
            this.mLeftEdge.finish();
            this.mRightEdge.finish();
        }
        if (zDraw) {
            invalidate();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mMarginDrawable;
        if (drawable == null || !drawable.isStateful()) {
            return;
        }
        drawable.setState(getDrawableState());
    }

    public void endFakeDrag() {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
        int yVelocity = (int) VelocityTrackerCompat.getYVelocity(velocityTracker, this.mActivePointerId);
        this.mPopulatePending = true;
        if (Math.abs(yVelocity) <= this.mMinimumVelocity && Math.abs(this.mInitialMotionX - this.mLastMotionX) < getWidth() / 3) {
            setCurrentItemInternal(this.mCurItem, true, true);
        } else if (this.mLastMotionX > this.mInitialMotionX) {
            setCurrentItemInternal(this.mCurItem - 1, true, true);
        } else {
            setCurrentItemInternal(this.mCurItem + 1, true, true);
        }
        endDrag();
        this.mFakeDragging = false;
    }

    public boolean executeKeyEvent(KeyEvent event) {
        if (event.getAction() == 0) {
            int keyCode = event.getKeyCode();
            if (keyCode == 21) {
                return arrowScroll(17);
            }
            if (keyCode == 22) {
                return arrowScroll(66);
            }
            if (keyCode == 61) {
                if (event.hasNoModifiers()) {
                    return arrowScroll(2);
                }
                if (event.hasModifiers(1)) {
                    return arrowScroll(1);
                }
            }
        }
        return false;
    }

    public void fakeDragBy(float xOffset) {
        if (!this.mFakeDragging) {
            throw new IllegalStateException("No fake drag in progress. Call beginFakeDrag first.");
        }
        this.mLastMotionX += xOffset;
        float scrollX = getScrollX() - xOffset;
        int width = getWidth() + this.mPageMargin;
        float fMax = Math.max(0, (this.mCurItem - 1) * width);
        float fMin = Math.min(this.mCurItem + 1, this.mAdapter.getSize() - 1) * width;
        if (scrollX < fMax) {
            scrollX = fMax;
        } else if (scrollX > fMin) {
            scrollX = fMin;
        }
        int i2 = (int) scrollX;
        this.mLastMotionX += scrollX - i2;
        scrollTo(i2, getScrollY());
        OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
        if (onPageChangeListener != null) {
            int i3 = i2 / width;
            int i4 = i2 % width;
            onPageChangeListener.onPageScrolled(i3, i4 / width, i4);
        }
        MotionEvent motionEventObtain = MotionEvent.obtain(this.mFakeDragBeginTime, SystemClock.uptimeMillis(), 2, this.mLastMotionX, 0.0f, 0);
        this.mVelocityTracker.addMovement(motionEventObtain);
        motionEventObtain.recycle();
    }

    public PagerAdapter getAdapter() {
        return this.mAdapter;
    }

    public int getCurrentItem() {
        return this.mCurItem;
    }

    public int getOffscreenPageLimit() {
        return this.mOffscreenPageLimit;
    }

    public int getPageMargin() {
        return this.mPageMargin;
    }

    public ItemInfo infoForAnyChild(View child) {
        while (true) {
            Object parent = child.getParent();
            if (parent == this) {
                return infoForChild(child);
            }
            if (parent == null || !(parent instanceof View)) {
                return null;
            }
            child = (View) parent;
        }
    }

    public ItemInfo infoForChild(View child) {
        for (int i2 = 0; i2 < this.mItems.size(); i2++) {
            ItemInfo itemInfo = this.mItems.get(i2);
            if (this.mAdapter.isViewFromObject(child, itemInfo.object)) {
                return itemInfo;
            }
        }
        return null;
    }

    public void initViewPager() {
        setWillNotDraw(false);
        setDescendantFocusability(262144);
        setFocusable(true);
        Context context = getContext();
        this.mScroller = new Scroller(context, sInterpolator);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(viewConfiguration);
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mLeftEdge = new EdgeEffectCompat(context);
        this.mRightEdge = new EdgeEffectCompat(context);
        this.mBaseLineFlingVelocity = context.getResources().getDisplayMetrics().density * 2500.0f;
        this.mFlingVelocityInfluence = 0.4f;
    }

    public boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mFirstLayout = true;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mPageMargin <= 0 || this.mMarginDrawable == null) {
            return;
        }
        int scrollX = getScrollX();
        int width = getWidth();
        int i2 = this.mPageMargin;
        int i3 = scrollX % (width + i2);
        if (i3 != 0) {
            int i4 = (scrollX - i3) + width;
            this.mMarginDrawable.setBounds(i4, 0, i2 + i4, getHeight());
            this.mMarginDrawable.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        PagerAdapter pagerAdapter;
        int action = ev.getAction() & 255;
        if (action == 3 || action == 1) {
            this.mIsBeingDragged = false;
            this.mIsUnableToDrag = false;
            this.mActivePointerId = -1;
            return false;
        }
        if (action != 0) {
            if (this.mIsBeingDragged) {
                return true;
            }
            if (this.mIsUnableToDrag) {
                return false;
            }
        }
        if (action == 0) {
            float x2 = ev.getX();
            this.mInitialMotionX = x2;
            this.mLastMotionX = x2;
            this.mLastMotionY = ev.getY();
            this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
            if (this.mScrollState == 2) {
                this.mIsBeingDragged = true;
                this.mIsUnableToDrag = false;
                setScrollState(1);
            } else {
                completeScroll();
                this.mIsBeingDragged = false;
                this.mIsUnableToDrag = false;
            }
        } else if (action == 2) {
            int i2 = this.mActivePointerId;
            if (i2 != -1) {
                int iFindPointerIndex = MotionEventCompat.findPointerIndex(ev, i2);
                float x3 = MotionEventCompat.getX(ev, iFindPointerIndex);
                float f2 = x3 - this.mLastMotionX;
                float fAbs = Math.abs(f2);
                float y2 = MotionEventCompat.getY(ev, iFindPointerIndex);
                float fAbs2 = Math.abs(y2 - this.mLastMotionY);
                int scrollX = getScrollX();
                if ((f2 <= 0.0f || scrollX != 0) && f2 < 0.0f && (pagerAdapter = this.mAdapter) != null) {
                    pagerAdapter.getSize();
                    getWidth();
                }
                if (canScroll(this, false, (int) f2, (int) x3, (int) y2)) {
                    this.mLastMotionX = x3;
                    this.mInitialMotionX = x3;
                    this.mLastMotionY = y2;
                    return false;
                }
                int i3 = this.mTouchSlop;
                if (fAbs > i3 && fAbs > fAbs2) {
                    this.mIsBeingDragged = true;
                    setScrollState(1);
                    this.mLastMotionX = x3;
                    setScrollingCacheEnabled(true);
                } else if (fAbs2 > i3) {
                    this.mIsUnableToDrag = true;
                }
            }
        } else if (action == 6) {
            onSecondaryPointerUp(ev);
        }
        return this.mIsBeingDragged;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        ItemInfo itemInfoInfoForChild;
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int childCount = getChildCount();
        int i2 = r2 - l2;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            if (childAt.getVisibility() != 8 && (itemInfoInfoForChild = infoForChild(childAt)) != null) {
                int paddingLeft = getPaddingLeft() + ((this.mPageMargin + i2) * itemInfoInfoForChild.position);
                int paddingTop = getPaddingTop();
                childAt.layout(paddingLeft, paddingTop, childAt.getMeasuredWidth() + paddingLeft, childAt.getMeasuredHeight() + paddingTop);
            }
        }
        this.mFirstLayout = false;
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(View.getDefaultSize(0, widthMeasureSpec), View.getDefaultSize(0, heightMeasureSpec));
        this.mChildWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), 1073741824);
        this.mChildHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), 1073741824);
        this.mInLayout = true;
        populate();
        this.mInLayout = false;
        int childCount = getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() != 8) {
                childAt.measure(this.mChildWidthMeasureSpec, this.mChildHeightMeasureSpec);
            }
        }
    }

    @Override // android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
        int i2;
        int i3;
        int i4;
        ItemInfo itemInfoInfoForChild;
        int childCount = getChildCount();
        if ((direction & 2) != 0) {
            i3 = childCount;
            i2 = 0;
            i4 = 1;
        } else {
            i2 = childCount - 1;
            i3 = -1;
            i4 = -1;
        }
        while (i2 != i3) {
            View childAt = getChildAt(i2);
            if (childAt.getVisibility() == 0 && (itemInfoInfoForChild = infoForChild(childAt)) != null && itemInfoInfoForChild.position == this.mCurItem && childAt.requestFocus(direction, previouslyFocusedRect)) {
                return true;
            }
            i2 += i4;
        }
        return false;
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            pagerAdapter.restoreState(savedState.adapterState, savedState.loader);
            setCurrentItemInternal(savedState.position, false, true);
        } else {
            this.mRestoredCurItem = savedState.position;
            this.mRestoredAdapterState = savedState.adapterState;
            this.mRestoredClassLoader = savedState.loader;
        }
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.position = this.mCurItem;
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            savedState.adapterState = pagerAdapter.saveState();
        }
        return savedState;
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        super.onSizeChanged(w2, h2, oldw, oldh);
        if (w2 != oldw) {
            int i2 = this.mPageMargin;
            recomputeScrollPosition(w2, oldw, i2, i2);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent ev) {
        PagerAdapter pagerAdapter;
        boolean zOnRelease;
        boolean zOnRelease2;
        if (this.mFakeDragging) {
            return true;
        }
        if ((ev.getAction() == 0 && ev.getEdgeFlags() != 0) || (pagerAdapter = this.mAdapter) == null || pagerAdapter.getSize() == 0) {
            return false;
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(ev);
        int action = ev.getAction() & 255;
        if (action == 0) {
            completeScroll();
            float x2 = ev.getX();
            this.mInitialMotionX = x2;
            this.mLastMotionX = x2;
            this.mActivePointerId = MotionEventCompat.getPointerId(ev, 0);
        } else if (action != 1) {
            if (action == 2) {
                if (!this.mIsBeingDragged) {
                    int iFindPointerIndex = MotionEventCompat.findPointerIndex(ev, this.mActivePointerId);
                    float x3 = MotionEventCompat.getX(ev, iFindPointerIndex);
                    float fAbs = Math.abs(x3 - this.mLastMotionX);
                    float fAbs2 = Math.abs(MotionEventCompat.getY(ev, iFindPointerIndex) - this.mLastMotionY);
                    if (fAbs > this.mTouchSlop && fAbs > fAbs2) {
                        this.mIsBeingDragged = true;
                        this.mLastMotionX = x3;
                        setScrollState(1);
                        setScrollingCacheEnabled(true);
                    }
                }
                if (this.mIsBeingDragged) {
                    float x4 = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId));
                    float f2 = this.mLastMotionX - x4;
                    this.mLastMotionX = x4;
                    float scrollX = getScrollX() + f2;
                    int width = getWidth();
                    int i2 = this.mPageMargin + width;
                    int size = this.mAdapter.getSize() - 1;
                    float fMax = Math.max(0, (this.mCurItem - 1) * i2);
                    float fMin = Math.min(this.mCurItem + 1, size) * i2;
                    if (scrollX < fMax) {
                        zOnPull = fMax == 0.0f ? this.mLeftEdge.onPull((-scrollX) / width) : false;
                        scrollX = fMax;
                    } else if (scrollX > fMin) {
                        zOnPull = fMin == ((float) (size * i2)) ? this.mRightEdge.onPull((scrollX - fMin) / width) : false;
                        scrollX = fMin;
                    }
                    int i3 = (int) scrollX;
                    this.mLastMotionX += scrollX - i3;
                    scrollTo(i3, getScrollY());
                    OnPageChangeListener onPageChangeListener = this.mOnPageChangeListener;
                    if (onPageChangeListener != null) {
                        int i4 = i3 / i2;
                        int i5 = i3 % i2;
                        onPageChangeListener.onPageScrolled(i4, i5 / i2, i5);
                    }
                }
            } else if (action != 3) {
                if (action == 5) {
                    int actionIndex = MotionEventCompat.getActionIndex(ev);
                    this.mLastMotionX = MotionEventCompat.getX(ev, actionIndex);
                    this.mActivePointerId = MotionEventCompat.getPointerId(ev, actionIndex);
                } else if (action == 6) {
                    onSecondaryPointerUp(ev);
                    this.mLastMotionX = MotionEventCompat.getX(ev, MotionEventCompat.findPointerIndex(ev, this.mActivePointerId));
                }
            } else if (this.mIsBeingDragged) {
                setCurrentItemInternal(this.mCurItem, true, true);
                this.mActivePointerId = -1;
                endDrag();
                zOnRelease = this.mLeftEdge.onRelease();
                zOnRelease2 = this.mRightEdge.onRelease();
                zOnPull = zOnRelease | zOnRelease2;
            }
        } else if (this.mIsBeingDragged) {
            VelocityTracker velocityTracker = this.mVelocityTracker;
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
            int xVelocity = (int) VelocityTrackerCompat.getXVelocity(velocityTracker, this.mActivePointerId);
            this.mPopulatePending = true;
            int scrollX2 = getScrollX() / (getWidth() + this.mPageMargin);
            if (xVelocity <= 0) {
                scrollX2++;
            }
            setCurrentItemInternal(scrollX2, true, true, xVelocity);
            this.mActivePointerId = -1;
            endDrag();
            zOnRelease = this.mLeftEdge.onRelease();
            zOnRelease2 = this.mRightEdge.onRelease();
            zOnPull = zOnRelease | zOnRelease2;
        }
        if (zOnPull) {
            invalidate();
        }
        return true;
    }

    public boolean pageLeft() {
        int i2 = this.mCurItem;
        if (i2 <= 0) {
            return false;
        }
        setCurrentItem(i2 - 1, true);
        return true;
    }

    public boolean pageRight() {
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null || this.mCurItem >= pagerAdapter.getSize() - 1) {
            return false;
        }
        setCurrentItem(this.mCurItem + 1, true);
        return true;
    }

    /*  JADX ERROR: NullPointerException in pass: LoopRegionVisitor
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.SSAVar.use(jadx.core.dex.instructions.args.RegisterArg)" because "ssaVar" is null
        	at jadx.core.dex.nodes.InsnNode.rebindArgs(InsnNode.java:493)
        	at jadx.core.dex.nodes.InsnNode.rebindArgs(InsnNode.java:496)
        */
    public void populate() {
        /*
            Method dump skipped, instructions count: 275
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.psychiatrygarden.widget.LazyViewPager.populate():void");
    }

    public void setAdapter(PagerAdapter adapter) {
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter != null) {
            pagerAdapter.unregisterDataSetObserver(this.mObserver);
            this.mAdapter.startUpdate((ViewGroup) this);
            for (int i2 = 0; i2 < this.mItems.size(); i2++) {
                ItemInfo itemInfo = this.mItems.get(i2);
                this.mAdapter.destroyItem((ViewGroup) this, itemInfo.position, itemInfo.object);
            }
            this.mAdapter.finishUpdate((ViewGroup) this);
            this.mItems.clear();
            removeAllViews();
            this.mCurItem = 0;
            scrollTo(0, 0);
        }
        this.mAdapter = adapter;
        if (adapter != null) {
            if (this.mObserver == null) {
                this.mObserver = new PagerObserver();
            }
            this.mAdapter.registerDataSetObserver(this.mObserver);
            this.mPopulatePending = false;
            if (this.mRestoredCurItem < 0) {
                populate();
                return;
            }
            this.mAdapter.restoreState(this.mRestoredAdapterState, this.mRestoredClassLoader);
            setCurrentItemInternal(this.mRestoredCurItem, false, true);
            this.mRestoredCurItem = -1;
            this.mRestoredAdapterState = null;
            this.mRestoredClassLoader = null;
        }
    }

    public void setCurrentItem(int item) {
        this.mPopulatePending = false;
        setCurrentItemInternal(item, !this.mFirstLayout, false);
    }

    public void setCurrentItemInternal(int item, boolean smoothScroll, boolean always) {
        setCurrentItemInternal(item, smoothScroll, always, 0);
    }

    public void setOffscreenPageLimit(int limit) {
        if (limit < 0) {
            StringBuilder sb = new StringBuilder();
            sb.append("Requested offscreen page limit ");
            sb.append(limit);
            sb.append(" too small; defaulting to ");
            limit = 0;
            sb.append(0);
            Log.w(TAG, sb.toString());
        }
        if (limit != this.mOffscreenPageLimit) {
            this.mOffscreenPageLimit = limit;
            populate();
        }
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.mOnPageChangeListener = listener;
    }

    public void setPageMargin(int marginPixels) {
        int i2 = this.mPageMargin;
        this.mPageMargin = marginPixels;
        int width = getWidth();
        recomputeScrollPosition(width, width, marginPixels, i2);
        requestLayout();
    }

    public void setPageMarginDrawable(Drawable d3) {
        this.mMarginDrawable = d3;
        if (d3 != null) {
            refreshDrawableState();
        }
        setWillNotDraw(d3 == null);
        invalidate();
    }

    public void smoothScrollTo(int x2, int y2) {
        smoothScrollTo(x2, y2, 0);
    }

    @Override // android.view.View
    public boolean verifyDrawable(Drawable who) {
        return super.verifyDrawable(who) || who == this.mMarginDrawable;
    }

    public void setCurrentItemInternal(int item, boolean smoothScroll, boolean always, int velocity) {
        OnPageChangeListener onPageChangeListener;
        OnPageChangeListener onPageChangeListener2;
        PagerAdapter pagerAdapter = this.mAdapter;
        if (pagerAdapter == null || pagerAdapter.getSize() <= 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (!always && this.mCurItem == item && this.mItems.size() != 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        if (item < 0) {
            item = 0;
        } else if (item >= this.mAdapter.getSize()) {
            item = this.mAdapter.getSize() - 1;
        }
        int i2 = this.mOffscreenPageLimit;
        int i3 = this.mCurItem;
        if (item > i3 + i2 || item < i3 - i2) {
            for (int i4 = 0; i4 < this.mItems.size(); i4++) {
                this.mItems.get(i4).scrolling = true;
            }
        }
        boolean z2 = this.mCurItem != item;
        this.mCurItem = item;
        populate();
        int width = (getWidth() + this.mPageMargin) * item;
        if (smoothScroll) {
            smoothScrollTo(width, 0, velocity);
            if (!z2 || (onPageChangeListener2 = this.mOnPageChangeListener) == null) {
                return;
            }
            onPageChangeListener2.onPageSelected(item);
            return;
        }
        if (z2 && (onPageChangeListener = this.mOnPageChangeListener) != null) {
            onPageChangeListener.onPageSelected(item);
        }
        completeScroll();
        scrollTo(width, 0);
    }

    public void smoothScrollTo(int x2, int y2, int velocity) {
        int i2;
        if (getChildCount() == 0) {
            setScrollingCacheEnabled(false);
            return;
        }
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        int i3 = x2 - scrollX;
        int i4 = y2 - scrollY;
        if (i3 == 0 && i4 == 0) {
            completeScroll();
            setScrollState(0);
            return;
        }
        setScrollingCacheEnabled(true);
        this.mScrolling = true;
        setScrollState(2);
        int iAbs = (int) ((Math.abs(i3) / (getWidth() + this.mPageMargin)) * 100.0f);
        int iAbs2 = Math.abs(velocity);
        if (iAbs2 > 0) {
            float f2 = iAbs;
            i2 = (int) (f2 + ((f2 / (iAbs2 / this.mBaseLineFlingVelocity)) * this.mFlingVelocityInfluence));
        } else {
            i2 = iAbs + 100;
        }
        this.mScroller.startScroll(scrollX, scrollY, i3, i4, Math.min(i2, 600));
        invalidate();
    }

    public void setCurrentItem(int item, boolean smoothScroll) {
        this.mPopulatePending = false;
        setCurrentItemInternal(item, smoothScroll, false);
    }

    public void setPageMarginDrawable(int resId) {
        setPageMarginDrawable(getContext().getResources().getDrawable(resId));
    }

    public LazyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mItems = new ArrayList<>();
        this.mRestoredCurItem = -1;
        this.mRestoredAdapterState = null;
        this.mRestoredClassLoader = null;
        this.mOffscreenPageLimit = 0;
        this.mActivePointerId = -1;
        this.mFirstLayout = true;
        this.mScrollState = 0;
        initViewPager();
    }
}
