package com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import com.easefun.polyv.livecommon.R;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVNavigatorHelper;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVMeasurablePagerTitleView;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerIndicator;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.IPLVPagerTitleView;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.abs.PLVCommonNavigatorAdapter;
import com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.model.PLVPositionData;
import com.plv.foundationsdk.log.PLVCommonLog;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PLVCommonNavigator extends FrameLayout implements IPLVPagerNavigator, PLVNavigatorHelper.OnNavigatorScrollListener {
    private static final String TAG = "PLVCommonNavigator";
    private PLVCommonNavigatorAdapter mAdapter;
    private boolean mAdjustMode;
    private boolean mEnablePivotScroll;
    private boolean mFollowTouch;
    private IPLVPagerIndicator mIndicator;
    private LinearLayout mIndicatorContainer;
    private boolean mIndicatorOnTop;
    private int mLeftPadding;
    private PLVNavigatorHelper mNavigatorHelper;
    private DataSetObserver mObserver;
    private List<PLVPositionData> mPositionDataList;
    private boolean mReselectWhenLayout;
    private int mRightPadding;
    private float mScrollPivotX;
    private HorizontalScrollView mScrollView;
    private boolean mSkimOver;
    private boolean mSmoothScroll;
    private LinearLayout mTitleContainer;

    public PLVCommonNavigator(Context context) {
        super(context);
        this.mScrollPivotX = 0.5f;
        this.mSmoothScroll = true;
        this.mFollowTouch = true;
        this.mReselectWhenLayout = true;
        this.mPositionDataList = new ArrayList();
        this.mObserver = new DataSetObserver() { // from class: com.easefun.polyv.livecommon.ui.widget.magicindicator.buildins.commonnavigator.PLVCommonNavigator.1
            @Override // android.database.DataSetObserver
            public void onChanged() {
                PLVCommonNavigator.this.mNavigatorHelper.setTotalCount(PLVCommonNavigator.this.mAdapter.getCount());
                PLVCommonNavigator.this.init();
            }

            @Override // android.database.DataSetObserver
            public void onInvalidated() {
            }
        };
        PLVNavigatorHelper pLVNavigatorHelper = new PLVNavigatorHelper();
        this.mNavigatorHelper = pLVNavigatorHelper;
        pLVNavigatorHelper.setNavigatorScrollListener(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void init() {
        removeAllViews();
        View viewInflate = this.mAdjustMode ? LayoutInflater.from(getContext()).inflate(R.layout.plv_pager_navigator_layout_no_scroll, this) : LayoutInflater.from(getContext()).inflate(R.layout.plv_pager_navigator_layout, this);
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
                    layoutParams.weight = this.mAdapter.getTitleWeight(getContext(), i2);
                } else {
                    layoutParams = new LinearLayout.LayoutParams(-2, -1);
                    layoutParams.weight = 1.0f;
                }
                this.mTitleContainer.addView(view, layoutParams);
            }
        }
        PLVCommonNavigatorAdapter pLVCommonNavigatorAdapter = this.mAdapter;
        if (pLVCommonNavigatorAdapter != null) {
            IPLVPagerIndicator indicator = pLVCommonNavigatorAdapter.getIndicator(getContext());
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
            PLVPositionData pLVPositionData = new PLVPositionData();
            View childAt = this.mTitleContainer.getChildAt(i2);
            if (childAt != 0) {
                pLVPositionData.setLeft(childAt.getLeft());
                pLVPositionData.setTop(childAt.getTop());
                pLVPositionData.setRight(childAt.getRight());
                pLVPositionData.setBottom(childAt.getBottom());
                if (childAt instanceof IPLVMeasurablePagerTitleView) {
                    IPLVMeasurablePagerTitleView iPLVMeasurablePagerTitleView = (IPLVMeasurablePagerTitleView) childAt;
                    pLVPositionData.setContentLeft(iPLVMeasurablePagerTitleView.getContentLeft());
                    pLVPositionData.setContentTop(iPLVMeasurablePagerTitleView.getContentTop());
                    pLVPositionData.setContentRight(iPLVMeasurablePagerTitleView.getContentRight());
                    pLVPositionData.setContentBottom(iPLVMeasurablePagerTitleView.getContentBottom());
                } else {
                    pLVPositionData.setContentLeft(pLVPositionData.getLeft());
                    pLVPositionData.setContentTop(pLVPositionData.getTop());
                    pLVPositionData.setContentRight(pLVPositionData.getRight());
                    pLVPositionData.setContentBottom(pLVPositionData.getBottom());
                }
            }
            this.mPositionDataList.add(pLVPositionData);
        }
    }

    public PLVCommonNavigatorAdapter getAdapter() {
        return this.mAdapter;
    }

    public int getLeftPadding() {
        return this.mLeftPadding;
    }

    public IPLVPagerIndicator getPagerIndicator() {
        return this.mIndicator;
    }

    public IPLVPagerTitleView getPagerTitleView(int index) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return null;
        }
        return (IPLVPagerTitleView) linearLayout.getChildAt(index);
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

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator
    public void notifyDataSetChanged() {
        PLVCommonNavigatorAdapter pLVCommonNavigatorAdapter = this.mAdapter;
        if (pLVCommonNavigatorAdapter != null) {
            pLVCommonNavigatorAdapter.notifyDataSetChanged();
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator
    public void onAttachToMagicIndicator() {
        init();
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVNavigatorHelper.OnNavigatorScrollListener
    public void onDeselected(int index, int totalCount) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return;
        }
        KeyEvent.Callback childAt = linearLayout.getChildAt(index);
        if (childAt instanceof IPLVPagerTitleView) {
            ((IPLVPagerTitleView) childAt).onDeselected(index, totalCount);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator
    public void onDetachFromMagicIndicator() {
        PLVCommonLog.d(TAG, "onDetachFromMagicIndicator");
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVNavigatorHelper.OnNavigatorScrollListener
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return;
        }
        KeyEvent.Callback childAt = linearLayout.getChildAt(index);
        if (childAt instanceof IPLVPagerTitleView) {
            ((IPLVPagerTitleView) childAt).onEnter(index, totalCount, enterPercent, leftToRight);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int left, int top2, int right, int bottom) {
        super.onLayout(changed, left, top2, right, bottom);
        if (this.mAdapter != null) {
            preparePositionData();
            IPLVPagerIndicator iPLVPagerIndicator = this.mIndicator;
            if (iPLVPagerIndicator != null) {
                iPLVPagerIndicator.onPositionDataProvide(this.mPositionDataList);
            }
            if (this.mReselectWhenLayout && this.mNavigatorHelper.getScrollState() == 0) {
                onPageSelected(this.mNavigatorHelper.getCurrentIndex());
                onPageScrolled(this.mNavigatorHelper.getCurrentIndex(), 0.0f, 0);
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVNavigatorHelper.OnNavigatorScrollListener
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return;
        }
        KeyEvent.Callback childAt = linearLayout.getChildAt(index);
        if (childAt instanceof IPLVPagerTitleView) {
            ((IPLVPagerTitleView) childAt).onLeave(index, totalCount, leavePercent, leftToRight);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator
    public void onPageScrollStateChanged(int state) {
        if (this.mAdapter != null) {
            this.mNavigatorHelper.onPageScrollStateChanged(state);
            IPLVPagerIndicator iPLVPagerIndicator = this.mIndicator;
            if (iPLVPagerIndicator != null) {
                iPLVPagerIndicator.onPageScrollStateChanged(state);
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (this.mAdapter != null) {
            this.mNavigatorHelper.onPageScrolled(position, positionOffset, positionOffsetPixels);
            IPLVPagerIndicator iPLVPagerIndicator = this.mIndicator;
            if (iPLVPagerIndicator != null) {
                iPLVPagerIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            if (this.mScrollView == null || this.mPositionDataList.isEmpty() || position < 0 || position >= this.mPositionDataList.size() || !this.mFollowTouch) {
                return;
            }
            int iMin = Math.min(this.mPositionDataList.size() - 1, position);
            int iMin2 = Math.min(this.mPositionDataList.size() - 1, position + 1);
            PLVPositionData pLVPositionData = this.mPositionDataList.get(iMin);
            PLVPositionData pLVPositionData2 = this.mPositionDataList.get(iMin2);
            float fHorizontalCenter = pLVPositionData.horizontalCenter() - (this.mScrollView.getWidth() * this.mScrollPivotX);
            this.mScrollView.scrollTo((int) (fHorizontalCenter + (((pLVPositionData2.horizontalCenter() - (this.mScrollView.getWidth() * this.mScrollPivotX)) - fHorizontalCenter) * positionOffset)), 0);
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.abs.IPLVPagerNavigator
    public void onPageSelected(int position) {
        if (this.mAdapter != null) {
            this.mNavigatorHelper.onPageSelected(position);
            IPLVPagerIndicator iPLVPagerIndicator = this.mIndicator;
            if (iPLVPagerIndicator != null) {
                iPLVPagerIndicator.onPageSelected(position);
            }
        }
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.magicindicator.PLVNavigatorHelper.OnNavigatorScrollListener
    public void onSelected(int index, int totalCount) {
        LinearLayout linearLayout = this.mTitleContainer;
        if (linearLayout == null) {
            return;
        }
        KeyEvent.Callback childAt = linearLayout.getChildAt(index);
        if (childAt instanceof IPLVPagerTitleView) {
            ((IPLVPagerTitleView) childAt).onSelected(index, totalCount);
        }
        if (this.mAdjustMode || this.mFollowTouch || this.mScrollView == null || this.mPositionDataList.isEmpty()) {
            return;
        }
        PLVPositionData pLVPositionData = this.mPositionDataList.get(Math.min(this.mPositionDataList.size() - 1, index));
        if (this.mEnablePivotScroll) {
            float fHorizontalCenter = pLVPositionData.horizontalCenter() - (this.mScrollView.getWidth() * this.mScrollPivotX);
            if (this.mSmoothScroll) {
                this.mScrollView.smoothScrollTo((int) fHorizontalCenter, 0);
                return;
            } else {
                this.mScrollView.scrollTo((int) fHorizontalCenter, 0);
                return;
            }
        }
        if (this.mScrollView.getScrollX() > pLVPositionData.getLeft()) {
            if (this.mSmoothScroll) {
                this.mScrollView.smoothScrollTo(pLVPositionData.getLeft(), 0);
                return;
            } else {
                this.mScrollView.scrollTo(pLVPositionData.getLeft(), 0);
                return;
            }
        }
        if (this.mScrollView.getScrollX() + getWidth() < pLVPositionData.getRight()) {
            if (this.mSmoothScroll) {
                this.mScrollView.smoothScrollTo(pLVPositionData.getRight() - getWidth(), 0);
            } else {
                this.mScrollView.scrollTo(pLVPositionData.getRight() - getWidth(), 0);
            }
        }
    }

    public void setAdapter(PLVCommonNavigatorAdapter adapter) {
        PLVCommonNavigatorAdapter pLVCommonNavigatorAdapter = this.mAdapter;
        if (pLVCommonNavigatorAdapter == adapter) {
            return;
        }
        if (pLVCommonNavigatorAdapter != null) {
            pLVCommonNavigatorAdapter.unregisterDataSetObserver(this.mObserver);
        }
        this.mAdapter = adapter;
        if (adapter == null) {
            this.mNavigatorHelper.setTotalCount(0);
            init();
            return;
        }
        adapter.registerDataSetObserver(this.mObserver);
        this.mNavigatorHelper.setTotalCount(this.mAdapter.getCount());
        if (this.mTitleContainer != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public void setAdjustMode(boolean is) {
        this.mAdjustMode = is;
    }

    public void setEnablePivotScroll(boolean is) {
        this.mEnablePivotScroll = is;
    }

    public void setFollowTouch(boolean followTouch) {
        this.mFollowTouch = followTouch;
    }

    public void setIndicatorOnTop(boolean indicatorOnTop) {
        this.mIndicatorOnTop = indicatorOnTop;
    }

    public void setLeftPadding(int leftPadding) {
        this.mLeftPadding = leftPadding;
    }

    public void setReselectWhenLayout(boolean reselectWhenLayout) {
        this.mReselectWhenLayout = reselectWhenLayout;
    }

    public void setRightPadding(int rightPadding) {
        this.mRightPadding = rightPadding;
    }

    public void setScrollPivotX(float scrollPivotX) {
        this.mScrollPivotX = scrollPivotX;
    }

    public void setSkimOver(boolean skimOver) {
        this.mSkimOver = skimOver;
        this.mNavigatorHelper.setSkimOver(skimOver);
    }

    public void setSmoothScroll(boolean smoothScroll) {
        this.mSmoothScroll = smoothScroll;
    }
}
