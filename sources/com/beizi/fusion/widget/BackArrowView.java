package com.beizi.fusion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.beizi.fusion.R;

/* loaded from: classes2.dex */
public class BackArrowView extends View {

    /* renamed from: a, reason: collision with root package name */
    private int f5347a;

    /* renamed from: b, reason: collision with root package name */
    private int f5348b;

    /* renamed from: c, reason: collision with root package name */
    private float f5349c;

    /* renamed from: d, reason: collision with root package name */
    private float f5350d;

    /* renamed from: e, reason: collision with root package name */
    private int f5351e;

    /* renamed from: f, reason: collision with root package name */
    private float f5352f;

    /* renamed from: g, reason: collision with root package name */
    private int f5353g;

    /* renamed from: h, reason: collision with root package name */
    private Paint f5354h;

    /* renamed from: i, reason: collision with root package name */
    private Path f5355i;

    public BackArrowView(Context context) {
        this(context, null);
    }

    private void a(Context context, @Nullable AttributeSet attributeSet, int i2) {
        b(context, attributeSet, i2);
        Paint paint = new Paint();
        this.f5354h = paint;
        paint.setColor(this.f5351e);
        this.f5354h.setStyle(Paint.Style.STROKE);
        this.f5354h.setAntiAlias(true);
        this.f5354h.setStrokeWidth(this.f5352f);
        this.f5354h.setStrokeJoin(Paint.Join.ROUND);
    }

    private void b(Context context, @Nullable AttributeSet attributeSet, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BeiZi_BackArrowView, i2, 0);
        this.f5351e = typedArrayObtainStyledAttributes.getColor(R.styleable.BeiZi_BackArrowView_beizi_bav_color, Color.argb(255, 0, 0, 0));
        this.f5352f = typedArrayObtainStyledAttributes.getDimension(R.styleable.BeiZi_BackArrowView_beizi_bav_stroke_width, dip2px(context, 2.0f));
        this.f5353g = typedArrayObtainStyledAttributes.getInt(R.styleable.BeiZi_BackArrowView_beizi_bav_arrow_style, 1);
        typedArrayObtainStyledAttributes.recycle();
    }

    public static int dip2px(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate((this.f5347a / 2.0f) - this.f5349c, this.f5348b / 2);
        canvas.rotate(45.0f);
        if (this.f5355i == null) {
            this.f5355i = new Path();
        }
        this.f5355i.reset();
        this.f5355i.lineTo(0.0f, this.f5350d);
        this.f5355i.moveTo(0.0f, 0.0f);
        this.f5355i.lineTo(-this.f5350d, 0.0f);
        if (this.f5353g == 1) {
            this.f5355i.moveTo(0.0f, 0.0f);
            Path path = this.f5355i;
            float f2 = this.f5350d;
            path.lineTo(f2, -f2);
        }
        this.f5355i.close();
        canvas.drawPath(this.f5355i, this.f5354h);
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        setMeasuredDimension(a(i2), a(i3));
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.f5347a = i2;
        this.f5348b = i3;
        float fMin = Math.min(i2, i3) / 2.0f;
        int i6 = this.f5353g;
        if (i6 == 1) {
            this.f5349c = fMin / 3.0f;
        } else if (i6 == 2) {
            this.f5349c = fMin / 4.0f;
        }
        this.f5350d = fMin * 0.63f;
    }

    public void setViewColor(int i2) {
        Paint paint = this.f5354h;
        if (paint != null) {
            paint.setColor(i2);
            invalidate();
        }
    }

    public BackArrowView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BackArrowView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        a(context, attributeSet, i2);
    }

    private int a(int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            return size;
        }
        if (mode == Integer.MIN_VALUE) {
            return Math.min(150, size);
        }
        return 150;
    }
}
