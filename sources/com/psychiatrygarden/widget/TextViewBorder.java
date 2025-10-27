package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.TextView;
import com.yikaobang.yixue.R;

@SuppressLint({"AppCompatCustomView"})
/* loaded from: classes6.dex */
public class TextViewBorder extends TextView {
    private static final int STROKE_WIDTH = 2;
    private int borderCol;
    private Paint borderPaint;
    private float radius;

    public TextViewBorder(Context context) {
        super(context);
        this.radius = 180.0f;
    }

    public int getBordderColor() {
        return this.borderCol;
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        if (getText().toString().length() == 0) {
            return;
        }
        this.borderPaint.setColor(this.borderCol);
        RectF rectF = new RectF(2.0f, 2.0f, getMeasuredWidth() - 2, getMeasuredHeight() - 2);
        float f2 = this.radius;
        canvas.drawRoundRect(rectF, f2, f2, this.borderPaint);
        super.onDraw(canvas);
    }

    public void setBorderColor(int newColor) {
        this.borderCol = newColor;
        invalidate();
        requestLayout();
    }

    public void setBorderColorAndRadius(int newColor, float radius) {
        this.borderCol = newColor;
        this.radius = radius;
        invalidate();
        requestLayout();
    }

    public TextViewBorder(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.radius = 180.0f;
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextViewBorder, 0, 0);
        try {
            this.borderCol = typedArrayObtainStyledAttributes.getInteger(0, 0);
            typedArrayObtainStyledAttributes.recycle();
            Paint paint = new Paint();
            this.borderPaint = paint;
            paint.setStyle(Paint.Style.STROKE);
            this.borderPaint.setStrokeWidth(2.0f);
            this.borderPaint.setAntiAlias(true);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }
}
