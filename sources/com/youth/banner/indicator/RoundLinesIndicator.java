package com.youth.banner.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import androidx.annotation.Nullable;

/* loaded from: classes8.dex */
public class RoundLinesIndicator extends BaseIndicator {
    public RoundLinesIndicator(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.config.getIndicatorSize() <= 1) {
            return;
        }
        this.mPaint.setColor(this.config.getNormalColor());
        canvas.drawRoundRect(new RectF(0.0f, 0.0f, canvas.getWidth(), this.config.getHeight()), this.config.getRadius(), this.config.getRadius(), this.mPaint);
        this.mPaint.setColor(this.config.getSelectedColor());
        canvas.drawRoundRect(new RectF(this.config.getCurrentPosition() * this.config.getSelectedWidth(), 0.0f, r0 + this.config.getSelectedWidth(), this.config.getHeight()), this.config.getRadius(), this.config.getRadius(), this.mPaint);
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        int indicatorSize = this.config.getIndicatorSize();
        if (indicatorSize <= 1) {
            return;
        }
        setMeasuredDimension(this.config.getSelectedWidth() * indicatorSize, this.config.getHeight());
    }

    public RoundLinesIndicator(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundLinesIndicator(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mPaint.setStyle(Paint.Style.FILL);
    }
}
