package com.tencent.connect.avatar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/* loaded from: classes6.dex */
public class b extends View {

    /* renamed from: a, reason: collision with root package name */
    private Rect f18065a;

    /* renamed from: b, reason: collision with root package name */
    private Paint f18066b;

    public b(Context context) {
        super(context);
        b();
    }

    private void b() {
        this.f18066b = new Paint();
    }

    public Rect a() {
        if (this.f18065a == null) {
            this.f18065a = new Rect();
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            int iMin = Math.min(Math.min((measuredHeight - 60) - 80, measuredWidth), 640);
            int i2 = (measuredWidth - iMin) / 2;
            int i3 = (measuredHeight - iMin) / 2;
            this.f18065a.set(i2, i3, i2 + iMin, iMin + i3);
        }
        return this.f18065a;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rectA = a();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        this.f18066b.setStyle(Paint.Style.FILL);
        this.f18066b.setColor(Color.argb(100, 0, 0, 0));
        float f2 = measuredWidth;
        canvas.drawRect(0.0f, 0.0f, f2, rectA.top, this.f18066b);
        canvas.drawRect(0.0f, rectA.bottom, f2, measuredHeight, this.f18066b);
        canvas.drawRect(0.0f, rectA.top, rectA.left, rectA.bottom, this.f18066b);
        canvas.drawRect(rectA.right, rectA.top, f2, rectA.bottom, this.f18066b);
        canvas.drawColor(Color.argb(100, 0, 0, 0));
        this.f18066b.setStyle(Paint.Style.STROKE);
        this.f18066b.setColor(-1);
        canvas.drawRect(rectA.left, rectA.top, rectA.right - 1, rectA.bottom, this.f18066b);
    }
}
