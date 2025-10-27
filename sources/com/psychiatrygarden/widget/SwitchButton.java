package com.psychiatrygarden.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.annotation.Nullable;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class SwitchButton extends View implements View.OnClickListener {
    private static final int DEF_H = 60;
    private static final int DEF_W = 120;
    private int BALL_X_RIGHT;
    private int greenColor;
    private int greyColor;
    private Paint mBallPaint;
    private Paint mBgPaint;
    private RectF mBgStrokeRectF;
    private int mCurrentState;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private float mSolidRadius;
    private int mStrokeRadius;
    private float mSwitchBallx;
    private int mViewHeight;
    private int mViewWidth;
    private int switchViewBallColor;
    private int switchViewBgColor;
    private float switchViewStrockWidth;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(SwitchButton buttonView, boolean isChecked);
    }

    public SwitchButton(Context context) {
        this(context, null);
    }

    private void animate(int from, int to, int startColor, int endColor) {
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(from, to);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.SwitchButton.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                SwitchButton.this.mSwitchBallx = ((Float) animation.getAnimatedValue()).floatValue();
                SwitchButton.this.postInvalidate();
            }
        });
        ValueAnimator valueAnimatorOfObject = ValueAnimator.ofObject(new ArgbEvaluator(), Integer.valueOf(startColor), Integer.valueOf(endColor));
        valueAnimatorOfObject.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.SwitchButton.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                SwitchButton.this.switchViewBgColor = ((Integer) animation.getAnimatedValue()).intValue();
                SwitchButton.this.mBgPaint.setColor(SwitchButton.this.switchViewBgColor);
                SwitchButton.this.postInvalidate();
            }
        });
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(valueAnimatorOfFloat, valueAnimatorOfObject);
        animatorSet.setDuration(200L);
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.psychiatrygarden.widget.SwitchButton.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
                SwitchButton.this.setClickable(true);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
                SwitchButton.this.setClickable(false);
            }
        });
        animatorSet.start();
    }

    private Paint createPaint(int paintColor, int textSize, Paint.Style style, int lineWidth) {
        Paint paint = new Paint(1);
        paint.setColor(paintColor);
        paint.setStrokeWidth(lineWidth);
        paint.setDither(true);
        paint.setTextSize(textSize);
        paint.setStyle(style);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        return paint;
    }

    private void drawSwitchBall(Canvas canvas) {
        canvas.drawCircle(this.mSwitchBallx, this.mStrokeRadius, this.mSolidRadius, this.mBallPaint);
    }

    private void drawSwitchBg(Canvas canvas) {
        RectF rectF = this.mBgStrokeRectF;
        int i2 = this.mStrokeRadius;
        canvas.drawRoundRect(rectF, i2, i2, this.mBgPaint);
    }

    private void initData() {
        this.greyColor = this.switchViewBgColor;
        this.greenColor = Color.parseColor("#171D2D");
        this.mBallPaint = createPaint(this.switchViewBallColor, 0, Paint.Style.FILL, 0);
        this.mBgPaint = createPaint(this.switchViewBgColor, 0, Paint.Style.FILL, 0);
        this.mCurrentState = 1;
        setOnClickListener(this);
    }

    public int getmCurrentState() {
        return this.mCurrentState;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        if (this.mCurrentState == 0) {
            animate(this.BALL_X_RIGHT, this.mStrokeRadius, this.greenColor, this.greyColor);
        } else {
            animate(this.mStrokeRadius, this.BALL_X_RIGHT, this.greyColor, this.greenColor);
        }
        OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
        if (onCheckedChangeListener != null) {
            if (this.mCurrentState == 1) {
                onCheckedChangeListener.onCheckedChanged(this, true);
            } else {
                onCheckedChangeListener.onCheckedChanged(this, false);
            }
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        drawSwitchBg(canvas);
        drawSwitchBall(canvas);
    }

    @Override // android.view.View
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = View.MeasureSpec.getMode(widthMeasureSpec);
        int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
        if (mode == Integer.MIN_VALUE || mode == 0) {
            widthMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) TypedValue.applyDimension(1, 120.0f, getResources().getDisplayMetrics()), 1073741824);
        }
        if (mode2 == Integer.MIN_VALUE || mode2 == 0) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec((int) TypedValue.applyDimension(1, 60.0f, getResources().getDisplayMetrics()), 1073741824);
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override // android.view.View
    public void onSizeChanged(int w2, int h2, int oldw, int oldh) {
        this.mViewHeight = h2;
        this.mViewWidth = w2;
        float f2 = (w2 * 1.0f) / 30.0f;
        this.switchViewStrockWidth = f2;
        int i2 = h2 / 2;
        this.mStrokeRadius = i2;
        this.mSolidRadius = (h2 - (f2 * 2.0f)) / 2.0f;
        this.BALL_X_RIGHT = w2 - i2;
        this.mSwitchBallx = i2;
        this.mBgStrokeRectF = new RectF(0.0f, 0.0f, this.mViewWidth, this.mViewHeight);
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.mOnCheckedChangeListener = listener;
    }

    public void setmCurrentState(int mCurrentState) {
        this.mCurrentState = mCurrentState;
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwitchButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SwitchView, defStyleAttr, R.style.def_switch_view);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index != 0) {
                if (index == 1) {
                    this.switchViewBgColor = typedArrayObtainStyledAttributes.getColor(index, -16777216);
                }
            } else if (SkinManager.getCurrentSkinType(context) == 1) {
                this.switchViewBallColor = Color.parseColor("#64729F");
            } else {
                this.switchViewBallColor = typedArrayObtainStyledAttributes.getColor(index, -16777216);
            }
        }
        typedArrayObtainStyledAttributes.recycle();
        initData();
    }
}
