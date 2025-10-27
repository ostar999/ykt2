package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/* loaded from: classes6.dex */
public class CircleProgressView extends View {
    private int dotColor;
    private Paint paint;

    public CircleProgressView(Context context) {
        super(context);
        this.dotColor = -436157;
        init();
    }

    private void init() {
        Paint paint = new Paint(1);
        this.paint = paint;
        paint.setStyle(Paint.Style.FILL);
        this.paint.setColor(this.dotColor);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2.0f, getHeight() / 2.0f, Math.min(getWidth(), getHeight()) / 2.0f, this.paint);
    }

    public void setColor(int color) {
        this.dotColor = color;
        this.paint.setColor(color);
        invalidate();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.dotColor = -436157;
        init();
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.dotColor = -436157;
        init();
    }
}
