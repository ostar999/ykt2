package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b&\b\u0087\b\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\rJ\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0003HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\t\u0010'\u001a\u00020\tHÆ\u0003J\t\u0010(\u001a\u00020\u000bHÆ\u0003J\u000b\u0010)\u001a\u0004\u0018\u00010\u0003HÆ\u0003J[\u0010*\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000b2\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010+\u001a\u00020\t2\b\u0010,\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\u0006\u0010-\u001a\u00020\tJ\t\u0010.\u001a\u00020\u000bHÖ\u0001J\u0006\u0010/\u001a\u00020\tJ\t\u00100\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0005\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000f\"\u0004\b\u0013\u0010\u0011R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001e\u0010\b\u001a\u00020\t8\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u0018\"\u0004\b\u0019\u0010\u001aR \u0010\f\u001a\u0004\u0018\u00010\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\u000f\"\u0004\b\u001b\u0010\u0011R\u001e\u0010\u0004\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u000f\"\u0004\b\u001d\u0010\u0011R\u001e\u0010\u0006\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u000f\"\u0004\b\u001f\u0010\u0011R\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u000f\"\u0004\b!\u0010\u0011¨\u00061"}, d2 = {"Lcom/ykb/ebook/model/Chapter;", "", "chapterTitle", "", "sort", "id", "title", "wordCount", "isPay", "", "index", "", "isPerusal", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZILjava/lang/String;)V", "getChapterTitle", "()Ljava/lang/String;", "setChapterTitle", "(Ljava/lang/String;)V", "getId", "setId", "getIndex", "()I", "setIndex", "(I)V", "()Z", "setPay", "(Z)V", "setPerusal", "getSort", "setSort", "getTitle", "setTitle", "getWordCount", "setWordCount", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "other", "hasUnlock", "hashCode", "isRead", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class Chapter {

    @NotNull
    private String chapterTitle;

    @SerializedName("id")
    @NotNull
    private String id;
    private int index;

    @SerializedName("is_pay")
    private boolean isPay;

    @SerializedName("is_perusal")
    @Nullable
    private String isPerusal;

    @SerializedName("sort")
    @NotNull
    private String sort;

    @SerializedName("title")
    @NotNull
    private String title;

    @SerializedName("word_count")
    @NotNull
    private String wordCount;

    public Chapter(@NotNull String chapterTitle, @NotNull String sort, @NotNull String id, @NotNull String title, @NotNull String wordCount, boolean z2, int i2, @Nullable String str) {
        Intrinsics.checkNotNullParameter(chapterTitle, "chapterTitle");
        Intrinsics.checkNotNullParameter(sort, "sort");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(wordCount, "wordCount");
        this.chapterTitle = chapterTitle;
        this.sort = sort;
        this.id = id;
        this.title = title;
        this.wordCount = wordCount;
        this.isPay = z2;
        this.index = i2;
        this.isPerusal = str;
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getChapterTitle() {
        return this.chapterTitle;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getSort() {
        return this.sort;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getWordCount() {
        return this.wordCount;
    }

    /* renamed from: component6, reason: from getter */
    public final boolean getIsPay() {
        return this.isPay;
    }

    /* renamed from: component7, reason: from getter */
    public final int getIndex() {
        return this.index;
    }

    @Nullable
    /* renamed from: component8, reason: from getter */
    public final String getIsPerusal() {
        return this.isPerusal;
    }

    @NotNull
    public final Chapter copy(@NotNull String chapterTitle, @NotNull String sort, @NotNull String id, @NotNull String title, @NotNull String wordCount, boolean isPay, int index, @Nullable String isPerusal) {
        Intrinsics.checkNotNullParameter(chapterTitle, "chapterTitle");
        Intrinsics.checkNotNullParameter(sort, "sort");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(wordCount, "wordCount");
        return new Chapter(chapterTitle, sort, id, title, wordCount, isPay, index, isPerusal);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Chapter)) {
            return false;
        }
        Chapter chapter = (Chapter) other;
        return Intrinsics.areEqual(this.chapterTitle, chapter.chapterTitle) && Intrinsics.areEqual(this.sort, chapter.sort) && Intrinsics.areEqual(this.id, chapter.id) && Intrinsics.areEqual(this.title, chapter.title) && Intrinsics.areEqual(this.wordCount, chapter.wordCount) && this.isPay == chapter.isPay && this.index == chapter.index && Intrinsics.areEqual(this.isPerusal, chapter.isPerusal);
    }

    @NotNull
    public final String getChapterTitle() {
        return this.chapterTitle;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    public final int getIndex() {
        return this.index;
    }

    @NotNull
    public final String getSort() {
        return this.sort;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getWordCount() {
        return this.wordCount;
    }

    public final boolean hasUnlock() {
        return this.isPay;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int iHashCode = ((((((((this.chapterTitle.hashCode() * 31) + this.sort.hashCode()) * 31) + this.id.hashCode()) * 31) + this.title.hashCode()) * 31) + this.wordCount.hashCode()) * 31;
        boolean z2 = this.isPay;
        int i2 = z2;
        if (z2 != 0) {
            i2 = 1;
        }
        int i3 = (((iHashCode + i2) * 31) + this.index) * 31;
        String str = this.isPerusal;
        return i3 + (str == null ? 0 : str.hashCode());
    }

    public final boolean isPay() {
        return this.isPay;
    }

    @Nullable
    public final String isPerusal() {
        return this.isPerusal;
    }

    public final boolean isRead() {
        return Intrinsics.areEqual("1", this.isPerusal);
    }

    public final void setChapterTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.chapterTitle = str;
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setIndex(int i2) {
        this.index = i2;
    }

    public final void setPay(boolean z2) {
        this.isPay = z2;
    }

    public final void setPerusal(@Nullable String str) {
        this.isPerusal = str;
    }

    public final void setSort(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.sort = str;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    public final void setWordCount(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.wordCount = str;
    }

    @NotNull
    public String toString() {
        return "Chapter(chapterTitle=" + this.chapterTitle + ", sort=" + this.sort + ", id=" + this.id + ", title=" + this.title + ", wordCount=" + this.wordCount + ", isPay=" + this.isPay + ", index=" + this.index + ", isPerusal=" + this.isPerusal + ')';
    }

    public /* synthetic */ Chapter(String str, String str2, String str3, String str4, String str5, boolean z2, int i2, String str6, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, str4, str5, (i3 & 32) != 0 ? false : z2, (i3 & 64) != 0 ? 0 : i2, (i3 & 128) != 0 ? "0" : str6);
    }
}
