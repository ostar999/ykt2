package com.angcyo.tablayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001a\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\u000e\u0010\u0013\u001a\u00020\u000e2\u0006\u0010\u0014\u001a\u00020\u0004R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001c\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\f¨\u0006\u0015"}, d2 = {"Lcom/angcyo/tablayout/DslTabBadge;", "Lcom/angcyo/tablayout/DslBadgeDrawable;", "()V", "defaultBadgeConfig", "Lcom/angcyo/tablayout/TabBadgeConfig;", "getDefaultBadgeConfig", "()Lcom/angcyo/tablayout/TabBadgeConfig;", "xmlBadgeText", "", "getXmlBadgeText", "()Ljava/lang/String;", "setXmlBadgeText", "(Ljava/lang/String;)V", "initAttribute", "", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "updateBadgeConfig", "badgeConfig", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public class DslTabBadge extends DslBadgeDrawable {

    @NotNull
    private final TabBadgeConfig defaultBadgeConfig = new TabBadgeConfig(null, 0, 0, 0, 0, 0, 0.0f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 2097151, null);

    @Nullable
    private String xmlBadgeText;

    @NotNull
    public final TabBadgeConfig getDefaultBadgeConfig() {
        return this.defaultBadgeConfig;
    }

    @Nullable
    public final String getXmlBadgeText() {
        return this.xmlBadgeText;
    }

    @Override // com.angcyo.tablayout.DslBadgeDrawable, com.angcyo.tablayout.AbsDslDrawable
    public void initAttribute(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        Intrinsics.checkNotNullParameter(context, "context");
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DslTabLayout);
        Intrinsics.checkNotNullExpressionValue(typedArrayObtainStyledAttributes, "context.obtainStyledAttr…R.styleable.DslTabLayout)");
        setGradientSolidColor(typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_badge_solid_color, this.defaultBadgeConfig.getBadgeSolidColor()));
        this.defaultBadgeConfig.setBadgeSolidColor(getGradientSolidColor());
        setBadgeTextColor(typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_badge_text_color, this.defaultBadgeConfig.getBadgeTextColor()));
        this.defaultBadgeConfig.setBadgeTextColor(getBadgeTextColor());
        setGradientStrokeColor(typedArrayObtainStyledAttributes.getColor(R.styleable.DslTabLayout_tab_badge_stroke_color, this.defaultBadgeConfig.getBadgeStrokeColor()));
        this.defaultBadgeConfig.setBadgeStrokeColor(getGradientStrokeColor());
        setGradientStrokeWidth(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_badge_stroke_width, this.defaultBadgeConfig.getBadgeStrokeWidth()));
        this.defaultBadgeConfig.setBadgeStrokeWidth(getGradientStrokeWidth());
        setBadgeGravity(typedArrayObtainStyledAttributes.getInt(R.styleable.DslTabLayout_tab_badge_gravity, this.defaultBadgeConfig.getBadgeGravity()));
        this.defaultBadgeConfig.setBadgeGravity(getBadgeGravity());
        setBadgeOffsetX(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_badge_offset_x, this.defaultBadgeConfig.getBadgeOffsetX()));
        this.defaultBadgeConfig.setBadgeOffsetX(getBadgeOffsetX());
        setBadgeOffsetY(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_badge_offset_y, this.defaultBadgeConfig.getBadgeOffsetY()));
        this.defaultBadgeConfig.setBadgeOffsetY(getBadgeOffsetY());
        setBadgeCircleOffsetX(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_badge_circle_offset_x, this.defaultBadgeConfig.getBadgeOffsetX()));
        this.defaultBadgeConfig.setBadgeCircleOffsetX(getBadgeCircleOffsetX());
        setBadgeCircleOffsetY(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_badge_circle_offset_y, this.defaultBadgeConfig.getBadgeOffsetY()));
        this.defaultBadgeConfig.setBadgeCircleOffsetY(getBadgeCircleOffsetY());
        setBadgeCircleRadius(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_badge_circle_radius, this.defaultBadgeConfig.getBadgeCircleRadius()));
        this.defaultBadgeConfig.setBadgeCircleRadius(getBadgeCircleRadius());
        int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_badge_radius, this.defaultBadgeConfig.getBadgeRadius());
        cornerRadius(dimensionPixelOffset);
        this.defaultBadgeConfig.setBadgeRadius(dimensionPixelOffset);
        setBadgePaddingLeft(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_badge_padding_left, this.defaultBadgeConfig.getBadgePaddingLeft()));
        this.defaultBadgeConfig.setBadgePaddingLeft(getBadgePaddingLeft());
        setBadgePaddingRight(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_badge_padding_right, this.defaultBadgeConfig.getBadgePaddingRight()));
        this.defaultBadgeConfig.setBadgePaddingRight(getBadgePaddingRight());
        setBadgePaddingTop(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_badge_padding_top, this.defaultBadgeConfig.getBadgePaddingTop()));
        this.defaultBadgeConfig.setBadgePaddingTop(getBadgePaddingTop());
        setBadgePaddingBottom(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_badge_padding_bottom, this.defaultBadgeConfig.getBadgePaddingBottom()));
        this.defaultBadgeConfig.setBadgePaddingBottom(getBadgePaddingBottom());
        this.xmlBadgeText = typedArrayObtainStyledAttributes.getString(R.styleable.DslTabLayout_tab_badge_text);
        setBadgeTextSize(typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.DslTabLayout_tab_badge_text_size, (int) this.defaultBadgeConfig.getBadgeTextSize()));
        this.defaultBadgeConfig.setBadgeTextSize(getBadgeTextSize());
        TabBadgeConfig tabBadgeConfig = this.defaultBadgeConfig;
        tabBadgeConfig.setBadgeAnchorChildIndex(typedArrayObtainStyledAttributes.getInteger(R.styleable.DslTabLayout_tab_badge_anchor_child_index, tabBadgeConfig.getBadgeAnchorChildIndex()));
        TabBadgeConfig tabBadgeConfig2 = this.defaultBadgeConfig;
        tabBadgeConfig2.setBadgeIgnoreChildPadding(typedArrayObtainStyledAttributes.getBoolean(R.styleable.DslTabLayout_tab_badge_ignore_child_padding, tabBadgeConfig2.getBadgeIgnoreChildPadding()));
        TabBadgeConfig tabBadgeConfig3 = this.defaultBadgeConfig;
        tabBadgeConfig3.setBadgeMinWidth(typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.DslTabLayout_tab_badge_min_width, tabBadgeConfig3.getBadgeMinWidth()));
        TabBadgeConfig tabBadgeConfig4 = this.defaultBadgeConfig;
        tabBadgeConfig4.setBadgeMinHeight(typedArrayObtainStyledAttributes.getLayoutDimension(R.styleable.DslTabLayout_tab_badge_min_height, tabBadgeConfig4.getBadgeMinHeight()));
        typedArrayObtainStyledAttributes.recycle();
        super.initAttribute(context, attributeSet);
    }

    public final void setXmlBadgeText(@Nullable String str) {
        this.xmlBadgeText = str;
    }

    public final void updateBadgeConfig(@NotNull TabBadgeConfig badgeConfig) {
        Intrinsics.checkNotNullParameter(badgeConfig, "badgeConfig");
        setGradientSolidColor(badgeConfig.getBadgeSolidColor());
        setGradientStrokeColor(badgeConfig.getBadgeStrokeColor());
        setGradientStrokeWidth(badgeConfig.getBadgeStrokeWidth());
        setBadgeTextColor(badgeConfig.getBadgeTextColor());
        setBadgeGravity(badgeConfig.getBadgeGravity());
        setBadgeOffsetX(badgeConfig.getBadgeOffsetX());
        setBadgeOffsetY(badgeConfig.getBadgeOffsetY());
        setBadgeCircleOffsetX(badgeConfig.getBadgeCircleOffsetX());
        setBadgeCircleOffsetY(badgeConfig.getBadgeCircleOffsetY());
        setBadgeCircleRadius(badgeConfig.getBadgeCircleRadius());
        setBadgePaddingLeft(badgeConfig.getBadgePaddingLeft());
        setBadgePaddingRight(badgeConfig.getBadgePaddingRight());
        setBadgePaddingTop(badgeConfig.getBadgePaddingTop());
        setBadgePaddingBottom(badgeConfig.getBadgePaddingBottom());
        setBadgeTextSize(badgeConfig.getBadgeTextSize());
        cornerRadius(badgeConfig.getBadgeRadius());
        setBadgeMinHeight(badgeConfig.getBadgeMinHeight());
        setBadgeMinWidth(badgeConfig.getBadgeMinWidth());
        setBadgeText(badgeConfig.getBadgeText());
    }
}
