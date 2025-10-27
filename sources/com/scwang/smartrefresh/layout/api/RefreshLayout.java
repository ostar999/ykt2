package com.scwang.smartrefresh.layout.api;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

/* loaded from: classes6.dex */
public interface RefreshLayout {
    boolean autoLoadMore();

    boolean autoLoadMore(int i2, int i3, float f2, boolean z2);

    boolean autoLoadMoreAnimationOnly();

    boolean autoRefresh();

    boolean autoRefresh(int i2);

    boolean autoRefresh(int i2, int i3, float f2, boolean z2);

    boolean autoRefreshAnimationOnly();

    RefreshLayout closeHeaderOrFooter();

    RefreshLayout finishLoadMore();

    RefreshLayout finishLoadMore(int i2);

    RefreshLayout finishLoadMore(int i2, boolean z2, boolean z3);

    RefreshLayout finishLoadMore(boolean z2);

    RefreshLayout finishLoadMoreWithNoMoreData();

    RefreshLayout finishRefresh();

    RefreshLayout finishRefresh(int i2);

    RefreshLayout finishRefresh(int i2, boolean z2, Boolean bool);

    RefreshLayout finishRefresh(boolean z2);

    RefreshLayout finishRefreshWithNoMoreData();

    @NonNull
    ViewGroup getLayout();

    @Nullable
    RefreshFooter getRefreshFooter();

    @Nullable
    RefreshHeader getRefreshHeader();

    @NonNull
    RefreshState getState();

    RefreshLayout resetNoMoreData();

    RefreshLayout setDisableContentWhenLoading(boolean z2);

    RefreshLayout setDisableContentWhenRefresh(boolean z2);

    RefreshLayout setDragRate(@FloatRange(from = 0.0d, to = 1.0d) float f2);

    RefreshLayout setEnableAutoLoadMore(boolean z2);

    RefreshLayout setEnableClipFooterWhenFixedBehind(boolean z2);

    RefreshLayout setEnableClipHeaderWhenFixedBehind(boolean z2);

    @Deprecated
    RefreshLayout setEnableFooterFollowWhenLoadFinished(boolean z2);

    RefreshLayout setEnableFooterFollowWhenNoMoreData(boolean z2);

    RefreshLayout setEnableFooterTranslationContent(boolean z2);

    RefreshLayout setEnableHeaderTranslationContent(boolean z2);

    RefreshLayout setEnableLoadMore(boolean z2);

    RefreshLayout setEnableLoadMoreWhenContentNotFull(boolean z2);

    RefreshLayout setEnableNestedScroll(boolean z2);

    RefreshLayout setEnableOverScrollBounce(boolean z2);

    RefreshLayout setEnableOverScrollDrag(boolean z2);

    RefreshLayout setEnablePureScrollMode(boolean z2);

    RefreshLayout setEnableRefresh(boolean z2);

    RefreshLayout setEnableScrollContentWhenLoaded(boolean z2);

    RefreshLayout setEnableScrollContentWhenRefreshed(boolean z2);

    RefreshLayout setFooterHeight(float f2);

    RefreshLayout setFooterInsetStart(float f2);

    RefreshLayout setFooterMaxDragRate(@FloatRange(from = 1.0d, to = 10.0d) float f2);

    RefreshLayout setFooterTriggerRate(@FloatRange(from = 0.0d, to = 1.0d) float f2);

    RefreshLayout setHeaderHeight(float f2);

    RefreshLayout setHeaderInsetStart(float f2);

    RefreshLayout setHeaderMaxDragRate(@FloatRange(from = 1.0d, to = 10.0d) float f2);

    RefreshLayout setHeaderTriggerRate(@FloatRange(from = 0.0d, to = 1.0d) float f2);

    RefreshLayout setNoMoreData(boolean z2);

    RefreshLayout setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener);

    RefreshLayout setOnMultiPurposeListener(OnMultiPurposeListener onMultiPurposeListener);

    RefreshLayout setOnRefreshListener(OnRefreshListener onRefreshListener);

    RefreshLayout setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener onRefreshLoadMoreListener);

    RefreshLayout setPrimaryColors(@ColorInt int... iArr);

    RefreshLayout setPrimaryColorsId(@ColorRes int... iArr);

    RefreshLayout setReboundDuration(int i2);

    RefreshLayout setReboundInterpolator(@NonNull Interpolator interpolator);

    RefreshLayout setRefreshContent(@NonNull View view);

    RefreshLayout setRefreshContent(@NonNull View view, int i2, int i3);

    RefreshLayout setRefreshFooter(@NonNull RefreshFooter refreshFooter);

    RefreshLayout setRefreshFooter(@NonNull RefreshFooter refreshFooter, int i2, int i3);

    RefreshLayout setRefreshHeader(@NonNull RefreshHeader refreshHeader);

    RefreshLayout setRefreshHeader(@NonNull RefreshHeader refreshHeader, int i2, int i3);

    RefreshLayout setScrollBoundaryDecider(ScrollBoundaryDecider scrollBoundaryDecider);
}
