package com.luck.picture.lib.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import com.luck.picture.lib.R;

/* loaded from: classes4.dex */
public class MediumBoldTextView extends AppCompatTextView {
    private float mStrokeWidth;

    public MediumBoldTextView(Context context) {
        super(context);
        this.mStrokeWidth = 0.6f;
        initView(context, null);
    }

    private void initView(Context context, @Nullable AttributeSet attributeSet) {
        if (attributeSet != null) {
            this.mStrokeWidth = context.obtainStyledAttributes(attributeSet, R.styleable.PsMediumBoldTextView).getFloat(R.styleable.PsMediumBoldTextView_stroke_Width, this.mStrokeWidth);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        TextPaint paint = getPaint();
        float strokeWidth = paint.getStrokeWidth();
        float f2 = this.mStrokeWidth;
        if (strokeWidth != f2) {
            paint.setStrokeWidth(f2);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
        }
        super.onDraw(canvas);
    }

    public void setStrokeWidth(float f2) {
        this.mStrokeWidth = f2;
        invalidate();
    }

    public MediumBoldTextView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStrokeWidth = 0.6f;
        initView(context, attributeSet);
    }
}
