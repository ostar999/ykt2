package com.psychiatrygarden.widget.glideUtil.progress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ProgressBar;
import com.psychiatrygarden.widget.glideUtil.util.Utils;
import com.yikaobang.yixue.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes6.dex */
public class CircleProgressView extends ProgressBar {
    private static final String INNER_BG_COLOR = "innerBgColor";
    private static final String INNER_PADDING = "innerPadding";
    private static final String IS_REACH_CAP_ROUND = "isReachCapRound";
    private static final String NORMAL_BAR_COLOR = "normalBarColor";
    private static final String NORMAL_BAR_SIZE = "normalBarSize";
    private static final String OUTER_COLOR = "outerColor";
    private static final String OUTER_SIZE = "outerSize";
    private static final String PROGRESS_STYLE = "progressStyle";
    private static final String RADIUS = "radius";
    private static final String REACH_BAR_COLOR = "reachBarColor";
    private static final String REACH_BAR_SIZE = "reachBarSize";
    private static final String START_ARC = "startArc";
    private static final String STATE = "state";
    private static final String TEXT_COLOR = "textColor";
    private static final String TEXT_PREFIX = "textPrefix";
    private static final String TEXT_SIZE = "textSize";
    private static final String TEXT_SKEW_X = "textSkewX";
    private static final String TEXT_SUFFIX = "textSuffix";
    private static final String TEXT_VISIBLE = "textVisible";
    private int mInnerBackgroundColor;
    private Paint mInnerBackgroundPaint;
    private int mInnerPadding;
    private int mNormalBarColor;
    private int mNormalBarSize;
    private Paint mNormalPaint;
    private Paint mOutPaint;
    private int mOuterColor;
    private int mOuterSize;
    private int mProgressStyle;
    private int mRadius;
    private int mReachBarColor;
    private int mReachBarSize;
    private boolean mReachCapRound;
    private Paint mReachPaint;
    private int mRealHeight;
    private int mRealWidth;
    private int mStartArc;
    private int mTextColor;
    private Paint mTextPaint;
    private String mTextPrefix;
    private int mTextSize;
    private float mTextSkewX;
    private String mTextSuffix;
    private boolean mTextVisible;
    private boolean needDrawInnerBackground;
    private RectF rectF;
    private RectF rectInner;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProgressStyle {
        public static final int FILL_IN = 1;
        public static final int FILL_IN_ARC = 2;
        public static final int NORMAL = 0;
    }

    public CircleProgressView(Context context) {
        this(context, null);
    }

    private void drawFillInArcCircle(Canvas canvas) {
        canvas.save();
        canvas.translate(this.mRealWidth / 2, this.mRealHeight / 2);
        canvas.drawArc(this.rectF, 0.0f, 360.0f, false, this.mOutPaint);
        float progress = ((getProgress() * 1.0f) / getMax()) * 360.0f;
        canvas.drawArc(this.rectInner, this.mStartArc, progress, true, this.mReachPaint);
        if (progress != 360.0f) {
            canvas.drawArc(this.rectInner, progress + this.mStartArc, 360.0f - progress, true, this.mNormalPaint);
        }
        canvas.restore();
    }

    private void drawFillInCircle(Canvas canvas) {
        canvas.save();
        canvas.translate(this.mRealWidth / 2, this.mRealHeight / 2);
        float progress = (getProgress() * 1.0f) / getMax();
        int i2 = this.mRadius;
        float fAcos = (float) ((Math.acos((i2 - (progress * (i2 * 2))) / i2) * 180.0d) / 3.141592653589793d);
        float f2 = fAcos * 2.0f;
        int i3 = this.mRadius;
        this.rectF = new RectF(-i3, -i3, i3, i3);
        this.mNormalPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(this.rectF, fAcos + 90.0f, 360.0f - f2, false, this.mNormalPaint);
        canvas.rotate(180.0f);
        this.mReachPaint.setStyle(Paint.Style.FILL);
        canvas.drawArc(this.rectF, 270.0f - fAcos, f2, false, this.mReachPaint);
        canvas.rotate(180.0f);
        if (this.mTextVisible) {
            String str = this.mTextPrefix + getProgress() + this.mTextSuffix;
            canvas.drawText(str, (-this.mTextPaint.measureText(str)) / 2.0f, (-(this.mTextPaint.descent() + this.mTextPaint.ascent())) / 2.0f, this.mTextPaint);
        }
    }

