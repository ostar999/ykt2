package com.ykb.ebook.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u000f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u0019\u0010\t\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lcom/ykb/ebook/model/QuestionListData;", "", "list", "", "Lcom/ykb/ebook/model/QuestionDetailBean;", "(Ljava/util/List;)V", "getList", "()Ljava/util/List;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class QuestionListData {

    @NotNull
    private final List<QuestionDetailBean> list;

    /* JADX WARN: Multi-variable type inference failed */
    public QuestionListData(@NotNull List<? extends QuestionDetailBean> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        this.list = list;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ QuestionListData copy$default(QuestionListData questionListData, List list, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            list = questionListData.list;
        }
        return questionListData.copy(list);
    }

    @NotNull
    public final List<QuestionDetailBean> component1() {
        return this.list;
    }

    @NotNull
    public final QuestionListData copy(@NotNull List<? extends QuestionDetailBean> list) {
        Intrinsics.checkNotNullParameter(list, "list");
        return new QuestionListData(list);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof QuestionListData) && Intrinsics.areEqual(this.list, ((QuestionListData) other).list);
    }

    @NotNull
    public final List<QuestionDetailBean> getList() {
        return this.list;
    }

    public int hashCode() {
        return this.list.hashCode();
    }

    @NotNull
    public String toString() {
        return "QuestionListData(list=" + this.list + ')';
    }
}
