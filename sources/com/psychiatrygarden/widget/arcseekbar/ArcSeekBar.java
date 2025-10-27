package com.psychiatrygarden.widget.arcseekbar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class ArcSeekBar extends View {
    private boolean isCanDrag;
    private boolean isEnabledDrag;
    private boolean isEnabledSingle;
    private boolean isMeasureCircle;
    private boolean isShader;
    private boolean isShowLabel;
    private boolean isShowPercentText;
    private boolean isShowThumb;
    private boolean isShowTick;
    private float mAllowableOffsets;
    private float mBlockAngle;
    private float mCircleCenterX;
    private float mCircleCenterY;
    private int mDuration;
    private float mLabelPaddingBottom;
    private float mLabelPaddingLeft;
    private float mLabelPaddingRight;
    private float mLabelPaddingTop;
    private String mLabelText;
    private int mLabelTextColor;
    private float mLabelTextSize;
    private int mMax;
    private int mNormalColor;
    private float mNormalStrokeWidth;
    private OnChangeListener mOnChangeListener;
    private Paint mPaint;
    private int mProgress;
    private int mProgressColor;
    private int mProgressPercent;
    private float mProgressStrokeWidth;
    private float mRadius;
    private Shader mShader;
    private int[] mShaderColors;
    private int mStartAngle;
    private Paint.Cap mStrokeCap;
    private int mSweepAngle;
    private TextPaint mTextPaint;
    private Bitmap mThumbBitmap;
    private float mThumbCenterX;
    private float mThumbCenterY;
    private int mThumbColor;
    private float mThumbRadius;
    private float mThumbRadiusEnlarges;
    private float mThumbStrokeWidth;
    private float mTickOffsetAngle;
    private float mTickPadding;
    private float mTickSplitAngle;
    private float mTickStrokeWidth;
    private int mTotalTickCount;

    public interface OnChangeListener {
        void onProgressChanged(float progress, float max, boolean fromUser);

        void onSingleTapUp();

        void onStartTrackingTouch(boolean isCanDrag);

        void onStopTrackingTouch(boolean isCanDrag);
    }

    public abstract class OnSimpleChangeListener implements OnChangeListener {
        public OnSimpleChangeListener() {
        }

        @Override // com.psychiatrygarden.widget.arcseekbar.ArcSeekBar.OnChangeListener
        public void onSingleTapUp() {
        }

        @Override // com.psychiatrygarden.widget.arcseekbar.ArcSeekBar.OnChangeListener
        public void onStartTrackingTouch(boolean isCanDrag) {
        }

        @Override // com.psychiatrygarden.widget.arcseekbar.ArcSeekBar.OnChangeListener
        public void onStopTrackingTouch(boolean isCanDrag) {
        }
    }

    public ArcSeekBar(Context context) {
        this(context, null);
    }

    private void checkCanDrag(float x2, float y2) {
        boolean z2 = getDistance(this.mThumbCenterX, this.mThumbCenterY, x2, y2) <= this.mThumbRadius + this.mAllowableOffsets;
        this.isCanDrag = z2;
        OnChangeListener onChangeListener = this.mOnChangeListener;
        if (onChangeListener != null) {
            onChangeListener.onStartTrackingTouch(z2);
        }
        invalidate();
    }

    private void drawArc(Canvas canvas) {
        Shader shader;
        Shader shader2;
        this.mPaint.reset();
        this.mPaint.setAntiAlias(true);
        this.mPaint.setStyle(Paint.Style.STROKE);
        if (this.isShowTick) {
            this.mPaint.setStrokeWidth(this.mTickStrokeWidth);
            float f2 = (this.mRadius - ((this.mThumbStrokeWidth / 2.0f) + this.mThumbRadius)) - this.mTickPadding;
            float f3 = f2 * 2.0f;
            float f4 = this.mCircleCenterX - f2;
            float f5 = this.mCircleCenterY - f2;
            RectF rectF = new RectF(f4, f5, f4 + f3, f3 + f5);
            int i2 = (int) ((this.mProgressPercent / 100.0f) * this.mTotalTickCount);
            for (int i3 = 0; i3 < this.mTotalTickCount; i3++) {
                if (i3 < i2) {
                    if (!this.isShader || (shader2 = this.mShader) == null) {
                        this.mPaint.setColor(this.mProgressColor);
                    } else {
                        this.mPaint.setShader(shader2);
                    }
                    float f6 = this.mBlockAngle;
                    canvas.drawArc(rectF, (i3 * (this.mTickSplitAngle + f6)) + this.mStartAngle + this.mTickOffsetAngle, f6, false, this.mPaint);
                } else if (this.mNormalColor != 0) {
                    this.mPaint.setShader(null);
                    this.mPaint.setColor(this.mNormalColor);
                    float f7 = this.mBlockAngle;
                    canvas.drawArc(rectF, (i3 * (this.mTickSplitAngle + f7)) + this.mStartAngle + this.mTickOffsetAngle, f7, false, this.mPaint);
                }
            }
        }
        this.mPaint.setStrokeCap(this.mStrokeCap);
        float f8 = this.mRadius;
        float f9 = 2.0f * f8;
        float f10 = this.mCircleCenterX - f8;
        float f11 = this.mCircleCenterY - f8;
        RectF rectF2 = new RectF(f10, f11, f10 + f9, f9 + f11);
        if (this.mNormalColor != 0) {
            this.mPaint.setStrokeWidth(this.mNormalStrokeWidth);
            this.mPaint.setShader(null);
            this.mPaint.setColor(this.mNormalColor);
            canvas.drawArc(rectF2, this.mStartAngle, this.mSweepAngle, false, this.mPaint);
        }
        float ratio = getRatio();
        if (ratio != 0.0f) {
            this.mPaint.setStrokeWidth(this.mProgressStrokeWidth);
            if (!this.isShader || (shader = this.mShader) == null) {
                this.mPaint.setColor(this.mProgressColor);
            } else {
                this.mPaint.setShader(shader);
            }
            canvas.drawArc(rectF2, this.mStartAngle, this.mSweepAngle * ratio, false, this.mPaint);
        }
    }

    private void drawThumb(Canvas canvas) {
        float fMax;
        this.mPaint.reset();
        this.mPaint.setAntiAlias(true);
        double ratio = this.mStartAngle + (this.mSweepAngle * getRatio());
        this.mThumbCenterX = (float) (this.mCircleCenterX + (this.mRadius * Math.cos(Math.toRadians(ratio))));
        this.mThumbCenterY = (float) (this.mCircleCenterY + (this.mRadius * Math.sin(Math.toRadians(ratio))));
        if (this.isShowThumb) {
            this.mPaint.setColor(-1);
            float fApplyDimension = TypedValue.applyDimension(1, this.mThumbRadius / 1.5f, getResources().getDisplayMetrics());
            this.mPaint.setStyle(Paint.Style.FILL);
            if (this.mThumbBitmap != null) {
                fMax = (this.isCanDrag ? Math.max(r1.getWidth(), this.mThumbBitmap.getHeight()) * (((this.mThumbRadiusEnlarges * 2.0f) / Math.min(this.mThumbBitmap.getWidth(), this.mThumbBitmap.getHeight())) + 1.0f) : Math.max(r1.getWidth(), this.mThumbBitmap.getHeight())) / 2.0f;
            } else {
                fMax = this.isCanDrag ? this.mThumbRadius + this.mThumbRadiusEnlarges : this.mThumbRadius;
            }
            canvas.drawCircle(this.mThumbCenterX, this.mThumbCenterY, fMax + fApplyDimension, this.mPaint);
            Bitmap bitmap = this.mThumbBitmap;
            if (bitmap == null) {
                this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
                this.mPaint.setStrokeWidth(this.mThumbStrokeWidth);
                this.mPaint.setColor(this.mThumbColor);
                canvas.drawCircle(this.mThumbCenterX, this.mThumbCenterY, this.isCanDrag ? this.mThumbRadius + this.mThumbRadiusEnlarges : this.mThumbRadius, this.mPaint);
                return;
            }
            if (!this.isCanDrag) {
                canvas.drawBitmap(this.mThumbBitmap, this.mThumbCenterX - (bitmap.getWidth() / 2.0f), this.mThumbCenterY - (this.mThumbBitmap.getHeight() / 2.0f), this.mPaint);
                return;
            }
            float fMin = Math.min(bitmap.getWidth(), this.mThumbBitmap.getHeight());
            float f2 = ((this.mThumbRadiusEnlarges * 2.0f) + fMin) / fMin;
            int iRound = Math.round(this.mThumbBitmap.getWidth() * f2);
            int iRound2 = Math.round(this.mThumbBitmap.getHeight() * f2);
            int iRound3 = (int) (this.mThumbCenterX - Math.round(iRound / 2.0f));
            int iRound4 = (int) (this.mThumbCenterY - Math.round(iRound2 / 2.0f));
            canvas.drawBitmap(this.mThumbBitmap, (Rect) null, new Rect(iRound3, iRound4, iRound + iRound3, iRound2 + iRound4), this.mPaint);
        }
    }

    private Bitmap getBitmapFormDrawable(Drawable drawable) {
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        drawable.setBounds(0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight());
        drawable.draw(canvas);
        return bitmapCreateBitmap;
    }

    private DisplayMetrics getDisplayMetrics() {
        return getResources().getDisplayMetrics();
    }

    private float getDistance(float x12, float y12, float x2, float y2) {
        return (float) Math.sqrt(Math.pow(x12 - x2, 2.0d) + Math.pow(y12 - y2, 2.0d));
    }

    private int getProgressForAngle(float angle) {
        return Math.round(((this.mMax * 1.0f) / this.mSweepAngle) * angle);
    }

    private float getRatio() {
        return (this.mProgress * 1.0f) / this.mMax;
    }

    private Paint.Cap getStrokeCap(int value) {
        return value != 1 ? value != 2 ? Paint.Cap.ROUND : Paint.Cap.SQUARE : Paint.Cap.BUTT;
    }

    private float getTouchDegrees(float x2, float y2) {
        float fAtan2 = ((float) ((Math.atan2(y2 - this.mCircleCenterY, x2 - this.mCircleCenterX) * 180.0d) / 3.141592653589793d)) - this.mStartAngle;
        while (fAtan2 < 0.0f) {
            fAtan2 += 360.0f;
        }
        return fAtan2;
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.ArcSeekBar);
        DisplayMetrics displayMetrics = getDisplayMetrics();
        float fApplyDimension = TypedValue.applyDimension(1, 12.0f, displayMetrics);
        this.mLabelTextSize = TypedValue.applyDimension(2, 30.0f, displayMetrics);
        this.mTickPadding = TypedValue.applyDimension(1, 2.0f, displayMetrics);
        this.mTickStrokeWidth = TypedValue.applyDimension(1, 10.0f, displayMetrics);
        float fApplyDimension2 = TypedValue.applyDimension(1, 8.0f, displayMetrics);
        this.mThumbRadius = fApplyDimension2;
        this.mThumbStrokeWidth = fApplyDimension2;
        this.mAllowableOffsets = TypedValue.applyDimension(1, 10.0f, displayMetrics);
        this.mThumbRadiusEnlarges = TypedValue.applyDimension(1, 2.0f, displayMetrics);
        int indexCount = typedArrayObtainStyledAttributes.getIndexCount();
        Drawable drawable = null;
        for (int i2 = 0; i2 < indexCount; i2++) {
            int index = typedArrayObtainStyledAttributes.getIndex(i2);
            if (index == 24) {
                fApplyDimension = typedArrayObtainStyledAttributes.getDimension(index, fApplyDimension);
            } else if (index == 15) {
                this.mNormalStrokeWidth = typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
            } else if (index == 18) {
                this.mProgressStrokeWidth = typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
            } else if (index == 23) {
                this.mStrokeCap = getStrokeCap(typedArrayObtainStyledAttributes.getInt(index, 3));
            } else if (index == 14) {
                this.mNormalColor = typedArrayObtainStyledAttributes.getColor(index, this.mNormalColor);
            } else if (index == 17) {
                this.mProgressColor = typedArrayObtainStyledAttributes.getColor(index, this.mProgressColor);
                this.isShader = false;
            } else if (index == 22) {
                this.mStartAngle = typedArrayObtainStyledAttributes.getInt(index, this.mStartAngle);
            } else if (index == 25) {
                this.mSweepAngle = typedArrayObtainStyledAttributes.getInt(index, this.mSweepAngle);
            } else if (index == 13) {
                int i3 = typedArrayObtainStyledAttributes.getInt(index, this.mMax);
                if (i3 > 0) {
                    this.mMax = i3;
                }
            } else if (index == 16) {
                this.mProgress = typedArrayObtainStyledAttributes.getInt(index, this.mProgress);
            } else if (index == 2) {
                this.mDuration = typedArrayObtainStyledAttributes.getInt(index, this.mDuration);
            } else if (index == 10) {
                this.mLabelText = typedArrayObtainStyledAttributes.getString(index);
            } else if (index == 12) {
                this.mLabelTextSize = typedArrayObtainStyledAttributes.getDimension(index, this.mLabelTextSize);
            } else if (index == 11) {
                this.mLabelTextColor = typedArrayObtainStyledAttributes.getColor(index, this.mLabelTextColor);
            } else if (index == 9) {
                this.mLabelPaddingTop = typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
            } else if (index == 6) {
                this.mLabelPaddingBottom = typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
            } else if (index == 7) {
                this.mLabelPaddingLeft = typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
            } else if (index == 8) {
                this.mLabelPaddingRight = typedArrayObtainStyledAttributes.getDimension(index, 0.0f);
            } else if (index == 19) {
                this.isShowLabel = typedArrayObtainStyledAttributes.getBoolean(index, true);
            } else if (index == 21) {
                this.isShowTick = typedArrayObtainStyledAttributes.getBoolean(index, true);
            } else if (index == 35) {
                this.mTickStrokeWidth = typedArrayObtainStyledAttributes.getDimension(index, this.mTickStrokeWidth);
            } else if (index == 33) {
                this.mTickPadding = typedArrayObtainStyledAttributes.getDimension(index, this.mTickPadding);
            } else if (index == 34) {
                this.mTickSplitAngle = typedArrayObtainStyledAttributes.getInt(index, 5);
            } else if (index == 1) {
                this.mBlockAngle = typedArrayObtainStyledAttributes.getInt(index, 1);
            } else if (index == 32) {
                this.mTickOffsetAngle = typedArrayObtainStyledAttributes.getInt(index, 0);
            } else if (index == 31) {
                this.mThumbStrokeWidth = typedArrayObtainStyledAttributes.getDimension(index, this.mThumbStrokeWidth);
            } else if (index == 27) {
                this.mThumbColor = typedArrayObtainStyledAttributes.getColor(index, this.mThumbColor);
            } else if (index == 29) {
                this.mThumbRadius = typedArrayObtainStyledAttributes.getDimension(index, this.mThumbRadius);
            } else if (index == 28) {
                drawable = typedArrayObtainStyledAttributes.getDrawable(index);
            } else if (index == 30) {
                this.mThumbRadiusEnlarges = typedArrayObtainStyledAttributes.getDimension(index, this.mThumbRadiusEnlarges);
            } else if (index == 20) {
                this.isShowThumb = typedArrayObtainStyledAttributes.getBoolean(index, this.isShowThumb);
            } else if (index == 0) {
                this.mAllowableOffsets = typedArrayObtainStyledAttributes.getDimension(index, this.mAllowableOffsets);
            } else if (index == 3) {
                this.isEnabledDrag = typedArrayObtainStyledAttributes.getBoolean(index, true);
            } else if (index == 4) {
                this.isEnabledSingle = typedArrayObtainStyledAttributes.getBoolean(index, true);
            }
        }
        if (this.mNormalStrokeWidth == 0.0f) {
            this.mNormalStrokeWidth = fApplyDimension;
        }
        if (this.mProgressStrokeWidth == 0.0f) {
            this.mProgressStrokeWidth = fApplyDimension;
        }
        this.isShowPercentText = TextUtils.isEmpty(this.mLabelText);
        if (drawable != null) {
            this.mThumbBitmap = getBitmapFormDrawable(drawable);
        }
        typedArrayObtainStyledAttributes.recycle();
        this.mProgressPercent = (int) ((this.mProgress * 100.0f) / this.mMax);
        this.mPaint = new Paint();
        this.mTextPaint = new TextPaint();
        this.mTotalTickCount = (int) (this.mSweepAngle / (this.mTickSplitAngle + this.mBlockAngle));
    }

    private boolean isInArc(float x2, float y2) {
        if (Math.abs(getDistance(this.mCircleCenterX, this.mCircleCenterY, x2, y2) - this.mRadius) > this.mThumbRadius + this.mAllowableOffsets) {
            return false;
        }
        if (this.mSweepAngle >= 360) {
            return true;
        }
        float touchDegrees = getTouchDegrees(x2, y2);
        int i2 = this.mStartAngle;
        float f2 = (touchDegrees + i2) % 360.0f;
        int i3 = this.mSweepAngle;
        return i2 + i3 <= 360 ? f2 >= ((float) i2) && f2 <= ((float) (i2 + i3)) : f2 >= ((float) i2) || f2 <= ((float) ((i2 + i3) % 360));
    }

    private int measureHandler(int measureSpec, int defaultSize) {
        int mode = View.MeasureSpec.getMode(measureSpec);
        int size = View.MeasureSpec.getSize(measureSpec);
        return mode == 1073741824 ? size : mode == Integer.MIN_VALUE ? Math.min(defaultSize, size) : defaultSize;
    }

    private void updateDragThumb(float x2, float y2, boolean isSingle) {
        int progressForAngle = getProgressForAngle(getTouchDegrees(x2, y2));
        if (!isSingle) {
            int i2 = this.mMax;
            int i3 = (int) ((progressForAngle * 100.0f) / i2);
            int i4 = this.mProgressPercent;
            if (i4 < 10 && i3 > 90) {
                progressForAngle = 0;
            } else if (i4 > 90 && i3 < 10) {
                progressForAngle = i2;
            }
            if (Math.abs(((int) ((progressForAngle * 100.0f) / i2)) - i4) > 50) {
                return;
            }
        }
        setProgress(progressForAngle, true);
    }

    public float getAllowableOffsets() {
        return this.mAllowableOffsets;
    }

    public float getCircleCenterX() {
        return this.mCircleCenterX;
    }

    public float getCircleCenterY() {
        return this.mCircleCenterY;
    }

    public String getLabelText() {
        return this.mLabelText;
    }

    public int getLabelTextColor() {
        return this.mLabelTextColor;
    }

    public int getMax() {
        return this.mMax;
    }

    public int getProgress() {
        return this.mProgress;
    }

    public int getProgressPercent() {
        return this.mProgressPercent;
    }

    public float getRadius() {
        return this.mRadius;
    }

    public int getStartAngle() {
        return this.mStartAngle;
    }

    public int getSweepAngle() {
        return this.mSweepAngle;
    }

    public String getText() {
        if (!this.isShowPercentText) {
            return this.mLabelText;
        }
        return this.mProgressPercent + "%";
    }

    public float getThumbCenterX() {
        return this.mThumbCenterX;
    }

    public float getThumbCenterY() {
        return this.mThumbCenterY;
    }

    public float getThumbRadius() {
        return this.mThumbRadius;
    }

    public float getThumbRadiusEnlarges() {
        return this.mThumbRadiusEnlarges;
    }

    public boolean isEnabledDrag() {
        return this.isEnabledDrag;
    }

    public boolean isEnabledSingle() {
        return this.isEnabledSingle;
    }

    public boolean isShowPercentText() {
        return this.isShowPercentText;
    }

    public boolean isShowThumb() {
        return this.isShowThumb;
    }

    public boolean isShowTick() {
        return this.isShowTick;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
        drawThumb(canvas);
    }

    @Override // android.view.View
    @SuppressLint({"DrawAllocation"})
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int iApplyDimension = (int) TypedValue.applyDimension(1, 200.0f, getDisplayMetrics());
        int iMeasureHandler = measureHandler(widthMeasureSpec, iApplyDimension);
        int iMeasureHandler2 = measureHandler(heightMeasureSpec, iApplyDimension);
        int iMin = Math.min(iMeasureHandler, iMeasureHandler2);
        this.mCircleCenterX = ((getPaddingLeft() + iMeasureHandler) - getPaddingRight()) / 2.0f;
        this.mCircleCenterY = ((getPaddingTop() + iMeasureHandler2) - getPaddingBottom()) / 2.0f;
        this.mRadius = (((iMin - Math.max(getPaddingLeft() + getPaddingRight(), getPaddingTop() + getPaddingBottom())) - this.mThumbStrokeWidth) / 2.0f) - this.mThumbRadius;
        float f2 = this.mCircleCenterX;
        this.mShader = new SweepGradient(f2, f2, this.mShaderColors, (float[]) null);
        this.isMeasureCircle = true;
        setMeasuredDimension(iMeasureHandler, iMeasureHandler2);
    }

    public void setAllowableOffsets(float allowableOffsets) {
        this.mAllowableOffsets = allowableOffsets;
    }

    public void setEnabledDrag(boolean enabledDrag) {
        this.isEnabledDrag = enabledDrag;
    }

    public void setEnabledSingle(boolean enabledSingle) {
        this.isEnabledSingle = enabledSingle;
    }

    public void setLabelPadding(float left, float top2, float right, float bottom) {
        this.mLabelPaddingLeft = left;
        this.mLabelPaddingTop = top2;
        this.mLabelPaddingRight = right;
        this.mLabelPaddingBottom = bottom;
        invalidate();
    }

    public void setLabelText(String labelText) {
        this.mLabelText = labelText;
        this.isShowPercentText = TextUtils.isEmpty(labelText);
        invalidate();
    }

    public void setLabelTextColor(int color) {
        this.mLabelTextColor = color;
        invalidate();
    }

    public void setLabelTextColorResource(int resId) {
        setLabelTextColor(getResources().getColor(resId));
    }

    public void setLabelTextSize(float textSize) {
        setLabelTextSize(2, textSize);
    }

    public void setMax(int max) {
        if (max > 0) {
            this.mMax = max;
            this.mProgressPercent = (int) ((this.mProgress * 100.0f) / max);
            invalidate();
        }
    }

    public void setNormalColor(int color) {
        this.mNormalColor = color;
        invalidate();
    }

    public void setOnChangeListener(OnChangeListener onChangeListener) {
        this.mOnChangeListener = onChangeListener;
    }

    public void setProgress(int progress) {
        setProgress(progress, false);
    }

    public void setProgressColor(int... colors) {
        if (this.isMeasureCircle) {
            float f2 = this.mCircleCenterX;
            setShader(new SweepGradient(f2, f2, colors, (float[]) null));
        } else {
            this.mShaderColors = colors;
            this.isShader = true;
        }
    }

    public void setProgressColorResource(int resId) {
        setProgressColor(getResources().getColor(resId));
    }

    public void setShader(Shader shader) {
        this.isShader = true;
        this.mShader = shader;
        invalidate();
    }

    public void setShowPercentText(boolean showPercentText) {
        this.isShowPercentText = showPercentText;
        invalidate();
    }

    public void setShowThumb(boolean showThumb) {
        this.isShowThumb = showThumb;
        invalidate();
    }

    public void setShowTick(boolean isShowTick) {
        this.isShowTick = isShowTick;
        invalidate();
    }

    public void setThumbBitmap(Bitmap bitmap) {
        this.mThumbBitmap = bitmap;
        invalidate();
    }

    public void setThumbDrawable(int resId) {
        setThumbBitmap(BitmapFactory.decodeResource(getResources(), resId));
    }

    public void setThumbRadiusEnlarges(float thumbRadiusEnlarges) {
        this.mThumbRadiusEnlarges = thumbRadiusEnlarges;
    }

    public void showAnimation(int progress) {
        showAnimation(progress, this.mDuration);
    }

    public void showAppendAnimation(int progress) {
        showAnimation(this.mProgress, progress, this.mDuration);
    }

    public ArcSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void setProgress(int progress, boolean fromUser) {
        if (this.mProgress == progress) {
            return;
        }
        if (progress < 0) {
            progress = 0;
        } else {
            int i2 = this.mMax;
            if (progress > i2) {
                progress = i2;
            }
        }
        this.mProgress = progress;
        this.mProgressPercent = (int) ((progress * 100.0f) / this.mMax);
        invalidate();
        OnChangeListener onChangeListener = this.mOnChangeListener;
        if (onChangeListener != null) {
            onChangeListener.onProgressChanged(this.mProgress, this.mMax, fromUser);
        }
    }

    public void setLabelTextSize(int unit, float textSize) {
        float fApplyDimension = TypedValue.applyDimension(unit, textSize, getDisplayMetrics());
        if (this.mLabelTextSize != fApplyDimension) {
            this.mLabelTextSize = fApplyDimension;
            invalidate();
        }
    }

    public void setThumbDrawable(Drawable drawable) {
        setThumbBitmap(getBitmapFormDrawable(drawable));
    }

    public void showAnimation(int progress, int duration) {
        showAnimation(0, progress, duration);
    }

    public ArcSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mStrokeCap = Paint.Cap.ROUND;
        this.mStartAngle = 270;
        this.mSweepAngle = 360;
        this.mNormalColor = Color.parseColor("#F8FAF9");
        this.mProgressColor = -11539796;
        this.isShader = true;
        this.mShaderColors = new int[]{Color.parseColor("#0EDEB4"), Color.parseColor("#FF7D5F"), Color.parseColor("#FF7D5F"), Color.parseColor("#FFFB72"), Color.parseColor("#1CE2BA")};
        this.mTickSplitAngle = 5.0f;
        this.mBlockAngle = 1.0f;
        this.mTickOffsetAngle = 0.0f;
        this.mMax = 100;
        this.mProgress = 100;
        this.mDuration = 500;
        this.mLabelTextColor = -13421773;
        this.isShowLabel = true;
        this.isShowPercentText = true;
        this.isShowTick = false;
        this.mThumbColor = -1518833;
        this.isShowThumb = true;
        this.isCanDrag = false;
        this.isEnabledDrag = true;
        this.isEnabledSingle = true;
        this.isMeasureCircle = false;
        init(context, attrs);
    }

    public void showAnimation(int from, int to, int duration) {
        showAnimation(from, to, duration, null);
    }

    public void showAnimation(int from, int to, int duration, Animator.AnimatorListener listener) {
        this.mDuration = duration;
        this.mProgress = from;
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(from, to);
        valueAnimatorOfInt.setDuration(duration);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.psychiatrygarden.widget.arcseekbar.ArcSeekBar.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                ArcSeekBar.this.setProgress(((Integer) animation.getAnimatedValue()).intValue());
            }
        });
        if (listener != null) {
            valueAnimatorOfInt.removeAllUpdateListeners();
            valueAnimatorOfInt.addListener(listener);
        }
        valueAnimatorOfInt.start();
    }

    public void setProgressColor(int color) {
        this.isShader = false;
        this.mProgressColor = color;
        invalidate();
    }
}
