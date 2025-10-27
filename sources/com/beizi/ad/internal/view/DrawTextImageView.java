package com.beizi.ad.internal.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatImageView;

/* loaded from: classes2.dex */
public class DrawTextImageView extends AppCompatImageView {

    /* renamed from: a, reason: collision with root package name */
    private String f4640a;

    /* renamed from: b, reason: collision with root package name */
    private float f4641b;

    /* renamed from: c, reason: collision with root package name */
    private float f4642c;

    /* renamed from: d, reason: collision with root package name */
    private float f4643d;

    /* renamed from: e, reason: collision with root package name */
    private int f4644e;

    /* renamed from: f, reason: collision with root package name */
    private int f4645f;

    public DrawTextImageView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.f4640a = "";
        this.f4641b = 30.0f;
        this.f4642c = -1000.0f;
        this.f4643d = -1000.0f;
        this.f4644e = 0;
        this.f4645f = 3;
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.f4640a.equals("")) {
            return;
        }
        Paint paint = new Paint();
        paint.setColor(getResources().getColor(this.f4644e));
        paint.setStrokeWidth(this.f4645f);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(this.f4641b);
        String str = this.f4640a;
        float width = this.f4642c;
        if (width == -1000.0f) {
            width = canvas.getWidth() - (this.f4641b * this.f4640a.length());
        }
        float height = this.f4643d;
        if (height == -1000.0f) {
            height = canvas.getHeight() - 50;
        }
        canvas.drawText(str, width, height, paint);
    }

    public void setDrawLocalXY(float f2, float f3) {
        this.f4642c = f2;
        this.f4643d = f3;
        drawableStateChanged();
    }

    public void setDrawText(String str) {
        this.f4640a = str;
        drawableStateChanged();
    }

    public void setDrawTextColorResourse(int i2) {
        this.f4644e = i2;
        drawableStateChanged();
    }

    public void setDrawTextSize(float f2) {
        this.f4641b = f2;
        drawableStateChanged();
    }

    public void setDrawTextStrokeWidth(int i2) {
        this.f4645f = i2;
        drawableStateChanged();
    }

    public DrawTextImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f4640a = "";
        this.f4641b = 30.0f;
        this.f4642c = -1000.0f;
        this.f4643d = -1000.0f;
        this.f4644e = 0;
        this.f4645f = 3;
    }

    public DrawTextImageView(Context context) {
        super(context);
        this.f4640a = "";
        this.f4641b = 30.0f;
        this.f4642c = -1000.0f;
        this.f4643d = -1000.0f;
        this.f4644e = 0;
        this.f4645f = 3;
    }
}
