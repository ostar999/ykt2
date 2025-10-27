package com.easefun.polyv.livecommon.ui.widget.roundview;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;
import androidx.annotation.ColorInt;
import androidx.annotation.Px;
import com.plv.foundationsdk.annos.Dp;
import com.plv.foundationsdk.annos.Sp;
import com.plv.thirdpart.blankj.utilcode.util.ConvertUtils;

/* loaded from: classes3.dex */
public class PLVRoundRectSpan extends ReplacementSpan {

    @Px
    private int marginLeft = 0;

    @Px
    private int marginRight = 0;

    @Px
    private int paddingLeft = 0;

    @Px
    private int paddingRight = 0;

    @Px
    private int radius = 0;

    @ColorInt
    private int backgroundColor = 0;

    @ColorInt
    private int textColor = -16777216;

    @Px
    private int textSize = ConvertUtils.sp2px(12.0f);

    public PLVRoundRectSpan backgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    @Override // android.text.style.ReplacementSpan
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x2, int top2, int y2, int bottom, Paint paint) {
        int color = paint.getColor();
        float textSize = paint.getTextSize();
        float f2 = paint.getFontMetrics().descent - paint.getFontMetrics().ascent;
        float f3 = (bottom - top2) / 2.0f;
        paint.setTextSize(this.textSize);
        float f4 = (paint.getFontMetrics().descent - paint.getFontMetrics().ascent) / f2;
        paint.setColor(this.backgroundColor);
        int i2 = this.marginLeft;
        RectF rectF = new RectF(x2 + i2, f3 - ((f3 - top2) * f4), x2 + i2 + paint.measureText(text, start, end) + this.paddingLeft + this.paddingRight, ((bottom - f3) * f4) + f3);
        int i3 = this.radius;
        canvas.drawRoundRect(rectF, i3, i3, paint);
        paint.setColor(this.textColor);
        canvas.drawText(text, start, end, x2 + this.marginLeft + this.paddingLeft, f3 + ((y2 - f3) * f4), paint);
        paint.setColor(color);
        paint.setTextSize(textSize);
    }

    @Override // android.text.style.ReplacementSpan
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        float textSize = paint.getTextSize();
        paint.setTextSize(this.textSize);
        int iMeasureText = (int) paint.measureText(text, start, end);
        paint.setTextSize(textSize);
        return iMeasureText + this.marginLeft + this.marginRight + this.paddingLeft + this.paddingRight;
    }

    public PLVRoundRectSpan marginLeft(@Dp int marginLeft) {
        this.marginLeft = ConvertUtils.dp2px(marginLeft);
        return this;
    }

    public PLVRoundRectSpan marginRight(@Dp int marginRight) {
        this.marginRight = ConvertUtils.dp2px(marginRight);
        return this;
    }

    public PLVRoundRectSpan paddingLeft(@Dp int paddingLeft) {
        this.paddingLeft = ConvertUtils.dp2px(paddingLeft);
        return this;
    }

    public PLVRoundRectSpan paddingRight(@Dp int paddingRight) {
        this.paddingRight = ConvertUtils.dp2px(paddingRight);
        return this;
    }

    public PLVRoundRectSpan radius(@Dp int radius) {
        this.radius = ConvertUtils.dp2px(radius);
        return this;
    }

    public PLVRoundRectSpan textColor(@ColorInt int textColor) {
        this.textColor = textColor;
        return this;
    }

    public PLVRoundRectSpan textSize(@Sp int textSize) {
        this.textSize = ConvertUtils.sp2px(textSize);
        return this;
    }
}
