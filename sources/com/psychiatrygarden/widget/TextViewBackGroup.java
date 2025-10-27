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
public class TextViewBackGroup extends TextView {
    private static final int STROKE_WIDTH = 2;
    private int borderCol;
    private Paint borderPaint;

    public TextViewBackGroup(Context context) {
        super(context);
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
        canvas.drawRoundRect(new RectF(2.0f, 2.0f, getMeasuredWidth() - 2, getMeasuredHeight() - 2), 5.0f, 5.0f, this.borderPaint);
        super.onDraw(canvas);
    }

    public void setBorderColor(int newColor) {
        this.borderCol = newColor;
        invalidate();
        requestLayout();
    }

    public TextViewBackGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TextViewBorder, 0, 0);
        try {
            this.borderCol = typedArrayObtainStyledAttributes.getInteger(0, 0);
            typedArrayObtainStyledAttributes.recycle();
            Paint paint = new Paint();
            this.borderPaint = paint;
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            this.borderPaint.setStrokeWidth(2.0f);
            this.borderPaint.setAntiAlias(true);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }
}
