package com.ykb.ebook.adapter.base;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lcom/ykb/ebook/adapter/base/EmptyLayoutVH;", "Landroidx/recyclerview/widget/RecyclerView$ViewHolder;", "emptyLayout", "Landroid/widget/FrameLayout;", "(Landroid/widget/FrameLayout;)V", "changeEmptyView", "", "view", "Landroid/view/View;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class EmptyLayoutVH extends RecyclerView.ViewHolder {

    @NotNull
    private final FrameLayout emptyLayout;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public EmptyLayoutVH(@NotNull FrameLayout emptyLayout) {
        super(emptyLayout);
        Intrinsics.checkNotNullParameter(emptyLayout, "emptyLayout");
        this.emptyLayout = emptyLayout;
    }

    public final void changeEmptyView(@Nullable View view) {
        if (view == null) {
            return;
        }
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(view);
        }
        if (view.getLayoutParams() == null) {
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 17;
            view.setLayoutParams(layoutParams);
        }
        this.emptyLayout.removeAllViews();
        this.emptyLayout.addView(view);
    }
}
