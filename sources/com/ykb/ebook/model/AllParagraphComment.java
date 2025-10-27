package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B#\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\u000f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0006HÆ\u0003J\t\u0010\u0015\u001a\u00020\u0006HÆ\u0003J-\u0010\u0016\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0017\u001a\u00020\u00182\b\u0010\u0019\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u001a\u001a\u00020\u001bHÖ\u0001J\t\u0010\u001c\u001a\u00020\u0006HÖ\u0001R$\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001e\u0010\u0007\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010¨\u0006\u001d"}, d2 = {"Lcom/ykb/ebook/model/AllParagraphComment;", "", "paragraphReviewList", "", "Lcom/ykb/ebook/model/ParagraphReviewList;", "sort", "", "title", "(Ljava/util/List;Ljava/lang/String;Ljava/lang/String;)V", "getParagraphReviewList", "()Ljava/util/List;", "setParagraphReviewList", "(Ljava/util/List;)V", "getSort", "()Ljava/lang/String;", "setSort", "(Ljava/lang/String;)V", "getTitle", "setTitle", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class AllParagraphComment {

    @SerializedName("paragraph_review_list")
    @NotNull
    private List<ParagraphReviewList> paragraphReviewList;

    @SerializedName("sort")
    @NotNull
    private String sort;

    @SerializedName("title")
    @NotNull
    private String title;

    public AllParagraphComment(@NotNull List<ParagraphReviewList> paragraphReviewList, @NotNull String sort, @NotNull String title) {
        Intrinsics.checkNotNullParameter(paragraphReviewList, "paragraphReviewList");
        Intrinsics.checkNotNullParameter(sort, "sort");
        Intrinsics.checkNotNullParameter(title, "title");
        this.paragraphReviewList = paragraphReviewList;
        this.sort = sort;
        this.title = title;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ AllParagraphComment copy$default(AllParagraphComment allParagraphComment, List list, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = allParagraphComment.paragraphReviewList;
        }
        if ((i2 & 2) != 0) {
            str = allParagraphComment.sort;
        }
        if ((i2 & 4) != 0) {
            str2 = allParagraphComment.title;
        }
        return allParagraphComment.copy(list, str, str2);
    }

    @NotNull
    public final List<ParagraphReviewList> component1() {
        return this.paragraphReviewList;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getSort() {
        return this.sort;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final AllParagraphComment copy(@NotNull List<ParagraphReviewList> paragraphReviewList, @NotNull String sort, @NotNull String title) {
        Intrinsics.checkNotNullParameter(paragraphReviewList, "paragraphReviewList");
        Intrinsics.checkNotNullParameter(sort, "sort");
        Intrinsics.checkNotNullParameter(title, "title");
        return new AllParagraphComment(paragraphReviewList, sort, title);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AllParagraphComment)) {
            return false;
        }
        AllParagraphComment allParagraphComment = (AllParagraphComment) other;
        return Intrinsics.areEqual(this.paragraphReviewList, allParagraphComment.paragraphReviewList) && Intrinsics.areEqual(this.sort, allParagraphComment.sort) && Intrinsics.areEqual(this.title, allParagraphComment.title);
    }

    @NotNull
    public final List<ParagraphReviewList> getParagraphReviewList() {
        return this.paragraphReviewList;
    }

    @NotNull
    public final String getSort() {
        return this.sort;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return (((this.paragraphReviewList.hashCode() * 31) + this.sort.hashCode()) * 31) + this.title.hashCode();
    }

    public final void setParagraphReviewList(@NotNull List<ParagraphReviewList> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.paragraphReviewList = list;
    }

    public final void setSort(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.sort = str;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    @NotNull
    public String toString() {
        return "AllParagraphComment(paragraphReviewList=" + this.paragraphReviewList + ", sort=" + this.sort + ", title=" + this.title + ')';
    }
}
