package com.lb.fast_scroller_and_recycler_view_fixes_library;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0011B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J(\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lcom/lb/fast_scroller_and_recycler_view_fixes_library/BottomOffsetDecoration;", "Landroidx/recyclerview/widget/RecyclerView$ItemDecoration;", "mBottomOffset", "", "layoutManagerType", "Lcom/lb/fast_scroller_and_recycler_view_fixes_library/BottomOffsetDecoration$LayoutManagerType;", "(ILcom/lb/fast_scroller_and_recycler_view_fixes_library/BottomOffsetDecoration$LayoutManagerType;)V", "getItemOffsets", "", "outRect", "Landroid/graphics/Rect;", "view", "Landroid/view/View;", "parent", "Landroidx/recyclerview/widget/RecyclerView;", "state", "Landroidx/recyclerview/widget/RecyclerView$State;", "LayoutManagerType", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class BottomOffsetDecoration extends RecyclerView.ItemDecoration {

    @NotNull
    private final LayoutManagerType layoutManagerType;
    private final int mBottomOffset;

    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004¨\u0006\u0005"}, d2 = {"Lcom/lb/fast_scroller_and_recycler_view_fixes_library/BottomOffsetDecoration$LayoutManagerType;", "", "(Ljava/lang/String;I)V", "GRID_LAYOUT_MANAGER", "LINEAR_LAYOUT_MANAGER", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
    public enum LayoutManagerType {
        GRID_LAYOUT_MANAGER,
        LINEAR_LAYOUT_MANAGER
    }

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[LayoutManagerType.values().length];
            try {
                iArr[LayoutManagerType.LINEAR_LAYOUT_MANAGER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[LayoutManagerType.GRID_LAYOUT_MANAGER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public BottomOffsetDecoration(int i2, @NotNull LayoutManagerType layoutManagerType) {
        Intrinsics.checkNotNullParameter(layoutManagerType, "layoutManagerType");
        this.mBottomOffset = i2;
        this.layoutManagerType = layoutManagerType;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NotNull Rect outRect, @NotNull View view, @NotNull RecyclerView parent, @NotNull RecyclerView.State state) {
        Intrinsics.checkNotNullParameter(outRect, "outRect");
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(parent, "parent");
        Intrinsics.checkNotNullParameter(state, "state");
        super.getItemOffsets(outRect, view, parent, state);
        int i2 = WhenMappings.$EnumSwitchMapping$0[this.layoutManagerType.ordinal()];
        int i3 = 0;
        if (i2 == 1) {
            int childAdapterPosition = parent.getChildAdapterPosition(view);
            if (state.getItemCount() > 0 && childAdapterPosition == state.getItemCount() - 1) {
                i3 = this.mBottomOffset;
            }
            outRect.bottom = i3;
            return;
        }
        if (i2 != 2) {
            return;
        }
        RecyclerView.Adapter adapter = parent.getAdapter();
        if (adapter != null && adapter.getItemCount() != 0 && GridLayoutManagerUtils.INSTANCE.isOnLastRow(view, parent)) {
            i3 = this.mBottomOffset;
        }
        outRect.bottom = i3;
    }
}
