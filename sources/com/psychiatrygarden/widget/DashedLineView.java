package com.psychiatrygarden.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DashedLineView extends View {
    private float gapW;
    private String lineColor;
    private int lineColorNew;
    private float lineStrokeW;
    private float lineW;
    private Paint paint;
    private Path path;

    public DashedLineView(Context context) {
        this(context, null);
    }

    private float dpToPx(float dp) {
        return dp * getContext().getResources().getDisplayMetrics().density;
    }

    private void init() {
        Paint paint = new Paint();
        this.paint = paint;
        paint.setColor(this.lineColorNew);
        this.paint.setStrokeWidth(dpToPx(this.lineStrokeW));
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setPathEffect(new DashPathEffect(new float[]{dpToPx(this.lineW), dpToPx(this.gapW)}, 0.0f));
        this.path = new Path();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.path.reset();
        this.path.moveTo(getWidth() / 2.0f, 0.0f);
        this.path.lineTo(getWidth() / 2.0f, getHeight());
        canvas.drawPath(this.path, this.paint);
    }

    public void setLineColor(String color) {
        if (color == null || !color.matches("^#([A-Fa-f0-9]{3,4}|[A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})$")) {
            return;
        }
        int color2 = Color.parseColor(color);
        this.lineColorNew = color2;
        this.paint.setColor(color2);
        invalidate();
    }

    public DashedLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DashedLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.lineColor = "#FFCAC3";
        this.lineW = 10.0f;
        this.gapW = 4.0f;
        this.lineStrokeW = 0.5f;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.YKBDashLine);
        this.lineColorNew = typedArrayObtainStyledAttributes.getColor(2, getContext().getColor(R.color.main_theme_four_deep_color));
        this.lineW = typedArrayObtainStyledAttributes.getDimension(3, 10.0f);
        this.gapW = typedArrayObtainStyledAttributes.getDimension(0, 4.0f);
        this.gapW = typedArrayObtainStyledAttributes.getDimension(0, 4.0f);
        this.lineStrokeW = typedArrayObtainStyledAttributes.getDimension(1, 0.5f);
        typedArrayObtainStyledAttributes.recycle();
        init();
    }
}
