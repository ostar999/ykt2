package com.actionbarsherlock.widget.wheelview;

import android.R;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import com.actionbarsherlock.widget.wheelview.WheelScroller;
import com.actionbarsherlock.widget.wheelview.adapter.WheelViewAdapter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/* loaded from: classes.dex */
public class WheelView extends View {
    private static final int DEF_VISIBLE_ITEMS = 5;
    private static final int ITEM_OFFSET_PERCENT = 0;
    private static final int PADDING = 10;
    private int[] SHADOWS_COLORS;
    private GradientDrawable bottomShadow;
    private Drawable centerDrawable;
    private List<OnWheelChangedListener> changingListeners;
    private List<OnWheelClickedListener> clickingListeners;
    private int currentItem;
    private DataSetObserver dataObserver;
    private boolean drawShadows;
    private int firstItem;
    boolean isCyclic;
    private boolean isScrollingPerformed;
    private int itemHeight;
    private LinearLayout itemsLayout;
    String label;
    private WheelRecycle recycle;
    private WheelScroller scroller;
    WheelScroller.ScrollingListener scrollingListener;
    private List<OnWheelScrollListener> scrollingListeners;
    private int scrollingOffset;
    private GradientDrawable topShadow;
    private WheelViewAdapter viewAdapter;
    private int visibleItems;
    private int wheelBackground;
    private int wheelForeground;

