package com.ykb.ebook.page;

import com.ykb.ebook.common_interface.DataSource;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b&\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0011\u001a\u00020\u0012H&J\b\u0010\u0013\u001a\u00020\u0012H&J\b\u0010\u0014\u001a\u00020\u0012H&J\b\u0010\u0015\u001a\u00020\u0016H&J\b\u0010\u0017\u001a\u00020\u0016H&J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0012H&J\u0010\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0012H&R\u0012\u0010\u0006\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0003\u001a\u00020\u0004X\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0012\u0010\r\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\bR\u0012\u0010\u000f\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\b¨\u0006\u001b"}, d2 = {"Lcom/ykb/ebook/page/PageFactory;", "DATA", "", "dataSource", "Lcom/ykb/ebook/common_interface/DataSource;", "(Lcom/ykb/ebook/common_interface/DataSource;)V", "curPage", "getCurPage", "()Ljava/lang/Object;", "getDataSource", "()Lcom/ykb/ebook/common_interface/DataSource;", "nextPage", "getNextPage", "nextPlusPage", "getNextPlusPage", "prevPage", "getPrevPage", "hasNext", "", "hasNextPlus", "hasPrev", "moveToFirst", "", "moveToLast", "moveToNext", "upContent", "moveToPrev", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public abstract class PageFactory<DATA> {

    @NotNull
    private final DataSource dataSource;

    public PageFactory(@NotNull DataSource dataSource) {
        Intrinsics.checkNotNullParameter(dataSource, "dataSource");
        this.dataSource = dataSource;
    }

    public abstract DATA getCurPage();

    @NotNull
    public final DataSource getDataSource() {
        return this.dataSource;
    }

    public abstract DATA getNextPage();

    public abstract DATA getNextPlusPage();

    public abstract DATA getPrevPage();

    public abstract boolean hasNext();

    public abstract boolean hasNextPlus();

    public abstract boolean hasPrev();

    public abstract void moveToFirst();

    public abstract void moveToLast();

    public abstract boolean moveToNext(boolean upContent);

    public abstract boolean moveToPrev(boolean upContent);
}