    private void drawNormalCircle(Canvas canvas) {
        canvas.save();
        canvas.translate(this.mRealWidth / 2, this.mRealHeight / 2);
        if (this.needDrawInnerBackground) {
            canvas.drawCircle(0.0f, 0.0f, this.mRadius - (Math.min(this.mReachBarSize, this.mNormalBarSize) / 2), this.mInnerBackgroundPaint);
        }
        if (this.mTextVisible) {
            String str = this.mTextPrefix + getProgress() + this.mTextSuffix;
            canvas.drawText(str, (-this.mTextPaint.measureText(str)) / 2.0f, (-(this.mTextPaint.descent() + this.mTextPaint.ascent())) / 2.0f, this.mTextPaint);
        }
        float progress = ((getProgress() * 1.0f) / getMax()) * 360.0f;
        if (progress != 360.0f) {
            canvas.drawArc(this.rectF, progress + this.mStartArc, 360.0f - progress, false, this.mNormalPaint);
        }
        canvas.drawArc(this.rectF, this.mStartArc, progress, false, this.mReachPaint);
        canvas.restore();
    }

    private void initPaint() {
        Paint paint = new Paint();
        this.mTextPaint = paint;
        paint.setColor(this.mTextColor);
        this.mTextPaint.setStyle(Paint.Style.FILL);
        this.mTextPaint.setTextSize(this.mTextSize);
        this.mTextPaint.setTextSkewX(this.mTextSkewX);
        this.mTextPaint.setAntiAlias(true);
        Paint paint2 = new Paint();
        this.mNormalPaint = paint2;
        paint2.setColor(this.mNormalBarColor);
        this.mNormalPaint.setStyle(this.mProgressStyle == 2 ? Paint.Style.FILL : Paint.Style.STROKE);
        this.mNormalPaint.setAntiAlias(true);
        this.mNormalPaint.setStrokeWidth(this.mNormalBarSize);
        Paint paint3 = new Paint();
        this.mReachPaint = paint3;
        paint3.setColor(this.mReachBarColor);
        this.mReachPaint.setStyle(this.mProgressStyle == 2 ? Paint.Style.FILL : Paint.Style.STROKE);
        this.mReachPaint.setAntiAlias(true);
        this.mReachPaint.setStrokeCap(this.mReachCapRound ? Paint.Cap.ROUND : Paint.Cap.BUTT);
        this.mReachPaint.setStrokeWidth(this.mReachBarSize);
        if (this.needDrawInnerBackground) {
            Paint paint4 = new Paint();
            this.mInnerBackgroundPaint = paint4;
            paint4.setStyle(Paint.Style.FILL);
            this.mInnerBackgroundPaint.setAntiAlias(true);
            this.mInnerBackgroundPaint.setColor(this.mInnerBackgroundColor);
        }
        if (this.mProgressStyle == 2) {
            Paint paint5 = new Paint();
            this.mOutPaint = paint5;
            paint5.setStyle(Paint.Style.STROKE);
            this.mOutPaint.setColor(this.mOuterColor);
            this.mOutPaint.setStrokeWidth(this.mOuterSize);
            this.mOutPaint.setAntiAlias(true);
        }
    }

