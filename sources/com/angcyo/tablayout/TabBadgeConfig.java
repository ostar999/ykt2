package com.angcyo.tablayout;

import androidx.annotation.Px;
import androidx.core.internal.view.SupportMenu;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\bP\b\u0086\b\u0018\u00002\u00020\u0001BÙ\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0003\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0016\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0018\u0012\b\b\u0002\u0010\u0019\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u001a\u001a\u00020\u0005¢\u0006\u0002\u0010\u001bJ\u000b\u0010N\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010O\u001a\u00020\u0005HÆ\u0003J\t\u0010P\u001a\u00020\u0005HÆ\u0003J\t\u0010Q\u001a\u00020\u0005HÆ\u0003J\t\u0010R\u001a\u00020\u0005HÆ\u0003J\t\u0010S\u001a\u00020\u0005HÆ\u0003J\t\u0010T\u001a\u00020\u0005HÆ\u0003J\t\u0010U\u001a\u00020\u0005HÆ\u0003J\t\u0010V\u001a\u00020\u0005HÆ\u0003J\t\u0010W\u001a\u00020\u0005HÆ\u0003J\t\u0010X\u001a\u00020\u0018HÆ\u0003J\t\u0010Y\u001a\u00020\u0005HÆ\u0003J\t\u0010Z\u001a\u00020\u0005HÆ\u0003J\t\u0010[\u001a\u00020\u0005HÆ\u0003J\t\u0010\\\u001a\u00020\u0005HÆ\u0003J\t\u0010]\u001a\u00020\u0005HÆ\u0003J\t\u0010^\u001a\u00020\u0005HÆ\u0003J\t\u0010_\u001a\u00020\u0005HÆ\u0003J\t\u0010`\u001a\u00020\u000bHÆ\u0003J\t\u0010a\u001a\u00020\u0005HÆ\u0003J\t\u0010b\u001a\u00020\u0005HÆ\u0003JÝ\u0001\u0010c\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00052\b\b\u0002\u0010\b\u001a\u00020\u00052\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0003\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u00052\b\b\u0002\u0010\u000f\u001a\u00020\u00052\b\b\u0002\u0010\u0010\u001a\u00020\u00052\b\b\u0002\u0010\u0011\u001a\u00020\u00052\b\b\u0002\u0010\u0012\u001a\u00020\u00052\b\b\u0002\u0010\u0013\u001a\u00020\u00052\b\b\u0002\u0010\u0014\u001a\u00020\u00052\b\b\u0002\u0010\u0015\u001a\u00020\u00052\b\b\u0002\u0010\u0016\u001a\u00020\u00052\b\b\u0002\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u00052\b\b\u0002\u0010\u001a\u001a\u00020\u0005HÆ\u0001J\u0013\u0010d\u001a\u00020\u00182\b\u0010e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010f\u001a\u00020\u0005HÖ\u0001J\t\u0010g\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0016\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\u001a\u0010\u0010\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u001d\"\u0004\b!\u0010\u001fR\u001a\u0010\u0011\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u001d\"\u0004\b#\u0010\u001fR\u001a\u0010\f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u001d\"\u0004\b%\u0010\u001fR\u001a\u0010\u0004\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u001d\"\u0004\b'\u0010\u001fR\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001a\u0010\u0019\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u001d\"\u0004\b-\u0010\u001fR\u001a\u0010\u001a\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u001d\"\u0004\b/\u0010\u001fR\u001a\u0010\u000e\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u001d\"\u0004\b1\u0010\u001fR\u001a\u0010\u000f\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u001d\"\u0004\b3\u0010\u001fR\u001a\u0010\u0015\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\u001d\"\u0004\b5\u0010\u001fR\u001a\u0010\u0012\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u001d\"\u0004\b7\u0010\u001fR\u001a\u0010\u0013\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b8\u0010\u001d\"\u0004\b9\u0010\u001fR\u001a\u0010\u0014\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b:\u0010\u001d\"\u0004\b;\u0010\u001fR\u001a\u0010\r\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b<\u0010\u001d\"\u0004\b=\u0010\u001fR\u001a\u0010\u0006\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b>\u0010\u001d\"\u0004\b?\u0010\u001fR\u001a\u0010\u0007\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b@\u0010\u001d\"\u0004\bA\u0010\u001fR\u001a\u0010\b\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bB\u0010\u001d\"\u0004\bC\u0010\u001fR\u001c\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bD\u0010E\"\u0004\bF\u0010GR\u001a\u0010\t\u001a\u00020\u0005X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bH\u0010\u001d\"\u0004\bI\u0010\u001fR\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010M¨\u0006h"}, d2 = {"Lcom/angcyo/tablayout/TabBadgeConfig;", "", "badgeText", "", "badgeGravity", "", "badgeSolidColor", "badgeStrokeColor", "badgeStrokeWidth", "badgeTextColor", "badgeTextSize", "", "badgeCircleRadius", "badgeRadius", "badgeOffsetX", "badgeOffsetY", "badgeCircleOffsetX", "badgeCircleOffsetY", "badgePaddingLeft", "badgePaddingRight", "badgePaddingTop", "badgePaddingBottom", "badgeAnchorChildIndex", "badgeIgnoreChildPadding", "", "badgeMinHeight", "badgeMinWidth", "(Ljava/lang/String;IIIIIFIIIIIIIIIIIZII)V", "getBadgeAnchorChildIndex", "()I", "setBadgeAnchorChildIndex", "(I)V", "getBadgeCircleOffsetX", "setBadgeCircleOffsetX", "getBadgeCircleOffsetY", "setBadgeCircleOffsetY", "getBadgeCircleRadius", "setBadgeCircleRadius", "getBadgeGravity", "setBadgeGravity", "getBadgeIgnoreChildPadding", "()Z", "setBadgeIgnoreChildPadding", "(Z)V", "getBadgeMinHeight", "setBadgeMinHeight", "getBadgeMinWidth", "setBadgeMinWidth", "getBadgeOffsetX", "setBadgeOffsetX", "getBadgeOffsetY", "setBadgeOffsetY", "getBadgePaddingBottom", "setBadgePaddingBottom", "getBadgePaddingLeft", "setBadgePaddingLeft", "getBadgePaddingRight", "setBadgePaddingRight", "getBadgePaddingTop", "setBadgePaddingTop", "getBadgeRadius", "setBadgeRadius", "getBadgeSolidColor", "setBadgeSolidColor", "getBadgeStrokeColor", "setBadgeStrokeColor", "getBadgeStrokeWidth", "setBadgeStrokeWidth", "getBadgeText", "()Ljava/lang/String;", "setBadgeText", "(Ljava/lang/String;)V", "getBadgeTextColor", "setBadgeTextColor", "getBadgeTextSize", "()F", "setBadgeTextSize", "(F)V", "component1", "component10", "component11", "component12", "component13", "component14", "component15", "component16", "component17", "component18", "component19", "component2", "component20", "component21", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "toString", "TabLayout_release"}, k = 1, mv = {1, 7, 1}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class TabBadgeConfig {
    private int badgeAnchorChildIndex;
    private int badgeCircleOffsetX;
    private int badgeCircleOffsetY;
    private int badgeCircleRadius;
    private int badgeGravity;
    private boolean badgeIgnoreChildPadding;
    private int badgeMinHeight;
    private int badgeMinWidth;
    private int badgeOffsetX;
    private int badgeOffsetY;
    private int badgePaddingBottom;
    private int badgePaddingLeft;
    private int badgePaddingRight;
    private int badgePaddingTop;
    private int badgeRadius;
    private int badgeSolidColor;
    private int badgeStrokeColor;
    private int badgeStrokeWidth;

    @Nullable
    private String badgeText;
    private int badgeTextColor;
    private float badgeTextSize;

    public TabBadgeConfig() {
        this(null, 0, 0, 0, 0, 0, 0.0f, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0, 2097151, null);
    }

    public TabBadgeConfig(@Nullable String str, int i2, int i3, int i4, int i5, int i6, @Px float f2, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, boolean z2, int i18, int i19) {
        this.badgeText = str;
        this.badgeGravity = i2;
        this.badgeSolidColor = i3;
        this.badgeStrokeColor = i4;
        this.badgeStrokeWidth = i5;
        this.badgeTextColor = i6;
        this.badgeTextSize = f2;
        this.badgeCircleRadius = i7;
        this.badgeRadius = i8;
        this.badgeOffsetX = i9;
        this.badgeOffsetY = i10;
        this.badgeCircleOffsetX = i11;
        this.badgeCircleOffsetY = i12;
        this.badgePaddingLeft = i13;
        this.badgePaddingRight = i14;
        this.badgePaddingTop = i15;
        this.badgePaddingBottom = i16;
        this.badgeAnchorChildIndex = i17;
        this.badgeIgnoreChildPadding = z2;
        this.badgeMinHeight = i18;
        this.badgeMinWidth = i19;
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getBadgeText() {
        return this.badgeText;
    }

    /* renamed from: component10, reason: from getter */
    public final int getBadgeOffsetX() {
        return this.badgeOffsetX;
    }

    /* renamed from: component11, reason: from getter */
    public final int getBadgeOffsetY() {
        return this.badgeOffsetY;
    }

    /* renamed from: component12, reason: from getter */
    public final int getBadgeCircleOffsetX() {
        return this.badgeCircleOffsetX;
    }

    /* renamed from: component13, reason: from getter */
    public final int getBadgeCircleOffsetY() {
        return this.badgeCircleOffsetY;
    }

    /* renamed from: component14, reason: from getter */
    public final int getBadgePaddingLeft() {
        return this.badgePaddingLeft;
    }

    /* renamed from: component15, reason: from getter */
    public final int getBadgePaddingRight() {
        return this.badgePaddingRight;
    }

    /* renamed from: component16, reason: from getter */
    public final int getBadgePaddingTop() {
        return this.badgePaddingTop;
    }

    /* renamed from: component17, reason: from getter */
    public final int getBadgePaddingBottom() {
        return this.badgePaddingBottom;
    }

    /* renamed from: component18, reason: from getter */
    public final int getBadgeAnchorChildIndex() {
        return this.badgeAnchorChildIndex;
    }

    /* renamed from: component19, reason: from getter */
    public final boolean getBadgeIgnoreChildPadding() {
        return this.badgeIgnoreChildPadding;
    }

    /* renamed from: component2, reason: from getter */
    public final int getBadgeGravity() {
        return this.badgeGravity;
    }

    /* renamed from: component20, reason: from getter */
    public final int getBadgeMinHeight() {
        return this.badgeMinHeight;
    }

    /* renamed from: component21, reason: from getter */
    public final int getBadgeMinWidth() {
        return this.badgeMinWidth;
    }

    /* renamed from: component3, reason: from getter */
    public final int getBadgeSolidColor() {
        return this.badgeSolidColor;
    }

    /* renamed from: component4, reason: from getter */
    public final int getBadgeStrokeColor() {
        return this.badgeStrokeColor;
    }

    /* renamed from: component5, reason: from getter */
    public final int getBadgeStrokeWidth() {
        return this.badgeStrokeWidth;
    }

    /* renamed from: component6, reason: from getter */
    public final int getBadgeTextColor() {
        return this.badgeTextColor;
    }

    /* renamed from: component7, reason: from getter */
    public final float getBadgeTextSize() {
        return this.badgeTextSize;
    }

    /* renamed from: component8, reason: from getter */
    public final int getBadgeCircleRadius() {
        return this.badgeCircleRadius;
    }

    /* renamed from: component9, reason: from getter */
    public final int getBadgeRadius() {
        return this.badgeRadius;
    }

    @NotNull
    public final TabBadgeConfig copy(@Nullable String badgeText, int badgeGravity, int badgeSolidColor, int badgeStrokeColor, int badgeStrokeWidth, int badgeTextColor, @Px float badgeTextSize, int badgeCircleRadius, int badgeRadius, int badgeOffsetX, int badgeOffsetY, int badgeCircleOffsetX, int badgeCircleOffsetY, int badgePaddingLeft, int badgePaddingRight, int badgePaddingTop, int badgePaddingBottom, int badgeAnchorChildIndex, boolean badgeIgnoreChildPadding, int badgeMinHeight, int badgeMinWidth) {
        return new TabBadgeConfig(badgeText, badgeGravity, badgeSolidColor, badgeStrokeColor, badgeStrokeWidth, badgeTextColor, badgeTextSize, badgeCircleRadius, badgeRadius, badgeOffsetX, badgeOffsetY, badgeCircleOffsetX, badgeCircleOffsetY, badgePaddingLeft, badgePaddingRight, badgePaddingTop, badgePaddingBottom, badgeAnchorChildIndex, badgeIgnoreChildPadding, badgeMinHeight, badgeMinWidth);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TabBadgeConfig)) {
            return false;
        }
        TabBadgeConfig tabBadgeConfig = (TabBadgeConfig) other;
        return Intrinsics.areEqual(this.badgeText, tabBadgeConfig.badgeText) && this.badgeGravity == tabBadgeConfig.badgeGravity && this.badgeSolidColor == tabBadgeConfig.badgeSolidColor && this.badgeStrokeColor == tabBadgeConfig.badgeStrokeColor && this.badgeStrokeWidth == tabBadgeConfig.badgeStrokeWidth && this.badgeTextColor == tabBadgeConfig.badgeTextColor && Float.compare(this.badgeTextSize, tabBadgeConfig.badgeTextSize) == 0 && this.badgeCircleRadius == tabBadgeConfig.badgeCircleRadius && this.badgeRadius == tabBadgeConfig.badgeRadius && this.badgeOffsetX == tabBadgeConfig.badgeOffsetX && this.badgeOffsetY == tabBadgeConfig.badgeOffsetY && this.badgeCircleOffsetX == tabBadgeConfig.badgeCircleOffsetX && this.badgeCircleOffsetY == tabBadgeConfig.badgeCircleOffsetY && this.badgePaddingLeft == tabBadgeConfig.badgePaddingLeft && this.badgePaddingRight == tabBadgeConfig.badgePaddingRight && this.badgePaddingTop == tabBadgeConfig.badgePaddingTop && this.badgePaddingBottom == tabBadgeConfig.badgePaddingBottom && this.badgeAnchorChildIndex == tabBadgeConfig.badgeAnchorChildIndex && this.badgeIgnoreChildPadding == tabBadgeConfig.badgeIgnoreChildPadding && this.badgeMinHeight == tabBadgeConfig.badgeMinHeight && this.badgeMinWidth == tabBadgeConfig.badgeMinWidth;
    }

    public final int getBadgeAnchorChildIndex() {
        return this.badgeAnchorChildIndex;
    }

    public final int getBadgeCircleOffsetX() {
        return this.badgeCircleOffsetX;
    }

    public final int getBadgeCircleOffsetY() {
        return this.badgeCircleOffsetY;
    }

    public final int getBadgeCircleRadius() {
        return this.badgeCircleRadius;
    }

    public final int getBadgeGravity() {
        return this.badgeGravity;
    }

    public final boolean getBadgeIgnoreChildPadding() {
        return this.badgeIgnoreChildPadding;
    }

    public final int getBadgeMinHeight() {
        return this.badgeMinHeight;
    }

    public final int getBadgeMinWidth() {
        return this.badgeMinWidth;
    }

    public final int getBadgeOffsetX() {
        return this.badgeOffsetX;
    }

    public final int getBadgeOffsetY() {
        return this.badgeOffsetY;
    }

    public final int getBadgePaddingBottom() {
        return this.badgePaddingBottom;
    }

    public final int getBadgePaddingLeft() {
        return this.badgePaddingLeft;
    }

    public final int getBadgePaddingRight() {
        return this.badgePaddingRight;
    }

    public final int getBadgePaddingTop() {
        return this.badgePaddingTop;
    }

    public final int getBadgeRadius() {
        return this.badgeRadius;
    }

    public final int getBadgeSolidColor() {
        return this.badgeSolidColor;
    }

    public final int getBadgeStrokeColor() {
        return this.badgeStrokeColor;
    }

    public final int getBadgeStrokeWidth() {
        return this.badgeStrokeWidth;
    }

    @Nullable
    public final String getBadgeText() {
        return this.badgeText;
    }

    public final int getBadgeTextColor() {
        return this.badgeTextColor;
    }

    public final float getBadgeTextSize() {
        return this.badgeTextSize;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        String str = this.badgeText;
        int iHashCode = (((((((((((((((((((((((((((((((((((str == null ? 0 : str.hashCode()) * 31) + this.badgeGravity) * 31) + this.badgeSolidColor) * 31) + this.badgeStrokeColor) * 31) + this.badgeStrokeWidth) * 31) + this.badgeTextColor) * 31) + Float.floatToIntBits(this.badgeTextSize)) * 31) + this.badgeCircleRadius) * 31) + this.badgeRadius) * 31) + this.badgeOffsetX) * 31) + this.badgeOffsetY) * 31) + this.badgeCircleOffsetX) * 31) + this.badgeCircleOffsetY) * 31) + this.badgePaddingLeft) * 31) + this.badgePaddingRight) * 31) + this.badgePaddingTop) * 31) + this.badgePaddingBottom) * 31) + this.badgeAnchorChildIndex) * 31;
        boolean z2 = this.badgeIgnoreChildPadding;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return ((((iHashCode + i2) * 31) + this.badgeMinHeight) * 31) + this.badgeMinWidth;
    }

    public final void setBadgeAnchorChildIndex(int i2) {
        this.badgeAnchorChildIndex = i2;
    }

    public final void setBadgeCircleOffsetX(int i2) {
        this.badgeCircleOffsetX = i2;
    }

    public final void setBadgeCircleOffsetY(int i2) {
        this.badgeCircleOffsetY = i2;
    }

    public final void setBadgeCircleRadius(int i2) {
        this.badgeCircleRadius = i2;
    }

    public final void setBadgeGravity(int i2) {
        this.badgeGravity = i2;
    }

    public final void setBadgeIgnoreChildPadding(boolean z2) {
        this.badgeIgnoreChildPadding = z2;
    }

    public final void setBadgeMinHeight(int i2) {
        this.badgeMinHeight = i2;
    }

    public final void setBadgeMinWidth(int i2) {
        this.badgeMinWidth = i2;
    }

    public final void setBadgeOffsetX(int i2) {
        this.badgeOffsetX = i2;
    }

    public final void setBadgeOffsetY(int i2) {
        this.badgeOffsetY = i2;
    }

    public final void setBadgePaddingBottom(int i2) {
        this.badgePaddingBottom = i2;
    }

    public final void setBadgePaddingLeft(int i2) {
        this.badgePaddingLeft = i2;
    }

    public final void setBadgePaddingRight(int i2) {
        this.badgePaddingRight = i2;
    }

    public final void setBadgePaddingTop(int i2) {
        this.badgePaddingTop = i2;
    }

    public final void setBadgeRadius(int i2) {
        this.badgeRadius = i2;
    }

    public final void setBadgeSolidColor(int i2) {
        this.badgeSolidColor = i2;
    }

    public final void setBadgeStrokeColor(int i2) {
        this.badgeStrokeColor = i2;
    }

    public final void setBadgeStrokeWidth(int i2) {
        this.badgeStrokeWidth = i2;
    }

    public final void setBadgeText(@Nullable String str) {
        this.badgeText = str;
    }

    public final void setBadgeTextColor(int i2) {
        this.badgeTextColor = i2;
    }

    public final void setBadgeTextSize(float f2) {
        this.badgeTextSize = f2;
    }

    @NotNull
    public String toString() {
        return "TabBadgeConfig(badgeText=" + this.badgeText + ", badgeGravity=" + this.badgeGravity + ", badgeSolidColor=" + this.badgeSolidColor + ", badgeStrokeColor=" + this.badgeStrokeColor + ", badgeStrokeWidth=" + this.badgeStrokeWidth + ", badgeTextColor=" + this.badgeTextColor + ", badgeTextSize=" + this.badgeTextSize + ", badgeCircleRadius=" + this.badgeCircleRadius + ", badgeRadius=" + this.badgeRadius + ", badgeOffsetX=" + this.badgeOffsetX + ", badgeOffsetY=" + this.badgeOffsetY + ", badgeCircleOffsetX=" + this.badgeCircleOffsetX + ", badgeCircleOffsetY=" + this.badgeCircleOffsetY + ", badgePaddingLeft=" + this.badgePaddingLeft + ", badgePaddingRight=" + this.badgePaddingRight + ", badgePaddingTop=" + this.badgePaddingTop + ", badgePaddingBottom=" + this.badgePaddingBottom + ", badgeAnchorChildIndex=" + this.badgeAnchorChildIndex + ", badgeIgnoreChildPadding=" + this.badgeIgnoreChildPadding + ", badgeMinHeight=" + this.badgeMinHeight + ", badgeMinWidth=" + this.badgeMinWidth + ')';
    }

    public /* synthetic */ TabBadgeConfig(String str, int i2, int i3, int i4, int i5, int i6, float f2, int i7, int i8, int i9, int i10, int i11, int i12, int i13, int i14, int i15, int i16, int i17, boolean z2, int i18, int i19, int i20, DefaultConstructorMarker defaultConstructorMarker) {
        this((i20 & 1) != 0 ? null : str, (i20 & 2) != 0 ? 17 : i2, (i20 & 4) != 0 ? SupportMenu.CATEGORY_MASK : i3, (i20 & 8) != 0 ? 0 : i4, (i20 & 16) != 0 ? 0 : i5, (i20 & 32) != 0 ? -1 : i6, (i20 & 64) != 0 ? 12 * LibExKt.getDp() : f2, (i20 & 128) != 0 ? LibExKt.getDpi() * 4 : i7, (i20 & 256) != 0 ? LibExKt.getDpi() * 10 : i8, (i20 & 512) != 0 ? 0 : i9, (i20 & 1024) != 0 ? 0 : i10, (i20 & 2048) != 0 ? 0 : i11, (i20 & 4096) != 0 ? 0 : i12, (i20 & 8192) != 0 ? LibExKt.getDpi() * 4 : i13, (i20 & 16384) != 0 ? LibExKt.getDpi() * 4 : i14, (i20 & 32768) != 0 ? 0 : i15, (i20 & 65536) != 0 ? 0 : i16, (i20 & 131072) != 0 ? -1 : i17, (i20 & 262144) != 0 ? true : z2, (i20 & 524288) != 0 ? -2 : i18, (i20 & 1048576) != 0 ? -1 : i19);
    }
}
