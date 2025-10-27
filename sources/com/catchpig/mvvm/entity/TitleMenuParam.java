package com.catchpig.mvvm.entity;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0003\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0003\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0003\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0003\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J1\u0010\u0011\u001a\u00020\u00002\b\b\u0003\u0010\u0002\u001a\u00020\u00032\b\b\u0003\u0010\u0004\u001a\u00020\u00032\b\b\u0003\u0010\u0005\u001a\u00020\u00032\b\b\u0003\u0010\u0006\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0003HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/catchpig/mvvm/entity/TitleMenuParam;", "", "rightFirstDrawable", "", "rightFirstText", "rightSecondDrawable", "rightSecondText", "(IIII)V", "getRightFirstDrawable", "()I", "getRightFirstText", "getRightSecondDrawable", "getRightSecondText", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "toString", "", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes2.dex */
public final /* data */ class TitleMenuParam {
    private final int rightFirstDrawable;
    private final int rightFirstText;
    private final int rightSecondDrawable;
    private final int rightSecondText;

    public TitleMenuParam() {
        this(0, 0, 0, 0, 15, null);
    }

    public TitleMenuParam(@DrawableRes int i2, @StringRes int i3, @DrawableRes int i4, @StringRes int i5) {
        this.rightFirstDrawable = i2;
        this.rightFirstText = i3;
        this.rightSecondDrawable = i4;
        this.rightSecondText = i5;
    }

    public static /* synthetic */ TitleMenuParam copy$default(TitleMenuParam titleMenuParam, int i2, int i3, int i4, int i5, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            i2 = titleMenuParam.rightFirstDrawable;
        }
        if ((i6 & 2) != 0) {
            i3 = titleMenuParam.rightFirstText;
        }
        if ((i6 & 4) != 0) {
            i4 = titleMenuParam.rightSecondDrawable;
        }
        if ((i6 & 8) != 0) {
            i5 = titleMenuParam.rightSecondText;
        }
        return titleMenuParam.copy(i2, i3, i4, i5);
    }

    /* renamed from: component1, reason: from getter */
    public final int getRightFirstDrawable() {
        return this.rightFirstDrawable;
    }

    /* renamed from: component2, reason: from getter */
    public final int getRightFirstText() {
        return this.rightFirstText;
    }

    /* renamed from: component3, reason: from getter */
    public final int getRightSecondDrawable() {
        return this.rightSecondDrawable;
    }

    /* renamed from: component4, reason: from getter */
    public final int getRightSecondText() {
        return this.rightSecondText;
    }

    @NotNull
    public final TitleMenuParam copy(@DrawableRes int rightFirstDrawable, @StringRes int rightFirstText, @DrawableRes int rightSecondDrawable, @StringRes int rightSecondText) {
        return new TitleMenuParam(rightFirstDrawable, rightFirstText, rightSecondDrawable, rightSecondText);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TitleMenuParam)) {
            return false;
        }
        TitleMenuParam titleMenuParam = (TitleMenuParam) other;
        return this.rightFirstDrawable == titleMenuParam.rightFirstDrawable && this.rightFirstText == titleMenuParam.rightFirstText && this.rightSecondDrawable == titleMenuParam.rightSecondDrawable && this.rightSecondText == titleMenuParam.rightSecondText;
    }

    public final int getRightFirstDrawable() {
        return this.rightFirstDrawable;
    }

    public final int getRightFirstText() {
        return this.rightFirstText;
    }

    public final int getRightSecondDrawable() {
        return this.rightSecondDrawable;
    }

    public final int getRightSecondText() {
        return this.rightSecondText;
    }

    public int hashCode() {
        return (((((this.rightFirstDrawable * 31) + this.rightFirstText) * 31) + this.rightSecondDrawable) * 31) + this.rightSecondText;
    }

    @NotNull
    public String toString() {
        return "TitleMenuParam(rightFirstDrawable=" + this.rightFirstDrawable + ", rightFirstText=" + this.rightFirstText + ", rightSecondDrawable=" + this.rightSecondDrawable + ", rightSecondText=" + this.rightSecondText + ')';
    }

    public /* synthetic */ TitleMenuParam(int i2, int i3, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? -1 : i2, (i6 & 2) != 0 ? -1 : i3, (i6 & 4) != 0 ? -1 : i4, (i6 & 8) != 0 ? -1 : i5);
    }
}
