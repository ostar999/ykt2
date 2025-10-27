package com.ykb.ebook.model;

import androidx.annotation.Keep;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Keep
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u001d\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tJ\u000e\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0000J\u001e\u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0007HÆ\u0003J;\u0010\u001f\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\u0007HÆ\u0001J\u0013\u0010 \u001a\u00020\u00072\b\u0010!\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\"\u001a\u00020\u0003HÖ\u0001J\u0006\u0010#\u001a\u00020\u0007J\u0006\u0010$\u001a\u00020%J\t\u0010&\u001a\u00020'HÖ\u0001J\u000e\u0010(\u001a\u00020%2\u0006\u0010\u0017\u001a\u00020\u0000J.\u0010(\u001a\u00020%2\u0006\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0007R\u001a\u0010\u0005\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\b\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u000e\"\u0004\b\u0011\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u000b\"\u0004\b\u0013\u0010\rR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u000b\"\u0004\b\u0015\u0010\r¨\u0006)"}, d2 = {"Lcom/ykb/ebook/model/TextPosition;", "", "relativePagePosition", "", "lineIndex", "columnIndex", "isTouch", "", "isLast", "(IIIZZ)V", "getColumnIndex", "()I", "setColumnIndex", "(I)V", "()Z", "setLast", "(Z)V", "setTouch", "getLineIndex", "setLineIndex", "getRelativePagePosition", "setRelativePagePosition", "compare", "pos", "relativePos", "charIndex", "component1", "component2", "component3", "component4", "component5", "copy", "equals", "other", "hashCode", "isSelected", "reset", "", "toString", "", "upData", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class TextPosition {
    private int columnIndex;
    private boolean isLast;
    private boolean isTouch;
    private int lineIndex;
    private int relativePagePosition;

    public TextPosition(int i2, int i3, int i4, boolean z2, boolean z3) {
        this.relativePagePosition = i2;
        this.lineIndex = i3;
        this.columnIndex = i4;
        this.isTouch = z2;
        this.isLast = z3;
    }

    public static /* synthetic */ TextPosition copy$default(TextPosition textPosition, int i2, int i3, int i4, boolean z2, boolean z3, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i2 = textPosition.relativePagePosition;
        }
        if ((i5 & 2) != 0) {
            i3 = textPosition.lineIndex;
        }
        int i6 = i3;
        if ((i5 & 4) != 0) {
            i4 = textPosition.columnIndex;
        }
        int i7 = i4;
        if ((i5 & 8) != 0) {
            z2 = textPosition.isTouch;
        }
        boolean z4 = z2;
        if ((i5 & 16) != 0) {
            z3 = textPosition.isLast;
        }
        return textPosition.copy(i2, i6, i7, z4, z3);
    }

    public final int compare(@NotNull TextPosition pos) {
        Intrinsics.checkNotNullParameter(pos, "pos");
        int i2 = this.relativePagePosition;
        int i3 = pos.relativePagePosition;
        if (i2 < i3) {
            return -3;
        }
        if (i2 > i3) {
            return 3;
        }
        int i4 = this.lineIndex;
        int i5 = pos.lineIndex;
        if (i4 < i5) {
            return -2;
        }
        if (i4 > i5) {
            return 2;
        }
        int i6 = this.columnIndex;
        int i7 = pos.columnIndex;
        if (i6 < i7) {
            return -1;
        }
        return i6 > i7 ? 1 : 0;
    }

    /* renamed from: component1, reason: from getter */
    public final int getRelativePagePosition() {
        return this.relativePagePosition;
    }

    /* renamed from: component2, reason: from getter */
    public final int getLineIndex() {
        return this.lineIndex;
    }

    /* renamed from: component3, reason: from getter */
    public final int getColumnIndex() {
        return this.columnIndex;
    }

    /* renamed from: component4, reason: from getter */
    public final boolean getIsTouch() {
        return this.isTouch;
    }

    /* renamed from: component5, reason: from getter */
    public final boolean getIsLast() {
        return this.isLast;
    }

    @NotNull
    public final TextPosition copy(int relativePagePosition, int lineIndex, int columnIndex, boolean isTouch, boolean isLast) {
        return new TextPosition(relativePagePosition, lineIndex, columnIndex, isTouch, isLast);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextPosition)) {
            return false;
        }
        TextPosition textPosition = (TextPosition) other;
        return this.relativePagePosition == textPosition.relativePagePosition && this.lineIndex == textPosition.lineIndex && this.columnIndex == textPosition.columnIndex && this.isTouch == textPosition.isTouch && this.isLast == textPosition.isLast;
    }

    public final int getColumnIndex() {
        return this.columnIndex;
    }

    public final int getLineIndex() {
        return this.lineIndex;
    }

    public final int getRelativePagePosition() {
        return this.relativePagePosition;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int hashCode() {
        int i2 = ((((this.relativePagePosition * 31) + this.lineIndex) * 31) + this.columnIndex) * 31;
        boolean z2 = this.isTouch;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (i2 + i3) * 31;
        boolean z3 = this.isLast;
        return i4 + (z3 ? 1 : z3 ? 1 : 0);
    }

    public final boolean isLast() {
        return this.isLast;
    }

    public final boolean isSelected() {
        return this.lineIndex >= 0 && this.columnIndex >= 0;
    }

    public final boolean isTouch() {
        return this.isTouch;
    }

    public final void reset() {
        this.relativePagePosition = 0;
        this.lineIndex = -1;
        this.columnIndex = -1;
        this.isTouch = true;
        this.isLast = false;
    }

    public final void setColumnIndex(int i2) {
        this.columnIndex = i2;
    }

    public final void setLast(boolean z2) {
        this.isLast = z2;
    }

    public final void setLineIndex(int i2) {
        this.lineIndex = i2;
    }

    public final void setRelativePagePosition(int i2) {
        this.relativePagePosition = i2;
    }

    public final void setTouch(boolean z2) {
        this.isTouch = z2;
    }

    @NotNull
    public String toString() {
        return "TextPosition(relativePagePosition=" + this.relativePagePosition + ", lineIndex=" + this.lineIndex + ", columnIndex=" + this.columnIndex + ", isTouch=" + this.isTouch + ", isLast=" + this.isLast + ')';
    }

    public final void upData(int relativePos, int lineIndex, int charIndex, boolean isTouch, boolean isLast) {
        this.relativePagePosition = relativePos;
        this.lineIndex = lineIndex;
        this.columnIndex = charIndex;
        this.isTouch = isTouch;
        this.isLast = isLast;
    }

    public final int compare(int relativePos, int lineIndex, int charIndex) {
        int i2 = this.relativePagePosition;
        if (i2 < relativePos) {
            return -3;
        }
        if (i2 > relativePos) {
            return 3;
        }
        int i3 = this.lineIndex;
        if (i3 < lineIndex) {
            return -2;
        }
        if (i3 > lineIndex) {
            return 2;
        }
        int i4 = this.columnIndex;
        if (i4 < charIndex) {
            return -1;
        }
        return i4 > charIndex ? 1 : 0;
    }

    public final void upData(@NotNull TextPosition pos) {
        Intrinsics.checkNotNullParameter(pos, "pos");
        this.relativePagePosition = pos.relativePagePosition;
        this.lineIndex = pos.lineIndex;
        this.columnIndex = pos.columnIndex;
        this.isTouch = pos.isTouch;
        this.isLast = pos.isLast;
    }

    public /* synthetic */ TextPosition(int i2, int i3, int i4, boolean z2, boolean z3, int i5, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, i3, i4, (i5 & 8) != 0 ? true : z2, (i5 & 16) != 0 ? false : z3);
    }
}
