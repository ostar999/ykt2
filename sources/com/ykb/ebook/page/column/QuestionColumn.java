package com.ykb.ebook.page.column;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import com.ykb.ebook.R;
import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.page.column.BaseColumn;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.weight.ContentTextView;
import java.util.List;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B5\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0018\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\u0006\u0010&\u001a\u00020'H\u0016R\u001a\u0010\u0004\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0017R\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u000e\"\u0004\b\u0019\u0010\u0010R\u0016\u0010\u001a\u001a\n \u001b*\u0004\u0018\u00010\t0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u001c\u001a\u00020\u001dX\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!¨\u0006("}, d2 = {"Lcom/ykb/ebook/page/column/QuestionColumn;", "Lcom/ykb/ebook/page/column/BaseColumn;", "start", "", "end", "isSingle", "", "ids", "", "", "index", "", "(FFZLjava/util/List;I)V", "getEnd", "()F", "setEnd", "(F)V", "getIds", "()Ljava/util/List;", "getIndex", "()I", "setIndex", "(I)V", "()Z", "getStart", "setStart", "tag", "kotlin.jvm.PlatformType", "textLine", "Lcom/ykb/ebook/model/TextLine;", "getTextLine", "()Lcom/ykb/ebook/model/TextLine;", "setTextLine", "(Lcom/ykb/ebook/model/TextLine;)V", "draw", "", "view", "Lcom/ykb/ebook/weight/ContentTextView;", "canvas", "Landroid/graphics/Canvas;", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nQuestionColumn.kt\nKotlin\n*S Kotlin\n*F\n+ 1 QuestionColumn.kt\ncom/ykb/ebook/page/column/QuestionColumn\n+ 2 TextLine.kt\ncom/ykb/ebook/model/TextLine\n*L\n1#1,53:1\n53#2:54\n*S KotlinDebug\n*F\n+ 1 QuestionColumn.kt\ncom/ykb/ebook/page/column/QuestionColumn\n*L\n30#1:54\n*E\n"})
/* loaded from: classes7.dex */
public final class QuestionColumn implements BaseColumn {
    private float end;

    @NotNull
    private final List<String> ids;
    private int index;
    private final boolean isSingle;
    private float start;
    private final String tag;

    @NotNull
    private TextLine textLine;

    public QuestionColumn(float f2, float f3, boolean z2, @NotNull List<String> ids, int i2) {
        Intrinsics.checkNotNullParameter(ids, "ids");
        this.start = f2;
        this.end = f3;
        this.isSingle = z2;
        this.ids = ids;
        this.index = i2;
        this.tag = QuestionColumn.class.getSimpleName();
        this.textLine = TextLine.INSTANCE.getEmptyTextLine();
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public void draw(@NotNull ContentTextView view, @NotNull Canvas canvas) {
        RectF rectF;
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        TextLine textLine = getTextLine();
        float lineBottom = textLine.getLineBottom() - textLine.getLineTop();
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(view.getContext().getResources(), this.ids.size() == 1 ? R.drawable.icon_question_single : R.drawable.icon_question_mutli);
        if (getTextLine().isImage()) {
            rectF = new RectF(getStart(), 0.0f, getEnd(), lineBottom);
        } else {
            float end = (lineBottom - ((((getEnd() - getStart()) / bitmapDecodeResource.getWidth()) * bitmapDecodeResource.getHeight()) + 16)) / 2;
            float f2 = 8;
            rectF = new RectF(getStart() - f2, end, getEnd() + f2, lineBottom - end);
        }
        try {
            Result.Companion companion = Result.INSTANCE;
            canvas.drawBitmap(bitmapDecodeResource, (Rect) null, rectF, view.getImagePaint());
            objM783constructorimpl = Result.m783constructorimpl(Unit.INSTANCE);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.INSTANCE;
            objM783constructorimpl = Result.m783constructorimpl(ResultKt.createFailure(th));
        }
        Throwable thM786exceptionOrNullimpl = Result.m786exceptionOrNullimpl(objM783constructorimpl);
        if (thM786exceptionOrNullimpl != null) {
            Log log = Log.INSTANCE;
            String tag = this.tag;
            Intrinsics.checkNotNullExpressionValue(tag, "tag");
            String localizedMessage = thM786exceptionOrNullimpl.getLocalizedMessage();
            if (localizedMessage == null) {
                localizedMessage = "未知错误";
            } else {
                Intrinsics.checkNotNullExpressionValue(localizedMessage, "e.localizedMessage ?: \"未知错误\"");
            }
            log.logE(tag, localizedMessage);
        }
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public float getEnd() {
        return this.end;
    }

    @NotNull
    public final List<String> getIds() {
        return this.ids;
    }

    public final int getIndex() {
        return this.index;
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

    /* renamed from: isSingle, reason: from getter */
    public final boolean getIsSingle() {
        return this.isSingle;
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

    public /* synthetic */ QuestionColumn(float f2, float f3, boolean z2, List list, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(f2, f3, z2, list, (i3 & 16) != 0 ? 0 : i2);
    }
}
