package com.yddmi.doctor.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import com.zhpan.bannerview.utils.BannerUtils;
import com.zhpan.indicator.base.BaseIndicatorView;

/* loaded from: classes6.dex */
public class FigureIndicatorView extends BaseIndicatorView {
    private int backgroundColor;
    private Paint mPaint;
    private int radius;
    private int textColor;
    private int textSize;

    public FigureIndicatorView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getPageSize() > 1) {
            this.mPaint.setAntiAlias(true);
            this.mPaint.setColor(this.backgroundColor);
            canvas.drawRoundRect(new RectF(0.0f, BannerUtils.dp2px(6.0f), getWidth(), getHeight() - BannerUtils.dp2px(6.0f)), BannerUtils.dp2px(20.0f), BannerUtils.dp2px(20.0f), this.mPaint);
            this.mPaint.setColor(this.textColor);
            this.mPaint.setTextSize(this.textSize);
            String str = (getCurrentPosition() + 1) + "/" + getPageSize();
            int iMeasureText = (int) this.mPaint.measureText(str);
            Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
            int measuredHeight = getMeasuredHeight() - fontMetricsInt.bottom;
            int i2 = fontMetricsInt.top;
            canvas.drawText(str, (getWidth() - iMeasureText) / 2.0f, ((measuredHeight + i2) / 2) - i2, this.mPaint);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        int i4 = this.radius;
        setMeasuredDimension(i4 * 2, i4 * 2);
    }

    @Override // android.view.View
    public void setBackgroundColor(@ColorInt int i2) {
        this.backgroundColor = i2;
    }

    public void setRadius(int i2) {
        this.radius = i2;
    }

    public void setTextColor(int i2) {
        this.textColor = i2;
    }

    public void setTextSize(int i2) {
        this.textSize = i2;
    }

    public FigureIndicatorView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FigureIndicatorView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.radius = BannerUtils.dp2px(20.0f);
        this.backgroundColor = Color.parseColor("#88FF5252");
        this.textColor = -1;
        this.textSize = BannerUtils.dp2px(13.0f);
        this.mPaint = new Paint();
    }
}
