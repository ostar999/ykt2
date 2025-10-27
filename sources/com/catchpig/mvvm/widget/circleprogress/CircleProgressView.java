package com.catchpig.mvvm.widget.circleprogress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import androidx.core.content.ContextCompat;
import com.blankj.utilcode.util.ScreenUtils;
import com.catchpig.mvvm.R;
import com.luck.lib.camerax.utils.DensityUtil;

/* loaded from: classes2.dex */
public class CircleProgressView extends View {
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_CLIP = 1;
    private int mBackgroundColor;
    private Paint mBackgroundPaint;
    private int mDefaultWidth;
    private int mDuration;
    private int mEndAngle;
    private OnProgressChangedListener mListener;
    private float mProgress;
    private int mProgressColor;
    private Paint mProgressPaint;
    private int mProgressType;
    private int mProgressWidth;
    private RectF mRectf;
    private boolean mShowAnimation;
    private int mStartAngle;
    private float mTotalProgress;
    private ValueAnimator mValueAnimator;
    private int mViewWidth;

    public interface OnProgressChangedListener {
        void onProgressChanged(float f2);
    }

    public CircleProgressView(Context context) {
        this(context, null);
    }

    private void initPaint() {
        Paint paint = new Paint(1);
        this.mProgressPaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        Paint paint2 = new Paint(1);
        this.mBackgroundPaint = paint2;
        paint2.setStyle(Paint.Style.STROKE);
    }

    private int measureHeight(int i2) {
        int size = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            return this.mDefaultWidth * 2;
        }
        if (mode != 1073741824) {
            return ScreenUtils.getScreenHeight();
        }
        int i3 = this.mProgressWidth;
        if (size < i3) {
            size = i3;
        }
        return size;
    }

    private int measureWidth(int i2) {
        int size = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i2);
        if (mode == Integer.MIN_VALUE) {
            return this.mDefaultWidth * 2;
        }
        if (mode != 1073741824) {
            return ScreenUtils.getScreenWidth();
        }
        int i3 = this.mProgressWidth;
        if (size < i3) {
            size = i3;
        }
        return size;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int i2 = this.mProgressWidth;
        int i3 = this.mViewWidth;
        RectF rectF = new RectF(i2 / 2, i2 / 2, i3 - (i2 / 2), i3 - (i2 / 2));
        this.mRectf = rectF;
        int i4 = this.mProgressType;
        if (i4 == 0) {
            int i5 = this.mViewWidth;
            canvas.drawCircle(i5 / 2, i5 / 2, (i5 / 2) - (this.mProgressWidth / 2), this.mBackgroundPaint);
            canvas.drawArc(this.mRectf, this.mStartAngle, (this.mProgress * 360.0f) / 100.0f, false, this.mProgressPaint);
        } else if (i4 == 1) {
            canvas.drawArc(rectF, this.mStartAngle, this.mEndAngle - r0, false, this.mBackgroundPaint);
            canvas.drawArc(this.mRectf, this.mStartAngle, (this.mProgress * 360.0f) / 100.0f, false, this.mProgressPaint);
        }
    }

    @Override // android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
        int iMin = Math.min(measureWidth(i2), measureHeight(i3));
        this.mViewWidth = iMin;
        setMeasuredDimension(iMin, iMin);
    }

    public void setBackgroundCircleColor(int i2) {
        this.mBackgroundColor = i2;
        this.mBackgroundPaint.setColor(i2);
        invalidate();
    }

    public void setCap(Paint.Cap cap) {
        this.mProgressPaint.setStrokeCap(cap);
        this.mBackgroundPaint.setStrokeCap(cap);
    }

    public void setDuration(int i2) {
        this.mDuration = i2;
    }

    public void setEndAngle(int i2) {
        this.mEndAngle = i2;
    }

    public void setOnProgressChangedListener(OnProgressChangedListener onProgressChangedListener) {
        this.mListener = onProgressChangedListener;
    }

    public void setProgress(float f2, boolean z2) {
        this.mShowAnimation = z2;
        if (this.mProgressType == 1) {
            f2 = (int) (((this.mEndAngle - this.mStartAngle) * 100) / 360.0f);
            this.mTotalProgress = f2;
        } else {
            this.mTotalProgress = 100.0f;
        }
        ValueAnimator valueAnimator = this.mValueAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mValueAnimator.cancel();
        }
        if (!this.mShowAnimation) {
            this.mProgress = f2;
            invalidate();
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f2);
        this.mValueAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(this.mDuration);
        this.mValueAnimator.setInterpolator(new LinearInterpolator());
        this.mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.catchpig.mvvm.widget.circleprogress.CircleProgressView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                CircleProgressView.this.mProgress = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                if (CircleProgressView.this.mListener != null) {
                    CircleProgressView.this.mListener.onProgressChanged((CircleProgressView.this.mProgress * 100.0f) / CircleProgressView.this.mTotalProgress);
                }
                CircleProgressView.this.invalidate();
            }
        });
        this.mValueAnimator.start();
    }

    public void setProgressColor(int i2) {
        this.mProgressColor = i2;
        this.mProgressPaint.setColor(i2);
    }

    public void setProgressType(int i2) {
        this.mProgressType = i2;
    }

    public void setProgressWidth(int i2) {
        this.mProgressWidth = i2;
        float f2 = i2;
        this.mBackgroundPaint.setStrokeWidth(f2);
        this.mProgressPaint.setStrokeWidth(f2);
    }

    public void setStartAngle(int i2) {
        this.mStartAngle = i2;
    }

    public CircleProgressView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CircleProgressView(Context context, AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mDefaultWidth = DensityUtil.dip2px(getContext(), 10.0f);
        initPaint();
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.CircleProgressView, i2, 0);
        this.mProgressWidth = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.CircleProgressView_progressWidth, this.mDefaultWidth);
        int i3 = R.styleable.CircleProgressView_startAngle;
        this.mStartAngle = typedArrayObtainStyledAttributes.getInt(i3, 0);
        this.mEndAngle = typedArrayObtainStyledAttributes.getInt(i3, 360);
        this.mBackgroundColor = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.CircleProgressView_bgColor, ContextCompat.getColor(getContext(), R.color.c_1772fb));
        this.mShowAnimation = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CircleProgressView_animation, false);
        this.mDuration = typedArrayObtainStyledAttributes.getInt(R.styleable.CircleProgressView_duration, 1000);
        typedArrayObtainStyledAttributes.recycle();
        this.mProgressPaint.setStrokeWidth(this.mProgressWidth);
        this.mProgressPaint.setColor(this.mProgressColor);
        this.mBackgroundPaint.setStrokeWidth(this.mProgressWidth);
        this.mBackgroundPaint.setColor(this.mBackgroundColor);
    }
}
