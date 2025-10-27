package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import com.ykb.ebook.common.PreferKeyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.apache.http.cookie.ClientCookie;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b+\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BW\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\u0003¢\u0006\u0002\u0010\rJ\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0003HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\u000b\u0010'\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\t\u0010)\u001a\u00020\u0003HÆ\u0003J\t\u0010*\u001a\u00020\u0003HÆ\u0003J\t\u0010+\u001a\u00020\u0003HÆ\u0003J\t\u0010,\u001a\u00020\u0003HÆ\u0003Jo\u0010-\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u0003HÆ\u0001J\u0013\u0010.\u001a\u00020/2\b\u00100\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u00101\u001a\u000202HÖ\u0001J\t\u00103\u001a\u00020\u0003HÖ\u0001R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u000f\"\u0004\b\u0011\u0010\u0012R\u001e\u0010\b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u000f\"\u0004\b\u0014\u0010\u0012R\u001e\u0010\t\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u000f\"\u0004\b\u0016\u0010\u0012R\u001e\u0010\n\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u000f\"\u0004\b\u0018\u0010\u0012R\u001e\u0010\f\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u000f\"\u0004\b\u001a\u0010\u0012R\u001a\u0010\u000b\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u000f\"\u0004\b\u001c\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u000f\"\u0004\b\u001e\u0010\u0012R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u000f\"\u0004\b \u0010\u0012R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u000f\"\u0004\b\"\u0010\u0012¨\u00064"}, d2 = {"Lcom/ykb/ebook/model/ParagraphReviewList;", "", "title", "", PreferKeyKt.START_POSITION, "sort", "chapterId", ClientCookie.COMMENT_ATTR, "ctime", "id", "paragraphContent", "picture", "paragraphId", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getChapterId", "()Ljava/lang/String;", "getComment", "setComment", "(Ljava/lang/String;)V", "getCtime", "setCtime", "getId", "setId", "getParagraphContent", "setParagraphContent", "getParagraphId", "setParagraphId", "getPicture", "setPicture", "getSort", "setSort", "getStart_position", "setStart_position", "getTitle", "setTitle", "component1", "component10", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class ParagraphReviewList {

    @SerializedName("chapter_id")
    @Nullable
    private final String chapterId;

    @SerializedName(ClientCookie.COMMENT_ATTR)
    @NotNull
    private String comment;

    @SerializedName("ctime")
    @NotNull
    private String ctime;

    @SerializedName("id")
    @NotNull
    private String id;

    @SerializedName("paragraph_content")
    @NotNull
    private String paragraphContent;

    @SerializedName("paragraph_id")
    @NotNull
    private String paragraphId;

    @NotNull
    private String picture;

    @NotNull
    private String sort;

    @NotNull
    private String start_position;

    @NotNull
    private String title;

    public ParagraphReviewList(@NotNull String title, @NotNull String start_position, @NotNull String sort, @Nullable String str, @NotNull String comment, @NotNull String ctime, @NotNull String id, @NotNull String paragraphContent, @NotNull String picture, @NotNull String paragraphId) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(start_position, "start_position");
        Intrinsics.checkNotNullParameter(sort, "sort");
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(paragraphContent, "paragraphContent");
        Intrinsics.checkNotNullParameter(picture, "picture");
        Intrinsics.checkNotNullParameter(paragraphId, "paragraphId");
        this.title = title;
        this.start_position = start_position;
        this.sort = sort;
        this.chapterId = str;
        this.comment = comment;
        this.ctime = ctime;
        this.id = id;
        this.paragraphContent = paragraphContent;
        this.picture = picture;
        this.paragraphId = paragraphId;
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    /* renamed from: component10, reason: from getter */
    public final String getParagraphId() {
        return this.paragraphId;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getStart_position() {
        return this.start_position;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getSort() {
        return this.sort;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getChapterId() {
        return this.chapterId;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getComment() {
        return this.comment;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    /* renamed from: component7, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component8, reason: from getter */
    public final String getParagraphContent() {
        return this.paragraphContent;
    }

    @NotNull
    /* renamed from: component9, reason: from getter */
    public final String getPicture() {
        return this.picture;
    }

    @NotNull
    public final ParagraphReviewList copy(@NotNull String title, @NotNull String start_position, @NotNull String sort, @Nullable String chapterId, @NotNull String comment, @NotNull String ctime, @NotNull String id, @NotNull String paragraphContent, @NotNull String picture, @NotNull String paragraphId) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(start_position, "start_position");
        Intrinsics.checkNotNullParameter(sort, "sort");
        Intrinsics.checkNotNullParameter(comment, "comment");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(paragraphContent, "paragraphContent");
        Intrinsics.checkNotNullParameter(picture, "picture");
        Intrinsics.checkNotNullParameter(paragraphId, "paragraphId");
        return new ParagraphReviewList(title, start_position, sort, chapterId, comment, ctime, id, paragraphContent, picture, paragraphId);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ParagraphReviewList)) {
            return false;
        }
        ParagraphReviewList paragraphReviewList = (ParagraphReviewList) other;
        return Intrinsics.areEqual(this.title, paragraphReviewList.title) && Intrinsics.areEqual(this.start_position, paragraphReviewList.start_position) && Intrinsics.areEqual(this.sort, paragraphReviewList.sort) && Intrinsics.areEqual(this.chapterId, paragraphReviewList.chapterId) && Intrinsics.areEqual(this.comment, paragraphReviewList.comment) && Intrinsics.areEqual(this.ctime, paragraphReviewList.ctime) && Intrinsics.areEqual(this.id, paragraphReviewList.id) && Intrinsics.areEqual(this.paragraphContent, paragraphReviewList.paragraphContent) && Intrinsics.areEqual(this.picture, paragraphReviewList.picture) && Intrinsics.areEqual(this.paragraphId, paragraphReviewList.paragraphId);
    }

    @Nullable
    public final String getChapterId() {
        return this.chapterId;
    }

    @NotNull
    public final String getComment() {
        return this.comment;
    }

    @NotNull
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    @NotNull
    public final String getParagraphContent() {
        return this.paragraphContent;
    }

    @NotNull
    public final String getParagraphId() {
        return this.paragraphId;
    }

    @NotNull
    public final String getPicture() {
        return this.picture;
    }

    @NotNull
    public final String getSort() {
        return this.sort;
    }

    @NotNull
    public final String getStart_position() {
        return this.start_position;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        int iHashCode = ((((this.title.hashCode() * 31) + this.start_position.hashCode()) * 31) + this.sort.hashCode()) * 31;
        String str = this.chapterId;
        return ((((((((((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.comment.hashCode()) * 31) + this.ctime.hashCode()) * 31) + this.id.hashCode()) * 31) + this.paragraphContent.hashCode()) * 31) + this.picture.hashCode()) * 31) + this.paragraphId.hashCode();
    }

    public final void setComment(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.comment = str;
    }

    public final void setCtime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ctime = str;
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setParagraphContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.paragraphContent = str;
    }

    public final void setParagraphId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.paragraphId = str;
    }

    public final void setPicture(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.picture = str;
    }

    public final void setSort(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.sort = str;
    }

    public final void setStart_position(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.start_position = str;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    @NotNull
    public String toString() {
        return "ParagraphReviewList(title=" + this.title + ", start_position=" + this.start_position + ", sort=" + this.sort + ", chapterId=" + this.chapterId + ", comment=" + this.comment + ", ctime=" + this.ctime + ", id=" + this.id + ", paragraphContent=" + this.paragraphContent + ", picture=" + this.picture + ", paragraphId=" + this.paragraphId + ')';
    }
}
