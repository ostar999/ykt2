package com.ykb.ebook.model;

import cn.hutool.core.lang.RegexPool;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Regex;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001BU\u0012\u001a\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0005\u0012\u001a\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b¢\u0006\u0002\u0010\u000bJ\u0006\u0010\u000e\u001a\u00020\u0013J\u0006\u0010\u0012\u001a\u00020\u0013R%\u0010\u0002\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0005¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0016\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0016\u0010\t\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR*\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u00058\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\rR\u0016\u0010\n\u001a\u00020\b8\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000f¨\u0006\u0014"}, d2 = {"Lcom/ykb/ebook/model/ParagraphComment;", "", "hot", "Ljava/util/ArrayList;", "Lcom/ykb/ebook/model/BookReview;", "Lkotlin/collections/ArrayList;", "timeLine", "hotTotal", "", "moreHot", "timeLineTotal", "(Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getHot", "()Ljava/util/ArrayList;", "getHotTotal", "()Ljava/lang/String;", "getMoreHot", "getTimeLine", "getTimeLineTotal", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class ParagraphComment {

    @Nullable
    private final ArrayList<BookReview> hot;

    @SerializedName("hot_total")
    @NotNull
    private final String hotTotal;

    @SerializedName("more_hot")
    @NotNull
    private final String moreHot;

    @SerializedName("time_line")
    @Nullable
    private final ArrayList<BookReview> timeLine;

    @SerializedName("time_line_total")
    @NotNull
    private final String timeLineTotal;

    public ParagraphComment(@Nullable ArrayList<BookReview> arrayList, @Nullable ArrayList<BookReview> arrayList2, @NotNull String hotTotal, @NotNull String moreHot, @NotNull String timeLineTotal) {
        Intrinsics.checkNotNullParameter(hotTotal, "hotTotal");
        Intrinsics.checkNotNullParameter(moreHot, "moreHot");
        Intrinsics.checkNotNullParameter(timeLineTotal, "timeLineTotal");
        this.hot = arrayList;
        this.timeLine = arrayList2;
        this.hotTotal = hotTotal;
        this.moreHot = moreHot;
        this.timeLineTotal = timeLineTotal;
    }

    @Nullable
    public final ArrayList<BookReview> getHot() {
        return this.hot;
    }

    @NotNull
    public final String getHotTotal() {
        return this.hotTotal;
    }

    @NotNull
    public final String getMoreHot() {
        return this.moreHot;
    }

    @Nullable
    public final ArrayList<BookReview> getTimeLine() {
        return this.timeLine;
    }

    @NotNull
    public final String getTimeLineTotal() {
        return this.timeLineTotal;
    }

    public final int getHotTotal() {
        if (new Regex(RegexPool.NUMBERS).matches(this.hotTotal)) {
            return Integer.parseInt(this.hotTotal);
        }
        return 0;
    }

    public final int getTimeLineTotal() {
        if (new Regex(RegexPool.NUMBERS).matches(this.timeLineTotal)) {
            return Integer.parseInt(this.timeLineTotal);
        }
        return 0;
    }
}
