package com.ykb.ebook.adapter.base.loadState.trailing;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.ykb.ebook.adapter.base.loadState.LoadState;
import com.ykb.ebook.adapter.base.loadState.LoadStateAdapter;
import com.ykb.ebook.adapter.base.loadState.trailing.TrailingLoadStateAdapter;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0015\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001-B\u000f\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0006\u0010\u0016\u001a\u00020\u0017J\u001d\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u0011H\u0000¢\u0006\u0002\b\u001bJ\u0010\u0010\u001c\u001a\u00020\u00052\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\u0012\u0010\u001f\u001a\u00020\u00112\b\u0010 \u001a\u0004\u0018\u00010!H\u0002J\u0006\u0010\"\u001a\u00020\u0017J\u0006\u0010#\u001a\u00020\u0017J\b\u0010$\u001a\u00020\u0005H\u0002J\b\u0010%\u001a\u00020\u0017H\u0002J\u0015\u0010&\u001a\u00020\u00172\u0006\u0010'\u001a\u00028\u0000H\u0017¢\u0006\u0002\u0010(J\u0016\u0010)\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\u0010*\u001a\u0004\u0018\u00010\fJ\b\u0010+\u001a\u00020,H\u0016R\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\bR\u000e\u0010\n\u001a\u00020\u0005X\u0082\u000e¢\u0006\u0002\n\u0000R\"\u0010\r\u001a\u0004\u0018\u00010\f2\b\u0010\u000b\u001a\u0004\u0018\u00010\f@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001a\u0010\u0010\u001a\u00020\u0011X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015¨\u0006."}, d2 = {"Lcom/ykb/ebook/adapter/base/loadState/trailing/TrailingLoadStateAdapter;", "VH", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Lcom/ykb/ebook/adapter/base/loadState/LoadStateAdapter;", "isLoadEndDisplay", "", "(Z)V", "isAutoLoadMore", "()Z", "setAutoLoadMore", "mNextLoadEnable", "<set-?>", "Lcom/ykb/ebook/adapter/base/loadState/trailing/TrailingLoadStateAdapter$OnTrailingListener;", "onTrailingListener", "getOnTrailingListener", "()Lcom/ykb/ebook/adapter/base/loadState/trailing/TrailingLoadStateAdapter$OnTrailingListener;", "preloadSize", "", "getPreloadSize", "()I", "setPreloadSize", "(I)V", "checkDisableLoadMoreIfNotFullPage", "", "checkPreload", "itemCount", "currentPosition", "checkPreload$ebook_release", "displayLoadStateAsItem", "loadState", "Lcom/ykb/ebook/adapter/base/loadState/LoadState;", "getTheBiggestNumber", "numbers", "", "invokeFailRetry", "invokeLoadMore", "isFullScreen", "loadAction", "onViewAttachedToWindow", "holder", "(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;)V", "setOnLoadMoreListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "toString", "", "OnTrailingListener", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public abstract class TrailingLoadStateAdapter<VH extends RecyclerView.ViewHolder> extends LoadStateAdapter<VH> {
    private boolean isAutoLoadMore;
    private final boolean isLoadEndDisplay;
    private boolean mNextLoadEnable;

    @Nullable
    private OnTrailingListener onTrailingListener;
    private int preloadSize;

    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\b\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0005H&¨\u0006\u0007"}, d2 = {"Lcom/ykb/ebook/adapter/base/loadState/trailing/TrailingLoadStateAdapter$OnTrailingListener;", "", "isAllowLoading", "", "onFailRetry", "", "onLoad", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnTrailingListener {

        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        public static final class DefaultImpls {
            public static boolean isAllowLoading(@NotNull OnTrailingListener onTrailingListener) {
                return true;
            }
        }

        boolean isAllowLoading();

        void onFailRetry();

        void onLoad();
    }

    public TrailingLoadStateAdapter() {
        this(false, 1, null);
    }

    public /* synthetic */ TrailingLoadStateAdapter(boolean z2, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? true : z2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkDisableLoadMoreIfNotFullPage$lambda$1(TrailingLoadStateAdapter this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        if (this$0.isFullScreen()) {
            this$0.mNextLoadEnable = true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void checkDisableLoadMoreIfNotFullPage$lambda$2(RecyclerView.LayoutManager manager, TrailingLoadStateAdapter this$0, RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(manager, "$manager");
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        Intrinsics.checkNotNullParameter(recyclerView, "$recyclerView");
        StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) manager;
        int[] iArr = new int[staggeredGridLayoutManager.getSpanCount()];
        staggeredGridLayoutManager.findLastCompletelyVisibleItemPositions(iArr);
        int theBiggestNumber = this$0.getTheBiggestNumber(iArr) + 1;
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        boolean z2 = false;
        if (adapter != null && theBiggestNumber == adapter.getItemCount()) {
            z2 = true;
        }
        if (z2) {
            return;
        }
        this$0.mNextLoadEnable = true;
    }

    private final int getTheBiggestNumber(int[] numbers) {
        int i2 = -1;
        if (numbers != null) {
            if (!(numbers.length == 0)) {
                for (int i3 : numbers) {
                    if (i3 > i2) {
                        i2 = i3;
                    }
                }
            }
        }
        return i2;
    }

    private final boolean isFullScreen() {
        RecyclerView.Adapter adapter;
        RecyclerView recyclerView = getRecyclerView();
        if (recyclerView == null || (adapter = recyclerView.getAdapter()) == null) {
            return true;
        }
        RecyclerView recyclerView2 = getRecyclerView();
        RecyclerView.LayoutManager layoutManager = recyclerView2 != null ? recyclerView2.getLayoutManager() : null;
        LinearLayoutManager linearLayoutManager = layoutManager instanceof LinearLayoutManager ? (LinearLayoutManager) layoutManager : null;
        return (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() + 1 == adapter.getItemCount() && linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) ? false : true;
    }

    private final void loadAction() {
        RecyclerView recyclerView;
        if (this.isAutoLoadMore) {
            OnTrailingListener onTrailingListener = this.onTrailingListener;
            boolean z2 = false;
            if (onTrailingListener != null && !onTrailingListener.isAllowLoading()) {
                z2 = true;
            }
            if (z2 || !this.mNextLoadEnable || !(getLoadState() instanceof LoadState.NotLoading) || getLoadState().getEndOfPaginationReached() || (recyclerView = getRecyclerView()) == null) {
                return;
            }
            if (recyclerView.isComputingLayout()) {
                recyclerView.post(new Runnable() { // from class: t1.c
                    @Override // java.lang.Runnable
                    public final void run() {
                        TrailingLoadStateAdapter.loadAction$lambda$0(this.f28234c);
                    }
                });
            } else {
                invokeLoadMore();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadAction$lambda$0(TrailingLoadStateAdapter this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.invokeLoadMore();
    }

    public final void checkDisableLoadMoreIfNotFullPage() {
        final RecyclerView.LayoutManager layoutManager;
        this.mNextLoadEnable = false;
        final RecyclerView recyclerView = getRecyclerView();
        if (recyclerView == null || (layoutManager = recyclerView.getLayoutManager()) == null) {
            return;
        }
        if (layoutManager instanceof LinearLayoutManager) {
            recyclerView.post(new Runnable() { // from class: t1.d
                @Override // java.lang.Runnable
                public final void run() {
                    TrailingLoadStateAdapter.checkDisableLoadMoreIfNotFullPage$lambda$1(this.f28235c);
                }
            });
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            recyclerView.post(new Runnable() { // from class: t1.e
                @Override // java.lang.Runnable
                public final void run() {
                    TrailingLoadStateAdapter.checkDisableLoadMoreIfNotFullPage$lambda$2(layoutManager, this, recyclerView);
                }
            });
        }
    }

    public final void checkPreload$ebook_release(int itemCount, int currentPosition) {
        if (currentPosition <= itemCount - 1 && (itemCount - currentPosition) - 1 <= this.preloadSize) {
            loadAction();
        }
    }

    @Override // com.ykb.ebook.adapter.base.loadState.LoadStateAdapter
    public boolean displayLoadStateAsItem(@NotNull LoadState loadState) {
        boolean z2;
        Intrinsics.checkNotNullParameter(loadState, "loadState");
        return super.displayLoadStateAsItem(loadState) || (((z2 = loadState instanceof LoadState.NotLoading)) && !loadState.getEndOfPaginationReached()) || (this.isLoadEndDisplay && z2 && loadState.getEndOfPaginationReached());
    }

    @Nullable
    public final OnTrailingListener getOnTrailingListener() {
        return this.onTrailingListener;
    }

    public final int getPreloadSize() {
        return this.preloadSize;
    }

    public final void invokeFailRetry() {
        setLoadState(LoadState.Loading.INSTANCE);
        OnTrailingListener onTrailingListener = this.onTrailingListener;
        if (onTrailingListener != null) {
            onTrailingListener.onFailRetry();
        }
    }

    public final void invokeLoadMore() {
        setLoadState(LoadState.Loading.INSTANCE);
        OnTrailingListener onTrailingListener = this.onTrailingListener;
        if (onTrailingListener != null) {
            onTrailingListener.onLoad();
        }
    }

    /* renamed from: isAutoLoadMore, reason: from getter */
    public final boolean getIsAutoLoadMore() {
        return this.isAutoLoadMore;
    }

    /* renamed from: isLoadEndDisplay, reason: from getter */
    public final boolean getIsLoadEndDisplay() {
        return this.isLoadEndDisplay;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @CallSuper
    public void onViewAttachedToWindow(@NotNull VH holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        loadAction();
    }

    public final void setAutoLoadMore(boolean z2) {
        this.isAutoLoadMore = z2;
    }

    @NotNull
    public final TrailingLoadStateAdapter<VH> setOnLoadMoreListener(@Nullable OnTrailingListener listener) {
        this.onTrailingListener = listener;
        return this;
    }

    public final void setPreloadSize(int i2) {
        this.preloadSize = i2;
    }

    @NotNull
    public String toString() {
        return StringsKt__IndentKt.trimIndent("\n            TrailingLoadStateAdapter ->\n            [isLoadEndDisplay: " + this.isLoadEndDisplay + "],\n            [isAutoLoadMore: " + this.isAutoLoadMore + "],\n            [preloadSize: " + this.preloadSize + "],\n            [loadState: " + getLoadState() + "]\n        ");
    }

    public TrailingLoadStateAdapter(boolean z2) {
        this.isLoadEndDisplay = z2;
        this.isAutoLoadMore = true;
        this.mNextLoadEnable = true;
    }
}
