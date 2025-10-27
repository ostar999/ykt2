package com.psychiatrygarden.bean;

import com.google.gson.annotations.SerializedName;
import com.psychiatrygarden.utils.Constants;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u001c\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B;\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nJ\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0007HÆ\u0003JE\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u00072\b\b\u0002\u0010\t\u001a\u00020\u0007HÆ\u0001J\u0013\u0010!\u001a\u00020\u00072\b\u0010\"\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010#\u001a\u00020$HÖ\u0001J\t\u0010%\u001a\u00020\u0003HÖ\u0001R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\t\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\f\"\u0004\b\u0014\u0010\u000eR\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0010\"\u0004\b\u0016\u0010\u0012R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\f¨\u0006&"}, d2 = {"Lcom/psychiatrygarden/bean/CourseLabel;", "", "value", "", "bgColor", "fontColor", "promotion", "", "gift", "coupon", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZZ)V", "getBgColor", "()Ljava/lang/String;", "setBgColor", "(Ljava/lang/String;)V", "getCoupon", "()Z", "setCoupon", "(Z)V", "getFontColor", "setFontColor", "getGift", "setGift", "getPromotion", "setPromotion", "getValue", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class CourseLabel {

    @SerializedName("background_color")
    @NotNull
    private String bgColor;
    private boolean coupon;

    @SerializedName("font_color")
    @NotNull
    private String fontColor;
    private boolean gift;
    private boolean promotion;

    @SerializedName(alternate = {Constants.PARAMS_CONSTANTS.PARAMS_TOPIC_LABEL}, value = "value")
    @NotNull
    private final String value;

    public CourseLabel(@NotNull String value, @NotNull String bgColor, @NotNull String fontColor, boolean z2, boolean z3, boolean z4) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        Intrinsics.checkNotNullParameter(fontColor, "fontColor");
        this.value = value;
        this.bgColor = bgColor;
        this.fontColor = fontColor;
        this.promotion = z2;
        this.gift = z3;
        this.coupon = z4;
    }

    public static /* synthetic */ CourseLabel copy$default(CourseLabel courseLabel, String str, String str2, String str3, boolean z2, boolean z3, boolean z4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = courseLabel.value;
        }
        if ((i2 & 2) != 0) {
            str2 = courseLabel.bgColor;
        }
        String str4 = str2;
        if ((i2 & 4) != 0) {
            str3 = courseLabel.fontColor;
        }
        String str5 = str3;
        if ((i2 & 8) != 0) {
            z2 = courseLabel.promotion;
        }
        boolean z5 = z2;
        if ((i2 & 16) != 0) {
            z3 = courseLabel.gift;
        }
        boolean z6 = z3;
        if ((i2 & 32) != 0) {
            z4 = courseLabel.coupon;
        }
        return courseLabel.copy(str, str4, str5, z5, z6, z4);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getValue() {
        return this.value;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getBgColor() {
        return this.bgColor;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getFontColor() {
        return this.fontColor;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getPromotion() {
        return this.promotion;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean getGift() {
        return this.gift;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean getCoupon() {
        return this.coupon;
    }

    @NotNull
    public final CourseLabel copy(@NotNull String value, @NotNull String bgColor, @NotNull String fontColor, boolean promotion, boolean gift, boolean coupon) {
        Intrinsics.checkNotNullParameter(value, "value");
        Intrinsics.checkNotNullParameter(bgColor, "bgColor");
        Intrinsics.checkNotNullParameter(fontColor, "fontColor");
        return new CourseLabel(value, bgColor, fontColor, promotion, gift, coupon);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CourseLabel)) {
            return false;
        }
        CourseLabel courseLabel = (CourseLabel) other;
        return Intrinsics.areEqual(this.value, courseLabel.value) && Intrinsics.areEqual(this.bgColor, courseLabel.bgColor) && Intrinsics.areEqual(this.fontColor, courseLabel.fontColor) && this.promotion == courseLabel.promotion && this.gift == courseLabel.gift && this.coupon == courseLabel.coupon;
    }

    @NotNull
    public final String getBgColor() {
        return this.bgColor;
    }

    public final boolean getCoupon() {
        return this.coupon;
    }

    @NotNull
    public final String getFontColor() {
        return this.fontColor;
    }

    public final boolean getGift() {
        return this.gift;
    }

    public final boolean getPromotion() {
        return this.promotion;
    }

    @NotNull
    public final String getValue() {
        return this.value;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((((this.value.hashCode() * 31) + this.bgColor.hashCode()) * 31) + this.fontColor.hashCode()) * 31;
        boolean z2 = this.promotion;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        int i3 = (iHashCode + i2) * 31;
        boolean z3 = this.gift;
        int i4 = z3;
        if (z3 != 0) {
            i4 = 1;
        }
        int i5 = (i3 + i4) * 31;
        boolean z4 = this.coupon;
        return i5 + (z4 ? 1 : z4 ? 1 : 0);
    }

    public final void setBgColor(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.bgColor = str;
    }

    public final void setCoupon(boolean z2) {
        this.coupon = z2;
    }

    public final void setFontColor(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.fontColor = str;
    }

    public final void setGift(boolean z2) {
        this.gift = z2;
    }

    public final void setPromotion(boolean z2) {
        this.promotion = z2;
    }

    @NotNull
    public String toString() {
        return "CourseLabel(value=" + this.value + ", bgColor=" + this.bgColor + ", fontColor=" + this.fontColor + ", promotion=" + this.promotion + ", gift=" + this.gift + ", coupon=" + this.coupon + ')';
    }

    public /* synthetic */ CourseLabel(String str, String str2, String str3, boolean z2, boolean z3, boolean z4, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, (i2 & 8) != 0 ? false : z2, (i2 & 16) != 0 ? false : z3, (i2 & 32) != 0 ? false : z4);
    }
}
