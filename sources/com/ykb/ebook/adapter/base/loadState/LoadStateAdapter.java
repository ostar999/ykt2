package com.ykb.ebook.adapter.base.loadState;

import android.view.ViewGroup;
import androidx.annotation.CallSuper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.ykb.ebook.adapter.base.FullSpanAdapterType;
import com.ykb.ebook.adapter.base.loadState.LoadState;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\u0005¢\u0006\u0002\u0010\u0005J\u0010\u0010\u0014\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\nH\u0016J\u0006\u0010\u0015\u001a\u00020\u0016J\u000e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0016J\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\t\u001a\u00020\nH\u0016J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0011\u001a\u00020\u0010H\u0017J\u001d\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00028\u00002\u0006\u0010\t\u001a\u00020\nH&¢\u0006\u0002\u0010\u001eJ\u001b\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u0016¢\u0006\u0002\u0010\u001fJ)\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u001d\u001a\u00028\u00002\u0006\u0010\u0018\u001a\u00020\u00162\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!¢\u0006\u0002\u0010#J\u001d\u0010$\u001a\u00028\u00002\u0006\u0010%\u001a\u00020&2\u0006\u0010\t\u001a\u00020\nH&¢\u0006\u0002\u0010'J\u001b\u0010$\u001a\u00028\u00002\u0006\u0010%\u001a\u00020&2\u0006\u0010(\u001a\u00020\u0016¢\u0006\u0002\u0010)J\u0010\u0010*\u001a\u00020\u001b2\u0006\u0010\u0011\u001a\u00020\u0010H\u0017R\u0011\u0010\u0006\u001a\u00020\u00078F¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR$\u0010\t\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\n@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\"\u0010\u0011\u001a\u0004\u0018\u00010\u00102\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013¨\u0006+"}, d2 = {"Lcom/ykb/ebook/adapter/base/loadState/LoadStateAdapter;", "VH", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "Landroidx/recyclerview/widget/RecyclerView$Adapter;", "Lcom/ykb/ebook/adapter/base/FullSpanAdapterType;", "()V", "isLoading", "", "()Z", "loadState", "Lcom/ykb/ebook/adapter/base/loadState/LoadState;", "getLoadState", "()Lcom/ykb/ebook/adapter/base/loadState/LoadState;", "setLoadState", "(Lcom/ykb/ebook/adapter/base/loadState/LoadState;)V", "<set-?>", "Landroidx/recyclerview/widget/RecyclerView;", "recyclerView", "getRecyclerView", "()Landroidx/recyclerview/widget/RecyclerView;", "displayLoadStateAsItem", "getItemCount", "", "getItemViewType", "position", "getStateViewType", "onAttachedToRecyclerView", "", "onBindViewHolder", "holder", "(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;Lcom/ykb/ebook/adapter/base/loadState/LoadState;)V", "(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;I)V", "payloads", "", "", "(Landroidx/recyclerview/widget/RecyclerView$ViewHolder;ILjava/util/List;)V", "onCreateViewHolder", "parent", "Landroid/view/ViewGroup;", "(Landroid/view/ViewGroup;Lcom/ykb/ebook/adapter/base/loadState/LoadState;)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "viewType", "(Landroid/view/ViewGroup;I)Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "onDetachedFromRecyclerView", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public abstract class LoadStateAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements FullSpanAdapterType {

    @NotNull
    private LoadState loadState = LoadState.None.INSTANCE;

    @Nullable
    private RecyclerView recyclerView;

    public boolean displayLoadStateAsItem(@NotNull LoadState loadState) {
        Intrinsics.checkNotNullParameter(loadState, "loadState");
        return (loadState instanceof LoadState.Loading) || (loadState instanceof LoadState.Error);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemCount() {
        return displayLoadStateAsItem(this.loadState) ? 1 : 0;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final int getItemViewType(int position) {
        return getStateViewType(this.loadState);
    }

    @NotNull
    public final LoadState getLoadState() {
        return this.loadState;
    }

    @Nullable
    public final RecyclerView getRecyclerView() {
        return this.recyclerView;
    }

    public int getStateViewType(@NotNull LoadState loadState) {
        Intrinsics.checkNotNullParameter(loadState, "loadState");
        return 0;
    }

    public final boolean isLoading() {
        return Intrinsics.areEqual(this.loadState, LoadState.Loading.INSTANCE);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @CallSuper
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        this.recyclerView = recyclerView;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(@NotNull VH holder, int position) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        onBindViewHolder((LoadStateAdapter<VH>) holder, this.loadState);
    }

    public abstract void onBindViewHolder(@NotNull VH holder, @NotNull LoadState loadState);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @NotNull
    public final VH onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        return (VH) onCreateViewHolder(parent, this.loadState);
    }

    @NotNull
    public abstract VH onCreateViewHolder(@NotNull ViewGroup parent, @NotNull LoadState loadState);

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    @CallSuper
    public void onDetachedFromRecyclerView(@NotNull RecyclerView recyclerView) {
        Intrinsics.checkNotNullParameter(recyclerView, "recyclerView");
        this.recyclerView = null;
    }

    public final void setLoadState(@NotNull LoadState loadState) {
        Intrinsics.checkNotNullParameter(loadState, "loadState");
        if (Intrinsics.areEqual(this.loadState, loadState)) {
            return;
        }
        boolean zDisplayLoadStateAsItem = displayLoadStateAsItem(this.loadState);
        boolean zDisplayLoadStateAsItem2 = displayLoadStateAsItem(loadState);
        if (zDisplayLoadStateAsItem && !zDisplayLoadStateAsItem2) {
            notifyItemRemoved(0);
        } else if (zDisplayLoadStateAsItem2 && !zDisplayLoadStateAsItem) {
            notifyItemInserted(0);
        } else if (zDisplayLoadStateAsItem && zDisplayLoadStateAsItem2) {
            notifyItemChanged(0);
        }
        this.loadState = loadState;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public final void onBindViewHolder(@NotNull VH holder, int position, @NotNull List<Object> payloads) {
        Intrinsics.checkNotNullParameter(holder, "holder");
        Intrinsics.checkNotNullParameter(payloads, "payloads");
        super.onBindViewHolder(holder, position, payloads);
    }
}
