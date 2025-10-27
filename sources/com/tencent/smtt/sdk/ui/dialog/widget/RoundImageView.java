package com.tencent.smtt.sdk.ui.dialog.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import java.lang.ref.WeakReference;

/* loaded from: classes6.dex */
public class RoundImageView extends ImageView {

    /* renamed from: a, reason: collision with root package name */
    private Paint f21355a;

    /* renamed from: b, reason: collision with root package name */
    private Xfermode f21356b;

    /* renamed from: c, reason: collision with root package name */
    private Bitmap f21357c;

    /* renamed from: d, reason: collision with root package name */
    private float[] f21358d;

    /* renamed from: e, reason: collision with root package name */
    private RectF f21359e;

    /* renamed from: f, reason: collision with root package name */
    private int f21360f;

    /* renamed from: g, reason: collision with root package name */
    private WeakReference<Bitmap> f21361g;

    /* renamed from: h, reason: collision with root package name */
    private float f21362h;

    /* renamed from: i, reason: collision with root package name */
    private Path f21363i;

    public RoundImageView(Context context) {
        this(context, null);
    }

    public RoundImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f21356b = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        this.f21360f = Color.parseColor("#eaeaea");
        Paint paint = new Paint();
        this.f21355a = paint;
        paint.setAntiAlias(true);
        this.f21363i = new Path();
        this.f21358d = new float[8];
        this.f21359e = new RectF();
        this.f21362h = com.tencent.smtt.sdk.ui.dialog.c.a(context, 16.46f);
        int i2 = 0;
        while (true) {
            float[] fArr = this.f21358d;
            if (i2 >= fArr.length) {
                return;
            }
            fArr[i2] = this.f21362h;
            i2++;
        }
    }

    private Bitmap a() {
        Bitmap bitmapCreateBitmap = null;
        try {
            bitmapCreateBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Paint paint = new Paint(1);
            paint.setColor(-16777216);
            RectF rectF = new RectF(0.0f, 0.0f, getWidth(), getHeight());
            float f2 = this.f21362h;
            canvas.drawRoundRect(rectF, f2, f2, paint);
            return bitmapCreateBitmap;
        } catch (Throwable th) {
            th.printStackTrace();
            return bitmapCreateBitmap;
        }
    }

    private void a(int i2, int i3) {
        this.f21363i.reset();
        this.f21355a.setStrokeWidth(i2);
        this.f21355a.setColor(i3);
        this.f21355a.setStyle(Paint.Style.STROKE);
    }

    private void a(Canvas canvas, int i2, int i3, RectF rectF, float[] fArr) {
        a(i2, i3);
        this.f21363i.addRoundRect(rectF, fArr, Path.Direction.CCW);
        canvas.drawPath(this.f21363i, this.f21355a);
    }

    @Override // android.view.View
    public void invalidate() {
        this.f21361g = null;
        Bitmap bitmap = this.f21357c;
        if (bitmap != null) {
            bitmap.recycle();
            this.f21357c = null;
        }
        super.invalidate();
    }

    @Override // android.widget.ImageView, android.view.View
    @SuppressLint({"DrawAllocation"})
    public void onDraw(Canvas canvas) {
        WeakReference<Bitmap> weakReference = this.f21361g;
        Bitmap bitmap = weakReference == null ? null : weakReference.get();
        if (bitmap == null || bitmap.isRecycled()) {
            Drawable drawable = getDrawable();
            if (drawable != null) {
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(bitmapCreateBitmap);
                float f2 = intrinsicWidth;
                float f3 = intrinsicHeight;
                float fMax = Math.max((getWidth() * 1.0f) / f2, (getHeight() * 1.0f) / f3);
                drawable.setBounds(0, 0, (int) (f2 * fMax), (int) (fMax * f3));
                drawable.draw(canvas2);
                Bitmap bitmap2 = this.f21357c;
                if (bitmap2 == null || bitmap2.isRecycled()) {
                    this.f21357c = a();
                }
                this.f21355a.reset();
                this.f21355a.setFilterBitmap(false);
                this.f21355a.setXfermode(this.f21356b);
                Bitmap bitmap3 = this.f21357c;
                if (bitmap3 != null) {
                    canvas2.drawBitmap(bitmap3, 0.0f, 0.0f, this.f21355a);
                }
                this.f21355a.setXfermode(null);
                canvas.drawBitmap(bitmapCreateBitmap, 0.0f, 0.0f, (Paint) null);
                this.f21361g = new WeakReference<>(bitmapCreateBitmap);
            }
        } else {
            this.f21355a.setXfermode(null);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, this.f21355a);
        }
        a(canvas, 1, this.f21360f, this.f21359e, this.f21358d);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
    }

    @Override // android.view.View
    public void onSizeChanged(int i2, int i3, int i4, int i5) {
        super.onSizeChanged(i2, i3, i4, i5);
        this.f21359e.set(0.5f, 0.5f, i2 - 0.5f, i3 - 0.5f);
    }
}
