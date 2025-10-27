package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u000f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J9\u0010\u0011\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/bean/ButtonBean;", "", "title", "", "type", "word_color", "border_color", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getBorder_color", "()Ljava/lang/String;", "getTitle", "getType", "getWord_color", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ButtonBean {

    @Nullable
    private final String border_color;

    @Nullable
    private final String title;

    @Nullable
    private final String type;

    @Nullable
    private final String word_color;

    public ButtonBean(@Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4) {
        this.title = str;
        this.type = str2;
        this.word_color = str3;
        this.border_color = str4;
    }

    public static /* synthetic */ ButtonBean copy$default(ButtonBean buttonBean, String str, String str2, String str3, String str4, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = buttonBean.title;
        }
        if ((i2 & 2) != 0) {
            str2 = buttonBean.type;
        }
        if ((i2 & 4) != 0) {
            str3 = buttonBean.word_color;
        }
        if ((i2 & 8) != 0) {
            str4 = buttonBean.border_color;
        }
        return buttonBean.copy(str, str2, str3, str4);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getType() {
        return this.type;
    }

    @Nullable
    /* renamed from: component3, reason: from getter */
    public final String getWord_color() {
        return this.word_color;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getBorder_color() {
        return this.border_color;
    }

    @NotNull
    public final ButtonBean copy(@Nullable String title, @Nullable String type, @Nullable String word_color, @Nullable String border_color) {
        return new ButtonBean(title, type, word_color, border_color);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ButtonBean)) {
            return false;
        }
        ButtonBean buttonBean = (ButtonBean) other;
        return Intrinsics.areEqual(this.title, buttonBean.title) && Intrinsics.areEqual(this.type, buttonBean.type) && Intrinsics.areEqual(this.word_color, buttonBean.word_color) && Intrinsics.areEqual(this.border_color, buttonBean.border_color);
    }

    @Nullable
    public final String getBorder_color() {
        return this.border_color;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    @Nullable
    public final String getWord_color() {
        return this.word_color;
    }

    public int hashCode() {
        String str = this.title;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.type;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.word_color;
        int iHashCode3 = (iHashCode2 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.border_color;
        return iHashCode3 + (str4 != null ? str4.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ButtonBean(title=" + this.title + ", type=" + this.type + ", word_color=" + this.word_color + ", border_color=" + this.border_color + ')';
    }
}
