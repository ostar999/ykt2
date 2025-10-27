package com.beizi.fusion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import com.beizi.fusion.R;

/* loaded from: classes2.dex */
public class CircleProgressView extends View {

    /* renamed from: a, reason: collision with root package name */
    private Paint f5356a;

    /* renamed from: b, reason: collision with root package name */
    private Paint f5357b;

    /* renamed from: c, reason: collision with root package name */
    private Paint f5358c;

    /* renamed from: d, reason: collision with root package name */
    private Paint f5359d;

    /* renamed from: e, reason: collision with root package name */
    private int f5360e;

    /* renamed from: f, reason: collision with root package name */
    private int f5361f;

    /* renamed from: g, reason: collision with root package name */
    private int f5362g;

    /* renamed from: h, reason: collision with root package name */
    private int f5363h;

    /* renamed from: i, reason: collision with root package name */
    private float f5364i;

    /* renamed from: j, reason: collision with root package name */
    private float f5365j;

    /* renamed from: k, reason: collision with root package name */
    private float f5366k;

    /* renamed from: l, reason: collision with root package name */
    private int f5367l;

    /* renamed from: m, reason: collision with root package name */
    private int f5368m;

    /* renamed from: n, reason: collision with root package name */
    private float f5369n;

    /* renamed from: o, reason: collision with root package name */
    private float f5370o;

    /* renamed from: p, reason: collision with root package name */
    private int f5371p;

    /* renamed from: q, reason: collision with root package name */
    private int f5372q;

    /* renamed from: r, reason: collision with root package name */
    private float f5373r;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    private void a(Context context, AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.CircleProgressViewStyle, 0, 0);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float fApplyDimension = TypedValue.applyDimension(1, 18.0f, displayMetrics);
        float fApplyDimension2 = TypedValue.applyDimension(1, 4.0f, displayMetrics);
        this.f5373r = TypedValue.applyDimension(1, 50.0f, displayMetrics);
        this.f5364i = typedArrayObtainStyledAttributes.getDimension(R.styleable.CircleProgressViewStyle_adScopeRadius, fApplyDimension);
        this.f5366k = typedArrayObtainStyledAttributes.getDimension(R.styleable.CircleProgressViewStyle_adScopeStrokeWidth, fApplyDimension2);
        this.f5360e = typedArrayObtainStyledAttributes.getColor(R.styleable.CircleProgressViewStyle_adScopeCircleColor, 0);
        this.f5361f = typedArrayObtainStyledAttributes.getColor(R.styleable.CircleProgressViewStyle_adScopeRingColor, -41216);
        this.f5362g = typedArrayObtainStyledAttributes.getColor(R.styleable.CircleProgressViewStyle_adScopeTextColor, -1);
        this.f5363h = typedArrayObtainStyledAttributes.getColor(R.styleable.CircleProgressViewStyle_adScopeRingBgColor, 1589427388);
        this.f5365j = this.f5364i + (this.f5366k / 2.0f);
        typedArrayObtainStyledAttributes.recycle();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        this.f5367l = getWidth() / 2;
        int height = getHeight() / 2;
        this.f5368m = height;
        canvas.drawCircle(this.f5367l, height, this.f5364i, this.f5356a);
        RectF rectF = new RectF();
        int i2 = this.f5367l;
        float f2 = this.f5365j;
        rectF.left = i2 - f2;
        int i3 = this.f5368m;
        rectF.top = i3 - f2;
        rectF.right = (f2 * 2.0f) + (i2 - f2);
        rectF.bottom = (f2 * 2.0f) + (i3 - f2);
        canvas.drawArc(rectF, 0.0f, 360.0f, false, this.f5358c);
        if (this.f5372q > 0) {
            RectF rectF2 = new RectF();
            int i4 = this.f5367l;
            float f3 = this.f5365j;
            rectF2.left = i4 - f3;
            int i5 = this.f5368m;
            rectF2.top = i5 - f3;
            rectF2.right = (f3 * 2.0f) + (i4 - f3);
            rectF2.bottom = (f3 * 2.0f) + (i5 - f3);
            canvas.drawArc(rectF2, -90.0f, (this.f5372q / this.f5371p) * 360.0f, false, this.f5357b);
            float fMeasureText = this.f5359d.measureText("跳过", 0, 2);
            this.f5369n = fMeasureText;
            canvas.drawText("跳过", this.f5367l - (fMeasureText / 2.0f), this.f5368m + (this.f5370o / 4.0f), this.f5359d);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        int mode = View.MeasureSpec.getMode(i2);
        int mode2 = View.MeasureSpec.getMode(i3);
        int size = View.MeasureSpec.getSize(i2);
        int size2 = View.MeasureSpec.getSize(i3);
        if (mode != 1073741824) {
            size = (int) (getPaddingLeft() + this.f5373r + getPaddingRight());
        }
        if (mode2 != 1073741824) {
            size2 = (int) (getPaddingTop() + this.f5373r + getPaddingBottom());
        }
        setMeasuredDimension(size, size2);
    }

    public void setProgress(int i2) {
        this.f5372q = i2;
        postInvalidate();
    }

    public CircleProgressView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f5371p = 100;
        a(context, attributeSet);
        a();
    }

    private void a() {
        Paint paint = new Paint();
        this.f5356a = paint;
        paint.setAntiAlias(true);
        this.f5356a.setColor(this.f5360e);
        this.f5356a.setStyle(Paint.Style.FILL);
        Paint paint2 = new Paint();
        this.f5358c = paint2;
        paint2.setAntiAlias(true);
        this.f5358c.setColor(this.f5363h);
        this.f5358c.setStyle(Paint.Style.STROKE);
        this.f5358c.setStrokeWidth(this.f5366k);
        Paint paint3 = new Paint();
        this.f5357b = paint3;
        paint3.setAntiAlias(true);
        this.f5357b.setColor(this.f5361f);
        this.f5357b.setStyle(Paint.Style.STROKE);
        this.f5357b.setStrokeWidth(this.f5366k);
        this.f5357b.setStrokeCap(Paint.Cap.ROUND);
        Paint paint4 = new Paint();
        this.f5359d = paint4;
        paint4.setAntiAlias(true);
        this.f5359d.setStyle(Paint.Style.FILL);
        this.f5359d.setColor(this.f5362g);
        this.f5359d.setTextSize((this.f5364i * 3.0f) / 5.0f);
        this.f5359d.setShadowLayer(2.0f, 1.0f, 1.0f, Color.parseColor("#000000"));
        Paint.FontMetrics fontMetrics = this.f5359d.getFontMetrics();
        this.f5370o = (int) Math.ceil(fontMetrics.descent - fontMetrics.ascent);
    }
}
