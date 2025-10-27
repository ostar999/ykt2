package com.ykb.ebook.page;

import android.content.AppCtxKt;
import com.yikaobang.yixue.R2;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.common_interface.DataSource;
import com.ykb.ebook.model.TextChapter;
import com.ykb.ebook.model.TextPage;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0012H\u0016J\b\u0010\u0014\u001a\u00020\u0012H\u0016J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\b\u0010\u0017\u001a\u00020\u0016H\u0016J\u0010\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0012H\u0016J\u0010\u0010\u001a\u001a\u00020\u00122\u0006\u0010\u0019\u001a\u00020\u0012H\u0016R\u0014\u0010\u0006\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0014\u0010\r\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\bR\u0014\u0010\u000f\u001a\u00020\u00028VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0010\u0010\b¨\u0006\u001b"}, d2 = {"Lcom/ykb/ebook/page/TextPageFactory;", "Lcom/ykb/ebook/page/PageFactory;", "Lcom/ykb/ebook/model/TextPage;", "dataSource", "Lcom/ykb/ebook/common_interface/DataSource;", "(Lcom/ykb/ebook/common_interface/DataSource;)V", "curPage", "getCurPage", "()Lcom/ykb/ebook/model/TextPage;", "keepSwipeTip", "", "nextPage", "getNextPage", "nextPlusPage", "getNextPlusPage", "prevPage", "getPrevPage", "hasNext", "", "hasNextPlus", "hasPrev", "moveToFirst", "", "moveToLast", "moveToNext", "upContent", "moveToPrev", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTextPageFactory.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TextPageFactory.kt\ncom/ykb/ebook/page/TextPageFactory\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,161:1\n1#2:162\n*E\n"})
/* loaded from: classes7.dex */
public final class TextPageFactory extends PageFactory<TextPage> {

    @NotNull
    private final String keepSwipeTip;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TextPageFactory(@NotNull DataSource dataSource) {
        super(dataSource);
        Intrinsics.checkNotNullParameter(dataSource, "dataSource");
        String string = AppCtxKt.getAppCtx().getString(R.string.keep_swipe_tip);
        Intrinsics.checkNotNullExpressionValue(string, "appCtx.getString(R.string.keep_swipe_tip)");
        this.keepSwipeTip = string;
    }

    @Override // com.ykb.ebook.page.PageFactory
    public boolean hasNext() {
        DataSource dataSource = getDataSource();
        if (dataSource.hasNextChapter()) {
            return true;
        }
        if (dataSource.getCurrentChapter() != null) {
            TextChapter currentChapter = dataSource.getCurrentChapter();
            if (!(currentChapter != null && currentChapter.isLastIndex(dataSource.getPageIndex()))) {
                return true;
            }
        }
        return false;
    }

    @Override // com.ykb.ebook.page.PageFactory
    public boolean hasNextPlus() {
        DataSource dataSource = getDataSource();
        if (dataSource.hasNextChapter()) {
            return true;
        }
        int pageIndex = dataSource.getPageIndex();
        TextChapter currentChapter = dataSource.getCurrentChapter();
        return pageIndex < (currentChapter != null ? currentChapter.getPageSize() : 1) + (-2);
    }

    @Override // com.ykb.ebook.page.PageFactory
    public boolean hasPrev() {
        DataSource dataSource = getDataSource();
        return dataSource.hasPrevChapter() || dataSource.getPageIndex() > 0;
    }

    @Override // com.ykb.ebook.page.PageFactory
    public void moveToFirst() {
        ReadBook.INSTANCE.setPageIndex(0);
    }

