package com.ykb.ebook.page.column;

import android.graphics.Canvas;
import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.page.column.BaseColumn;
import com.ykb.ebook.weight.ContentTextView;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u0014\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0018\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u0013\u0010\u001b\u001a\u00020\u001c2\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eHÖ\u0003J\t\u0010\u001f\u001a\u00020 HÖ\u0001J\t\u0010!\u001a\u00020\"HÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0007\"\u0004\b\u000b\u0010\tR\u001a\u0010\f\u001a\u00020\rX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011¨\u0006#"}, d2 = {"Lcom/ykb/ebook/page/column/ButtonColumn;", "Lcom/ykb/ebook/page/column/BaseColumn;", "start", "", "end", "(FF)V", "getEnd", "()F", "setEnd", "(F)V", "getStart", "setStart", "textLine", "Lcom/ykb/ebook/model/TextLine;", "getTextLine", "()Lcom/ykb/ebook/model/TextLine;", "setTextLine", "(Lcom/ykb/ebook/model/TextLine;)V", "component1", "component2", "copy", "draw", "", "view", "Lcom/ykb/ebook/weight/ContentTextView;", "canvas", "Landroid/graphics/Canvas;", "equals", "", "other", "", "hashCode", "", "toString", "", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class ButtonColumn implements BaseColumn {
    private float end;
    private float start;

    @NotNull
    private TextLine textLine = TextLine.INSTANCE.getEmptyTextLine();

    public ButtonColumn(float f2, float f3) {
        this.start = f2;
        this.end = f3;
    }

    public static /* synthetic */ ButtonColumn copy$default(ButtonColumn buttonColumn, float f2, float f3, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f2 = buttonColumn.getStart();
        }
        if ((i2 & 2) != 0) {
            f3 = buttonColumn.getEnd();
        }
        return buttonColumn.copy(f2, f3);
    }

    public final float component1() {
        return getStart();
    }

    public final float component2() {
        return getEnd();
    }

    @NotNull
    public final ButtonColumn copy(float start, float end) {
        return new ButtonColumn(start, end);
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public void draw(@NotNull ContentTextView view, @NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ButtonColumn)) {
            return false;
        }
        ButtonColumn buttonColumn = (ButtonColumn) other;
        return Float.compare(getStart(), buttonColumn.getStart()) == 0 && Float.compare(getEnd(), buttonColumn.getEnd()) == 0;
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public float getEnd() {
        return this.end;
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public float getStart() {
        return this.start;
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    @NotNull
    public TextLine getTextLine() {
        return this.textLine;
    }

    public int hashCode() {
        return (Float.floatToIntBits(getStart()) * 31) + Float.floatToIntBits(getEnd());
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public boolean isTouch(float f2) {
        return BaseColumn.DefaultImpls.isTouch(this, f2);
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public void setEnd(float f2) {
        this.end = f2;
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public void setStart(float f2) {
        this.start = f2;
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public void setTextLine(@NotNull TextLine textLine) {
        Intrinsics.checkNotNullParameter(textLine, "<set-?>");
        this.textLine = textLine;
    }

    @NotNull
    public String toString() {
        return "ButtonColumn(start=" + getStart() + ", end=" + getEnd() + ')';
    }
}