    private void obtainAttributes(AttributeSet attrs) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        this.mProgressStyle = typedArrayObtainStyledAttributes.getInt(12, 0);
        this.mNormalBarSize = (int) typedArrayObtainStyledAttributes.getDimension(8, this.mNormalBarSize);
        this.mNormalBarColor = typedArrayObtainStyledAttributes.getColor(7, this.mNormalBarColor);
        this.mReachBarSize = (int) typedArrayObtainStyledAttributes.getDimension(10, this.mReachBarSize);
        this.mReachBarColor = typedArrayObtainStyledAttributes.getColor(9, this.mReachBarColor);
        this.mTextSize = (int) typedArrayObtainStyledAttributes.getDimension(16, this.mTextSize);
        this.mTextColor = typedArrayObtainStyledAttributes.getColor(13, this.mTextColor);
        this.mTextSkewX = typedArrayObtainStyledAttributes.getDimension(17, 0.0f);
        if (typedArrayObtainStyledAttributes.hasValue(18)) {
            this.mTextSuffix = typedArrayObtainStyledAttributes.getString(18);
        }
        if (typedArrayObtainStyledAttributes.hasValue(15)) {
            this.mTextPrefix = typedArrayObtainStyledAttributes.getString(15);
        }
        this.mTextVisible = typedArrayObtainStyledAttributes.getBoolean(19, this.mTextVisible);
        this.mRadius = (int) typedArrayObtainStyledAttributes.getDimension(20, this.mRadius);
        int i2 = this.mRadius;
        this.rectF = new RectF(-i2, -i2, i2, i2);
        int i3 = this.mProgressStyle;
        if (i3 == 0) {
            this.mReachCapRound = typedArrayObtainStyledAttributes.getBoolean(21, true);
            this.mStartArc = typedArrayObtainStyledAttributes.getInt(11, 0) + 270;
            if (typedArrayObtainStyledAttributes.hasValue(2)) {
                this.mInnerBackgroundColor = typedArrayObtainStyledAttributes.getColor(2, Color.argb(0, 0, 0, 0));
                this.needDrawInnerBackground = true;
            }
        } else if (i3 == 1) {
            this.mReachBarSize = 0;
            this.mNormalBarSize = 0;
            this.mOuterSize = 0;
        } else if (i3 == 2) {
            this.mStartArc = typedArrayObtainStyledAttributes.getInt(11, 0) + 270;
            this.mInnerPadding = (int) typedArrayObtainStyledAttributes.getDimension(3, this.mInnerPadding);
            this.mOuterColor = typedArrayObtainStyledAttributes.getColor(5, this.mReachBarColor);
            this.mOuterSize = (int) typedArrayObtainStyledAttributes.getDimension(6, this.mOuterSize);
            this.mReachBarSize = 0;
            this.mNormalBarSize = 0;
            if (!typedArrayObtainStyledAttributes.hasValue(7)) {
                this.mNormalBarColor = 0;
            }
            int i4 = (this.mRadius - (this.mOuterSize / 2)) - this.mInnerPadding;
            float f2 = -i4;
            float f3 = i4;
            this.rectInner = new RectF(f2, f2, f3, f3);
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public int getInnerBackgroundColor() {
        return this.mInnerBackgroundColor;
    }

    public int getInnerPadding() {
        return this.mInnerPadding;
    }

    public int getNormalBarColor() {
        return this.mNormalBarColor;
    }

    public int getNormalBarSize() {
        return this.mNormalBarSize;
    }

    public int getOuterColor() {
        return this.mOuterColor;
    }

    public int getOuterSize() {
        return this.mOuterSize;
    }

    public int getProgressStyle() {
        return this.mProgressStyle;
    }

    public int getRadius() {
        return this.mRadius;
    }

    public int getReachBarColor() {
        return this.mReachBarColor;
    }

    public int getReachBarSize() {
        return this.mReachBarSize;
    }

    public int getStartArc() {
        return this.mStartArc;
    }

    public int getTextColor() {
        return this.mTextColor;
    }

    public String getTextPrefix() {
        return this.mTextPrefix;
    }

    public int getTextSize() {
        return this.mTextSize;
    }

    public float getTextSkewX() {
        return this.mTextSkewX;
    }

    public String getTextSuffix() {
        return this.mTextSuffix;
    }

    @Override // android.view.View
    public void invalidate() {
        initPaint();
        super.invalidate();
    }

    public boolean isReachCapRound() {
        return this.mReachCapRound;
    }

    public boolean isTextVisible() {
        return this.mTextVisible;
    }

    @Override // android.widget.ProgressBar, android.view.View
    public synchronized void onDraw(Canvas canvas) {
        int i2 = this.mProgressStyle;
        if (i2 == 0) {
            drawNormalCircle(canvas);
        } else if (i2 == 1) {
            drawFillInCircle(canvas);
        } else if (i2 == 2) {
            drawFillInArcCircle(canvas);
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int paddingTop;
        int paddingLeft;
        int paddingTop2;
        int paddingLeft2;
        int iMax = Math.max(this.mReachBarSize, this.mNormalBarSize);
        int iMax2 = Math.max(iMax, this.mOuterSize);
        int i2 = this.mProgressStyle;
        if (i2 != 0) {
            if (i2 == 1) {
                paddingTop2 = getPaddingTop() + getPaddingBottom() + Math.abs(this.mRadius * 2);
                paddingLeft2 = getPaddingLeft() + getPaddingRight() + Math.abs(this.mRadius * 2);
            } else if (i2 != 2) {
                paddingLeft = 0;
                paddingTop = 0;
            } else {
                paddingTop2 = getPaddingTop() + getPaddingBottom() + Math.abs(this.mRadius * 2) + iMax2;
                paddingLeft2 = iMax2 + getPaddingLeft() + getPaddingRight() + Math.abs(this.mRadius * 2);
            }
            int i3 = paddingLeft2;
            paddingTop = paddingTop2;
            paddingLeft = i3;
        } else {
            paddingTop = getPaddingTop() + getPaddingBottom() + Math.abs(this.mRadius * 2) + iMax;
            paddingLeft = iMax + getPaddingLeft() + getPaddingRight() + Math.abs(this.mRadius * 2);
        }
        this.mRealWidth = View.resolveSize(paddingLeft, widthMeasureSpec);
        int iResolveSize = View.resolveSize(paddingTop, heightMeasureSpec);
        this.mRealHeight = iResolveSize;
        setMeasuredDimension(this.mRealWidth, iResolveSize);
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof Bundle)) {
            super.onRestoreInstanceState(state);
            return;
        }
        Bundle bundle = (Bundle) state;
        this.mProgressStyle = bundle.getInt(PROGRESS_STYLE);
        this.mRadius = bundle.getInt(RADIUS);
        this.mReachCapRound = bundle.getBoolean(IS_REACH_CAP_ROUND);
        this.mStartArc = bundle.getInt(START_ARC);
        this.mInnerBackgroundColor = bundle.getInt(INNER_BG_COLOR);
        this.mInnerPadding = bundle.getInt(INNER_PADDING);
        this.mOuterColor = bundle.getInt(OUTER_COLOR);
        this.mOuterSize = bundle.getInt(OUTER_SIZE);
        this.mTextColor = bundle.getInt(TEXT_COLOR);
        this.mTextSize = bundle.getInt(TEXT_SIZE);
        this.mTextSkewX = bundle.getFloat(TEXT_SKEW_X);
        this.mTextVisible = bundle.getBoolean(TEXT_VISIBLE);
        this.mTextSuffix = bundle.getString(TEXT_SUFFIX);
        this.mTextPrefix = bundle.getString(TEXT_PREFIX);
        this.mReachBarColor = bundle.getInt(REACH_BAR_COLOR);
        this.mReachBarSize = bundle.getInt(REACH_BAR_SIZE);
        this.mNormalBarColor = bundle.getInt(NORMAL_BAR_COLOR);
        this.mNormalBarSize = bundle.getInt(NORMAL_BAR_SIZE);
        initPaint();
        super.onRestoreInstanceState(bundle.getParcelable(STATE));
    }

