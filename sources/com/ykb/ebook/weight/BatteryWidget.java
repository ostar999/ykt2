package com.ykb.ebook.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import com.nirvana.tools.logger.cache.db.DBHelpTool;
import com.ykb.ebook.common.ReadConfig;
import com.ykb.ebook.extensions.StringExtensionsKt;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt___RangesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0017\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\fH\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0014J\u000e\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001e\u001a\u00020\fR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\fX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u000f\u001a\u00020\fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0007X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\nX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\fX\u0082D¢\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lcom/ykb/ebook/weight/BatteryWidget;", "Landroid/view/View;", com.umeng.analytics.pro.d.R, "Landroid/content/Context;", "attributeSet", "Landroid/util/AttributeSet;", "def", "", "(Landroid/content/Context;Landroid/util/AttributeSet;I)V", "capRectF", "Landroid/graphics/RectF;", "capWidthDp", "", "chargeLevel", "cornerRadiusDp", "innerPaddingDp", "innerRectF", "outerColor", "outerHeightDp", "outerRectF", "outerWidthDp", "rectPaint", "Landroid/graphics/Paint;", "dpToPx", "dp", "onDraw", "", "canvas", "Landroid/graphics/Canvas;", "setCurrentBatteryLevel", DBHelpTool.RecordEntry.COLUMN_NAME_LEVEL, "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes8.dex */
public final class BatteryWidget extends View {

    @NotNull
    private RectF capRectF;
    private final float capWidthDp;
    private float chargeLevel;
    private final float cornerRadiusDp;
    private final float innerPaddingDp;

    @NotNull
    private RectF innerRectF;
    private int outerColor;
    private final float outerHeightDp;

    @NotNull
    private RectF outerRectF;
    private final float outerWidthDp;

    @NotNull
    private final Paint rectPaint;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public BatteryWidget(@NotNull Context context, @NotNull AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attributeSet, "attributeSet");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public BatteryWidget(@NotNull Context context, @NotNull AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(attributeSet, "attributeSet");
        this.chargeLevel = 0.5f;
        this.outerColor = StringExtensionsKt.hexValue2IntColor("#909090");
        this.cornerRadiusDp = 2.67f;
        this.outerWidthDp = 22.0f;
        this.outerHeightDp = 14.0f;
        this.innerPaddingDp = 1.5f;
        this.capWidthDp = 1.8f;
        Paint paint = new Paint(1);
        this.rectPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        float fDpToPx = dpToPx(22.0f);
        float fDpToPx2 = dpToPx(14.0f);
        float fDpToPx3 = dpToPx(1.5f);
        float fDpToPx4 = dpToPx(1.8f);
        this.outerRectF = new RectF(fDpToPx3, fDpToPx3, (fDpToPx - fDpToPx3) - fDpToPx4, fDpToPx2 - fDpToPx3);
        float fDpToPx5 = this.outerRectF.left + dpToPx(1.5f);
        float fDpToPx6 = this.outerRectF.top + dpToPx(1.5f);
        RectF rectF = this.outerRectF;
        this.innerRectF = new RectF(fDpToPx5, fDpToPx6, (rectF.right * this.chargeLevel) - fDpToPx3, rectF.bottom - dpToPx(1.5f));
        RectF rectF2 = this.outerRectF;
        float f2 = rectF2.right + (0.1f * fDpToPx4);
        float f3 = 7;
        float fCenterY = rectF2.centerY() - dpToPx(14.0f / f3);
        RectF rectF3 = this.outerRectF;
        this.capRectF = new RectF(f2, fCenterY, rectF3.right + fDpToPx4 + (fDpToPx4 * 0.4f), rectF3.centerY() + dpToPx(14.0f / f3));
    }

    private final float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }

    @Override // android.view.View
    public void onDraw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        super.onDraw(canvas);
        ReadConfig readConfig = ReadConfig.INSTANCE;
        int iHexValue2IntColor = readConfig.getColorMode() == 2 ? StringExtensionsKt.hexValue2IntColor("#b2575F79") : StringExtensionsKt.hexValue2IntColor("#b2909090");
        this.outerColor = iHexValue2IntColor;
        this.rectPaint.setColor(iHexValue2IntColor);
        this.rectPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRoundRect(this.outerRectF, dpToPx(this.cornerRadiusDp), dpToPx(this.cornerRadiusDp), this.rectPaint);
        int iHexValue2IntColor2 = readConfig.getColorMode() == 2 ? StringExtensionsKt.hexValue2IntColor("#575F79") : StringExtensionsKt.hexValue2IntColor("#909090");
        this.outerColor = iHexValue2IntColor2;
        this.rectPaint.setColor(iHexValue2IntColor2);
        this.rectPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(this.innerRectF, dpToPx(1.33f), dpToPx(1.33f), this.rectPaint);
        int iHexValue2IntColor3 = readConfig.getColorMode() == 2 ? StringExtensionsKt.hexValue2IntColor("#b2575F79") : StringExtensionsKt.hexValue2IntColor("#b2909090");
        this.outerColor = iHexValue2IntColor3;
        this.rectPaint.setColor(iHexValue2IntColor3);
        this.rectPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(this.capRectF, -90.0f, 180.0f, true, this.rectPaint);
    }

    public final void setCurrentBatteryLevel(float level) {
        this.chargeLevel = RangesKt___RangesKt.coerceIn(level, 0.0f, 1.0f);
        RectF rectF = this.innerRectF;
        RectF rectF2 = this.outerRectF;
        rectF.right = (rectF2.left + (rectF2.width() * this.chargeLevel)) - dpToPx(this.innerPaddingDp);
        invalidate();
    }

    public /* synthetic */ BatteryWidget(Context context, AttributeSet attributeSet, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, attributeSet, (i3 & 4) != 0 ? 0 : i2);
    }
}
