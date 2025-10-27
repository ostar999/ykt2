package com.angcyo.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.angcyo.tablayout.DslTabLayout;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0016J\u001a\u0010 \u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\n\u0010%\u001a\u0004\u0018\u00010&H\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u000b\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0011\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000e\"\u0004\b\u0013\u0010\u0010R\u001a\u0010\u0014\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000e\"\u0004\b\u0016\u0010\u0010R\u001a\u0010\u0017\u001a\u00020\fX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000e\"\u0004\b\u0019\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001b¨\u0006'"}, d2 = {"Lcom/angcyo/tablayout/DslTabHighlight;", "Lcom/angcyo/tablayout/DslGradientDrawable;", "tabLayout", "Lcom/angcyo/tablayout/DslTabLayout;", "(Lcom/angcyo/tablayout/DslTabLayout;)V", "highlightDrawable", "Landroid/graphics/drawable/Drawable;", "getHighlightDrawable", "()Landroid/graphics/drawable/Drawable;", "setHighlightDrawable", "(Landroid/graphics/drawable/Drawable;)V", "highlightHeight", "", "getHighlightHeight", "()I", "setHighlightHeight", "(I)V", "highlightHeightOffset", "getHighlightHeightOffset", "setHighlightHeightOffset", "highlightWidth", "getHighlightWidth", "setHighlightWidth", "highlightWidthOffset", "getHighlightWidthOffset", "setHighlightWidthOffset", "getTabLayout", "()Lcom/angcyo/tablayout/DslTabLayout;", "draw", "", "canvas", "Landroid/graphics/Canvas;", "initAttribute", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "updateOriginDrawable", "Landroid/graphics/drawable/GradientDrawable;", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public class DslTabHighlight extends DslGradientDrawable {

    @Nullable
    private Drawable highlightDrawable;
    private int highlightHeight;
    private int highlightHeightOffset;
    private int highlightWidth;
    private int highlightWidthOffset;

    @NotNull
    private final DslTabLayout tabLayout;

    public DslTabHighlight(@NotNull DslTabLayout tabLayout) {
        Intrinsics.checkNotNullParameter(tabLayout, "tabLayout");
        this.tabLayout = tabLayout;
        this.highlightWidth = -1;
        this.highlightHeight = -1;
    }

    @Override // com.angcyo.tablayout.DslGradientDrawable, com.angcyo.tablayout.AbsDslDrawable, android.graphics.drawable.Drawable
    public void draw(@NotNull Canvas canvas) {
        Drawable highlightDrawable;
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        View currentItemView = this.tabLayout.getCurrentItemView();
        if (currentItemView != null) {
            ViewGroup.LayoutParams layoutParams = currentItemView.getLayoutParams();
            if (!(layoutParams instanceof DslTabLayout.LayoutParams) || (highlightDrawable = ((DslTabLayout.LayoutParams) layoutParams).getHighlightDrawable()) == null) {
                highlightDrawable = this.highlightDrawable;
            }
            if (highlightDrawable != null) {
                int intrinsicWidth = this.highlightWidth;
                if (intrinsicWidth == -2) {
                    intrinsicWidth = highlightDrawable.getIntrinsicWidth();
                } else if (intrinsicWidth == -1) {
                    intrinsicWidth = currentItemView.getMeasuredWidth();
                }
                int i2 = intrinsicWidth + this.highlightWidthOffset;
                int intrinsicHeight = this.highlightHeight;
                if (intrinsicHeight == -2) {
                    intrinsicHeight = highlightDrawable.getIntrinsicHeight();
                } else if (intrinsicHeight == -1) {
                    intrinsicHeight = currentItemView.getMeasuredHeight();
                }
                int i3 = intrinsicHeight + this.highlightHeightOffset;
                int left = currentItemView.getLeft() + ((currentItemView.getRight() - currentItemView.getLeft()) / 2);
                int top2 = currentItemView.getTop() + ((currentItemView.getBottom() - currentItemView.getTop()) / 2);
                int i4 = i2 / 2;
                int i5 = i3 / 2;
                highlightDrawable.setBounds(left - i4, top2 - i5, left + i4, top2 + i5);
                highlightDrawable.draw(canvas);
                canvas.save();
                if (this.tabLayout.isHorizontal()) {
                    canvas.translate(currentItemView.getLeft(), 0.0f);
                } else {
                    canvas.translate(0.0f, currentItemView.getTop());
                }
                currentItemView.draw(canvas);
                canvas.restore();
            }
        }
    }

    @Nullable
    public final Drawable getHighlightDrawable() {
        return this.highlightDrawable;
    }

    public final int getHighlightHeight() {
        return this.highlightHeight;
    }

    public final int getHighlightHeightOffset() {
        return this.highlightHeightOffset;
    }

    public final int getHighlightWidth() {
        return this.highlightWidth;
    }

    public final int getHighlightWidthOffset() {
        return this.highlightWidthOffset;
    }

    @NotNull
    public final DslTabLayout getTabLayout() {
        return this.tabLayout;
    }

    @Override // com.angcyo.tablayout.AbsDslDrawable
    public void initAttribute(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        Intrinsics.checkNotNullParameter(context, "context");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DslTabLayout);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.obtainStyledAttr…R.styleable.DslTabLayout)");
        this.highlightDrawable = typedArrayObtainStyledAttributes.getDrawable(R.styleable.DslTabLayout_tab_highlight_drawable);
        this.highlightWidth = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.DslTabLayout_tab_highlight_width, this.highlightWidth);
        this.highlightHeight = typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.DslTabLayout_tab_highlight_height, this.highlightHeight);
        this.highlightWidthOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_highlight_width_offset, this.highlightWidthOffset);
        this.highlightHeightOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_highlight_height_offset, this.highlightHeightOffset);
        typedArrayObtainStyledAttributes.recycle();
        if (this.highlightDrawable == null && isValidConfig()) {
            updateOriginDrawable();
        }
    }

    public final void setHighlightDrawable(@Nullable Drawable drawable) {
        this.highlightDrawable = drawable;
    }

    public final void setHighlightHeight(int i2) {
        this.highlightHeight = i2;
    }

    public final void setHighlightHeightOffset(int i2) {
        this.highlightHeightOffset = i2;
    }

    public final void setHighlightWidth(int i2) {
        this.highlightWidth = i2;
    }

    public final void setHighlightWidthOffset(int i2) {
        this.highlightWidthOffset = i2;
    }

    @Override // com.angcyo.tablayout.DslGradientDrawable
    @Nullable
    public GradientDrawable updateOriginDrawable() {
        GradientDrawable gradientDrawableUpdateOriginDrawable = super.updateOriginDrawable();
        this.highlightDrawable = getOriginDrawable();
        return gradientDrawableUpdateOriginDrawable;
    }
}
