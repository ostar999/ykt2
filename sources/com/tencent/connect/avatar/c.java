package com.tencent.connect.avatar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

/* loaded from: classes6.dex */
public class c extends ImageView {

    /* renamed from: a, reason: collision with root package name */
    final String f18067a;

    /* renamed from: b, reason: collision with root package name */
    public boolean f18068b;

    /* renamed from: c, reason: collision with root package name */
    private Matrix f18069c;

    /* renamed from: d, reason: collision with root package name */
    private Matrix f18070d;

    /* renamed from: e, reason: collision with root package name */
    private int f18071e;

    /* renamed from: f, reason: collision with root package name */
    private float f18072f;

    /* renamed from: g, reason: collision with root package name */
    private float f18073g;

    /* renamed from: h, reason: collision with root package name */
    private Bitmap f18074h;

    /* renamed from: i, reason: collision with root package name */
    private boolean f18075i;

    /* renamed from: j, reason: collision with root package name */
    private float f18076j;

    /* renamed from: k, reason: collision with root package name */
    private float f18077k;

    /* renamed from: l, reason: collision with root package name */
    private PointF f18078l;

    /* renamed from: m, reason: collision with root package name */
    private PointF f18079m;

    /* renamed from: n, reason: collision with root package name */
    private float f18080n;

    /* renamed from: o, reason: collision with root package name */
    private float f18081o;

    /* renamed from: p, reason: collision with root package name */
    private Rect f18082p;

    public c(Context context) {
        super(context);
        this.f18069c = new Matrix();
        this.f18070d = new Matrix();
        this.f18071e = 0;
        this.f18072f = 1.0f;
        this.f18073g = 1.0f;
        this.f18075i = false;
        this.f18067a = "TouchView";
        this.f18078l = new PointF();
        this.f18079m = new PointF();
        this.f18080n = 1.0f;
        this.f18081o = 0.0f;
        this.f18068b = false;
        Rect rect = new Rect();
        this.f18082p = rect;
        getDrawingRect(rect);
        a();
    }

