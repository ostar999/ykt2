package com.ykb.ebook.page;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import com.ykb.ebook.model.TextLine;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\u0002\u0010\bJ\t\u0010%\u001a\u00020\u0003HÆ\u0003J\u0019\u0010&\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007HÆ\u0003J-\u0010'\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u0018\b\u0002\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007HÆ\u0001J\u0013\u0010(\u001a\u00020\u00142\b\u0010)\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010*\u001a\u00020\u0003HÖ\u0001J\t\u0010+\u001a\u00020 HÖ\u0001R\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0010\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u00148F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0011\u0010\u0016\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0012R\u0011\u0010\u0018\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u000fR\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u000f\"\u0004\b\u001b\u0010\u001cR\u0011\u0010\u001d\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u000fR\u0011\u0010\u001f\u001a\u00020 8F¢\u0006\u0006\u001a\u0004\b!\u0010\"R!\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$¨\u0006,"}, d2 = {"Lcom/ykb/ebook/page/TextParagraph;", "", "num", "", "textLines", "Ljava/util/ArrayList;", "Lcom/ykb/ebook/model/TextLine;", "Lkotlin/collections/ArrayList;", "(ILjava/util/ArrayList;)V", "chapterIndices", "Lkotlin/ranges/IntRange;", "getChapterIndices", "()Lkotlin/ranges/IntRange;", "chapterPosition", "getChapterPosition", "()I", "firstLine", "getFirstLine", "()Lcom/ykb/ebook/model/TextLine;", "isParagraphEnd", "", "()Z", "lastLine", "getLastLine", SessionDescription.ATTR_LENGTH, "getLength", "getNum", "setNum", "(I)V", "realNum", "getRealNum", "text", "", "getText", "()Ljava/lang/String;", "getTextLines", "()Ljava/util/ArrayList;", "component1", "component2", "copy", "equals", "other", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final /* data */ class TextParagraph {
    private int num;

    @NotNull
    private final ArrayList<TextLine> textLines;

    public TextParagraph(int i2, @NotNull ArrayList<TextLine> textLines) {
        Intrinsics.checkNotNullParameter(textLines, "textLines");
        this.num = i2;
        this.textLines = textLines;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ TextParagraph copy$default(TextParagraph textParagraph, int i2, ArrayList arrayList, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i2 = textParagraph.num;
        }
        if ((i3 & 2) != 0) {
            arrayList = textParagraph.textLines;
        }
        return textParagraph.copy(i2, arrayList);
    }

    /* renamed from: component1, reason: from getter */
    public final int getNum() {
        return this.num;
    }

    @NotNull
    public final ArrayList<TextLine> component2() {
        return this.textLines;
    }

    @NotNull
    public final TextParagraph copy(int num, @NotNull ArrayList<TextLine> textLines) {
        Intrinsics.checkNotNullParameter(textLines, "textLines");
        return new TextParagraph(num, textLines);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextParagraph)) {
            return false;
        }
        TextParagraph textParagraph = (TextParagraph) other;
        return this.num == textParagraph.num && Intrinsics.areEqual(this.textLines, textParagraph.textLines);
    }

    @NotNull
    public final IntRange getChapterIndices() {
        return new IntRange(getFirstLine().getChapterPosition(), getLastLine().getChapterPosition() + getLastLine().getCharSize());
    }

    public final int getChapterPosition() {
        return getFirstLine().getChapterPosition();
    }

    @NotNull
    public final TextLine getFirstLine() {
        return (TextLine) CollectionsKt___CollectionsKt.first((List) this.textLines);
    }

    @NotNull
    public final TextLine getLastLine() {
        return (TextLine) CollectionsKt___CollectionsKt.last((List) this.textLines);
    }

    public final int getLength() {
        return getText().length();
    }

    public final int getNum() {
        return this.num;
    }

    public final int getRealNum() {
        return getFirstLine().getParagraphNum();
    }

    @NotNull
    public final String getText() {
        return CollectionsKt___CollectionsKt.joinToString$default(this.textLines, "", null, null, 0, null, new Function1<TextLine, CharSequence>() { // from class: com.ykb.ebook.page.TextParagraph$text$1
            @Override // kotlin.jvm.functions.Function1
            @NotNull
            public final CharSequence invoke(@NotNull TextLine it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getText();
            }
        }, 30, null);
    }

    @NotNull
    public final ArrayList<TextLine> getTextLines() {
        return this.textLines;
    }

    public int hashCode() {
        return (this.num * 31) + this.textLines.hashCode();
    }

    public final boolean isParagraphEnd() {
        return getLastLine().isParagraphEnd();
    }

    public final void setNum(int i2) {
        this.num = i2;
    }

    @NotNull
    public String toString() {
        return "TextParagraph(num=" + this.num + ", textLines=" + this.textLines + ')';
    }

    public /* synthetic */ TextParagraph(int i2, ArrayList arrayList, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i2, (i3 & 2) != 0 ? new ArrayList() : arrayList);
    }
}
