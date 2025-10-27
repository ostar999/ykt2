package com.scwang.smartrefresh.layout.listener;

import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;

/* loaded from: classes6.dex */
public interface OnMultiPurposeListener extends OnRefreshLoadMoreListener, OnStateChangedListener {
    void onFooterFinish(RefreshFooter refreshFooter, boolean z2);

    void onFooterMoving(RefreshFooter refreshFooter, boolean z2, float f2, int i2, int i3, int i4);

    void onFooterReleased(RefreshFooter refreshFooter, int i2, int i3);

    void onFooterStartAnimator(RefreshFooter refreshFooter, int i2, int i3);

    void onHeaderFinish(RefreshHeader refreshHeader, boolean z2);

    void onHeaderMoving(RefreshHeader refreshHeader, boolean z2, float f2, int i2, int i3, int i4);

    void onHeaderReleased(RefreshHeader refreshHeader, int i2, int i3);

    void onHeaderStartAnimator(RefreshHeader refreshHeader, int i2, int i3);
}
