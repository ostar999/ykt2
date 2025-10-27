package com.ykb.ebook.page.column;

import android.content.AppCtxKt;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextPaint;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.page.ChapterProvider;
import com.ykb.ebook.page.column.BaseColumn;
import com.ykb.ebook.util.Log;
import com.ykb.ebook.util.ScreenUtil;
import com.ykb.ebook.weight.ContentTextView;
import java.util.ArrayList;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001B/\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\u0002\u0010\nJ\t\u0010'\u001a\u00020\u0003HÆ\u0003J\t\u0010(\u001a\u00020\u0003HÆ\u0003J\t\u0010)\u001a\u00020\u0006HÆ\u0003J\u000f\u0010*\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J7\u0010+\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0001J\u0018\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/2\u0006\u00100\u001a\u000201H\u0016J\u0013\u00102\u001a\u0002032\b\u00104\u001a\u0004\u0018\u000105HÖ\u0003J\t\u00106\u001a\u00020\u0006HÖ\u0001J\t\u00107\u001a\u00020\tHÖ\u0001R\u001b\u0010\u000b\u001a\u00020\t8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\f\u0010\rR\u001a\u0010\u0004\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001b\u0010\u0018\u001a\u00020\u00198FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u000f\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0011\"\u0004\b\u001e\u0010\u0013R\u0016\u0010\u001f\u001a\n  *\u0004\u0018\u00010\t0\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010!\u001a\u00020\"X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&¨\u00068"}, d2 = {"Lcom/ykb/ebook/page/column/ReviewColumn;", "Lcom/ykb/ebook/page/column/BaseColumn;", "start", "", "end", "index", "", "mList", "", "", "(FFILjava/util/List;)V", "countText", "getCountText", "()Ljava/lang/String;", "countText$delegate", "Lkotlin/Lazy;", "getEnd", "()F", "setEnd", "(F)V", "getIndex", "()I", "getMList", "()Ljava/util/List;", "path", "Landroid/graphics/Path;", "getPath", "()Landroid/graphics/Path;", "path$delegate", "getStart", "setStart", "tag", "kotlin.jvm.PlatformType", "textLine", "Lcom/ykb/ebook/model/TextLine;", "getTextLine", "()Lcom/ykb/ebook/model/TextLine;", "setTextLine", "(Lcom/ykb/ebook/model/TextLine;)V", "component1", "component2", "component3", "component4", "copy", "draw", "", "view", "Lcom/ykb/ebook/weight/ContentTextView;", "canvas", "Landroid/graphics/Canvas;", "equals", "", "other", "", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nReviewColumn.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ReviewColumn.kt\ncom/ykb/ebook/page/column/ReviewColumn\n+ 2 TextLine.kt\ncom/ykb/ebook/model/TextLine\n*L\n1#1,113:1\n53#2:114\n*S KotlinDebug\n*F\n+ 1 ReviewColumn.kt\ncom/ykb/ebook/page/column/ReviewColumn\n*L\n32#1:114\n*E\n"})
/* loaded from: classes7.dex */
public final /* data */ class ReviewColumn implements BaseColumn {

    /* renamed from: countText$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy countText;
    private float end;
    private final int index;

    @NotNull
    private final List<String> mList;

    /* renamed from: path$delegate, reason: from kotlin metadata */
    @NotNull
    private final Lazy path;
    private float start;
    private final String tag;

    @NotNull
    private TextLine textLine;

    public ReviewColumn(float f2, float f3, int i2, @NotNull List<String> mList) {
        Intrinsics.checkNotNullParameter(mList, "mList");
        this.start = f2;
        this.end = f3;
        this.index = i2;
        this.mList = mList;
        this.tag = ReviewColumn.class.getSimpleName();
        this.textLine = TextLine.INSTANCE.getEmptyTextLine();
        this.countText = LazyKt__LazyJVMKt.lazy(new Function0<String>() { // from class: com.ykb.ebook.page.column.ReviewColumn$countText$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final String invoke() throws NumberFormatException {
                int i3 = Integer.parseInt((String) StringsKt__StringsKt.split$default((CharSequence) this.this$0.getMList().get(this.this$0.getIndex() - 1), new String[]{","}, false, 0, 6, (Object) null).get(1));
                return i3 > 99 ? "99+" : String.valueOf(i3);
            }
        });
        this.path = LazyKt__LazyJVMKt.lazy(new Function0<Path>() { // from class: com.ykb.ebook.page.column.ReviewColumn$path$2
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            @NotNull
            public final Path invoke() {
                return new Path();
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ReviewColumn copy$default(ReviewColumn reviewColumn, float f2, float f3, int i2, List list, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            f2 = reviewColumn.getStart();
        }
        if ((i3 & 2) != 0) {
            f3 = reviewColumn.getEnd();
        }
        if ((i3 & 4) != 0) {
            i2 = reviewColumn.index;
        }
        if ((i3 & 8) != 0) {
            list = reviewColumn.mList;
        }
        return reviewColumn.copy(f2, f3, i2, list);
    }

    private final String getCountText() {
        return (String) this.countText.getValue();
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
        return this.mList;
    }

    @NotNull
    public final ReviewColumn copy(float start, float end, int index, @NotNull List<String> mList) {
        Intrinsics.checkNotNullParameter(mList, "mList");
        return new ReviewColumn(start, end, index, mList);
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public void draw(@NotNull ContentTextView view, @NotNull Canvas canvas) throws NumberFormatException {
        RectF rectF;
        int color;
        Object objM783constructorimpl;
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        TextLine textLine = getTextLine();
        float lineBottom = textLine.getLineBottom() - textLine.getLineTop();
        if (getTextLine().getColumns().size() == 2 && (getTextLine().getColumns().get(0) instanceof ImageColumn) && (getTextLine().getColumns().get(1) instanceof ReviewColumn)) {
            return;
        }
        int i2 = Integer.parseInt((String) StringsKt__StringsKt.split$default((CharSequence) this.mList.get(this.index - 1), new String[]{","}, false, 0, 6, (Object) null).get(2));
        boolean zAreEqual = Intrinsics.areEqual(getCountText(), "99+");
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(view.getContext().getResources(), i2 == 1 ? R.drawable.icon_review_hot : ReadConfig.INSTANCE.getColorMode() != 2 ? R.drawable.icon_review_normal : R.drawable.icon_review_normal_night);
        if (getTextLine().isImage()) {
            rectF = new RectF(getStart(), 0.0f, getEnd(), lineBottom);
        } else {
            float end = (lineBottom - (((getEnd() - getStart()) / bitmapDecodeResource.getWidth()) * bitmapDecodeResource.getHeight())) / 2;
            float fPxToSp = zAreEqual ? (int) ScreenUtil.pxToSp(view.getContext(), Float.valueOf(17.0f)) : 0;
            rectF = new RectF((getStart() - fPxToSp) + ((int) ScreenUtil.pxToSp(view.getContext(), Float.valueOf(11.0f))), end - (i2 == 1 ? (int) ScreenUtil.pxToSp(view.getContext(), Float.valueOf(14.0f)) : 0), getEnd() + fPxToSp, ((((float) bitmapDecodeResource.getHeight()) <= lineBottom || !zAreEqual) ? lineBottom - end : lineBottom) + (i2 == 1 ? (int) ScreenUtil.pxToSp(view.getContext(), Float.valueOf(14.0f)) : 0));
        }
        TextPaint titleNumPaint = getTextLine().getTextLineIsNum() ? ChapterProvider.getTitleNumPaint() : getTextLine().isTitle() ? ChapterProvider.getTitlePaint() : new TextPaint();
        titleNumPaint.setTextSize(20.0f);
        if (i2 == 1) {
            color = AppCtxKt.getAppCtx().getColor(R.color.white);
        } else {
            color = AppCtxKt.getAppCtx().getColor(ReadConfig.INSTANCE.getColorMode() != 2 ? R.color.color_909090 : R.color.color_575F79);
        }
        titleNumPaint.setColor(color);
        if (ReadConfig.INSTANCE.isShowReview()) {
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
        Paint.FontMetrics fontMetrics = titleNumPaint.getFontMetrics();
        float f2 = fontMetrics.bottom;
        float f3 = 2;
        float f4 = (lineBottom - ((lineBottom - (f2 - fontMetrics.top)) / f3)) - f2;
        float end2 = ((getEnd() - getStart()) - titleNumPaint.measureText(getCountText())) / f3;
        if (ReadConfig.INSTANCE.isShowReview()) {
            if (i2 == 1) {
                canvas.drawText(getCountText(), end2 + getStart() + ScreenUtil.pxToSp(view.getContext(), Float.valueOf(11.0f)), f4 + (Intrinsics.areEqual(getCountText(), "99+") ? (int) ScreenUtil.pxToSp(view.getContext(), Float.valueOf(25.0f)) : 4), titleNumPaint);
            } else {
                canvas.drawText(getCountText(), end2 + getStart() + ScreenUtil.pxToSp(view.getContext(), Float.valueOf(10.0f)), f4 + (zAreEqual ? (int) ScreenUtil.pxToSp(view.getContext(), Float.valueOf(10.0f)) : 0), titleNumPaint);
            }
        }
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ReviewColumn)) {
            return false;
        }
        ReviewColumn reviewColumn = (ReviewColumn) other;
        return Float.compare(getStart(), reviewColumn.getStart()) == 0 && Float.compare(getEnd(), reviewColumn.getEnd()) == 0 && this.index == reviewColumn.index && Intrinsics.areEqual(this.mList, reviewColumn.mList);
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public float getEnd() {
        return this.end;
    }

    public final int getIndex() {
        return this.index;
    }

    @NotNull
    public final List<String> getMList() {
        return this.mList;
    }

    @NotNull
    public final Path getPath() {
        return (Path) this.path.getValue();
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
        return (((((Float.floatToIntBits(getStart()) * 31) + Float.floatToIntBits(getEnd())) * 31) + this.index) * 31) + this.mList.hashCode();
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
        return "ReviewColumn(start=" + getStart() + ", end=" + getEnd() + ", index=" + this.index + ", mList=" + this.mList + ')';
    }

    public /* synthetic */ ReviewColumn(float f2, float f3, int i2, List list, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(f2, f3, (i3 & 4) != 0 ? 0 : i2, (i3 & 8) != 0 ? new ArrayList() : list);
    }
}
