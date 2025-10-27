package com.tencent.smtt.sdk.ui.dialog.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/* loaded from: classes6.dex */
public class c extends Drawable {

    /* renamed from: a, reason: collision with root package name */
    private float f21383a;

    /* renamed from: b, reason: collision with root package name */
    private float f21384b;

    /* renamed from: c, reason: collision with root package name */
    private float f21385c;

    /* renamed from: d, reason: collision with root package name */
    private float f21386d;

    /* renamed from: e, reason: collision with root package name */
    private Path f21387e;

    /* renamed from: f, reason: collision with root package name */
    private Paint f21388f;

    /* renamed from: g, reason: collision with root package name */
    private RectF f21389g;

    public c(int i2, float f2, float f3, float f4, float f5) {
        this.f21383a = f2;
        this.f21384b = f3;
        this.f21386d = f4;
        this.f21385c = f5;
        Paint paint = new Paint();
        this.f21388f = paint;
        paint.setStyle(Paint.Style.FILL);
        this.f21388f.setAntiAlias(true);
        this.f21388f.setColor(i2);
        this.f21389g = new RectF();
    }

    public void a(int i2, int i3) {
        RectF rectF = this.f21389g;
        rectF.left = 0.0f;
        rectF.top = 0.0f;
        rectF.right = i2;
        rectF.bottom = i3;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        if (this.f21387e == null) {
            this.f21387e = new Path();
        }
        this.f21387e.reset();
        Path path = this.f21387e;
        RectF rectF = this.f21389g;
        float f2 = this.f21383a;
        float f3 = this.f21384b;
        float f4 = this.f21386d;
        float f5 = this.f21385c;
        path.addRoundRect(rectF, new float[]{f2, f2, f3, f3, f4, f4, f5, f5}, Path.Direction.CCW);
        this.f21387e.close();
        canvas.drawPath(this.f21387e, this.f21388f);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i2) {
        this.f21388f.setAlpha(i2);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.f21388f.setColorFilter(colorFilter);
        invalidateSelf();
    }
}
