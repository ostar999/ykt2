package com.umeng.socialize.shareboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes6.dex */
public class IndicatorView extends View {
    private int mIndicatorMargin;
    private int mIndicatorWidth;
    private float mLeftPosition;
    private Paint mNormalPaint;
    private int mPageCount;
    private Paint mSelectPaint;
    private int mSelectPosition;

    public IndicatorView(Context context) {
        super(context);
    }

    private int measureHeight(int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            return size;
        }
        int paddingTop = getPaddingTop() + getPaddingBottom() + (this.mIndicatorWidth * 2);
        return mode == Integer.MIN_VALUE ? Math.min(paddingTop, size) : paddingTop;
    }

    private int measureWidth(int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        int paddingLeft = getPaddingLeft() + getPaddingRight();
        int i3 = this.mIndicatorWidth;
        int i4 = this.mPageCount;
        int i5 = paddingLeft + (i3 * i4 * 2) + (this.mIndicatorMargin * (i4 - 1));
        this.mLeftPosition = ((getMeasuredWidth() - i5) / 2.0f) + getPaddingLeft();
        return mode == 1073741824 ? Math.max(i5, size) : mode == Integer.MIN_VALUE ? Math.min(i5, size) : i5;
    }

    public int dip2px(float f2) {
        return (int) ((f2 * getContext().getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mSelectPaint == null || this.mNormalPaint == null) {
            return;
        }
        float f2 = this.mLeftPosition + this.mIndicatorWidth;
        int i2 = 0;
        while (i2 < this.mPageCount) {
            int i3 = this.mIndicatorWidth;
            canvas.drawCircle(f2, i3, i3, i2 == this.mSelectPosition ? this.mSelectPaint : this.mNormalPaint);
            f2 += this.mIndicatorMargin + (this.mIndicatorWidth * 2);
            i2++;
        }
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        setMeasuredDimension(measureWidth(i2), measureHeight(i3));
    }

    public void setIndicator(int i2, int i3) {
        this.mIndicatorMargin = dip2px(i3);
        this.mIndicatorWidth = dip2px(i2);
    }

    public void setIndicatorColor(int i2, int i3) {
        Paint paint = new Paint();
        this.mSelectPaint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.mSelectPaint.setAntiAlias(true);
        this.mSelectPaint.setColor(i3);
        Paint paint2 = new Paint();
        this.mNormalPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.mNormalPaint.setAntiAlias(true);
        this.mNormalPaint.setColor(i2);
    }

    public void setPageCount(int i2) {
        this.mPageCount = i2;
        invalidate();
    }

    public void setSelectedPosition(int i2) {
        this.mSelectPosition = i2;
        invalidate();
    }

    public IndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public IndicatorView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
    }

    @TargetApi(21)
    public IndicatorView(Context context, AttributeSet attributeSet, int i2, int i3) {
        super(context, attributeSet, i2, i3);
    }
}
