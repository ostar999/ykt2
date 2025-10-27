package com.ykb.ebook.common_interface;

import com.aliyun.player.aliyunplayerbase.net.ServiceCommon;
import com.ykb.ebook.model.TextChapter;
import com.ykb.ebook.page.ReadBook;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\bf\u0018\u00002\u00020\u0001J\b\u0010\u0011\u001a\u00020\u0007H&J\b\u0010\u0012\u001a\u00020\u0007H&J\u001c\u0010\u0013\u001a\u00020\u00142\b\b\u0002\u0010\u0015\u001a\u00020\f2\b\b\u0002\u0010\u0016\u001a\u00020\u0007H&R\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0014\u0010\t\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u0005R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\u0005¨\u0006\u0017"}, d2 = {"Lcom/ykb/ebook/common_interface/DataSource;", "", "currentChapter", "Lcom/ykb/ebook/model/TextChapter;", "getCurrentChapter", "()Lcom/ykb/ebook/model/TextChapter;", "isScroll", "", "()Z", "nextChapter", "getNextChapter", ServiceCommon.RequestKey.FORM_KEY_PAGE_INDEX, "", "getPageIndex", "()I", "prevChapter", "getPrevChapter", "hasNextChapter", "hasPrevChapter", "upContent", "", "relativePosition", "resetPageOffset", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes6.dex */
public interface DataSource {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        public static int getPageIndex(@NotNull DataSource dataSource) {
            return ReadBook.INSTANCE.getDurPageIndex();
        }

        public static /* synthetic */ void upContent$default(DataSource dataSource, int i2, boolean z2, int i3, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: upContent");
            }
            if ((i3 & 1) != 0) {
                i2 = 0;
            }
            if ((i3 & 2) != 0) {
                z2 = true;
            }
            dataSource.upContent(i2, z2);
        }
    }

    @Nullable
    TextChapter getCurrentChapter();

    @Nullable
    TextChapter getNextChapter();

    int getPageIndex();

    @Nullable
    TextChapter getPrevChapter();

    boolean hasNextChapter();

    boolean hasPrevChapter();

    boolean isScroll();

    void upContent(int relativePosition, boolean resetPageOffset);
}
