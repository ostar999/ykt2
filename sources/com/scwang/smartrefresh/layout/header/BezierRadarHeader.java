package com.scwang.smartrefresh.layout.header;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import com.scwang.smartrefresh.layout.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;
import com.scwang.smartrefresh.layout.util.SmartUtil;

/* loaded from: classes6.dex */
public class BezierRadarHeader extends InternalAbstract implements RefreshHeader {
    protected static final byte PROPERTY_DOT_ALPHA = 2;
    protected static final byte PROPERTY_RADAR_ANGLE = 4;
    protected static final byte PROPERTY_RADAR_SCALE = 0;
    protected static final byte PROPERTY_RIPPLE_RADIUS = 3;
    protected static final byte PROPERTY_WAVE_HEIGHT = 1;
    protected int mAccentColor;
    protected Animator mAnimatorSet;
    protected float mDotAlpha;
    protected float mDotFraction;
    protected float mDotRadius;
    protected boolean mEnableHorizontalDrag;
    protected boolean mManualAccentColor;
    protected boolean mManualPrimaryColor;
    protected Paint mPaint;
    protected Path mPath;
    protected int mPrimaryColor;
    protected int mRadarAngle;
    protected float mRadarCircle;
    protected float mRadarRadius;
    protected RectF mRadarRect;
    protected float mRadarScale;
    protected float mRippleRadius;
    protected int mWaveHeight;
    protected int mWaveOffsetX;
    protected int mWaveOffsetY;
    protected boolean mWavePulling;
    protected int mWaveTop;

