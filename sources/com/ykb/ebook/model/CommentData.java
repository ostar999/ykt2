package com.ykb.ebook.model;

import androidx.annotation.Keep;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001BC\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0006¢\u0006\u0002\u0010\nJ\u000f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0006HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0006HÆ\u0003J\u000f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0006HÆ\u0003JG\u0010\u001e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u00062\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\t\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u001f\u001a\u00020 2\b\u0010!\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\"\u001a\u00020\u0006HÖ\u0001J\t\u0010#\u001a\u00020$HÖ\u0001R$\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001e\u0010\u0007\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0010\"\u0004\b\u0014\u0010\u0012R$\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\f\"\u0004\b\u0016\u0010\u000eR\u001e\u0010\t\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0010\"\u0004\b\u0018\u0010\u0012¨\u0006%"}, d2 = {"Lcom/ykb/ebook/model/CommentData;", "", "hot", "", "Lcom/ykb/ebook/model/BookReview;", "hot_total", "", "more_hot", "time_line", "time_line_total", "(Ljava/util/List;IILjava/util/List;I)V", "getHot", "()Ljava/util/List;", "setHot", "(Ljava/util/List;)V", "getHot_total", "()I", "setHot_total", "(I)V", "getMore_hot", "setMore_hot", "getTime_line", "setTime_line", "getTime_line_total", "setTime_line_total", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "", "other", "hashCode", "toString", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class CommentData {

    @SerializedName("hot")
    @NotNull
    private List<BookReview> hot;

    @SerializedName("hot_total")
    private int hot_total;

    @SerializedName("more_hot")
    private int more_hot;

    @SerializedName("time_line")
    @NotNull
    private List<BookReview> time_line;

    @SerializedName("time_line_total")
    private int time_line_total;

    public CommentData() {
        this(null, 0, 0, null, 0, 31, null);
    }

    public CommentData(@NotNull List<BookReview> hot, int i2, int i3, @NotNull List<BookReview> time_line, int i4) {
        Intrinsics.checkNotNullParameter(hot, "hot");
        Intrinsics.checkNotNullParameter(time_line, "time_line");
        this.hot = hot;
        this.hot_total = i2;
        this.more_hot = i3;
        this.time_line = time_line;
        this.time_line_total = i4;
    }

    public static /* synthetic */ CommentData copy$default(CommentData commentData, List list, int i2, int i3, List list2, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            list = commentData.hot;
        }
        if ((i5 & 2) != 0) {
            i2 = commentData.hot_total;
        }
        int i6 = i2;
        if ((i5 & 4) != 0) {
            i3 = commentData.more_hot;
        }
        int i7 = i3;
        if ((i5 & 8) != 0) {
            list2 = commentData.time_line;
        }
        List list3 = list2;
        if ((i5 & 16) != 0) {
            i4 = commentData.time_line_total;
        }
        return commentData.copy(list, i6, i7, list3, i4);
    }

    @NotNull
    public final List<BookReview> component1() {
        return this.hot;
    }

    /* renamed from: component2, reason: from getter */
    public final int getHot_total() {
        return this.hot_total;
    }

    /* renamed from: component3, reason: from getter */
    public final int getMore_hot() {
        return this.more_hot;
    }

    @NotNull
    public final List<BookReview> component4() {
        return this.time_line;
    }

    /* renamed from: component5, reason: from getter */
    public final int getTime_line_total() {
        return this.time_line_total;
    }

    @NotNull
    public final CommentData copy(@NotNull List<BookReview> hot, int hot_total, int more_hot, @NotNull List<BookReview> time_line, int time_line_total) {
        Intrinsics.checkNotNullParameter(hot, "hot");
        Intrinsics.checkNotNullParameter(time_line, "time_line");
        return new CommentData(hot, hot_total, more_hot, time_line, time_line_total);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CommentData)) {
            return false;
        }
        CommentData commentData = (CommentData) other;
        return Intrinsics.areEqual(this.hot, commentData.hot) && this.hot_total == commentData.hot_total && this.more_hot == commentData.more_hot && Intrinsics.areEqual(this.time_line, commentData.time_line) && this.time_line_total == commentData.time_line_total;
    }

    @NotNull
    public final List<BookReview> getHot() {
        return this.hot;
    }

    public final int getHot_total() {
        return this.hot_total;
    }

    public final int getMore_hot() {
        return this.more_hot;
    }

    @NotNull
    public final List<BookReview> getTime_line() {
        return this.time_line;
    }

    public final int getTime_line_total() {
        return this.time_line_total;
    }

    public int hashCode() {
        return (((((((this.hot.hashCode() * 31) + this.hot_total) * 31) + this.more_hot) * 31) + this.time_line.hashCode()) * 31) + this.time_line_total;
    }

    public final void setHot(@NotNull List<BookReview> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.hot = list;
    }

    public final void setHot_total(int i2) {
        this.hot_total = i2;
    }

    public final void setMore_hot(int i2) {
        this.more_hot = i2;
    }

    public final void setTime_line(@NotNull List<BookReview> list) {
        Intrinsics.checkNotNullParameter(list, "<set-?>");
        this.time_line = list;
    }

    public final void setTime_line_total(int i2) {
        this.time_line_total = i2;
    }

    @NotNull
    public String toString() {
        return "CommentData(hot=" + this.hot + ", hot_total=" + this.hot_total + ", more_hot=" + this.more_hot + ", time_line=" + this.time_line + ", time_line_total=" + this.time_line_total + ')';
    }

    public /* synthetic */ CommentData(List list, int i2, int i3, List list2, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this((i5 & 1) != 0 ? new ArrayList() : list, (i5 & 2) != 0 ? 0 : i2, (i5 & 4) != 0 ? 0 : i3, (i5 & 8) != 0 ? new ArrayList() : list2, (i5 & 16) == 0 ? i4 : 0);
    }
}
