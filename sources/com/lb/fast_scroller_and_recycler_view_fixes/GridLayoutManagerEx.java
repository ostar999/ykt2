package com.lb.fast_scroller_and_recycler_view_fixes;

import android.content.Context;
import android.util.AttributeSet;
import androidx.recyclerview.widget.GridLayoutManager;
import com.umeng.analytics.pro.d;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\u0018\u00002\u00020\u0001B'\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tB\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0007¢\u0006\u0002\u0010\u000bB'\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\u0007\u0012\u0006\u0010\r\u001a\u00020\u000e¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u000eH\u0016¨\u0006\u0011"}, d2 = {"Lcom/lb/fast_scroller_and_recycler_view_fixes/GridLayoutManagerEx;", "Landroidx/recyclerview/widget/GridLayoutManager;", d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "defStyleAttr", "", "defStyleRes", "(Landroid/content/Context;Landroid/util/AttributeSet;II)V", "spanCount", "(Landroid/content/Context;I)V", "orientation", "reverseLayout", "", "(Landroid/content/Context;IIZ)V", "supportsPredictiveItemAnimations", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class GridLayoutManagerEx extends GridLayoutManager {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GridLayoutManagerEx(@NotNull Context context, @NotNull AttributeSet attrs, int i2, int i3) {
        super(context, attrs, i2, i3);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attrs, "attrs");
    }

    @Override // androidx.recyclerview.widget.GridLayoutManager, androidx.recyclerview.widget.LinearLayoutManager, androidx.recyclerview.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GridLayoutManagerEx(@NotNull Context context, int i2) {
        super(context, i2);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GridLayoutManagerEx(@NotNull Context context, int i2, int i3, boolean z2) {
        super(context, i2, i3, z2);
        Intrinsics.checkNotNullParameter(context, "context");
    }
}