    @Override // android.widget.ProgressBar, android.view.View
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(STATE, super.onSaveInstanceState());
        bundle.putInt(PROGRESS_STYLE, getProgressStyle());
        bundle.putInt(RADIUS, getRadius());
        bundle.putBoolean(IS_REACH_CAP_ROUND, isReachCapRound());
        bundle.putInt(START_ARC, getStartArc());
        bundle.putInt(INNER_BG_COLOR, getInnerBackgroundColor());
        bundle.putInt(INNER_PADDING, getInnerPadding());
        bundle.putInt(OUTER_COLOR, getOuterColor());
        bundle.putInt(OUTER_SIZE, getOuterSize());
        bundle.putInt(TEXT_COLOR, getTextColor());
        bundle.putInt(TEXT_SIZE, getTextSize());
        bundle.putFloat(TEXT_SKEW_X, getTextSkewX());
        bundle.putBoolean(TEXT_VISIBLE, isTextVisible());
        bundle.putString(TEXT_SUFFIX, getTextSuffix());
        bundle.putString(TEXT_PREFIX, getTextPrefix());
        bundle.putInt(REACH_BAR_COLOR, getReachBarColor());
        bundle.putInt(REACH_BAR_SIZE, getReachBarSize());
        bundle.putInt(NORMAL_BAR_COLOR, getNormalBarColor());
        bundle.putInt(NORMAL_BAR_SIZE, getNormalBarSize());
        return bundle;
    }

    public void runProgressAnim(long duration) {
        setProgressInTime(0, duration);
    }

    public void setInnerBackgroundColor(int innerBackgroundColor) {
        this.mInnerBackgroundColor = innerBackgroundColor;
        invalidate();
    }

    public void setInnerPadding(int innerPadding) {
        int iDp2px = Utils.dp2px(getContext(), innerPadding);
        this.mInnerPadding = iDp2px;
        int i2 = (this.mRadius - (this.mOuterSize / 2)) - iDp2px;
        float f2 = -i2;
        float f3 = i2;
        this.rectInner = new RectF(f2, f2, f3, f3);
        invalidate();
    }

    public void setNormalBarColor(int normalBarColor) {
        this.mNormalBarColor = normalBarColor;
        invalidate();
    }

    public void setNormalBarSize(int normalBarSize) {
        this.mNormalBarSize = Utils.dp2px(getContext(), normalBarSize);
        invalidate();
    }

    public void setOuterColor(int outerColor) {
        this.mOuterColor = outerColor;
        invalidate();
    }

    public void setOuterSize(int outerSize) {
        this.mOuterSize = Utils.dp2px(getContext(), outerSize);
        invalidate();
    }

    public void setProgressInTime(final int progress, final long duration) {
        setProgressInTime(progress, getProgress(), duration);
    }

    public void setProgressStyle(int progressStyle) {
        this.mProgressStyle = progressStyle;
        invalidate();
    }

    public void setRadius(int radius) {
        this.mRadius = Utils.dp2px(getContext(), radius);
        invalidate();
    }

    public void setReachBarColor(int reachBarColor) {
        this.mReachBarColor = reachBarColor;
        invalidate();
    }

    public void setReachBarSize(int reachBarSize) {
        this.mReachBarSize = Utils.dp2px(getContext(), reachBarSize);
        invalidate();
    }

    public void setReachCapRound(boolean reachCapRound) {
        this.mReachCapRound = reachCapRound;
        invalidate();
    }

    public void setStartArc(int startArc) {
        this.mStartArc = startArc;
        invalidate();
    }

    public void setTextColor(int textColor) {
        this.mTextColor = textColor;
        invalidate();
    }

    public void setTextPrefix(String textPrefix) {
        this.mTextPrefix = textPrefix;
        invalidate();
    }

    public void setTextSize(int textSize) {
        this.mTextSize = Utils.sp2px(getContext(), textSize);
        invalidate();
    }

    public void setTextSkewX(float textSkewX) {
        this.mTextSkewX = textSkewX;
        invalidate();
    }

    public void setTextSuffix(String textSuffix) {
        this.mTextSuffix = textSuffix;
        invalidate();
    }

    public void setTextVisible(boolean textVisible) {
        this.mTextVisible = textVisible;
        invalidate();
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public void setProgressInTime(int startProgress, final int progress, final long duration) {
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(startProgress, progress);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.glideUtil.progress.CircleProgressView.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animator) {
                CircleProgressView.this.setProgress(((Integer) animator.getAnimatedValue()).intValue());
            }
        });
        valueAnimatorOfInt.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimatorOfInt.setDuration(duration);
        valueAnimatorOfInt.start();
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mReachBarSize = Utils.dp2px(getContext(), 2.0f);
        this.mNormalBarSize = Utils.dp2px(getContext(), 2.0f);
        this.mReachBarColor = Color.parseColor("#108ee9");
        this.mNormalBarColor = Color.parseColor("#FFD3D6DA");
        this.mTextSize = Utils.sp2px(getContext(), 14.0f);
        this.mTextColor = Color.parseColor("#108ee9");
        this.mTextSuffix = "%";
        this.mTextPrefix = "";
        this.mTextVisible = true;
        this.mRadius = Utils.dp2px(getContext(), 20.0f);
        this.mProgressStyle = 0;
        this.mInnerPadding = Utils.dp2px(getContext(), 1.0f);
        this.mOuterSize = Utils.dp2px(getContext(), 1.0f);
        obtainAttributes(attrs);
        initPaint();
    }
}
