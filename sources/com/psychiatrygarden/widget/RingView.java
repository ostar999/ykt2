package com.psychiatrygarden.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.hyphenate.easeui.modules.EaseBaseLayout;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class RingView extends View {
    private static final int textSizeHint = 12;
    private static final int textSizeRate = 20;
    private int bgColor;
    private int[] color;
    private Matrix gradientMatrix;
    private SweepGradient mGradient;
    private Paint mPaint;
    private int progressColor;
    private final int progressMax;
    private int progressValue;
    private int textColorHint;
    private int textColorRate;
    private float viewRoundWidth;

    public RingView(Context context) {
        this(context, null);
    }

    private void initPaint() {
        this.mPaint = new Paint();
        this.mGradient = new SweepGradient(0.0f, 0.0f, this.color, (float[]) null);
        this.gradientMatrix = new Matrix();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setProgressValue$0(ValueAnimator valueAnimator) {
        this.progressValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
        postInvalidate();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth() / 2.0f;
        int i2 = (int) (width - (this.viewRoundWidth / 2.0f));
        this.mPaint.setColor(this.bgColor);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(this.viewRoundWidth);
        this.mPaint.setAntiAlias(true);
        canvas.drawCircle(width, width, i2, this.mPaint);
        canvas.save();
        this.mPaint.reset();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStrokeWidth(this.viewRoundWidth);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mPaint.setShader(this.mGradient);
        this.gradientMatrix.setTranslate(width, width);
        this.mGradient.setLocalMatrix(this.gradientMatrix);
        canvas.rotate(-90.0f, width, width);
        float f2 = this.viewRoundWidth;
        canvas.drawArc(f2 / 2.0f, f2 / 2.0f, getWidth() - (this.viewRoundWidth / 2.0f), getWidth() - (this.viewRoundWidth / 2.0f), 9.0f, (this.progressValue * 360) / 100.0f, false, this.mPaint);
        this.mPaint.setShader(null);
        canvas.restore();
        Paint paint = new Paint();
        paint.setColor(SupportMenu.CATEGORY_MASK);
        paint.setTextAlign(Paint.Align.CENTER);
        String str = this.progressValue + "%";
        paint.setTextSize(EaseBaseLayout.sp2px(getContext(), 20.0f));
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        float f3 = fontMetrics.descent - fontMetrics.ascent;
        paint.setTypeface(Typeface.DEFAULT);
        paint.setTextSize(EaseBaseLayout.sp2px(getContext(), 12.0f));
        Paint.FontMetrics fontMetrics2 = paint.getFontMetrics();
        float f4 = fontMetrics2.descent - fontMetrics2.ascent;
        float f5 = (f3 + f4) / 4.0f;
        float f6 = f3 - f4;
        float fAbs = (width - f5) + (Math.abs(f6) / 2.0f);
        float fAbs2 = f5 + width + (Math.abs(f6) / 2.0f);
        paint.setTextSize(EaseBaseLayout.sp2px(getContext(), 20.0f));
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setColor(this.textColorRate);
        canvas.drawText(str, width, fAbs, paint);
        paint.setTypeface(Typeface.DEFAULT);
        paint.setColor(this.textColorHint);
        paint.setTextSize(EaseBaseLayout.sp2px(getContext(), 12.0f));
        canvas.drawText("正确率", width, fAbs2, paint);
        paint.reset();
    }

    public void setBgColor(@ColorInt int color) {
        this.bgColor = color;
    }

    public void setProgressGradientColor(int[] gradientColor) {
        this.mGradient = new SweepGradient(0.0f, 0.0f, gradientColor, (float[]) null);
        invalidate();
    }

    public void setProgressValue(int targetProgress) {
        if (targetProgress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        this.progressValue = Math.min(targetProgress, 100);
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, targetProgress);
        valueAnimatorOfInt.setDuration(1000L);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.kg
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                this.f16654c.lambda$setProgressValue$0(valueAnimator);
            }
        });
        valueAnimatorOfInt.start();
    }

    public void setTextColorHint(@ColorInt int color) {
        this.textColorHint = color;
    }

    public void setTextColorRate(@ColorInt int color) {
        this.textColorRate = color;
    }

    public RingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RingView(Context context, @Nullable AttributeSet attrs, int deStyleAttr) {
        super(context, attrs, deStyleAttr);
        this.bgColor = -7829368;
        this.progressColor = SupportMenu.CATEGORY_MASK;
        this.textColorRate = -16777216;
        this.textColorHint = -7829368;
        this.viewRoundWidth = 20.0f;
        this.progressValue = 0;
        this.progressMax = 100;
        this.color = new int[]{InputDeviceCompat.SOURCE_ANY, SupportMenu.CATEGORY_MASK};
        initPaint();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.YKBRingView);
        this.bgColor = typedArrayObtainStyledAttributes.getColor(0, getContext().getColor(R.color.new_bg_two_color));
        this.progressColor = typedArrayObtainStyledAttributes.getColor(1, getContext().getColor(R.color.main_theme_color));
        this.textColorRate = typedArrayObtainStyledAttributes.getColor(3, getContext().getColor(R.color.main_theme_color));
        this.textColorHint = typedArrayObtainStyledAttributes.getColor(2, getContext().getColor(R.color.third_txt_color));
        this.viewRoundWidth = typedArrayObtainStyledAttributes.getDimension(4, 12.0f);
        typedArrayObtainStyledAttributes.recycle();
    }
}
