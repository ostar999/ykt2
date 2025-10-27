package com.lb.fast_scroller_and_recycler_view_fixes_library;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0002J\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b¨\u0006\u000b"}, d2 = {"Lcom/lb/fast_scroller_and_recycler_view_fixes_library/GridLayoutManagerUtils;", "", "()V", "getLastItemPositionOnSameRow", "", "view", "Landroid/view/View;", "parent", "Landroidx/recyclerview/widget/RecyclerView;", "isOnLastRow", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class GridLayoutManagerUtils {

    @NotNull
    public static final GridLayoutManagerUtils INSTANCE = new GridLayoutManagerUtils();

    private GridLayoutManagerUtils() {
    }

    private final int getLastItemPositionOnSameRow(View view, RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        Intrinsics.checkNotNull(layoutManager, "null cannot be cast to non-null type androidx.recyclerview.widget.GridLayoutManager");
        GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
        GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();
        int spanCount = gridLayoutManager.getSpanCount();
        RecyclerView.Adapter adapter = parent.getAdapter();
        Intrinsics.checkNotNull(adapter);
        int itemCount = adapter.getItemCount() - 1;
        int childAdapterPosition = parent.getChildAdapterPosition(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        Intrinsics.checkNotNull(layoutParams, "null cannot be cast to non-null type androidx.recyclerview.widget.GridLayoutManager.LayoutParams");
        int spanIndex = ((GridLayoutManager.LayoutParams) layoutParams).getSpanIndex() + spanSizeLookup.getSpanSize(childAdapterPosition);
        while (childAdapterPosition <= itemCount && spanIndex <= spanCount) {
            int i2 = childAdapterPosition + 1;
            spanIndex += spanSizeLookup.getSpanSize(i2);
            if (spanIndex > spanCount) {
                return childAdapterPosition;
            }
            childAdapterPosition = i2;
        }
        return itemCount;
    }

    public final boolean isOnLastRow(@NotNull View view, @NotNull RecyclerView parent) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(parent, "parent");
        int lastItemPositionOnSameRow = getLastItemPositionOnSameRow(view, parent);
        RecyclerView.Adapter adapter = parent.getAdapter();
        Intrinsics.checkNotNull(adapter);
        return lastItemPositionOnSameRow == adapter.getItemCount() - 1;
    }
}
