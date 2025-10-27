package com.ykb.ebook.page.column;

import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.page.column.BaseColumn;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0006HÆ\u0003J\u000f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J7\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0001J\u0018\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)H\u0016J\u0013\u0010*\u001a\u00020+2\b\u0010,\u001a\u0004\u0018\u00010-HÖ\u0003J\t\u0010.\u001a\u00020\u0006HÖ\u0001J\t\u0010/\u001a\u00020\tHÖ\u0001R\u001a\u0010\u0004\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\f\"\u0004\b\u0016\u0010\u000eR\u0016\u0010\u0017\u001a\n \u0018*\u0004\u0018\u00010\t0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0019\u001a\u00020\u001aX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001e¨\u00060"}, d2 = {"Lcom/ykb/ebook/page/column/ImageColumn;", "Lcom/ykb/ebook/page/column/BaseColumn;", "start", "", "end", "index", "", "srcList", "", "", "(FFILjava/util/List;)V", "getEnd", "()F", "setEnd", "(F)V", "getIndex", "()I", "setIndex", "(I)V", "getSrcList", "()Ljava/util/List;", "getStart", "setStart", "tag", "kotlin.jvm.PlatformType", "textLine", "Lcom/ykb/ebook/model/TextLine;", "getTextLine", "()Lcom/ykb/ebook/model/TextLine;", "setTextLine", "(Lcom/ykb/ebook/model/TextLine;)V", "component1", "component2", "component3", "component4", "copy", "draw", "", "view", "Lcom/ykb/ebook/weight/ContentTextView;", "canvas", "Landroid/graphics/Canvas;", "equals", "", "other", "", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nImageColumn.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ImageColumn.kt\ncom/ykb/ebook/page/column/ImageColumn\n+ 2 TextLine.kt\ncom/ykb/ebook/model/TextLine\n*L\n1#1,74:1\n53#2:75\n*S KotlinDebug\n*F\n+ 1 ImageColumn.kt\ncom/ykb/ebook/page/column/ImageColumn\n*L\n29#1:75\n*E\n"})
/* loaded from: classes7.dex */
public final /* data */ class ImageColumn implements BaseColumn {
    private float end;
    private int index;

    @NotNull
    private final List<String> srcList;
    private float start;
    private final String tag;

    @NotNull
    private TextLine textLine;

    public ImageColumn(float f2, float f3, int i2, @NotNull List<String> srcList) {
        Intrinsics.checkNotNullParameter(srcList, "srcList");
        this.start = f2;
        this.end = f3;
        this.index = i2;
        this.srcList = srcList;
        this.tag = ImageColumn.class.getSimpleName();
        this.textLine = TextLine.INSTANCE.getEmptyTextLine();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ImageColumn copy$default(ImageColumn imageColumn, float f2, float f3, int i2, List list, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            f2 = imageColumn.getStart();
        }
        if ((i3 & 2) != 0) {
            f3 = imageColumn.getEnd();
        }
        if ((i3 & 4) != 0) {
            i2 = imageColumn.index;
        }
        if ((i3 & 8) != 0) {
            list = imageColumn.srcList;
        }
        return imageColumn.copy(f2, f3, i2, list);
    }

    public final float component1() {
        return getStart();
    }

    public final float component2() {
        return getEnd();
    }

    /* renamed from: component3, reason: from getter */
    public final int getIndex() {
        return this.index;
    }

    @NotNull
    public final List<String> component4() {
        return this.srcList;
    }

    @NotNull
    public final ImageColumn copy(float start, float end, int index, @NotNull List<String> srcList) {
        Intrinsics.checkNotNullParameter(srcList, "srcList");
        return new ImageColumn(start, end, index, srcList);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0052  */
    @Override // com.ykb.ebook.page.column.BaseColumn
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void draw(@org.jetbrains.annotations.NotNull com.ykb.ebook.weight.ContentTextView r9, @org.jetbrains.annotations.NotNull android.graphics.Canvas r10) {
        /*
            Method dump skipped, instructions count: 270
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ykb.ebook.page.column.ImageColumn.draw(com.ykb.ebook.weight.ContentTextView, android.graphics.Canvas):void");
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ImageColumn)) {
            return false;
        }
        ImageColumn imageColumn = (ImageColumn) other;
        return Float.compare(getStart(), imageColumn.getStart()) == 0 && Float.compare(getEnd(), imageColumn.getEnd()) == 0 && this.index == imageColumn.index && Intrinsics.areEqual(this.srcList, imageColumn.srcList);
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public float getEnd() {
        return this.end;
    }

    public final int getIndex() {
        return this.index;
    }

    @NotNull
    public final List<String> getSrcList() {
        return this.srcList;
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
        return (((((Float.floatToIntBits(getStart()) * 31) + Float.floatToIntBits(getEnd())) * 31) + this.index) * 31) + this.srcList.hashCode();
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public boolean isTouch(float f2) {
        return BaseColumn.DefaultImpls.isTouch(this, f2);
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public void setEnd(float f2) {
        this.end = f2;
    }

    public final void setIndex(int i2) {
        this.index = i2;
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
        return "ImageColumn(start=" + getStart() + ", end=" + getEnd() + ", index=" + this.index + ", srcList=" + this.srcList + ')';
    }

    public /* synthetic */ ImageColumn(float f2, float f3, int i2, List list, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(f2, f3, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? new ArrayList() : list);
    }
}
