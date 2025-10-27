package com.ykb.ebook.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0018\b\u0086\b\u0018\u00002\u00020\u0001B=\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\nHÆ\u0003JE\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\nHÆ\u0001J\u0013\u0010\u001e\u001a\u00020\n2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020\u0003HÖ\u0001J\t\u0010!\u001a\u00020\u0005HÖ\u0001R\u001a\u0010\t\u001a\u00020\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011¨\u0006\""}, d2 = {"Lcom/ykb/ebook/model/PermissionItem;", "", "value", "", "title", "", "tip", "selectIcon", "unSelectIcon", "select", "", "(ILjava/lang/String;Ljava/lang/String;IIZ)V", "getSelect", "()Z", "setSelect", "(Z)V", "getSelectIcon", "()I", "getTip", "()Ljava/lang/String;", "getTitle", "getUnSelectIcon", "getValue", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class PermissionItem {
    private boolean select;
    private final int selectIcon;

    @NotNull
    private final String tip;

    @NotNull
    private final String title;
    private final int unSelectIcon;
    private final int value;

    public PermissionItem(int i2, @NotNull String title, @NotNull String tip, int i3, int i4, boolean z2) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(tip, "tip");
        this.value = i2;
        this.title = title;
        this.tip = tip;
        this.selectIcon = i3;
        this.unSelectIcon = i4;
        this.select = z2;
    }

    public static /* synthetic */ PermissionItem copy$default(PermissionItem permissionItem, int i2, String str, String str2, int i3, int i4, boolean z2, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i2 = permissionItem.value;
        }
        if ((i5 & 2) != 0) {
            str = permissionItem.title;
        }
        String str3 = str;
        if ((i5 & 4) != 0) {
            str2 = permissionItem.tip;
        }
        String str4 = str2;
        if ((i5 & 8) != 0) {
            i3 = permissionItem.selectIcon;
        }
        int i6 = i3;
        if ((i5 & 16) != 0) {
            i4 = permissionItem.unSelectIcon;
        }
        int i7 = i4;
        if ((i5 & 32) != 0) {
            z2 = permissionItem.select;
        }
        return permissionItem.copy(i2, str3, str4, i6, i7, z2);
    }

    /* renamed from: component1, reason: from getter */
    public final int getValue() {
        return this.value;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getTip() {
        return this.tip;
    }

    /* renamed from: component4, reason: from getter */
    public final int getSelectIcon() {
        return this.selectIcon;
    }

    /* renamed from: component5, reason: from getter */
    public final int getUnSelectIcon() {
        return this.unSelectIcon;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean getSelect() {
        return this.select;
    }

    @NotNull
    public final PermissionItem copy(int value, @NotNull String title, @NotNull String tip, int selectIcon, int unSelectIcon, boolean select) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(tip, "tip");
        return new PermissionItem(value, title, tip, selectIcon, unSelectIcon, select);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PermissionItem)) {
            return false;
        }
        PermissionItem permissionItem = (PermissionItem) other;
        return this.value == permissionItem.value && Intrinsics.areEqual(this.title, permissionItem.title) && Intrinsics.areEqual(this.tip, permissionItem.tip) && this.selectIcon == permissionItem.selectIcon && this.unSelectIcon == permissionItem.unSelectIcon && this.select == permissionItem.select;
    }

    public final boolean getSelect() {
        return this.select;
    }

    public final int getSelectIcon() {
        return this.selectIcon;
    }

    @NotNull
    public final String getTip() {
        return this.tip;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public final int getUnSelectIcon() {
        return this.unSelectIcon;
    }

    public final int getValue() {
        return this.value;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((((((((this.value * 31) + this.title.hashCode()) * 31) + this.tip.hashCode()) * 31) + this.selectIcon) * 31) + this.unSelectIcon) * 31;
        boolean z2 = this.select;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        return iHashCode + i2;
    }

    public final void setSelect(boolean z2) {
        this.select = z2;
    }

    @NotNull
    public String toString() {
        return "PermissionItem(value=" + this.value + ", title=" + this.title + ", tip=" + this.tip + ", selectIcon=" + this.selectIcon + ", unSelectIcon=" + this.unSelectIcon + ", select=" + this.select + ')';
    }

    public /* synthetic */ PermissionItem(int i2, String str, String str2, int i3, int i4, boolean z2, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? 0 : i2, str, str2, (i5 & 8) != 0 ? 0 : i3, (i5 & 16) != 0 ? 0 : i4, (i5 & 32) != 0 ? false : z2);
    }
}
