package com.catchpig.mvvm.entity;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0012\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B7\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0003\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0003\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0003\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0003\u0010\u0007\u001a\u00020\u0003¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J;\u0010\u0014\u001a\u00020\u00002\b\b\u0003\u0010\u0002\u001a\u00020\u00032\b\b\u0003\u0010\u0004\u001a\u00020\u00032\b\b\u0003\u0010\u0005\u001a\u00020\u00032\b\b\u0003\u0010\u0006\u001a\u00020\u00032\b\b\u0003\u0010\u0007\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001b"}, d2 = {"Lcom/catchpig/mvvm/entity/TitleParam;", "", "value", "", TtmlNode.ATTR_TTS_BACKGROUND_COLOR, "textColor", "rightFirstTextColor", "backIcon", "(IIIII)V", "getBackIcon", "()I", "getBackgroundColor", "getRightFirstTextColor", "getTextColor", "getValue", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class TitleParam {
    private final int backIcon;
    private final int backgroundColor;
    private final int rightFirstTextColor;
    private final int textColor;
    private final int value;

    public TitleParam(@StringRes int i2, @ColorRes int i3, @ColorRes int i4, @ColorRes int i5, @DrawableRes int i6) {
        this.value = i2;
        this.backgroundColor = i3;
        this.textColor = i4;
        this.rightFirstTextColor = i5;
        this.backIcon = i6;
    }

    public static /* synthetic */ TitleParam copy$default(TitleParam titleParam, int i2, int i3, int i4, int i5, int i6, int i7, Object obj) {
        if ((i7 & 1) != 0) {
            i2 = titleParam.value;
        }
        if ((i7 & 2) != 0) {
            i3 = titleParam.backgroundColor;
        }
        int i8 = i3;
        if ((i7 & 4) != 0) {
            i4 = titleParam.textColor;
        }
        int i9 = i4;
        if ((i7 & 8) != 0) {
            i5 = titleParam.rightFirstTextColor;
        }
        int i10 = i5;
        if ((i7 & 16) != 0) {
            i6 = titleParam.backIcon;
        }
        return titleParam.copy(i2, i8, i9, i10, i6);
    }

    /* renamed from: component1, reason: from getter */
    public final int getValue() {
        return this.value;
    }

    /* renamed from: component2, reason: from getter */
    public final int getBackgroundColor() {
        return this.backgroundColor;
    }

    /* renamed from: component3, reason: from getter */
    public final int getTextColor() {
        return this.textColor;
    }

    /* renamed from: component4, reason: from getter */
    public final int getRightFirstTextColor() {
        return this.rightFirstTextColor;
    }

    /* renamed from: component5, reason: from getter */
    public final int getBackIcon() {
        return this.backIcon;
    }

    @NotNull
    public final TitleParam copy(@StringRes int value, @ColorRes int backgroundColor, @ColorRes int textColor, @ColorRes int rightFirstTextColor, @DrawableRes int backIcon) {
        return new TitleParam(value, backgroundColor, textColor, rightFirstTextColor, backIcon);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TitleParam)) {
            return false;
        }
        TitleParam titleParam = (TitleParam) other;
        return this.value == titleParam.value && this.backgroundColor == titleParam.backgroundColor && this.textColor == titleParam.textColor && this.rightFirstTextColor == titleParam.rightFirstTextColor && this.backIcon == titleParam.backIcon;
    }

    public final int getBackIcon() {
        return this.backIcon;
    }

    public final int getBackgroundColor() {
        return this.backgroundColor;
    }

    public final int getRightFirstTextColor() {
        return this.rightFirstTextColor;
    }

    public final int getTextColor() {
        return this.textColor;
    }

    public final int getValue() {
        return this.value;
    }

    public int hashCode() {
        return (((((((this.value * 31) + this.backgroundColor) * 31) + this.textColor) * 31) + this.rightFirstTextColor) * 31) + this.backIcon;
    }

    @NotNull
    public String toString() {
        return "TitleParam(value=" + this.value + ", backgroundColor=" + this.backgroundColor + ", textColor=" + this.textColor + ", rightFirstTextColor=" + this.rightFirstTextColor + ", backIcon=" + this.backIcon + ')';
    }

    public /* synthetic */ TitleParam(int i2, int i3, int i4, int i5, int i6, int i7, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i7 & 2) != 0 ? -1 : i3, (i7 & 4) != 0 ? -1 : i4, (i7 & 8) != 0 ? -1 : i5, (i7 & 16) != 0 ? -1 : i6);
    }
}
