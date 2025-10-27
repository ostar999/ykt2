package com.ykb.ebook.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00032\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\b\u0010\u0013\u001a\u00020\u0006H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0014"}, d2 = {"Lcom/ykb/ebook/model/BookContent;", "", "sameTitleRemoved", "", "textList", "", "", "(ZLjava/util/List;)V", "getSameTitleRemoved", "()Z", "getTextList", "()Ljava/util/List;", "component1", "component2", "copy", "equals", "other", "hashCode", "", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class BookContent {
    private final boolean sameTitleRemoved;

    @NotNull
    private final List<String> textList;

    public BookContent(boolean z2, @NotNull List<String> textList) {
        Intrinsics.checkNotNullParameter(textList, "textList");
        this.sameTitleRemoved = z2;
        this.textList = textList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ BookContent copy$default(BookContent bookContent, boolean z2, List list, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z2 = bookContent.sameTitleRemoved;
        }
        if ((i2 & 2) != 0) {
            list = bookContent.textList;
        }
        return bookContent.copy(z2, list);
    }

    /* renamed from: component1, reason: from getter */
    public final boolean getSameTitleRemoved() {
        return this.sameTitleRemoved;
    }

    @NotNull
    public final List<String> component2() {
        return this.textList;
    }

    @NotNull
    public final BookContent copy(boolean sameTitleRemoved, @NotNull List<String> textList) {
        Intrinsics.checkNotNullParameter(textList, "textList");
        return new BookContent(sameTitleRemoved, textList);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BookContent)) {
            return false;
        }
        BookContent bookContent = (BookContent) other;
        return this.sameTitleRemoved == bookContent.sameTitleRemoved && Intrinsics.areEqual(this.textList, bookContent.textList);
    }

    public final boolean getSameTitleRemoved() {
        return this.sameTitleRemoved;
    }

    @NotNull
    public final List<String> getTextList() {
        return this.textList;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public int hashCode() {
        boolean z2 = this.sameTitleRemoved;
        ?? r02 = z2;
        if (z2) {
            r02 = 1;
        }
        return (r02 * 31) + this.textList.hashCode();
    }

    @NotNull
    public String toString() {
        return CollectionsKt___CollectionsKt.joinToString$default(this.textList, "\n", null, null, 0, null, null, 62, null);
    }
}
