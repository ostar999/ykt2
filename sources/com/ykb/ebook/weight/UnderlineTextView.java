package com.ykb.ebook.weight;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.internal.view.SupportMenu;
import com.ykb.ebook.R;

/* loaded from: classes8.dex */
public class UnderlineTextView extends AppCompatTextView {
    private float density;
    private int mColor;
    private float mLineTopMargin;
    private Paint mPaint;
    private Rect mRect;
    private float mStrokeWidth;

    public UnderlineTextView(Context context) {
        this(context, null, 0);
    }

    private void init(Context context, AttributeSet attributeSet, int i2) {
        this.density = context.getResources().getDisplayMetrics().density;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.UnderlineTextView, i2, 0);
        this.mColor = typedArrayObtainStyledAttributes.getColor(R.styleable.UnderlineTextView_underlineColor, SupportMenu.CATEGORY_MASK);
        this.mStrokeWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.UnderlineTextView_underlineWidth, this.density * 2.0f);
        float dimension = typedArrayObtainStyledAttributes.getDimension(R.styleable.UnderlineTextView_underlineTopMargin, this.density * 2.0f);
        this.mLineTopMargin = dimension;
        setLineSpacing(dimension, 1.5f);
        setPadding(getLeft(), getTop(), getRight(), getBottom());
        typedArrayObtainStyledAttributes.recycle();
        this.mRect = new Rect();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mPaint.setColor(this.mColor);
        this.mPaint.setStrokeWidth(this.mStrokeWidth);
    }

    public int getUnderLineColor() {
        return this.mColor;
    }

    public float getUnderlineWidth() {
        return this.mStrokeWidth;
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        int lineCount = getLineCount();
        Layout layout = getLayout();
        for (int i2 = 0; i2 < lineCount; i2++) {
            int lineBounds = getLineBounds(i2, this.mRect);
            int lineStart = layout.getLineStart(i2);
            int lineEnd = layout.getLineEnd(i2);
            float primaryHorizontal = layout.getPrimaryHorizontal(lineStart);
            float primaryHorizontal2 = layout.getPrimaryHorizontal(lineEnd - 1) + (layout.getPrimaryHorizontal(lineStart + 1) - primaryHorizontal);
            float f2 = lineBounds;
            float f3 = this.mLineTopMargin;
            float f4 = this.mStrokeWidth;
            canvas.drawLine(primaryHorizontal, f2 + f3 + f4, primaryHorizontal2, f2 + f3 + f4, this.mPaint);
        }
        super.onDraw(canvas);
    }

    @Override // android.widget.TextView, android.view.View
    public void setPadding(int i2, int i3, int i4, int i5) {
        super.setPadding(i2, i3, i4, i5 + ((int) this.mLineTopMargin) + ((int) this.mStrokeWidth));
    }

    public void setUnderLineColor(int i2) {
        this.mColor = i2;
        this.mPaint.setColor(i2);
        invalidate();
    }

    public void setUnderlineWidth(float f2) {
        this.mStrokeWidth = f2;
        invalidate();
    }

    public UnderlineTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public UnderlineTextView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mLineTopMargin = 0.0f;
        init(context, attributeSet, i2);
    }
}
