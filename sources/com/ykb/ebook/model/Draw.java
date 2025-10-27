package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.gson.annotations.SerializedName;
import com.ykb.ebook.common.PreferKeyKt;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b'\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BO\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003¢\u0006\u0002\u0010\fJ\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\u000b\u0010#\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0003HÆ\u0003J\t\u0010%\u001a\u00020\u0003HÆ\u0003J\t\u0010&\u001a\u00020\u0003HÆ\u0003J\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003Je\u0010)\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u0003HÆ\u0001J\u0013\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010-\u001a\u00020.HÖ\u0001J\t\u0010/\u001a\u00020\u0003HÖ\u0001R\u0018\u0010\u0006\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u001e\u0010\u000b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u000e\"\u0004\b\u0010\u0010\u0011R\u001e\u0010\u0007\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000e\"\u0004\b\u0013\u0010\u0011R\u001e\u0010\b\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000e\"\u0004\b\u0015\u0010\u0011R\u001e\u0010\t\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u000e\"\u0004\b\u0017\u0010\u0011R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000e\"\u0004\b\u0019\u0010\u0011R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000e\"\u0004\b\u001b\u0010\u0011R\u001e\u0010\n\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u000e\"\u0004\b\u001d\u0010\u0011R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u000e\"\u0004\b\u001f\u0010\u0011¨\u00060"}, d2 = {"Lcom/ykb/ebook/model/Draw;", "", "title", "", "sort", PreferKeyKt.START_POSITION, "chapterId", "ctime", "drawContent", "id", TtmlNode.TAG_STYLE, "color", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getChapterId", "()Ljava/lang/String;", "getColor", "setColor", "(Ljava/lang/String;)V", "getCtime", "setCtime", "getDrawContent", "setDrawContent", "getId", "setId", "getSort", "setSort", "getStart_position", "setStart_position", "getStyle", "setStyle", "getTitle", "setTitle", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class Draw {

    @SerializedName("chapter_id")
    @Nullable
    private final String chapterId;

    @SerializedName("color")
    @NotNull
    private String color;

    @SerializedName("ctime")
    @NotNull
    private String ctime;

    @SerializedName("draw_content")
    @NotNull
    private String drawContent;

    @SerializedName("id")
    @NotNull
    private String id;

    @NotNull
    private String sort;

    @NotNull
    private String start_position;

    @SerializedName(TtmlNode.TAG_STYLE)
    @NotNull
    private String style;

    @NotNull
    private String title;

    public Draw(@NotNull String title, @NotNull String sort, @NotNull String start_position, @Nullable String str, @NotNull String ctime, @NotNull String drawContent, @NotNull String id, @NotNull String style, @NotNull String color) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(sort, "sort");
        Intrinsics.checkNotNullParameter(start_position, "start_position");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(drawContent, "drawContent");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(style, "style");
        Intrinsics.checkNotNullParameter(color, "color");
        this.title = title;
        this.sort = sort;
        this.start_position = start_position;
        this.chapterId = str;
        this.ctime = ctime;
        this.drawContent = drawContent;
        this.id = id;
        this.style = style;
        this.color = color;
    }

    @NotNull
    /* renamed from: component1, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    /* renamed from: component2, reason: from getter */
    public final String getSort() {
        return this.sort;
    }

    @NotNull
    /* renamed from: component3, reason: from getter */
    public final String getStart_position() {
        return this.start_position;
    }

    @Nullable
    /* renamed from: component4, reason: from getter */
    public final String getChapterId() {
        return this.chapterId;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    /* renamed from: component6, reason: from getter */
    public final String getDrawContent() {
        return this.drawContent;
    }

    @NotNull
    /* renamed from: component7, reason: from getter */
    public final String getId() {
        return this.id;
    }

    @NotNull
    /* renamed from: component8, reason: from getter */
    public final String getStyle() {
        return this.style;
    }

    @NotNull
    /* renamed from: component9, reason: from getter */
    public final String getColor() {
        return this.color;
    }

    @NotNull
    public final Draw copy(@NotNull String title, @NotNull String sort, @NotNull String start_position, @Nullable String chapterId, @NotNull String ctime, @NotNull String drawContent, @NotNull String id, @NotNull String style, @NotNull String color) {
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(sort, "sort");
        Intrinsics.checkNotNullParameter(start_position, "start_position");
        Intrinsics.checkNotNullParameter(ctime, "ctime");
        Intrinsics.checkNotNullParameter(drawContent, "drawContent");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(style, "style");
        Intrinsics.checkNotNullParameter(color, "color");
        return new Draw(title, sort, start_position, chapterId, ctime, drawContent, id, style, color);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Draw)) {
            return false;
        }
        Draw draw = (Draw) other;
        return Intrinsics.areEqual(this.title, draw.title) && Intrinsics.areEqual(this.sort, draw.sort) && Intrinsics.areEqual(this.start_position, draw.start_position) && Intrinsics.areEqual(this.chapterId, draw.chapterId) && Intrinsics.areEqual(this.ctime, draw.ctime) && Intrinsics.areEqual(this.drawContent, draw.drawContent) && Intrinsics.areEqual(this.id, draw.id) && Intrinsics.areEqual(this.style, draw.style) && Intrinsics.areEqual(this.color, draw.color);
    }

    @Nullable
    public final String getChapterId() {
        return this.chapterId;
    }

    @NotNull
    public final String getColor() {
        return this.color;
    }

    @NotNull
    public final String getCtime() {
        return this.ctime;
    }

    @NotNull
    public final String getDrawContent() {
        return this.drawContent;
    }

    @NotNull
    public final String getId() {
        return this.id;
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
    public final String getStyle() {
        return this.style;
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        int iHashCode = ((((this.title.hashCode() * 31) + this.sort.hashCode()) * 31) + this.start_position.hashCode()) * 31;
        String str = this.chapterId;
        return ((((((((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.ctime.hashCode()) * 31) + this.drawContent.hashCode()) * 31) + this.id.hashCode()) * 31) + this.style.hashCode()) * 31) + this.color.hashCode();
    }

    public final void setColor(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.color = str;
    }

    public final void setCtime(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.ctime = str;
    }

    public final void setDrawContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.drawContent = str;
    }

    public final void setId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.id = str;
    }

    public final void setSort(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.sort = str;
    }

    public final void setStart_position(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.start_position = str;
    }

    public final void setStyle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.style = str;
    }

    public final void setTitle(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.title = str;
    }

    @NotNull
    public String toString() {
        return "Draw(title=" + this.title + ", sort=" + this.sort + ", start_position=" + this.start_position + ", chapterId=" + this.chapterId + ", ctime=" + this.ctime + ", drawContent=" + this.drawContent + ", id=" + this.id + ", style=" + this.style + ", color=" + this.color + ')';
    }
}
