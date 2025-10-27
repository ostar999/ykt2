package com.necer.drawable;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import com.necer.utils.NAttrs;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\fH\u0017J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000eH\u0002J\u0006\u0010\u0010\u001a\u00020\bJ\u0010\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\fH\u0016J\u0012\u0010\u0013\u001a\u00020\b2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\u0010\u0010\u0016\u001a\u00020\b2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0017"}, d2 = {"Lcom/necer/drawable/TextDrawable;", "Landroid/graphics/drawable/Drawable;", "()V", "text", "", "textPaint", "Landroid/graphics/Paint;", "draw", "", "canvas", "Landroid/graphics/Canvas;", "getOpacity", "", "getTextBaseLineY", "", "centerY", "initBoldText", "setAlpha", "alpha", "setColorFilter", "colorFilter", "Landroid/graphics/ColorFilter;", "setText", "ncalendar_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class TextDrawable extends Drawable {

    @Nullable
    private String text;

    @NotNull
    private final Paint textPaint;

    public TextDrawable() {
        Paint paint = new Paint();
        this.textPaint = paint;
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        NAttrs nAttrs = NAttrs.INSTANCE;
        paint.setTextSize(nAttrs.getNumberBackgroundTextSize());
        paint.setColor(nAttrs.getNumberBackgroundTextColor());
        paint.setAlpha(nAttrs.getBackgroundAlphaColor());
    }

    private final float getTextBaseLineY(float centerY) {
        Paint.FontMetrics fontMetrics = this.textPaint.getFontMetrics();
        float f2 = fontMetrics.bottom;
        float f3 = fontMetrics.top;
        return (centerY - ((f2 - f3) / 2)) - f3;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(@NotNull Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        Rect bounds = getBounds();
        Intrinsics.checkNotNullExpressionValue(bounds, "bounds");
        String str = this.text;
        Intrinsics.checkNotNull(str);
        canvas.drawText(str, bounds.centerX(), getTextBaseLineY(bounds.centerY()), this.textPaint);
    }

    @Override // android.graphics.drawable.Drawable
    @SuppressLint({"WrongConstant"})
    public int getOpacity() {
        return 0;
    }

    public final void initBoldText() {
        this.textPaint.setFakeBoldText(true);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
        this.textPaint.setAlpha(alpha);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        this.textPaint.setColorFilter(colorFilter);
    }

    public final void setText(@Nullable String text) {
        this.text = text;
    }
}
