package com.psychiatrygarden.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\u000b\u0010\t\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J!\u0010\u000b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lcom/psychiatrygarden/bean/ChapterTitleInfo;", "", "subjectTitle", "", "chapterTitle", "(Ljava/lang/String;Ljava/lang/String;)V", "getChapterTitle", "()Ljava/lang/String;", "getSubjectTitle", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final /* data */ class ChapterTitleInfo {

    @Nullable
    private final String chapterTitle;

    @Nullable
    private final String subjectTitle;

    public ChapterTitleInfo(@Nullable String str, @Nullable String str2) {
        this.subjectTitle = str;
        this.chapterTitle = str2;
    }

    public static /* synthetic */ ChapterTitleInfo copy$default(ChapterTitleInfo chapterTitleInfo, String str, String str2, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = chapterTitleInfo.subjectTitle;
        }
        if ((i2 & 2) != 0) {
            str2 = chapterTitleInfo.chapterTitle;
        }
        return chapterTitleInfo.copy(str, str2);
    }

    @Nullable
    /* renamed from: component1, reason: from getter */
    public final String getSubjectTitle() {
        return this.subjectTitle;
    }

    @Nullable
    /* renamed from: component2, reason: from getter */
    public final String getChapterTitle() {
        return this.chapterTitle;
    }

    @NotNull
    public final ChapterTitleInfo copy(@Nullable String subjectTitle, @Nullable String chapterTitle) {
        return new ChapterTitleInfo(subjectTitle, chapterTitle);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ChapterTitleInfo)) {
            return false;
        }
        ChapterTitleInfo chapterTitleInfo = (ChapterTitleInfo) other;
        return Intrinsics.areEqual(this.subjectTitle, chapterTitleInfo.subjectTitle) && Intrinsics.areEqual(this.chapterTitle, chapterTitleInfo.chapterTitle);
    }

    @Nullable
    public final String getChapterTitle() {
        return this.chapterTitle;
    }

    @Nullable
    public final String getSubjectTitle() {
        return this.subjectTitle;
    }

    public int hashCode() {
        String str = this.subjectTitle;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.chapterTitle;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    @NotNull
    public String toString() {
        return "ChapterTitleInfo(subjectTitle=" + this.subjectTitle + ", chapterTitle=" + this.chapterTitle + ')';
    }
}
