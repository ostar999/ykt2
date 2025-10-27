package com.angcyo.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0017\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0016J\u0018\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020\b2\u0006\u0010&\u001a\u00020\bH\u0016J\u0018\u0010'\u001a\u00020$2\u0006\u0010%\u001a\u00020\b2\u0006\u0010&\u001a\u00020\bH\u0016J\u001a\u0010(\u001a\u00020 2\u0006\u0010)\u001a\u00020*2\b\u0010+\u001a\u0004\u0018\u00010,H\u0016R\u0013\u0010\u0003\u001a\u0004\u0018\u00010\u00048F¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\n\"\u0004\b\u000f\u0010\fR\u001a\u0010\u0010\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\n\"\u0004\b\u0012\u0010\fR\u001a\u0010\u0013\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\n\"\u0004\b\u0015\u0010\fR\u001a\u0010\u0016\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\n\"\u0004\b\u0018\u0010\fR\u001a\u0010\u0019\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\n\"\u0004\b\u001b\u0010\fR\u001a\u0010\u001c\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\n\"\u0004\b\u001e\u0010\f¨\u0006-"}, d2 = {"Lcom/angcyo/tablayout/DslTabDivider;", "Lcom/angcyo/tablayout/DslGradientDrawable;", "()V", "_tabLayout", "Lcom/angcyo/tablayout/DslTabLayout;", "get_tabLayout", "()Lcom/angcyo/tablayout/DslTabLayout;", "dividerHeight", "", "getDividerHeight", "()I", "setDividerHeight", "(I)V", "dividerMarginBottom", "getDividerMarginBottom", "setDividerMarginBottom", "dividerMarginLeft", "getDividerMarginLeft", "setDividerMarginLeft", "dividerMarginRight", "getDividerMarginRight", "setDividerMarginRight", "dividerMarginTop", "getDividerMarginTop", "setDividerMarginTop", "dividerShowMode", "getDividerShowMode", "setDividerShowMode", "dividerWidth", "getDividerWidth", "setDividerWidth", "draw", "", "canvas", "Landroid/graphics/Canvas;", "haveAfterDivider", "", "childIndex", "childCount", "haveBeforeDivider", "initAttribute", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public class DslTabDivider extends DslGradientDrawable {
    private int dividerMarginBottom;
    private int dividerMarginLeft;
    private int dividerMarginRight;
    private int dividerMarginTop;
    private int dividerWidth = LibExKt.getDpi() * 2;
    private int dividerHeight = LibExKt.getDpi() * 2;
    private int dividerShowMode = 2;

    @Override // com.angcyo.tablayout.DslGradientDrawable, com.angcyo.tablayout.AbsDslDrawable, android.graphics.drawable.Drawable
    public void draw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.draw(canvas);
        Drawable originDrawable = getOriginDrawable();
        if (originDrawable != null) {
            originDrawable.setBounds(getBounds());
            originDrawable.draw(canvas);
        }
    }

    public final int getDividerHeight() {
        return this.dividerHeight;
    }

    public final int getDividerMarginBottom() {
        return this.dividerMarginBottom;
    }

    public final int getDividerMarginLeft() {
        return this.dividerMarginLeft;
    }

    public final int getDividerMarginRight() {
        return this.dividerMarginRight;
    }

    public final int getDividerMarginTop() {
        return this.dividerMarginTop;
    }

    public final int getDividerShowMode() {
        return this.dividerShowMode;
    }

    public final int getDividerWidth() {
        return this.dividerWidth;
    }

    @Nullable
    public final DslTabLayout get_tabLayout() {
        if (!(getCallback() instanceof DslTabLayout)) {
            return null;
        }
        Drawable.Callback callback = getCallback();
        Intrinsics.checkNotNull(callback, "null cannot be cast to non-null type com.angcyo.tablayout.DslTabLayout");
        return (DslTabLayout) callback;
    }

    public boolean haveAfterDivider(int childIndex, int childCount) {
        DslTabLayout dslTabLayout = get_tabLayout();
        return (dslTabLayout != null && dslTabLayout.isHorizontal() && dslTabLayout.isLayoutRtl() && childIndex == childCount + (-1)) ? (this.dividerShowMode & 1) != 0 : childIndex == childCount - 1 && (this.dividerShowMode & 4) != 0;
    }

    public boolean haveBeforeDivider(int childIndex, int childCount) {
        DslTabLayout dslTabLayout = get_tabLayout();
        return (dslTabLayout != null && dslTabLayout.isHorizontal() && dslTabLayout.isLayoutRtl()) ? childIndex == 0 ? (this.dividerShowMode & 4) != 0 : (this.dividerShowMode & 2) != 0 : childIndex == 0 ? (this.dividerShowMode & 1) != 0 : (this.dividerShowMode & 2) != 0;
    }

    @Override // com.angcyo.tablayout.AbsDslDrawable
    public void initAttribute(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        Intrinsics.checkNotNullParameter(context, "context");
        super.initAttribute(context, attributeSet);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DslTabLayout);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.obtainStyledAttr…R.styleable.DslTabLayout)");
        this.dividerWidth = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_divider_width, this.dividerWidth);
        this.dividerHeight = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_divider_height, this.dividerHeight);
        this.dividerMarginLeft = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_divider_margin_left, this.dividerMarginLeft);
        this.dividerMarginRight = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_divider_margin_right, this.dividerMarginRight);
        this.dividerMarginTop = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_divider_margin_top, this.dividerMarginTop);
        this.dividerMarginBottom = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_divider_margin_bottom, this.dividerMarginBottom);
        int i2 = R.styleable.DslTabLayout_tab_divider_solid_color;
        if (typedArrayObtainStyledAttributes.hasValue(i2)) {
            setGradientSolidColor(typedArrayObtainStyledAttributes.getColor(i2, getGradientSolidColor()));
        } else {
            int i3 = R.styleable.DslTabLayout_tab_border_stroke_color;
            if (typedArrayObtainStyledAttributes.hasValue(i3)) {
                setGradientSolidColor(typedArrayObtainStyledAttributes.getColor(i3, getGradientSolidColor()));
            } else {
                setGradientSolidColor(typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_deselect_color, getGradientSolidColor()));
            }
        }
        setGradientStrokeColor(typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_divider_stroke_color, getGradientStrokeColor()));
        setGradientStrokeWidth(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_divider_stroke_width, 0));
        cornerRadius(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_divider_radius_size, LibExKt.getDpi() * 2));
        setOriginDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.DslTabLayout_tab_divider_drawable));
        this.dividerShowMode = typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_tab_divider_show_mode, this.dividerShowMode);
        typedArrayObtainStyledAttributes.recycle();
        if (getOriginDrawable() == null) {
            updateOriginDrawable();
        }
    }

    public final void setDividerHeight(int i2) {
        this.dividerHeight = i2;
    }

    public final void setDividerMarginBottom(int i2) {
        this.dividerMarginBottom = i2;
    }

    public final void setDividerMarginLeft(int i2) {
        this.dividerMarginLeft = i2;
    }

    public final void setDividerMarginRight(int i2) {
        this.dividerMarginRight = i2;
    }

    public final void setDividerMarginTop(int i2) {
        this.dividerMarginTop = i2;
    }

    public final void setDividerShowMode(int i2) {
        this.dividerShowMode = i2;
    }

    public final void setDividerWidth(int i2) {
        this.dividerWidth = i2;
    }
}