    private void a() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void b() {
        boolean z2;
        Animation translateAnimation;
        if (this.f18074h == null) {
            return;
        }
        float fWidth = this.f18082p.width();
        float fHeight = this.f18082p.height();
        float[] fArr = new float[9];
        this.f18069c.getValues(fArr);
        float f2 = fArr[2];
        float f3 = fArr[5];
        float f4 = fArr[0];
        float f5 = this.f18072f;
        if (f4 > f5) {
            float f6 = f5 / f4;
            this.f18081o = f6;
            Matrix matrix = this.f18069c;
            PointF pointF = this.f18079m;
            matrix.postScale(f6, f6, pointF.x, pointF.y);
            setImageMatrix(this.f18069c);
            float f7 = this.f18081o;
            float f8 = 1.0f / f7;
            float f9 = 1.0f / f7;
            PointF pointF2 = this.f18079m;
            translateAnimation = new ScaleAnimation(f8, 1.0f, f9, 1.0f, pointF2.x, pointF2.y);
        } else {
            float f10 = this.f18073g;
            if (f4 < f10) {
                float f11 = f10 / f4;
                this.f18081o = f11;
                Matrix matrix2 = this.f18069c;
                PointF pointF3 = this.f18079m;
                matrix2.postScale(f11, f11, pointF3.x, pointF3.y);
                float f12 = this.f18081o;
                PointF pointF4 = this.f18079m;
                translateAnimation = new ScaleAnimation(1.0f, f12, 1.0f, f12, pointF4.x, pointF4.y);
            } else {
                float width = this.f18074h.getWidth() * f4;
                float height = this.f18074h.getHeight() * f4;
                Rect rect = this.f18082p;
                int i2 = rect.left;
                float f13 = i2 - f2;
                int i3 = rect.top;
                float f14 = i3 - f3;
                if (f13 < 0.0f) {
                    f2 = i2;
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (f14 < 0.0f) {
                    f3 = i3;
                    z2 = true;
                }
                float f15 = height - f14;
                if (width - f13 < fWidth) {
                    f2 = i2 - (width - fWidth);
                    z2 = true;
                }
                if (f15 < fHeight) {
                    f3 = i3 - (height - fHeight);
                    z2 = true;
                }
                if (z2) {
                    float f16 = fArr[2] - f2;
                    float f17 = fArr[5] - f3;
                    fArr[2] = f2;
                    fArr[5] = f3;
                    this.f18069c.setValues(fArr);
                    setImageMatrix(this.f18069c);
                    translateAnimation = new TranslateAnimation(f16, 0.0f, f17, 0.0f);
                } else {
                    setImageMatrix(this.f18069c);
                    translateAnimation = null;
                }
            }
        }
        if (translateAnimation != null) {
            this.f18075i = true;
            translateAnimation.setDuration(300L);
            startAnimation(translateAnimation);
            new Thread(new Runnable() { // from class: com.tencent.connect.avatar.c.1
                @Override // java.lang.Runnable
                public void run() throws InterruptedException {
                    try {
                        Thread.sleep(300L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                    c.this.post(new Runnable() { // from class: com.tencent.connect.avatar.c.1.1
                        @Override // java.lang.Runnable
                        public void run() {
                            c.this.clearAnimation();
                            c.this.b();
                        }
                    });
                    c.this.f18075i = false;
                }
            }).start();
        }
    }

    private void c() {
        if (this.f18074h == null) {
            return;
        }
        float[] fArr = {fMax, 0.0f, this.f18076j, 0.0f, fMax, height, 0.0f, 0.0f, 0.0f};
        this.f18069c.getValues(fArr);
        float fMax = Math.max(this.f18082p.width() / this.f18074h.getWidth(), this.f18082p.height() / this.f18074h.getHeight());
        this.f18076j = this.f18082p.left - (((this.f18074h.getWidth() * fMax) - this.f18082p.width()) / 2.0f);
        float height = this.f18082p.top - (((this.f18074h.getHeight() * fMax) - this.f18082p.height()) / 2.0f);
        this.f18077k = height;
        this.f18069c.setValues(fArr);
        float fMin = Math.min(2048.0f / this.f18074h.getWidth(), 2048.0f / this.f18074h.getHeight());
        this.f18072f = fMin;
        this.f18073g = fMax;
        if (fMin < fMax) {
            this.f18072f = fMax;
        }
        setImageMatrix(this.f18069c);
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0089  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            boolean r0 = r5.f18075i
            r1 = 1
            if (r0 == 0) goto L6
            return r1
        L6:
            int r0 = r6.getAction()
            r0 = r0 & 255(0xff, float:3.57E-43)
            if (r0 == 0) goto L90
            if (r0 == r1) goto L89
            r2 = 1092616192(0x41200000, float:10.0)
            r3 = 2
            if (r0 == r3) goto L37
            r4 = 5
            if (r0 == r4) goto L1d
            r6 = 6
            if (r0 == r6) goto L89
            goto Laf
        L1d:
            float r6 = r5.a(r6)
            r5.f18080n = r6
            int r6 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r6 <= 0) goto Laf
            android.graphics.Matrix r6 = r5.f18070d
            android.graphics.Matrix r0 = r5.f18069c
            r6.set(r0)
            android.graphics.PointF r6 = r5.f18079m
            r5.a(r6)
            r5.f18071e = r3
            goto Laf
        L37:
            int r0 = r5.f18071e
            if (r0 != r1) goto L5f
            android.graphics.Matrix r0 = r5.f18069c
            android.graphics.Matrix r2 = r5.f18070d
            r0.set(r2)
            float r0 = r6.getX()
            android.graphics.PointF r2 = r5.f18078l
            float r2 = r2.x
            float r0 = r0 - r2
            float r6 = r6.getY()
            android.graphics.PointF r2 = r5.f18078l
            float r2 = r2.y
            float r6 = r6 - r2
            android.graphics.Matrix r2 = r5.f18069c
            r2.postTranslate(r0, r6)
            android.graphics.Matrix r6 = r5.f18069c
            r5.setImageMatrix(r6)
            goto Laf
        L5f:
            if (r0 != r3) goto Laf
            android.graphics.Matrix r0 = r5.f18069c
            r0.set(r0)
            float r6 = r5.a(r6)
            int r0 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1))
            if (r0 <= 0) goto L83
            android.graphics.Matrix r0 = r5.f18069c
            android.graphics.Matrix r2 = r5.f18070d
            r0.set(r2)
            float r0 = r5.f18080n
            float r6 = r6 / r0
            android.graphics.Matrix r0 = r5.f18069c
            android.graphics.PointF r2 = r5.f18079m
            float r3 = r2.x
            float r2 = r2.y
            r0.postScale(r6, r6, r3, r2)
        L83:
            android.graphics.Matrix r6 = r5.f18069c
            r5.setImageMatrix(r6)
            goto Laf
        L89:
            r5.b()
            r6 = 0
            r5.f18071e = r6
            goto Laf
        L90:
            android.graphics.Matrix r0 = r5.f18069c
            android.graphics.Matrix r2 = r5.getImageMatrix()
            r0.set(r2)
            android.graphics.Matrix r0 = r5.f18070d
            android.graphics.Matrix r2 = r5.f18069c
            r0.set(r2)
            android.graphics.PointF r0 = r5.f18078l
            float r2 = r6.getX()
            float r6 = r6.getY()
            r0.set(r2, r6)
            r5.f18071e = r1
        Laf:
            r5.f18068b = r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.connect.avatar.c.onTouchEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.widget.ImageView
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        this.f18074h = bitmap;
        if (bitmap != null) {
            this.f18074h = bitmap;
        }
    }

    private float a(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() < 2) {
            return 0.0f;
        }
        float x2 = motionEvent.getX(0) - motionEvent.getX(1);
        float y2 = motionEvent.getY(0) - motionEvent.getY(1);
        return (float) Math.sqrt((x2 * x2) + (y2 * y2));
    }

    public void a(Rect rect) {
        this.f18082p = rect;
        if (this.f18074h != null) {
            c();
        }
    }

    private void a(PointF pointF) {
        if (this.f18074h == null) {
            return;
        }
        float[] fArr = new float[9];
        this.f18069c.getValues(fArr);
        float f2 = fArr[2];
        float f3 = fArr[5];
        float f4 = fArr[0];
        float width = this.f18074h.getWidth() * f4;
        float height = this.f18074h.getHeight() * f4;
        Rect rect = this.f18082p;
        float f5 = rect.left - f2;
        if (f5 <= 1.0f) {
            f5 = 1.0f;
        }
        float f6 = (f2 + width) - rect.right;
        if (f6 <= 1.0f) {
            f6 = 1.0f;
        }
        float fWidth = (rect.width() * f5) / (f6 + f5);
        Rect rect2 = this.f18082p;
        float f7 = fWidth + rect2.left;
        float f8 = rect2.top - f3;
        float f9 = (f3 + height) - rect2.bottom;
        if (f8 <= 1.0f) {
            f8 = 1.0f;
        }
        pointF.set(f7, ((rect2.height() * f8) / ((f9 > 1.0f ? f9 : 1.0f) + f8)) + this.f18082p.top);
    }
}
