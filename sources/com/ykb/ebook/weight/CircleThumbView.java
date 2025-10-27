package com.ykb.ebook.weight;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes8.dex */
public class CircleThumbView extends View {
    private Paint circlePaint;
    private Paint textPaint;
    private String textTop;

    public CircleThumbView(Context context) {
        super(context);
        init();
    }

    private void init() {
        Paint paint = new Paint(1);
        this.circlePaint = paint;
        paint.setColor(-1);
        Paint paint2 = new Paint(1);
        this.textPaint = paint2;
        paint2.setColor(Color.parseColor("#303030"));
        this.textPaint.setTextSize(24.0f);
        this.textTop = "边距";
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth();
        int height = getHeight();
        int iMin = Math.min(width, height) / 2;
        canvas.drawCircle(width / 2, height / 2, iMin, this.circlePaint);
        Rect rect = new Rect();
        Paint paint = this.textPaint;
        String str = this.textTop;
        paint.getTextBounds(str, 0, str.length(), rect);
        canvas.drawText(this.textTop, r0 - (rect.width() / 2), r1 - (rect.height() / 2), this.textPaint);
    }

    public CircleThumbView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public CircleThumbView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        init();
    }
}
