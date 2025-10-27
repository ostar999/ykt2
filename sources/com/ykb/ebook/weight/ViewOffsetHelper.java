package com.ykb.ebook.weight;

import android.view.View;
import androidx.core.view.ViewCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u000f\u001a\u00020\u0010J\u0006\u0010\u0011\u001a\u00020\bJ\u0006\u0010\u0012\u001a\u00020\bJ\u0006\u0010\u0013\u001a\u00020\bJ\u0006\u0010\u0014\u001a\u00020\bJ\u0006\u0010\u0015\u001a\u00020\u0006J\u0006\u0010\u0016\u001a\u00020\u0006J\u0006\u0010\u0017\u001a\u00020\u0010J\u000e\u0010\u0018\u001a\u00020\u00102\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\bJ\u000e\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\bJ\u000e\u0010\u001c\u001a\u00020\u00102\u0006\u0010\f\u001a\u00020\u0006R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001d"}, d2 = {"Lcom/ykb/ebook/weight/ViewOffsetHelper;", "", "view", "Landroid/view/View;", "(Landroid/view/View;)V", "horizontalOffsetEnabled", "", "layoutLeft", "", "layoutTop", "offsetLeft", "offsetTop", "verticalOffsetEnabled", "getView", "()Landroid/view/View;", "applyOffsets", "", "getLayoutLeft", "getLayoutTop", "getLeftAndRightOffset", "getTopAndBottomOffset", "isHorizontalOffsetEnabled", "isVerticalOffsetEnabled", "onViewLayout", "setHorizontalOffsetEnabled", "setLeftAndRightOffset", "offset", "setTopAndBottomOffset", "setVerticalOffsetEnabled", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class ViewOffsetHelper {
    private boolean horizontalOffsetEnabled;
    private int layoutLeft;
    private int layoutTop;
    private int offsetLeft;
    private int offsetTop;
    private boolean verticalOffsetEnabled;

    @NotNull
    private final View view;

    public ViewOffsetHelper(@NotNull View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        this.view = view;
        this.verticalOffsetEnabled = true;
        this.horizontalOffsetEnabled = true;
    }

    public final void applyOffsets() {
        View view = this.view;
        ViewCompat.offsetTopAndBottom(view, this.offsetTop - (view.getTop() - this.layoutTop));
        View view2 = this.view;
        ViewCompat.offsetLeftAndRight(view2, this.offsetLeft - (view2.getLeft() - this.layoutLeft));
    }

    public final int getLayoutLeft() {
        return this.layoutLeft;
    }

    public final int getLayoutTop() {
        return this.layoutTop;
    }

    /* renamed from: getLeftAndRightOffset, reason: from getter */
    public final int getOffsetLeft() {
        return this.offsetLeft;
    }

    /* renamed from: getTopAndBottomOffset, reason: from getter */
    public final int getOffsetTop() {
        return this.offsetTop;
    }

    @NotNull
    public final View getView() {
        return this.view;
    }

    /* renamed from: isHorizontalOffsetEnabled, reason: from getter */
    public final boolean getHorizontalOffsetEnabled() {
        return this.horizontalOffsetEnabled;
    }

    /* renamed from: isVerticalOffsetEnabled, reason: from getter */
    public final boolean getVerticalOffsetEnabled() {
        return this.verticalOffsetEnabled;
    }

    public final void onViewLayout() {
        this.layoutTop = this.view.getTop();
        this.layoutLeft = this.view.getLeft();
    }

    public final void setHorizontalOffsetEnabled(boolean horizontalOffsetEnabled) {
        this.horizontalOffsetEnabled = horizontalOffsetEnabled;
    }

    public final boolean setLeftAndRightOffset(int offset) {
        if (!this.horizontalOffsetEnabled || this.offsetLeft == offset) {
            return false;
        }
        this.offsetLeft = offset;
        applyOffsets();
        return true;
    }

    public final boolean setTopAndBottomOffset(int offset) {
        if (!this.verticalOffsetEnabled || this.offsetTop == offset) {
            return false;
        }
        this.offsetTop = offset;
        applyOffsets();
        return true;
    }

    public final void setVerticalOffsetEnabled(boolean verticalOffsetEnabled) {
        this.verticalOffsetEnabled = verticalOffsetEnabled;
    }
}
