package com.psychiatrygarden.utils;

import android.view.View;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0004\u001a\u00020\u0005*\u00020\u0002\u001a\n\u0010\u0006\u001a\u00020\u0001*\u00020\u0002\u001a\n\u0010\u0007\u001a\u00020\u0001*\u00020\u0002¨\u0006\b"}, d2 = {"getCustomerAdapterPosition", "", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "getCustomerBindAdapterPosition", "getCustomerItemView", "Landroid/view/View;", "getCustomerItemViewType", "getCustomerLayoutPosition", "xizongapp_ykbRelease"}, k = 2, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class BaseViewHolderUtilKt {
    public static final int getCustomerAdapterPosition(@NotNull BaseViewHolder baseViewHolder) {
        Intrinsics.checkNotNullParameter(baseViewHolder, "<this>");
        return baseViewHolder.getAdapterPosition();
    }

    public static final int getCustomerBindAdapterPosition(@NotNull BaseViewHolder baseViewHolder) {
        Intrinsics.checkNotNullParameter(baseViewHolder, "<this>");
        return baseViewHolder.getBindingAdapterPosition();
    }

    @NotNull
    public static final View getCustomerItemView(@NotNull BaseViewHolder baseViewHolder) {
        Intrinsics.checkNotNullParameter(baseViewHolder, "<this>");
        View itemView = baseViewHolder.itemView;
        Intrinsics.checkNotNullExpressionValue(itemView, "itemView");
        return itemView;
    }

    public static final int getCustomerItemViewType(@NotNull BaseViewHolder baseViewHolder) {
        Intrinsics.checkNotNullParameter(baseViewHolder, "<this>");
        return baseViewHolder.getItemViewType();
    }

    public static final int getCustomerLayoutPosition(@NotNull BaseViewHolder baseViewHolder) {
        Intrinsics.checkNotNullParameter(baseViewHolder, "<this>");
        return baseViewHolder.getLayoutPosition();
    }
}
