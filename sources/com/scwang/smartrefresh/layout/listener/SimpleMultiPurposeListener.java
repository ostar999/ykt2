package com.scwang.smartrefresh.layout.listener;

import androidx.annotation.NonNull;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;

/* loaded from: classes6.dex */
public class SimpleMultiPurposeListener implements OnMultiPurposeListener {
    @Override // com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener
    public void onFooterFinish(RefreshFooter refreshFooter, boolean z2) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener
    public void onFooterMoving(RefreshFooter refreshFooter, boolean z2, float f2, int i2, int i3, int i4) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener
    public void onFooterReleased(RefreshFooter refreshFooter, int i2, int i3) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener
    public void onFooterStartAnimator(RefreshFooter refreshFooter, int i2, int i3) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener
    public void onHeaderFinish(RefreshHeader refreshHeader, boolean z2) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener
    public void onHeaderMoving(RefreshHeader refreshHeader, boolean z2, float f2, int i2, int i3, int i4) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener
    public void onHeaderReleased(RefreshHeader refreshHeader, int i2, int i3) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnMultiPurposeListener
    public void onHeaderStartAnimator(RefreshHeader refreshHeader, int i2, int i3) {
    }

    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
    }

    @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
    }

    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
    }
}
