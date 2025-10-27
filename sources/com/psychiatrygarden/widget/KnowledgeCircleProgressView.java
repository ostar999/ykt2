package com.psychiatrygarden.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import androidx.annotation.ColorRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.internal.view.SupportMenu;
import com.psychiatrygarden.utils.CommonUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class KnowledgeCircleProgressView extends View {
    private Paint mBackPaint;
    private int[] mColorArray;
    private Paint mEndCirclePaint;
    private int mMaxProgress;
    private Paint mProgPaint;
    private int mProgress;
    private RectF mRectF;

    public KnowledgeCircleProgressView(Context context) {
        this(context, null);
    }

    private void drawEndCircle(Canvas canvas) {
        float fWidth = this.mRectF.width() / 2.0f;
        float fCenterX = this.mRectF.centerX();
        float fCenterY = this.mRectF.centerY();
        double radians = Math.toRadians(((this.mProgress * 360.0f) / this.mMaxProgress) + 275.0f);
        double d3 = fWidth;
        canvas.drawCircle(fCenterX + ((float) (Math.cos(radians) * d3)), fCenterY + ((float) (d3 * Math.sin(radians))), CommonUtil.dip2px(getContext(), 3.0f), this.mEndCirclePaint);
    }

    public int getProgress() {
        return this.mProgress;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(this.mRectF, 0.0f, 360.0f, false, this.mBackPaint);
        canvas.drawArc(this.mRectF, 275.0f, (this.mProgress * 360) / this.mMaxProgress, false, this.mProgPaint);
        drawEndCircle(canvas);
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
        int measuredHeight = (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom();
        int strokeWidth = (int) ((measuredWidth > measuredHeight ? measuredHeight : measuredWidth) - (this.mBackPaint.getStrokeWidth() > this.mProgPaint.getStrokeWidth() ? this.mBackPaint : this.mProgPaint).getStrokeWidth());
        this.mRectF = new RectF(getPaddingLeft() + ((measuredWidth - strokeWidth) / 2), getPaddingTop() + ((measuredHeight - strokeWidth) / 2), r1 + strokeWidth, r9 + strokeWidth);
        int[] iArr = this.mColorArray;
        if (iArr == null || iArr.length <= 1) {
            return;
        }
        this.mProgPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, getMeasuredWidth(), this.mColorArray, (float[]) null, Shader.TileMode.MIRROR));
    }

    public void setBackWidth(int width) {
        this.mBackPaint.setStrokeWidth(width);
        invalidate();
    }

    public void setProgColor(String color) {
        this.mProgPaint.setColor(Color.parseColor(color));
        this.mProgPaint.setShader(null);
        this.mEndCirclePaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public void setProgWidth(int width) {
        this.mProgPaint.setStrokeWidth(width);
        invalidate();
    }

    public void setProgress(int progress, int maxProgress) {
        this.mProgress = progress;
        this.mMaxProgress = maxProgress;
        invalidate();
    }

    public KnowledgeCircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KnowledgeCircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mProgress = 0;
        this.mMaxProgress = 100;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        Paint paint = new Paint();
        this.mBackPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mBackPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mBackPaint.setAntiAlias(true);
        this.mBackPaint.setDither(true);
        float fDip2px = CommonUtil.dip2px(getContext(), 7.0f);
        this.mBackPaint.setStrokeWidth(typedArrayObtainStyledAttributes.getDimension(1, fDip2px));
        this.mBackPaint.setColor(Color.parseColor("#F2F4F6"));
        Paint paint2 = new Paint();
        this.mProgPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
        this.mProgPaint.setStrokeCap(Paint.Cap.ROUND);
        this.mProgPaint.setAntiAlias(true);
        this.mProgPaint.setDither(true);
        this.mProgPaint.setStrokeWidth(typedArrayObtainStyledAttributes.getDimension(5, fDip2px));
        this.mProgPaint.setColor(typedArrayObtainStyledAttributes.getColor(2, -16776961));
        Paint paint3 = new Paint();
        this.mEndCirclePaint = paint3;
        paint3.setStyle(Paint.Style.FILL);
        this.mEndCirclePaint.setAntiAlias(true);
        this.mEndCirclePaint.setColor(SupportMenu.CATEGORY_MASK);
        int color = typedArrayObtainStyledAttributes.getColor(4, -1);
        int color2 = typedArrayObtainStyledAttributes.getColor(3, -1);
        if (color != -1 && color2 != -1) {
            this.mColorArray = new int[]{color, color2};
        } else {
            this.mColorArray = null;
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public void setProgress(int progress, long animTime, int maxProgress) {
        if (animTime <= 0) {
            setProgress(progress, maxProgress);
            return;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.mProgress, progress);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.KnowledgeCircleProgressView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                KnowledgeCircleProgressView.this.mProgress = ((Integer) animation.getAnimatedValue()).intValue();
                KnowledgeCircleProgressView.this.invalidate();
            }
        });
        valueAnimatorOfInt.setInterpolator(new OvershootInterpolator());
        valueAnimatorOfInt.setDuration(animTime);
        valueAnimatorOfInt.start();
    }

    public void setProgColor(int startColor, int firstColor) {
        this.mColorArray = new int[]{startColor, firstColor};
        this.mProgPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, getMeasuredWidth(), this.mColorArray, (float[]) null, Shader.TileMode.MIRROR));
        this.mEndCirclePaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, getMeasuredWidth(), this.mColorArray, (float[]) null, Shader.TileMode.MIRROR));
        invalidate();
    }

    public void setProgColor(@ColorRes int[] colorArray) {
        if (colorArray == null || colorArray.length < 2) {
            return;
        }
        this.mColorArray = new int[colorArray.length];
        for (int i2 = 0; i2 < colorArray.length; i2++) {
            this.mColorArray[i2] = ContextCompat.getColor(getContext(), colorArray[i2]);
        }
        this.mProgPaint.setShader(new LinearGradient(0.0f, 0.0f, 0.0f, getMeasuredWidth(), this.mColorArray, (float[]) null, Shader.TileMode.MIRROR));
        invalidate();
    }
}
