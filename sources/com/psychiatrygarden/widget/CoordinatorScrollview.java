package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.core.view.NestedScrollingParent2;
import androidx.core.widget.NestedScrollView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0011\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004¢\u0006\u0002\u0010\u0005B\u001b\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bB#\b\u0016\u0012\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ0\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\nH\u0016J(\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0019\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\nH\u0016J\u000e\u0010\u001a\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\nR\u000e\u0010\f\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u001b"}, d2 = {"Lcom/psychiatrygarden/widget/CoordinatorScrollview;", "Landroidx/core/widget/NestedScrollView;", "Landroidx/core/view/NestedScrollingParent2;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "(Landroid/content/Context;)V", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "defStyleAttr", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "maxScrollY", "onNestedPreScroll", "", TypedValues.AttributesType.S_TARGET, "Landroid/view/View;", "dx", "dy", "consumed", "", "type", "onStartNestedScroll", "", "child", "axes", "setMaxScrollY", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CoordinatorScrollview extends NestedScrollView implements NestedScrollingParent2 {
    private int maxScrollY;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CoordinatorScrollview(@Nullable Context context) {
        super(context);
        Intrinsics.checkNotNull(context);
    }

    @Override // androidx.core.widget.NestedScrollView, androidx.core.view.NestedScrollingParent2
    public void onNestedPreScroll(@NotNull View target, int dx, int dy, @NotNull int[] consumed, int type) {
        Intrinsics.checkNotNullParameter(target, "target");
        Intrinsics.checkNotNullParameter(consumed, "consumed");
        if (dy <= 0 || getScrollY() >= this.maxScrollY) {
            return;
        }
        scrollBy(0, dy);
        consumed[1] = dy;
    }

    @Override // androidx.core.widget.NestedScrollView, androidx.core.view.NestedScrollingParent2
    public boolean onStartNestedScroll(@NotNull View child, @NotNull View target, int axes, int type) {
        Intrinsics.checkNotNullParameter(child, "child");
        Intrinsics.checkNotNullParameter(target, "target");
        return true;
    }

    public final void setMaxScrollY(int maxScrollY) {
        this.maxScrollY = maxScrollY;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CoordinatorScrollview(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNull(context);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CoordinatorScrollview(@Nullable Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNull(context);
    }
}
