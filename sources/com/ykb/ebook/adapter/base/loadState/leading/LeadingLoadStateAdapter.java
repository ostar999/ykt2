package com.ykb.ebook.adapter.base.loadState.leading;

import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;
import com.ykb.ebook.adapter.base.loadState.LoadState;
import com.ykb.ebook.adapter.base.loadState.LoadStateAdapter;
import com.ykb.ebook.adapter.base.loadState.leading.LeadingLoadStateAdapter;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\u0002\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0001%B\u0005¢\u0006\u0002\u0010\u0004J\u0015\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0010H\u0000¢\u0006\u0002\b\u0018J\u0010\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0006\u0010\u001c\u001a\u00020\u0016J\b\u0010\u001d\u001a\u00020\u0016H\u0002J\u0015\u0010\u001e\u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00028\u0000H\u0017¢\u0006\u0002\u0010 J\u0016\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\u0010\"\u001a\u0004\u0018\u00010\u000bJ\b\u0010#\u001a\u00020$H\u0016R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0007\"\u0004\b\b\u0010\tR\"\u0010\f\u001a\u0004\u0018\u00010\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\u000b@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014¨\u0006&"}, d2 = {"Lcom/ykb/ebook/adapter/base/loadState/leading/LeadingLoadStateAdapter;", "VH", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Lcom/ykb/ebook/adapter/base/loadState/LoadStateAdapter;", "()V", "isLoadEnable", "", "()Z", "setLoadEnable", "(Z)V", "<set-?>", "Lcom/ykb/ebook/adapter/base/loadState/leading/LeadingLoadStateAdapter$OnLeadingListener;", "onLeadingListener", "getOnLeadingListener", "()Lcom/ykb/ebook/adapter/base/loadState/leading/LeadingLoadStateAdapter$OnLeadingListener;", "preloadSize", "", "getPreloadSize", "()I", "setPreloadSize", "(I)V", "checkPreload", "", "currentPosition", "checkPreload$ebook_release", "displayLoadStateAsItem", "loadState", "Lcom/ykb/ebook/adapter/base/loadState/LoadState;", "invokeLoad", "loadAction", "onViewAttachedToWindow", "holder", "(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;)V", "setOnLeadingListener", ServiceSpecificExtraArgs.CastExtraArgs.LISTENER, "toString", "", "OnLeadingListener", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public abstract class LeadingLoadStateAdapter<VH extends RecyclerView.ViewHolder> extends LoadStateAdapter<VH> {
    private boolean isLoadEnable = true;

    @Nullable
    private OnLeadingListener onLeadingListener;
    private int preloadSize;

    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H\u0016J\b\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0006"}, d2 = {"Lcom/ykb/ebook/adapter/base/loadState/leading/LeadingLoadStateAdapter$OnLeadingListener;", "", "isAllowLoading", "", "onLoad", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public interface OnLeadingListener {

        @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
        public static final class DefaultImpls {
            public static boolean isAllowLoading(@NotNull OnLeadingListener onLeadingListener) {
                return true;
            }
        }

        boolean isAllowLoading();

        void onLoad();
    }

    private final void loadAction() {
        RecyclerView recyclerView;
        if (this.isLoadEnable) {
            OnLeadingListener onLeadingListener = this.onLeadingListener;
            boolean z2 = false;
            if (onLeadingListener != null && !onLeadingListener.isAllowLoading()) {
                z2 = true;
            }
            if (z2 || !(getLoadState() instanceof LoadState.NotLoading) || getLoadState().getEndOfPaginationReached() || (recyclerView = getRecyclerView()) == null) {
                return;
            }
            if (recyclerView.isComputingLayout()) {
                recyclerView.post(new Runnable() { // from class: s1.a
                    @Override // java.lang.Runnable
                    public final void run() {
                        LeadingLoadStateAdapter.loadAction$lambda$0(this.f28204c);
                    }
                });
            } else {
                invokeLoad();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void loadAction$lambda$0(LeadingLoadStateAdapter this$0) {
        Intrinsics.checkNotNullParameter(this$0, "this$0");
        this$0.invokeLoad();
    }

    public final void checkPreload$ebook_release(int currentPosition) {
        if (currentPosition >= 0 && currentPosition <= this.preloadSize) {
            loadAction();
        }
    }

    @Override // com.ykb.ebook.adapter.base.loadState.LoadStateAdapter
    public boolean displayLoadStateAsItem(@NotNull LoadState loadState) {
        Intrinsics.checkNotNullParameter(loadState, "loadState");
        return loadState instanceof LoadState.Loading;
    }

    @Nullable
    public final OnLeadingListener getOnLeadingListener() {
        return this.onLeadingListener;
    }

    public final int getPreloadSize() {
        return this.preloadSize;
    }

    public final void invokeLoad() {
        setLoadState(LoadState.Loading.INSTANCE);
        OnLeadingListener onLeadingListener = this.onLeadingListener;
        if (onLeadingListener != null) {
            onLeadingListener.onLoad();
        }
    }

    /* renamed from: isLoadEnable, reason: from getter */
    public final boolean getIsLoadEnable() {
        return this.isLoadEnable;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @CallSuper
    public void onViewAttachedToWindow(@NotNull VH holder) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        loadAction();
    }

    public final void setLoadEnable(boolean z2) {
        this.isLoadEnable = z2;
    }

    @NotNull
    public final LeadingLoadStateAdapter<VH> setOnLeadingListener(@Nullable OnLeadingListener listener) {
        this.onLeadingListener = listener;
        return this;
    }

    public final void setPreloadSize(int i2) {
        this.preloadSize = i2;
    }

    @NotNull
    public String toString() {
        return StringsKt__IndentKt.trimIndent("\n            LeadingLoadStateAdapter ->\n            [isLoadEnable: " + this.isLoadEnable + "],\n            [preloadSize: " + this.preloadSize + "],\n            [loadState: " + getLoadState() + "]\n        ");
    }
}