    /* renamed from: com.scwang.smartrefresh.layout.header.BezierRadarHeader$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState;

        static {
            int[] iArr = new int[RefreshState.values().length];
            $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState = iArr;
            try {
                iArr[RefreshState.None.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[RefreshState.PullDownToRefresh.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public class AnimatorUpdater implements ValueAnimator.AnimatorUpdateListener {
        byte propertyName;

        public AnimatorUpdater(byte b3) {
            this.propertyName = b3;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            byte b3 = this.propertyName;
            if (b3 == 0) {
                BezierRadarHeader.this.mRadarScale = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            } else if (1 == b3) {
                BezierRadarHeader bezierRadarHeader = BezierRadarHeader.this;
                if (bezierRadarHeader.mWavePulling) {
                    valueAnimator.cancel();
                    return;
                }
                bezierRadarHeader.mWaveHeight = ((Integer) valueAnimator.getAnimatedValue()).intValue() / 2;
            } else if (2 == b3) {
                BezierRadarHeader.this.mDotAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            } else if (3 == b3) {
                BezierRadarHeader.this.mRippleRadius = ((Float) valueAnimator.getAnimatedValue()).floatValue();
            } else if (4 == b3) {
                BezierRadarHeader.this.mRadarAngle = ((Integer) valueAnimator.getAnimatedValue()).intValue();
            }
            BezierRadarHeader.this.invalidate();
        }
    }

    public BezierRadarHeader(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        int width = getWidth();
        int height = isInEditMode() ? getHeight() : this.mWaveOffsetY;
        drawWave(canvas, width);
        drawDot(canvas, width, height);
        drawRadar(canvas, width, height);
        drawRipple(canvas, width, height);
        super.dispatchDraw(canvas);
    }

    public void drawDot(Canvas canvas, int i2, int i3) {
        if (this.mDotAlpha > 0.0f) {
            this.mPaint.setColor(this.mAccentColor);
            float fPx2dp = SmartUtil.px2dp(i3);
            float f2 = i2;
            float f3 = 7.0f;
            float f4 = (f2 * 1.0f) / 7.0f;
            float f5 = this.mDotFraction;
            float f6 = (f4 * f5) - (f5 > 1.0f ? ((f5 - 1.0f) * f4) / f5 : 0.0f);
            float f7 = i3;
            float f8 = f7 - (f5 > 1.0f ? (((f5 - 1.0f) * f7) / 2.0f) / f5 : 0.0f);
            int i4 = 0;
            while (i4 < 7) {
                this.mPaint.setAlpha((int) (this.mDotAlpha * (1.0f - ((Math.abs(r7) / f3) * 2.0f)) * 255.0f * (1.0d - (1.0d / Math.pow((fPx2dp / 800.0d) + 1.0d, 15.0d)))));
                float f9 = this.mDotRadius * (1.0f - (1.0f / ((fPx2dp / 10.0f) + 1.0f)));
                canvas.drawCircle(((f2 / 2.0f) - (f9 / 2.0f)) + (f6 * ((i4 + 1.0f) - 4.0f)), f8 / 2.0f, f9, this.mPaint);
                i4++;
                f3 = 7.0f;
            }
            this.mPaint.setAlpha(255);
        }
    }

    public void drawRadar(Canvas canvas, int i2, int i3) {
        if (this.mAnimatorSet != null || isInEditMode()) {
            float f2 = this.mRadarRadius;
            float f3 = this.mRadarScale;
            float f4 = f2 * f3;
            float f5 = this.mRadarCircle * f3;
            this.mPaint.setColor(this.mAccentColor);
            this.mPaint.setStyle(Paint.Style.FILL);
            float f6 = i2 / 2.0f;
            float f7 = i3 / 2.0f;
            canvas.drawCircle(f6, f7, f4, this.mPaint);
            this.mPaint.setStyle(Paint.Style.STROKE);
            float f8 = f5 + f4;
            canvas.drawCircle(f6, f7, f8, this.mPaint);
            this.mPaint.setColor((this.mPrimaryColor & 16777215) | 1426063360);
            this.mPaint.setStyle(Paint.Style.FILL);
            this.mRadarRect.set(f6 - f4, f7 - f4, f6 + f4, f4 + f7);
            canvas.drawArc(this.mRadarRect, 270.0f, this.mRadarAngle, true, this.mPaint);
            this.mPaint.setStyle(Paint.Style.STROKE);
            this.mRadarRect.set(f6 - f8, f7 - f8, f6 + f8, f7 + f8);
            canvas.drawArc(this.mRadarRect, 270.0f, this.mRadarAngle, false, this.mPaint);
            this.mPaint.setStyle(Paint.Style.FILL);
        }
    }

    public void drawRipple(Canvas canvas, int i2, int i3) {
        if (this.mRippleRadius > 0.0f) {
            this.mPaint.setColor(this.mAccentColor);
            canvas.drawCircle(i2 / 2.0f, i3 / 2.0f, this.mRippleRadius, this.mPaint);
        }
    }

    public void drawWave(Canvas canvas, int i2) {
        this.mPath.reset();
        this.mPath.lineTo(0.0f, this.mWaveTop);
        Path path = this.mPath;
        int i3 = this.mWaveOffsetX;
        float f2 = i3 >= 0 ? i3 : i2 / 2.0f;
        float f3 = i2;
        path.quadTo(f2, this.mWaveHeight + r3, f3, this.mWaveTop);
        this.mPath.lineTo(f3, 0.0f);
        this.mPaint.setColor(this.mPrimaryColor);
        canvas.drawPath(this.mPath, this.mPaint);
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public boolean isSupportHorizontalDrag() {
        return this.mEnableHorizontalDrag;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Animator animator = this.mAnimatorSet;
        if (animator != null) {
            animator.removeAllListeners();
            this.mAnimatorSet.end();
            this.mAnimatorSet = null;
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z2) {
        Animator animator = this.mAnimatorSet;
        if (animator != null) {
            animator.removeAllListeners();
            this.mAnimatorSet.end();
            this.mAnimatorSet = null;
        }
        int width = getWidth();
        int i2 = this.mWaveOffsetY;
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mRadarRadius, (float) Math.sqrt((width * width) + (i2 * i2)));
        valueAnimatorOfFloat.setDuration(400L);
        valueAnimatorOfFloat.addUpdateListener(new AnimatorUpdater((byte) 3));
        valueAnimatorOfFloat.start();
        return 400;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onHorizontalDrag(float f2, int i2, int i3) {
        this.mWaveOffsetX = i2;
        invalidate();
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onMoving(boolean z2, float f2, int i2, int i3, int i4) {
        this.mWaveOffsetY = i2;
        if (z2 || this.mWavePulling) {
            this.mWavePulling = true;
            this.mWaveTop = Math.min(i3, i2);
            this.mWaveHeight = (int) (Math.max(0, i2 - i3) * 1.9f);
            this.mDotFraction = f2;
            invalidate();
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onReleased(@NonNull RefreshLayout refreshLayout, int i2, int i3) {
        this.mWaveTop = i2 - 1;
        this.mWavePulling = false;
        SmartUtil smartUtil = new SmartUtil(SmartUtil.INTERPOLATOR_DECELERATE);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
        valueAnimatorOfFloat.setInterpolator(smartUtil);
        valueAnimatorOfFloat.addUpdateListener(new AnimatorUpdater((byte) 2));
        ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setInterpolator(smartUtil);
        valueAnimatorOfFloat2.addUpdateListener(new AnimatorUpdater((byte) 0));
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(0, 360);
        valueAnimatorOfInt.setDuration(720L);
        valueAnimatorOfInt.setRepeatCount(-1);
        valueAnimatorOfInt.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimatorOfInt.addUpdateListener(new AnimatorUpdater((byte) 4));
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(valueAnimatorOfFloat, valueAnimatorOfFloat2, valueAnimatorOfInt);
        animatorSet.start();
        int i4 = this.mWaveHeight;
        ValueAnimator valueAnimatorOfInt2 = ValueAnimator.ofInt(i4, 0, -((int) (i4 * 0.8f)), 0, -((int) (i4 * 0.4f)), 0);
        valueAnimatorOfInt2.addUpdateListener(new AnimatorUpdater((byte) 1));
        valueAnimatorOfInt2.setInterpolator(new SmartUtil(SmartUtil.INTERPOLATOR_DECELERATE));
        valueAnimatorOfInt2.setDuration(800L);
        valueAnimatorOfInt2.start();
        this.mAnimatorSet = animatorSet;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.listener.OnStateChangedListener
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState refreshState, @NonNull RefreshState refreshState2) {
        int i2 = AnonymousClass1.$SwitchMap$com$scwang$smartrefresh$layout$constant$RefreshState[refreshState2.ordinal()];
        if (i2 == 1 || i2 == 2) {
            this.mDotAlpha = 1.0f;
            this.mRadarScale = 0.0f;
            this.mRippleRadius = 0.0f;
        }
    }

    public BezierRadarHeader setAccentColor(@ColorInt int i2) {
        this.mAccentColor = i2;
        this.mManualAccentColor = true;
        return this;
    }

    public BezierRadarHeader setAccentColorId(@ColorRes int i2) {
        setAccentColor(ContextCompat.getColor(getContext(), i2));
        return this;
    }

    public BezierRadarHeader setEnableHorizontalDrag(boolean z2) {
        this.mEnableHorizontalDrag = z2;
        if (!z2) {
            this.mWaveOffsetX = -1;
        }
        return this;
    }

    public BezierRadarHeader setPrimaryColor(@ColorInt int i2) {
        this.mPrimaryColor = i2;
        this.mManualPrimaryColor = true;
        return this;
    }

    public BezierRadarHeader setPrimaryColorId(@ColorRes int i2) {
        setPrimaryColor(ContextCompat.getColor(getContext(), i2));
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    @Deprecated
    public void setPrimaryColors(@ColorInt int... iArr) {
        if (iArr.length > 0 && !this.mManualPrimaryColor) {
            setPrimaryColor(iArr[0]);
            this.mManualPrimaryColor = false;
        }
        if (iArr.length <= 1 || this.mManualAccentColor) {
            return;
        }
        setAccentColor(iArr[1]);
        this.mManualAccentColor = false;
    }

    public BezierRadarHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mEnableHorizontalDrag = false;
        this.mWaveOffsetX = -1;
        this.mWaveOffsetY = 0;
        this.mRadarAngle = 0;
        this.mRadarRadius = 0.0f;
        this.mRadarCircle = 0.0f;
        this.mRadarScale = 0.0f;
        this.mRadarRect = new RectF(0.0f, 0.0f, 0.0f, 0.0f);
        this.mSpinnerStyle = SpinnerStyle.FixedBehind;
        this.mPath = new Path();
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setAntiAlias(true);
        this.mDotRadius = SmartUtil.dp2px(7.0f);
        this.mRadarRadius = SmartUtil.dp2px(20.0f);
        this.mRadarCircle = SmartUtil.dp2px(7.0f);
        this.mPaint.setStrokeWidth(SmartUtil.dp2px(3.0f));
        setMinimumHeight(SmartUtil.dp2px(100.0f));
        if (isInEditMode()) {
            this.mWaveTop = 1000;
            this.mRadarScale = 1.0f;
            this.mRadarAngle = 270;
        } else {
            this.mRadarScale = 0.0f;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BezierRadarHeader);
        this.mEnableHorizontalDrag = typedArrayObtainStyledAttributes.getBoolean(R.styleable.BezierRadarHeader_srlEnableHorizontalDrag, this.mEnableHorizontalDrag);
        int i2 = R.styleable.BezierRadarHeader_srlAccentColor;
        setAccentColor(typedArrayObtainStyledAttributes.getColor(i2, -1));
        int i3 = R.styleable.BezierRadarHeader_srlPrimaryColor;
        setPrimaryColor(typedArrayObtainStyledAttributes.getColor(i3, -14540254));
        this.mManualAccentColor = typedArrayObtainStyledAttributes.hasValue(i2);
        this.mManualPrimaryColor = typedArrayObtainStyledAttributes.hasValue(i3);
        typedArrayObtainStyledAttributes.recycle();
    }
}
