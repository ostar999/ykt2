package com.ykb.ebook.page.column;

import android.content.AppCtxKt;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.TypedValue;
import com.psychiatrygarden.db.SQLHelper;
import com.ykb.ebook.R;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.model.TextLine;
import com.ykb.ebook.page.ChapterProvider;
import com.ykb.ebook.page.column.BaseColumn;
import com.ykb.ebook.page.pool.PaintPool;
import com.ykb.ebook.weight.ContentTextView;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt__StringsJVMKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b \n\u0002\u0010\u000b\n\u0002\b\u0014\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\b\u0086\b\u0018\u00002\u00020\u0001Bw\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\b\b\u0002\u0010\n\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u0012\b\b\u0002\u0010\f\u001a\u00020\b\u0012\b\b\u0002\u0010\r\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u000f\u001a\u00020\b\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0006¢\u0006\u0002\u0010\u0011J\t\u0010C\u001a\u00020\u0003HÆ\u0003J\t\u0010D\u001a\u00020\u0006HÆ\u0003J\t\u0010E\u001a\u00020\bHÆ\u0003J\t\u0010F\u001a\u00020\u0006HÆ\u0003J\t\u0010G\u001a\u00020\u0003HÆ\u0003J\t\u0010H\u001a\u00020\u0006HÆ\u0003J\t\u0010I\u001a\u00020\bHÆ\u0003J\t\u0010J\u001a\u00020\bHÆ\u0003J\t\u0010K\u001a\u00020\u0006HÆ\u0003J\t\u0010L\u001a\u00020\u0006HÆ\u0003J\t\u0010M\u001a\u00020\bHÆ\u0003J\t\u0010N\u001a\u00020\u0006HÆ\u0003J\u0081\u0001\u0010O\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\b2\b\b\u0002\u0010\n\u001a\u00020\u00062\b\b\u0002\u0010\u000b\u001a\u00020\u00062\b\b\u0002\u0010\f\u001a\u00020\b2\b\b\u0002\u0010\r\u001a\u00020\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u00062\b\b\u0002\u0010\u000f\u001a\u00020\b2\b\b\u0002\u0010\u0010\u001a\u00020\u0006HÆ\u0001J\u0018\u0010P\u001a\u00020Q2\u0006\u0010R\u001a\u00020S2\u0006\u0010T\u001a\u00020UH\u0016J\u0013\u0010V\u001a\u00020)2\b\u0010W\u001a\u0004\u0018\u00010XHÖ\u0003J\t\u0010Y\u001a\u00020\u0006HÖ\u0001J\t\u0010Z\u001a\u00020\bHÖ\u0001R\u001a\u0010\u0007\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\r\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0017\"\u0004\b\u001b\u0010\u0019R\u001a\u0010\u000f\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0013\"\u0004\b\u001d\u0010\u0015R\u001a\u0010\f\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u0013\"\u0004\b\u001f\u0010\u0015R\u001a\u0010\u000e\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0017\"\u0004\b!\u0010\u0019R\u001a\u0010\u0010\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0017\"\u0004\b#\u0010\u0019R\u001a\u0010\u0004\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010'R$\u0010*\u001a\u00020)2\u0006\u0010(\u001a\u00020)@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R$\u0010.\u001a\u00020)2\u0006\u0010(\u001a\u00020)@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b.\u0010+\"\u0004\b/\u0010-R$\u00100\u001a\u00020)2\u0006\u0010(\u001a\u00020)@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b0\u0010+\"\u0004\b1\u0010-R\u001a\u0010\n\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u0017\"\u0004\b3\u0010\u0019R\u001a\u0010\t\u001a\u00020\bX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\u0013\"\u0004\b5\u0010\u0015R\u001a\u0010\u000b\u001a\u00020\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u0017\"\u0004\b7\u0010\u0019R$\u00108\u001a\u00020)2\u0006\u0010(\u001a\u00020)@FX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b9\u0010+\"\u0004\b:\u0010-R\u001a\u0010\u0002\u001a\u00020\u0003X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b;\u0010%\"\u0004\b<\u0010'R\u001a\u0010=\u001a\u00020>X\u0096\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010B¨\u0006["}, d2 = {"Lcom/ykb/ebook/page/column/TextColumn;", "Lcom/ykb/ebook/page/column/BaseColumn;", "start", "", "end", "charIndex", "", "charData", "", "lineId", "lineColor", "lineStyle", "dashId", "dashColor", "dashLength", "dashContent", "dashStartPosition", "(FFILjava/lang/String;Ljava/lang/String;IILjava/lang/String;IILjava/lang/String;I)V", "getCharData", "()Ljava/lang/String;", "setCharData", "(Ljava/lang/String;)V", "getCharIndex", "()I", "setCharIndex", "(I)V", "getDashColor", "setDashColor", "getDashContent", "setDashContent", "getDashId", "setDashId", "getDashLength", "setDashLength", "getDashStartPosition", "setDashStartPosition", "getEnd", "()F", "setEnd", "(F)V", "value", "", "isDrawDashLine", "()Z", "setDrawDashLine", "(Z)V", "isDrawUnderLine", "setDrawUnderLine", "isSearchResult", "setSearchResult", "getLineColor", "setLineColor", "getLineId", "setLineId", "getLineStyle", "setLineStyle", SQLHelper.SELECTED, "getSelected", "setSelected", "getStart", "setStart", "textLine", "Lcom/ykb/ebook/model/TextLine;", "getTextLine", "()Lcom/ykb/ebook/model/TextLine;", "setTextLine", "(Lcom/ykb/ebook/model/TextLine;)V", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "draw", "", "view", "Lcom/ykb/ebook/weight/ContentTextView;", "canvas", "Landroid/graphics/Canvas;", "equals", "other", "", "hashCode", "toString", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
@SourceDebugExtension({"SMAP\nTextColumn.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TextColumn.kt\ncom/ykb/ebook/page/column/TextColumn\n+ 2 TextLine.kt\ncom/ykb/ebook/model/TextLine\n*L\n1#1,139:1\n53#2:140\n53#2:141\n53#2:142\n53#2:143\n*S KotlinDebug\n*F\n+ 1 TextColumn.kt\ncom/ykb/ebook/page/column/TextColumn\n*L\n91#1:140\n110#1:141\n124#1:142\n136#1:143\n*E\n"})
/* loaded from: classes7.dex */
public final /* data */ class TextColumn implements BaseColumn {

    @NotNull
    private String charData;
    private int charIndex;
    private int dashColor;

    @NotNull
    private String dashContent;

    @NotNull
    private String dashId;
    private int dashLength;
    private int dashStartPosition;
    private float end;
    private boolean isDrawDashLine;
    private boolean isDrawUnderLine;
    private boolean isSearchResult;
    private int lineColor;

    @NotNull
    private String lineId;
    private int lineStyle;
    private boolean selected;
    private float start;

    @NotNull
    private TextLine textLine;

    public TextColumn(float f2, float f3, int i2, @NotNull String charData, @NotNull String lineId, int i3, int i4, @NotNull String dashId, int i5, int i6, @NotNull String dashContent, int i7) {
        Intrinsics.checkNotNullParameter(charData, "charData");
        Intrinsics.checkNotNullParameter(lineId, "lineId");
        Intrinsics.checkNotNullParameter(dashId, "dashId");
        Intrinsics.checkNotNullParameter(dashContent, "dashContent");
        this.start = f2;
        this.end = f3;
        this.charIndex = i2;
        this.charData = charData;
        this.lineId = lineId;
        this.lineColor = i3;
        this.lineStyle = i4;
        this.dashId = dashId;
        this.dashColor = i5;
        this.dashLength = i6;
        this.dashContent = dashContent;
        this.dashStartPosition = i7;
        this.textLine = TextLine.INSTANCE.getEmptyTextLine();
    }

    public final float component1() {
        return getStart();
    }

    /* renamed from: component10, reason: from getter */
    public final int getDashLength() {
        return this.dashLength;
    }

    @NotNull
    /* renamed from: component11, reason: from getter */
    public final String getDashContent() {
        return this.dashContent;
    }

    /* renamed from: component12, reason: from getter */
    public final int getDashStartPosition() {
        return this.dashStartPosition;
    }

    public final float component2() {
        return getEnd();
    }

    /* renamed from: component3, reason: from getter */
    public final int getCharIndex() {
        return this.charIndex;
    }

    @NotNull
    /* renamed from: component4, reason: from getter */
    public final String getCharData() {
        return this.charData;
    }

    @NotNull
    /* renamed from: component5, reason: from getter */
    public final String getLineId() {
        return this.lineId;
    }

    /* renamed from: component6, reason: from getter */
    public final int getLineColor() {
        return this.lineColor;
    }

    /* renamed from: component7, reason: from getter */
    public final int getLineStyle() {
        return this.lineStyle;
    }

    @NotNull
    /* renamed from: component8, reason: from getter */
    public final String getDashId() {
        return this.dashId;
    }

    /* renamed from: component9, reason: from getter */
    public final int getDashColor() {
        return this.dashColor;
    }

    @NotNull
    public final TextColumn copy(float start, float end, int charIndex, @NotNull String charData, @NotNull String lineId, int lineColor, int lineStyle, @NotNull String dashId, int dashColor, int dashLength, @NotNull String dashContent, int dashStartPosition) {
        Intrinsics.checkNotNullParameter(charData, "charData");
        Intrinsics.checkNotNullParameter(lineId, "lineId");
        Intrinsics.checkNotNullParameter(dashId, "dashId");
        Intrinsics.checkNotNullParameter(dashContent, "dashContent");
        return new TextColumn(start, end, charIndex, charData, lineId, lineColor, lineStyle, dashId, dashColor, dashLength, dashContent, dashStartPosition);
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public void draw(@NotNull ContentTextView view, @NotNull Canvas canvas) {
        Context appCtx;
        int i2;
        Intrinsics.checkNotNullParameter(view, "view");
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        TextPaint titleNumPaint = getTextLine().getTextLineIsNum() ? ChapterProvider.getTitleNumPaint() : getTextLine().isTitle() ? ChapterProvider.getTitlePaint() : ChapterProvider.getContentPaint();
        PaintPool paintPool = PaintPool.INSTANCE;
        Paint paintObtain = paintPool.obtain();
        if (ReadConfig.INSTANCE.getColorMode() != 2) {
            appCtx = AppCtxKt.getAppCtx();
            i2 = R.color.color_303030;
        } else {
            appCtx = AppCtxKt.getAppCtx();
            i2 = R.color.color_7380a9;
        }
        titleNumPaint.setColor(appCtx.getColor(i2));
        paintObtain.set(titleNumPaint);
        canvas.drawText(this.charData, getStart(), getTextLine().getLineBase() - getTextLine().getLineTop(), paintObtain);
        paintPool.recycle(paintObtain);
        boolean z2 = (TextUtils.isEmpty(this.charData) || TextUtils.equals(" ", this.charData) || !(StringsKt__StringsJVMKt.isBlank(this.charData) ^ true)) ? false : true;
        if (this.selected) {
            float start = getStart();
            float end = getEnd();
            TextLine textLine = getTextLine();
            canvas.drawRect(start, 0.0f, end, textLine.getLineBottom() - textLine.getLineTop(), view.getSelectedPaint());
        }
        if (!this.isDrawUnderLine) {
            if (this.isDrawDashLine) {
                Paint paint = new Paint();
                paint.setColor(this.dashColor);
                paint.setStyle(Paint.Style.STROKE);
                paint.setStrokeWidth(TypedValue.applyDimension(1, 1.25f, AppCtxKt.getAppCtx().getResources().getDisplayMetrics()));
                paint.setPathEffect(new DashPathEffect(new float[]{10.0f, 5.0f, 10.0f, 5.0f}, 0.0f));
                if (z2) {
                    float start2 = getStart();
                    TextLine textLine2 = getTextLine();
                    float lineBottom = textLine2.getLineBottom() - textLine2.getLineTop();
                    float end2 = getEnd();
                    TextLine textLine3 = getTextLine();
                    canvas.drawLine(start2, lineBottom, end2, textLine3.getLineBottom() - textLine3.getLineTop(), paint);
                    return;
                }
                return;
            }
            return;
        }
        if (this.lineStyle != 0) {
            Paint paint2 = new Paint();
            int i3 = this.lineColor;
            paint2.setColor(i3 != 0 ? i3 != 1 ? i3 != 2 ? i3 != 3 ? i3 != 4 ? AppCtxKt.getAppCtx().getColor(R.color.color_ff9da6) : AppCtxKt.getAppCtx().getColor(R.color.color_26ffc86e) : AppCtxKt.getAppCtx().getColor(R.color.color_2690d691) : AppCtxKt.getAppCtx().getColor(R.color.color_2678aeff) : AppCtxKt.getAppCtx().getColor(R.color.color_26b6a0ff) : AppCtxKt.getAppCtx().getColor(R.color.color_26ff9da6));
            paint2.setStyle(Paint.Style.FILL);
            if (z2) {
                float start3 = getStart();
                float end3 = getEnd();
                TextLine textLine4 = getTextLine();
                canvas.drawRect(start3, 0.0f, end3, textLine4.getLineBottom() - textLine4.getLineTop(), paint2);
                return;
            }
            return;
        }
        Paint paint3 = new Paint();
        int i4 = this.lineColor;
        paint3.setColor(i4 != 0 ? i4 != 1 ? i4 != 2 ? i4 != 3 ? i4 != 4 ? AppCtxKt.getAppCtx().getColor(R.color.color_ff9da6) : AppCtxKt.getAppCtx().getColor(R.color.color_ffc86e) : AppCtxKt.getAppCtx().getColor(R.color.color_90d691) : AppCtxKt.getAppCtx().getColor(R.color.color_78aeff) : AppCtxKt.getAppCtx().getColor(R.color.color_b6a0ff) : AppCtxKt.getAppCtx().getColor(R.color.color_ff9da6));
        paint3.setStyle(Paint.Style.STROKE);
        paint3.setStrokeWidth(TypedValue.applyDimension(1, 1.25f, AppCtxKt.getAppCtx().getResources().getDisplayMetrics()));
        if (z2) {
            float start4 = getStart();
            TextLine textLine5 = getTextLine();
            float lineBottom2 = textLine5.getLineBottom() - textLine5.getLineTop();
            float end4 = getEnd();
            TextLine textLine6 = getTextLine();
            canvas.drawLine(start4, lineBottom2, end4, textLine6.getLineBottom() - textLine6.getLineTop(), paint3);
        }
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextColumn)) {
            return false;
        }
        TextColumn textColumn = (TextColumn) other;
        return Float.compare(getStart(), textColumn.getStart()) == 0 && Float.compare(getEnd(), textColumn.getEnd()) == 0 && this.charIndex == textColumn.charIndex && Intrinsics.areEqual(this.charData, textColumn.charData) && Intrinsics.areEqual(this.lineId, textColumn.lineId) && this.lineColor == textColumn.lineColor && this.lineStyle == textColumn.lineStyle && Intrinsics.areEqual(this.dashId, textColumn.dashId) && this.dashColor == textColumn.dashColor && this.dashLength == textColumn.dashLength && Intrinsics.areEqual(this.dashContent, textColumn.dashContent) && this.dashStartPosition == textColumn.dashStartPosition;
    }

    @NotNull
    public final String getCharData() {
        return this.charData;
    }

    public final int getCharIndex() {
        return this.charIndex;
    }

    public final int getDashColor() {
        return this.dashColor;
    }

    @NotNull
    public final String getDashContent() {
        return this.dashContent;
    }

    @NotNull
    public final String getDashId() {
        return this.dashId;
    }

    public final int getDashLength() {
        return this.dashLength;
    }

    public final int getDashStartPosition() {
        return this.dashStartPosition;
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public float getEnd() {
        return this.end;
    }

    public final int getLineColor() {
        return this.lineColor;
    }

    @NotNull
    public final String getLineId() {
        return this.lineId;
    }

    public final int getLineStyle() {
        return this.lineStyle;
    }

    public final boolean getSelected() {
        return this.selected;
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
        return (((((((((((((((((((((Float.floatToIntBits(getStart()) * 31) + Float.floatToIntBits(getEnd())) * 31) + this.charIndex) * 31) + this.charData.hashCode()) * 31) + this.lineId.hashCode()) * 31) + this.lineColor) * 31) + this.lineStyle) * 31) + this.dashId.hashCode()) * 31) + this.dashColor) * 31) + this.dashLength) * 31) + this.dashContent.hashCode()) * 31) + this.dashStartPosition;
    }

    /* renamed from: isDrawDashLine, reason: from getter */
    public final boolean getIsDrawDashLine() {
        return this.isDrawDashLine;
    }

    /* renamed from: isDrawUnderLine, reason: from getter */
    public final boolean getIsDrawUnderLine() {
        return this.isDrawUnderLine;
    }

    /* renamed from: isSearchResult, reason: from getter */
    public final boolean getIsSearchResult() {
        return this.isSearchResult;
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public boolean isTouch(float f2) {
        return BaseColumn.DefaultImpls.isTouch(this, f2);
    }

    public final void setCharData(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.charData = str;
    }

    public final void setCharIndex(int i2) {
        this.charIndex = i2;
    }

    public final void setDashColor(int i2) {
        this.dashColor = i2;
    }

    public final void setDashContent(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.dashContent = str;
    }

    public final void setDashId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.dashId = str;
    }

    public final void setDashLength(int i2) {
        this.dashLength = i2;
    }

    public final void setDashStartPosition(int i2) {
        this.dashStartPosition = i2;
    }

    public final void setDrawDashLine(boolean z2) {
        if (this.isDrawDashLine != z2) {
            getTextLine().invalidate();
        }
        this.isDrawDashLine = z2;
    }

    public final void setDrawUnderLine(boolean z2) {
        if (this.isDrawUnderLine != z2) {
            getTextLine().invalidate();
        }
        this.isDrawUnderLine = z2;
    }

    @Override // com.ykb.ebook.page.column.BaseColumn
    public void setEnd(float f2) {
        this.end = f2;
    }

    public final void setLineColor(int i2) {
        this.lineColor = i2;
    }

    public final void setLineId(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        this.lineId = str;
    }

    public final void setLineStyle(int i2) {
        this.lineStyle = i2;
    }

    public final void setSearchResult(boolean z2) {
        if (this.isSearchResult != z2) {
            getTextLine().invalidate();
            if (z2) {
                TextLine textLine = getTextLine();
                textLine.setSearchResultColumnCount(textLine.getSearchResultColumnCount() + 1);
            } else {
                getTextLine().setSearchResultColumnCount(r0.getSearchResultColumnCount() - 1);
            }
        }
        this.isSearchResult = z2;
    }

    public final void setSelected(boolean z2) {
        if (this.selected != z2) {
            getTextLine().invalidate();
        }
        this.selected = z2;
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
        return "TextColumn(start=" + getStart() + ", end=" + getEnd() + ", charIndex=" + this.charIndex + ", charData=" + this.charData + ", lineId=" + this.lineId + ", lineColor=" + this.lineColor + ", lineStyle=" + this.lineStyle + ", dashId=" + this.dashId + ", dashColor=" + this.dashColor + ", dashLength=" + this.dashLength + ", dashContent=" + this.dashContent + ", dashStartPosition=" + this.dashStartPosition + ')';
    }

    public /* synthetic */ TextColumn(float f2, float f3, int i2, String str, String str2, int i3, int i4, String str3, int i5, int i6, String str4, int i7, int i8, DefaultConstructorMarker defaultConstructorMarker) {
        this(f2, f3, i2, (i8 & 8) != 0 ? "" : str, (i8 & 16) != 0 ? "" : str2, (i8 & 32) != 0 ? -1 : i3, (i8 & 64) != 0 ? -1 : i4, (i8 & 128) != 0 ? "" : str3, (i8 & 256) != 0 ? -1 : i5, (i8 & 512) != 0 ? -1 : i6, (i8 & 1024) != 0 ? "" : str4, (i8 & 2048) != 0 ? -1 : i7);
    }
}
