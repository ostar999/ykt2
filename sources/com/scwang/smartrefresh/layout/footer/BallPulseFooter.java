package com.scwang.smartrefresh.layout.footer;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.ColorUtils;
import com.scwang.smartrefresh.layout.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.internal.InternalAbstract;
import com.scwang.smartrefresh.layout.util.SmartUtil;

/* loaded from: classes6.dex */
public class BallPulseFooter extends InternalAbstract implements RefreshFooter {
    protected int mAnimatingColor;
    protected float mCircleSpacing;
    protected TimeInterpolator mInterpolator;
    protected boolean mIsStarted;
    protected boolean mManualAnimationColor;
    protected boolean mManualNormalColor;
    protected int mNormalColor;
    protected Paint mPaint;
    protected long mStartTime;

    public BallPulseFooter(Context context) {
        this(context, null);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        float fMin = Math.min(width, height);
        float f2 = this.mCircleSpacing;
        float f3 = (fMin - (f2 * 2.0f)) / 6.0f;
        float f4 = f3 * 2.0f;
        float f5 = (width / 2.0f) - (f2 + f4);
        float f6 = height / 2.0f;
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i2 = 0;
        while (i2 < 3) {
            int i3 = i2 + 1;
            float interpolation = this.mInterpolator.getInterpolation((jCurrentTimeMillis - this.mStartTime) - (i3 * 120) > 0 ? (r10 % 750) / 750.0f : 0.0f);
            canvas.save();
            float f7 = i2;
            canvas.translate((f4 * f7) + f5 + (this.mCircleSpacing * f7), f6);
            if (interpolation < 0.5d) {
                float f8 = 1.0f - ((interpolation * 2.0f) * 0.7f);
                canvas.scale(f8, f8);
            } else {
                float f9 = ((interpolation * 2.0f) * 0.7f) - 0.4f;
                canvas.scale(f9, f9);
            }
            canvas.drawCircle(0.0f, 0.0f, f3, this.mPaint);
            canvas.restore();
            i2 = i3;
        }
        super.dispatchDraw(canvas);
        if (this.mIsStarted) {
            invalidate();
        }
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean z2) {
        this.mIsStarted = false;
        this.mStartTime = 0L;
        this.mPaint.setColor(this.mNormalColor);
        return 0;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int i2, int i3) {
        if (this.mIsStarted) {
            return;
        }
        invalidate();
        this.mIsStarted = true;
        this.mStartTime = System.currentTimeMillis();
        this.mPaint.setColor(this.mAnimatingColor);
    }

    public BallPulseFooter setAnimatingColor(@ColorInt int i2) {
        this.mAnimatingColor = i2;
        this.mManualAnimationColor = true;
        if (this.mIsStarted) {
            this.mPaint.setColor(i2);
        }
        return this;
    }

    public BallPulseFooter setNormalColor(@ColorInt int i2) {
        this.mNormalColor = i2;
        this.mManualNormalColor = true;
        if (!this.mIsStarted) {
            this.mPaint.setColor(i2);
        }
        return this;
    }

    @Override // com.scwang.smartrefresh.layout.internal.InternalAbstract, com.scwang.smartrefresh.layout.api.RefreshInternal
    @Deprecated
    public void setPrimaryColors(@ColorInt int... iArr) {
        if (!this.mManualAnimationColor && iArr.length > 1) {
            setAnimatingColor(iArr[0]);
            this.mManualAnimationColor = false;
        }
        if (this.mManualNormalColor) {
            return;
        }
        if (iArr.length > 1) {
            setNormalColor(iArr[1]);
        } else if (iArr.length > 0) {
            setNormalColor(ColorUtils.compositeColors(-1711276033, iArr[0]));
        }
        this.mManualNormalColor = false;
    }

    public BallPulseFooter setSpinnerStyle(SpinnerStyle spinnerStyle) {
        this.mSpinnerStyle = spinnerStyle;
        return this;
    }

    public BallPulseFooter(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.mNormalColor = -1118482;
        this.mAnimatingColor = -1615546;
        this.mStartTime = 0L;
        this.mIsStarted = false;
        this.mInterpolator = new AccelerateDecelerateInterpolator();
        setMinimumHeight(SmartUtil.dp2px(60.0f));
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BallPulseFooter);
        Paint paint = new Paint();
        this.mPaint = paint;
        paint.setColor(-1);
        this.mPaint.setStyle(Paint.Style.FILL);
        this.mPaint.setAntiAlias(true);
        SpinnerStyle spinnerStyle = SpinnerStyle.Translate;
        this.mSpinnerStyle = spinnerStyle;
        this.mSpinnerStyle = SpinnerStyle.values[typedArrayObtainStyledAttributes.getInt(R.styleable.BallPulseFooter_srlClassicsSpinnerStyle, spinnerStyle.ordinal)];
        int i2 = R.styleable.BallPulseFooter_srlNormalColor;
        if (typedArrayObtainStyledAttributes.hasValue(i2)) {
            setNormalColor(typedArrayObtainStyledAttributes.getColor(i2, 0));
        }
        int i3 = R.styleable.BallPulseFooter_srlAnimatingColor;
        if (typedArrayObtainStyledAttributes.hasValue(i3)) {
            setAnimatingColor(typedArrayObtainStyledAttributes.getColor(i3, 0));
        }
        typedArrayObtainStyledAttributes.recycle();
        this.mCircleSpacing = SmartUtil.dp2px(4.0f);
    }
}
