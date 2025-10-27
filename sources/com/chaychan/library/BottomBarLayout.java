package com.chaychan.library;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class BottomBarLayout extends LinearLayout implements ViewPager.OnPageChangeListener {
    private int mCurrentItem;
    private List<BottomBarItem> mItemViews;
    private boolean mSmoothScroll;
    private ViewPager mViewPager;
    private OnItemSelectedListener onItemSelectedListener;

    public class MyOnClickListener implements View.OnClickListener {
        private int currentIndex;

        public MyOnClickListener(int i2) {
            this.currentIndex = i2;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) throws Resources.NotFoundException {
            if (BottomBarLayout.this.mViewPager == null) {
                if (BottomBarLayout.this.onItemSelectedListener != null) {
                    BottomBarLayout.this.onItemSelectedListener.onItemSelected(BottomBarLayout.this.getBottomItem(this.currentIndex), BottomBarLayout.this.mCurrentItem, this.currentIndex);
                }
                BottomBarLayout.this.updateTabState(this.currentIndex);
            } else if (this.currentIndex != BottomBarLayout.this.mCurrentItem) {
                BottomBarLayout.this.mViewPager.setCurrentItem(this.currentIndex, BottomBarLayout.this.mSmoothScroll);
            } else if (BottomBarLayout.this.onItemSelectedListener != null) {
                BottomBarLayout.this.onItemSelectedListener.onItemSelected(BottomBarLayout.this.getBottomItem(this.currentIndex), BottomBarLayout.this.mCurrentItem, this.currentIndex);
            }
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(BottomBarItem bottomBarItem, int i2, int i3);
    }

    public BottomBarLayout(Context context) {
        this(context, null);
    }

    private void init() {
        this.mItemViews.clear();
        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            if (!(getChildAt(i2) instanceof BottomBarItem)) {
                throw new IllegalArgumentException("BottomBarLayout的子View必须是BottomBarItem");
            }
            BottomBarItem bottomBarItem = (BottomBarItem) getChildAt(i2);
            this.mItemViews.add(bottomBarItem);
            bottomBarItem.setOnClickListener(new MyOnClickListener(i2));
        }
        if (this.mCurrentItem < this.mItemViews.size()) {
            this.mItemViews.get(this.mCurrentItem).refreshTab(true);
        }
    }

    private void resetState() {
        if (this.mCurrentItem >= this.mItemViews.size() || !this.mItemViews.get(this.mCurrentItem).isSelected()) {
            return;
        }
        this.mItemViews.get(this.mCurrentItem).refreshTab(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTabState(int i2) {
        resetState();
        this.mCurrentItem = i2;
        this.mItemViews.get(i2).refreshTab(true);
    }

    public void addItem(BottomBarItem bottomBarItem) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.weight = 1.0f;
        bottomBarItem.setLayoutParams(layoutParams);
        addView(bottomBarItem);
        init();
    }

    public BottomBarItem getBottomItem(int i2) {
        return this.mItemViews.get(i2);
    }

    public int getCurrentItem() {
        return this.mCurrentItem;
    }

    public void hideMsg(int i2) {
        this.mItemViews.get(i2).hideMsg();
    }

    public void hideNotify(int i2) {
        this.mItemViews.get(i2).hideNotify();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrollStateChanged(int i2) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageScrolled(int i2, float f2, int i3) {
    }

    @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
    public void onPageSelected(int i2) {
        resetState();
        this.mItemViews.get(i2).refreshTab(true);
        OnItemSelectedListener onItemSelectedListener = this.onItemSelectedListener;
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(getBottomItem(i2), this.mCurrentItem, i2);
        }
        this.mCurrentItem = i2;
    }

    public void removeItem(int i2) {
        if (i2 < 0 || i2 >= this.mItemViews.size()) {
            return;
        }
        if (this.mItemViews.contains(this.mItemViews.get(i2))) {
            resetState();
            removeViewAt(i2);
            init();
        }
    }

    public void setCurrentItem(int i2) throws Resources.NotFoundException {
        ViewPager viewPager = this.mViewPager;
        if (viewPager != null) {
            viewPager.setCurrentItem(i2, this.mSmoothScroll);
            return;
        }
        OnItemSelectedListener onItemSelectedListener = this.onItemSelectedListener;
        if (onItemSelectedListener != null) {
            onItemSelectedListener.onItemSelected(getBottomItem(i2), this.mCurrentItem, i2);
        }
        updateTabState(i2);
    }

    public void setMsg(int i2, String str) {
        this.mItemViews.get(i2).setMsg(str);
    }

    public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
        this.onItemSelectedListener = onItemSelectedListener;
    }

    @Override // android.widget.LinearLayout
    public void setOrientation(int i2) {
        super.setOrientation(i2);
    }

    public void setSmoothScroll(boolean z2) {
        this.mSmoothScroll = z2;
    }

    public void setUnread(int i2, int i3) {
        this.mItemViews.get(i2).setUnreadNum(i3);
    }

    public void setViewPager(ViewPager viewPager) {
        PagerAdapter adapter;
        this.mViewPager = viewPager;
        if (viewPager != null && (adapter = viewPager.getAdapter()) != null && adapter.getCount() != getChildCount()) {
            throw new IllegalArgumentException("LinearLayout的子View数量必须和ViewPager条目数量一致");
        }
        ViewPager viewPager2 = this.mViewPager;
        if (viewPager2 != null) {
            viewPager2.setOnPageChangeListener(this);
        }
    }

    public void showNotify(int i2) {
        this.mItemViews.get(i2).showNotify();
    }

    public BottomBarLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BottomBarLayout(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mItemViews = new ArrayList();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BottomBarLayout);
        this.mSmoothScroll = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BottomBarLayout_smoothScroll, false);
        typedArrayObtainStyledAttributes.recycle();
    }
}
