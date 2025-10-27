package com.angcyo.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.view.ViewCompat;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u0015\n\u0002\b\u0017\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u00100\u001a\u0002012\u0006\u00102\u001a\u000203H\u0016J\u000e\u00104\u001a\u0002012\u0006\u00102\u001a\u000203J\u001a\u00105\u001a\u0002012\u0006\u00106\u001a\u0002072\b\u00108\u001a\u0004\u0018\u000109H\u0016J(\u0010:\u001a\u0002012\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020>2\u0006\u0010?\u001a\u00020\n2\u0006\u0010@\u001a\u00020\u0013H\u0016R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000eR\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001e\u0010\u001e\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u0010\n\u0002\u0010#\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u001e\u0010$\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u0010\n\u0002\u0010#\u001a\u0004\b%\u0010 \"\u0004\b&\u0010\"R\u001a\u0010'\u001a\u00020\u0013X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0015\"\u0004\b)\u0010\u0017R\u001c\u0010*\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b+\u0010\u0006\"\u0004\b,\u0010\bR\u001c\u0010-\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0006\"\u0004\b/\u0010\b¨\u0006A"}, d2 = {"Lcom/angcyo/tablayout/DslTabBorder;", "Lcom/angcyo/tablayout/DslGradientDrawable;", "()V", "borderBackgroundDrawable", "Landroid/graphics/drawable/Drawable;", "getBorderBackgroundDrawable", "()Landroid/graphics/drawable/Drawable;", "setBorderBackgroundDrawable", "(Landroid/graphics/drawable/Drawable;)V", "borderBackgroundHeightOffset", "", "getBorderBackgroundHeightOffset", "()I", "setBorderBackgroundHeightOffset", "(I)V", "borderBackgroundWidthOffset", "getBorderBackgroundWidthOffset", "setBorderBackgroundWidthOffset", "borderDrawItemBackground", "", "getBorderDrawItemBackground", "()Z", "setBorderDrawItemBackground", "(Z)V", "borderItemBackgroundGradientColors", "", "getBorderItemBackgroundGradientColors", "()[I", "setBorderItemBackgroundGradientColors", "([I)V", "borderItemBackgroundSolidColor", "getBorderItemBackgroundSolidColor", "()Ljava/lang/Integer;", "setBorderItemBackgroundSolidColor", "(Ljava/lang/Integer;)V", "Ljava/lang/Integer;", "borderItemBackgroundSolidDisableColor", "getBorderItemBackgroundSolidDisableColor", "setBorderItemBackgroundSolidDisableColor", "borderKeepItemRadius", "getBorderKeepItemRadius", "setBorderKeepItemRadius", "itemDeselectBgDrawable", "getItemDeselectBgDrawable", "setItemDeselectBgDrawable", "itemSelectBgDrawable", "getItemSelectBgDrawable", "setItemSelectBgDrawable", "draw", "", "canvas", "Landroid/graphics/Canvas;", "drawBorderBackground", "initAttribute", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "updateItemBackground", "tabLayout", "Lcom/angcyo/tablayout/DslTabLayout;", "itemView", "Landroid/view/View;", "index", "select", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public class DslTabBorder extends DslGradientDrawable {

    @Nullable
    private Drawable borderBackgroundDrawable;
    private int borderBackgroundHeightOffset;
    private int borderBackgroundWidthOffset;
    private boolean borderDrawItemBackground = true;

    @Nullable
    private int[] borderItemBackgroundGradientColors;

    @Nullable
    private Integer borderItemBackgroundSolidColor;

    @Nullable
    private Integer borderItemBackgroundSolidDisableColor;
    private boolean borderKeepItemRadius;

    @Nullable
    private Drawable itemDeselectBgDrawable;

    @Nullable
    private Drawable itemSelectBgDrawable;

    @Override // com.angcyo.tablayout.DslGradientDrawable, com.angcyo.tablayout.AbsDslDrawable, android.graphics.drawable.Drawable
    public void draw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.draw(canvas);
        Drawable originDrawable = getOriginDrawable();
        if (originDrawable != null) {
            originDrawable.setBounds(getPaddingLeft(), getPaddingBottom(), getViewWidth() - getPaddingRight(), getViewHeight() - getPaddingBottom());
            originDrawable.draw(canvas);
        }
    }

    public final void drawBorderBackground(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.draw(canvas);
        Drawable drawable = this.borderBackgroundDrawable;
        if (drawable != null) {
            drawable.setBounds(getPaddingLeft(), getPaddingBottom(), getViewWidth() - getPaddingRight(), getViewHeight() - getPaddingBottom());
            drawable.draw(canvas);
        }
    }

    @Nullable
    public final Drawable getBorderBackgroundDrawable() {
        return this.borderBackgroundDrawable;
    }

    public final int getBorderBackgroundHeightOffset() {
        return this.borderBackgroundHeightOffset;
    }

    public final int getBorderBackgroundWidthOffset() {
        return this.borderBackgroundWidthOffset;
    }

    public final boolean getBorderDrawItemBackground() {
        return this.borderDrawItemBackground;
    }

    @Nullable
    public final int[] getBorderItemBackgroundGradientColors() {
        return this.borderItemBackgroundGradientColors;
    }

    @Nullable
    public final Integer getBorderItemBackgroundSolidColor() {
        return this.borderItemBackgroundSolidColor;
    }

    @Nullable
    public final Integer getBorderItemBackgroundSolidDisableColor() {
        return this.borderItemBackgroundSolidDisableColor;
    }

    public final boolean getBorderKeepItemRadius() {
        return this.borderKeepItemRadius;
    }

    @Nullable
    public final Drawable getItemDeselectBgDrawable() {
        return this.itemDeselectBgDrawable;
    }

    @Nullable
    public final Drawable getItemSelectBgDrawable() {
        return this.itemSelectBgDrawable;
    }

    @Override // com.angcyo.tablayout.AbsDslDrawable
    public void initAttribute(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        Intrinsics.checkNotNullParameter(context, "context");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DslTabLayout);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.obtainStyledAttr…R.styleable.DslTabLayout)");
        final int color = typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_border_solid_color, getGradientSolidColor());
        setGradientStrokeColor(typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_border_stroke_color, getGradientStrokeColor()));
        setGradientStrokeWidth(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_border_stroke_width, LibExKt.getDpi() * 2));
        cornerRadius(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_border_radius_size, 0));
        setOriginDrawable(typedArrayObtainStyledAttributes.getDrawable(R.styleable.DslTabLayout_tab_border_drawable));
        this.borderDrawItemBackground = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_border_draw_item_background, this.borderDrawItemBackground);
        this.borderKeepItemRadius = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_border_keep_item_radius, this.borderKeepItemRadius);
        this.borderBackgroundWidthOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_border_item_background_width_offset, this.borderBackgroundWidthOffset);
        this.borderBackgroundHeightOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_border_item_background_height_offset, this.borderBackgroundHeightOffset);
        int i2 = R.styleable.DslTabLayout_tab_border_item_background_solid_color;
        if (typedArrayObtainStyledAttributes.hasValue(i2)) {
            this.borderItemBackgroundSolidColor = Integer.valueOf(typedArrayObtainStyledAttributes.getColor(i2, getGradientStrokeColor()));
        }
        int i3 = R.styleable.DslTabLayout_tab_border_item_background_solid_disable_color;
        if (typedArrayObtainStyledAttributes.hasValue(i3)) {
            Integer num = this.borderItemBackgroundSolidColor;
            this.borderItemBackgroundSolidDisableColor = Integer.valueOf(typedArrayObtainStyledAttributes.getColor(i3, num != null ? num.intValue() : getGradientStrokeColor()));
        }
        int i4 = R.styleable.DslTabLayout_tab_border_item_background_gradient_start_color;
        if (typedArrayObtainStyledAttributes.hasValue(i4) || typedArrayObtainStyledAttributes.hasValue(R.styleable.DslTabLayout_tab_border_item_background_gradient_end_color)) {
            this.borderItemBackgroundGradientColors = new int[]{typedArrayObtainStyledAttributes.getColor(i4, getGradientStrokeColor()), typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_border_item_background_gradient_end_color, getGradientStrokeColor())};
        }
        typedArrayObtainStyledAttributes.recycle();
        if (getOriginDrawable() == null) {
            this.borderBackgroundDrawable = new DslGradientDrawable().configDrawable(new Function1<DslGradientDrawable, Unit>() { // from class: com.angcyo.tablayout.DslTabBorder.initAttribute.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(DslGradientDrawable dslGradientDrawable) {
                    invoke2(dslGradientDrawable);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull DslGradientDrawable configDrawable) {
                    Intrinsics.checkNotNullParameter(configDrawable, "$this$configDrawable");
                    configDrawable.setGradientSolidColor(color);
                    configDrawable.setGradientRadii(this.getGradientRadii());
                }
            }).getOriginDrawable();
            updateOriginDrawable();
        }
    }

    public final void setBorderBackgroundDrawable(@Nullable Drawable drawable) {
        this.borderBackgroundDrawable = drawable;
    }

    public final void setBorderBackgroundHeightOffset(int i2) {
        this.borderBackgroundHeightOffset = i2;
    }

    public final void setBorderBackgroundWidthOffset(int i2) {
        this.borderBackgroundWidthOffset = i2;
    }

    public final void setBorderDrawItemBackground(boolean z2) {
        this.borderDrawItemBackground = z2;
    }

    public final void setBorderItemBackgroundGradientColors(@Nullable int[] iArr) {
        this.borderItemBackgroundGradientColors = iArr;
    }

    public final void setBorderItemBackgroundSolidColor(@Nullable Integer num) {
        this.borderItemBackgroundSolidColor = num;
    }

    public final void setBorderItemBackgroundSolidDisableColor(@Nullable Integer num) {
        this.borderItemBackgroundSolidDisableColor = num;
    }

    public final void setBorderKeepItemRadius(boolean z2) {
        this.borderKeepItemRadius = z2;
    }

    public final void setItemDeselectBgDrawable(@Nullable Drawable drawable) {
        this.itemDeselectBgDrawable = drawable;
    }

    public final void setItemSelectBgDrawable(@Nullable Drawable drawable) {
        this.itemSelectBgDrawable = drawable;
    }

    public void updateItemBackground(@NotNull final DslTabLayout tabLayout, @NotNull View itemView, int index, boolean select) {
        Intrinsics.checkNotNullParameter(tabLayout, "tabLayout");
        Intrinsics.checkNotNullParameter(itemView, "itemView");
        if (this.borderDrawItemBackground) {
            if (!select) {
                ViewCompat.setBackground(itemView, this.itemDeselectBgDrawable);
                return;
            }
            final boolean z2 = index == 0;
            final boolean z3 = index == tabLayout.getDslSelector().getVisibleViewList().size() - 1;
            DslGradientDrawable dslGradientDrawableConfigDrawable = new DslGradientDrawable().configDrawable(new Function1<DslGradientDrawable, Unit>() { // from class: com.angcyo.tablayout.DslTabBorder$updateItemBackground$drawable$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public /* bridge */ /* synthetic */ Unit invoke(DslGradientDrawable dslGradientDrawable) {
                    invoke2(dslGradientDrawable);
                    return Unit.INSTANCE;
                }

                /* renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(@NotNull DslGradientDrawable configDrawable) {
                    Intrinsics.checkNotNullParameter(configDrawable, "$this$configDrawable");
                    configDrawable.setGradientWidthOffset(this.this$0.getBorderBackgroundWidthOffset());
                    configDrawable.setGradientHeightOffset(this.this$0.getBorderBackgroundHeightOffset());
                    Integer borderItemBackgroundSolidColor = this.this$0.getBorderItemBackgroundSolidColor();
                    configDrawable.setGradientSolidColor(borderItemBackgroundSolidColor != null ? borderItemBackgroundSolidColor.intValue() : this.this$0.getGradientStrokeColor());
                    if (!tabLayout.getItemEnableSelector() && this.this$0.getBorderItemBackgroundSolidDisableColor() != null) {
                        Integer borderItemBackgroundSolidDisableColor = this.this$0.getBorderItemBackgroundSolidDisableColor();
                        Intrinsics.checkNotNull(borderItemBackgroundSolidDisableColor);
                        configDrawable.setGradientSolidColor(borderItemBackgroundSolidDisableColor.intValue());
                    }
                    configDrawable.setGradientColors(this.this$0.getBorderItemBackgroundGradientColors());
                    if ((z2 && z3) || this.this$0.getBorderKeepItemRadius()) {
                        configDrawable.setGradientRadii(this.this$0.getGradientRadii());
                        return;
                    }
                    if (z2) {
                        if (!tabLayout.isHorizontal()) {
                            configDrawable.setGradientRadii(new float[]{this.this$0.getGradientRadii()[0], this.this$0.getGradientRadii()[1], this.this$0.getGradientRadii()[2], this.this$0.getGradientRadii()[3], 0.0f, 0.0f, 0.0f, 0.0f});
                            return;
                        } else if (tabLayout.isLayoutRtl()) {
                            configDrawable.setGradientRadii(new float[]{0.0f, 0.0f, this.this$0.getGradientRadii()[2], this.this$0.getGradientRadii()[3], this.this$0.getGradientRadii()[4], this.this$0.getGradientRadii()[5], 0.0f, 0.0f});
                            return;
                        } else {
                            configDrawable.setGradientRadii(new float[]{this.this$0.getGradientRadii()[0], this.this$0.getGradientRadii()[1], 0.0f, 0.0f, 0.0f, 0.0f, this.this$0.getGradientRadii()[6], this.this$0.getGradientRadii()[7]});
                            return;
                        }
                    }
                    if (z3) {
                        if (!tabLayout.isHorizontal()) {
                            configDrawable.setGradientRadii(new float[]{0.0f, 0.0f, 0.0f, 0.0f, this.this$0.getGradientRadii()[4], this.this$0.getGradientRadii()[5], this.this$0.getGradientRadii()[6], this.this$0.getGradientRadii()[7]});
                        } else if (tabLayout.isLayoutRtl()) {
                            configDrawable.setGradientRadii(new float[]{this.this$0.getGradientRadii()[0], this.this$0.getGradientRadii()[1], 0.0f, 0.0f, 0.0f, 0.0f, this.this$0.getGradientRadii()[6], this.this$0.getGradientRadii()[7]});
                        } else {
                            configDrawable.setGradientRadii(new float[]{0.0f, 0.0f, this.this$0.getGradientRadii()[2], this.this$0.getGradientRadii()[3], this.this$0.getGradientRadii()[4], this.this$0.getGradientRadii()[5], 0.0f, 0.0f});
                        }
                    }
                }
            });
            this.itemSelectBgDrawable = dslGradientDrawableConfigDrawable;
            ViewCompat.setBackground(itemView, dslGradientDrawableConfigDrawable);
        }
    }
}
