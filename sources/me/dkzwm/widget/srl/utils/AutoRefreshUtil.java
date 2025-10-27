package me.dkzwm.widget.srl.utils;

import android.view.View;
import android.view.ViewConfiguration;
import androidx.annotation.NonNull;
import me.dkzwm.widget.srl.ILifecycleObserver;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.indicator.IIndicator;

/* loaded from: classes9.dex */
public class AutoRefreshUtil implements ILifecycleObserver, SmoothRefreshLayout.OnNestedScrollChangedListener, SmoothRefreshLayout.OnUIPositionChangedListener {
    private int mMaximumFlingVelocity;
    private SmoothRefreshLayout mRefreshLayout;
    private int mStatus;
    private View mTargetView;
    private boolean mNeedToTriggerRefresh = false;
    private boolean mNeedToTriggerLoadMore = false;
    private boolean mCachedActionAtOnce = false;
    private boolean mCachedAutoRefreshUseSmoothScroll = false;

    public AutoRefreshUtil(@NonNull View view) {
        this.mTargetView = view;
        this.mMaximumFlingVelocity = ViewConfiguration.get(view.getContext()).getScaledMaximumFlingVelocity();
    }

    public void autoLoadMore(boolean z2, boolean z3) {
        SmoothRefreshLayout smoothRefreshLayout = this.mRefreshLayout;
        if (smoothRefreshLayout == null || this.mStatus != 1) {
            return;
        }
        if (!smoothRefreshLayout.isNotYetInEdgeCannotMoveFooter()) {
            this.mRefreshLayout.autoLoadMore(z2, z3);
            this.mNeedToTriggerLoadMore = false;
            this.mCachedActionAtOnce = false;
            this.mCachedAutoRefreshUseSmoothScroll = false;
            return;
        }
        if (this.mRefreshLayout.getSupportScrollAxis() == 2) {
            ScrollCompat.flingCompat(this.mTargetView, this.mMaximumFlingVelocity);
        } else if (this.mRefreshLayout.getSupportScrollAxis() == 1) {
            HorizontalScrollCompat.flingCompat(this.mTargetView, this.mMaximumFlingVelocity);
        }
        this.mNeedToTriggerLoadMore = true;
        this.mCachedActionAtOnce = z2;
        this.mCachedAutoRefreshUseSmoothScroll = z3;
    }

    public void autoRefresh(boolean z2, boolean z3) {
        SmoothRefreshLayout smoothRefreshLayout = this.mRefreshLayout;
        if (smoothRefreshLayout == null || this.mStatus != 1) {
            return;
        }
        if (!smoothRefreshLayout.isNotYetInEdgeCannotMoveHeader()) {
            this.mRefreshLayout.autoRefresh(z2, z3);
            this.mNeedToTriggerRefresh = false;
            this.mCachedActionAtOnce = false;
            this.mCachedAutoRefreshUseSmoothScroll = false;
            return;
        }
        if (this.mRefreshLayout.getSupportScrollAxis() == 2) {
            ScrollCompat.flingCompat(this.mTargetView, -this.mMaximumFlingVelocity);
        } else if (this.mRefreshLayout.getSupportScrollAxis() == 1) {
            HorizontalScrollCompat.flingCompat(this.mTargetView, -this.mMaximumFlingVelocity);
        }
        this.mNeedToTriggerRefresh = true;
        this.mCachedActionAtOnce = z2;
        this.mCachedAutoRefreshUseSmoothScroll = z3;
    }

    @Override // me.dkzwm.widget.srl.ILifecycleObserver
    public void onAttached(SmoothRefreshLayout smoothRefreshLayout) {
        this.mRefreshLayout = smoothRefreshLayout;
        smoothRefreshLayout.addOnUIPositionChangedListener(this);
        this.mRefreshLayout.addOnNestedScrollChangedListener(this);
    }

    @Override // me.dkzwm.widget.srl.SmoothRefreshLayout.OnUIPositionChangedListener
    public void onChanged(byte b3, IIndicator iIndicator) {
        this.mStatus = b3;
    }

    @Override // me.dkzwm.widget.srl.ILifecycleObserver
    public void onDetached(SmoothRefreshLayout smoothRefreshLayout) {
        this.mRefreshLayout.removeOnUIPositionChangedListener(this);
        this.mRefreshLayout.removeOnNestedScrollChangedListener(this);
        this.mRefreshLayout = null;
    }

    @Override // me.dkzwm.widget.srl.SmoothRefreshLayout.OnNestedScrollChangedListener
    public void onNestedScrollChanged() {
        SmoothRefreshLayout smoothRefreshLayout = this.mRefreshLayout;
        if (smoothRefreshLayout != null) {
            if (this.mNeedToTriggerRefresh && !smoothRefreshLayout.isNotYetInEdgeCannotMoveHeader()) {
                if (this.mRefreshLayout.autoRefresh(this.mCachedActionAtOnce, this.mCachedAutoRefreshUseSmoothScroll)) {
                    ScrollCompat.stopFling(this.mTargetView);
                    this.mNeedToTriggerRefresh = false;
                    this.mCachedActionAtOnce = false;
                    this.mCachedAutoRefreshUseSmoothScroll = false;
                    return;
                }
                return;
            }
            if (this.mNeedToTriggerLoadMore && !this.mRefreshLayout.isNotYetInEdgeCannotMoveFooter() && this.mRefreshLayout.autoLoadMore(this.mCachedActionAtOnce, this.mCachedAutoRefreshUseSmoothScroll)) {
                ScrollCompat.stopFling(this.mTargetView);
                this.mNeedToTriggerLoadMore = false;
                this.mCachedActionAtOnce = false;
                this.mCachedAutoRefreshUseSmoothScroll = false;
            }
        }
    }
}
