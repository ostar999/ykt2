package com.catchpig.mvvm.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;
import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.catchpig.mvvm.ksp.KotlinMvvmCompiler;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.umeng.analytics.pro.d;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\b\u0002\b\u0016\u0018\u0000 $2\u00020\u00012\u00020\u0002:\u0001$B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u000eH\u0016J0\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0014\u001a\u00020\t2\u0006\u0010\u0015\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\t2\u0006\u0010\u0017\u001a\u00020\tH\u0014J\b\u0010\u0018\u001a\u00020\u000eH\u0016J)\u0010\u0019\u001a\u00020\u000e2!\u0010\u001a\u001a\u001d\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u001c\u0012\b\b\u001d\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u000e0\u001bJ>\u0010\u0019\u001a\u00020\u000e26\u0010\u001a\u001a2\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u001c\u0012\b\b\u001d\u0012\u0004\b\b(\n\u0012\u0013\u0012\u00110\t¢\u0006\f\b\u001c\u0012\b\b\u001d\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\u000e0\u001eJ\b\u0010\u001f\u001a\u00020\u000eH\u0002J\b\u0010 \u001a\u00020\u000eH\u0016J\u0016\u0010!\u001a\u00020\u000e2\f\u0010\"\u001a\b\u0012\u0002\b\u0003\u0018\u00010#H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\tX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lcom/catchpig/mvvm/widget/refresh/RefreshLayoutWrapper;", "Lcom/scwang/smart/refresh/layout/SmartRefreshLayout;", "Lcom/catchpig/mvvm/widget/refresh/IPageControl;", d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "currentPageIndex", "", "nextPageIndex", ServiceCommon.RequestKey.FORM_KEY_PAGE_SIZE, "startPageIndex", "finishUpdate", "", "success", "", "loadNextPageIndex", "onLayout", "changed", NotifyType.LIGHTS, "t", "r", "b", "resetPageIndex", "setOnRefreshLoadMoreListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlin/Function2;", "updateCurrentPageIndex", "updateError", "updateSuccess", "list", "", "Companion", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public class RefreshLayoutWrapper extends SmartRefreshLayout implements IPageControl {
    public static final int NONE_PRE_PAGE_INDEX = -1;

    @NotNull
    public static final String SIMPLE_NAME_MATERIAL_HEADER = "MaterialHeader";
    private int currentPageIndex;
    private int nextPageIndex;
    private final int pageSize;
    private final int startPageIndex;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RefreshLayoutWrapper(@NotNull Context context, @NotNull AttributeSet attrs) {
        super(context, attrs);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
        setEnableLoadMore(false);
        KotlinMvvmCompiler kotlinMvvmCompiler = KotlinMvvmCompiler.INSTANCE;
        int startPageIndex = kotlinMvvmCompiler.globalConfig().getStartPageIndex();
        this.startPageIndex = startPageIndex;
        this.currentPageIndex = startPageIndex;
        this.nextPageIndex = -1;
        this.pageSize = kotlinMvvmCompiler.globalConfig().getPageSize();
    }

    private final void finishUpdate(boolean success) {
        if (isRefreshing()) {
            finishRefresh(success);
        } else if (isLoading()) {
            finishLoadMore(success);
        }
    }

    private final void updateCurrentPageIndex() {
        this.currentPageIndex = this.nextPageIndex;
        this.nextPageIndex = -1;
    }

    @Override // com.catchpig.mvvm.widget.refresh.IPageControl
    public void loadNextPageIndex() {
        this.nextPageIndex = this.currentPageIndex + 1;
    }

    @Override // com.scwang.smart.refresh.layout.SmartRefreshLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean changed, int l2, int t2, int r2, int b3) {
        super.onLayout(changed, l2, t2, r2, b3);
        RefreshHeader refreshHeader = getRefreshHeader();
        if (refreshHeader == null || !Intrinsics.areEqual(SIMPLE_NAME_MATERIAL_HEADER, refreshHeader.getView().getClass().getSimpleName())) {
            return;
        }
        refreshHeader.getView().setBackgroundColor(0);
    }

    @Override // com.catchpig.mvvm.widget.refresh.IPageControl
    public void resetPageIndex() {
        this.nextPageIndex = this.startPageIndex;
    }

    public final void setOnRefreshLoadMoreListener(@NotNull final Function1<? super Integer, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        setOnRefreshLoadMoreListener(new OnRefreshListener() { // from class: com.catchpig.mvvm.widget.refresh.RefreshLayoutWrapper.setOnRefreshLoadMoreListener.1
            @Override // com.catchpig.mvvm.widget.refresh.OnRefreshListener
            public void update(@NotNull RefreshLayoutWrapper refreshLayout) {
                Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
                listener.invoke(Integer.valueOf(refreshLayout.nextPageIndex));
            }
        });
    }

    @Override // com.catchpig.mvvm.widget.refresh.IPageControl
    public void updateError() {
        this.nextPageIndex = -1;
        finishUpdate(false);
    }

    @Override // com.catchpig.mvvm.widget.refresh.IPageControl
    public void updateSuccess(@Nullable List<?> list) {
        if (list != null && list.size() >= this.pageSize) {
            setEnableLoadMore(true);
        } else {
            setEnableLoadMore(false);
        }
        setEnableRefresh(true);
        updateCurrentPageIndex();
        finishUpdate(true);
    }

    public final void setOnRefreshLoadMoreListener(@NotNull final Function2<? super Integer, ? super Integer, Unit> listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        setOnRefreshLoadMoreListener(new OnRefreshListener() { // from class: com.catchpig.mvvm.widget.refresh.RefreshLayoutWrapper.setOnRefreshLoadMoreListener.2
            @Override // com.catchpig.mvvm.widget.refresh.OnRefreshListener
            public void update(@NotNull RefreshLayoutWrapper refreshLayout) {
                Intrinsics.checkNotNullParameter(refreshLayout, "refreshLayout");
                listener.invoke(Integer.valueOf(refreshLayout.nextPageIndex), Integer.valueOf(refreshLayout.pageSize));
            }
        });
    }
}
