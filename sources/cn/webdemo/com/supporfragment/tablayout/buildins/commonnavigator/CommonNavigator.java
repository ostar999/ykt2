package cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import cn.webdemo.com.supporfragment.R;
import cn.webdemo.com.supporfragment.tablayout.NavigatorHelper;
import cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IMeasurablePagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerIndicator;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.abs.IPagerTitleView;
import cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.model.PositionData;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CommonNavigator extends FrameLayout implements IPagerNavigator, NavigatorHelper.OnNavigatorScrollListener {
    private CommonNavigatorAdapter mAdapter;
    private boolean mAdjustMode;
    private boolean mEnablePivotScroll;
    private boolean mFollowTouch;
    private IPagerIndicator mIndicator;
    private LinearLayout mIndicatorContainer;
    private boolean mIndicatorOnTop;
    private int mLeftPadding;
    private NavigatorHelper mNavigatorHelper;
    private DataSetObserver mObserver;
    private List<PositionData> mPositionDataList;
    private boolean mReselectWhenLayout;
    private int mRightPadding;
    private float mScrollPivotX;
    private HorizontalScrollView mScrollView;
    private boolean mSkimOver;
    private boolean mSmoothScroll;
    private LinearLayout mTitleContainer;
    private List<Float> weights;

    public CommonNavigator(Context context) {
        super(context);
        this.mScrollPivotX = 0.5f;
        this.mSmoothScroll = true;
        this.mFollowTouch = true;
        this.mReselectWhenLayout = true;
        this.weights = new ArrayList();
        this.mPositionDataList = new ArrayList();
        this.mObserver = new DataSetObserver() { // from class: cn.webdemo.com.supporfragment.tablayout.buildins.commonnavigator.CommonNavigator.1
            @Override // android.database.DataSetObserver
            public void onChanged() {
                CommonNavigator.this.mNavigatorHelper.setTotalCount(CommonNavigator.this.mAdapter.getCount());
                CommonNavigator.this.init();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
            }
        };
        NavigatorHelper navigatorHelper = new NavigatorHelper();
        this.mNavigatorHelper = navigatorHelper;
        navigatorHelper.setNavigatorScrollListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        removeAllViews();
        View viewInflate = this.mAdjustMode ? LayoutInflater.from(getContext()).inflate(R.layout.pager_navigator_layout_no_scroll, this) : LayoutInflater.from(getContext()).inflate(R.layout.pager_navigator_layout, this);
        this.mScrollView = (HorizontalScrollView) viewInflate.findViewById(R.id.scroll_view);
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.title_container);
        this.mTitleContainer = linearLayout;
        linearLayout.setPadding(this.mLeftPadding, 0, this.mRightPadding, 0);
        LinearLayout linearLayout2 = (LinearLayout) viewInflate.findViewById(R.id.indicator_container);
        this.mIndicatorContainer = linearLayout2;
        if (this.mIndicatorOnTop) {
            linearLayout2.getParent().bringChildToFront(this.mIndicatorContainer);
        }
        initTitlesAndIndicator();
    }

    private void initTitlesAndIndicator() {
        LinearLayout.LayoutParams layoutParams;
        int totalCount = this.mNavigatorHelper.getTotalCount();
        for (int i2 = 0; i2 < totalCount; i2++) {
            Object titleView = this.mAdapter.getTitleView(getContext(), i2);
            if (titleView instanceof View) {
                View view = (View) titleView;
                if (this.mAdjustMode) {
                    layoutParams = new LinearLayout.LayoutParams(0, -1);
                    if (this.weights.size() == this.mNavigatorHelper.getTotalCount()) {
                        layoutParams.weight = this.weights.get(i2).floatValue();
                    } else {
                        layoutParams.weight = this.mAdapter.getTitleWeight(getContext(), i2);
                    }
                } else {
                    layoutParams = new LinearLayout.LayoutParams(-2, -1);
                }
                this.mTitleContainer.addView(view, layoutParams);
            }
        }
        CommonNavigatorAdapter commonNavigatorAdapter = this.mAdapter;
        if (commonNavigatorAdapter != null) {
            IPagerIndicator indicator = commonNavigatorAdapter.getIndicator(getContext());
            this.mIndicator = indicator;
            if (indicator instanceof View) {
                this.mIndicatorContainer.addView((View) this.mIndicator, new FrameLayout.LayoutParams(-1, -1));
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void preparePositionData() {
        this.mPositionDataList.clear();
        int totalCount = this.mNavigatorHelper.getTotalCount();
        for (int i2 = 0; i2 < totalCount; i2++) {
            PositionData positionData = new PositionData();
            View childAt = this.mTitleContainer.getChildAt(i2);
            if (childAt != 0) {
                positionData.mLeft = childAt.getLeft();
                positionData.mTop = childAt.getTop();
                positionData.mRight = childAt.getRight();
                int bottom = childAt.getBottom();
                positionData.mBottom = bottom;
                if (childAt instanceof IMeasurablePagerTitleView) {
                    IMeasurablePagerTitleView iMeasurablePagerTitleView = (IMeasurablePagerTitleView) childAt;
                    positionData.mContentLeft = iMeasurablePagerTitleView.getContentLeft();
                    positionData.mContentTop = iMeasurablePagerTitleView.getContentTop();
                    positionData.mContentRight = iMeasurablePagerTitleView.getContentRight();
                    positionData.mContentBottom = iMeasurablePagerTitleView.getContentBottom();
                } else {
                    positionData.mContentLeft = positionData.mLeft;
                    positionData.mContentTop = positionData.mTop;
                    positionData.mContentRight = positionData.mRight;
                    positionData.mContentBottom = bottom;
                }
            }
            this.mPositionDataList.add(positionData);
        }
    }

    public CommonNavigatorAdapter getAdapter() {
        return this.mAdapter;
    }

    public int getLeftPadding() {
        return this.mLeftPadding;
    }

    public IPagerIndicator getPagerIndicator() {
        return this.mIndicator;
    }

    public IPagerTitleView getPagerTitleView(int i2) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return null;
        }
        return (IPagerTitleView) linearLayout.getChildAt(i2);
    }

    public int getRightPadding() {
        return this.mRightPadding;
    }

    public float getScrollPivotX() {
        return this.mScrollPivotX;
    }

    public LinearLayout getTitleContainer() {
        return this.mTitleContainer;
    }

    public List<Float> getWeights() {
        return this.weights;
    }

    public boolean isAdjustMode() {
        return this.mAdjustMode;
    }

    public boolean isEnablePivotScroll() {
        return this.mEnablePivotScroll;
    }

    public boolean isFollowTouch() {
        return this.mFollowTouch;
    }

    public boolean isIndicatorOnTop() {
        return this.mIndicatorOnTop;
    }

    public boolean isReselectWhenLayout() {
        return this.mReselectWhenLayout;
    }

    public boolean isSkimOver() {
        return this.mSkimOver;
    }

    public boolean isSmoothScroll() {
        return this.mSmoothScroll;
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator
    public void notifyDataSetChanged() {
        CommonNavigatorAdapter commonNavigatorAdapter = this.mAdapter;
        if (commonNavigatorAdapter != null) {
            commonNavigatorAdapter.notifyDataSetChanged();
        }
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator
    public void onAttachToMagicIndicator() {
        init();
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.NavigatorHelper.OnNavigatorScrollListener
    public void onDeselected(int i2, int i3) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return;
        }
        KeyEvent.Callback childAt = linearLayout.getChildAt(i2);
        if (childAt instanceof IPagerTitleView) {
            ((IPagerTitleView) childAt).onDeselected(i2, i3);
        }
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator
    public void onDetachFromMagicIndicator() {
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.NavigatorHelper.OnNavigatorScrollListener
    public void onEnter(int i2, int i3, float f2, boolean z2) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return;
        }
        KeyEvent.Callback childAt = linearLayout.getChildAt(i2);
        if (childAt instanceof IPagerTitleView) {
            ((IPagerTitleView) childAt).onEnter(i2, i3, f2, z2);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z2, int i2, int i3, int i4, int i5) {
        super.onLayout(z2, i2, i3, i4, i5);
        if (this.mAdapter != null) {
            preparePositionData();
            IPagerIndicator iPagerIndicator = this.mIndicator;
            if (iPagerIndicator != null) {
                iPagerIndicator.onPositionDataProvide(this.mPositionDataList);
            }
            if (this.mReselectWhenLayout && this.mNavigatorHelper.getScrollState() == 0) {
                onPageSelected(this.mNavigatorHelper.getCurrentIndex());
                onPageScrolled(this.mNavigatorHelper.getCurrentIndex(), 0.0f, 0);
            }
        }
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.NavigatorHelper.OnNavigatorScrollListener
    public void onLeave(int i2, int i3, float f2, boolean z2) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return;
        }
        KeyEvent.Callback childAt = linearLayout.getChildAt(i2);
        if (childAt instanceof IPagerTitleView) {
            ((IPagerTitleView) childAt).onLeave(i2, i3, f2, z2);
        }
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator
    public void onPageScrollStateChanged(int i2) {
        if (this.mAdapter != null) {
            this.mNavigatorHelper.onPageScrollStateChanged(i2);
            IPagerIndicator iPagerIndicator = this.mIndicator;
            if (iPagerIndicator != null) {
                iPagerIndicator.onPageScrollStateChanged(i2);
            }
        }
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator
    public void onPageScrolled(int i2, float f2, int i3) {
        if (this.mAdapter != null) {
            this.mNavigatorHelper.onPageScrolled(i2, f2, i3);
            IPagerIndicator iPagerIndicator = this.mIndicator;
            if (iPagerIndicator != null) {
                iPagerIndicator.onPageScrolled(i2, f2, i3);
            }
            if (this.mScrollView == null || this.mPositionDataList.size() <= 0 || i2 < 0 || i2 >= this.mPositionDataList.size() || !this.mFollowTouch) {
                return;
            }
            int iMin = Math.min(this.mPositionDataList.size() - 1, i2);
            int iMin2 = Math.min(this.mPositionDataList.size() - 1, i2 + 1);
            PositionData positionData = this.mPositionDataList.get(iMin);
            PositionData positionData2 = this.mPositionDataList.get(iMin2);
            float fHorizontalCenter = positionData.horizontalCenter() - (this.mScrollView.getWidth() * this.mScrollPivotX);
            this.mScrollView.scrollTo((int) (fHorizontalCenter + (((positionData2.horizontalCenter() - (this.mScrollView.getWidth() * this.mScrollPivotX)) - fHorizontalCenter) * f2)), 0);
        }
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.abs.IPagerNavigator
    public void onPageSelected(int i2) {
        if (this.mAdapter != null) {
            this.mNavigatorHelper.onPageSelected(i2);
            IPagerIndicator iPagerIndicator = this.mIndicator;
            if (iPagerIndicator != null) {
                iPagerIndicator.onPageSelected(i2);
            }
        }
    }

    @Override // cn.webdemo.com.supporfragment.tablayout.NavigatorHelper.OnNavigatorScrollListener
    public void onSelected(int i2, int i3) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return;
        }
        KeyEvent.Callback childAt = linearLayout.getChildAt(i2);
        if (childAt instanceof IPagerTitleView) {
            ((IPagerTitleView) childAt).onSelected(i2, i3);
        }
        if (this.mAdjustMode || this.mFollowTouch || this.mScrollView == null || this.mPositionDataList.size() <= 0) {
            return;
        }
        PositionData positionData = this.mPositionDataList.get(Math.min(this.mPositionDataList.size() - 1, i2));
        if (this.mEnablePivotScroll) {
            float fHorizontalCenter = positionData.horizontalCenter() - (this.mScrollView.getWidth() * this.mScrollPivotX);
            if (this.mSmoothScroll) {
                this.mScrollView.smoothScrollTo((int) fHorizontalCenter, 0);
                return;
            } else {
                this.mScrollView.scrollTo((int) fHorizontalCenter, 0);
                return;
            }
        }
        int scrollX = this.mScrollView.getScrollX();
        int i4 = positionData.mLeft;
        if (scrollX > i4) {
            if (this.mSmoothScroll) {
                this.mScrollView.smoothScrollTo(i4, 0);
                return;
            } else {
                this.mScrollView.scrollTo(i4, 0);
                return;
            }
        }
        int scrollX2 = this.mScrollView.getScrollX() + getWidth();
        int i5 = positionData.mRight;
        if (scrollX2 < i5) {
            if (this.mSmoothScroll) {
                this.mScrollView.smoothScrollTo(i5 - getWidth(), 0);
            } else {
                this.mScrollView.scrollTo(i5 - getWidth(), 0);
            }
        }
    }

    public void setAdapter(CommonNavigatorAdapter commonNavigatorAdapter) {
        CommonNavigatorAdapter commonNavigatorAdapter2 = this.mAdapter;
        if (commonNavigatorAdapter2 == commonNavigatorAdapter) {
            return;
        }
        if (commonNavigatorAdapter2 != null) {
            commonNavigatorAdapter2.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = commonNavigatorAdapter;
        if (commonNavigatorAdapter == null) {
            this.mNavigatorHelper.setTotalCount(0);
            init();
            return;
        }
        commonNavigatorAdapter.registerDataSetObserver(this.mObserver);
        this.mNavigatorHelper.setTotalCount(this.mAdapter.getCount());
        if (this.mTitleContainer != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public void setAdjustMode(boolean z2) {
        this.mAdjustMode = z2;
    }

    public void setEnablePivotScroll(boolean z2) {
        this.mEnablePivotScroll = z2;
    }

    public void setFollowTouch(boolean z2) {
        this.mFollowTouch = z2;
    }

    public void setIndicatorOnTop(boolean z2) {
        this.mIndicatorOnTop = z2;
    }

    public void setLeftPadding(int i2) {
        this.mLeftPadding = i2;
    }

    public void setReselectWhenLayout(boolean z2) {
        this.mReselectWhenLayout = z2;
    }

    public void setRightPadding(int i2) {
        this.mRightPadding = i2;
    }

    public void setScrollPivotX(float f2) {
        this.mScrollPivotX = f2;
    }

    public void setSkimOver(boolean z2) {
        this.mSkimOver = z2;
        this.mNavigatorHelper.setSkimOver(z2);
    }

    public void setSmoothScroll(boolean z2) {
        this.mSmoothScroll = z2;
    }

    public void setWeights(List<Float> list) {
        this.weights = list;
    }
}
