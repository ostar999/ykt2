package com.scwang.smart.refresh.layout.api;

import android.animation.ValueAnimator;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import com.scwang.smart.refresh.layout.listener.ScrollBoundaryDecider;

/* loaded from: classes6.dex */
public interface RefreshContent {
    boolean canLoadMore();

    boolean canRefresh();

    @NonNull
    View getScrollableView();

    @NonNull
    View getView();

    void moveSpinner(int i2, int i3, int i4);

    void onActionDown(MotionEvent motionEvent);

    ValueAnimator.AnimatorUpdateListener scrollContentWhenFinished(int i2);

    void setEnableLoadMoreWhenContentNotFull(boolean z2);

    void setScrollBoundaryDecider(ScrollBoundaryDecider scrollBoundaryDecider);

    void setUpComponent(RefreshKernel refreshKernel, View view, View view2);
}
