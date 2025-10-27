package me.dkzwm.widget.srl.utils;

import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.AppBarLayout;
import me.dkzwm.widget.srl.ILifecycleObserver;
import me.dkzwm.widget.srl.SmoothRefreshLayout;
import me.dkzwm.widget.srl.extra.IRefreshView;

/* loaded from: classes9.dex */
public class AppBarUtil implements ILifecycleObserver, SmoothRefreshLayout.OnHeaderEdgeDetectCallBack, SmoothRefreshLayout.OnFooterEdgeDetectCallBack {
    private boolean mFound = false;
    private boolean mFullyCollapsed;
    private boolean mFullyExpanded;
    private AppBarLayout.OnOffsetChangedListener mListener;

    private AppBarLayout findAppBarLayout(ViewGroup viewGroup) {
        AppBarLayout appBarLayoutFindAppBarLayout;
        int i2 = 0;
        if (viewGroup instanceof CoordinatorLayout) {
            CoordinatorLayout coordinatorLayout = (CoordinatorLayout) viewGroup;
            int childCount = coordinatorLayout.getChildCount();
            while (i2 < childCount) {
                View childAt = coordinatorLayout.getChildAt(i2);
                if (childAt instanceof AppBarLayout) {
                    return (AppBarLayout) childAt;
                }
                i2++;
            }
            return null;
        }
        int childCount2 = viewGroup.getChildCount();
        while (i2 < childCount2) {
            View childAt2 = viewGroup.getChildAt(i2);
            if (childAt2 instanceof CoordinatorLayout) {
                return findAppBarLayout((ViewGroup) childAt2);
            }
            if ((childAt2 instanceof ViewGroup) && (appBarLayoutFindAppBarLayout = findAppBarLayout((ViewGroup) childAt2)) != null) {
                return appBarLayoutFindAppBarLayout;
            }
            i2++;
        }
        return null;
    }

    private ViewGroup findRootViewGroup(ViewGroup viewGroup) {
        if (viewGroup.getParent() == null || !(viewGroup.getParent() instanceof ViewGroup)) {
            return null;
        }
        return (ViewGroup) viewGroup.getParent();
    }

    public boolean hasFound() {
        return this.mFound;
    }

    @Override // me.dkzwm.widget.srl.SmoothRefreshLayout.OnFooterEdgeDetectCallBack
    public boolean isNotYetInEdgeCannotMoveFooter(SmoothRefreshLayout smoothRefreshLayout, @Nullable View view, @Nullable IRefreshView iRefreshView) {
        View scrollTargetView = smoothRefreshLayout.getScrollTargetView();
        if (scrollTargetView != null) {
            view = scrollTargetView;
        }
        return !this.mFullyCollapsed || ScrollCompat.canChildScrollDown(view);
    }

    @Override // me.dkzwm.widget.srl.SmoothRefreshLayout.OnHeaderEdgeDetectCallBack
    public boolean isNotYetInEdgeCannotMoveHeader(SmoothRefreshLayout smoothRefreshLayout, @Nullable View view, @Nullable IRefreshView iRefreshView) {
        View scrollTargetView = smoothRefreshLayout.getScrollTargetView();
        if (scrollTargetView != null) {
            view = scrollTargetView;
        }
        return !this.mFullyExpanded || ScrollCompat.canChildScrollUp(view);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v6, types: [android.view.ViewGroup] */
    @Override // me.dkzwm.widget.srl.ILifecycleObserver
    public void onAttached(SmoothRefreshLayout smoothRefreshLayout) {
        SmoothRefreshLayout smoothRefreshLayout2 = smoothRefreshLayout;
        while (true) {
            if (smoothRefreshLayout == null) {
                smoothRefreshLayout = smoothRefreshLayout2;
                break;
            }
            try {
                if (smoothRefreshLayout instanceof CoordinatorLayout) {
                    break;
                }
                smoothRefreshLayout2 = smoothRefreshLayout;
                smoothRefreshLayout = findRootViewGroup(smoothRefreshLayout);
            } catch (Exception unused) {
                this.mFound = false;
                return;
            }
        }
        AppBarLayout appBarLayoutFindAppBarLayout = findAppBarLayout(smoothRefreshLayout);
        if (appBarLayoutFindAppBarLayout == null) {
            return;
        }
        if (this.mListener == null) {
            this.mListener = new AppBarLayout.OnOffsetChangedListener() { // from class: me.dkzwm.widget.srl.utils.AppBarUtil.1
                @Override // com.google.android.material.appbar.AppBarLayout.OnOffsetChangedListener, com.google.android.material.appbar.AppBarLayout.BaseOnOffsetChangedListener
                public void onOffsetChanged(AppBarLayout appBarLayout, int i2) {
                    AppBarUtil.this.mFullyExpanded = i2 >= 0;
                    AppBarUtil.this.mFullyCollapsed = appBarLayout.getTotalScrollRange() + i2 <= 0;
                }
            };
        }
        appBarLayoutFindAppBarLayout.addOnOffsetChangedListener(this.mListener);
        this.mFound = true;
    }

    @Override // me.dkzwm.widget.srl.ILifecycleObserver
    public void onDetached(SmoothRefreshLayout smoothRefreshLayout) {
        AppBarLayout appBarLayoutFindAppBarLayout;
        try {
            appBarLayoutFindAppBarLayout = findAppBarLayout(smoothRefreshLayout);
        } catch (Exception unused) {
        }
        if (appBarLayoutFindAppBarLayout == null) {
            return;
        }
        AppBarLayout.OnOffsetChangedListener onOffsetChangedListener = this.mListener;
        if (onOffsetChangedListener != null) {
            appBarLayoutFindAppBarLayout.removeOnOffsetChangedListener(onOffsetChangedListener);
        }
        this.mFound = false;
    }
}
