package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.mobile.auth.BuildConfig;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class RoundProgressBar extends View {
    public static final int FILL = 1;
    public static final int STROKE = 0;
    private int max;
    private Paint paint;
    private int progress;
    private int roundColor;
    private int roundProgressColor;
    private float roundWidth;
    private int style;
    private int textColor;
    private boolean textIsDisplayable;
    private float textSize;

    public RoundProgressBar(Context context) {
        this(context, null);
    }

    public int getCricleColor() {
        return this.roundColor;
    }

    public int getCricleProgressColor() {
        return this.roundProgressColor;
    }

    public synchronized int getMax() {
        return this.max;
    }

    public synchronized int getProgress() {
        return this.progress;
    }

    public float getRoundWidth() {
        return this.roundWidth;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public float getTextSize() {
        return this.textSize;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() / 2;
        float f2 = width;
        int i2 = (int) (f2 - (this.roundWidth / 2.0f));
        this.paint.setColor(this.roundColor);
        this.paint.setStyle(Paint.Style.STROKE);
        this.paint.setStrokeWidth(this.roundWidth);
        this.paint.setAntiAlias(true);
        canvas.drawCircle(f2, f2, i2, this.paint);
        Log.e(BuildConfig.FLAVOR_type, width + "");
        this.paint.setStrokeWidth(0.0f);
        this.paint.setColor(this.textColor);
        this.paint.setTextSize(this.textSize);
        this.paint.setTypeface(Typeface.DEFAULT_BOLD);
        int i3 = (int) ((this.progress / this.max) * 100.0f);
        float fMeasureText = this.paint.measureText(i3 + "%");
        if (this.textIsDisplayable && this.style == 0) {
            canvas.drawText(i3 + "%", f2 - (fMeasureText / 2.0f), f2 + (this.textSize / 2.0f), this.paint);
        }
        this.paint.setStrokeWidth(this.roundWidth);
        this.paint.setColor(this.roundProgressColor);
        float f3 = width - i2;
        float f4 = width + i2;
        RectF rectF = new RectF(f3, f3, f4, f4);
        int i4 = this.style;
        if (i4 == 0) {
            this.paint.setStyle(Paint.Style.STROKE);
            canvas.drawArc(rectF, 270.0f, (this.progress * 360) / this.max, false, this.paint);
        } else {
            if (i4 != 1) {
                return;
            }
            this.paint.setStyle(Paint.Style.FILL_AND_STROKE);
            if (this.progress != 0) {
                canvas.drawArc(rectF, 270.0f, (r0 * 360) / this.max, true, this.paint);
            }
        }
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public synchronized void setMax(int max) {
        if (max < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        int i2 = this.max;
        if (progress > i2) {
            progress = i2;
        }
        if (progress <= i2) {
            this.progress = progress;
            postInvalidate();
        }
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint({"ResourceAsColor"})
    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.paint = new Paint();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
        this.roundColor = typedArrayObtainStyledAttributes.getColor(1, R.color.color_white_pressed);
        this.roundProgressColor = typedArrayObtainStyledAttributes.getColor(2, R.color.white);
        this.textColor = typedArrayObtainStyledAttributes.getColor(5, R.color.white);
        this.textSize = typedArrayObtainStyledAttributes.getDimension(7, 15.0f);
        this.roundWidth = typedArrayObtainStyledAttributes.getDimension(3, 7.0f);
        this.max = typedArrayObtainStyledAttributes.getInteger(0, 100);
        this.textIsDisplayable = typedArrayObtainStyledAttributes.getBoolean(6, false);
        this.style = typedArrayObtainStyledAttributes.getInt(4, 0);
        typedArrayObtainStyledAttributes.recycle();
    }
}
