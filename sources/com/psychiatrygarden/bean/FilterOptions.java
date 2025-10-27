package com.psychiatrygarden.bean;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\u0003¢\u0006\u0002\u0010\nJ\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006HÆ\u0003J\t\u0010\u001a\u001a\u00020\bHÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003JA\u0010\u001c\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u001d\u001a\u00020\u001e2\b\u0010\u001f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010 \u001a\u00020\bHÖ\u0001J\t\u0010!\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\f\"\u0004\b\u0013\u0010\u0014R\u001a\u0010\t\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\f\"\u0004\b\u0016\u0010\u0014¨\u0006\""}, d2 = {"Lcom/psychiatrygarden/bean/FilterOptions;", "", "id", "", "title", "list", "", "isSelected", "", "yearTitle", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;ILjava/lang/String;)V", "getId", "()Ljava/lang/String;", "()I", "setSelected", "(I)V", "getList", "()Ljava/util/List;", "getTitle", "setTitle", "(Ljava/lang/String;)V", "getYearTitle", "setYearTitle", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class FilterOptions {

    @NotNull
    private final String id;
    private int isSelected;

    @NotNull
    private final List<String> list;

    @NotNull
    private String title;

    @NotNull
    private String yearTitle;

    public FilterOptions(@NotNull String id, @NotNull String title, @NotNull List<String> list, int i2, @NotNull String yearTitle) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(yearTitle, "yearTitle");
        this.id = id;
        this.title = title;
        this.list = list;
        this.isSelected = i2;
        this.yearTitle = yearTitle;
    }

    public static /* synthetic */ FilterOptions copy$default(FilterOptions filterOptions, String str, String str2, List list, int i2, String str3, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = filterOptions.id;
        }
        if ((i3 & 2) != 0) {
            str2 = filterOptions.title;
        }
        String str4 = str2;
        if ((i3 & 4) != 0) {
            list = filterOptions.list;
        }
        List list2 = list;
        if ((i3 & 8) != 0) {
            i2 = filterOptions.isSelected;
        }
        int i4 = i2;
        if ((i3 & 16) != 0) {
            str3 = filterOptions.yearTitle;
        }
        return filterOptions.copy(str, str4, list2, i4, str3);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final List<String> component3() {
        return this.list;
    }

    /* renamed from: component4, reason: from getter */
    public final int getIsSelected() {
        return this.isSelected;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getYearTitle() {
        return this.yearTitle;
    }

    @NotNull
    public final FilterOptions copy(@NotNull String id, @NotNull String title, @NotNull List<String> list, int isSelected, @NotNull String yearTitle) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(list, "list");
        Intrinsics.checkNotNullParameter(yearTitle, "yearTitle");
        return new FilterOptions(id, title, list, isSelected, yearTitle);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FilterOptions)) {
            return false;
        }
        FilterOptions filterOptions = (FilterOptions) other;
        return Intrinsics.areEqual(this.id, filterOptions.id) && Intrinsics.areEqual(this.title, filterOptions.title) && Intrinsics.areEqual(this.list, filterOptions.list) && this.isSelected == filterOptions.isSelected && Intrinsics.areEqual(this.yearTitle, filterOptions.yearTitle);
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final List<String> getList() {
        return this.list;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getYearTitle() {
        return this.yearTitle;
    }

    public int hashCode() {
        return (((((((this.id.hashCode() * 31) + this.title.hashCode()) * 31) + this.list.hashCode()) * 31) + this.isSelected) * 31) + this.yearTitle.hashCode();
    }

    public final int isSelected() {
        return this.isSelected;
    }

    public final void setSelected(int i2) {
        this.isSelected = i2;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final void setYearTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.yearTitle = str;
    }

    @NotNull
    public String toString() {
        return "FilterOptions(id=" + this.id + ", title=" + this.title + ", list=" + this.list + ", isSelected=" + this.isSelected + ", yearTitle=" + this.yearTitle + ')';
    }
}
