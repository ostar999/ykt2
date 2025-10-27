package com.scwang.smart.refresh.layout.wrapper;

import android.animation.ValueAnimator;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.Space;
import androidx.annotation.NonNull;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingParent;
import androidx.viewpager.widget.ViewPager;
import com.scwang.smart.refresh.layout.api.RefreshContent;
import com.scwang.smart.refresh.layout.api.RefreshKernel;
import com.scwang.smart.refresh.layout.kernel.R;
import com.scwang.smart.refresh.layout.listener.CoordinatorLayoutListener;
import com.scwang.smart.refresh.layout.listener.ScrollBoundaryDecider;
import com.scwang.smart.refresh.layout.simple.SimpleBoundaryDecider;
import com.scwang.smart.refresh.layout.util.DesignUtil;
import com.scwang.smart.refresh.layout.util.SmartUtil;
import java.util.LinkedList;

/* loaded from: classes6.dex */
public class RefreshContentWrapper implements RefreshContent, CoordinatorLayoutListener, ValueAnimator.AnimatorUpdateListener {
    protected View mContentView;
    protected View mFixedFooter;
    protected View mFixedHeader;
    protected View mOriginalContentView;
    protected View mScrollableView;
    protected int mLastSpinner = 0;
    protected boolean mEnableRefresh = true;
    protected boolean mEnableLoadMore = true;
    protected SimpleBoundaryDecider mBoundaryAdapter = new SimpleBoundaryDecider();

