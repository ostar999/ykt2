package com.catchpig.mvvm.widget.refresh;

import com.plv.socket.event.linkmic.PLVUpdateMicSiteEvent;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0006H\u0016J\u0010\u0010\t\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\nH&¨\u0006\u000b"}, d2 = {"Lcom/catchpig/mvvm/widget/refresh/OnRefreshListener;", "Lcom/scwang/smart/refresh/layout/listener/OnRefreshLoadMoreListener;", "()V", "onLoadMore", "", "refreshLayout", "Lcom/scwang/smart/refresh/layout/api/RefreshLayout;", "onRefresh", "refreshlayout", PLVUpdateMicSiteEvent.EVENT_NAME, "Lcom/catchpig/mvvm/widget/refresh/RefreshLayoutWrapper;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public abstract class OnRefreshListener implements OnRefreshLoadMoreListener {
    @Override // com.scwang.smart.refresh.layout.listener.OnLoadMoreListener
    public void onLoadMore(@NotNull RefreshLayout refreshLayout) {
        Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
        RefreshLayoutWrapper refreshLayoutWrapper = (RefreshLayoutWrapper) refreshLayout;
        refreshLayoutWrapper.loadNextPageIndex();
        refreshLayout.setEnableRefresh(false);
        update(refreshLayoutWrapper);
    }

    @Override // com.scwang.smart.refresh.layout.listener.OnRefreshListener
    public void onRefresh(@NotNull RefreshLayout refreshlayout) {
        Intrinsics.checkNotNullParameter(refreshlayout, "refreshlayout");
        RefreshLayoutWrapper refreshLayoutWrapper = (RefreshLayoutWrapper) refreshlayout;
        refreshLayoutWrapper.resetPageIndex();
        refreshlayout.setEnableLoadMore(false);
        update(refreshLayoutWrapper);
    }

    public abstract void update(@NotNull RefreshLayoutWrapper refreshLayout);
}