    public WheelView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.SHADOWS_COLORS = new int[]{11316396, 0, 11316396};
        this.currentItem = 0;
        this.visibleItems = 5;
        this.itemHeight = 0;
        this.wheelBackground = R.color.transparent;
        this.wheelForeground = R.color.transparent;
        this.drawShadows = true;
        this.isCyclic = false;
        this.recycle = new WheelRecycle(this);
        this.changingListeners = new LinkedList();
        this.scrollingListeners = new LinkedList();
        this.clickingListeners = new LinkedList();
        this.label = "";
        this.scrollingListener = new WheelScroller.ScrollingListener() { // from class: com.actionbarsherlock.widget.wheelview.WheelView.1
            @Override // com.actionbarsherlock.widget.wheelview.WheelScroller.ScrollingListener
            public void onFinished() {
                if (WheelView.this.isScrollingPerformed) {
                    WheelView.this.notifyScrollingListenersAboutEnd();
                    WheelView.this.isScrollingPerformed = false;
                }
                WheelView.this.scrollingOffset = 0;
                WheelView.this.invalidate();
            }

            @Override // com.actionbarsherlock.widget.wheelview.WheelScroller.ScrollingListener
            public void onJustify() {
                if (Math.abs(WheelView.this.scrollingOffset) > 1) {
                    WheelView.this.scroller.scroll(WheelView.this.scrollingOffset, 0);
                }
            }

            @Override // com.actionbarsherlock.widget.wheelview.WheelScroller.ScrollingListener
            public void onScroll(int i3) {
                WheelView.this.doScroll(i3);
                int height = WheelView.this.getHeight();
                if (WheelView.this.scrollingOffset > height) {
                    WheelView.this.scrollingOffset = height;
                    WheelView.this.scroller.stopScrolling();
                    return;
                }
                int i4 = -height;
                if (WheelView.this.scrollingOffset < i4) {
                    WheelView.this.scrollingOffset = i4;
                    WheelView.this.scroller.stopScrolling();
                }
            }

            @Override // com.actionbarsherlock.widget.wheelview.WheelScroller.ScrollingListener
            public void onStarted() {
                WheelView.this.isScrollingPerformed = true;
                WheelView.this.notifyScrollingListenersAboutStart();
            }
        };
        this.dataObserver = new DataSetObserver() { // from class: com.actionbarsherlock.widget.wheelview.WheelView.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                WheelView.this.invalidateWheel(false);
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                WheelView.this.invalidateWheel(true);
            }
        };
        initData(context);
    }

    private boolean addViewItem(int i2, boolean z2) {
        View itemView = getItemView(i2);
        if (itemView == null) {
            return false;
        }
        if (z2) {
            this.itemsLayout.addView(itemView, 0);
            return true;
        }
        this.itemsLayout.addView(itemView);
        return true;
    }

    private void buildViewForMeasuring() {
        LinearLayout linearLayout = this.itemsLayout;
        if (linearLayout != null) {
            this.recycle.recycleItems(linearLayout, this.firstItem, new ItemsRange());
        } else {
            createItemsLayout();
        }
        int i2 = this.visibleItems / 2;
        for (int i3 = this.currentItem + i2; i3 >= this.currentItem - i2; i3--) {
            if (addViewItem(i3, true)) {
                this.firstItem = i3;
            }
        }
    }

    private int calculateLayoutWidth(int i2, int i3) {
        initResourcesIfNecessary();
        this.itemsLayout.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.itemsLayout.measure(View.MeasureSpec.makeMeasureSpec(i2, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        int measuredWidth = this.itemsLayout.getMeasuredWidth();
        if (i3 != 1073741824) {
            int iMax = Math.max(measuredWidth + 20, getSuggestedMinimumWidth());
            if (i3 != Integer.MIN_VALUE || i2 >= iMax) {
                i2 = iMax;
            }
        }
        this.itemsLayout.measure(View.MeasureSpec.makeMeasureSpec(i2 - 20, 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
        return i2;
    }

    private void createItemsLayout() {
        if (this.itemsLayout == null) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            this.itemsLayout = linearLayout;
            linearLayout.setOrientation(1);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doScroll(int i2) {
        this.scrollingOffset += i2;
        int itemHeight = getItemHeight();
        int i3 = this.scrollingOffset / itemHeight;
        int i4 = this.currentItem - i3;
        int itemsCount = this.viewAdapter.getItemsCount();
        int i5 = this.scrollingOffset % itemHeight;
        if (Math.abs(i5) <= itemHeight / 2) {
            i5 = 0;
        }
        if (this.isCyclic && itemsCount > 0) {
            if (i5 > 0) {
                i4--;
                i3++;
            } else if (i5 < 0) {
                i4++;
                i3--;
            }
            while (i4 < 0) {
                i4 += itemsCount;
            }
            i4 %= itemsCount;
        } else if (i4 < 0) {
            i3 = this.currentItem;
            i4 = 0;
        } else if (i4 >= itemsCount) {
            i3 = (this.currentItem - itemsCount) + 1;
            i4 = itemsCount - 1;
        } else if (i4 > 0 && i5 > 0) {
            i4--;
            i3++;
        } else if (i4 < itemsCount - 1 && i5 < 0) {
            i4++;
            i3--;
        }
        int i6 = this.scrollingOffset;
        if (i4 != this.currentItem) {
            setCurrentItem(i4, false);
        } else {
            invalidate();
        }
        int i7 = i6 - (i3 * itemHeight);
        this.scrollingOffset = i7;
        if (i7 > getHeight()) {
            this.scrollingOffset = (this.scrollingOffset % getHeight()) + getHeight();
        }
    }

    private void drawCenterRect(Canvas canvas) {
        int height = getHeight() / 2;
        int height2 = getHeight() % 2;
        int itemHeight = getItemHeight() / 2;
        int itemHeight2 = getItemHeight() % 2;
    }

    private void drawItems(Canvas canvas) {
        canvas.save();
        canvas.translate(10.0f, (-(((this.currentItem - this.firstItem) * getItemHeight()) + ((getItemHeight() - getHeight()) / 2))) + this.scrollingOffset);
        this.itemsLayout.draw(canvas);
        canvas.restore();
    }

    private int getDesiredHeight(LinearLayout linearLayout) {
        if (linearLayout != null && linearLayout.getChildAt(0) != null) {
            this.itemHeight = linearLayout.getChildAt(0).getMeasuredHeight() + 10;
        }
        int i2 = this.itemHeight;
        return Math.max((this.visibleItems * i2) - ((i2 * 0) / 50), getSuggestedMinimumHeight());
    }

    private int getItemHeight() {
        int i2 = this.itemHeight;
        if (i2 != 0) {
            return i2;
        }
        LinearLayout linearLayout = this.itemsLayout;
        if (linearLayout == null || linearLayout.getChildAt(0) == null) {
            return getHeight() / this.visibleItems;
        }
        int height = this.itemsLayout.getChildAt(0).getHeight();
        this.itemHeight = height;
        return height;
    }

    private View getItemView(int i2) {
        WheelViewAdapter wheelViewAdapter = this.viewAdapter;
        if (wheelViewAdapter == null || wheelViewAdapter.getItemsCount() == 0) {
            return null;
        }
        int itemsCount = this.viewAdapter.getItemsCount();
        if (!isValidItemIndex(i2)) {
            return this.viewAdapter.getEmptyItem(this.recycle.getEmptyItem(), this.itemsLayout);
        }
        while (i2 < 0) {
            i2 += itemsCount;
        }
        return this.viewAdapter.getItem(i2 % itemsCount, this.recycle.getItem(), this.itemsLayout);
    }

    private ItemsRange getItemsRange() {
        if (getItemHeight() == 0) {
            return null;
        }
        int i2 = this.currentItem;
        int iAsin = 1;
        while (getItemHeight() * iAsin < getHeight()) {
            i2--;
            iAsin += 2;
        }
        int i3 = this.scrollingOffset;
        if (i3 != 0) {
            if (i3 > 0) {
                i2--;
            }
            int itemHeight = i3 / getItemHeight();
            i2 -= itemHeight;
            iAsin = (int) (iAsin + 1 + Math.asin(itemHeight));
        }
        return new ItemsRange(i2, iAsin);
    }

    private void initData(Context context) {
        this.scroller = new WheelScroller(getContext(), this.scrollingListener);
    }

    private void initResourcesIfNecessary() {
        if (this.centerDrawable == null) {
            this.centerDrawable = getContext().getResources().getDrawable(this.wheelForeground);
        }
        if (this.topShadow == null) {
            this.topShadow = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, this.SHADOWS_COLORS);
        }
        if (this.bottomShadow == null) {
            this.bottomShadow = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP, this.SHADOWS_COLORS);
        }
        setBackgroundResource(this.wheelBackground);
    }

    private boolean isValidItemIndex(int i2) {
        WheelViewAdapter wheelViewAdapter = this.viewAdapter;
        return wheelViewAdapter != null && wheelViewAdapter.getItemsCount() > 0 && (this.isCyclic || (i2 >= 0 && i2 < this.viewAdapter.getItemsCount()));
    }

    private void layout(int i2, int i3) {
        this.itemsLayout.layout(0, 0, i2 - 20, i3);
    }

    private boolean rebuildItems() {
        boolean z2;
        ItemsRange itemsRange = getItemsRange();
        LinearLayout linearLayout = this.itemsLayout;
        if (linearLayout != null) {
            int iRecycleItems = this.recycle.recycleItems(linearLayout, this.firstItem, itemsRange);
            z2 = this.firstItem != iRecycleItems;
            this.firstItem = iRecycleItems;
        } else {
            createItemsLayout();
            z2 = true;
        }
        if (!z2) {
            z2 = (this.firstItem == itemsRange.getFirst() && this.itemsLayout.getChildCount() == itemsRange.getCount()) ? false : true;
        }
        if (this.firstItem <= itemsRange.getFirst() || this.firstItem > itemsRange.getLast()) {
            this.firstItem = itemsRange.getFirst();
        } else {
            for (int i2 = this.firstItem - 1; i2 >= itemsRange.getFirst() && addViewItem(i2, true); i2--) {
                this.firstItem = i2;
            }
        }
        int i3 = this.firstItem;
        for (int childCount = this.itemsLayout.getChildCount(); childCount < itemsRange.getCount(); childCount++) {
            if (!addViewItem(this.firstItem + childCount, false) && this.itemsLayout.getChildCount() == 0) {
                i3++;
            }
        }
        this.firstItem = i3;
        return z2;
    }

    private void updateView() {
        if (rebuildItems()) {
            calculateLayoutWidth(getWidth(), 1073741824);
            layout(getWidth(), getHeight());
        }
    }

    public void addChangingListener(OnWheelChangedListener onWheelChangedListener) {
        this.changingListeners.add(onWheelChangedListener);
    }

    public void addClickingListener(OnWheelClickedListener onWheelClickedListener) {
        this.clickingListeners.add(onWheelClickedListener);
    }

    public void addScrollingListener(OnWheelScrollListener onWheelScrollListener) {
        this.scrollingListeners.add(onWheelScrollListener);
    }

    public boolean drawShadows() {
        return this.drawShadows;
    }

    public int getCurrentItem() {
        return this.currentItem;
    }

    public WheelViewAdapter getViewAdapter() {
        return this.viewAdapter;
    }

    public int getVisibleItems() {
        return this.visibleItems;
    }

    public void invalidateWheel(boolean z2) {
        if (z2) {
            this.recycle.clearAll();
            LinearLayout linearLayout = this.itemsLayout;
            if (linearLayout != null) {
                linearLayout.removeAllViews();
            }
            this.scrollingOffset = 0;
        } else {
            LinearLayout linearLayout2 = this.itemsLayout;
            if (linearLayout2 != null) {
                this.recycle.recycleItems(linearLayout2, this.firstItem, new ItemsRange());
            }
        }
        invalidate();
    }

    public boolean isCyclic() {
        return this.isCyclic;
    }

    public void notifyChangingListeners(int i2, int i3) {
        Iterator<OnWheelChangedListener> it = this.changingListeners.iterator();
        while (it.hasNext()) {
            it.next().onChanged(this, i2, i3);
        }
    }

    public void notifyClickListenersAboutClick(int i2) {
        Iterator<OnWheelClickedListener> it = this.clickingListeners.iterator();
        while (it.hasNext()) {
            it.next().onItemClicked(this, i2);
        }
    }

    public void notifyScrollingListenersAboutEnd() {
        Iterator<OnWheelScrollListener> it = this.scrollingListeners.iterator();
        while (it.hasNext()) {
            it.next().onScrollingFinished(this);
        }
    }

    public void notifyScrollingListenersAboutStart() {
        Iterator<OnWheelScrollListener> it = this.scrollingListeners.iterator();
        while (it.hasNext()) {
            it.next().onScrollingStarted(this);
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        WheelViewAdapter wheelViewAdapter = this.viewAdapter;
        if (wheelViewAdapter != null && wheelViewAdapter.getItemsCount() > 0) {
            updateView();
            drawItems(canvas);
            drawCenterRect(canvas);
        }
        if (this.drawShadows) {
            drawShadows(canvas);
        }
    }

    @Override // android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        layout(i4 - i2, i5 - i3);
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        int mode = View.MeasureSpec.getMode(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        buildViewForMeasuring();
        int iCalculateLayoutWidth = calculateLayoutWidth(size, mode);
        if (mode2 != 1073741824) {
            int desiredHeight = getDesiredHeight(this.itemsLayout);
            size2 = mode2 == Integer.MIN_VALUE ? Math.min(desiredHeight, size2) : desiredHeight;
        }
        setMeasuredDimension(iCalculateLayoutWidth, size2);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isEnabled() || getViewAdapter() == null) {
            return true;
        }
        int action = motionEvent.getAction();
        if (action != 1) {
            if (action == 2 && getParent() != null) {
                getParent().requestDisallowInterceptTouchEvent(true);
            }
        } else if (!this.isScrollingPerformed) {
            int y2 = ((int) motionEvent.getY()) - (getHeight() / 2);
            int itemHeight = (y2 > 0 ? y2 + (getItemHeight() / 2) : y2 - (getItemHeight() / 2)) / getItemHeight();
            if (itemHeight != 0 && isValidItemIndex(this.currentItem + itemHeight)) {
                notifyClickListenersAboutClick(this.currentItem + itemHeight);
            }
        }
        return this.scroller.onTouchEvent(motionEvent);
    }

    public void removeChangingListener(OnWheelChangedListener onWheelChangedListener) {
        this.changingListeners.remove(onWheelChangedListener);
    }

    public void removeClickingListener(OnWheelClickedListener onWheelClickedListener) {
        this.clickingListeners.remove(onWheelClickedListener);
    }

    public void removeScrollingListener(OnWheelScrollListener onWheelScrollListener) {
        this.scrollingListeners.remove(onWheelScrollListener);
    }

    public void scroll(int i2, int i3) {
        this.scroller.scroll((i2 * getItemHeight()) - this.scrollingOffset, i3);
    }

    public void setCurrentItem(int i2, boolean z2) {
        int iMin;
        WheelViewAdapter wheelViewAdapter = this.viewAdapter;
        if (wheelViewAdapter == null || wheelViewAdapter.getItemsCount() == 0) {
            return;
        }
        int itemsCount = this.viewAdapter.getItemsCount();
        if (i2 < 0 || i2 >= itemsCount) {
            if (!this.isCyclic) {
                return;
            }
            while (i2 < 0) {
                i2 += itemsCount;
            }
            i2 %= itemsCount;
        }
        int i3 = this.currentItem;
        if (i2 != i3) {
            if (!z2) {
                this.scrollingOffset = 0;
                this.currentItem = i2;
                notifyChangingListeners(i3, i2);
                invalidate();
                return;
            }
            int i4 = i2 - i3;
            if (this.isCyclic && (iMin = (itemsCount + Math.min(i2, i3)) - Math.max(i2, this.currentItem)) < Math.abs(i4)) {
                i4 = i4 < 0 ? iMin : -iMin;
            }
            scroll(i4, 0);
        }
    }

    public void setCyclic(boolean z2) {
        this.isCyclic = z2;
        invalidateWheel(false);
    }

    public void setDrawShadows(boolean z2) {
        this.drawShadows = z2;
    }

    public void setInterpolator(Interpolator interpolator) {
        this.scroller.setInterpolator(interpolator);
    }

    public void setShadowColor(int i2, int i3, int i4) {
        this.SHADOWS_COLORS = new int[]{i2, i3, i4};
    }

    public void setViewAdapter(WheelViewAdapter wheelViewAdapter) {
        WheelViewAdapter wheelViewAdapter2 = this.viewAdapter;
        if (wheelViewAdapter2 != null) {
            wheelViewAdapter2.unregisterDataSetObserver(this.dataObserver);
        }
        this.viewAdapter = wheelViewAdapter;
        if (wheelViewAdapter != null) {
            wheelViewAdapter.registerDataSetObserver(this.dataObserver);
        }
        invalidateWheel(true);
    }

    public void setVisibleItems(int i2) {
        this.visibleItems = i2;
    }

    public void setWheelBackground(int i2) {
        this.wheelBackground = i2;
        setBackgroundResource(i2);
    }

    public void setWheelForeground(int i2) {
        this.wheelForeground = i2;
        this.centerDrawable = getContext().getResources().getDrawable(this.wheelForeground);
    }

    public void stopScrolling() {
        this.scroller.stopScrolling();
    }

    private void drawShadows(Canvas canvas) {
        int itemHeight = getItemHeight() * 3;
        this.topShadow.setBounds(0, 0, getWidth(), itemHeight);
        this.topShadow.draw(canvas);
        this.bottomShadow.setBounds(0, getHeight() - itemHeight, getWidth(), getHeight());
        this.bottomShadow.draw(canvas);
    }

    public void setCurrentItem(int i2) {
        setCurrentItem(i2, false);
    }

    public WheelView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.SHADOWS_COLORS = new int[]{11316396, 0, 11316396};
        this.currentItem = 0;
        this.visibleItems = 5;
        this.itemHeight = 0;
        this.wheelBackground = R.color.transparent;
        this.wheelForeground = R.color.transparent;
        this.drawShadows = true;
        this.isCyclic = false;
        this.recycle = new WheelRecycle(this);
        this.changingListeners = new LinkedList();
        this.scrollingListeners = new LinkedList();
        this.clickingListeners = new LinkedList();
        this.label = "";
        this.scrollingListener = new WheelScroller.ScrollingListener() { // from class: com.actionbarsherlock.widget.wheelview.WheelView.1
            @Override // com.actionbarsherlock.widget.wheelview.WheelScroller.ScrollingListener
            public void onFinished() {
                if (WheelView.this.isScrollingPerformed) {
                    WheelView.this.notifyScrollingListenersAboutEnd();
                    WheelView.this.isScrollingPerformed = false;
                }
                WheelView.this.scrollingOffset = 0;
                WheelView.this.invalidate();
            }

            @Override // com.actionbarsherlock.widget.wheelview.WheelScroller.ScrollingListener
            public void onJustify() {
                if (Math.abs(WheelView.this.scrollingOffset) > 1) {
                    WheelView.this.scroller.scroll(WheelView.this.scrollingOffset, 0);
                }
            }

            @Override // com.actionbarsherlock.widget.wheelview.WheelScroller.ScrollingListener
            public void onScroll(int i3) {
                WheelView.this.doScroll(i3);
                int height = WheelView.this.getHeight();
                if (WheelView.this.scrollingOffset > height) {
                    WheelView.this.scrollingOffset = height;
                    WheelView.this.scroller.stopScrolling();
                    return;
                }
                int i4 = -height;
                if (WheelView.this.scrollingOffset < i4) {
                    WheelView.this.scrollingOffset = i4;
                    WheelView.this.scroller.stopScrolling();
                }
            }

            @Override // com.actionbarsherlock.widget.wheelview.WheelScroller.ScrollingListener
            public void onStarted() {
                WheelView.this.isScrollingPerformed = true;
                WheelView.this.notifyScrollingListenersAboutStart();
            }
        };
        this.dataObserver = new DataSetObserver() { // from class: com.actionbarsherlock.widget.wheelview.WheelView.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                WheelView.this.invalidateWheel(false);
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                WheelView.this.invalidateWheel(true);
            }
        };
        initData(context);
    }

    public WheelView(Context context) {
        super(context);
        this.SHADOWS_COLORS = new int[]{11316396, 0, 11316396};
        this.currentItem = 0;
        this.visibleItems = 5;
        this.itemHeight = 0;
        this.wheelBackground = R.color.transparent;
        this.wheelForeground = R.color.transparent;
        this.drawShadows = true;
        this.isCyclic = false;
        this.recycle = new WheelRecycle(this);
        this.changingListeners = new LinkedList();
        this.scrollingListeners = new LinkedList();
        this.clickingListeners = new LinkedList();
        this.label = "";
        this.scrollingListener = new WheelScroller.ScrollingListener() { // from class: com.actionbarsherlock.widget.wheelview.WheelView.1
            @Override // com.actionbarsherlock.widget.wheelview.WheelScroller.ScrollingListener
            public void onFinished() {
                if (WheelView.this.isScrollingPerformed) {
                    WheelView.this.notifyScrollingListenersAboutEnd();
                    WheelView.this.isScrollingPerformed = false;
                }
                WheelView.this.scrollingOffset = 0;
                WheelView.this.invalidate();
            }

            @Override // com.actionbarsherlock.widget.wheelview.WheelScroller.ScrollingListener
            public void onJustify() {
                if (Math.abs(WheelView.this.scrollingOffset) > 1) {
                    WheelView.this.scroller.scroll(WheelView.this.scrollingOffset, 0);
                }
            }

            @Override // com.actionbarsherlock.widget.wheelview.WheelScroller.ScrollingListener
            public void onScroll(int i3) {
                WheelView.this.doScroll(i3);
                int height = WheelView.this.getHeight();
                if (WheelView.this.scrollingOffset > height) {
                    WheelView.this.scrollingOffset = height;
                    WheelView.this.scroller.stopScrolling();
                    return;
                }
                int i4 = -height;
                if (WheelView.this.scrollingOffset < i4) {
                    WheelView.this.scrollingOffset = i4;
                    WheelView.this.scroller.stopScrolling();
                }
            }

            @Override // com.actionbarsherlock.widget.wheelview.WheelScroller.ScrollingListener
            public void onStarted() {
                WheelView.this.isScrollingPerformed = true;
                WheelView.this.notifyScrollingListenersAboutStart();
            }
        };
        this.dataObserver = new DataSetObserver() { // from class: com.actionbarsherlock.widget.wheelview.WheelView.2
            @Override // android.database.DataSetObserver
            public void onChanged() {
                WheelView.this.invalidateWheel(false);
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
                WheelView.this.invalidateWheel(true);
            }
        };
        initData(context);
    }
}
