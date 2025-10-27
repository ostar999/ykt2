package com.ykb.ebook.page.column;

import android.graphics.Canvas;
import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.weight.ContentTextView;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H&J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0003H\u0016R\u0018\u0010\u0002\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\u0004\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u0018\u0010\b\u001a\u00020\u0003X¦\u000e¢\u0006\f\u001a\u0004\b\t\u0010\u0005\"\u0004\b\n\u0010\u0007R\u0018\u0010\u000b\u001a\u00020\fX¦\u000e¢\u0006\f\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010¨\u0006\u001a"}, d2 = {"Lcom/ykb/ebook/page/column/BaseColumn;", "", "end", "", "getEnd", "()F", "setEnd", "(F)V", "start", "getStart", "setStart", "textLine", "Lcom/ykb/ebook/model/TextLine;", "getTextLine", "()Lcom/ykb/ebook/model/TextLine;", "setTextLine", "(Lcom/ykb/ebook/model/TextLine;)V", "draw", "", "view", "Lcom/ykb/ebook/weight/ContentTextView;", "canvas", "Landroid/graphics/Canvas;", "isTouch", "", "x", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public interface BaseColumn {

    @Metadata(k = 3, mv = {1, 8, 0}, xi = 48)
    public static final class DefaultImpls {
        public static boolean isTouch(@NotNull BaseColumn baseColumn, float f2) {
            return f2 > baseColumn.getStart() && f2 < baseColumn.getEnd();
        }
    }

    void draw(@NotNull ContentTextView view, @NotNull Canvas canvas);

    float getEnd();

    float getStart();

    @NotNull
    TextLine getTextLine();

    boolean isTouch(float x2);

    void setEnd(float f2);

    void setStart(float f2);

    void setTextLine(@NotNull TextLine textLine);
}
