package com.tencent.smtt.sdk.ui.dialog.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

/* loaded from: classes6.dex */
public class a extends View {

    /* renamed from: a, reason: collision with root package name */
    private int f21364a;

    /* renamed from: b, reason: collision with root package name */
    private int f21365b;

    /* renamed from: c, reason: collision with root package name */
    private Paint f21366c;

    /* renamed from: d, reason: collision with root package name */
    private Paint f21367d;

    /* renamed from: e, reason: collision with root package name */
    private Path f21368e;

    /* renamed from: f, reason: collision with root package name */
    private Path f21369f;

    /* renamed from: g, reason: collision with root package name */
    private RectF f21370g;

    /* renamed from: h, reason: collision with root package name */
    private float[] f21371h;

    /* renamed from: i, reason: collision with root package name */
    private float f21372i;

    /* renamed from: j, reason: collision with root package name */
    private float f21373j;

    public a(Context context, float f2, float f3, float f4) {
        super(context, null, 0);
        a(context, f2, f3, f4);
    }

    private int a(int i2) {
        int mode = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i2);
        if (mode == 1073741824) {
            return size;
        }
        if (mode == Integer.MIN_VALUE) {
            return Math.min(100, size);
        }
        return 100;
    }

    public static int a(Context context, float f2) {
        return (int) ((f2 * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    private void a(Context context, float f2, float f3, float f4) {
        this.f21372i = f3;
        this.f21373j = f4;
        int color = Color.parseColor("#989DB4");
        float fA = a(context, 6.0f);
        this.f21366c = new Paint();
        Paint paint = new Paint();
        this.f21367d = paint;
        paint.setColor(-1);
        this.f21367d.setStyle(Paint.Style.FILL);
        this.f21367d.setAntiAlias(true);
        this.f21366c.setColor(color);
        this.f21366c.setStyle(Paint.Style.STROKE);
        this.f21366c.setAntiAlias(true);
        this.f21366c.setStrokeWidth(fA);
        this.f21366c.setStrokeJoin(Paint.Join.ROUND);
        this.f21370g = new RectF();
        this.f21371h = new float[]{f2, f2, f2, f2, 0.0f, 0.0f, 0.0f, 0.0f};
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(0.0f, 0.0f);
        canvas.rotate(0.0f);
        if (this.f21369f == null) {
            this.f21369f = new Path();
        }
        this.f21369f.reset();
        this.f21369f.addRoundRect(this.f21370g, this.f21371h, Path.Direction.CCW);
        this.f21369f.close();
        canvas.drawPath(this.f21369f, this.f21367d);
        canvas.translate(this.f21364a / 2.0f, (this.f21365b / 2.0f) + (this.f21373j / 2.0f));
        if (this.f21368e == null) {
            this.f21368e = new Path();
        }
        this.f21368e.reset();
        this.f21368e.moveTo(0.0f, 0.0f);
        this.f21368e.lineTo((-this.f21372i) / 2.0f, (-this.f21373j) / 2.0f);
        this.f21368e.close();
        canvas.drawPath(this.f21368e, this.f21366c);
        this.f21368e.reset();
        this.f21368e.moveTo(0.0f, 0.0f);
        this.f21368e.lineTo(this.f21372i / 2.0f, (-this.f21373j) / 2.0f);
        this.f21368e.close();
        canvas.drawPath(this.f21368e, this.f21366c);
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        setMeasuredDimension(a(i2), a(i3));
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.f21364a = i2;
        this.f21365b = i3;
        RectF rectF = this.f21370g;
        rectF.left = 0.0f;
        rectF.top = 0.0f;
        rectF.right = i2;
        rectF.bottom = i3;
    }
}
