package com.ykb.ebook.model;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B#\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001J\t\u0010\u0018\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\nR\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\b\"\u0004\b\u000e\u0010\n¨\u0006\u0019"}, d2 = {"Lcom/ykb/ebook/model/BookShelf;", "", "id", "", "name", "title", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getId", "()Ljava/lang/String;", "setId", "(Ljava/lang/String;)V", "getName", "setName", "getTitle", "setTitle", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class BookShelf {

    @NotNull
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String title;

    public BookShelf() {
        this(null, null, null, 7, null);
    }

    public BookShelf(@NotNull String id, @NotNull String name, @NotNull String title) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(title, "title");
        this.id = id;
        this.name = name;
        this.title = title;
    }

    public static /* synthetic */ BookShelf copy$default(BookShelf bookShelf, String str, String str2, String str3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = bookShelf.id;
        }
        if ((i2 & 2) != 0) {
            str2 = bookShelf.name;
        }
        if ((i2 & 4) != 0) {
            str3 = bookShelf.title;
        }
        return bookShelf.copy(str, str2, str3);
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getName() {
        return this.name;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final BookShelf copy(@NotNull String id, @NotNull String name, @NotNull String title) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(title, "title");
        return new BookShelf(id, name, title);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BookShelf)) {
            return false;
        }
        BookShelf bookShelf = (BookShelf) other;
        return Intrinsics.areEqual(this.id, bookShelf.id) && Intrinsics.areEqual(this.name, bookShelf.name) && Intrinsics.areEqual(this.title, bookShelf.title);
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final String getName() {
        return this.name;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return (((this.id.hashCode() * 31) + this.name.hashCode()) * 31) + this.title.hashCode();
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setName(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.name = str;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    @NotNull
    public String toString() {
        return "BookShelf(id=" + this.id + ", name=" + this.name + ", title=" + this.title + ')';
    }

    public /* synthetic */ BookShelf(String str, String str2, String str3, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? "1" : str, (i2 & 2) != 0 ? "" : str2, (i2 & 4) != 0 ? "" : str3);
    }
}
