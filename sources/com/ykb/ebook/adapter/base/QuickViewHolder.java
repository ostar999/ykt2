package com.ykb.ebook.adapter.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0019\b\u0016\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006B\r\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\b\b\u0001\u0010\f\u001a\u00020\u0003J\u0010\u0010\r\u001a\u00020\u000b2\b\b\u0001\u0010\f\u001a\u00020\u0003J\u0018\u0010\u000e\u001a\u00020\u00002\b\b\u0001\u0010\f\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000b¨\u0006\u000f"}, d2 = {"Lcom/ykb/ebook/adapter/base/QuickViewHolder;", "Lcom/chad/library/adapter/base/viewholder/BaseViewHolder;", "resId", "", "parent", "Landroid/view/ViewGroup;", "(ILandroid/view/ViewGroup;)V", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "isEnabled", "", "viewId", "isSelected", "setSelected", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public class QuickViewHolder extends BaseViewHolder {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public QuickViewHolder(@NotNull View view) {
        super(view);
        Intrinsics.checkNotNullParameter(view, "view");
    }

    public final boolean isEnabled(@IdRes int viewId) {
        return getView(viewId).isEnabled();
    }

    public final boolean isSelected(@IdRes int viewId) {
        return getView(viewId).isSelected();
    }

    @NotNull
    public final QuickViewHolder setSelected(@IdRes int viewId, boolean isSelected) {
        getView(viewId).setSelected(isSelected);
        return this;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public QuickViewHolder(@LayoutRes int i2, @NotNull ViewGroup parent) {
        Intrinsics.checkNotNullParameter(parent, "parent");
        View viewInflate = LayoutInflater.from(parent.getContext()).inflate(i2, parent, false);
        Intrinsics.checkNotNullExpressionValue(viewInflate, "from(parent.context).inflate(resId, parent, false)");
        this(viewInflate);
    }
}
