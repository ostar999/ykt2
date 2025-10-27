package com.ykb.ebook.util;

import android.text.TextUtils;
import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.model.TextPage;
import com.ykb.ebook.page.column.BaseColumn;
import com.ykb.ebook.page.column.TextColumn;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0015\n\u0002\b\u0002\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0010\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002\u001a\u0018\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002\u001a\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t\u001a\u0018\u0010\f\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0002¨\u0006\r"}, d2 = {"getLineFirstIndex", "", "lines", "Lcom/ykb/ebook/model/TextLine;", "getLineLastIndex", "getParagraphEndIndexByClickColumn", "textPage", "Lcom/ykb/ebook/model/TextPage;", "clickColumn", "Lcom/ykb/ebook/page/column/TextColumn;", "getParagraphStartEndIndex", "", "getParagraphStartIndexByClickColumn", "ebook_release"}, k = 2, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nParagraphUtils.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParagraphUtils.kt\ncom/ykb/ebook/util/ParagraphUtilsKt\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,111:1\n1855#2:112\n1855#2,2:113\n1856#2:115\n1855#2:116\n1855#2,2:117\n1856#2:119\n1855#2,2:120\n1855#2,2:122\n*S KotlinDebug\n*F\n+ 1 ParagraphUtils.kt\ncom/ykb/ebook/util/ParagraphUtilsKt\n*L\n28#1:112\n29#1:113,2\n28#1:115\n58#1:116\n59#1:117,2\n58#1:119\n88#1:120,2\n103#1:122,2\n*E\n"})
/* loaded from: classes7.dex */
public final class ParagraphUtilsKt {
    private static final int getLineFirstIndex(TextLine textLine) {
        for (BaseColumn baseColumn : textLine.getColumns()) {
            if (baseColumn instanceof TextColumn) {
                TextColumn textColumn = (TextColumn) baseColumn;
                if (textColumn.getCharIndex() != -1) {
                    return textColumn.getCharIndex();
                }
            }
        }
        return -1;
    }

    private static final int getLineLastIndex(TextLine textLine) {
        int charIndex = -1;
        for (BaseColumn baseColumn : textLine.getColumns()) {
            if (baseColumn instanceof TextColumn) {
                TextColumn textColumn = (TextColumn) baseColumn;
                if (textColumn.getCharIndex() != -1) {
                    charIndex = textColumn.getCharIndex();
                }
            }
        }
        return charIndex;
    }

    private static final int getParagraphEndIndexByClickColumn(TextPage textPage, TextColumn textColumn) {
        Iterator<T> it = textPage.textChapter.getPages().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            for (TextLine textLine : ((TextPage) it.next()).getLines()) {
                if (textLine.getColumns().size() >= 3 && (textLine.getColumns().get(0) instanceof TextColumn) && (textLine.getColumns().get(1) instanceof TextColumn)) {
                    BaseColumn baseColumn = textLine.getColumns().get(0);
                    Intrinsics.checkNotNull(baseColumn, "null cannot be cast to non-null type com.ykb.ebook.page.column.TextColumn");
                    if (TextUtils.isEmpty(StringsKt__StringsKt.trim((CharSequence) ((TextColumn) baseColumn).getCharData()).toString())) {
                        BaseColumn baseColumn2 = textLine.getColumns().get(1);
                        Intrinsics.checkNotNull(baseColumn2, "null cannot be cast to non-null type com.ykb.ebook.page.column.TextColumn");
                        if (TextUtils.isEmpty(StringsKt__StringsKt.trim((CharSequence) ((TextColumn) baseColumn2).getCharData()).toString())) {
                            if (getLineFirstIndex(textLine) > textColumn.getCharIndex()) {
                                return i2;
                            }
                        }
                    }
                }
                int lineLastIndex = getLineLastIndex(textLine);
                if (lineLastIndex != -1) {
                    i2 = lineLastIndex;
                }
            }
        }
        return i2;
    }

    @NotNull
    public static final int[] getParagraphStartEndIndex(@NotNull TextPage textPage, @NotNull TextColumn clickColumn) {
        Intrinsics.checkNotNullParameter(textPage, "textPage");
        Intrinsics.checkNotNullParameter(clickColumn, "clickColumn");
        Log.INSTANCE.logD("ParagraphUtils", "触摸的位置：" + clickColumn.getCharIndex());
        return new int[]{getParagraphStartIndexByClickColumn(textPage, clickColumn), getParagraphEndIndexByClickColumn(textPage, clickColumn)};
    }

    private static final int getParagraphStartIndexByClickColumn(TextPage textPage, TextColumn textColumn) {
        int lineFirstIndex;
        Iterator<T> it = textPage.textChapter.getPages().iterator();
        int i2 = 0;
        while (it.hasNext()) {
            for (TextLine textLine : ((TextPage) it.next()).getLines()) {
                if (textLine.getColumns().size() >= 3 && (textLine.getColumns().get(0) instanceof TextColumn) && (textLine.getColumns().get(1) instanceof TextColumn)) {
                    BaseColumn baseColumn = textLine.getColumns().get(0);
                    Intrinsics.checkNotNull(baseColumn, "null cannot be cast to non-null type com.ykb.ebook.page.column.TextColumn");
                    if (TextUtils.isEmpty(StringsKt__StringsKt.trim((CharSequence) ((TextColumn) baseColumn).getCharData()).toString())) {
                        BaseColumn baseColumn2 = textLine.getColumns().get(1);
                        Intrinsics.checkNotNull(baseColumn2, "null cannot be cast to non-null type com.ykb.ebook.page.column.TextColumn");
                        if (TextUtils.isEmpty(StringsKt__StringsKt.trim((CharSequence) ((TextColumn) baseColumn2).getCharData()).toString()) && (lineFirstIndex = getLineFirstIndex(textLine)) != -1) {
                            if (lineFirstIndex >= textColumn.getCharIndex()) {
                                return i2;
                            }
                            i2 = lineFirstIndex;
                        }
                    } else {
                        continue;
                    }
                }
            }
        }
        return i2;
    }
}
