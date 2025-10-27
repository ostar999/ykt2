package com.catchpig.mvvm.base.adapter;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u000f\b\u0016\u0012\u0006\u0010\u0004\u001a\u00028\u0000¢\u0006\u0002\u0010\u0005B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001f\u0010\n\u001a\u00020\u000b2\u0017\u0010\f\u001a\u0013\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u000b0\r¢\u0006\u0002\b\u000eR\u0012\u0010\u0004\u001a\u0004\u0018\u00018\u0000X\u0082\u000e¢\u0006\u0004\n\u0002\u0010\t¨\u0006\u000f"}, d2 = {"Lcom/catchpig/mvvm/base/adapter/CommonViewHolder;", "VB", "Landroidx/viewbinding/ViewBinding;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "itemViewBinding", "(Landroidx/viewbinding/ViewBinding;)V", "itemView", "Landroid/view/View;", "(Landroid/view/View;)V", "Landroidx/viewbinding/ViewBinding;", "viewBanding", "", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final class CommonViewHolder<VB extends ViewBinding> extends RecyclerView.ViewHolder {

    @Nullable
    private VB itemViewBinding;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CommonViewHolder(@NotNull View itemView) {
        super(itemView);
        Intrinsics.checkNotNullParameter(itemView, "itemView");
    }

    public final void viewBanding(@NotNull Function1<? super VB, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        VB vb = this.itemViewBinding;
        if (vb != null) {
            block.invoke(vb);
        }
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public CommonViewHolder(@NotNull VB itemViewBinding) {
        Intrinsics.checkNotNullParameter(itemViewBinding, "itemViewBinding");
        View root = itemViewBinding.getRoot();
        Intrinsics.checkNotNullExpressionValue(root, "itemViewBinding.root");
        this(root);
        this.itemViewBinding = itemViewBinding;
    }
}