    @Override // com.ykb.ebook.page.PageFactory
    public void moveToLast() {
        Unit unit;
        TextChapter currentChapter = getDataSource().getCurrentChapter();
        if (currentChapter != null) {
            if (currentChapter.getPageSize() == 0) {
                ReadBook.INSTANCE.setPageIndex(0);
            } else {
                ReadBook.INSTANCE.setPageIndex(currentChapter.getPageSize() - 1);
            }
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        if (unit == null) {
            ReadBook.INSTANCE.setPageIndex(0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x005d  */
    @Override // com.ykb.ebook.page.PageFactory
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean moveToNext(boolean r6) {
        /*
            r5 = this;
            com.ykb.ebook.common_interface.DataSource r0 = r5.getDataSource()
            boolean r1 = r5.hasNext()
            r2 = 0
            if (r1 == 0) goto L62
            int r1 = r0.getPageIndex()
            com.ykb.ebook.model.TextChapter r3 = r0.getCurrentChapter()
            r4 = 1
            if (r3 == 0) goto L43
            com.ykb.ebook.model.TextChapter r3 = r0.getCurrentChapter()
            if (r3 == 0) goto L24
            boolean r3 = r3.isLastIndex(r1)
            if (r3 != r4) goto L24
            r3 = r4
            goto L25
        L24:
            r3 = r2
        L25:
            if (r3 == 0) goto L28
            goto L43
        L28:
            if (r1 < 0) goto L55
            com.ykb.ebook.model.TextChapter r3 = r0.getCurrentChapter()
            if (r3 == 0) goto L38
            boolean r3 = r3.isLastIndexCurrent(r1)
            if (r3 != r4) goto L38
            r3 = r4
            goto L39
        L38:
            r3 = r2
        L39:
            if (r3 == 0) goto L3c
            goto L55
        L3c:
            com.ykb.ebook.page.ReadBook r3 = com.ykb.ebook.page.ReadBook.INSTANCE
            int r1 = r1 + r4
            r3.setPageIndex(r1)
            goto L5b
        L43:
            com.ykb.ebook.model.TextChapter r1 = r0.getCurrentChapter()
            if (r1 == 0) goto L4f
            boolean r1 = r0.getIsScroll()
            if (r1 == 0) goto L56
        L4f:
            com.ykb.ebook.model.TextChapter r1 = r0.getNextChapter()
            if (r1 != 0) goto L56
        L55:
            return r2
        L56:
            com.ykb.ebook.page.ReadBook r1 = com.ykb.ebook.page.ReadBook.INSTANCE
            r1.moveToNextChapter(r6, r2)
        L5b:
            if (r6 == 0) goto L61
            r6 = 0
            com.ykb.ebook.common_interface.DataSource.DefaultImpls.upContent$default(r0, r2, r2, r4, r6)
        L61:
            r2 = r4
        L62:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.page.TextPageFactory.moveToNext(boolean):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0034, code lost:
    
        if (((r1 == null || r1.getIsCompleted()) ? false : true) != false) goto L24;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0055  */
    @Override // com.ykb.ebook.page.PageFactory
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean moveToPrev(boolean r11) {
        /*
            r10 = this;
            com.ykb.ebook.common_interface.DataSource r0 = r10.getDataSource()
            boolean r1 = r10.hasPrev()
            r2 = 0
            if (r1 == 0) goto L5a
            int r1 = r0.getPageIndex()
            r3 = 1
            if (r1 > 0) goto L42
            com.ykb.ebook.model.TextChapter r1 = r0.getCurrentChapter()
            if (r1 != 0) goto L1f
            com.ykb.ebook.model.TextChapter r1 = r0.getPrevChapter()
            if (r1 != 0) goto L1f
            goto L48
        L1f:
            com.ykb.ebook.model.TextChapter r1 = r0.getPrevChapter()
            if (r1 == 0) goto L37
            com.ykb.ebook.model.TextChapter r1 = r0.getPrevChapter()
            if (r1 == 0) goto L33
            boolean r1 = r1.getIsCompleted()
            if (r1 != 0) goto L33
            r1 = r3
            goto L34
        L33:
            r1 = r2
        L34:
            if (r1 == 0) goto L37
            goto L48
        L37:
            com.ykb.ebook.page.ReadBook r4 = com.ykb.ebook.page.ReadBook.INSTANCE
            r6 = 0
            r7 = 0
            r8 = 2
            r9 = 0
            r5 = r11
            com.ykb.ebook.page.ReadBook.moveToPrevChapter$default(r4, r5, r6, r7, r8, r9)
            goto L53
        L42:
            com.ykb.ebook.model.TextChapter r1 = r0.getCurrentChapter()
            if (r1 != 0) goto L49
        L48:
            return r2
        L49:
            com.ykb.ebook.page.ReadBook r1 = com.ykb.ebook.page.ReadBook.INSTANCE
            int r4 = r0.getPageIndex()
            int r4 = r4 - r3
            r1.setPageIndex(r4)
        L53:
            if (r11 == 0) goto L59
            r11 = 0
            com.ykb.ebook.common_interface.DataSource.DefaultImpls.upContent$default(r0, r2, r2, r3, r11)
        L59:
            r2 = r3
        L5a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.page.TextPageFactory.moveToPrev(boolean):boolean");
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.ykb.ebook.page.PageFactory
    @NotNull
    public TextPage getCurPage() {
        DataSource dataSource = getDataSource();
        String msg = ReadBook.INSTANCE.getMsg();
        if (msg != null) {
            return new TextPage(0, msg, null, null, 0, 0, 0.0f, 0, R2.attr.actionModeSplitBackground, null).format();
        }
        TextChapter currentChapter = dataSource.getCurrentChapter();
        if (currentChapter == null) {
            return new TextPage(0, null, null, null, 0, 0, 0.0f, 0, 255, null).format();
        }
        TextPage page = currentChapter.getPage(dataSource.getPageIndex());
        if (page != null) {
            return page;
        }
        TextPage textPage = new TextPage(0, null, currentChapter.getTitle(), null, 0, 0, 0.0f, 0, R2.attr.actionModeSelectAllDrawable, null);
        textPage.textChapter = currentChapter;
        return textPage.format();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.ykb.ebook.page.PageFactory
    @NotNull
    public TextPage getNextPage() {
        TextPage textPage;
        TextPage textPage2;
        TextPage textPage3;
        DataSource dataSource = getDataSource();
        String msg = ReadBook.INSTANCE.getMsg();
        if (msg != null) {
            return new TextPage(0, msg, null, null, 0, 0, 0.0f, 0, R2.attr.actionModeSplitBackground, null).format();
        }
        TextChapter currentChapter = dataSource.getCurrentChapter();
        if (currentChapter != null) {
            int pageIndex = dataSource.getPageIndex();
            if (pageIndex < currentChapter.getPageSize() - 1) {
                if (ReadConfig.INSTANCE.getFirstPage()) {
                    TextPage page = currentChapter.getPage(0);
                    return (page == null || (textPage3 = page.format()) == null) ? new TextPage(0, null, currentChapter.getTitle(), null, 0, 0, 0.0f, 0, R2.attr.actionModeSelectAllDrawable, null).format() : textPage3;
                }
                TextPage page2 = currentChapter.getPage(pageIndex + 1);
                return (page2 == null || (textPage2 = page2.format()) == null) ? new TextPage(0, null, currentChapter.getTitle(), null, 0, 0, 0.0f, 0, R2.attr.actionModeSelectAllDrawable, null).format() : textPage2;
            }
            if (!currentChapter.getIsCompleted()) {
                return new TextPage(0, null, currentChapter.getTitle(), null, 0, 0, 0.0f, 0, R2.attr.actionModeSelectAllDrawable, null).format();
            }
        }
        TextChapter nextChapter = dataSource.getNextChapter();
        if (nextChapter == null) {
            return new TextPage(0, null, null, null, 0, 0, 0.0f, 0, 255, null).format();
        }
        TextPage page3 = nextChapter.getPage(0);
        return (page3 == null || (textPage = page3.format()) == null) ? new TextPage(0, null, nextChapter.getTitle(), null, 0, 0, 0.0f, 0, R2.attr.actionModeSelectAllDrawable, null).format() : textPage;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.ykb.ebook.page.PageFactory
    @NotNull
    public TextPage getNextPlusPage() {
        DataSource dataSource = getDataSource();
        TextChapter currentChapter = dataSource.getCurrentChapter();
        if (currentChapter != null) {
            int pageIndex = dataSource.getPageIndex();
            if (pageIndex < currentChapter.getPageSize() - 2) {
                return new TextPage(0, null, currentChapter.getTitle(), null, 0, 0, 0.0f, 0, R2.attr.actionModeSelectAllDrawable, null).format();
            }
            if (!currentChapter.getIsCompleted()) {
                return new TextPage(0, null, currentChapter.getTitle(), null, 0, 0, 0.0f, 0, R2.attr.actionModeSelectAllDrawable, null).format();
            }
            TextChapter nextChapter = dataSource.getNextChapter();
            if (nextChapter != null) {
                return pageIndex < currentChapter.getPageSize() + (-1) ? new TextPage(0, null, nextChapter.getTitle(), null, 0, 0, 0.0f, 0, R2.attr.actionModeSelectAllDrawable, null).format() : new TextPage(0, this.keepSwipeTip, null, null, 0, 0, 0.0f, 0, R2.attr.actionModeSplitBackground, null).format();
            }
        }
        return new TextPage(0, null, null, null, 0, 0, 0.0f, 0, 255, null).format();
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.ykb.ebook.page.PageFactory
    @NotNull
    public TextPage getPrevPage() {
        TextPage textPage;
        TextPage textPage2;
        DataSource dataSource = getDataSource();
        String msg = ReadBook.INSTANCE.getMsg();
        if (msg != null) {
            return new TextPage(0, msg, null, null, 0, 0, 0.0f, 0, R2.attr.actionModeSplitBackground, null).format();
        }
        TextChapter currentChapter = dataSource.getCurrentChapter();
        if (currentChapter != null) {
            int pageIndex = dataSource.getPageIndex();
            if (pageIndex > 0) {
                TextPage page = currentChapter.getPage(pageIndex - 1);
                return (page == null || (textPage2 = page.format()) == null) ? new TextPage(0, null, currentChapter.getTitle(), null, 0, 0, 0.0f, 0, R2.attr.actionModeSelectAllDrawable, null).format() : textPage2;
            }
            if (!currentChapter.getIsCompleted()) {
                return new TextPage(0, null, currentChapter.getTitle(), null, 0, 0, 0.0f, 0, R2.attr.actionModeSelectAllDrawable, null).format();
            }
        }
        TextChapter prevChapter = dataSource.getPrevChapter();
        if (prevChapter == null) {
            return new TextPage(0, null, null, null, 0, 0, 0.0f, 0, 255, null).format();
        }
        TextPage lastPage = prevChapter.getLastPage();
        return (lastPage == null || (textPage = lastPage.format()) == null) ? new TextPage(0, null, prevChapter.getTitle(), null, 0, 0, 0.0f, 0, R2.attr.actionModeSelectAllDrawable, null).format() : textPage;
    }
}