    public RefreshContentWrapper(@NonNull View view) {
        this.mScrollableView = view;
        this.mOriginalContentView = view;
        this.mContentView = view;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshContent
    public boolean canLoadMore() {
        return this.mEnableLoadMore && this.mBoundaryAdapter.canLoadMore(this.mContentView);
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshContent
    public boolean canRefresh() {
        return this.mEnableRefresh && this.mBoundaryAdapter.canRefresh(this.mContentView);
    }

    public void findScrollableView(View view, RefreshKernel refreshKernel) {
        boolean zIsInEditMode = this.mContentView.isInEditMode();
        View view2 = null;
        while (true) {
            if (view2 != null && (!(view2 instanceof NestedScrollingParent) || (view2 instanceof NestedScrollingChild))) {
                break;
            }
            view = findScrollableViewInternal(view, view2 == null);
            if (view == view2) {
                break;
            }
            if (!zIsInEditMode) {
                DesignUtil.checkCoordinatorLayout(view, refreshKernel, this);
            }
            view2 = view;
        }
        if (view2 != null) {
            this.mScrollableView = view2;
        }
    }

    public View findScrollableViewByPoint(View view, PointF pointF, View view2) {
        if ((view instanceof ViewGroup) && pointF != null) {
            ViewGroup viewGroup = (ViewGroup) view;
            PointF pointF2 = new PointF();
            for (int childCount = viewGroup.getChildCount(); childCount > 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount - 1);
                if (SmartUtil.isTransformedTouchPointInView(viewGroup, childAt, pointF.x, pointF.y, pointF2)) {
                    if (!(childAt instanceof ViewPager) && SmartUtil.isContentView(childAt)) {
                        return childAt;
                    }
                    pointF.offset(pointF2.x, pointF2.y);
                    View viewFindScrollableViewByPoint = findScrollableViewByPoint(childAt, pointF, view2);
                    pointF.offset(-pointF2.x, -pointF2.y);
                    return viewFindScrollableViewByPoint;
                }
            }
        }
        return view2;
    }

    public View findScrollableViewInternal(View view, boolean z2) {
        LinkedList linkedList = new LinkedList();
        linkedList.add(view);
        View view2 = null;
        while (linkedList.size() > 0 && view2 == null) {
            View view3 = (View) linkedList.poll();
            if (view3 != null) {
                if ((z2 || view3 != view) && SmartUtil.isContentView(view3)) {
                    view2 = view3;
                } else if (view3 instanceof ViewGroup) {
                    ViewGroup viewGroup = (ViewGroup) view3;
                    for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
                        linkedList.add(viewGroup.getChildAt(i2));
                    }
                }
            }
        }
        return view2 == null ? view : view2;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshContent
    @NonNull
    public View getScrollableView() {
        return this.mScrollableView;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshContent
    @NonNull
    public View getView() {
        return this.mContentView;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshContent
    public void moveSpinner(int i2, int i3, int i4) {
        boolean z2;
        View viewFindViewById;
        View viewFindViewById2;
        boolean z3 = true;
        if (i3 == -1 || (viewFindViewById2 = this.mOriginalContentView.findViewById(i3)) == null) {
            z2 = false;
        } else if (i2 > 0) {
            viewFindViewById2.setTranslationY(i2);
            z2 = true;
        } else {
            if (viewFindViewById2.getTranslationY() > 0.0f) {
                viewFindViewById2.setTranslationY(0.0f);
            }
            z2 = false;
        }
        if (i4 == -1 || (viewFindViewById = this.mOriginalContentView.findViewById(i4)) == null) {
            z3 = z2;
        } else if (i2 < 0) {
            viewFindViewById.setTranslationY(i2);
        } else {
            if (viewFindViewById.getTranslationY() < 0.0f) {
                viewFindViewById.setTranslationY(0.0f);
            }
            z3 = z2;
        }
        if (z3) {
            this.mOriginalContentView.setTranslationY(0.0f);
        } else {
            this.mOriginalContentView.setTranslationY(i2);
        }
        View view = this.mFixedHeader;
        if (view != null) {
            view.setTranslationY(Math.max(0, i2));
        }
        View view2 = this.mFixedFooter;
        if (view2 != null) {
            view2.setTranslationY(Math.min(0, i2));
        }
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshContent
    public void onActionDown(MotionEvent motionEvent) {
        PointF pointF = new PointF(motionEvent.getX(), motionEvent.getY());
        pointF.offset(-this.mContentView.getLeft(), -this.mContentView.getTop());
        View view = this.mScrollableView;
        View view2 = this.mContentView;
        if (view != view2) {
            this.mScrollableView = findScrollableViewByPoint(view2, pointF, view);
        }
        if (this.mScrollableView == this.mContentView) {
            this.mBoundaryAdapter.mActionEvent = null;
        } else {
            this.mBoundaryAdapter.mActionEvent = pointF;
        }
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        try {
            float scaleY = (iIntValue - this.mLastSpinner) * this.mScrollableView.getScaleY();
            View view = this.mScrollableView;
            if (view instanceof AbsListView) {
                SmartUtil.scrollListBy((AbsListView) view, (int) scaleY);
            } else {
                view.scrollBy(0, (int) scaleY);
            }
        } catch (Throwable th) {
            th.printStackTrace();
        }
        this.mLastSpinner = iIntValue;
    }

    @Override // com.scwang.smart.refresh.layout.listener.CoordinatorLayoutListener
    public void onCoordinatorUpdate(boolean z2, boolean z3) {
        this.mEnableRefresh = z2;
        this.mEnableLoadMore = z3;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshContent
    public ValueAnimator.AnimatorUpdateListener scrollContentWhenFinished(int i2) {
        View view = this.mScrollableView;
        if (view == null || i2 == 0) {
            return null;
        }
        if ((i2 >= 0 || !view.canScrollVertically(1)) && (i2 <= 0 || !this.mScrollableView.canScrollVertically(-1))) {
            return null;
        }
        this.mLastSpinner = i2;
        return this;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshContent
    public void setEnableLoadMoreWhenContentNotFull(boolean z2) {
        this.mBoundaryAdapter.mEnableLoadMoreWhenContentNotFull = z2;
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshContent
    public void setScrollBoundaryDecider(ScrollBoundaryDecider scrollBoundaryDecider) {
        if (scrollBoundaryDecider instanceof SimpleBoundaryDecider) {
            this.mBoundaryAdapter = (SimpleBoundaryDecider) scrollBoundaryDecider;
        } else {
            this.mBoundaryAdapter.boundary = scrollBoundaryDecider;
        }
    }

    @Override // com.scwang.smart.refresh.layout.api.RefreshContent
    public void setUpComponent(RefreshKernel refreshKernel, View view, View view2) {
        findScrollableView(this.mContentView, refreshKernel);
        if (view == null && view2 == null) {
            return;
        }
        this.mFixedHeader = view;
        this.mFixedFooter = view2;
        FrameLayout frameLayout = new FrameLayout(this.mContentView.getContext());
        int iIndexOfChild = refreshKernel.getRefreshLayout().getLayout().indexOfChild(this.mContentView);
        refreshKernel.getRefreshLayout().getLayout().removeView(this.mContentView);
        frameLayout.addView(this.mContentView, 0, new ViewGroup.LayoutParams(-1, -1));
        refreshKernel.getRefreshLayout().getLayout().addView(frameLayout, iIndexOfChild, this.mContentView.getLayoutParams());
        this.mContentView = frameLayout;
        if (view != null) {
            view.setTag(R.id.srl_tag, "fixed-top");
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            int iIndexOfChild2 = viewGroup.indexOfChild(view);
            viewGroup.removeView(view);
            layoutParams.height = SmartUtil.measureViewHeight(view);
            viewGroup.addView(new Space(this.mContentView.getContext()), iIndexOfChild2, layoutParams);
            frameLayout.addView(view, 1, layoutParams);
        }
        if (view2 != null) {
            view2.setTag(R.id.srl_tag, "fixed-bottom");
            ViewGroup.LayoutParams layoutParams2 = view2.getLayoutParams();
            ViewGroup viewGroup2 = (ViewGroup) view2.getParent();
            int iIndexOfChild3 = viewGroup2.indexOfChild(view2);
            viewGroup2.removeView(view2);
            FrameLayout.LayoutParams layoutParams3 = new FrameLayout.LayoutParams(layoutParams2);
            layoutParams2.height = SmartUtil.measureViewHeight(view2);
            viewGroup2.addView(new Space(this.mContentView.getContext()), iIndexOfChild3, layoutParams2);
            layoutParams3.gravity = 80;
            frameLayout.addView(view2, 1, layoutParams3);
        }
    }
}
