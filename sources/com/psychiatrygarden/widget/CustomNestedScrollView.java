package com.psychiatrygarden.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.core.widget.NestedScrollView;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J\u0010\u0010\u0014\u001a\u00020\b2\u0006\u0010\u0015\u001a\u00020\u0013H\u0016R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u0016"}, d2 = {"Lcom/psychiatrygarden/widget/CustomNestedScrollView;", "Landroidx/core/widget/NestedScrollView;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "scrollable", "", "getScrollable", "()Z", "setScrollable", "(Z)V", "fling", "", "velocityY", "", "onInterceptTouchEvent", "ev", "Landroid/view/MotionEvent;", "onTouchEvent", "motionEvent", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public final class CustomNestedScrollView extends NestedScrollView {
    private boolean scrollable;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public CustomNestedScrollView(@NotNull Context context) {
        this(context, null, 2, 0 == true ? 1 : 0);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    public /* synthetic */ CustomNestedScrollView(Context context, AttributeSet attributeSet, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet);
    }

    @Override // androidx.core.widget.NestedScrollView
    public void fling(int velocityY) {
        if (this.scrollable) {
            super.fling(velocityY);
        }
    }

    public final boolean getScrollable() {
        return this.scrollable;
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(@NotNull MotionEvent ev) {
        Intrinsics.checkNotNullParameter(ev, "ev");
        return this.scrollable && super.onInterceptTouchEvent(ev);
    }

    @Override // androidx.core.widget.NestedScrollView, android.view.View
    public boolean onTouchEvent(@NotNull MotionEvent motionEvent) {
        Intrinsics.checkNotNullParameter(motionEvent, "motionEvent");
        return this.scrollable && super.onTouchEvent(motionEvent);
    }

    public final void setScrollable(boolean z2) {
        this.scrollable = z2;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public CustomNestedScrollView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        Intrinsics.checkNotNullParameter(context, "context");
        this.scrollable = true;
    }
}
