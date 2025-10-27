package com.beizi.fusion.update;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.beizi.fusion.g.ac;

/* loaded from: classes2.dex */
public class ShakeArcView extends View {

    /* renamed from: a, reason: collision with root package name */
    private int f5318a;

    /* renamed from: b, reason: collision with root package name */
    private int f5319b;

    /* renamed from: c, reason: collision with root package name */
    private int f5320c;

    /* renamed from: d, reason: collision with root package name */
    private Paint f5321d;

    /* renamed from: e, reason: collision with root package name */
    private Paint f5322e;

    /* renamed from: f, reason: collision with root package name */
    private double f5323f;

    /* renamed from: g, reason: collision with root package name */
    private double f5324g;

    public ShakeArcView(Context context) {
        this(context, null);
    }

    private void a() {
        Paint paint = new Paint();
        this.f5321d = paint;
        paint.setAntiAlias(true);
        this.f5321d.setDither(true);
        this.f5321d.setStrokeWidth(this.f5320c);
        this.f5321d.setColor(this.f5318a);
        this.f5321d.setStyle(Paint.Style.STROKE);
        this.f5321d.setStrokeCap(Paint.Cap.ROUND);
    }

    private void b() {
        Paint paint = new Paint();
        this.f5322e = paint;
        paint.setAntiAlias(true);
        this.f5322e.setDither(true);
        this.f5322e.setStrokeWidth(this.f5320c);
        this.f5322e.setColor(this.f5319b);
        this.f5322e.setStyle(Paint.Style.STROKE);
        this.f5322e.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i2 = this.f5320c;
        RectF rectF = new RectF(i2 / 2, i2 / 2, getWidth() - (this.f5320c / 2), getHeight() - (this.f5320c / 2));
        canvas.drawArc(rectF, 225.0f, 90.0f, false, this.f5321d);
        double d3 = this.f5324g;
        if (d3 >= 0.0d) {
            double d4 = this.f5323f;
            if (d4 > 0.0d) {
                if (d3 >= d4) {
                    this.f5324g = d4;
                }
                float f2 = (float) (((float) this.f5324g) / d4);
                ac.c("sweepAngle", "sweepAngle:" + f2 + ",mMaxProgress:" + this.f5323f + ",mCurrentProgress:" + this.f5324g);
                canvas.drawArc(rectF, 270.0f, (-f2) * 45.0f, false, this.f5322e);
                canvas.drawArc(rectF, 270.0f, f2 * 45.0f, false, this.f5322e);
            }
        }
        int color = Color.parseColor("#CACCCA");
        if (this.f5324g == this.f5323f) {
            color = Color.parseColor("#FFFFFF");
        }
        int width = getWidth();
        int i3 = width / 4;
        int i4 = this.f5320c;
        int i5 = width / 8;
        int i6 = color;
        a(canvas, true, i3 - i4, i3 - i4, i5 - i4, i3 - i4, i5 - i4, i5 - i4, i6);
        int i7 = this.f5320c;
        int i8 = (width * 7) / 8;
        a(canvas, true, ((width * 3) / 4) + i7, i3 - i7, i8 + i7, i3 - i7, i8 + i7, i5 - i7, i6);
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        int size = View.MeasureSpec.getSize(i2) + (this.f5320c * 2);
        int size2 = View.MeasureSpec.getSize(i3) + (this.f5320c * 2);
        setMeasuredDimension(Math.min(size, size2), Math.min(size, size2));
    }

    public ShakeArcView setCurrentProgress(double d3) {
        this.f5324g = d3 * 100.0d;
        invalidate();
        return this;
    }

    public ShakeArcView setMaxProgress(double d3) {
        this.f5323f = d3 * 100.0d;
        return this;
    }

    public ShakeArcView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ShakeArcView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f5318a = Color.parseColor("#CACCCA");
        this.f5319b = Color.parseColor("#FFFFFF");
        this.f5320c = 6;
        this.f5323f = -1.0d;
        this.f5324g = -1.0d;
        a();
        b();
    }

    private void a(Canvas canvas, boolean z2, float f2, float f3, float f4, float f5, float f6, float f7, int i2) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(i2);
        if (z2) {
            paint.setStyle(Paint.Style.FILL);
        } else {
            paint.setStyle(Paint.Style.STROKE);
        }
        Path path = new Path();
        path.moveTo(f2, f3);
        path.lineTo(f4, f5);
        path.lineTo(f6, f7);
        path.lineTo(f2, f3);
        path.close();
        canvas.drawPath(path, paint);
    }
}
